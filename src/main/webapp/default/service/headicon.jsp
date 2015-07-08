<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
function saveUserIcon(){
	
	 var iconId=$("#cusericon").attr('role');
	 if(iconId=='')return;
	 var oncallback=function(){
		window.location.reload();	
	 }
	 $.ajax({
		 url:'/rest/user/icon/'+iconId+'.html?dt'+new Date(),
		 type:'GET',
		 success:oncallback,
		 error:function(xhr){
			 alert(xhr.responseText);
		 }
	 });	 
	 
}

function onHeadClicked(iconId){
	 $('#cusericon').attr('role',iconId);
	 $('#cusericon').attr('src','/common/usericon.html?iconId='+iconId)
}
</script>
<div class="bg clearfix">
       <h2><a>个人中心</a> 〉我的成就</h2>
        <c:import url="/default/service/pbanner.jsp"></c:import>
        <div class="myinfo-wrap">
        <c:import url="/default/service/bmenu.jsp"></c:import>
            <div class="header-wrap">
                <p>当前头像</p>
                <img id="cusericon" role="${user.userIcon.id }" src="/common/usericon.html?iconId=${user.userIcon.id }">
                <p>选择新头像</p>
                <div class="headers-box">
                 <c:forEach items="${userIconGroups }" var="iconGroup">
                   <ul class="clearfix">
                    <c:forEach items="${iconGroup.userIconList }" var="userIcon">
                      <c:choose>
		                       <c:when test="${(empty user.userIcon) or (user.userIcon.id != userIcon.id) }">
		                         <li id="lihead_${userIcon.id }"><a href="javascript:onHeadClicked('${userIcon.id }')"><img src="/common/usericon.html?iconId=${userIcon.id }"></a></li>
		                       </c:when>
		                       <c:otherwise>
		                         <li id="lihead_${userIcon.id }" style="display:none;"><a href="javascript:onHeadClicked('${userIcon.id }')"><img src="/common/usericon.html?iconId=${userIcon.id }"></a></li>
		                       </c:otherwise>
		                      </c:choose>
                    </c:forEach>
                    </ul>
                 </c:forEach>                    
                   
                </div>
                <button class="btn-red" onclick="saveUserIcon();">保存</button>
                <div class="blank100"></div>
            </div>
        </div>
        <div class="blank20"></div>
    </div>

