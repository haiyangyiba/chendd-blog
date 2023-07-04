package cn.chendd.blog.web.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.article.tag.TagArticleDto;
import cn.chendd.blog.web.home.service.TagService;
import com.google.common.base.Charsets;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 标签查询
 * @author chendd
 * @date 2022/02/07 15:55
 */
@RequestMapping(value = "/blog/tag" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class TagController extends BaseController {

    @Resource
    private TagService tagService;

    @GetMapping("/{key}")
    public String tagArticle(@PathVariable String key) throws UnsupportedEncodingException {
        //encode两次，因为URLEncoder中的空格不会被编码为20%，而是+，所以编码两次传递，解码两次
        String encode = URLEncoder.encode(key , Charsets.UTF_8.name());
        encode = URLEncoder.encode(encode , Charsets.UTF_8.name());
        List<TagArticleDto> dataList = this.tagService.getTagArticles(encode);
        super.addAttribute("dataList" , dataList);
        return "/tag/tag";
    }

}
