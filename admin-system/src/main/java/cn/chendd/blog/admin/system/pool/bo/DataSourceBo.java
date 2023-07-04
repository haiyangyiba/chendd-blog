package cn.chendd.blog.admin.system.pool.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 数据应管理Bo业务参数对象
 *
 * @author chendd
 * @date 2021/11/20 21:02
 */
@Data
public class DataSourceBo {

    @ApiModelProperty("连接池名称")
    private String name;
    @ApiModelProperty("驱动名称")
    private String driverClassName;
    @ApiModelProperty("连接URL")
    private String url;
    @ApiModelProperty("用户账号")
    private String username;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("最大连接")
    private Integer maxActive;
    @ApiModelProperty("最小连接")
    private Integer minIdle;
    @ApiModelProperty("等待时间")
    private Long maxWait;
    @ApiModelProperty("初始连接")
    private Integer initialSize;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("活跃连接")
    private Integer activeCount;
    @ApiModelProperty("连接个数")
    private Integer poolingCount;
    @ApiModelProperty("创建个数")
    private Long createCount;
    @ApiModelProperty("销毁个数")
    private Long destroyCount;
    @ApiModelProperty("关闭个数")
    private Long closeCount;
    @ApiModelProperty("连接个数")
    private Long connectCount;
    @ApiModelProperty("执行查询个数")
    private Long executeQueryCount;
    @ApiModelProperty("执行更新个数")
    private Long executeUpdateCount;
    @ApiModelProperty("总执行个数")
    private Long executeCount;
    @ApiModelProperty("总执行个数2")
    private Long executeCount2;

    @ApiModelProperty("连接活跃数")
    private List<DataSourceConnectionBo> dataSourceConnections;

    /**
     * 数据源参数活跃参数对象
     */
    @Getter
    @Setter
    public static class DataSourceConnectionBo {

        @ApiModelProperty("连接ID")
        private Long connectionId;
        @ApiModelProperty("连接时间")
        private String connectTime;
        @ApiModelProperty("使用连接")
        private Long useCount;
        @ApiModelProperty("最后活跃时间")
        private String lastActiveTime;

    }

}
