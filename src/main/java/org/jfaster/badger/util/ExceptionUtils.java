package org.jfaster.badger.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yanpengfang
 * @create 2019-01-13 12:28 AM
 */
public class ExceptionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

    public static void runIgnoreThrowable(Runnable run){
        try {
            run.run();
        } catch (Throwable e) {
            logger.error("执行拦截器异常", e);
        }
    }
}
