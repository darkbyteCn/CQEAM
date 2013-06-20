<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>盘点结果报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CheckResultServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产分析报表-->>盘点结果统计")
</script>
	<table width="100%" border="0" class="queryTable">
        <tr>
			<td width="8%" align="right">公司名称：</td>
			<td width="12%"><select class="select_style1" size="1" name="organizationId" style="width:100%"><%=dto.getOrgOpt()%></select></td>
            <td width="8%" align="right">设备标签：</td>
            <td width="12%"><input class="input_style1" type="text" name="fromBarcode" style="width:100%" value="<%=dto.getFromBarcode()%>"></td>
            <td width="4%" align="center">到</td>
            <td width="12%" align="left"><input class="" type="text" name="toBarcode" style="width:100%" value="<%=dto.getToBarcode()%>"></td>
            <td width="8%" align="right">创建日期：</td>
            <td width="12%"><input class="input_style1" type="text" name="creationDate" value="<%=dto.getCreationDate()%>" style="width:100%;cursor:hand" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(creationDate, lastUpdateDate)"></td>
            <td width="4%" align="center">到</td>
            <td width="12%"><input class="input_style1" type="text" name="lastUpdateDate" value="<%=dto.getLastUpdateDate()%>" style="cursor:hand;width:100%" title="点击选择截至日期" readonly onclick="gfPop.fEndPop(creationDate, lastUpdateDate)"></td>
        </tr>
        <tr>
            <td width="8%" align="right">盘点日期：</td>
            <td width="12%"><input class="input_style1" type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:100%;cursor:hand" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(startDate, endDate)"></td>
            <td width="4%" align="center">到</td>
            <td width="12%"><input class="input_style1" type="text" name="endDate" value="<%=dto.getEndDate()%>" style="cursor:hand;width:100%" title="点击选择截至日期" readonly onclick="gfPop.fEndPop(startDate, endDate)"></td>
            <!--<td width="5%" align="right">截止日期：</td>-->
			<%--<td width="10%"><input type="text" name="endDate" style="cursor:hand;width:100%" title="点击选择截至日期" readonly value="<%=dto.getEndDate()%>" onclick="gfPop.fPopCalendar(endDate);"></td>--%>
			<td width="22%" colspan="6" align="right">
			<img border="0" src="/images/eam_images/search.jpg"  onclick="do_Search();">
<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
	<input name="companyName" type="hidden">
	<input type="hidden" name="analyseType" value="<%=AssetsDictConstant.ANALYZE_CATEGORY_4%>">
</form>


<div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:70px;left:0px;width:100%" class="crystalScroll">
	<table class="eamHeaderTable" border="1" width="100%">
		<tr height="22">
			<td width="9%" align="center">公司名称</td>
			<td width="8%" align="center">MIS资产数量</td>
			<td width="11%" align="center">MIS需PDA扫描数量</td>
			<td width="12%" align="center">MIS无需PDA扫描数量</td>
			<td width="9%" align="center">已盘点总量</td>
			<td width="9%" align="center">已盘MIS数量</td>
			<td width="10%" align="center">已盘实物数量</td>
			<td width="12%" align="center">盘点率<!--(按MIS数)--></td>
			<!--<td width="12%" align="center">帐实相符率(按盘点数)</td>-->
		</tr>
	</table>
</div>
<div style="overflow:scroll;height:320px;width:100%;position:absolute;top:92px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22" title="点击查看公司“<%=row.getValue("COMPANY")%>”盘点情况" style="cursor:hand" onClick="do_ShowDetail('<%=row.getValue("ORGANIZATION_ID")%>', '<%=row.getValue("COMPANY")%>')">
			<td width="9%"><%=row.getValue("COMPANY")%></td>
			<td width="8%" align="right"><%=row.getValue("TOTAL_COUNT")%></td>
			<td width="11%" align="right"><%=row.getValue("NEED_COUNT")%></td>
			<td width="12%" align="right"><%=row.getValue("NOT_NEED_COUNT")%></td>
			<td width="9%" align="right"><%=row.getValue("SCANED_COUNT")%></td>
			<td width="9%" align="right"><%=row.getValue("IDENTICAL_COUNT")%></td>
			<td width="10%" align="right"><%=row.getValue("UNMATCHED_COUNT")%></td>
			<td width="12%" align="right"><%=row.getValue("IDENTICAL_RATE_1")%></td>
			<%--<td width="12%" align="right"><%=row.getValue("IDENTICAL_RATE_2")%></td>--%>
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
function initPage(){
	do_SetPageWidth();
}

function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.CheckResultServlet";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
//    var style = 'width=900,height=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
//    window.open("/public/waiting2.htm", "assWin", style);
//    document.mainFrm.target = "assWin";
//    mainFrm.submit();
//    mainFrm.target = "_self";

    mainFrm.target = "_blank";
    mainFrm.submit();
    mainFrm.target = "_self";

//    mainFrm.target = "_self";
//	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.CheckResultServlet";
//    mainFrm.submit();
}

function do_ShowDetail(organizationId, companyName){
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.CheckResultFrmServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.DETAIL_ACTION%>";
	var selObj = mainFrm.organizationId;
	selectSpecialOptionByItem(selObj, organizationId);
	mainFrm.companyName.value = companyName;
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    window.open("/public/waiting2.htm", "assWin", style);
    mainFrm.target = "assWin";
    mainFrm.submit();
}
</script>