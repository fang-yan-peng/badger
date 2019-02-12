package org.jfaster.badger.transaction.exception;


import org.jfaster.badger.exception.BadgerException;

public abstract class TransactionException extends BadgerException {

    public TransactionException(Throwable e) {
        super(e);
    }


    public TransactionException(String msg) {
        super(msg);
    }

    public TransactionException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
