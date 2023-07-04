package cn.chendd.blog.web.forest.examples;

import cn.chendd.blog.web.BaseBootstrapTest;
import cn.chendd.core.result.BaseResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.servlet.tags.Param;

import javax.annotation.Resource;

/**
 * ForestApiPostTest
 *
 * @author chendd
 * @date 2020/9/30 21:06
 */
@Slf4j
public class ForestApiPostTest extends BaseBootstrapTest {

    @Resource
    private ForestApiPostClient postClient;

    @Test
    public void testNoParam() {
        log.debug("======无参返回String类型======");
        BaseResult result = postClient.noParam();
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testMoreParam() {
        log.debug("======多个参数返回String======");
        BaseResult result = postClient.more("chendd" , 86);
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testMoreParam2Entity() {
        log.debug("======多个参数传递为对象类型======");
        BaseResult result = postClient.entity("chendd" , "666");
        log.debug(JSON.toJSONString(result , true));
    }

    @Test
    public void testBody2Entity() {
        log.debug("======直接传递JSON对象类型======");
        Param param = new Param();
        param.setName("chendd");
        param.setValue("888");
        BaseResult result = postClient.body(param);
        log.debug(JSON.toJSONString(result , true));
    }

}
