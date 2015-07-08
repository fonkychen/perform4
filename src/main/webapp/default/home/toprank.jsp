<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(document).ready(function(){
	if($.browser.msie && $.browser.version == '7.0'){
		$("div").each(function(){
			if($(this).attr("class")=='miniwrap'){
				$(this).parent("td").hover(function(){					
					$(this).attr("class","this-miniwrap");
				},function(){
					$(this).removeAttr("class");
				});
			}
		});
	}
});
function favor(uid,type){
	if(uid=='' || type=='')return;
	var oncallback=function(){
		
		window.location.reload();
	}
	//alert('新版本暂未开通');
	
	$.ajax({
		url:'/rest/user/favor.html?userId='+uid+'&type='+type+"&dt="+new Date(),
		type:'GET',
		success:oncallback,
		error:function(xhr){
			alert(xhr.responseText);
		}
	});
	
}
</script>
<div class="bg clearfix">
    	<h2></h2>
        <a href="" class="lastQ">上季度排名</a>
    	<table class="hot">
    		<thead>
    			<tr>
    				
    			</tr>
    		</thead>
    		<tbody>
    			<tr>
    				<th width="8%">排名</th>
    				
    				<th width="13%">
    				 <c:choose>
    				   <c:when test="${(type eq 'nickName') and ( order eq 'desc') }">
    				    <a href="/home/toprank/nickName.html?order=asc" <c:if test="${type eq 'nickName' }">style="color:#50c8d7;"</c:if> >昵称<span class="up">↑</span></a>
    				   </c:when>
    				   <c:otherwise>
    				    <a href="/home/toprank/nickName.html?order=desc" <c:if test="${type eq 'nickName' }">style="color:#50c8d7;"</c:if> >昵称<span class="up">↓</span></a>
    				   </c:otherwise>
    				 </c:choose>     				    				  
    				</th>
    				<th width="11%">
    				送花
    				</th>
    				<th width="11%">捣蛋</th>
    				<th width="11%" >
    				 <c:choose>
    				   <c:when test="${(type eq 'country') and ( order eq 'desc') }">
    				    <a href="/home/toprank/country.html?order=asc" <c:if test="${type eq 'country' }">style="color:#50c8d7;"</c:if>>国家<span class="up">↑</span></a>
    				   </c:when>
    				   <c:otherwise>
    				    <a href="/home/toprank/country.html?order=desc" <c:if test="${type eq 'country' }">style="color:#50c8d7;"</c:if>>国家<span class="up">↓</span></a>
    				   </c:otherwise>
    				 </c:choose>       				
    				</th>
    				<th width="13%" >
    				<c:choose>
    				   <c:when test="${(type eq 'title') and ( order eq 'desc') }">
    				    <a href="/home/toprank/title.html?order=asc" <c:if test="${type eq 'title' }">style="color:#50c8d7;"</c:if>>官衔<span class="up">↑</span></a>
    				   </c:when>
    				   <c:otherwise>
    				    <a href="/home/toprank/title.html?order=desc" <c:if test="${type eq 'title' }">style="color:#50c8d7;"</c:if>>官衔<span class="up">↓</span></a>
    				   </c:otherwise>
    				 </c:choose>     
    				  
    				</th>
    				<th width="11%" >
    				<c:choose>
    				   <c:when test="${(type eq 'userScored') and ( order eq 'desc') }">
    				    <a href="/home/toprank/userScored.html?order=asc" <c:if test="${type eq 'userScored' }">style="color:#50c8d7;"</c:if>>功勋值<span class="up">↑</span></a>
    				   </c:when>
    				   <c:otherwise>
    				    <a href="/home/toprank/userScored.html?order=desc" <c:if test="${type eq 'userScored' }">style="color:#50c8d7;"</c:if>>功勋值<span class="up">↓</span></a>
    				   </c:otherwise>
    				 </c:choose>    
    				
    				<th width="11%" >
    				<c:choose>
    				   <c:when test="${(type eq 'userCoins') and ( order eq 'desc') }">
    				    <a href="/home/toprank/userCoins.html?order=asc" <c:if test="${type eq 'userCoins' }">style="color:#50c8d7;"</c:if>>财富值<span class="up">↑</span></a>
    				   </c:when>
    				   <c:otherwise>
    				    <a href="/home/toprank/userCoins.html?order=desc" <c:if test="${type eq 'userCoins' }">style="color:#50c8d7;"</c:if>>财富值<span class="up">↓</span></a>
    				   </c:otherwise>
    				 </c:choose>    
    				</th>
    				<th width="11%" >
    				<c:choose>
    				   <c:when test="${(type eq 'userPopular') and ( order eq 'desc') }">
    				    <a href="/home/toprank/userPopular.html?order=asc" <c:if test="${type eq 'userPopular' }">style="color:#50c8d7;"</c:if>>人气值<span class="up">↑</span></a>
    				   </c:when>
    				   <c:otherwise>
    				    <a href="/home/toprank/userPopular.html?order=desc" <c:if test="${type eq 'userPopular' }">style="color:#50c8d7;"</c:if>>人气值<span class="up">↓</span></a>
    				   </c:otherwise>
    				 </c:choose>  
    				</th>
    			</tr>
    			
    			<c:forEach items="${page.content }" var="cuser" varStatus="status">
    			  <tr>
    				<td>${(page.size*page.number)+(status.count) }</td>
    				<td><a>${cuser.nickName }</a>
    				<div class="miniwrap">
                            <img <c:if test="${not empty cuser.userIcon }">src="/common/usericon.html?iconId=${cuser.userIcon.id }"</c:if>  class="fl">
                            <p class="fl">
                                <strong>${cuser.name }</strong><br />${cuser.title.name }<br />${cuser.department.name }<br />${cuser.position.name }
                            </p>
                            <!-- 
                            <a class="fr" title="和我PK试试吧！" onclick="javascript:document.getElementById('pk').style.display='block';">PK</a>
                             -->
                        </div>
    				</td>
    				<td><a class="hot-flower" onclick="favor('${cuser.id}','1')"><sup>
    				  <c:forEach items="${popularlist }" var="popular">
    				    <c:if test="${(popular.user eq cuser) and (popular.popularAction eq 'SendFlower') and (popular.count > 0)}">
    				    +${popular.count }
    				    </c:if>
    				  </c:forEach>
    				</sup></a></td>
    				<td><a class="hot-egg" onclick="favor('${cuser.id}','0')"><sup>
    				 <c:forEach items="${popularlist }" var="popular">
    				    <c:if test="${(popular.user eq cuser) and (popular.popularAction eq 'ThrowEgg') and (popular.count > 0) }">
    				    -${popular.count }
    				    </c:if>
    				  </c:forEach>
    				</sup></a></td>
    				<td><c:if test="${not empty cuser.country }">${cuser.country.name }</c:if></td>
    				<td>${cuser.title.name }</td>
    				<td class="c-kpired">${cuser.userScored }</td>
    				<td class="c-yellow">${cuser.userCoins }</td>
    				<td class="c-blue" id="td_userpopular_${cuser.id }">${cuser.userPopular }</td>
    			</tr>
    			</c:forEach>   			
    		</tbody>
    	</table>
    	<c:import url="/default/common/page-index.jsp"></c:import>
    </div>
