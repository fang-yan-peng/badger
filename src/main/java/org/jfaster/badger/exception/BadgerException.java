package org.jfaster.badger.exception;

/**
 * 框架统一异常
 * @author yanpengfang
 * @create 2019-01-03 8:46 PM
 */
public class BadgerException extends RuntimeException {

    public BadgerException(Throwable e) {
        super(e);
    }

    public BadgerException(String fmt, Object... params) {
        super(String.format(fmt, params));
    }

    public BadgerException(String msg, Throwable e) {
        super(msg, e);
    }
}
