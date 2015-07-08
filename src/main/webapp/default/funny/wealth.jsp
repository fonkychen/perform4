<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="bg clearfix">
        <h2>趣多多 〉 <a href="/funny/money/countrywealth.html">数数小钱</a></h2>
        <ul class="myub-nav clearfix">
            <li><a href="/funny/money/countrywealth.html">国家财富</a></li>
            <li><a href="/funny/money/editcountrywealth.html">支出录入</a></li>
            <li><a class="thistab" href="/funny/money/wealth.html">个人财富</a></li>
        </ul>

       
        <!--个人财富-->
        <ul class="myub-nav2 clearfix">
            <li><span>今日收入</span><span><c:choose><c:when test="${empty dailyincome}">0</c:when><c:otherwise> ${dailyincome }</c:otherwise></c:choose>UB</span></li>
            <li><span>今日支出</span><span><c:choose><c:when test="${empty dailyoutcome }">0</c:when><c:otherwise> ${dailyoutcome }</c:otherwise></c:choose>UB</span></li>
            <li><span>金额总计</span><span><c:choose><c:when test="${empty yearlycoins }">0</c:when><c:otherwise> ${yearlycoins }</c:otherwise></c:choose>UB</span></li>
        </ul>
        <table class="table-ub">
            <tr>
                <th width="30%">时间</th>
                <th width="20%">收支详情</th>
                <th width="20%">收入</th>
                <th width="30%">支出</th>
            </tr>
           <c:forEach items="${page.content }" var="history">
            <tr>
                <td><c:if test="${not empty history.created }"><fmt:formatDate value="${history.created }" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
                <td>${history.coinType.toString() }</td>
                <td><c:if test="${history.balanceType eq 'Income' }">${history.coinNum }UB</c:if></td>
                <td><c:if test="${history.balanceType eq 'Outcome' }">${history.coinNum }UB</c:if></td>
            </tr>
           </c:forEach>
          
        </table>
        <!--个人财富end-->

        <!--翻页-->
      <c:import url="/default/common/page-index.jsp"></c:import>
    </div>
