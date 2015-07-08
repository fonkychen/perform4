<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bg clearfix">
       <h2><a>个人中心</a> 〉我的成就</h2>
        <c:import url="/default/service/pbanner.jsp"></c:import>
        <div class="myinfo-wrap">
            <c:import url="/default/service/bmenu.jsp"></c:import>
            <p class="p-right">成就大全，不点亮不舒服！</p>
            <table class="table-ub firsttd-gray">
                <tr>
                    <th width="8%">&nbsp;</th>
                    <th width="16%">成就</th>
                    <th width="21%">获取方式</th>
                    <th width="16%">模块</th>
                    <th width="13%">奖励</th>
                    <th width="13%">进度</th>
                    <th width="13%">点亮</th>
                </tr>
              <c:forEach items="${models}" var="model" varStatus="status">
               <tr>
                    <td>${status.count }</td>
	    					<td>${model.name }</td>
	    					<td>${model.description }</td>
	    					<td>${model.achieveName }</td>
	    					<td>${model.coinnum}UB</td>
	    					<td>${model.record}/${model.num }</td>
	    					<td>
	    					 <c:choose>
	    					   <c:when test="${model.isOn == true }"> 
	    					     <img src="/default/images/icon_cd1.png"/>
	    					   </c:when>
	    					   <c:otherwise>
	    					     <img src="/default/images/icon_cd2.png"/>
	    					   </c:otherwise>
	    					 </c:choose>
	    					 
	    					</td>
                </tr>
              </c:forEach>
                
               
            </table>
            <div class="blank100"></div>
        </div>
        <div class="blank20"></div>

    </div>

