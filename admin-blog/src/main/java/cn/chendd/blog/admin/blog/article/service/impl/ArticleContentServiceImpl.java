package cn.chendd.blog.admin.blog.article.service.impl;

import cn.chendd.ansj.Ansj;
import cn.chendd.ansj.enums.EnumAnalysis;
import cn.chendd.ansj.moduletag.service.ISysModuleTagsService;
import cn.chendd.ansj.moduletag.vo.ModuleResult;
import cn.chendd.blog.admin.blog.article.enums.EnumArticleComment;
import cn.chendd.blog.admin.blog.article.mapper.ArticleContentMapper;
import cn.chendd.blog.admin.blog.article.model.Article;
import cn.chendd.blog.admin.blog.article.model.ArticleContent;
import cn.chendd.blog.admin.blog.article.service.ArticleManageService;
import cn.chendd.blog.admin.blog.article.service.IArticleContentService;
import cn.chendd.blog.admin.blog.article.service.IArticlePropertyService;
import cn.chendd.blog.admin.blog.article.vo.ArticlePropertyResult;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.client.article.vo.ArticleSingleContentResult;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import cn.chendd.ueditor.UeditorCodeConvert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章内容Service接口实现
 *
 * @author chendd
 * @date 2020/8/2 22:09
 */
@Service
public class ArticleContentServiceImpl extends ServiceImpl<ArticleContentMapper, ArticleContent> implements IArticleContentService {

    protected static final String MODULE_NAME = "article";

    @Resource
    private ArticleManageService articleManageService;
    @Resource
    private ISysModuleTagsService sysModuleTagsService;
    @Resource
    private ArticleContentMapper articleContentMapper;
    @Resource
    private IArticlePropertyService articlePropertyService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    @Log(description = "保存文章内容数据" , scope = LogScopeEnum.ALL)
    @Caching(evict = {
        @CacheEvict(key = "'viewArticleById_' + #content.articleId" , allEntries = false , beforeInvocation = true , cacheNames = CacheNameConstant.NOT_EXPIRED)
    })
    public BaseResult saveArticleContent(ArticleContent content) {
        //根据文章内容对象获得文章信息
        Article article = articleManageService.getById(content.getArticleId());
        //更新文章内容
        super.saveOrUpdate(content);
        //保存分词结果
        this.saveArticleTags(content , article.getTitle());
        //更新最后状态
        articleManageService.updateLastState(article);
        return new SuccessResult<>("文章内容保存成功！");
    }

    @Override
    @Cacheable(key = "'viewArticleById_' + #articleId" , cacheNames = CacheNameConstant.NOT_EXPIRED)
    public ArticleSingleContentResult getArticleContent(Long articleId) {
        //文章主体信息
        ArticleSingleContentResult article = articleContentMapper.getArticleContent(articleId);
        //文章tag
        article.setArticleTags(articleContentMapper.getArticleTags(articleId));
        //上一篇和下一篇
        List<ArticleSingleContentResult.ArticlePosition> articlePositionList = articleContentMapper.getArticlePosition(article.getSortOrder() , article.getType());
        article.setArticlePosition(ArticleSingleContentResult.ArticlePosition.convertList2Entity(articlePositionList));
        //文章默认十个关键字提取
        List<ModuleResult> moduleResultList = sysModuleTagsService.getModuleCountFor10(MODULE_NAME , articleId);
        List<ArticleSingleContentResult.ArticleKeywords> articleKeywordsList = Lists.newArrayList();
        moduleResultList.forEach(item -> {
            ArticleSingleContentResult.ArticleKeywords keywords = new ArticleSingleContentResult.ArticleKeywords();
            BeanUtils.copyProperties(item , keywords);
            articleKeywordsList.add(keywords);
        });
        article.setArticleKeywords(articleKeywordsList);
        //感兴趣的文章列表
        List<ArticleSingleContentResult.ArticleDependent> articleDependents = articleContentMapper.getArticleDependent(articleId);
        article.setArticleDependents(articleDependents);
        //文章属性功能加载
        ArticlePropertyResult articleProperty = articlePropertyService.getArticleStateById(articleId);
        //设置文章属性--评论
        if (articleProperty != null) {
            EnumArticleComment comment = articleProperty.getComment();
            if (comment != null) {
                ArticleSingleContentResult.ArticleProperty articlePropertyResult = new ArticleSingleContentResult.ArticleProperty();
                articlePropertyResult.setComment(ArticleSingleContentResult.ArticleProperty.EnumArticleComment.valueOf(comment.name()));
                article.setArticleProperty(articlePropertyResult);
            }
        }
        //是否包含代码块，如是则导入代码块插件，包含的文本由百度ueditor中代码开始片段，转换为兼容Prism插件代码块
        String editorContent = article.getEditorContent();
        if (StringUtils.containsIgnoreCase(editorContent , "<pre class=\"brush")) {
            article.setHasCode(true);
            article.setEditorContent(UeditorCodeConvert.code2Prims(editorContent));
        }
        return article;
    }

    /**
     * 保存文章内容分词
     * @param content 文章内容
     * @param title 文章标题
     */
    private void saveArticleTags(ArticleContent content , String title) {
        Ansj ansj = Ansj.newInstance();
        Result titleResult = ansj.keyWords(EnumAnalysis.NLP, title);
        List<Term> titleList = titleResult.getTerms().stream().filter(item -> item.getName().length() > 1).collect(Collectors.toList());
        Result contentResult = ansj.keyWords(EnumAnalysis.NLP, content.getSimpleContent());
        List<Term> contentList = contentResult.getTerms().stream().filter(item -> item.getName().length() > 1).collect(Collectors.toList());
        contentList.addAll(0 , titleList);
        List<String> dataList = Lists.newArrayList();
        contentList.forEach(item -> dataList.add(item.getName()));
        sysModuleTagsService.saveModuleTags(content.getArticleId() , MODULE_NAME , dataList);
    }

}
