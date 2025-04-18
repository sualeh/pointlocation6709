/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2025, Sualeh Fatehi.
 */

package us.fatehi.pointlocation6709.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.fatehi.pointlocation6709.Angle;
import us.fatehi.pointlocation6709.Angle.Field;
import us.fatehi.pointlocation6709.Latitude;
import us.fatehi.pointlocation6709.Longitude;
import us.fatehi.pointlocation6709.parse.CoordinateParser;
import us.fatehi.pointlocation6709.parse.ParserException;

public class TestCoordinateParser {

  @Test
  @DisplayName("Duplicated degree symbol in latitude")
  public void bad_latitude_1a() throws ParserException {
    assertThrows(
        ParserException.class, () -> new CoordinateParser().parseLatitude("-48° 36° 12.20\""));
  }

  @Test
  @DisplayName("Misplaced degree symbol in latitude")
  public void bad_latitude_2() throws ParserException {
    assertThrows(
        ParserException.class, () -> new CoordinateParser().parseLatitude("48\' 36° 12.20\""));
  }

  @Test
  @DisplayName("Duplicated degree symbol, with negative degrees, and cardinality in latitude")
  public void bad_latitude_3() throws ParserException {
    assertThrows(
        ParserException.class, () -> new CoordinateParser().parseLatitude("-48° 36° 12.20\" S"));
  }

  @Test
  @DisplayName("Duplicated degree symbol, with negative degrees in latitude")
  public void bad_latitude_4() throws ParserException {
    assertThrows(
        ParserException.class, () -> new CoordinateParser().parseLatitude("-48° -36° 12.20\""));
  }

  @Test
  @DisplayName("Misplaced degree symbol in latitude")
  public void bad_latitude_5() throws ParserException {
    assertThrows(
        ParserException.class,
        () -> new CoordinateParser().parseLatitude("48.60333333333334\' 36° 12.20\""));
  }

  @Test
  @DisplayName("Duplicated degree symbol in longitude")
  public void bad_longitude_1a() throws ParserException {
    assertThrows(
        ParserException.class, () -> new CoordinateParser().parseLongitude("-48° 36° 12.20\""));
  }

  @Test
  @DisplayName("Misplaced degree symbol in longitude")
  public void bad_longitude_2() throws ParserException {
    assertThrows(
        ParserException.class, () -> new CoordinateParser().parseLongitude("48\' 36° 12.20\""));
  }

  @Test
  @DisplayName("Duplicated degree symbol, with negative degrees, and cardinality in longitude")
  public void bad_longitude_3() throws ParserException {
    assertThrows(
        ParserException.class, () -> new CoordinateParser().parseLongitude("-48° 36° 12.20\" S"));
  }

  @Test
  @DisplayName("Duplicated degree symbol, with negative degrees in longitude")
  public void bad_longitude_4() throws ParserException {
    assertThrows(
        ParserException.class, () -> new CoordinateParser().parseLongitude("-48° -36° 12.20\""));
  }

  @Test
  @DisplayName("Misplaced degree symbol in longitude")
  public void bad_longitude_5() throws ParserException {
    assertThrows(
        ParserException.class,
        () -> new CoordinateParser().parseLongitude("48.60333333333334\' 36° 12.20\""));
  }

  @Test
  public void latitude_1() throws ParserException {
    parseAndCheckLatitude("+483612.20", 48, 36, 12);
    parseAndCheckLatitude("48° 36\' 12.20\"", 48, 36, 12);
    parseAndCheckLatitude("+48° 36\' 12.20\"", 48, 36, 12);
    parseAndCheckLatitude("48.60333333333334°", 48, 36, 12);
    parseAndCheckLatitude("48.60333333333334° N", 48, 36, 12);
    parseAndCheckLatitude("48° 36\' 12.20\" N", 48, 36, 12);

    parseAndCheckLatitude("-483612.20", -48, -36, -12);
    parseAndCheckLatitude("-48° 36\' 12.20\"", -48, -36, -12);
    parseAndCheckLatitude("-48.60333333333334°", -48, -36, -12);
    parseAndCheckLatitude("48.60333333333334° S", -48, -36, -12);
    parseAndCheckLatitude("48° 36\' 12.20\" S", -48, -36, -12);
  }

  @Test
  public void latitude_2() throws ParserException {
    parseAndCheckLatitude("+003612.20", 0, 36, 12);
    parseAndCheckLatitude("0° 36\' 12.20\"", 0, 36, 12);
    parseAndCheckLatitude("+0° 36\' 12.20\"", 0, 36, 12);
    parseAndCheckLatitude("0° 36\' 12.20\" N", 0, 36, 12);

    parseAndCheckLatitude("-003612.20", 0, -36, -12);
    parseAndCheckLatitude("-0° 36\' 12.20\"", 0, -36, -12);
    parseAndCheckLatitude("0° 36\' 12.20\" S", 0, -36, -12);

    parseAndCheckLatitude("48° 36\'", 48, 36, 0);
    parseAndCheckLatitude("+48° 36\'", 48, 36, 0);
    parseAndCheckLatitude("48° 36\' N", 48, 36, 0);

    parseAndCheckLatitude("-48° 36\'", -48, -36, 0);
    parseAndCheckLatitude("48° 36\' S", -48, -36, 0);

    parseAndCheckLatitude("48° 12.20\"", 48, 0, 12);
    parseAndCheckLatitude("+48° 12.20\"", 48, 0, 12);
    parseAndCheckLatitude("48° 12.20\" N", 48, 0, 12);

    parseAndCheckLatitude("-48° 12.20\"", -48, 0, -12);
    parseAndCheckLatitude("48° 12.20\" S", -48, 0, -12);

    parseAndCheckLatitude("36\' 12.20\"", 0, 36, 12);
    parseAndCheckLatitude("+36\' 12.20\"", 0, 36, 12);
    parseAndCheckLatitude("36\' 12.20\" N", 0, 36, 12);

    parseAndCheckLatitude("-36\' 12.20\"", 0, -36, -12);
    parseAndCheckLatitude("36\' 12.20\" S", 0, -36, -12);

    parseAndCheckLatitude("36\'", 0, 36, 0);
    parseAndCheckLatitude("+36\'", 0, 36, 0);
    parseAndCheckLatitude("36\' N", 0, 36, 0);

    parseAndCheckLatitude("-36\'", 0, -36, 0);
    parseAndCheckLatitude("36\' S", 0, -36, 0);

    parseAndCheckLatitude("12.20\"", 0, 0, 12);
    parseAndCheckLatitude("+12.20\"", 0, 0, 12);
    parseAndCheckLatitude("12.20\" N", 0, 0, 12);

    parseAndCheckLatitude("-12.20\"", 0, 0, -12);
    parseAndCheckLatitude("12.20\" S", 0, 0, -12);
  }

  @Test
  public void longitude_1() throws ParserException {
    parseAndCheckLongitude("+0483612.20", 48, 36, 12);
    parseAndCheckLongitude("48° 36\' 12.20\"", 48, 36, 12);
    parseAndCheckLongitude("+48° 36\' 12.20\"", 48, 36, 12);
    parseAndCheckLongitude("+48°36\'12.20\"", 48, 36, 12);
    parseAndCheckLongitude("48.60333333333334°", 48, 36, 12);
    parseAndCheckLongitude("48.60333333333334° E", 48, 36, 12);
    parseAndCheckLongitude("48.60333333333334°E", 48, 36, 12);
    parseAndCheckLongitude("48° 36\' 12.20\" E", 48, 36, 12);
    parseAndCheckLongitude("48°36\'12.20\"E", 48, 36, 12);

    parseAndCheckLongitude("-0483612.20", -48, -36, -12);
    parseAndCheckLongitude("-48° 36\' 12.20\"", -48, -36, -12);
    parseAndCheckLongitude("-48.60333333333334°", -48, -36, -12);
    parseAndCheckLongitude("48.60333333333334° W", -48, -36, -12);
    parseAndCheckLongitude("48° 36\' 12.20\" W", -48, -36, -12);
  }

  @Test
  public void longitude_2() throws ParserException {
    parseAndCheckLongitude("+0003612.20", 0, 36, 12);
    parseAndCheckLongitude("0° 36\' 12.20\"", 0, 36, 12);
    parseAndCheckLongitude("+0° 36\' 12.20\"", 0, 36, 12);
    parseAndCheckLongitude("0° 36\' 12.20\" E", 0, 36, 12);

    parseAndCheckLongitude("-0003612.20", 0, -36, -12);
    parseAndCheckLongitude("-0° 36\' 12.20\"", 0, -36, -12);
    parseAndCheckLongitude("0° 36\' 12.20\" W", 0, -36, -12);

    parseAndCheckLongitude("48° 36\'", 48, 36, 0);
    parseAndCheckLongitude("+48° 36\'", 48, 36, 0);
    parseAndCheckLongitude("48° 36\' E", 48, 36, 0);

    parseAndCheckLongitude("-48° 36\'", -48, -36, 0);
    parseAndCheckLongitude("48° 36\' W", -48, -36, 0);

    parseAndCheckLongitude("48° 12.20\"", 48, 0, 12);
    parseAndCheckLongitude("+48° 12.20\"", 48, 0, 12);
    parseAndCheckLongitude("48° 12.20\" E", 48, 0, 12);

    parseAndCheckLongitude("-48° 12.20\"", -48, 0, -12);
    parseAndCheckLongitude("48° 12.20\" W", -48, 0, -12);

    parseAndCheckLongitude("36\' 12.20\"", 0, 36, 12);
    parseAndCheckLongitude("+36\' 12.20\"", 0, 36, 12);
    parseAndCheckLongitude("36\' 12.20\" E", 0, 36, 12);

    parseAndCheckLongitude("-36\' 12.20\"", 0, -36, -12);
    parseAndCheckLongitude("36\' 12.20\" W", 0, -36, -12);

    parseAndCheckLongitude("36\'", 0, 36, 0);
    parseAndCheckLongitude("+36\'", 0, 36, 0);
    parseAndCheckLongitude("36\' E", 0, 36, 0);

    parseAndCheckLongitude("-36\'", 0, -36, 0);
    parseAndCheckLongitude("36\' W", 0, -36, 0);

    parseAndCheckLongitude("12.20\"", 0, 0, 12);
    parseAndCheckLongitude("+12.20\"", 0, 0, 12);
    parseAndCheckLongitude("12.20\" E", 0, 0, 12);

    parseAndCheckLongitude("-12.20\"", 0, 0, -12);
    parseAndCheckLongitude("12.20\" W", 0, 0, -12);
  }

  private void checkAngle(
      final Angle angle,
      final String string,
      final int degrees,
      final int minutes,
      final int seconds) {
    assertThat(string + " - degrees do not match", angle.getField(Field.DEGREES), is(degrees));
    assertThat(string + " - minutes do not match", angle.getField(Field.MINUTES), is(minutes));
    assertThat(string + " - seconds do not match", angle.getField(Field.SECONDS), is(seconds));
  }

  private void parseAndCheckLatitude(
      final String string, final int degrees, final int minutes, final int seconds)
      throws ParserException {
    final Latitude latitude = new CoordinateParser().parseLatitude(string);
    checkAngle(latitude, string, degrees, minutes, seconds);
  }

  private void parseAndCheckLongitude(
      final String string, final int degrees, final int minutes, final int seconds)
      throws ParserException {
    final Longitude longitude = new CoordinateParser().parseLongitude(string);
    checkAngle(longitude, string, degrees, minutes, seconds);
  }
}
