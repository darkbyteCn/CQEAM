<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>盘点结果报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CostDiffReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("盘点日常报表-->>成本中心差异")
</script>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="10%" align="right">成本中心：</td>
			<td width="30%"><select size="1" name="costCode" style="width:100%"><%=dto.getCostCenterOpt()%></select></td>
			<td width="10%" align="right">盘点时间：</td>
			<td width="14%"><input type="text" name="startDate" style="cursor:hand;width:100%" title="点击选择开始日期" readonly class="readonlyInput" value="<%=dto.getStartDate()%>" onclick="gfPop.fStartPop(startDate,endDate);"></td>
			<td width="4%" align="center">到</td>
			<td width="13%"><input type="text" name="endDate" style="cursor:hand;width:100%" title="点击选择截至日期" readonly class="readonlyInput" value="<%=dto.getEndDate()%>" onclick="gfPop.fEndPop(startDate,endDate);"></td>
			<td width="20%"><img border="0" src="/images/eam_images/search.jpg" width="63" height="18" align="right" onclick="do_Search();">&nbsp;<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="8%" align="center">公司代码</td>
			<td width="12%" align="center">公司名称</td>
			<td width="40%" align="center">成本中心</td>
			<td width="10%" align="center">资产总数</td>
			<td width="10%" align="center">已盘点数</td>
			<td width="10%" align="center">未盘点数</td>
			<td width="10%" align="center">完成比例</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:68%;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		String notScanedCount = "";
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
			notScanedCount = row.getStrValue("NOT_SCANED_COUNT");
			if(notScanedCount.startsWith("-")){
				notScanedCount = "----";
			}
%>
		<tr height="22" title="点击查看该成本中心“<%=row.getValue("COST_NAME")%>”盘点情况" style="cursor:hand" onClick="do_ShowDetail('<%=row.getValue("COST_CODE")%>', '<%=row.getValue("COST_NAME")%>', '<%=row.getValue("SCANED_COUNT")%>')">
			<td width="8%" align="center"><%=row.getValue("COMPANY_CODE")%></td>
			<td width="12%"><%=row.getValue("COMPANY")%></td>
			<td width="40%"><%=row.getValue("COST_NAME")%></td>
			<td width="10%" align="right"><%=row.getValue("OWN_COUNT")%></td>
			<td width="10%" align="right"><%=row.getValue("SCANED_COUNT")%></td>
			<td width="10%" align="right"><%=notScanedCount%></td>
			<td width="10%" align="right"><%=row.getValue("SCAN_RATE")%></td>
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
<div style="position:absolute;top:428px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}

function do_ShowDetail(costCode, costName, scanCount){
	if(scanCount == 0){
		alert("本成本中心“"+costName+"”盘点资产数为0，无相关信息。");
		return;
	}
	var url = "/servlet/com.sino.ams.newasset.report.servlet.CostDiffDtlReportServlet?act=<%=AssetsActionConstant.QUERY_ACTION%>";
	url += "&costCode=" + costCode;
	url += "&costName=" + costName;
	url += "&startDate=" + mainFrm.startDate.value;
	url += "&endDate=" + mainFrm.endDate.value;
	var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	window.open(url, "responWin", style);
}
</script>