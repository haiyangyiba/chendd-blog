$(function () {
    initEvent();
    initElement();
});

function initElement() {
    var grid_table = $("#grid_table");
    //60为底部footer的高度
    var height = $(window).height() - 60;
    grid_table.datagrid({
        url: "/system/menu.html",
        toolbar: "#grid_toolbar",
        height: height,
        columns: [
            {"radio" : true},
            {"title" : "菜单名称" , "field" : "menuName" ,
                formatter: function (value , rowData , rowIndex) {
                    var leafIcon = rowData.isLeaf ? "<i class=\"far fa-file\"></i>&nbsp;" : "";
                    return leafIcon +
                        "<a href='javascript:void(0);' style='text-decoration: underline;' onclick=\"viewMenu('" + rowData.menuId + "')\">" +
                        value + "</a>"
                },
                cellStyle: function (value , rowData , rowIndex) {
                    if(rowData.menuNameMatch) {
                        return {
                            css : {"background-color" : "rgba(0 , 255 , 0 , 0.1)"}
                        };
                    }
                    return {};
                }
            },
            {"title" : "菜单标识" , "field": "menuKey",
                cellStyle: function (value , rowData , rowIndex) {
                    if(rowData.menuKeyMatch) {
                        return {
                            css : {"background-color" : "rgba(0 , 255 , 0 , 0.1)"}
                        };
                    }
                    return {};
                }
            },
            {"title" : "菜单类型" , "field": "menuType.text" , "align" : "center"},
            {"title" : "菜单地址" , "field": "menuUrl"},
            {"title" : "菜单图标" , "field": "menuIcon" , "sortable" : false , "align" : "center" , formatter: function (value , rowData , rowIndex) {
                     if(value) {
                         return "<i class=\"" + value + "\"></i>";
                     }
                     return value;
                }
            },
            {"title" : "请求类型" , "field": "requestMethod"},
            {"title" : "显示顺序" , "field": "menuOrder"},
            {"title" : "可用状态" , "field": "menuStatus.text" , "align" : "center"},
            {"title" : "创建时间" , "field": "createDate" , "align" : "center" , visible : false},
            {"title" : "最后修改时间" , "field": "updateDate" , "align" : "center" , visible : false}
        ],
        pagination: false,
        treeShowField: 'menuName', //tree节点显示列位置，似乎不起作用
        idField: 'menuId', //指定id列，与parentId列关联
        parentIdField: 'parentId', //指定parentId列
        onPostBody: function(data) {
            var columns = grid_table.bootstrapTable('getOptions').columns;
            if (columns && columns[0][1].visible) {
                grid_table.treegrid({
                    initialState: 'collapsed',// 所有节点都折叠
                    treeColumn: 1,
                    saveState: true,
                    expanderExpandedClass: 'fas fa-angle-down',  //图标样式
                    expanderCollapsedClass: 'fas fa-angle-right',
                    onChange: function() {
                        grid_table.datagrid('resetView');
                    }
                });
            }
            return data;
        },
        rightButton: [
            {"id" : "grid_expanded" , "title" : "展开所有" , "className" : "btn btn-secondary" , "icon" : " fas fa-angle-double-right" , click : function () {
                    grid_table.treegrid("expandAll");
                }
            },
            {"id" : "grid_collapsed" , "title" : "折叠所有" , "className" : "btn btn-secondary" , "icon" : " fas fa-angle-double-up" , click : function () {
                    grid_table.treegrid("collapseAll");
                }
            },
            {"id" : "cancel_selected" , "title" : "展开选中" , "className" : "btn btn-secondary" , "icon" : "fas fa-bullseye" , click : function () {
                   //展开所选
                    var rows = $("#grid_table").datagrid("getSelections");
                    if(rows.length === 0) {
                        $.alert("请选择一行数据！");
                        return;
                    }
                    $(".treegrid-" + rows[0].menuId).treegrid("expandRecursive")
                }
            }
        ]
    });
}

function initEvent() {
    //查询按钮
    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
    //新增按钮
    $("#add_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.openWindow("新增 <label class='text-danger'>[一级菜单]</label> " , "/system/menu/menu.html" , {
                height: "440px"
            });
        } else if(rows.length === 1) {
            var url = "/system/menu/menu.html?parentId=" + rows[0].menuId;
            $.openWindow("新增 <label class='text-danger'>[" + rows[0].menuName + "]</label> 子级" , url , {
                height: "440px"
            });
        } else {
            $.alert.warn("请选择一行数据！");
        }
    });
    //修改按钮
    $("#modif_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var url = "/system/menu/" + rows[0].menuId + ".html";
        $.openWindow("菜单修改" , url , {
            height: "440px"
        });
    });
    //删除按钮
    $("#remove_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length === 0) {
            $.alert.warn("请至少选择一行数据！");
            return;
        }
        $.confirm("确定是要删除吗？" , function (result) {
            if (result === true) {
                removeSysMenu(rows[0].menuId);
            }
        });
    });
}

function removeSysMenu(menuId) {
    $.ajaxRequest({
        url: "/system/menu/" + menuId + ".html" , type: "DELETE",
        success: function (result) {
            $.alert.success("菜单删除成功，删除数据 " + result.data + " 条！" , function () {
                $("#query_btn_id").click();
            });
        }
    });
}

function viewMenu(menuId) {
    var url = "/system/menu/view_" + menuId + ".html";
    $.openWindow("菜单查看" , url , {
        height: "440px",
        buttons: {
            cancel: {
                label: "<i class=\"fe-x\"></i>&nbsp;取消",
                className: "btn-default"
            }
        }
    });
}