<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="/js/jquery.flip.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#luckytime a").flip({
		 trigger: 'manual'
	});
	
	$("#luckytime a").click(function(){
		//alert($.browser.msie);
		
		lottery(this);
	});
	
	
});

function lottery(tag){
	var oncallback=function(coin){
		$(this).flip(true);
		$(tag).find(".back").append('<span class="numb">'+coin+'</span>');
		$("#luckyresult").html(coin);
		$("#luckyresult").css("display","block");
	}
	$.ajax({
		url:'/rest/wealth/lottery/request.html?dt='+new Date(),
		type:'GET',
		success:oncallback,
		error:function(xhr){
			alert(xhr.responseText);
		}
	});
	
}

function orderProduct(pid){
	  var oncallback=function(){
		  window.location.href='/funny/mall/orderlist.html';
	  }
	  if(confirm("确实要兑换该物品吗？")){
		  $.ajax({
			  url:'/rest/mall/order.html?productId='+pid+"&dt="+new Date(),
			  type:'GET',
			  success:oncallback,
			  error:function(xhr){
				  alert(xhr.responseText);
			  }
		  });  
	  }
	  else{
		  
	  }
}


</script>
 <div class="bg clearfix">
    	<h2>趣多多 〉 <a href="/funny/mall/index.html">碰碰运气</a></h2>
    	<p class="linetext"><span>选一张牌，试试手气</span></p>
    	<ul id="luckytime" class="luckytime clearfix">
    		<li>
    		   
    			<a>
    			  <div class="front">
    			   <img src="/default/images/luckytime-bg-1.jpg" />
    			  </div>
    			  <div class="back">
    			    <img src="/default/images/luckytime-bg-3.jpg" />
    			  </div>  
    			</a>
    			<p>点击翻牌</p>
    		</li>
    		<li>
    			<a>
    			 <div class="front"> 
    			  <img src="/default/images/luckytime-bg-2.jpg">
    			 </div>
    			 <div class="back">
    			  <img src="/default/images/luckytime-bg-3.jpg" />
    			 </div>
    			</a>
    			<p>点击翻牌</p>
    		</li>
    		<li>
    			<a>
    			 <div class="front"> 
    			  <img src="/default/images/luckytime-bg-1.jpg">
    			 </div>
    			 <div class="back">
    			  <img src="/default/images/luckytime-bg-3.jpg" />
    			 </div>
    			</a>
    			<p>点击翻牌</p>
    		</li>
    		<li>
    			<a>
    			 <div class="front"> 
    			  <img src="/default/images/luckytime-bg-2.jpg">
    			 </div>
    			 <div class="back">
    			  <img src="/default/images/luckytime-bg-3.jpg" />
    			 </div>
    			</a>
    			<p>点击翻牌</p>
    		</li>
    		<li>
    			<a>
    			 <div class="front"> 
    			  <img src="/default/images/luckytime-bg-1.jpg">
    			 </div>
    			 <div class="back">
    			  <img src="/default/images/luckytime-bg-3.jpg" />
    			 </div>
    			</a>
    			<p>点击翻牌</p>
    		</li>
    		<li>
    			<a>
    			 <div class="front"> 
    			  <img src="/default/images/luckytime-bg-2.jpg">
    			 </div>
    			 <div class="back">
    			  <img src="/default/images/luckytime-bg-3.jpg" />
    			 </div>
    			</a>
    			<p>点击翻牌</p>
    		</li>
    		<li>
    			<a>
    			 <div class="front"> 
    			  <img src="/default/images/luckytime-bg-1.jpg">
    			 </div>
    			 <div class="back">
    			  <img src="/default/images/luckytime-bg-3.jpg" />
    			 </div>
    			</a>
    			<p>点击翻牌</p>
    		</li>
    		<li>
    			<a>
    			 <div class="front"> 
    			  <img src="/default/images/luckytime-bg-2.jpg">
    			 </div>
    			 <div class="back">
    			  <img src="/default/images/luckytime-bg-3.jpg" />
    			 </div>
    			</a>
    			<p>点击翻牌</p>
    		</li>
    		
    		<div id="luckyresult" class="luckyresult" style="display:none;"></div>
    		
    	</ul>
    	<p class="linetext"><span>商城物品兑换</span></p>
    	<div class="blank20"></div>
    	<div class="keepcenter-wrap">
	    	<ul class="prize-menu keepcenter">
	    		<li><a href="/funny/mall/product.html">商城管理</a></li>
	    		<li><a href="/funny/mall/checkorder.html">批准兑换</a></li>
	    		<li><a href="/funny/mall/orderlist.html">兑换记录</a></li>
	    	</ul>
    	</div>
    	<div class="blank20"></div>
    	<ul class="prize">
    	  <c:forEach items="${products }" var="product">
             <li>
    			<div class="fl"><div class="prize-img"><img src="/common/producticon.html?productId=${product.id }" alt=""></div></div>
    			<div class="fr">
    				<p class="prize-name">${product.name }</p>
    				<p class="prize-info">${product.description }</p>
    				<p class="prize-price">${product.coinNum }UB</p>
    				<p class="prize-stock">库存 ${product.remainNum }</p>
    				<button class="btn-blue" onclick="orderProduct('${product.id}')">兑换</button>
    			</div>
    		</li>    	  
    	  </c:forEach> 		
    		
    	</ul>
    	<div class="blank20"></div>
    </div>
