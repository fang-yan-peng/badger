package org.jfaster.badger.spi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jfaster.badger.cache.CacheLoader;
import org.jfaster.badger.cache.support.DoubleCheckCache;
import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.util.Strings;

/**
 * spi类加载器，接口需要@Spi注解，实现类需要@SpiInfo注解
 *
 * Created by fangyanpeng1 on 2017/7/28.
 */
@SuppressWarnings("ALL")
public class ExtensionLoader<T> {

    private static DoubleCheckCache<Class<?>, ExtensionLoader<?>> spiClassLoaders = new DoubleCheckCache<>((CacheLoader<Class<?>, ExtensionLoader<?>>) ExtensionLoader::new);

    private Map<String, T> singletonObjects = null;

    private Map<String, Class<T>> spiClasses = null;

    private Class<T> type;

    private AtomicBoolean init = new AtomicBoolean(false);

    private ClassLoader classLoader;

    private static final String DEFAULT_CHARACTER = "UTF-8";

    private static final String PREFIX = "META-INF/services/";

    private ExtensionLoader(Class<T> type) {
        this(type, Thread.currentThread().getContextClassLoader());
    }

    private ExtensionLoader(Class<T> type, ClassLoader loader) {
        this.type = type;
        this.classLoader = loader;
    }

    private void checkInit() throws ClassNotFoundException {
        if (init.compareAndSet(false, true)) {
            loadExtensionClasses();
        }
    }

    public static <T> ExtensionLoader<T> get(Class<T> type) {
        checkInterfaceType(type);
        return (ExtensionLoader<T>) spiClassLoaders.get(type);
    }

    public T getExtension(String name) {
        try {
            checkInit();
            if (name == null) {
                return null;
            }

            Spi spi = type.getAnnotation(Spi.class);
            if (spi.scope() == Scope.SINGLETON) {
                return getSingletonInstance(name);
            } else {
                Class<T> clz = spiClasses.get(name);
                if (clz == null) {
                    return null;
                }
                return clz.newInstance();
            }
        } catch (Exception e) {
            throw new BadgerException(e);
        }
    }

    private T getSingletonInstance(String name) throws InstantiationException, IllegalAccessException {
        T obj = singletonObjects.get(name);
        if (obj != null) {
            return obj;
        }
        Class<T> clz = spiClasses.get(name);
        if (clz == null) {
            return null;
        }
        synchronized (singletonObjects) {
            obj = singletonObjects.get(name);
            if (obj != null) {
                return obj;
            }
            obj = clz.newInstance();
            singletonObjects.put(name, obj);
        }
        return obj;
    }

    private static <T> void checkInterfaceType(Class<T> clz) {
        if (clz == null) {
            throw new BadgerException("Error type is null");
        }

        if (!clz.isInterface()) {
            throw new BadgerException("Error %s is not a interface", clz.getName());
        }

        if (!isSpiType(clz)) {
            throw new BadgerException("Error %s is not spi type", clz.getName());
        }
    }

    private void checkSpiType(Class<T> clz) {
        checkClassPublic(clz);

        checkConstructorPublic(clz);

        checkClassInherit(clz);
    }

    private void checkClassInherit(Class<T> clz) {
        if (!type.isAssignableFrom(clz)) {
            throw new BadgerException("Error %s is not instanceof %s ", clz.getName(), type.getName());
        }
    }

    private void checkClassPublic(Class<T> clz) {
        if (!Modifier.isPublic(clz.getModifiers())) {
            throw new BadgerException("Error %s is not a public class ", clz.getName());
        }
    }

    private void checkConstructorPublic(Class<T> clz) {
        Constructor<?>[] constructors = clz.getConstructors();

        if (constructors == null || constructors.length == 0) {
            throw new BadgerException("Error %s has no public no-args constructor", clz.getName());
        }
    }

    private static <T> boolean isSpiType(Class<T> clz) {
        return clz.isAnnotationPresent(Spi.class);
    }

    public void addExtensionClass(Class<T> clz) throws ClassNotFoundException {
        if (clz == null) {
            return;
        }

        checkInit();

        checkSpiType(clz);

        String spiName = getSpiName(clz);

        synchronized (spiClasses) {
            if (spiClasses.containsKey(spiName)) {
                throw new BadgerException("%s:Error spiName:%s already exist ", clz.getName(), spiName);
            } else {
                spiClasses.put(spiName, clz);
            }
        }
    }

    public Class<T> getExtensionClass(String spiName) throws ClassNotFoundException {
        if (Strings.isNullOrEmpty(spiName)) {
            return null;
        }
        checkInit();
        return spiClasses.get(spiName);
    }

    private void loadExtensionClasses() throws ClassNotFoundException {
        spiClasses = loadExtensionClasses(PREFIX);
        singletonObjects = new ConcurrentHashMap<>();

    }

    private Map<String, Class<T>> loadExtensionClasses(String prefix) throws ClassNotFoundException {
        String fullName = prefix + type.getName();
        List<String> classNames = new ArrayList<String>();

        try {
            Enumeration<URL> urls;
            if (classLoader == null) {
                urls = ClassLoader.getSystemResources(fullName);
            } else {
                urls = classLoader.getResources(fullName);
            }

            if (urls == null || !urls.hasMoreElements()) {
                return new ConcurrentHashMap<>();
            }

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

                parseUrl(type, url, classNames);
            }
        } catch (Exception e) {
            throw new BadgerException(e);
        }

        return loadClass(classNames);
    }

    private Map<String, Class<T>> loadClass(List<String> classNames) throws ClassNotFoundException {
        ConcurrentMap<String, Class<T>> map = new ConcurrentHashMap<String, Class<T>>();

        for (String className : classNames) {
            Class<T> clz;
            if (classLoader == null) {
                clz = (Class<T>) Class.forName(className);
            } else {
                clz = (Class<T>) Class.forName(className, true, classLoader);
            }

            checkSpiType(clz);

            String spiName = getSpiName(clz);

            if (map.containsKey(spiName)) {
                throw new BadgerException("%s:Error spiName already exist %s", clz.getName(), spiName);
            } else {
                map.put(spiName, clz);
            }
        }
        return map;
    }


    private String getSpiName(Class<?> clz) {
        SpiMeta spiMeta = clz.getAnnotation(SpiMeta.class);

        String name = (spiMeta != null && !"".equals(spiMeta.name())) ? spiMeta.name() : clz.getSimpleName();

        return name;
    }

    private void parseUrl(Class<T> type, URL url, List<String> classNames) throws ServiceConfigurationError, IOException {
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = url.openStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, DEFAULT_CHARACTER));
            String line;
            int indexNumber = 0;
            while ((line = reader.readLine()) != null) {
                indexNumber++;
                parseLine(type, url, line, indexNumber, classNames);
            }
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException y) {
                throw new BadgerException(y);
            }
        }
    }

    private void parseLine(Class<T> type, URL url, String line, int lineNumber, List<String> names) throws IOException,
            ServiceConfigurationError {
        int ci = line.indexOf('#');

        if (ci >= 0) {
            line = line.substring(0, ci);
        }

        line = line.trim();

        if (line.length() <= 0) {
            return;
        }

        if ((line.indexOf(' ') >= 0) || (line.indexOf('\t') >= 0)) {
            throw new BadgerException("Illegal spi:%s configuration-file syntax lineNmber:%d url:%s", type.getName(), lineNumber, url.getPath());
        }

        int cp = line.codePointAt(0);
        if (!Character.isJavaIdentifierStart(cp)) {
            throw new BadgerException("Illegal spi provider-class name:%s,url:%s,lineNumber:%d,line:%s", type.getName(), url.getPath(), lineNumber, line);
        }

        for (int i = Character.charCount(cp); i < line.length(); i += Character.charCount(cp)) {
            cp = line.codePointAt(i);
            if (!Character.isJavaIdentifierPart(cp) && (cp != '.')) {
                throw new BadgerException("Illegal spi provider-class name:%s,url:%s,lineNumber:%d,line:%s ", type.getName(), url.getPath(), lineNumber, line);
            }
        }

        if (!names.contains(line)) {
            names.add(line);
        }
    }
}
