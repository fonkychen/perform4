<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
 $(document).ready(function(){
	 $( window ).scroll(function() {
		var top=$(document).scrollTop();		
		if(top>100){
			$("#sidenav").css("display","block");
		}
		else{
			$("#sidenav").css("display","none");
		}
	 });
 });
</script>
<div class="sidemodul" id="sidenav" style="display:none;">
        <a href="#topbar" class="backtop" title="返回顶部"><img src="/default/images/sidemodul-arrow.png"></a>
        <a href="http://www.kpi365.com" class="qrcode" title="扫描二维码，关注KPI 365">
            <img src="/default/images/qrcode.png">
            <div class="qrcode2"><img src="/default/images/qrcode-kpi365.jpg" alt="扫描二维码，关注KPI 365"></div>
        </a>
        <a href="/club/topic.html" class="feedback" title="有意见？说说看！">吐槽</a>
    </div>
