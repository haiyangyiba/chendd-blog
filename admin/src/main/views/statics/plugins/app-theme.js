/**
 * 静态页面按主题导入css的实现
 * @type {jQuery.fn.init}
 */
var top_tab = window.top.$(".content-tabs");
if(window !== window.top && top_tab.length === 1 && top_tab.attr("currentTheme") === "dark") {
    //子页面按主题导入css
    document.writeln("<link href=\"../assets/css/bootstrap-dark.min.css\"  rel=\"stylesheet\" type=\"text/css\" />");
    document.writeln("<link href=\"../assets/css/app-dark.min.css\"  rel=\"stylesheet\" type=\"text/css\" />");
} else {
    document.writeln("<link href=\"../assets/css/bootstrap.min.css\"  rel=\"stylesheet\" type=\"text/css\" />");
    document.writeln("<link href=\"../assets/css/app.min.css\"  rel=\"stylesheet\" type=\"text/css\" />");
}