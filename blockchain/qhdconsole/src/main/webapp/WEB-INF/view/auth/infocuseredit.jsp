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
<script src="${contextPath}/resources/web/js/md5.js"></script>
</head>
<body>
	<div class="content">
		<div class="container-fluid">
			<div id="pad-wrapper" class="new-user">
				<div class="span9" id="content">
					<div class="row-fluid">
						<form id="editForm" modelAttribute="infocuser" action="" method="post" class="form-horizontal inline-input">
							<fieldset>
								<legend>编辑用户</legend>
								<div class="control-group">
									<label class="control-label" for="focusedInput">账号：</label>
									<div class="controls">
										<input id="username" name="username" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
											value="${infocuser.username}" <c:if test="${not empty infocuser && infocuser.username == 'admin'}">readonly="readonly" </c:if> />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">用户名：</label>
									<div class="controls">
										<input id="nickname" name="nickname" class="input-xlarge focused inline-input" id="focusedInput" placeholder="" type="text" maxlength="20"
											value="${infocuser.nickname}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">密码：</label>
									<div class="controls">
										<input id="password" name="password" class="input-xlarge focused inline-input" id="focusedInput" placeholder="" type="password"
											maxlength="20" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">状态：</label>
									<div class="ui-select span3">
										<select name="userstate" id="userstate">
											<c:forEach items="${userstatelist}" var="a">
												<option value="${a.ck}" <c:if test="${infocuser.userstate==a.ck}"> selected="selected"</c:if> />${a.cv}
														</c:forEach>
										</select>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">角色：</label>
									<div class="controls ui-select span3">
										<select name="rid" id="rid">
											<c:forEach items="${rolelist}" var="a">
												<option value="${a.rid}" <c:if test="${infocuser.rid==a.rid}"> selected="selected"</c:if> />${a.sname}
														</c:forEach>
										</select>
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
	function doSave() {
		ajax_get("${contextPath}/auth/infocusersave.htm", $("#editForm").serialize() + "&passwd=" + hex_md5(trim($('#password').val())), function(
				data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
				window.setTimeout("location.href='${contextPath}/auth/infocuserlist.htm'", 1500);
			}
		});
	}
</script>