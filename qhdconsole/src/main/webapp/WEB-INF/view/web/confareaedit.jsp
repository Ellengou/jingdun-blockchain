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
						<form id="editForm" modelAttribute="confarea" action="" method="post" class="form-horizontal inline-input">
							<input type="hidden" id="id" name="id" value="${confarea.id}" />
							<fieldset>
								<legend>编辑地区级地区表</legend>
								<div class="control-group">
									<label class="control-label" for="focusedInput">地区id：</label>
									<div class="controls">
										<input id="areaid" name="areaid" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${confarea.areaid}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">地区名：</label>
									<div class="controls">
										<input id="areaname" name="areaname" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="60" value="${confarea.areaname}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">上一级辖区id：</label>
									<div class="controls">
										<input id="fatherid" name="fatherid" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${confarea.fatherid}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">排序号：</label>
									<div class="controls">
										<input id="orderby" name="orderby" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20" value="${confarea.orderby}" />
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
		var ids = [ 'areaid','areaname','fatherid','orderby' ];
		if (check_required(ids)) {
			return;
		}
		ajax_get("${contextPath}/confarea/confareasave.htm", $("#editForm").serialize(), function(data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
				window.setTimeout("location.href='${contextPath}/confarea/confarealist.htm'", 1500);
			}
		});
	}
</script>