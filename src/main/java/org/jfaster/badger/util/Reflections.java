package org.jfaster.badger.util;

import org.jfaster.badger.exception.BeanInstantiationException;

/**
 *
 * @author yanpengfang
 * create 2019-01-07 6:31 PM
 */
public class Reflections {
    public static <T> T instantiate(Class<T> clazz) throws BeanInstantiationException {
        if (clazz.isInterface()) {
            throw new BeanInstantiationException(clazz, "specified class is an interface");
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new BeanInstantiationException(clazz, "Is it an abstract class?", e);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(clazz, "Is the constructor accessible?", e);
        }
    }
}
