package cn.chendd.blog.client.search;

import lombok.Data;

/**
 * 关键字查询Vo
 *
 * @author chendd
 * @date 2022/2/17 20:59
 */
@Data
public class SearchVo {

    //文章ID
    private Long id;
    //标题
    private String title;
    //最后更新时间
    private String lastTime;
    //内容简述
    private String simpleContent;
    //匹配个数
    private Integer counts;
    //匹配次数
    private Integer sums;

}
