<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
var lineopt=null;
var pieopt=null;
var defopt={
		
};
var scoreTypes={}
var chartdata={};
var colors=['#1bd0dc', '#eb6100', '#f9b700', '#009944', '#b804b6','#7dc400', '#0060ff','#6bee00'];
$(document).ready(function(){
	$("input[name='name']").click(function(){
		concateusername();
	});
	<c:forEach items="${scoreTypes }" var="scoreType">
	  scoreTypes["${scoreType }"]="${scoreType.toString() }";
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
            success : function(obj) {reqscore(obj);return true;},
        };

	
	var _datepicker=new pickerDateRange("date",dateObj);
	
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
        series: [{}]
	};
	
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
                showInLegend:true
            }
        },
        series: [{type:'pie'}]
	};
	
	reqscore();
	
	
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

function reqscore(dateobj){
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
		  url: '/chart/requestscore.html?startDate='+startDate+"&endDate="+endDate,		
		  data:doj,	  
		  dataType:'json',		  
		  async:false
	}).responseText;
	 
	chartdata=$.parseJSON(response);
	redrawchart(chartdata);
	rendertable(chartdata,1);
	if((typeof linechart !='undefined') && linechart!=null) linechart.hideLoading();
	if((typeof piechart !='undefined') && piechart!=null)piechart.hideLoading();
}

function redrawchart(obj){
	var scoreType=$("input[name='scoreType']:checked").val();
	
	var lineItem=null;
	var pieItem=null;
	for(var i=0;i<obj.lineItems.length;i++){
		
		if(obj.lineItems[i].scoreType == scoreType){			
			lineItem=obj.lineItems[i];
			break;
		}
	}
    for(var i=0;i<obj.pieItems.length;i++){
    	if(obj.pieItems[i].scoreType == scoreType){
			pieItem=obj.pieItems[i];
			break;
		}
	}
	
    if(lineItem!=null){
    	
    	lineopt.xAxis= {
    		tickmarkPlacement: 'on',
            categories: lineItem.categories
        };
    	lineopt.series[0].name=scoreTypes[lineItem.scoreType];
    	lineopt.series[0].data=lineItem.values;
    	$("#linechart").highcharts(lineopt);
    	
    }
    
    if(pieItem!=null){
    	pieopt.series[0].name=scoreTypes[pieItem.scoreType];
    	var pdata=[];
    	for(var i=0;i<pieItem.categories.length && i<7;i++){
    		if(pieItem.categories[i]==0)continue;
    		var idata=[];
    		idata[0]=pieItem.categories[i]+'分';
    		idata[1]=pieItem.values[i];
    		pdata.push(idata);
    	}
    	if(pieItem.categories.length > 7){
    		var idata=[];
    		idata[0]='其他';
    		var tcount=0;
    		for(var i=7;i<pieItem.categories.length;i++){
    			tcount=tcount+pieItem.values[i];
    		}
    		idata[1]=tcount;
    		pdata.push(idata);
    	}
    	pieopt.series[0].data=pdata;
    	$("#piechart").highcharts(pieopt);
    }
}

function rendertable(obj,page){
	var html="";
	var basecate="";
	if(obj.lineItems==null || obj.lineItems.length>0){
		basecate=obj.lineItems[0].categories;
	}
	else return;
	if(page==null){
		page=1;
	}
	var range1=(page-1)*30;
	var range2=page*30;
	for(var i=range1;i<basecate.length && i<range2;i++){
		var cate=basecate[i];
		var tr="<tr>";
		tr=tr+"<td>"+(i+1)+"</td>"+"<td>"+cate+"</td>";
		
		
		for(var j=0;j<obj.lineItems.length;j++){
			var categories=obj.lineItems[j].categories;
			var isexist=0;
			for(var k=0;k<categories.length;k++){
				if(categories[k] == cate){
					tr=tr+"<td>"+obj.lineItems[j].values[k]+"</td>";
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
	
	
	for(var j=0;j<obj.lineItems.length;j++){
		var categories=obj.lineItems[j].categories;
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
			
			if(isexist==1) total=total+obj.lineItems[j].values[k];									
		}
		footer=footer+"<td>"+total+"</td>";
	}
		
		
	
	
	$("#tfooter_score").html(footer);
	
	//set page
	var totalpage=(basecate.length-1)/30+1;
	var phtml="";
	if(page>1){
		phtml=phtml+'<li><a href="javascript:rendertable(chartdata,'+(page-1)+')">&lt; &nbsp; &nbsp; &nbsp; 上一页</a></li>';
	}
	else{
		phtml=phtml+'<li></li>';
	}
	for(var i=1;i<=totalpage;i++){
		if(i==page){
			phtml=phtml+'<li><a class="thispage">'+i+'</a></li>';
		}
		else{
			phtml=phtml+'<li><a href="javascript:rendertable(chartdata,'+i+')">'+i+'</a></li>';
		}		
	}
	
	if((page+1)<=totalpage){
		phtml=phtml+'<li class="last-child"><a href="javascript:rendertable(chartdata,'+(page+1)+')">下一页 &nbsp; &nbsp; &nbsp; &gt;</a></li>';
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
	if(chartdata==null || chartdata.lineItems==null){
		return;
	}
	var obj=chartdata;
	var CSV = '';    
	var basecate="";
	if(obj.lineItems!=null && obj.lineItems.length>0){
		basecate=obj.lineItems[0].categories;
	}
	var head='"序号","时间"';
	<c:forEach items="${scoreTypes }" var="scoreType">
	  head +=',"'+'${scoreType.toString()}'+'"';
	</c:forEach>
	
	CSV +=head + escape('\r\n');
	for(var i=0;i<basecate.length;i++){
		var cate=basecate[i];
		var row='"'+(i+1)+'","'+cate+'"';
		
		for(var j=0;j<obj.lineItems.length;j++){
			var categories=obj.lineItems[j].categories;
			var isexist=0;
			for(var k=0;k<categories.length;k++){
				if(categories[k] == cate){
					row +=',"' +obj.lineItems[j].values[k]+'"';
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
	 
	 var fileName = "绩效统计";
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
<h2><a>统计中心</a> 〉功勋统计</h2>

        <ul class="myub-nav clearfix">
            <li ><a class="thistab">功勋统计</a></li>
            <li><a href="/chart/statis/useractivity.html">活跃度统计</a></li>
            <li><a href="/chart/statis/userindicator.html">KPI进度条统计</a></li>
            <li><a href="/chart/statis/boardtask.html">公事榜统计</a></li>
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
                    <button class="confirm" onclick="$('#userType').css('display','none');reqscore();">确定</button>
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
           
           
            <a class="fr chart-select" onclick="javascript:document.getElementById('chart-screening').style.display='block';">条件选择<span class="arrow-down"></span></a>
            <div class="chart-screening" id="chart-screening">
                <dl>
                    <dt>指标类型：</dt>
                    <dd>
                       <c:forEach items="${scoreTypes }" var="scoreType" varStatus="status">
                         <label><input type="radio" name="scoreType" value="${scoreType}" <c:if test="${status.count ==1 }">checked="checked"</c:if> ><span>${scoreType.toString() }</span></label>                       
                       </c:forEach>                 
                       
                    </dd>
                    <!-- 
                    <dt>展现粒度：</dt>
                    <dd>
                        <label><input type="radio" name="chartTime"><span>按天</span></label>
                        <label><input type="radio" name="chartTime"><span>按周</span></label>
                        <label><input type="radio" name="chartTime"><span>按月</span></label>
                    </dd>
                     -->
                </dl>
                <div class="chart-screening-btm">
                    <button class="confirm" onclick="$('#chart-screening').css('display','none');if(chartdata!=null) redrawchart(chartdata);">确定</button>
                </div>
                <a class="close" onclick="javascript:document.getElementById('chart-screening').style.display='none';"></a>
            </div>
            <a href="javascript:exportcsv();" class="fr">导出数据</a>
        </div>
        
        <div class="chart-wrap" id="chart-1" style="display:block;">
            <div class="chart-box">
                <div class="fl" id="linechart" style="min-width: 624px; height: 269px; margin: 0 auto"></div>
               
                <div class="fr w30" id="piechart" style="min-width: 294px; height: 269px; margin: 0 auto"></div>
                
            </div>
            <div class="more-chart">
                <table class="chart-detail">
                    <thead>
                        <tr>
                            <th>&nbsp;</th>
                            <th>时间</th>
                            <c:forEach items="${scoreTypes }" var="scoreType">
                              <th class="hover">${scoreType.toString() }</th>
                            </c:forEach>                           
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