$(function () {
    //初始化事件
    initEvent();
    //初始化图表
    initChart();
});

function initChart() {
    //由于图表区域初始化时为隐藏区域，故设置图表区域的宽高，否则echarts因隐藏元素导致的图表不显示
    var width = $("#report_box_id").width();
    var height = $("#report_box_id").height() - 15;
    $("#report_box_id .slide-item-report").css("width" , width + "px").css("height" , height + "px");
    var allData = document.getElementById("reportSource_data_id").value;
    eval("allData = " + allData + ";");
    //用户来源统计
    userSourceChart(allData);
    //年份数量统计
    articleYearsChart(allData);
    //访问量前N名统计
    articleVisitsChart(allData);
    //各种数量统计
    amountNumbersChart(allData);
}

function initEvent() {
    //加载更多文章
    $("#loadMoreArticle_id").click(function () {
        var button = $(this);
        var pageNumber = parseInt(button.data("pageNumber")) + 1;
        $.ajaxRequest({
            url: "/blog/article/" + pageNumber + ".html" , type: "post",
            useWait: false,
            beforeSend: function(xhr) {
                $("#loadMoreArticle_id").hide();
                $("#loadMoreArticle_status_id").show();
            },
            success: function (result) {
                $("#loadMoreArticle_id").show();
                $("#loadMoreArticle_status_id").hide();
                var list = result.data;
                var lens = list.length;
                if (lens === 0) {
                    return;
                }
                button.data("pageNumber" , pageNumber);
                var html = $.template("article_template_id" , {data: list});
                $("#loadMoreArticle").before(html);
            },
            error: function (a ,b , c) {
                $("#loadMoreArticle_id").show();
                $("#loadMoreArticle_status_id").hide();
            }
        });
    });
}

function userSourceChart(allData) {
    var myChart = echarts.init(document.getElementById("userSources_report_id"));
    var legendDatas = [];
    var datas = allData["userSources"];
    for(var i=0 , lens = datas.length ; i < lens ; i++){
        legendDatas[i] = datas[i].name;
    }
    var option = {
        title : {
            text : '用户来源统计',
            subtext : '更新频率：每天',
            x : 'center'
        },
        tooltip : {
            trigger : 'item',
            formatter : "{a} <br/>{b} : {c} ({d})"
        },
        legend : {
            orient : 'vertical',
            left : 'left',
            data : legendDatas
        },
        series : [
            {
                name : '分类明细',
                type : 'pie',
                radius : '75%',
                center : [ '50%', '60%' ],
                data : datas,
                itemStyle : {
                    normal: {label : {show: true}},
                    emphasis : {
                        shadowBlur : 10,
                        shadowOffsetX : 0,
                        shadowColor : 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart.setOption(option, true);
}

function articleYearsChart(allData) {
    var myChart = echarts.init(document.getElementById("articleYears_report_id"));
    var legendDatas = [];
    var datas = allData["articleYears"];
    for(var i=0 , lens = datas.length ; i < lens ; i++){
        legendDatas[i] = datas[i].name;
    }
    var option = {
        title : {
            text : '年份数量统计',
            subtext : '更新频率：每小时',
            x : 'center'
        },
        tooltip: {},
        xAxis : {
            type : 'category',
            data : legendDatas
        },
        yAxis : {
            type : 'value'
        },
        series : [ {
            data : datas,
            itemStyle : { normal: {label : {show: true}}},
            type : 'line',
            smooth : true
        } ]
    };
    myChart.setOption(option, true);
}

function articleVisitsChart(allData) {
    var myChart = echarts.init(document.getElementById("articleVisits_report_id"));
    var datas = allData["articleVisits"];
    var legendDatas = [];var items = [];
    for(var i=0 , lens = datas.length ; i < lens ; i++){
        legendDatas[i] = datas[i].name;
        items[i] = datas[i].value;
    }
    var option = {
        title : {
            text : '访问量前N名',
            subtext : '更新频率：每天'
        },
        tooltip : {
            trigger : 'axis',
            axisPointer : {
                type : 'shadow'
            }
        },
        legend : {
            data : [ '前N名' ]
        },
        grid : {
            left : '3%',
            right : '4%',
            bottom : '3%',
            containLabel : true
        },
        xAxis : {
            type : 'value',
            boundaryGap : [ 0, 0.01 ]
        },
        yAxis : {
            type : 'category',
            data : legendDatas
        },
        series : [{
            name : '前N名',
            type : 'bar',
            data : items,
            itemStyle: {
                normal: {
                    label: {
                        show:true, position: "right"
                    }
                }
            }
        }]
    };
    myChart.setOption(option, true);
}

function amountNumbersChart(allData) {
    var myChart = echarts.init(document.getElementById("amountNumbers_report_id"));
    var datas = allData["amountNumbers"];
    var legendDatas = [];var items = [];
    for(var i=0 , lens = datas.length ; i < lens ; i++){
        legendDatas[i] = datas[i].name;
        items[i] = datas[i].value;
    }
    var option = {
        title : {
            text : '各种数量统计',
            subtext : '更新频率：每天'
        },
        tooltip : {},
        legend : {
            data : [ '数量' ]
        },
        xAxis : {
            data : legendDatas
        },
        yAxis : {},
        series : [ {
            name : '数量',
            type : 'bar',
            data : items,
            itemStyle : { normal: {label : {show: true}}},
        } ]
    };
    myChart.setOption(option, true);
}

function showTags(element) {
    $.openWindow("<b class='p-0 m-0 font-20'>" + element.innerText + "</b>" , element.href , {
        height: "360px",
        buttons: {
            cancel: {
                label: "<i class=\"fe-x\"></i>&nbsp;取消",
                className: "btn-primary"
            }
        }
    });
    return false;
}
