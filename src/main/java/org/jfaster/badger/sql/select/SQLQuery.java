package org.jfaster.badger.sql.select;

import java.util.Collection;
import java.util.List;

/**
 * 原生sql
 * @author yanpengfang
 * @create 2019-01-29 11:30 PM
 */
public interface SQLQuery<T> {
    /*
     * 添加参数
     */
    SQLQuery addParam(Object obj) ;

    /*
     * 添加参数
     */
    SQLQuery addParam(Object... objs) ;

    /*
     * 添加参数
     */
    SQLQuery addParam(Collection<Object> objs) ;

    /**
     * 设置数据源名称
     * @param name
     * @return
     */
    SQLQuery setDataSourceName(String name);

    /**
     * 强制使用主库
     * @return
     */
    SQLQuery userMaster();

    /*
     * 返回列表
     */
    List<T> list() ;
}
