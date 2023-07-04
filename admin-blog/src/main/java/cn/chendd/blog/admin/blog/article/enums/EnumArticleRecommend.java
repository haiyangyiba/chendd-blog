package cn.chendd.blog.admin.blog.article.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 文章是否推荐枚举定义
 *
 * @author chendd
 * @date 2020/8/1 23:35
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumArticleRecommend {

    /**
     * 有内容
     */
    hot("推荐"),
    ;

    private String text;

    EnumArticleRecommend(String text) {
        this.text = text;
    }



}
