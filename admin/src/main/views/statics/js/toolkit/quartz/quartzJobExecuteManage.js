$(function () {
    initElement();
    initEvent();
});

function initElement() {
    var height = $(window).height() - 60;
    $("#grid_table").datagrid({
        url: "/toolkit/quartzJobExecute.html",
        height: height,
        toolbar: "#grid_toolbar",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "任务组名" , "field" : "jobGroup"},
            {"title" : "任务名称" , "field": "jobName"},
            {"title" : "执行开始时间" , "field": "beginTime"},
            {"title" : "执行结束时间" , "field": "endTime"},
            {"title" : "耗时（毫秒）" , "field": "executionTime"},
            {"title" : "执行结果" , "field": "result" , formatter: function (index , row) {
                    if(row.result === "success") {
                        return '<span class="badge badge-success badge-pill">成功</span>';
                    } else if (row.result === "error") {
                        return '<span class="badge badge-danger badge-pill">错误</span>';
                    } else {
                        return '<span class="badge badge-blue badge-pill">' + row.result + '</span>';
                    }
                }
            },
            {"title" : "任务说明" , "field": "remark" , "align" : "center" , formatter: function (index , row) {
                    if(row.result === "success") {
                        var view = "<a href='javascript:void(0);' onclick=\"$.alert.success('" + row.remark +"');\">查看" + "</a>";
                        return view;
                    }
                    var view = "<a href='javascript:void(0);' onclick=\"viewDetail('" + row.id +"')\">查看" + "</a>";
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
        var param = $.formToValues("query_form_id");
        var beginTime = param.beginTime;
        var endTime = param.endTime;
        if(beginTime > endTime) {
            $.alert("开始日期不能大于结束日期！");
            return;
        }
        $("#grid_table").datagrid("refresh");
    });
}

function refreshDatagrid() {
    $("#query_btn_id").click();
}

function viewDetail(id) {
    var url = "/toolkit/quartzJobExecute/" + id + ".html";
    $.openWindow("查看执行结果" , url , {
        height: "330px",
        buttons: {
            cancel: {
                label: "<i class=\"fe-x\"></i>&nbsp;取消",
                className: "btn-default"
            }
        }
    });
}