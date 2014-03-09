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
package us.fatehi.pointlocation6709;


/**
 * Represents a latitude in degrees or radians.
 * 
 * @author Sualeh Fatehi
 */
public final class Latitude
  extends Angle
{

  private static final long serialVersionUID = -1048509855080052523L;

  /**
   * Copy constructor. Copies the value of a provided angle.
   * 
   * @param angle
   *        Angle to copy the value from.
   */
  public Latitude(final Angle angle)
  {
    super(angle, 90);
  }

  @Override
  protected String getDirection()
  {
    if (getRadians() < 0)
    {
      return "S";
    }
    else
    {
      return "N";
    }
  }

}
