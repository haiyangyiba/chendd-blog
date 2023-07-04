$(function () {
    initElement();
    initEvent();
});

function createCropper() {
    //初始化头像裁切
    $("#userImage_id").cropper("destroy").cropper({
        aspectRatio: 3/3 ,//容器大小比例
        viewMode: 0,//自由选择
        autoCropArea: 0.4,//裁切框占图片大小比例
        dragMode: "move",//设置移动图片、重新绘制选图区域
        cropBoxResizable: false,
        toggleDragModeOnDblclick: false,//双击可调整选区大小
        movable: true,//是否允许移动裁切框
        zoomable: true,//是否允许放大图片
        guides: true,//取消显示裁切线中间的虚线网格
        minContainerWidth: 512,
        minContainerHeight: 320,
        minCanvasWidth: 200,
        minCanvasHeight: 200,
        minCropBoxWidth: 50,
        minCropBoxHeight: 50,
        crop: function(data){
        }
    });
    return $("#userImage_id");
}

function initElement() {
    //步骤
    $("#user_wizard").bootstrapWizard();
}

function initEvent() {

    $("#checkUser_btn_id").click(function () {
        var username = $("#username_add_id").val();
        if($.trim(username).length === 0) {
            $.alert("用户账号不能为空！");
            return;
        }
        $.ajaxRequest({
            url: "/system/user/account/" + username + ".html", type : "GET",
            success: function (result) {
                if(result.data === true) {
                    $.alert.success("用户账户可用！");
                } else {
                    $.alert("用户账户已经被使用！");
                }
            }
        });
    });

    //旋转
    $("#rotate_btn_id").click(function () {
        $("#userImage_id").cropper("rotate" , 90);
    });
    //左右翻转
    $("#scale_LR_btn_id").click(function () {
        window.scaleX = (window.scaleX / -1) || -1;
        $("#userImage_id").cropper("scaleX", window.scaleX);
    });
    //上下翻转
    $("#scale_UB_btn_id").click(function () {
        window.scaleY = (window.scaleY / -1) || -1;
        $("#userImage_id").cropper("scaleY", window.scaleY);
    });
    //确定裁切
    $("#confirm_btn_id").click(function () {
        if(window.cropperStatus === "end") {
            return;
        }
        var dataURL = $("#userImage_id").cropper("getCroppedCanvas" , {
            width: 128 , height: 128
        });
        var imgUrl = dataURL.toDataURL("image/png", 1);//将选区canvas转换为base64图片
        cropperEnd();
        $("#userImage_id").attr("src" , imgUrl);
    });

}

function cropperBegin() {
    window.cropperStatus = "begin";//定义全局变量，标记当前状态为开始裁切图片
    $(".tools_button").removeClass("disabled").find("label").css("cursor" , "pointer");
}

function cropperEnd() {
    $("#userImage_id").cropper("destroy");
    window.cropperStatus = "end";
    $(".tools_button").addClass("disabled").find("label").css("cursor" , "help");
}

function selectImage(fileInput) {
    var file = fileInput.files[0];
    if(file.size > 1024 * 1024) {
        $.alert("上传图片不能大于 1M!");
        return;
    }
    cropperBegin();
    var url = getObjectURL(file);
    createCropper().cropper("replace" , url);
}

//获取本地图片的地址，转换为http在线地址
function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL !== undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL !== undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL !== undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

function submitForm() {
    var params = $.formToValues("user_form_id");
    var userImagePath = $("#userImage_id").attr("src");
    if(userImagePath.substr(0 , 4) === "blob") {
        $.alert("所选图片还未完成裁切！");
        return;
    } else {
        params.userImage = userImagePath;
    }
    var userId = $("#userId_add_id").val();
    if(userId !== "") {
        params.username = $("#username_add_id").val();
    }
    $.ajaxRequest({
        url: "/system/user.html" , type: "PUT", data: JSON.stringify(params),
        success: function (result) {
            $.alert.success(result.data , refreshDatagrid);

        }
    });
}

function refreshDatagrid() {
    $.closeWindow();
    var src = window.parent.$('.J_menuTab.active').data("id");
    window.parent.$("iframe[src='" + src + "'")[0].contentWindow.document.getElementById("query_btn_id").click();
}