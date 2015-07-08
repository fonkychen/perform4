<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
	$("#scoreNum").keyup(function(){
		this.value=this.value.replace(/\D/g,'');
		var val=parseInt(this.value);
		if(val>100){
			this.value=this.value.substring(0,(this.value.length-1));
		}
	});
});

var isSending=false;
function savecomment(){
	  var wcmodel={};
	  wcmodel.userId='${handleuser.id}';
	  if($("#scoreNum").val()=='' || $("#scoreNum").val().indexOf('.')>=0){
		  alert("分数不合法");
		  return;
	  }
	  if($("#taskDescription").val()=='' || $("#taskDescription").val()=='请对员工一周工作进行点评'){
		  alert("点评内容不能为空");
		  return;
	  }
	  wcmodel.scoreNum=$("#scoreNum").val();
	  wcmodel.taskDescription=$("#taskDescription").val();
	  
	  var oncallback=function(){
		  //myalert("保存成功");
		  //var scroll_pos=(0);          
		  // $('html, body').animate({scrollTop:(scroll_pos)}, '500');
		  isSending=false;
		  window.location.href="/wechat/selfmark-detail.html?userId="+wcmodel.userId;
	  }
	  var onerror=function(errString,exception){
		  alert(errString);
		  isSending=false;
		  return;
	  }
	  if(!isSending){
		  isSending=true;
		  $.ajax({
				url:'/rest/scored/comment/save.html',
				type:'POST',		
				data:'userId=${handleuser.id}&scored='+wcmodel.scoreNum+"&description="+wcmodel.taskDescription,
				
				success:function(){
				  // alert('保存成功');
				  window.location.reload();
				},
				error:function(xhr){
				   alert(xhr.responseText);
				}
			});
		 // WeeklyCommentInterface.saveComment(wcmodel,{callback:oncallback,errorHandler:onerror});  
	  }
	  
}

function changeuser(){
	window.location.href='/wechat/weeklycomment.html?userId='+$("#user_select").val();
}
</script>


	<div class="wrap-page">
		<div class="blank20"></div>
		<section class="clearfix sec-border">
			<div class="blank10"></div>
			<span class="cls-t1">姓名</span>
			<select id="user_select" class="inpt-1 angle-down" onchange="changeuser();">
			  <c:forEach items="${usergroup.users }" var="cuser">
			    <c:choose>
			      <c:when test="${cuser.id eq handleuser.id }">
			       <option selected="selected" value="${cuser.id }">${cuser.name }</option>
			      </c:when>
			      <c:when test="${cuser.id <0 }">
			      </c:when>
			      <c:otherwise>
			       <option value="${cuser.id }">${cuser.name }</option>
			      </c:otherwise>
			    </c:choose>
			    
			  </c:forEach>
			</select>
			<!-- 
			<a href="" class="wrap-select clearfix">
				<input class="inpt-1 angle-down" type="text" value="旧曾谙" onclick="javascript:document.getElementById('list-name').style.display='block';">
				<ul class="list-name" id="list-name">
					<li></li>
					<li>旧曾谙</li>
					<li>红似火</li>
					<li>绿如蓝</li>
				</ul>
			</a>
			 -->
			<span class="cls-t1">分数</span>
			<c:choose>
			  <c:when test="${(not empty comment) and (not empty disabled)}">
			     <input class="inpt-1" type="text" value="${comment.scoreNum }">
			     <textarea class="text" onfocus="if(this.value=='一天下来工作感受如何，请有效率的总结下吧！') {this.value='';}this.style.color='#000';" onblur="if(this.value=='') {this.value='一天下来工作感受如何，请有效率的总结下吧！';this.style.color='#a3a3a3';}">一天下来工作感受如何，请有效率的总结下吧！</textarea>
			
			  </c:when>
			   <c:when test="${not empty disabled }">
			     <input class="inpt-1" type="text" value="">
			     <textarea class="text" readonly="readonly">一天下来工作感受如何，请有效率的总结下吧！</textarea>
			
			   </c:when>
			   <c:otherwise>
			     <input id="scoreNum" class="inpt-1" type="text" <c:if test="${not empty comment }"> value="${comment.scoreNum }"</c:if>>
			     <textarea id="taskDescription" class="text" onfocus="if(this.value=='请对员工一周工作进行点评') {this.value='';}this.style.color='#000';" onblur="if(this.value=='') {this.value='请对员工一周工作进行点评';this.style.color='#a3a3a3';}"><c:choose><c:when test="${not empty comment }">${comment.taskDescription }</c:when><c:otherwise>请对员工一周工作进行点评</c:otherwise></c:choose></textarea>
			     <button class="btn-ack" onclick="savecomment();">确认</button>
			   </c:otherwise>
			</c:choose>			
			
			<div class="blank20"></div>
		</section>
		<div class="blank20"></div>
		<section class="sec-border">
			<a href="/wechat/selfmark-detail.html?userId=${handleuser.id }" class="more">查看历史评价</a>
		</section>
	</div>
