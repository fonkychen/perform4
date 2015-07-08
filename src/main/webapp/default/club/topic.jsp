<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
$(document).ready(function(){
    var viewname='${viewname}';
	
	var vns=viewname.split("_");
	$("a[id^='"+vns[0]+"_"+vns[1]+"']").attr("class","thistab");
	
	$("#weibo-input").keyup(function(event){
		var content=$("#weibo-input").val();
		$("#counter").html(content.length);
		if(event.which == 16){
			
		}
	});
});

//function loaduser

function saveWeibo(){
	   var content=$('#weibo-input').val();
	   if(content=='有什么想说的？' || content==''){
		   alert('请输入内容');return;
	   }
	   if(!(content.length > 0 && content.length <=200)){
		   alert('字数应在0到200之间');return;
	   }
	   
	   $.ajax({
		   url:'/rest/weibo/save.html',
		   type:'POST',
		   data:"content="+content,
		   success:function(){
			   //alert('保存成功');
			   window.location.reload();
		   },
		   error:function(){
			   alert('出错了');
		   }
	   });
}

 function reqfavor(topicId){
	 	var oncallback=function(topic){
	 		if($("#span_favor_"+topicId).attr("class")=='icon-fav2'){
	 			$("#span_favor_"+topicId).attr("class","icon-fav");
	 		}
	 		else{
	 			$("#span_favor_"+topicId).attr("class","icon-fav2");
	 		}
	 		if(topic.favorNum>0){
	 			$("#small_favor_"+topicId).html("("+topic.favorNum+")");
	 		}
	 		else{
	 			$("#small_favor_"+topicId).html("");
	 		}
	 	}	 	
	 	
	 	$.ajax({
	 		url:'/rest/weibo/favor.html?topicId='+topicId,
	 		type:'GET',
	 		success:oncallback,
	 		error:function(xhr){
	 			alert(xhr.responseText);
	 		}
	 	});
 }
 
 function showreply(topicId){
	 if($('#div_topic_'+topicId).css('display')=='none'){
		 $('#div_topic_'+topicId).css('display','block');
	 }
	 else{
		 $('#div_topic_'+topicId).css('display','none');
	 }
	 
	 var page=$('#div_reply_'+topicId).attr("next");
	 var oncallback=function(pdata){
		 var replylist=pdata.content;
		 var html='';
		 for(var i=0;i<replylist.length;i++){
			html=html+' <div class="fl headicon"><img src="/common/usericon.html?iconId='+replylist[i].user.userIcon.id+'"></div>';
            html=html+' <div class="fl weibo-details" id="div_detail_'+replylist[i].id+'">';
            html=html+'     <p><span class="name">'+replylist[i].user.name+' &nbsp;|&nbsp; '+replylist[i].user.title.name+' &nbsp;|&nbsp; '+(replylist[i].user.country==null?'':replylist[i].user.country.name)+'</span>';
            if(replylist[i].replyTo!=null){
            	html=html+'<span class="reply-span">回复</span><span class="name">'+replylist[i].replyTo.user.name+' &nbsp;|&nbsp; '+replylist[i].replyTo.user.title.name+' &nbsp;|&nbsp;'+(replylist[i].replyTo.user.country==null?'':replylist[i].replyTo.user.country.name)+'</span>';
            }
           
            html=html+'<span class="time">'+timeformat(new Date(replylist[i].created))+'</span>';          
            html=html+'     <a class="reply reply2" onclick="showdetailreply(\''+topicId+'\',\''+replylist[i].id+'\')"><span class="icon-reply"></span></a></p>';
            html=html+'     <p class="weibo-detail">'+replylist[i].content+'</p>';
            html=html+'  </div><div class="blank20"></div>';

		 }
		 $('#div_reply_'+topicId).append(html);
		 $('#div_reply_'+topicId).attr("next",pdata.number+1+1);
		
	 }
	 $.ajax({
		 url:'/rest/weibo/reply/getPageReplyByTopic.html?id='+topicId+'&page='+page,
		 type:'GET',
		 success:oncallback,
	     error:function(xhr){
	    	 alert(xhr.responseText);
	     }
	 });
 }
 
 function showdetailreply(topicId,replyTo){
	 
	 $('#detail_reply_area').remove();
	 
	 if($('#div_detail_'+replyTo).attr("showreply") == null){
		 var area='<div id="detail_reply_area"><textarea id="textarea_detailreply_'+replyTo+'" class="weibo-textarea"></textarea><button class="btn-blue fr" onclick="submitreply(\''+topicId+'\',\''+replyTo+'\');">回复</button></div>';
		 $('#div_detail_'+replyTo).after(area);
		 $('#div_detail_'+replyTo).attr("showreply","1"); 
	 }
	 else{
		 $('#div_detail_'+replyTo).removeAttr("showreply");
	 }
	 
 }
 
 function submitreply(topicId,replyTo){
	 var content='';
	 
	 if(replyTo==null ){
		 content=$('#textarea_reply_'+topicId).val().trim(); 
	 }
	 else{
		 
		 content=$('#textarea_detailreply_'+replyTo).val().trim();
		
	 }
	
	 if(content.length<=0 || content.length>200){
		 alert('字数应介于0到200之间');
		 return;
	 }
	 var oncallback=function(reply){
		 var html='';
		 html=html+' <div class="fl headicon"><img src="/common/usericon.html?iconId='+reply.user.userIcon.id+'"></div>';
         html=html+' <div class="fl weibo-details" id="div_detail_'+reply.id+'">';
         html=html+'     <p><span class="name">'+reply.user.name+' &nbsp;|&nbsp; '+reply.user.title.name+' &nbsp;|&nbsp; '+(reply.user.country==null?'':reply.user.country.name)+'</span>';
         if(reply.replyTo!=null){
         	html=html+'<span class="reply-span">回复</span><span class="name">'+reply.replyTo.user.name+' &nbsp;|&nbsp; '+reply.replyTo.user.title.name+' &nbsp;|&nbsp;'+(reply.replyTo.user.country==null?'':replylist[i].replyTo.user.country.name)+'</span>';
         }
         html=html+'<span class="time">'+timeformat(new Date(reply.created))+'</span>';
         html=html+'     <a class="reply reply2" onclick="showdetailreply(\''+topicId+'\',\''+reply.id+'\')"><span class="icon-reply"></span></a></p>';
         html=html+'     <p class="weibo-detail">'+reply.content+'</p>';
         html=html+'  </div><div class="blank20"></div>';
         $('#div_reply_'+topicId).prepend(html);
         $("#small_reply_"+topicId).html("("+reply.weiboTopic.replyNum+")");
         if(replyTo==null){
        	 $('#textarea_reply_'+topicId).val(''); 
         }
         else{
        	 $('#detail_reply_area').remove();
        	 $('div[id^="div_detail_"]').removeAttr("showreply");
         }
         
	 }
	 var param='topicId='+topicId;
	 if(replyTo!=null){
		 param=param+'&replyTo='+replyTo;
	 }
	 $.ajax({
		 url:'/rest/weibo/reply/save.html?'+param,
		 type:'POST',
		 contentType:"text/plain; charset=utf-8",
		 data:content,
		 success:oncallback,
		 error:function(xhr){
			 alert(xhr.responseText);
		 }
	 });
	 
 }
 
 function deletetopic(topicId){
	 $.ajax({
		 url:'/rest/weibo/topic/delete.html?id='+topicId,
		 type:'GET',
		 success:function(){
			 window.location.reload();
		 },
		 error:function(xhr){
			 alert(xhr.responseText);
		 }
	 });
 }
</script>
<div class="bg clearfix">
        <ul class="weibo-tab3">
            <li><a id="club_topic" href="/club/topic.html">所有说说</a></li>
            <li><a id="club_from" href="/club/from.html">我说的</a></li>
            <li><a id="club_attention" href="/club/attention.html">响应我</a></li>
        </ul>
        <div class="clearfix"></div>
        <div class="weibo-wrap2">
            <textarea class="weibo-textarea" id="weibo-input"
            onfocus="if(this.value=='有什么想说的？') {this.value='';}this.style.color='#456';"
            onblur="if(this.value=='') {this.value='有什么想说的？';this.style.color='#bdc3c7';}" >有什么想说的？</textarea>
            <span class="wordscount"><span id="counter">0</span> / 200 字</span>
            <button class="btn-blue fr" onclick="saveWeibo();">发布</button>
            <div class="clearfix"></div>
            <ul class="weibo-tab2">
               <c:choose>
                <c:when test="${(empty type) or not (type eq 'hot')  }">
                 <li><a class="thistab">最新</a></li>
                 <li><a href="/club/topic/hot.html">热门</a></li>
                </c:when>
                <c:otherwise>
                  <li><a href="/club/topic/new.html">最新</a></li>
                  <li><a class="thistab">热门</a></li>
                </c:otherwise>
               </c:choose>
              
            </ul>
            <ul class="weibo-new weibo-new2">
               <c:forEach var="topic" items="${page.content }">
               <li class="clearfix">
                    <div class="fl headicon"><img src="/common/usericon.html?iconId=${topic.user.userIcon.id }"></div>
                    <div class="fl weibo-details">
                        <p><span class="name">${topic.user.nickName } &nbsp;|&nbsp; ${topic.user.title.name } &nbsp;|&nbsp; <c:if test="${not empty topic.user.country }">${topic.user.country.name }</c:if> </span><span class="time"><fmt:formatDate value="${topic.created }" pattern="yyyy-MM-dd HH:mm"/></span></p>
                        <p class="weibo-detail">${topic.content }</p>
                        <div class="reply">
                            <a onclick="reqfavor('${topic.id}');">
                             <c:set var="isfavor" value="0"></c:set>
                             <c:forEach items="${favors }" var="ftopic">
                               <c:if test="${ftopic.id eq topic.id }">
                                <c:set var="isfavor" value="1"></c:set>
                               </c:if>
                             </c:forEach>
                             <c:choose>
                               <c:when test="${isfavor eq 1 }">
                               <span id="span_favor_${topic.id }" class="icon-fav2"></span>
                               </c:when>
                               <c:otherwise>
                               <span id="span_favor_${topic.id }" class="icon-fav"></span>
                               </c:otherwise>
                             </c:choose>
                              
                              <small id="small_favor_${topic.id }"><c:if test="${(not empty topic.favorNum) and (topic.favorNum >0) }">(${topic.favorNum })</c:if></small>
                            </a>
                            <a  onclick="showreply('${topic.id }');"><span class="icon-reply"></span>回复<small id="small_reply_${topic.id }"><c:if test="${(not empty topic.replyNum) and (topic.replyNum >0) }">(${topic.replyNum })</c:if></small></a>
                            <c:if test="${topic.user eq user }">
                            <!-- 
                            <a onclick="deletetopic('${topic.id}');"><span class="icon-del"></span>删除</a>
                             -->
                            </c:if>
                            
                        </div>       
                       <div id="div_topic_${topic.id }" style="display:none;">
                         <textarea id="textarea_reply_${topic.id }" class="weibo-textarea"></textarea>
                         <button class="btn-blue fr" onclick="submitreply('${topic.id}');">回复</button>      
                         <div id="div_reply_${topic.id }" next="1"></div>
                       </div>                
                    </div>
                </li>
               </c:forEach>   
            </ul>
        </div>
        <c:import url="/default/common/page-index.jsp"></c:import>
        <div class="blank100"></div>
    </div>
