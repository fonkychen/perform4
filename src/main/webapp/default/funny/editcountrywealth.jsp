<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
  function selectcountry(cid){
	  $("#country_choose").css('display','none');
	  $("#country_wealth_value").val('');
	  $("#outcome").val('');
	  $("#description").val('');
	  $("#country_text").val("请选择国家");
	  var oncallback=function(mcw){
		  if(mcw==null) return;
		  $("#outcome").val(mcw.outcome);
		  $("#description").val(mcw.description);
		  $("#country_wealth_value").val(mcw.id);	
		  $("#country_text").val($("#li_"+cid).html());
		  $("#settled").val(mcw.settled);		  
	  }
	  
	  $.ajax({
		  url:'/rest/wealth/country/monthly.html?countryId='+cid+"&dt="+new Date(),
		  type:'GET',
		  success:oncallback,
		  error:function(xhr){
			  alert(xhr.responseText);
		  }
	  });
	  //CountryInterface.getCurrentMonthlyWealth(cid,{callback:oncallback});
  }
  
  function saveWealth(){
	  if($("#country_wealth_value").val()=='' || $("#outcome").val()==''){
		  return;
	  } 	
	  
	  $.ajax({
		  url:'/rest/wealth/country/savewealth.html',
		  type:'POST',
		  data:'wealthId='+$("#country_wealth_value").val()+'&outcome='+$("#outcome").val()+'&description='+$('#description').val(),
		  success:function(){
			  window.location.href='/funny/money/countrywealth.html';
		  },
		  error:function(xhr){
			  alert(xhr.responseText);
		  }
	  });
	  
	 // CountryInterface.saveCountryWealth($("#country_wealth_value").val(),$("#outcome").val(),$("#description").val(),{callback:oncallback});
  }
</script>
<div class="bg clearfix">
        <h2>趣多多 〉 <a href="/funny/money/countrywealth.html">数数小钱</a></h2>
        <ul class="myub-nav clearfix">
            <li><a href="/funny/money/countrywealth.html">国家财富</a></li>
            <li><a class="thistab" href="/funny/money/editcountrywealth.html">支出录入</a></li>
            <li><a href="/funny/money/wealth.html">个人财富</a></li>
        </ul>

       
        <!--支出录入-->
        <table class="myub-expend">
            <tr>
                <td>国 &nbsp; &nbsp; &nbsp; 家</td>
                <td><div class="relative">
                    <input type="hidden" value="" id="country_value"/><input id="country_wealth_value" type="hidden" value=""/>
                    <input class="arrow" type="text" id="country_text" value="请选择国家" class="choose" onClick="if($('#country_choose').css('display')=='none') $('#country_choose').css('display','block');else $('#country_choose').css('display','none')" />
                    <ul class="select-ul" id="country_choose">
                       <c:forEach items="${ countries}" var="country" varStatus="status">
    						   <li><a id="li_${country.id }" onClick="selectcountry('${country.id}');$('#country_value').val('${country.id }');">${country.name }</a></li>
    						   <c:if test="${status.count  == 1 }">
    						     <script type="text/javascript">
    						     $(document).ready(function(){
    						    	 selectcountry('${country.id}');
    						    	 $('#country_value').val('${country.id }');
    						     });
    						   </script>
    						   </c:if>
    						   
    						  </c:forEach>  
                    </ul>
                </div></td>
            </tr>
            <tr>
                <td>当前结余</td>
                <td><input type="text" id="settled" ></td>
            </tr>
            <tr>
                <td>支出金额</td>
                <td><input type="number" id="outcome" ></td>
            </tr>
            <tr>
                <td>详 &nbsp; &nbsp; &nbsp; 情</td>
                <td><textarea id="description"></textarea></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><button class="btn-red" onclick="saveWealth();">保存</button></td>
            </tr>
        </table>
        <!--支出录入end-->

    
    </div>
