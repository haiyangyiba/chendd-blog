package cn.chendd.blog.admin.blog.article.service;

import cn.chendd.blog.admin.blog.article.model.Article;
import cn.chendd.blog.admin.blog.article.po.ArticleManageParam;
import cn.chendd.blog.admin.blog.article.vo.ArticleContentResult;
import cn.chendd.blog.admin.blog.article.vo.ArticleManageResult;
import cn.chendd.blog.admin.blog.article.vo.ArticleResult;
import cn.chendd.blog.admin.blog.article.vo.ArticleTagResult;
import cn.chendd.blog.client.article.vo.ArticleCustomDto;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 博客管理-文章管理Service接口定义
 * @auth chendd
 * @date 2020/07/31 21:21
 */
public interface ArticleManageService extends IService<Article> {


    /**
     * 文章管理分页查询
     * @param param 查询条件
     * @return 分页数据
     */
    Page<ArticleManageResult> queryArticleManagePage(ArticleManageParam param);

    /**
     * 保存文章
     * @param article 文章对象
     * @param user 当前用户
     * @return 操作结果
     */
    Article saveArticle(Article article, SysUserResult user);

    /**
     *
     * @param id 文章ID
     * @return 操作结果
     */
    BaseResult removeArticle(Long id);

    /**
     * 根据文章ID查询对应数据
     * @param id 文章ID
     * @return 文章对象
     */
    ArticleResult getArticle(Long id);

    /**
     * 更新最后更改时间
     * @param article 文章对象
     */
    void updateLastState(Article article);

    /**
     * 按页码查询最新的文章数据信息
     * @param pageNumber 页码
     * @return 文章列表
     */
    List<ArticleContentResult> queryArticleLimit(Integer pageNumber);

    /**
     * 根据文章ID查询该文章对应的标签数据集合
     * @param id 文章ID
     * @return 标签集合
     */
    List<ArticleTagResult> queryArticleTags(Long id);

    /**
     * 根据文章ID查询访问次数，并次数+1
     * @param articleId 文章ID
     * @return 访问次数
     */
    Integer getArticleVisitsNumber(Long articleId);

    /**
     * 根据文章ID查询访问次数，并次数+1
     * @param articleId 文章ID
     * @return 访问次数
     */
    Integer queryAndModifArticleVisitsNumber(Long articleId);

    /**
     * 根据文章类型查询分页
     * @param articleType 文章类型ID
     * @param pageNumber 页码
     * @param pageSize 每页显示大小
     * @return 分页数据
     */
    Page<ArticleContentResult> queryArticleTypePage(Long articleType, Long pageNumber, Long pageSize);
}
