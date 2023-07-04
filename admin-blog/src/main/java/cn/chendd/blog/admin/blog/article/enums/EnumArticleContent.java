package cn.chendd.blog.admin.blog.article.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 文章内容枚举
 *
 * @author chendd
 * @date 2020/8/2 22:31
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumArticleContent implements IEnum<String> {

    /**
     * 有内容
     */
    found("内容"),
    ;

    private String text;

    EnumArticleContent(String text) {
        this.text = text;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
