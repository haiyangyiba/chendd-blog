package cn.chendd.toolkit.operationlog.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 自动创建表Mapper接口定义
 *
 * @author chendd
 * @date 2020/7/10 21:22
 */
public interface AutoCreateTableMapper {

    /**
     * 根据tableName查询表名是否存在
     * @param tableName 表名
     */
    Boolean getTableExist(String tableName);

    /**
     * 根据旧表创建新表，采用 create table as select
     * @param oldTableName 旧表
     * @param newTableName 新表
     */
    void createTable(@Param("oldTableName") String oldTableName, @Param("newTableName") String newTableName);
}
