package cn.chendd.blog.admin.system.maintain.mapper;

import cn.chendd.blog.base.model.Dual;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * SQL在线查询Mapper
 *
 * @author chendd
 * @date 2022/2/24 10:16
 */
public interface OnlineQueryMapper extends BaseMapper<Dual> {

    /**
     * SQL在线查询分页
     * @param sql sql语句
     * @param page 分页语句
     * @return 列表数据
     */
    Page<Map<String , Object>> queryForList(@Param("sql") String sql , Page<Map<String , Object>> page);

}
