<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-9-17
  Time: 16:13:43
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>抽查工单监控报表(按批)</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.sampling.report.servlet.AssetsSamplingReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("抽查工单监控报表(按批)")
</script>
	<table width="100%" border="0" class="queryTable">
		<tr>
			<td width="10%" align="right">公司名称：</td>
			<td width="20%"><select class="select_style1" size="1" name="organizationId" style="width:100%"><%=dto.getSampledOuOpt()%></select></td>
			<td width="70%" align="right"><img border="0" src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_Search();" alt="查询">&nbsp;<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
	<input name="batchNo" type="hidden">
	<input name="company" type="hidden">
</form>


<div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:45px;left:0px;width:100%" class="crystalScroll">
	<table class="eamHeaderTable" border="1" width="120%">
		<tr height="22">
			<td width="9%" align="center">公司名称</td>
			<td width="13%" align="center">抽查工单批</td>
			<td width="7%" align="center">要求完成日期</td>
			<!--<td width="6%" align="center">总量</td>-->
			<td width="6%" align="center">抽查百分比</td>
			<td width="6%" align="center">抽查数量</td>
            <td width="6%" align="center">实际完成数量</td>
            <td width="7%" align="center">抽查完成百分比</td>
            <td width="6%" align="center">账实相符数</td>
            <td width="6%" align="center">账实相符率</td>
        </tr>
	</table>
</div>
<div style="overflow:scroll;height:340px;width:100%;position:absolute;top:68px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22" title="点击查看工单批“<%=row.getValue("BATCH_NO")%>”抽查情况" style="cursor:hand" onClick="do_ShowDetail('<%=row.getValue("BATCH_NO")%>', '<%=row.getValue("COMPANY")%>')">
			<td width="9%" align="center"><%=row.getValue("COMPANY")%></td>
			<td width="13%" align="center"><%=row.getValue("BATCH_NO")%></td>
			<td width="7%" align="center"><%=row.getValue("END_DATE")%></td>
			<%--<td width="6%"  align="center"><%=row.getValue("ITEM_COUNT")%></td>--%>
			<td width="6%"  align="right"><%=row.getValue("SAMPLING_RATIO")%></td>
			<td width="6%"  align="right"><%=row.getValue("REQ_COUNT")%></td>
            <td width="6%"  align="right"><%=row.getValue("SCAN_COUNT")%></td>
            <td width="7%"  align="right"><%=row.getValue("FINISH_RATE")%></td>
            <td width="6%"  align="right"><%=row.getValue("IDENTICAL_COUNT")%></td>
            <td width="6%"  align="right"><%=row.getValue("IDENTICAL_RATE")%></td>
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
<div style="position:absolute;top:410px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
	document.mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	document.mainFrm.target = "_self";
	document.mainFrm.action = "/servlet/com.sino.ams.sampling.report.servlet.AssetsSamplingReportServlet";
	document.mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    document.mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	document.mainFrm.target = "_self";
	document.mainFrm.action = "/servlet/com.sino.ams.sampling.report.servlet.AssetsSamplingReportServlet";
    document.mainFrm.submit();
}

function do_ShowDetail(batchNo, company){
	document.mainFrm.action = "/servlet/com.sino.ams.sampling.report.servlet.AssetsSamplingReportFrmServlet";
	document.mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.batchNo.value = batchNo;
    mainFrm.company.value = company;
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    window.open("/public/waiting2.htm", "reportWin", style);
    document.mainFrm.target = "reportWin";
    document.mainFrm.submit();
}
</script>