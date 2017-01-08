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
						<form id="editForm" modelAttribute="infouser" action="" method="post" class="form-horizontal inline-input">
							<input type="hidden" id="userid" name="userid" value="${infouser.userid}" />
							<fieldset>
								<legend>用户信息</legend>
								<div class="control-group">
									<label class="control-label" for="focusedInput">注册用户名：</label>
									<div class="controls">
										<input id="username" name="username" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
											value="${infouser.username}" readonly="readonly" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">用户昵称：</label>
									<div class="controls">
										<input id="nickname" name="nickname" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="50"
											value="${infouser.nickname}" readonly="readonly" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">注册时间：</label>
									<div class="controls">
										<input id="registerdate" name="registerdate" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text"
											maxlength="20" value="${infouser.registerdate}" readonly="readonly" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">icon：</label>
									<div class="controls">
										<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
											<img id="imgurlimg" name="imgurlimg" style="max-width: 200px; max-height: 150px;"
												<c:if test="${empty infouser.icon}">src="${contextPath}/resources/assets/img/noimage.png"</c:if>
												<c:if test="${not empty infouser.icon}">src="${infouser.icon}"</c:if> alt="" style="width: 200px; height: 150px;">
										</div>
									</div>
								</div>
								<legend>帐户绑定信息</legend>
								<c:if test="${not empty infopayconfig0}">
									<div class="control-group">
										<label class="control-label" for="focusedInput">支付宝：</label>
										<div class="controls">
											<input class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
												value="${infopayconfig0.sname}" readonly="readonly" />
											<span class="red ml5">*</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="focusedInput">帐户：</label>
										<div class="controls">
											<input class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
												value="${infopayconfig0.carnum}" readonly="readonly" />
											<span class="red ml5">*</span>
										</div>
									</div>
								</c:if>
								<c:if test="${not empty infopayconfig1}">
									<div class="control-group">
										<label class="control-label" for="focusedInput">PayPal：</label>
										<div class="controls">
											<input class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
												value="${infopayconfig1.sname}" readonly="readonly" />
											<span class="red ml5">*</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="focusedInput">帐户：</label>
										<div class="controls">
											<input class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
												value="${infopayconfig1.carnum}" readonly="readonly" />
											<span class="red ml5">*</span>
										</div>
									</div>
								</c:if>
								<div class="control-group">
									<label class="control-label"></label>
									<div class="controls">
										<span id="msgerr" name="msgerr" class="red ml5">带*为必填项</span>
									</div>
								</div>
								<div class="form-actions">
									<!-- <button type="button" class="btn btn-primary" onclick="doSave();">保存</button> -->
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
		var ids = [ 'username', 'password', 'nickname', 'icon', 'iconid', 'registerdate', 'payconfig', ];
		if (check_required(ids)) {
			return;
		}
		ajax_get("${contextPath}/infouser/infousersave.htm", $("#editForm").serialize(), function(data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
				window.setTimeout("location.href='${contextPath}/infouser/infouserlist.htm'", 1500);
			}
		});
	}
</script>