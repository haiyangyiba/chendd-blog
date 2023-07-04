package cn.chendd.blog.admin.system.client.sysinfo;

import cn.chendd.blog.admin.system.info.model.SysInfoContent;
import cn.chendd.blog.admin.system.info.service.SysInfoContentService;
import cn.chendd.blog.admin.system.info.service.SystemInfoService;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.infocontent.SysInfoProjectResult;
import cn.chendd.blog.client.system.SystemInfoResult;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.assertj.core.util.Lists;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统信息管理
 *
 * @author chendd
 * @date 2020/11/14 11:21
 */
@Api(value = "系统内容管理" , tags = "系统内容管理")
@ApiSort(10)
@RequestMapping(value = "/system/" , produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@ApiVersion("1")
public class SysInfoClientController extends BaseController {

    @Resource
    private SysInfoContentService sysInfoContentService;
    @Resource
    private SystemInfoService systemInfoService;

    @GetMapping("/infocontent/introduce")
    @ApiOperation(value = "查询系统介绍",notes = "根据系统内容标识查询介绍数据")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public List<SysInfoContent> queryInfoContentIntroduceList() {
        List<SysInfoContent> dataList = sysInfoContentService.querySystemInfo("systemInfo_");
        return dataList;
    }

    @GetMapping("/infocontent/project")
    @ApiOperation(value = "查询系统精粹项目",notes = "根据系统精粹项目查询对应数据")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public List<SysInfoProjectResult> queryInfoProjects() {
        List<SysInfoContent> dataList = sysInfoContentService.querySystemProject("systemProject_");
        List<SysInfoProjectResult> jsonList = Lists.newArrayList();
        dataList.forEach(item -> {
            JSONObject jsonObject = item.getEditorContentJSONObject();
            if (jsonObject != null) {
                SysInfoProjectResult result = JSONObject.toJavaObject(jsonObject , SysInfoProjectResult.class);
                jsonList.add(result);
            }
        });
        return jsonList;
    }

    @GetMapping("/infocontent/project/{key}")
    @ApiOperation(value = "查询系统介绍",notes = "根据系统内容标识查询介绍数据")
    @ApiOperationSupport(order = 30)
    @ResponseBody
    public SysInfoProjectResult queryInfoContentIntroduceList(@ApiParam("项目标识") @PathVariable String key) {
        SysInfoProjectResult result = sysInfoContentService.queryInfoContentByKey(key);
        return result;
    }

    @GetMapping("/status")
    @ApiOperation(value = "查询系统信息",notes = "查询系统环境及硬件信息")
    @ApiOperationSupport(order = 40)
    @ResponseBody
    public SystemInfoResult querySystemInfo() {
        return systemInfoService.getSystemInfoResult();
    }

}
