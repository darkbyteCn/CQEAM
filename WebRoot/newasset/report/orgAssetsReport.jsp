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
	AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String analyseType = dto.getAnalyseType();
	String reportTitle = "";
	if(analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_1)){
		reportTitle = "应用领域";
	} else if(analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_2)){
		reportTitle = "资产大类";
	} else if(analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_3)){
		reportTitle = "资产项";
	}
	RowSet rows = (RowSet) request.getAttribute(AssetsWebAttributes.REPORT_DATA);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<table border="0" width="100%" background="/images/HeaderBack.png" height="76">
	<tr>
		<td width="100%" align="center"><span style="letter-spacing: 3pt; vertical-align: middle"><b><font color="#FFFFFF" size="4"><%=dto.getCompany()%><p><%=dto.getAccountPeriod()%>资产变动分析报表(<%=reportTitle%>)</font></b></span></td>
	</tr>
</table>
<div id="headDiv" style="overflow:hidden;position:absolute;top:75px;left:1px;width:840px">
	<table border="1" bordercolor="#336699" width="100%">
		<tr height="22">
			<td width="20%" align="center" rowspan="2"><%=reportTitle%></td>
			<td width="16%" align="center" colspan="2">现有资产</td>
			<td width="16%" align="center" colspan="2">本年新增</td>
			<td width="16%" align="center" colspan="2">本月新增</td>
			<td width="16%" align="center" colspan="2">本年报废</td>
			<td width="16%" align="center" colspan="2">本月报废</td>
		</tr>
		<tr height="22">
			<td width="5%" align="center">数量</td>
			<td width="11%" align="center">金额(元)</td>
			<td width="5%" align="center">数量</td>
			<td width="11%" align="center">金额(元)</td>
			<td width="5%" align="center">数量</td>
			<td width="11%" align="center">金额(元)</td>
			<td width="5%" align="center">数量</td>
			<td width="11%" align="center">金额(元)</td>
			<td width="5%" align="center">数量</td>
			<td width="11%" align="center">金额(元)</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:500px;width:857px;position:absolute;top:120px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#336699" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		NumberFormat currFormat = NumberFormat.getCurrencyInstance();
		double totalCost = 0;
		double yearAddCost = 0;
		double monthAddCost = 0;
		double yearDisCost = 0;
		double monthDisCost = 0;
		
		String strTotalCost = "";
		String strYearAddCost = "";
		String strMonthAddCost = "";
		String strYearDisCost = "";
		String strMonthDisCost = "";
		
		int sumTotalCount = 0;
		int sumYearAddCount = 0;
		int sumMonthAddCount = 0;
		int sumYearDisCount = 0;
		int sumMonthDisCount = 0;

		double sumTotalCost = 0;
		double sumYearAddCost = 0;
		double sumMonthAddCost = 0;
		double sumYearDisCost = 0;
		double sumMonthDisCost = 0;

		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);

			totalCost = Double.parseDouble(row.getStrValue("TOTAL_COST"));
			yearAddCost = Double.parseDouble(row.getStrValue("YEAR_ADD_COST"));
			monthAddCost = Double.parseDouble(row.getStrValue("MONTH_ADD_COST"));
			yearDisCost = Double.parseDouble(row.getStrValue("YEAR_DIS_COST"));
			monthDisCost = Double.parseDouble(row.getStrValue("MONTH_DIS_COST"));

			strTotalCost = currFormat.format(totalCost);
			strTotalCost = strTotalCost.substring(1);

			strYearAddCost = currFormat.format(yearAddCost);
			strYearAddCost = strYearAddCost.substring(1);

			strMonthAddCost = currFormat.format(monthAddCost);
			strMonthAddCost = strMonthAddCost.substring(1);

			strYearDisCost = currFormat.format(yearDisCost);
			strYearDisCost = strYearDisCost.substring(1);

			strMonthDisCost = currFormat.format(monthDisCost);
			strMonthDisCost = strMonthDisCost.substring(1);

			sumTotalCount += Integer.parseInt(row.getStrValue("TOTAL_COUNT"));
			sumYearAddCount += Integer.parseInt(row.getStrValue("YEAR_ADD_COUNT"));
			sumMonthAddCount += Integer.parseInt(row.getStrValue("MONTH_ADD_COUNT"));
			sumYearDisCount += Integer.parseInt(row.getStrValue("YEAR_DIS_COUNT"));
			sumMonthDisCount += Integer.parseInt(row.getStrValue("MONTH_DIS_COUNT"));

			sumTotalCost += totalCost;
			sumYearAddCost += yearAddCost;
			sumMonthAddCost += monthAddCost;
			sumYearDisCost += yearDisCost;
			sumMonthDisCost += monthDisCost;
%>
		<tr height="22">
			<td width="20%"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY")%>"></td>
			<td width="5%"><input type="text" class="finput3" readonly value="<%=row.getValue("TOTAL_COUNT")%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=strTotalCost%>"></td>
			<td width="5%"><input type="text" class="finput3" readonly value="<%=row.getValue("YEAR_ADD_COUNT")%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=strYearAddCost%>"></td>
			<td width="5%"><input type="text" class="finput3" readonly value="<%=row.getValue("MONTH_ADD_COUNT")%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=strMonthAddCost%>"></td>
			<td width="5%"><input type="text" class="finput3" readonly value="<%=row.getValue("YEAR_DIS_COUNT")%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=strYearDisCost%>"></td>
			<td width="5%"><input type="text" class="finput3" readonly value="<%=row.getValue("MONTH_DIS_COUNT")%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=strMonthDisCost%>"></td>
		</tr>
<%
		}
		String strSumTotalCost = currFormat.format(sumTotalCost);
		strSumTotalCost = strSumTotalCost.substring(1);

		String strSumYearAddCost = currFormat.format(sumYearAddCost);
		strSumYearAddCost = strSumYearAddCost.substring(1);

		String strSumMonthAddCost = currFormat.format(sumMonthAddCost);
		strSumMonthAddCost = strSumMonthAddCost.substring(1);

		String strSumYearDisCost = currFormat.format(sumYearDisCost);
		strSumYearDisCost = strSumYearDisCost.substring(1);

		String strSumMonthDisCost = currFormat.format(sumMonthDisCost);
		strSumMonthDisCost = strSumMonthDisCost.substring(1);
%>
		<tr height="22">
			<td width="20%"><input type="text" class="finput" readonly value="合计"></td>
			<td width="5%"><input type="text" class="finput3" readonly value="<%=sumTotalCount%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=strSumTotalCost%>"></td>
			<td width="5%"><input type="text" class="finput3" readonly value="<%=sumYearAddCount%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=strSumYearAddCost%>"></td>
			<td width="5%"><input type="text" class="finput3" readonly value="<%=sumMonthAddCount%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=strSumMonthAddCost%>"></td>
			<td width="5%"><input type="text" class="finput3" readonly value="<%=sumYearDisCount%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=strSumYearDisCost%>"></td>
			<td width="5%"><input type="text" class="finput3" readonly value="<%=sumMonthDisCount%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=strSumMonthDisCost%>"></td>
		</tr>
<%
	}
%>
	</table>
</div>
<div style="position:absolute;top:630px;left:450px; right:20px">
<a href="" onClick="do_Export(); return false"><img src="/images/eam_images/export.jpg" border="0"></a>&nbsp;&nbsp
<a href="" onClick="self.close(); return false"><img src="/images/eam_images/close.jpg" border="0"></a>
</div>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.OrgAssetsExportServlet">
<input type="hidden" name="organizationId" value="<%=dto.getOrganizationId()%>">
<input type="hidden" name="company" value="<%=dto.getCompany()%>">
<input type="hidden" name="accountPeriod" value="<%=dto.getAccountPeriod()%>">
<input type="hidden" name="analyseType" value="<%=dto.getAnalyseType()%>">
<input type="hidden" name="act" value="<%=AssetsActionConstant.EXPORT_ACTION%>">
<input type="hidden" name="assetsCreateDate" value="<%=dto.getAssetsCreateDate()%>">
</form>
</body>

</html>
<script>
function initPage(){
	window.focus();
	do_SetPageWidth();
}

function do_Export() {
    mainFrm.submit();
}
</script>