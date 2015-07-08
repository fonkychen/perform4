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
  function loadcaptcha(){
	  var img_url='/common/getverifycode.html?t_date='+new Date();
	  $('#captcha_img').attr('src',img_url);
  }
  
  function check(){
	  var mail=$('#input_mail').val();
	  var captcha=$('#input_captcha').val();
	  if(mail=='' || captcha ==''){
		  return;
	  }
	  var data=$.ajax({
		  url:"/common/checkandreset.html",
		  data:"mail="+$('#input_mail').val()+"&captcha="+$('#input_captcha').val(),
		  type:'POST',
		  dataType:'xml',
		  async:false
		 
	  }).responseText;
	 // alert(data);
	  if(data=='ok'){
		  window.location.href='/common/findpass2.html';
	  }
	  else if(data=='err1'){
		  $('#span_mail').html('无效的邮件地址');
	  }
	  else if(data=='err2'){
		  $('#span_captcha').html('验证码不正确');
	  }
	  
  }
  
  $(document).ready(function(){
	  $('#input_mail').blur(function(){
		  if($('#input_mail').val()=='' || $('#input_mail').val().indexOf('@')<0){
			  $('#span_mail').html('邮件地址格式不正确');
		  }
		  else{
			  $('#span_mail').html('');
		  }
		  
	  });
      $('#input_captcha').blur(function(){
		  if($('#input_captcha').val()==''){
			  $('#span_captcha').html('验证码不能为空');
		  }
		  else{
			  $('#span_captcha').html('');
		  }
	  });
	  loadcaptcha();
	  
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
		<p class="p-top50">请输入您的邮箱：</p>
		<input id="input_mail" type="text"><span id="span_mail" class="ps-error fontColor_f00"></span>		
		<p>请输入验证码：</p>
		<span class="checkcode">
		 <img id="captcha_img" alt="看不清？" src="" onclick="loadcaptcha();"/>
		</span>
		<input id="input_captcha" class="checkcode-input" type="text"><span id="span_captcha" class="ps-error fontColor_f00"></span>
		
		<div class="pswd-bar"><button onclick="check();">下一步</button></div>

	</div>
	
</div>
<!-- copyright -->
<div class="copyright_home">© 2010-2013 &nbsp; 上海缔安软件技术有限公司 版权所有 &nbsp; 沪ICP备07510580号</div>
</body>
</html>