/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2026, Sualeh Fatehi.
 */
package us.fatehi.pointlocation6709.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import us.fatehi.pointlocation6709.Angle;
import us.fatehi.pointlocation6709.Latitude;
import us.fatehi.pointlocation6709.Longitude;

/** Location tests. */
public class TestStringConversions {

  @Test
  public void negativeAngleFormat() {
    final Angle angle = Angle.fromDegrees(-15.45);
    final Latitude latitude = new Latitude(angle);
    final Angle longitude = new Longitude(angle);

    assertThat(angle.toString(), is("-15°27\'00\""));
    assertThat(latitude.toString(), is("15°27\'00\"S"));
    assertThat(longitude.toString(), is("15°27\'00\"W"));
  }

  @Test
  public void positiveAngleFormat() {
    final Angle angle = Angle.fromDegrees(15.45);
    final Latitude latitude = new Latitude(angle);
    final Angle longitude = new Longitude(angle);

    assertThat(angle.toString(), is("15°27\'00\""));
    assertThat(latitude.toString(), is("15°27\'00\"N"));
    assertThat(longitude.toString(), is("15°27\'00\"E"));
  }
}
