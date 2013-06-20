<%@ page language="java" buffer="none" contentType="text/html; charset=GBK" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.system.trust.dto.AmsMaintainCompanyDTO" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderDTO" %>

<html>
<head>
<title>代维巡检责任</title>
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
	var ArrAction1 = new Array(true, "导出责任地点", "toexcel.gif", "导出责任地点", "do_Export('<%=DictConstant.EXPORT_RES_LOC%>')");
	var ArrAction2 = new Array(true, "导出已巡检地点", "toexcel.gif", "导出已巡检地点", "do_Export('<%=DictConstant.EXPORT_SCAN_LOC_Y%>')");
	var ArrAction3 = new Array(true, "导出未巡检地点", "toexcel.gif", "导出未巡检地点", "do_Export('<%=DictConstant.EXPORT_SCAN_LOC_N%>')");
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
    
	AmsMaintainCompanyDTO mainCompany = (AmsMaintainCompanyDTO)request.getAttribute(WebAttrConstant.MAINTAIN_CORP_ATTR);
	EtsWorkorderDTO dto = (EtsWorkorderDTO)request.getAttribute(WebAttrConstant.WORKORDER_DTO);
	Row row = null;
	
	String resLocURL = "/servlet/com.sino.ams.workorder.servlet.ResponsibleLocServlet";
	resLocURL += "?act=" + WebActionConstant.DETAIL_ACTION;
	resLocURL += "&maintainCompany=" + mainCompany.getCompanyId();
	resLocURL += "&startDate=" + dto.getStartDate();
	resLocURL += "&endDate=" + dto.getEndDate();
	resLocURL += "&exportType=" + DictConstant.EXPORT_RES_LOC;
	
	String scanLocURL = "/servlet/com.sino.ams.workorder.servlet.MaintainScanedLocServlet";
	scanLocURL += "?act=" + WebActionConstant.DETAIL_ACTION;
	scanLocURL += "&maintainCompany=" + mainCompany.getCompanyId();
	scanLocURL += "&startDate=" + dto.getStartDate();
	scanLocURL += "&endDate=" + dto.getEndDate();
	scanLocURL += "&exportType=" + DictConstant.EXPORT_SCAN_LOC_Y;

	
	String notScanLocURL = "/servlet/com.sino.ams.workorder.servlet.MaintainNotScanedLocServlet";
	notScanLocURL += "?act=" + WebActionConstant.DETAIL_ACTION;
	notScanLocURL += "&maintainCompany=" + mainCompany.getCompanyId();
	notScanLocURL += "&startDate=" + dto.getStartDate();
	notScanLocURL += "&endDate=" + dto.getEndDate();
	notScanLocURL += "&exportType=" + DictConstant.EXPORT_SCAN_LOC_N;
	
%>
<script language="javascript">
	var tabBox = new TabBox("tab");
	tabBox.addtab("respLocs", "责任地点");
	tabBox.addtab("scanedLocs", "已巡检地点");
	tabBox.addtab("notScanedLocs", "未巡检地点");
	tabBox.addtab("mainCompany", "代维公司");
	printTitleBar("<%=mainCompany.getName()%>代维信息");
	printToolBar();
	tabBox.init();
</script>

<table align="center" width='100%' border="0" cellpadding="2" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF" style="width:100%;height:87%">
	<tr>
		<td style="width:100%;height:100%">
			<div id="respLocs" style='display:none'>
			<iframe id="respLocsFrm" style="width:100%;height:100%" frameborder="1" src="<%=resLocURL%>"></iframe>
			</div>
			<div id="scanedLocs" style='display:none'>
			<iframe id="scanedLocsFrm" style="width:100%;height:100%" frameborder="1" src="<%=scanLocURL%>"></iframe>
			</div>
			<div id="notScanedLocs" style="display:none">
			<iframe id="notScanedLocsFrm" style="width:100%;height:100%" frameborder="1" src="<%=notScanLocURL%>"></iframe>
			</div>
			<div id="mainCompany" style='display:none'>
				<table border="1" width="100%" style="border-collapse: collapse; width:100%;height:100%" bordercolor="#245DD7" id="table1" bgcolor="#E4E4E4" cellpadding="0">
					<tr>
						<td style="width:100%;height:100%">
						    <table width="100%" border="1"  style="width:100%;height:100%" cellpadding="0" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF">
								<tr>
									<td width="20%" align="right" height="22">公司名称：</td>
									<td width="80%" height="22"><%=mainCompany.getName()%></td>
								</tr>
								<tr>
									<td width="20%" height="22" align="right">公司地址：</td>
									<td width="80%" height="22"><%=mainCompany.getAddress()%></td>
								</tr>
								<tr>
									<td width="20%" height="22" align="right">法人代表：</td>
									<td width="80%" height="22"><%=mainCompany.getLegalRepresentative()%></td>
								</tr>
								<tr>
									<td width="20%" height="22" align="right">办公电话：</td>
									<td width="80%" height="22"><%=mainCompany.getOfficeTelephone()%></td>
								</tr>
								<tr>
									<td width="20%" height="22" align="right">联系人姓名：</td>
									<td width="80%" height="22"><%=mainCompany.getContactPeople()%></td>
								</tr>
								<tr>
									<td width="20%" height="22" align="right">联系人电话：</td>
									<td width="80%" height="22"><%=mainCompany.getContactTelephone()%></td>
								</tr>
								<tr>
									<td width="20%" height="22" align="right">传真号码：</td>
									<td width="80%" height="22"><%=mainCompany.getFaxNumber()%></td>
								</tr>
								<tr>
									<td width="20%" height="22" align="right">备注信息：</td>
									<td width="80%" height="22"><%=mainCompany.getRemark()%></td>
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
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.workorder.servlet.LocResponReportServlet">
	<input type="hidden" name="startDate" value="<%=dto.getStartDate()%>">
	<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
	<input type="hidden" name="maintainCompany" value="<%=mainCompany.getCompanyId()%>">
	<input type="hidden" name="exportType" value="">
	<input type="hidden" name="act" value="">
</form>
</body>
</html>

<script language="javascript">
function initPage() {
	window.focus();
	document.all("respLocs").style.display = "";
	document.all("scanedLocs").style.display = "none";
	document.all("notScanedLocs").style.display = "none";
	document.all("mainCompany").style.display = "none";
	tabBox.inithidetab(1);
}

function do_ShowScanInfo(objectNo){
    var screenHeight = window.screen.height;
    var screenWidth = window.screen.width;
	var url = "/servlet/com.sino.ams.workorder.servlet.ItemResponReportServlet?act=<%=WebActionConstant.DETAIL_ACTION%>";
	url += "&maintainCompany=" + mainFrm.maintainCompany.value;
	url += "&startDate=" + mainFrm.startDate.value;
	url += "&endDate=" + mainFrm.endDate.value;
	url += "&workorderObjectNo=" + objectNo;
	var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,help=no,status=no,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,center=yes";
    window.open(url, "itemScanWin", winstyle);
}

function do_Export(exportType){
	mainFrm.exportType.value = exportType;
	mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}
</script>