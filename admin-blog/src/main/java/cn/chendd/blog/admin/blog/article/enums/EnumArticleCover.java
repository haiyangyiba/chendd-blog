package cn.chendd.blog.admin.blog.article.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 文章封面图片
 *
 * @author chendd
 * @date 2020/8/1 23:48
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumArticleCover {

    /**
     * 文章封面图片，该数据存储的最终为图片格式
     */
    cover("封面图片"),
    ;

    private String text;

    EnumArticleCover(String text) {
        this.text = text;
    }
}
