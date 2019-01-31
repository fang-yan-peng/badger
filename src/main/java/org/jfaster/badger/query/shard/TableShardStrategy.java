package org.jfaster.badger.query.shard;

import java.util.List;
/**
 * 分表策略
 * @author yanpengfang
 * @create 2019-01-04 9:24 PM
 */
public interface TableShardStrategy<T> {

    /**
     * 根据单个值分表
     * @param shardValue
     * @return
     */
    String shardSingle(List<String> tables,T shardValue);

}
