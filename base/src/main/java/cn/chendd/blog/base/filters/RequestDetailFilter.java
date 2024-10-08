package cn.chendd.blog.base.filters;

import cn.chendd.blog.base.filters.detail.RequestItem;
import cn.chendd.blog.base.log.BaseLog;
import cn.chendd.blog.base.spring.component.SystemParamComponent;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.utils.Ajax;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.core.utils.Http;
import cn.chendd.core.utils.Ip;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 请求详细Filter，只记录.html后缀和非Ajax请求的
 *
 * @author chendd
 * @date 2020/7/20 19:03
 */
public class RequestDetailFilter implements Filter {

    /**
     * 请求资源队列
     */
    public static BlockingQueue<RequestItem> REQUEST_ITEM_QUEUE = Queues.newLinkedBlockingQueue();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final boolean ipBlack = this.checkIpBlack(request);
        //构造请求对象，并记录日志
        RequestItem item = info(request);
        //ip黑名单处理
        if (ipBlack) {
            //如果不是Ajax请求，则标记ip地址增加ip_black
            if (item != null) {
                item.setUrl(item.getUrl() + "#ip_black");
            }
            BaseLog.getLogger().warn("出现ipBlack防护黑名单：item = {}" , item);
            response.sendRedirect("about:blank");
            return;
        }
        //获取当前运行环境，如果时生产环境则限制访问的域名必须是本站：chendd.cn，白名单
        if (this.checkBaseWhite(request)) {
            chain.doFilter(request, servletResponse);
        } else {
            BaseLog.getLogger().warn("出现basePath防护黑名单：item = {}" , item);
            response.sendRedirect("about:blank");
        }

    }

    @Override
    public void destroy() {

    }

    /**
     * 从request中获取信息存储至日志文件
     *
     * @param request httpServletRequest
     */
    private RequestItem info(HttpServletRequest request) {
        boolean isAjax = Ajax.isAJAXRequest(request);
        //不记录Ajax
        if (isAjax) {
            return null;
        }
        //过滤掉localhost、127.0.0.1、192.168相关的
        String url = request.getRequestURL().toString();
        //是否为外站请求
        String referer = request.getHeader("referer");
        RequestItem item = new RequestItem();
        item.setUrl(url);
        item.setReferer(referer);
        item.setUserAgent(request.getHeader("user-agent"));
        item.setIp(Ip.getIpAddress(request));
        Date date = new Date();
        item.setCreateDate(DateFormat.formatDate(date));
        item.setCreateTime(DateFormat.formatTime(date));
        //请求记录至队列
        REQUEST_ITEM_QUEUE.add(item);
        return item;
    }

    /**
     * 检查IP是否在黑名单
     *
     * @param request 请求
     * @return boolean
     */
    private boolean checkIpBlack(HttpServletRequest request) {
        final List<String> ipAddressList = Ip.getIpAddressList(request);
        final List<String> ipBlackList = this.getIpBlackDataList();
        if (ipBlackList == null) {
            return false;
        }
        for (String userIp : ipAddressList) {
            for (String blackIp : ipBlackList) {
                if (StringUtils.equals(userIp, blackIp) || userIp.matches(blackIp)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查basePath是否在白名单
     *
     * @param request 请求
     * @return boolean
     */
    private boolean checkBaseWhite(HttpServletRequest request) {
        final String basePath = Http.getBasePath(request);
        final List<String> basePathWhiteDataList = this.getBasePathWhiteDataList();
        if (basePathWhiteDataList == null) {
            return true;
        }
        for (String basePathWhite : basePathWhiteDataList) {
            if (StringUtils.contains(basePath , basePathWhite)) {
                return true;
            }
        }
        return false;
    }

    /**
     * ip限制黑名单
     * @return 黑名单ip地址
     */
    private List<String> getIpBlackDataList() {
        final String ipBlack = SpringBeanFactory.getBean(SystemParamComponent.class).getIpBlack();
        if (ipBlack != null) {
            return Lists.newArrayList(ipBlack.split(StringPool.COMMA));
        }
        return null;
    }

    /**
     * basePath限制白名单
     * @return 白名单
     */
    private List<String> getBasePathWhiteDataList() {
        final String basePathWhite = SpringBeanFactory.getBean(SystemParamComponent.class).getBasePathWhite();
        if (basePathWhite != null) {
            return Lists.newArrayList(basePathWhite.split(StringPool.COMMA));
        }
        return null;
    }
}
