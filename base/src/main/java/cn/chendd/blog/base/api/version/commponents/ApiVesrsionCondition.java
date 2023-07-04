package cn.chendd.blog.base.api.version.commponents;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestCondition自定义实现
 *
 * @author chendd
 * @date 2019/10/16 22:36
 */
public class ApiVesrsionCondition implements RequestCondition<ApiVesrsionCondition> {

    private String apiVersion;

    private String getApiVersion() {
        return this.apiVersion;
    }

    public ApiVesrsionCondition(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public ApiVesrsionCondition combine(ApiVesrsionCondition other) {
        return new ApiVesrsionCondition(other.getApiVersion());
    }

    /**
    * 用于根据request对象提供对象筛选条件规则
    */
    @Override
    public ApiVesrsionCondition getMatchingCondition(HttpServletRequest request) {
        return this;
    }

    /**
    * 用于定位多组地址的版本号排序筛选规则
    */
    @Override
    public int compareTo(ApiVesrsionCondition other, HttpServletRequest request) {
        return 0;
    }
}
