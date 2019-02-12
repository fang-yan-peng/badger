package org.jfaster.badger.transaction.exception;

/**
 * 事务相关异常
 */
public class TransactionSystemException extends TransactionException {

  public TransactionSystemException(Throwable e) {
    super(e);
  }

  public TransactionSystemException(String msg) {
    super(msg);
  }

  public TransactionSystemException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
