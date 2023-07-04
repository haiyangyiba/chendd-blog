package cn.chendd.blog.admin.blog.article.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 文章是否置顶枚举定义
 *
 * @author chendd
 * @date 2020/8/1 23:34
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumArticleTopping {

    /**
     * 有内容
     */
    top("置顶"),
    ;

    private String text;

    EnumArticleTopping(String text) {
        this.text = text;
    }

}
