<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.calen.SimpleCalendar"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<title></title>
</head>
<%
	SimpleCalendar cal = new SimpleCalendar();
	long accessTime = cal.getTimeInMillis();
%>
<frameset framespacing="0" border="0" frameborder="0" cols="150,*">
	<frame name="contents" target="right" src="/servlet/com.sino.sinoflow.framework.resource.servlet.ResourceTreeServlet?act=<%=WebActionConstant.TREE_ACTION%>&accessTime=<%=accessTime%>" scrolling="yes">
	<frame name="right" src="/servlet/com.sino.sinoflow.framework.resource.servlet.SfResDefineServlet?act=<%=WebActionConstant.QUERY_ACTION%>&accessTime="<%=accessTime%>" scrolling="no">
	<noframes>
	<body>

	<p>此网页使用了框架，但您的浏览器不支持框架。</p>

	</body>
	</noframes>
</frameset>
</html>
