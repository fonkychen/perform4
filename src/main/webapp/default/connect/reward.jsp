<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="bg clearfix">
        <h2>飞鸽传书 〉 悬赏榜</h2>
        <div class="blank20"></div>
        <div class="keepcenter-wrap clearfix">
            <div class="keepcenter"><button class="btn-red" onclick="window.location.href='/connect/reward/new.html'">发布悬赏</button></div>
        </div>
        <div class="clearfix"></div>
        <p class="p-right">揭下悬赏榜，赚取U币奖励！</p>
        <table class="table-ub table-rewrad">
            <tr>
                <th width="14%">发布时间</th>
                <th width="14%">悬赏人</th>
                <th width="30%">悬赏内容</th>
                <th width="14%">截止日</th>
                <th width="14%">奖励</th>
                <th width="14%">状态</th>
            </tr>
          <c:forEach items="${page.content }" var="reward">
           <tr>
                <td><fmt:formatDate value="${reward.publishDate }" pattern="yyyy-MM-dd"/></td>
                <td>${reward.byUser.name }</td>
                <td align="left">${reward.title }</td>
                <td><fmt:formatDate value="${reward.endDate }" pattern="yyyy-MM-dd"/> </td>
                <td>${reward.coinNum }UB</td>
                <td><a href="/connect/reward/item-${reward.id }.html" class="a-ok">
                 <c:choose>
                   <c:when test="${reward.rewardStatus eq 'OnGoing' }">
                    正在进行中
                   </c:when>
                   <c:otherwise>
                   查看
                   </c:otherwise>
                 </c:choose>
                </a></td>
            </tr>
          </c:forEach>      
         
        </table>
        <c:import url="/default/common/page-index.jsp"></c:import>
        <dl class="tips">
            <dt>悬赏榜概述：</dt>
            <dd>1、所有玩家均可发布UB悬赏任务。</dd>
            <dd>2、任务有时间期限，到截止日自动结束。</dd>
            <dd>3、有回复有采纳满意答案的问题为完整提问。</dd>
        </dl>
        <dl class="tips">
            <dt>悬赏奖励：</dt>
            <dd>1、悬赏人挑选满意答案，采纳后发放UB奖励，悬赏人可获得3UB参与分。</dd>
            <dd>2、奖励的UB有悬赏人发放，如：任务奖励为5UB，采纳2种答案，即扣除悬赏人10UB。</dd>
        </dl>
    </div>
