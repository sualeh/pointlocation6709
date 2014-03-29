/*
 *
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
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


import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.endsWithAny;
import static org.apache.commons.lang3.StringUtils.indexOf;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.left;
import static org.apache.commons.lang3.StringUtils.right;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import us.fatehi.pointlocation6709.Angle;
import us.fatehi.pointlocation6709.Latitude;
import us.fatehi.pointlocation6709.Longitude;

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

  private final Pattern patternAngleFields = Pattern
    .compile("(\\d+\\.?\\d*)[\u00B0\'\"]\\b*");
  private final Pattern patternCompassDirection1 = Pattern
    .compile(".*([NSEW])");
  private final Pattern patternCompassDirection2 = Pattern
    .compile("([\\+\\-]).*");

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
    try
    {
      final Latitude latitude = new Latitude(parseAngle(latitudeString));
      return latitude;
    }
    catch (final RuntimeException e)
    {
      throw new ParserException("Cannot parse latitude: " + latitudeString);
    }

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
    try
    {
      final Longitude longitude = new Longitude(parseAngle(longitudeString));
      return longitude;
    }
    catch (final RuntimeException e)
    {
      throw new ParserException("Cannot parse longitude: " + longitudeString);
    }

  }

  private int getISO6709HumanFormatSign(final String coordinateString)
    throws ParserException
  {
    // Look for compass points
    final Matcher matcherCompassDirection1 = patternCompassDirection1
      .matcher(coordinateString);
    while (matcherCompassDirection1.find())
    {
      final String token = trimToEmpty(matcherCompassDirection1.group(1))
        .toUpperCase();
      try
      {
        final CompassDirection compassDirection = CompassDirection
          .valueOf(token);
        return compassDirection.getSign();
      }
      catch (final IllegalArgumentException e)
      {
        // Ignore
      }
    }

    // Look for signs
    final Matcher matcherCompassDirection2 = patternCompassDirection2
      .matcher(coordinateString);
    while (matcherCompassDirection2.find())
    {
      final String token = trimToEmpty(matcherCompassDirection2.group(1));
      if (token.equals("-"))
      {
        return -1;
      }
    }

    // No sign, or + found
    return 1;
  }

  private boolean isLikelyISO6709Format(final String coordinateString)
    throws ParserException
  {
    final String representation = trimToEmpty(coordinateString);
    if (isBlank(representation))
    {
      throw new ParserException("No value provided");
    }

    final int countDegrees = countMatches(representation,
                                          Angle.Field.DEGREES.toString());
    final int countMinutes = countMatches(representation,
                                          Angle.Field.MINUTES.toString());
    final int countSeconds = countMatches(representation,
                                          Angle.Field.SECONDS.toString());
    if (countDegrees > 0 || countMinutes > 0 || countSeconds > 0)
    {
      return false;
    }

    if (endsWithAny(representation, "E", "W", "N", "S"))
    {
      return false;
    }

    return true;

  }

  private Angle parseAngle(final String angleString)
    throws ParserException
  {

    final boolean isLikelyISO6709Format = isLikelyISO6709Format(angleString);

    final List<String> coordinateTokens;
    if (isLikelyISO6709Format)
    {
      coordinateTokens = parseISO6709Format(angleString);
    }
    else
    {
      coordinateTokens = parseISO6709HumanFormat(angleString);
    }
    if (coordinateTokens == null || coordinateTokens.size() != 4)
    {
      throw new ParserException("Cannot parse " + angleString);
    }

    final int sign = NumberUtils.toInt(coordinateTokens.get(0), 1);
    final double degrees = NumberUtils.toDouble(coordinateTokens.get(1), 0);
    final double minutes = NumberUtils.toDouble(coordinateTokens.get(2), 0);
    if (Math.abs(minutes) >= 60D)
    {
      throw new ParserException("Too many minutes: " + angleString);
    }
    final double seconds = NumberUtils.toDouble(coordinateTokens.get(3), 0);
    if (Math.abs(seconds) >= 60D)
    {
      throw new ParserException("Too many seconds: " + angleString);
    }

    final double angle = sign * (degrees + minutes / 60D + seconds / 3600D);
    return Angle.fromDegrees(angle);
  }

  private List<String> parseISO6709Format(final String representation)
    throws ParserException
  {
    if (isBlank(representation))
    {
      throw new ParserException("No value provided");
    }

    // Find sign
    int sign = 1;
    final String signChar = left(representation, 1);
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

    final String[] split = split(right(representation,
                                       representation.length() - 1),
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
    String degreesString = left(anglePart, degreeLength);
    if (isBlank(degreesString))
    {
      throw new ParserException("Cannot parse: " + representation);
    }
    final boolean hasMinutes = anglePart.length() > degreeLength;
    if (!hasMinutes)
    {
      degreesString = degreesString + fractionPart;
    }

    // Parse minutes
    String minutesString = "0";
    if (hasMinutes)
    {
      minutesString = substring(anglePart, degreeLength, degreeLength + 2);
    }
    final boolean hasSeconds = anglePart.length() > degreeLength + 2;
    if (hasMinutes && !hasSeconds)
    {
      minutesString = minutesString + fractionPart;
    }

    // Parse seconds
    String secondsString = "0";
    if (hasSeconds)
    {
      secondsString = substring(anglePart, degreeLength + 2);
      secondsString = secondsString + fractionPart;
    }

    final List<String> coordinateTokens = new ArrayList<>();
    coordinateTokens.add(String.valueOf(sign));
    coordinateTokens.add(String.valueOf(degreesString));
    coordinateTokens.add(String.valueOf(minutesString));
    coordinateTokens.add(String.valueOf(secondsString));

    return coordinateTokens;
  }

  private List<String> parseISO6709HumanFormat(final String coordinateString)
    throws ParserException
  {
    final String representation = trimToEmpty(coordinateString);
    if (isBlank(representation))
    {
      throw new ParserException("No value provided");
    }

    validateHumanCoordinate(representation);
    final List<String> angleParts = splitHumanCoordinates(representation);
    final int sign = getISO6709HumanFormatSign(representation);

    final List<String> coordinateTokens = new ArrayList<>();
    coordinateTokens.add(String.valueOf(sign));

    int anglePartsIndex = 0;
    if (contains(representation, Angle.Field.DEGREES.toString()))
    {
      coordinateTokens.add(angleParts.get(anglePartsIndex));
      anglePartsIndex++;
    }
    else
    {
      coordinateTokens.add("0");
    }
    if (contains(representation, Angle.Field.MINUTES.toString()))
    {
      coordinateTokens.add(angleParts.get(anglePartsIndex));
      anglePartsIndex++;
    }
    else
    {
      coordinateTokens.add("0");
    }
    if (contains(representation, Angle.Field.SECONDS.toString()))
    {
      coordinateTokens.add(angleParts.get(anglePartsIndex));
      anglePartsIndex++;
    }
    else
    {
      coordinateTokens.add("0");
    }

    return coordinateTokens;
  }

  private List<String> splitHumanCoordinates(final String coordinateString)
    throws ParserException
  {
    final List<String> tokens = new ArrayList<>();

    final Matcher matcherAngleFields = patternAngleFields
      .matcher(coordinateString);
    while (matcherAngleFields.find())
    {
      final String token = matcherAngleFields.group(1);
      if (StringUtils.isNotBlank(token))
      {
        tokens.add(token);
      }
    }
    if (tokens.size() > 3)
    {
      throw new ParserException("Too many parts in " + coordinateString);
    }

    return tokens;
  }

  private void validateHumanCoordinate(final String representation)
    throws ParserException
  {
    final int countDegrees = countMatches(representation,
                                          Angle.Field.DEGREES.toString());
    final int countMinutes = countMatches(representation,
                                          Angle.Field.MINUTES.toString());
    final int countSeconds = countMatches(representation,
                                          Angle.Field.SECONDS.toString());
    if (countDegrees > 1 || countMinutes > 1 || countSeconds > 1)
    {
      throw new ParserException("Incorrectly formed angle - " + representation);
    }

    final int indexOfDegrees = indexOf(representation,
                                       Angle.Field.DEGREES.toString());
    final int indexOfMinutes = indexOf(representation,
                                       Angle.Field.MINUTES.toString());
    final int indexOfSeconds = indexOf(representation,
                                       Angle.Field.SECONDS.toString());
    if (countMinutes > 0 && indexOfDegrees > indexOfMinutes)
    {
      throw new ParserException("Incorrectly formed angle - " + representation);
    }
    if (countSeconds > 0 && indexOfMinutes > indexOfSeconds)
    {
      throw new ParserException("Incorrectly formed angle - " + representation);
    }
  }

}
