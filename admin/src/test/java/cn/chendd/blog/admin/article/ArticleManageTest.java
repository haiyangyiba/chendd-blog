package cn.chendd.blog.admin.article;

import cn.chendd.BaseBootstrapTest;
import cn.chendd.blog.admin.blog.article.model.Article;
import cn.chendd.blog.admin.blog.article.model.ArticleContent;
import cn.chendd.blog.admin.blog.article.service.ArticleManageService;
import cn.chendd.blog.admin.blog.article.service.IArticleContentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文章管理Test
 * 说明：
 * 1.本测试类是提取纯文章内容blog_article_content表中的simpleContent中的内容部分；
 * 2.根据博客V1版本中的id关联新版数据内容id修改创建时间、创建人、修改时间、修改人4个字段；
 * @author chendd
 * @date 2022/2/4 16:29
 */
public class ArticleManageTest extends BaseBootstrapTest {

    @Resource
    private ArticleManageService articleManageService;
    @Resource
    private IArticleContentService articleContentService;

    @Test
    public void queryArticles() {
        System.out.println("ArticleManageTest.queryArticles--begin");
        QueryWrapper<ArticleContent> contentWrapper = new QueryWrapper<>();
        contentWrapper.select("articleId" , "simpleContent")
                .like("simpleContent" , "上述许多内容已经过时和过期了")
                .orderByAsc("articleId");
        List<Map<String, Object>> contentList = this.articleContentService.listMaps(contentWrapper);
        List<ArticleInfo> dataList = Lists.newArrayList();
        for (Map<String, Object> map : contentList) {
            String simpleContent = (String) map.get("simpleContent");
            ArticleInfo articleInfo = this.getArticleInfo(simpleContent);
            if (articleInfo == null) {
                continue;
            }
            Long articleId = (Long) map.get("articleId");
            articleInfo.setArticleId(articleId);
            dataList.add(articleInfo);
        }
        //数据按博客V1版本的id排序
        dataList.sort((o1, o2) -> {
            long result = o1.getOldArticleId() - o2.getOldArticleId();
            if (result > 0) {
                return 1;
            } else if (result < 0) {
                return -1;
            } else {
                return 0;
            }
        });
        dataList.forEach(System.out::println);
        System.out.println("ArticleManageTest.queryArticles--batch update");
        for (ArticleInfo articleInfo : dataList) {
            UpdateChainWrapper<Article> updateWrapper = ChainWrappers.updateChain(this.articleManageService.getBaseMapper());
            updateWrapper
                    .set("createTime", articleInfo.getDatetime())
                    .set("createUsername" , "admin")
                    .set("updateTime", articleInfo.getDatetime())
                    .set("updateUsername" , "admin")
                    .eq("id", articleInfo.getArticleId());
            updateWrapper.update();
        }
        System.out.println("ArticleManageTest.queryArticles--end");
    }

    /**
     * 文章内容信息
     */
    private static class ArticleInfo {

        /*文章ID*/
        private Long articleId;
        /*博客V1版本的创建时间/最后一次修改时间*/
        private String datetime;
        /*博客V1版本的文章ID*/
        private Long oldArticleId;

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder("{");
            builder.append("articleId=").append(articleId);
            builder.append(", datetime='").append(datetime).append('\'');
            builder.append(", oldArticleId=").append(oldArticleId);
            builder.append('}');
            return builder.toString();
        }

        public Long getArticleId() {
            return articleId;
        }

        public ArticleInfo setArticleId(Long articleId) {
            this.articleId = articleId;
            return this;
        }

        public String getDatetime() {
            return datetime;
        }

        public ArticleInfo setDatetime(String datetime) {
            this.datetime = datetime;
            return this;
        }

        public Long getOldArticleId() {
            return oldArticleId;
        }

        public ArticleInfo setOldArticleId(Long oldArticleId) {
            this.oldArticleId = oldArticleId;
            return this;
        }
    }

    /**
     * 根据文章纯内容文本提取博客V1版本的id 和 创建时间
     * @param simpleContent 博客纯内容
     * @return 文章内容信息
     */
    private ArticleInfo getArticleInfo(String simpleContent) {
        String regex = "上述许多内容已经过时和过期了.*?访问次数";
        Matcher matcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(simpleContent);
        if (matcher.find()) {
            String info = StringUtils.substringAfterLast(matcher.group(), "/");
            String id = StringUtils.substringBefore(info, ".a");
            String datetime = StringUtils.substringBetween(info , "最后更新：" , " 访问次数");
            /*System.out.println(String.format("id = %s，datetime = %s，info = %s" , id , datetime , matcher.group()));*/
            return new ArticleInfo().setOldArticleId(Long.valueOf(id)).setDatetime(datetime);
        }
        return null;
    }

}
