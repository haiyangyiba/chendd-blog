package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.search.SearchVo;
import cn.chendd.blog.web.home.service.SearchService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 关键字查询Controller定义
 *
 * @author chendd
 * @date 2022/2/16 22:15
 */
@Api(value = "自定义内容页面加载Controller" , tags = "自定义内容页面")
@ApiSort(50)
@Controller
@RequestMapping("/blog/search")
public class SearchController extends BaseController {

    @Resource
    private SearchService searchService;

    /**
     * 关键字查询页面
     * @return 页面路径
     */
    @GetMapping
    public String searchManage() {

        return "/search/search";
    }

    /**
     * 关键字查询实现
     * @param keyWords 关键字
     * @return 查询结果页面
     */
    @PostMapping("/{pageNumber}")
    public String searchManage(@RequestParam String keyWords , @PathVariable Long pageNumber) {
        Page<SearchVo> pageFinder = this.searchService.querySearchPage(keyWords , pageNumber);
        super.addAttribute("keyWords" , keyWords).addAttribute("pageFinder" , pageFinder);
        return "/search/search";
    }

}
