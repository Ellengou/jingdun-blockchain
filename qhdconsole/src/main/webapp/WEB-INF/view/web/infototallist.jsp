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
<body>
	<div class="content">
		<div class="container-fluid">
			<div id="pad-wrapper">
				<div class="table-products">
					<form id="SearchForm" name="SearchForm" class="" method="get" action="${contextPath}/infototal/infototallist.htm">
						<div class="row-fluid header">
							<h4>
								<legend>
									第三方交易表管理
									<small>管理平台的第三方交易表管理</small>
								</legend>
							</h4>
							<div class="pull-right">
								<input id="sname" name="sname" type="text" class="search" placeholder="Type ..." value="${param.sname}" />
								<a class="btn-flat new-product" onclick="doSearch()">搜索</a>
								&nbsp;
								<a class="btn-flat success pull-right" href="${contextPath}/infototal/infototaledit.htm">
									<span>&#43;</span> 新增
								</a>
							</div>
						</div>
					</form>
					<form id="listForm" name="listForm" method="get" class="">
						<div class="row-fluid table">
							<table class="table table-hover table-striped">
								<thead>
									<tr>
										<th class="span2">
											支付类型：0-alipay; 1-paypal;
										</th>
										<th class="span2">
											<span class="line"></span>订单生成时间
										</th>
										<th class="span2">
											<span class="line"></span>到帐时间
										</th>
										<th class="span2">
											<span class="line"></span>0-未处理；1-已处理；
										</th>
										<th class="span2">
											<span class="line"></span>第三方反馈的交易号
										</th>
										<th class="span2 align-right">
											<span class="line"></span>操作
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.result}" varStatus="status" var="v">
										<tr>
											<td>${v.stype}</td>
											<td>${v.askdate}</td>
											<td>${v.paydate}</td>
											<td>${v.sflag}</td>
											<td>${v.tradeno}</td>
											<td class="align-right">
												<a href="${contextPath}/infototal/infototaledit.htm?totalid=${v.totalid}" class="btn-flat default">修改</a>
												<a onclick="doDel(${v.totalid},${status.index});" class="btn-flat danger">删除</a>
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
	</div>
</body>
</html>
<script type="text/javascript">
	function doSearch() {
		$('#SearchForm').submit();
	}
	function doDel(id, index) {
		if (confirm("确认删除？")) {
			ajax_get("${contextPath}/infototal/infototaldelete.htm", "&totalid=" + id, function(data) {
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