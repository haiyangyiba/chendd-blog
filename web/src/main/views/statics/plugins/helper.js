//登录后跳转至登录前的页面
var backUrl = window.sessionStorage.getItem("login.backUrl");
if (backUrl !== null && backUrl !== "") {
    window.sessionStorage.removeItem("login.backUrl");
    if (backUrl !== document.URL) {
        window.location.href = backUrl;
    }
}

$(function () {
    //加载访问次数
    loadAccessData();

    //判断是否是第一次登录，提示录入邮箱
    var userInfo = $("#user_info_id");
    if (userInfo.length === 0) {
        return;
    }
    //用户已经登录，判断是否为首次登录
    var email = userInfo.attr("email");
    if (typeof email !== "undefined") {
        return;
    }
    var setEmailClose = window.sessionStorage.getItem("login.setEmailClose");
    if (setEmailClose !== null && setEmailClose !== "") {
        return;
    }
    var createTime = userInfo.attr("createTime");
    var lastLoginTime = userInfo.attr("lastLoginTime");
    if (createTime === lastLoginTime) {
        //首次登录
        userEmailDialog(true);
    }
});


/**
 * 第三方登录
 * @param source
 */
function thirdLogin(source) {
    $.ajaxRequest({
        url: "/third-login/" + source + ".html",
        data: {"friend" : "chendd"},
        type: "get", useWait: false,
        success: function (result) {
            window.sessionStorage.setItem("login.backUrl", document.URL);
            window.location.href = result.data;
        }
    });

}

/**
 * 用户信息窗口
 */
function userInfoDialog() {
    $.openWindow("<b class='p-0 m-0 font-16'><i class=\"fe-user\"></i>&nbsp;用户信息</b>" , "/user/userInfo.html" , {
        height: "260px",
        buttons: {
            cancel: {
                label: "<i class=\"fe-x\"></i>&nbsp;取消",
                className: "btn-primary"
            }
        }
    });
}

/**
 * 修改用户邮箱窗口
 * @param flag 取值：true或false，当true表示为第一次登录自动弹出，false为非第一次登录
 */
function userEmailDialog(flag) {
    var title = "设置Email";
    var buttons = {
        cancel: {
            label: "<i class=\"fe-x\"></i>&nbsp;取消",
            className: "btn-primary"
        }
    };
    if (flag) {
        title = title + "（首次登录）";
        buttons.ok = {
            label: "<i class=\"fe-x\"></i>&nbsp;不再提示",
            className: "btn-danger",
            callback: function () {
                window.sessionStorage.setItem("login.setEmailClose" , "true");
                return true;
            }
        };
    }
    $.openWindow("<b class='p-0 m-0 font-16'><i class=\"fe-mail\"></i>&nbsp;" + title + "</b>" , "/user/userEmail.html" , {
        height: "260px",
        buttons: buttons
    });
}

/**
 * 加载访问数据
 */
function loadAccessData() {
    //判断是否存在显示访问次数的元素
    if ($("#access_today_id").length === 0) {
        return;
    }
    $.ajaxRequest({
        url: "/blog/access.html",
        type: "get", useWait: false,
        success: function (result) {
            var data = result.data;
            $("#access_yesterday_id").text(data.yesterday);
            $("#access_today_id").text(data.today);
            $("#access_todayOutLink_id").text(data.todayOutLink);
            $("#access_maxCount_id").text(data.maxCount);
            $("#access_maxDay_id").text(data.maxDay);
            $("#access_total_id").text(data.total);
            //访问明细页
            $("#label_today_id").text(data.today);
            $("#label_todayOutLink_id").text(data.todayOutLink);
            $("#label_yesterday_id").text(data.yesterday);
            $("#label_maxCount_id").text(data.maxCount);
            $("#label_maxDay_id").text("最高：" + data.maxDay);
            $("#label_total_id").text(data.total);

        },
        error: function (a) {}
    });
}

//bootbox
;(function ($) {

    //如果祖先页面有导入alert对象时采用祖先页面的弹出
    var win = window;
    while(win !== window.top) {
        if(win.parent.bootbox !== undefined) {
            win = window.parent;
        } else {
            break;
        }
    }

    $.win = function () {
        return win;
    };

    var getMessage = function (icon, message) {
        var template = [
            '<div class="media"><div class="media-left">',
            '<img class="media-object img-lg img-circle" src="/statics/plugins/bootbox/icons/' + icon + '.png">',
            '</div>',
            '<div class="media-body">' + message + '</div></div>'
        ];
        return template.join("");
    };
    //提示框
    $.alert = function (message, fn, title, icon) {
        win.bootbox.alert({
            title: title || "提示",
            className: "dialog-alert",
            centerVertical: true,
            backdrop: "static",//true显示遮罩层，false表示不显示遮罩层，"static"显示背景且点击不关闭
            scrollable: true,
            message: getMessage(icon || 'warn', message),
            locale: "zh_CN",
            callback: fn || function () {},
            buttons: {
                ok: {
                    label: "确定",
                    className: "btn-primary"
                }
            }
        });
    };

    $.alert.success = function(message , fn) {
        $.alert(message , fn , "提示" , "success");
    };

    $.alert.warn = function(message , fn) {
        $.alert(message , fn , "警告" , "warn");
    };

    $.alert.error = function(message , fn) {
        $.alert(message , fn , "错误" , "error");
    };

    //确认框
    $.confirm = function (message, fn, title) {
        win.bootbox.confirm({
            title: title || "询问",
            className: "dialog-confirm",
            centerVertical: true,
            backdrop: "static",//true显示遮罩层，false表示不显示遮罩层，"static"显示背景且点击不关闭
            message: getMessage('question', message),
            callback: fn,
            locale: "zh_CN",
            buttons: {
                cancel: {
                    label: "取消",
                    className: "btn-soft-secondary waves-effect"
                },
                confirm: {
                    label: "确定",
                    className: "btn-danger"
                }
            }
        });
    };

    //遮罩层
    $.loading = function(message){
        if(win.loading) {
            return win.loading;
        }
        win.loading = win.bootbox.dialog({
            message: "<div align='center'><i class='fa fa-spin fa-spinner'></i>&nbsp;&nbsp;" + message + "</div>",
            closeButton: false,
            animate: false,
            centerVertical: true,
            size: "small"
        });
        return win.loading;
    };

    $.loading.show = function(message) {
        //防止重复显示
        if(!win.loading) {
            $.loading(message);
        }
    };

    $.loading.hide = function() {
        window.setTimeout(function (){
            if(win.loading) {
                win.loading.modal("hide");
                win.loading = undefined;
            }
        } , 0);
    };

    /**
     * 加载页面
     * @param title 窗口标题
     * @param url 窗口地址-全路径
     * @param options 参数对象
     * options {
     *     height: 高度像素
     *     callback: 窗口显示后的回调函数
     * }
     */
    $.openWindow = function(title , url , options) {
        win.openWindow = win.bootbox.dialog({
            title: title === "none" ? null : title + "<label id='dialog_page_title_" + url +"'></label>",
            className: options.className || "dialog-page",
            centerVertical: false,
            closeButton: options.closeButton === undefined ? true : options.closeButton,
            size: options.size || "large",
            backdrop: options.backdrop === undefined ? true : options.backdrop,
            scrollable: true, //内容自动滚动
            message: pageTemplate(url , options && options.height),
            locale: "zh_CN",
            buttons: options.buttons || {
                cancel: {
                    label: "<i class=\"fe-x\"></i>&nbsp;取消",
                    className: "btn-default",
                    callback: (options && options.calcelCallback) || function () {

                    }
                },
                confirm: {
                    label: "<i class=\"fe-save\"></i>&nbsp;确定",
                    className: "btn-blue" ,
                    callback: (options && options.submitCallback) || function () {
                        var iframeId = "iframe_" + url;
                        win.document.getElementById(iframeId).contentWindow.submitForm();
                        return false;
                    }
                }
            }
        });
        if(options && options.callback !== undefined) {
            options.callback.apply(this , []);
        }
    };

    $.closeWindow = function (url) {
        if(url) {
            win.$("label[id^='dialog_page_title_" + url + "']").parents(".bootbox.modal").modal("hide");
            return;
        }
        win.openWindow.modal("hide");
        win.openWindow = null;
    };

    /**
     * 封装art-template与thymeleaf不兼容的调用
     * @param id
     * @param options
     */
    $.template = function (id , options) {
        var content = $("#" + id).html().replace("<![CDATA[" , "").replace("]]>" , "");
        var html = template.render(content , options);
        return html;
    };

    function pageTemplate(url , height) {
        var template = [
            "<h5 " , "id='loading_" , url , "'" ,
            "class='nav flex-column justify-content-center text-center' style='overflow:hidden;height:"
            , height , ";'><p><i class='spinner-grow text-primary m-2'></i><i class='spinner-grow text-primary m-2'></i><i class='spinner-grow text-primary m-2'></i></p>" ,
            "</h5>" ,
            "<iframe id='iframe_" , url , "' src='" , url , "' onload=\"" ,
            "document.getElementById('loading_" , url ,"').style.display='none';this.style.display='block';\"" ,
            "' style='display:none;width:100%;height:" , (height || "460px") , "';" ,
            " frameborder='0' scrolling='no' "
            ,"></iframe>"
        ];
        return template.join("");
    }

})(jQuery);

//ajax
;(function ($) {

    function commonDefaules(options) {
        // 默认参数定义
        var defaults = {
            url: null,
            dataType: "json",
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            async: true,
            useWait: true,
            timeout: 20000 * 3,
            callback: options.callback || function(){}, //当请求未返回 success 时，点击alert的回调函数
            onBeforeSend : function(xhr) {
                if (defaults.useWait === true) {
                    $.loading.show("操作正在执行中...");
                }
                if (options.beforeSend) {
                    options.beforeSend(xhr);
                }
            },
            onSuccess : function(data) {
                if (defaults.useWait === true) {
                    // 显示等待效果
                    $.loading.hide();
                }
                if (this.dataType === "json" && data.result !== "success") {
                    $.alert.error(data.message , options.callback);
                } else if(options.success){
                    options.success(data);
                }
            },
            onError : function(a, b, c) {
                if (defaults.useWait === true) {
                    // 隐藏等待效果
                    $.loading.hide();
                }
                if (options.error) {
                    options.error(a, b, c);
                } else {
                    $.alert("操作出现错误，参考信息：" + a.statusText);
                }
            }
        };
        return defaults;
    }
    jQuery.extend({
        /**
         * ajax 请求包装
         * @param options
         */
        ajaxRequest : function(options) {
            // 默认参数定义
            var defaults = commonDefaules(options);
            var options = $.extend(defaults, options);
            if(["PUT","DELETE"].indexOf(options.type.toUpperCase()) !== -1) {
                options.contentType = "application/json";
            }
            options.beforeSend = defaults.onBeforeSend;
            options.success = defaults.onSuccess;
            options.error = defaults.onError;
            $.ajax(options);
        },
        /**
         * 获取form表单的数据
         * @param formId 必须
         */
        formToValues : function (formId , semantic, elements, filtering) {
            var arrays = $("#" + formId).formToArray(semantic, elements, filtering);
            var values = {};
            for(var i=0 ; i < arrays.length ; i++) {
                var item = arrays[i];
                if(values.hasOwnProperty(item.name)) {
                    values[item.name] = values[item.name] + "," + item.value;
                } else {
                    values[item.name] = item.value;
                }
            }
            return values;
        },
        /**
         * 清空form表单的数据
         * @param formId
         */
        clearForm : function (formId , includeHidden) {
            $("#" + formId).clearForm(includeHidden);
        },
        /**
         * 设置用户上下文信息
         * @param result 用户对象
         */
        setUserInfo : function(result) {
            var userResult = JSON.stringify(result);
            window.sessionStorage.setItem("userContext" , userResult);
        },
        getUserInfo : function(key) {
            var userResult = window.sessionStorage.getItem("userContext");
            if (! userResult) {
                if (key) {
                    return null;
                }
                return {};
            }
            var userInfo = JSON.parse(userResult);
            if (! key) {
                return userInfo;
            }
            return userInfo[key];
        },
        /**
         * 清空用户上下文对象信息
         */
        clearUserInfo : function() {
            window.sessionStorage.removeItem("userContext");
        },
        /**
         * 获取用户参数设置，key为null或undefined时返回参数设置对象，否则返回key对应的数据
         * @param key 不传 或 属性key
         */
        getUserSetting : function(key) {
            var userSetting = window.localStorage.getItem("userSetting");
            if (! userSetting) {
                if (key) {
                    return null;
                }
                return {};
            }
            var setting = JSON.parse(userSetting);
            if (! key) {
                return setting;
            }
            return setting[key];
        },
        /**
         * 用户参数设置记录
         * @param key 参数名
         * @param value 参数值
         */
        setUserSetting : function (key , value) {
            var userSetting = window.localStorage.getItem("userSetting");
            var setting;
            if (! userSetting) {
                setting = {};
            } else {
                setting = JSON.parse(userSetting);
            }
            setting[key] = value;
            window.localStorage.setItem("userSetting" , JSON.stringify(setting));
        },
        clearUserSetting : function (key) {
            var userSetting = window.localStorage.getItem("userSetting");
            if (! userSetting) {
                return;
            }
            var setting = JSON.parse(userSetting);
            if (key) {
                delete setting[key];
                window.localStorage.setItem("userSetting" , JSON.stringify(setting));
            } else {
                window.localStorage.removeItem("userSetting");
            }
        }
    });

})(jQuery);

//table
;(function ($) {

    $.fn.datagrid = function(options , parameter) {
        var tableElement = $(this);
        if(typeof options === "string" && typeof parameter !== "undefined") {
            return this.bootstrapTable(options , parameter);
        } else if(typeof options === "string" && typeof parameter === "undefined") {
            return this.bootstrapTable(options);
        }
        var defaults = {
            theadClasses: "thead-light",
            clickToSelect: true,
            method: "post",
            icons: {
                refresh: "fa-refresh",
                fullscreen: "fa-arrows-alt",
                toggleOn: "fa-toggle-on",
                toggleOff: "fa-toggle-off",
                columns: "fa-th-list"
            },
            showColumns: true,
            showRefresh: true,
            showToggle: false,
            pagination: false,
            toolbarAlign: 'left',
            queryParamsType: '',
            sidePagination: 'server',
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 25, 50],
            totalField: "total",
            dataField: "records",
            ajaxOptions: {
                beforeSend: function (xhr) {
                    beforeSend(tableElement);
                    return true;
                }
            },
            contentType: "application/x-www-form-urlencoded",
            queryParams: function(params) {
                var values = $.formToValues("query_form_id");
                var newParams = $.extend(values , params);
                return newParams;
            },
            responseHandler: function (result) {
                afterSend(tableElement);
                if (result.result === "success") {
                    return result.data;
                }
                tableElement.datagrid("hideLoading");
                $.alert(result.message);
            },
            onPostHeader: function () {
                if(! options.rightButton) {
                    return;
                }
                var lens = options.rightButton.length;
                if(lens == 0) {
                    return;
                }
                var temp = options.rightButton[0];
                if($("#" + temp.id).length > 0){
                    return;
                }
                var buttons = "";
                for(var i=0 ; i < lens ; i++) {
                    var item = options.rightButton[i];
                    var button = getRightButtonTemplate(item);
                    buttons += button;
                }
                tableElement.parents(".bootstrap-table").find(".fixed-table-toolbar .columns").prepend(buttons);
                for(var i=0 ; i < lens ; i++) {
                    var item = options.rightButton[i];
                    $("#" + item.id).click(item.click);
                }
            }
        };
        var options = $.extend(defaults , options);
        return tableElement.bootstrapTable(fillRules(options));
    };

    function fillRules(options) {
        var columns = options.columns;
        if(typeof columns === "array" && columns.length > 0) {
            columns = columns[columns.length - 1];
        }
        //填充编号列
        for(var i=0 ; i < columns.length ; i++) {
            var column = columns[i];
            var rownum = column.hasOwnProperty("rownum");
            if(rownum && column["rownum"] === true && !column.hasOwnProperty("formatter")) {
                column.formatter = function (value, rowData , rowIndex) {
                    return rowIndex + 1;
                };
                break;
            }
        }
        return options;
    }

    /**
     * 表格请求发送之前触发
     */
    function beforeSend(tableElement) {
        //获取当前主题，并设置数据加载中时显示的样式
        var currentTheme = window.top.$(".content-tabs[currentTheme]");
        if(currentTheme.length === 1) {
            if(currentTheme.attr("currentTheme") === "dark") {
                $(".fixed-table-loading").css("background" , "#303841");
            } else {
                $(".fixed-table-loading").css("background" , "#fff");
            }
        }
    }

    /**
     * 表格请求响应后加载数据之前触发
     */
    function afterSend(tableElement) {

    }

    function getRightButtonTemplate(item){
        var template = [
            "<button class='" , item.className , "' type='button' id='" , item.id , "' title='" , item.title , "'>",
            "<i class='" , item.icon , "'></i>",
            "</button>"
        ];
        return template.join("");
    }

})(jQuery);


//helper
;(function ($) {

    $.helper = {
        notNull: function(value) {
            var arrays = [undefined , "undefined" , null , "null"];
            return arrays.indexOf(value) !== -1 ? "" : value;
        },
        getDate: function() {
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1, month = month < 10 ? '0' + month : month;
            var day = date.getDate(), day = day < 10 ? '0' + day : day;
            return year + '-' + month + '-' + day;
        },
        getParameters: function (separator) {
            var queryString = window.location.search;
            var params = {};
            if (queryString.length == 0) {
                return params;
            }
            var props = queryString.substr(1).split("&");
            for (var i = 0; i < props.length; i++) {
                var prop = props[i].split("=");
                var propName = prop[0];
                var propValue = decodeURI(decodeURI(prop[1]));
                if (params[propName]) {
                    params[propName] = params [propName] + (separator || ",") + propValue;
                } else {
                    params [propName] = propValue;
                }
                return params;
            }
        },
        getParameter: function (name, separator) {
            var value = $.helper.getParameters(separator)[name];
            return $.helper.notNull(value);
        }
    }

})(jQuery);

//公共事件处理
$(function () {
    //绑定clearForm事件
    $(".resetForm").each(function () {
        var resetButton = $(this);
        resetButton.click(function () {
            var formId = resetButton.parents("form").attr("id");
            $.clearForm(formId , true);
            //绑定日期框的默认值
            $('[data-date-default="true"]').each(function () {
                $(this).val($.helper.getDate());
            });
        });
    });
    //绑定回车查询事件
    $(".enterSubmit").each(function () {
        var enterSubmit = $(this);
        enterSubmit.keyup(function (e) {
            var value = enterSubmit.val();
            if(e.keyCode === 13 && value !== "") {
                $("#query_btn_id").click();
            }
        });
    });
    //绑定输入框不能输入中文
    $(".not-chinese").each(function () {
        var element = $(this);
        element.on({
            "paste" : function () {
                return false;
            },
            "dragenter" : function (e) {
                return false;
            },
            "keyup" : function () {
                element.val(element.val().replace(/[\u0391-\uFFE5]/gi,''));
            },
            "dragover" : function () {
                return false;
            }
        });
    });
    /**
     * 绑定日期框的默认值
     */
    $('[data-date-default="true"]').each(function () {
        $(this).val($.helper.getDate());
    });
    /**
     * 返回到顶部
     */
    $("#returnTop_id").click(function() {
        $("html, body").animate({
            scrollTop : 0
        }, 300);
    });
    /**
     * 返回到底部
     */
    $("#returnBottom_id").click(function() {
        var height = $(document).height();
        $("html, body").animate({
            scrollTop : height
        }, 300);
    });

});