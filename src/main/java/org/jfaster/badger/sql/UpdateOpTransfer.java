package org.jfaster.badger.sql;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author yanpengfang
 * @create 2019-01-13 11:34 AM
 */
public class UpdateOpTransfer {
    /**
     * 生成自增id的更新操作支持的返回类型
     */
    public final static Map<Class, GeneratedTransformer> GENERATED_TRANSFORMERS = new LinkedHashMap<>();

    static {
        GENERATED_TRANSFORMERS.put(int.class, IntegerTransformer.INSTANCE);
        GENERATED_TRANSFORMERS.put(long.class, LongTransformer.INSTANCE);
        GENERATED_TRANSFORMERS.put(Integer.class, IntegerTransformer.INSTANCE);
        GENERATED_TRANSFORMERS.put(Long.class, LongTransformer.INSTANCE);
    }

    public interface Transformer {
        Object transform(Number n);
    }

    public interface GeneratedTransformer extends Transformer {
        Class<? extends Number> getRawType();
    }

    public enum IntegerTransformer implements GeneratedTransformer {
        INSTANCE;

        @Override
        public Object transform(Number n) {
            return n.intValue();
        }

        @Override
        public Class<? extends Number> getRawType() {
            return int.class;
        }
    }

    public enum LongTransformer implements GeneratedTransformer {
        INSTANCE;

        @Override
        public Object transform(Number n) {
            return n.longValue();
        }

        @Override
        public Class<? extends Number> getRawType() {
            return long.class;
        }
    }

    public enum VoidTransformer implements Transformer {
        INSTANCE;

        @Override
        public Object transform(Number n) {
            return null;
        }
    }

    public enum BooleanTransformer implements Transformer {
        INSTANCE;

        @Override
        public Object transform(Number n) {
            return n.intValue() > 0 ? Boolean.TRUE : Boolean.FALSE;
        }
    }
}
