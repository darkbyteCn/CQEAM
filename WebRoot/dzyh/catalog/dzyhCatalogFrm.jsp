<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.calen.SimpleCalendar"%>
<%@ page import="com.sino.ams.dzyh.dto.EamDhCatalogSetDTO"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<title></title>
</head>
<%
	SimpleCalendar cal = new SimpleCalendar();
	long accessTime = cal.getTimeInMillis();
	EamDhCatalogSetDTO eamDhSet = (EamDhCatalogSetDTO)request.getAttribute("FIRST_DZYH");
%>
<frameset framespacing="0" border="0" frameborder="0" cols="150,*">
	<frame name="contents" target="right" src="/servlet/com.sino.ams.dzyh.servlet.EamDhCatalogTreeServlet?act=<%=WebActionConstant.TREE_ACTION%>&accessTime=<%=accessTime%>" scrolling="no">
	<frame name="right" src="/servlet/com.sino.ams.dzyh.servlet.EamDhCatalogValuesServlet?act=<%=WebActionConstant.QUERY_ACTION%>&catlogSetId=<%=eamDhSet.getCatlogSetId()%>&setName=<%=eamDhSet.getSetName()%>&accessTime=<%=accessTime%>" scrolling="auto">
	<noframes>
	<body>

	<p>此网页使用了框架，但您的浏览器不支持框架。</p>

	</body>
	</noframes>
</frameset>
</html>
