package org.jfaster.badger.query.shard;

/**
 * 数据源路由
 * @author yanpengfang
 * create 2019-01-08 8:49 PM
 */
public interface DataSourceShardStrategy<T> {
    /**
     * 根据单个值分库
     * @param shardValue
     * @return
     */
    String shardSingle(T shardValue);
}
