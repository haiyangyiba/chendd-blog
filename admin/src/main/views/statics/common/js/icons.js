var iconBox = document.getElementById("icons-list");
var icons = iconBox.childNodes;
for(var i=0 ; i < icons.length ; i++) {
    if(icons[i].nodeName === "DIV") {
        (function (i) {
            icons[i].onclick = function(){
                selectedIcon(icons[i]);
            }
        })(i);
    }
}

function selectedIcon(icon) {
    //菜单新增页面
    var win = window.parent.$("iframe[id^='iframe_/system/menu/menu.html']");
    if(win.length === 1) {
        var menuIcon = win[0].contentWindow.document.getElementById("menuIcon_add_id");
        menuIcon.value = window.parent.$.trim(icon.innerText);
        var iconHTML = "<i class=\"" + menuIcon.value + "\"></i>";
        win[0].contentWindow.document.getElementById("menuIconView_add_id").innerHTML = iconHTML;
        $.closeWindow();
    } else {
        //菜单修改页面
        win = window.parent.$("iframe[id^='iframe_/system/menu/']");
        var menuIcon = win[0].contentWindow.document.getElementById("menuIcon_add_id");
        menuIcon.value = window.parent.$.trim(icon.innerText);
        var iconHTML = "<i class=\"" + menuIcon.value + "\"></i>";
        win[0].contentWindow.document.getElementById("menuIconView_add_id").innerHTML = iconHTML;
        $.closeWindow();
    }
}