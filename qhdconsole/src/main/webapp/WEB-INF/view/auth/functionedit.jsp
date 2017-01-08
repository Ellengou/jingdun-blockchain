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
			<div class="span9" id="content">
				<div class="row-fluid">
					<form id="editForm" modelAttribute="function" action="" method="post" class="form-horizontal inline-input">
						<input type="hidden" name="fid" id="fid" value="${function.fid}">
						<fieldset>
							<legend>编辑菜单</legend>
							<div class="control-group">
								<label class="control-label" for="focusedInput">功能名称：</label>
								<div class="controls">
									<input id="sname" name="sname" type="text" value="${function.sname}" maxlength="20" placeholder="" class="input-xlarge focused inline-input"
										readonly="readonly" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="focusedInput">链接：</label>
								<div class="controls">
									<input id="href" name="href" type="text" value="${function.href}" maxlength="60" placeholder="" class="input-xlarge focused inline-input" />
									<span class="red ml5">*</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="focusedInput">标记：</label>
								<div class="ui-select span3">
									<select name="sflag" id="sflag">
										<c:forEach items="${sflaglist}" var="a">
											<option value="${a.ck}" <c:if test="${infocuser.sflag==a.ck}"> selected="selected"</c:if>>${a.cv}</option>
										</c:forEach>
										<select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="focusedInput">是否在菜单中展示：</label>
								<div class="ui-select span3">
									<select name="isshow" id="isshow">
										<c:forEach items="${isshowlist}" var="a">
											<option value="${a.ck}" <c:if test="${infocuser.isshow==a.ck}"> selected="selected"</c:if>>${a.cv}</option>
										</c:forEach>
										<select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="focusedInput">创建者：</label>
								<div class="controls">
									<input id="username" name="username" type="text" value="${function.username}" maxlength="20" placeholder=""
										class="input-medium focused inline-input" readonly="readonly" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="focusedInput">创建时间：</label>
								<div class="controls">
									<input size="20" id="createdate" name="createdate" value="${function.createdate}" placeholder="" class="input-medium focused inline-input"
										type="text" readonly="readonly" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="focusedInput">备注信息：</label>
								<div class="controls">
									<input id="remarks" name="remarks" type="text" value="${function.remarks}" maxlength="20" placeholder=""
										class="input-xlarge focused inline-input" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="focusedInput"></label>
								<div class="controls">
									<span id="msgerr" name="msgerr" class="red ml5">带*为必填项</span>
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="doSave();">保存</button>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script language="javascript">
	function doSave() {
		ajax_get("${contextPath}/auth/functionsave.htm", $("#editForm").serialize(), function(data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
			}
		});
	}
</script>