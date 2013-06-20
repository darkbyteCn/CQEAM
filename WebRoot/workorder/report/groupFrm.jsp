<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderDTO" %>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<title>部门巡检详细信息</title>
</head>
<%
	EtsWorkorderDTO dto = (EtsWorkorderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String act = dto.getAct();
	String groupId = dto.getGroupId();
	String groupURL = "/servlet/com.sino.ams.workorder.servlet.ScanGroupServlet?act=" + act;
	groupURL += "&groupId=" + groupId;
	String locationURL = "/servlet/com.sino.ams.workorder.servlet.GroupLocationsServlet?act=" + act;
	locationURL += "&groupId=" + groupId;
	locationURL += "&startDate=" + dto.getStartDate();
	locationURL += "&endDate=" + dto.getEndDate();
	String itemURL = "/servlet/com.sino.ams.workorder.servlet.GroupItemsServlet?act=" + act;
	itemURL += "&groupId=" + groupId;
	itemURL += "&workorderObjectNo=" + dto.getWorkorderObjectNo();
	itemURL += "&workorderObjectLocation=" + dto.getWorkorderObjectLocation();
	itemURL += "&startDate=" + dto.getStartDate();
	itemURL += "&endDate=" + dto.getEndDate();
%>
<frameset rows="86,*" framespacing="0" border="0" frameborder="0">
	<frame name="banner" scrolling="no" noresize target="contents" src="<%=groupURL%>">
	<frameset cols="350,*">
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