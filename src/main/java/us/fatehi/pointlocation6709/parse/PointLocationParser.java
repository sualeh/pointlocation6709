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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import us.fatehi.pointlocation6709.Latitude;
import us.fatehi.pointlocation6709.Longitude;
import us.fatehi.pointlocation6709.PointLocation;

/**
 * Parses objects from strings.
 * 
 * @author Sualeh Fatehi
 */
public final class PointLocationParser
{

  /**
   * Parses a string representation of the point location.
   * 
   * @param representation
   *        String representation of the point location
   * @return Point location
   * @throws ParserException
   *         On an exception
   */
  public static PointLocation parsePointLocation(final String representation)
    throws ParserException
  {
    if (StringUtils.isBlank(representation))
    {
      throw new ParserException("No point location value provided");
    }

    PointLocationParser parser = new PointLocationParser();
    CoordinateParser coordinateParser = new CoordinateParser();
    // Split by group
    List<String> tokens = parser.split(representation);
    if (tokens.size() > 4)
    {
      throw new ParserException("Cannot parse " + representation);
    }

    final Latitude latitude;
    if (tokens.size() >= 1)
    {
      latitude = coordinateParser.parseLatitude(tokens.get(0));
    }
    else
    {
      throw new ParserException("No latitude provided for " + representation);
    }

    final Longitude longitude;
    if (tokens.size() >= 2)
    {
      longitude = coordinateParser.parseLongitude(tokens.get(1));
    }
    else
    {
      throw new ParserException("No longitude provided for " + representation);
    }

    final double altitude;
    if (tokens.size() >= 3)
    {
      altitude = NumberUtils.toDouble(tokens.get(2));
    }
    else
    {
      altitude = 0;
    }

    final String crs;
    if (tokens.size() >= 4)
    {
      crs = StringUtils.trimToEmpty(tokens.get(3));
    }
    else
    {
      crs = "";
    }

    final PointLocation pointLocation = new PointLocation(latitude,
                                                          longitude,
                                                          altitude);
    return pointLocation;
  }

  private final Pattern patternCoordinates = Pattern
    .compile("([NSEW\\+\\-]\\d+\\.?\\d*)");
  private final Pattern patternCRS = Pattern.compile(".*CRS(.*)\\/");

  private List<String> split(final String representation)
  {
    final List<String> tokens = new ArrayList<>();

    final Matcher matcherCoordinates = patternCoordinates
      .matcher(representation);
    while (matcherCoordinates.find())
    {
      // group 0 is always the entire match
      final String token = matcherCoordinates.group(1);
      if (StringUtils.isNotBlank(token))
      {
        tokens.add(token);
      }
    }

    final Matcher matcherCRS = patternCRS.matcher(representation);
    while (matcherCRS.find())
    {
      // group 0 is always the entire match
      final String token = matcherCRS.group(1);
      if (StringUtils.isNotBlank(token))
      {
        tokens.add(token);
      }
    }

    return tokens;
  }
}
