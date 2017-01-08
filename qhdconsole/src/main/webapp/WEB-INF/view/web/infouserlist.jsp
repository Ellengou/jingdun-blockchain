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
<link href="${contextPath}/resources/assets/css/layout.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/elements.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/icons.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/lib/font-awesome.css" type="text/css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/compiled/user-list.css" type="text/css" media="screen" rel="stylesheet" />
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
	<div class="content">
		<div class="container-fluid">
			<div id="pad-wrapper" class="users-list">
				<form id="SearchForm" name="SearchForm" class="" method="get" action="${contextPath}/infouser/infouserlist.htm">
					<div class="row-fluid header">
						<h4><legend> 用户管理 <small>平台用户管理</small>
						</legend></h4>
						<div class="pull-right">
							<input type="text" id="username" name="username" class="search" placeholder="用户名..." value="${param.username}" />
							<a class="btn-flat new-product" onclick="doSearch()">搜索</a>
						</div>
					</div>
				</form>
				<form id="listForm" name="listForm" method="get" class="">
					<div class="row-fluid table">
						<table class="table table-hover table-striped">
							<thead>
								<tr>
									<th class="span4">用户名信息</th>
									<th class="span3">
										<span class="line"></span> 注册时间
									</th>
									<th class="span3">
										<span class="line"></span> 帐户信息
									</th>
									<th class="span2 align-right">
										<span class="line"></span> 操作
									</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.result}" varStatus="status" var="v">
									<tr>
										<td>
											<c:if test="${not empty v.icon}">
												<img src="${v.icon}" class="img-circle avatar hidden-phone" />
											</c:if>
											<c:if test="${empty v.icon}">
												<img src="${contextPath}/resources/assets/img/contact-img.png" class="img-circle avatar hidden-phone" />
											</c:if>
											<a href="user-profile.html" class="name">${v.username}</a> <span class="subtext">${v.nickname}</span> &nbsp;
										</td>
										<td>${v.registerdate}</td>
										<td>${v.payconfig}</td>
										<td class="align-right">
											<a href="${contextPath}/infouser/infouseredit.htm?userid=${v.userid}" class="btn-flat default">查看</a>
											<%--  <a
												onclick="doDel(${v.userid},${status.index});" class="btn-flat danger">删除</a> --%>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<tags:pagination page="${page}" />
				</form>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	function doSearch() {
		$('#SearchForm').submit();
	}
	function doDel(id, index) {
		if (confirm("确认删除？")) {
			ajax_get("${contextPath}/infouser/infouserdelete.htm", "&userid=" + id, function(data) {
				if (data.errorMessage) {
					showmsg(0, data.errorMessage);
				} else {
					showmsg(1, "处理成功!");
					window.setTimeout("location.reload()", 1500);
				}
			});
		}
	}
</script>