<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <div class="bg clearfix">
        <ul class="notice-all">
          <c:forEach items="${page.content }" var="message">
           <li><a>【${message.messageType.toString() }】：${message.content }</a><span><fmt:formatDate value="${message.updated }" pattern="yyyy-MM-dd"/></span></li>
          </c:forEach>           
        </ul>
      
        <c:import url="/default/common/page-index.jsp"></c:import>
    </div>
