package cn.chendd.blog.admin.blog.client.search;

import cn.chendd.ansj.Ansj;
import cn.chendd.ansj.enums.EnumAnalysis;
import cn.chendd.blog.admin.blog.search.service.SearchManageService;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.blog.client.search.SearchVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 关键字查询Controller
 *
 * @author chendd
 * @date 2022/2/17 22:22
 */
@Api(value = "搜索管理V1" , tags = "搜索管理V1")
@ApiSort(100)
@RequestMapping(value = "/search" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@ApiVersion("1")
public class SearchClientController extends BaseController {

    @Resource
    private SearchManageService searchManageService;

    @PostMapping("/{pageNumber}")
    @ApiOperation(value = "关键字查询" , notes = "根据关键字查询数据分页")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public Page<SearchVo> querySearchPage(@ApiParam("分页编号") @PathVariable Long pageNumber ,
                                          @ApiParam("关键字") @RequestParam String keyWords) {
        Result result = Ansj.newInstance().keyWords(EnumAnalysis.BASE, keyWords);
        List<Term> terms = result.getTerms();
        List<String> tagList = Lists.newArrayList();
        terms.forEach(item -> {
            String name = item.getName();
            if (StringUtils.isNotBlank(name)) {
                tagList.add(name);
            }
        });
        Page<SearchVo> pageFinder = this.searchManageService.querySearchPage(tagList, pageNumber);
        List<SearchVo> dataList = pageFinder.getRecords();
        dataList.forEach(item -> {
            //处理标题存在匹配的红色文本
            String title = this.reloadKeyWords(item.getTitle(), tagList , true);
            item.setTitle(title);
            //处理内容存在匹配的红色文本
            String content = this.reloadKeyWords(item.getSimpleContent() , tagList ,false);
            item.setSimpleContent(content);
        });
        return pageFinder;
    }

    /**
     * 重新渲染关键字为红色文本
     * @param text 关键字
     * @param textList 文本列表
     * @return 文本
     */
    private String reloadKeyWords(String text , List<String> textList , boolean filter) {
        if(filter){
            text = StringEscapeUtils.unescapeHtml4(text);
        }
        text = reloadXSS(text);
        for (int i = 0; i < textList.size(); i++) {
            String tag = textList.get(i);
            Matcher m = Pattern.compile(tag , Pattern.CASE_INSENSITIVE).matcher(text);
            while(m.find()){
                String group = m.group();
                String newValue = "[{" + i + "}]";
                //将匹配的文本替换为特殊匹配文本
                text = text.replace(group, newValue);
            }
        }
        //将特殊匹配文本转换为html段落
        for (int i = 0; i < textList.size(); i++) {
            String newValue = "[{" + i + "}]";
            text = text.replace(newValue , "<font color='red'>" + textList.get(i) + "</font>");
        }
        return text;
    }

    /**
     * 处理特殊标记的html标签
     * @param text 文本
     * @return 匹配后的文本
     */
    private String reloadXSS(String text){
        String regexs[] = {"(<)(style.*?)(>)" , "(<)(/style.*?)(>)" , "(<)(script.*?)(>)" , "(<)(/script.*?)(>)"};
        for (String regex : regexs) {
            Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(text);
            while(m.find()){
                String left = "&lt;";
                String center = m.group(2);
                String right = "&gt;";
                String all = m.group();
                String newText = left + center + right;
                text = text.replace(all, newText);
            }
        }
        return text.replace(">", "﹥").replace("<", "﹤");
    }

}
