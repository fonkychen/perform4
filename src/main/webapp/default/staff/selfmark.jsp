<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="/js/json2.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("li[id^='li_']").hover(function(){
		
		var ids=$(this).attr("id").split("_");
		for(var i=1;i<=ids[2];i++){			
			$("#li_"+ids[1]+"_"+i).attr("class","checked");	
		}
		
		for(var i=(parseInt(ids[2])+1);i<=5;i++){
			
			$("#li_"+ids[1]+"_"+i).removeAttr("class");
		}
		
		$("#tipshow").html('旁白：'+$(this).attr("tip"));
	},function(){
		var ids=$(this).attr("id").split("_");
		var starNum=$("#starvalue_"+ids[1]).val();
		for(var i=1;i<=5;i++){		
			if(i<=starNum){
				$("#li_"+ids[1]+"_"+i).attr("class","checked");	
			}
			else{
				$("#li_"+ids[1]+"_"+i).removeAttr("class");
			}
		}
		$("#tipshow").html("");
	});
	
	$("li[id^='li_']").click(function(){
		var ids=$(this).attr("id").split("_");
		for(var i=1;i<=ids[2];i++){			
			$("#li_"+ids[1]+"_"+i).attr("class","checked");		
		}
		for(var i=(parseInt(ids[2])+1);i<=5;i++){
			$("#li_"+ids[1]+"_"+i).removeAttr("class");
		}
		$("#starvalue_"+ids[1]).val(ids[2]);
	});
		
});

function saveselfmark(){
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
	
	$.ajax({
		url:'/rest/scored/selfmark/save.html',
		type:'POST',		
		data:JSON.stringify(selfMark),
		contentType:"application/json; charset=utf-8",
		success:function(){
		  // alert('保存成功');
		  window.location.href='/staff/selfmark/detail.html';
		},
		error:function(xhr){
		   alert(xhr.responseText);
		}
	});
}
</script>
 <div class="bg clearfix">
        <div class="w710 fl clearfix">
            <h2>功勋英雄 〉 每日自评</h2>
            <div class="w50p fl">
                <dl class="selfmark-star">
                    <dt>自我评分：</dt>
                     <c:forEach items="${categories }" var="category">
    				     <dd><span class="starName">${category.name }</span>
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
    				     <ul class="starUl">
    				     
    				     <c:choose>
    				       <c:when test="${starNum<1 }">
    				        <li id="li_${category.id }_1" tip="${category.starTip1 }" ><a></a></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${category.id }_1" tip="${category.starTip1 }" class="checked"><a></a></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<2 }">
    				        <li id="li_${category.id }_2" tip="${category.starTip2 }" ><a></a></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${category.id }_2" tip="${category.starTip2 }" class="checked"><a></a></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<3 }">
    				        <li id="li_${category.id }_3" tip="${category.starTip3 }"><a></a></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${category.id }_3" tip="${category.starTip3 }" class="checked" ><a></a></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				     <c:choose>
    				       <c:when test="${starNum<4 }">
    				        <li id="li_${category.id }_4" tip="${category.starTip4 }"><a></a></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${category.id }_4" tip="${category.starTip4 }" class="checked" ><a></a></li>
    				       </c:otherwise>
    				     </c:choose>
    				     
    				      <c:choose>
    				       <c:when test="${starNum<5 }">
    				        <li id="li_${category.id }_5" tip="${category.starTip5 }"><a></a></li>
    				       </c:when>
    				       <c:otherwise>
    				        <li id="li_${category.id }_5" tip="${category.starTip5 }" class="checked" ><a></a></li>
    				       </c:otherwise>
    				     </c:choose>
    				     </ul>
    				     </dd>
    				  </c:forEach>                 
                     <dd class="lastdd" id="tipshow"></dd>
                     
                </dl>
            </div>
            <div class="w50p fl">
                <form>
                    <div class="selfmark-star">
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
                     
                    </div>
                </form>
            </div>
            <div class="clearfix"></div>
            <div id=taskDescription class="selfmarkReport-wrap" contenteditable="true"><ol class="selfmarkReport-ol"><c:choose><c:when test="${not empty mark }">${mark.taskDescription }</c:when><c:otherwise><li></li></c:otherwise></c:choose></ol></div>
           
            <p class="wordscount"></p>
           
            <button class="btn-blue fr" onclick="saveselfmark();">确定</button>
        </div>
        <div class="lastweek fr">
           <c:if test="${not empty weeklycomment }">
            <h2 class="left0">最新点评：</h2>
            <div class="lastweek-score">${weeklycomment.scoreNum }</div>
            <p class="lastweek-time">日期：<fmt:formatDate value="${weeklycomment.updated }" pattern="yyyy-MM-dd"/> </p>
            <p class="lastweek-detail">${weeklycomment.taskDescription }</p>
            <button class="btn-blue" onclick="window.location.href='/staff/selfmark/detail.html'">功勋历史</button>
           </c:if>
            
        </div>
        <div class="blank100"></div>
    </div>
