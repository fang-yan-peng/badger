package org.jfaster.badger.query.bean.invoker.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfaster.badger.cache.LoadingCache;
import org.jfaster.badger.cache.support.DoubleCheckCache;
import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.query.bean.BeanUtil;
import org.jfaster.badger.query.bean.PropertyMeta;
import org.jfaster.badger.query.bean.invoker.GetterInvoker;
import org.jfaster.badger.query.bean.invoker.SetterInvoker;


public class InvokerCache {

    public static GetterInvoker getNullableGetterInvoker(Class<?> clazz, String propertyName) {
        return cache.get(clazz).getGetterInvoker(propertyName);
    }

    public static GetterInvoker getGetterInvoker(Class<?> clazz, String propertyName) {
        GetterInvoker invoker = getNullableGetterInvoker(clazz, propertyName);
        if (invoker == null) {
            throw new BadgerException("There is no getter/setter for property named '" + propertyName + "' in '" + clazz + "'");
        }
        return invoker;
    }

    public static List<GetterInvoker> getGetterInvokers(Class<?> clazz) {
        return cache.get(clazz).getGetterInvokers();
    }


    public static SetterInvoker getNullableSetterInvoker(Class<?> clazz, String propertyName) {
        return cache.get(clazz).getSetterInvoker(propertyName);
    }

    public static SetterInvoker getSetterInvoker(Class<?> clazz, String propertyName) {
        SetterInvoker invoker = cache.get(clazz).getSetterInvoker(propertyName);
        if (invoker == null) {
            throw new BadgerException("There is no getter/setter for property named '" + propertyName + "' in '" +
                    clazz + "'");
        }
        return invoker;
    }

    public static List<SetterInvoker> getSetterInvokers(Class<?> clazz) {
        return cache.get(clazz).getSetterInvokers();
    }

    private final static LoadingCache<Class<?>, InvokerInfo> cache = new DoubleCheckCache<>(
            clazz -> {
                try {
                    return new InvokerInfo(clazz);
                } catch (Exception e) {
                    throw new BadgerException(e);
                }
            });

    private static class InvokerInfo {

        private final Map<String, GetterInvoker> getterInvokerMap;
        private final Map<String, SetterInvoker> setterInvokerMap;
        private final List<GetterInvoker> getterInvokers;
        private final List<SetterInvoker> setterInvokers;

        public InvokerInfo(Class<?> clazz) {
            Map<String, GetterInvoker> gim = new HashMap<>();
            Map<String, SetterInvoker> sim = new HashMap<>();
            List<GetterInvoker> gis = new ArrayList<>();
            List<SetterInvoker> sis = new ArrayList<>();

            for (PropertyMeta pm : BeanUtil.fetchPropertyMetas(clazz)) {
                String name = pm.getName();
                Method readMethod = pm.getReadMethod();
                Method writeMethod = pm.getWriteMethod();

                FunctionalGetterInvoker fgi = FunctionalGetterInvoker.create(name, pm, readMethod);
                gim.put(name, fgi);
                gis.add(fgi);
                FunctionalSetterInvoker fsi = FunctionalSetterInvoker.create(name, pm, writeMethod);
                sim.put(name, fsi);
                sis.add(fsi);
            }
            getterInvokerMap = Collections.unmodifiableMap(gim);
            setterInvokerMap = Collections.unmodifiableMap(sim);
            getterInvokers = Collections.unmodifiableList(gis);
            setterInvokers = Collections.unmodifiableList(sis);
        }

        GetterInvoker getGetterInvoker(String propertyName) {
            return getterInvokerMap.get(propertyName);
        }

        SetterInvoker getSetterInvoker(String propertyName) {
            return setterInvokerMap.get(propertyName);
        }

        private List<GetterInvoker> getGetterInvokers() {
            return getterInvokers;
        }

        private List<SetterInvoker> getSetterInvokers() {
            return setterInvokers;
        }

        private static boolean isBoolean(Class<?> clazz) {
            return boolean.class.equals(clazz) || Boolean.class.equals(clazz);
        }

    }

}
