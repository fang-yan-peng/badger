package org.jfaster.badger.sql.interceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程上下文
 * @author yanpengfang
 * @create 2019-01-11 7:14 PM
 */
public class Context {

    private Map<String, Object> parameters;

    public void put(String key, Object o) {
        if (parameters == null) {
            parameters = new HashMap<>(4);
        }
        parameters.put(key, o);
    }

    public Object getParameter(String key) {
        if (parameters == null) {
            return null;
        }
        return parameters.get(key);
    }

    public <T> T getParameter(String key, Class<T> clz){
        if (parameters == null) {
            return null;
        }
        Object o = parameters.get(key);
        return clz.cast(o);
    }
}
