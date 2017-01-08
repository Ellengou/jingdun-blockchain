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
<link href="${contextPath}/resources/assets/css/compiled/new-user.css" type="text/css" media="screen" rel="stylesheet" />
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<link href="${contextPath}/resources/ztree/css/demo.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet">
<script src="${contextPath}/resources/ztree/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="${contextPath}/resources/ztree/js/jquery.ztree.exedit-3.5.js" type="text/javascript"></script>
<script src="${contextPath}/resources/web/js/json2.js" type="text/javascript"></script>
<script type="text/javascript">
	var setting = {
		view : {
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom,
			selectedMulti : false
		},
		edit : {
			enable : true,
			editNameSelectAll : true,
			showRemoveBtn : showRemoveBtn,
			showRenameBtn : showRenameBtn
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeDrag : beforeDrag,
			beforeEditName : beforeEditName,
			beforeRemove : beforeRemove,
			beforeRename : beforeRename,
			onRemove : onRemove,
			onRename : onRename,
			beforeClick : beforeClick
		}
	};
	var zNodes = [];
	var log, className = "dark";
	function beforeClick(treeId, treeNode) {
		if (treeNode.level == 0) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.expandNode(treeNode);
			return false;
		}
		funFrame.location.href = "${contextPath}/auth/functionform.htm?id=" + treeNode.id;
		return true;
	}
	function beforeDrag(treeId, treeNodes) {
		return false;
	}
	function beforeEditName(treeId, treeNode) {
		className = (className === "dark" ? "" : "dark");
		showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		return true;
	}
	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "" : "dark");
		showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
	}
	function onRemove(e, treeId, treeNode) {
		ajax_get("${contextPath}/auth/removeztree", "&id=" + treeNode.id, function(data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
			}
		});
		showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	}
	function beforeRename(treeId, treeNode, newName, isCancel) {
		className = (className === "dark" ? "" : "dark");
		showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime() + " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
				+ (isCancel ? "</span>" : ""));
		if (newName.length == 0) {
			alert("节点名称不能为空.");
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			setTimeout(function() {
				zTree.editName(treeNode);
			}, 10);
			return false;
		}
		ajax_get("${contextPath}/auth/renameztree", "&id=" + treeNode.id + "&sname=" + newName, function(data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
			}
		});
		return true;
	}
	function onRename(e, treeId, treeNode, isCancel) {
		showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime() + " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
				+ (isCancel ? "</span>" : ""));
	}
	function showRemoveBtn(treeId, treeNode) {
		return true;
	}
	function showRenameBtn(treeId, treeNode) {
		return true;
	}
	function showLog(str) {
		if (!log)
			log = $("#log");
		log.append("<li class='"+className+"'>" + str + "</li>");
		if (log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	function getTime() {
		var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now.getSeconds(), ms = now.getMilliseconds();
		return (h + ":" + m + ":" + s + " " + ms);
	}
	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
			return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		if (btn) {
			btn.bind("click", function() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var spId = treeNode.id;
				var sname = "new node" + (newCount++);
				var sid = RndNum(8);
				addztree(sid, spId, sname);
				zTree.addNodes(treeNode, {
					id : sid,
					pId : spId,
					name : sname
				});
				return false;
			});
		}
	};
	function addztree(id, pId, name) {
		ajax_get("${contextPath}/auth/addztree", "&id=" + id + "&pid=" + pId + "&sname=" + name, function(data) {
			if (data.errorMessage) {
				showmsg(0, data.errorMessage);
			} else {
				showmsg(1, "处理成功!");
			}
		});
	}
	function RndNum(n) {
		var rnd = "";
		for (var i = 0; i < n; i++)
			rnd += Math.floor(Math.random() * 10);
		return rnd;
	}
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	};
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
	}
	$(document).ready(function() {
		var s = $('#hinitnodes').val();
		if (s != "") {
			s = s.replace(/\'/g, "\"");
			zNodes = JSON.parse(s);
		}
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		$("#selectAll").bind("click", selectAll);
	});
</script>
</HEAD>
<BODY>
	<div class="content">
		<div class="container-fluid">
			<div id="pad-wrapper" class="new-user">
				<div class="span12 row-fluid form-wrapper">
					<div class="span3">
						<fieldset>
							<legend>菜单列表</legend>
							<input type="hidden" id="hinitnodes" name="hinitnodes" value="${initnodes}" />
							<div class="control-group">
								<div class="zTreeDemoBackground left controls">
									<ul id="treeDemo" class="ztree"></ul>
								</div>
							</div>
						</fieldset>
					</div>
					<div class="span9 form-sidebar pull-right">
						<iframe id="funFrame" name="funFrame" src="" style="overflow: hidden; height: 626px;" scrolling="no" frameborder="no" height="650" width="100%"></iframe>
					</div>
				</div>
			</div>
		</div>
	</div>
</BODY>
</HTML>