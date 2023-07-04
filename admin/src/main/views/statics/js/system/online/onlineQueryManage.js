$(function () {
    initElement();
    initEvent();
});

function initEvent() {
    window.editor = CodeMirror.fromTextArea(document.getElementById("code_template_id"), {
        mode: "text/x-sql",
        indentWithTabs: true,
        height: 100,
        smartIndent: true,
        lineNumbers: true,
        matchBrackets : true,
        autofocus: true,
        extraKeys: {"Ctrl-Space": "autocomplete"}
    });
    $("#eclipse_theme_id").click(function () {
        window.editor.setOption("theme", "eclipse");
    });
    $("#idea_theme_id").click(function () {
        window.editor.setOption("theme", "idea");
    });
    $("#clear_btn_id").click(function () {
        $.confirm("确定是要 【清空】 吗？" , function (flag){
            if (flag === true) {
                window.editor.setValue("");
                $("#grid_table").datagrid("removeAll");
            }
        });
    });
    $("#execute_btn_id").click(function () {
        var content = window.editor.getValue();
        $.confirm("确定是要 【执行】 吗？" , function (flag){
            if (flag === true) {
                executeSubmit(content);
            }
        });
    });
}

function initElement() {

    $("#grid_table").datagrid({
        toolbar: "#grid_toolbar",
        columns: [

        ],
        pagination: true,
        sidePagination: 'true'
    });
}

function executeSubmit(content) {
    $.ajaxRequest({
        url: "/system/online/query.html", type: "post",
        data: {
            "editorContent" : content
        },
        success: function (result) {
            fillDatagrid(result.data);
        }
    });
}

function fillDatagrid(data) {
    if (data.total === 0) {
        return;
    }
    var columns = [];
    columns.push({"title" : "序号" , "rownum" : true , "align" : "center" ,
        formatter: function (value , rowData , index) {
            return index + 1;
        }
    });
    var item = data.records[0];
    for (var key in item) {
        columns.push({"title" : key , "field": key});
    }
    var height = $(window).height() - $(".CodeMirror").height() - 60;
    $("#grid_table").datagrid("refreshOptions" , {
         "height" : height,
         "columns" : columns ,
         "data" : data.records
    });
}