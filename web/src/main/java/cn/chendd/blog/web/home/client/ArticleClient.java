package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.article.po.ArticlePraisesParam;
import cn.chendd.blog.client.article.vo.ArticlePraisesResult;
import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;
import cn.chendd.blog.web.home.client.vo.ArticleContentResult;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtflys.forest.annotation.*;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

import java.util.List;

/**
 * 博客文章相关接口
 *
 * @author chendd
 * @date 2020/11/14 22:30
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface ArticleClient {

    /**
     * 根据博客文章id查询文章数据
     * @param articleId 文章id
     * @return 文章数据
     */
    @Get(url = "/v1/blog/article/${articleId}.html")
    BaseResult<ArticleSingleContentResult> getArticleContent(@DataVariable("articleId") Long articleId);

    /**
     * 根据博客文章id查询文章数据
     * @param articleId 文章id
     * @return 文章数据
     */
    @Get(url = "/v1/blog/article/visits/${articleId}.html")
    BaseResult<Integer> getArticleVisitsNumber(@DataVariable("articleId") Long articleId);

    /**
     * 根据文章id和点赞类型保存点赞数据
     * @param param 文章点赞数据
     * @return 点赞数据
     */
    @Put(url = "/v1/blog/article/praises.html" , contentType = MediaType.APPLICATION_JSON_VALUE)
    BaseResult<Long> saveArticlePraises(@Body ArticlePraisesParam param);

    /**
     * 根据文章id查询所有点赞
     * @param articleId 文章id
     * @return 用户点赞数据详细列表
     */
    @Get(url = "/v1/blog/article/praises/${articleId}.html" , dataType = "json" , logEnabled = true)
    BaseResult<List<ArticlePraisesResult>> queryPraises(@DataVariable("articleId") Long articleId);

    /**
     * 根据文章分类查询分页数据
     * @param articleType 文章分类
     * @param pageNumber 页码
     * @param pageSize 每页显示大小
     * @return 分页数据
     */
    @Post(url = "/v1/blog/article/type/${articleType}.html" , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    BaseResult<Page<ArticleContentResult>> queryArticleTypePage(@DataVariable("articleType") Long articleType,
                                                                @Query("pageNumber") Long pageNumber,
                                                                @Query("pageSize") Long pageSize);
}
