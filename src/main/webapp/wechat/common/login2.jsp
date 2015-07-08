<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>KPI绑定</title>
<meta name="viewport" content="width=device-width, initial-scale=0.5, user-scalable=no, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes"><!--删除默认的苹果工具栏和菜单栏-->
<meta name="apple-mobile-web-app-status-bar-style" content="black" /><!--控制状态栏显示样式-->
<meta name="format-detection" content="telephone=no, email=no" />
<link href="/wechat/css/style.css" rel="stylesheet">
<link href="/wechat/css/detail.css" rel="stylesheet">
<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
function login(){
	  if($("#username").val()==''|| $("#username").val()=='邮箱：' || $("#username").val().indexOf('@')<0 || $("#password").val()=='' || $("#password").val()=='密码：')
	  {
		  $("#p_msg").html("无效的用户名或密码");
		 // $("#errmsg").css("display","block");
	     return;	  
	  }
	 
	  
	  var response=$.ajax({
		  type: "POST",
		  url: '/kpi_security_check_userpassword',
		  data: "kpi_remember_me=on&"+"kpi_username="+$("#username").val()+"&kpi_password="+$("#password").val(),
		  async:false
		}).responseText;
	 
	  var obj=$.parseJSON(response);
	  if(obj.success=='false'){
		  //alert(obj.message);
		  $("#p_msg").html("用户名或者密码错误");
		  $("#p_msg").css("display","block");
	  }
	  else{
		 // window.location.href=obj.targetUrl;
		 mapping();
	  }
  }
  
  function mapping(){
	  var code=getUrlParameter("code");
	  if(code==null || code=='')return;
	  var response=$.ajax({
		  type: "POST",
		  url: '/wechat/createmapping.html?code='+code,		 
		  dataType:'text',
		  success:function(data,  textStatus, jqXHR){
			 if(data=='true') {
				// window.location.href="/wechat/mappingsuccess.html";
				$("#div_login").css("display","none");
				$("#div_msg").css("display","block");
			 }
			 else{
				// $("#p_msg").html("用户名或者密码错误");
				 window.location.href='/wechat/kpiauth.html';
			 }
		  }
		});
  }
  
  function getUrlParameter(sParam)
  {
      var sPageURL = window.location.search.substring(1);
      var sURLVariables = sPageURL.split('&');
      for (var i = 0; i < sURLVariables.length; i++) 
      {
          var sParameterName = sURLVariables[i].split('=');
          if (sParameterName[0] == sParam) 
          {
              return sParameterName[1];
          }
      }
  }       
</script>
</head>
<body>
	
	<table class="login" id="div_login" >
		<tbody>
		<tr>
			<td><p id="p_msg"><span></span>您好！<br />首次使用请先登陆KPI进行绑定。</p></td>
		</tr>
		<tr>
			<td><input id="username" type="text" value="邮箱：" onfocus="if(this.value=='邮箱：') {this.value='';}"  onblur="if(this.value=='') {this.value='邮箱：';}"></td>
		</tr>
		<tr>
			<td><input id="password" class="pswd"  type="text" value="密码：" onfocus="if(this.value==defaultValue) {this.value='';this.type='password';}" onblur="if(!value) {value=defaultValue; this.type='text'; }" ></td>
		</tr>
		<tr>
			<td><button onclick="login();">绑定</button></td>
		</tr>
		</tbody>
	</table>
	<table id="div_msg" class="login" style="display:none;">
		<tr>
			<td>
				<p class="p-center" >绑定成功!现在您可以<a href="javascript:WeixinJSBridge.invoke('closeWindow',{},function(res){});">返回</a>重新点击菜单了。</p>
			</td>
		</tr>
	</table>
</body>
</html>