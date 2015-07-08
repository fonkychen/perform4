var userpath=[];
function addUserGroup(div,selectedId,targetUrl){
	//checkpath
	
	var handleGroup=function(data){
		userpath=new Array();
		retrieveGroupUser(data,selectedId);		
	    var html='';
	    
		for(var i=0;i<userpath.length;i++){
			if(i==0 ){
				checkGroup(div,userpath[i],selectedId,targetUrl)
			}
			if(userpath[i].ownerGroup == null) {//only add 				
				continue;
			}
			var dl='<dl class="managermark-choose clearfix">';
			dl=dl+'<dt>'+userpath[i].name+'：</dt>';
			dl=dl+'<dd><ul class="clearfix">';
			for(var j=0;j<userpath[i].ownerGroup.users.length;j++){
				if(userpath[i].ownerGroup.users[j].id == selectedId){
					dl=dl+'<li class="choosed"><a href="'+targetUrl+userpath[i].ownerGroup.users[j].id+'">'+userpath[i].ownerGroup.users[j].name+'</a></li>';
				}
				else{
					dl=dl+'<li><a href="'+targetUrl+userpath[i].ownerGroup.users[j].id+'">'+userpath[i].ownerGroup.users[j].name+'</a></li>';
				}
				
			}
			dl=dl+'</ul></dd></dl>';
			html=html+dl;
		}
		$("#"+div).html(html);
	};
	$.ajax({
		url:'/rest/user/get.html',
		type:'GET',
		contentType:"application/json; charset=utf-8",
		success:function(data){
		  handleGroup(data);
		},
		error:function(xhr){
		   //alert(xhr.responseText);
		}
	});
}

function checkGroup(div,user,selectedId,targetUrl){
	$.ajax({
		url:'/rest/user/hasGroup.html',
		type:'GET',
		data:'userId='+user.id,
		asyc:false,
		success:function(data){
			if(data)return;
			var html='';
			html='<dl class="managermark-choose clearfix">';
			html=html+'<dt>成员：</dt>';
			html=html+'<dd><ul class="clearfix">';
			if(user.id == selectedId){
				html=html+'<li class="choosed"><a href="'+targetUrl+user.id+'">'+user.name+'</a></li>';
			}
			else{
				html=html+'<li><a href="'+targetUrl+user.id+'">'+user.name+'</a></li>';
			}
			html=html+'</ul></dd></dl>';
			$("#"+div).prepend(html);
		},
		error:function(xhr){
		  
		}
	});
}

function retrieveGroupUser(user,userId){
	if(user.id == userId){	
		userpath.push(user);
		return true;
	}
	
	
	
	if(user.ownerGroup!=null){		
		
		userpath.push(user);
		var flag=false;
		for(var i=0;i<user.ownerGroup.users.length;i++){
			
			if(retrieveGroupUser(user.ownerGroup.users[i],userId))	{
				flag=true;
			}	
			
		}
		if(!flag){			
			userpath.pop()
		}
	}	
	
	
	return flag;
	

	
}


