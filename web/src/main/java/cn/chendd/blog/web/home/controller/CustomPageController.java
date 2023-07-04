package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.base.filters.RequestDetailFilter;
import cn.chendd.blog.base.filters.detail.RequestItem;
import cn.chendd.blog.client.friendslink.FriendsLinkDto;
import cn.chendd.blog.client.system.SystemInfoResult;
import cn.chendd.blog.web.home.service.CustomPageService;
import cn.chendd.blog.web.home.service.FriendsLinkService;
import cn.chendd.blog.web.home.service.InfoContentService;
import cn.chendd.core.spring.SpringBeanFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiSort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * 博客附属信息Controller
 *
 * @author chendd
 * @date 2021/10/17 21:01
 */
@Api(value = "自定义内容页面加载Controller" , tags = "自定义内容页面")
@ApiSort(50)
@Controller
@RequestMapping("/blog/page")
public class CustomPageController extends BaseController {

    /**
     * 自定义页面
     * @param page 页面标识
     * @return 处理逻辑
     */
    @GetMapping("/{page}")
    @ApiOperation(value = "自定义页面" , notes = "自定义页面")
    public String getCustomPage(@ApiParam("页面标识") @PathVariable String page) {
        return this.dispose(page);
    }

    /**
     * 填充友情链接数据
     * @param page 页面标识
     * @return 页面地址
     */
    private String dispose(String page) {
        if (StringUtils.equalsIgnoreCase("link" , page)) {
            //判断是否是友情链接，查询友情链接数据
            FriendsLinkService friendsLinkService = SpringBeanFactory.getBean(FriendsLinkService.class);
            List<FriendsLinkDto> friendsLinkList = friendsLinkService.queryFriendsLink();
            super.addAttribute("friendsLinkList" , friendsLinkList);
            //自定也页面内容
            this.disposeCustomPage(page);
            return "/page/link";
        } else if (StringUtils.equalsIgnoreCase("server" , page)) {
            InfoContentService infoContentService = SpringBeanFactory.getBean(InfoContentService.class);
            SystemInfoResult systemInfoResult = infoContentService.getSystemInfo();
            super.addAttribute("custom" , systemInfoResult);
            return "/page/server";
        } else if (StringUtils.equalsIgnoreCase("history" , page)) {
            this.disposeCustomPage(page);
            return "/page/history";
        } else if (StringUtils.equalsIgnoreCase("visits" , page)) {
            this.disposeVisitsPage(page);
            return "/page/visits";
        }
        return this.disposeCustomPage(page);
    }

    /**
     * 处理自定义页面地址
     * @return 页面地址
     */
    private String disposeCustomPage(String page) {
        CustomPageService customPageService = SpringBeanFactory.getBean(CustomPageService.class);
        Map<String , Object> result = customPageService.getCustomPage(page);
        if (result != null) {
            super.addAttribute("custom", result)
                    .addAttribute("article", result.get("article"));
            return "/page/custom";
        } else {
            return "/page/" + page;
        }
    }

    /**
     * 处理访问量
     */
    private void disposeVisitsPage(String page) {
        BlockingQueue<RequestItem> tempQueue = Queues.newLinkedBlockingQueue(RequestDetailFilter.REQUEST_ITEM_QUEUE);
        List<RequestItem> dataList = Lists.newArrayList();
        for (int i = 0; i < tempQueue.size() ;  i++) {
            RequestItem item = tempQueue.poll();
            String agent = item.getUserAgent();
            if (StringUtils.isNotEmpty(agent)) {
                UserAgent userAgent = UserAgent.parseUserAgentString(item.getUserAgent());
                item.setUserAgent(userAgent.getOperatingSystem().getName() + " " + userAgent.getBrowser().getName());
            }
            dataList.add(item);
            i--;
        }
        dataList.sort((v1 , v2) -> v2.getCreateTime().compareTo(v1.getCreateTime()));
        super.addAttribute("visits" , dataList);
    }

}
