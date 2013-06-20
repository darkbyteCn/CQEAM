<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>成本中心已扫描设备</title>
</head>

<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<%
    RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:0px;left:1px;width:990px">
	<table class="headerTable" border="1" width="160%">
		<tr height="20">
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="8%">标签号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="8%">设备分类</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="8%">设备名称</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="8%">设备型号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="8%">启用日期</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="10%">地点代码</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="16%">地点简称</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="7%">责任人</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="7%">员工号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="14%">责任部门</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="6%">使用人</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:506px;width:1007px;position:absolute;top:21px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="160%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(rows!= null && !rows.isEmpty()){
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
			<tr onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
				<td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
				<td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_CATEGORY_NAME")%>"></td>
				<td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
				<td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
				<td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("START_DATE")%>"></td>
				<td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
				<td width="16%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
				<td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("USER_NAME")%>"></td>
				<td width="7%"><input type="text" class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
				<td width="14%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
				<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_MAINTAIN_USER")%>"></td>
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
