$(document).ready(function(){
	
    
 var alert_on=false;
 var confirm_on=false;
  //重写alert函数
 window.myalert = function(text){
     
      if(text=='' || alert_on ){
          return;
      }
    
    
      else{
    	 
          if($("#alert_message_dialog").length<=0){
        	  var html='<div id="alert_message_dialog" class="wrap_Alert">';
              html=html+'	<div class="Alert">';
        	  html=html+'	<a href="javascript:closeAlert();" class="AlertClose"></a>';
        	  html=html+'	<table>';
        	  html=html+'	<tr>';
        	  html=html+'		<td width="30%" height="116" align="center" valign="bottom"><img src="/default/images/alert_img01.png"/></td>';
        	  html=html+'		<td><p id="alert_message_content">'+text+'</p></td>';
        	  html=html+'		</tr>';
        	  html=html+'	<tr><td colspan="2">';
        	  html=html+'				<div class="btns">';
        	  html=html+'				<button class="btn_04" onclick="closeAlert();">确定</button>';
              //html=html+'			<button class="btn_04">取消</button>'
        	  html=html+'			</div></td></tr></table></div></div>';
        	  
        	  $(document.body).append(html);
        	  alert_on=true;
        	  $("#alert_message_dialog").css("display","block");
            };
                 
            window.closeAlert = function(){            	
               	$("#alert_message_dialog").remove();     
                alert_on=false;
            }
                 
          }         
      };
      
   
      
      window.myconfirm=function(text,funok,funcancel){
    	  if(text==''  || confirm_on){
              return;
          }
        
        
          else{
        	 
              if($("#confirm_message_dialog").length<=0){
            	  var html='<div id="confirm_message_dialog" class="wrap_Alert">';
                  html=html+'	<div class="Alert">';
            	  html=html+'	<a href="javascript:closeConfirm(\'false\');" class="AlertClose"></a>';
            	  html=html+'	<table>';
            	  html=html+'	<tr>';
            	  html=html+'		<td width="30%" height="116" align="center" valign="bottom"><img src="/default/images/alert_img01.png"/></td>';
            	  html=html+'		<td><p id="alert_message_content">'+text+'</p></td>';
            	  html=html+'		</tr>';
            	  html=html+'	<tr><td colspan="2">';
            	  html=html+'				<div class="btns">';
            	  html=html+'				<button class="btn_04" onclick=" closeConfirm(\'true\');">确定</button>';
                  html=html+'			<button class="btn_04" onclick="closeConfirm(\'false\');">取消</button>';
            	  html=html+'			</div></td></tr></table></div></div>';
            	  
            	  $(document.body).append(html);
            	  confirm_on=true;
            	  $("#confirm_message_dialog").css("display","block");
                };
                     
                window.closeConfirm= function(flag){            	
                   	$("#confirm_message_dialog").remove();     
                   	confirm_on=false;
                   	if(flag=='true'){
                   		if(funok!=null)funok();
                   	}
                   	else {
                   		if(funcancel!=null)funcancel();
                   	}
                }
                     
              }         
    }
      
      window.showTips=function(text){
    	  if($("#tips_message_dialog").length<=0){
        	  var html='';
              html=html+'	<div id="tips_message_dialog" class="Alert">';
        	  html=html+'	<a href="javascript:closeTips();" class="AlertClose"></a>';
        	  html=html+'	<table>';
        	  html=html+'	<tr>';
        	  html=html+'		<td width="30%" height="116" align="center" valign="bottom"><img src="/default/images/alert_img01.png"/></td>';
        	  html=html+'		<td><p id="alert_message_content">'+text+'</p></td>';
        	  html=html+'		</tr>';
        	  html=html+'	<tr><td colspan="2">';
        	  html=html+'				<div class="btns">';
        	
        	  html=html+'			</div></td></tr></table></div>';
        	  
        	  $(document.body).append(html);      
        	  var top=$(document).scrollTop();
  			  var height=$("#tips_message_dialog").height();
  			  var wheight=$(window).height();
  			  var dtop=top+((wheight-height)/2);
			  if(dtop<0)dtop=0;
			  $("#tips_message_dialog").css("top",dtop+"px");
			  $("#tips_message_dialog").css("display","block");
        	  $( window ).scroll(function() {
        			top=$(document).scrollTop();
        			height=$("#tips_message_dialog").height();
        			wheight=$(window).height();
        			dtop=top+((wheight-height)/2);
        			if(dtop<0)dtop=0;
        			$("#tips_message_dialog").css("top",dtop+"px");
        		});
        	  window.setTimeout(function() {
        		  $("#tips_message_dialog").animate({opacity: 0}, 1000, 'swing',function(){
            	        $("#tips_message_dialog").remove();
                	  });
        	  },1000);
        	  
            };
            
            window.closeTips=function(){
            	$("#tips_message_dialog").remove();
            }
      }
      
    
      var messagelist={};
      var isloadingmessage=false;
      window.getMessages=function(){
    	 var oncallback=function(obj){
    		 if(obj.notifyList!=null){
    			 resetNotify(obj.notifyList);
    		 }
    		 messagelist=obj.messageList;
    		 if(messagelist!=null && messagelist.length>0){
    			 $('#message_popup').remove();
    			// showMessage(0);
    		 }
    		 
    		 window.setTimeout(function() {
    			 getMessages();
    		 },1*60*1000);
    		 
    		 isloadingmessage=false;
    	 }
    	 if(!isloadingmessage){
    		 isloadingmessage=true;
    		 $.ajax({
    			 url:'/rest/user/messages.html',
    			 type:'GET',
    			 dataType:'json',
    			
    		 }).done(function(data){
    			 oncallback(data);
    		 }).fail(function(){
    			 
    		 }).always(function(){
    			 isloadingmessage=false;
    		 });
    		// PersonalInterface.getMessageAndNotify({callback:oncallback,errorHandler:function(){}}); 
    	 }
    	 
      }
      
     window.resetNotify=function(notifylist){
    	 var totalnew=0;
    	 for(var i=0;i<notifylist.length;i++){
    		var nf=notifylist[i];
    		if(nf.count>0){
    			totalnew=totalnew+nf.count;
    			$("#a_mesg_"+nf.notifyType).attr("class","newMesg");
       	        $("#span_mesg_"+nf.notifyType).html('('+nf.count+')');
    		}    		
    	 }
    	 
    	 if(totalnew>0){
    		 //$("#newMesg_circle").css("display","block");
    		 $("#span_notify_totalnew").html('（'+totalnew+'）');
    	 }
    	 else{
    		 $("#span_notify_totalnew").html('');
    	 }
     }
      
      window.setMessageIsRead=function(mid){
    	  var oncallback=function(){};
    	//  PersonalInterface.setMessageIsRead(parseInt(mid),{callback:oncallback});
      }
      
      window.showMessage=function(index){
    	 
    	  var message="";
    	  if(messagelist.length>parseInt(index)){
    		  message=messagelist[parseInt(index)];
    	  }
    	  else return;
    	  var html='';
    	  
    	 // html=html+'  <div class="firework"> <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="400" height="400" id="FlashID"> <param name="movie" value="/default/flash/fireworks.swf" /> <param name="quality" value="high" />  <param name="wmode" value="transparent" /> <param name="swfversion" value="6.0.65.0" /> <param name="expressinstall" value="Scripts/expressInstall.swf" /> <!--[if !IE]>--> <object type="application/x-shockwave-flash" data="/default/flash/fireworks.swf" width="400" height="400">         <!--<![endif]-->  ';    
    	 // html=html+'<param name="quality" value="high" />   <param name="wmode" value="transparent" />  <param name="swfversion" value="6.0.65.0" />      <param name="expressinstall" value="Scripts/expressInstall.swf" />   <div><h4>此页面上的内容需要较新版本的 Adobe Flash Player。</h4> <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="获取 Adobe Flash Player" width="112" height="33" /></a></p> </div>   <!--[if !IE]>-->      </object>  <!--<![endif]--> </object>';
    	 // html=html+'  </div>';
    	  var autodispear=false;
    	  if(message.messageType == 'AchieveEventType'){
    		  html=html+'<div id="message_reel" class="reel">';
    		  if(message.content!=null){
    			  var mcontents=message.content.split("|");
        		  html=html+'<div class="achievement">'+mcontents[0]+'</div>';
        		  html=html+'<div class="achieveNote clearfix"><span class="left-ac">'+(mcontents.length>1?mcontents[1]:'')+'</span><span class="right-ac">'+(mcontents.length>2?mcontents[2]:'')+'</span></div>';
        		  html=html+'<div id="message_stamp" class="stamp" ><img src="/default/images/stamp.png"></div>';  
    		  }    		 
    		  html=html+'</div>';
    		  autodispear=true;
    	  }
    	  else {
    		  html=html+'<div id="message_popup" class="wrap_Alert" style="display: block;">';  
    		  html=html+'<div class="infoshow">';
        	  html=html+'<p>'+message.content+'</p>';
              html=html+'   <button class="btn_04" onclick="setMessageIsRead(\''+message.id+'\');$(\'#message_popup\').remove();showMessage(\''+(parseInt(index)+1)+'\');">确定</button>'
              html=html+'	</div></div>';
    	  }
    	 
    	 
          
          
          if(autodispear){
        	  window.setTimeout(function(){
        		  $(document.body).append(html);
        		  window.setTimeout(function() {      
             		 
            		  $("#message_stamp").css("width","18px");
            		  $("#message_stamp").css("height","15px");
            		  $("#message_stamp").css("right","40px");
            		  $("#message_stamp").css("top","27px"); 
            		  $("#message_stamp").css("opacity",1);
            		  $("#message_stamp").animate({opacity: 1,width:"66px",height:"54px",right:'15px',top:'8px'}, 500, 'swing',function(){
            			 
                  	  });
            		  $("#message_stamp").animate({width:"54px",height:"44px",right:'22px',top:'12px'}, 250, 'swing',function(){
            			  
            		  });
            		  $("#message_stamp").animate({width:"60px",height:"49px",right:'18px',top:'10px'}, 250, 'swing',function(){
                		 // $("#message_reel").remove();
                		  
                  	  });
            		  window.setTimeout(function(){
            			  $("#message_reel").animate({opacity:0}, 500, 'swing',function(){
            				  $("#message_reel").remove();
            				  setMessageIsRead(message.id);
            				  showMessage(parseInt(index)+1);
            			  });
            		  },2500);
            		  
            		 // showMessage(parseInt(index)+1);
            	  },200);
        	  },2000);
        	  
        	  
          }
          
          else{
        	  $(document.body).append(html);
          }
         
      }
      
     
      window.NumberUtils = {
    			/**
    			 * 数字转中文
    			 * 
    			 * @number {Integer} 形如123的数字
    			 * @return {String} 返回转换成的形如 一百二十三 的字符串
    			 */
    			numberToChinese : function(number) {
    				/*
    				 * 单位
    				 */
    				var units = '个十百千万@#%亿^&~';
    				/*
    				 * 字符
    				 */
    				var chars = '零一二三四五六七八九';
    				var a = (number + '').split(''), s = [];
    				if (a.length > 12) {
    					throw new Error('too big');
    				} else {
    					for ( var i = 0, j = a.length - 1; i <= j; i++) {
    						if (j == 1 || j == 5 || j == 9) {// 两位数 处理特殊的 1*
    							if (i == 0) {
    								if (a[i] != '1')
    									s.push(chars.charAt(a[i]));
    							} else {
    								s.push(chars.charAt(a[i]));
    							}
    						} else {
    							s.push(chars.charAt(a[i]));
    						}
    						if (i != j) {
    							s.push(units.charAt(j - i));
    						}
    					}
    				}
    				// return s;
    				return s.join('').replace(/零([十百千万亿@#%^&~])/g, function(m, d, b) {// 优先处理 零百 零千 等
    					b = units.indexOf(d);
    					if (b != -1) {
    						if (d == '亿')
    							return d;
    						if (d == '万')
    							return d;
    						if (a[j - b] == '0')
    							return '零'
    					}
    					return '';
    				}).replace(/零+/g, '零').replace(/零([万亿])/g, function(m, b) {// 零百 零千处理后 可能出现 零零相连的 再处理结尾为零的
    					return b;
    				}).replace(/亿[万千百]/g, '亿').replace(/[零]$/, '').replace(/[@#%^&~]/g, function(m) {
    					return {
    						'@' : '十',
    						'#' : '百',
    						'%' : '千',
    						'^' : '十',
    						'&' : '百',
    						'~' : '千'
    					}[m];
    				}).replace(/([亿万])([一-九])/g, function(m, d, b, c) {
    					c = units.indexOf(d);
    					if (c != -1) {
    						if (a[j - c] == '0')
    							return d + '零' + b
    					}
    					return m;
    				});
    			}
    		};
      
      /**
       * time format
       */
   
      window.timeformat=function(date){
    	  var year = date.getFullYear();
    	  var month = (1 + date.getMonth()).toString();
    	  month = month.length > 1 ? month : '0' + month;
    	  var day = date.getDate().toString();
    	  day = day.length > 1 ? day : '0' + day;
    	  var hour=(date.getHours()).toString();
    	  hour=hour.length>1 ? hour:'0'+hour;
    	  var min=(date.getMinutes()).toString();
    	  min=min.length>1?min:'0'+min;
    	  return year + '-' + month + '-' + day+" "+hour+":"+min;
      }
});

$(window).load(function() {
	getMessages();
});