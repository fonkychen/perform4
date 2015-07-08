<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>KPI 365——充满乐趣的互动社区</title>
    <meta name="description" content="缔安软件自主研发的KPI互动社区，以KPI为基础，以快乐工作为原则，打造的SAAS化KPI365互动社区。">
    <meta name="keywords" content="KPI, 互动社区, OA, 在线办公, 娱乐化工作, 365">
   
    <link rel="icon" href="/favicon.ico" type="image/x-icon" /> 
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <tiles:insertAttribute name="head"></tiles:insertAttribute>
</head>

<body>
    <!--Topbar-->
    <tiles:insertAttribute name="top"></tiles:insertAttribute>
    <!--nav-->
   <tiles:insertAttribute name="nav"></tiles:insertAttribute>
    <!--main-->
    <div class="center clearfix">
        <div class="fl w710">
            <!--随便说说-->
           
            <tiles:insertAttribute name="topic"></tiles:insertAttribute>
            <div class="blank20"></div>

            <tiles:insertAttribute name="rank"></tiles:insertAttribute>
        </div>
        <div class="fr w274">
            <!--用户信息-->
            <tiles:insertAttribute name="right"></tiles:insertAttribute>
        </div>
    </div>
    <div class="blank20"></div>
    <!--返回顶部-->
    <tiles:insertAttribute name="side-nav"></tiles:insertAttribute>
    <!--Footer-->
    <tiles:insertAttribute name="foot"></tiles:insertAttribute>
</body>

</html>