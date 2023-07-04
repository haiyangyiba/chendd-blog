$(function () {
    initElement();
});

function initElement() {

}

function submitForm() {
    var userId = $("#userId_add_id").val();
    var params = $.formToValues("submit_form_id");
    if(params.roleIds === undefined ||params.roleIds === "") {
        $.confirm("当前没有选中项，确定是要清空所有角色吗？" , function (r) {
            if(r === true) {
                saveData(userId , "");
            }
        });
        return;
    }
    saveData(userId , params.roleIds);
}

function saveData(userId , roleIds) {
    $.ajaxRequest({
        url: "/system/userrole/" + userId + ".html", type: "post",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "roleIds" : roleIds
        },
        success: function (result) {
            $.alert.success("保存成功，共保存 " + result.data + " 条角色数据！" , refreshDatagrid);
        }
    });
}

function refreshDatagrid() {
    $.closeWindow();
    var src = window.parent.$('.J_menuTab.active').data("id");
    window.parent.$("iframe[src='" + src + "'")[0].contentWindow.document.getElementById("query_btn_id").click();
}