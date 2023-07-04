package cn.chendd.blog.admin.system.pool.controller;

import cn.chendd.blog.admin.system.pool.bo.DataSourceBo;
import cn.chendd.blog.admin.system.pool.service.DataSourceService;
import cn.chendd.blog.base.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 连接池Controller管理
 *
 * @author chendd
 * @date 2021/11/20 20:50
 */
@Api(value = "连接池管理" , tags = "连接池管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(100)
@RequestMapping(value = "/system/datasource")
@Controller
public class DataSourceController extends BaseController {

    @Resource
    private DataSourceService dataSourceService;

    @GetMapping
    @ApiOperation(value = "数据库管理页面" , notes = "数据库管理页面")
    @ApiOperationSupport(order = 10)
    public String dataSourceManage() {
        DataSourceBo dataSourceConfig = this.dataSourceService.getDataSourceConfig();
        super.addAttribute("item" , dataSourceConfig);
        return "/system/datasource/dataSourceManage";
    }

    @PostMapping
    @ApiOperation(value = "数据库管理查询" , notes = "数据库管理查询")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public DataSourceBo getDataSourceConfig() {
        return this.dataSourceService.getDataSourceConfig();
    }

}
