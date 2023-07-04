package cn.chendd.toolkit.operationlog.service;

/**
 * 自动创建表Service接口定义
 *
 * @author chendd
 * @date 2020/7/10 21:01
 */
public interface AutoCreateTableService {

    /**
     * 查询数据库表是否存在
     * @param tableName 表名称
     */
    Boolean tableExist(String tableName);

    /**
     * 创建动态表
     * @param oldTableName 原表名称
     * @param newTableName 新表名称
     */
    void createTable(String oldTableName, String newTableName);
}
