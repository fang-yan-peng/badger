package org.jfaster.badger.query.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jfaster.badger.jdbc.datasource.support.AbstractDataSourceFactory;
import org.jfaster.badger.query.shard.DataSourceShardStrategy;
import org.jfaster.badger.query.shard.NotShardStrategy;
import org.jfaster.badger.query.shard.ShardType;
import org.jfaster.badger.query.shard.TableShardStrategy;

/**
 * 分库分表标示
 * @author yanpengfang
 * @create 2019-01-04 9:10 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShardTable {
    String[] tables();

    ShardType shardType();

    Class<? extends TableShardStrategy> tableShardStrategy() default NotShardStrategy.class;

    Class<? extends DataSourceShardStrategy> dbShardStrategy() default NotShardStrategy.class;

    String dataSourceName() default AbstractDataSourceFactory.DEFULT_NAME;
}
