<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
	<script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
<title>代维公司的责任地点</title>
</head>

<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<%
    RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:0px;left:1px;width:990px">
	<table class="headerTable" border="1" width="500%">
		<tr height="20">
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="34%" colspan="11">公共信息</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="32%" colspan="10">系统信息　　</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="32%" colspan="10">盘点信息</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%" colspan="2">状态</td>
		</tr>
		<tr height="20">
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">标签号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">计量单位</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="1%">年限</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">启用日期</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">地点代码</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="5%">地点简称</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="5%">所在位置</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">所在区县</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="5%">项目名称</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">项目编号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">资产类型</td>

			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">设备分类</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">设备名称</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">设备型号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">厂商名称</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">厂商代码</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">责任部门</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">责任人</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">员工号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">使用部门</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">使用人</td>

			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">设备分类</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">设备名称</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">设备型号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">厂商名称</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">厂商代码</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">责任部门</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">责任人</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">员工号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">使用部门</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="2%">使用人</td>

			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="1%">系统</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="1%">盘点</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:86%;width:1007px;position:absolute;top:41px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="500%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(rows!= null && !rows.isEmpty()){
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
			<tr onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
				<td width="2%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_UNIT")%>"></td>
				<td width="1%"><input type="text" class="finput3" readonly value="<%=row.getValue("YEARS")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("START_DATE")%>"></td>
				<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
				<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
				<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("COUNTY_NAME")%>"></td>
				<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("PROJECT_NUMBER")%>"></td>
				<td width="2%"><input type="text" class="finput" readonly value="<%=row.getValue("FINANCE_PROP_VALUE")%>"></td>

				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_CATEGORY_NAME")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("VENDOR_NAME")%>"></td>
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("VENDOR_NUMBER")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("RESPONSIBILITY_DEPT_NAME")%>"></td>
				<td width="2%"><input type="text" class="finput" readonly value="<%=row.getValue("RESPONSIBILITY_USER_NAME")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("MAINTAIN_DEPT_NAME")%>"></td>
				<td width="2%"><input type="text" class="finput" readonly value="<%=row.getValue("MAINTAIN_USER")%>"></td>
				
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_ITEM_CATEGORY_NAME")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_ITEM_NAME")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_ITEM_SPEC")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_VENDOR_NAME")%>"></td>
				<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_VENDOR_NUMBER")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_RESPONSIBILITY_DEPT_NAME")%>"></td>
				<td width="2%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_RESPONSIBILITY_USER_NAME")%>"></td>
				<td width="2%"><input type="text" class="finput2" readonly value="<%=row.getValue("SCAN_EMPLOYEE_NUMBER")%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_MAINTAIN_DEPT_NAME")%>"></td>
				<td width="2%"><input type="text" class="finput" readonly value="<%=row.getValue("SCAN_MAINTAIN_USER")%>"></td>

				<td width="1%"><input type="text" class="finput2" readonly value="<%=row.getValue("SYS_STATUS")%>"></td>
				<td width="1%"><input type="text" class="finput2" readonly value="<%=row.getValue("SCAN_STATUS")%>"></td>
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
