package cn.chendd.blog.web.components;

import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.Interceptor;

/**
 * Forest Client 接口拦截器
 *
 * @author chendd
 * @date 2022/7/24 20:56
 */
public class ForestApiInterceptor implements Interceptor<Object> {

    public ForestApiInterceptor() {

    }

    @Override
    public boolean beforeExecute(ForestRequest request) {
        request.addHeader("api" , "chendd");
        return Interceptor.super.beforeExecute(request);
    }
}
