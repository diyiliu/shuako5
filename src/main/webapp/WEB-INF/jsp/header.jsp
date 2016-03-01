<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mainmenu-wrapper">
    <div class="container">
        <div class="menuextras">
            <ul>
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <li><a href="<c:url value="/anon/login.htm"/>">登录</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="dropdown">
                            <a href="#" data-toggle="dropdown">${user.username}</a>
                            <ul class="dropdown-menu">
                                <li><a href="#">个人中心</a></li>
                                <li class="divider"></li>
                                <li><a href="<c:url value="/logout.htm"/> ">退出</a></li>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>

            <div class="extras"></div>
        </div>
        <nav id="mainmenu" class="mainmenu">
            <ul>
                <li class="logo-wrapper"><a href="../../index.html"><img
                        src="${pageContext.request.contextPath}/resource/img/shuako-logo.png" alt="刷壳"></a></li>
                <li><a href="../../index.html">首页</a></li>
                <li><a href="task.html">任务</a></li>
                <li><a href="order.html">订单</a></li>
                <li><a href="publish.html">发布</a></li>
                <li><a href="#">帮助</a></li>
            </ul>
        </nav>
    </div>
</div>