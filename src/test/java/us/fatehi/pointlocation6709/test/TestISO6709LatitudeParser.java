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
package us.fatehi.pointlocation6709.test;


import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import us.fatehi.pointlocation6709.parse.CoordinateParser;
import us.fatehi.pointlocation6709.parse.PointLocationParser;

public class TestISO6709LatitudeParser
{

  private Map<String, Double> testCases;

  @Test
  public void latitudes()
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
      final String latitudeString = split.get(0);
      final double value = (double) method.invoke(parser, latitudeString);
      assertEquals(representation, (double) testCase.getValue(), value, 1E-12);
    }
  }

  @Before
  public void setup()
  {
    testCases = new HashMap<>();

    testCases.put("+40-075CRSxxxx/", 40D);
    testCases.put("+40-075/", 40D);
    testCases.put("+40.20361-75.00417CRSxxxx/", 40.20361);
    testCases.put("+4012-07500CRSxxxx/", 40 + 12 / 60D);
    testCases.put("+4012.22-07500.25CRSxxxx/", 40 + 12.22 / 60D);
    testCases.put("+401213-0750015CRSxxxx/", 40 + 12 / 60D + 13 / 3600D);
    testCases.put("+401213.1-0750015.1CRSxxxx/", 40 + 12 / 60D + 13.1 / 3600D);

    testCases.put("+40-075+350CRSxxxx/", 40D);
    testCases.put("+40.20361-75.00417+350.517CRSxxxx/", 40.20361);
    testCases.put("+4012-07500-169.2CRSxxxx/", 40 + 12 / 60D);
    testCases.put("+4012.22-07500.25-169.2CRSxxxx/", 40 + 12.22 / 60D);
    testCases.put("+401213-0750015+2.79CRSxxxx/", 40 + 12 / 60D + 13 / 3600D);
    testCases.put("+401213.1-0750015.1+2.79CRSxxxx/", 40 + 12 / 60D + 13.1 /
                                                      3600D);

    testCases.put("N40W075CRSxxxx/", 40D);
    testCases.put("N40W075/", 40D);
    testCases.put("N40.20361W75.00417CRSxxxx/", 40.20361);
    testCases.put("N4012W07500CRSxxxx/", 40 + 12 / 60D);
    testCases.put("N4012.22W07500.25CRSxxxx/", 40 + 12.22 / 60D);
    testCases.put("N401213W0750015CRSxxxx/", 40 + 12 / 60D + 13 / 3600D);
    testCases.put("N401213.1W0750015.1CRSxxxx/", 40 + 12 / 60D + 13.1 / 3600D);
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
