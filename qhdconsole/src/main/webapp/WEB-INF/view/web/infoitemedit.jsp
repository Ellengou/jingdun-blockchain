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
<link href="${contextPath}/resources/assets/css/lib/font-awesome.css" type="text/css" rel="stylesheet" />
<link href="${contextPath}/resources/assets/css/layout.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/elements.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/icons.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/assets/css/compiled/new-user.css" type="text/css" media="screen" rel="stylesheet" />
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js">
</script>
<![endif]-->
</head>
<body>
	<div class="content">
		<div class="container-fluid">
			<div id="pad-wrapper" class="new-user">
				<div class="span9" id="content">
					<div class="row-fluid">
						<form id="editForm" modelAttribute="infoitem" action="" method="post" class="form-horizontal inline-input">
							<input type="hidden" id="itemid" name="itemid" value="${infoitem.itemid}" />
							<fieldset>
								<legend>需求信息</legend>
								<div class="control-group">
									<label class="control-label" for="focusedInput">用户名：</label>
									<div class="controls">
										<input id="username" name="username" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
											value="${infoitem.username}" readonly="readonly" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">需求类型：</label>
									<div class="ui-select span3">
										<select name="stype" id="stype" disabled="disabled">
											<c:forEach items="${stypelist}" var="sa">
												<option value="${sa.ckey}" <c:if test="${infoitem.stype==sa.ckey}"> selected="selected"</c:if>>${sa.cvalue}</option>
											</c:forEach>
										</select>
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">需求金额：</label>
									<div class="controls">
										<input id="money" name="money" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
											value="${infoitem.money}" readonly="readonly" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">支付费用类型：</label>
									<div class="ui-select span3">
										<select name="intype" id="intype" disabled="disabled">
											<c:forEach items="${stypelist}" var="sax">
												<option value="${sax.ckey}" <c:if test="${infoitem.intype==sax.ckey}"> selected="selected"</c:if>>${sax.cvalue}</option>
											</c:forEach>
										</select>
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">汇率：</label>
									<div class="controls">
										<input id="ratio" name="ratio" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
											value="${infoitem.ratio}" readonly="readonly" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">汇率金额：</label>
									<div class="controls">
										<input id="cost" name="cost" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
											value="${infoitem.cost}" readonly="readonly" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">总金额（含手续费）：</label>
									<div class="controls">
										<input id="totalcost" name="totalcost" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
											value="${infoitem.totalcost}" readonly="readonly" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<%-- <div class="control-group">
										<label class="control-label" for="focusedInput">需求方到帐时间：</label>
										<div class="controls">
											<input id="paydate" name="paydate" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
												value="${infoitem.paydate}" readonly="readonly" />
											<span class="red ml5">*</span>
										</div>
									</div> --%>
								<div class="control-group">
									<label class="control-label" for="focusedInput">标志：</label>
									<div class="ui-select span3">
										<select name="sflag" id="sflag" disabled="disabled">
											<c:forEach items="${sflaglist}" var="sl">
												<option value="${sl.ckey}" <c:if test="${infoitem.sflag==sl.ckey}"> selected="selected"</c:if>>${sl.cvalue}</option>
											</c:forEach>
										</select>
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">状态：</label>
									<div class="ui-select span3">
										<select name="sflag" id="sflag" disabled="disabled">
											<c:forEach items="${sstatuslist}" var="ss">
												<option value="${ss.ckey}" <c:if test="${infoitem.sstatus==ss.ckey}"> selected="selected"</c:if>>${ss.cvalue}</option>
											</c:forEach>
										</select>
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label"></label>
									<div class="controls">
										<span id="msgerr" name="msgerr" class="red ml5">带*为必填项</span>
									</div>
								</div>
								<div class="form-actions">
									<button type="button" class="btn btn-primary" onclick="doSave();">保存</button>
									<button type="reset" class="btn" onclick="history.go(-1)">取消</button>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script language="javascript">
	function clearerr() {
		$('#msgerr').text('带*为必填项');
	}
	function doSave() {
		clearerr();
		var ids = [ 'username', 'stype', 'money', 'intype', 'ratio', 'cost', 'totalcost', 'paydate', 'sflag', 'sstatus', ];
		if (check_required(ids)) {
			return;
		}
		ajax_get("${contextPath}/infoitem/infoitemsave.htm", $("#editForm").serialize(), function(data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
				window.setTimeout("location.href='${contextPath}/infoitem/infoitemlist.htm'", 1500);
			}
		});
	}
</script>