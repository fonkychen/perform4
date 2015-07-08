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
			   window.location.reload();
		   },
		   error:function(xhr){
			   alert('出错了：'+xhr.responseText);
		   }
	   });
}
</script>
<div class="bg clearfix">
        <h2>飞鸽传书 〉 得胜鼓</h2>
        <div class="blank20"></div>
        <textarea class="drumevent-text" id="drum-input" style="color:#bdc3c7;height:38px"
           onfocus="if(this.value=='胜利有时就取决于消息的传达') {this.value='';}this.style.color='#445566';this.style.height='120px';"
           onblur="if(this.value=='') {this.value='胜利有时就取决于消息的传达';this.style.color='#bdc3c7';this.style.height='38px';}"           
        >胜利有时就取决于消息的传达</textarea>
        <span class="wordscount"><span id="drumcounter">0</span> / 200 字</span>
        <div class="blank20"></div>
        <div class="keepcenter-wrap clearfix">
            <div class="keepcenter">
                <button class="btn-red" onclick="saveDrum();">发布得胜鼓</button>
            </div>
        </div>
        <div class="blank20"></div>
        
        <ul class="notice-all">
         <c:forEach items="${page.content }" var="drum">
           <li><a>【<fmt:formatDate value="${drum.updated }" pattern="yyyy-MM-dd HH:mm"/>】：${drum.event }</a><span>${drum.user.name }</span></li>
         </c:forEach>
            
        </ul>
        <c:import url="/default/common/page-index.jsp"></c:import>
    </div>
