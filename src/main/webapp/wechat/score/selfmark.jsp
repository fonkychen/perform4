<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css" href="/trumbowyg/design/css/trumbowyg.css">
<script src="/trumbowyg/trumbowyg.min.js"></script>
<script src="/trumbowyg/langs/zh_cn.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("li[id^='li_']").hover(function(){
		
		var ids=$(this).attr("id").split("_");
		for(var i=1;i<=ids[2];i++){			
			$("#li_"+ids[1]+"_"+i).css("background-image","url(/wechat/images/icon-star-2.svg)")
		}
		
		for(var i=(parseInt(ids[2])+1);i<=5;i++){
			
			$("#li_"+ids[1]+"_"+i).css("background-image","url(/wechat/images/icon-star-1.svg)")
		}
		
		$("#tip_"+ids[1]).html($(this).attr("tip"));
	},function(){
		var ids=$(this).attr("id").split("_");
		var starNum=$("#starvalue_"+ids[1]).val();
		for(var i=1;i<=5;i++){		
			if(i<=starNum){
				$("#li_"+ids[1]+"_"+i).css("background-image","url(/wechat/images/icon-star-2.svg)");
			}
			else{
				$("#li_"+ids[1]+"_"+i).css("background-image","url(/wechat/images/icon-star-1.svg)");
			}
		}
		
	});
	
	$("li[id^='li_']").click(function(){
		var ids=$(this).attr("id").split("_");
		for(var i=1;i<=ids[2];i++){			
			$("#li_"+ids[1]+"_"+i).css("background-image","url(/wechat/images/icon-star-2.svg)");		
		}
		for(var i=(parseInt(ids[2])+1);i<=5;i++){
			$("#li_"+ids[1]+"_"+i).css("background-image","url(/wechat/images/icon-star-1.svg)");
		}
		$("#starvalue_"+ids[1]).val(ids[2]);
	});
	

	

	
});

var isSending=false;
function saveTask(){
	var selfMark={};
	var markbox=null;
	if($("#taskDescription").find("ol").length<=0){
		markbox=$("#taskDescription");
	}
	else{
		markbox=$("#taskDescription").find("ol")[0];
	}
	var mark="";
	$(markbox).children().each(function(){
		var thistext=$(this).text();
		if(thistext.trim()!=''){
			mark=mark+"<li>"+thistext+"</li>";
		}
		
	});
	
	selfMark.taskDescription=mark;
	if(selfMark.taskDescription=='' || selfMark.taskDescription=='一天下来工作感受如何，请有效率的总结下吧，您可输入300字。'){
		alert("内容不能空");
		return;
	}
	var csms=[];
	var inputs=$("input[id^='starvalue_']");
	for(var i=0;i<inputs.length;i++){
		var csm={};
		var ids=$(inputs[i]).attr("id").split("_");
		csm.categoryId=ids[1];
		csm.starNum=$(inputs[i]).val();
		csms[i]=csm;
	}
	selfMark.csms=csms;
	var indicators=[];
	var checkboxs=$("input[id^='indicator_']:checked");
	for(var i=0;i<checkboxs.length;i++){
		var ids=$(checkboxs[i]).attr("id").split("_");
		indicators.push(ids[1]);
	}
	selfMark.indicators=indicators;
	var oncallback=function(){
		//myalert("保存成功");
		isSending=false;
		window.location.href="/wechat/selfmark-detail.html";
	}
	
	if(!isSending){
		isSending=true;
		$.ajax({
			url:'/rest/scored/selfmark/save.html',
			type:'POST',		
			data:JSON.stringify(selfMark),
			contentType:"application/json; charset=utf-8",
			success:oncallback,
			error:function(xhr){
			   alert(xhr.responseText);
			}
		});
	}
	
}
</script>

	<div class="wrap-page">
	  <c:if test="${empty disabled }">	  
		<section>
			<ul class="star-rating clearfix">
			    <c:forEach items="${categories }" var="category">
				<li class="clearfix">
					<span>${category.name }</span>
					<c:set var="starNum" value="0"></c:set>
					<c:choose>
    				    <c:when test="${not empty mark }">
    				        <c:forEach items="${mark.selfDailyMarks }" var="emark">
    				          <c:if test="${emark.markCategory.id eq category.id }">
    				            <c:set var="starNum" value="${emark.starNum }"/>
    				          </c:if>
    				       </c:forEach>    				         
    				    </c:when>    				    
    			    </c:choose>
    				<input type="hidden" id="starvalue_${category.id }" value="${starNum }">
					<ul class="stars clearfix">
						<c:choose>
    				       <c:when test="${starNum<1 }">
    				        <li id="li_${category.id }_1" tip="${category.starTip1 }"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${category.id }_1" tip="${category.starTip1 }" style="background-image:url(/wechat/images/icon-star-2.svg)"></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<2 }">
    				        <li id="li_${category.id }_2" tip="${category.starTip2 }"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${category.id }_2" tip="${category.starTip2 }" style="background-image:url(/wechat/images/icon-star-2.svg)"></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<3 }">
    				        <li id="li_${category.id }_3" tip="${category.starTip3 }"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${category.id }_3" tip="${category.starTip3 }" style="background-image:url(/wechat/images/icon-star-2.svg)"></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<4 }">
    				        <li id="li_${category.id }_4" tip="${category.starTip4 }"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${category.id }_4" tip="${category.starTip4 }" style="background-image:url(/wechat/images/icon-star-2.svg)"></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				      <c:choose>
    				       <c:when test="${starNum<5 }">
    				        <li id="li_${category.id }_5" tip="${category.starTip5 }"></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${category.id }_5" tip="${category.starTip5 }" style="background-image:url(/wechat/images/icon-star-2.svg)"></li>
    				       </c:otherwise>
    				     </c:choose>
					</ul>
					<br />
					<p id="tip_${category.id }" class="desc"></p>
					<c:if test="${starNum >0 }">
					  <script type="text/javascript">
					    $(document).ready(function(){
					    	$("#tip_${category.id }").html($("#li_${category.id }_${starNum}").attr("tip"));
					    });
					  </script>
					</c:if>
				</li>
				</c:forEach>
				
			</ul>
			<dl class="selfmark-star">
                    <dt>选择项目：</dt>
                   <c:forEach items="${indicators }" var="indicator" varStatus="status">
                         <c:set var="isExists" value="0"></c:set>
                         <c:if test="${not empty mark  }">
                           <c:forEach items="${mark.dailyIndicators }" var="uindicator">
                             <c:if test="${indicator.id eq uindicator.id }">
                               <c:set var="isExists" value="1"></c:set>
                             </c:if>
                           </c:forEach>  
                         </c:if>
                         
                         <dd>
                            <c:choose>
                              <c:when test="${isExists eq 1 }">
                                <input type="checkbox" id="indicator_${indicator.id }" class="checkbox-red checkbox-selfmark" checked="checked"><span class="listnum">KPI.${status.count }：</span><span class="kpidetail">${indicator.name }</span>
                              </c:when>
                              <c:otherwise>
                                <input type="checkbox" id="indicator_${indicator.id }" class="checkbox-red checkbox-selfmark" ><span class="listnum">KPI.${status.count }：</span><span class="kpidetail">${indicator.name }</span>
                              </c:otherwise>
                            </c:choose>
                            
                         </dd>
                        </c:forEach>                        
                </dl>
			<div id="taskDescription" class="dailyReport-wrap" contenteditable="true"><ol class="dailyReport-ol" ><c:choose><c:when test="${not empty mark }">${mark.taskDescription }</c:when><c:otherwise><li></li></c:otherwise></c:choose></ol></div>
			<button class="btn-ack" onclick="saveTask();">确认</button>
			<div class="blank20"></div>
		</section>
	  </c:if>
	  
	  <c:if test="${not empty disabled }">
	     暂未开放
	  </c:if>
		
	</div>
