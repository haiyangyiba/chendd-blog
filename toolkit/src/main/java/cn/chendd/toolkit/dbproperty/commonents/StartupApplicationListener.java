package cn.chendd.toolkit.dbproperty.commonents;

import cn.chendd.core.enums.DatetimeBetweenEnum;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import cn.chendd.toolkit.dbproperty.service.SysDbValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 设置服务器启动时系统时间
 *
 * @author chendd
 * @date 2021/11/06 22:08
 */
@Component
public class StartupApplicationListener implements ApplicationListener<ApplicationContextEvent> {

    private static final AtomicReference<Date> server = new AtomicReference<>();

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        //设置服务器启动时间
        server.set(new Date());
    }

    /**
     * 获取服务器启动时的系统时间
     * @return 时间
     */
    public static String getStartupServerTime() {
        Date date = server.get();
        if (date == null) {
            return "unknown";
        }
        return DateFormat.getTimeInterval(date);
    }
}
