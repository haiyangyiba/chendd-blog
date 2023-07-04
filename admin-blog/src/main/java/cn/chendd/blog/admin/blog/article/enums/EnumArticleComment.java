package cn.chendd.blog.admin.blog.article.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 文章评论管理
 *
 * @author chendd
 * @date 2020/8/2 22:31
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumArticleComment implements IEnum<String> {

    /**
     * 关闭评论
     */
    close("评论"),
    ;

    private String text;

    EnumArticleComment(String text) {
        this.text = text;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
