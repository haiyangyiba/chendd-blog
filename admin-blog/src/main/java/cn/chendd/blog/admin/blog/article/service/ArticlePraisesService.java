package cn.chendd.blog.admin.blog.article.service;

import cn.chendd.blog.admin.blog.article.model.ArticlePraises;
import cn.chendd.blog.admin.blog.article.vo.RemindPrausesAndCommentResult;
import cn.chendd.blog.client.article.vo.ArticlePraisesResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 文章点赞Service接口实现
 * @author chendd
 * @date 2021/2/18 9:26
 */
public interface ArticlePraisesService extends IService<ArticlePraises> {

    /**
     * 保存点赞信息
     * @param praises 点赞对象
     * @return 数据对象ID
     */
    Long saveArticlePraises(ArticlePraises praises);

    /**
     * 查询点赞明细
     * @param articleId 文章ID
     * @return 文章点赞明细
     */
    List<ArticlePraisesResult> queryArticlePraisesResult(Long articleId);

    /**
     * 查询时间区间的点赞数据
     * @param begin 开始时间
     * @param end 结束时间
     * @return 列表数据
     */
    List<RemindPrausesAndCommentResult> queryRemindPraises(String begin , String end);
}
