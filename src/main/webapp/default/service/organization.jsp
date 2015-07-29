<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="bg clearfix">
        <h2><a>个人中心</a> 〉 官职俸禄</h2>
        <c:import url="/default/service/pbanner.jsp"></c:import>
        <div class="myinfo-wrap">
            <c:import url="/default/service/bmenu.jsp"></c:import>
            
            
         <div class="wrap-level-org clearfix">
         <c:forEach var="usergroup" items="${usergroups }">
                <c:if test="${not empty usergroup }">
      <ul class="level-1">
        <li class="level-1-li last-li"><span><c:if test="${not empty usergroup.owner }">${usergroup.owner.name }</c:if></span>
          <ul class="level-2">
            <c:set var="usercount2" value="${fn:length(usergroup.users)}"></c:set>
            <c:forEach items="${usergroup.users }" var="user2" varStatus="status2">
              <li <c:if test="${status2.count == usercount2 }">class="last-li"</c:if>><span>${user2.name }</span>
               <c:if test="${fn:length(user2.ownerGroups) >0 }">
                 <ul id="ul3_${status2.count }" class="level-3 clearfix">
                  <c:forEach var="usergroup2" items="${user2.ownerGroups }">
                   <c:set var="usercount3" value="${fn:length(usergroup2.users)}"></c:set>
                   <c:set var="ul3_expand" value="1"></c:set>
                   <c:forEach items="${usergroup2.users}" var="user3" varStatus="status3">
                     <li <c:if test="${status3.count == usercount3 }">class="last-li"</c:if>><span>${user3.name }</span>
                       <c:if test="${fn:length(user3.ownerGroups) >0 }">
                         <c:set var="ul3_expand" value="0"></c:set>
                         <ul class="level-4">                           
								<li class="last-li">
								 <c:forEach var="usergroup3" items="${user3.ownerGroups }">
								   <c:forEach items="${usergroup3.users }" var="user4">
								    <span>${user4.name }</span>
								   </c:forEach>								 
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
                   </c:forEach>     
                 </ul> 
               </c:if>
                     
              </li>            
          </c:forEach>
      
         
          </ul>        
        </li>
        
      </ul>
   </c:if>
   </c:forEach>
        </div>
       </div>
        <div class="blank20"></div>


    </div>

