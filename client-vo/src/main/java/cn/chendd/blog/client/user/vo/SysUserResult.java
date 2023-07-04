package cn.chendd.blog.client.user.vo;

import cn.chendd.blog.base.enums.EnumMenuType;
import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.core.enums.EnumFastjsonDeserializer;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录后的会话实体对象
 *
 * @author chendd
 * @date 2019/11/24 17:11
 */
@Data
public class SysUserResult implements Serializable {

    @ApiModelProperty("账号信息")
    private SysAccount account;

    @ApiModelProperty("用户信息")
    private SysUser user;

    @ApiModelProperty("角色列表")
    private List<SysRole> roles;

    @ApiModelProperty("用户配置信息")
    private SysUserConfig userConfig;

    /**
     * 账户对象
     */
    @Data
    public static class SysAccount {

        @ApiModelProperty(value = "用户账户主键ID")
        @JSONField(serialize = false)
        private Long accountId;

        @ApiModelProperty(value = "用户名称")
        private String username;

        @ApiModelProperty(value = "用户密码")
        @JsonIgnore
        @JSONField(serialize = false)
        private String password;

        @ApiModelProperty(value = "用户状态")
        @JSONField(deserializeUsing = EnumFastjsonDeserializer.class)
        private EnumStatus status;

        @ApiModelProperty(value = "创建时间")
        private String createDate;

    }

    /**
     * 用户对象
     */
    @Data
    public static class SysUser {

        @ApiModelProperty(value = "用户信息的主键ID")
        private Long userId;

        @ApiModelProperty(value = "用户ID，第三方用户时的用户编号")
        private String userNumber;

        @ApiModelProperty(value = "真实姓名，第三方登录用户时的用户昵称")
        private String realName;

        @ApiModelProperty(value = "用户头像地址")
        private String portrait;

        @ApiModelProperty(value = "用户邮箱地址")
        private String email;

        @ApiModelProperty(value = "创建时间")
        private String createTime;

        @ApiModelProperty(value = "最后一次登录时间")
        private String lastLoginTime;

        @ApiModelProperty(value = "用户来源")
        @JSONField(deserializeUsing = EnumFastjsonDeserializer.class)
        private EnumUserSource userSource;

        @ApiModelProperty(value = "账号ID")
        private Long accountId;
    }

    /**
     * 角色对象
     */
    @Data
    public static class SysRole {

        @ApiModelProperty("用户ID")
        private String userId;
        @ApiModelProperty("角色ID")
        private Long roleId;
        @ApiModelProperty("角色标识")
        private String roleKey;
        @ApiModelProperty("角色名称")
        private String roleName;
        @ApiModelProperty("角色菜单集合")
        List<SysRoleMenu> menuList = Lists.newArrayList();

        @Data
        public static class SysRoleMenu {

            @ApiModelProperty("菜单ID")
            private Long menuId;
            @ApiModelProperty("菜单名称")
            private String menuName;
            @ApiModelProperty("菜单类型")
            @JSONField(deserializeUsing = EnumFastjsonDeserializer.class)
            private EnumMenuType menuType;
            @ApiModelProperty("菜单图标")
            private String menuIcon;
            @ApiModelProperty("上级菜单")
            private String parentId;
            @ApiModelProperty("菜单标识")
            private String menuKey;
            @ApiModelProperty("菜单地址")
            private String menuUrl;
            @ApiModelProperty("角色菜单集合")
            private List<SysMenu> roleMenus = Lists.newArrayList();

        }
    }

    /**
     * 菜单对象
     */
    @Data
    public static class SysMenu {

        @ApiModelProperty("角色ID")
        private Long roleId;
        @ApiModelProperty("菜单ID")
        private Long menuId;
        @ApiModelProperty("菜单名称")
        private String menuName;
        @ApiModelProperty("菜单类型")
        @JSONField(deserializeUsing = EnumFastjsonDeserializer.class)
        private EnumMenuType menuType;
        @ApiModelProperty("菜单图标")
        private String menuIcon;
        @ApiModelProperty("上级菜单")
        private Long parentId;
        @ApiModelProperty("菜单标识")
        private String menuKey;
        @ApiModelProperty("菜单url")
        private String menuUrl;

        @ApiModelProperty("子级菜单集合")
        private List<SysMenu> roleMenus = Lists.newArrayList();
    }

    @Data
    public static class SysUserConfig {

        @ApiModelProperty("后台主题")
        private String adminTheme;

    }

}
