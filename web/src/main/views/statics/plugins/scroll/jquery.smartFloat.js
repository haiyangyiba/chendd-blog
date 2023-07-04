/**
 * 滚动条插件，监听滚动条滚动事件，当滚动至某个元素时使其浮动显示在最上
 * @returns {*|Window.jQuery}
 */
$.fn.smartFloat = function () {
    var position = function (element) {
        var top = element.position().top, pos = element.css("position");
        var width = element.outerWidth(true);
        var minWidth = element.data("minWidth");
        $(window).scroll(function () {
            //最小宽度，小于当前宽度时不增加浮动实现
            if (minWidth && $(document).width() < minWidth) {
                return;
            }
            var scrolls = $(this).scrollTop();
            if (scrolls > top) {
                if (window.XMLHttpRequest) {
                    element.css({
                        position: "fixed",
                        //接收data-top属性，显示距离上部的位置间距，否则默认显示居上为：0
                        top: element.data("top") || 0,
                        //由于元素增加position:fixed后脱离文档上下文显示，会出现原有宽度丢失的情况
                        width: width
                    });
                } else {
                    element.css({
                        top: scrolls
                    });
                }
            } else {
                element.css({
                    position: pos,
                    top: top
                });
            }
        });
    };
    return $(this).each(function () {
        position($(this));
    });
};