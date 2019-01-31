package org.jfaster.badger.jdbc.mapper.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfaster.badger.exception.MappingException;
import org.jfaster.badger.jdbc.PropertyTokenizer;
import org.jfaster.badger.jdbc.ResultSetWrapper;
import org.jfaster.badger.jdbc.mapper.RowMapper;
import org.jfaster.badger.jdbc.type.TypeHandler;
import org.jfaster.badger.jdbc.type.TypeHandlerRegistry;
import org.jfaster.badger.query.bean.invoker.SetterInvoker;
import org.jfaster.badger.query.bean.invoker.support.FunctionalSetterInvokerGroup;
import org.jfaster.badger.query.bean.invoker.support.InvokerCache;
import org.jfaster.badger.util.Reflections;
import org.jfaster.badger.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 单列或多列组装对象RowMapper
 */
public class BeanPropertyRowMapper<T> implements RowMapper<T> {

    private final static Logger logger = LoggerFactory.getLogger(BeanPropertyRowMapper.class);

    private Class<T> mappedClass;

    private Map<String, SetterInvoker> invokerMap;

    private Map<String, String> columnToPropertyMap;

    public BeanPropertyRowMapper(Class<T> mappedClass, Map<String, String> propertyToColumnMap) {
        initialize(mappedClass, propertyToColumnMap);
    }

    protected void initialize(Class<T> mappedClass, Map<String, String> propertyToColumnMap) {
        this.mappedClass = mappedClass;
        this.columnToPropertyMap = new HashMap<>();
        this.invokerMap = new HashMap<>();

        // 初始化columnToPropertyPathMap
        for (Map.Entry<String, String> entry : propertyToColumnMap.entrySet()) {
            String property = entry.getKey();
            PropertyTokenizer propToken = new PropertyTokenizer(property);
            if (propToken.hasNext()) {
                columnToPropertyMap.put(entry.getValue().toLowerCase(), property);
            }
        }

        // 初始化invokerMap
        List<SetterInvoker> invokers = InvokerCache.getSetterInvokers(mappedClass);
        for (SetterInvoker invoker : invokers) {
            String column = propertyToColumnMap.get(invoker.name());
            if (column != null) { // 使用配置映射
                invokerMap.put(column.toLowerCase(), invoker);
            } else { // 使用约定映射
                invokerMap.put(invoker.name().toLowerCase(), invoker);
                String underscoredName = Strings.underscoreName(invoker.name());
                if (!invoker.name().toLowerCase().equals(underscoredName)) {
                    invokerMap.put(underscoredName, invoker);
                }
            }
        }
    }

    public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
        T mappedObject = Reflections.instantiate(mappedClass);

        ResultSetWrapper rsw = new ResultSetWrapper(rs);
        int columnCount = rsw.getColumnCount();

        for (int index = 1; index <= columnCount; index++) {
            String columnName = rsw.getColumnName(index);
            String lowerCaseColumnName = columnName.toLowerCase();
            String propertyPath = columnToPropertyMap.get(lowerCaseColumnName);
            if (propertyPath != null) { // 使用自定义多级映射
                setValueByPropertyPath(mappedObject, propertyPath, rsw, index, rowNumber);
            } else {
                PropertyTokenizer prop = new PropertyTokenizer(columnName);
                if (prop.hasNext()) { // select语句中的字段存在多级映射
                    setValueByPropertyPath(mappedObject, columnName, rsw, index, rowNumber);
                } else { // 单级映射（包含约定和自定义）
                    setValueByProperty(mappedObject, lowerCaseColumnName, rsw, index, rowNumber);
                }
            }
        }
        return mappedObject;
    }

    private void setValueByPropertyPath(Object mappedObject, String propertyPath, ResultSetWrapper rsw,
            int index, int rowNumber) throws SQLException {

        FunctionalSetterInvokerGroup g = FunctionalSetterInvokerGroup.create(mappedClass, propertyPath);
        TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(g.getTargetType(), rsw.getJdbcType(index));
        Object value = typeHandler.getResult(rsw.getResultSet(), index);
        if (logger.isDebugEnabled() && rowNumber == 0) {
            logger.debug("Mapping column '" + rsw.getColumnName(index) + "' to property '" +
                    propertyPath + "' of type " + g.getTargetType());
        }
        g.invoke(mappedObject, value);
    }

    private void setValueByProperty(Object mappedObject, String lowerCaseColumnName, ResultSetWrapper rsw,
            int index, int rowNumber) throws SQLException {

        SetterInvoker invoker = invokerMap.get(lowerCaseColumnName);
        if (invoker != null) {
            TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(invoker.getRawType(), rsw.getJdbcType(index));
            Object value = typeHandler.getResult(rsw.getResultSet(), index);
            if (logger.isDebugEnabled() && rowNumber == 0) {
                logger.debug("Mapping column '" + rsw.getColumnName(index) + "' to property '" +
                        invoker.name() + "' of type " + invoker.getRawType());
            }
            invoker.invoke(mappedObject, value);
        }
        throw new MappingException("Unable to map column '" + rsw.getColumnName(index) +
                "' to any property of '" + mappedClass + "'");
    }

    @Override
    public Class<T> getMappedClass() {
        return mappedClass;
    }

}
