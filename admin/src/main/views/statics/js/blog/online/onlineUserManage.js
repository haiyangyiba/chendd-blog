$(function () {
    initElement();
    initEvent();
});


function initEvent() {

}

function initElement() {

    $("#grid_table").datagrid({
        url: "/blog/online/user.html",
        toolbar: "#grid_toolbar",
        uniqueId: "id",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "主键ID" , "field" : "primaryId" , visible: false},
            {"title" : "session Id" , "field": "sessionId"},
            {"title" : "数据类型" , "field": "attributeName.text"},
            {"title" : "关键数据" , "field": "attributeValue"},
            {"title" : "有效期（秒）" , "field": "maxInactiveInterval"},
            {"title" : "创建时间" , "field": "creationTime"},
            {"title" : "最后访问时间" , "field": "lastAccessTime"},
            {"title" : "到期时间" , "field": "expiryTime" , formatter: function (index , row) {
                    if(row.currentTime > row.expiryTime) {
                        return "<s>" + value + "</s>"
                    }
                    return row.expiryTime;
                }
            }
        ],
        pagination: true,
        sidePagination: 'server'
    });
}

function changeQuery(obj , otherId) {
    var check = $(obj).prop("checked");
    if(check === true) {
        $(obj).prop("checked" , true);
        $("#" + otherId).prop("checked" , false);
        refreshGrid();
        return;
    }
    $("#" + otherId).prop("checked" , false);
    $(obj).prop("checked" , true);
}

function refreshGrid() {
    $("#grid_table").datagrid("refresh");
}