package org.jfaster.badger.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.jdbc.datasource.support.AbstractDataSourceFactory;
import org.jfaster.badger.query.annotations.Column;
import org.jfaster.badger.query.annotations.Extension;
import org.jfaster.badger.query.annotations.Id;
import org.jfaster.badger.query.annotations.ShardColumn;
import org.jfaster.badger.query.annotations.ShardTable;
import org.jfaster.badger.query.annotations.Table;
import org.jfaster.badger.query.bean.BeanUtil;
import org.jfaster.badger.query.bean.PropertyMeta;
import org.jfaster.badger.query.shard.DataSourceShardStrategy;
import org.jfaster.badger.query.shard.NotShardStrategy;
import org.jfaster.badger.query.shard.ShardTableInfo;
import org.jfaster.badger.query.shard.TableInfo;
import org.jfaster.badger.query.shard.TableShardStrategy;

import lombok.Data;

/**
 * class 信息
 * @author yanpengfang
 * @create 2019-01-07 10:01 PM
 */
@Data
public class ClassInfo {

    private static final Set<String> notFields = new HashSet<>(Arrays.asList("serialVersionUID", "class"));

    private Map<String, String> allColumns;

    private Map<String, String> allReverseColumns;

    private List<String> columns;

    private List<String> fields;

    private List<String> idColumns;

    private List<String> idFields;

    private TableInfo tableInfo;

    private ShardTableInfo shardTableInfo;

    public ClassInfo(Class<?> clz) {
        this.tableInfo = getTableInfo(clz);
        this.shardTableInfo = getShardTable(clz);
        this.fillNeedColumns(clz);
    }

    private TableInfo getTableInfo(Class<?> clz) {
        TableInfo tableInfo = new TableInfo();
        if (clz.isAnnotationPresent(Table.class)) {
            Table table = clz.getAnnotation(Table.class);
            String tableName = table.tableName();
            if (Strings.isNullOrEmpty(tableName)) {
                tableInfo.setTableName(Strings.underscoreName(clz.getSimpleName()));
            } else {
                tableInfo.setTableName(tableName);
            }
            tableInfo.setDataSourceName(table.dataSourceName());
            return tableInfo;
        } else if (clz.isAnnotationPresent(Extension.class)) {
            Extension extension = clz.getAnnotation(Extension.class);
            Class<?> eClazz = extension.extend();
            if (!eClazz.equals(Void.class)) {
                return getTableInfo(eClazz);
            }
        }
        tableInfo.setTableName(Strings.underscoreName(clz.getSimpleName()));
        tableInfo.setDataSourceName(AbstractDataSourceFactory.DEFULT_NAME);
        return tableInfo;
    }

    public String getFieldByColumn(String column) {
        return allReverseColumns.get(column);
    }

    public String getColumnByField(String field) {
        return allColumns.get(field);
    }

    private ShardTableInfo getShardTable(Class<?> clz) {
        try {
            if (clz.isAnnotationPresent(ShardTable.class)) {
                ShardTableInfo tableInfo = new ShardTableInfo();
                ShardTable shardTable = clz.getAnnotation(ShardTable.class);
                tableInfo.setDatasourceName(shardTable.dataSourceName());
                String[] tableArr = shardTable.tables();
                List<String> tables = new ArrayList<>(tableArr.length);
                Collections.addAll(tables, tableArr);
                tableInfo.setTalbes(tables);
                Class<? extends TableShardStrategy> tbStrategy = shardTable.tableShardStrategy();
                Class<? extends DataSourceShardStrategy> dbStrategy = shardTable.dbShardStrategy();
                if (tbStrategy == NotShardStrategy.class && dbStrategy == NotShardStrategy.class) {
                    throw new BadgerException("if annotated with @ShardTable, " +
                            "either tableShardStrategy or dbShardStrategy must be configured");
                }
                if (tbStrategy != NotShardStrategy.class) {
                    tableInfo.setTableShardStrategy(tbStrategy.newInstance());
                }
                if (dbStrategy != NotShardStrategy.class) {
                    tableInfo.setDbShardStrategy(dbStrategy.newInstance());
                }
                /*tableInfo.setShardType(shardTable.shardType());*/
                return tableInfo;
            } else if (clz.isAnnotationPresent(Extension.class)) {
                Extension extension = clz.getAnnotation(Extension.class);
                Class<?> eClazz = extension.extend();
                if (!eClazz.equals(Void.class)) {
                    return getShardTable(eClazz);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BadgerException(e);
        }
        return null;
    }

    private void fillNeedColumns(Class<?> clz) {
        this.idFields = new ArrayList<>();
        this.idColumns = new ArrayList<>();
        this.allColumns = new HashMap<>();
        this.allReverseColumns = new HashMap<>();
        this.columns = new ArrayList<>();
        this.fields = new ArrayList<>();
        List<PropertyMeta> metas = BeanUtil.fetchPropertyMetas(clz);
        for (PropertyMeta meta : metas) {
            if (notFields.contains(meta.getName())) {
                continue;
            }
            Id id = meta.getPropertyAnno(Id.class);
            Column column = meta.getPropertyAnno(Column.class);
            ShardColumn shardColumn = meta.getPropertyAnno(ShardColumn.class);
            String fieldName = meta.getName();
            if (id != null || column != null || shardColumn != null) {
                String columnName = getColumnName(fieldName, column);
                allColumns.put(fieldName, columnName);
                allReverseColumns.put(columnName, fieldName);
                if (id != null) {
                    idFields.add(fieldName);
                    idColumns.add(columnName);
                } else {
                    columns.add(columnName);
                    fields.add(fieldName);
                }
                if (shardColumn != null && shardTableInfo != null) {
                    shardTableInfo.setColumn(columnName);
                    shardTableInfo.setFieldName(fieldName);
                }
            }
        }
    }

    private String getColumnName(String fieldName, Column column) {
        if (column == null || Strings.isNullOrEmpty(column.name())) {
            return Strings.underscoreName(fieldName);
        }
        return column.name();
    }
}
