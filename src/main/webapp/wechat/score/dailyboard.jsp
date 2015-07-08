<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="/js/json2.js"></script>
<script type="text/javascript">
   $(document).ready(function(){
	   $("input[id^='input_']").keyup(function(event){	
		   /*
		    if(event.keyCode == 13){
		    	
		    	var ids=$(this).attr("id").split("_");
		    	var typeid=$("#select_"+ids[1]+"_"+ids[2]+"_"+ids[3]+"_"+ids[4]).val();
		    	if(typeid=='' || typeid==null) {alert('请选择类型');return;}
		    	saveTask(ids[1],ids[2],ids[3],ids[4],typeid);
		    	
		    }
		   */
		});  
	   /*
	   $("input[id^='input_']").blur(function(){
		   var ids=$(this).attr("id").split("_");
	       var typeid=$("#select_"+ids[1]+"_"+ids[2]+"_"+ids[3]+"_"+ids[4]).val();
	       if(typeid=='' || typeid==null) {alert('请选择类型');return;}
	       saveTask(ids[1],ids[2],ids[3],ids[4],typeid);
	   });
	   */
	   
	   $("select[id^='select_']").change(function(){
		   var ids=$(this).attr("id").split("_");
		   var oid="option_"+ids[1]+"_"+ids[2]+"_"+ids[3]+"_"+$(this).val();
		   $(this).css("background",$("#"+oid).attr("color"));
	   });
   });
   
   var isSaving=false;
   function saveTask(){
	   var inputs=$("input[id^='input_']");
	   var models=[];
	   for(var i=0;i<inputs.length;i++){
		   if($(inputs[i]).val()=='') continue;
		   var ids=$(inputs[i]).attr("id").split("_");
		   var sid="select_"+ids[1]+"_"+ids[2]+"_"+ids[3]+"_"+ids[4];
		   var typeId=$("#"+sid).val();
		   
		   var model={};
		   if($(inputs[i]).attr("name")!='')model.id=$(inputs[i]).attr("name");
		   model.yearNum=ids[1];
		   model.monthNum=ids[2];
		   model.dayNum=ids[3];
		   model.taskTypeId=typeId;
		   model.userId=ids[4];
		   model.task=$(inputs[i]).val();
		   
		   models.push(model);
	   }
	  
	   if(!isSaving){
	   		isSaving=true;
	   		
	   		$.ajax({
	   			url:'/rest/scored/dailyboard/savegroup.html',
	   			type:'POST',
	   			data:JSON.stringify(models),
				contentType:"application/json; charset=utf-8",
				success:function(){
					window.location.reload();
				},
				error:function(xhr){
					alert(xhr.responseText);
				}
	   		});
	   }
	   
   }
  
   
</script>


	
	<div class="wrap-page bgfff">
		<section>
			<ul class="daily">
			    <c:forEach items="${dbmodel.staffDailyBoardItemModels }" var="item" varStatus="status">
			    <c:if test="${(item.isEditable==true) or ((item.isEditable==false) and (not empty item.task)) }">
			     <li class="clearfix">
			        
					<p><c:choose><c:when test="${status.count == 1 }">周一</c:when><c:when test="${status.count == 2 }">周二</c:when><c:when test="${status.count == 3 }">周三</c:when><c:when test="${status.count == 4 }">周四</c:when><c:when test="${status.count == 5 }">周五</c:when><c:when test="${status.count == 6 }">周六</c:when><c:when test="${status.count == 7 }">周日</c:when></c:choose></p>
					<select id="select_${item.yearNum }_${item.monthNum}_${item.dayNum}_${item.userId}"  <c:if test="${item.isEditable==false }">disabled="disabled"</c:if> class="daily-select" <c:if test="${not empty item.taskType }">style="background:${item.taskType.color }"</c:if> >
					   <c:forEach items="${typegroups}" var="typegroup">
					     <optgroup label="${typegroup.name }">
					       <c:forEach items="${typegroup.taskTypes }" var="taskType">
					         <c:choose>
					           <c:when test="${not empty item.taskType and (taskType.id eq item.taskType.id)}">
					             <option id="option_${item.yearNum }_${item.monthNum}_${item.dayNum}_${taskType.id}" selected="selected"  value="${taskType.id}" color="${taskType.color }"  onselect="$('#select_${item.yearNum }_${item.monthNum}_${item.dayNum}_${item.userId}').css('background','${taskType.color }');">${taskType.name }</option>
					           </c:when>
					           <c:otherwise>
					             <option id="option_${item.yearNum }_${item.monthNum}_${item.dayNum}_${taskType.id}" value="${taskType.id}" color="${taskType.color }"  onselect="$('#select_${item.yearNum }_${item.monthNum}_${item.dayNum}_${item.userId}').css('background','${taskType.color }');">${taskType.name }</option>
					           </c:otherwise>
					         </c:choose>
					       </c:forEach>
					     </optgroup>
					   </c:forEach>
					</select>
					
					<!-- 
					<ul class="daily-select">					    
						<li style="background:${item.taskType.color }">${item.taskType.name }</li>
					</ul>
					 -->
					<c:choose>
					  <c:when test="${item.isEditable==true  }">
					    <input id="input_${item.yearNum }_${item.monthNum}_${item.dayNum}_${item.userId}"  type="text" class="daily-content" value="${item.task }" name="${item.id }" />
					  </c:when>
					  <c:when test="${(item.isEditable==false) and (not empty item.task)  }">
					    <input readonly="readonly" type="text" class="daily-content" value="${item.task }" name="${item.id }" />
					  </c:when>
					  
					</c:choose>			
					
					
				 </li>
				  </c:if>
				 </c:forEach>
				
				
			</ul>
		</section>
		 <button class="btn-ack" onclick="saveTask();">确认</button>
	</div>
	
   