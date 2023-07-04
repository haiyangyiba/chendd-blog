$(function () {
    initElement();
    initEvent();
});

function initEvent() {
    $("#query_btn_id").click(function () {
        $("#grid_table").datagrid("refresh");
    });
    $("#add_btn_id").click(function () {
        var url = "/blog/article/article.html";
        $.openWindow("新增博客文章" , url , {
            height: "230px"
        });
    });
    $("#modif_btn_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var id = rows[0].id;
        var url = "/blog/article/" + id + ".html";
        $.openWindow("修改博客文章" , url , {
            height: "230px"
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
                var rowId = rows[0].id;
                $.ajaxRequest({
                    url : "/blog/article/" + rowId + ".html",
                    type : "delete",
                    success: function (result) {
                        $.alert.success(result.data , refreshDatagrid);
                    }
                });
            }
        });
    });
    //文章功能集
    initArticleEvent();
    //类型选择
    $("#typeText_search_id").focus(function () {
        var typeId = $("#type_add_id").val();
        var url = "/system/menu/selectMenu/0.html";
        $.openWindow("选择文章分类" , url , {
            height: "460px",
            submitCallback: function () {
                var iframe = $(window.parent.document.getElementById("iframe_" + url));
                if (iframe.length === 1) {
                    var rows = iframe[0].contentWindow.$("#grid_table").datagrid("getSelections");
                    if(rows.length === 0) {
                        $("#typeText_search_id").val("");
                        $("#type_search_id").val("");
                    } else {
                        $("#typeText_search_id").val(rows[0].menuName);
                        $("#type_search_id").val(rows[0].menuId);
                    }
                }
                return true;
            }
        });
    });
}

function initElement() {
    var height = $(window).height() - 60;
    $("#grid_table").datagrid({
        url: "/blog/article.html",
        toolbar: "#grid_toolbar",
        height: height,
        uniqueId: "id",
        columns: [
            {"radio" : true},
            {"title" : "序号" , "rownum" : true , "align" : "center"},
            {"title" : "标题" , "field" : "title" , sortable: true},
            {"title" : "文章归属" , "field" : "typeText" , sortable: true},
            {"title" : "类型" , "field" : "ascription.text" , sortable: false},
            {"title" : "创建人" , "field": "createUsername"},
            {"title" : "创建时间" , "field": "createTime" , sortable: true},
            {"title" : "修改人" , "field": "updateUsername"},
            {"title" : "修改时间" , "field": "updateTime" , sortable: true},
            {"title" : "访问次数" , "field": "visitsNumber" , sortable: false},
            {"title" : "更多状态" , "field": "property" , formatter: function (value , rowData) {
                    if(value === null) {
                        return "<span class=\"badge badge-warning\">全部为无</span>";
                    }
                    //内容、发布状态、置顶、推荐、封面、评论
                    var content = getValue(value , "content" , "内容");
                    var state = getValue(value , "state" , "发布");
                    var topping = getValue(value , "topping" , "置顶");
                    var recommend = getValue(value , "recommend" , "推荐");
                    var cover = getValue(value , "cover" , "封面");
                    var comment = getValue(value , "comment" , "评论");
                    return content + "&nbsp;&nbsp;" + state + "&nbsp;&nbsp;" + topping + "&nbsp;&nbsp;" + recommend
                        + "&nbsp;&nbsp;" + cover + "&nbsp;&nbsp;" + comment;
                }
            },
            {"title" : "排序" , "field": "sortOrder" , sortable: true}
        ],
        pagination: true,
        sidePagination: 'server'
    });
}

function initArticleEvent() {

    //编辑内容
    $("#article_content_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var id = rows[0].id;
        var url = "/blog/content/" + id + ".html";
        window.parent.$.toggleTab("内容编辑：" + rows[0].title , url);
    });
    //发布
    $("#article_state_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var state = rows[0].property && rows[0].property.state;
        changeProperty("确定是要设置【" + (state === null ? "" : "取消") + "发布】吗？" , "state" , rows[0]);
    });
    //置顶
    $("#article_topping_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if (rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var topping = rows[0].property && rows[0].property.topping;
        changeProperty("确定是要设置【" + (topping === null ? "" : "取消") + "置顶】吗？", "topping", rows[0]);
    });
    //推荐
    $("#article_recommend_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var recommend = rows[0].property && rows[0].property.recommend;
        changeProperty("确定是要设置【" + (recommend === null ? "" : "取消") + "推荐】吗？" , "recommend" , rows[0]);
    });
    //封面
    $("#article_cover_id label").click(function (e) {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！" , function () {
                $("#more_btn_id").dropdown('toggle');
            });
            return false;
        }
        return true;
    });
    //评论
    $("#article_comment_id").click(function () {
        var rows = $("#grid_table").datagrid("getSelections");
        if(rows.length !== 1) {
            $.alert.warn("请选择一行数据！");
            return;
        }
        var comment = rows[0].property && rows[0].property.comment;
        changeProperty("确定是要设置【" + (comment === null ? "关闭" : "开启") + "评论】吗？" , "comment" , rows[0]);
    });
}

function changeProperty(text , property , row) {
    $.confirm(text , function (result) {
         if(result === true) {
             $.ajaxRequest({
                 url : "/blog/property/" + property + "/" + row.id + ".html",
                 type : "get",
                 success: function (result) {
                     $.alert.success(result.data , refreshDatagrid);
                 }
             });
         }
    });
}

function getValue(value , property , label) {
    var html = "";
    var text = $("#article_" + property + "_id").find("i")[0].outerHTML;
    if(property === "comment" && value[property] !== null) {
        html = "<span class=\"badge badge-soft-danger\">" + text + "&nbsp;" + label + "</span>";
    } else if(property === "comment" && value[property] === null){
        html = "<span class=\"badge badge-soft-success\">" + text + "&nbsp;" + label + "</span>";
    } else if(value[property] === null){
        html = "<span class=\"badge badge-soft-danger\">" + text + "&nbsp;" + label + "</span>";
    }  else if(property === "cover" && value[property] !== null){
        html = "<span class=\"badge badge-soft-success\" style='cursor: pointer;' onclick=\"removeCoverFile(event , '" + value["articleId"] + "')\">"
            + text + "&nbsp;" + label + "&nbsp;<i class=\"fas fa-window-close\"></i></span>";
    } else {
        html = "<span class=\"badge badge-soft-success\">" + text + "&nbsp;" + value[property].text + "</span>";
    }
    return html;
}

function refreshDatagrid() {
    document.getElementById("query_btn_id").click();
}

function uploadCover(file) {
    if(file && file.files && file.files.length === 1) {
        var fileSize = file.files[0].size;
        if (fileSize > 512 * 1024) {
            $.alert.warn("当前上传图片不能大于 500KB！");
            $("#upload_form_reset_id").click();
            return;
        }
    } else {
        $.alert.warn("当前浏览器不支持此图片上传功能！");
        $("#upload_form_reset_id").click();
        return;
    }
    uploadCoverFile(file.files[0]);
}

function uploadCoverFile(file) {
    var formData = new FormData();
    formData.append("file" , file);
    var rows = $("#grid_table").datagrid("getSelections");
    formData.append("articleId" , rows[0].id);
    $.ajaxRequest({
        url: "/blog/property/cover.html",
        data: formData,
        contentType: false,
        processData: false,
        beforeSend: function(xhr) {
            //请求发起后重置文件上传框，防止重复选择同一个文件上传不会触发onchange
           window.setTimeout(function () {
               $("#upload_form_reset_id").click();
           } , 2000);
        },
        success: function(result){
            $.alert.success(result.data , refreshDatagrid);
        }
    });
}

function removeCoverFile(e , articleId) {
    e.preventDefault();
    if(e.stopPropagation) {
        e.stopPropagation();
    }
    $.confirm("确定是要删除吗？" , function (result) {
        if(result === true) {
            $.ajaxRequest({
                url: "/blog/property/cover/" + articleId + ".html",
                type: "delete",
                success: function (result) {
                    $.alert.success(result.data , refreshDatagrid);
                }
            });
        }
    })
}