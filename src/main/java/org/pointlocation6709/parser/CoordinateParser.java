/* 
 * 
 * Daylight Chart
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
package org.pointlocation6709.parser;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.pointlocation6709.Angle;
import org.pointlocation6709.Latitude;
import org.pointlocation6709.Longitude;
import org.pointlocation6709.Angle.Field;

import antlr.RecognitionException;
import antlr.TokenStreamException;

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

    private CompassDirection(int sign)
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
  public static Latitude parseLatitude(final String latitudeString)
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
    if (latitude == null)
    {
      final AntlrPointLocationParser parser = PointLocationParser
        .constructPointLocationParser(representation + "+");
      try
      {
        latitude = parser.latitude();
      }
      catch (final RecognitionException e)
      {
        throw new ParserException("Error parsing \"" + representation + "\"", e);
      }
      catch (final TokenStreamException e)
      {
        throw new ParserException("Error parsing \"" + representation + "\"", e);
      }
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
  public static Longitude parseLongitude(final String longitudeString)
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
    if (longitude == null)
    {
      final AntlrPointLocationParser parser = PointLocationParser
        .constructPointLocationParser(representation + "+");
      try
      {
        longitude = parser.longitude();
      }
      catch (final RecognitionException e)
      {
        throw new ParserException("Error parsing - " + representation, e);
      }
      catch (final TokenStreamException e)
      {
        throw new ParserException("Error parsing - " + representation, e);
      }
    }

    return longitude;
  }

  private static String cleanupCoordinate(final String coordinateString)
    throws ParserException
  {
    // Clean the representation, so that it can be parsed
    if (StringUtils.isBlank(coordinateString))
    {
      throw new ParserException("No value provided");
    }
    String representation = coordinateString.trim();

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
    catch (IllegalArgumentException e)
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
            int currentField = field.ordinal();
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
          Double doubleValue = Double.valueOf(degreePart.trim());
          angleFields[i] = sign * doubleValue;
          if (i > 0 && doubleValue != 0 &&
              angleFields[i - 1] != angleFields[i - 1].intValue())
          {
            throw new ParserException(String
              .format("Cannot use decimal parts when %s is also specified",
                      Field.values()[i].name().toLowerCase()));
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
        List<Double> angleFieldsReversed = Arrays.asList(angleFields);
        Collections.reverse(angleFieldsReversed);
        for (Double part: angleFieldsReversed)
        {
          angleValue = angleValue / 60D + part.doubleValue();
        }
      }

      return String.valueOf(angleValue);
    }

  }

  private static boolean hasSign(final String[] degreeParts)
    throws ParserException
  {
    boolean hasSign = false;
    for (String degreePart: degreeParts)
    {
      if (degreePart.equals("0"))
      {
        continue;
      }
      boolean degreePartHasSign = degreePart.trim().startsWith("+") ||
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

  private static int getSign(final String[] degreeParts)
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

  private CoordinateParser()
  {
    // Prevent instantiation
  }

}
