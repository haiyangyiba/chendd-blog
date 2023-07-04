package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.client.custompage.CustomPageBo;
import cn.chendd.blog.web.home.client.CustomPageClient;
import cn.chendd.blog.web.home.service.CustomPageService;
import cn.chendd.core.result.BaseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 自定义内容页面Service接口实现
 *
 * @author chendd
 * @date 2022/2/11 22:37
 */
@Service
public class CustomPageServiceImpl implements CustomPageService {

    @Resource
    private CustomPageClient customPageClient;

    @Override
    public Map<String , Object> getCustomPage(String page) {
        BaseResult<Map<String , Object>> result = this.customPageClient.getCustomPage(page);
        return result.getData();
    }
}
