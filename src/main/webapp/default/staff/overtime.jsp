<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
var page=1;
function loadOvertime(){
	var oncallback=function(datas){
		var size=$("#overtime_list").children().length;
		var html='';		
		for(var i=0;i<datas.length;i++){
			var tr='<tr>';
		
			tr=tr+'<td>'+(size+i+1)+'</td>';
			tr=tr+'<td>'+datas[i].user.name+'</td>';
            tr=tr+'<td>'+datas[i].yearNum+'-'+datas[i].monthNum+'-'+datas[i].dayNum+'</td>';
            tr=tr+'<td>'+datas[i].dailyBoard.taskType.name+'</td>';
            tr=tr+'<td>'+datas[i].dailyBoard.task+'</td>';
            if(datas[i].status == 'NOT_PROCESSED'){
            	tr=tr+'<td><a onclick=\'setstatus("'+datas[i].id+'",0)\'>无效</a><a onclick=\'setstatus("'+datas[i].id+'",1)\'>确认</a></td>';
            }
            else if(datas[i].status == 'REJECTED'){
            	tr=tr+'<td><span>无效</span></td>';
            }
            else if(datas[i].status == 'CONFIRMED'){
            	tr=tr+'<td><span>已确认</span></td>';
            }
            else if(datas[i].status == 'TIMEOUT'){
            	tr=tr+'<td><span>已超时</span></td>';
            }
            else if(datas[i].status == 'ABANDON'){
            	tr=tr+'<td><span>放弃</span></td>';
            }
            

			tr=tr+'</tr>';
			html=html+tr;
		}
		
		$("#overtime_list").append(html);
	}
	
	$.ajax({
		url:'/rest/user/getWorkOvertime.html?page='+page,
		type:'GET',
		success:function(datas){
			oncallback(datas.content);
			page=datas.number+1+1;
		},
		error:function(xhr){
			alert(xhr.responseText);
		}
	}).done(function(){
		//page=page+1;
	});
}

function setstatus(id,status){
	
	$.ajax({
		url:'/rest/user/overtime/status.html?id='+id+"&enabled="+status,
		type:'GET',
		success:function(){
			window.location.reload();
		},
		error:function(xhr){
			alert(xhr.responseText);
		}
	});
}

$(document).ready(function(){
	loadOvertime();
});
</script>
 <div class="bg clearfix">
    	<h2>功勋英雄 〉 <a href="/staff/dailyboard.html">公事榜</a> 〉 加班审批</h2>
        <!--公事榜选择-->
        <div class="keepcenter-wrap">
            <div class="keepcenter pagingdate">
            </div>
        </div>
    	<table class="apply">
            <thead>
                <tr>
                    <th width="6%">&nbsp;</th>
                    <th width="8%">申请人</th>
                    <th width="22%">日期</th>
                    <th width="17%">项目</th>
                    <th width="27%">内容</th>
                    <th width="20%">加班审批</th>
                </tr>
            </thead>
            <tbody id="overtime_list">
             
            </tbody>
        </table>
        <div class="blank50"></div>
        <div class="keepcenter-wrap">
            <div class="keepcenter"><button class="btn-blue" onclick="loadOvertime();">加载更多</button></div>
        </div>
        
    	<div class="blank100"></div>
    </div>
