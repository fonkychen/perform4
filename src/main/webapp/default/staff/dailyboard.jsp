<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" src="/js/json2.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("input[id^='input_']").focusin(function(event){
		var ids=$(this).attr("id").split("_");
		var dlid="dl_"+ids[1]+"_"+ids[2]+"_"+ids[3]+"_"+ids[4];
		
		$("#"+dlid).css("display","block");
		if($(this).val()=='请输入...'){
			$(this).val('');
			$(this).css('color','#456');
		}
	});
	$("input[id^='input_']").focusout(function(event){
		var ids=$(this).attr("id").split("_");
		var dlid="dl_"+ids[1]+"_"+ids[2]+"_"+ids[3]+"_"+ids[4];
		
		//
		
		if($(this).val()==''){
			$(this).val('请输入...');
			$(this).css('color','#959ea7');
		}
		window.setTimeout(function(){$("#"+dlid).css("display","none");},500);
	});
	$("dl[id^='dl_']").focusin(function(){
		$(this).css("display","block");
	});
    $("dl[id^='dl_']").focusout(function(){
    
		$(this).css("display","none");
	});
});
var isSaving=false;
function saveTask(yearNum,monthNum,dayNum,userId,typeId){
	var id="input_"+yearNum+"_"+monthNum+"_"+dayNum+"_"+userId;
	if($("#"+id).val()=='' || $("#"+id).val()=='请输入...'){
		alert('不能为空');
		return;
	}
	
	var model={};
	if($("#"+id).attr("name")!='')model.id=$("#"+id).attr("name");
	model.yearNum=yearNum;
	model.monthNum=monthNum;
	model.dayNum=dayNum;
	model.taskTypeId=typeId;
	//model.userId=userId;
	model.task=$("#"+id).val();
	
	var oncallback=function(){
		window.location.reload();
	}
	var onerror=function(msg){
		isSaving=false;
		alert(msg);
		return;
	}
	if(!isSaving){
		isSaving=true;
		$.ajax({
			url:'/rest/scored/dailyboard/save.html',
			type:'POST',		
			data:JSON.stringify(model),
			contentType:"application/json; charset=utf-8",
			success:function(){
			   oncallback();
			},
			error:function(xhr){
			   onerror(xhr.responseText);
			}
		});
	}	
}
</script>
 <div class="bg clearfix">
    	<h2>功勋英雄 〉 公事榜</h2>
    	<!--公事榜色块-->
    	<div class="clearfix">
	    	
	    	   <c:forEach var="typeGroup" items="${typeGroups }" varStatus="status">
	    	    <table class="dailyboard-colors">
	    	     <td>${typeGroup.name }</td>
	    	     <td><ul>
	    	     <c:forEach var="taskType" items="${typeGroup.taskTypes }">
	    	      <li><span style="background-color: ${taskType.color};"></span>${taskType.name }</li>	    	      
	    	     </c:forEach>
	    	    </ul></td>	 
	    	    </table>   	     
	    	   </c:forEach>
	    		
    	</div>

    	<!--公事榜选择-->
    	<div class="keepcenter-wrap">
    		<div class="keepcenter pagingdate">
    			<span class="blod">查询历史记录</span>
    			
    			<a href="/staff/dailyboard.html?yearNum=${preyear }&weekOfYear=${preweekofyear}" class="arrow blue"></a><!--不可点击时class里去掉blue-->
    			<div class="date">${weekstring }</div>
    			
    			<a href="/staff/dailyboard.html?yearNum=${nextyear }&weekOfYear=${nextweekofyear}" class="arrow right blue rightblue"></a>
    			<!--不可点击时class里去掉blue和rightblue-->
    			<span class="blod"><a href="/staff/dailyboard.html">回本周</a></span>
    			<c:if test="${(fn:length(user.ownerGroups)) >0 }">
    			 <button class="btn-blue" onclick="window.location.href='/staff/dailyboard/overtime.html'">加班审批</button><!--没加班改为btn-gray-->
    			</c:if>
    			
    		</div>
    	</div>
    	<!--公事榜表格-->
    	<table class="dailyboard">
    		<thead>
    			<tr>
    				<td width="4.5%"></td>
    				<td width="8%">姓名</td>
    				<td width="12.5%">周一</td>
    				<td width="12.5%">周二</td>
    				<td width="12.5%">周三</td>
    				<td width="12.5%">周四</td>
    				<td width="12.5%">周五</td>
    				<td width="12.5%">周六</td>
    				<td width="12.5%">周日</td>
    			</tr>
    		</thead>
    		<tbody>
    		   <c:forEach items="${cmodel.staffDailyBoardModels}" var="dbmodel" varStatus="status">
    		     <tr>
    		        <c:if test="${status.count == 1 }">
    			       <td rowspan="${fn:length(cmodel.staffDailyBoardModels) }">${cmodel.countryName }</td>
    			    </c:if>
    				
    				<td>${dbmodel.userName }</td>
    				 <c:forEach items="${dbmodel.staffDailyBoardItemModels }" var="item" varStatus="windex">
    			        <c:if test="${windex.count<=7 }">
    			          <c:choose>
    			          <c:when test="${item.isEditable==false }">
    			            
    			            <td <c:if test="${not empty item.taskType }">style="background:${item.taskType.color }"</c:if>>${item.task }</td>
    			            
    			          </c:when>
    			          <c:otherwise>
    			           <td><div class="relative" >
    			    <c:choose>
    			      <c:when test="${not empty item.task }">
    			        <input id="input_${item.yearNum }_${item.monthNum}_${item.dayNum}_${item.userId}" name="${item.id }" type="text" style="background:${item.taskType.color };color:#456"    				
    			    	value="${item.task }"  />
    			      </c:when>
    			      <c:otherwise>
    			        <input id="input_${item.yearNum }_${item.monthNum}_${item.dayNum}_${item.userId}" name="${item.id }" type="text" style="color:#959ea7"    				
    			    	value="请输入..."  />
    			      </c:otherwise>
    			    </c:choose> 
    				
    				
    				<dl tabindex="-1" id="dl_${item.yearNum }_${item.monthNum}_${item.dayNum}_${item.userId}" class="typechoose" style="display:none;">
    				    
    					<dt>工作类型选择：<a></a></dt>
    					<c:forEach items="${ typeGroups}" var="typeGroup">
    					 <dt>${typeGroup.name }</dt>
    					 <dd>
    					  <c:forEach items="${typeGroup.taskTypes }" var="taskType">
    					   <a <c:if test="${item.taskType.id eq taskType.id }">class="choosed"</c:if> onclick="saveTask('${item.yearNum }','${item.monthNum }','${item.dayNum }','${item.userId }','${taskType.id }')">${taskType.name }</a>
    					  </c:forEach>
    					 </dd>
    					</c:forEach>
    					
    				</dl>
    				
    				</div>
    				</td>

    			          </c:otherwise>
    			        </c:choose>
    			        </c:if>
    			        
    			      </c:forEach>
    			 </tr>
    		   </c:forEach>
    		  </tbody>
    		  
    		  <c:forEach items="${modellist}" var="sdbg">
    		    <tr class="line">
    		     	<td colspan="9"></td>
    		    </tr>
    		      <tbody>
    			 <c:forEach items="${sdbg.staffDailyBoardModels }" var="dbmodel" varStatus="status">
    			   <tr>
    			      <c:if test="${status.count == 1 }">
    			        <td rowspan="${fn:length(sdbg.staffDailyBoardModels) }">${sdbg.countryName }</td>
    			      </c:if>
    			      <td>${dbmodel.userName }</td>
    			      <c:forEach items="${dbmodel.staffDailyBoardItemModels }" var="item" varStatus="windex">
    			       <td <c:if test="${not empty item.taskType }">style="background:${item.taskType.color }"</c:if>>${item.task }</td>
    			      </c:forEach>
    			   </tr>
    		       			  
    			 </c:forEach>
    			  </tbody>  
    		   </c:forEach>
    		      			
    	</table>
    	<div class="blank100"></div>
    </div>
