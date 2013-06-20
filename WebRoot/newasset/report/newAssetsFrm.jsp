<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="/WebLibary/css/tab.css">
	<script language="javascript" src="/WebLibary/js/tab.js"></script>
<script language="javascript">
	var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "window.parent.close()");
	var ArrAction1 = new Array(true, "导出新增资产报表", "toexcel.gif", "导出新增资产报表", "do_Export(1)");
	var ArrAction2 = new Array(true, "导出扫描资产报表", "toexcel.gif", "导出扫描资产报表", "do_Export(2)");
	var ArrAction3 = new Array(true, "导出帐实一致报表", "toexcel.gif", "导出帐实一致报表", "do_Export(3)");
    var ArrAction4 = new Array(true, "导出帐实不符报表", "toexcel.gif", "导出帐实不符报表", "do_Export(4)");
    var ArrAction5 = new Array(true, "导出未扫描资产报表", "toexcel.gif", "导出未扫描资产报表", "do_Export(5)");
    var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3, ArrAction4, ArrAction5);
	var ArrSinoViews = new Array();
	var ArrSinoTitles = new Array();
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 style="overflow:auto" onload="do_initPage();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String startDate = dto.getStartDate().getCalendarValue();
	String endDate = dto.getEndDate().getCalendarValue();
	String companyName = dto.getCompanyName();

	String commonURL = "";
	commonURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
	commonURL += "&organizationId=" + dto.getOrganizationId();
	commonURL += "&companyName=" + companyName;
	commonURL += "&startDate=" + startDate;
	commonURL += "&endDate=" + endDate;

	String checkResultURL1 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsReportServlet";
	checkResultURL1 += commonURL;

	String checkResultURL2 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsScanedServlet";
	checkResultURL2 += commonURL;

	String checkResultURL3 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsIdenticalServlet";
	checkResultURL3 += commonURL;

    String checkResultURL4 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsDifferentServlet";
	checkResultURL4 += commonURL;

    String checkResultURL5 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsNotScanedServlet";
	checkResultURL5 += commonURL;
    String pageTitle = "";
	if(startDate.equals("") && endDate.equals("")){
		pageTitle = "截至今天" + companyName + "创建资产盘点情况";
	} else if(!startDate.equals("") && endDate.equals("")){
		pageTitle = startDate + "之后" + companyName + "创建资产盘点情况";
	} else if(startDate.equals("") && !endDate.equals("")){
		pageTitle = endDate + "之前" + companyName + "创建资产盘点情况";
	} else if(!startDate.equals("") && !endDate.equals("")){
		pageTitle = startDate + "和" + endDate + "之间" + companyName + "创建资产盘点情况";
	}
%>
<script language="javascript">
	var tabBox = new TabBox("tab");
	tabBox.addtab("checkResult1", "新增资产");
	tabBox.addtab("checkResult2", "扫描资产");
	tabBox.addtab("checkResult3", "帐实一致");
    tabBox.addtab("checkResult4", "帐实不符");
    tabBox.addtab("checkResult5", "未扫描资产");
    printTitleBar("<%=pageTitle%>");
	printToolBar();
	tabBox.init();
</script>

<table align="center" width='100%' border="0" cellpadding="2" cellspacing="0" bordercolor="#666666" bordercolordark="#FFFFFF" style="width:100%;height:89%">
	<tr>
		<td style="width:100%;height:100%">
			<div id="checkResult1" style='display:none'>
				<iframe id="checkResultFrm1" style="width:100%;height:100%" border="0" frameborder="0" src="<%=checkResultURL1%>"></iframe>
			</div>
			<div id="checkResult2" style="display:none">
				<iframe id="checkResultFrm2" style="width:100%;height:100%" border="0" frameborder="0" src="<%=checkResultURL2%>"></iframe>
			</div>
			<div id="checkResult3" style="display:none">
				<iframe id="checkResultFrm3" style="width:100%;height:100%" border="0" frameborder="0" src="<%=checkResultURL3%>"></iframe>
			</div>
            <div id="checkResult4" style="display:none">
				<iframe id="checkResultFrm4" style="width:100%;height:100%" border="0" frameborder="0" src="<%=checkResultURL4%>"></iframe>
			</div>
            <div id="checkResult5" style="display:none">
				<iframe id="checkResultFrm5" style="width:100%;height:100%" border="0" frameborder="0" src="<%=checkResultURL5%>"></iframe>
			</div>
        </td>
	</tr>
</table>
<form name="mainFrm" method="post" action="">
	<input type="hidden" name="organizationId" value="<%=dto.getOrganizationId()%>">
	<input type="hidden" name="startDate" value="<%=startDate%>">
	<input type="hidden" name="endDate" value="<%=endDate%>">
	<input type="hidden" name="companyName" value="<%=companyName%>">
	<input type="hidden" name="act" value="<%=AssetsActionConstant.EXPORT_ACTION%>">
</form>
</body>
</html>

<script language="javascript">
function do_initPage() {
	window.focus();
	document.all("checkResult1").style.display = "block";
	document.all("checkResult2").style.display = "none";
	document.all("checkResult3").style.display = "none";
    document.all("checkResult4").style.display = "none";
    document.all("checkResult5").style.display = "none";
    tabBox.inithidetab(1);
}

function do_Export(exportType){
	var strDiv = "";
	var tabName = "";
	var strFrm = "";
	var action = "";
	if(exportType == "1"){
		strDiv = "checkResult1";
		strFrm = "checkResultFrm1";
		tabName = "新增资产";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsReportServlet"
	} else if(exportType == "2"){
		strDiv = "checkResult2";
		strFrm = "checkResultFrm2";
		tabName = "扫描资产";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsScanedServlet"
	} else if(exportType == "3"){
		strDiv = "checkResult3";
		strFrm = "checkResultFrm3";
		tabName = "帐实一致";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsIdenticalServlet"
	} else if(exportType == "4"){
		strDiv = "checkResult4";
		strFrm = "checkResultFrm4";
		tabName = "帐实不符";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsDifferentServlet"
	} else if(exportType == "5"){
		strDiv = "checkResult5";
		strFrm = "checkResultFrm5";
		tabName = "未扫描资产";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsNotScanedServlet"
	}
	tabName += "选项卡";
	var divStyle = document.all(strDiv).style.display;
	if(divStyle == "none"){
		alert("“" + tabName + "”没有处于活动状态，不能执行指定导出操作");
		return;
	}
	var wind = window.frames[strFrm];
	var subFrm = wind.document.mainFrm;
	document.mainFrm.action = action;
	document.mainFrm.submit();
}
</script>