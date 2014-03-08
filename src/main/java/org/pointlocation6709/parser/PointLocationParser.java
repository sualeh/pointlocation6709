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
package org.pointlocation6709.parser;


import java.io.Reader;
import java.io.StringReader;

import org.pointlocation6709.PointLocation;

import antlr.RecognitionException;
import antlr.TokenStreamException;

/**
 * Parses objects from strings.
 * 
 * @author Sualeh Fatehi
 */
public final class PointLocationParser
{

  /**
   * Parses a string representation of the point location.
   * 
   * @param representation
   *        String representation of the point location
   * @return Point location
   * @throws ParserException
   *         On an exception
   */
  public static PointLocation parsePointLocation(final String representation)
    throws ParserException
  {
    final AntlrPointLocationParser parser = constructPointLocationParser(representation);
    PointLocation pointLocation;
    try
    {
      pointLocation = parser.locationPoint();
    }
    catch (final RecognitionException e)
    {
      throw new ParserException("Error parsing - " + representation, e);
    }
    catch (final TokenStreamException e)
    {
      throw new ParserException("Error parsing - " + representation, e);
    }
    return pointLocation;
  }

  static AntlrPointLocationParser constructPointLocationParser(final Reader reader)
  {
    final AntlrPointLocationLexer lexer = new AntlrPointLocationLexer(reader);
    final AntlrPointLocationParser parser = new AntlrPointLocationParser(lexer);
    return parser;
  }

  static AntlrPointLocationParser constructPointLocationParser(final String text)
  {
    final StringReader reader = new StringReader(text);
    return constructPointLocationParser(reader);
  }

  private PointLocationParser()
  {
    // Prevent instantiation
  }
}
