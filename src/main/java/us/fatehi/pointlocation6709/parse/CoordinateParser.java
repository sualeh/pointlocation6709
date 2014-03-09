/* 
 * 
 * Point Location 6709
 * http://sourceforge.net/projects/daylightchart
 * Copyright (c) 2007-2014, Sualeh Fatehi.
 * 
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */
package us.fatehi.pointlocation6709.parse;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import us.fatehi.pointlocation6709.Angle;
import us.fatehi.pointlocation6709.Latitude;
import us.fatehi.pointlocation6709.Longitude;
import us.fatehi.pointlocation6709.Angle.Field;

/**
 * Parses objects from strings.
 * 
 * @author Sualeh Fatehi
 */
public final class CoordinateParser
{

  /**
   * Compass direction, for parsing.
   */
  private enum CompassDirection
  {
    N(1), S(-1), E(1), W(-1);

    private final int sign;

    private CompassDirection(final int sign)
    {
      this.sign = sign;
    }

    /**
     * @return the sign
     */
    final int getSign()
    {
      return sign;
    }

  }

  /**
   * Parses a string representation of the latitude.
   * 
   * @param latitudeString
   *        String representation of the point location
   * @return Latitude
   * @throws ParserException
   *         On an exception
   */
  public Latitude parseLatitude(final String latitudeString)
    throws ParserException
  {

    final String representation = cleanupCoordinate(latitudeString);
    Latitude latitude;

    // 1. Attempt to parse as an angle
    try
    {
      latitude = new Latitude(Angle.fromDegrees(Double
        .parseDouble(representation)));
    }
    catch (final RuntimeException e)
    {
      latitude = null;
    }

    // 2. Attempt to parse in ISO 6709 format
    try
    {
      if (latitude == null)
      {
        latitude = new Latitude(Angle.fromDegrees(parseISO6709Format(representation)));
      }
    }
    catch (final RuntimeException e)
    {
      throw new ParserException("Cannot parse latitude: " + latitudeString);
    }

    return latitude;
  }

  /**
   * Parses a string representation of the longitude.
   * 
   * @param longitudeString
   *        String representation of the longitude
   * @return Longitude
   * @throws ParserException
   *         On an exception
   */
  public Longitude parseLongitude(final String longitudeString)
    throws ParserException
  {

    final String representation = cleanupCoordinate(longitudeString);
    Longitude longitude;

    // 1. Attempt to parse as an angle
    try
    {
      longitude = new Longitude(Angle.fromDegrees(Double
        .parseDouble(representation)));
    }
    catch (final RuntimeException e)
    {
      longitude = null;
    }

    // 2. Attempt to parse in ISO 6709 format
    try
    {
      if (longitude == null)
      {
        longitude = new Longitude(Angle.fromDegrees(parseISO6709Format(representation)));
      }
    }
    catch (final RuntimeException e)
    {
      throw new ParserException("Cannot parse longitude: " + longitudeString);
    }

    return longitude;
  }

  private String cleanupCoordinate(final String coordinateString)
    throws ParserException
  {
    // Clean the representation, so that it can be parsed
    if (StringUtils.isBlank(coordinateString))
    {
      throw new ParserException("No value provided");
    }
    final String representation = coordinateString.trim();

    boolean isIso6709Format = true;

    // Validate format
    final int countDegrees = StringUtils.countMatches(representation,
                                                      Angle.Field.DEGREES
                                                        .toString());
    final int countMinutes = StringUtils.countMatches(representation,
                                                      Angle.Field.MINUTES
                                                        .toString());
    final int countSeconds = StringUtils.countMatches(representation,
                                                      Angle.Field.SECONDS
                                                        .toString());
    if (countDegrees > 1 || countMinutes > 1 || countSeconds > 1)
    {
      throw new ParserException("Incorrectly formed angle - " +
                                coordinateString);
    }
    if (countDegrees > 0 || countMinutes > 0 || countSeconds > 0)
    {
      isIso6709Format = false;
    }

    final List<String> parts = new ArrayList<String>(Arrays.asList(StringUtils
      .split(representation, ' ')));
    if (parts.size() > 1)
    {
      isIso6709Format = false;
    }

    try
    {
      Field.valueOf(parts.get(0).substring(0, parts.get(0).length() - 1));
      isIso6709Format = false;
    }
    catch (final IllegalArgumentException e)
    {
      // Is in ISO 6709 format
    }

    CompassDirection compassDirection = null;
    if (parts.size() > 4)
    {
      throw new ParserException("Incorrectly formed angle - " +
                                coordinateString);
    }

    final String[] degreeParts = new String[3];
    if (!isIso6709Format)
    {
      for (String part: parts)
      {
        part = part.trim();
        for (final Field field: Field.values())
        {
          if (part.endsWith(field.toString()))
          {
            final int currentField = field.ordinal();
            for (int i = currentField; i < degreeParts.length; i++)
            {
              if (degreeParts[i] != null)
              {
                throw new ParserException("Degree fields are out of order");
              }
            }
            degreeParts[currentField] = part.substring(0, part.length() - 1);
            break;
          }
        }
      }
      for (int i = 0; i < degreeParts.length; i++)
      {
        if (degreeParts[i] == null)
        {
          degreeParts[i] = "0";
        }
      }

      try
      {
        compassDirection = CompassDirection.valueOf(parts.get(parts.size() - 1)
          .trim().toUpperCase());
      }
      catch (final IllegalArgumentException e)
      {
        compassDirection = null;
      }
    }

    // Attempt to find the compass direction, and thus the sign of the
    // angle
    int sign = 1;
    if (!isIso6709Format)
    {
      final boolean hasSign = hasSign(degreeParts);

      if (compassDirection != null && hasSign)
      {
        throw new ParserException("Corordinate cannot have a compass direction, as well as a signed angle");
      }

      if (compassDirection != null)
      {
        sign = compassDirection.getSign();
      }
      if (hasSign)
      {
        sign = getSign(degreeParts);
      }
    }

    if (isIso6709Format)
    {
      return coordinateString.trim();
    }
    else
    {
      // Parse all the numbers
      final Double[] angleFields = new Double[3];
      for (int i = 0; i < degreeParts.length; i++)
      {
        final String degreePart = degreeParts[i];
        try
        {
          final Double doubleValue = toDouble(degreePart.trim());
          angleFields[i] = sign * doubleValue;
          if (i > 0 && doubleValue != 0 &&
              angleFields[i - 1] != angleFields[i - 1].intValue())
          {
            throw new ParserException(String.format("Cannot use decimal parts when %s are also specified",
                                                    Field.values()[i]
                                                      .getDescription()));
          }
        }
        catch (final NumberFormatException e)
        {
          throw new ParserException("Incorrectly formed angle - " +
                                    coordinateString);
        }
      }

      double angleValue = 0D;
      if (angleFields != null)
      {
        final List<Double> angleFieldsReversed = Arrays.asList(angleFields);
        Collections.reverse(angleFieldsReversed);
        for (final Double part: angleFieldsReversed)
        {
          angleValue = angleValue / 60D + part.doubleValue();
        }
      }

      return String.valueOf(angleValue);
    }

  }

  private int getSign(final String[] degreeParts)
  {
    int sign = 1;
    for (int i = 0; i < degreeParts.length; i++)
    {
      final String degreePart = degreeParts[i];
      if (degreePart.equals("0"))
      {
        continue;
      }
      if (degreePart.trim().startsWith("-"))
      {
        sign = -1;
      }
      if (degreePart.trim().startsWith("+") ||
          degreePart.trim().startsWith("-"))
      {
        // Strip the sign
        degreeParts[i] = degreePart.trim().substring(1).trim();
      }
    }
    return sign;
  }

  private boolean hasSign(final String[] degreeParts)
    throws ParserException
  {
    boolean hasSign = false;
    for (final String degreePart: degreeParts)
    {
      if (degreePart.equals("0"))
      {
        continue;
      }
      final boolean degreePartHasSign = degreePart.trim().startsWith("+") ||
                                        degreePart.trim().startsWith("-");
      if (hasSign && degreePartHasSign)
      {
        throw new ParserException("Cannot specify the sign more than once");
      }
      if (degreePartHasSign)
      {
        hasSign = true;
      }
    }
    return hasSign;
  }

  private double parseISO6709Format(final String representation)
    throws ParserException
  {
    if (StringUtils.isBlank(representation))
    {
      throw new ParserException("No value provided");
    }

    // Find sign
    int sign = 1;
    final String signChar = StringUtils.left(representation, 1);
    try
    {
      final CompassDirection compassDirection = CompassDirection
        .valueOf(signChar.toUpperCase());
      sign = compassDirection.getSign();
    }
    catch (final IllegalArgumentException e)
    {
      if (signChar.equals("-"))
      {
        sign = -1;
      }
      else if (!signChar.equals("+"))
      {
        throw new ParserException("Cannot parse: " + representation);
      }
    }

    final String[] split = StringUtils
      .split(StringUtils.right(representation, representation.length() - 1),
             ".");
    if (split.length < 1 || split.length > 2)
    {
      throw new ParserException("Cannot parse: " + representation);
    }
    final String anglePart = split[0];
    final String fractionPart;
    if (split.length == 2)
    {
      fractionPart = "." + split[1];
    }
    else
    {
      fractionPart = "";
    }

    // Parse degrees
    int degreeLength;
    if (anglePart.length() % 2 == 0)
    {
      degreeLength = 2;
    }
    else
    {
      degreeLength = 3;
    }
    String degreesString = StringUtils.left(anglePart, degreeLength);
    if (StringUtils.isBlank(degreesString))
    {
      throw new ParserException("Cannot parse: " + representation);
    }
    final boolean hasMinutes = anglePart.length() > degreeLength;
    if (!hasMinutes)
    {
      degreesString = degreesString + fractionPart;
    }
    final double degrees = toDouble(degreesString);

    // Parse minutes
    String minutesString = "";
    if (hasMinutes)
    {
      minutesString = StringUtils.substring(anglePart,
                                            degreeLength,
                                            degreeLength + 2);
    }
    final boolean hasSeconds = anglePart.length() > degreeLength + 2;
    if (hasMinutes && !hasSeconds)
    {
      minutesString = minutesString + fractionPart;
    }
    final double minutes = NumberUtils.toDouble(minutesString, 0);
    if (Math.abs(minutes) >= 60D)
    {
      throw new ParserException("Too many minutes: " + representation);
    }

    // Parse seconds
    String secondsString = "";
    if (hasSeconds)
    {
      secondsString = StringUtils.substring(anglePart, degreeLength + 2);
      secondsString = secondsString + fractionPart;
    }
    final double seconds = NumberUtils.toDouble(secondsString, 0);
    if (Math.abs(seconds) >= 60D)
    {
      throw new ParserException("Too many seconds: " + representation);
    }

    final double angle = sign * (degrees + minutes / 60D + seconds / 3600D);
    return angle;
  }

  private double toDouble(final String representation)
    throws ParserException
  {
    if (StringUtils.isBlank(representation))
    {
      throw new ParserException("Cannot parse: " + representation);
    }
    try
    {
      return Double.parseDouble(representation);
    }
    catch (final NumberFormatException nfe)
    {
      throw new ParserException("Cannot parse: " + representation);
    }
  }

}
