$(function () {
    initElement();
    initEvent();
});

function initElement() {

    $("#grid_table").datagrid({
        url: "/system/user.html",
        toolbar: "#grid_toolbar",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "用户名" , "field" : "username"},
            {"title" : "用户昵称" , "field": "realName"},
            {"title" : "email" , "field": "email"},
            {"title" : "用户来源" , "field": "userSource.text"},
            {"title" : "用户状态" , "field": "status.text"},
            {"title" : "创建时间" , "field": "createTime"},
            {"title" : "上次登录时间" , "field": "lastLoginTime"},
            {"title" : "操作" , "field": "operation" , "align" : "center" , formatter: function (value , rowData , rowIndex) {
                    var view = "<a href='javascript:void(0);' onclick=\"viewDetail('" + rowData.userId + "')\">查看详细</a>";
                    var role = "<a href='javascript:void(0);' onclick=\"editRole('" + rowData.userId + "')\">编辑角色</a>";
                    return view + "&nbsp;&nbsp;" + role;
                }
            }
        ],
        pagination: true
    });

    $('#userSource_add_id').on('loaded.bs.select', function (e, clickedIndex, isSelected, previousValue) {
        $("#userSource_label").remove();
        $("#userSource_add_id").selectpicker("show");
    });
    $("#userSource_add_id").selectpicker({
        noneSelectedText: "请选择用户来源",
        width: "200px",
        style: "min-width: 200px",
        actionsBox: true,
        show: false,
        dropdownAlignRight:"auto"
    });

    $('#userStatus_add_id').on('loaded.bs.select', function (e, clickedIndex, isSelected, previousValue) {
        $("#userStatus_label").remove();
        $("#userStatus_add_id").selectpicker("show");
    });
    $("#userStatus_add_id").selectpicker({
        noneSelectedText: "请选择用户状态",
        width: "200px",
        style: "min-width: 200px",
        actionsBox: true,
        show: false,
        dropdownAlignRight:"auto"
    });
}

function initEvent() {
    $(".resetForm").click(function () {
        $("#userStatus_add_id").selectpicker("val" , []);
        $("#userSource_add_id").selectpicker("val" , []);
    });
    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
    $("#add_btn_id").click(function () {
        var url = "/system/user/user.html";
        $.openWindow("none" , url , {
            height: "420px",
            closeButton: false
        });
    });
    $("#modif_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var userId = rows[0].userId;
        var url = "/system/user/" + userId + ".html";
        $.openWindow("none" , url , {
            height: "420px",
            closeButton: false
        });
    });
    $("#enable_btn_id").click(function () {
        changeStatus("enable" , "启用");
    });
    $("#disable_btn_id").click(function () {
        changeStatus("disable" , "禁用");
    });
    $("#resetPassword_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var accountId = rows[0].accountId;
        //发送请求重置密码
        var url = "/system/user/password_" + accountId + ".html";
        $.openWindow("重置密码" , url , {
            height: "120px",
            className: "small-dialog-page"
        });
    });
}

function viewDetail(userId) {
    var url = "/system/user/view_" + userId + ".html";
    $.openWindow("用户详细" , url , {
        height: "420px",
        buttons: {
            cancel: {
                label: "<i class=\"fe-x\"></i>&nbsp;取消",
                className: "btn-default",
                callback: function () {

                }
            }
        }
    });
}

function refreshDatagrid() {
    $("#query_btn_id").click();
}

function changeStatus(status , statusText) {
    var rows = $("#grid_table").datagrid("getSelections");
    if(rows.length !== 1) {
        $.alert.warn("请选择一行数据！");
        return;
    }
    var userId = rows[0].userId;
    var url = "/system/user/" + status + "_" + userId + ".html";
    $.confirm("确定是要" + statusText + "所选账号吗？" , function (r) {
        if(r !== true) return;
        $.ajaxRequest({
            url: url, type: "GET" ,
            success: function (result) {
                $.alert.success(result.data , refreshDatagrid);
            }
        })
    });
}

function editRole(userId) {
    var url = "/system/userrole/" + userId + ".html";
    $.openWindow("编辑用户角色" , url , {
        height: "420px"
    });
}