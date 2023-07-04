package cn.chendd.blog.web.home.client;

import cn.chendd.blog.client.comment.vo.CommentQueryResult;
import cn.chendd.blog.client.search.SearchVo;
import cn.chendd.blog.web.home.po.CommentPutParam;
import cn.chendd.core.result.BaseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtflys.forest.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.utils.CharsetNames;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 评论管理接口调用
 * @author chendd
 * @date 2021/2/22 20:51
 */
@BaseRequest(baseURL = "${serverAdminPath}" , contentEncoding = CharsetNames.UTF_8 , contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public interface SearchClient {

    /**
     * 按数据位置查询评论数据列表
     * @param pageNumber 页码
     * @param keyWords 关键字
     * @return 数据列表
     */
    @Post(url = "/v1/search/${pageNumber}.html" , dataType = "json" , data = {
            "keyWords=${keyWords}"
        }
    )
    BaseResult<Page<SearchVo>> querySearchPage(@DataVariable("keyWords") String keyWords ,
                                               @DataVariable("pageNumber") Long pageNumber);

}