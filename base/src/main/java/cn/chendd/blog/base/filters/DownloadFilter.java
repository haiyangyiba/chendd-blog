package cn.chendd.blog.base.filters;

import cn.chendd.blog.base.log.BaseLog;
import cn.chendd.blog.base.model.UserInfo;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.user.UserContext;
import cn.chendd.core.utils.Http;
import cn.chendd.core.utils.Ip;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 下载文件过滤器【只可以在web项目中启用】
 *
 * @author chendd
 * @date 2022/7/5 22:19
 */
public class DownloadFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        UserInfo userInfo = UserContext.getCurrentUser(request.getSession() , UserInfo.class);
        if (userInfo == null) {
            //未登录页面
            String basePath = Http.getBasePath(request);
            response.sendRedirect(basePath + "/blog/frame/login.html");
            return;
        }
        final UserInfo.SysAccount account = userInfo.getAccount();
        final UserInfo.SysUser user = userInfo.getUser();
        if (account != null && user != null) {
            this.downloadAttachment(request , account , user);
        }
        filterChain.doFilter(request , response);
    }

    /**
     * 下载附件
     * @param request request
     * @param account 用户账号
     * @param user 用户对象
     */
    private void downloadAttachment(HttpServletRequest request , UserInfo.SysAccount account , UserInfo.SysUser user) {
        final String uri = request.getRequestURI();
        String fileName = FilenameUtils.getName(uri);
        final String referer = request.getHeader("referer");
        String targetId = StringUtils.substringBefore(StringUtils.substringAfterLast(referer , StringPool.SLASH) , StringPool.DOT);
        if (! NumberUtils.isCreatable(targetId)) {
            BaseLog.getLogger().info("{} 用户 {} 下载文件 {} 来自 {} 页" , user.getUserSource().getText() , account.getUsername() , fileName , referer);
            return;
        }
        String ip = Ip.getIpAddress(request);
        Object object = SpringBeanFactory.getBean("commentServiceImpl");
        try {
            MethodUtils.invokeMethod(object , "saveComment" , Long.valueOf(targetId), user.getUserId(), ip);
            BaseLog.getLogger().info("{} 用户 {} 下载文件 {} 来自 {} 页" , user.getUserSource().getText() , account.getUsername() , fileName , referer);
        } catch (Exception e) {
            BaseLog.getLogger().error("{} 用户 {} 下载文件 {} 来自 {} 页 出现错误：" , user.getUserSource().getText() , account.getUsername() , fileName , referer , e);
        }
    }
}
