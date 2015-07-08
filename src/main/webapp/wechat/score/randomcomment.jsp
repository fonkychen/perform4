<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function(){
	$("li[id^='li2_']").hover(function(){
	    //alert("a");
		var ids=$(this).attr("id").split("_");
		
		for(var i=1;i<=ids[2];i++){			
			
			$("#li2_"+ids[1]+"_"+i).css("background","url(/wechat/images/icon-star-2.svg)");	
		}
		
		for(var i=(parseInt(ids[2])+1);i<=5;i++){
			
			$("#li2_"+ids[1]+"_"+i).css("background","url(/wechat/images/icon-star-1.svg)");	
		}		
		$("#tipspan_"+ids[1]).html($(this).attr("tip"));
	},
 
    function(){
		
		var ids=$(this).attr("id").split("_");
		var starNum=parseInt($("#starvalue2_"+ids[1]).val());		
		for(var i=1;i<=5;i++){		
			if(i<=starNum){				
				$("#li2_"+ids[1]+"_"+i).css("background","url(/wechat/images/icon-star-2.svg)");	
			}
			else{
				$("#li2_"+ids[1]+"_"+i).css("background","url(/wechat/images/icon-star-1.svg)");	
			}
		}
		if(starNum >0){
			$("#tipspan_"+ids[1]).html($("#li2_"+ids[1]+"_"+starNum).attr("tip"));
		}
		else{
			$("#tipspan_"+ids[1]).html('');
		}
		
	});
	
	$("li[id^='li2_']").click(function(){
		
		var ids=$(this).attr("id").split("_");
		for(var i=1;i<=ids[2];i++){			
			$("#li2_"+ids[1]+"_"+i).css("background","url(/wechat/images/icon-star-2.svg)");	
		}
		for(var i=(parseInt(ids[2])+1);i<=5;i++){
			$("#li2_"+ids[1]+"_"+i).css("background","url(/wechat/images/icon-star-1.svg)");	
		}
		$("#starvalue2_"+ids[1]).val(ids[2]);
		
		//window.setTimeout(function(){
			//$("#tipspan_"+ids[1]).html($("#li_2"+ids[1]+"_"+ids[2]).attr("tip"));
		//},200);
		
	});
	
	<c:if test="${empty currandom and not empty enablerandom}">
	selectUser();
	</c:if>
});

function selectUser(){
	var oncallback=function(user){
		if(user.userIcon!=null){
			$("#user_img").attr("src","/common/usericon.html?iconId="+user.userIcon.id);	
		}
		else{
			$("#user_img").attr("src","");
		}
		
		$("#user_name").html(user.name);
		$("#randomUserId").val(user.id);
	}
	 $.ajax({
		 url:'/rest/user/selectRandomUser.html',
		 type:'GET',
		 success:function(user){
			 oncallback(user);
		 },
		 error:function(xhr){
			 alert(xhr.responseText);
		 }
	 });
	
}
var isSending=false;
function saveRandom(){
	var urmodel={};
	if($("#random_description").val()=='' || $("#random_description").val()=='对介位同事的一周表现还满意吗？请对他做中肯的评价喔，拜托了！'){
		alert("内容不能空");
		return;
	}
	urmodel.description=$("#random_description").val();
	if($("#randomId").length>0){
		urmodel.randomId=$("#randomId").val();
	}
	if($("#randomUserId").length<=0 || $("#randomUserId").val()==''){
		alert('没有可点评的用户');
		return;
	}
	urmodel.userId=$("#randomUserId").val();
	var rimodels=[];
	var inputs=$("input[id^='starvalue2_']");
	for(var i=0;i<inputs.length;i++){
		var rimodel={};
		var ids=$(inputs[i]).attr("id").split("_");
		rimodel.randomCatId=ids[1];
		rimodel.starNum=$(inputs[i]).val();
		rimodels[i]=rimodel;
	}
	urmodel.rimodels=rimodels;
	var oncallback=function(){
		isSending=false;
		window.location.href='/wechat/selfmark-detail.html';	
	}
	var onerror=function(errString,exception){
		alert(errString);
		isSending=false;
		return;
	}
	if(!isSending){
		isSending=true;
		$.ajax({
			url:'/rest/scored/random/save.html',
			type:'POST',
			data:JSON.stringify(urmodel),
			contentType:"application/json; charset=utf-8",
			success:function(){
				oncallback();
			},
		    error:function(xhr){
		    	onerror(xhr.responseText);
		    }
		});
		
	}
}
</script>

	<div class="wrap-page">
		<!--头像栏-->
		 <c:if test="${not empty enablerandom }">
		  
           
           <section>
	    	<figure class="home-top clearfix">
	    	   <c:if test="${not empty currandom }"> 
                 <input type="hidden" id="randomId" value="${currandom.id }"/>
                 <input type="hidden" id="randomUserId" value="${currandom.user.id }"/>
                 <c:if test="${not empty currandom.user and not empty currandom.user.userIcon and not empty currandom.user.department }">
                   <img id="user_img" class="fl" src="/common/usericon.html?iconId=${currandom.user.userIcon.id }">
	    		   <figcaption id="user_name">${currandom.user.name }</figcaption>
                 </c:if>
                 
               </c:if>
	    		
	    		<c:if test="${empty currandom }">
	    		  <input type="hidden" id="randomUserId" value=""/>
	    		  <img class="fl" id="user_img">
	    		  <figcaption id="user_name"></figcaption>
	    		</c:if>
	    		<p>周五0点～周日24点，请对Ta进行点评吧！没有点评会被扣分哦！</p>
	    	</figure>
	    </section>
		<section class="sec-border">
			<ul class="star-rating clearfix">
			  <c:forEach items="${randomcats }" var="randomcat">
                         <c:set var="starNum" value="0"></c:set>
                         <c:if test="${not empty currandom }">
                          
                           <c:forEach items="${currandom.randomItems }" var="randomItem">
                         
                            
                             <c:if test="${randomItem.commentCategory.id eq randomcat.id }">
                               <c:set var="starNum" value="${randomItem.starNum }"></c:set>
                             </c:if>
                           </c:forEach>
                           
                         </c:if>
                          <input type="hidden" id="starvalue2_${randomcat.id }" value="${starNum }">
                  <li class="clearfix">
					<span>${randomcat.name }</span>
					<ul class="stars clearfix">
						  <c:choose>
    				       <c:when test="${starNum<1 }">
    				        <li id="li2_${randomcat.id }_1" tip="${randomcat.starTip1 }"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li2_${randomcat.id }_1" tip="${randomcat.starTip1 }" style="background-image:url(/wechat/images/icon-star-2.svg) "></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<2 }">
    				        <li id="li2_${randomcat.id }_2" tip="${randomcat.starTip2 }"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li2_${randomcat.id }_2" tip="${randomcat.starTip2 }" style="background-image:url(/wechat/images/icon-star-2.svg)"></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<3 }">
    				        <li id="li2_${randomcat.id }_3" tip="${randomcat.starTip3 }"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li2_${randomcat.id }_3" tip="${randomcat.starTip3 }" style="background-image:url(/wechat/images/icon-star-2.svg)"></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<4 }">
    				        <li id="li2_${randomcat.id }_4" tip="${randomcat.starTip4 }"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li2_${randomcat.id }_4" tip="${randomcat.starTip4 }" style="background-image:url(/wechat/images/icon-star-2.svg)"></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				      <c:choose>
    				       <c:when test="${starNum<5 }">
    				        <li id="li2_${randomcat.id }_5" tip="${randomcat.starTip5 }"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li2_${randomcat.id }_5" tip="${randomcat.starTip5 }" style="background-image:url(/wechat/images/icon-star-2.svg)"></li>
    				       </c:otherwise>
    				     </c:choose>
					</ul>
					<br />
					<p class="desc" id="tipspan_${randomcat.id }">
					   <c:if test="${not empty currandom }">
                          <c:forEach items="${currandom.randomItems }" var="randomItem">    
                          <c:if test="${randomItem.commentCategory.id eq  randomcat.id}">                   
                         <c:choose>
                           <c:when test="${randomItem.starNum == 1 }">
                             ${randomItem.commentCategory.starTip1 }
                           </c:when>
                           <c:when test="${randomItem.starNum == 2 }">
                             ${randomItem.commentCategory.starTip2 }
                           </c:when>
                           <c:when test="${randomItem.starNum == 3 }">
                             ${randomItem.commentCategory.starTip3 }
                           </c:when>
                           <c:when test="${randomItem.starNum == 4 }">
                             ${randomItem.commentCategory.starTip5 }
                           </c:when>
                           <c:when test="${randomItem.starNum == 5 }">
                             ${randomItem.commentCategory.starTip5 }
                           </c:when>
                         </c:choose>      
                         </c:if>                
                        </c:forEach>
                        </c:if>
					</p>
				 </li>	
                </c:forEach>
							
			</ul>
		</section>
		<div class="blank20"></div>
		<section class="sec-border">
			<textarea id="random_description" class="text" onfocus="if(this.value=='对介位同事的一周表现还满意吗？请对他做中肯的评价喔，拜托了！') {this.value='';}this.style.color='#000';" onblur="if(this.value=='') {this.value='对介位同事的一周表现还满意吗？请对他做中肯的评价喔，拜托了！';this.style.color='#a3a3a3';}"><c:choose><c:when test="${not empty currandom }">${currandom.taskDescription }</c:when><c:otherwise>对介位同事的一周表现还满意吗？请对他做中肯的评价喔，拜托了！</c:otherwise></c:choose></textarea>
			<c:choose>
			  <c:when test="${empty currandom }">
			    <button class="btn-change" onclick="selectUser();">换一位</button>
			    <button class="btn-ack2" onclick="saveRandom()">确 认</button>		
			  </c:when>
			  <c:otherwise>
			    <button class="btn-ack" onclick="saveRandom()">确 认</button>		
			  </c:otherwise>
			</c:choose>
				
			
			<div class="blank20"></div>
		</section>
		 
		 </c:if>
		 
		 
	     <c:if test="${empty enablerandom and not empty hisrandom}">
	           <section>
	    	<span class="score2">${hisrandom.actualScored*5 }</span>
	    </section>
		<section class="sec-border">
			<ul class="star-rating clearfix">
			  <c:forEach items="${hisrandom.randomItems }" var="randomItem">
			    <li class="clearfix">
					<span>${randomItem.commentCategory.name }</span>
					<ul class="stars clearfix">
						<c:forEach begin="1" end="${randomItem.starNum }">
						  <li style="background-image:url(/wechat/images/icon-star-2.svg);"></li>						  
						</c:forEach>
					</ul>
					<br />
					<p class="desc">
					 <c:choose>
                           <c:when test="${randomItem.starNum == 1 }">
                             ${randomItem.commentCategory.starTip1 }
                           </c:when>
                           <c:when test="${randomItem.starNum == 2 }">
                             ${randomItem.commentCategory.starTip2 }
                           </c:when>
                           <c:when test="${randomItem.starNum == 3 }">
                             ${randomItem.commentCategory.starTip3 }
                           </c:when>
                           <c:when test="${randomItem.starNum == 4 }">
                             ${randomItem.commentCategory.starTip5 }
                           </c:when>
                           <c:when test="${randomItem.starNum == 5 }">
                             ${randomItem.commentCategory.starTip5 }
                           </c:when>
                         </c:choose>      
					</p>
				</li>
			  </c:forEach>				
				
			</ul>
		</section>
		<div class="blank20"></div>
		<section class="sec-border">
			<textarea class="text" readonly="readonly" style="color: #000;">${hisrandom.taskDescription }</textarea>
			
			<div class="blank20"></div>
		</section>
	          
	     </c:if>
	  
	</div>
