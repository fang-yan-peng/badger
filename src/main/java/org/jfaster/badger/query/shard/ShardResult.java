package org.jfaster.badger.query.shard;

import java.util.List;

import org.jfaster.badger.jdbc.datasource.support.AbstractDataSourceFactory;
import org.jfaster.badger.sql.ParseResult;

import lombok.Data;

/**
 * 分库分表 结果
 * @author yanpengfang
 * create 2019-01-15 9:50 PM
 */
@Data
public class ShardResult {

    private String tableName;

    private String dataSourceName = AbstractDataSourceFactory.DEFULT_NAME;

    private List<String> dynamicFields;
}
