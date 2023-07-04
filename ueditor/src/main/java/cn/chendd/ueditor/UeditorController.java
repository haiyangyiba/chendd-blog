package cn.chendd.ueditor;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ueditor.ActionEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * ueditor控制器
 *
 * @author chendd
 * @date 2020/8/4 10:20
 */
@Api(value = "ueditor编辑器" , tags = "ueditor编辑器")
@ApiSort(10)
@RequestMapping(value = "/ueditor/controller" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class UeditorController {

    @RequestMapping
    @ResponseBody
    public void server(HttpServletRequest request , HttpServletResponse response) throws IOException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        System.out.println(JSONObject.toJSONString(request.getParameterMap()));
        System.out.println("path = " + path);
        try(PrintWriter out = response.getWriter()) {
            out.write(new ActionEnter(request, "/statics/plugins/ueditor/jsp/config.json" , "/app/BLOG_FILES").exec());
        }

    }

}
