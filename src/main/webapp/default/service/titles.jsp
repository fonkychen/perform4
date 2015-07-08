<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg clearfix">
        <h2><a>个人中心</a> 〉 官职俸禄</h2>
        <c:import url="/default/service/pbanner.jsp"></c:import>
        <div class="myinfo-wrap">
            <c:import url="/default/service/bmenu.jsp"></c:import>
            
            
            <c:forEach items="${titleGroups }" var="titleGroup">
            <div class="blank20"></div>
            <p class="linetext"><span><strong>${titleGroup.name }俸禄</strong></span></p>
             <div class="blank20"></div>
            <table class="table-ub">
                <tr>
                    <th width="12%">等级</th>
                    <th width="38%">官职名称</th>
                    <th width="20%">每周俸禄</th>
                    <th width="30%">所需功勋值</th>
                </tr>
                <c:forEach items="${titleGroup.titles }" var="title">
                <tr>
                    <td>${title.rank }</td>
                    <td>${title.name }</td>
                    <td>0</td>
                    <td>${title.bottom }-${title.upper }</td>
                </tr>
                </c:forEach>
                
            </table>
            </c:forEach>
            
           

           <div class="blank20"></div>
            <dl class="tips">
                <dt>官职俸禄概述：</dt>
                <dd>1、官职依据功勋值授予；</dd>
                <dd>2、不同的官职每周可获得不同的俸禄（UB）；</dd>
                <dd>3、官职所需的功勋值不会自动发生变化；</dd>
                <dd>4、管理员可重新设置官职所需功勋值。</dd>
            </dl>
        </div>
        <div class="blank20"></div>


    </div>

