<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.base.calen.SimpleCalendar"%>
<%@ page import="com.sino.ams.system.dict.dto.EtsFlexValueSetDTO"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<title></title>
</head>
<%
	SimpleCalendar cal = new SimpleCalendar();
	long accessTime = cal.getTimeInMillis(); 
	EtsFlexValueSetDTO dictionary = (EtsFlexValueSetDTO)request.getAttribute("FIRST_DICT");
%>
<frameset framespacing="0" border="0" frameborder="0" cols="150,*">
	<frame name="contents" target="right" src="/servlet/com.sino.ams.system.dict.servlet.DictionaryTreeServlet?act=<%=WebActionConstant.TREE_ACTION%>&accessTime=<%=accessTime%>" scrolling="no">
	<frame name="right" src="/servlet/com.sino.ams.system.dict.servlet.EtsFlexValuesServlet?act=<%=WebActionConstant.QUERY_ACTION%>&flexValueSetId=<%=dictionary.getFlexValueSetId()%>&flexValueSetName=<%=dictionary.getName()%>&accessTime=<%=accessTime%>" scrolling="auto">
	<noframes>
	<body>

	<p>此网页使用了框架，但您的浏览器不支持框架。</p>

	</body>
	</noframes>
</frameset>
</html>
