package cn.chendd.toolkit.operationlog.service.impl;

import cn.chendd.toolkit.operationlog.mapper.AutoCreateTableMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 自动创建表Service接口实现
 *
 * @author chendd
 * @date 2020/7/10 21:02
 */
@Service
public class AutoCreateTableServiceImpl implements cn.chendd.toolkit.operationlog.service.AutoCreateTableService {

    @Resource
    private AutoCreateTableMapper autoCreateTableMapper;

    /**
     * 查询表是否存在
     * @param tableName 表名称
     */
    public Boolean tableExist(String tableName) {
        return autoCreateTableMapper.getTableExist(tableName);
    }

    @Override
    public void createTable(String oldTableName, String newTableName) {
        autoCreateTableMapper.createTable(oldTableName , newTableName);
    }

}
