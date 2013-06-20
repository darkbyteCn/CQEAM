<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-5-25
  Time: 10:57:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>新增资产盘点监控报表（按部门）</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();');" onload="initPage();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId();
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CostNewAssetsCheckServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产分析报表-->>新增资产盘点监控（按部门）");
</script>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="12%" align="right">成本中心：</td>
			<td width="20%"><select size="1" name="costCenterCode" style="width:100%"><%=dto.getOrgOpt()%></select></td>
			<td width="12%" align="right">资产创建日期：</td>
			<td width="15%"><input type="text" name="startDate" style="cursor:hand;width:100%" title="点击选择开始日期" readonly value="<%=dto.getStartDate()%>" onclick="gfPop.fStartPop(startDate, endDate)" size="20"></td>
			<td width="6%" align="center">到：</td>
			<td width="15%"><input type="text" name="endDate" style="cursor:hand;width:100%" title="点击选择截至日期" readonly value="<%=dto.getEndDate()%>" onclick="gfPop.fEndPop(startDate, endDate)"></td>
			<td width="20%"><img border="0" src="/images/eam_images/search.jpg" width="63" height="18" align="right" onclick="do_Search();">&nbsp;<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
	<input name="companyName" type="hidden" value="<%=userAccount.getCompany()%>">
	<input name="organizationId" type="hidden" value="<%=orgId%>">
    <input name="costCode" type="hidden" value="">
    <input name="costCenterName" type="hidden" value="">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%" style="table-layout:fixed;word-break:keep-all;" >
		<tr height="22">
			<td width="22%" align="center">成本中心</td>
			<td width="13%" align="center">MIS需PDA扫描数量</td>
			<td width="13%" align="center">已盘MIS数量</td>
			<td width="13%" align="center">未盘MIS数量</td>
			<td width="13%" align="center">帐实相符数</td>
			<td width="13%" align="center">帐实相符率</td>
			<td width="13%" align="center">盘点百分比</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:370px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="table-layout:fixed;word-break:break-all">
        		<%--<tr height="22" title="点击查看本本公司盘点详细情况" style="cursor:hand" onClick="do_ShowDetail('400015', '省公司计费账务结算中心');">--%>
			<%--<td width="22%" >0</td>--%>
			<%--<td width="13%" align="right">0</td>--%>
			<%--<td width="13%" align="right">0</td>--%>
			<%--<td width="13%" align="right">0</td>--%>
			<%--<td width="13%" align="right">0</td>--%>
			<%--<td width="13%" align="right">0</td>--%>
			<%--<td width="13%" align="right">0</td>--%>
                    <%--</tr>--%>
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22" title="点击查看本本公司盘点详细情况" style="cursor:hand" onClick="do_ShowDetail('<%=row.getValue("COST_CENTER_CODE")%>', '<%=row.getValue("COST_CENTER_NAME")%>')">
			<td width="22%" ><%=row.getValue("COST_CENTER_NAME")%></td>
			<td width="13%" align="right"><%=row.getValue("TOTAL_COUNT")%></td>
			<td width="13%" align="right"><%=row.getValue("SCANED_COUNT")%></td>
			<td width="13%" align="right"><%=row.getValue("NOT_SCANED_COUNT")%></td>
			<td width="13%" align="right"><%=row.getValue("IDENTICAL_COUNT")%></td>
			<td width="13%" align="right"><%=row.getValue("IDENTICAL_RATE")%></td>
			<td width="13%" align="right"><%=row.getValue("SCANED_RATE")%></td>
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
<div style="position:absolute;top:460px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
	document.mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.CostNewAssetsCheckServlet";
	document.mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    document.mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	document.mainFrm.target = "_self";
	document.mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.CostNewAssetsCheckServlet";
    document.mainFrm.submit();
}

function do_ShowDetail(costCode, costCenterName){
	document.mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.CostNewAssetsFrmServlet";
	document.mainFrm.act.value = "<%=AssetsActionConstant.DETAIL_ACTION%>";
	var selObj = document.mainFrm.costCenterCode;
    selectSpecialOptionByItem(selObj, costCode);
    document.mainFrm.costCode.value = costCode;
    document.mainFrm.costCenterName.value = costCenterName;
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    window.open("/public/waiting2.htm", "assWin", style);
    document.mainFrm.target = "assWin";
    document.mainFrm.submit();
}
</script>