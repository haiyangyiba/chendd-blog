package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.client.infocontent.SysInfoProjectResult;
import cn.chendd.blog.client.system.SystemInfoResult;
import cn.chendd.blog.web.home.client.InfoContentClient;
import cn.chendd.blog.web.home.service.InfoContentService;
import cn.chendd.core.result.BaseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 内容管理接口实现
 * @author chendd
 * @date 2021/06/20 20:12
 */
@Service
public class InfoContentServiceImpl implements InfoContentService {

    @Resource
    private InfoContentClient client;

    @Override
    public SysInfoProjectResult getProjectInfo(String key) {
        BaseResult<SysInfoProjectResult> baseResult = client.getProjectInfo(key);
        return baseResult.getData();
    }

    @Override
    public SystemInfoResult getSystemInfo() {
        BaseResult<SystemInfoResult> baseResult = this.client.getSystemInfo();
        return baseResult.getData();
    }
}
