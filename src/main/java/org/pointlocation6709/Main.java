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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.pointlocation6709.parser.FormatterException;
import org.pointlocation6709.parser.ParserException;
import org.pointlocation6709.parser.PointLocationFormatType;
import org.pointlocation6709.parser.PointLocationFormatter;
import org.pointlocation6709.parser.PointLocationParser;

/**
 * Main.
 * 
 * @author Sualeh Fatehi
 */
public final class Main
{

  /**
   * Main.
   * 
   * @param args
   *        Arguments
   * @throws IOException
   *         On an i/o error.
   */
  public static void main(final String[] args)
    throws IOException
  {

    System.out.println(Version.about());
    System.out.println("ISO 6709:1983 geographic point location tester. ");
    System.out.println("For example, enter: +401213-0750015/");
    System.out.println("Enter a blank line to quit.");
    System.out.println("Starting. " + new Date());

    final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String inputLine = "starter";
    while (inputLine != null && inputLine.trim().length() > 0)
    {
      try
      {
        System.out.print("Enter an ISO 6709:1983 geographic point location: ");
        inputLine = in.readLine();
        // Parse and print location point
        final PointLocation pointLocation = PointLocationParser
          .parsePointLocation(inputLine);
        System.out.println(pointLocation);
        System.out.println(PointLocationFormatter
          .formatIso6709(pointLocation, PointLocationFormatType.MEDIUM));
      }
      catch (final ParserException e)
      {
        System.err.println(e.getMessage());
      }
      catch (final FormatterException e)
      {
        System.err.println(e.getMessage());
      }
    }
    System.out.println("Done. " + new Date());

  }

  private Main()
  {
    // Prevent instantiation
  }

}
