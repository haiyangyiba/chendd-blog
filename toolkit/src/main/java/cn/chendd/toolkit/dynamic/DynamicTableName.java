package cn.chendd.toolkit.dynamic;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 动态TableName规则定义
 *
 * @author chendd
 * @date 2020/7/12 22:11
 */
public interface DynamicTableName {

    default String getTableName() {
        return this.getClass().getAnnotation(TableName.class).value();
    }

    /**
     * @return 构建动态表名规则
     */
    default String getTableNameCondition() {
        String value = this.getTableName();
        return value + this.setTableNameCondition();
    }

    /**
     * 定义标名解析规则
     */
    String setTableNameCondition();

}
