<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.system.user.dto.EtsOuCityMapDTO" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderDTO" %>
<html>
<head>
<title>公司巡检详细信息</title>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/tab.css">
<script language="javascript" src="/WebLibary/js/tab.js"></script>
<link href="/WebLibary/css/css.css" rel="stylesheet" type="text/css">
<link href="/WebLibary/css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
<script language="javascript">
	var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "window.parent.close()");
	var ArrAction1 = new Array(true, "导出公司设备", "toexcel.gif", "导出公司设备", "do_Export('<%=DictConstant.OWN_ITEM%>')");
	var ArrAction2 = new Array(true, "导出已巡检设备", "toexcel.gif", "导出已巡检设备", "do_Export('<%=DictConstant.SCAN_ITEM_Y%>')");
	var ArrAction3 = new Array(true, "导出未巡检设备", "toexcel.gif", "导出未巡检设备", "do_Export('<%=DictConstant.SCAN_ITEM_N%>')");
	var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3);
	var ArrSinoViews = new Array();
	var ArrSinoTitles = new Array();
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 style="overflow:auto" onload="initPage();">
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    
	EtsOuCityMapDTO company = (EtsOuCityMapDTO)request.getAttribute(WebAttrConstant.OU_DTO);
	EtsWorkorderDTO dto = (EtsWorkorderDTO)request.getAttribute(WebAttrConstant.WORKORDER_DTO);
	Row row = null;
	String ownItemURL = "/servlet/com.sino.ams.workorder.servlet.CompOwnItemServlet";
	ownItemURL += "?act=" + WebActionConstant.DETAIL_ACTION;
	ownItemURL += "&organizationId=" + dto.getOrganizationId();
	ownItemURL += "&itemCategory=" + dto.getItemCategory();
	ownItemURL += "&exportType=" + DictConstant.OWN_ITEM;
	ownItemURL += "&statDate=" + dto.getStartDate();
	ownItemURL += "&endDate=" + dto.getEndDate();
	
	
	String scanItemURL = "/servlet/com.sino.ams.workorder.servlet.CompScanItemServlet";
	scanItemURL += "?act=" + WebActionConstant.DETAIL_ACTION;
	scanItemURL += "&organizationId=" + dto.getOrganizationId();
	scanItemURL += "&itemCategory=" + dto.getItemCategory();	
	scanItemURL += "&exportType=" + DictConstant.SCAN_ITEM_Y;
	scanItemURL += "&statDate=" + dto.getStartDate();
	scanItemURL += "&endDate=" + dto.getEndDate();
	
	String notScanItemURL = "/servlet/com.sino.ams.workorder.servlet.CompNotScanItemServlet";
	notScanItemURL += "?act=" + WebActionConstant.DETAIL_ACTION;
	notScanItemURL += "&organizationId=" + dto.getOrganizationId();
	notScanItemURL += "&itemCategory=" + dto.getItemCategory();	
	notScanItemURL += "&exportType=" + DictConstant.SCAN_ITEM_N;
	notScanItemURL += "&statDate=" + dto.getStartDate();
	notScanItemURL += "&endDate=" + dto.getEndDate();
	
	
%>
<script language="javascript">
	var tabBox = new TabBox("tab");
	tabBox.addtab("respItems", "公司设备");
	tabBox.addtab("scanedItems", "已巡检设备");
	tabBox.addtab("notScanedItems", "未巡检设备");
	tabBox.addtab("company", "公司信息");
	printTitleBar("<%=company.getCompany()%>巡检信息");
	printToolBar();
	tabBox.init();
</script>

<table align="center" style="width:100%;height:87%" border="0" cellpadding="2" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF">
	<tr>
		<td style="width:100%;height:100%">
			<div id="respItems" style="display:none;width:100%;height:100%">
			<iframe id="respItemsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=ownItemURL%>"></iframe>
			</div>
			<div id="scanedItems" style="display:none;width:100%;height:100%">
			<iframe id="scanedItemsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=scanItemURL%>"></iframe>
			</div>
			<div id="notScanedItems" style="display:none;width:100%;height:100%">
			<iframe id="notScanedItemsFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=notScanItemURL%>"></iframe>
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
									<td width="20%" height="22" align="right">资产账簿代码：</td>
									<td width="80%" height="22"><%=company.getBookTypeCode()%></td>
								</tr>
								<tr>
									<td width="20%" height="22" align="right">资产账簿名称：</td>
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
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.workorder.servlet.CompItemDetailReportServlet">
	<input type="hidden" name="startDate" value="<%=dto.getStartDate()%>">
	<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
	<input type="hidden" name="itemCategory" value="<%=dto.getItemCategory()%>">
	<input type="hidden" name="organizationId" value="<%=company.getOrganizationId()%>">
	<input type="hidden" name="company" value="<%=company.getCompany()%>">
	<input type="hidden" name="exportType" value="">
	<input type="hidden" name="act" value="">
</form>
</body>
</html>
<script language="javascript">
function initPage() {
	window.focus();
	document.all("respItems").style.display = "";
	document.all("scanedItems").style.display = "none";
	document.all("notScanedItems").style.display = "none";
	document.all("company").style.display = "none";
	tabBox.inithidetab(1);
}

function do_Export(exportType){
	mainFrm.exportType.value = exportType;
	mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}
</script>