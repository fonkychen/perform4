<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty page }"> 
 
 <ul class="page clearfix">
   <c:choose>
     <c:when test="${page.hasPrevious() }">
        <li><a href="?order=${order }&page=${page.number+1-1}">&lt; &nbsp; &nbsp; &nbsp; 上一页</a></li>
     </c:when>
     <c:otherwise>
        <li></li>
     </c:otherwise>
   </c:choose>
 
   <c:forEach var="pnum" begin="1" end="${page.totalPages }">
     <c:choose>
       <c:when test="${(pnum eq 1)  or (pnum eq page.totalPages)}">
         <li><a <c:if test="${pnum eq (page.number+1) }">class="thispage"</c:if> href="?order=${order }&page=${pnum}">${pnum }</a></li>         
       </c:when>
       <c:when test="${(pnum-(page.number+1)== -4) or (pnum-(page.number+1)==4) }">
         <li><a <c:if test="${pnum eq (page.number+1) }">class="thispage"</c:if> href="?order=${order }&page=${pnum}">...</a></li>         
       </c:when>
       <c:when test="${(pnum-(page.number+1)>-4) and (pnum-(page.number+1)<4)}">
         <li><a <c:if test="${pnum eq (page.number+1) }">class="thispage"</c:if> href="?order=${order }&page=${pnum}">${pnum }</a></li>         
       </c:when>       
     </c:choose>
     <c:if test="${(pnum eq 1)  || (pnum eq page.totalPages)}">
      
     </c:if>     
    
   </c:forEach>
   <c:choose>
     <c:when test="${page.hasNext() }">
     <li class="last-child"><a href="?order=${order }&page=${page.number+1+1}">下一页 &nbsp; &nbsp; &nbsp; &gt;</a></li>
     </c:when>
     <c:otherwise>
     <li></li>
     </c:otherwise>
   </c:choose>
   
   
 </ul>
 </c:if>