package cn.chendd.blog.admin.blog.comment.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 系统评论类型
 *
 * @author chendd
 * @date 2021/2/20 9:32
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum EnumSysComment implements IEnum<String> {

    /**
     * 博客文章
     */
    Article("博客文章"),
    ;

    private String text;

    @JsonCreator
    EnumSysComment(String text) {
        this.text = text;
    }

    @Override
    public String getValue() {
        return this.name();
    }

}
