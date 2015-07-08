<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <div class="bg clearfix">
    	<h2>功勋英雄 〉<a href="/staff/selfmark.html">每日自评</a> 〉 功勋历史</h2>
        <div class="keepcenter-wrap">
            <div class="keepcenter pagingdate">
                <span class="blod">查询历史记录</span>
                <a href="/staff/selfmark/detail.html?yearNum=${preyear }&weekOfYear=${preweekofyear}" class="arrow blue"></a><!--不可点击时class里去掉blue-->
                <div class="date">${weekstring }</div>
                <a href="/staff/selfmark/detail.html?yearNum=${nextyear }&weekOfYear=${nextweekofyear}" class="arrow right blue rightblue"></a><!--不可点击时class里去掉blue和rightblue-->
                <span class="blod"><a href="/staff/selfmark/detail.html">回本周</a></span>
                <!-- 
                <button class="btn-blue">数据导出</button>
                 -->
            </div>
        </div>
        <div class="clearfix"></div>
        <table class="kpidetail">
            <thead>
                <tr>
                    <th width="14%">日 期</th>
                    <th width="14%">自评分</th>
                    <th width="72%">工作报告</th>
                </tr>
            </thead>
            <tbody>
                
                <tr>
                 <c:if test="${not empty comment }">
                    <td><fmt:formatDate value="${comment.updated }" pattern="yyyy-MM-dd"/></td>
                    <td><b>${comment.scoreNum }</b></td>
                    <td>${comment.taskDescription }</td>
                 </c:if>
                    
                </tr>
            </tbody>
        </table>
        <div class="blank20"></div>
        <table class="kpidetail">
            <thead>
                <tr>
                    <th width="14%">日 期</th>
                    <th width="14%">自评分</th>
                    <th width="58%">工作报告</th>
                    <th width="14%">星评</th>
                </tr>
            </thead>
            <tbody>
               <c:forEach items="${selfmarks }" var="selfmark">
                 <tr>
                    <td><fmt:formatDate value="${selfmark.updated }" pattern="yyyy-MM-dd"/><br /><fmt:formatDate value="${selfmark.updated}" pattern="E"/></td>
                    <td><b>${selfmark.actualScored }</b></td>
                    <td>
                        <p>
                         <c:forEach items="${selfmark.dailyIndicators }" var="indicator" varStatus="status">
                          KPI.${status.count } : ${indicator.name }&nbsp;
                         </c:forEach>                         
                        </p>
                        <ol>
                           ${selfmark.taskDescription }
                        </ol>
                    </td>
                    <td>
                    <c:forEach items="${selfmark.selfDailyMarks }" var="dailymark">
                     <p><span>${dailymark.markCategory.name }：</span>${dailymark.starNum } 星</p>
                    </c:forEach>
                   
                    </td>
                </tr>
               </c:forEach>
                
               
            </tbody>
        </table>
    	<div class="blank50"></div>
        <div class="keepcenter-wrap">
            <div class="keepcenter"><button class="btn-blue" onclick="window.location.href='/chart/statis/userscore.html'">查看统计</button></div>
        </div>
        <div class="blank100"></div><div class="blank100"></div>
    </div>

