$(function () {
    initElement();
    initEvent();
});

function initEvent() {

}

function initElement() {
    var height = $(window).height() - 60;
    $("#grid_table").datagrid({
        url: "/blog/access.html",
        height: height,
        toolbar: "#grid_toolbar",
        uniqueId: "id",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "日期" , "field" : "date" , "align" : "center" , formatter: function (value , row , index) {
                    var url = "/blog/access/request/" + value + ".html";
                    return "<a href='javascript:void(0);' onclick=\"window.parent.$.toggleTab('" + value + "' , '" + url + "');\">" + value + "</a>";
                }
            },
            {"title" : "访问次数" , "field": "count"},
            {"title" : "外站请求次数" , "field": "outLinkCount"}
        ],
        pagination: true,
        sidePagination: 'server'
    });
}

function refreshGrid() {
    $("#grid_table").datagrid("refresh");
}