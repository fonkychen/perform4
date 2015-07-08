<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
 $(document).ready(function(){
	 $("#type_text").focusin(function(){
		$("#type_select").css("display","block"); 
	 });
	 
	 $("#type_text").focusout(function(){
		 window.setTimeout(function(){
			 $("#type_select").css("display","none"); 
		 },200);		  
     });
	 
	 $("a[id^='type_select_']").click(function(){
		 $("#type_select").css("display","none");
		 $("#type").val($(this).attr("val"));
		 $("#type_text").val($(this).html());
	 });
	
	 $("#coin_text").focusin(function(){
		$("#coin_select").css("display","block");
	 });
	 $("#coin_text").focusout(function(){
		 window.setTimeout(function(){
			 $("#coin_select").css("display","none"); 
		 },200);
		
	 });
	 $("a[id^='coin_select_']").click(function(){
		$("#coin_text").val($(this).html());
		$("#coin_select").css("display","none");
		$("#coin").val($(this).attr("coin"));
	});
		
		
 });
 var isload=false;
 function saveTask(){
	 var title=$("#title").val();
	 var type=$("#type").val();
	 var description=$("#description").val();
	 var coin=$("#coin").val();
	 if(title.length <=0 || title.length > 20){
		 alert('标题长度应在1-20之间');
		 return;
	 }
	 
	 if(type == ''){
		 alert('请选择任务类型');
		 return;
	 }
	 if(coin == ''){
		 alert('请选择人物奖励');
		 return;
	 }
	 
	 if(!isload){
		 isload=true;
		 $.ajax({
			 url:'/rest/connect/task/save.html',
			 type:'POST',
			 data:'title='+title+'&taskType='+type+'&description='+description+'&coinNum='+coin,
			 success:function(){
				 window.location.href='/connect/task/index.html';
			 },
			 error:function(xhr){
				 alert(xhr.responseText);
			 }
		 }).done(function(){
			 isload=false;
		 });
	 }
	 
 }
</script>
<div class="bg clearfix">
        <h2>飞鸽传书 〉 <a href="/connect/task/index.html">任务中心</a> 〉 发布任务</h2>
        <table class="myub-expend">
            <tr>
                <td>任务标题</td>
                <td><input id="title" type="text" value=""></td>
            </tr>
            <tr>
                <td>任务类型</td>
                <td class="relative2 thistd"><!--IE7 hack:点击当前td时class增加thistd或style="z-index:10;"-->
                    <input type="hidden" id="type">
                    <input class="arrow" type="text" value="" id="type_text">
                    <ul class="select-ul" id="type_select">
                      <c:forEach items="${taskTypes }" var="taskType" varStatus="status">
                       <li><a id="type_select_${status.count }" val="${taskType }">${taskType.toString() }</a></li>
                      </c:forEach>                        
                    </ul>
                </td>
            </tr>
            <tr>
                <td>悬赏详情</td>
                <td><textarea id="description"></textarea></td>
            </tr>
           
            <tr>
                <td>任务奖励</td>
                <td class="relative2">
                    <input type="hidden" id="coin">
                    <input class="arrow" type="text" id="coin_text" value="">
                    <ul class="select-ul" id="coin_select">
                        <li><a id="coin_select_1" coin="3">3UB</a></li>
                        <li><a id="coin_select_2" coin="5">5UB</a></li>
                        <li><a id="coin_select_3" coin="10">10UB</a></li>
                        <li><a id="coin_select_4" coin="15">15UB</a></li>
                        <li><a id="coin_select_5" coin="30">30UB</a></li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><button class="btn-red" onclick="saveTask();">发布任务</button></td>
            </tr>
        </table>
        <dl class="tips">
            <dt>注意事项</dt>
            <dd>1、奖励由系统出，不会扣除HR个人的财富</dd>
        </dl>
        <div class="blank20"></div>
    </div>

