package cn.chendd.blog.admin;

import cn.chendd.blog.base.spring.configuration.ContextConfiguration;
import cn.chendd.toolkit.operationlog.mapper.SysOperationLogMapper;
import cn.chendd.toolkit.operationlog.model.SysOperationLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * admin工程测试类
 *
 * @author chendd
 * @date 2019/9/12 17:48
 */
@SpringBootTest
/*@ComponentScan(basePackages = {
        "cn.chendd.core.spring" , "cn.chendd.blog.admin" , "cn.chendd.toolkit.dbproperty"
})*/
@Import(value = ContextConfiguration.class)
@MapperScan("cn.chendd.**.mapper")
@RunWith(SpringRunner.class)
@Slf4j
public class Bootstrap {

    @Resource
    private SysOperationLogMapper sysOperationLogMapper;

    @Test
    public void testAll() {
        test11();
        test22();
    }

    @Test
    public void test11() {
        log.debug("-----------" + sysOperationLogMapper);
        QueryWrapper<SysOperationLog> query = new QueryWrapper<>();
        Map<String, Object> params = query.getParamNameValuePairs();
        params.put("operUsername" , "chendd");
        System.out.println(params);
        Integer count = sysOperationLogMapper.selectCount(query);
        System.out.println(count);
    }

    @Test
    public void test22() {
        log.debug("-----------" + sysOperationLogMapper);
        QueryWrapper<SysOperationLog> query = new QueryWrapper<>();
        Map<String, Object> params = query.getParamNameValuePairs();
        params.put("operUsername" , "chendd222");
        System.out.println(params);
        Integer count = sysOperationLogMapper.selectCount(query);
        System.out.println(count);
    }

    @Test
    public void test33() {

    }

}
