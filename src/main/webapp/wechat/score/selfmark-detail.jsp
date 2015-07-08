<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<div class="wrap-page">
		<h2>主管点评</h2>
		<section>
			<div class="mark-manager clearfix">
			  <c:if test="${not empty comment }">
			  
				<span class="fl number">${comment.scoreNum  }</span>
				<p class="fr">
					<span class="time"><fmt:formatDate value="${comment.updated }" pattern="yyyy-MM-dd"/></span>
					<span>${comment.taskDescription }</span>
				</p>
			  </c:if>
			</div>
		</section>
		
		<h2>每日自评</h2>
		<section>
			<ul class="mark-self">
			 
			  <c:forEach items="${selfmarks }" var="selfmark" varStatus="status">
			   <c:if test="${not empty selfmark.actualScored }">   
			    <li>
					<p>${selfmark.yearNum }-${selfmark.monthNum }-${selfmark.dayNum }  <fmt:formatDate value="${selfmark.updated}" pattern="E"/></p>
					<div class="score clearfix">
						<span class="fl number">${selfmark.actualScored }</span>
						<dl class="fr score-detail">
						  <c:forEach items="${selfmark.selfDailyMarks }" var="dailymark">
						    <dt>${dailymark.markCategory.name }</dt>
							<c:forEach begin="1" end="${dailymark.starNum }">
							 <dd class="light"></dd>
							</c:forEach>
							<c:forEach begin="${dailymark.starNum +1}" end="5">
							  <dd></dd>
							</c:forEach>
						  </c:forEach>				
						
						</dl>
					</div>
					<ol class="dailyReport-display">
                     ${selfmark.taskDescription }
                   </ol>
				</li>
			   </c:if>
			  </c:forEach>
				
				
			</ul>
		</section>
	</div>
