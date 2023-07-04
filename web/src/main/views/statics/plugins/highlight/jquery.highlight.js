/**
 * @author chendd
 * @date 2022/03/10
 * @description 关键字搜索高亮插件
 * @link http://www.chendd.cn
 * @example 1.使用默认的渲染方式 $.highlight();需要配合绑定事件的元素增加data-search属性；
 * @example 2.使用传递参数的方式进行渲染；
 */
;(function ($) {

    /**
     * 定义高亮插件名称
     * 1.当options传递为对象时处理为默认的插件初始化；
     * 2.当options传递为methods中存在的某个属性时，表示调用某个方法；
     * 3.其它情况提示错误；
     * @param options 对象参数或字符串方法名称
     * @returns {void|*}
     */
    $.highlight = function (options) {
        if (typeof options === 'object' || !options) {
            return methods.init.apply(this, arguments);
        } else if (methods[options]) {
            return methods[options].apply(this, Array.prototype.slice.call(arguments, 1));
        } else {
            $.error('Options with name ' + options + ' does not exists for jQuery.highlight');
        }
    };

    /**
     * 定义默认的属性参数
     * @type {{...}}
     */
    $.highlight.defaults = {
        tagName: "span", //查找到文本时增加高亮的标签名称，可以为span、a、b、i、等等标签
        eventTarget: ".highlight-event", //处理绑定该属性的标签赋予点击高亮事件处理
        cancelTarget: ".highlight-cancel", //为对应绑定事件源的对象增加取消高亮事件
        stamp: "highlight", //无特殊含义，方法选取所有高亮元素
        //高亮元素绑定的全部css属性
        tagStyle: {
            "background-color": "yellow",
            "color": "red"
        }
    };
    /**
     * 定义所有方法集
     * @type {{...}}
     */
    var methods = {

        /**
         * 初始化选择器对应的事件绑定
         * @param opt 参数属性
         */
        init: function (opt) {
            var $this = $(this);
            var settings = $.extend({}, this.highlight.defaults, opt);
            //初始化绑定事件
            $(settings.eventTarget).each(function () {
                var element = $(this);
                element.on("click", function () {
                    //高亮元素之前先清空一波
                    methods.cancel.apply(settings);
                    //高亮元素实现
                    methods.searchText.apply(settings, [element.data("search")]);
                });
            });
            /**
             * 初始化绑定清空
             */
            $(settings.cancelTarget).on("click", function () {
                methods.cancel.apply(settings);
            });
        },

        /**
         * 取消高亮/清空所有高亮的元素
         */
        cancel: function () {
            $(this.tagName + "[" + this.stamp + "]").each(function () {
                var current = $(this);
                current.replaceWith(current.text());
            });
        },

        /**
         * 查找文本并增加高亮处理
         * @param text 文本
         */
        searchText: function (text) {
            var settings = this;
            //绑定选中时间处理
            if ("ActiveXObject" in window) {
                //IE浏览器
                var word = document.body.createTextRange();
                while (word.findText(text)) {
                    //拼接html标签与设置样式属性
                    var templates = ["<", settings.tagName, " ", settings.stamp, " style='"];
                    for (var key in settings.tagStyle) {
                        templates.push(key + ":" + settings.tagStyle[key] + ";");
                    }
                    templates.push("'>", word.text, "</", settings.tagName, ">");
                    word.pasteHTML(templates.join(""));
                }
            } else if (document.implementation && document.implementation.createDocument) {
                //Chrome与Firefox等浏览器
                window.getSelection().collapse(document.body, 0);
                while (window.find(text)) {
                    var selection = window.getSelection().getRangeAt(0);
                    var selectedText = selection.extractContents();
                    //创建标签与设置样式属性
                    var tag = document.createElement(settings.tagName);
                    for (key in settings.tagStyle) {
                        tag.style[key] = settings.tagStyle[key];
                    }
                    tag.setAttribute(settings.stamp, "");
                    tag.appendChild(selectedText);
                    selection.insertNode(tag);
                }
                window.getSelection().removeAllRanges();
            }
        }
    };

})(jQuery);