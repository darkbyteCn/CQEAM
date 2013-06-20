<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>资产分析报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String analyseType = dto.getAnalyseType();
	String reportTitle = "资产增减变动分析";
	if(analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_1)){
		reportTitle = "应用领域分析";
	} else if(analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_2)){
		reportTitle = "资产大类分析";
	} else if(analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_3)){
		reportTitle = "资产项分析";
	}
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.AssetsAnalysisServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产分析报表-->><%=reportTitle%>");
</script>

	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="15%" align="right">公司名称：</td>
			<td width="30%"><select size="1" name="organizationId" style="width:100%"><%=dto.getOrgOption()%></select></td>
			<td width="15%" align="right">会计期间：</td>
			<td width="15%"><select size="1" name="accountPeriod" style="width:100%" onChange="do_PassPeriod(this)"><%=dto.getAccPeriodOpt()%></select></td>
			<td width="20%" align="right"><img border="0" src="/images/eam_images/search.jpg" width="63" height="18" align="right" onclick="do_Search();">&nbsp;<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input type="hidden" name="act">
	<input type="hidden" name="company">
	<input type="hidden" name="analyseType" value="<%=dto.getAnalyseType()%>">
	<input type="hidden" name="assetsCreateDate" value="<%=dto.getAssetsCreateDate()%>">
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table border="1" width="140%" style="color: #FFFFFF" bgcolor="#2A79C6">
		<tr height="22">
			<td width="10%" align="center" rowspan="2">公司名称</td>
			<td width="18%" align="center" colspan="2">现有资产</td>
			<td width="18%" align="center" colspan="2">本年新增</td>
			<td width="18%" align="center" colspan="2">本月新增</td>
			<td width="18%" align="center" colspan="2">本年报废</td>
			<td width="18%" align="center" colspan="2">本月报废</td>
		</tr>
		<tr height="22">
			<td width="6%" align="center">数量</td>
			<td width="12%" align="center">金额(元)</td>
			<td width="6%" align="center">数量</td>
			<td width="12%" align="center">金额(元)</td>
			<td width="6%" align="center">数量</td>
			<td width="12%" align="center">金额(元)</td>
			<td width="6%" align="center">数量</td>
			<td width="12%" align="center">金额(元)</td>
			<td width="6%" align="center">数量</td>
			<td width="12%" align="center">金额(元)</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:330px;width:857px;position:absolute;top:90px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
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
		<tr height="22" style="cursor:hand" onclick="do_ShowDetail('<%=row.getValue("ORGANIZATION_ID")%>', '<%=row.getValue("COMPANY")%>')" title="点击查看该公司资产变动情况" style="cursor:hand">
			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("COMPANY")%>"></td>
			<td width="6%"><input type="text" class="finput3" readonly value="<%=row.getValue("TOTAL_COUNT")%>"></td>
			<td width="12%"><input type="text" class="finput3" readonly value="<%=strTotalCost%>"></td>
			<td width="6%"><input type="text" class="finput3" readonly value="<%=row.getValue("YEAR_ADD_COUNT")%>"></td>
			<td width="12%"><input type="text" class="finput3" readonly value="<%=strYearAddCost%>"></td>
			<td width="6%"><input type="text" class="finput3" readonly value="<%=row.getValue("MONTH_ADD_COUNT")%>"></td>
			<td width="12%"><input type="text" class="finput3" readonly value="<%=strMonthAddCost%>"></td>
			<td width="6%"><input type="text" class="finput3" readonly value="<%=row.getValue("YEAR_DIS_COUNT")%>"></td>
			<td width="12%"><input type="text" class="finput3" readonly value="<%=strYearDisCost%>"></td>
			<td width="6%"><input type="text" class="finput3" readonly value="<%=row.getValue("MONTH_DIS_COUNT")%>"></td>
			<td width="12%"><input type="text" class="finput3" readonly value="<%=strMonthDisCost%>"></td>
		</tr>
<%
		}
		if(userAccount.isProvinceUser()){
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
			<td width="10%"><input type="text" class="finput" readonly value="合计"></td>
			<td width="6%"><input type="text" class="finput3" readonly value="<%=sumTotalCount%>"></td>
			<td width="12%"><input type="text" class="finput3" readonly value="<%=strSumTotalCost%>"></td>
			<td width="6%"><input type="text" class="finput3" readonly value="<%=sumYearAddCount%>"></td>
			<td width="12%"><input type="text" class="finput3" readonly value="<%=strSumYearAddCost%>"></td>
			<td width="6%"><input type="text" class="finput3" readonly value="<%=sumMonthAddCount%>"></td>
			<td width="12%"><input type="text" class="finput3" readonly value="<%=strSumMonthAddCost%>"></td>
			<td width="6%"><input type="text" class="finput3" readonly value="<%=sumYearDisCount%>"></td>
			<td width="12%"><input type="text" class="finput3" readonly value="<%=strSumYearDisCost%>"></td>
			<td width="6%"><input type="text" class="finput3" readonly value="<%=sumMonthDisCount%>"></td>
			<td width="12%"><input type="text" class="finput3" readonly value="<%=strSumMonthDisCost%>"></td>
		</tr>
<%
		}
	}
%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:455px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>

</html>
<script>
function initPage(){
	do_SetPageWidth();
}

function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
    mainFrm.submit();
}

function do_PassPeriod(obj){
	mainFrm.assetsCreateDate.value = obj.value + "-01";
}

function do_ShowDetail(organizationId, company){
	var analyseType = mainFrm.analyseType.value;
	var selObj = mainFrm.organizationId;
	selectSpecialOptionByItem(selObj, organizationId);
	mainFrm.company.value = company;
	var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
	window.open("/public/waiting2.htm", "assWin", style);
	mainFrm.target = "assWin";
	mainFrm.act.value = "<%=AssetsActionConstant.DETAIL_ACTION%>";
	mainFrm.submit();
}
</script>