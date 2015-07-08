<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">

 function showcountry(cid){
	 $("tbody[id^='tbody_']").css('display','none');
	 $('#tbody_'+cid).css('display','table-row-group');
	
 }
</script>
<div class="bg clearfix">
        <h2>趣多多 〉 <a href="/funny/money/countrywealth.html">数数小钱</a></h2>
        <ul class="myub-nav clearfix">
            <li><a class="thistab" href="/funny/money/countrywealth.html">国家财富</a></li>
            <li><a href="/funny/money/editcountrywealth.html">支出录入</a></li>
            <li><a href="/funny/money/wealth.html">个人财富</a></li>
        </ul>

        <!--国家财富-->
        <div class="blank20"></div>
       <c:forEach items="${countries }" var="country" varStatus="status">
        <table class="ub-country">
            <thead>
                <tr onclick="showcountry('${country.id}')">
                    <th width="16%" style="background:${country.color}">${country.name }</th>
                    <th colspan="4">威望：第${status.count }名 &nbsp;  &nbsp; 财力：${country.wealth } &nbsp;  &nbsp; 人数：${fn:length(country.users) }<a><img src="/default/images/icon-more-grandet02.png"></a></th>
                </tr>
            </thead>
            <tbody id="tbody_${country.id }" <c:if test="${not (country eq user.country) }">style="display:none;"</c:if>  >
                <tr>
                    <th width="16%"><strong>财力</strong></th>
                    <th width="21%"><strong>收入</strong></th>
                    <th width="21%"><strong>支出</strong></th>
                    <th width="21%"><strong>收支详情</strong></th>
                    <th width="21%"><strong>结余</strong></th>
                </tr>
                <c:forEach items="${country.monthlyCountryWealthList }" var="countrywealth">
    				  <tr id="tr_${country.id }_${countrywealth.id}" >
    					<td>${countrywealth.yearNum }-${countrywealth.monthNum }</td>
    					<td>${countrywealth.income }</td>
    					<td>${countrywealth.outcome }</td>
    					<td class="expend">${countrywealth.description }</td>
    					<td>${countrywealth.settled }</td>
    				  </tr>
    			</c:forEach>               
            </tbody>
        </table>
      </c:forEach>
       
        <div class="blank20"></div>
        <dl class="tips">
            <dt>国家财富概述：</dt>
            <dd>1、各国的收入依据国家战况排名而定，每月自动结算。</dd>
            <dd>2、收入金额：第一名(人数+2)×150；第二名(人数+2)×50；其他无收入。</dd>
            <dd>3、支出金额由专人录入。 </dd>
        </dl>
        <!--国家财富end-->

      
    </div>
