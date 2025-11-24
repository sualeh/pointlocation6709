/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2026, Sualeh Fatehi.
 */
package us.fatehi.pointlocation6709;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import us.fatehi.pointlocation6709.format.FormatterException;
import us.fatehi.pointlocation6709.format.PointLocationFormatType;
import us.fatehi.pointlocation6709.format.PointLocationFormatter;
import us.fatehi.pointlocation6709.parse.ParserException;
import us.fatehi.pointlocation6709.parse.PointLocationParser;

/**
 * Main.
 *
 * @author Sualeh Fatehi
 */
public final class Main {

  /**
   * Main.
   *
   * @param args Arguments
   * @throws IOException On an i/o error.
   */
  public static void main(final String[] args) throws IOException {

    System.out.println(Version.about());
    System.out.println("ISO 6709 geographic point location tester. ");
    System.out.println("For example, enter: +401213-0750015/");
    System.out.println("Enter a blank line to quit.");
    System.out.println("Starting. " + new Date());

    final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String inputLine = "starter";
    while (inputLine != null && inputLine.trim().length() > 0) {
      try {
        System.out.print("Enter an ISO 6709 geographic point location: ");
        inputLine = in.readLine();
        // Parse and print location point
        final PointLocation pointLocation = PointLocationParser.parsePointLocation(inputLine);
        System.out.println(pointLocation);
        System.out.println(
            PointLocationFormatter.formatPointLocation(
                pointLocation, PointLocationFormatType.LONG));
      } catch (final ParserException e) {
        System.out.println(e.getMessage());
      } catch (final FormatterException e) {
        System.err.println(e.getMessage());
      }
    }
    System.out.println("Done. " + new Date());
  }

  private Main() {
    // Prevent instantiation
  }
}
