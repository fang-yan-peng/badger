package org.jfaster.badger.test.bean;

import org.jfaster.badger.query.bean.BeanUtil;
import org.jfaster.badger.query.bean.PropertyMeta;
import org.junit.Test;

/**
 * @author yanpengfang
 * @create 2019-01-09 3:49 PM
 */
public class BeanUtilTest {

    @Test
    public void simpleBean() {
        for (PropertyMeta meta : BeanUtil.fetchPropertyMetas(ParentBean.class)) {
            System.out.println(meta.getName());
            System.out.println(meta.getType());
            System.out.println(meta.getReadMethod());
            System.out.println(meta.getWriteMethod());
        }
    }

    @Test
    public void complexBean() {
        for (PropertyMeta meta : BeanUtil.fetchPropertyMetas(ChildBean.class)) {
            System.out.println(meta.getName());
            System.out.println(meta.getType());
            System.out.println(meta.getReadMethod());
            System.out.println(meta.getWriteMethod());
        }
    }
}
