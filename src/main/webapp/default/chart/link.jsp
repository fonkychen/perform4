<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta name="description" content="KPI互动社区，欢乐工作每一天。" />
<meta name="keywords" content="KPI,诸侯争霸,功勋簿,绩效英雄,财富英雄,人气英雄" />
<link href="/default/css/base.css" rel="stylesheet">
<link href="/default/css/common.css" rel="stylesheet">
<link href="/default/css/layout-set.css" rel="stylesheet">

<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/js/kpi.js"></script>
<script type="text/javascript" src="/js/dateRange.js"></script>
<script type="text/javascript" src="/js/highcharts.js"></script>
<script type="text/javascript" src="/js/json2.js"></script>
<script type="text/javascript">
    window.onload = function() {
        var oDiv = document.getElementById("userType");
        var oLi = oDiv.getElementsByTagName("div")[0].getElementsByTagName("li");//tab中第1个div中的li标签
        var aCon = oDiv.getElementsByTagName("div")[1].getElementsByTagName("ul");//tab中第2个div中的ul标签
        var timer = null;
        for (var i = 0; i < oLi.length; i++) {
            oLi[i].index = i;
            oLi[i].onclick = function() {
                show(this.index);
            }
        }
        function show(a) {
            index = a;
            var alpha = 0;
            for (var j = 0; j < oLi.length; j++) {
                oLi[j].className = "";
                aCon[j].className = "name";
                aCon[j].style.display = "none";
            }
            oLi[index].className = "thispage";
            clearInterval(timer);
            timer = setInterval (
                function timer() {
                    aCon[index].style.display = "block";
                    clearInterval(timer);
                },5
            )
        }
    }
</script>

