<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
function savenick(){
	 if($("#nick_text").val()=='' || $("#nick_text").val()=='请输入昵称'){
		 myalert("请输入5个字之内的昵称");
		 return;
	 }
	 if(isChinese($("#nick_text").val())==false || $("#nick_text").val().length>5){
			$("#nick_error").html("昵称仅支持中文，最大不超过5字");
			$("#nick_error").css("display","block");
			return;
	 }
	 var oncallback=function(){
		window.location.reload();
	 }
	 
	$.ajax({
		url:'/rest/user/nickname/set.html',
		type:'POST',
		data:'nickName='+$("#nick_text").val(),
		error:function(xhr){
			alert(xhr.responseText);
		},
		success:oncallback
	
	});
}

function isChinese(temp)  
{  
  var re=/[^\u4e00-\u9fa5]/;  
  if(re.test(temp)) return false;  
  return true;  
}  

function savepass(){
	
	 if($("#oldpassword").val()==''){
		
		 alert("旧密码不能为空");
		 return;
	 }
	 
	 if($("#newpassword").val()==''){
		 alert("新密码不能为空");
		 return;
	 }
	 
	 if($("#newpassword").val()!=$("#conpassword").val()){
		 alert("请重新确认新密码");
		 return;
	 }
	 if($("#newpassword").val().length<8 || $("#newpassword").val().length>16){
		
		 alert("密码长度应介于8到16位之间");
		 return;
	 }
	 var oncallback=function(){
		 window.location.reload();		
	 }
	 $.ajax({
		 url:'/rest/user/password/set.html',
		 type:'POST',
		 data:'oldpassword='+$("#oldpassword").val()+'&newpassword='+$("#newpassword").val(),
		 success:oncallback,
		 error:function(xhr){
			 alert(xhr.responseText);
		 }
	 });
	 
}
</script>
<div class="bg clearfix">
       <h2><a>个人中心</a> 〉我的成就</h2>
        <c:import url="/default/service/pbanner.jsp"></c:import>
        <div class="myinfo-wrap">
            <c:import url="/default/service/bmenu.jsp"></c:import>
           <table class="myub-expend">
                <tr>
                    <td>昵称</td>
                    <td><input id="nick_text" type="text" value="${user.nickName }"></td>
                </tr>
                 <tr>
                    <td>&nbsp;</td>
                    <td><button class="btn-red" onclick="savenick();">保存</button></td>
                </tr>
            </table>
         
           <table class="myub-expend">
                <tr>
                    <td>原有密码</td>
                    <td><input id="oldpassword" type="password" value=""></td>
                </tr>
                <tr>
                    <td>新密码</td>
                    <td><input id="newpassword" type="password" value=""></td>
                </tr>
                <tr>
                    <td>重复密码</td>
                    <td><input id="conpassword" type="password" value=""></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><button class="btn-red" onclick="savepass();">保存</button></td>
                </tr>
            </table>
        </div>
        <div class="blank20"></div>


    </div>

