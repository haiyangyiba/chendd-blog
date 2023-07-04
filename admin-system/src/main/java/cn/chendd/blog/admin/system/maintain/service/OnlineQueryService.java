package cn.chendd.blog.admin.system.maintain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * SQL在线查询Service接口查询
 *
 * @author chendd
 * @date 2022/2/24 9:57
 */
public interface OnlineQueryService {

    /**
     * 执行查询SQL
     * @param sql 查询sql语句
     * @return 查询结果
     */
    Page<Map<String, Object>> executeQuery(String sql);
}
