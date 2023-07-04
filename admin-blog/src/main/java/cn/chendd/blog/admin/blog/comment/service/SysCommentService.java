package cn.chendd.blog.admin.blog.comment.service;

import cn.chendd.blog.admin.blog.article.vo.RemindPrausesAndCommentResult;
import cn.chendd.blog.admin.blog.article.vo.RemindUserCommentResult;
import cn.chendd.blog.admin.blog.comment.model.SysComment;
import cn.chendd.blog.admin.blog.comment.po.SysCommentManageParam;
import cn.chendd.blog.admin.blog.comment.po.SysCommentParam;
import cn.chendd.blog.admin.blog.comment.vo.SysCommentManageResult;
import cn.chendd.blog.client.comment.vo.CommentNewestDto;
import cn.chendd.blog.client.comment.vo.CommentQueryResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 留言评论表Service接口定义
 * @author chendd
 * @date 2021/02/19 20:46
 */
public interface SysCommentService extends IService<SysComment> {

    /**
     * 保存系统评论数据
     * @param param 评论数据
     * @return 数据ID
     */
    Long saveSysComment(SysCommentParam param);

    /**
     * 保存系统子级评论的数据
     * @param param 评论数据
     * @return 数据ID
     */
    Long saveSysCommentForChild(SysCommentParam param);

    /**
     * 根据目标（文章）ID和当前标记位ID查询最前面N层主回复数据
     * @param targetId 目标ID
     * @param pageNumber 分页编号
     * @return 最新N条数据ID
     */
    Page<CommentQueryResult> querySysComment(Long targetId , Long pageNumber);

    /**
     * 根据条件查询系统评论分页列表
     * @param param 查询条件
     * @return 分页数据
     */
    Page<SysCommentManageResult> querySysCommentManagePage(SysCommentManageParam param);

    /**
     * 根据数据ID查询明细数据
     * @param id 数据ID
     * @return 明细数据
     */
    SysComment getCommentById(Long id);

    /**
     * 根据数据ID删除数据或撤销删除数据
     * @param id 数据ID
     * @return true ? 删除成功 : 删除失败
     */
    Boolean removeCommentById(Long id);

    /**
     * 根据ID查询一共存在多条条评论数据
     * @param id 数据ID
     * @return 评论个数（含已删除数据）
     */
    Integer querySysCountById(Long id);

    /**
     * 查询最新的limit条留言评论数据
     * @param limit 提取条数
     * @return 集合数据
     */
    List<CommentNewestDto> queryCommentNewestList(int limit);

    /**
     * 查询时间段内的评论列表
     * @param begin 开始时间
     * @param end 结束时间
     * @return 数据列表
     */
    List<RemindPrausesAndCommentResult> queryRemindComment(String begin, String end);

    /**
     * 查询时间段内的评论列表
     * @param begin 开始时间
     * @param end 结束时间
     * @return 数据列表
     */
    List<RemindUserCommentResult> queryRemindUserComment(String begin, String end);
}
