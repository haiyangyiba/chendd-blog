$(function () {
    initElement();
    initEvent();
});

function initElement() {
    var height = $(window).height() - 60;
    $("#grid_table").datagrid({
        height: height,
        url: "/toolkit/quartz.html",
        toolbar: "#grid_toolbar",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "任务组名" , "field" : "jobGroup"},
            {"title" : "任务名称" , "field": "jobName"},
            {"title" : "任务描述" , "field": "jobDescription"},
            {"title" : "表达式" , "field": "cronExpression"},
            {"title" : "表达式描述" , "field": "triggerDescription"},
            {"title" : "实现类" , "field": "jobClassName"},
            {"title" : "运行状态" , "field": "triggerState"},
            {"title" : "开始执行时间" , "field": "startTime" , visible: false},
            {"title" : "上次执行时间" , "field": "prevFireTime"},
            {"title" : "下次执行时间" , "field": "nextFireTime"}
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
        var url = "/toolkit/quartz/quartz.html";
        $.openWindow("新增定时任务" , url , {
            height: "365px"
        });
    });
    //修改按钮
    $("#modif_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var jobGroup = encodeURI(rows[0].jobGroup);
        var jobName = encodeURI(rows[0].jobName);
        var url = "/toolkit/quartz/" + jobGroup + "/" + jobName + ".html";
        $.openWindow("修改定时任务" , url , {
            height: "365px"
        });
    });
    //删除按钮
    $("#remove_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请至少选择一行数据！");
            return;
        }
        var url = "/toolkit/quartz/" + rows[0].jobGroup + "/" + rows[0].jobName + ".html";
        $.confirm("确定是要删除吗？" , function (result) {
            if (result === true) {
                $.ajaxRequest({
                    url: url , type: "DELETE",
                    success: function (result) {
                        $.alert.success(result.data , function () {
                            $("#query_btn_id").click();
                        });
                    }
                });
            }
        });
    });
    //暂停
    $("#pause_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请至少选择一行数据！");
            return;
        }
        $.confirm("确定是要停用所选任务吗？" , function (result) {
            if (result === true) {
                operations("paused" , rows[0].jobGroup , rows[0].jobName);
            }
        });
    });
    //恢复
    $("#resume_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请至少选择一行数据！");
            return;
        }
        $.confirm("确定是要启用所选任务吗？" , function (result) {
            if (result === true) {
                operations("resume" , rows[0].jobGroup , rows[0].jobName);
            }
        });
    });
    //立即运行
    $("#run_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请至少选择一行数据！");
            return;
        }
        $.confirm("确定是要立即运行所选任务吗？" , function (result) {
            if (result === true) {
                operations("run" , rows[0].jobGroup , rows[0].jobName);
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