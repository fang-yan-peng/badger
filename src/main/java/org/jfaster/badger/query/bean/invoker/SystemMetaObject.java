package org.jfaster.badger.query.bean.invoker;

import org.jfaster.badger.query.bean.invoker.support.MetaObject;

/**
 * @author Clinton Begin
 */
public final class SystemMetaObject {

  public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(NullObject.class);

  private SystemMetaObject() {
    // Prevent Instantiation of Static Class
  }

  private static class NullObject {
  }

}
