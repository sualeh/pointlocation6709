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
package org.pointlocation6709.test;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.pointlocation6709.Angle;
import org.pointlocation6709.PointLocation;
import org.pointlocation6709.format.FormatterException;
import org.pointlocation6709.format.PointLocationFormatType;
import org.pointlocation6709.format.PointLocationFormatter;
import org.pointlocation6709.parse.ParserException;
import org.pointlocation6709.parse.PointLocationParser;

public class TestPointLocationParser
{

  @Test(expected = ParserException.class)
  public void badPointLocation_1()
    throws ParserException, FormatterException
  {
    PointLocationParser.parsePointLocation("+40/");
  }

  @Test(expected = ParserException.class)
  public void badPointLocation_2()
    throws ParserException, FormatterException
  {
    PointLocationParser.parsePointLocation("4000/");
  }

  @Test(expected = ParserException.class)
  public void badPointLocation_3()
    throws ParserException, FormatterException
  {
    PointLocationParser.parsePointLocation("+40121300-075001500/");
  }

  @Test(expected = ParserException.class)
  public void badPointLocation_4()
    throws ParserException, FormatterException
  {
    PointLocationParser.parsePointLocation("+40121-075001/");
  }

  @Test(expected = ParserException.class)
  public void invalidPointLocation_1()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+4060-07560/";
    /* final PointLocation pointLocation = */PointLocationParser
      .parsePointLocation(pointLocationString);
  }

  @Test(expected = ParserException.class)
  public void invalidPointLocation_2()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+4060.22-07560.25/";
    /* final PointLocation pointLocation = */PointLocationParser
      .parsePointLocation(pointLocationString);
  }

  @Test(expected = ParserException.class)
  public void invalidPointLocation_3()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+401260-0750060/";
    /* final PointLocation pointLocation = */PointLocationParser
      .parsePointLocation(pointLocationString);
  }

  @Test(expected = ParserException.class)
  public void invalidPointLocation_4()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+401260.22-0750060.22/";
    /* final PointLocation pointLocation = */PointLocationParser
      .parsePointLocation(pointLocationString);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_a()
    throws ParserException, FormatterException, FormatterException
  {

    final String pointLocationString = "+40-075/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.SECONDS));

    assertEquals(0D, pointLocation.getAltitude(), 1e-10);

    assertEquals("+400000-0750000/", formattedPointLocationString1);
    assertEquals("+40.0-075.0/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_b()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+40.20361-075.00417/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(12, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(13, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(-15, pointLocation.getLongitude()
      .getField(Angle.Field.SECONDS));

    assertEquals(0D, pointLocation.getAltitude(), 1e-10);

    assertEquals("+401213-0750015/", formattedPointLocationString1);
    assertEquals("+40.20361-075.00417/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_c()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+4012-07500/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(12, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.SECONDS));

    assertEquals(0D, pointLocation.getAltitude(), 1e-10);

    assertEquals("+401200-0750000/", formattedPointLocationString1);
    assertEquals("+40.2-075.0/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_d()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+4012.22-07500.25/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(12, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(13, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(-15, pointLocation.getLongitude()
      .getField(Angle.Field.SECONDS));

    assertEquals(0D, pointLocation.getAltitude(), 1e-10);

    assertEquals("+401213-0750015/", formattedPointLocationString1);
    assertEquals("+40.20367-075.00417/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_e()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+401213-0750015/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(12, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(13, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(-15, pointLocation.getLongitude()
      .getField(Angle.Field.SECONDS));

    assertEquals(0D, pointLocation.getAltitude(), 1e-10);

    assertEquals("+401213-0750015/", formattedPointLocationString1);
    assertEquals("+40.20361-075.00417/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_f()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+401213.1-0750015.1/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(12, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(13, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(-15, pointLocation.getLongitude()
      .getField(Angle.Field.SECONDS));

    assertEquals(0D, pointLocation.getAltitude(), 1e-10);

    assertEquals("+401213-0750015/", formattedPointLocationString1);
    assertEquals("+40.20364-075.00419/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_g()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+40-075+350/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.SECONDS));

    assertEquals(350D, pointLocation.getAltitude(), 1e-10);

    assertEquals("+400000-0750000+350.0/", formattedPointLocationString1);
    assertEquals("+40.0-075.0+350.0/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_h()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+40.20361-075.00417+350.517/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(12, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(13, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(-15, pointLocation.getLongitude()
      .getField(Angle.Field.SECONDS));

    assertEquals(350.517, pointLocation.getAltitude(), 1e-10);

    assertEquals("+401213-0750015+350.517/", formattedPointLocationString1);
    assertEquals("+40.20361-075.00417+350.517/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_j()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+4012-07500-169.2/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(12, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.SECONDS));

    assertEquals(-169.2, pointLocation.getAltitude(), 1e-10);

    assertEquals("+401200-0750000-169.2/", formattedPointLocationString1);
    assertEquals("+40.2-075.0-169.2/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_k()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+4012.22-07500.25-169.2/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(12, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(13, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(-15, pointLocation.getLongitude()
      .getField(Angle.Field.SECONDS));

    assertEquals(-169.2, pointLocation.getAltitude(), 1e-10);

    assertEquals("+401213-0750015-169.2/", formattedPointLocationString1);
    assertEquals("+40.20367-075.00417-169.2/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_m()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+401213-0750015+2.79/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(12, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(13, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(-15, pointLocation.getLongitude()
      .getField(Angle.Field.SECONDS));

    assertEquals(2.79, pointLocation.getAltitude(), 1e-10);

    assertEquals("+401213-0750015+2.79/", formattedPointLocationString1);
    assertEquals("+40.20361-075.00417+2.79/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocation_n()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+401213.1-0750015.1+2.79/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(12, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(13, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(-15, pointLocation.getLongitude()
      .getField(Angle.Field.SECONDS));

    assertEquals(2.79, pointLocation.getAltitude(), 1e-10);

    assertEquals("+401213-0750015+2.79/", formattedPointLocationString1);
    assertEquals("+40.20364-075.00419+2.79/", formattedPointLocationString2);
  }

  @Test
  public void pointLocationBoundary_1()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+40-180/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(40, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-180,
                 pointLocation.getLongitude().getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.SECONDS));

    assertEquals(0D, pointLocation.getAltitude(), 1e-10);

    assertEquals("+400000-1800000/", formattedPointLocationString1);
    assertEquals("+40.0-180.0/", formattedPointLocationString2);
  }

  @Test(expected = ParserException.class)
  public void pointLocationBoundary_2()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+40+180/";
    /* final PointLocation pointLocation = */PointLocationParser
      .parsePointLocation(pointLocationString);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocationBoundary_3()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "+90-075/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(90, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.SECONDS));

    assertEquals(0D, pointLocation.getAltitude(), 1e-10);

    assertEquals("+900000-0750000/", formattedPointLocationString1);
    assertEquals("+90.0-075.0/", formattedPointLocationString2);
  }

  @SuppressWarnings("boxing")
  @Test
  public void pointLocationBoundary_4()
    throws ParserException, FormatterException
  {

    final String pointLocationString = "-90-075/";
    final PointLocation pointLocation = PointLocationParser
      .parsePointLocation(pointLocationString);
    final String formattedPointLocationString1 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.LONG);
    final String formattedPointLocationString2 = PointLocationFormatter
      .formatIso6709(pointLocation, PointLocationFormatType.DECIMAL);

    assertEquals(-90, pointLocation.getLatitude().getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLatitude().getField(Angle.Field.SECONDS));

    assertEquals(-75, pointLocation.getLongitude()
      .getField(Angle.Field.DEGREES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.MINUTES));
    assertEquals(0, pointLocation.getLongitude().getField(Angle.Field.SECONDS));

    assertEquals(0D, pointLocation.getAltitude(), 1e-10);

    assertEquals("-900000-0750000/", formattedPointLocationString1);
    assertEquals("-90.0-075.0/", formattedPointLocationString2);
  }

}
