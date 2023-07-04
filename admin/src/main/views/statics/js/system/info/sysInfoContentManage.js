$(function () {
    initElement();
    initEvent();
});

function initElement() {
    var height = $(window).height() - 60;
    $("#grid_table").datagrid({
        url: "/system/infocontent.html",
        height: height,
        toolbar: "#grid_toolbar",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "标题" , "field" : "title"},
            {"title" : "标识" , "field": "key"},
            {"title" : "创建人" , "field": "createUser"},
            {"title" : "创建时间" , "field": "createTime"},
            {"title" : "修改人" , "field": "updateUser"},
            {"title" : "修改时间" , "field": "updateTime"},
            {"title" : "排序" , "field": "sortOrder"},
            {"title" : "操作" , "align" : "center" , formatter: function (value , row , index) {
                    var view = "<a href=\"javascript:void(0);\" onclick=\"viewSysInfo('" + row.id + "')\"><i class='fe-eye'></i>&nbsp;查看内容</a>";
                    return view;
                }
            }
        ],
        pagination: true,
        sidePagination: 'client'
    });
}

function initEvent() {

    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
    $("#add_btn_id").click(function () {
        var url = "/system/infocontent/content.html";
        $.openWindow("新增数据" , url , {
            height: "520px"
        });
    });
    //修改按钮
    $("#modif_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var url = "/system/infocontent/content/" + rows[0].id + ".html";
        $.openWindow("修改数据" , url , {
            height: "520px"
        });
    });
    //删除按钮
    $("#remove_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请至少选择一行数据！");
            return;
        }
        var url = "/system/infocontent/content/" + rows[0].id + ".html";
        $.confirm("确定是要删除吗？" , function (result) {
            if (result === true) {
                $.ajaxRequest({
                    url: url , type: "DELETE",
                    success: function (result) {
                        $.alert.success("数据删除成功！" , function () {
                            $("#query_btn_id").click();
                        });
                    }
                });
            }
        });
    });
}

function refreshDatagrid() {
    $("#query_btn_id").click();
}

function operations(operation , jobGroup , jobName) {
    $.ajaxRequest({
        url: "/toolkit/quartz/" + operation + ".html" ,
        data: {
           "jobGroup": jobGroup,
           "jobName": jobName
        },
        success: function (result) {
            $.alert.success(result.data , refreshDatagrid);
        }
    });
}

function viewSysInfo(id) {
    var url = "/system/infocontent/view/" + id + ".html";
    $.openWindow("查看数据" , url , {
        height: "520px",
        buttons: {
            cancel: {
                label: "<i class=\"fe-x\"></i>&nbsp;取消",
                className: "btn-default"
            }
        }
    });
}