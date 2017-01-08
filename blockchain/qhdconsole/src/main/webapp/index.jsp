<%@page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>管理平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/resources/inc/inc.jsp"%>
<link href="${contextPath}/resources/assets/css/bootstrap/bootstrap.css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/lib/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/lib/font-awesome.css" type="text/css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/layout.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/elements.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/icons.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/compiled/index.css" type="text/css" media="screen" />
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<style type="text/css">
#main {
	padding: 0;
	margin: 0;
}

#header {
	position: static;
}

#header li {
	font-size: 16px;
	_font-size: 14px;
}

#header .brand {
	font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
	font-size: 26px;
	padding-left: 33px;
}

#footer {
	margin: 8px 0 0 0;
	padding: 3px 0 0 0;
	font-size: 11px;
	text-align: center;
	border-top: 2px solid #0663A2;
}

#footer,#footer a {
	color: #999;
}
</style>
</head>
<body style="overflow: hidden;">
	<div style="width: auto;" id="main">
		<div id="header" class="navbar navbar-inverse">
			<div class="navbar-inner">
				<button type="button" class="btn btn-navbar visible-phone" id="menu-toggler">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="brand" href="#">管理平台</a>
				<ul class="nav pull-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle hidden-phone" data-toggle="dropdown">
							${_USER_.nickname}
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="${contextPath}/auth/logout.htm">Logout</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
		<div class="container-fluid" style="padding-right: 0px !important; padding-left: 0px !important;">
			<div id="content" class="row-fluid">
				<div style="width: 207px; display: block; float: left;" id="left">
					<iframe id="menuFrame" name="menuFrame" src="${contextPath}/auth/menu.htm" scrolling="auto" frameborder="no" height="600"
						style="overflow-x: hidden; overflow-y: visible; width: 207px;"></iframe>
				</div>
				<div style="width: 100%; float: left;" id="right">
					<iframe id="mainFrame" name="mainFrame" src="${contextPath}/auth/home.htm" noresize="noresize" marginwidth="0" marginheight="0" scrolling="auto"
						style="overflow-x: visible; overflow-y: visible;" frameborder="no" height="600" width="100%"></iframe>
				</div>
			</div>
			<div id="footer" class="row-fluid">
				Copyright © 2014-2016 - Powered By
				<a href="http://www.yuyou-technology.com" target="_blank">杭州语右科技有限公司</a>
				V1.0.0
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var leftWidth = "207"; // 左侧窗口大小
		function wSize() {
			var minHeight = 600, minWidth = 980;
			var strs = getWindowSize().toString().split(",");
			$("#menuFrame, #mainFrame").height((strs[0] < minHeight ? minHeight : strs[0]) - $("#header").height() - $("#footer").height() - 32);
			if (strs[1] < minWidth) {
				$("#main").css("width", minWidth);
				$("html,body").css({
					"overflow" : "auto",
					"overflow-x" : "auto",
					"overflow-y" : "auto"
				});
			} else {
				$("#main").css("width", "auto");
				$("html,body").css({
					"overflow" : "auto",
					"overflow-x" : "auto",
					"overflow-y" : "auto"
				});
			}
			$("#right").width($("#content").width() - $("#left").width());
		}
	</script>
	<script src="${contextPath}/resources/web/js/wsize.js" type="text/javascript"></script>
</body>
</html>