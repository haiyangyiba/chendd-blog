package cn.chendd;

import cn.chendd.blog.Bootstrap;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * test
 *
 * @author chendd
 * @date 2020/9/26 20:53
 */
@SpringBootTest(classes = Bootstrap.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class BaseBootstrapTest {

}
