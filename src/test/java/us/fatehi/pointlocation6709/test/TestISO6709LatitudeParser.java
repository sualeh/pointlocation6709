/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2020, Sualeh Fatehi.
 */
package us.fatehi.pointlocation6709.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import us.fatehi.pointlocation6709.parse.CoordinateParser;
import us.fatehi.pointlocation6709.parse.PointLocationParser;

public class TestISO6709LatitudeParser {

  private Map<String, Double> testCases;

  @Test
  public void latitudes() throws Exception {
    final Method method =
        CoordinateParser.class.getDeclaredMethod("parseISO6709Format", String.class);
    method.setAccessible(true);

    final CoordinateParser parser = new CoordinateParser();
    for (final Entry<String, Double> testCase : testCases.entrySet()) {
      final String representation = testCase.getKey();
      final List<String> split = split(representation);
      final String latitudeString = split.get(0);
      final List<String> values = (List<String>) method.invoke(parser, latitudeString);
      assertThat(representation, parseAngle(values), is(testCase.getValue()));
    }
  }

  @BeforeEach
  public void setup() {
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
    testCases.put("+401213.1-0750015.1+2.79CRSxxxx/", 40 + 12 / 60D + 13.1 / 3600D);

    testCases.put("N40W075CRSxxxx/", 40D);
    testCases.put("N40W075/", 40D);
    testCases.put("N40.20361W75.00417CRSxxxx/", 40.20361);
    testCases.put("N4012W07500CRSxxxx/", 40 + 12 / 60D);
    testCases.put("N4012.22W07500.25CRSxxxx/", 40 + 12.22 / 60D);
    testCases.put("N401213W0750015CRSxxxx/", 40 + 12 / 60D + 13 / 3600D);
    testCases.put("N401213.1W0750015.1CRSxxxx/", 40 + 12 / 60D + 13.1 / 3600D);
  }

  private double parseAngle(final List<String> coordinateTokens) {
    final int sign = NumberUtils.toInt(coordinateTokens.get(0), 1);
    final double degrees = NumberUtils.toDouble(coordinateTokens.get(1), 0);
    final double minutes = NumberUtils.toDouble(coordinateTokens.get(2), 0);
    final double seconds = NumberUtils.toDouble(coordinateTokens.get(3), 0);

    final double angle = sign * (degrees + minutes / 60D + seconds / 3600D);
    return angle;
  }

  private List<String> split(final String representation) throws Exception {
    final Method method = PointLocationParser.class.getDeclaredMethod("split", String.class);
    method.setAccessible(true);

    final PointLocationParser parser = new PointLocationParser();
    final List<String> split = (List<String>) method.invoke(parser, representation);
    return split;
  }
}
