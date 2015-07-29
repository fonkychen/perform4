<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="/js/usergroup.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	addUserGroup("div_userchoose",'${handleuser.id}','/staff/manindicator.html?userId=');
	$("a[id^='a_edit_']").click(function(){
		 var ids=$(this).attr("id").split("_");
		 $("#a_ok_"+ids[2]).css("display","block");
		 $(this).css("display","none");
		 $("#span_progress_"+ids[2]).css("display","none");
		 $("#input_progress_"+ids[2]).css("display","block");
		 $("#span_weight_"+ids[2]).css("display","none");
		 $("#input_weight_"+ids[2]).css("display","block");
		 $("#span_name_"+ids[2]).css("display","none");
		 $("#input_name_"+ids[2]).css("display","block");
	 });
});
var isSending=false;
function savemonthly(id){
	 var name=$("#input_name_"+id).val();
	 if(name==''){
		 alert('指标内容不能为空');return;
	 }
	 var weight=$("#input_weight_"+id).val();
	 if(weight=='' || isInteger(weight)){
		 alert("权重必须为整数");return;
	 }
	 var progress=$("#input_progress_"+id).val();
	 if(progress=='' || isInteger(progress)){
		 alert("进度必须为整数");return;
	 }
	 
	 var oncallback=function(){
		window.location.reload();
	 }
	 if(!isSending){
		  isSending=true;
		  //saveManProgress(Long userId,Long indicatorId,String name,Integer weight,Integer manProgress)
		  var indicatorId=id.indexOf('empty')>=0?null:id;
		  var pdata='userId=${handleuser.id}'+'&name='+name+'&weight='+weight+'&manProgress='+progress;
		  if(indicatorId!=null){
			  pdata=pdata+'&indicatorId='+indicatorId;
		  }
		  //IndicatorInterface.saveManProgress('${user.id}',indicatorId,name,weight,progress,{callback:oncallback});
		  $.ajax({
			  url:'/rest/indicator/manprogress/save.html',
			  type:'POST',
			  data:pdata,
			  success:oncallback,
			  error:function(xhr){
				  alert(xhr.responseText);
			  }
		  });
	 }
	 
}
function isInteger(n) {
   return n === +n && n === (n|0);
}
</script>
<div class="bg clearfix">
    	<h2>功勋英雄 〉 <a href="/staff/indicator.html">指标进度</a> 〉 主管测评</h2>
        <div id="div_userchoose"></div>
		<p class="dashline-title"><span>调整指标进度：</span></p>
		<table class="kpiProgress-table1">
			<tr>
				<th width="12%">指标</th>
				<th width="31%">指标内容</th>
				<th width="16%">所占权重(%)</th>
				<th width="12%">本月进度(%)</th>
                <th width="17%"></th>
				<th width="12%">编辑</th>
			</tr>
			 <c:forEach items="${milist }" var="mindicator" varStatus="status">
    				  <tr>
    					<td>KPI${status.count }</td>
    					<td class="text-left"><span id="span_name_${mindicator.indicator.id }">${mindicator.indicator.name }</span><input id="input_name_${mindicator.indicator.id }" type="text" value="${mindicator.indicator.name }" style="color: #bebebe;display:none;" onfocus="if(this.value==defaultValue) {this.style.color='#676566';}" onblur="if(!value) {this.style.color='#bebebe';}"></td>
    					<td><span id="span_weight_${mindicator.indicator.id}">${mindicator.indicator.weight }</span><input id="input_weight_${mindicator.indicator.id }" type="text" value="${mindicator.indicator.weight }" style="color: #bebebe;display:none;" onfocus="if(this.value==defaultValue) {this.style.color='#676566';}" onblur="if(!value) {this.style.color='#bebebe';}"></td>
    					<td id="td_progress_${mindicator.indicator.id }"><span id="span_progress_${mindicator.indicator.id }">${mindicator.managerProgress }</span><input id="input_progress_${mindicator.indicator.id }" type="text" value="${mindicator.managerProgress }" style="color: #bebebe;display:none;" onfocus="if(this.value==defaultValue) {this.style.color='#676566';}" onblur="if(!value) {this.style.color='#bebebe';}"></td>
    					<td><p class="lv-up clearfix"><span><strong style="width: ${mindicator.progress/2}%;"></strong></span></p></td>
    					
    					<td><a id="a_ok_${mindicator.indicator.id }" onclick="savemonthly('${mindicator.indicator.id}')" class="a-ok" style="display:none;">确定</a><a id="a_edit_${mindicator.indicator.id }">编辑</a></td>
    				  </tr>
    				</c:forEach>
    				
    				<c:forEach begin="${(fn:length(milist))+1 }" end="5" var="mindex">
    				   <tr>
    				     <td>KPI${mindex }</td>
    				     <td class="text-left"><input id="input_name_empty${mindex}" type="text" value="" style="color: #bebebe;" onfocus="if(this.value==defaultValue) {this.style.color='#676566';}" onblur="if(!value) {this.style.color='#bebebe';}"></td>
    				     <td><input id="input_weight_empty${mindex }" type="text" value="" style="color: #bebebe;" onfocus="if(this.value==defaultValue) {this.style.color='#676566';}" onblur="if(!value) {this.style.color='#bebebe';}"></td>
    				     <td><input id="input_progress_empty${mindex}" type="text" value="" style="color: #bebebe;" onfocus="if(this.value==defaultValue) {this.style.color='#676566';}" onblur="if(!value) {this.style.color='#bebebe';}"></td>
    				     <td></td>
    				     <td><a id="a_ok_empty${mindex }" onclick="savemonthly('empty${mindex}')" class="a-ok" >确定</a></td>
    				   </tr>
    				</c:forEach>
		</table>
        <p class="dashline-title"><span>每月功勋加成：</span></p>
        <table class="kpiProgress-table2">
            <tr>
                <th width="10%">时间</th>
                <th width="7.5%">一月</th>
                <th width="7.5%">二月</th>
                <th width="7.5%">三月</th>
                <th width="7.5%">四月</th>
                <th width="7.5%">五月</th>
                <th width="7.5%">六月</th>
                <th width="7.5%">七月</th>
                <th width="7.5%">八月</th>
                <th width="7.5%">九月</th>
                <th width="7.5%">十月</th>
                <th width="7.5%">十一月</th>
                <th width="7.5%">十二月</th>
            </tr>
            <tr>
                <td>功勋值总和</td>
                <c:forEach items="${mislist }" var ="mis">
                        <td>${mis.score }</td>
                      </c:forEach>
            </tr>
        </table>
        <p class="dashline-title"><span>自测——指标进度：</span></p>
		<table class="kpiProgress-table3">
			<tr>
				<th width="12.3%">日期</th>
					<c:forEach items="${indicators }" var="indicator" varStatus="status">
    					  <th width="14.28%">KPI ${status.count }</th>
    					</c:forEach>
				<th width="16.3%">总计</th>
			</tr>
		<c:forEach items="${map}" var="entry">
    				  <tr>
    				    <td>${entry.key }月</td>
    				    <c:set var="totalpro" value="0"></c:set>
    				    <c:forEach items="${entry.value }" var="mindi">
    				     <c:set var="totalpro" value="${totalpro+mindi.selfProgress*mindi.indicator.weight/100 }"></c:set>
    				     <td>${mindi.selfProgress }%</td>
    				    </c:forEach>
    				    <td><fmt:formatNumber maxFractionDigits="1" value="${totalpro }"></fmt:formatNumber></td>
    				  </tr>
    				</c:forEach>
		</table>
		<p class="dashline-title"><span>主管测评——指标进度：</span></p>
		<table class="kpiProgress-table3">
			<tr>
				<th width="12.3%">日期</th>
				<c:forEach items="${indicators }" var="indicator" varStatus="status">
    					  <th width="14.28%">KPI ${status.count }</th>
    					</c:forEach>
				<th width="16.3%">总计</th>
			</tr>
			<c:forEach items="${map}" var="entry">
    				  <tr>
    				    <td>${entry.key }月</td>
    				    <c:set var="totalpro" value="0"></c:set>
    				    <c:forEach items="${entry.value }" var="mindi">
    				    <c:set var="totalpro" value="${totalpro+mindi.managerProgress*mindi.indicator.weight/100 }"></c:set>
    				     <td>${mindi.managerProgress }%</td>
    				    </c:forEach>
    				    <td><fmt:formatNumber maxFractionDigits="1" value="${totalpro }"></fmt:formatNumber></td>
    				  </tr>
    				</c:forEach>
		</table>
		<div class="blank20"></div>
		<div class="keepcenter-wrap">
            <div class="keepcenter"><button class="btn-blue" onclick="window.location.href='/chart/statis/userindicator.html'">查看统计</button></div>
        </div>
        <div class="blank100"></div>
    </div>

