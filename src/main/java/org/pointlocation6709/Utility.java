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


/**
 * General utility methods.
 * 
 * @author Sualeh Fatehi
 */
public class Utility
{

  /**
   * Splits a double value into it's sexagesimal parts. Each part has
   * the same sign as the provided value.
   * 
   * @param value
   *        Value to split
   * @return Split parts
   */
  public static int[] sexagesimalSplit(final double value)
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

  private Utility()
  {
    // Prevent instantiation
  }

}
