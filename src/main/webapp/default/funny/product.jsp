<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="/js/jquery.form.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
	 $("#icon").change(function(event){
		   var input = event.target;

		    var reader = new FileReader();
		    reader.onload = function(){
		      var dataURL = reader.result;
		      var html="<img src='"+dataURL+"' width='154' height='105'/>";
		      $("#icon_img").html(html);
		    };
		    reader.readAsDataURL(input.files[0]);
	 });
	 $("input[name='isEntity']").change(function(){
		 var value=$("input[name='isEntity']:checked").val();
		 if(value=='1') {
			 $("#serviceName").attr("disabled","disabled");
		 }
		 else{
			 $("#serviceName").removeAttr("disabled");
		 }
	 });

	 $("#productform").submit(function(e) {
		 e.preventDefault();
		
		 if(($("#icon").val()=='' || $("#icon").val()==null) && !($("#id").length>0 && $("#id").val()!='')){
			 alert("请上传图片");
			 return;
		 }
		if($("#name").val()=='' || $("#name").val().length>6){
			alert("奖品名称不能为空或者长度大于6");
			 return;
		}
		if($("#description").val()=='' || $("#description").val().length > 6){
			alert("奖品描述不能为空或者长度大于6");
			 return;
		}
		if($("#coinNum").val()==''){
			alert("兑换金额不能为空");
			 return;
		}
		if($("#remainNum").val()==''){
			alert("奖品库存不能为空");
			 return;
		}
		if($("#icon").val()!='' && $("#icon").val()!=null){
			$.ajax( {
			      url: '/rest/mall/add.html'+"?dt="+new Date(),
			      type: 'POST',
			      data: new FormData( this ),
			      processData: false,
			      contentType: false,
			      success:function(){
			    	  window.location.href='/funny/mall/product.html';
			      },
			      error:function(xhr){
			    	  alert(xhr.responseText);
			      }
			});
		}
		else{
			$.ajax( {
			      url: '/rest/mall/edit.html'+"?dt="+new Date(),
			      type: 'POST',
			      data: $('#productform').serialize(),
			      
			      success:function(){
			    	  window.location.href='/funny/mall/product.html';
			      },
			      error:function(xhr){
			    	  alert(xhr.responseText);
			      }
			});
		}
		
		
	 });

 });
 
 

	function onproduct(){
		var oncallback=function(){
			window.location.reload();
		}
		if($("#id").length<=0 || $("#id").val()=='' || $("#remainNum").val()=='' || $("#remainNum").val()<=0){
			alert("数据不合法");
			return;
		}
		if(confirm("确实要重新上架改商品吗？")){
			$.ajax({
				url:'/rest/mall/onproduct.html?id='+$("#id").val()+"&remainNum="+$("#remainNum").val(),
				type:'GET',
				success:function(){
					window.location.href='/funny/mall/product.html';
				},
				error:function(xhr){
					alert(xhr.responseText);
				}
			});
		}		
	}

	

	function offproduct(pid){
		var oncallback=function(){
			window.location.reload();
		}
		if($("#id").length<=0 || $("#id").val()==''){
			alert("数据不合法");
			return;
		}
		if(confirm("确实要下架改商品吗？")){
			$.ajax({
				url:'/rest/mall/offproduct.html?id='+$("#id").val()+"&dt="+new Date(),
				type:'GET',
				success:function(){
					window.location.href='/funny/mall/product.html';
				},
				error:function(xhr){
					alert(xhr.responseText);
				}
			});
		}		
	}

	

</script>
 <div class="bg clearfix">
    	<h2>趣多多 〉 <a href="/funny/mall/index.html">碰碰运气</a> 〉 商城管理</h2>
    	<form id="productform" method="post">
    	 <c:if test="${not empty product }">
    	  <input type="hidden" id="id" value="${product.id }" name="id">
    	 </c:if>
    	
        <table class="prize-add">
            <tr>
                <td rowspan="5" width="40%">
                    <a class="upload" onclick="icon.click();" id="icon_img">
                     <c:choose>
                      <c:when test="${not empty product }">
                      <img src="/common/producticon.html?productId=${product.id }">
                      </c:when>
                      <c:otherwise>
                      上传封面
                      </c:otherwise>
                     </c:choose>
                    </a><!--正常显示“上传图片”四个字-->
                    
                    <input type="file" id="icon" name="icon" style="display:none;" />
                </td>
                <td><span>物品名称</span><input type="text" id="name" name="name" <c:if test="${not empty product }">value="${product.name }"</c:if> ></td>
            </tr>
            <tr>
                <td><span>物品描述</span><input type="text" id="description" name="description" <c:if test="${not empty product }">value="${product.description }"</c:if>></td>
            </tr>
            <tr>
                <td><span>兑换金额</span><input type="number" id="coinNum" name="coinNum" <c:if test="${not empty product }">value="${product.coinNum }"</c:if>></td>
            </tr>
            <tr>
                <td><span>物品库存</span><input type="number" id="remainNum" name="remainNum" <c:if test="${not empty product }">value="${product.remainNum }"</c:if>></td>
            </tr>
            <tr>
                <td><span>选择类型</span>
                <label><input class="checkbox-red" type="radio" name="isEntity" value="1" <c:if test="${(empty product) or (empty product.isEntity) or(product.isEntity)  }">checked="checked"</c:if> ><span>实物</span></label>
                <label><input class="checkbox-red" type="radio" name="isEntity" value="0" <c:if test="${(not empty product) and (not empty product.isEntity) and (not product.isEntity) }">checked="checked"</c:if> ><span>服务</span></label>
                <select id="serviceName" <c:if test="${(empty product) or (empty product.isEntity) or(product.isEntity)  }">disabled="disabled"</c:if> name="serviceName">
                  <c:forEach items="${mallServices }" var="mallService">
                   <option value="${mallService.serviceName }">${mallService.name }</option>
                  </c:forEach>
                </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                 <c:if test="${not empty product }">
                  <c:choose>
                   <c:when test="${product.productStatus == 'OnSale' }">
                    <a class="btn-red" onclick="offproduct();">下架</a>
                   </c:when>
                   <c:otherwise>
                    <a class="btn-red" onclick="onproduct();">上架</a>
                   </c:otherwise>
                  </c:choose>
                 
                 </c:if>
                  
                  
                  <button class="btn-red" type="submit">确定</button>
                </td>
            </tr>
        </table>
        </form>
        <div class="blank20"></div><div class="blank20"></div>
    	<p class="linetext"><span>商城物品管理</span></p>
        <div class="blank20"></div>
        
    	<ul class="prize">
    	   <c:forEach items="${page.content }" var="cproduct">
    	   <li>
    			<div class="fl"><div class="prize-img"><img src="/common/producticon.html?productId=${cproduct.id }" alt=""></div></div>
    			<div class="fr">
    				<p class="prize-name">${cproduct.name }</p>
    				<p class="prize-info">${cproduct.description }</p>
    				<p class="prize-price">${cproduct.coinNum }UB</p>
    				<p class="prize-stock">库存 ${cproduct.remainNum }</p>
    				<button class="btn-blue" onclick="window.location.href='/funny/mall/product/${cproduct.id}.html'">编辑</button>
    			</div>
    		</li>
    	   </c:forEach>
    		
    		
    	</ul>
    	
    	<div class="blank20"></div><div class="blank20"></div>
    	<c:import url="/default/common/page-index.jsp"></c:import>
    </div>
