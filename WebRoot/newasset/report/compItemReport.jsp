<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>公司设备统计</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsCheckLineDTO dto = (AmsAssetsCheckLineDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.ChkItemReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("盘点常用报表-->>公司设备统计")
</script>

	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="10%" align="right">公司名称：</td>
			<td width="15%"><select size="1" name="organizationId" style="width:100%"><%=dto.getCompanyOpt()%></select></td>
			<td width="10%" align="right">设备专业：</td>
			<td width="15%"><select size="1" name="itemCategory" style="width:100%"><%=dto.getItemCategoryOpt()%></select></td>
			<td width="10%" align="right">盘点时间：</td>
			<td width="22%"><input type="text" name="startDate" style="cursor:hand;width:45%" title="点击选择开始日期" readonly class="readonlyInput" value="<%=dto.getStartDate()%>" onclick="gfPop.fStartPop(startDate,endDate);">到<input type="text" name="endDate" style="cursor:hand;width:45%" title="点击选择截至日期" readonly class="readonlyInput" value="<%=dto.getEndDate()%>" onclick="gfPop.fEndPop(startDate,endDate);"></td>
			<td width="18%"><img border="0" src="/images/eam_images/search.jpg" width="63" height="18" align="right" onclick="do_Search();">&nbsp;<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="20%" align="center">公司名称</td>
			<td width="20%" align="center">设备专业</td>
			<td width="15%" align="center">现有数量</td>
			<td width="15%" align="center">未盘点数</td>
			<td width="15%" align="center">已盘点数</td>
			<td width="15%" align="center">完成比例</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:68%;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22" onclick="do_ShowDetail('<%=row.getValue("ORGANIZATION_ID")%>', '<%=row.getValue("ITEM_CATEGORY")%>', '<%=row.getValue("ITEM_CATEGORY_NAME")%>')" title="点击查看该公司盘点情况" style="cursor:hand">
			<td width="20%"><%=row.getValue("COMPANY")%></td>
			<td width="20%"><%=row.getValue("ITEM_CATEGORY_NAME")%></td>
			<td width="15%" align="right"><%=row.getValue("TOTAL_COUNT")%></td>
			<td width="15%" align="right"><%=row.getValue("NOT_SCAN_COUNT")%></td>
			<td width="15%" align="right"><%=row.getValue("SCAN_OVER_COUNT")%></td>
			<td width="15%" align="right"><%=row.getValue("SCAN_RATE")%></td>
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

function do_ShowDetail(orgId, itemCategory, itemCategoryName){
	var url = "/servlet/com.sino.ams.newasset.report.servlet.ItemCatDtlRptServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>";
	url += "&organizationId=" + orgId
	url += "&startDate=" + mainFrm.startDate.value;
	url += "&endDate=" + mainFrm.endDate.value;
	url += "&itemCategory=" + itemCategory;
	url += "&itemCategoryName=" + itemCategoryName;
	var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	window.open(url, "responWin", style);
}
</script>