<%@page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html class="login-bg">
<head>
<title>管理平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/resources/inc/inc.jsp"%>
<!-- bootstrap -->
<link href="${contextPath}/resources/assets/css/bootstrap/bootstrap.css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />
<!-- global styles -->
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/layout.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/elements.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/icons.css" />
<!-- libraries -->
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/lib/font-awesome.css" />
<!-- this page specific styles -->
<link rel="stylesheet" href="${contextPath}/resources/assets/css/compiled/signin.css" type="text/css" media="screen" />
<!-- open sans font -->
<link href="${contextPath}/resources/assets/css//fonts1.css" rel="stylesheet" type="text/css" />
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js">
</script>
<![endif]-->
<!-- scripts -->
<script src="${contextPath}/resources/web/js/md5.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
html,body,table {
	background-color: #f5f5f5;
	width: 100%;
	height: 1px;
	text-align: center;
}
</style>
</head>
<body onkeydown="btn(event);">
	<!-- background switcher -->
	<div class="bg-switch visible-desktop" style="display: none !important;">
		<div class="bgs">
			<a href="#" data-img="landscape.jpg" class="bg active">
				<img src="${contextPath}/resources/assets/img/bgs/landscape.jpg" />
			</a>
			<a href="#" data-img="blueish.jpg" class="bg">
				<img src="${contextPath}/resources/assets/img/bgs/blueish.jpg" />
			</a>
			<a href="#" data-img="7.jpg" class="bg">
				<img src="${contextPath}/resources/assets/img/bgs/7.jpg" />
			</a>
			<a href="#" data-img="8.jpg" class="bg">
				<img src="${contextPath}/resources/assets/img/bgs/8.jpg" />
			</a>
			<a href="#" data-img="9.jpg" class="bg">
				<img src="${contextPath}/resources/assets/img/bgs/9.jpg" />
			</a>
			<a href="#" data-img="10.jpg" class="bg">
				<img src="${contextPath}/resources/assets/img/bgs/10.jpg" />
			</a>
			<a href="#" data-img="11.jpg" class="bg">
				<img src="${contextPath}/resources/assets/img/bgs/11.jpg" />
			</a>
		</div>
	</div>
	<div class="row-fluid login-wrapper">
		<h2 class="logo">管理平台</h2>
		<div class="span4 box">
			<form id="loginForm" class="" name="loginForm" action="" method="post">
				<div class="content-wrap">
					<h6>登 录</h6>
					<input class="span12" type="text" id="username" name="username" placeholder="Your username" value="${username}" tabindex="1" />
					<input class="span12" type="password" id="passwd" name="passwd" placeholder="Your password" tabindex="2" />
					<!-- <a href="#" class="forgot">Forgot password?</a> -->
					<div class="remember">
						<!-- <input id="remember-me" type="checkbox" />
					<label for="remember-me">Remember me</label> -->
					</div>
					<a class="btn-glow primary login" href="#" onclick="doLogin();">登 录</a>
				</div>
			</form>
		</div>
		<!-- <div class="span4 no-account">
			<p>Don't have an account?</p>
			<a href="signup.html">Sign up</a>
		</div> -->
	</div>
</body>
</html>
<script type="text/javascript">
	$(function() {
		// bg switcher
		var $btns = $(".bg-switch .bg");
		$btns.click(function(e) {
			e.preventDefault();
			$btns.removeClass("active");
			$(this).addClass("active");
			var bg = $(this).data("img");
			$("html").css("background-image", "url('${contextPath}/resources/assets/img/bgs/" + bg + "!important;')");
		});
	});
	function doLogin() {
		if (trim($('#username').val()) == "") {
			showmsg(0, "请输入用户名");
			return;
		}
		if (trim($('#passwd').val()) == "") {
			showmsg(0, "请输入密码");
			return;
		}
		$.ajax({
			url : "${contextPath}/auth/login.htm",
			data : $("#loginForm").serialize() + "&password=" + hex_md5(trim($('#passwd').val())),
			cache : false,
			success : doLoginCallback
		});
	}
	function doLoginCallback(xmlHttp) {
		var res = xmlHttp;
		if (res.errorMessage && res.errorMessage != "") {
			showmsg(0, res.errorMessage);
		} else {
			location.href = "${contextPath}/index.jsp";
		}
	}
	function btn(e) {
		var num = 0;
		if (window.event) {
			num = e.keyCode;
		} else if (e.which) {
			num = e.which;
		}
		if (num == 13) {
			doLogin();
		}
	}
</script>