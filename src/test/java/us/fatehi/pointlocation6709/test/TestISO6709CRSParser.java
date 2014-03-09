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

import org.junit.Before;
import org.junit.Test;

import us.fatehi.pointlocation6709.parse.CoordinateParser;
import us.fatehi.pointlocation6709.parse.PointLocationParser;

public class TestISO6709CRSParser
{

  private Map<String, String> testCases;

  @Test
  public void crs()
    throws Exception
  {
    final CoordinateParser parser = new CoordinateParser();
    for (final Entry<String, String> testCase: testCases.entrySet())
    {
      final String representation = testCase.getKey();
      final List<String> split = split(representation);
      final String crs = split.get(split.size() - 1);
      assertEquals(representation, testCase.getValue(), crs);
    }
  }

  @Before
  public void setup()
  {
    testCases = new HashMap<>();

    testCases.put("+40-075CRSxxxx/", "xxxx");
    testCases.put("+40-075/", "");
    testCases.put("+40.20361-75.00417CRSxxxx/", "xxxx");
    testCases.put("+4012-07500CRSxxxx/", "xxxx");
    testCases.put("+4012.22-07500.25CRSxxxx/", "xxxx");
    testCases.put("+401213-0750015CRSxxxx/", "xxxx");
    testCases.put("+401213.1-0750015.1CRSxxxx/", "xxxx");

    testCases.put("+40-075+350CRSxxxx/", "xxxx");
    testCases.put("+40.20361-75.00417+350.517CRSxxxx/", "xxxx");
    testCases.put("+4012-07500-169.2CRSxxxx/", "xxxx");
    testCases.put("+4012.22-07500.25-169.2CRSxxxx/", "xxxx");
    testCases.put("+401213-0750015+2.79CRSxxxx/", "xxxx");
    testCases.put("+401213.1-0750015.1+2.79CRSxxxx/", "xxxx");
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
