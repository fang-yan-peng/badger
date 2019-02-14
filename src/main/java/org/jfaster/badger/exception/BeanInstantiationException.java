package org.jfaster.badger.exception;

/**
 * 运行时实例化类异常
 *
 * 框架统一异常
 * @author yanpengfang
 * create 2019-01-07 8:46 PM
 */
public class BeanInstantiationException extends RuntimeException {

  private Class beanClass;

  public BeanInstantiationException(Class beanClass, String msg) {
    this(beanClass, msg, null);
  }

  public BeanInstantiationException(Class beanClass, String msg, Throwable cause) {
    super("Could not instantiate bean class [" + beanClass.getName() + "]: " + msg, cause);
    this.beanClass = beanClass;
  }

  public Class getBeanClass() {
    return beanClass;
  }

}
