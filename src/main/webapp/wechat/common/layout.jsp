<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1,no-cache, no-store, must-revalidate">
<title><tiles:getAsString name="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes"><!--删除默认的苹果工具栏和菜单栏-->
<meta name="apple-mobile-web-app-status-bar-style" content="black" /><!--控制状态栏显示样式-->
<meta name="format-detection" content="telephone=no, email=no" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link href="/wechat/css/style.css" rel="stylesheet">
<link href="/wechat/css/detail.css" rel="stylesheet">
<script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    
		    WeixinJSBridge.call('hideOptionMenu');
	  });
  });
</script>
</head>
<body>
 <tiles:insertAttribute name="content"></tiles:insertAttribute>
 
 <tiles:insertAttribute name="footer"></tiles:insertAttribute>

</body>

</html>
