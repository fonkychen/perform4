<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <div class="bg clearfix">
    	<h2>趣多多 〉 <a href="/funny/mall/index.html">碰碰运气</a> 〉 兑换记录</h2>
    	
        <table class="table-ub">
            <tr>
                <th width="8%">&nbsp;</th>
                <th width="14%">姓名</th>
                <th width="20%">兑换物品</th>
                <th width="14%">所需UB</th>
                <th width="22%">兑换时间</th>
                <th width="22%">实物兑换</th>
            </tr>
           <c:forEach items="${page.content }" var="order" varStatus="status">
            <tr>
                <td>${(page.size*page.number)+(status.count) }</td>
                <td>${order.orderUser.name }</td>
                <td>${order.mallProduct.name }</td>
                <td>${order.coinNum }</td>
                <td><fmt:formatDate value="${order.updated }" pattern="yyyy-MM-dd HH:mm"/></td>
                <td><c:if test="${not empty order.checkedTime }"><fmt:formatDate value="${order.checkedTime }" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
            </tr>
           
           </c:forEach>
        </table>
       <c:import url="/default/common/page-index.jsp"></c:import>
    </div>
 