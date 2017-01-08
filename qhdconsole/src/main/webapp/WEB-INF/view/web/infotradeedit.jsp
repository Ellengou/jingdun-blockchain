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
						<form id="editForm" modelAttribute="infotrade" action="" method="post" class="form-horizontal inline-input">
							<input type="hidden" id="tradeid" name="tradeid" value="${infotrade.tradeid}" />
							<fieldset>
								<legend>编辑交易表</legend>
								<div class="control-group">
									<label class="control-label" for="focusedInput">：</label>
									<div class="controls">
										<input id="itemids" name="itemids" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="60" value="${infotrade.itemids}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">需求方用户id：</label>
									<div class="controls">
										<input id="userids" name="userids" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="60" value="${infotrade.userids}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">应答方用户id：</label>
									<div class="controls">
										<input id="buyuserid" name="buyuserid" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${infotrade.buyuserid}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">应答方到帐时间：</label>
									<div class="controls">
										<input id="paydate" name="paydate" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${infotrade.paydate}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">0-等待转帐；3-完成；200-支付失败取消交易；：</label>
									<div class="controls">
										<input id="sstatus" name="sstatus" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${infotrade.sstatus}" />
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
		var ids = [ 'itemids','userids','buyuserid','paydate','sstatus' ];
		if (check_required(ids)) {
			return;
		}
		ajax_get("${contextPath}/infotrade/infotradesave.htm", $("#editForm").serialize(), function(data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
				window.setTimeout("location.href='${contextPath}/infotrade/infotradelist.htm'", 1500);
			}
		});
	}
</script>