<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
$(document).ready(function(){
	$("#drum-input").keyup(function(event){
		var content=$("#drum-input").val();
		$("#drumcounter").html(content.length);
		if(event.which == 16){
		
		}
	});
});
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
			   window.location.href='/club/topic.html';
		   },
		   error:function(xhr){
			   alert('出错了：'+xhr.responseText);
		   }
	   });
   }
   
   function saveDrum(){
	   var content=$('#drum-input').val();
	   if(content=='胜利有时就取决于消息的传达' || content==''){
		   alert('请输入内容');return;
	   }
	   if(!(content.length > 0 && content.length <=200)){
		   alert('字数应在0到200之间');return;
	   }
	   
	   $.ajax({
		   url:'/rest/event/drum/save.html',
		   type:'POST',
		   data:"content="+content,
		   success:function(){
			   window.location.href='/club/notice.html';
		   },
		   error:function(xhr){
			   alert('出错了：'+xhr.responseText);
		   }
	   });
   }
   
</script>
<div class="miniweibo clearfix">
                <ul class="weibo-tab1">
                    <li id="li_topic_weibo" class="thistab" onclick="$(this).attr('class','thistab');$('#li_topic_drum').removeAttr('class');$('#div_weibo').css('display','block');$('#div_drum').css('display','none');"><a>随便说说</a></li>
                    <li id="li_topic_drum" onclick="$(this).attr('class','thistab');$('#li_topic_weibo').removeAttr('class');$('#div_weibo').css('display','none');$('#div_drum').css('display','block');"><a>得胜鼓</a></li>
                </ul>
                <!--随便说说tab-->
                <div id="div_weibo" class="weibo-wrap">
                    <script language="javascript">
                        function countChar(textareaName,spanName)
                            {
                                document.getElementById(spanName).innerHTML = document.getElementById(textareaName).value.length;
                            }
                    </script>
                    <textarea class="weibo-input" title="随便说说吧"  id="weibo-input" maxlength="200"
                    onfocus="if(this.value=='有什么想说的？') {this.value='';}this.style.color='#445566';this.style.height='120px';"
                    onblur="if(this.value=='') {this.value='有什么想说的？';this.style.color='#bdc3c7';this.style.height='38px';}"
                    onkeydown='countChar("weibo-input","counter");' onkeyup='countChar("weibo-input","counter");'>有什么想说的？</textarea>
                    <span class="wordscount"><span id="counter">0</span> / 200 字</span>
                    <button class="btn-blue fr" onclick="saveWeibo();">发布</button>
                    <div class="clearfix"></div>
                    <ul class="weibo-tab2">
                        <li><a id="a_weibo_new" class="thistab" onclick="$(this).attr('class','thistab');$('#a_weibo_hot').removeAttr('class');$('#ul_weibo_hot').css('display','none');$('#ul_weibo_new').css('display','block');">最新</a></li>
                        <li><a id="a_weibo_hot" onclick="$(this).attr('class','thistab');$('#a_weibo_new').removeAttr('class');$('#ul_weibo_new').css('display','none');$('#ul_weibo_hot').css('display','block');">热门</a></li>
                    </ul>
                    <ul id="ul_weibo_new" class="weibo-new">
                      <c:forEach items="${newtopics }" var="newtopic">
                       <li class="clearfix">
                            <div class="fl headicon"><img src="/common/usericon.html?iconId=${newtopic.user.userIcon.id }"></div>
                            <div class="fl weibo-details">
                                <p><span class="name">${newtopic.user.nickName }&nbsp;|&nbsp; ${newtopic.user.title.name }&nbsp;|&nbsp; <c:if test="${not empty newtopic.user.country }">${newtopic.user.country.name }</c:if></span><span class="time"><fmt:formatDate value="${newtopic.created }" pattern="yyyy-MM-dd HH:mm"/></span></p>
                                <a class="weibo-detail" href="/club/topic/new.html">${newtopic.content }</a>
                            </div>
                            <div class="fr reply">
                            <!-- 
                                <a href=""><span class="icon-fav"></span><small><c:if test="${(not empty newtopic.favorNum) and (newtopic.favorNum > 0) }">(${newtopic.favorNum })</c:if></small></a>
                                <a href="">回复<small><c:if test="${(not empty newtopic.replyNum) and (newtopic.replyNum > 0) }">(${newtopic.replyNum })</c:if></small></a>
                                 -->
                            </div>
                        </li>
                      </c:forEach>   
                       
                    </ul>
                    
                    <ul id="ul_weibo_hot" class="weibo-new" style="display:none;">
                     <c:forEach items="${hottopics }" var="hottopic">
                       <li class="clearfix">
                            <div class="fl headicon"><img src="/common/usericon.html?iconId=${hottopic.user.userIcon.id }"></div>
                            <div class="fl weibo-details">
                                <p><span class="name">${hottopic.user.nickName }&nbsp;|&nbsp; ${hottopic.user.title.name }&nbsp;|&nbsp; <c:if test="${not empty hottopic.user.country }">${hottopic.user.country.name }</c:if></span><span class="time"><fmt:formatDate value="${hottopic.created }" pattern="yyyy-MM-dd HH:mm"/></span></p>
                                <a class="weibo-detail" href="/club/topic/hot.html">${hottopic.content }</a>
                            </div>
                            <div class="fr reply">
                            <!-- 
                                <a href=""><span class="icon-fav"></span><small><c:if test="${(not empty newtopic.favorNum) and (newtopic.favorNum > 0) }">(${newtopic.favorNum })</c:if></small></a>
                                <a href="">回复<small><c:if test="${(not empty newtopic.replyNum) and (newtopic.replyNum > 0) }">(${newtopic.replyNum })</c:if></small></a>
                                 -->
                            </div>
                        </li>
                      </c:forEach>   
                    </ul>
                    <a href="/club/topic.html" class="fr weibo-more">查看所有</a>
                </div>
                <!--得胜鼓tab-->
                <div id="div_drum" class="weibo-wrap" style="display: none;">
                    <textarea id="drum-input" class="weibo-input weibo-input2" title="得胜鼓" maxlength="200"
                    onfocus="if(this.value=='胜利有时就取决于消息的传达') {this.value='';}this.style.color='#445566';"
                    onblur="if(this.value=='') {this.value='胜利有时就取决于消息的传达';this.style.color='#bdc3c7';}"
                    onkeydown='countChar("weibo-input","counter");' onkeyup='countChar("weibo-input","counter");'>胜利有时就取决于消息的传达</textarea>
                    <span class="wordscount"><span id="drumcounter">0</span> / 200 字</span>
                    <button class="btn-blue fr" onclick="saveDrum();">发布</button>
                    <div class="clearfix"></div>
                </div>
            </div>
