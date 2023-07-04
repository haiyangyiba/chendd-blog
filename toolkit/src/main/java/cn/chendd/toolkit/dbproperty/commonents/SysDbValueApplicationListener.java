package cn.chendd.toolkit.dbproperty.commonents;

import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import cn.chendd.toolkit.dbproperty.service.SysDbValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.List;

/**
 * spring服务加载完毕触发
 *
 * @author chendd
 * @date 2019/9/14 12:34
 */
@Component
@Slf4j
public class SysDbValueApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("加载数据系统参数配置表数据开始");
        GenericWebApplicationContext applicationContext = (GenericWebApplicationContext) contextRefreshedEvent.getApplicationContext();
        SysDbValueService service = applicationContext.getBean(SysDbValueService.class);
        List<SysDbValue> sysDbValues = service.queryAllAndRefresh();
        log.info("加载数据系统参数配置表数据 {} 条" , sysDbValues.size());
    }

}
