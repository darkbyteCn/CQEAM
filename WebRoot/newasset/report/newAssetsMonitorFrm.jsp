<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="/WebLibary/css/tab.css">
	<script language="javascript" src="/WebLibary/js/tab.js"></script>
<script language="javascript">
	var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "window.parent.close()");
	var ArrAction1 = new Array(true, "导出MIS新增资产", "toexcel.gif", "导出MIS新增资产", "do_Export(1)");
	var ArrAction2 = new Array(true, "导出需PDA扫描确认资产报表", "toexcel.gif", "导出需PDA扫描确认资产报表", "do_Export(2)");
	var ArrAction3 = new Array(true, "导出期间实际扫描资产", "toexcel.gif", "导出期间实际扫描资产", "do_Export(3)");
    var ArrAction4 = new Array(true, "导出帐实相符资产", "toexcel.gif", "导出账实相符资产", "do_Export(4)");
    var ArrAction5 = new Array(true, "导出其中新转资扫描资产", "toexcel.gif", "导出其中新转资扫描资产", "do_Export(5)");
    
    var ArrAction6 = new Array(true, "导出已完成新转资并且需PDA扫描资产", "toexcel.gif", "导出已完成新转资并且需PDA扫描资产", "do_Export(6)");
    var ArrAction7 = new Array(true, "导出新转资尚未扫描确认资产", "toexcel.gif", "导出新转资尚未扫描确认资产", "do_Export(7)");
    var ArrAction8 = new Array(true, "导出帐实不符资产报表", "toexcel.gif", "导出帐实不符资产报表", "do_Export(8)");

    var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2, ArrAction3, ArrAction4, ArrAction5, ArrAction6, ArrAction7, ArrAction8);
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

	String checkResultURL1 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorByMisServlet";
	checkResultURL1 += commonURL;

    String checkResultURL2 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsReportServlet";
	checkResultURL2 += commonURL;

	String checkResultURL3 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorTotalScanedServlet";
	checkResultURL3 += commonURL;
    
    String checkResultURL4 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorIdenticalServlet";
	checkResultURL4 += commonURL;
    
    String checkResultURL5 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorScanedServlet";
	checkResultURL5 += commonURL;

    String checkResultURL6 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorNeedScanedServlet";
	checkResultURL6 += commonURL;

    String checkResultURL7 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorNotScanedServlet";
	checkResultURL7 += commonURL;

    String checkResultURL8 = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorDifferentServlet";
	checkResultURL8 += commonURL;
    String pageTitle = "";
	if(startDate.equals("") && endDate.equals("")){
		pageTitle = "截至今天" + companyName + "创建资产情况";
	} else if(!startDate.equals("") && endDate.equals("")){
		pageTitle = startDate + "之后" + companyName + "创建资产情况";
	} else if(startDate.equals("") && !endDate.equals("")){
		pageTitle = endDate + "之前" + companyName + "创建资产情况";
	} else if(!startDate.equals("") && !endDate.equals("")){
		pageTitle = startDate + "和" + endDate + "之间" + companyName + "创建资产情况";
	}
%>
<script language="javascript">
	var tabBox = new TabBox("tab");
	tabBox.addtab("checkResult1", "MIS新增资产");
	tabBox.addtab("checkResult2", "新转资需PDA扫描确认资产");
	tabBox.addtab("checkResult3", "期间实际扫描资产");
	tabBox.addtab("checkResult4", "帐实相符资产");
    tabBox.addtab("checkResult5", "其中新转资资产扫描数量");
    
    tabBox.addtab("checkResult6", "已完成新转资并且需PDA扫描确认资产");
    tabBox.addtab("checkResult7", "新转资尚未扫描确认资产");
    tabBox.addtab("checkResult8", "帐实不符资产");

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
            <div id="checkResult6" style="display:none">
				<iframe id="checkResultFrm6" style="width:100%;height:100%" border="0" frameborder="0" src="<%=checkResultURL6%>"></iframe>
			</div>
            <div id="checkResult7" style="display:none">
				<iframe id="checkResultFrm7" style="width:100%;height:100%" border="0" frameborder="0" src="<%=checkResultURL7%>"></iframe>
			</div>
            <div id="checkResult8" style="display:none">
				<iframe id="checkResultFrm8" style="width:100%;height:100%" border="0" frameborder="0" src="<%=checkResultURL8%>"></iframe>
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
    document.all("checkResult6").style.display = "none";
    document.all("checkResult7").style.display = "none";
    document.all("checkResult8").style.display = "none";
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
		tabName = "MIS新增资产";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorByMisServlet"
	} else if(exportType == "2"){
		strDiv = "checkResult2";
		strFrm = "checkResultFrm2";
		tabName = "新转资需PDA扫描确认资产";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsReportServlet"
	} else if(exportType == "3"){
		strDiv = "checkResult3";
		strFrm = "checkResultFrm3";
		tabName = "期间实际扫描资产";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorTotalScanedServlet"
	} else if(exportType == "4"){
		strDiv = "checkResult4";
		strFrm = "checkResultFrm4";
		tabName = "帐实相符资产";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorIdenticalServlet"
	} else if(exportType == "5"){
		strDiv = "checkResult5";
		strFrm = "checkResultFrm5";
		tabName = "其中新转资资产扫描数量";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorScanedServlet"
	} else if(exportType == "6"){
		strDiv = "checkResult6";
		strFrm = "checkResultFrm6";
		tabName = "已完成新转资并且需PDA扫描确认资产";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorNeedScanedServlet"
	} else if(exportType == "7"){
		strDiv = "checkResult7";
		strFrm = "checkResultFrm7";
		tabName = "新转资尚未扫描确认资产";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorNotScanedServlet"
	} else if(exportType == "8"){
		strDiv = "checkResult8";
		strFrm = "checkResultFrm8";
		tabName = "帐实不符资产";
		action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorDifferentServlet"
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