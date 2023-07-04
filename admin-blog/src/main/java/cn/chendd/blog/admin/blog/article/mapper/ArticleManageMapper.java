package cn.chendd.blog.admin.blog.article.mapper;

import cn.chendd.blog.admin.blog.article.model.Article;
import cn.chendd.blog.admin.blog.article.po.ArticleManageParam;
import cn.chendd.blog.admin.blog.article.vo.ArticleContentResult;
import cn.chendd.blog.admin.blog.article.vo.ArticleManageResult;
import cn.chendd.blog.admin.blog.article.vo.ArticleResult;
import cn.chendd.blog.admin.blog.article.vo.ArticleTagResult;
import cn.chendd.blog.client.article.vo.ArticleCustomDto;
import cn.chendd.blog.client.search.SearchVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 博客管理-文章管理Mapper接口定义
 * @author chendd
 * @date 2020/07/31 21:21
 */
public interface ArticleManageMapper extends BaseMapper<Article> {

    /**
     * 查询文章管理分页
     * @param page 分页参数
     * @param param 查询条件
     * @return 分页数据
     */
    Page<ArticleManageResult> queryArticleManagePage(Page<ArticleManageResult> page, @Param("param") ArticleManageParam param);

    /**
     * 查询文章类型分页
     * @param page 分页参数
     * @param articleTypeId 文章类型ID
     * @return 分页数据
     */
    Page<ArticleContentResult> queryArticleTypePage(Page<ArticleContentResult> page, @Param("articleTypeId") Long articleTypeId);

    /**
     * 根据ID查询一条文章数据
     * @param id 文章ID
     * @return 文章主体数据
     */
    ArticleResult getArticleById(Long id);

    /**
     * 根据ID 更新最后更新时间
     * @param article 文章对象
     */
    void updateLastState(Article article);

    /**
     * 按页码查询最新的文章数据信息
     * @param begin 开始位置
     * @param end 结束位置
     * @return 文章列表
     */
    List<ArticleContentResult> queryArticleContents(@Param("begin") Integer begin , @Param("end") Integer end);

    /**
     * 根据文章ID查询文章相应的标签列表
     * @param id 文章ID
     * @return 标签列表
     */
    List<ArticleTagResult> queryArticleTags(@Param("id") Long id);

    /**
     * 根据文章ID修改文章访问次数
     * @param articleId 文章ID
     */
    void updateVisitsNumber(@Param("articleId") Long articleId);

    /**
     * 查询文章类型置顶数据
     * @param articleTypeId 文章分类ID
     * @return 列表数据
     */
    List<ArticleContentResult> queryArticleTypeTop(@Param("articleTypeId") Long articleTypeId);

    /**
     * 关键字搜索分页
     * @param tagList 关键字组
     * @param page 分页参数
     * @return 列表数据
     */
    Page<SearchVo> querySearchPage(@Param("tagList") List<String> tagList, Page<SearchVo> page);

}
