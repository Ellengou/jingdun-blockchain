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
					<form id="SearchForm" name="SearchForm" class="" method="get" action="${contextPath}/confconfig/confconfiglist.htm">
						<div class="row-fluid header">
							<h4>
								<legend>
									配置表管理
									<small>管理平台的配置表管理</small>
								</legend>
							</h4>
							<div class="pull-right">
								<input id="sname" name="sname" type="text" class="search" placeholder="Type ..." value="${param.sname}" />
								<a class="btn-flat new-product" onclick="doSearch()">搜索</a>
								&nbsp;
								<a class="btn-flat success pull-right" href="${contextPath}/confconfig/confconfigedit.htm">
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
											key
										</th>
										<th class="span2">
											<span class="line"></span>value
										</th>
										<th class="span2">
											<span class="line"></span>value
										</th>
										<th class="span2">
											<span class="line"></span>value
										</th>
										<th class="span2">
											<span class="line"></span>是否有效：0-有效；1-无效；
										</th>
										<th class="span2">
											<span class="line"></span>备注
										</th>
										<th class="span2 align-right">
											<span class="line"></span>操作
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.result}" varStatus="status" var="v">
										<tr>
											<td>${v.ckey}</td>
											<td>${v.cvalue}</td>
											<td>${v.ctbname}</td>
											<td>${v.cfield}</td>
											<td>${v.cflag}</td>
											<td>${v.remark}</td>
											<td class="align-right">
												<a href="${contextPath}/confconfig/confconfigedit.htm?seq=${v.seq}" class="btn-flat default">修改</a>
												<a onclick="doDel(${v.seq},${status.index});" class="btn-flat danger">删除</a>
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
			ajax_get("${contextPath}/confconfig/confconfigdelete.htm", "&seq=" + id, function(data) {
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