package cn.chendd.blog.web.json;

import cn.chendd.blog.web.home.client.vo.TagManageResult;
import cn.chendd.core.result.BaseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

import java.util.List;

/**
 * json测试
 *
 * @author chendd
 * @date 2021/4/26 17:58
 */
public class JsonTest {

    @Test
    public void hello() {
        String source = "{\"result\":\"success\",\"message\":null,\"data\":[{\"id\":\"1283367430541922306\",\"tag\":\"JPA\",\"strong\":{\"text\":\"是\",\"value\":\"yes\"},\"sortOrder\":\"1\",\"counts\":2},{\"id\":\"1321448631789764609\",\"tag\":\"Jxls2.0\",\"strong\":{\"text\":\"是\",\"value\":\"yes\"},\"sortOrder\":\"30\",\"counts\":0}]}";
        BaseResult result = JSON.parseObject(source , new TypeReference<BaseResult<List<TagManageResult>>>(){});
        System.out.println(result);
    }

}
