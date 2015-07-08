<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
var page=1;
 $(document).ready(function(){
	 
	 $("li[id^='li_']").hover(function(){
			
			var ids=$(this).attr("id").split("_");
			for(var i=1;i<=ids[2];i++){			
				$("#li_"+ids[1]+"_"+i).css("background","url(/default/images/icon-star2.png)")
			}
			
			for(var i=(parseInt(ids[2])+1);i<=5;i++){
				
				$("#li_"+ids[1]+"_"+i).css("background","url(/default/images/icon-star.png)")
			}
			
			$("#tipspan").html($(this).attr("tip"));
		},function(){
			var ids=$(this).attr("id").split("_");
			var starNum=$("#starvalue_"+ids[1]).val();
			for(var i=1;i<=5;i++){		
				if(i<=starNum){
					$("#li_"+ids[1]+"_"+i).css("background","url(/default/images/icon-star2.png)");
				}
				else{
					$("#li_"+ids[1]+"_"+i).css("background","url(/default/images/icon-star.png)");
				}
			}
			$("#tipspan").html("");
		});
		
		$("li[id^='li_']").click(function(){
			var ids=$(this).attr("id").split("_");
			for(var i=1;i<=ids[2];i++){			
				$("#li_"+ids[1]+"_"+i).css("background","url(/default/images/icon-star2.png)");		
			}
			for(var i=(parseInt(ids[2])+1);i<=5;i++){
				$("#li_"+ids[1]+"_"+i).css("background","url(/default/images/icon-star.png)");
			}
			$("#starvalue_"+ids[1]).val(ids[2]);
		});
	 
	 
	 page=1;
	 
	 shownexthistory();
 });
 
 function showrandom(){
	 var oncallback=function(user){
		$('#randomUserId').val(user.id);
		$("#user_name").html(user.name);
		if(user.userIcon!=null){
			$("#user_img").attr("src","/common/usericon.html?iconId="+user.userIcon.id);
		}
		else{
			$("#user_img").attr("src","");
		}
		
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
 
 function shownexthistory(){
	 var oncallback=function(randoms){
		 for(var i=0;i<randoms.length;i++){
			 var tr="<tr>";
			 tr=tr+'<td width="15%">'+timeformat(new Date(randoms[i].updated))+'</td>';
			 tr=tr+'<td width="20%"><span>'+randoms[i].actualScored+'</span></td>';
			 tr=tr+'<td width="40%">收到点评:<br />'+randoms[i].taskDescription+'</td>';
			 tr=tr+'<td width="25%">';
			 for(var j=0;j<randoms[i].randomItems.length;j++){
				 tr=tr+"旁白："+randoms[i].randomItems[j].commentCategory.name+'：'+randoms[i].randomItems[j].starNum+'<br/>';
			 }
			 tr=tr+'</td>';
			 
			 $('#random_history').append(tr);
		 }
		 
	 }
	 $.ajax({
		 url:'/rest/scored/random/get.html?page='+page,
		 type:'GET',
		 success:function(hisrandoms){
			page=page+1; 
			oncallback(hisrandoms.content);
		 },
		 error:function(xhr){
			 
		 }
	 
	 });
 }
 var isSending=false;
 function saveRandom(){
		var urmodel={};
		if($("#random_description").val()=='' || $("#random_description").val()=='对介位同事的一周表现还满意吗？请对他做中肯的评价喔，拜托了！'){
			myalert("内容不能空");
			return;
		}
		urmodel.description=$("#random_description").val();
		if($("#randomId").length>0){
			urmodel.randomId=$("#randomId").val();
		}
		if($("#randomUserId").length<=0 || $("#randomUserId").val()==''){
			myalert('没有可点评的用户');
			return;
		}
		urmodel.userId=$("#randomUserId").val();
		var rimodels=[];
		var inputs=$("input[id^='starvalue_']");
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
			window.location.reload();		
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
<div class="bg clearfix">
        <h2>功勋英雄 〉 有缘点评</h2>
        
       <div class="fl othermark-wrap">
            <c:choose>
              <c:when test="${not empty random }">
               <input type="hidden" id="randomId" value="${random.id }"/>
               <input type="hidden" id="randomUserId" value="${random.user.id }"/>
               <c:choose>
                 <c:when test="${not empty random.user.userIcon }">
                 <div class="othermark-bg"><img id="user_img" src="/common/usericon.html?iconId=${random.user.userIcon.id }"></div>
                 </c:when>
                 <c:otherwise>
                 <div class="othermark-bg"><img id="user_img" src=""></div>
                 </c:otherwise>
               </c:choose>               
               <p class="othermark-name" id="user_name">${random.user.name }</p>
              </c:when>
              <c:otherwise>
               <input type="hidden" id="randomUserId" value=""/>
               <div class="othermark-bg"><img id="user_img" src=""></div>
               <p class="othermark-name" id="user_name"></p>
               <button class="btn-othermark" onclick="showrandom();"></button>
               <em class="othermark-em">随机出现，刷新可更换</em>
               <script type="text/javascript">
                 $(document).ready(function(){
                	 showrandom();
                 });
               </script>
              </c:otherwise>
            </c:choose>
          
        </div>
        <div class="fl">
            <dl class="selfmark-star">
                <dt>给随机人员评分：</dt>
                <c:forEach items="${randomcats }" var="randomcat">
                  <c:set var="starNum" value="0"></c:set>
                  <c:if test="${not empty random }">
                    <c:forEach items="${random.randomItems }" var="randomItem">
                      <c:if test="${randomItem.commentCategory.id eq randomcat.id }">
                         <c:set var="starNum" value="${randomItem.starNum }"></c:set>
                      </c:if>
                    </c:forEach>                           
                  </c:if>
                  <input type="hidden" id="starvalue_${randomcat.id }" value="${starNum }">
                  <dd><span class="starName">${randomcat.name }</span>
                  <ul class="starUl">
                            <c:choose>
    				       <c:when test="${starNum<1 }">
    				        <li id="li_${randomcat.id }_1" tip="${randomcat.starTip1 }" style="background:url(/default/images/icon-star.png)" ></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${randomcat.id }_1" tip="${randomcat.starTip1 }" style="background:url(/default/images/icon-star2.png) "></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<2 }">
    				        <li id="li_${randomcat.id }_2" tip="${randomcat.starTip2 }" style="background:url(/default/images/icon-star.png)"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${randomcat.id }_2" tip="${randomcat.starTip2 }" style="background:url(/default/images/icon-star2.png)"></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<3 }">
    				        <li id="li_${randomcat.id }_3" tip="${randomcat.starTip3 }" style="background:url(/default/images/icon-star.png)"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${randomcat.id }_3" tip="${randomcat.starTip3 }" style="background:url(/default/images/icon-star2.png)"></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<4 }">
    				        <li id="li_${randomcat.id }_4" tip="${randomcat.starTip4 }" style="background:url(/default/images/icon-star.png)"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${randomcat.id }_4" tip="${randomcat.starTip4 }" style="background:url(/default/images/icon-star2.png)"></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				      <c:choose>
    				       <c:when test="${starNum<5 }">
    				        <li id="li_${randomcat.id }_5" tip="${randomcat.starTip5 }" style="background:url(/default/images/icon-star.png)"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${randomcat.id }_5" tip="${randomcat.starTip5 }" style="background:url(/default/images/icon-star2.png)"></li>
    				       </c:otherwise>
    				     </c:choose>
                          
                        </ul>
                </c:forEach>
               
                <dd class="lastdd" ><span id="tipspan"></span></dd>
            </dl>
            <textarea class="othermark-text" name="" id="random_description"
            onfocus="if(this.value=='对介位同事的一周表现还满意吗？请对他做中肯的评价喔，拜托了！') {this.value='';}this.style.color='#456';"
            onblur="if(this.value=='') {this.value='对介位同事的一周表现还满意吗？请对他做中肯的评价喔，拜托了！';this.style.color='#bdc3c7';}"
            ><c:choose><c:when test="${not empty random }">${random.taskDescription }</c:when><c:otherwise>对介位同事的一周表现还满意吗？请对他做中肯的评价喔，拜托了！</c:otherwise></c:choose></textarea>
            <p class="wordscount"></p>
            <button class="btn-blue fr" onclick="saveRandom();">确定</button>
        </div>

        <div class="blank100"></div><div class="blank100"></div>
        <table class="othermark-history" >
           <tbody id="random_history">
            
           </tbody>
           <tfoot>
            <tr>
                <td colspan="4"><a onclick="shownexthistory();">显示更多</a></td>
            </tr>
           </tfoot>
        </table>
        <div class="blank100"></div><div class="blank100"></div>
    </div>
