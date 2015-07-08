<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
 $(document).ready(function(){
	 $("a[id^='a_edit_']").click(function(){
		 var ids=$(this).attr("id").split("_");
		 $("#a_ok_"+ids[2]).css("display","block");
		 $(this).css("display","none");
		 $("#span_"+ids[2]).css("display","none");
		 $("#input_"+ids[2]).css("display","block");
	 });
 });
 var isSending=false;
 function savemonthly(id){
	 var progress=$("#input_"+id).val();
	 if(progress=='' || isInteger(progress)){
		 alert("进度必须为整数");return;
	 }
	 
	 if(!isSending){
		  isSending=true;
		  //IndicatorInterface.saveSelfProgress(id,progress,{callback:oncallback});
		  $.ajax({
			  url:'/rest/indicator/progress/save.html',
			  type:'POST',
			  data:'indicatorId='+id+"&selfProgress="+progress,
			  success:function(){
				  window.location.reload();
			  },
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
    	<h2>功勋英雄 〉 指标进度</h2>
		<p class="dashline-title"><span>调整指标进度：</span>
		 <c:if test="${(empty user.userGroup) or (not empty user.ownerGroup)  }">
		 
		  <button class="btn-blue fr" onclick="window.location.href='/staff/manindicator.html'">主管评测</button>
		 </c:if>
		</p>
		<table class="kpiProgress-table1">
			<tr>
				<th width="12%">指标</th>
				<th width="31%">指标内容</th>
				<th width="19%">所占权重(%)</th>
				<th width="19%">本月进度(%)</th>
				<th width="19%">编辑</th>
			</tr>
			<c:forEach items="${milist }" var="mindicator" varStatus="status">
			 <tr>
				<td>KPI${status.count }</td>
    			<td class="text-left">${mindicator.indicator.name }</td>
    			<td>${mindicator.indicator.weight }</td>
    			<td id="td_progress_${mindicator.indicator.id }"><span id="span_${mindicator.indicator.id }">${mindicator.selfProgress }</span><input id="input_${mindicator.indicator.id }" type="text" value="${mindicator.selfProgress }" style="color: #bebebe;display:none;" onfocus="if(this.value==defaultValue) {this.style.color='#676566';}" onblur="if(!value) {this.style.color='#bebebe';}"></td>
    			<td><a id="a_ok_${mindicator.indicator.id }" onclick="savemonthly('${mindicator.indicator.id}')" class="a-ok" style="display:none;">确定</a><a id="a_edit_${mindicator.indicator.id }">编辑</a></td>
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
				<th width="14.28%">日期</th>
    			<c:forEach items="${indicators }" var="indicator" varStatus="status">
    			  <th width="14.28%">KPI ${status.count }</th>
    			</c:forEach>
    					
    					
    			<th width="14.28%">总计</th>
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
    					<th width="14.28%">日期</th>
    					<c:forEach items="${indicators }" var="indicator" varStatus="status">
    					  <th width="14.28%">KPI ${status.count }</th>
    					</c:forEach>
    					<th width="14.28%">总计</th>
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
