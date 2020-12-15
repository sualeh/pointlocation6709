/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2020, Sualeh Fatehi.
 */
package us.fatehi.pointlocation6709.parse;

/**
 * Parser exception.
 *
 * @author Sualeh Fatehi
 */
public class ParserException extends Exception {

  private static final long serialVersionUID = -8091140656979529951L;

  /** Constructor. */
  public ParserException() {}

  /**
   * Constructor.
   *
   * @param message Exception message
   */
  public ParserException(final String message) {
    super(message);
  }

  /**
   * Constructor.
   *
   * @param message Exception message
   * @param cause Exception cause
   */
  public ParserException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor.
   *
   * @param cause Exception cause
   */
  public ParserException(final Throwable cause) {
    super(cause);
  }
}
