package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 标签查询
 * @author chendd
 * @date 2022/02/07 15:55
 */
@RequestMapping(value = "/blog/frame" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class FrameController extends BaseController {

    @GetMapping("/login")
    public String login() {
        return "/frame/401";
    }

}
