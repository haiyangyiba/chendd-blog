package cn.chendd.blog.admin.blog.article.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 文章状态枚举定义
 *
 * @author chendd
 * @date 2020/8/1 23:31
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumArticleState {

    /**
     * 有内容
     */
    publish("发布"),
    ;

    private String text;

    EnumArticleState(String text) {
        this.text = text;
    }
}
