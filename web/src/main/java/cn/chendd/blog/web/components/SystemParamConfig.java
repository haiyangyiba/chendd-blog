package cn.chendd.blog.web.components;

import cn.chendd.toolkit.dbproperty.annotions.DbValue;
import cn.chendd.toolkit.dbproperty.annotions.DbValueConfiguration;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 系统参数配置（暂时未使用到）
 *
 * @author chendd
 * @date 2022/3/8 21:53
 */
@DbValueConfiguration
@Component
@Data
@Deprecated
public class SystemParamConfig {

    @DbValue
    private String adminServerPath;

}
