$(function () {

});

function submitForm() {
    var password = $("#password_add_id").val();
    if(password === "" || $.trim(password).length === 0 || $.trim(password).length < 8) {
        $.alert("请输入8位以上长度的密码！");
        return;
    }
    var accountId = $("#accountId_add_id").val();
    $.ajaxRequest({
        url: "/system/user/password_" + accountId + ".html" , type: "PUT",
        data: JSON.stringify(password),
        success: function (result) {
            $.alert.success(result.data , refreshDatagrid);

        }
    });
}

function refreshDatagrid() {
    $.closeWindow();
    var src = window.parent.$('.J_menuTab.active').data("id");
    window.parent.$("iframe[src='" + src + "'")[0].contentWindow.document.getElementById("query_btn_id").click();
}