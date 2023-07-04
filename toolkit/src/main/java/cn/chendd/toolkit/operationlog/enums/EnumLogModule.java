package cn.chendd.toolkit.operationlog.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 功能明细，细化功能描述
 *
 * @author chendd
 * @date 2020/6/26 13:09
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumLogModule implements IEnum<String> {

    /**
     * 系统默认功能
     */
    DEFAULT("默认" , EnumLogType.DEFAULT),
    LOGIN_IN("登录" , EnumLogType.LOGIN),
    LOGIN_OUT("退出" , EnumLogType.LOGIN),
    //--用户管理
    SYSTEM_USER_SELECT("用户管理-查询" , EnumLogType.SYSTEM),
    SYSTEM_USER_INSERT("用户管理-新增" , EnumLogType.SYSTEM),
    SYSTEM_USER_UPDATE("用户管理-修改" , EnumLogType.SYSTEM),
    SYSTEM_USER_VIEW("用户管理-查看" , EnumLogType.SYSTEM),
    SYSTEM_USER_RESET_PASSWORD("用户管理-密码重置" , EnumLogType.SYSTEM),
    //--用户角色管理
    SYSTEM_USER_ROLE_EDITOR("用户管理-角色编辑" , EnumLogType.SYSTEM),
    //--角色管理
    SYSTEM_ROLE_SELECT("角色管理-查询" , EnumLogType.SYSTEM),
    SYSTEM_ROLE_SAVE("角色管理-新增或修改" , EnumLogType.SYSTEM),
    SYSTEM_ROLE_DELETE("角色管理-删除" , EnumLogType.SYSTEM),
    //--角色菜单管理
    SYSTEM_ROLE_MENU_EDITOR("角色管理-菜单编辑" , EnumLogType.SYSTEM),
    //--菜单管理
    SYSTEM_MENU_SELECT("菜单管理-查询" , EnumLogType.SYSTEM),
    SYSTEM_MENU_SAVE("菜单管理-新增或修改" , EnumLogType.SYSTEM),
    SYSTEM_MENU_DELETE("菜单管理-删除" , EnumLogType.SYSTEM),
    //--系统参数管理
    SYSTEM_DBVALUE_ASYNC("参数管理-同步更新" , EnumLogType.SYSTEM),
    SYSTEM_DBVALUE_SAVE("参数管理-新增或修改" , EnumLogType.SYSTEM),
    SYSTEM_DBVALUE_DELETE("参数管理-删除" , EnumLogType.SYSTEM),
    //--定时任务
    SYSTEM_QUARTZ_SAVE("定时任务管理-新增或修改" , EnumLogType.SYSTEM),
    SYSTEM_QUARTZ_DELETE("定时任务管理-删除" , EnumLogType.SYSTEM),
    SYSTEM_QUARTZ_SELECT("定时任务管理-查询" , EnumLogType.SYSTEM),
    SYSTEM_QUARTZ_OPERATION("定时任务管理-操作" , EnumLogType.SYSTEM),
    //--友链管理
    BLOG_FRIENDS_LINK_SELECT("友链管理-查询" , EnumLogType.BLOG),
    BLOG_FRIENDS_LINK_SAVE("友链管理-新增或修改" , EnumLogType.BLOG),
    BLOG_FRIENDS_LINK_DELETE("友链管理-删除" , EnumLogType.BLOG),
    //标签管理
    BLOG_TAG_SELECT("标签管理-查询" , EnumLogType.BLOG),
    BLOG_TAG_SAVE("标签管理-新增或修改" , EnumLogType.BLOG),
    BLOG_TAG_DELETE("标签管理-删除" , EnumLogType.BLOG),
    //系统请求汇总
    BLOG_REQUEST_INSERT("系统请求-定时统计" , EnumLogType.BLOG),
    BLOG_REQUEST_SELECT("系统请求-查询" , EnumLogType.BLOG),
    //系统文章属性管理
    BLOG_ARGICLE_PROPERTY_CHANGE("文章属性-设置" , EnumLogType.BLOG),
    BLOG_ARGICLE_PROPERTY_COVER_DELETE("文章属性-封面删除" , EnumLogType.BLOG),
    BLOG_ARGICLE_PROPERTY_COVER_UPLOAD("文章属性-封面上传" , EnumLogType.BLOG),
    BLOG_ARGICLE_CONTENT("文章管理-编辑内容" , EnumLogType.BLOG),
    BLOG_ARGICLE_PAGE_SELECT("文章管理-查询分页" , EnumLogType.BLOG),
    BLOG_ARGICLE_SAVE("文章管理-新增或修改" , EnumLogType.BLOG),
    ;

    private String text;
    private EnumLogType logModule;

    EnumLogModule(String text , EnumLogType logModule) {
        this.text = text;
        this.logModule = logModule;
    }


    @Override
    public String getValue() {
        return this.name();
    }
}
