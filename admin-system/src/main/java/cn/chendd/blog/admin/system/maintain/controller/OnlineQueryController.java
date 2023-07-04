package cn.chendd.blog.admin.system.maintain.controller;

import cn.chendd.blog.admin.system.maintain.service.OnlineQueryService;
import cn.chendd.blog.base.controller.BaseController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 运维管理-SQL在线查询
 *
 * @author chendd
 * @date 2022/2/24 9:51
 */
@Api(value = "在线查询管理" , tags = "SQL在线查询")
@ApiSort(90)
@RequestMapping(value = "/system/online" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class OnlineQueryController extends BaseController {

    @Resource
    private OnlineQueryService onlineQueryService;

    @GetMapping("/query")
    @ApiOperation(value = "在线查询页面",notes = "跳转至SQL在线查询页面")
    @ApiOperationSupport(order = 10)
    public String queryManager() {
        return "/system/online/onlineQueryManage";
    }

    @PostMapping("/query")
    @ApiOperation(value = "在线查询页面",notes = "跳转至SQL在线查询页面")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public Page<Map<String , Object>> executeQuery(@ApiParam(name = "editorContent") @RequestParam("editorContent") String sql) {
        Page<Map<String , Object>> result = this.onlineQueryService.executeQuery(sql);
        return result;
    }

}
