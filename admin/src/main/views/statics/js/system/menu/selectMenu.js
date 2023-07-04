$(function () {
    initElement();
});

function initElement() {
    var grid_table = $("#grid_table");
    grid_table.datagrid({
        url: "/system/menu/selectMenu.html",
        height: 460,
        showColumns: false,
        showRefresh: false,
        columns: [
            {"radio" : true},
            {"title" : "菜单名称" , "field" : "menuName" ,
                formatter: function (value , rowData , rowIndex) {
                    var leafIcon = rowData.isLeaf ? "<i class=\"far fa-file\"></i>&nbsp;" : "";
                    return leafIcon + value;
                }
            }
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
            //默认选中
            fillElement();
            return data;
        }
    });
}

function submitForm() {
    var rows = $("#grid_table").datagrid("getSelections");
    if(rows.length === 0) {
        $.alert("请选择一行数据！");
        return;
    }
    var win = window.parent.$("iframe[id^='iframe_/blog/article/']");
    if (win.length === 1) {
        win[0].contentWindow.document.getElementById("typeText_add_id").value = rows[0].menuName;
        win[0].contentWindow.document.getElementById("type_add_id").value = rows[0].menuId;
        $.closeWindow();
    }
}

function fillElement() {
    var menuId = $("#menuId_add_id").val();
    if(menuId !== "") {
        $("#grid_table").datagrid("checkBy" , {"field" : "menuId" , values : [menuId]});
    }
}
