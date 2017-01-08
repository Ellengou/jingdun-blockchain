<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<link href="${contextPath}/resources/web/css/util.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resources/web/js/humane/themes/jackedup.css" rel="stylesheet" type="text/css" />
<script src="${contextPath}/resources/web/js/humane/humane.js" type="text/javascript"></script>
<script src="${contextPath}/resources/web/js/kjutils.js"></script>
<script src="${contextPath}/resources/web/js/my.js"></script>
<script src="${contextPath}/resources/assets/js/jquery-latest.js"></script>
<script src="${contextPath}/resources/assets/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/assets/js/theme.js"></script>
