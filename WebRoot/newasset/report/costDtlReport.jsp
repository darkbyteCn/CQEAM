<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="/WebLibary/css/tab.css">
	<script language="javascript" src="/WebLibary/js/tab.js"></script>
<title>盘点情况</title>
<script language="javascript">
	var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "window.parent.close()");
	var ArrAction1 = new Array(true, "导出现有资产", "toexcel.gif", "导出现有资产", "do_Export('<%=AssetsDictConstant.OWN_ITEM%>')");
	var ArrAction2 = new Array(true, "导出已盘点设备", "toexcel.gif", "导出已盘点设备", "do_Export('<%=AssetsDictConstant.SCAN_ITEM_Y%>')");
	var ArrAction3 = new Array(true, "导出未盘点资产", "toexcel.gif", "导出未盘点资产", "do_Export('<%=AssetsDictConstant.SCAN_ITEM_N%>')");
	var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3);
	var ArrSinoViews = new Array();
	var ArrSinoTitles = new Array();
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 style="overflow:auto" onload="initPage();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String ownAssetsURL = "/servlet/com.sino.ams.newasset.report.servlet.CostOwnAssetsServlet";
	ownAssetsURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
	ownAssetsURL += "&costCode=" + dto.getCostCode();
	ownAssetsURL += "&startDate=" + dto.getStartDate();
	ownAssetsURL += "&endDate=" + dto.getEndDate();
	ownAssetsURL += "&exportType=" + AssetsDictConstant.OWN_ITEM;

	String scanItemURL = "/servlet/com.sino.ams.newasset.report.servlet.CostScanedItemsServlet";
	scanItemURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
	scanItemURL += "&costCode=" + dto.getCostCode();
	scanItemURL += "&startDate=" + dto.getStartDate();
	scanItemURL += "&endDate=" + dto.getEndDate();
	scanItemURL += "&exportType=" + AssetsDictConstant.SCAN_ITEM_Y;


	String notScanedAssetsURL = "/servlet/com.sino.ams.newasset.report.servlet.CostNotScanedAssetsServlet";
	notScanedAssetsURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
	notScanedAssetsURL += "&costCode=" + dto.getCostCode();
	notScanedAssetsURL += "&startDate=" + dto.getStartDate();
	notScanedAssetsURL += "&endDate=" + dto.getEndDate();
	notScanedAssetsURL += "&exportType=" + AssetsDictConstant.SCAN_ITEM_N;
%>
<script language="javascript">
	var tabBox = new TabBox("tab");
	tabBox.addtab("ownAssets", "现有资产");
	tabBox.addtab("scanItems", "已盘点设备");
	tabBox.addtab("notScanedAssets", "未盘点资产");
	tabBox.addtab("costCenter", "成本中心");
	printTitleBar("成本中心<%=dto.getCostName()%>盘点信息");
	printToolBar();
	tabBox.init();
</script>

<table align="center" width='100%' border="0" cellpadding="2" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF" style="width:100%;height:87%">
	<tr>
		<td style="width:100%;height:100%">
			<div id="ownAssets" style='display:none'>
			<iframe id="ownAssetsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=ownAssetsURL%>"></iframe>
			</div>
			<div id="scanItems" style='display:none'>
			<iframe id="scanItemsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=scanItemURL%>"></iframe>
			</div>
			<div id="notScanedAssets" style="display:none">
			<iframe id="notScanedAssetsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=notScanedAssetsURL%>"></iframe>
			</div>
			<div id="costCenter" style='display:none'>
				<table border="1" width="100%" style="border-collapse: collapse; width:100%;height:100%" bordercolor="#245DD7" id="table1" bgcolor="#E4E4E4" cellpadding="0">
					<tr>
						<td style="width:100%;height:100%">
							<table width="100%" border="1"  style="width:100%;height:100%" cellpadding="0" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF">
								<tr>
									<td width="20%" align="right" height="22">成本中心代码：</td>
									<td width="80%" height="22"><%=dto.getCostCode()%></td>
								</tr>
								<tr>
									<td width="20%" height="22" align="right">成本中心名称：</td>
									<td width="80%" height="22"><%=dto.getCostName()%></td>
								</tr>
								<tr>
									<td colspan="2" height="90%"></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CostDiffDtlReportServlet">
	<input type="hidden" name="costCode" value="<%=dto.getCostCode()%>">
	<input type="hidden" name="costName" value="<%=dto.getCostName()%>">
	<input type="hidden" name="startDate" value="<%=dto.getStartDate()%>">
	<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
	<input type="hidden" name="exportType" value="">
	<input type="hidden" name="act" value="">
</form>
</body>
</html>

<script language="javascript">
function initPage() {
	window.focus();
	document.all("ownAssets").style.display = "";
	document.all("scanItems").style.display = "none";
	document.all("notScanedAssets").style.display = "none";
	document.all("costCenter").style.display = "none";
	tabBox.inithidetab(1);
}

function do_Export(exportType){
	mainFrm.exportType.value = exportType;
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}
</script>
