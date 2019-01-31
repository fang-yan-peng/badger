package org.jfaster.badger.query.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.jfaster.badger.cache.LoadingCache;
import org.jfaster.badger.cache.support.DoubleCheckCache;
import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.query.annotations.Column;
import org.jfaster.badger.util.Strings;

public class BeanUtil {

    private static final int MISS_FLAG = -1;

    private final static LoadingCache<Class<?>, List<PropertyMeta>> cache =
            new DoubleCheckCache<>(
                    clazz -> {
                        try {
                            List<Field> fields = fetchField(clazz);
                            TreeMap<Integer, PropertyMeta> metaMap = new TreeMap<>();
                            int missIndex = fields.size();
                            for (Field f : fields) {
                                Class<?> fieldType = f.getType();
                                Class<?> declareClass = f.getDeclaringClass();
                                String fieldName = f.getName();
                                String getFuncName = null;
                                String setFuncName = null;
                                if (f.isAnnotationPresent(Column.class)) {
                                    Column col = f.getAnnotation(Column.class);
                                    getFuncName = col.getFuncName();
                                    setFuncName = col.setFuncName();
                                }
                                Method readMethod;
                                if (Strings.isNullOrEmpty(getFuncName)) {
                                    getFuncName = "get" + Strings.firstLetterToUpperCase(fieldName);
                                    readMethod = tryGetMethod(declareClass, getFuncName);
                                    if (readMethod == null && isBoolean(fieldType)) {
                                        getFuncName = "is" + Strings.firstLetterToUpperCase(fieldName);
                                        readMethod = tryGetMethod(declareClass, getFuncName);
                                        if (readMethod == null) {
                                            getFuncName = fieldName;
                                            readMethod = tryGetMethod(declareClass, getFuncName);
                                        }
                                    }
                                } else {
                                    readMethod = tryGetMethod(declareClass, getFuncName);
                                }
                                Method writeMethod;
                                if (Strings.isNullOrEmpty(setFuncName)) {
                                    setFuncName = "set" + Strings.firstLetterToUpperCase(fieldName);
                                    writeMethod = tryGetMethod(declareClass, setFuncName, fieldType);
                                    if (writeMethod == null && fieldName.startsWith("is") && isBoolean(fieldType)) {
                                        setFuncName = fieldName.replaceFirst("is", "set");
                                        writeMethod = tryGetMethod(declareClass, setFuncName, fieldType);
                                    }
                                } else {
                                    writeMethod = tryGetMethod(declareClass, setFuncName, fieldType);
                                }
                                if (readMethod != null && writeMethod != null) {
                                    PropertyMeta meta = new PropertyMeta(fieldName, fieldType, readMethod, writeMethod, methodAnnos(readMethod), methodAnnos(writeMethod), fieldAnnos(f));
                                    int index = indexOfFields(f, fields);
                                    if (index == MISS_FLAG) {
                                        index = missIndex;
                                        missIndex++;
                                    }
                                    metaMap.put(index, meta);
                                }
                            }
                            return transToList(metaMap);
                        } catch (Exception e) {
                            throw new BadgerException(e);
                        }
                    });

    public static List<PropertyMeta> fetchPropertyMetas(Class<?> clazz) {
        return cache.get(clazz);
    }

    public static List<Field> fetchField(Class<?> clazz) {
        List<Field> fields = new LinkedList<>();
        fillFields(clazz, fields);
        return fields;
    }

    private static void fillFields(Class<?> clazz, List<Field> fields) {
        if (Object.class.equals(clazz)) {
            return;
        }
        fillFields(clazz.getSuperclass(), fields);
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }
    }

    private static boolean isBoolean(Class<?> clazz) {
        return boolean.class.equals(clazz) || Boolean.class.equals(clazz);
    }

    private static int indexOfFields(Field field, List<Field> fields) {
        if (field != null) {
            for (int i = 0; i < fields.size(); i++) {
                if (field.equals(fields.get(i))) {
                    return i;
                }
            }
        }
        return MISS_FLAG;
    }


    private static Field tryGetField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (Exception e) {
            // ignore
            return null;
        }
    }

    private static Method tryGetMethod(Class<?> clazz, String name, Class... type) {
        try {
            return clazz.getDeclaredMethod(name, type);
        } catch (Exception e) {
            // ignore
            return null;
        }
    }
    private static List<PropertyMeta> transToList(TreeMap<Integer, PropertyMeta> metaMap) {
        List<PropertyMeta> metas = new ArrayList<>();
        for (Integer key : metaMap.keySet()) {
            metas.add(metaMap.get(key));
        }
        return Collections.unmodifiableList(metas);
    }

    private static Set<Annotation> methodAnnos(Method m) {
        Set<Annotation> annos = new HashSet<>();
        for (Annotation anno : m.getAnnotations()) {
            annos.add(anno);
        }
        return annos;
    }

    private static Set<Annotation> fieldAnnos(Field f) {
        Set<Annotation> annos = new HashSet<>();
        if (f != null) {
            for (Annotation anno : f.getAnnotations()) {
                annos.add(anno);
            }
        }
        return annos;
    }
}
