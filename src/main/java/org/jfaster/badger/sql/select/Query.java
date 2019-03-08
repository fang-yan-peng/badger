package org.jfaster.badger.sql.select;

import java.util.Collection;
import java.util.List;

/**
 * 查询条件
 * @author yanpengfang
 * create 2019-01-29 10:26 PM
 */
public interface Query<T> {
    /*
     * 添加参数
     */
    Query<T> addParam(Object obj);

    /*
     * 添加参数
     */
    Query<T> addParam(Object... objs);

    /*
     * 添加参数
     */
    Query<T> addParam(Collection<Object> objs);

    /**
     * 手动设置分库分表字段
     */
    Query<T> setShardValue(Object shardValue);

    /**
     * 强制使用master
     * @return
     */
    Query<T> userMaster();

    /*
     * 返回列表
     */
    List<T> list();

    /*
     * 返回统计结果
     */
    long count();

    /*
     * 设置结果集大小
     */
    Query<T> setPageSize(int pageSize);

    /*
     * 设置页码
     */
    Query<T> setPageIndex(int pageIndex);

    /*
     * 返回单个bean
     */
    T getOne() ;

}
