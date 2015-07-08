<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<script type="text/javascript">
$(document).ready(function(){
	$("a[id^='gap_select_']").click(function(){
		$("#gap_text").val($(this).html());
		$("#gap_select").css("display","none");
		$("#dategap").val($(this).attr("gap"));
	});
	
	$("#gap_text").focusin(function(){
		$("#gap_select").css("display","block");
	});
	$("#gap_text").focusout(function(){
		window.setTimeout(function(){
			$("#gap_select").css("display","none");
		},200);
		
	});
	
	
	$("a[id^='coin_select_']").click(function(){
		$("#coin_text").val($(this).html());
		$("#coin_select").css("display","none");
		$("#coinNum").val($(this).attr("coin"));
	});
	
	$("#coin_text").focusin(function(){
		$("#coin_select").css("display","block");
	});
	$("#coin_text").focusout(function(){
		window.setTimeout(function(){
			$("#coin_select").css("display","none");
		},200);
		
	})
	
	
});
var isload=false;
function saveReward(){
	var title=$("#title").val();
	var description=$("#description").val();
	var dategap=$("#dategap").val();
	var coinNum=$("#coinNum").val();
	
	if(title.length<=0 && title.length>20){
		alert('标题长度应在1-20之间');
		return;
	}
	
	if(description.length > 255){
		alert('详情不能超过255个字');
		return;
	}
	
	if(dategap==''){
		alert('请选择悬赏时间');
		return;
	}
	if(coinNum==''){
		alert('请选择悬赏奖励');
		return;
	}
	
	
	if(!isload){
		isload=true;
		$.ajax({
			url:'/rest/connect/reward/save.html',
			type:'POST',
			data:'title='+title+"&description="+description+"&dateGap="+dategap+"&coinNum="+coinNum,
			success:function(){
				window.location.href='/connect/reward/index.html';
			},
			error:function(xhr){
				alert(xhr.responseText);
			}
		}).done(function(){
			isload=false;
		});
	}
}
</script>
<div class="bg clearfix">
        <h2>飞鸽传书 〉 <a href="/connect/reward/index.html">悬赏榜</a> 〉 发布悬赏</h2>
        <table class="myub-expend">
            <tr>
                <td>悬赏标题</td>
                <td><input id="title" type="text"></td>
            </tr>
            <tr>
                <td>悬赏详情</td>
                <td><textarea id="description"></textarea></td>
            </tr>
            <tr>
                <td>悬赏时间</td>
                <td class="relative2 thistd"><!--IE7 hack:点击当前td时class增加thistd或style="z-index:10;"-->
                    <input type="hidden" id="dategap" />
                    <input id="gap_text" class="arrow" type="text" contenteditable="false">
                    <ul id="gap_select" class="select-ul">
                        <li><a id="gap_select_1" gap="1">1天</a></li>
                        <li><a id="gap_select_2" gap="5">5天</a></li>
                        <li><a id="gap_select_3" gap="10">10天</a></li>
                        <li><a id="gap_select_4" gap="15">15天</a></li>
                        <li><a id="gap_select_5" gap="30">30天</a></li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>悬赏奖励</td>
                <td class="relative2">
                    <input type="hidden" id="coinNum" />
                    <input id="coin_text" class="arrow" type="text" value="">
                    <ul class="select-ul" id="coin_select">
                        <li><a id="coin_select_1" coin="3">3UB</a></li>
                        <li><a id="coin_select_2" coin="5">5UB</a></li>
                        <li><a id="coin_select_3" coin="10">10UB</a></li>
                        <li><a id="coin_select_4" coin="15">15UB</a></li>
                    </ul>
                </td>
            </tr>
           
            <tr>
                <td>&nbsp;</td>
                <td><button class="btn-red" onclick="saveReward();">发布悬赏</button></td>
            </tr>
        </table>
        <dl class="tips">
            <dt>注意事项</dt>
            <dd>1、奖励从个人财富中扣除</dd>
            <dd>2、多人以空格间隔</dd>
        </dl>
        <div class="blank20"></div>
    </div>

