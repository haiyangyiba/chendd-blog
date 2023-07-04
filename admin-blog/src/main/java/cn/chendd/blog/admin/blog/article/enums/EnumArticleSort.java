package cn.chendd.blog.admin.blog.article.enums;

/**
 * 排序字段枚举：限定排序属性，防止传递动态SQL注入
 *
 * @author chendd
 * @date 2020/8/15 19:35
 */
public enum EnumArticleSort {

    /**
     * 标题
     */
    title(" a.title "),
    /**
     * 类型名称
     */
    typeText(" b.menuName "),
    /**
     * 创建时间
     */
    createTime(" a.createTime "),
    /**
     * 修改时间
     */
    updateTime(" a.updateTime "),
    /**
     * 默认排序
     */
    sortOrder(" a.sortOrder "),
    ;

    private String order;

    EnumArticleSort(String order) {
        this.order = order;
    }

    public String getOrder(String asc) {
        return this.order + asc;
    }

}
