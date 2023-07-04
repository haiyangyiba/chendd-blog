package cn.chendd.blog.admin.system.info.service.impl;

import cn.chendd.ansj.Ansj;
import cn.chendd.ansj.enums.EnumAnalysis;
import cn.chendd.ansj.moduletag.service.ISysModuleTagsService;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.admin.system.info.mapper.SysInfoContentMapper;
import cn.chendd.blog.admin.system.info.model.SysInfoContent;
import cn.chendd.blog.admin.system.info.po.SysInfoContentManageParam;
import cn.chendd.blog.admin.system.info.service.SysInfoContentService;
import cn.chendd.blog.admin.system.info.vo.SysInfoContentManageResult;
import cn.chendd.blog.client.article.vo.ArticleCustomDto;
import cn.chendd.blog.client.infocontent.MaintenanceInfoVo;
import cn.chendd.blog.client.infocontent.SysInfoProjectResult;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.user.UserContext;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.core.utils.Http;
import cn.chendd.toolkit.operationlog.annotions.Log;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.apache.commons.text.StringEscapeUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 系统内容信息表Service接口实现
 * @author chendd
 * @date 2020/08/29 11:46
 */
@Service
@CacheConfig(cacheNames = CacheNameConstant.NOT_EXPIRED)
public class SysInfoContentServiceImpl extends ServiceImpl<SysInfoContentMapper , SysInfoContent> implements SysInfoContentService {

    /**
     * 系统内容模块名称
     */
    private static final String SYS_INFO_CONTENT = "sysInfoContent";

    @Resource
    private SysInfoContentMapper sysInfoContentMapper;
    @Resource
    private ISysModuleTagsService sysModuleTagsService;

    @Override
    @Log(description = "内容管理-列表查询")
    public List<SysInfoContentManageResult> queryInfoContentList(SysInfoContentManageParam param) {
        return this.sysInfoContentMapper.queryInfoContentList(param);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "'querySystemInfo'" , allEntries = false , beforeInvocation = true),
            @CacheEvict(key = "'querySystemProject'" , allEntries = false , beforeInvocation = true),
            @CacheEvict(key = "'queryCustomPage_' + #sysInfoContent.customPageName" , allEntries = false , beforeInvocation = true),
            @CacheEvict(key = "'systemProject_' + #sysInfoContent.customPageName" , allEntries = false , beforeInvocation = true)
    })
    @Log(description = "'内容管理-' + (#sysInfoContent.id == null ? '新增' : '修改')")
    public Integer saveSysInfoContent(SysInfoContent sysInfoContent) {
        Long id = sysInfoContent.getId();
        SysUserResult userResult = UserContext.getCurrentUser(SysUserResult.class);
        Integer result;
        if(id == null) {
            sysInfoContent.setCreateUser(userResult.getAccount().getUsername());
            sysInfoContent.setCreateTime(DateFormat.formatDatetime());
            result = this.sysInfoContentMapper.insert(sysInfoContent);
        } else {
            SysInfoContent dbInfo = super.getById(id);
            BeanUtils.copyProperties(sysInfoContent , dbInfo , "createUser" , "createTime" , "updateUser" , "updateTime");
            dbInfo.setUpdateUser(userResult.getAccount().getUsername());
            dbInfo.setUpdateTime(DateFormat.formatDatetime());
            result = this.sysInfoContentMapper.updateById(dbInfo);
        }
        //分词存储
        String content = Http.getHtmlInnerText(sysInfoContent.getEditorContent());
        Result list = Ansj.newInstance().keyWords(EnumAnalysis.DIC, content);
        List<Term> tagList = list.getTerms().stream().filter(item -> item.getName().length() > 1).collect(Collectors.toList());
        List<String> dataList = Lists.newArrayList();
        tagList.forEach(item -> dataList.add(item.getName()));
        this.sysModuleTagsService.saveModuleTags(sysInfoContent.getId() , SYS_INFO_CONTENT , dataList);
        return result;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "'querySystemInfo'" , allEntries = false , beforeInvocation = true),
            @CacheEvict(key = "'querySystemProject'" , allEntries = false , beforeInvocation = true)/*,
            @CacheEvict(key = "'queryCustomPage_' + #sysInfoContent.customPageName" , allEntries = false , beforeInvocation = true),
            @CacheEvict(key = "'systemProject_' + #sysInfoContent.customPageName" , allEntries = false , beforeInvocation = true)*/
    })
    @Log(description = "内容管理-数据删除")
    public Integer removeInfoContent(Long id) {
        return this.sysInfoContentMapper.deleteById(id);
    }

    @Override
    @Log(description = "内容管理-明细数据查看")
    public SysInfoContent viewSysInfoContent(Long id) {
        return this.getById(id);
    }

    @Override
    @Cacheable(key = "'querySystemInfo'")
    public List<SysInfoContent> querySystemInfo(String key) {
        return this.queryInfoContent(key);
    }

    @Override
    @Cacheable(key = "'querySystemProject'")
    public List<SysInfoContent> querySystemProject(String key) {
        return this.queryInfoContent(key);
    }

    @Override
    @Cacheable(key = "'systemProject_' + #key")
    public SysInfoProjectResult queryInfoContentByKey(String key) {
        //根据项目内容信息查询关联的文章数据
        QueryWrapper<SysInfoContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("`key`" , "systemProject_" + key);
        SysInfoContent sysInfoContent = this.sysInfoContentMapper.selectOne(queryWrapper);
        if (sysInfoContent != null) {
            JSONObject jsonObject = sysInfoContent.getEditorContentJSONObject();
            if (jsonObject != null) {
                SysInfoProjectResult result = JSONObject.toJavaObject(jsonObject , SysInfoProjectResult.class);
                Integer visitsNumber = this.sysInfoContentMapper.queryArticleVisitsNumber(result.getId());
                result.setVisitsNumber(visitsNumber);
                return result;
            }
        }
        return null;
    }

    @Override
    @Cacheable(key = "'queryCustomPage_' + #page")
    public Map<String , Object> queryCustomPage(String page) {
        QueryWrapper<SysInfoContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("`key`" , "customPage_" + page);
        SysInfoContent sysInfo = this.sysInfoContentMapper.selectOne(queryWrapper);
        if (sysInfo != null) {
            String content = sysInfo.getEditorContent();
            Map<String , Object> result = Maps.newHashMap();
            Matcher matcher = Pattern.compile("<pre\\s*[^>]*>([\\s\\S]*)</pre>", Pattern.CASE_INSENSITIVE).matcher(content);
            if (matcher.find()) {
                String code = matcher.group(1);
                String html = StringEscapeUtils.unescapeHtml4(code).replace("\u00a0" , " ");
                result.put("content" , html);
                Matcher propertyMatcher = Pattern.compile("data-property='([\\s\\S]*)'>" , Pattern.CASE_INSENSITIVE).matcher(html);
                if (propertyMatcher.find()) {
                    //\u00a0是ueditor中非常倔强的一种空白字符，参见：“一个字符串空格替换的问题”文章
                    String property = propertyMatcher.group(1);
                    JSONObject json = JSONObject.parseObject(property);
                    for (String key : json.keySet()) {
                        result.put(key , json.get(key));
                    }
                    String articleId = json.getString("articleId");
                    ArticleCustomDto customDto = this.sysInfoContentMapper.queryArticleCustom(Long.parseLong(articleId));
                    result.put("article" , customDto);
                }
            }
            return result;
        }
        return null;
    }

    @Override
    @Cacheable(key = "'queryArticleForParise'" , cacheNames = CacheNameConstant.AUTO_EXPIRED_1_DAY)
    public List<MaintenanceInfoVo.ArticleRecord> queryArticleForParise() {
        return this.sysInfoContentMapper.queryArticleForParise();
    }

    @Override
    @Cacheable(key = "'queryArticleForComment'" , cacheNames = CacheNameConstant.AUTO_EXPIRED_1_DAY)
    public List<MaintenanceInfoVo.ArticleRecord> queryArticleForComment() {
        return this.sysInfoContentMapper.queryArticleForComment();
    }

    /**
     * 根据关键字前缀查询数据列表
     * @param key 关键字
     * @return 数据列表
     */
    private List<SysInfoContent> queryInfoContent(String key) {
        QueryWrapper<SysInfoContent> queryWrapper = new QueryWrapper<SysInfoContent>().likeRight("`key`", key).orderByAsc("sortOrder");
        return this.sysInfoContentMapper.selectList(queryWrapper);
    }

}
