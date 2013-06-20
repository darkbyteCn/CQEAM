<%@ page import="com.sino.soa.common.MessagePrint" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<body>
<% 
	MessagePrint message=(MessagePrint)request.getAttribute("MESSAGEPRINT");
%>
<div id="$$$publicMessage$$$" style="position:absolute; bottom:45%; left:5; z-index:10; visibility:visible">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="20%"></td>
			<td bgcolor="#36786C">
				<table width="100%" height="90" border="0" cellspacing="2" cellpadding="0">
					<tr  height="30">
						<td bgcolor="#eeeeee" align="left"><font color="#0000FF" size="4"><b>系统提示：</b></font></td>
					</tr>
					<tr height="60">
					<%	if(message.isSuccess()){
					 %>
						<td bgcolor="#eeeeee" align="center"><a href="#" onClick="do_ControlURL()"><font size="2" color="green"><%=message.getMessageValue() %></font></a></td>
					<%
						}else{
					 %>
					 <td bgcolor="#eeeeee" align="center"><a href="#" onClick="do_ControlURL()"><font size="2" color="red"><%=message.getMessageValue() %></font></a></td>
					<%
						}
					 %>
					</tr>
				</table>
			</td>
			<td width="20%"></td>
		</tr>
	</table>
</div>
</body>
<script language="javascript">
function do_ControlURL(){
	document.getElementById("$$$publicMessage$$$").style.display = "none";
}
</script>
</html>