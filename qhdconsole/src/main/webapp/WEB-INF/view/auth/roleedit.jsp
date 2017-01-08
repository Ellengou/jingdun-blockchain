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
<link href="${contextPath}/resources/ztree/css/demo.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet">
<script src="${contextPath}/resources/ztree/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="${contextPath}/resources/ztree/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
<script src="${contextPath}/resources/web/js/json2.js" type="text/javascript"></script>
<script type="text/javascript">
	var setting = {
		view : {
			selectedMulti : false
		},
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeCheck : beforeCheck,
			onCheck : onCheck
		}
	};
	var zNodes = [];
	var code, log, className = "dark";
	function beforeCheck(treeId, treeNode) {
		className = (className === "dark" ? "" : "dark");
		return (treeNode.doCheck !== false);
	}
	function onCheck() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = zTree.getCheckedNodes();
		var nodesid = '';
		for (var i = 0; i < nodes.length; i++) {
			nodesid += nodes[i].id + ',';
		}
		$('#chknodes').val(nodesid);
	}
	function showLog(str) {
		if (!log)
			log = $("#log");
		log.append("<li class='"+className+"'>" + str + "</li>");
		if (log.children("li").length > 6) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	$(document).ready(function() {
		var s = $('#hinitnodes').val();
		if (s != "") {
			s = s.replace(/\'/g, "\"");
			zNodes = JSON.parse(s);
		}
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		onCheck();
	});
</script>
</head>
<body>
	<div class="content">
		<div class="container-fluid">
			<div id="pad-wrapper" class="new-user">
				<div class="span9" id="content">
					<div class="row-fluid">
						<form id="editForm" modelAttribute="role" action="" method="post" class="form-horizontal inline-input">
							<input type="hidden" id="chknodes" name="chknodes" value="" />
							<input type="hidden" name="rid" id="rid" value="${role.rid}">
							<fieldset>
								<legend>编辑角色</legend>
								<div class="control-group">
									<label class="control-label" for="focusedInput">角色名称：</label>
									<div class="controls">
										<input id="sname" name="sname" class="input-xlarge focused inline-input" placeholder="" id="focusedInput" type="text" maxlength="20"
											value="${role.sname}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">备注信息：</label>
									<div class="controls">
										<input id="remarks" name="remarks" class="input-xlarge focused inline-input" id="focusedInput" placeholder="" type="text" maxlength="20"
											value="${role.remarks}" />
										<span class="red ml5">*</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="focusedInput">菜单管理：</label>
									<input type="hidden" id="hinitnodes" name="hinitnodes" value="${initnodes}" />
									<div class="controls">
										<ul id="treeDemo" class="ztree"></ul>
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
		if ($('#sname').val() == "") {
			showmsg(0, "请输入角色名");
			return;
		}
		ajax_get("${contextPath}/auth/rolesave.htm", $("#editForm").serialize(), function(data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
				window.setTimeout("location.href='${contextPath}/auth/rolelist.htm'", 1500);
			}
		});
	}
</script>