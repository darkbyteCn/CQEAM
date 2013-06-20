<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO"%>
<%@ page import="com.sino.base.constant.db.QueryConstant"%>

<html>
<head>
    <meta http-equiv="Content-Language" content="en-us">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <%
	EtsObjectDTO dtoPara = (EtsObjectDTO)request.getAttribute(QueryConstant.QUERY_DTO);
%>
</head>
<body topmargin="0" leftmargin="0" onkeydown="autoExeFunction('do_Search()');" bgcolor="#36786C" background="/images/HeaderSlice.jpg">
<form method="post" name="mainFrm" action="/servlet/com.sino.ams.assets.servlet.AssignLocationServlet">
	<table border="0" width="100%" id="table1" style="border-collapse: collapse; color:#FFFFFF"  bgcolor="#36786C">
		<tr>
			<td width="15%" align="right" height="22">编号：</td>
			<td width="35%" align="right" height="22">
			<input type="text" name="workorderObjectCode" size="20" style="width:100%" value="<%=dtoPara.getWorkorderObjectCode()%>"></td>
			<td width="15%" align="right" height="22">名称：</td>
			<td width="35%" align="right" height="22">
			<input type="text" name="workorderObjectName" size="20" style="width:100%" value="<%=dtoPara.getWorkorderObjectName()%>"></td>
		</tr>
		<tr>
			<td width="50%" align="center" colspan="2">请选择<span lang="zh-cn">地点</span>：</td>
			<td width="50%" align="center" colspan="2">
			<img src="/images/eam_images/search.jpg" title="点击查询人员" onClick="do_Search()"></td>
		</tr>
	</table>
	<table border="0" width="100%" id="table2">
		<tr>
			<td width="100%" align="right" height="250">
			<select size="25" name="assignedToLocation" style="width:100%;height:100%"><%=request.getAttribute(AssetsWebAttributes.ASSIGN_LOCATIONS)%></select>　
			</td>
		</tr>
	</table>
<input type="hidden" name="act">	
</form>
</body>
</html>
<script>
function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}
</script>
