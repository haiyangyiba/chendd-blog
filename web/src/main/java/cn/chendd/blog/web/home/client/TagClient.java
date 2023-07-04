package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.article.tag.TagArticleDto;
import cn.chendd.blog.web.home.client.vo.TagManageResult;
import cn.chendd.core.result.BaseResult;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.DataVariable;
import com.dtflys.forest.annotation.Get;
import org.springframework.http.MediaType;

import java.util.List;

/**
 * 测试接口方法
 *
 * @author chendd
 * @date 2021/4/26 13:06
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = "utf-8" , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface TagClient {

    /**
     * 获取所有数据接口
     * @return 数据集合
     */
    @Get(url = "/v1/blog/tag")
    BaseResult<List<TagManageResult>> getTagList();

    /**
     * 获取标签对应的文章数据
     * @param tag 标签名称
     * @return 数据列表
     */
    @Get(url = "/v1/blog/tag/${tag}.html")
    BaseResult<List<TagArticleDto>> getTagArticles(@DataVariable("tag") String tag);
}
