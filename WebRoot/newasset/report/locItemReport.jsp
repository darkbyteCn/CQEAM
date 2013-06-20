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
	var ArrAction1 = new Array(true, "导出当前地点设备", "toexcel.gif", "导出当前地点设备", "do_Export('<%=DictConstant.EXPORT_LOC_ITEM%>')");
	var ArrAction2 = new Array(true, "导出已盘点设备", "toexcel.gif", "导出已盘点设备", "do_Export('<%=DictConstant.EXPORT_SCAN_ITEM%>')");
	var ArrAction3 = new Array(true, "导出数据差异", "toexcel.gif", "导出数据差异", "do_Export('<%=DictConstant.EXPORT_DIFF_ITEM%>')");
	var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3);
	var ArrSinoViews = new Array();
	var ArrSinoTitles = new Array();
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 style="overflow:auto" onload="initPage();">
<%
	EtsOuCityMapDTO company = (EtsOuCityMapDTO)request.getAttribute(WebAttrConstant.OU_DTO);
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(WebAttrConstant.WORKORDER_DTO);
	String ownItemURL = "/servlet/com.sino.ams.newasset.report.servlet.OwnItemReportServlet";
	ownItemURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
	ownItemURL += "&organizationId=" + company.getOrganizationId();
	ownItemURL += "&checkLocation=" + dto.getCheckLocation();
	ownItemURL += "&startDate=" + dto.getStartDate();
	ownItemURL += "&endDate=" + dto.getEndDate();
	ownItemURL += "&objectCategory=" + dto.getObjectCategory();
	ownItemURL += "&exportType=" + DictConstant.EXPORT_LOC_ITEM;

	String scanItemURL = "/servlet/com.sino.ams.newasset.report.servlet.ScanedItemReportServlet";
	scanItemURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
	scanItemURL += "&organizationId=" + company.getOrganizationId();
	scanItemURL += "&checkLocation=" + dto.getCheckLocation();
	scanItemURL += "&startDate=" + dto.getStartDate();
	scanItemURL += "&endDate=" + dto.getEndDate();
	scanItemURL += "&objectCategory=" + dto.getObjectCategory();
	scanItemURL += "&exportType=" + DictConstant.EXPORT_SCAN_ITEM;


	String diffItemURL = "/servlet/com.sino.ams.newasset.report.servlet.DiffItemRptServlet";
	diffItemURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
	diffItemURL += "&organizationId=" + company.getOrganizationId();
	diffItemURL += "&checkLocation=" + dto.getCheckLocation();
	diffItemURL += "&startDate=" + dto.getStartDate();
	diffItemURL += "&endDate=" + dto.getEndDate();
	diffItemURL += "&objectCategory=" + dto.getObjectCategory();
	diffItemURL += "&exportType=" + DictConstant.EXPORT_DIFF_ITEM;
%>
<script language="javascript">
	var tabBox = new TabBox("tab");
	tabBox.addtab("ownItems", "当前地点设备");
	tabBox.addtab("scanItems", "已盘点设备");
	tabBox.addtab("diffItems", "数据差异");
	tabBox.addtab("company", "公司信息");
	printTitleBar("<%=company.getCompany()%>盘点信息(<%=dto.getObjectName()%>)");
	printToolBar();
	tabBox.init();
</script>

<table align="center" width='100%' border="0" cellpadding="2" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF" style="width:100%;height:87%">
	<tr>
		<td style="width:100%;height:100%">
			<div id="ownItems" style='display:none'>
			<iframe id="respLocsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=ownItemURL%>"></iframe>
			</div>
			<div id="scanItems" style='display:none'>
			<iframe id="scanItemsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=scanItemURL%>"></iframe>
			</div>
			<div id="diffItems" style="display:none">
			<iframe id="diffItemsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=diffItemURL%>"></iframe>
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
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.LocItemReportServlet">
	<input type="hidden" name="startDate" value="<%=dto.getStartDate()%>">
	<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
	<input type="hidden" name="objectCategory" value="<%=dto.getObjectCategory()%>">
	<input type="hidden" name="checkLocation" value="<%=dto.getCheckLocation()%>">
	<input type="hidden" name="organizationId" value="<%=dto.getOrganizationId()%>">
	<input type="hidden" name="objectName" value="<%=dto.getObjectName()%>">
	<input type="hidden" name="exportType" value="">
	<input type="hidden" name="act" value="">
</form>
</body>
</html>

<script language="javascript">
function initPage() {
	window.focus();
	document.all("ownItems").style.display = "";
	document.all("scanItems").style.display = "none";
	document.all("diffItems").style.display = "none";
	document.all("company").style.display = "none";
	tabBox.inithidetab(1);
}

function do_Export(exportType){
	mainFrm.exportType.value = exportType;
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}
</script>
