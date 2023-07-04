$(function () {
    $("#save_btn_id").click(function () {
        var email = $.trim($("#email_add_id").val());
        if (email === "") {
            $.alert.warn("请输入Email！");
            return;
        }
        $.ajaxRequest({
            url : "/user/userEmail.html" , type: "put",
            data: JSON.stringify(email),
            success : function (result) {
                window.parent.$.alert.success("Email保存成功！" , function () {
                    window.location.reload();
                });
            }
        });
    });
});