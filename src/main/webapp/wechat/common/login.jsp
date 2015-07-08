<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>KPI账号绑定</title>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes"><!--删除默认的苹果工具栏和菜单栏-->
<meta name="apple-mobile-web-app-status-bar-style" content="black" /><!--控制状态栏显示样式-->
<meta name="format-detection" content="telephone=no, email=no" />
<link href="/wechat/css/style.css" rel="stylesheet">
<link href="/wechat/css/detail.css" rel="stylesheet">
<script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
function login(){
	  if($("#username").val()==''|| $("#username").val()=='邮箱地址' || $("#username").val().indexOf('@')<0 || $("#password").val()=='')
	  {
		  $("#errmsg").html("无效的用户名或密码");
		  $("#errmsg").css("display","block");
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
		//  $("#p_msg").css("display","block");
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
	<div class="wrap-page2">
		<div class="login" id="div_login">
			<p id="p_msg"><span></span>您好！123<br />首次使用请先登陆KPI进行绑定。</p>
			<input type="text" id="username" value="" placeholder="邮箱">
			<input class="pswd" id="password" type="password" value="" placeholder="密码">
			<button onclick="login();">绑定</button>
		</div>
		<div class="login" id="div_msg" style="display:none;">
			<p class="p-center" id="p_msg">绑定成功!现在您可以<a href="javascript:WeixinJSBridge.invoke('closeWindow',{},function(res){});">返回</a>重新点击菜单了。</p>
		</div>
	
	</div>
</body>

</html>