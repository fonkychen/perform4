<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>KPI互动社区——这一次，鹿死谁手</title>
<meta name="description" content="KPI互动社区，欢乐工作每一天。">
<meta name="keywords" content="KPI,诸侯争霸,功勋簿,绩效英雄,财富英雄,人气英雄">
<link rel="stylesheet" type="text/css" href="/old/css/style.css" />
<link rel="stylesheet" type="text/css" href="/old/css/kpi365.css" />
<link rel="icon" href="/favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  $("#password").keyup(function(event){
			
		    if(event.keyCode == 13){
		    	login();
		    }
		});
  });
  
  function login(){
	  if($("#username").val()==''|| $("#username").val()=='邮箱地址' || $("#username").val().indexOf('@')<0 || $("#password").val()=='')
	  {
		  $("#errmsg").html("无效的用户名或密码");
		  $("#errmsg").css("display","block");
	     return;	  
	  }
	 
	  
	  var response=$.ajax({
		  type: "POST",
		  url: '/kpi_security_check_userpassword?kpiredirect='+(getUrlParameter("kpiredirect")==null?"/home/index.html":getUrlParameter("kpiredirect")),
		  data: $("#loginForm").serialize(),
		  async:false
		}).responseText;
	 
	  var obj=$.parseJSON(response);
	  if(obj.success=='false'){
		  //alert(obj.message);
		  $("#errmsg").html(obj.message);
		  $("#errmsg").css("display","block");
	  }
	  else{
		  window.location.href=obj.targetUrl;
	  }
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
      
      return null;
  }          
</script>
</head>
<body>
<!--Page 1-->
<a class="top-info" id="page1"><span>KPI互动社区 让绩效系统华丽变身！</span><i>HOT</i></a>
<div class="wrap-100 clearfix">
    <div class="wrap-center topbg"><img src="/old/images/kpi365-topbg.png" alt="创业英雄榜"></div>
</div>
<div class="wrap-100 clearfix">
    <div class="wrap-center">
      <form id="loginForm" method="post">
    	<div class="loginbox">
			<input type="text" id="username" name="kpi_username" value="邮箱" style="color: #b5b5b5;" onfocus="if(this.value==defaultValue) {this.value='';this.style.color='#404040';}" onblur="if(!value) {value=defaultValue;this.style.color='#b5b5b5';}">
			<span></span>
			<input type="text" id="password" name="kpi_password" value="密码" style="color: #b5b5b5;" onfocus="if(this.value==defaultValue) {this.value='';this.type='password';this.style.color='#404040';}" onblur="if(!value) {value=defaultValue; this.type='text'; this.style.color='#b5b5b5';}" />
			<input class="checkbox_login" checked="checked" type="checkbox" id="kpi_remember_me" name="kpi_remember_me" style="display:none;"/>
			<a onclick="login();" class="login">登 录</a>
		</div>
	  </form>
    </div>
</div>
<div class="wrap-100 clearfix">
    <div class="wrap-center">
    	<div class="wechatbox">
    	  <p id="errmsg"></p> 
    	  <p class="forget-pasd"><a href="/common/findpass.html">忘记密码？</a></p>
    	     	  
    		<a class="wechat-kpi365">
	    		<svg width="32" height="26">
				  <image xlink:href="/old/images/wechat.svg" src="/old/images/wechat.png" width="32" height="26" />
				</svg>
	    		官方微信
	    		<img class="img-wechat" src="/old/images/wechat-kpi365.png">
    		</a>
		</div>
    	<div class="top-topic2"><img src="/old/images/icon_login_password.png"></div>
    </div>
</div>
<div class="wrap-100 clearfix">
    <div class="wrap-center">
		<svg width="226" height="376">
		  <image xlink:href="/old/images/kpi365-img01.svg" src="/old/images/kpi365-img01.png" width="226" height="376" alt="核心目标：凝聚人才" />
		</svg>
    </div>
</div>
<!--Page 2-->
<div class="wrap-100 clearfix">
    <div class="wrap-center margin-top45 adjust"><img src="/old/images/kpi365-img02.png" alt="管理沟通，绩效激励"></div>
</div>
<div class="wrap-100 clearfix">
    <div class="wrap-center margin-top45"><img src="/old/images/kpi365-img03.png" alt="英雄榜"></div>
</div>
<!--Page 3-->
<div class="wrap-100 clearfix">
	<div class="wrap-center"><p class="main-title">充满乐趣的KPI365互动社区</p></div>
</div>
<div class="level1"></div>
<div class="wrap-100 clearfix">
	<dl class="wrap-center function-dl">
		<dt>
			<svg width="124" height="23">
			  <image xlink:href="/old/images/dt-1.svg" src="/old/images/dt-1.png" width="124" height="23" />
			</svg>
		</dt>
		<dt>
			<svg width="124" height="23">
			  <image xlink:href="/old/images/dt-2.svg" src="/old/images/dt-2.png" width="124" height="23" />
			</svg>
		</dt>
		<dt>
			<svg width="124" height="23">
			  <image xlink:href="/old/images/dt-3.svg" src="/old/images/dt-3.png" width="124" height="23" />
			</svg>
		</dt>
		<dt>
			<svg width="124" height="23">
			  <image xlink:href="/old/images/dt-4.svg" src="/old/images/dt-4.png" width="124" height="23" />
			</svg>
		</dt>
		<dd><img src="/old/images/kpi365-dd-1.png"></dd>
		<dd><img src="/old/images/kpi365-dd-2.png"></dd>
		<dd><img src="/old/images/kpi365-dd-3.png"></dd>
		<dd><img src="/old/images/kpi365-dd-4.png"></dd>
	</dl>
</div>
<!--Page 4-->
<div class="wrap-100 clearfix">
	<div class="wrap-center"><p class="main-title2">开启中小微企业高效互联网时代</p></div>
</div>
<div class="wrap-100 clearfix">
	<ul class="wrap-center function-ul">
		<li><img src="/old/images/kpi365-p4-01.png"></li>
		<li><img src="/old/images/kpi365-p4-02.png"></li>
		<li><img src="/old/images/kpi365-p4-03.png"></li>
		<li><img src="/old/images/kpi365-p4-04.png"></li>
		<li><img src="/old/images/kpi365-p4-05.png"></li>
		<li><img src="/old/images/kpi365-p4-06.png"></li>
	</ul>
</div>
<div class="wrap-100 clearfix">
	<div class="wrap-center function3-wrap">
		<dl class="function3">
			<dt>明确战略方向</dt>
			<dd><span class="list-box"></span>合理化优先级战略与企业文化</dd>
			<dd><span class="list-box"></span>关键策略快速传递与部署</dd>
			<dd><span class="list-box"></span>各岗位职责明确目标一致</dd>
		</dl>
		<dl class="function3">
			<dt>提高整体效率</dt>
			<dd><span class="list-box"></span>提升内部与跨部门的沟通效率</dd>
			<dd><span class="list-box"></span>提高单个作战与团队作战能力</dd>
			<dd><span class="list-box"></span>成就感的快速实现与累积</dd>
		</dl>
		<dl class="function3">
			<dt>降低无形成本</dt>
			<dd><span class="list-box"></span>降低小概率的沟通低效</dd>
			<dd><span class="list-box"></span>杜绝不围绕核心指标的战略部署</dd>
			<dd><span class="list-box"></span>通过提高员工忠诚度以降低离职率</dd>
		</dl>
	</div>
</div>
<!--Page 5-->
<div class="btm-bg">
	<div class="wrap-100 clearfix">
		<div class="wrap-center">
			<div class="btm-bg2">
				<p>TEL · 400-670-6006</p>
				<ul class="links">
					<li><img src="/old/images/wechat-aolc.png">缔安官方微信</li>
					<li><img src="/old/images/wechat-kpi365.png">kpi365微信版</li>
					<li><span class="ppt">PPT</span>下载PPT文档</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="wrap-100 clearfix">
		<div class="wrap-center"><a href="#page1" class="backtop"></a><a href="#page1" class="backtop-span">BACK TOP</a></div>
	</div>
	<div class="wrap-100 clearfix">
	    <div class="wrap-center">
			<svg width="119" height="23">
			  <image xlink:href="/old/images/KPI365-footer.svg" src="/old/images/KPI365-footer.png" width="119" height="23" />
			</svg>
	    </div>
	</div>
	<div class="wrap-100 clearfix">
		<div class="wrap-center"><p class="btm-copyright">©2010-2015 上海缔安软件技术有限公司 版权所有   沪ICP备07510580号</p></div>
	</div>
</div>

</body>
</html>