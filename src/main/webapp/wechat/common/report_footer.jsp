<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal.user" var="user"/>
<footer class="footer fixed-btm">
		<ul>
		 <c:choose>
		   <c:when test="${((not empty user.title) and (not empty user.title.titleGroup) and (user.title.titleGroup.level <= 2) ) }">
		    <li class="w33"><a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri=http%3A%2F%2Fwww.kpi365.com%2Fwechat%2Fviewreport.html%3FreportDate=${preday }&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect">查看前一天</a></li>
		    <li class="w33"><a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri=http%3A%2F%2Fwww.kpi365.com%2Fwechat%2Fviewreport.html%3FreportDate=${today }&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect">查看当日日报</a></li>		   
		    <li class="w33"><a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri=http%3A%2F%2Fwww.kpi365.com%2Fwechat%2Fteamreport.html&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect">发布日报</a></li>
		   		   
		   </c:when>
		   <c:otherwise>
		    <li class="w50"><a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri=http%3A%2F%2Fwww.kpi365.com%2Fwechat%2Fviewreport.html%3FreportDate=${preday }&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect">查看前一天</a></li>
		    <li class="w50"><a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri=http%3A%2F%2Fwww.kpi365.com%2Fwechat%2Fviewreport.html%3FreportDate=${today }&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect">查看当日日报</a></li>		      
		   </c:otherwise>
		 </c:choose>		 
		</ul>
	</footer>