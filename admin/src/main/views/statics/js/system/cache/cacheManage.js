$(function () {
    initElement();
    initEvent();
});

function initElement() {
    $("#grid_table").datagrid({
        url: "/system/cache.html",
        toolbar: "#grid_toolbar",
        sidePagination: 'client',
        columns: [
            {"radio" : true},
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

function initEvent() {
    //删除全部
    $("#removeAll_btn_id").click(function () {
        if ($.confirm("确定是要删除全部吗？" , function (flag) {
            if (! flag) {
                return;
            }
            $.ajaxRequest({
                url: "/system/cache/all.html", type: "delete",
                success: function (data) {
                    $.alert.success(data.message , function () {
                        refreshDatagrid("grid_table");
                    });
                }
            });
        }));
    });
    //删除所选
    $("#removeSelected_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var key = rows[0].name;
        if ($.confirm("确定是要删除所选吗？" , function (flag) {
            if (! flag) {
                return;
            }
            $.ajaxRequest({
                url: "/system/cache/select/" + key + ".html",
                type: "delete",
                success: function (data) {
                    $.alert.success(data.message , function () {
                        refreshDatagrid("grid_table");
                    });
                }
            });
        }));
    });
    //删除所选
    $("#removeArticleView_btn_id").click(function () {
        if ($.confirm("确定是要删除特定[文章预览]吗？" , function (flag) {
            if (! flag) {
                return;
            }
            $.ajaxRequest({
                url: "/system/cache/article_view.html",
                type: "delete",
                success: function (data) {
                    $.alert.success(data.message , function () {
                        refreshDatagrid("grid_table");
                    });
                }
            });
        }));
    });
}

function expandTable(index , row , $detail) {
    var tableId = "detailTable_" + index;
    var table = $detail.html("<table class='detailTable' id='" + tableId + "'></table>").find("table");
    $(table).datagrid({
        url: "/system/cache/" + row.name + ".html",
        toolbar: null,
        sidePagination: 'client',
        showColumns: false,
        showRefresh: false,
        method: "get",
        columns: [
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "key名称" , "field" : "keyName" , sortable : true},
            {"title" : "操作" , "field": "operation" , formatter: function (value , rowData , index) {
                    var remove = "<a href='javascript:void(0);' onclick=\"removeData('" + row.name + "' , '" + rowData.keyName + "' , '" + tableId + "')\">清除</a>";
                    return remove;
                }
            },
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

function removeData(cacheName , keyName , tableId) {
    if ($.confirm("确定是要清除吗？" , function (flag) {
            if (flag === true) {
                removeCacheElement(cacheName , keyName , tableId);
            }
        })
    );
}

function removeCacheElement(cacheName , keyName , tableId) {
    $.ajaxRequest({
        url: "/system/cache/" + cacheName + "/" + keyName + ".html",
        type: "get",
        success: function (data) {
            $.alert(data.message , function () {
                refreshDatagrid(tableId)
            });
        }
    });
}

function refreshDatagrid(tableId) {
    $("#" + tableId).datagrid("refresh");
}
