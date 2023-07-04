$(function () {
    initEvent();
});

function initEvent() {
    //登录按钮
    $("#login_btn_id").click(function () {
        loginSubmit();
    });
    //获取验证码
    $("#validateCode_btn_id").click(function () {
        getValidateCode();
    });
    //验证码回车事件
    $("#validateCode").keyup(function (e) {
        if(e.keyCode === 13) {
            loginSubmit();
        }
    });
}

function getValidateCode() {
    $("#validateCode").css("background" , "url('../statics/assets/images/loading.gif') no-repeat scroll 97% center");
    var url = "verificationCode.html?" + Math.random();
    var image = new Image(160 , 140);
    image.src = url;
    image.onload = function(){
        $("#validateCode").css("background" , "url('" + url +"') no-repeat scroll 97% center");
    };
}

function loginSubmit() {
    $.clearUserInfo();
    $.ajaxRequest({
        url: "/system/login",
        data: $.formToValues("loginForm_id"),
        success: function (result) {
            $.setUserInfo(result.data);
            window.location.reload(true);
        },
        callback: function () {
            getValidateCode();//重新加载验证码
            $("#validateCode").val("");
        }
    });
}