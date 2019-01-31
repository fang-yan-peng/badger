package org.jfaster.badger.query;

/**
 * 解析映射关系
 * @author yanpengfang
 * @create 2019-01-03 10:43 AM
 */
public interface Mapping {

    /**
     * 根据属性名得到数据库字段名
     * @param propertyName 表达式中属性名
     * @return 对应数据库字段名
     */
    String get(String propertyName);
}
