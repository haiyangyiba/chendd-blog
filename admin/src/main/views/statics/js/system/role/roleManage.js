$(function () {
    initEvent();
    initElement();
});

function initEvent() {
    //查询按钮
    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
    //新增按钮
    $("#add_btn_id").click(function () {
        $.openWindow("角色新增" , "/system/role/role.html" , {
            height: "260px"
        });
    });
    //修改按钮
    $("#modif_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var roleId = rows[0].roleId;
        var url = "/system/role/" + roleId + ".html";
        $.openWindow("角色修改" , url , {
            height: "260px"
        });
    });
    //删除按钮
    $("#remove_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请至少选择一行数据！");
            return;
        }
        var roleIds = [];
        for(var i=0 ; i < rows.length ; i++) {
            var row = rows[i];
            roleIds.push("&ids=" + row.roleId);
        }
        $.confirm("确定是要删除吗？" , function (result) {
           if (result === true) {
               removeSysRole(roleIds.join(""));
           }
        });
    });
}

function removeSysRole(roleIds) {
    $.ajaxRequest({
        url: "/system/role.html?1=1" + roleIds , type: "DELETE",
        success: function (result) {
            $.alert.success("角色删除成功，删除数据 " + result.data + " 条！" , function () {
                $("#query_btn_id").click();
            });
        }
    });
}

function initElement() {
    $("#grid_table").datagrid({
        url: "/system/role.html",
        toolbar: "#grid_toolbar",
        columns: [
            {"checkbox" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "角色ID" , "field" : "roleId" , "sortable" : true , visible : false},
            {"title" : "角色名称" , "field" : "roleName" , "sortable" : true},
            {"title" : "角色标识" , "field": "roleKey" , "sortable" : true},
            {"title" : "创建时间" , "field": "createDate" , "sortable" : true},
            {"title" : "最后修改时间" , "field": "updateDate" , "sortable" : true},
            {"title" : "操作" , "field": "operation" , "align" : "center" , formatter: function (value , rowData , rowIndex) {
                    var remarkId = "detail_" + rowData.roleId;
                    var remark = "<p id='" + remarkId + "' style='display: none;'>" + rowData.remark + "</p>"
                    var view = "<a href='javascript:void(0);' onclick=\"viewDetail('" + remarkId + "')\"><i class='fe-eye'></i>&nbsp;查看描述</a>";
                    var edit = "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick=\"editorMenu('" + rowData.roleId + "')\"><i class='fe-edit'></i>&nbsp;编辑菜单</a>";
                    return remark + view + edit;
                }
            }
        ],
        pagination: true,
        sidePagination: 'client'
    });
}

function viewDetail(content) {
    $.alert($("#" + content).html());
}

function editorMenu(roleId) {
    var url = "/system/rolemenu/" + roleId + ".html";
    $.openWindow("编辑角色菜单" , url , {
        height: "460px"
    });
}