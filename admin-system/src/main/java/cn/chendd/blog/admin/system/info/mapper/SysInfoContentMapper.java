package cn.chendd.blog.admin.system.info.mapper;

import cn.chendd.blog.admin.system.info.po.SysInfoContentManageParam;
import cn.chendd.blog.admin.system.info.vo.SysInfoContentManageResult;
import cn.chendd.blog.client.article.vo.ArticleCustomDto;
import cn.chendd.blog.client.infocontent.MaintenanceInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.chendd.blog.admin.system.info.model.SysInfoContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统内容信息表Mapper接口定义
 *
 * @author chendd
 * @date 2020/08/29 11:46
 */
public interface SysInfoContentMapper extends BaseMapper<SysInfoContent> {

    /**
     * 查询列表数据
     *
     * @param param 查询条件
     * @return 列表数据
     */
    List<SysInfoContentManageResult> queryInfoContentList(@Param("param") SysInfoContentManageParam param);

    /**
     * 根据项目key获取项目描述信息
     *
     * @param articleId 文章ID
     * @return 数据
     */
    Integer queryArticleVisitsNumber(@Param("articleId") String articleId);

    /**
     * 查询文章自定义内容
     *
     * @param articleId 文章id
     * @return 文章数据对象
     */
    ArticleCustomDto queryArticleCustom(@Param("articleId") Long articleId);

    /**
     * 获取点赞最多的前N篇文章
     * @return 文章列表
     */
    List<MaintenanceInfoVo.ArticleRecord> queryArticleForParise();

    /**
     * 获取评论最多的前N篇文章
     * @return 文章列表
     */
    List<MaintenanceInfoVo.ArticleRecord> queryArticleForComment();
}
