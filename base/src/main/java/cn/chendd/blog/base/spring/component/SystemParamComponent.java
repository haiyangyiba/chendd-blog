package cn.chendd.blog.base.spring.component;

import cn.chendd.toolkit.dbproperty.annotions.DbValue;
import cn.chendd.toolkit.dbproperty.annotions.DbValueConfiguration;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 系统参数管理
 *
 * @author chendd
 * @date 2020/6/20 19:09
 */
@DbValueConfiguration
@Component
@Data
public class SystemParamComponent {

    @DbValue(group = "system.file" , value = "rootPath")
    private String rootPath;

    @DbValue(value = "blog.createSiteTime")
    private String createSiteTime;

}
