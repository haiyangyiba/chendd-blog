$(function () {

    initElement();
});

function initElement() {
    //构造文章目录
    handleDirectory();
    //加载访问次数
    handleVisitsNumber();
    //判断当前页面是否存在品论
    var comment = document.getElementById("editor_content_id");
    if (comment == null) {
        return;
    }

    //加载点赞次数
    reloadPraises();
    //加载评论数据
    reloadComment(1);

    window.editor = new Simditor({
        textarea: $("#editor_content_id"),
        placeholder: "这里输入文字...",
        //toolbar: ["title", "bold", "italic", "underline", "strikethrough", "fontScale", "color", "|", "ol", "ul", "blockquote", "code", "table", "|", "link", "image", "hr", "|", "indent", "outdent", "alignment"],
        toolbar: ["title", "bold", "italic", "underline", "strikethrough", "fontScale", "color"],
        toolbarFloat: false,
        toolbarFloatOffset: 0,
        pasteImage: true,
        defaultImage: "/statics/plugins/simditor/assets/images/image.png",
        upload: false
        /*不开启图片上传功能*/
    });

    //设置默认数据
    getCacheComment();

    window.editor.on('blur', function() {
        setCacheComment();
    });

    //高亮支持
    $.highlight();
    //处理导出PDF
    $("#export_pdf_id").show().click(function () {
        var articleId = $("#page_article_id").attr("articleId");
        window.location.href = "/blog/export/pdf/" + articleId + ".html";
    });
}

/**
 * 处理目录
 */
function handleDirectory() {
    var directorys = $("#articleContent_box_id a[name]");
    if (directorys.length === 0) {
        $("#directory_box_id").hide();
        //处理文章关键字的浮动菜单
        $("#articleWords_box_id").smartFloat();
        return;
    }
    var trees = [];
    directorys.each(function () {
        var item = $(this);
        var name = item.attr("name");
        var element = JSON.parse(name);
        item.attr("id" , element.id);
        trees.push(element);
    });
    //转换数据结果集，修改为采用模板引擎的方式
    var data = [];
    for (var i=0 ; i < trees.length ; i++) {
        var item = trees[i];
        var parentId = item.parentId;
        if (!parentId || parentId === "") {
            data.push(item);
        }
    }
    //循环一级目录
    for (var i = 0; i < data.length; i++) {
        var item = data[i];
        item.childs = [];
        for (var j=0 ; j < trees.length ; j++) {
            var child = trees[j];
            if (item.id === child.parentId) {
                item.childs.push(child);
            }
        }
    }
    var html = $.template("directory_template_id" , {data: data});
    $("#directory_box_id").empty().append(html);
    //处理文章目录的浮动菜单
    $("#directory_box_id").smartFloat();
}

/**
 * 处理访问次数
 */
function handleVisitsNumber() {
    var articleId = getArticleId();
    var flag = window.sessionStorage.getItem("visits_" + articleId);
    if (flag !== null && flag !== "") {
        $("#visitsNumber_id").text("阅读：" + flag);
        return;
    }
    $.ajaxRequest({
        url: "/blog/article/visits/" + articleId + ".html",
        type: "get",
        useWait: false,
        success: function (result) {
            //存储访问次数
            window.sessionStorage.setItem("visits_" + articleId , result.data);
            $("#visitsNumber_id").text("阅读：" + result.data);
        }
    });
}

/**
 * 处理点赞事件
 */
function handlePraises(dataType) {
    var articleId = getArticleId();
    $.ajaxRequest({
        url: "/blog/article/praises.html",
        type: "put",
        data: JSON.stringify({"dataType" : dataType , "articleId" : articleId}),
        success: function (result) {
            //存储访问次数
            $.alert.success("感谢支持！" , reloadPraises);
        }
    });
}

/**
 * 加载点赞数据
 */
function reloadPraises() {
    var articleId = getArticleId();
    $.ajaxRequest({
        type: "get",
        useWait: false,
        url: "/blog/article/praises/" + articleId + ".html",
        success: function (result) {
            $("#praises_list_id").empty();
            var datas = result.data;
            var type = {};
            var html = "";
            for(var i=0 ; i < datas.length ; i++) {
                var item = datas[i];
                var dataTypes = item.dataTypes.split(",");
                for(var j=0 ; j < dataTypes.length ; j++) {
                    if (type[dataTypes[j]]) {
                        type[dataTypes[j]] = type[dataTypes[j]] + 1;
                    } else {
                        type[dataTypes[j]] = 1;
                    }
                }
                //输出每个点赞的用户信息
                if (item.description.indexOf("微博") !== -1 || item.description.indexOf("百度") !== -1) {
                    $("#praises_list_id").append(praisesUserTemplate("/internet/image?url=" + item.portrait , item.description));
                } else {
                    $("#praises_list_id").append(praisesUserTemplate(item.portrait , item.description));
                }
            }
            //输出每个点赞类型的个数
            for(var key in type) {
                $("#" + key + "_count_id").text(type[key]);
            }
        }
    });
}

function praisesUserTemplate(portrait , description) {
    var template = [
        '<a href="javascript: void(0);" class="avatar-group-item" title="' , description , '">',
            '<img src="' , portrait , '" class="rounded-circle avatar-sm" alt="' , description , '">',
        '</a>'
    ];
    return template.join("");
}


/**
 * 获取当前页面文章id
 * @returns 文章id
 */
function getArticleId() {
    var url = document.URL;
    var index = url.lastIndexOf("/") + 1;
    if (index === -1) {
        return index;
    }
    var last = url.lastIndexOf(".");
    var articleId = url.substring(index , last);
    return articleId;
}

/**
 * （重新）加载评论数据
 */
function reloadComment(pageNumber) {
    var useWait = true;
    if (pageNumber === 1) {
        useWait = false;
    }
    var targetId = getArticleId();
    $.ajaxRequest({
        url: "/blog/comment/" + targetId + "/" + pageNumber + ".html",
        type: "get",useWait: useWait,
        data: {},
        success: function (result) {
            var pageFinder = result.data;
            var pageHeader = ["当前有 " , pageFinder.current , "/" , pageFinder.pages , " 页数据，总计 " , pageFinder.total , " 条"].join("");
            $("#coment_pageHeader_id").text(pageHeader);
            pageFinder.current = parseInt(pageFinder.current);
            pageFinder.pages = parseInt(pageFinder.pages);
            pageFinder.size = parseInt(pageFinder.size);
            pageFinder.total = parseInt(pageFinder.total);
            var html = $.template("comment_template_id" , {pageFinder: pageFinder , userSource: getUserSourceObject()});
            $("#comment_box_id").empty().append(html);
        }
    });
}

function getUserSourceObject() {
    var object = {
        "Tencent_qq" : "bg-soft-danger text-danger" ,
        "Alipay" : "bg-soft-blue text-blue" ,
        "Sina_weibo" : "bg-soft-success text-success",
        "Gitee" : "bg-soft-warning text-warning",
        "Github" : "bg-soft-dark text-dark",
        "Baidu" : "bg-soft-info text-info",
        "System" : "bg-soft-primary text-primary"
    };
    return object;
}

/**
 * 弹出验证码，并提交验证
 * @param result 回调结果
 */
function handleComment(result) {
    if(result.ret === 0){
        handleCommentCommit(result.ticket , result.randstr);
    }
}

/**
 * 页面验证成功则将请求发至服务器校验
 * @param ticket 票据
 * @param randstr 随机数
 */
function handleCommentCommit(ticket , randstr) {
    var editorContent = window.editor.getValue();
    if (editorContent.length === 0) {
        $.alert.warn("还没有输入内容呢");
        return;
    }
    var childId = $("#childId_hidden_id").val();
    var replyUserId = $("#replyUserId_hidden_id").val();
    var targetId = getArticleId();
    var pageNumber = getPageNumber();
    var datas = {
        "moduleKey" : "Article",
        "pageNumber": pageNumber,
        "targetId": targetId,
        "childId": childId,
        "replyUserId": replyUserId,
        "editorContent": editorContent
    };
    $.ajaxRequest({
        url: "/blog/comment.html?ticket=" + ticket + "&randstr=" + randstr,
        type: "put",
        data: JSON.stringify(datas),
        success: function(result){
            //清空掉数据、新增数据后刷新当前页数据
            clearCacheComment();
            //判断当前是子回复文章呢还是回复某一条留言，如果是前者直接定位到第一页，如果是后者直接刷新本业
            if (childId !== "") {
                reloadComment(pageNumber);
                return;
            }
            reloadComment(1);
        }
    });
}

function getPageNumber() {
    var pageElement = $("#current_page_id");
    if (pageElement.length === 1) {
        return pageElement.data("page");
    }
    return 1;
}

function gotoComment(description , childId , createUserId) {
    $("#commentDescritpion_default_id").show();
    $("#commentDescritpion_text_id").text(description);
    $("#childId_hidden_id").val(childId);
    $("#replyUserId_hidden_id").val(createUserId);
    var top = $("#praises_title_id").offset().top + "px";
    $("html,body").animate({scrollTop : top}, 100);
    window.editor.focus();
}

function resetCommentText() {
    $("#commentDescritpion_default_id").hide();
    $("#commentDescritpion_text_id").text("当前回复：作者");
    $("#childId_hidden_id").val("");
    $("#replyUserId_hidden_id").val("");
}

function goArticleDirectory(id) {
    $("html, body").animate({
        scrollTop : $("#" + id).offset().top - 70
    }, 300);
}

/**
 * 设置编辑内容缓存
 */
function setCacheComment() {
    var editorContent = window.editor.getValue();
    if (editorContent.length === 0) {
        return;
    }
    var name = window.location.pathname.replace(/\//g , "");
    window.sessionStorage.setItem(name , editorContent);
}

/**
 * 清除编辑内容缓存
 */
function clearCacheComment() {
    window.editor.setValue("");
    var name = window.location.pathname.replace(/\//g , "");
    window.sessionStorage.removeItem(name);
}

/**
 * 清除编辑内容缓存
 */
function getCacheComment() {
    var name = window.location.pathname.replace(/\//g , "");
    var value = window.sessionStorage.getItem(name);
    if (value && value.length > 0) {
        window.editor.setValue(value);
    }
}
