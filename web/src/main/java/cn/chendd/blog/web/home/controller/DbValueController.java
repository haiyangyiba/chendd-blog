package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.web.home.client.DbValueClient;
import cn.chendd.core.result.BaseResult;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 前台参数值交互Controller
 *
 * @author chendd
 * @date 2021/2/9 21:51
 */
@Api(value = "文章管理" , tags = "文章管理")
@ApiSort(10)
@Controller
@ApiVersion("1")
@RequestMapping("/toolkit/dbvalue")
public class DbValueController extends BaseController {

    @Resource
    private DbValueClient dbValueClient;

    @GetMapping("/{group}/{key}")
    @ApiOperation(value = "根据group-key查询value值" , notes = "按group-key取值")
    @ApiOperationSupport(order = 80)
    @ResponseBody
    public BaseResult<String> getDbValueByKey(@ApiParam("group") @PathVariable String group , @ApiParam("key") @PathVariable String key) {
        return dbValueClient.getDbValueByKey(group , key);
    }

}
