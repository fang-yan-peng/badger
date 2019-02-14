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
    Query addParam(Object obj);

    /*
     * 添加参数
     */
    Query addParam(Object... objs);

    /*
     * 添加参数
     */
    Query addParam(Collection<Object> objs);

    /**
     * 强制使用master
     * @return
     */
    Query userMaster();

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
    Query setPageSize(int pageSize);

    /*
     * 设置页码
     */
    Query setPageIndex(int pageIndex);

}
