$(function () {

    initElement();
});

function initElement() {
    //加载文章目录
    handleDirectory();
    //加载访问次数
    handleVisitsNumber();
    //加造统计信息
    reloadMaintenanceInfo();
    //加载点赞次数
    reloadPraises();
    //加载评论数据
    reloadComment(1);

    window.editor = new Simditor({
        textarea: $("#editor_content_id"),
        height: 160,
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

}

/**
 * 加载统计信息
 */
function reloadMaintenanceInfo() {
    var maintenanceInfo = $("#mantenanceInfo_id");
    if (maintenanceInfo.length === 0) {
        return;
    }
    $.ajaxRequest({
        url: "/blog/maintenanceInfo.html",
        useWait: false,
        type: "get",
        success: function (result) {
            //处理单属性
            var data = result.data;
            $("#articles_text_id").text(data.articles);
            $("#createSiteTime_text_id").text(data.createSiteTime);
            $("#users_text_id").text(data.users);
            $("#comments_text_id").text(data.comments);
            $("#praises_text_id").text(data.praises);
            $("#lastUpdateTime_text_id").text(data.lastUpdateTime);
            /*//处理年份文章数量
            var years = data.years;
            var yearHtml = "";
            for (var i = years.length - 1 ; i >= 0 ; i--) {
                var item = years[i];
                var li = "<li class='list-group-item d-flex align-items-center'><i class='fe-linkedin'></i>&nbsp;" + item + "</li>";
                yearHtml = yearHtml + li;
            }
            $("#years_box_id").append(yearHtml);*/
            //处理标签
            var tags = data.tags;
            var tagHtml = "";
            for (var i = 0 ; i < tags.length ; i++) {
                var item = JSON.parse(tags[i]);
                tagHtml += "<div class=\"col\">" +
                    "<a  style=\"" + item.style + "\" onclick=\"return showTags(this)\" title=\"" + item.name + "\" href=\"/blog/tag/" + item.name + ".html\">" + item.name + "(" + item.count + ")" + "</a></div>";
            }
            $("#tags_box_id").append(tagHtml);
            //处理文章点赞
            var pariseHtml = "";
            for (var i = 0 ; i < data.pariseList.length ; i++) {
                var item = data.pariseList[i];
                var t = "<i class=\"fe-thumbs-up\">&nbsp;" + item.counts + "</i>";
                var a = "<a target='_blank' class='text-truncate' href='/blog/article/" + item.articleId + ".html'>" + item.title + "</a>" + t;
                var html = "<li class=\"list-group-item d-flex justify-content-between align-items-center list-group-item-action\">" + a + "</li>";
                pariseHtml = pariseHtml + html;
            }
            $("#pariseArticle_box_id").append(pariseHtml);
            //处理留言最多
            var commentHtml = "";
            for (var i = 0 ; i < data.commentList.length ; i++) {
                var item = data.commentList[i];
                var t = "<i class=\"fe-message-square\">&nbsp;" + item.counts + "</i>";
                var a = "<a target='_blank' class='text-truncate' href='/blog/article/" + item.articleId + ".html'>" + item.title + "</a>" + t;
                var html = "<li class=\"list-group-item d-flex justify-content-between align-items-center list-group-item-action\">" + a + "</li>";
                commentHtml = commentHtml + html;
            }
            $("#commentArticle_box_id").append(commentHtml);
        }
    });
}

/**
 * 处理目录
 */
function handleDirectory() {
    var directorys = $("#articleContent_box_id a[name]");
    if (directorys.length === 0) {
        $("#directory_box_id").hide();
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
 * 虚拟文章id -1
 * @returns -1
 */
function getArticleId() {
    return document.getElementById("article_id").value;
}

/**
 * （重新）加载评论数据
 */
function reloadComment(pageNumber) {

    var targetId = getArticleId();
    $.ajaxRequest({
        url: "/blog/comment/" + targetId + "/" + pageNumber + ".html",
        type: "get",useWait: true,
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
    var datas = {
        "moduleKey" : "Article",
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
            var pageNumber = $("#current_page_id ").data("page");
            if (!pageNumber) {
                pageNumber = 1;
            }
            reloadComment(pageNumber);
        }
    });
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

function showTags(element) {
    $.openWindow("<b class='p-0 m-0 font-20'>" + element.innerText + "</b>" , element.href , {
        height: "360px",
        buttons: {
            cancel: {
                label: "<i class=\"fe-x\"></i>&nbsp;取消",
                className: "btn-primary"
            }
        }
    });
    return false;
}

function showVideo() {
    $.openWindow("none" , "/statics/frame/app-example.html" , {
        size: "small",
        buttons: {}
    });
    return false;
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