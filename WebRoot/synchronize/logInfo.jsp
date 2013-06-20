<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2008-11-19
  Time: 10:51:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.flow.constant.ReqAttributeList" %>
<%@ page import="com.sino.base.util.StrUtil" %>

<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
	<title>查阅错误日志</title>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/flow/flowTitleBar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
<script language="javascript">
	var ArrAction0 = new Array(true, "关闭", "close.gif", "关闭", "do_Close()");
	var ArrActions = new Array(ArrAction0);
	var ArrSinoViews = new Array();
	var ArrSinoTitles = new Array();
	printToolBar();
</script>
</head>
<body  leftmargin="0" topmargin="0">
<script>
    printTitleBar("查阅错误日志");
</script>
<div id="headDiv" style="overflow:hidden;position:absolute;top:48px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
		<tr class="headTR" height="20">
			<td align=center width="10%" height="20">处理类型</td>
			<td align=center width="10%" height="20">资产编号</td>
			<td align=center width="40%" height="20">日志信息</td>
			<td align=center width="10%" height="20">状态</td>
			<td align=center width="10%" height="20">参考ID</td>
			<td align=center width="15%" height="20">同步日期</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:100%;width:857px;position:absolute;top:70px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all" >
<%
	RowSet rs = (RowSet) request.getAttribute("DATA");
	if (rs != null && !rs.isEmpty()) {
		for (int i = 0; i < rs.getSize(); i++) {
			Row row = rs.getRow(i);
%>
		<tr height="20">
			<td align=center width="10%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("TRANSACTION_TYPE")%>"></td>
			<td align=center width="10%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ASSET_ID")%>"></td>
			<td align=center width="40%" class="AutoNewline"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("ERROR_MSG")%>"></td>
			<td align=center width="10%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("STATUS")%>"></td>
			<td align=center width="10%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("REF_ID")%>"></td>
			<td align=center width="15%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("CREATION_DATE")%>"></td>
		</tr>
<%
		}
	}
%>
	</table>
</div>
</body>
</html>
<script type="text/javascript">
function do_Close(){
	self.close();
}
</script>