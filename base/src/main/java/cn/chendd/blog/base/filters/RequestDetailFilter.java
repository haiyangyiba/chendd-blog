package cn.chendd.blog.base.filters;

import cn.chendd.blog.base.filters.detail.RequestItem;
import cn.chendd.core.utils.Ajax;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.core.utils.Ip;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Queues;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
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
        //构造请求对象，并记录日志
        info(request);
        chain.doFilter(request , servletResponse);
    }

    @Override
    public void destroy() {

    }

    /**
     * 从request中获取信息存储至日志文件
     * @param request httpServletRequest
     */
    private void info(HttpServletRequest request) {
        boolean isAjax = Ajax.isAJAXRequest(request);
        //不记录Ajax
        if (isAjax) {
            return;
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
    }

}
