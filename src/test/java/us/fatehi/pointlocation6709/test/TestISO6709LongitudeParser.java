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
package us.fatehi.pointlocation6709.test;


import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;

import us.fatehi.pointlocation6709.parse.CoordinateParser;
import us.fatehi.pointlocation6709.parse.PointLocationParser;

public class TestISO6709LongitudeParser
{

  private Map<String, Double> testCases;

  @Test
  public void longitudes()
    throws Exception
  {
    final Method method = CoordinateParser.class
      .getDeclaredMethod("parseISO6709Format", String.class);
    method.setAccessible(true);

    final CoordinateParser parser = new CoordinateParser();
    for (final Entry<String, Double> testCase: testCases.entrySet())
    {
      final String representation = testCase.getKey();
      final List<String> split = split(representation);
      final String longitudeString = split.get(1);
      final List<String> values = (List<String>) method.invoke(parser,
                                                               longitudeString);
      assertEquals(representation,
                   testCase.getValue(),
                   parseAngle(values),
                   1E-12);
    }
  }

  @Before
  public void setup()
  {
    testCases = new HashMap<>();

    testCases.put("+40-075CRSxxxx/", -75D);
    testCases.put("+40-075/", -75D);
    testCases.put("+40.20361-75.00417CRSxxxx/", -75.00417);
    testCases.put("+4012-07500CRSxxxx/", -75D);
    testCases.put("+4012.22-07500.25CRSxxxx/", -75 - 0.25 / 60D);
    testCases.put("+401213-0750015CRSxxxx/", -75 - 0 / 60D - 15 / 3600D);
    testCases.put("+401213.1-0750015.1CRSxxxx/", -75 - 0 / 60D - 15.1 / 3600D);

    testCases.put("+40-075+350CRSxxxx/", -75D);
    testCases.put("+40.20361-75.00417+350.517CRSxxxx/", -75.00417);
    testCases.put("+4012-07500-169.2CRSxxxx/", -75D);
    testCases.put("+4012.22-07500.25-169.2CRSxxxx/", -75 - 0.25 / 60D);
    testCases.put("+401213-0750015+2.79CRSxxxx/", -75 - 0 / 60D - 15 / 3600D);
    testCases.put("+401213.1-0750015.1+2.79CRSxxxx/", -75 - 0 / 60D - 15.1 /
                                                      3600D);

    testCases.put("N40W075CRSxxxx/", -75D);
    testCases.put("N40W075/", -75D);
    testCases.put("N40.20361W75.00417CRSxxxx/", -75.00417);
    testCases.put("N4012W07500CRSxxxx/", -75D);
    testCases.put("N4012.22W07500.25CRSxxxx/", -75 - 0.25 / 60D);
    testCases.put("N401213W0750015CRSxxxx/", -75 - 0 / 60D - 15 / 3600D);
    testCases.put("N401213.1W0750015.1CRSxxxx/", -75 - 0 / 60D - 15.1 / 3600D);
  }

  private double parseAngle(final List<String> coordinateTokens)
  {
    final int sign = NumberUtils.toInt(coordinateTokens.get(0), 1);
    final double degrees = NumberUtils.toDouble(coordinateTokens.get(1), 0);
    final double minutes = NumberUtils.toDouble(coordinateTokens.get(2), 0);
    final double seconds = NumberUtils.toDouble(coordinateTokens.get(3), 0);

    final double angle = sign * (degrees + minutes / 60D + seconds / 3600D);
    return angle;
  }

  private List<String> split(final String representation)
    throws Exception
  {
    final Method method = PointLocationParser.class
      .getDeclaredMethod("split", String.class);
    method.setAccessible(true);

    final PointLocationParser parser = new PointLocationParser();
    final List<String> split = (List<String>) method.invoke(parser,
                                                            representation);
    return split;
  }

}
