package org.jfaster.badger.sql.select;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author yanpengfang
 * @create 2019-04-19 6:31 PM
 */
public interface Condition {

    String getSql();

    List<Object> getParams();

    <T> Condition gt(String column, T param, Predict<T> predict);

    <T> Condition eq(String column, T param, Predict<T> predict);


    <T> Condition gte(String column, T param, Predict<T> predict);


    <T> Condition lt(String column, T param, Predict<T> predict);


    <T> Condition lte(String column, T param, Predict<T> predict);


    <T> Condition like(String column, T param, Predict<T> predict);

    <T> Condition ne(String column, T param, Predict<T> predict);


    //##################################################


    <T> Condition gt(String column, T param);

    <T> Condition gte(String column, T param);

    <T> Condition lt(String column, T param);

    <T> Condition lte(String column, T param);

    <T> Condition like(String column, T param);

    <T> Condition eq(String column, T param);

    <T> Condition ne(String column, T param);

    //###################################################################


    <T> Condition in(String column, Collection<T> param);


    Condition subLeft();


    Condition subRight();


    Condition and();


    Condition or();

    //###############################################

    Condition groupBy(String... column);

    Condition orderByAsc(String... column);

    Condition orderByDesc(String... column);

}
