<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <div class="bg clearfix">
        <h2>飞鸽传书 〉 <a href="/connect/reward/index.html">悬赏榜</a> 〉 揭榜</h2>
        <div class="w94p">
            <a href="javascript:window.history.go(-1);" class="btn-back">返回</a>
            <hr class="line-back" />

            <div class="reward-question clearfix">
                <div class="fl">
                    <p class="reward-question-top">
                        <span class="icon-question">${reward.title }</span>
                        <span class="fr">悬赏：${reward.byUser.name } &nbsp; | &nbsp; 日期：<fmt:formatDate value="${reward.publishDate }" pattern="yyyy-MM-dd"/> 至 <fmt:formatDate value="${reward.endDate }" pattern="yyyy-MM-dd"/> &nbsp; | &nbsp; 奖励：${reward.coinNum }UB</span>
                    </p>
                    <p>${reward.description }</p>
                </div>
                <div class="fr"><div class="blank20"></div><a>领取</a></div>
                <textarea class="fl reward-text"></textarea>
                <div class="fl"><button class="btn-blue fr">回复</button></div>
            </div>

            
            <div class="blank20"></div>
        </div>
    </div>
