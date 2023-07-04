package cn.chendd.blog.admin.blog.article.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 文章点赞类型
 *
 * @author chendd
 * @date 2021/2/18 9:53
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum EnumArticlePraises implements IEnum<String> {

    /**
     * 给力
     */
    Awesome("给力"),
    /**
     * 精品
     */
    Boutique("精品"),
    /**
     * 囧
     */
    Embarrassed("囧"),
    /**
     * 膜拜
     */
    Worship("膜拜"),
    /**
     * 路过
     */
    Walk("路过")
    ;

    private String text;

    EnumArticlePraises(String text) {
        this.text = text;
    }


    @Override
    public String getValue() {
        return this.name();
    }
}
