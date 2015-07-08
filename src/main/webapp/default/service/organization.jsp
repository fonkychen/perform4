<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="bg clearfix">
        <h2><a>个人中心</a> 〉 官职俸禄</h2>
        <c:import url="/default/service/pbanner.jsp"></c:import>
        <div class="myinfo-wrap">
            <c:import url="/default/service/bmenu.jsp"></c:import>
            
            
         <div class="wrap-level-org clearfix">
                <c:if test="${not empty usergroup }">
      <ul class="level-1">
        <li class="level-1-li last-li"><span><c:if test="${not empty usergroup.owner }">${usergroup.owner.name }</c:if></span>
          <ul class="level-2">
            <c:set var="usercount2" value="${fn:length(usergroup.users)}"></c:set>
            <c:forEach items="${usergroup.users }" var="user2" varStatus="status2">
              <li <c:if test="${status2.count == usercount2 }">class="last-li"</c:if>><span>${user2.name }</span>
               <c:if test="${not empty user2.ownerGroup }">
                 <ul id="ul3_${status2.count }" class="level-3 clearfix">
                   <c:set var="usercount3" value="${fn:length(user2.ownerGroup.users)}"></c:set>
                   <c:set var="ul3_expand" value="1"></c:set>
                   <c:forEach items="${user2.ownerGroup.users }" var="user3" varStatus="status3">
                     <li <c:if test="${status3.count == usercount3 }">class="last-li"</c:if>><span>${user3.name }</span>
                       <c:if test="${not empty user3.ownerGroup }">
                         <c:set var="ul3_expand" value="0"></c:set>
                         <ul class="level-4">                           
								<li class="last-li">
								  <c:forEach items="${user3.ownerGroup.users }" var="user4">
								    <span>${user4.name }</span>
								  </c:forEach>								 
								</li>
						 </ul>
                       </c:if>
                     </li>
                   </c:forEach>
                   <c:if test="${ul3_expand == 1 }">
                     <script type="text/javascript">
                       $(document).ready(function(){
                    	   var lis=$("#ul3_${status2.count }").find("li");
                    	   var html="";
                    	   for(var i=0;i<lis.length;i++){
                    		   html=html+$(lis[i]).html();
                    	   }
                    	   $("#ul3_${status2.count }").html('<li class="last-li">'+html+'</li>');
                    	   
                       });
                     </script>
                   </c:if>
                 </ul> 
               </c:if>
                            
              </li>            
         </c:forEach>
          </ul>        
        </li>
      </ul>
   </c:if>
        </div>
       </div>
        <div class="blank20"></div>


    </div>

