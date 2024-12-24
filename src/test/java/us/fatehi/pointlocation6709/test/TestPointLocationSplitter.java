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

import us.fatehi.pointlocation6709.parse.PointLocationParser;

public class TestPointLocationSplitter {

  private Map<String, String> testCases;

  @BeforeEach
  public void setup() {
    testCases = new HashMap<>();
    testCases.put("+40-075CRSxxxx/", "+40~-075~0~xxxx");
    testCases.put("+40-075/", "+40~-075~0~");
    testCases.put("+40.20361-75.00417CRSxxxx/", "+40.20361~-75.00417~0~xxxx");
    testCases.put("+4012-07500CRSxxxx/", "+4012~-07500~0~xxxx");
    testCases.put("+4012.22-07500.25CRSxxxx/", "+4012.22~-07500.25~0~xxxx");
    testCases.put("+401213-0750015CRSxxxx/", "+401213~-0750015~0~xxxx");
    testCases.put("+401213.1-0750015.1CRSxxxx/", "+401213.1~-0750015.1~0~xxxx");

    testCases.put("+40-075+350CRSxxxx/", "+40~-075~+350~xxxx");
    testCases.put("+40.20361-75.00417+350.517CRSxxxx/", "+40.20361~-75.00417~+350.517~xxxx");
    testCases.put("+4012-07500-169.2CRSxxxx/", "+4012~-07500~-169.2~xxxx");
    testCases.put("+4012.22-07500.25-169.2CRSxxxx/", "+4012.22~-07500.25~-169.2~xxxx");
    testCases.put("+401213-0750015+2.79CRSxxxx/", "+401213~-0750015~+2.79~xxxx");
    testCases.put("+401213.1-0750015.1+2.79CRSxxxx/", "+401213.1~-0750015.1~+2.79~xxxx");
  }

  @Test
  public void split() throws Exception {
    final Method method = PointLocationParser.class.getDeclaredMethod("split", String.class);
    method.setAccessible(true);

    final PointLocationParser parser = new PointLocationParser();
    for (final Entry<String, String> testCase : testCases.entrySet()) {
      final List<String> split = (List<String>) method.invoke(parser, testCase.getKey());
      assertThat(StringUtils.join(split, "~"), is(testCase.getValue()));
    }
  }
}
