package cn.chendd.blog.admin.system.cache.controller;

import cn.chendd.blog.admin.system.cache.dto.CacheKeyDto;
import cn.chendd.blog.admin.system.cache.dto.CacheManageDto;
import cn.chendd.blog.admin.system.cache.po.CacheManageParam;
import cn.chendd.blog.admin.system.cache.service.CacheManageService;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.NoneResult;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 缓存管理
 *
 * @author chendd
 * @date 2021/11/13 13:38
 */
@Api(value = "缓存管理" , tags = "缓存管理" , produces = MediaType.APPLICATION_JSON_VALUE)
@ApiSort(80)
@RequestMapping(value = "/system/cache")
@Controller
public class CacheManageController extends BaseController {

    @Resource
    private CacheManageService cacheManageService;

    @GetMapping
    @ApiOperation(value = "缓存管理" , notes = "缓存管理页面")
    @ApiOperationSupport(order = 10)
    public String cacheManage() {

        return "/system/cache/cacheManage";
    }

    @PostMapping
    @ApiOperation(value = "缓存管理查询" , notes = "缓存列表查询")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public List<CacheManageDto> queryCachesList(CacheManageParam param) {

        List<CacheManageDto> dataList = this.cacheManageService.queryCachesList(param);
        return dataList;
    }

    @GetMapping("/{cacheName}")
    @ApiOperation(value = "缓存管理查询" , notes = "缓存列表查询")
    @ApiOperationSupport(order = 30)
    @ResponseBody
    public List<CacheKeyDto> queryCacheByName(@ApiParam("缓存name") @PathVariable String cacheName) {
        return this.cacheManageService.queryCacheByName(cacheName);
    }

    @GetMapping("/{cacheName}/{keyName}")
    @ApiOperation(value = "缓存管理查询" , notes = "缓存列表查询")
    @ApiOperationSupport(order = 40)
    @ResponseBody
    public BaseResult removeCacheElement(@ApiParam("缓存name") @PathVariable String cacheName ,
                                         @ApiParam("缓存key") @PathVariable String keyName) {
        return cacheManageService.removeCacheElement(cacheName , keyName);
    }

    @DeleteMapping("/all")
    @ApiOperation(value = "缓存删除-全部" , notes = "删除全部缓存数据")
    @ApiOperationSupport(order = 50)
    @ResponseBody
    public BaseResult removeAllCache() {
        return cacheManageService.removeAllCache();
    }

    @DeleteMapping("/select/{cacheName}")
    @ApiOperation(value = "缓存删除-选中" , notes = "删除选中缓存数据")
    @ApiOperationSupport(order = 60)
    @ResponseBody
    public BaseResult removeSelectedCache(@ApiParam("缓存name") @PathVariable String cacheName) {
        return cacheManageService.removeSelectedCache(cacheName);
    }

    @DeleteMapping("/article_view")
    @ApiOperation(value = "缓存删除-特定功能" , notes = "删除缓存文章数据")
    @ApiOperationSupport(order = 70)
    @ResponseBody
    public BaseResult removeArticleViewCache() {
        this.cacheManageService.removeCacheElementStartWith(CacheNameConstant.NOT_EXPIRED , "viewArticleById_");
        return new NoneResult("缓存清除成功！");
    }

}
