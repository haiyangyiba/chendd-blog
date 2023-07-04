$(function () {
    initElement();
    initEvent();
    initTable();
});

function initEvent() {
    $("#query_btn_id").click(function () {
        refreshGrid();
    });
}

function initElement() {
    $("#isOutLink_search_id").on('loaded.bs.select', function (e, clickedIndex, isSelected, previousValue) {
        $("#isOutLink_label").remove();
        $("#isOutLink_search_id").selectpicker("show");
    });
    $("#isOutLink_search_id").selectpicker({
        noneSelectedText: "请选择",
        width: "200px",
        style: "min-width: 200px",
        actionsBox: true,
        show: false,
        dropdownAlignRight:"auto"
    });
}

/**
 * 初始化表格
 */
function initTable() {
    var height = $(window).height() - 60;
    var date = $("#date_id").val();
    var url = "/blog/access/request/" + date + ".html";
    $("#grid_table").datagrid({
        url: url,
        height: height,
        toolbar: "#grid_toolbar",
        uniqueId: "id",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "URL" , "field" : "url" , "align" : "left" , sortable: true},
            {"title" : "来源" , "field" : "referer" , "align" : "left" , sortable: true},
            {"title" : "IP地址" , "field": "ip" , sortable: true},
            {"title" : "操作系统" , "field": "operatingSystem" , sortable: true},
            {"title" : "浏览器版本" , "field": "browser" , sortable: true},
            {"title" : "是否外链" , "field": "isOutLink" , "align" : "center" , sortable: true , formatter: function (value , row , index) {
                    if (value === "true") {
                        return "是";
                    } else if (value === "false") {
                        return "否";
                    }
                    return value;
                }
            },
            {"title" : "访问时间" , "field": "createTime" , "align" : "center" , sortable: true}
        ],
        pagination: true,
        sidePagination: 'server'
    });
}

function refreshGrid() {
    $("#grid_table").datagrid("refreshOptions" , {
        pageNumber: 1
    });
}