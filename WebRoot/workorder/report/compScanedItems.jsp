<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.constant.*" %>


<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
	<script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>

<title>公司已巡检设备</title>
</head>

<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    RowSet respItems = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:1px;left:1px;width:990px">
	<table class="headerTable" border="1" width="140%">
		<tr>
			<td width="10%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">标签号</td>
			<td width="8%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">设备分类</td>
			<td width="10%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">设备名称</td>
			<td width="10%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">设备型号</td>
			<td width="6%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">单位</td>
			<td width="28%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">所在地点</td>
			<td width="6%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">责任人</td>
			<td width="22%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">责任部门</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:90%;width:1007px;position:absolute;top:23px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(respItems != null && !respItems.isEmpty()){
		for(int i = 0; i < respItems.getSize(); i++){
			row = respItems.getRow(i);
%>					
		<tr onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
			<td width="10%" height="22"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("BARCODE")%>"></td>
			<td width="8%" height="22"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ITEM_CATEGORY_NAME")%>"></td>
			<td width="10%" height="22"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
			<td width="10%" height="22"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
			<td width="6%" height="22"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("ITEM_UNIT")%>"></td>
			<td width="28%" height="22"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
			<td width="6%" height="22"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("USER_NAME")%>"></td>
			<td width="22%" height="22"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
		</tr>
<%
		}
	}
%>					
	</table>
</div>
<div style="position:absolute;top:564px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>

</body>

</html>
