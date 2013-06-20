<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.sino.appbase.help.dto.HelpDTO"%>
<html>
<head>
    <meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
    <title></title>
</head>
<%
    HelpDTO resource = (HelpDTO) request.getAttribute(com.sino.ams.constant.WebAttrConstant.RESOURCE_DATA);
%>
<!-- <iframe frameborder="0" src="/system/help/A.mht" style=" WIDTH:100%;height:100%;"></iframe> -->
<frameset framespacing="5" border="5" frameborder="5">
	<!-- <frame name="right_b" src="/system/help/<%//=request.getAttribute("helpCode")%>"> -->
	<frame name="right_b" src="/system/help/helpSearch.jsp?helpCode=<%=request.getAttribute("helpCode")%>&helpKeyName=<%=request.getAttribute("helpKeyName")%>&treeId=<%=request.getAttribute("treeId")%>">
	<noframes>
	<body>
	<p>此网页使用了框架，但您的浏览器不支持框架。</p>
	</body>
	</noframes>
</frameset>
</html>
