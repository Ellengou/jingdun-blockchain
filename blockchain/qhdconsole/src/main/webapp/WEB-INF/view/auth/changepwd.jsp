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
						<form id="editForm" modelAttribute="" action="" method="post" class="form-horizontal inline-input">
							<fieldset>
								<legend>密码修改</legend>
								<div class="control-group">
									<label class="control-label" for="focusedInput">原密码：</label>
									<div class="controls">
										<input id="soldpasswd" name="soldpasswd" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="password"
											maxlength="20" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">新密码：</label>
									<div class="controls">
										<input id="newpasswd" name="newpasswd" class="input-xlarge focused inline-input" id="focusedInput" placeholder="" type="password"
											maxlength="20" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">确认密码：</label>
									<div class="controls">
										<input id="rnewpasswd" name="rnewpasswd" class="input-xlarge focused inline-input" id="focusedInput" placeholder="" type="password"
											maxlength="20" />
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
		var ids = [ 'soldpasswd', 'newpasswd', 'rnewpasswd' ];
		if (check_required(ids)) {
			return;
		}
		if (trim($('#newpasswd').val()) != trim($('#rnewpasswd').val())) {
			$('#msgerr').text('两次确认密码不一致');
			return;
		}
		if (trim($('#soldpasswd').val()) == trim($('#newpasswd').val())) {
			$('#msgerr').text('原密码和新密码相同');
			return;
		}
		$.ajax({
			url : "${contextPath}/auth/changepwd_save.htm",
			data : $("#editForm").serialize() + "&oldpasswd=" + hex_md5(trim($('#soldpasswd').val())) + "&password="
					+ hex_md5(trim($('#newpasswd').val())),
			cache : false,
			success : function(xmlHttp) {
				var res = xmlHttp;
				if (res.errorMessage != "") {
					showmsg(0, res.errorMessage);
				} else {
					showmsg(1, "密码修改成功");
					window.setTimeout("location.reload()", 1500);
				}
			}
		});
	}
</script>