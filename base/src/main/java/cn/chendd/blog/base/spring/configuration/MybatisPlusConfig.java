package cn.chendd.blog.base.spring.configuration;

import cn.chendd.toolkit.dynamic.DynamicTableName;
import cn.chendd.toolkit.operationlog.model.SysOperationLog;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;
import java.util.Set;

/**
 * MybatisPlusConfig
 *
 * @author chendd
 * @date 2019/10/26 19:19
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor getPaginationInterceptor(){
        PaginationInterceptor interceptor = new PaginationInterceptor();
        //构建动态标名解析器
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        Map<String, ITableNameHandler> tableNameMap = Maps.newHashMap();
        tableNameMap.put("Sys_operationlog", (metaObject, sql, tableName) -> {
            RoutingStatementHandler handler = (RoutingStatementHandler) metaObject.getOriginalObject();
            BoundSql boundSql = handler.getBoundSql();
            Object parameterObject = boundSql.getParameterObject();
            if(parameterObject instanceof SysOperationLog) {
                SysOperationLog sysOperationLog = (SysOperationLog) parameterObject;
                //日志表每个月初始化一次
                return sysOperationLog.getTableNameCondition();
            }
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) handler.getBoundSql().getParameterObject();
            Set<Map.Entry<String , Object>> set = paramMap.entrySet();
            for (Map.Entry<String, Object> entry : set) {
                Object value = entry.getValue();
                if(value instanceof DynamicTableName) {
                    DynamicTableName dynamicTableName = (DynamicTableName) value;
                    return dynamicTableName.getTableNameCondition();
                }
            }
            /*QueryWrapper queryWrapper = (QueryWrapper) paramMap.get("ew");
            System.err.println(queryWrapper.getParamNameValuePairs());
            System.err.println(sql);
            System.err.println(tableName);*/
            return tableName;
        });
        dynamicTableNameParser.setTableNameHandlerMap(tableNameMap);
        interceptor.setSqlParserList(Lists.newArrayList(dynamicTableNameParser));
        return interceptor;
    }

}
