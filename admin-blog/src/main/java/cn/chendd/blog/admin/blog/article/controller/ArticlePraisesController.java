package cn.chendd.blog.admin.blog.article.controller;

import cn.chendd.blog.base.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文章点赞Controller
 *
 * @author chendd
 * @date 2021/2/18 9:17
 */
@Api(value = "博客管理-文章管理" , tags = "博客管理-文章管理")
@ApiSort(40)
@RequestMapping(value = "/blog/article" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class ArticlePraisesController extends BaseController {


}
