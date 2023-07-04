var editor = null;
$(function () {
    var width = $(window).width() - 26;//左右两个15px
    var height = $(window).height() - 130;//上下两个60px，ue的工具栏，footer
    editor = UE.getEditor('contentEditor',{
        id: "contentFrame",
        initialFrameWidth: width,
        initialFrameHeight: height,
        labelMap: {
            "submit": "提交请求" , "close": "关闭页面"
        },
        fullscreen: false,
        //allowDivTransToP: false, //阻止div标签自动转换为p标签
        autoHeight : false,
        autoHeightEnabled: false,
        autoFloatEnabled: false //是否保持toolbar的位置不动
    });

    editor.ready(function(){
        //设置全屏事件
        editor.addListener('fullscreenchanged',function(event , isFullScreen){
            $("#fullScreen_btn_id").click();
        });
    });

    //绑定保存事件
    $("#submit_id").click(submitForm);
    //关闭按钮事件
    $("#close_id").click(function () {
        window.parent.$.confirm("确定是要关闭吗？" , function (result) {
           if (result) {
               window.parent.$.closeTab();
           }
        });
    });
});

function submitForm() {
    var articleId = $("#articleId_add_id").val();
    var param = {
        "articleId" : articleId,
        "editorContent" : editor.getContent()
    };
    $.ajaxRequest({
        url: "/blog/content/" + articleId + ".html", type: "put",
        data: JSON.stringify(param),
        success: function (result) {
            $.alert.success("文章内容保存成功！" , refreshDatagrid);
            return true;
        }
    });
}

function refreshDatagrid() {
    window.location.reload();
}