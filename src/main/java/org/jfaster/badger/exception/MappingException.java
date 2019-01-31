package org.jfaster.badger.exception;

public class MappingException extends BadgerException {

  public MappingException(String msg) {
    super(msg);
  }

  public MappingException(String msg, Object... parameters) {
    super(String.format(msg, parameters));
  }

  public MappingException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
