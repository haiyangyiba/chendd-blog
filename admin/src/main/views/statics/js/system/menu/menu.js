$(function () {
    initEvent();
    initElement();
});

function initEvent() {
    $("#menuIcon_add_id").focus(function () {
        //document.getElementById("menuIconView_add_id").style.border = "1px solid #B1BBC4";
        //document.getElementById("menuIconView_add_id").style.borderLeft = "none";
    }).blur(function () {
        //document.getElementById("menuIconView_add_id").style.border = "1px solid #CED4DA";
        //document.getElementById("menuIconView_add_id").style.borderLeft = "none";
    });
    //选择图标
    $("#menuIconView_add_id").click(function () {
        $.openWindow("选择图标" , "../../../statics/common/icons.html" , {
            height: "380px",
            backdrop: false,
            buttons: {
                cancel: {
                    label: "<i class=\"fe-x\"></i>&nbsp;取消",
                    className: "btn-default"
                }
            }
        });
    });
}

function initElement() {
    $("#remark_add_id").maxlength({
        alwaysShow: true,
        placement: "bottom-left-inside"
    });
}

function submitForm() {
    var params = $.formToValues("roleForm_save_id");
    //新增时ID为空
    if(params.menuId === "") {
        params.parentId = $.helper.getParameter("parentId");
    }
    if(params.menuType === "") {
        $.alert("请选择菜单类型！");
        return;
    }
    if(params.requestMethod === "") {
        $.alert("请选择菜单请求方式！");
        return;
    }
    if(params.menuStatus === "") {
        $.alert("请选择可用状态！");
        return;
    }
    $.ajaxRequest({
        url: "/system/menu.html", type: "PUT", data: JSON.stringify(params),
        success: function (result) {
            $.alert.success("角色保存成功！" , refreshDatagrid);
        }
    });
}

function refreshDatagrid() {
    var menuId = $("#menuId_add_id").val();
    if(menuId === "") {
        $.closeWindow('/system/menu/menu.html');
    } else {
        $.closeWindow('/system/menu/' + menuId + '.html');
    }
    var src = window.parent.$('.J_menuTab.active').data("id");
    window.parent.$("iframe[src='" + src + "'")[0].contentWindow.document.getElementById("query_btn_id").click();

}