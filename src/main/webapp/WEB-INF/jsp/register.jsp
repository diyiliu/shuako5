<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/font-awesome.min.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,600,800' rel='stylesheet' type='text/css'>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/main.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
    <title>刷壳</title>
</head>
<body>
<div class="mainmenu-wrapper">
    <div class="container">
        <div class="menuextras">
            <ul>
                <li><a href="../../login.html">登录</a></li>
            </ul>

            <div class="extras"></div>
        </div>
        <nav id="mainmenu" class="mainmenu">
            <ul>
                <li class="logo-wrapper"><a href="../../index.html"><img src="${pageContext.request.contextPath}/resource/img/shuako-logo.png" alt="刷壳"></a></li>
                <li><a href="../../index.html">首页</a></li>
                <li><a href="task.html">任务</a></li>
                <li><a href="order.html">订单</a></li>
                <li><a href="publish.html">发布</a></li>
                <li><a href="#">帮助</a></li>
            </ul>
        </nav>
    </div>
</div>
<!-- Page Title -->
<div class="section section-breadcrumbs">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1>注册</h1>
            </div>
        </div>
    </div>
</div>

<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-sm-5">
                <div class="basic-login">
                    <form role="form" action="<c:url value="/user/register.htm"/>" method="post">
                        <div class="form-group">
                            <label for="register-username"><i class="icon-user"></i> <b>昵称</b></label>
                            <input class="form-control" id="register-username" type="text" placeholder="" name="username">
                        </div>
                        <div class="form-group">
                            <label for="register-password"><i class="icon-lock"></i> <b>密码</b></label>
                            <input class="form-control" id="register-password" type="password" placeholder="" name="password">
                        </div>
                        <div class="form-group">
                            <label for="register-password2"><i class="icon-lock"></i> <b>确认密码</b></label>
                            <input class="form-control" id="register-password2" type="password" placeholder="">
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn pull-right">注册</button>
                            <div class="clearfix"></div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-sm-7 social-login">
                <p>Or login with your Facebook or Twitter</p>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<!-- Footer -->
<div class="footer">
    <div class="container">
        <div class="row">
            <div class="col-footer col-md-3 col-xs-6">
                <h3>Our Latest Work</h3>

                <div class="portfolio-item">
                    <div class="portfolio-image">
                        <a href="page-portfolio-item.html"><img src="${pageContext.request.contextPath}/resource/img/portfolio6.jpg" alt="Project Name"></a>
                    </div>
                </div>
            </div>
            <div class="col-footer col-md-3 col-xs-6">
                <h3>Navigate</h3>
                <ul class="no-list-style footer-navigate-section">
                    <li><a href="page-blog-posts.html">Blog</a></li>
                    <li><a href="page-portfolio-3-columns-2.html">Portfolio</a></li>
                    <li><a href="page-products-3-columns.html">eShop</a></li>
                    <li><a href="page-services-3-columns.html">Services</a></li>
                    <li><a href="page-pricing.html">Pricing</a></li>
                    <li><a href="page-faq.html">FAQ</a></li>
                </ul>
            </div>

            <div class="col-footer col-md-4 col-xs-6">
                <h3>Contacts</h3>

                <p class="contact-us-details">
                    <b>Address:</b> 123 Fake Street, LN1 2ST, London, United Kingdom<br/>
                    <b>Phone:</b> +44 123 654321<br/>
                    <b>Fax:</b> +44 123 654321<br/>
                    <b>Email:</b> <a href="mailto:getintoutch@yourcompanydomain.com">getintoutch@yourcompanydomain.com</a>
                </p>
            </div>
            <div class="col-footer col-md-2 col-xs-6">
                <h3>Stay Connected</h3>
                <ul class="footer-stay-connected no-list-style">
                    <li><a href="#" class="facebook"></a></li>
                    <li><a href="#" class="twitter"></a></li>
                    <li><a href="#" class="googleplus"></a></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="footer-copyright">&copy; 2013 mPurpose. All rights reserved.</div>
            </div>
        </div>
    </div>
</div>

<!-- Javascripts -->
<!--
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="${pageContext.request.contextPath}/resource/js/jquery-1.9.1.min.js"><\/script>')</script>-->
<script src="${pageContext.request.contextPath}/resource/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/main-menu.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/template.js"></script>
</body>
</html>
