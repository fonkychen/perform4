<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>密码找回</title>
<meta name="description" content="KPI互动社区，欢乐工作每一天。" />
<meta name="keywords" content="KPI,诸侯争霸,功勋簿,绩效英雄,财富英雄,人气英雄" />
<link rel="stylesheet" type="text/css" href="/old/css/style.css" />
<link rel="stylesheet" type="text/css" href="/old/css/home.css" />
<link rel="icon" href="/favicon.ico" type="image/x-icon" /> 
<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
  function changepass(){
	  if($("#pass1").val().length<8 || $("#pass1").val().length>16){
		  $("#span_err1").html("密码长度应介于8位到16位之间");
		  return;
	  }
	  if($("#pass2").val()!=$("#pass1").val()){
		  $("#span_err2").html("密码不一致");
		  return;
	  }
	  
	  var data=$.ajax({
		  url:'/common/changepass.html',
		  data:'code=${code}&password='+$("#pass1").val(),
		  type:'POST',
		  dataType:'xml',
		  async:false
	  }).responseText;
	  
	  if(data=='ok'){
		  window.location.href='/common/resetpass2.html';
	  }
  } 
  $(document).ready(function(){
	  $("#pass1").blur(function(){
		  if($("#pass1").val().length<8 || $("#pass1").val().length>16){
			  $("#span_err1").html("密码长度应介于8位到16位之间");
		  }
		  else{
			  $("#span_err1").html('');
		  }
	  });
	  $("#pass2").blur(function(){
		  if($("#pass2").val()!=$("#pass1").val()){
			  $("#span_err2").html("密码不一致");
		  }
		  else{
			  $("#span_err2").html('');
		  }
	  });
	  
  });
</script>
</head>
<body>
<!-- header -->
<div class="header">
	<a href="index.html" class="index"><h1>群英荟萃——笑看谁是英雄！</h1></a>
</div>

<!-- Body -->
<div class="wrap clearfix">
	
	<div class="pswd">
		<p class="p-top20">现在您可以重新设定您的密码。</p>
		<p>新密码：</p>
		<input type="password"  id="pass1"><span id="span_err1" class="ps-error font14 fontColor_f00"></span>
		<p>重复输入新密码：</p>
		<input type="password" id="pass2"><span id="span_err2" class="ps-error fontColor_f00"></span>
		<div class="pswd-bar"><button onclick="changepass();">确认</button></div>

	</div>
</div>
<!-- copyright -->
<div class="copyright_home">© 2010-2013 &nbsp; 上海缔安软件技术有限公司 版权所有 &nbsp; 沪ICP备07510580号</div>
</body>
</html>