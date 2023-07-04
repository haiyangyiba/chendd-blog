package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.infocontent.SysInfoProjectResult;
import cn.chendd.blog.web.home.service.InfoContentService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 项目介绍
 * @author chendd
 * @date 2021/06/20 17:12
 */
@RequestMapping(value = "/blog/project" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class InfoContentController extends BaseController {

    @Resource
    private InfoContentService infoContentService;

    @GetMapping("/{key}")
    public String projectInfo(@PathVariable String key) {
        SysInfoProjectResult infoContent = this.infoContentService.getProjectInfo(key);
        super.addAttribute("article" , infoContent);
        return "/project/projectInfo";
    }

}
