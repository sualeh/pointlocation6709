/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2025, Sualeh Fatehi.
 */

package us.fatehi.pointlocation6709.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static us.fatehi.pointlocation6709.format.PointLocationFormatter.formatPointLocation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import us.fatehi.pointlocation6709.PointLocation;
import us.fatehi.pointlocation6709.format.FormatterException;
import us.fatehi.pointlocation6709.format.PointLocationFormatType;
import us.fatehi.pointlocation6709.parse.ParserException;
import us.fatehi.pointlocation6709.parse.PointLocationParser;

public class TestFormatter {

  private final PointLocation[] pointLocation = new PointLocation[3];

  @BeforeEach
  public void _setup() throws ParserException {
    pointLocation[0] = PointLocationParser.parsePointLocation("+401213.123-0750015.123/");
    pointLocation[1] = PointLocationParser.parsePointLocation("+401213.123-075+23.23/");
    pointLocation[2] = PointLocationParser.parsePointLocation("+401213.123-075-13.13CRScustom/");
  }

  @Test
  public void decimalFormat() throws FormatterException {
    final PointLocationFormatType format = PointLocationFormatType.DECIMAL;
    assertThat(formatPointLocation(pointLocation[0], format), is("+40.20365-075.00420/"));
    assertThat(formatPointLocation(pointLocation[1], format), is("+40.20365-075.00000+23.23000/"));
    assertThat(
        formatPointLocation(pointLocation[2], format),
        is("+40.20365-075.00000-13.13000CRScustom/"));
  }

  @Test
  public void humanLong() {
    assertThat(pointLocation[0].toString(), is("40°12'13\"N 75°00'15\"W"));
    assertThat(pointLocation[1].toString(), is("40°12'13\"N 75°00'00\"W 23.230"));
    assertThat(pointLocation[2].toString(), is("40°12'13\"N 75°00'00\"W -13.130"));
  }

  @Test
  public void humanMedium() throws FormatterException {
    final PointLocationFormatType format = PointLocationFormatType.HUMAN_MEDIUM;
    assertThat(formatPointLocation(pointLocation[0], format), is("40°12'N 75°00'W"));
    assertThat(formatPointLocation(pointLocation[1], format), is("40°12'N 75°00'W +23.23000"));
    assertThat(formatPointLocation(pointLocation[2], format), is("40°12'N 75°00'W -13.13000"));
  }

  @Test
  public void humanShort() throws FormatterException {
    final PointLocationFormatType format = PointLocationFormatType.HUMAN_SHORT;
    assertThat(formatPointLocation(pointLocation[0], format), is("40°N 75°W"));
    assertThat(formatPointLocation(pointLocation[1], format), is("40°N 75°W +23.23000"));
    assertThat(formatPointLocation(pointLocation[2], format), is("40°N 75°W -13.13000"));
  }

  @Test
  public void longFormat() throws FormatterException {
    final PointLocationFormatType format = PointLocationFormatType.LONG;
    assertThat(formatPointLocation(pointLocation[0], format), is("+401213-0750015/"));
    assertThat(formatPointLocation(pointLocation[1], format), is("+401213-0750000+23.23000/"));
    assertThat(
        formatPointLocation(pointLocation[2], format), is("+401213-0750000-13.13000CRScustom/"));
  }

  @Test
  public void mediumFormat() throws FormatterException {
    final PointLocationFormatType format = PointLocationFormatType.MEDIUM;
    assertThat(formatPointLocation(pointLocation[0], format), is("+4012-07500/"));
    assertThat(formatPointLocation(pointLocation[1], format), is("+4012-07500+23.23000/"));
    assertThat(formatPointLocation(pointLocation[2], format), is("+4012-07500-13.13000CRScustom/"));
  }

  @Test
  public void shortFormat() throws FormatterException {
    final PointLocationFormatType format = PointLocationFormatType.SHORT;
    assertThat(formatPointLocation(pointLocation[0], format), is("+40-075/"));
    assertThat(formatPointLocation(pointLocation[1], format), is("+40-075+23.23000/"));
    assertThat(formatPointLocation(pointLocation[2], format), is("+40-075-13.13000CRScustom/"));
  }
}
