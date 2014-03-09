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

import org.junit.Test;

import us.fatehi.pointlocation6709.Angle;
import us.fatehi.pointlocation6709.Latitude;
import us.fatehi.pointlocation6709.Longitude;

/**
 * Location tests.
 */
public class TestStringConversions
{

  @Test
  public void negativeAngleFormat()
  {
    final Angle angle = Angle.fromDegrees(-15.45);
    final Latitude latitude = new Latitude(angle);
    final Angle longitude = new Longitude(angle);

    assertEquals("-15° 27\'", angle.toString());
    assertEquals("15° 27\' S", latitude.toString());
    assertEquals("15° 27\' W", longitude.toString());
  }

  @Test
  public void positiveAngleFormat()
  {
    final Angle angle = Angle.fromDegrees(15.45);
    final Latitude latitude = new Latitude(angle);
    final Angle longitude = new Longitude(angle);

    assertEquals("15° 27\'", angle.toString());
    assertEquals("15° 27\' N", latitude.toString());
    assertEquals("15° 27\' E", longitude.toString());
  }

}
