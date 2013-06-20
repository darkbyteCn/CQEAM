<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>资产分析报表</title>
 </head>
<body leftmargin="0" topmargin="0" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<%
	AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(AssetsWebAttributes.REPORT_DATA);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<table border="0" width="100%" background="/images/HeaderBack.png" height="76">
	<tr>
		<td width="100%" align="center"><span style="letter-spacing: 3pt; vertical-align: middle"><b><font color="#FFFFFF" size="4">截止<%=dto.getEndDate()%><br><%=dto.getCompanyName()%>盘点情况</font></b></span></td>
	</tr>
</table>
<div id="headDiv" style="overflow:hidden;position:absolute;top:75px;left:1px;width:990px">
	<table border="1" bordercolor="#336699" width="100%">
		<tr height="22">
			<td width="16%" align="center">成本中心代码</td>
			<td width="24%" align="center">成本中心描述</td>
			<td width="15%" align="center">MIS资产数量</td>
			<td width="15%" align="center">已盘点数量</td>
			<td width="15%" align="center">未盘点数量</td>
			<td width="15%" align="center">盘点百分比</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:544px;width:1007px;position:absolute;top:98px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#336699" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
			<td width="16%"><input type="text" class="finput2" readonly value="<%=row.getValue("COST_CENTER_CODE")%>"></td>
			<td width="24%"><input type="text" class="finput" readonly value="<%=row.getValue("COST_CENTER_NAME")%>"></td>
			<td width="15%"><input type="text" class="finput3" readonly value="<%=row.getValue("TOTAL_COUNT")%>"></td>
			<td width="15%"><input type="text" class="finput3" readonly value="<%=row.getValue("SCANED_COUNT")%>"></td>
			<td width="15%"><input type="text" class="finput3" readonly value="<%=row.getValue("NOT_SCANED_COUNT")%>"></td>
			<td width="15%"><input type="text" class="finput3" readonly value="<%=row.getValue("SCAN_RATE")%>"></td>
		</tr>
<%
		}
%>
		<tr height="22">
			<td width="40%" colspan="2"><input type="text" class="finput3" readonly value="合计"></td>
			<td width="15%"><input type="text" class="finput3" readonly value=""></td>
			<td width="15%"><input type="text" class="finput3" readonly value=""></td>
			<td width="15%"><input type="text" class="finput3" readonly value=""></td>
			<td width="15%"><input type="text" class="finput3" readonly value=""></td>
		</tr>
<%
	}
%>
	</table>
</div>
<div style="position:absolute;top:660px;left:450px; right:20px">
	<a href="" onClick="do_Export(); return false"><img src="/images/eam_images/export.jpg" border="0"></a>&nbsp;&nbsp
	<a href="" onClick="self.close(); return false"><img src="/images/eam_images/close.jpg" border="0"></a>
</div>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CostChkExportServlet">
	<input type="hidden" name="organizationId" value="<%=dto.getOrganizationId()%>">
	<input type="hidden" name="companyName" value="<%=dto.getCompanyName()%>">
	<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
	<input name="act" type="hidden" value="<%=AssetsActionConstant.EXPORT_ACTION%>">
</form>
</body>

</html>
<script>
function initPage(){
	window.focus();
	do_SetPageWidth();
	do_CountSummary();
}

/**
 * 功能：计算合计
 */
function do_CountSummary(){
	var hasData = <%=hasData%>;
	if(hasData){
		var tab = document.getElementById("dataTable");
		var rows = tab.rows;
		var rowCount = rows.length;
		var columnCount = rows[0].cells.length;
		var sumarryArr = new Array();
		for(var j = 0; j < columnCount - 2; j++){
			sumarryArr[j] = 0;
		}
		var trObj = null;
		var tdObj = null;
		var numObj = null;
		for(var i = 0; i < rowCount - 1; i++){
			trObj = rows[i];
			for(var j = 2; j < columnCount - 1; j++){
				tdObj = trObj.cells[j];
				numObj = tdObj.childNodes[0];
				sumarryArr[j - 2] += Number(numObj.value);
			}
		}
		trObj = rows[rowCount - 1];
		for(var j = 1; j < columnCount - 2; j++){
			tdObj = trObj.cells[j];
			numObj = tdObj.childNodes[0];
			numObj.value = formatNum(sumarryArr[j - 1], 2);
		}
		//计算百分比
		j = columnCount - 2;
		tdObj = trObj.cells[j];
		numObj = tdObj.childNodes[0];
		numObj.value = "" + formatNum(100 * sumarryArr[1]/sumarryArr[0], 2) + "%";
	}
}

function do_Export(){
	mainFrm.submit();
}
</script>