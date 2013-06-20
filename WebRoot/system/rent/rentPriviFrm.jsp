<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.system.user.dto.SfRoleDTO" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes" %>

<%
    SfRoleDTO firstRole = (SfRoleDTO) request.getAttribute(AssetsWebAttributes.PRIVI_ROLE);
    String roleId = firstRole.getRoleId();
    String roleName = firstRole.getRolename();
//    System.out.println("roleName"+roleName);
    String topURL = "/servlet/com.sino.ams.system.rent.servlet.RentTopServlet?roleId=" + roleId + "&roleName=" + roleName;
    String leftURL = "/servlet/com.sino.ams.system.rent.servlet.RentPriviLeftServlet?roleId=" + roleId + "&roleName=" + roleName;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
</head>
<frameset rows="86,*" framespacing="0" border="0" frameborder="0" onload="window.focus();">
	<frame name="banner" scrolling="no" noresize="noresize" target="contents" src="<%=topURL%>">
	<frameset cols="320,*" >
		<frame name="contents" target="priviMain" src="<%=leftURL%>">
		<frame name="priviMain" src="" scrolling="auto">
	</frameset>
	<noframes>
	<body>
	<p>此网页使用了框架，但您的浏览器不支持框架。</p>
	</body>
	</noframes>
</frameset>
</html>
