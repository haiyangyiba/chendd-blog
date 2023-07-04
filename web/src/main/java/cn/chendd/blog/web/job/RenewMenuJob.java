package cn.chendd.blog.web.job;

import cn.chendd.blog.web.components.WebInitApplicationListener;
import cn.chendd.blog.web.home.service.IndexService;
import cn.chendd.core.common.treeview.stratified.Treeview;
import cn.chendd.core.spring.SpringBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 定时更新系统菜单
 *
 * @author chendd
 * @date 2022/3/8 22:03
 */
@Component
@Slf4j
public class RenewMenuJob {

    /**
     * 每隔30分钟更新一下前台菜单
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void execute() {
        log.info("开始执行更新系统菜单定时任务");
        //服务器启动时间
        AnnotationConfigServletWebServerApplicationContext applicationContext = (AnnotationConfigServletWebServerApplicationContext) SpringBeanFactory.getApplicationContext();
        try {
            List<Treeview> menuList = SpringBeanFactory.getBean(IndexService.class).queryMenus();
            WebInitApplicationListener.resetMenuList(menuList);
            Objects.requireNonNull(applicationContext.getServletContext()).setAttribute("menuList" , menuList);
            log.info("首页菜单定时更新-成功");
        } catch (Exception e) {
            log.error("首页菜单定时更新-失败：{}" , e.getMessage());
        }
        log.info("结束执行更新系统菜单定时任务");
    }

}
