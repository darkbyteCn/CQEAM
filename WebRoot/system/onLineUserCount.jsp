<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@page import="com.sino.framework.security.servlet.SessionHandle"%>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css"/>
<body topmargin="0" leftmargin="0" >
<script language="javascript">
function showUsers(){
	window.open( "/system/onLineUser.jsp" );
} 
function doRefresh(){
	location.href="/system/onLineUserCount.jsp";
} 
</script>
<table width="100%" align="center" border="0">
	<tr>
		<td  align="left" valign="middle" height="25">
			<font style="font-size: 12px;">当前共&nbsp;<a href="javascript:void(0)" style="text-decoration:underline" onclick="showUsers();return false;" title="点这里看详细列表" target="_blank"><font color="red" size="2"><%=SessionHandle.getOnLineUserCount()%></font></a>&nbsp;用户在线，<a href="javascript:void(0);" onclick="doRefresh();return false;" style="text-decoration:underline" >刷新</a>&nbsp;<a href="javascript:void(0);" style="text-decoration:underline" onclick="showUsers();return false;" >查看详细</a></font> 
		</td>
	</tr>
</table>
</body> 