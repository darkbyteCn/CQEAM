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
<script type="text/javascript">
    printTitleBar("资产分析报表-->>基站盘点用时监控报表");
</script>
	<input name="act" type="hidden">
	<input name="organizationId" type="hidden" value="<%=dto.getOrganizationId()%>">
	<input name="averageChkTime" type="hidden" value="<%=dto.getAverageChkTime()%>">
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:20px;left:1px;width:990px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="11%" align="center">公司名称</td>
			<td width="13%" align="center">地点代码</td>
			<td width="34%" align="center">地点名称</td>
			<td width="9%" align="center">基站资产数</td>
			<td width="11%" align="center">最新盘点用时(分)</td>
			<td width="11%" align="center">盘点平均用时(分)</td>
			<td width="11%" align="center">盘点用时差异(分)</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:570px;width:1007px;position:absolute;top:43px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
			<td width="11%"><input type="text" class="finput" readonly value="<%=row.getValue("COMPANY")%>"></td>
			<td width="13%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
			<td width="34%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
			<td width="9%"><input type="text" class="finput3" readonly value="<%=row.getValue("BTS_ASSETS_COUNT")%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=row.getValue("BTS_SCAN_TIME")%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=row.getValue("BTS_CHK_TIME")%>"></td>
			<td width="11%"><input type="text" class="finput3" readonly value="<%=row.getValue("LEFT_SCAN_TIME")%>"></td>
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
<div style="position:absolute;top:615px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
<div style="position:absolute;top:650px;left:460; right:20"><img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" title="导出到Excel">&nbsp;&nbsp;<img src="/images/eam_images/close.jpg" style="cursor:'hand'" onclick="self.close();" title="关闭窗口"></div>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function initPage(){
	window.focus();
	do_SetPageWidth();
}

function do_Export() {
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.BtsDetailReportServlet";
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
    mainFrm.submit();
}

</script>