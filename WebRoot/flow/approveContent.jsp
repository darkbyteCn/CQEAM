<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.flow.constant.ReqAttributeList" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
    查阅审批意见
  Created by wwb.
  User: demo
  Date: 2006-12-21
  Time: 11:56:30
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
	<title>查阅意见</title>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script type="text/javascript"  src="/flow/flowTitleBar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<script type="text/javascript">
    printTitleBar("查阅审批意见");
	var ArrAction0 = new Array(true, "导出", "toexcel.gif", "关闭", "do_Export()");
	var ArrAction1 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close()");
	var ArrActions = new Array(ArrAction0, ArrAction1);
	var ArrSinoViews = new Array();
	var ArrSinoTitles = new Array();
	printToolBar();
</script>
</head>
<body onload="do_SetPageWidth()" leftmargin="0" topmargin="0">
<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:50px;left:1px;width:640px">
	<table class="headerTable" border="1" width="100%">
		<tr class="headerTR">
			<td align=center width="15%" height="20">审批人(办理人)</td>
            <td align=center width="20%" height="20">办理环节</td>
            <td align=center width="18%" height="20">审批时间</td>
            <td align=center width="47%" height="20">审批意见</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:88%;width:657px;position:absolute;top:73px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rs = (RowSet) request.getAttribute(ReqAttributeList.APPROVE_CONTENT_DATA);
	if (rs != null && !rs.isEmpty()) {
		String userName = "";
		String agentUser = "";
		for (int i = 0; i < rs.getSize(); i++) {
			Row row = rs.getRow(i);
			userName = row.getStrValue("USER_NAME");
			agentUser = row.getStrValue("AGENT_USER_NAME");
			if(!agentUser.equals("") && !agentUser.equals(userName)){
				userName += "(" + agentUser + ")";
			}
%>
		<tr class="dataTR">
			<td align=center width="15%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=userName%>"></td>
			<td align=center width="20%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("SFACT_TASK_NAME")%>"></td>
			<td align=center width="18%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("APPROVE_TIME")%>"></td>
			<td align=center width="47%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("APPROVE_CONTENT")%>"></td>
		</tr>
<%
		}
	}
%>
	</table>
</div>
<form name="mainFrm" action="/servlet/com.sino.flow.servlet.ApproveContentServlet" method="post">
<%
	String actId = StrUtil.nullToString(request.getParameter("actId"));
	String appId = StrUtil.nullToString(request.getParameter("appId"));
	String appTableName = StrUtil.nullToString(request.getParameter("appTableName"));
%>
	<input type="hidden" name="actId" value="<%=actId%>">
	<input type="hidden" name="appId" value="<%=appId%>">
	<input type="hidden" name="appTableName" value="<%=appTableName%>">
	<input type="hidden" name="act" value="">
</form>
</body>
</html>
<script type="text/javascript">

function do_Export(){
	mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}

function do_Close(){
	self.close();
}
</script>