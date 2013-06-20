<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>成本中心自有资产</title>
</head>

<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<%
    RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:0px;left:0px;width:990px">
	<table class="headerTable" border="1" width="310%">
		<tr height="22">
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">资产条码</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">应用领域</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="13%">资产类别</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">类别代码</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="5%">资产名称</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="5%">资产型号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">资产数量</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">计量单位</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">地点代码</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="13%">资产地点</td>

			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">员工编号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">责任人</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">项目编号</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="7%">项目名称</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">原始成本</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">当前成本</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">折旧年限</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">创建日期</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="4%">启用日期</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">累计折旧</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">资产净值</td>
			<td align=center background="/images/bg_011.gif" style="color: #FFFFFF" width="3%">资产残值</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:506px;width:1007px;position:absolute;top:20px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="310%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(rows!= null && !rows.isEmpty()){
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
			<tr onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
				<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
		        <td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY1")%>"></td>
		        <td width="13%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY2")%>"></td>
		        <td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("SEGMENT2")%>"></td>
		        <td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
		        <td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
		        <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("CURRENT_UNITS")%>"></td>
		        <td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("UNIT_OF_MEASURE")%>"></td>
		        <td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>
		        <td width="13%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>

		        <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSIGNED_TO_NUMBER")%>"></td>
		        <td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
		        <td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("MIS_PROJECT_NUMBER")%>"></td>
		        <td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
		        <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("ORIGINAL_COST")%>"></td>
		        <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("COST")%>"></td>
		        <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("LIFE_IN_YEARS")%>"></td>
		        <td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSETS_CREATE_DATE")%>"></td>
		        <td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
		        <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("DEPRN_RESERVE")%>"></td>
		        <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("DEPRN_COST")%>"></td>
		        <td width="3%"><input type="text" class="finput3" readonly value="<%=row.getValue("SCRAP_VALUE")%>"></td>
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
