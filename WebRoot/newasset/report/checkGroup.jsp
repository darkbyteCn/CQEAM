<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>盘点部门信息</title>

</head>

<body leftmargin="0" topmargin="0" rightmargin="1" background="/images/HeaderBack.png">
<%
	AmsMisDeptDTO dept = (AmsMisDeptDTO)request.getAttribute(AssetsWebAttributes.DEPT_DTO);
%>
<table border="0" width="100%" cellspacing="0" id="table1" cellpadding="0" style="color: #FFFFFF;">
	<tr>
		<td width="100%" align="center" colspan="2" height="50" valign="bottom"><font size="5"><B><%=dept.getDeptName()%>盘点情况</B></font></td>
	</tr>
</table>
<p align="center">
<a href="" onClick="do_Close(); return false"><font color="green">关  闭</font></a></p>
</body>

</html>
<script>
function do_Close(){
	window.parent.close();
}
</script>