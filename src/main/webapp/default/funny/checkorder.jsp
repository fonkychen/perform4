<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
  $(document).ready(function(){
	  $("input[id^='order_check_']").click(function(){
		  var checked=$(this).is(':checked');
		  if(checked){
			  var ids=$(this).attr("id").split("_");
			  checkorder(ids[2]);
		  }
	  });
  });
  function checkorder(orderId){
	  var oncallback=function(){
		window.location.reload();
	  }
	  if(confirm("确认该物品已兑换？")){
		  $.ajax({
			  url:'/rest/mall/order/check.html?orderId='+orderId+"&dt="+new Date(),
			  type:'GET',
			  success:oncallback,
			  error:function(xhr){
				  alert(xhr.responseText);
			  }		  
		  });
	  }
	  //myconfirm('确认该物品已兑换？',function(){ShopMallInterface.checkOrder(orderId,{callback:oncallback})},function(){});
  }
</script>
 <div class="bg clearfix">
    	<h2>趣多多 〉 <a href="/funny/mall/index.html">碰碰运气</a> 〉 批准兑换</h2>
        <table class="table-ub">
            <tr>
                <th width="8%">&nbsp;</th>
                <th width="14%">姓名</th>
                <th width="20%">兑换物品</th>
                <th width="14%">所需UB</th>
                <th width="22%">兑换时间</th>
                <th width="22%">实物兑换</th>
            </tr>
            <c:forEach items="${page.content }" var="order">
            <tr>
                <td>
                  <c:choose>
                    <c:when test="${(empty order.checked) or (not order.checked) }">
                     <input id="order_check_${order.id }" class="checkbox-red" type="checkbox">
                    </c:when>
                    <c:otherwise>
                     <input class="checkbox-red" type="checkbox" checked="checked" disabled="disabled">
                    </c:otherwise>
                  </c:choose>
                  
                </td>
                <td>${order.orderUser.name }</td>
                <td>${order.mallProduct.name }</td>
                <td>${order.coinNum }</td>
                <td><fmt:formatDate value="${order.updated }" pattern="yyyy-MM-dd HH:mm"/></td>
                <td><c:if test="${not empty order.checkedTime }"><fmt:formatDate value="${order.checkedTime }" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
            </tr>
            </c:forEach>
        </table>
       <c:import url="/default/common/page-index.jsp"></c:import>
    </div>
 