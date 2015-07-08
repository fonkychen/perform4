<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(document).ready(function(){
	if($.browser.msie && $.browser.version == '7.0'){
		$("div").each(function(){
			if($(this).attr("class")=='miniwrap'){
				$(this).parent("li").hover(function(){					
					$(this).attr("class","this-miniwrap");
				},function(){
					$(this).removeAttr("class");
				});
			}
		});
	}
	
	$.ajax({
		url:'/rest/user/rank.html'+"?dt="+new Date(),
		type:'GET',
		success:function(ranks){
			
			for(var i=0;i<ranks.length;i++){
				var cscore=parseInt($("#scorerank_"+ranks[i].user.id).attr("rank"));
				var ccoin=parseInt($("#coinrank_"+ranks[i].user.id).attr("rank"));
				var cpopular=parseInt($("#popularrank_"+ranks[i].user.id).attr("rank"));
				if(cscore > ranks[i].scoreRank){
					$("#scorerank_"+ranks[i].user.id).html('<img src="/default/images/arrow-down.png" alt="排名下降">');
				}
				else if(cscore < ranks[i].scoreRank){
					$("#scorerank_"+ranks[i].user.id).html('<img src="/default/images/arrow-up.png" alt="排名上升">');
				}
				
				if(ccoin > ranks[i].coinRank){
					$("#coinrank_"+ranks[i].user.id).html('<img src="/default/images/arrow-down.png" alt="排名下降">');
				}
				else if(ccoin < ranks[i].coinRank){
					$("#coinrank_"+ranks[i].user.id).html('<img src="/default/images/arrow-up.png" alt="排名上升">');
				}
				
				if(cpopular > ranks[i].popularRank){
					$("#popularrank_"+ranks[i].user.id).html('<img src="/default/images/arrow-down.png" alt="排名下降">');
				}
				else if(cpopular < ranks[i].popularRank){
					$("#popularrank_"+ranks[i].user.id).html('<img src="/default/images/arrow-up.png" alt="排名上升">');
				}
			}
		},
		error:function(xhr){
			//ignore
		}
	});
});
</script>
<div class="top10 nomargin clearfix"><!--功勋排行 SB IE7 hack 注释不可移动-->
                <div class="top10-title">
                    <h3>功勋排行</h3>
                    <a href="/home/toprank/userScored.html">更多</a>
                </div>
                <ol class="top10-ol">
                  <c:forEach items="${scorepage.content }" var="cuser" varStatus="status">
                    <li>
                    	<p class="fl">
                    		<span class="num">${status.count }</span>
                    		
                    		
                        	<span class="arrow" rank="${status.count }" id="scorerank_${cuser.id }">
                        	
                        	</span>
                        	
                    	</p>
                        <p class="fl">
                        	<span class="name">${cuser.nickName }</span>
                        	<span class="country"><c:if test="${not empty cuser.country }">${cuser.country.name }</c:if></span>
                        </p>
                        <span class="score">${cuser.userScored }</span>
                        <div class="miniwrap">
                            <img <c:if test="${not empty cuser.userIcon }">src="/common/usericon.html?iconId=${cuser.userIcon.id }"</c:if>  class="fl">
                            <p class="fl">
                                <strong>${cuser.name }</strong><br />${cuser.title.name }<br />${cuser.department.name }<br />${cuser.position.name }
                            </p>
                            <!-- 
                            <a class="fr" title="和我PK试试吧！" onclick="javascript:document.getElementById('pk').style.display='block';">PK</a>
                             -->
                        </div>
                        
                    </li>
                  
                  </c:forEach>
                   
                    
                </ol>
            </div>
            <!--财富排行-->
            <div class="top10 clearfix">
                <div class="top10-title">
                    <h3>财富排行</h3>
                    <a href="/home/toprank/userCoins.html">更多</a>
                </div>
                <ol class="top10-ol">
                    <c:forEach items="${coinpage.content }" var="cuser" varStatus="status">
                    <li>
                    	<p class="fl">
                    		<span class="num">${status.count }</span>
                    		
                    		 
                        	<span class="arrow" rank="${status.count }" id="coinrank_${cuser.id }"></span>
                        	
                    	</p>
                        <p class="fl">
                        	<span class="name">${cuser.nickName }</span>
                        	<span class="country"><c:if test="${not empty cuser.country }">${cuser.country.name }</c:if></span>
                        </p>
                        <span class="score">${cuser.userCoins }</span>
                        <div class="miniwrap">
                            <img <c:if test="${not empty cuser.userIcon }">src="/common/usericon.html?iconId=${cuser.userIcon.id }"</c:if>  class="fl">
                            <p class="fl">
                                <strong>${cuser.name }</strong><br />${cuser.title.name }<br />${cuser.department.name }<br />${cuser.position.name }
                            </p>
                            <!-- 
                            <a class="fr" title="和我PK试试吧！" onclick="javascript:document.getElementById('pk').style.display='block';">PK</a>
                             -->
                        </div>
                    </li>
                  
                  </c:forEach>
                </ol>
            </div>
            <!--人气排行-->
            <div class="top10 clearfix">
                <div class="top10-title">
                    <h3>人气排行</h3>
                    <a href="/home/toprank/userPopular.html">更多</a>
                </div>
                <ol class="top10-ol">
                  <c:forEach items="${popularpage.content }" var="cuser" varStatus="status">
                    <li>
                    	<p class="fl">
                    		<span class="num">${status.count }</span>
                    		 
                        	<span class="arrow" rank="${status.count }" id="popularrank_${cuser.id }"></span>
                        	
                    	</p>
                        <p class="fl">
                        	<span class="name">${cuser.nickName }</span>
                        	<span class="country"><c:if test="${not empty cuser.country }">${cuser.country.name }</c:if></span>
                        </p>
                        <span class="score">${cuser.userPopular }</span>
                        <div class="miniwrap">
                            <img <c:if test="${not empty cuser.userIcon }">src="/common/usericon.html?iconId=${cuser.userIcon.id }"</c:if>  class="fl">
                            <p class="fl">
                                <strong>${cuser.name }</strong><br />${cuser.title.name }<br />${cuser.department.name }<br />${cuser.position.name }
                            </p>
                            <!-- 
                            <a class="fr" title="和我PK试试吧！" onclick="javascript:document.getElementById('pk').style.display='block';">PK</a>
                             -->
                        </div>
                    </li>
                  
                  </c:forEach>
                </ol>
            </div>
      
