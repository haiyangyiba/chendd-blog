$(function () {
    initElement();
    initEvent();
});

function initEvent() {
    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
    $("#add_btn_id").click(function () {
        var url = "/blog/tag/tag.html";
        $.openWindow("新增标签" , url , {
            height: "180px"
        });
    });
    $("#modif_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var id = rows[0].id;
        var url = "/blog/tag/" + id + ".html";
        $.openWindow("修改标签" , url , {
            height: "180px"
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
                removeTag(rows[0].id);
            }
        });
    });
}

function removeTag(id) {
    $.ajaxRequest({
        url: "/blog/tag/" + id + ".html" , type: "DELETE",
        success: function (result) {
            $.alert.success(result.data , function () {
                $("#query_btn_id").click();
            });
        }
    });
}

function initElement() {

    $('#strong_search_id').on('loaded.bs.select', function (e, clickedIndex, isSelected, previousValue) {
        $("#strong_label").remove();
        $("#strong_search_id").selectpicker("show");
    });
    $("#strong_search_id").selectpicker({
        noneSelectedText: "请选择",
        width: "200px",
        style: "min-width: 200px",
        actionsBox: true,
        show: false,
        dropdownAlignRight:"auto"
    });

    var height = $(window).height() - 60;
    $("#grid_table").datagrid({
        url: "/blog/tag.html",
        height: height,
        toolbar: "#grid_toolbar",
        uniqueId: "id",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "标签名称" , "field" : "tag"},
            {"title" : "是否推荐" , "field": "strong.text"},
            {"title" : "排序" , "field": "sortOrder"},
            {"title" : "绑定个数" , "field": "counts"},
            {"title" : "操作" , "field": "operation" , "align" : "center" , formatter: function (value , rowData , rowIndex) {
                    var operation = "<a href='javascript:void(0);' onclick=\"articleReferance('" + rowData.id + "' , '" + rowData.tag + "')\">文章关联</a>";;
                    return operation;
                }
            }
        ],
        pagination: true,
        sidePagination: 'server'
    });
}

function articleReferance(id , tag) {
    var url = "/blog/tagarticle/" + id + ".html";
    $.openWindow("关联标签：" + tag , url , {
        height: "560px",
        buttons: {
            cancel: {
                label: "<i class=\"fe-x\"></i>&nbsp;取消",
                className: "btn-default"
            }
        }
    });
}