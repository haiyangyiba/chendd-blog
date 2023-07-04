package cn.chendd.blog.admin.blog.comment.mapper;

import cn.chendd.blog.admin.blog.article.vo.RemindPrausesAndCommentResult;
import cn.chendd.blog.admin.blog.article.vo.RemindUserCommentResult;
import cn.chendd.blog.admin.blog.comment.model.SysComment;
import cn.chendd.blog.admin.blog.comment.po.SysCommentManageParam;
import cn.chendd.blog.admin.blog.comment.vo.SysCommentManageResult;
import cn.chendd.blog.client.comment.vo.CommentNewestDto;
import cn.chendd.blog.client.comment.vo.CommentQueryResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 留言评论表Mapper接口定义
 * @author chendd
 * @date 2021/02/19 20:46
 */
public interface SysCommentMapper extends BaseMapper<SysComment> {

    /**
     * 根据目标（文章）ID和当前标记位ID查询最前面N层主回复数据
     * @param page 分页信息
     * @param targetId 目标ID
     * @return 最新N条数据ID
     */
    Page<CommentQueryResult> queryPageSysComment(Page page, @Param("targetId") Long targetId);

    /**
     * 根据文章ID和所有楼层数据获取所有楼层数据的全部子回复数据
     * @param targetId 目标（文章）ID
     * @param commentList 楼层数据集合
     * @return 全部子回复数据
     */
    List<CommentQueryResult> querySysComment(@Param("targetId") Long targetId, @Param("commentList")  List<CommentQueryResult> commentList);

    /**
     * 查询分页数据
     * @param page 分页参数
     * @param param 查询条件
     * @return 分页数据
     */
    Page<SysCommentManageResult> querySysCommentManagePage(Page page, @Param("param") SysCommentManageParam param);

    /**
     * 修改数据的有效性状态
     * @param id 主键ID
     * @param dataStatus 数据状态
     * @return 修改结果
     */
    int updateDataStatusById(@Param("id") Long id , @Param("dataStatus") String dataStatus);

    /**
     * 根据ID查询系统评论数据
     * @param id 主键ID
     * @return 查询留言数据
     */
    SysComment querySysCommentById(@Param("id") Long id);

    /**
     * 根据ID查询评论数据总记录数（含已删除数据）
     * @param id 主键ID
     * @return 评论总记录条数
     */
    Integer querySysCountById(@Param("targetId") Long id);

    /**
     * 查询最新的limit条留言评论数据
     * @param limit 提取条数
     * @return 集合数据
     */
    List<CommentNewestDto> queryCommentNewestList(@Param("limit") int limit);

    /**
     * 根据日期区间查询评论数据
     * @param begin 开始时间
     * @param end 结束时间
     * @return 列表数据
     */
    List<RemindPrausesAndCommentResult> queryRemindComment(@Param("begin") String begin, @Param("end") String end);

    /**
     * 根据日期区间查询待提醒的评论数据至用户
     * @param begin 开始时间
     * @param end 结束时间
     * @return 列表数据
     */
    List<RemindUserCommentResult> queryRemindUserComment(@Param("begin") String begin, @Param("end") String end);
}
