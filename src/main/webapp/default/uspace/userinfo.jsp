<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    	<div class="noticeheader-wrap">
    		<div class="noticeheader-bg"></div>
    		<a class="icon-setbg" title="更换封面图像"></a>
			<a class="fl hot-flower" title="献花"><sup>${flower }</sup></a>
			<a class="fl headericon" title="一只果子狸"><img src="images/headers/head33.png"></a>
			<a class="fr hot-egg" title="砸蛋"><sup>${egg }</sup></a>
    		<div class="noticeheader-p">${cuser.nickName }<br /><c:if test="${not empty cuser.country }">${cuser.country.name }</c:if> | ${user.title.name }</div>
    	</div>

    	<div class="keepcenter-wrap">
    		<div class="keepcenter">
    			<ul class="score-ul clearfix">
    				<li>
    					<p><strong>${cuser.userScored }</strong></p>
    					<p>功勋值</p>
    				</li>
    				<li>
    					<p><strong>${cuser.userCoins }</strong></p>
    					<p>财富值</p>
    				</li>
    				<li>
    					<p><strong>${cuser.userPopular }</strong></p>
    					<p>人气值</p>
    				</li>
    			</ul>
    		</div>
    	</div>
       
    
    	