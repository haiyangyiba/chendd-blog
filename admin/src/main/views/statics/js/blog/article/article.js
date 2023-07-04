$(function () {
    initEvent();
});

function initEvent() {
    $("#typeText_add_id").focus(function () {
        var typeId = $("#type_add_id").val();
        var url = "/system/menu/selectMenu/" + typeId + ".html";
        $.openWindow("选择文章分类" , url , {
            height: "460px"
        });
    });

    $("#ascription_add_id").selectpicker({
        noneSelectedText: "请选择文章归属",
        width: "200px",
        height: "100px",
        style: "min-width: 200px",
        actionsBox: true,
        show: false,
        dropdownAlignRight:"down"
    });
}

function submitForm() {
    var params = $.formToValues("form_save_id");
    $.ajaxRequest({
        url: "/blog/article.html", type: "put",
        data: JSON.stringify(params),
        success: function (result) {
            $.alert.success("文章保存成功！" , refreshDatagrid);
            return true;
        }
    });
}

function refreshDatagrid() {
    var id = $("#id_add_id").val();
    if(id === "") {
        $.closeWindow('/blog/article/article.html');
    } else {
        $.closeWindow('/blog/article/' + id + '.html');
    }
    var src = window.parent.$('.J_menuTab.active').data("id");
    window.parent.$("iframe[src='" + src + "'")[0].contentWindow.document.getElementById("query_btn_id").click();
}