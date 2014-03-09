/* 
 * 
 * Daylight Chart
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
package org.pointlocation6709;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Represents an angle in degrees or radians. Has convenience methods to
 * do trigonometric operations, and normalizations.
 * 
 * @author Sualeh Fatehi
 */
public class Angle
  implements Serializable, Comparable<Angle>
{

  /**
   * Angle fields.
   */
  public enum Field
  {
    /** Degrees. */
    DEGREES("\u00B0"),
    /** Minutes. */
    MINUTES("'"),
    /** Seconds. */
    SECONDS("\"");

    private final String symbol;

    private Field(final String symbol)
    {
      this.symbol = symbol;
    }

    /**
     * Description of the field.
     * 
     * @return Description of the field
     */
    public String getDescription()
    {
      return name().toLowerCase();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
      return symbol;
    }

  }

  private static final long serialVersionUID = -6330836471692225095L;

  /**
   * Static construction method, constructs an angle from the degree
   * value provided.
   * 
   * @param degrees
   *        Value of the angle in degrees.
   * @return A new Angle.
   */
  public static Angle fromDegrees(final double degrees)
  {
    return fromRadians(degrees * Math.PI / 180D);
  }

  /**
   * Static construction method, constructs an angle from the radian
   * value provided.
   * 
   * @param radians
   *        Value of the angle in radians.
   * @return A new Angle.
   */
  public static Angle fromRadians(final double radians)
  {
    return new Angle(radians);
  }

  private final double radians;
  private transient int[] sexagesimalDegreeParts;

  /**
   * Copy constructor. Copies the value of a provided angle.
   * 
   * @param angle
   *        Angle to copy the value from.
   */
  public Angle(final Angle angle)
  {
    this(angle.radians);
  }

  /**
   * Copy constructor. Copies the value of a provided angle.
   * 
   * @param angle
   *        Angle to copy the value from.
   */
  protected Angle(final Angle angle, final int range)
  {
    this(angle);

    final double degrees = getDegrees();
    if (Math.abs(degrees) > range)
    {
      throw new IllegalArgumentException("" + degrees + Field.DEGREES +
                                         " is out of range, +/-" + range +
                                         Field.DEGREES);
    }
  }

  /**
   * Default constructor. Initializes the angle to a value of 0.
   */
  private Angle(final double radians)
  {
    this.radians = radians;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int compareTo(final Angle angle)
  {
    int comparison;
    comparison = getField(Field.DEGREES) - angle.getField(Field.DEGREES);
    if (comparison == 0)
    {
      comparison = getField(Field.MINUTES) - angle.getField(Field.MINUTES);
    }
    if (comparison == 0)
    {
      comparison = getField(Field.SECONDS) - angle.getField(Field.SECONDS);
    }
    return comparison;
  }

  /**
   * Calculates the cosine of the angle.
   * 
   * @return Cosine.
   */
  public final double cos()
  {
    return Math.cos(radians);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final Angle other = (Angle) obj;
    if (Double.doubleToLongBits(radians) != Double
      .doubleToLongBits(other.radians))
    {
      return false;
    }
    return true;
  }

  /**
   * Degrees value of the angle.
   * 
   * @return Value in degrees.
   */
  public final double getDegrees()
  {
    return radians * 180D / Math.PI;
  }

  /**
   * <p>
   * Gets an angle field - such as degrees, minutes, or seconds. Signs
   * will be consistent.
   * </p>
   * <p>
   * Throws NullPointerException if field is not provided.
   * </p>
   * 
   * @param field
   *        One of the field constants specifying the field to be
   *        retrieved.
   * @return Value of the specified field.
   */
  public final int getField(final Field field)
  {
    if (sexagesimalDegreeParts == null)
    {
      sexagesimalDegreeParts = sexagesimalSplit(getDegrees());
    }
    return sexagesimalDegreeParts[field.ordinal()];
  }

  /**
   * Radians value of the angle.
   * 
   * @return Value in radians.
   */
  public final double getRadians()
  {
    return radians;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(radians);
    result = prime * result + (int) (temp ^ temp >>> 32);
    return result;
  }

  /**
   * Calculates the sine of the angle.
   * 
   * @return Sine.
   */
  public final double sin()
  {
    return Math.sin(radians);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    final StringBuffer representation = new StringBuffer();
    final String direction = getDirection();

    final int absIntDegrees = Math.abs(getField(Field.DEGREES));
    final int absIntMinutes = Math.abs(getField(Field.MINUTES));
    final int absIntSeconds = Math.abs(getField(Field.SECONDS));

    representation.append(absIntDegrees).append(Field.DEGREES).append(" ")
      .append(Math.abs(absIntMinutes)).append(Field.MINUTES);
    if (absIntSeconds > 0)
    {
      representation.append(" ").append(absIntSeconds).append(Field.SECONDS);
    }
    if (direction == null)
    {
      if (radians < 0)
      {
        representation.insert(0, "-");
      }
    }
    else
    {
      representation.append(" ").append(direction);
    }

    return new String(representation);

  }

  /**
   * Get direction.
   * 
   * @return Direction.
   */
  protected String getDirection()
  {
    return null;
  }

  private void readObject(final ObjectInputStream objectInputStream)
    throws ClassNotFoundException, IOException
  {
    // Perform the default deserialization first
    objectInputStream.defaultReadObject();

    // Set transient fields
    sexagesimalDegreeParts = sexagesimalSplit(getDegrees());
  }

  /**
   * Splits a double value into it's sexagesimal parts. Each part has
   * the same sign as the provided value.
   * 
   * @param value
   *        Value to split
   * @return Split parts
   */
  private int[] sexagesimalSplit(final double value)
  {
    final double absValue = Math.abs(value);

    int units;
    int minutes;
    int seconds;
    final int sign = value < 0? -1: 1;

    // Calculate absolute integer units
    units = (int) Math.floor(absValue);
    seconds = (int) Math.round((absValue - units) * 3600D);

    // Calculate absolute integer minutes
    minutes = seconds / 60; // Integer arithmetic
    if (minutes == 60)
    {
      minutes = 0;
      units++;
    }

    // Calculate absolute integer seconds
    seconds = seconds % 60;

    // Correct for sign
    units = units * sign;
    minutes = minutes * sign;
    seconds = seconds * sign;

    return new int[] {
        units, minutes, seconds
    };
  }

}
