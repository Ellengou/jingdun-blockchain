<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.yuyoukj.ao.model.Page" required="true"%>
<%@ attribute name="pageNoName" type="java.lang.String" required="false"%>
<%
if (page != null && page.getTotalPages() > 1) {
%>
<div class="pagination pull-right">
	<ul>
		<%
			if (pageNoName == null) {
				pageNoName = "pageNo";
			}
				
			String pageParam = page.getParamUrl();
			if (pageParam != null) {
				String regex = pageNoName + "\\=[\\d]*\\&";
				pageParam = pageParam.replaceAll(regex, "");
			}
			
			int count = 8;
			
			if (page.getPageNo() > page.getTotalPages()) {
				page.setPageNo(page.getTotalPages());
			}
			
			int begin = page.getPageNo() - count / 2;
			if (begin < 1) {
				begin = 1;
			}
			
			int end = page.getPageNo() + count / 2;
			if (end > page.getTotalPages()) {
				end = page.getTotalPages();
			}
			
			if (page.getPageNo() - 1 >= 1) {
				%>
		<li>
			<a href='?<%=pageNoName%>=<%=page.getPrePage()%>&<%=pageParam%>'>&#8249;</a>
		</li>
		<%
			}else{
				%>
		<li>
			<a href='?<%=pageNoName%>=1&<%=pageParam%>'>&#8249;</a>
		</li>
		<%
			}
			
			for (int i = begin; i <= end; i++) {
				if (i == page.getPageNo()) {
					%>
		<li class="active">
			<a href='?<%=pageNoName%>=<%=i%>&<%=pageParam%>'><%=i%></a>
		</li>
		<%
				} else {
					%>
		<li>
			<a href='?<%=pageNoName%>=<%=i%>&<%=pageParam%>'><%=i%></a>
		</li>
		<%
				}
			}
			
			if (page.getPageNo() + 1 <= page.getTotalPages()) {
				%>
		<li>
			<a href='?<%=pageNoName%>=<%=page.getNextPage()%>&<%=pageParam%>'>&#8250;</a>
		</li>
		<%
			}else{
				%>
		<li>
			<a href='?<%=pageNoName%>=<%=page.getTotalPages()%>&<%=pageParam%>'>&#8250;</a>
		</li>
		<%
			}
		%>
	</ul>
	<span><%=page.getPageNo()%>/<%=page.getTotalPages()%></span>
</div>
<%
}
%>
