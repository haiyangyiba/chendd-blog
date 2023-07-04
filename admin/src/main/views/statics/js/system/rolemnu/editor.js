$(function () {
    initElement();
});

function initElement() {

}

function getData() {
    var data = $("#dataList_hidden_id").html();
    return eval(data);
}

$(function () {
    $("#treeview-checkable").treeview({
        data: getData(),
        showIcon: true,
        levels: 5,
        showCheckbox: true,
        uncheckedIcon: "far fa-square",//未选中时图标
        checkedIcon: "far fa-check-square",
        //selectedIcon: "far fa-check-square",//选中某行时的图标
        //nodeIcon: "fa fa-hand-o-right",//节点默认增加图标
        collapseIcon: "fas fa-chevron-down", //展开时显示图标
        // selectedIcon: "fa fa-checked-square-o",
        expandIcon: "fas fa-chevron-right", //折叠时显示图标
        //backColor: "red",//背景色
        selectedColor: "rgb(0,0,0)",
        selectedBackColor: "rgb(245,245,245)",
        //emptyIcon: "fa fa-file-o",//叶子级别显示图标
        showTags: true,
        onNodeChecked: function (event, node) {
            checkAllParent(node);
        },
        onNodeUnchecked: function (event, node) {
            uncheckAllParent(node);
            uncheckAllSon(node);
        }

    });
});

//选中全部父节点
function checkAllParent(node) {
    $('#treeview-checkable').treeview('checkNode', node.nodeId, { silent: true });
    var parentNode = $('#treeview-checkable').treeview('getParent', node.nodeId);
    if (!("nodeId" in parentNode)) {
        return;
    } else {
        checkAllParent(parentNode);
    }
}
//取消全部父节点
function uncheckAllParent(node) {
    $('#treeview-checkable').treeview('uncheckNode', node.nodeId, { silent: true });
    var siblings = $('#treeview-checkable').treeview('getSiblings', node.nodeId);
    var parentNode = $('#treeview-checkable').treeview('getParent', node.nodeId);
    if (!("nodeId" in parentNode)) {
        return;
    }
    var isAllUnchecked = true;  //是否全部没选中
    for (var i in siblings) {
        if (siblings[i].state.checked) {
            isAllUnchecked = false;
            break;
        }
    }
    if (isAllUnchecked) {
        uncheckAllParent(parentNode);
    }
}

//级联选中所有子节点
function checkAllSon(node) {
    $('#treeview-checkable').treeview('checkNode', node.nodeId, { silent: true });
    if (node.nodes != null && node.nodes.length > 0) {
        for (var i in node.nodes) {
            checkAllSon(node.nodes[i]);
        }
    }
}
//级联取消所有子节点
function uncheckAllSon(node) {
    $('#treeview-checkable').treeview('uncheckNode', node.nodeId, { silent: true });
    if (node.nodes != null && node.nodes.length > 0) {
        for (var i in node.nodes) {
            uncheckAllSon(node.nodes[i]);
        }
    }
}

function expandAll() {
    $('#treeview-checkable').treeview('expandAll');
}

function collapseAll() {
    $('#treeview-checkable').treeview('collapseAll');
}

function checkAll() {
    $('#treeview-checkable').treeview('checkAll');
}

function uncheckAll() {
    $('#treeview-checkable').treeview('uncheckAll');
}

function submitForm() {
    var roleId = $("#roleId_add_id").text();
    var checkeds = $('#treeview-checkable').treeview('getChecked');
    if(checkeds.length === 0) {
        $.confirm("当前没有选中项，确定是要清空所选数据吗？" , function (r) {
            if(r === true){
                saveData(roleId , "");
            }
        });
        return;
    }
    var menuIds = "";
    for(var i=0 ; i < checkeds.length ; i++) {
        var item = checkeds[i];
        menuIds += item.id + ",";
    }
    menuIds = menuIds.substring(0 , menuIds.length - 1);
    saveData(roleId , menuIds);
}

function saveData(roleId , menuIds) {
    var param = "?menuIds=" + menuIds;
    $.ajaxRequest({
        url: "/system/rolemenu/" + roleId + ".html" + param, type: "put",
        success: function (result) {
            $.alert.success("保存成功，共保存 " + result.data + " 条数据！" , refreshDatagrid);
            return true;
        }
    });
}

function refreshDatagrid() {
    $.closeWindow();
    var src = window.parent.$('.J_menuTab.active').data("id");
    window.parent.$("iframe[src='" + src + "'")[0].contentWindow.document.getElementById("query_btn_id").click();
}