<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
var lineopt=null;
var defopt={
		
};
var chartdata={};
var colors=['#1bd0dc', '#eb6100', '#f9b700', '#009944', '#b804b6','#7dc400', '#0060ff'];
$(document).ready(function(){
	$("input[name='name']").click(function(){
		concateusername();
	});

	
	
	lineopt={	
			chart:{
				type: 'area',
				renderTo:'linechart'
			},
			title: {
	            text: '',           
	        },     
	        colors: colors,
	        credits: {
	            enabled: false
	        },
	      
	        yAxis: {
	            title: {
	                text: ''
	            }
	        },
	        tooltip: {
	            valueSuffix: ''
	        },
	        plotOptions: {
	            series: {
	                //fillColor: null
	                fillOpacity: 0.15
	            }
	        },
	        legend: {
	        	align: 'center'
	        },
	        series: [{},{}]
		};
	
	
	reqindicator('${user.id}');
	
	
});

function concateusername(){
	
	var inputs=$("input[name='name']:checked");
	var inputs2=$("input[name='name']");
	if(inputs.length == inputs2.length){
		$("#userType_text").html("全部");
		return;
	}
	var cname="&nbsp;";
	if(inputs.length>0){
		cname=$(inputs[0]).attr("uname");
	}
	if(inputs.length>1){
		for(var i=1;i<inputs.length;i++){
			cname=cname+","+$(inputs[i]).attr("uname");
		}
	}
	$("#userType_text").html(cname);
}

function reqindicator(userid){
	var linechart=$("#linechart").highcharts();
	if((typeof linechart !='undefined') && linechart!=null)linechart.showLoading();
	
	var response=$.ajax({
		  type: "POST",
		  contentType: "application/json; charset=utf-8",
		  url: '/chart/requestindicator.html?userId='+userid,		
		 
		  dataType:'json',		  
		  async:false
	}).responseText;
	 
	chartdata=$.parseJSON(response);
	drawchart(chartdata);
	rendertable()
	//if((typeof linechart !='undefined') && linechart!=null) linechart.hideLoading();
	if((typeof linechart !='undefined') && linechart!=null)linechart.hideLoading();
}

function drawchart(obj){
	
	 if(obj!=null){
	    	
	    	lineopt.xAxis= {
	    		tickmarkPlacement: 'on',
	            categories: obj.categories
	        };
	    	lineopt.series[0].name="主管评定";
	    	lineopt.series[0].data=obj.managerItems;
	    	lineopt.series[1].name="个人评定";
	    	lineopt.series[1].data=obj.selfItems;
	    	$("#linechart").highcharts(lineopt);
	    	
	 }
}


function rendertable(){
	var html="";
	var cates=chartdata.categories;
	
	for(var i=0;i<cates.length;i++){
		var cate=cates[i];
		var tr="<tr>";
		tr=tr+"<td>"+(i+1)+"</td>"+"<td>"+cate+"</td>";
		
		tr=tr+"<td>"+chartdata.managerItems[i]+"</td>";
		tr=tr+"<td>"+chartdata.selfItems[i]+"</td>";
		tr=tr+"<td>"+chartdata.settledItems[i]+"</td>";
		
		tr=tr+"</tr>";		
		html=html+tr;
	}
	$("#tbody_score").html(html);
	
}
<!--

//-->

function onuselected(){
	$('#userType').css('display','none');
	reqindicator($("input[name='name']:checked").val());
}

function selectall(){
	$("input[name='name']").attr("checked","checked");
	
}

function selectcurrent(){
	//$("input[name='name']")attr("checked","checked");
	var uls=$("ul[class='name']");
	
	for(var i=0;i<uls.length;i++){
		
		if($(uls[i]).css('display')=='block'){			
			$(uls[i]).find(":input").attr('checked','checked');
		}		
	}
	
}

function cancelall(){
	$("input[name='name']").removeAttr("checked");
}

function exportcsv(){

	var obj=chartdata;
	var CSV = '';    
	
	var head='"序号","时间","主管评定","个人评定","合计评定"';
	
	
		
	CSV +=head + escape('\r\n');
	
	
	
	for(var i=0;i<obj.categories.length;i++){
		var cate=obj.categories[i];
		var row='"'+(i+1)+'","'+cate+'"';
		row+=',"'+obj.managerItems[i]+'","'+obj.selfItems[i]+'","'+obj.settledItems[i]+'"';
		row.slice(0,row.length -1);
		CSV += row + escape('\r\n');
	}
		
		

	 if (CSV == '') {        
	      alert("Invalid data");
	      return;
	 }
	 
	 var fileName = "KPI指标统计";
	 if (navigator.appName == "Microsoft Internet Explorer") {    
		
		 CSV =  unescape(CSV);
		 var oWin = window.open("text/html", "replace");
		 oWin.document.write(CSV);
		 oWin.document.close();
		 oWin.document.execCommand('SaveAs', true, fileName + ".csv");
		 oWin.close();
	 }
	 else{
		  var uri = 'data:text/csv;charset=utf-8,\ufeff' + CSV;
		  var link = document.createElement("a");    
		  link.href = uri;
			    
		  link.style = "visibility:hidden";
		  link.download = fileName + ".csv";
			    
		  document.body.appendChild(link);
		  link.click();
		  document.body.removeChild(link);			 
	 }
   
}

</script>

 <div class="bg clearfix">
<h2><a>统计中心</a> 〉 KPI进度条统计</h2>

        <ul class="myub-nav clearfix">
            <li><a href="/chart/statis/userscore.html">功勋统计</a></li>
            <li><a href="/chart/statis/useractivity.html">活跃度统计</a></li>
            <li><a class="thistab">KPI进度条统计</a></li>
            <li><a href="/chart/statis/boardtask.html">公事榜统计</a></li>
        </ul>
        <div class="select-bar">
            <a class="fl chart-select" onclick="if($('#userType').css('display')=='block') $('#userType').css('display','none');else $('#userType').css('display','block');"><i id="userType_text">${user.name }</i><span class="arrow-down"></span></a>
            <div class="userType" id="userType">
                <div>
                    <ul class="country">
                       <c:forEach var="country" items="${countries }" varStatus="status">
                         <li style="width: ${100/(fn:length(countries)+1)}%;" <c:if test="${status.count == 1 }">class="thispage"</c:if> ><a>${country.name }</a></li>
                       </c:forEach>    
                         <li style="width: ${100/(fn:length(countries)+1)}%;"><a>无国籍</a></li>                           
                    </ul>
                </div>
                <div>
                   <c:forEach var="country" items="${countries }" varStatus="status">
                       <ul class="name" <c:if test="${status.count ==1 }">style="display: block;"</c:if> >
                        <c:forEach items="${country.users }" var="cuser">
                          <li><label><input type="radio" name="name" value="${cuser.id }" uname="${cuser.name }" <c:if test="${user.id eq cuser.id }">checked="checked"</c:if> >                             
                          <span>${cuser.name }</span></label></li>
                        </c:forEach>                      
                       </ul>
                   </c:forEach>
                      <ul class="name">
                         <c:forEach items="${nocountryusers }" var="cuser">
                           <li><label><input type="radio" name="name" value="${cuser.id }" uname="${cuser.name }" <c:if test="${user.id eq cuser.id }">checked="checked"</c:if>>                             
                          <span>${cuser.name }</span></label></li>
                         </c:forEach>
                       </ul>
                   
                </div>
                <div class="userType-btm">
                    <a onclick="selectall();$('#userType_text').html('全部');">所有用户</a>
                    <a onclick="selectcurrent();concateusername();">当前全选</a>
                    <a onclick="cancelall();$('#userType_text').html('&nbsp;');">全部取消</a>
                    <button class="confirm" onclick="onuselected();">确定</button>
                </div>
            </div>
           
            
              
           
           
           
            <a href="javascript:exportcsv();" class="fr">导出数据</a>
        </div>
        
        <div class="chart-wrap" id="chart-3" style="display:block;">
           
            <div class="chart-box">
                <div id="linechart" style="min-width: 958px; height: 268px; margin: 0 auto"></div>               
            </div>
            
            
            <div class="clearfix"></div>
            
           
            <div class="more-chart">
                <table class="chart-detail">
                    <thead id="thead_score">
                     
                        <tr>
                            <th>&nbsp;</th>
                            <th>时间</th>                           
                            <th class="hover">主管评定</th>
                            <th class="hover">个人评定</th>
                            <th class="hover">合计评定</th>                                                      
                        </tr>
                      
                    </thead>
                    <tbody id="tbody_score">
                       
                    </tbody>
                    <tfoot id="tfooter_score">
                       
                    </tfoot>
                </table>
               
                <ul id="page_score" class="page clearfix">
                    
                 
                </ul>
            </div>
            <div class="clearfix"></div>
        </div>
   </div>     