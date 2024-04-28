package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.comment.vo.CommentQueryResult;
import cn.chendd.blog.web.home.po.CommentPutParam;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 评论管理Service接口
 * @author chendd
 * @date 2021/2/22 20:48
 */
public interface CommentService {

    /**
     * 查询评论列表前N条
     * @param targetId 目标文章ID
     * @param pageNumber 分页编号
     * @return 列表数据
     */
    BaseResult<Page<CommentQueryResult>> queryComment(Long targetId, Long pageNumber);

    /**
     * 保存评论数据对象
     * @param param 数据对象
     * @return 数据ID
     */
    Long saveComment(CommentPutParam param);

    /**
     * 保存评论数据对象
     * @param targetId 数据对象
     * @param userId 用户ID
     * @param ip ip地址
     * @return 数据ID
     */
    Long saveComment(Long targetId, Long userId, String ip);
}
