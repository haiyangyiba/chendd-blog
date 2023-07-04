package cn.chendd.blog.admin.blog.article.service.impl;

import cn.chendd.blog.admin.blog.article.enums.EnumArticleContent;
import cn.chendd.blog.admin.blog.article.mapper.ArticleManageMapper;
import cn.chendd.blog.admin.blog.article.model.Article;
import cn.chendd.blog.admin.blog.article.po.ArticleManageParam;
import cn.chendd.blog.admin.blog.article.service.ArticleManageService;
import cn.chendd.blog.admin.blog.article.service.ArticlePraisesService;
import cn.chendd.blog.admin.blog.article.service.IArticlePropertyService;
import cn.chendd.blog.admin.blog.article.vo.*;
import cn.chendd.blog.admin.blog.comment.service.SysCommentService;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.admin.system.sysmenu.service.SysMenuService;
import cn.chendd.blog.client.article.vo.ArticlePraisesResult;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.core.common.treeview.stratified.Treeview;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.user.UserContext;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.BooleanUtils;
import org.assertj.core.util.Lists;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 博客管理-文章管理Service接口实现
 * @author chendd
 * @date 2020/07/31 21:21
 */
@Service
public class ArticleManageServiceImpl extends ServiceImpl<ArticleManageMapper, Article> implements ArticleManageService {

    @Resource
    private ArticleManageMapper articleManageMapper;
    @Resource
    private IArticlePropertyService articleStateService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private ArticleManageService articleManageService;
    @Resource
    private IArticlePropertyService articlePropertyService;
    @Resource
    private ArticlePraisesService articlePraisesService;
    @Resource
    private SysCommentService sysCommentService;

    @Override
    @Log(description = "文章管理-查询分页")
    public Page<ArticleManageResult> queryArticleManagePage(ArticleManageParam param) {
        Page<ArticleManageResult> page = new Page<>(param.getPageNumber() , param.getPageSize());
        Page<ArticleManageResult> pageFinder = articleManageMapper.queryArticleManagePage(page, param);
        List<ArticleManageResult> list = pageFinder.getRecords();
        List<Long> articles = Lists.newArrayList();
        list.forEach(item -> articles.add(item.getId()));
        //查询一页数据的文章状态数据
        List<ArticlePropertyResult> stateList = articleStateService.getArticleStateById(articles);
        for (ArticleManageResult result : list) {
            Long id = result.getId();
            for (ArticlePropertyResult stateResult : stateList) {
                if(id.equals(stateResult.getArticleId())) {
                    result.setProperty(stateResult);
                }
            }
            //是否存在文章内容，转换为枚举属性
            String existContent = result.getExistContent();
            ArticlePropertyResult state = result.getProperty();
            //如果存在文章内容，文章其它属性均无
            if (BooleanUtils.toBoolean(existContent) && state == null){
                state = new ArticlePropertyResult();
                state.setArticleId(id);
                state.setContent(EnumArticleContent.found);
                result.setProperty(state);
            } else if(BooleanUtils.toBoolean(existContent) && state != null) {
                state.setContent(EnumArticleContent.found);
            }
        }
        return pageFinder;
    }

    @Override
    @Log(description = "'文章管理-' + (#article.id == null ? '新增' : '修改')" , scope = LogScopeEnum.ALL)
    public Article saveArticle(Article article, SysUserResult user) {
        Long id = article.getId();
        if(id != null) {
            Article dbArticle = super.getById(id);
            dbArticle.setTitle(article.getTitle());
            dbArticle.setType(article.getType());
            dbArticle.setAscription(article.getAscription());
            dbArticle.setSortOrder(article.getSortOrder());
            dbArticle.setUpdateUsername(user.getAccount().getUsername());
            dbArticle.setUpdateTime(DateFormat.formatDatetime());
            super.saveOrUpdate(dbArticle);
        } else {
            article.setCreateUsername(user.getAccount().getUsername());
            article.setCreateTime(DateFormat.formatDatetime());
            super.saveOrUpdate(article);
        }
        return article;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(description = "文章数据删除" , scope = LogScopeEnum.ALL)
    public BaseResult removeArticle(Long id) {
        super.removeById(id);
        return new SuccessResult<>("文章删除成功！");
    }

    @Override
    public ArticleResult getArticle(Long id) {
        return articleManageMapper.getArticleById(id);
    }

    /**
     * 根据ID更新最终状态
     * @param article 文章ID
     */
    @Override
    public void updateLastState(Article article) {
        SysUserResult user = UserContext.getCurrentUser(SysUserResult.class);
        article.setUpdateUsername(user.getAccount().getUsername());
        article.setUpdateTime(DateFormat.formatDatetime());
        articleManageMapper.updateLastState(article);
    }

    @Override
    @Cacheable(key = "'index_queryArticleLimit_' + #pageNumber" , cacheNames = CacheNameConstant.NOT_EXPIRED)
    public List<ArticleContentResult> queryArticleLimit(Integer pageNumber) {
        Integer begin = (pageNumber - 1) * 10;
        Integer end = 10;
        List<ArticleContentResult> dataList = articleManageMapper.queryArticleContents(begin , end);
        this.resultArticleInfo(dataList);
        return dataList;
    }

    @Override
    public List<ArticleTagResult> queryArticleTags(Long id) {

        return this.articleManageMapper.queryArticleTags(id);
    }

    @Override
    public Integer getArticleVisitsNumber(Long articleId) {
        Integer visitsNumber = articleManageMapper.selectById(articleId).getVisitsNumber();
        return visitsNumber == null ? 0 : visitsNumber;
    }

    @Override
    public Integer queryAndModifArticleVisitsNumber(Long articleId) {
        articleManageMapper.updateVisitsNumber(articleId);
        Integer visitsNumber = articleManageMapper.selectById(articleId).getVisitsNumber();
        return visitsNumber;
    }

    @Override
    @Cacheable(key = "'queryArticleTypePage_' + #articleType + '_' + #pageNumber" , cacheNames = CacheNameConstant.NOT_EXPIRED)
    public Page<ArticleContentResult> queryArticleTypePage(Long articleType, Long pageNumber, Long pageSize) {
        Page<ArticleContentResult> page = new Page<>(pageNumber , pageSize);
        Page<ArticleContentResult> pageFinder = articleManageMapper.queryArticleTypePage(page, articleType);
        //查询文章分类
        List<ArticleContentResult> dataList = pageFinder.getRecords();
        //每一页都要显示置顶的数据
        dataList.addAll(0 , this.queryArticleTypeTop(articleType));
        List<Long> ids = dataList.stream().map(ArticleContentResult::getId).collect(Collectors.toList());
        //根据当前页的文章id数据，查询对应的属性数据
        List<ArticlePropertyResult> stateList = this.articlePropertyService.getArticleStateById(ids);
        for (ArticleContentResult result : dataList) {
            Long id = result.getId();
            for (ArticlePropertyResult stateResult : stateList) {
                if(id.equals(stateResult.getArticleId())) {
                    result.setProperty(stateResult);
                }
            }
        }
        this.resultArticleInfo(dataList);
        return pageFinder;
    }

    /**
     * 查询分类文章的所有置顶数据
     * @param articleType 文章分类ID
     * @return 数据集合
     */
    private List<ArticleContentResult> queryArticleTypeTop(Long articleType) {
        List<ArticleContentResult> dataList = this.articleManageMapper.queryArticleTypeTop(articleType);
        return dataList;
    }

    /**
     * 重设文章列表数据
     * @param dataList 数据列表
     */
    private void resultArticleInfo(List<ArticleContentResult> dataList) {
        List<Treeview> treeviews = sysMenuService.queryTreeviews();
        for (ArticleContentResult result : dataList) {
            Long type = result.getType();
            //根据type或者文章层级文本显示
            String typeText = sysMenuService.getTreeviewType(treeviews, type , " <i class=\"fe-chevron-right\"></i> ");
            result.setTypeText(typeText);
        }
        //构造文章的关联对象数据
        for (ArticleContentResult item : dataList) {
            //文章标签
            List<ArticleTagResult> tagList = articleManageService.queryArticleTags(item.getId());
            item.setTagList(tagList);
            //文章并列属性集
            ArticlePropertyResult property = articlePropertyService.getArticleStateById(item.getId());
            item.setProperty(property);
            //文章访问个数
            Integer visitsCount = this.articleManageService.getArticleVisitsNumber(item.getId());
            item.setVisitsCount(visitsCount);
            //文章点赞个数
            List<ArticlePraisesResult> result = articlePraisesService.queryArticlePraisesResult(item.getId());
            int praisesCount = 0;
            for (ArticlePraisesResult praises : result) {
                String description = praises.getDescription();
                int index = description.lastIndexOf("：");
                if (index != -1) {
                    praisesCount = praisesCount + description.substring(index + 1).split("、").length;
                }
            }
            item.setPraisesCount(praisesCount);
            //文章评论个数
            Integer commentCount = sysCommentService.querySysCountById(item.getId());
            item.setCommentCount(commentCount);
        }
    }

}
