<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
var baropt=null;
var pieopt=null;
var defopt={
		
};
var typeGroups=[];
var chartdata={};
var selectedgroup=-1;
var colors=['#1bd0dc', '#eb6100', '#f9b700', '#009944', '#b804b6','#7dc400', '#0060ff'];
$(document).ready(function(){
	$("input[name='name']").click(function(){
		concateusername();
	});

	<c:forEach items="${typeGroups }" var="typeGroup">
	  var typeGroup${typeGroup.id}={};
	  typeGroup${typeGroup.id}.id=${typeGroup.id};
	  typeGroup${typeGroup.id}.name="${typeGroup.name}";
	  var taskTypes${typeGroup.id}=[];
	  <c:forEach items="${typeGroup.taskTypes}" var="taskType">
	    var taskType${taskType.id}={};
	    taskType${taskType.id}.id=${taskType.id};
	    taskType${taskType.id}.name="${taskType.name}";
	    taskType${taskType.id}.color="${taskType.color}";
	    taskTypes${typeGroup.id}.push(taskType${taskType.id});
	  </c:forEach>
	  typeGroup${typeGroup.id}.taskTypes=taskTypes${typeGroup.id};
	  typeGroups.push(typeGroup${typeGroup.id});
	</c:forEach>
	
	var dateObj = {
            inputId:'date',
            target:'datePicker',
            startDate: "${startDate}" || '',
            endDate: "${endDate}" || '',
                     
            defaultText : ''|| ' 至 ',
            singleCompare : '',
            isTodayValid: '0',
            validStartTime: '1304611200',
            theme : 'ta',
            success : function(obj) {reqboard(obj);return true;},
        };

	
	var _datepicker=new pickerDateRange("date",dateObj);
	
	
	
	pieopt={
		chart:{
			type: 'pie',
			renderTo:'piechart'
		},
		title: {
            text: '',           
        },     
        colors: colors,
        credits: {
            enabled: false
        },
        plotOptions: {
            pie: {
                depth: 45,
                innerSize: 100,
                dataLabels: {
                    enabled: false                   
                },
                showInLegend:true,
                allowPointSelect: true,
                cursor: 'pointer',
                point: {
                    events: {
                        click: function () {
                           redrawbar(this.id);
                           rendertable(this.id);
                        }
                    }
                }
            }
        },
        series: [{type:'pie'}]
	};
	
	baropt={ 
			   chart: {
		            type: 'bar'
		        },
		        title: {
		            text: ''
		        },
		        xAxis: {
		        	tickmarkPlacement: 'on',
		        },
		        yAxis: {
		        	title:{
		        		text: ' '
		        	},
		        	allowDecimals: false
		        },
		        tooltip: {
		           
		        },
		        colors:colors,
		        plotOptions: {
		            bar: {
		                dataLabels: {
		                    enabled: true
		                }
		            }
		        },
		        legend: {
		           enabled:false
		        },
		        credits: {
		            enabled: false
		        },
		        series: [{
		           
		        }]	
	};
	
	reqboard();
	
	
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

function reqboard(dateobj){
	var linechart=$("#linechart").highcharts();
	var piechart=$("#piechart").highcharts();
	if((typeof linechart !='undefined') && linechart!=null)linechart.showLoading();
	if((typeof piechart !='undefined') && piechart!=null)piechart.showLoading();
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	if(dateobj!=null){
		startDate=dateobj.startDate;
		endDate=dateobj.endDate;
	}
	
	var userIds=[];
	var inputs=$("input[name='name']");
	for(var i=0;i<inputs.length;i++){
		var checked=$(inputs[i]).is(':checked');
		if(checked) userIds.push($(inputs[i]).val());
	}
	if(userIds.length<=0)return;
	var doj=JSON.stringify(userIds);	
	
	var response=$.ajax({
		  type: "POST",
		  contentType: "application/json; charset=utf-8",
		  url: '/chart/requestboard.html?startDate='+startDate+"&endDate="+endDate,		
		  data:doj,	  
		  dataType:'json',		  
		  async:false
	}).responseText;
	 
	chartdata=$.parseJSON(response);
	redrawchart(chartdata);
	//rendertable(chartdata,1);
	//if((typeof linechart !='undefined') && linechart!=null) linechart.hideLoading();
	if((typeof piechart !='undefined') && piechart!=null)piechart.hideLoading();
}

function redrawchart(obj){
	
	var cates=[];
	var vals=[];
	var groups=[];
	for(var i=0;i<typeGroups.length;i++){
		cates.push(typeGroups[i].name);
		groups.push(typeGroups[i].id);
		var totalval=0;
		var taskTypes=typeGroups[i].taskTypes;
		for(var j=0;j<taskTypes.length;j++){
			var taskType=taskTypes[j];
			
			for(var k=0;k<obj.items.length;k++){
				var item=obj.items[k];
				if(item.taskType.id == taskType.id){
					totalval += item.totalCount;
					break;
				}
			}
			
		}
		vals.push(totalval);
	}
	
	pieopt.series[0].name='公事榜分布';
	var pdata=[];
	var selectid=-1;
	for(var i=0;i<cates.length;i++){		
		var idata={};
		idata.name=cates[i];
		idata.y=vals[i];
		idata.id=groups[i];
		if(selectid<0 && vals[i]>0){
			selectid=groups[i];
			idata.sliced=true;
			idata.selected=true;
		}
		pdata.push(idata);
	}
	
	pieopt.series[0].data=pdata;
	$("#piechart").highcharts(pieopt);
	
	if(selectid >=0){
		selectedgroup=selectid;
		redrawbar(selectid);
		rendertable(selectid);
	}
}

function redrawbar(groupid){
	var cates=[];
	var vals=[];
	var typeGroup='';
	var obj=chartdata;
	for(var i=0;i<typeGroups.length;i++){
		if(typeGroups[i].id == groupid){
			typeGroup=typeGroups[i].name;
			var taskTypes=typeGroups[i].taskTypes;
			for(var j=0;j<taskTypes.length ;j++){
				var taskType=taskTypes[j];
				cates.push(taskType.name);
				var totalval=0;
				for(var k=0;k<obj.items.length;k++){
					var item=obj.items[k];
					if(item.taskType.id == taskType.id){
						totalval += item.totalCount;
						break;
					}
				}
				
				var ditem={};
				ditem.y=totalval;
				ditem.color=taskType.color;
				
				vals.push(ditem);
			}
			
			break;
		}
	}
	
	baropt.xAxis.categories=cates;
	baropt.series[0].name=typeGroup;
	baropt.series[0].data=vals;
	$("#barchart").highcharts(baropt);
	
}

function rendertable(groupid,page){
	var html="";
	var cates=[];
	var obj=[];
	for(var i=0;i<typeGroups.length;i++){
		var typeGroup=typeGroups[i];
		if(typeGroup.id == groupid){
			var taskTypes=typeGroup.taskTypes;
			for(var j=0;j<taskTypes.length;j++){
				
				for(var k=0;k<chartdata.items.length;k++){
					if(chartdata.items[k].taskType.id == taskTypes[j].id){
						cates.push(taskTypes[j].name);
						obj.push(chartdata.items[k]);
						break;						
					}
				}
			}
			
			break;
		}
	}
	
	var head="<tr> <th>&nbsp;</th><th>时间</th>";
	for(var i=0;i<cates.length;i++){
		head+="<th>"+cates[i]+"</th>";
	}
	head+="</tr>";
	$("#thead_score").html(head);
	
	var basecate=[];
	var startDate=new Date(chartdata.startDate);
	var endDate=new Date(chartdata.endDate);
	
	while(!(startDate>endDate)){
		basecate.push((startDate.getMonth()+1)+"-"+startDate.getDate());
		
		startDate.setDate(startDate.getDate()+1);
	}
	
	if(page==null){
		page=1;
	}
	var range1=(page-1)*30;
	var range2=page*30;
	for(var i=range1;i<basecate.length && i<range2;i++){
		var cate=basecate[i];
		var tr="<tr>";
		tr=tr+"<td>"+(i+1)+"</td>"+"<td>"+cate+"</td>";
		
		
		for(var j=0;j<obj.length;j++){
			var categories=obj[j].categories;
			var isexist=0;
			for(var k=0;k<categories.length;k++){
				if(categories[k] == cate){
					tr=tr+"<td>"+obj[j].values[k]+"</td>";
					isexist=1;
					break;
				}
			}
			
			if(isexist==0){
				tr=tr+"<td></td>";
			}
		}
		
		
		
		tr=tr+"</tr>";		
		html=html+tr;
	}
	$("#tbody_score").html(html);
	
	var footer="<td></td><td>当页汇总</td>";
	
	
	for(var j=0;j<obj.length;j++){
		var categories=obj[j].categories;
		var total=0;
		for(var k=0;k<categories.length;k++){
			var isexist=0;
			for(var i=range1;i<basecate.length && i<range2;i++){
				var cate=basecate[i];
				if(categories[k] == cate){
					isexist=1;
					break;
				}
			}
			
			if(isexist==1) total=total+obj[j].values[k];									
		}
		footer=footer+"<td>"+total+"</td>";
	}
		
		
	
	
	$("#tfooter_score").html(footer);
	
	//set page
	var totalpage=(basecate.length-1)/30+1;
	var phtml="";
	if(page>1){
		phtml=phtml+'<li><a href="javascript:rendertable('+groupid+','+(page-1)+')">&lt; &nbsp; &nbsp; &nbsp; 上一页</a></li>';
	}
	else{
		phtml=phtml+'<li></li>';
	}
	for(var i=1;i<=totalpage;i++){
		if(i==page){
			phtml=phtml+'<li><a class="thispage">'+i+'</a></li>';
		}
		else{
			phtml=phtml+'<li><a href="javascript:rendertable('+groupid+','+i+')">'+i+'</a></li>';
		}		
	}
	if((page+1)<=totalpage){
		phtml=phtml+'<li class="last-child"><a href="javascript:rendertable('+groupid+','+(page+1)+')">下一页 &nbsp; &nbsp; &nbsp; &gt;</a></li>';
	}
	else{
		phtml=phtml+'<li></li>';
	}
	$("#page_score").html(phtml);
}
<!--

//-->


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
	
	var head='"序号","时间"';
	
	for(var i=0;i<obj.items.length;i++){
		var taskType=obj.items[i].taskType;
		head +=',"'+taskType.name+'"';
	}
		
	CSV +=head + escape('\r\n');
	
	var basecate=[];
	var startDate=new Date(chartdata.startDate);
	var endDate=new Date(chartdata.endDate);
	
	while(!(startDate>endDate)){
		basecate.push((startDate.getMonth()+1)+"-"+startDate.getDate());		
		startDate.setDate(startDate.getDate()+1);
	}
	
	for(var i=0;i<basecate.length;i++){
		var cate=basecate[i];
		var row='"'+(i+1)+'","'+cate+'"';
		
		for(var j=0;j<obj.items.length;j++){
			//var item=obj.items[j];
			var categories=obj.items[j].categories; //get item
			var isexist=0;
			
			for(var k=0;k<categories.length;k++){
				if(categories[k] == cate){
					row +=',"' +obj.items[j].values[k]+'"';
					isexist=1;
					break;
				}
			}
			if(isexist==0){
				row +='," "';
			}
			
		}
		
		row.slice(0,row.length -1);
		CSV += row + escape('\r\n');
	}
		
		

	 if (CSV == '') {        
	      alert("Invalid data");
	      return;
	 }
	 
	 var fileName = "公事榜统计";
	 if (navigator.appName == "Microsoft Internet Explorer") {    
		 var oWin = window.open();
		 oWin.document.write(unescape(CSV));
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
<h2><a>统计中心</a> 〉 公事榜统计</h2>

        <ul class="myub-nav clearfix">

            <li><a href="/chart/statis/userscore.html">功勋统计</a></li>
            <li><a href="/chart/statis/useractivity.html">活跃度统计</a></li>
            <li><a href="/chart/statis/userindicator.html">KPI进度条统计</a></li>
            <li><a class="thistab">公事榜统计</a></li>
        </ul>
        <div class="select-bar">
            <a class="fl chart-select" onclick="if($('#userType').css('display')=='block') $('#userType').css('display','none');else $('#userType').css('display','block');"><i id="userType_text">全部</i><span class="arrow-down"></span></a>
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
                          <li><label><input type="checkbox" name="name" checked="checked" value="${cuser.id }" uname="${cuser.name }">                             
                          <span>${cuser.name }</span></label></li>
                        </c:forEach>                      
                       </ul>
                   </c:forEach>
                      <ul class="name">
                         <c:forEach items="${nocountryusers }" var="cuser">
                           <li><label><input type="checkbox" name="name" checked="checked" value="${cuser.id }" uname="${cuser.name }">                             
                          <span>${cuser.name }</span></label></li>
                         </c:forEach>
                       </ul>
                   
                </div>
                <div class="userType-btm">
                    <a onclick="selectall();$('#userType_text').html('全部');">所有用户</a>
                    <a onclick="selectcurrent();concateusername();">当前全选</a>
                    <a onclick="cancelall();$('#userType_text').html('&nbsp;');">全部取消</a>
                    <button class="confirm" onclick="$('#userType').css('display','none');reqboard();">确定</button>
                </div>
            </div>
           
           <div class="fl">
            
                <div class="date" id="div_date">
                    <span class="date_title" id="date">2015-01-30 至 2015-02-28</span>
                    <a class="opt_sel arrow-down" id="input_trigger" href="#">
                        <i></i>
                    </a>
                </div>
                <div id="datePicker"></div>
               
                 
            </div>
            <a id="aRecent7Days" href="javascript:void(0);">7天</a>
            <a id="aRecent14Days" href="javascript:void(0);">14天</a>
            <a id="aRecent30Days" href="javascript:void(0);">30天</a>
           
           
           
            <a href="javascript:exportcsv();" class="fr">导出数据</a>
        </div>
        
        <div class="chart-wrap" id="chart-4" style="display:block;">
            <div class="chart-box">
                <div id="piechart" style="min-width: 314px; height: 261px; margin: 0 auto" class="fl w30"></div>
                <div id="barchart" style="min-width: 569px; height: 261px; margin: 0 auto" class="fr"></div>
            </div>
            
            <div class="clearfix"></div>
            
           
            <div class="more-chart">
                <table class="chart-detail">
                    <thead id="thead_score">
                    <!-- 
                        <tr>
                            <th>&nbsp;</th>
                            <th>时间</th>
                            <c:forEach items="${scoreTypes }" var="scoreType">
                              <th class="hover">${scoreType.toString() }</th>
                            </c:forEach>                           
                        </tr>
                       -->
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