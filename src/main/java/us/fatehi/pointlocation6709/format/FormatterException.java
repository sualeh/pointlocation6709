/*
 * Point Location 6709
 * http://github.com/sualeh/pointlocation6709
 * Copyright (c) 2007-2025, Sualeh Fatehi.
 */
package us.fatehi.pointlocation6709.format;

import java.io.Serial;

/**
 * Parser exception.
 *
 * @author Sualeh Fatehi
 */
public class FormatterException extends Exception {

  @Serial private static final long serialVersionUID = -8091140656979529951L;

  /** Constructor. */
  public FormatterException() {}

  /**
   * Constructor.
   *
   * @param message Exception message
   */
  public FormatterException(final String message) {
    super(message);
  }

  /**
   * Constructor.
   *
   * @param message Exception message
   * @param cause Exception cause
   */
  public FormatterException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor.
   *
   * @param cause Exception cause
   */
  public FormatterException(final Throwable cause) {
    super(cause);
  }
}
