$(function () {
    initElement();
    initEvent();
});

function initEvent() {
    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
    //删除数据
    $("#remove_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请至少选择一行数据！");
            return;
        }
        $.confirm("确定是要删除/撤销删除吗？" , function (result) {
            if (result === true) {
                removeData(rows[0].id);
            }
        });
    });
}

function removeData(id) {
    $.ajaxRequest({
        url: "/blog/comment/" + id + ".html" , type: "DELETE",
        success: function (result) {
            $.alert.success("操作成功！" , function () {
                $("#query_btn_id").click();
            });
        }
    });
}

function initElement() {
    var height = $(window).height() - 60;
    $("#grid_table").datagrid({
        url: "/blog/comment.html",
        height: height,
        toolbar: "#grid_toolbar",
        uniqueId: "id",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "标题" , "field" : "targetName"},
            {"title" : "用户名称" , "field": "realName"},
            {"title" : "用户来源" , "field": "userSource.text"},
            {"title" : "ip地址" , "field": "ip"},
            {"title" : "状态" , "field": "dataStatus.value" , "align" : "center" , formatter: function (value , rowData , rowIndex) {
                    if (value === "USABLE") {
                        return "<a class=\"badge badge-soft-success badge-pill\">可用</a>";
                    } else if (value === "DISABLE") {
                        return "<a class=\"badge badge-soft-danger badge-pill\">已删除</a>";
                    } else {
                        return "unknow";
                    }
                }
            },
            {"title" : "留言时间" , "field": "createTime"},
            {"title" : "操作" , "field": "operation" , "align" : "center" , formatter: function (value , rowData , rowIndex) {
                    var view = "<a href='javascript:void(0);' onclick=\"viewDetail('" + rowData.id + "')\">查看详细</a>";
                    return view;
                }
            }
        ],
        pagination: true
    });
}

function viewDetail(id) {
    var url = "/blog/comment/" + id + ".html";
    $.openWindow("查看明细" , url , {
        height: "330px",
        buttons: {
            cancel: {
                label: "<i class=\"fe-x\"></i>&nbsp;取消",
                className: "btn-default"
            }
        }
    });
}