<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="/js/usergroup.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	addUserGroup("div_userchoose",'${handleuser.id}','/staff/comment.html?userId=');
	$("#scoreNum").keyup(function(){
		this.value=this.value.replace(/\D/g,'');
		var val=parseInt(this.value);
		if(val>100){
			this.value=this.value.substring(0,(this.value.length-1));
		}
	});
});
function savecomment(){
	var scoreNum=$("#scoreNum").val();
	var taskDescription=$("#taskDescription").val();
	if(scoreNum == '' || taskDescription == ''){
		alert('点评分数和内容不能为空');
		return;
	}
	$.ajax({
		url:'/rest/scored/comment/save.html',
		type:'POST',		
		data:'userId=${handleuser.id}&scored='+scoreNum+"&description="+taskDescription,
		
		success:function(){
		  // alert('保存成功');
		  window.location.reload();
		},
		error:function(xhr){
		   alert(xhr.responseText);
		}
	});
}
</script>
<div class="bg clearfix">
    	<h2>功勋英雄 〉 主管点评</h2>
    	<div id="div_userchoose">
    	 
    	</div>
        
        <div class="keepcenter-wrap">
            <div class="keepcenter pagingdate">
                <span class="blod">查询历史记录</span>
                <a href="/staff/comment.html?yearNum=${preyear }&weekOfYear=${preweekofyear}&userId=${handleuser.id}" class="arrow blue"></a><!--不可点击时class里去掉blue-->
                <div class="date">${weekstring }</div>
                <c:choose>
                  <c:when test="${not empty hasNext }">
                   <a href="/staff/comment.html?yearNum=${nextyear }&weekOfYear=${nextweekofyear}&userId=${handleuser.id}" class="arrow right blue rightblue"></a><!--不可点击时class里去掉blue和rightblue-->
                  </c:when>
                  <c:otherwise>
                  <a class="arrow right"></a><!--不可点击时class里去掉blue和rightblue-->
                  </c:otherwise>
                </c:choose>
                
                <span class="blod"><a href="/staff/comment.html?userId=${handleuser.id }">回本周</a></span>
            </div>
        </div>
        <div class="clearfix"></div>
        <div class="clearfix">
            <input id="scoreNum" type="text" <c:if test="${empty isEnabled }">readOnly="readOnly"</c:if>  <c:if test="${not empty comment }"> value="${comment.scoreNum }"</c:if> class="managermark-input">
            <textarea class="managermark-text"  id="taskDescription" <c:if test="${empty isEnabled }">readOnly="readOnly"</c:if>
            onfocus="if(this.value=='请对本位员工的一周工作表现进行点评，您可输入300字。') {this.value='';}this.style.color='#456';"
            onblur="if(this.value=='') {this.value='请对本位员工的一周工作表现进行点评，您可输入300字。';this.style.color='#bdc3c7';}"
            ><c:choose><c:when test="${not empty comment }">${comment.taskDescription }</c:when><c:otherwise>请对本位员工的一周工作表现进行点评，您可输入300字。</c:otherwise></c:choose></textarea>
        </div>
        <div class="blank20"></div>
        <div class="keepcenter-wrap">
            <div class="keepcenter"><button class="btn-blue" onclick="savecomment();">确定</button></div>
        </div>
        <div class="blank20"></div>
        
        <table class="kpidetail">
            <thead>
                <tr>
                    <th width="14%">日 期</th>
                    <th width="14%">自评分</th>
                    <th width="58%">工作报告</th>
                    <th width="14%">星评</th>
                </tr>
            </thead>
            <tbody>
              <c:forEach items="${selfmarks }" var="selfmark">
               <tr>
                    <td><fmt:formatDate value="${selfmark.updated }" pattern="yyyy-MM-dd"/><br /><fmt:formatDate value="${selfmark.updated }" pattern="E"/></td>
                    <td><b>${selfmark.actualScored }</b></td>
                    <td>
                        <p>
                         <c:forEach items="${selfmark.dailyIndicators }" var="indicator" varStatus="status">
                          KPI.${status.count } : ${indicator.name }&nbsp;
                         </c:forEach>                         
                        </p>
                        <ol>
                           ${selfmark.taskDescription }
                        </ol>
                    </td>
                    <td>
                    <c:forEach items="${selfmark.selfDailyMarks }" var="dailymark">
                     <p><span>${dailymark.markCategory.name }：</span>${dailymark.starNum } 星</p>
                    </c:forEach>
                   
                    </td>
                </tr>
              </c:forEach>
                
               
            </tbody>
        </table>
    	<div class="blank50"></div>
        <div class="keepcenter-wrap">
            <div class="keepcenter"><button class="btn-blue" onclick="window.location.href='/chart/statis/userscore.html';">查看统计</button></div>
        </div>
        <div class="blank100"></div><div class="blank100"></div>
    </div>
