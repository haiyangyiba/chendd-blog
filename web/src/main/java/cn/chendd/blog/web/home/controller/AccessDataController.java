package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.access.AccessDataBo;
import cn.chendd.blog.web.home.service.AccessDataService;
import cn.chendd.core.result.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 文章导出Controller
 *
 * @author chendd
 * @date 2022/4/2 14:53
 */
@Api(value = "访问量统计" , tags = "访问量统计")
@ApiSort(60)
@Controller
@RequestMapping("/blog/access")
public class AccessDataController extends BaseController {

    @Resource
    private AccessDataService accessDataService;

    /**
     * 访问量统计
     */
    @GetMapping
    @ApiOperation(value = "访问量统计" , notes = "昨日/今日访问量统计")
    @ApiOperationSupport(order = 10)
    @ResponseBody
    public BaseResult<AccessDataBo> getAccessData() {
        return this.accessDataService.getAccessData();
    }

}
