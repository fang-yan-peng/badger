package org.jfaster.badger.transaction.exception;

/**
 * 不能获得jdbc连接异常
 */
public class CannotGetJdbcConnectionException extends TransactionException {

  public CannotGetJdbcConnectionException(String message, Throwable cause) {
    super(message, cause);
  }

  public CannotGetJdbcConnectionException(String message) {
    super(message);
  }
}
