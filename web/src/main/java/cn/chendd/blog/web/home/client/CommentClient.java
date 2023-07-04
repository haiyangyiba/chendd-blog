package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.comment.vo.CommentQueryResult;
import cn.chendd.blog.web.home.po.CommentPutParam;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtflys.forest.annotation.*;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;

/**
 * 评论管理接口调用
 * @author chendd
 * @date 2021/2/22 20:51
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface CommentClient {

    /**
     * 按数据位置查询评论数据列表
     * @param targetId 目标文章ID
     * @param pageNumber 分页编号
     * @return 数据列表
     */
    @Get(url = "/v1/system/comment/${targetId}/${pageNumber}.html" , dataType = "json")
    BaseResult<Page<CommentQueryResult>> queryComment(@DataVariable("targetId") Long targetId, @DataVariable("pageNumber") Long pageNumber);

    /**
     * 保存评论数据
     * @param param 评论参数对象
     * @return 数据ID
     */
    @Put(url = "/v1/system/comment.html" , dataType = "json" , contentType = MediaType.APPLICATION_JSON_VALUE)
    BaseResult<Long> saveComment(@Body CommentPutParam param);
}
