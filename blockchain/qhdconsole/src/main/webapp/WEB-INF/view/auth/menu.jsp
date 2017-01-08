<%@page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<TITLE>管理平台</TITLE>
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
</head>
<body style="overflow: hidden; width: 210px;">
	<div id="sidebar-nav">
		<ul id="dashboard-menu">
			<li class="active">
				<div class="pointer" style="display: block">
					<div class="arrow"></div>
					<div class="arrow_border"></div>
				</div>
				<a id="home-menu" href="${contextPath}/auth/home.htm" target="mainFrame" style="text-decoration: none;">
					<i class="icon-home"></i>
					<span>Home</span>
				</a>
			</li>
			<c:forEach items="${menuList}" var="a" varStatus="status">
				<li>
					<div class="pointer" style="display: none">
						<div class="arrow"></div>
						<div class="arrow_border"></div>
					</div>
					<a class="dropdown-toggle" href="#" style="text-decoration: none;">
						<i class="${a.icon}"></i>
						<span>${a.sname}</span>
						<i class="icon-chevron-down"></i>
					</a>
					<ul class="submenu">
						<c:forEach items="${a.menulist}" var="b">
							<li>
								<c:if test="${not empty b.href && b.href != '#'}">
									<a href="${contextPath}${b.href}" target="mainFrame">${b.sname}</a>
								</c:if>
								<c:if test="${empty b.href || b.href == '#'}">
									<a href="${contextPath}/auth#" target="mainFrame">${b.sname}</a>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>