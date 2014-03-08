header {
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

import java.util.*;
import org.pointlocation6709.*;

}

class AntlrPointLocationParser extends Parser;
options
{
  defaultErrorHandler = false;
}

locationPoint
  returns [PointLocation locationPoint = null]
  {
    Latitude latitude;
    Longitude longitude;
    double altitude = 0;
  }
  :
  latitude = latitude
  longitude = longitude
  (altitude = altitude)?
  SLASH
  {
    locationPoint = new PointLocation(latitude, longitude, altitude);
  }
  ;

altitude
  returns [double altitude = 0]
  {
    int sign = 1;
  }
  :
  (PLUS | MINUS { sign = -1; })
  altitude = realNumber
  {
    altitude = sign * altitude;
  }
  ;

latitude
  returns [Latitude latitude = null]
  {
    int sign = 1;
    double degrees = 0;
    double minutesAndSeconds = 0;
  }
  :
  (PLUS | MINUS { sign = -1; })
  (
    (DIGIT DIGIT DECIMAL_POINT DIGIT) =>
    (
      degrees = realNumber
      {
        latitude = new Latitude(Angle.fromDegrees(sign * degrees));
      }
    )
    |
    (
      degrees = twoDigitInteger
      (minutesAndSeconds = minutesAndSeconds)?
      {
        latitude = new Latitude(Angle.fromDegrees(sign * (degrees + minutesAndSeconds)));
      }
    )
  )
  ;

longitude
  returns [Longitude longitude = null]
  {
    int sign = 1;
    double degrees = 0;
    double minutesAndSeconds = 0;
  }
  :
  (PLUS | MINUS { sign = -1; })
  (
    (DIGIT DIGIT DIGIT DECIMAL_POINT DIGIT) =>
    (
      degrees = realNumber
      {
        longitude = new Longitude(Angle.fromDegrees(sign * degrees));
      }
    )
    |
    (
      degrees = threeDigitInteger
      (minutesAndSeconds = minutesAndSeconds)?
      {
        longitude = new Longitude(Angle.fromDegrees(sign * (degrees + minutesAndSeconds)));
      }
    )
  )
  ;

minutesAndSeconds
  returns [double value = 0D]
  {
    double minutes = 0;
    double seconds = 0;
  }
  :
  (DIGIT DIGIT DECIMAL_POINT DIGIT) =>
  (
  minutes = realNumber
  {
    if (minutes >= 60D) {
      throw new antlr.RecognitionException("Minutes cannot be 60 or greater");
    }
    value = value + minutes / 60D;
  }
  )
  |
  (
    (
    minutes = twoDigitInteger
    {
	    if (minutes >= 60D) {
	      throw new antlr.RecognitionException("Minutes cannot be 60 or greater");
	    }    
      value = value + minutes / 60D;
    }
    )
    (
      (DIGIT DIGIT DECIMAL_POINT DIGIT) =>
      (
      seconds = realNumber
      {
		    if (seconds >= 60D) {
		      throw new antlr.RecognitionException("Seconds cannot be 60 or greater");
		    }      
        value = value + seconds / 3600D;
      }
      )
      |
      (
      seconds = twoDigitInteger
      {
		    if (seconds >= 60D) {
		      throw new antlr.RecognitionException("Seconds cannot be 60 or greater");
		    }            
        value = value + seconds / 3600D;
      }
      )?
    )
  )
  ;

twoDigitInteger
  returns [int number = 0]
  :
    tens: DIGIT
    {
      number = number + 10 * Integer.parseInt(tens.getText());
    }
    units: DIGIT
    {
      number = number + Integer.parseInt(units.getText());
    }
  ;

threeDigitInteger
  returns [int number = 0]
  :
    hundreds: DIGIT
    {
      number = number + 100 * Integer.parseInt(hundreds.getText());
    }
    tens: DIGIT
    {
      number = number + 10 * Integer.parseInt(tens.getText());
    }
    units: DIGIT
    {
      number = number + Integer.parseInt(units.getText());
    }
  ;

realNumber
  returns [double number = 0D]
  {
    int digits = 0;
    int decimalDigits = 1;
  }
  :
  number = integerNumber
  (
    DECIMAL_POINT
    (
    decimalDigit: DIGIT
    {
      number = number + Math.pow(10, -decimalDigits) * Integer.parseInt(decimalDigit.getText());
      decimalDigits++;
    }
    )+
  )?
  ;

integerNumber
  returns [int number = 0]
  :
  (
  digit: DIGIT
  {
    number = number * 10 + Integer.parseInt(digit.getText());
  }
  )+
  ;

class AntlrPointLocationLexer extends Lexer;

options {
  k = 2; // Lookahead, needed for processing newlines
  charVocabulary = '\u0000'..'\u007F'; // Allow ASCII
}

NEWLINE
  : ('\r''\n') => '\r''\n' // Windows
  | '\r'                   // Macintosh
  | '\n'                   // UNIX
  {
    newline();
    $setType(Token.SKIP);
  }
  ;

PLUS
  : '+'
  ;

MINUS
  : '-'
  ;

DIGIT
  : '0'..'9'
  ;

DECIMAL_POINT
  : '.'
  ;

SLASH
  : '/'
  ;
