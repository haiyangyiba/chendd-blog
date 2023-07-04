package cn.chendd.blog.admin.system.client.sysinfo;

import cn.chendd.blog.admin.system.info.service.SysInfoContentService;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 自定义页面Controller定义
 *
 * @author chendd
 * @date 2022/2/11 22:43
 */
@Api(value = "系统内容管理" , tags = "系统内容管理")
@ApiSort(10)
@RequestMapping(value = "/system/page" , produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@ApiVersion("1")
public class CustomPageClientController extends BaseController {

    @Resource
    private SysInfoContentService sysInfoContentService;

    @GetMapping("/{page}")
    @ApiOperation(value = "查询页面标识",notes = "根据页面标识查询自定义页面内容")
    @ApiOperationSupport(order = 10)
    public Map<String , Object> getCustomPage(@ApiParam("页面标识") @PathVariable String page) {
        return sysInfoContentService.queryCustomPage(page);
    }

}
