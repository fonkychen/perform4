<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="bg clearfix">
        <h2>飞鸽传书 〉 任务中心</h2>
        <div class="blank20"></div>
        <div class="keepcenter-wrap clearfix">
            <div class="keepcenter">
             <sec:authorize ifAnyGranted="ROLE_ADMIN"></sec:authorize>
              <button class="btn-red" onclick="window.location.href='/connect/task/new.html'">发布任务</button>
             </div>
        </div>
        <div class="clearfix"></div>
        <table class="table-ub table-rewrad">
            <tr>
                <th width="12%">发布时间</th>
                <th width="12%">悬赏人</th>
                <th width="12%">类型</th>
                <th width="28%">详情</th>
                <th width="12%">截止日</th>
                <th width="12%">奖励</th>
                <th width="12%">状态</th>
            </tr>
          <c:forEach items="${page.content }" var="task">
            <tr>
                <td><fmt:formatDate value="${task.publishDate }" pattern="yyyy-MM-dd"/></td>
                <td>${task.user.name }</td>
                <td>
                  <c:choose>
                    <c:when test="${task.taskType eq 'None' }">
                     快抢任务
                    </c:when>
                    <c:otherwise>
                    循环任务
                    </c:otherwise>
                  </c:choose>
                </td>
                <td align="left">${task.title }</td>
                <td></td>
                <td>${task.coinNum }UB</td>
                <td><a href="/connect/task/item-${task.id }.html" class="a-ok">新任务</a></td>
            </tr>
          </c:forEach>   
            
          
        </table>
        <c:import url="/default/common/page-index.jsp"></c:import>
        <p class="p-right">如没有在截止日前审核，UB将无法奖励给接收人！</p>
        <div class="blank20"></div>
    </div>

