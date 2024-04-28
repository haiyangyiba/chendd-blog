package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.client.comment.vo.CommentQueryResult;
import cn.chendd.blog.web.home.client.CommentClient;
import cn.chendd.blog.web.home.po.CommentPutParam;
import cn.chendd.blog.web.home.service.CommentService;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 评论管理Service接口实现
 *
 * @author chendd
 * @date 2021/2/22 20:49
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentClient commentClient;

    @Override
    public BaseResult<Page<CommentQueryResult>> queryComment(Long targetId, Long pageNumber) {
        return commentClient.queryComment(targetId , pageNumber);
    }

    @Override
    public Long saveComment(CommentPutParam param) {
        return commentClient.saveComment(param).getData();
    }

    @Override
    public Long saveComment(Long targetId, Long userId, String ip) {
        CommentPutParam param = new CommentPutParam();
        param.setCreateUserId(userId);
        param.setTargetId(targetId);
        param.setIp(ip);
        param.setPageNumber(1L);
        param.setModuleKey("Article");
        param.setEditorContent("<p>下载了文章附件（<b><i>该留言为系统自动生成</i></b>）</p>");
        return commentClient.saveComment(param).getData();
    }
}
