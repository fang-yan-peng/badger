package org.jfaster.badger.transaction.exception;


public class CannotCreateTransactionException extends TransactionException {

  public CannotCreateTransactionException(String msg) {
    super(msg);
  }

  public CannotCreateTransactionException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
