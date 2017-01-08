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
					<form id="SearchForm" name="SearchForm" class="" method="get" action="${contextPath}/infotrade/infotradelist.htm">
						<div class="row-fluid header">
							<h4><legend> 交易表管理 <small>管理平台的交易表管理</small>
							</legend></h4>
							<div class="pull-right">
								<input id="buyusername" name="buyusername" type="text" class="search" placeholder="应答方..." value="${param.sname}" />
								<a class="btn-flat new-product" onclick="doSearch()">搜索</a>
								<%-- &nbsp;
								<a class="btn-flat success pull-right" href="${contextPath}/infotrade/infotradeedit.htm">
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
										<th class="span2">应答方</th>
										<th class="span2">
											<span class="line"></span>需求编号
										</th>
										<th class="span2">
											<span class="line"></span>需求方
										</th>
										<!-- <th class="span2">
											<span class="line"></span>总金额
										</th> -->
										<th class="span2">
											<span class="line"></span>到帐时间
										</th>
										<th class="span2">
											<span class="line"></span>状态
										</th>
										<th class="span2 align-right">
											<span class="line"></span>操作
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.result}" varStatus="status" var="v">
										<tr>
											<td>${v.buyusername}</td>
											<td>${v.itemids}</td>
											<td>${v.usernames}</td>
											<%-- <td>${v.totalcost}</td> --%>
											<td>${v.paydate}</td>
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
												<c:if test="${v.sstatus == 200}">支付失败取消交易</c:if>
											</td>
											<td class="align-right">
												<%-- <a href="${contextPath}/infotrade/infotradeedit.htm?tradeid=${v.tradeid}" class="btn-flat default">修改</a> <a
													onclick="doDel(${v.tradeid},${status.index});" class="btn-flat danger">删除</a> --%>
													
												<c:if test="${v.sstatus == 2 && not empty v.failure_msg}">
													<a href="${v.failure_msg}" class="btn-flat default" target="_blank">转帐确认</a>
												</c:if>
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
			ajax_get("${contextPath}/infotrade/infotradedelete.htm", "&tradeid=" + id, function(data) {
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