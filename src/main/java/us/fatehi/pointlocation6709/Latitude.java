/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2020, Sualeh Fatehi.
 */
package us.fatehi.pointlocation6709;

/**
 * Represents a latitude in degrees or radians.
 *
 * @author Sualeh Fatehi
 */
public final class Latitude extends Angle {

  private static final long serialVersionUID = -1048509855080052523L;

  /**
   * Copy constructor. Copies the value of a provided angle.
   *
   * @param angle Angle to copy the value from.
   */
  public Latitude(final Angle angle) {
    super(angle, 90);
  }

  @Override
  protected String getDirection() {
    if (getRadians() < 0) {
      return "S";
    } else {
      return "N";
    }
  }
}
