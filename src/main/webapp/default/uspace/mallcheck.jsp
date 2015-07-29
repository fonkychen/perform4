<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="w94p">
    		<h3><span class="fl">商城审核</span><span>共2个</span></h3>
    		<hr class="line-back clearfix">
    	</div>    	

		<table class="table-ub">
            <tr>
                <th width="8%">序号</th>
                <th width="14%">姓名</th>
                <th width="20%">兑换物品</th>
                <th width="14%">所需UB</th>
                <th width="22%">兑换时间</th>
                <th width="22%">实物兑换</th>
            </tr>
            <c:forEach var="order" items="${mallorders }" varStatus="status">
             <tr>
               <td>${status.count }</td>
                <td>${order.orderUser.name }</td>
                <td>${order.mallProduct.name }</td>
                <td>${order.coinNum }</td>
                <td><fmt:formatDate value="${order.updated }" pattern="yyyy-MM-dd HH:mm"/></td>
                <td><c:if test="${not empty order.checkedTime }"><fmt:formatDate value="${order.checkedTime }" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
             </tr>
            </c:forEach>
            
           
        </table>

    	<div class="blank20"></div>
