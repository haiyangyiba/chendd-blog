$(function () {
    /**
     * 处理分页连接点击事件
     */
   $(".pagination .page-link").each(function () {
       var pageLink = $(this);
       pageLink.click(function () {
           var pageNumber = $(this).data("pageNumber");
           var url = "/blog/search/" + pageNumber + ".html";
           $("#searchForm_id").attr("action" , url).submit();
       });
   });
});