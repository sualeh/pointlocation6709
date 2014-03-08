/*
 *
 * Daylight Chart
 * http://sourceforge.net/projects/daylightchart
 * Copyright (c) 2007-2014, Sualeh Fatehi.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */
package org.pointlocation6709;


/**
 * Version information for this product. Has methods to obtain
 * information about the product, as well as a main method, so it can be
 * called from the command line.
 * 
 * @author Sualeh Fatehi
 */
public final class Version
{

  private static final String PRODUCTNAME = "Point Location 6709"; //$NON-NLS-1$
  private static final String VERSION = "3.4"; //$NON-NLS-1$

  /**
   * Information about this product.
   * 
   * @return Information about this product.
   */
  public static String about()
  {
    final StringBuffer about = new StringBuffer();
    about.append(getProductName()).append(" ").append(getVersion()) //$NON-NLS-1$
      .append("\n").append("\u00A9 2007-2008 Sualeh Fatehi"); //$NON-NLS-1$ //$NON-NLS-2$
    return new String(about);
  }

  /**
   * Product name.
   * 
   * @return Product name.
   */
  public static String getProductName()
  {
    return PRODUCTNAME;
  }

  /**
   * Product version number.
   * 
   * @return Product version number.
   */
  public static String getVersion()
  {
    return VERSION;
  }

  /**
   * Main routine. Prints information about this product.
   * 
   * @param args
   *        Arguments to the main routine - they are ignored.
   */
  public static void main(final String[] args)
  {
    System.out.println(about());
  }

  private Version()
  {
  }

  /**
   * String representation. Information about this product.
   * 
   * @return String representation.
   */
  @Override
  public String toString()
  {
    return about();
  }

}
