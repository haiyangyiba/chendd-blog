package cn.chendd.blog.web.components;

import cn.chendd.blog.web.home.service.IndexService;
import cn.chendd.core.common.treeview.stratified.Treeview;
import cn.chendd.core.spring.SpringBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 初始化加载
 *
 * @author chendd
 * @date 2021/4/9 10:24
 */
@Component
@Slf4j
public class WebInitApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //获取菜单数据
        GenericWebApplicationContext applicationContext = (GenericWebApplicationContext) event.getApplicationContext();
        initMenuList(applicationContext);
    }

    private void initMenuList(GenericWebApplicationContext applicationContext) {
        new Thread(() -> {
            while(true) {
                try {
                    List<Treeview> menuList = SpringBeanFactory.getBean(IndexService.class).queryMenus();
                    resetMenuList(menuList);
                    applicationContext.getServletContext().setAttribute("menuList" , menuList);
                    log.info("首页菜单初始化-成功");
                    break;
                } catch (Exception e) {
                    log.error("首页菜单初始化-错误：{}" , e.getMessage());
                    try {
                        Thread.sleep(1000 * 10L);
                    } catch (InterruptedException ie) {}
                }
            }
        }).start();

        /*Executor executor = (Executor) SpringBeanFactory.getBean("simplePoolExecutor");
        executor.execute(() -> {
            while(true) {
                try {
                    List<Treeview> menuList = SpringBeanFactory.getBean(IndexService.class).queryMenus();
                    resetMenuList(menuList);
                    applicationContext.getServletContext().setAttribute("menuList" , menuList);
                    log.info("首页菜单初始化-成功");
                    break;
                } catch (Exception e) {
                    log.error("首页菜单初始化-错误：{}" , e.getMessage());
                    try {
                        Thread.sleep(1000 * 10L);
                    } catch (InterruptedException ie) {}
                }
            }
        });*/
    }

    /**
     * 重置菜单集合数据，将特殊URL设置为具体的url地址，最多处理5级节点
     * @param menuList 菜单列表
     */
    public static void resetMenuList(List<Treeview> menuList) {

        if (CollectionUtils.isEmpty(menuList)) {
            return;
        }
        for (Treeview treeview : menuList) {
            String url = (String) treeview.getValues().get("url");
            if (StringUtils.equalsIgnoreCase("goUrl" , url)) {
                treeview.getValues().put("url" , String.format("/blog/article/type/%s.html" , treeview.getId()));
            } else if (StringUtils.equalsIgnoreCase(url , "#") || StringUtils.isBlank(url)) {
                treeview.getValues().put("url" , "javascript:void(0);");
            }
            resetMenuList(treeview.getNodes());
        }
    }

}
