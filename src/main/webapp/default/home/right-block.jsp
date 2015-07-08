 <%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <script type="text/javascript">
 var isload=false;
 function attend(isfix){
	 
	 var oncallback=function(event){
		 var html='';
		 html=html+'<p class="sign-over"><strong>今日'+(event.type=='RANDOM_COIN'?'冒险':'')+'签到获得'+event.coinNum+'UB！</strong></p>';
         html=html+'<button class="btn-red sign-over-btn" onclick="window.location.href=\''+'/funny/mall/index.html'+'\'">逛逛商城</button>';
         $('#div_attend').html(html);
	 }
	 if(!isload){
		 $.ajax({
			 url:'/rest/wealth/attend/request.html?isfix='+isfix+'&dt='+new Date(),
			 type:'GET',
			 success:function(data){
				 oncallback(data);
			 },
			 error:function(xhr){
				 alert(xhr.responseText);
			 }
		 }).done(function(){
			 isload=false;
		 }); 
	 }
	 
 }
 function showattend(){
	  var html='';
	  html=html+' <dl class="sign-dl fl">';
	  html=html+'<dt>稳赚型</dt>';
	  html=html+'<dd>每日稳赚2UB</dd>';
	  html=html+'<dt>冒险型</dt>';
	  html=html+'<dd>随机领取-5~10UB</dd>';
	  html=html+'</dl>';
	  html=html+'<button class="btn-blue fl sign-btn2" onclick="attend(true);">签到</button>';
	  html=html+'<button class="btn-blue fl sign-btn2" onclick="attend(false);">冒险</button>';
	  html=html+'<div class="blank20"></div><div class="blank20"></div>';
      $("#div_attend").html(html);
  }
 
 function levelup(titleId){
	 if(!isload){
		 isload=true;
		 $.ajax({
			 url:'/rest/user/levelup.html',
			 type:'POST',
			 data:'titleId='+titleId,
			 success:function(){
				 window.location.reload();
			 },
			 error:function(xhr){
				 alert(xhr.responseText);
			 }
		 }).done(function(){
			 isload=false;
		 });
	 }
 }
</script>
 <div class="user-wrap clearfix">
                <div class="user-header"><img src="/default/images/headers/head33.png" alt="我的头像"></div>
                <span class="user-name">${user.name }</span>
                <span class="user-lv">Lv.${user.title.rank }</span>
                <p class="user-country"><c:if test="${not empty user.country }">${user.country.name }</c:if>&nbsp;|&nbsp; ${user.title.name }</p>
                <div class="clearfix"></div>
                <ul class="user-score clearfix">
                    <li><a href=""><strong>${user.userScored }</strong><span>功勋值</span></a></li>
                    <li><a href=""><strong>${user.userCoins }</strong><span>财富值</span></a></li>
                    <li><a href=""><strong>${user.userPopular }</strong><span>人气值</span></a></li>
                </ul>
                <p class="lv-up clearfix">
                   <c:if test="${not empty user.title }">
                    <em>${user.title.rank }级：</em>
                    
                    <c:if test="${not empty nexttitle }">
                    <span><strong style="width: <c:choose><c:when test="${user.userScored/nexttitle.bottom >= 1 }">100%</c:when><c:otherwise><fmt:formatNumber minFractionDigits="0" value="${100*user.userScored/nexttitle.bottom}"></fmt:formatNumber>%</c:otherwise></c:choose>;"></strong></span>
                    <em>${user.userScored}/${nexttitle.bottom }</em>
                    </c:if>
                    
                   </c:if>
                   
                </p>
                <div class="user-help clearfix">
                    <p class="fl">
                        <a href="">各官职对应的俸禄</a>
                        <a href="">获得成就？</a>
                    </p>
                    <c:choose>
                    <c:when test="${(not empty nexttitle) and (nexttitle.bottom <= user.userScored) }">
                    <button class="fr btn-blue" onclick="levelup('${nexttitle.id}')">升官领俸禄</button>
                    </c:when>
                    <c:otherwise>
                    <button class="fr btn-gray">升官领俸禄</button>
                    </c:otherwise>
                    </c:choose>
                    
                    
                </div>
                <ul class="medal clearfix">
                    <li><a href=""><img src="/default/images/medal-1.png" alt="名垂青史"></a></li>
                    <li><a href=""><img src="/default/images/medal-2.png" alt="水滴石穿"></a></li>
                    <li><a href=""><img src="/default/images/medal-3.png" alt="铁杵成针"></a></li>
                    <li><a href=""><img src="/default/images/medal-4.png" alt="愤怒的小鸟"></a></li>
                    <li><a href="" title="前往勋章馆">···</a></li>
                </ul>
            </div>
            <!--签到-->
            <div class="title clearfix"><h3>签到领U币</h3></div>
            <c:choose>
             <c:when test="${empty attendevent }">
             
              <div class="white-wrap" id="div_attend">
                <p class="sign-in"><strong>你今天签到了吗？</strong></p>
                <p class="sign-in">“人在江湖飘，哪能不签到！”</p><jsp:useBean id="curdate" class="java.util.Date"></jsp:useBean>
                <p class="sign-time"><fmt:formatDate value="${curdate }" pattern="yyyy-MM-dd"/></p>
                <button class="btn-blue fr sign-btn" onclick="showattend();">签 到</button>
               <div class="blank20"></div>
             </div>
             </c:when>
             <c:otherwise>
              
             <div class="white-wrap">
                <p class="sign-over"><strong>今日<c:if test="${not empty attendevent.type }"><c:choose><c:when test="${attendevent.type eq 'RANDOM_COIN' }">冒险</c:when><c:otherwise></c:otherwise></c:choose></c:if>签到获得${attendevent.coinNum }UB！</strong></p>
                <button class="btn-red sign-over-btn" onclick="window.location.href='/funny/mall/index.html'">逛逛商城</button>
             </div>
              
             </c:otherwise>
            </c:choose>
            
            <!--国家排行榜-->
            <div class="title clearfix"><h3>国家排行榜</h3><a href="" class="fr"><span>|</span>邻国排名</a></div>
            <div class="white-wrap clearfix">
                <div class="country-header fl"><img src="/default/images/country-header.png" alt="国家排行"></div>
                <dl class="fl country-rank">
                  <c:if test="${not empty countrystatis }">
                  
                    <dt>排行月：</dt>
                    <dd>${countrystatis.monthNum }月</dd>
                    <dt>第一名：</dt>
                    <dd>${countrystatis.country.name }</dd>
                    <dt>国 &nbsp; 力：</dt>
                    <dd>${countrystatis.calScored }</dd>
                    <dt>财 &nbsp; 力：</dt>
                    <dd>${countrystatis.country.wealth }</dd>
                    <dt>国 &nbsp; 主：</dt>
                    <dd>${countrystatis.country.owner.name }</dd>
                   </c:if>
                </dl>
            </div>
            <!--即时公告栏-->
            <div class="title clearfix"><h3>即时公告栏</h3><a href="/club/notice.html" class="fr"><span>|</span>更多</a></div>
            <div class="white-wrap">
                <ul class="notice-ul">
                  <c:forEach items="${pmessages.content }" var="message">
                   <li>
                        <a>
                            <span class="arrow"></span>
                            <span class="notice">${message.content }</span>
                            <span class="notice-time"><fmt:formatDate value="${message.updated }" pattern="MM-dd"/></span>
                        </a>
                    </li>
                  </c:forEach>
                    
                   
                </ul>
            </div>
        </div>
