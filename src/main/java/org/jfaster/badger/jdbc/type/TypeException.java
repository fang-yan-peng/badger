package org.jfaster.badger.jdbc.type;

import org.jfaster.badger.exception.BadgerException;

public class TypeException extends BadgerException {

  public TypeException(String msg) {
    super(msg);
  }

  public TypeException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
