package org.jfaster.badger.util;

import java.util.ArrayList;
import java.util.List;

import org.jfaster.badger.Badger;
import org.jfaster.badger.exception.MappingException;
import org.jfaster.badger.query.shard.DataSourceShardStrategy;
import org.jfaster.badger.query.shard.ShardResult;
import org.jfaster.badger.query.shard.ShardTableInfo;
import org.jfaster.badger.query.shard.TableInfo;
import org.jfaster.badger.query.shard.TableShardStrategy;
import org.jfaster.badger.sql.ParseResult;

/**
 * 分库分表工具
 * @author yanpengfang
 * create 2019-01-16 3:31 PM
 */
public class ShardUtils {

    public static ShardResult shard(Class<?> clazz, Object shardValue) {
        ShardResult res = new ShardResult();
        ShardTableInfo shardInfo = SqlUtils.getShardTableInfo(clazz);
        if (shardInfo == null) {
            TableInfo tableInfo = SqlUtils.getTableInfo(clazz);
            res.setDataSourceName(tableInfo.getDataSourceName());
            return res;
        }
        String shardField = shardInfo.getFieldName();
        String fieldName = SqlUtils.getIdFields(clazz).get(0);
        if (!fieldName.equals(shardField)) {
            throw new MappingException("如果通过id删除，并且带有分库分表信息，则分表字段必须是id");
        }
        return shard(shardValue, shardInfo, res);
    }

    public static ShardResult shard(Object o, boolean checkId) {
        Class<?> clazz = o.getClass();
        ShardResult res = new ShardResult();
        ShardTableInfo shardInfo = SqlUtils.getShardTableInfo(clazz);
        if (shardInfo == null) {
            TableInfo tableInfo = SqlUtils.getTableInfo(clazz);
            res.setDataSourceName(tableInfo.getDataSourceName());
            return res;
        }
        String shardField = shardInfo.getFieldName();
        if (checkId) {
            String fieldName = SqlUtils.getIdFields(clazz).get(0);
            if (!fieldName.equals(shardField)) {
                throw new MappingException("如果通过id对表进行操作，并且带有分库分表信息，则分表字段必须是id");
            }
        }
        Object shardValue = SqlUtils.getValueByField(o, shardField);
        return shard(shardValue, shardInfo, res);
    }

    public static ShardResult shard(Class<?> clazz, String condition, List<Object> parameters, Badger badger) {
        ShardTableInfo shardInfo = SqlUtils.getShardTableInfo(clazz);
        ParseResult conditionParse = SQLParseUtils.parse(clazz, condition, badger);
        List<String> conditionFields = conditionParse.getDynamicFields();
        List<String> dynamicFields = new ArrayList<>(conditionFields.size());
        dynamicFields.addAll(conditionFields);
        int parameterCount = parameters == null ? 0 : parameters.size();
        if (parameterCount != dynamicFields.size()) {
            throw new MappingException("condition:" + condition + " not match parameters:" + (parameterCount == 0 ? "none" : Joiner.on(",").join(parameters)));
        }
        ShardResult res = new ShardResult();
        res.setDynamicFields(dynamicFields);
        if (shardInfo == null) {
            TableInfo tableInfo = SqlUtils.getTableInfo(clazz);
            res.setDataSourceName(tableInfo.getDataSourceName());
            return res;
        }
        int shardIndex = conditionParse.getShardParameterIndex();
        Object shardValue = shardIndex != -1 ? (parameters == null ? null : parameters.get(shardIndex)) : conditionParse.getShardValue();
        return shard(shardValue, shardInfo, res);
    }

    public static ShardResult shard(Class<?> clazz, String updateStatement, String condition,
            List<Object> parameters, Badger badger) {
        ShardTableInfo shardInfo = SqlUtils.getShardTableInfo(clazz);
        ParseResult conditionParse = SQLParseUtils.parse(clazz, condition, badger);
        ParseResult updateParse = SQLParseUtils.parseUpdateStatement(clazz, updateStatement, badger);

        List<String> conditionFields = conditionParse.getDynamicFields();
        List<String> updateFields = updateParse.getDynamicFields();

        List<String> dynamicFields = new ArrayList<>(conditionFields.size() + updateFields.size());
        dynamicFields.addAll(updateFields);
        dynamicFields.addAll(conditionFields);

        int parameterCount = parameters == null ? 0 : parameters.size();
        if (parameterCount != dynamicFields.size()) {
            throw new MappingException("condition:" + condition + " not match parameters:" + (parameterCount == 0 ? "none" : Joiner.on(",").join(parameters)));
        }
        ShardResult res = new ShardResult();
        res.setDynamicFields(dynamicFields);
        if (shardInfo == null) {
            TableInfo tableInfo = SqlUtils.getTableInfo(clazz);
            res.setDataSourceName(tableInfo.getDataSourceName());
            return res;
        }
        int shardIndex = conditionParse.getShardParameterIndex();
        Object shardValue = shardIndex != -1 ? (parameters == null ? null : parameters.get(shardIndex + updateFields.size())) : conditionParse.getShardValue();
        return shard(shardValue, shardInfo, res);
    }

    @SuppressWarnings("ALL")
    private static ShardResult shard(Object shardValue, ShardTableInfo shardInfo, ShardResult res) {
        CheckConditions.checkNotNull(shardValue, "分库分表字段不能为null");
        DataSourceShardStrategy dataSourceShardStrategy = shardInfo.getDbShardStrategy();
        if (dataSourceShardStrategy != null) {
            String dbName = dataSourceShardStrategy.shardSingle(shardValue);
            CheckConditions.checkNotNull(dbName, "分库不能为空");
        } else {
            res.setDataSourceName(shardInfo.getDatasourceName());
        }
        TableShardStrategy tableShardStrategy = shardInfo.getTableShardStrategy();
        if (tableShardStrategy != null) {
            String tableName = tableShardStrategy.shardSingle(shardInfo.getTalbes(), shardValue);
            CheckConditions.checkNotNull(tableName, "分表名不能为空");
            res.setTableName(tableName);
        }
        return res;
    }
}
