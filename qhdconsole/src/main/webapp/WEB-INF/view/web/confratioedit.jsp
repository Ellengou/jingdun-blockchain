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
						<form id="editForm" modelAttribute="confratio" action="" method="post" class="form-horizontal inline-input">
							<input type="hidden" id="ratioid" name="ratioid" value="${confratio.ratioid}" />
							<fieldset>
								<legend>编辑汇率表</legend>
								<div class="control-group">
									<label class="control-label" for="focusedInput">日期，如：20160410：</label>
									<div class="controls">
										<input id="sdate" name="sdate" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${confratio.sdate}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">汇率：</label>
									<div class="controls">
										<input id="ratio" name="ratio" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${confratio.ratio}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">需求类型：0-CNY；1-USD：</label>
									<div class="controls">
										<input id="stype" name="stype" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${confratio.stype}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">支付费用类型：0-CNY；1-USD：</label>
									<div class="controls">
										<input id="intype" name="intype" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${confratio.intype}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">汇率来源url：</label>
									<div class="controls">
										<input id="sfrom" name="sfrom" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="200" value="${confratio.sfrom}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">更新时间：</label>
									<div class="controls">
										<input id="getdate" name="getdate" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${confratio.getdate}" />
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
		var ids = [ 'sdate','ratio','stype','intype','sfrom','getdate', ];
		if (check_required(ids)) {
			return;
		}
		ajax_get("${contextPath}/confratio/confratiosave.htm", $("#editForm").serialize(), function(data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
				window.setTimeout("location.href='${contextPath}/confratio/confratiolist.htm'", 1500);
			}
		});
	}
</script>