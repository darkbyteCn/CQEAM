<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.BtsMonitorReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产分析报表-->>基站盘点用时监控报表");
</script>
	<table width="100%" border="0" class="queryTable">
		<tr>
			<td width="20%" align="right">公司名称：</td>
			<td width="30%"><select size="1" class="select_style1" name="organizationId" style="width:100%"><%=dto.getOrgOpt()%></select></td>
			<td width="50%" align="right"><img border="0" src="/images/eam_images/search.jpg" onclick="do_Search();">&nbsp;<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
	<input name="company" type="hidden">
	<input name="averageChkTime" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="12%" align="center">公司名称</td>
			<td width="12%" align="center">基站数量</td>
			<td width="12%" align="center">基站资产数量</td>
			<td width="12%" align="center">盘点基站数</td>
			<td width="12%" align="center">盘点基站资产数</td>
			<td width="12%" align="center">盘点总体用时(分)</td>
			<td width="12%" align="center">盘点平均用时(分)</td>
			<td width="12%" align="center">平均用时排名</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:300px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22" title="点击查看公司“<%=row.getValue("COMPANY")%>”基站盘点详情" style="cursor:hand" onClick="do_ShowDetail('<%=row.getValue("ORGANIZATION_ID")%>', '<%=row.getValue("AVERAGE_MINUTE")%>')">
			<td width="12%" align="center"><%=row.getValue("COMPANY")%></td>
			<td width="12%" align="right"><%=row.getValue("TOTAL_BTS_COUNT")%></td>
			<td width="12%" align="right"><%=row.getValue("TOTAL_ASSETS_COUNT")%></td>
			<td width="12%" align="right"><%=row.getValue("SCANED_BTS_COUNT")%></td>
			<td width="12%" align="right"><%=row.getValue("SCANED_ASSETS_COUNT")%></td>
			<td width="12%" align="right"><%=row.getValue("SCANED_MINUTE")%></td>
			<td width="12%" align="right"><%=row.getValue("AVERAGE_MINUTE")%></td>
			<td width="12%" align="right"><%=row.getValue("AVERAGE_LOCATION")%></td>
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
<div style="position:absolute;top:380px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.BtsMonitorReportServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.BtsMonitorReportServlet";
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
    mainFrm.submit();
}

function do_ShowDetail(organizationId, averageChkTime){
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.BtsDetailReportServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	var selObj = mainFrm.organizationId;
	selectSpecialOptionByItem(selObj, organizationId);
	mainFrm.averageChkTime.value = averageChkTime;
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    window.open("/public/waiting2.htm", "averageWin", style);
    mainFrm.target = "averageWin";
    mainFrm.submit();
}
</script>