<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
$(document).ready(function(){
	var viewname='${viewname}';
	
	var vns=viewname.split("_");
	
	$("li[id^='"+vns[0]+"_"+vns[1]+"']").attr("class","thispage");
});

</script>
<div class="navbar bg">
        <ul class="nav-lv1 clearfix">
            <li>
                <ul class="nav-lv2 clearfix">
                    <li>功勋英雄：</li>
                    <li id="staff_dailyboard"><a href="/staff/dailyboard.html">公事榜</a></li>
                    <li id="staff_selfmark"><a href="/staff/selfmark.html">每日自评</a></li>
                    <c:if test="${(empty user.userGroup) or (not empty user.ownerGroup)  }">
                     <li id="staff_comment"><a href="/staff/comment.html">主管点评</a></li>
                    </c:if>
                    
                    <li id="staff_indicator"><a href="/staff/indicator.html">指标进度</a></li>
                    <li id="staff_random"><a href="/staff/random.html">有缘点评</a></li>
                </ul>
            </li>
            <li>
                <ul class="nav-lv2 clearfix">
                    <li>趣多多：</li>
                    <li id="funny_mall"><a href="/funny/mall/index.html">碰碰运气</a></li>
                    <li id="funny_money"><a href="/funny/money/countrywealth.html">数数小钱</a></li>
                    <li id="home_toprank"><a href="/home/toprank/userScored.html">人气排排坐</a></li>
                </ul>
            </li>
            <li>
                <ul class="nav-lv2 clearfix">
                    <li>飞鸽传书：</li>
                    <li id="connect_reward"><a href="/connect/reward/index.html">悬赏榜</a></li>
                    <li id="connect_task"><a href="/connect/task/index.html">任务中心</a></li>
                    <li id="connect_drum"><a href="/connect/drum/index.html">得胜鼓</a><sup>N</sup></li>
                    
                </ul>
            </li>
            <li>
                <ul class="nav-lv2 clearfix">
                    <li>我是中心：</li>
                    <li id="service_mycenter"><a href="/service/mycenter/pinfo.html">我的主页</a></li>
                    <li id="chart_statis"><a href="/chart/statis//userscore.html">数据统计</a></li>
                   
                </ul>
            </li>
        </ul>
        <div class="fr">
            <a class="username fl">${user.nickName }</a>
            <a class="userheader fl"><img src="/common/usericon.html?iconId=${user.userIcon.id }"></a>
            <p class="userscore fl">功勋值：${user.userScored }
            <br>财富值：${user.userCoins }
            <br>人气值：${user.userPopular }</p>
        </div>
    </div>
