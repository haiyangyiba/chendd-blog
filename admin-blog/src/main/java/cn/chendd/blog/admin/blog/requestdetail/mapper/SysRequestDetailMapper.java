package cn.chendd.blog.admin.blog.requestdetail.mapper;

import cn.chendd.blog.admin.blog.requestdetail.model.SysRequestDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口定义
 *
 * @author chendd
 * @date 2020/7/21 11:17
 */
public interface SysRequestDetailMapper extends BaseMapper<SysRequestDetail> {

    /**
     * 根据日期查询访问数量
     * @param date 日期
     * @return 访问次数
     */
    Integer queryRequestAccessDate(@Param("date") String date);

    /**
     * 根据日期查询外链访问（从外部进入站点）数量
     * @param date 昨日日期
     * @return 访问次数
     */
    Integer queryRequestAccessOutLinkDate(@Param("date") String date);
}
