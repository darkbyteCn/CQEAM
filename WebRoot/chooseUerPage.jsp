<%@ page contentType="text/html;charset=GBK" language="java"
	isErrorPage="true"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>
<%@ page import="com.sino.base.data.Row"%>
<%@ page import="com.sino.base.data.RowSet"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.base.constant.calen.DateConstant"%>
<%@ page import="com.sino.framework.security.dto.*"%>
<%@ page import="com.sino.framework.security.bean.SessionUtil"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@ page import="com.sino.framework.dto.BaseUserDTO"%>
<%@ page import="com.sino.ams.constant.URLDefineList"%>
<%@page import="java.util.List"%>
<%@page import="com.sino.ams.system.user.dto.SfUserDTO"%>

<html>
	<head>
		<title>—°‘Ò“≥√Ê</title>
	</head>
	<%
		List<SfUserDTO> userList = (List<SfUserDTO>) request
				.getAttribute("listUser");
	%>
	<body>
			<table width="492" height="73" border="0">
				<%
					for (int i = 0; i < userList.size(); i++) {
						SfUserDTO userDto=userList.get(i);
				%>
				<tr>
					<form method="post" id="mainFram<%=i %>" action="/servlet/com.sino.framework.security.servlet.UserLoginServlet">
						<td>
							<a href="javascript:doSubmit('mainFram<%=i %>')"><%=userDto.getLoginName() %></a>
							<input type="hidden" name="loginName" id="loginName" value="<%=userDto.getLoginName() %>">
							<input type="hidden" name="password" id="password" value="<%=userDto.getPassword() %>">
							<input type="hidden" name="userType" value="sso">
						</td>
					</form>
				</tr>
				<%
					}
				%>
			</table>
	</body>
	<script type="text/javascript">
		function doSubmit(obj)
		{
			document.getElementById(obj).submit();
		}
	</script>
</html>