/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2025, Sualeh Fatehi.
 */
package us.fatehi.pointlocation6709.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import us.fatehi.pointlocation6709.parse.CoordinateParser;

public class TestCoordinateSplitter {

  private Map<String, String> testCases;

  @BeforeEach
  public void setup() {
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
  public void split() throws Exception {
    final Method method =
        CoordinateParser.class.getDeclaredMethod("splitHumanCoordinates", String.class);
    method.setAccessible(true);

    final CoordinateParser parser = new CoordinateParser();
    for (final Entry<String, String> testCase : testCases.entrySet()) {
      final List<String> split = (List<String>) method.invoke(parser, testCase.getKey());
      assertThat(StringUtils.join(split, "~"), is(testCase.getValue()));
    }
  }
}
