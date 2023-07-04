$(function () {
    var width = $(window).width() - 26;//左右两个15px
    window.editor = UE.getEditor('editorContent', {
        autoHeight : false,
        initialFrameWidth: width,
        initialFrameHeight: 250,
        fullscreen: false,
        autoHeightEnabled: false
    });
});

function submitForm() {
    var params = $.formToValues("form_save_id");
    params.editorContent = window.editor.getContent();
    $.ajaxRequest({
        url: "/system/infocontent.html", type: "put",
        data: JSON.stringify(params),
        success: function (result) {
            $.alert.success("数据保存成功！" , refreshDatagrid);
            return true;
        }
    });
}

function refreshDatagrid() {
    $.closeWindow();
    var src = window.parent.$('.J_menuTab.active').data("id");
    window.parent.$("iframe[src='" + src + "'")[0].contentWindow.document.getElementById("query_btn_id").click();
}