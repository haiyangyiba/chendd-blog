$(function () {
    initElement();
    initEvent();
});

function initEvent() {
    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
    $("#add_btn_id").click(function () {
        var url = "/toolkit/dbvalue/dbvalue.html";
        $.openWindow("新增系统参数" , url , {
            height: "330px"
        });
    });
    $("#modif_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var id = rows[0].id;
        var url = "/toolkit/dbvalue/" + id + ".html";
        $.openWindow("修改系统参数" , url , {
            height: "330px"
        });
    });
    //删除数据
    $("#remove_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请至少选择一行数据！");
            return;
        }
        $.confirm("确定是要删除吗？" , function (result) {
            if (result === true) {
                removeDbValue(rows[0].id);
            }
        });
    });
    //同步更新数据
    $("#async_btn_id").click(function () {
        $.confirm("确定是要同步数据吗？" , function (result) {
            if (result === true) {
                asyncDbValue();
            }
        });
    });
}

function asyncDbValue() {
    $.ajaxRequest({
        url: "/toolkit/dbvalue/async.html" , type: "PUT",
        success: function (result) {
            $.alert.success(result.data , function () {
                $("#query_btn_id").click();
            });
        }
    });
}

function removeDbValue(id) {
    $.ajaxRequest({
        url: "/toolkit/dbvalue/" + id + ".html" , type: "DELETE",
        success: function (result) {
            $.alert.success(result.data , function () {
                $("#query_btn_id").click();
            });
        }
    });
}

function initElement() {
    var height = $(window).height() - 60;
    $("#grid_table").datagrid({
        url: "/toolkit/dbvalue.html",
        height: height,
        toolbar: "#grid_toolbar",
        uniqueId: "id",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "参数名" , "field" : "key"},
            {"title" : "参数值" , "field": "value"},
            {"title" : "组名" , "field": "group"},
            {"title" : "参数说明" , "field": "remark"}
        ],
        pagination: true,
        sidePagination: 'client'
    });
}