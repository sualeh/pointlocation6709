/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2025, Sualeh Fatehi.
 */
package us.fatehi.pointlocation6709;

import java.io.Serial;

/**
 * Represents a longitude in degrees or radians.
 *
 * @author Sualeh Fatehi
 */
public final class Longitude extends Angle {

  @Serial private static final long serialVersionUID = -8615691791807614256L;

  /**
   * Copy constructor. Copies the value of a provided angle.
   *
   * @param angle Angle to copy the value from.
   */
  public Longitude(final Angle angle) {
    super(angle, 180);

    final double degrees = getDegrees();
    if (degrees == 180) {
      throw new IllegalArgumentException(
          "According to the ISO6709 standard, "
              + "the 180th meridian is always negative "
              + "(180"
              + Field.DEGREES
              + " W)");
    }
  }

  @Override
  protected String getDirection() {
    if (getRadians() < 0) {
      return "W";
    } else {
      return "E";
    }
  }
}
