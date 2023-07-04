$(function () {
    $("#remark_add_id").maxlength({
        alwaysShow: true,
        placement: "bottom-left-inside"
    });
});

function submitForm() {
    var params = $.formToValues("form_save_id");
    $.ajaxRequest({
        url: "/toolkit/dbvalue.html", type: "put",
        data: JSON.stringify(params),
        success: function (result) {
            $.alert.success(result.data , refreshDatagrid);
            return true;
        }
    });
}

function refreshDatagrid() {
    $.closeWindow();
    var src = window.parent.$('.J_menuTab.active').data("id");
    window.parent.$("iframe[src='" + src + "'")[0].contentWindow.document.getElementById("query_btn_id").click();
}