package cn.chendd.blog.admin.blog.requestdetail.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 最高访问量
 *
 * @author chendd
 * @date 2023/3/11 22:22
 */
@Data
public class SysRequestMaxDay implements Serializable {

    private String maxDay;

    private Integer maxCount;

}
