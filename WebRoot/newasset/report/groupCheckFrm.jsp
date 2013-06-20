<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<title>部门盘点详细信息</title>
</head>
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String act = dto.getAct();
	String checkDept = dto.getCheckDept();
	String groupURL = "/servlet/com.sino.ams.newasset.report.servlet.CheckGroupServlet?act=" + act;
	groupURL += "&deptCode=" + checkDept;

	String locationURL = "/servlet/com.sino.ams.newasset.report.servlet.GroupCheckedLocServlet?act=" + act;
	locationURL += "&checkDept=" + checkDept;
	locationURL += "&startDate=" + dto.getStartDate();
	locationURL += "&endDate=" + dto.getEndDate();

	String itemURL = "/servlet/com.sino.ams.newasset.report.servlet.GroupCheckedItemServlet?act=" + act;
	itemURL += "&checkDept=" + checkDept;
	itemURL += "&checkLocation=" + dto.getCheckLocation();
	itemURL += "&checkLocationName=" + dto.getCheckLocationName();
	itemURL += "&startDate=" + dto.getStartDate();
	itemURL += "&endDate=" + dto.getEndDate();
%>
<frameset rows="86,*" framespacing="0" border="0" frameborder="0" onload="window.focus()">
	<frame name="banner" scrolling="no" noresize target="contents" src="<%=groupURL%>">
	<frameset cols="365,*">
		<frame name="contents" target="main" src="<%=locationURL%>">
		<frame name="main" scrolling="auto" src="<%=itemURL%>">
	</frameset>
	<noframes>
	<body>

	<p>此网页使用了框架，但您的浏览器不支持框架。</p>

	</body>
	</noframes>
</frameset>
</html>