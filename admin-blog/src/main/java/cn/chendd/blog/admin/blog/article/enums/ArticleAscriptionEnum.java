package cn.chendd.blog.admin.blog.article.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章归属
 *
 * @author chendd
 * @date 2021/5/5 21:04
 */
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ArticleAscriptionEnum implements IEnum<String> {

    /**
     * 文章页面
     */
    Content("内容页面") ,
    /**
     * 自定义页面
     */
    Custom("自定义页面"),
    ;

    private String text;


    @Override
    public String getValue() {
        return this.name();
    }
}
