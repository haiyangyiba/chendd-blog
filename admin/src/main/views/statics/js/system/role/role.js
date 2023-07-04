$(function () {
    $("#remark_add_id").maxlength({
        alwaysShow: true,
        placement: "bottom-left-inside"
    });
});

function submitForm() {
    var params = $.formToValues("roleForm_save_id");
    $.ajaxRequest({
        url: "/system/role.html", type: "put",
        data: JSON.stringify(params),
        success: function (result) {
            $.alert.success("角色保存成功！" , refreshDatagrid);
            return true;
        }
    });
}

function refreshDatagrid() {
    $.closeWindow();
    var src = window.parent.$('.J_menuTab.active').data("id");
    window.parent.$("iframe[src='" + src + "'")[0].contentWindow.document.getElementById("query_btn_id").click();
}