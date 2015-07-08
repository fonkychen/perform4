<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(document).ready(function(){
	$('#${viewname}').attr("class","thistab");
});
</script>
<ul class="myub-nav clearfix">
                <li><a id="service_mycenter_achieve" href="/service/mycenter/achieve.html">我的成就</a></li>
                <!-- 
                <li><a href="#">我的勋章</a></li>
                 -->
                <li><a id="service_mycenter_titles" href="/service/mycenter/titles.html">官职俸禄</a></li>
                <li><a id="service_mycenter_organization" href="/service/mycenter/organization.html">组织架构</a></li>
                <li><a id="service_mycenter_headicon" href="/service/mycenter/headicon.html">更换头像</a></li>
                <li><a id="service_mycenter_pinfo" href="/service/mycenter/pinfo.html">昵称密码</a></li>
            </ul>