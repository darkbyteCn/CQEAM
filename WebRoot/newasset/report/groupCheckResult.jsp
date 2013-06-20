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
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.GroupCheckResultServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("盘点日常报表-->>盘点结果报表")
   
</script>

	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="10%" align="right">部门：</td>
			<td width="30%"><select size="1" class="select_style1" name="checkDept" style="width:100%"><%=dto.getDeptOpt()%></select></td>
			<td width="10%" align="right">盘点时间：</td>
			<td width="14%"><input type="text" name="startDate" class="input_style1" style="cursor:hand;width:100%" title="点击选择开始日期" readonly class="readonlyInput" value="<%=dto.getStartDate()%>" onclick="gfPop.fStartPop(startDate,endDate);"></td>
			<td width="4%" align="center">到</td>
			<td width="13%"><input type="text" name="endDate" class="input_style1" style="cursor:hand;width:100%" title="点击选择截至日期" readonly class="readonlyInput" value="<%=dto.getEndDate()%>" onclick="gfPop.fEndPop(startDate,endDate);"></td>
			<td width="20%" align="right" >
			<img border="0" src="/images/eam_images/search.jpg"  onclick="do_Search();">
			<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">
			</td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="40%" align="center">部门名称</td>
			<td width="20%" align="center">已盘点地点数</td>
			<td width="20%" align="center">未盘点地点数</td>
			<td width="20%" align="center">地点总数</td>
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
		<tr height="22" onclick="do_ShowDetail('<%=row.getValue("DEPT_CODE")%>', '<%=row.getValue("DEPT_NAME")%>', <%=row.getValue("SCAN_COUNT")%>,<%=row.getValue("NOSCAN_COUNT")%>,<%=row.getValue("ADDRESS_COUNT")%>)" title="点击查看该部门盘点情况" style="cursor:hand">
			<td width="40%"><%=row.getValue("DEPT_NAME")%></td>
			<td width="20%" id="scan" align="right"><%=row.getValue("SCAN_COUNT")%></td>
			<td width="20%" id="noscan" align="right"><%=row.getValue("NOSCAN_COUNT")%></td>
			<td width="20%" align="right"><%=row.getValue("ADDRESS_COUNT")%></td>
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
<div style="position:absolute;top:91%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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

function do_ShowDetail(checkDept, deptName, scanCount){
	if(scanCount == 0){
		alert("“" + deptName + "”盘点地点数为0，无相关信息。");
		return;
	}
	var url = "/servlet/com.sino.ams.newasset.report.servlet.GroupCheckFrmServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>";
	url += "&checkDept=" + checkDept
	url += "&startDate=" + mainFrm.startDate.value;
	url += "&endDate=" + mainFrm.endDate.value;
	var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	window.open(url, "responWin", style);
}
</script>