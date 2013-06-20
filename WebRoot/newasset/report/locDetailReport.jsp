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
	var ArrAction1 = new Array(true, "导出所有地点", "toexcel.gif", "导出所有地点", "do_Export('<%=DictConstant.EXPORT_RES_LOC%>')");
	var ArrAction2 = new Array(true, "导出已盘点地点", "toexcel.gif", "导出已盘点地点", "do_Export('<%=DictConstant.EXPORT_SCAN_LOC_Y%>')");
	var ArrAction3 = new Array(true, "导出未盘点地点", "toexcel.gif", "导出未盘点地点", "do_Export('<%=DictConstant.EXPORT_SCAN_LOC_N%>')");
	var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3);
	var ArrSinoViews = new Array();
	var ArrSinoTitles = new Array();
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 style="overflow:auto" onload="initPage();">
<%
	EtsOuCityMapDTO company = (EtsOuCityMapDTO)request.getAttribute(WebAttrConstant.OU_DTO);
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(WebAttrConstant.WORKORDER_DTO);

	String ownLocURL = "/servlet/com.sino.ams.newasset.report.servlet.OwnLocReportServlet";
	ownLocURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
	ownLocURL += "&organizationId=" + company.getOrganizationId();
	ownLocURL += "&startDate=" + dto.getStartDate();
	ownLocURL += "&endDate=" + dto.getEndDate();
	ownLocURL += "&objectCategory=" + dto.getObjectCategory();
	ownLocURL += "&exportType=" + DictConstant.EXPORT_RES_LOC;

	String scanLocURL = "/servlet/com.sino.ams.newasset.report.servlet.ScanedLocReportServlet";
	scanLocURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
	scanLocURL += "&organizationId=" + company.getOrganizationId();
	scanLocURL += "&startDate=" + dto.getStartDate();
	scanLocURL += "&endDate=" + dto.getEndDate();
	scanLocURL += "&objectCategory=" + dto.getObjectCategory();
	scanLocURL += "&exportType=" + DictConstant.EXPORT_SCAN_LOC_Y;


	String notScanLocURL = "/servlet/com.sino.ams.newasset.report.servlet.NotScanedLocRptServlet";
	notScanLocURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
	notScanLocURL += "&organizationId=" + company.getOrganizationId();
	notScanLocURL += "&startDate=" + dto.getStartDate();
	notScanLocURL += "&endDate=" + dto.getEndDate();
	notScanLocURL += "&objectCategory=" + dto.getObjectCategory();
	notScanLocURL += "&exportType=" + DictConstant.EXPORT_SCAN_LOC_N;

%>
<script language="javascript">
	var tabBox = new TabBox("tab");
	tabBox.addtab("totalLocs", "所有地点");
	tabBox.addtab("scanedLocs", "已盘点地点");
	tabBox.addtab("notScanedLocs", "未盘点地点");
	tabBox.addtab("company", "公司信息");
	printTitleBar("<%=company.getCompany()%>盘点信息(<%=dto.getObjectCategoryName()%>)");
	printToolBar();
	tabBox.init();
</script>

<table align="center" width='100%' border="0" cellpadding="2" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF" style="width:100%;height:87%">
	<tr>
		<td style="width:100%;height:100%">
			<div id="totalLocs" style='display:none'>
			<iframe id="respLocsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=ownLocURL%>"></iframe>
			</div>
			<div id="scanedLocs" style='display:none'>
			<iframe id="scanedLocsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=scanLocURL%>"></iframe>
			</div>
			<div id="notScanedLocs" style="display:none">
			<iframe id="notScanedLocsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=notScanLocURL%>"></iframe>
			</div>
			<div id="company" style='display:none'>
				<table border="1" width="100%" style="border-collapse: collapse; width:100%;height:100%" bordercolor="#245DD7" id="table1" bgcolor="#E4E4E4" cellpadding="0">
					<tr>
						<td style="width:100%;height:100%">
						    <table width="100%" border="1"  style="width:100%;height:100%" cellpadding="0" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF">
							    <tr>
								    <td width="20%" align="right" height="22">公司名称：</td>
								    <td width="80%" height="22"><%=company.getCompany()%></td>
							    </tr>
								<tr>
									<td width="20%" height="22" align="right">公司代码：</td>
									<td width="80%" height="22"><%=company.getCompanyCode()%></td>
							    </tr>
							    <tr>
								    <td width="20%" height="22" align="right">组织ID：</td>
								    <td width="80%" height="22"><%=company.getOrganizationId()%></td>
							    </tr>
							    <tr>
								    <td width="20%" height="22" align="right">账簿代码：</td>
								    <td width="80%" height="22"><%=company.getBookTypeCode()%></td>
							    </tr>
							    <tr>
									<td width="20%" height="22" align="right">账簿名称：</td>
									<td width="80%" height="22"><%=company.getBookTypeName()%></td>
								</tr>
								<tr>
									<td colspan="2" height="80%"></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.LocDtlReportServlet">
	<input type="hidden" name="startDate" value="<%=dto.getStartDate()%>">
	<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
	<input type="hidden" name="objectCategory" value="<%=dto.getObjectCategory()%>">
	<input type="hidden" name="organizationId" value="<%=company.getOrganizationId()%>">
	<input type="hidden" name="exportType" value="">
	<input type="hidden" name="act" value="">
</form>
</body>
</html>

<script language="javascript">
function initPage() {
	window.focus();
	document.all("totalLocs").style.display = "";
	document.all("scanedLocs").style.display = "none";
	document.all("notScanedLocs").style.display = "none";
	document.all("company").style.display = "none";
	tabBox.inithidetab(1);
}

function do_Export(exportType){
	mainFrm.exportType.value = exportType;
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}
</script>