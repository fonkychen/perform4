   <%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   

        <!--个人中心-->
        <div class="myinfo-wrap">
            <div class="fl"><img src="/common/usericon.html?iconId=${user.userIcon.id }"></div>
            <div class="fl">
                <p><span>姓 名：</span>${user.name }</p>
                <p><span>部 门：</span>${user.department.name }</p>
                <p><span>职 位：</span>${user.position.name }</p>
                <p><span>能 力：</span>${user.capability.name }</p>
                <p><span>资 历：</span>${user.qualification.name }</p>
            </div>
            <div class="fl">
                <p><span>昵 称：</span>${user.nickName }</p>
                <p><span>国 家：</span><c:if test="${not empty user.country }">${user.country.name }</c:if> </p>
                <p><span>官 职：</span>${user.title.name } </p>
                <p><span>等 级：</span>${user.title.rank }级</p>
            </div>
            <!--<a href="" class="icon-set"></a>-->
        </div>
        <div class="blank20"></div>
