package cn.chendd.blog.web.forest.examples;

import cn.chendd.blog.web.BaseBootstrapTest;
import cn.chendd.core.result.BaseResult;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * 简单Forest组件测试类
 *
 * @author chendd
 * @date 2020/9/26 20:56
 */
@Slf4j
public class ForestApiGetTest extends BaseBootstrapTest {

    @Autowired
    private ForestApiGetClient client;

    @Test
    public void throwNoParamString() {
        log.debug("======无参返回String类型======");
        BaseResult result = client.string();
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testNoParamEntity() {
        log.debug("======无参返回Entity类型======");
        BaseResult<Point> result = client.entity();
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testNoParamList() {
        log.debug("======无参返回List类型======");
        BaseResult<List<Point>> result = client.list();
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testSayHelloIndexParam() {
        log.debug("======get参数索引占位符======");
        BaseResult result = client.sayHelloIndexParam("chendd" , 666);
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testSayHelloNameVariableParam() {
        log.debug("======get参数路径变量======");
        BaseResult result = client.sayHelloVariableParam("chendd" , 777);
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testSayHelloNameParam() {
        log.debug("======get参数名称占位符======");
        BaseResult result = client.sayHelloNameParam(10 , 20);
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testAutoQueryParam() {
        log.debug("======get参数隐式传递======");
        BaseResult result = client.autoQueryParam(20 , 30);
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testAutoEntityQueryParam() {
        log.debug("======get对象参数隐式传递======");
        BaseResult result = client.autoEntityQueryParam(new Point(30 , 40));
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testDataMapQueryEntity() {
        log.debug("======get对象参数data方式传递map======");
        Map<String , Integer> params = Maps.newHashMap();
        params.put("x" , 40);
        params.put("y" , 50);
        BaseResult result = client.dataMapQueryEntity(40 , 50);
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testDataMapEntity() {
        log.debug("======get对象参数data方式传递map======");
        Map<String , Integer> params = Maps.newHashMap();
        params.put("x" , 40);
        params.put("y" , 50);
        BaseResult result = client.mapQueryEntity(params);
        log.debug(JSON.toJSONString(result , true));
    }

}
