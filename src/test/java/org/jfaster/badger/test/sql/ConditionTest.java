package org.jfaster.badger.test.sql;

import java.util.ArrayList;
import java.util.List;

import org.jfaster.badger.sql.select.Condition;
import org.jfaster.badger.sql.select.ConditionImpl;
import org.junit.Test;

/**
 *
 * @author yanpengfang
 * create 2019-04-19 6:55 PM
 */
public class ConditionTest {

    @Test
    public void cond1() {
        Condition condition = new ConditionImpl();
        List<String> adds = new ArrayList<>();
        adds.add("北京");
        adds.add("上海");
        String cond = condition
                .eq("name", "张三")
                .and()
                .gt("age", 11)
                .and()
                .lt("age", 55)
                .and()
                .like("sex", "%男%")
                .and()
                .subLeft()
                .in("address", adds)
                .subRight()
                .getSql();

        System.out.println(cond);
    }

    @Test
    public void cond2() {
        Condition condition = new ConditionImpl();
        List<String> adds = new ArrayList<>();
        adds.add("北京");
        adds.add("上海");
        String cond = condition
                .eq("name", "张三")
                .or()
                .gt("age", 11)
                .or()
                .lt("age", 55)
                .or()
                .like("sex", "%男%")
                .or()
                .in("address", adds)
                .getSql();
        System.out.println(cond);
    }

    @Test
    public void cond3() {
        Condition condition = new ConditionImpl();
        String cond = condition.eq("name", 2).groupBy("name", "age").getSql();
        System.out.println(cond);
    }

    @Test
    public void cond4() {
        Condition condition = new ConditionImpl();
        String cond = condition.orderByAsc("name", "age").getSql();
        System.out.println(cond);
    }

    @Test
    public void cond5() {
        Condition condition = new ConditionImpl();
        String cond = condition.eq("name", "张三").and().eq("age", 10, a -> a > 15).getSql();
        System.out.println(cond);
    }
}
