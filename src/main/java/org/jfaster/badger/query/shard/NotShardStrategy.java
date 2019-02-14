package org.jfaster.badger.query.shard;

import java.util.List;

import org.jfaster.badger.exception.BadgerException;

/**
 *
 * @author yanpengfang
 * create 2019-01-08 8:51 PM
 */
public class NotShardStrategy implements TableShardStrategy, DataSourceShardStrategy {
    @Override
    public String shardSingle(Object shardValue) {
        throw new BadgerException("can not run here");
    }

    @Override
    public String shardSingle(List tables, Object shardValue) {
        throw new BadgerException("can not run here");
    }
}
