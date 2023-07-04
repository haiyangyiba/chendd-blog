$(function () {
    initElement();
});

function initElement() {
    $("#grid_table").datagrid({
        url: "/system/datasource.html",
        toolbar: "#grid_toolbar",
        sidePagination: 'client',
        columns: [
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "名字" , "field" : "name"},
            {"title" : "是否永久有效" , "field" : "eternal"},
            {"title" : "内存最多存放个数" , "field": "maxElementsInMemory"},
            {"title" : "闲置时间（毫秒）" , "field": "timeToIdleSeconds"},
            {"title" : "有效时间" , "field": "timeToLiveSeconds"},
            {"title" : "启用磁盘缓存" , "field": "overflowToDisk"},
            {"title" : "磁盘最大缓存数量" , "field": "maxElementsOnDisk"},
            {"title" : "持久化磁盘" , "field": "diskPersistent"},
            {"title" : "清理线程间隔" , "field": "diskExpiryThreadIntervalSeconds"},
            {"title" : "缓存释放策略" , "field": "memoryStoreEvictionPolicy"}
        ],
        method: "post",
        pagination: true,
        detailView: true,
        onExpandRow: function (index, row, $detail) {
            var tableBody = $("#grid-table").parent(".fixed-table-body");
            tableBody.scrollTop(0);
            window.setTimeout(function () {
                var top = $detail.offset().top - 75;
                tableBody.animate({scrollTop : top + "px"} , 50);
            } , 50);
            expandTable(index , row , $detail);
        }
    });
}

function expandTable(index , row , $detail) {
    var table = $detail.html("<table class='detailTable' id='detailTable_'" + index + "></table>").find("table");
    $(table).datagrid({
        url: "/system/cache/" + row.name + ".html",
        toolbar: "#grid_toolbar",
        sidePagination: 'client',
        showColumns: false,
        showRefresh: false,
        method: "get",
        columns: [
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "key名称" , "field" : "keyName" , sortable : true},
            {"title" : "创建时间" , "field": "creationTime"},
            {"title" : "到期时间" , "field": "expirationTime"},
            {"title" : "最后访问时间" , "field": "lastAccessTime"},
            {"title" : "最后更新时间" , "field": "lastUpdateTime"},
            {"title" : "最新的创建和更新时间" , "field": "latestOfCreationAndUpdateTime"},
            {"title" : "空闲时间(秒)" , "field": "timeToIdle"},
            {"title" : "有效时间(秒)" , "field": "timeToLive"},
            {"title" : "key类型" , "field": "keyType"}
        ],
        pagination: true
    });
}

function refreshDatagrid() {
    $("#query_btn_id").click();
}
