package org.jfaster.badger.query.shard;

import java.util.List;

import lombok.Data;

/**
 * 分库、分表信息
 * @author yanpengfang
 * create 2019-01-08 8:39 PM
 */
@Data
public class ShardTableInfo {
    private List<String> talbes;
    private ShardType shardType;
    private String column;
    private String fieldName;
    TableShardStrategy tableShardStrategy;
    DataSourceShardStrategy dbShardStrategy;
    private String datasourceName;
}
