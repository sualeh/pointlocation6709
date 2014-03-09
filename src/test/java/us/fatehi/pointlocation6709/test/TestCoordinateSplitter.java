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

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import us.fatehi.pointlocation6709.parse.CoordinateParser;

public class TestCoordinateSplitter
{

  private Map<String, String> testCases;

  @Before
  public void setup()
  {
    testCases = new HashMap<>();

    testCases.put("48° 36\' 12.20\"", "48~36~12.20");
    testCases.put("+48° 36\' 12.20\"", "48~36~12.20");
    testCases.put("48.60333333333334°", "48.60333333333334");
    testCases.put("48.60333333333334° N", "48.60333333333334");
    testCases.put("48° 36\' 12.20\" N", "48~36~12.20");

    testCases.put("48°36\'12.20\"", "48~36~12.20");
    testCases.put("+48°36\'12.20\"", "48~36~12.20");
    testCases.put("48.60333333333334°", "48.60333333333334");
    testCases.put("48.60333333333334°N", "48.60333333333334");
    testCases.put("48°36\'12.20\"N", "48~36~12.20");

    testCases.put("-0° 36\' 12.20\"", "0~36~12.20");
    testCases.put("0° 36\' 12.20\" S", "0~36~12.20");

    testCases.put("-0°36\'12.20\"", "0~36~12.20");
    testCases.put("0°36\'12.20\"S", "0~36~12.20");
  }

  @Test
  public void split()
    throws Exception
  {
    final Method method = CoordinateParser.class
      .getDeclaredMethod("splitHumanCoordinates", String.class);
    method.setAccessible(true);

    final CoordinateParser parser = new CoordinateParser();
    for (final Entry<String, String> testCase: testCases.entrySet())
    {
      final List<String> split = (List<String>) method
        .invoke(parser, testCase.getKey());
      assertEquals(testCase.getValue(), StringUtils.join(split, "~"));
    }
  }

}
