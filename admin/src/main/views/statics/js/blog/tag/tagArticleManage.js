$(function () {
    initElement();
    initEvent();
});

function initEvent() {
    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
}

function initElement() {
    var tagId = $("#tagId_search_id").val();
    var height = $(window).height();
    $("#grid_table").datagrid({
        url: "/blog/tagarticle/" + tagId + ".html",
        toolbar: "#grid_toolbar",
        height: height,
        uniqueId: "id",
        checkboxHeader: false,//不显示全选CheckBox
        clickToSelect: false,
        showColumns: false,
        showRefresh: false,
        columns: [
            {"checkbox" : true , field : "selected" , formatter: function(value , rowData , index){
                    return value === "true";
                }
            },
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "标题" , "field" : "title" , sortable: false},
            {"title" : "类型" , "field" : "typeText" , sortable: false}
        ],
        pagination: true,
        sidePagination: 'server',
        onCheck: function (row) {
            toggleTagArticle(row.id);
        },
        onUncheck: function (row) {
            toggleTagArticle(row.id);
        }
    });
}

function refreshDatagrid() {
    document.getElementById("query_btn_id").click();
}

function toggleTagArticle(articleId) {
    var tagId = $("#tagId_search_id").val();
    console.log("tagId-->" + tagId);
    var url = "/blog/tagarticle/" + tagId + "/" + articleId + ".html";
    $.ajaxRequest({
        url: url,type: "GET",
        success: function(result) {
            $.alert.success(result.data , refreshDatagrid);
        }
    });
}