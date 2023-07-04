package cn.chendd.blog.base.spring.component;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 拦截器地址配置
 *
 * @author chendd
 * @date 2021/2/14 19:54
 */
@Getter
@Setter
public class InterceptorConfiguration {

    /**
     * 拦截器实现类地址
     */
    private String beanClassName;
    /**
     * 拦截器拦截的路径范围
     */

    private List<String> includeMapping = Lists.newArrayList();
    /**
     * 按请求类型放行
     */
    private List<String> excludeMappingTypes = Lists.newArrayList();
    /**
     * 拦截器放行地址
     */
    private List<String> excludeMapping = Lists.newArrayList();

}
