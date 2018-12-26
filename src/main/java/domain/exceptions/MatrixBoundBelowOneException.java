package domain.exceptions;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class MatrixBoundBelowOneException extends ValueException {

  private static final String ERROR_MESSAGE = "Matrix cannot have bound below 1 : x = %d, y = %d";
  public MatrixBoundBelowOneException(final int x, final int y) {
    super(String.format(ERROR_MESSAGE, x, y));
  }
}
