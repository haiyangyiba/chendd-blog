package cn.chendd.blog.admin.blog.online.service;

import cn.chendd.blog.admin.blog.online.model.SpringSession;
import cn.chendd.blog.admin.blog.online.po.OnlineManageParam;
import cn.chendd.blog.admin.blog.online.vo.OnlineManageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author chendd
 * @date 2020/7/15 21:49
 */
public interface IOnlineManageService extends IService<SpringSession> {

    /**
     * 查询在线人数管理
     * @param param 查询条件
     * @return 列表数据
     */
    Page<OnlineManageResult> queryOnlineManagePage(OnlineManageParam param);
}
