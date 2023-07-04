package cn.chendd.blog.admin.blog.article.service.impl;

import cn.chendd.blog.admin.blog.article.enums.EnumArticlePraises;
import cn.chendd.blog.admin.blog.article.mapper.ArticlePraisesMapper;
import cn.chendd.blog.admin.blog.article.model.ArticlePraises;
import cn.chendd.blog.admin.blog.article.service.ArticlePraisesService;
import cn.chendd.blog.admin.blog.article.vo.RemindPrausesAndCommentResult;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.client.article.vo.ArticlePraisesResult;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.core.utils.EnumUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章点赞接口实现
 *
 * @author chendd
 * @date 2021/2/18 9:31
 */
@Service
public class ArticlePraisesServiceImpl extends ServiceImpl<ArticlePraisesMapper , ArticlePraises> implements ArticlePraisesService {

    @Resource
    private ArticlePraisesMapper articlePraisesMapper;

    @Override
    @CacheEvict(key = "'viewArticleById_ForPraises_' + #praises.articleId" , allEntries = false , beforeInvocation = true , cacheNames = CacheNameConstant.NOT_EXPIRED)
    public Long saveArticlePraises(ArticlePraises praises) {
        //判断当前用户是否已经存在点赞数据
        Long createUserId = praises.getCreateUserId();
        QueryWrapper<ArticlePraises> countQuery = new QueryWrapper<>();
        countQuery.eq("createUserId" , createUserId).eq("dataType" , praises.getDataType().name()).eq("articleId" , praises.getArticleId());
        int count = super.count(countQuery);
        if (count > 0) {
            throw new ValidateDataException(String.format("已经赞过 【%s】 了" , praises.getDataType().getText()));
        }
        praises.setCreateTime(DateFormat.formatDatetime());
        super.save(praises);
        return praises.getArticleId();
    }

    @Override
    @Cacheable(key = "'viewArticleById_ForPraises_' + #articleId" , cacheNames = CacheNameConstant.NOT_EXPIRED)
    public List<ArticlePraisesResult> queryArticlePraisesResult(Long articleId) {
        List<ArticlePraisesResult> dataList = articlePraisesMapper.queryArticlePraisesResult(articleId);
        String description = "%s 用户 %s 表示：%s";
        for (ArticlePraisesResult result : dataList) {
            String[] dataTypes = StringUtils.split(result.getDataTypes() , ",");
            StringBuilder builder = new StringBuilder();
            for (String dataType : dataTypes) {
                builder.append("、").append(EnumUtil.getInstanceByName(dataType , EnumArticlePraises.class).getText());
            }
            builder.deleteCharAt(0);
            result.setDescription(String.format(description , result.getUserSource().getText() , result.getUserName() , builder.toString()));
        }
        return dataList;
    }

    @Override
    public List<RemindPrausesAndCommentResult> queryRemindPraises(String begin, String end) {
        return articlePraisesMapper.queryRemindPraises(begin , end);
    }
}
