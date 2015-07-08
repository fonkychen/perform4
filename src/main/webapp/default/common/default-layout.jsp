<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
 <title><tiles:getAsString name="title"/></title>
 <meta name="description" content="每天对照下自己的KPI，对自己的工作进行一个小结吧！">
 <meta name="keywords" content="KPI, 互动社区, OA, 在线办公, 娱乐化工作, 365, 每日自评, 工作小结">
 <link rel="icon" href="/favicon.ico" type="image/x-icon" /> 
 <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
  <tiles:insertAttribute name="head"></tiles:insertAttribute>
</head>

<body>
    <!--Topbar-->
    <tiles:insertAttribute name="top"></tiles:insertAttribute>
    <!--NavBar-->
    <tiles:insertAttribute name="nav"></tiles:insertAttribute>
    <!--每日自评-->
    <tiles:insertAttribute name="content"></tiles:insertAttribute>
    <!--返回顶部-->
    <tiles:insertAttribute name="side-nav"></tiles:insertAttribute>
    <!--Footer-->
    <tiles:insertAttribute name="foot"></tiles:insertAttribute></body>

</html>
