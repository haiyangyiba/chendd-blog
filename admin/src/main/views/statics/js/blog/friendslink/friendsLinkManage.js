$(function () {
    initElement();
    initEvent();
});

function initEvent() {
    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
    $("#add_btn_id").click(function () {
        var url = "/blog/friendslink/friendsLink.html";
        $.openWindow("新增友情链接" , url , {
            height: "350px"
        });
    });
    $("#modif_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var id = rows[0].id;
        var url = "/blog/friendslink/" + id + ".html";
        $.openWindow("修改友情链接" , url , {
            height: "350px"
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
                removeFriendsLink(rows[0].id);
            }
        });
    });
}

function removeFriendsLink(id) {
    $.ajaxRequest({
        url: "/blog/friendslink/" + id + ".html" , type: "DELETE",
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
        url: "/blog/friendslink.html",
        height: height,
        toolbar: "#grid_toolbar",
        sortable: true,
        uniqueId: "id",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "标题" , "field" : "title" , sortable : true},
            {"title" : "logo" , "field": "logo" , sortable : true},
            {"title" : "地址" , "field": "link" , sortable : true},
            {"title" : "地址标识" , "field": "domainTag" , sortable : true},
            {"title" : "访问次数" , "field": "counts" , sortable : true},
            {"title" : "状态" , "field": "status.text" , sortable : true},
            {"title" : "排序" , "field": "sortOrder" , sortable : true},
            {"title" : "操作" , "field": "operation" , "align" : "center" , formatter: function (value , rowData , rowIndex) {
                    var view = "<a href='javascript:void(0);' onclick=\"viewDetail('" + rowData.id + "')\">查看详细</a>";
                    return view;
                }
            }
        ],
        pagination: true,
        sidePagination: 'client'
    });
}

function viewDetail(id) {
    var row = $("#grid_table").datagrid("getRowByUniqueId" , id);
    $.alert(row.remark);
}