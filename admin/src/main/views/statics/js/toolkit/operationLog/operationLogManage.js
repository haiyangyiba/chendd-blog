$(function () {
    initElement();
    initEvent();
});

function initElement() {
    var height = $(window).height() - 60;//60表示底部的footer部分
    $("#grid_table").datagrid({
        url: "/toolkit/operationLog.html",
        height: height,
        toolbar: "#grid_toolbar",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "功能模块" , "field": "moduleName"},
            {"title" : "子功能模块" , "field": "functionName"},
            {"title" : "用户名称" , "field": "userName"},
            {"title" : "开始时间" , "field": "beginTime"},
            {"title" : "结束时间" , "field": "endTime"},
            {"title" : "执行结果" , "field": "result.text" , formatter: function (value , rowData , rowIndex) {
                    if (rowData.result.value === "success") {
                        return "<span class='badge badge-success badge-pill'>" + value + "</span>";
                    }
                    return "<span class='badge badge-danger badge-pill'>" + value + "</span>";
                }
            },
            {"title" : "执行时间（毫秒）" , "field": "runTime"},
            {"title" : "描述" , "field": "description"},
            {"title" : "IP地址" , "field": "ip"},
            {"title" : "操作" , "field": "remark" , "align" : "center" , formatter: function (index , row) {
                    var view = "<a href='javascript:void(0);' onclick=\"viewDetail('" + row.id +"' , '" + row.endTime.substr(0 , 10) + "')\">查看" + "</a>";
                    return view;
                }
            }
        ],
        pagination: true,
        sidePagination: 'server'
    });
}

function initEvent() {

    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
}

function refreshDatagrid() {
    $("#query_btn_id").click();
}

function viewDetail(id , date) {
    var url = "/toolkit/operationLog/" + date + "/" + id + ".html";
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