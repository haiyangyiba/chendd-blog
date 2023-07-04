$(function () {
    initEvent();
    loadUserRoleMenus();
});

function initEvent() {
    NProgress.start();
    var homepage = document.getElementById("iframe_homepage");
    homepage.src="/system/info/systemInfoManage.html";
    homepage.style.height = getIframeHeight("iframe_homepage");
    homepage.onload = function (ev) {
        NProgress.done();
    }
}

function changeAdminTheme(object) {
    var type = $(object).attr("type");
    $.ajaxRequest({
        url: "/system/theme/" + type + ".html", type: "get",
        success: function (result) {
            $.alert.success(result.data , function () {
                window.location.reload();
            });
        }
    });
}

/**
 * 用户退出登录
 */
function logout() {
    $.clearUserInfo();
    $.ajaxRequest({
        url: "/system/login.html", type: "get",
        success: function (result) {
            window.top.location.href = "/login.html";
        },
        error: function (a , b , c) {
            window.top.location.href = "/login.html";
        }
    });
}

/**
 * 初始化加载用户的角色菜单信息
 */
function loadUserRoleMenus() {
    var userInfo = $.getUserInfo();
    if (userInfo == null) {
        $.ajaxRequest({
            url: "/system/login.html",
            type: "put",
            success: function (result) {
                $.setUserInfo(result.data);
                disposeRoleMenu();
            }
        });
        return;
    }
    disposeRoleMenu();
}

function disposeRoleMenu() {
    var roles = $.getUserInfo("roles");
    if (! roles) {
        $.alert("当前用户没有角色，请重新登录！" , logout);
        return;
    }
    var defaultRole = $.getUserSetting("defaultRole");
    //存在切换角色的值时，当前默认选中为该角色显示
    if (defaultRole != null) {
        //读取默认角色显示菜单
        for (var i=0 ; i < roles.length ; i++) {
            var role = roles[i];
            if (defaultRole === role.roleId) {
                loadRoleMenu(role);
                return;
            }
        }
    }
    loadRoleMenu(roles[0]);
}

/**
 * 根据角色加载对应角色菜单
 * @param role 角色对象
 */
function loadRoleMenu(role) {
    //移除所有角色的图标，再切换至当前所选角色
    $("i[roles_icon]").removeClass("fe-check-square").addClass("fe-square");
    $("#role_icon_" + role.roleId).removeClass("fe-square").addClass("fe-check-square");
    //存储当前切换的角色数据为默认角色
    $.setUserSetting("defaultRole" , role.roleId);
    var currentTheme = $("body").data("sidebarColor");
    var html = $.template("roleMenu_template" , {data: role.menuList , currentTheme : currentTheme});
    $("#side-menu").empty().append(html);
    //删除一些空白得子级节点
    $("div[id^='role_menu_']").each(function () {
        var item = $(this);
        var ul = item.find("ul").children().length;
        if (ul === 0) {
            item.remove();
        }
    });
}

/**
 * 处理菜单打开方式
 * @param menuName 菜单名称
 * @param menuKey 菜单标识
 * @param menuUrl 菜单地址
 */
function disposeMenu(menuName , menuKey , menuUrl) {
    if (menuUrl && menuUrl !== "" && menuUrl !== "#") {
        $.toggleTab(menuName , menuUrl);
    }
}

/**
 * 角色切换
 * @param roleId 点击切换角色对象的id
 */
function changeRole(roleId) {
    var defaultRole = $.getUserSetting("defaultRole");
    //存在切换角色的值时，当前默认选中为该角色显示
    if (defaultRole !== null && roleId === defaultRole) {
        //存储的默认角色与当前点击的角色相同，则不进行角色切换操作
        return;
    }
    //关闭所有已经打开的页面
    $('.J_tabCloseAll').click();
    var roles = $.getUserInfo("roles");
    for (var i=0 ; i < roles.length ; i++) {
        var role = roles[i];
        if (roleId === role.roleId) {
            loadRoleMenu(role);
            break;
        }
    }
}