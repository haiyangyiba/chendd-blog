package cn.chendd.blog.admin.blog.article.mapper;

import cn.chendd.blog.admin.blog.article.model.ArticlePraises;
import cn.chendd.blog.admin.blog.article.vo.RemindPrausesAndCommentResult;
import cn.chendd.blog.client.article.vo.ArticlePraisesResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章点赞Mapper
 *
 * @author chendd
 * @date 2021/2/18 9:42
 */
public interface ArticlePraisesMapper extends BaseMapper<ArticlePraises> {

    /**
     * 查询点赞明细
     * @param articleId 文章ID
     * @return 文章点赞明细
     */
    List<ArticlePraisesResult> queryArticlePraisesResult(@Param("articleId") Long articleId);

    /**
     * 根据时间区间查询点赞数据
     * @param begin 开始时间
     * @param end 结束时间
     * @return 列表数据
     */
    List<RemindPrausesAndCommentResult> queryRemindPraises(@Param("begin") String begin, @Param("end") String end);
}
