<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>新增资产监控报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("新增资产监控报表")
</script>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="10%" align="right">公司名称：</td>
			<td width="15%"><select size="1" name="organizationId" style="width:100%"><%=dto.getOrgOpt()%></select></td>
			<td width="12%" align="right">资产创建日期：</td>
			<td width="12%"><input type="text" name="startDate" style="cursor:hand;width:100%" title="点击选择开始日期" readonly class="readonlyInput" value="<%=dto.getStartDate()%>" onclick="gfPop.fStartPop(startDate, endDate);" size="20"></td>
			<td width="6%" align="center">到：</td>
			<td width="12%"><input type="text" name="endDate" style="cursor:hand;width:100%" title="点击选择截至日期" readonly class="readonlyInput" value="<%=dto.getEndDate()%>" onclick="gfPop.fEndPop(startDate, endDate);"></td>
            <td width="7%" align="right" >阀值：</td>
            <td width="6%"><input type="text" name="threshold" style="width:100%" value="<%=dto.getThreshold()%>"></td>
			<td width="20%" align="right" ><img border="0" src="/images/eam_images/search.jpg" width="63" height="18"  onclick="do_Search();">&nbsp;&nbsp;&nbsp;<img src="/images/eam_images/export.jpg"  style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
	<input name="companyName" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
         <tr height="22">
            <td width="21%" colspan="3" align="center">新转资资产情况</td>
            <td width="21%" colspan="3" align="center">实际扫描完成情况</td>
            <td width="31%" colspan="4" align="center">其中新转资资产实际扫描完成情况</td>
            <%--<td width="8%" rowspan="2" align="center">新转资资产实际扫描完成百分比排名</td>--%>
        </tr>
		<tr height="5">
			<td width="11%" align="center">公司名称</td>
			<td width="8%" align="center">MIS新增资产数量</td>
			<td width="8%" align="center">需PDA扫描确认数量</td>

			<td width="8%" align="center">期间实际扫描数量</td>
			<td width="8%" align="center">账实相符数量</td>
			<td width="8%" align="center">账实相符百分比</td>

			<td width="9%" align="center">其中新转资资产扫描数量</td>
			<td width="13%" align="center">已完成新转资资产并且需PDA扫描确认的数量</td>
			<td width="10%" align="center">新转资资产尚未扫描确认数量</td>
            <td width="10%" align="center">新转资资产扫描确认百分比</td>
        </tr>

	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:69%;width:857px;position:absolute;top:100px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

        		<%--<tr height="22" title="点击查看公司“大同”盘点情况" style="cursor:hand" onClick="do_ShowDetail('83', '大同')">--%>
			<%--<td width="22%">0</td>--%>
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
        int num = 1;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22" title="点击查看公司“<%=row.getValue("COMPANY")%>”新增资产情况" style="cursor:hand" onClick="do_ShowDetail('<%=row.getValue("ORGANIZATION_ID")%>', '<%=row.getValue("COMPANY")%>')">
			<td width="11%"><%=row.getValue("COMPANY")%></td>
			<td width="8%" align="right"><%=row.getValue("TOTAL_COUNT")%></td>
			<td width="8%" align="right"><%=row.getValue("TOTAL_NEED_SCANCEL_COUNT")%></td>

			<td width="8%" align="right"><%=row.getValue("SCANED_TOTAL_COUNT")%></td>
			<td width="8%" align="right"><%=row.getValue("SCANCER_IDENTICAL_COUNT")%></td>
			<td width="8%" align="right"><%=row.getValue("IDENTICAL_RATE")%></td>

            <td width="9%" align="right"><%=row.getValue("SCANED_FILTER_COUNT")%></td>
            <td width="13%" align="right"><%=row.getValue("SCANER_NEED_SCANED_COUNT")%></td>
            <td width="10%" align="right"><%=row.getValue("NOT_SCANED_COUNT")%></td>
            <td width="10%" align="right"><%=row.getValue("SCANED_RATE")%></td>
            
            <%--<td width="7%" align="center"><%=num %></td>--%>
        </tr>
<%
            num++;
		}
	}
%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:90%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
    var threshold = mainFrm.threshold.value;
    if(threshold == ""){
        mainFrm.threshold.value = 0;
        threshold = 0;
    }
    var Expression = /^[0-9]*$/;
    var objExp=new RegExp(Expression);
    if(objExp.test(threshold)){
        if(parseInt(threshold) <= 30){
            document.mainFrm.action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorServlet";
            document.mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
            document.mainFrm.target = "_self";
            document.mainFrm.submit();
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        } else {
            alert("阀值不能超过30天！");
        }
    } else {
        alert("阀值必须为数字！");
        mainFrm.threshold.value = 0;
        mainFrm.threshold.focus();
    }
}

function do_Export() {
    document.mainFrm.action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorServlet";
    document.mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	document.mainFrm.target = "_self";
    document.mainFrm.submit();
}

function do_ShowDetail(organizationId, companyName){
	document.mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.NewAssetsMonitorFrmServlet";
	document.mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	var selObj = document.mainFrm.organizationId;
	selectSpecialOptionByItem(selObj, organizationId);
	document.mainFrm.companyName.value = companyName;
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    window.open("/public/waiting2.htm", "reportWin", style);
    document.mainFrm.target = "reportWin";
    document.mainFrm.submit();
}
</script>