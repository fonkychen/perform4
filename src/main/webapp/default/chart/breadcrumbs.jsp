<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script type="text/javascript">
 $(document).ready(function(){
	 var title='<tiles:getAsString name="title" />';
	 if(title.indexOf('—')>0){
		 title=title.substring(0,title.indexOf('—'));
	 }
	 $("#title").html(title);
 })
</script>
        <div class="breadcrumbs">
    		<img src="/default/images/img_kpihero_01.png" />
    		<span><a href="/home/index.html">首页</a>&nbsp;&gt;&nbsp;<a href="/service/mycenter.html">管理中心</a>&nbsp;&gt;&nbsp;<span id="title"></span></span>
    	</div>