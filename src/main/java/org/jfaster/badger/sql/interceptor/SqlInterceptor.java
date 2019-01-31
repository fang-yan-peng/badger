package org.jfaster.badger.sql.interceptor;

/**
 * sql 执行拦截器
 * @author yanpengfang
 * @create 2019-01-11 6:46 PM
 */
public interface SqlInterceptor {

    ThreadLocal<Context> context = ThreadLocal.withInitial(Context::new);

    void before(String sql);

    void after(String sql);

    void error(String sql, Throwable e);

    static Object get(String key) {
        Context ctx = context.get();
        return ctx.getParameter(key);
    }

    static <T> T get(String key, Class<T> clz) {
        Context ctx = context.get();
        return ctx.getParameter(key, clz);
    }

    static void put(String key, Object value) {
        Context ctx = context.get();
        ctx.put(key, value);
    }

    static void clear() {
        context.remove();
    }
}
