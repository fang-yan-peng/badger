package org.jfaster.badger.sql.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * sql条件
 * @author yanpengfang
 * create 2019-04-19 5:32 PM
 */
public class ConditionImpl implements Condition {

    private StringBuilder sqlBuilder = new StringBuilder();

    private List<Object> params = new ArrayList<>();

    private static final String OR = " or";

    private static final String AND = " and";

    private static final String DESC = " desc";

    private static final String ASC = " asc";

    private String last;

    @Override
    public <T> Condition gt(String column, T param, Predict<T> predict) {
        if (predict.apply(param)) {
            sqlBuilder.append(" ").append(column).append(" > ?");
            params.add(param);
        } else if (last != null) {
            sqlBuilder.delete(sqlBuilder.length() - last.length(), sqlBuilder.length());
        }
        return this;
    }

    @Override
    public <T> Condition eq(String column, T param, Predict<T> predict) {
        if (predict.apply(param)) {
            sqlBuilder.append(" ").append(column).append(" = ?");
            params.add(param);
        } else if (last != null) {
            sqlBuilder.delete(sqlBuilder.length() - last.length(), sqlBuilder.length());
        }
        return this;
    }

    @Override
    public <T> Condition gte(String column, T param, Predict<T> predict) {
        if (predict.apply(param)) {
            sqlBuilder.append(" ").append(column).append(" >= ?");
            params.add(param);
        } else if (last != null) {
            sqlBuilder.delete(sqlBuilder.length() - last.length(), sqlBuilder.length());
        }
        return this;

    }

    @Override
    public <T> Condition lt(String column, T param, Predict<T> predict) {
        if (predict.apply(param)) {
            sqlBuilder.append(" ").append(column).append(" < ?");
            params.add(param);
        } else if (last != null) {
            sqlBuilder.delete(sqlBuilder.length() - last.length(), sqlBuilder.length());
        }
        return this;

    }

    @Override
    public <T> Condition lte(String column, T param, Predict<T> predict) {
        if (predict.apply(param)) {
            sqlBuilder.append(" ").append(column).append(" <= ?");
            params.add(param);
        } else if (last != null) {
            sqlBuilder.delete(sqlBuilder.length() - last.length(), sqlBuilder.length());
        }
        return this;

    }

    @Override
    public <T> Condition like(String column, T param, Predict<T> predict) {
        if (predict.apply(param)) {
            sqlBuilder.append(" ").append(column).append(" like ?");
            params.add(param);
        } else if (last != null) {
            sqlBuilder.delete(sqlBuilder.length() - last.length(), sqlBuilder.length());
        }
        return this;

    }

    @Override
    public <T> Condition ne(String column, T param, Predict<T> predict) {
        if (predict.apply(param)) {
            sqlBuilder.append(" ").append(column).append(" != ?");
            params.add(param);
        } else if (last != null) {
            sqlBuilder.delete(sqlBuilder.length() - last.length(), sqlBuilder.length());
        }
        return this;
    }

    //##################################################

    @Override
    public <T> Condition gt(String column, T param) {
        return this.gt(column, param, Objects::nonNull);
    }

    @Override
    public <T> Condition gte(String column, T param) {
        return this.gte(column, param, Objects::nonNull);

    }

    @Override
    public <T> Condition lt(String column, T param) {
        return this.lt(column, param, Objects::nonNull);

    }

    @Override
    public <T> Condition lte(String column, T param) {
        return this.lte(column, param, Objects::nonNull);

    }

    @Override
    public <T> Condition like(String column, T param) {
        return this.like(column, param, Objects::nonNull);
    }

    @Override
    public <T> Condition eq(String column, T param) {
        return this.eq(column, param, Objects::nonNull);
    }

    @Override
    public <T> Condition ne(String column, T param) {
        return this.ne(column, param, Objects::nonNull);
    }

    //###################################################################

    @Override
    public <T> Condition in(String column, Collection<T> param) {
        if (param != null && !param.isEmpty()) {
            sqlBuilder.append(" ").append(column).append(" in(?");
            Iterator<T> it = param.iterator();
            if (it.hasNext()) {
                params.add(it.next());
            }
            while (it.hasNext()) {
                sqlBuilder.append(",?");
                params.add(it.next());
            }
            sqlBuilder.append(")");
        } else if (last != null) {
            sqlBuilder.delete(sqlBuilder.length() - last.length(), sqlBuilder.length());
        }
        return this;
    }

    @Override
    public Condition subLeft() {
        sqlBuilder.append(" (");
        return this;
    }

    @Override
    public Condition subRight() {
        sqlBuilder.append(" )");
        return this;
    }

    @Override
    public Condition and() {
        if (sqlBuilder.length() == 0) {
            sqlBuilder.append("1=1");
        }
        sqlBuilder.append(AND);
        last = AND;
        return this;
    }

    @Override
    public Condition or() {
        if (sqlBuilder.length() == 0) {
            sqlBuilder.append("1=1");
        }
        sqlBuilder.append(OR);
        last = OR;
        return this;
    }

    @Override
    public Condition groupBy(String... column) {
        if (column == null || column.length == 0) {
            return this;
        }
        if (sqlBuilder.length() == 0) {
            sqlBuilder.append("1=1");
        }
        sqlBuilder.append(" group by ").append(column[0]);
        for (int i = 1; i < column.length; ++i) {
            sqlBuilder.append(",").append(column[i]);
        }
        return this;
    }

    @Override
    public Condition orderByAsc(String... column) {
        if (column == null || column.length == 0) {
            return this;
        }
        if (sqlBuilder.length() == 0) {
            sqlBuilder.append("1=1");
        }
        sqlBuilder.append(" order by ").append(column[0]).append(ASC);
        for (int i = 1; i < column.length; ++i) {
            sqlBuilder.append(",").append(column[i]).append(ASC);
        }
        return this;
    }

    @Override
    public Condition orderByDesc(String... column) {
        if (column == null || column.length == 0) {
            return this;
        }
        if (sqlBuilder.length() == 0) {
            sqlBuilder.append(" 1=1");
        }
        sqlBuilder.append(" order by ").append(column[0]).append(DESC);
        for (int i = 1; i < column.length; ++i) {
            sqlBuilder.append(",").append(column[i]).append(DESC);
        }
        return this;
    }

    @Override
    public Condition orderBy(OrderByColumn... columns) {
        if (columns == null || columns.length == 0) {
            return this;
        }
        if (sqlBuilder.length() == 0) {
            sqlBuilder.append(" 1=1");
        }
        sqlBuilder.append(" order by ").append(columns[0].orderColumn());
        for (int i = 1; i < columns.length; ++i) {
            sqlBuilder.append(",").append(columns[i].orderColumn());
        }
        return this;
    }

    @Override
    public String getSql() {
        if (sqlBuilder.length() == 0) {
            return "1=1";
        }
        return sqlBuilder.toString();
    }

    @Override
    public List<Object> getParams() {
        return params;
    }
}
