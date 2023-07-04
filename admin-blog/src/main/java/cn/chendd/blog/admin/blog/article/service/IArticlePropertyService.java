package cn.chendd.blog.admin.blog.article.service;

import cn.chendd.blog.admin.blog.article.enums.EnumArticleProperty;
import cn.chendd.blog.admin.blog.article.model.ArticleProperty;
import cn.chendd.blog.admin.blog.article.vo.ArticlePropertyResult;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文章状态Service接口定义
 *
 * @author chendd
 * @date 2020/8/2 22:20
 */
public interface IArticlePropertyService extends IService<ArticleProperty> {

    /**
     * 查询一组文章状态信息
     * @param articleIds 文章ID
     * @return 文章数据
     */
    List<ArticlePropertyResult> getArticleStateById(List<Long> articleIds);

    /**
     * 查询一条文章状态信息
     * @param articleId 文章ID
     * @return 文章数据
     */
    ArticlePropertyResult getArticleStateById(Long articleId);

    /**
     * 文章发布状态处理
     * @param property 操作类型
     * @param articleId 文章ID
     * @param name 文章属性值
     * @param yesText 成功提示信息
     * @param noText 其它提示信息
     * @return 操作处理结果
     */
    BaseResult changeProperty(EnumArticleProperty property, Long articleId , String name , String yesText , String noText);

    /**
     * 处理文章封面图片
     * @param articleId 文章ID
     * @param file 封面图片
     * @param basePath 根路径
     */
    void uploadArticleCover(Long articleId, MultipartFile file , String basePath);

    /**
     * 删除封面图片
     * @param articleId 文章ID
     */
    void removeArticleCover(Long articleId);
}
