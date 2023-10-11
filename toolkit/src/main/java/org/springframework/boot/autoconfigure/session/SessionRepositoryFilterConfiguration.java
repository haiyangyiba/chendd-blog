/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.session;

import java.util.EnumSet;
import java.util.stream.Collectors;

import javax.servlet.DispatcherType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.SessionRepositoryFilter;

/**
 * Configuration for customizing the registration of the {@link SessionRepositoryFilter}.
 *
 * @author Andy Wilkinson
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(SessionRepositoryFilter.class)
@EnableConfigurationProperties(SessionProperties.class)
@Slf4j
public class SessionRepositoryFilterConfiguration {

    @Bean
    FilterRegistrationBean<SessionRepositoryFilter<?>> sessionRepositoryFilterRegistration(
            SessionProperties sessionProperties, SessionRepositoryFilter<?> filter) {
        log.warn("注意：已使用SessionRepositoryFilterConfiguration重载实现");
        //定义session 拦截路径范围
        FilterRegistrationBean<SessionRepositoryFilter<?>> registration = new FilterRegistrationBean<>(filter);
        registration.addUrlPatterns("*.html" , "*.zip" , "*.exe" , "*.txt" , "/system/login" , "/" , "/index.html" ,
                "/third-login/loginCallback" , "/third-login/baiduCallback" ,  "/tencent-login/tencentCallback" ,
                "/swagger-resources" , "/swagger-resources/**");
        registration.addInitParameter("excludeUrls", "/statics/**");
        registration.setDispatcherTypes(getDispatcherTypes(sessionProperties));
        registration.setOrder(sessionProperties.getServlet().getFilterOrder());
        return registration;
    }

    private EnumSet<DispatcherType> getDispatcherTypes(SessionProperties sessionProperties) {
        SessionProperties.Servlet servletProperties = sessionProperties.getServlet();
        if (servletProperties.getFilterDispatcherTypes() == null) {
            return null;
        }
        return servletProperties.getFilterDispatcherTypes().stream().map((type) -> DispatcherType.valueOf(type.name()))
                .collect(Collectors.collectingAndThen(Collectors.toSet(), EnumSet::copyOf));
    }

}
