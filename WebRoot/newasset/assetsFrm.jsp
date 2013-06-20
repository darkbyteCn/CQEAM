<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<html>

<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<title>资产台账管理</title>
</head>
<%
	String treeCategory = (String)request.getAttribute(AssetsWebAttributes.TREE_CATEGORY);
	String transferType = (String)request.getAttribute(AssetsWebAttributes.TRANSFER_TYPE);
	if(!treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_COMM_QUERY)){
		String mainPage = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet";
		mainPage += "?act=";
		mainPage += "&treeCategory=" + treeCategory;
%>	
<frameset rows="86,*" framespacing="0" border="0" frameborder="0" onload="window.focus()">
	<frame name="banner" scrolling="no" noresize target="contents" src="/servlet/com.sino.ams.newasset.servlet.AssetsTopServlet?treeCategory=<%=treeCategory%>&transferType=<%=transferType%>">
	<frameset cols="194,*">
		<frame name="contents" target="main" src="" scrolling="auto">
		<frame name="assetsMain" src="<%=mainPage%>" scrolling="auto">
	</frameset>
	<noframes>
	<body>

	<p>此网页使用了框架，但您的浏览器不支持框架。</p>

	</body>
	</noframes>
</frameset>	
<%
	} else {
%>
<frameset rows="86,*" framespacing="0" border="0" frameborder="0" onload="window.focus()">
	<frame name="header" scrolling="no" noresize target="assetsMain" src="/servlet/com.sino.ams.newasset.servlet.AssetsTopServlet?treeCategory=<%=treeCategory%>">
	<frame name="assetsMain" src="/servlet/com.sino.ams.newasset.servlet.CommonQueryServlet" target="_self">
	<noframes>
	<body>

	<p>此网页使用了框架，但您的浏览器不支持框架。</p>

	</body>
	</noframes>
</frameset>	
<%	
	}
%>	

</html>
