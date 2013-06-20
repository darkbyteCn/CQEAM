<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/left.css">
</head>
<body leftmargin="0" rightmargin="0" topmargin="0">
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);    
%>
<table border="0" width="100%" height="100%" cellpadding="0" style="border-collapse: collapse;color:azure;background-image:url(/images/main/bg_17.jpg);background-repeat: repeat;background-position: left top;">
    <tr>
<%
	try {
		String menuTree = (String) request.getAttribute(WebAttrConstant.MENU_RESOURCE);
		if (StrUtil.isEmpty(menuTree)) {
%>
			<td><a href="" onClick="do_Refresh();">如果长时间没有响应，请点击此处刷新</a></td>
<%
		} else {
%>
        <td height="300"><%=menuTree%></td>
<%
		}
	} catch (Exception e) {
%>
        <td bgcolor="#EFEFEF"><a href="" onClick="do_Refresh();">如果长时间没有响应，请点击此处刷新</a></td>
<%
	}
%>
    </tr>
</table>
</body>
</html>
<script>
function do_Refresh() {
	parent.location.reload();
}
</script>
