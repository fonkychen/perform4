<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="wrap-page">
<c:forEach items="${companyReports}" var="report">
  <section>
			<div class="blank20"></div>
			<p class="teamreport-title2 padding20"><fmt:formatDate value="${report.updated }" pattern="yyyy年M月d日"/>产品日报——${report.byUser.name }</p>
			<div class="summary">
				${report.summary }
			</div>
			<ol class="teamreport-ol">
			    <c:forEach items="${report.items }" var="item">
			     <li>${item.description }</li>
			    </c:forEach>
			</ol>
			<div class="blank20"></div>
		</section>
</c:forEach>
<c:forEach items="${countryReports}" var="report">
   <section>
			<div class="blank20"></div>
			<p class="teamreport-title2 padding20"><fmt:formatDate value="${report.updated }" pattern="yyyy年M月d日"/>产品日报——${report.byUser.name }</p>
			<div class="summary">
				${report.summary }
			</div>
			<ol class="teamreport-ol">
			    <c:forEach items="${report.items }" var="item">
			     <li>${item.description }</li>
			    </c:forEach>
			</ol>
			<div class="blank20"></div>
		</section>
</c:forEach>

</div>