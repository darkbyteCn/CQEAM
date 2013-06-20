<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.calen.SimpleCalendar"%>
<%@ page import="com.sino.sinoflow.framework.resource.dto.SfResDefineDTO"%>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<title></title>
</head>
<%
	SimpleCalendar cal = new SimpleCalendar();
	long accessTime = cal.getTimeInMillis();
	SfResDefineDTO resource = (SfResDefineDTO)request.getAttribute(WebAttrConstant.FIRST_RESOURCE);
%>
<frameset framespacing="0" border="0" frameborder="0" cols="200,*">
	<frame name="contents" target="right" src="/servlet/com.sino.sinoflow.framework.resource.servlet.ResPriviTreeServlet?act=<%=WebActionConstant.TREE_ACTION%>&accessTime=<%=accessTime%>" scrolling="yes">
	<frame name="right" src="/servlet/com.sino.sinoflow.framework.resource.servlet.SfResPrivsServlet?resId=<%=resource.getResId()%>&accessTime=<%=accessTime%>" scrolling="no">
	<noframes>
	<body>

	<p>此网页使用了框架，但您的浏览器不支持框架。</p>

	</body>
	</noframes>
</frameset>
</html>
