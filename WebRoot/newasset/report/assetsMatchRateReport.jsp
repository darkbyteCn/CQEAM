<%@ page import="com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-3-10
  Time: 14:04:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
<script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
<title>年底盘点资产账实相符率</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    boolean comManager = userAccount.isComAssetsManager();
    boolean provManager = userAccount.isProvAssetsManager();
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.AssetsMatchRateServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
var matchAssetsType = "<%=dto.getMatchAssetsType()%>";
 if (matchAssetsType == "MATCH_DEPT") {
    printTitleBar("年底盘点资产账实相符率（实物管理部门层面）")
} else {
    printTitleBar("年底盘点资产账实相符率（公司层面）")
}
</script>
<%
if (dto.getMatchAssetsType().equals("MATCH_DEPT")) {
%>
    <table width="100%" border="0">
		<tr>
			<td width="10%" align="right">公司：</td>
			<td width="15%" align="left">
                <select class="select_style1" name="organizationId" style="width:55%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
<%
    if (comManager && !provManager) {
%>
            <td width="10%" align="right">责任部门：</td>
            <td width="25%" align="left">
                <select class="select_style1" name="responsibilityDept" style="width:95%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select>
            </td>
<%
    } else {
%>
<%}%>
            <td width="10%" align="right">截止日期：</td>
            <td width="15%" align="left">
                <input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:60%" title="点击选择截至日期" readonly class="input_style2" onclick="gfPop.fEndPop('', endDate)"><img src="/images/calendar.gif" alt="点击选择截至日期" onclick="gfPop.fEndPop('', endDate);">
            </td>
            <td width="15%" align="right">
                <img src="/images/eam_images/search.jpg"   onclick="do_Search();">
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
<%
} else {
%>
    <table width="100%" border="0">
		<tr>
			<td width="10%" align="right">公司：</td>
			<td width="20%" align="left">
                <select class="select_style1" name="organizationId" style="width:75%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
			<td width="10%" align="right">截止日期：</td>
            <td width="20%" align="left">
                <input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:55%" title="点击选择截至日期" readonly class="input_style2" onclick="gfPop.fEndPop('', endDate)"><img src="/images/calendar.gif" alt="点击选择截至日期" onclick="gfPop.fEndPop('', endDate);">
            </td>
            <td width="20%" align="right">
                <img src="/images/eam_images/search.jpg"  onclick="do_Search();">
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
<%}%>
    <input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="matchAssetsType" value="<%=dto.getMatchAssetsType()%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
<%
if (dto.getMatchAssetsType().equals("MATCH_DEPT")) {
%>
    <table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="12%" align="center">公司</td>
            <td width="22%" align="center">责任部门</td>
            <td width="9%" align="center">MIS资产数量</td>
			<td width="10%" align="center">MIS资产原值</td>
            <td width="9%" align="center">账实相符数量</td>
			<td width="10%" align="center">账实相符原值</td>
			<td width="10%" align="center">账实相符数量百分比</td>
			<td width="10%" align="center">账实相符原值百分比</td>
		</tr>
	</table>
<%
} else {
%>
    <table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="12%" align="center">公司</td>
            <td width="10%" align="center">MIS资产数量</td>
			<td width="10%" align="center">MIS资产原值</td>
            <td width="10%" align="center">账实相符数量</td>
			<td width="10%" align="center">账实相符原值</td>
			<td width="10%" align="center">账实相符数量百分比</td>
			<td width="10%" align="center">账实相符原值百分比</td>
		</tr>
	</table>
<%}%>
</div>
<div id="dataDiv" style="overflow:scroll;height:350px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
if (dto.getMatchAssetsType().equals("MATCH_DEPT")) {
%>
		<tr >
			<td width="12%" align="center"><%=row.getValue("COMPANY")%></td>
            <td width="22%" align="left"><%=row.getValue("DEPT_NAME")%></td>
            <td width="9%" align="right"><%=row.getValue("SUM_UNITS")%></td>
            <td width="10%" align="right"><%=row.getValue("SUM_COST2")%></td>
            <td width="9%" align="right"><%=row.getValue("SUM_COUNT")%></td>
            <td width="10%" align="right"><%=row.getValue("SUM_COST")%></td>
			<td width="10%" align="right"><%=row.getValue("AMOUNT_RATE")%></td>
			<td width="10%" align="right"><%=row.getValue("MONEY_RATE")%></td>
        </tr>
<%
} else {
%>
        <tr >
			<td width="12%" align="center"><%=row.getValue("COMPANY")%></td>
			<td width="10%" align="right"><%=row.getValue("SUM_UNITS")%></td>
            <td width="10%" align="right"><%=row.getValue("SUM_COST2")%></td>
            <td width="10%" align="right"><%=row.getValue("SUM_COUNT")%></td>
            <td width="10%" align="right"><%=row.getValue("SUM_COST")%></td>
			<td width="10%" align="right"><%=row.getValue("AMOUNT_RATE")%></td>
			<td width="10%" align="right"><%=row.getValue("MONEY_RATE")%></td>
        </tr>
<%
}
        }
	}
%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:91%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function initPage(){
	do_SetPageWidth();
}

function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AssetsMatchRateServlet";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AssetsMatchRateServlet";
    mainFrm.submit();
}

function do_ShowDetail(organizationId, companyName, scanCount){
	var analyseType = mainFrm.analyseType.value;
	if(scanCount == 0){
		alert("“"+companyName+"”盘点资产数为0，无相关信息。");
		return;
	}
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AssetsMatchRateServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.DETAIL_ACTION%>";
	var selObj = mainFrm.organizationId;
	selectSpecialOptionByItem(selObj, organizationId);
	mainFrm.companyName.value = companyName;
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    window.open("/public/waiting2.htm", "assWin", style);
    mainFrm.target = "assWin";
    mainFrm.submit();
}
function do_SelectContent() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_CONTENT%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        } else {
            mainFrm.contentName.value = "";
            mainFrm.contentCode.value = "";
        }
}
</script>