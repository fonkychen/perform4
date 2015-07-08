<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type='text/javascript' src='/dwr/interface/ReportInterface.js'></script>
<script type="text/javascript">
 $(document).ready(function(){
	 var text = document.getElementById("textarea");
	 if(text!=null)autoTextarea(text);
	 
	 $("input[id^='checkbox1_']").click(function(){
		 var checkbox1s=$("input:checked[id^='checkbox1_']");
		 
		 if(checkbox1s.length <1){
			 $("#btn_select").attr("class","btn-wait"); 
		 }
		 else{
			 $("#btn_select").attr("class","btn-ack"); 
		 }
	 });
 });
 
 var autoTextarea = function (elem, extra, maxHeight) {
	    extra = extra || 0;
	    var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
	    isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
	            addEvent = function (type, callback) {
	                    elem.addEventListener ?
	                            elem.addEventListener(type, callback, false) :
	                            elem.attachEvent('on' + type, callback);
	            },
	            getStyle = elem.currentStyle ? function (name) {
	                    var val = elem.currentStyle[name];

	                    if (name === 'height' && val.search(/px/i) !== 1) {
	                            var rect = elem.getBoundingClientRect();
	                            return rect.bottom - rect.top -
	                                    parseFloat(getStyle('paddingTop')) -
	                                    parseFloat(getStyle('paddingBottom')) + 'px';
	                    };

	                    return val;
	            } : function (name) {
	                            return getComputedStyle(elem, null)[name];
	            },
	            minHeight = parseFloat(getStyle('height'));


	    elem.style.resize = 'none';

	    var change = function () {
	            var scrollTop, height,
	                    padding = 0,
	                    style = elem.style;

	            if (elem._length === elem.value.length) return;
	            elem._length = elem.value.length;

	            if (!isFirefox && !isOpera) {
	                    padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
	            };
	            scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

	            elem.style.height = minHeight + 'px';
	            if (elem.scrollHeight > minHeight) {
	                    if (maxHeight && elem.scrollHeight > maxHeight) {
	                            height = maxHeight - padding;
	                            style.overflowY = 'auto';
	                    } else {
	                            height = elem.scrollHeight - padding;
	                            style.overflowY = 'hidden';
	                    };
	                    style.height = height + extra + 'px';
	                    scrollTop += parseInt(style.height) - elem.currHeight;
	                    document.body.scrollTop = scrollTop;
	                    document.documentElement.scrollTop = scrollTop;
	                    elem.currHeight = parseInt(style.height);
	            };
	    };

	    addEvent('propertychange', change);
	    addEvent('input', change);
	    addEvent('focus', change);
	    change();
	};

 
 function onselectfinished(){
	 var checkbox1s=$("input:checked[id^='checkbox1_']");
	 if(checkbox1s.length <1){
		 alert('请至少选择1个');
		 return; 
	 }
	 var inputs="";
	 for(var i=0;i<checkbox1s.length;i++){
		 var ids=$(checkbox1s[i]).attr("id").split("_");
		 var ival=$("#label1_"+ids[1]+"_"+ids[2]).html();
		 inputs=inputs+"<li><span contenteditable=\"true\" id=\"value2_"+ids[1]+"_"+i+"\">"+ival+"</span></li>";
		 
	 }
	 $("#ulsection2").html(inputs);
	 $("#section1").css("display","none");
	 $("#section2").css("display","block");
	 var textarea2s=$("textarea[id^='value2_']");
	 for(var i=0;i<textarea2s.length;i++){
		 autoTextarea(textarea2s[i]);
	 }
 }
 
 function onreselect(){
	 $("#section1").css("display","block");
	 $("#section2").css("display","none");
 }
 
 function onsavereport(){
	 var textareas=$("span[id^='value2_']");
	 var descriptions=[];
	 var userIds=[];
	 for(var i=0;i<textareas.length;i++){
		 var ids=$(textareas[i]).attr("id").split("_");
		 if($(textareas[i]).html()=='')continue;
		 userIds.push(parseInt(ids[1]));
		 descriptions.push($(textareas[i]).html());
	 }
	 var oncallback=function(){
		 window.location.reload();
	 }
	 var onerr=function(err,ex){
		 alert(err);
	 }
	 if(userIds.length<=0){
		 return;
	 }
	 else{
		 var summary=$("#textarea").val();
		 if(summary !='' && summary!='非必填，您可简要概述今天项目进展'){
			 ReportInterface.saveDailyReport(summary,descriptions,userIds,{callback:oncallback,errorHandler:onerr}); 
		 }
		  
	 }
	 
 }
</script>
<div class="wrap-page">
       <c:if test="${empty dailyReport }">
		<section class="teamreport-header">
			<p class="teamreport-title">产品日报分享</p>
			<p class="teamreport-p">请从团队成员日报中选择今天与项目进展有关的<span class="color-red"> 3 ~ 5 </span>事，编辑完成后立即分享给所有人。</p>
		</section>
	   </c:if>
		<c:if test="${not empty userSelfMarkList }">
		<section class="sec-border" id="section1">
			<div class="blank20"></div>
			
			
			  <c:forEach var="userSelfMark" items="${userSelfMarkList }">
			    <p class="check-style padding20">${userSelfMark.user.name }日报</p>
			    <ul id="userSelfMark_ul_${userSelfMark.user.id }" class="teamreport-ul">
			     <c:set var="tasklist" value="${fn:replace(userSelfMark.taskDescription, '</li>', '^')}"/>
			     <c:set var="tasklist" value="${fn:replace(tasklist, '<li>', '^')}"/>
			    <c:set var="tasks" value="${fn:split(tasklist, '^') }"></c:set>
				 <c:forEach var="task" items="${tasks }" varStatus="status">
				   <c:set var="task" value="${fn:trim(task)}"></c:set>
				   <c:if test="${task!='' }"><li><input id="checkbox1_${userSelfMark.user.id }_${status.count}" type="checkbox"><label id="label1_${userSelfMark.user.id }_${status.count}">${task }</label></li></c:if>				   			   
				 </c:forEach>
				
			    </ul>  
			  </c:forEach>
			<button id="btn_select" class="btn-wait" onclick="onselectfinished();">下一步，编辑</button>
			<div class="blank20"></div><div class="blank20"></div>
	     </section>
	     
	     <section class="sec-border" id="section2" style="display:none;">
			<div class="blank20"></div>
			<p class="check-style padding20">简要概述</p>
			<div class="summary">
				<textarea id="textarea" onfocus="if(this.value=='非必填，您可简要概述今天项目进展') {this.value='';}this.style.color='#000';"
                    onblur="if(this.value=='') {this.value='非必填，您可简要概述今天项目进展';this.style.color='#a0a0a0';}">非必填，您可简要概述今天项目进展</textarea>
			</div>
			<p class="check-style padding20">详细内容</p>
			
			<ul id="ulsection2" class="teamreport-ul list-decimal">				
			</ul>
			<button class="btn-change" onclick="onreselect();">重新选择</button><button class="btn-ack2" onclick="onsavereport();">立即分享</button>
			
			<div class="blank20"></div><div class="blank20"></div>
		</section>
	     
		</c:if>
		<c:if test="${not empty dailyReport }">
		  <section>
			<div class="blank20"></div>
			<p class="teamreport-title2 padding20">产品日报——${dailyReport.byUser.name }</p>
			<div class="summary">
				${dailyReport.summary }
			</div>
			<ol class="teamreport-ol">
			    <c:forEach items="${dailyReport.items }" var="item">
			     <li>${item.description }</li>
			    </c:forEach>				
			</ol>
			<button class="btn-wait">已分享</button>
			<div class="blank20"></div><div class="blank20"></div>
		</section>
		  
		</c:if>
	</div>
