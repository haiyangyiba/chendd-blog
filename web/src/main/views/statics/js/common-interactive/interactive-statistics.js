$(function () {

    initElement();
});

function initElement() {
    //加载统计信息
    reloadMaintenanceInfo();
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
                var t = "<i class=\"fe-thumbs-up\">&nbsp;" + item.counts + "</i>";
                var a = "<a target='_blank' class='text-truncate' href='/blog/article/" + item.articleId + ".html'>" + item.title + "</a>" + t;
                var html = "<li class=\"list-group-item d-flex justify-content-between align-items-center list-group-item-action\">" + a + "</li>";
                commentHtml = commentHtml + html;
            }
            $("#commentArticle_box_id").append(commentHtml);
        }
    });
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