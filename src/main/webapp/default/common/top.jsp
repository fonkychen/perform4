<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
 
 $(document).ready(function(){
		 $("#a_shownotice")
		 .mouseenter(function(){
			 $("#newMesg_ul").css("display","block");
		 })
		 .mouseleave(function(){
			 $("#newMesg_ul").css("display","none");
			
		 });	
		 $("#newMesg_ul")
		 .mouseenter(function(){
			
			 $("#newMesg_ul").css("display","block"); 			
		 })
		 .mouseleave(function(){			
			 $("#newMesg_ul").css("display","none");			
		 });
	 });
    
	  

</script>
 <div class="topbar-wrap" id="topbar">
        <div class="topbar center">
        
            <a class="logo fl" href="/home/index.html"></a>
            <ul class="topnav fl">
            	<li><a href="/staff/dailyboard.html">功勋英雄</a></li>
            	<li><a href="/funny/mall/index.html">趣多多</a></li>
            	<li><a href="">飞鸽传书</a></li>
            	<li><a href="/service/mycenter/pinfo.html">我是中心</a></li>
            </ul>
            
            <div class="fr">
                <a class="c-fff fl" href="">${user.nickName }</a>
                <span class="fl line-1">|</span>
                <a class="c-fff fl" href="/common/logoutProcess">退出</a>
                <span class="fl line-1">|</span>
                <a class="c-fff fl" id="a_shownotice">提醒<span id="span_notify_totalnew"></span><img class="icon-notification-bell" src="/default/images/icon/notification-bell.png"></a>
                <ul id="newMesg_ul" class="notice">
                   <li class="mycenter first-arrow"><a href="/service/mycenter/pinfo.html">我的主页</a></li>
                   
                   
                    
                   <li id="mesg_NewReward"><a id="a_mesg_NewReward" href="/connect/reward/index.html" >新的悬赏<span id="span_mesg_NewReward"></span></a></li>
    	           <li id="mesg_NewTask"><a id="a_mesg_NewTask" href="/connect/task/index.html">新任务<span id="span_mesg_NewTask"></span></a></li>
                   <li id="mesg_NewRewardResponse"><a id="a_mesg_NewRewardResponse" href="#">揭榜回复<span id="span_mesg_NewRewardResponse"></span></a></li>              
                   
                    <li class="btmline" id="mesg_NewTaskResponse"><a id="a_mesg_NewTaskResponse" href="#">任务回复<span id="span_mesg_NewTaskResponse"></span></a></li>
                   <li id="mesg_NewMallProduct"><a id="a_mesg_NewMallProduct" href="/funny/mall/index.html" >商城添丁<span id="span_mesg_NewMallProduct"></span></a></li>
                  <sec:authorize ifAllGranted="ROLE_ADMIN">
                   
                    <li id="mesg_NewDrumCheck"><a id="a_mesg_NewDrumCheck" href="#">功勋薄审核<span id="span_mesg_NewDrumCheck"></span></a></li>
                    <li id="mesg_NewOrderRecord"><a id="a_mesg_NewOrderRecord" href="#">物品兑换<span id="span_mesg_NewOrderRecord"></span></a></li>
                  </sec:authorize>
                </ul>
                <span class="c-fff fl">服务热线：400-670-6006</span>

            </div>
        </div>
    </div>
