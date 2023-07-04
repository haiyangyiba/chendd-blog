package cn.chendd.blog.admin.blog.online.service.impl;

import cn.chendd.blog.admin.blog.online.mapper.OnlineManageMapper;
import cn.chendd.blog.admin.blog.online.model.SpringSession;
import cn.chendd.blog.admin.blog.online.po.OnlineManageParam;
import cn.chendd.blog.admin.blog.online.service.IOnlineManageService;
import cn.chendd.blog.admin.blog.online.vo.OnlineManageResult;
import cn.chendd.toolkit.operationlog.annotions.Log;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 在线人数管理接口实现
 *
 * @author chendd
 * @date 2020/7/15 21:50
 */
@Service
public class OnlineManageService extends ServiceImpl<OnlineManageMapper, SpringSession> implements IOnlineManageService {

    @Resource
    private OnlineManageMapper onlineManageMapper;

    @Override
    @Log(description = "SQL在线查询")
    public Page<OnlineManageResult> queryOnlineManagePage(OnlineManageParam param) {
        Page<OnlineManageResult> page = new Page<>(param.getPageNumber() , param.getPageSize());
        return onlineManageMapper.queryOnlineManagePage(page , param);
    }

}
