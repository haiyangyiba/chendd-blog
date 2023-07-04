package cn.chendd.blog.admin.blog.online.mapper;

import cn.chendd.blog.admin.blog.online.model.SpringSession;
import cn.chendd.blog.admin.blog.online.po.OnlineManageParam;
import cn.chendd.blog.admin.blog.online.vo.OnlineManageResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author chendd
 * @date 2020/7/15 21:51
 */
public interface OnlineManageMapper extends BaseMapper<SpringSession> {

    /**
     * 查询在线人数管理
     * @param page 分页
     * @param param 查询条件
     * @return 查询参数
     */
    Page<OnlineManageResult> queryOnlineManagePage(Page<OnlineManageResult> page, @Param("param") OnlineManageParam param);
}
