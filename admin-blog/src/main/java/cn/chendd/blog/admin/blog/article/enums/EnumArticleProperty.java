package cn.chendd.blog.admin.blog.article.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 文章属性范围枚举定义
 *
 * @author chendd
 * @date 2020/8/1 23:37
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumArticleProperty implements IEnum<String> {

    /**
     * 内容编辑
     */
    content("内容编辑" , EnumArticleState.class , "fas fa-file-alt" , "存在内容" , "不存在内容"),
    state("发布" , EnumArticleState.class , "fas fa-exclamation-circle" , "设置为发布状态" , "设置为暂存暂存"),
    topping("置顶" , EnumArticleTopping.class , " fas fa-sort-amount-up-alt" , "设置置顶" , "取消置顶"),
    recommend("推荐" , EnumArticleRecommend.class , "fab fa-hotjar" , "设置系统推荐" , "取消系统推荐"),
    cover("封面" , EnumArticleCover.class , "far fa-image" , "设置封面图片" , "取消设置封面图片"),
    comment("评论" , EnumArticleComment.class , "far fa-comment-dots" , "开启评论" , "关闭评论"),
    ;

    /**
     * 文本
     */
    private String text;
    /**
     * 对应枚举类
     */
    private Class<? extends Enum> enumClass;
    /**
     * 图标
     */
    private String icon;
    /**
     * true 对应文本
     */
    private String submitText;
    /**
     * false 对应文本
     */
    private String cancelText;

    EnumArticleProperty(String text , Class<? extends Enum> enumClass , String icon , String submitText , String cancelText) {
        this.text = text;
        this.enumClass = enumClass;
        this.icon = icon;
        this.submitText = submitText;
        this.cancelText = cancelText;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
