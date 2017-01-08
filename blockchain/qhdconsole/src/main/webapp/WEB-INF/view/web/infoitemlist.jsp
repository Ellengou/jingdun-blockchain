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
					<form id="SearchForm" name="SearchForm" class="" method="get" action="${contextPath}/infoitem/infoitemlist.htm">
						<div class="row-fluid header">
							<h4><legend> 需求表管理 <small>平台的需求表管理</small>
							</legend></h4>
							<div class="pull-right">
								<input id="username" name="username" type="text" class="search" placeholder="需求用户..." value="${param.sname}" />
								<a class="btn-flat new-product" onclick="doSearch()">搜索</a>
								<%-- &nbsp;
								<a class="btn-flat success pull-right" href="${contextPath}/infoitem/infoitemedit.htm">
									<span>&#43;</span> 新增
								</a> --%>
							</div>
						</div>
					</form>
					<form id="listForm" name="listForm" method="get" class="">
						<div class="row-fluid table">
							<table class="table table-hover table-striped">
								<thead>
									<tr>
										<th class="span2">用户名</th>
										<th class="span2">
											<span class="line"></span>类型
										</th>
										<th class="span2">
											<span class="line"></span>金额/支付金额
										</th>
										<th class="span2">
											<span class="line"></span>标志
										</th>
										<th class="span2">
											<span class="line"></span>状态
										</th>
										<th class="span2">
											<span class="line"></span>时间
										</th>
										<th class="span2 align-right">
											<span class="line"></span>操作
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.result}" varStatus="status" var="v">
										<tr>
											<td>${v.username}</td>
											<td>
												<c:if test="${v.stype == 0}">CNY</c:if>
												<c:if test="${v.stype == 1}">USD</c:if>
											</td>
											<td>${v.money}/${v.totalcost}</td>
											<td>
												<c:if test="${v.sflag == 0}">
													<span class="label label-success">正常</span>
												</c:if>
												<c:if test="${v.sflag == 1}">
													<span class="label label-info">超时系统取消</span>
												</c:if>
												<c:if test="${v.sflag == 2}">
													<span class="label">用户主动取消</span>
												</c:if>
												<c:if test="${v.sflag == 11}">已删除</c:if>
											</td>
											<td>
												<c:if test="${v.sstatus == 0}">等待转账</c:if>
												<c:if test="${v.sstatus == 2}">等待系统转账</c:if>
												<c:if test="${v.sstatus == 3}">完成</c:if>
												<c:if test="${v.sstatus == 21}">系统已转账给需求方</c:if>
												<c:if test="${v.sstatus == 22}">系统已转账给应答方</c:if>
												<c:if test="${v.sstatus == 98}">
													<span class="label">登记</span>
												</c:if>
												<c:if test="${v.sstatus == 99}">
													<span class="label label-success">登记并转帐</span>
												</c:if>
												<c:if test="${v.sstatus == 96}">超时系统取消未退款</c:if>
												<c:if test="${v.sstatus == 97}">超时系统取消已退款</c:if>
												<c:if test="${v.sstatus == 94}">用户主动取消未退款</c:if>
												<c:if test="${v.sstatus == 95}">用户主动取消已退款</c:if>
											</td>
											<td>${v.createdate}</td>
											<td class="align-right">
												<a href="${contextPath}/infoitem/infoitemedit.htm?itemid=${v.itemid}" class="btn-flat default">查看</a>
												<c:if test="${v.sstatus == 2 && not empty v.failure_msg}">
													<a href="${v.failure_msg}" class="btn-flat default" target="_blank">转帐确认</a>
												</c:if>
												<%--  <a
													onclick="doDel(${v.itemid},${status.index});" class="btn-flat danger">删除</a> --%>
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
			ajax_get("${contextPath}/infoitem/infoitemdelete.htm", "&itemid=" + id, function(data) {
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