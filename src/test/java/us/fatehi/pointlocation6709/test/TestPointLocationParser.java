/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2026, Sualeh Fatehi.
 */
package us.fatehi.pointlocation6709.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import us.fatehi.pointlocation6709.Angle;
import us.fatehi.pointlocation6709.PointLocation;
import us.fatehi.pointlocation6709.format.FormatterException;
import us.fatehi.pointlocation6709.format.PointLocationFormatType;
import us.fatehi.pointlocation6709.format.PointLocationFormatter;
import us.fatehi.pointlocation6709.parse.ParserException;
import us.fatehi.pointlocation6709.parse.PointLocationParser;

public class TestPointLocationParser {

  @Test
  public void badPointLocation_1() {
    assertThrows(ParserException.class, () -> PointLocationParser.parsePointLocation("+40/"));
  }

  @Test
  public void badPointLocation_2() {
    assertThrows(ParserException.class, () -> PointLocationParser.parsePointLocation("4000/"));
  }

  @Test
  public void badPointLocation_3() {
    assertThrows(
        ParserException.class,
        () -> PointLocationParser.parsePointLocation("+40121300-075001500/"));
  }

  @Test
  public void badPointLocation_4() {
    assertThrows(
        ParserException.class, () -> PointLocationParser.parsePointLocation("+40121-075001/"));
  }

  @Test
  public void invalidPointLocation_1() {
    assertThrows(
        ParserException.class, () -> PointLocationParser.parsePointLocation("+4060-07560/"));
  }

  @Test
  public void invalidPointLocation_2() {
    assertThrows(
        ParserException.class, () -> PointLocationParser.parsePointLocation("+4060.22-07560.25/"));
  }

  @Test
  public void invalidPointLocation_3() {
    assertThrows(
        ParserException.class, () -> PointLocationParser.parsePointLocation("+401260-0750060/"));
  }

  @Test
  public void invalidPointLocation_4() {
    assertThrows(
        ParserException.class,
        () -> PointLocationParser.parsePointLocation("+401260.22-0750060.22/"));
  }

  @Test
  public void pointLocation_a() throws ParserException, FormatterException {

    final String pointLocationString = "+40-075/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getAltitude(), is(0D));

    assertThat(formattedPointLocationString1, is("+400000-0750000/"));
    assertThat(formattedPointLocationString2, is("+40.00000-075.00000/"));
  }

  @Test
  public void pointLocation_b() throws ParserException, FormatterException {

    final String pointLocationString = "+40.20361-075.00417/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(12));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(13));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(-15));

    assertThat(pointLocation.getAltitude(), is(0D));

    assertThat(formattedPointLocationString1, is("+401213-0750015/"));
    assertThat(formattedPointLocationString2, is("+40.20361-075.00417/"));
  }

  @Test
  public void pointLocation_c() throws ParserException, FormatterException {

    final String pointLocationString = "+4012-07500/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(12));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getAltitude(), is(0D));

    assertThat(formattedPointLocationString1, is("+401200-0750000/"));
    assertThat(formattedPointLocationString2, is("+40.20000-075.00000/"));
  }

  @Test
  public void pointLocation_d() throws ParserException, FormatterException {

    final String pointLocationString = "+4012.22-07500.25/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(12));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(13));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(-15));

    assertThat(pointLocation.getAltitude(), is(0D));

    assertThat(formattedPointLocationString1, is("+401213-0750015/"));
    assertThat(formattedPointLocationString2, is("+40.20367-075.00417/"));
  }

  @Test
  public void pointLocation_e() throws ParserException, FormatterException {

    final String pointLocationString = "+401213-0750015/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(12));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(13));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(-15));

    assertThat(pointLocation.getAltitude(), is(0D));

    assertThat(formattedPointLocationString1, is("+401213-0750015/"));
    assertThat(formattedPointLocationString2, is("+40.20361-075.00417/"));
  }

  @Test
  public void pointLocation_f() throws ParserException, FormatterException {

    final String pointLocationString = "+401213.1-0750015.1/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(12));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(13));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(-15));

    assertThat(pointLocation.getAltitude(), is(0D));

    assertThat(formattedPointLocationString1, is("+401213-0750015/"));
    assertThat(formattedPointLocationString2, is("+40.20364-075.00419/"));
  }

  @Test
  public void pointLocation_g() throws ParserException, FormatterException {

    final String pointLocationString = "+40-075+350/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getAltitude(), is(350D));

    assertThat(formattedPointLocationString1, is("+400000-0750000+350.00000/"));
    assertThat(formattedPointLocationString2, is("+40.00000-075.00000+350.00000/"));
  }

  @Test
  public void pointLocation_h() throws ParserException, FormatterException {

    final String pointLocationString = "+40.20361-075.00417+350.517/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(12));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(13));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(-15));

    assertThat(pointLocation.getAltitude(), is(350.517));

    assertThat(formattedPointLocationString1, is("+401213-0750015+350.51700/"));
    assertThat(formattedPointLocationString2, is("+40.20361-075.00417+350.51700/"));
  }

  @Test
  public void pointLocation_j() throws ParserException, FormatterException {

    final String pointLocationString = "+4012-07500-169.2/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(12));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getAltitude(), is(-169.2));

    assertThat(formattedPointLocationString1, is("+401200-0750000-169.20000/"));
    assertThat(formattedPointLocationString2, is("+40.20000-075.00000-169.20000/"));
  }

  @Test
  public void pointLocation_k() throws ParserException, FormatterException {

    final String pointLocationString = "+4012.22-07500.25-169.2/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(12));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(13));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(-15));

    assertThat(pointLocation.getAltitude(), is(-169.2));

    assertThat(formattedPointLocationString1, is("+401213-0750015-169.20000/"));
    assertThat(formattedPointLocationString2, is("+40.20367-075.00417-169.20000/"));
  }

  @Test
  public void pointLocation_m() throws ParserException, FormatterException {

    final String pointLocationString = "+401213-0750015+2.79/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(12));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(13));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(-15));

    assertThat(pointLocation.getAltitude(), is(2.79));

    assertThat(formattedPointLocationString1, is("+401213-0750015+2.79000/"));
    assertThat(formattedPointLocationString2, is("+40.20361-075.00417+2.79000/"));
  }

  @Test
  public void pointLocation_n() throws ParserException, FormatterException {

    final String pointLocationString = "+401213.1-0750015.1+2.79/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(12));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(13));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(-15));

    assertThat(pointLocation.getAltitude(), is(2.79));

    assertThat(formattedPointLocationString1, is("+401213-0750015+2.79000/"));
    assertThat(formattedPointLocationString2, is("+40.20364-075.00419+2.79000/"));
  }

  @Test
  public void pointLocationBoundary_1() throws ParserException, FormatterException {

    final String pointLocationString = "+40-180/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(40));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-180));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getAltitude(), is(0D));

    assertThat(formattedPointLocationString1, is("+400000-1800000/"));
    assertThat(formattedPointLocationString2, is("+40.00000-180.00000/"));
  }

  @Test
  public void pointLocationBoundary_2() {
    assertThrows(ParserException.class, () -> PointLocationParser.parsePointLocation("+40+180/"));
  }

  @Test
  public void pointLocationBoundary_3() throws ParserException, FormatterException {

    final String pointLocationString = "+90-075/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(90));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getAltitude(), is(0D));

    assertThat(formattedPointLocationString1, is("+900000-0750000/"));
    assertThat(formattedPointLocationString2, is("+90.00000-075.00000/"));
  }

  @Test
  public void pointLocationBoundary_4() throws ParserException, FormatterException {

    final String pointLocationString = "-90-075/";
    final PointLocation pointLocation = PointLocationParser.parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 =
        PointLocationFormatter.formatPointLocation(pointLocation, PointLocationFormatType.DECIMAL);

    assertThat(pointLocation.getLatitude().getField(Angle.Field.DEGREES), is(-90));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLatitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getLongitude().getField(Angle.Field.DEGREES), is(-75));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.MINUTES), is(0));
    assertThat(pointLocation.getLongitude().getField(Angle.Field.SECONDS), is(0));

    assertThat(pointLocation.getAltitude(), is(0D));

    assertThat(formattedPointLocationString1, is("-900000-0750000/"));
    assertThat(formattedPointLocationString2, is("-90.00000-075.00000/"));
  }
}
