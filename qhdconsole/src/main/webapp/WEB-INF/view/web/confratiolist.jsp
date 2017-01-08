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
					<form id="SearchForm" name="SearchForm" class="" method="get" action="${contextPath}/confratio/confratiolist.htm">
						<div class="row-fluid header">
							<h4>
								<legend>
									汇率表管理
									<small>管理平台的汇率表管理</small>
								</legend>
							</h4>
							<div class="pull-right">
								<input id="sname" name="sname" type="text" class="search" placeholder="Type ..." value="${param.sname}" />
								<a class="btn-flat new-product" onclick="doSearch()">搜索</a>
								&nbsp;
								<a class="btn-flat success pull-right" href="${contextPath}/confratio/confratioedit.htm">
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
											日期，如：20160410
										</th>
										<th class="span2">
											<span class="line"></span>汇率
										</th>
										<th class="span2">
											<span class="line"></span>需求类型：0-CNY；1-USD
										</th>
										<th class="span2">
											<span class="line"></span>支付费用类型：0-CNY；1-USD
										</th>
										<th class="span2">
											<span class="line"></span>汇率来源url
										</th>
										<th class="span2">
											<span class="line"></span>更新时间
										</th>
										<th class="span2 align-right">
											<span class="line"></span>操作
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.result}" varStatus="status" var="v">
										<tr>
											<td>${v.sdate}</td>
											<td>${v.ratio}</td>
											<td>${v.stype}</td>
											<td>${v.intype}</td>
											<td>${v.sfrom}</td>
											<td>${v.getdate}</td>
											<td class="align-right">
												<a href="${contextPath}/confratio/confratioedit.htm?ratioid=${v.ratioid}" class="btn-flat default">修改</a>
												<a onclick="doDel(${v.ratioid},${status.index});" class="btn-flat danger">删除</a>
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
			ajax_get("${contextPath}/confratio/confratiodelete.htm", "&ratioid=" + id, function(data) {
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