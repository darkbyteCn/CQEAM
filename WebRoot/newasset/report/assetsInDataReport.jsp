<%@ page import="com.sino.ams.newasset.report.dto.AssetsInDataReportDTO"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-5-16
  Time: 13:40:10
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
<title>管理指标类报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	AssetsInDataReportDTO dto = (AssetsInDataReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    String managerGuideType = dto.getManagerGuideType();
    String pageTitle= null; //SessionUtil.getPageTile(request);
    if (dto.getManagerGuideType().equals("CHECK_RATE")){
    	pageTitle = "资产实物管理抽查任务完成率";
    }else if (dto.getManagerGuideType().equals("MATCH_CASE_RATE")){
    	pageTitle = "抽查盘点资产账实相符率";
    }else if (dto.getManagerGuideType().equals("TRUN_RATE")){
    	pageTitle = "转资率";
    } 
    else{
    	pageTitle = SessionUtil.getPageTile(request);
    }
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.AssetsInDataReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
    <script type="text/javascript">
        printTitleBar("<%=pageTitle%>")
    </script>
	<table width="100%" border="0">
		<tr>
<%
    if (managerGuideType.equals("TRUN_RATE") || managerGuideType.equals("CHECK_RATE") || managerGuideType.equals("MATCH_CASE_RATE") || managerGuideType.equals("COP_RATE") || managerGuideType.equals("COP_MATCH_RATE") || managerGuideType.equals("ACCOUNTING_ACCURATE")) {
%>
            <td width="10%" align="right">公司：</td>
			<td width="20%" align="left">
                <select class="select_style1" name="organizationId" style="width:60%"><%=request.getAttribute(AssetsWebAttributes.OU_OPTION)%></select>
            </td>
<%
    }
%>
            <td width="10%" align="right">会计期间：</td>
			<td width="20%" align="left">
                <input type="text" name="period" style="width:35%" value="<%="".equals(StrUtil.nullToString(dto.getPeriod())) ? "" : dto.getPeriod().substring(0,4) + "-" + dto.getPeriod().substring(4) + "-01"%>" title="点击选择会计期" readonly class="input_style2" onclick="gfPop.fPopCalendar(period)">
            </td>
<%
    if (managerGuideType.equals("IN_TIME_RATE") || managerGuideType.equals("NICETY_RATE")) {
%>
            <td width="25%"></td>
<%
    }
%>
            <td width="15%" align="right">
                <img src="/images/eam_images/search.jpg"   onclick="do_Search();">
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出Excel">
            </td>
		</tr>
	</table>
	<input type="hidden" name="act" value="">
    <input type="hidden" name="managerGuideType" value="<%=managerGuideType%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
        <tr height="22">
<%
    if (managerGuideType.equals("TRUN_RATE")) {
%>
            <td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">考核期内工程转资额</td>
            <td width="10%" align="center">工程累计投入总额</td>
            <td width="10%" align="center">转资率</td>
<%
    } else if (managerGuideType.equals("IN_TIME_RATE")) {
%>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">未及时上报次数</td>
            <td width="10%" align="center">考核期应上报次数</td>
            <td width="10%" align="center">决策分析报表上报及时率</td>
<%
    } else if (managerGuideType.equals("NICETY_RATE")) {
%>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">考核期内发生的转资资产不准确的数量</td>
            <td width="10%" align="center">考核期内转资资产总量</td>
            <td width="10%" align="center">转资信息准确率</td>
<%
    } else if (managerGuideType.equals("CHECK_RATE")) {
%>
            <td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">抽查盘点任务工单数量</td>
            <td width="10%" align="center">计划规定的抽查盘点任务工单总数</td>
            <td width="10%" align="center">抽查任务完成率</td>
<%
    } else if (managerGuideType.equals("MATCH_CASE_RATE")) {
%>
            <td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">抽查中账实相符的资产数量</td>
            <td width="10%" align="center">抽查资产总数量</td>
            <td width="10%" align="center">抽查盘点资产账实相符率</td>
<%
    } else if (managerGuideType.equals("COP_RATE")) {
%>
            <td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">已完成的日常巡检盘点的工单数</td>
            <td width="10%" align="center">计划的日常巡检盘点工单总数</td>
            <td width="10%" align="center">日常巡检资产盘点完成率</td>
<%
    } else if (managerGuideType.equals("COP_MATCH_RATE")) {
%>
            <td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">盘点中账实相符的资产数量</td>
            <td width="10%" align="center">盘点资产总数量</td>
            <td width="10%" align="center">日常巡检资产盘点账实相符率</td>
<%
    } else if (managerGuideType.equals("ACCOUNTING_ACCURATE")) {
%>
            <td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">资产核算相关的差错次数</td>
<%
    }
%>
        </tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:350px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
<%
            if (managerGuideType.equals("TRUN_RATE")) {
%>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("PROJECT_TRUN_ASSETS")%></td>
                <td width="10%" align="center"><%=row.getValue("PROJECT_SUM_ASSETS")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
<%
            } else if (managerGuideType.equals("IN_TIME_RATE")) {
%>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("NO_TIMELY_REPORT_NUM")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETSMENT_REPORT_NUM")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
<%
            } else if (managerGuideType.equals("NICETY_RATE")) {
%>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETSMENT_FALSE_NUM")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETSMENT_ASSETS_SUM")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
<%
            } else if (managerGuideType.equals("CHECK_RATE")) {
%>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("COMPLETE_CHECK_NUM")%></td>
                <td width="10%" align="center"><%=row.getValue("PLAN_CHECK_NUM")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
<%
            } else if (managerGuideType.equals("MATCH_CASE_RATE")) {
%>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("ACCOUNT_MATCH_CASE")%></td>
                <td width="10%" align="center"><%=row.getValue("CHECK_ASSETS_SUM")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
<%
            } else if (managerGuideType.equals("COP_RATE")) {
%>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_COP_NUM")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_COP_SUM")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
<%
            } else if (managerGuideType.equals("COP_MATCH_RATE")) {
%>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_MATCH_CASE")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_CHECK_SUM")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
<%
            } else if (managerGuideType.equals("ACCOUNTING_ACCURATE")) {
%>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("ACCURATE_ERROR_NUMBER")%></td>
<%
            }
%>
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
<div style="position:absolute;top:430px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
	var period = mainFrm.period.value;
	if(period == null || period == ""){
		alert("会计期间不能为空！");
		mainFrm.period.focus();
		return;
	}
	var dates=mainFrm.period.value.split("-",3);
	if (dates.length==3) {
		mainFrm.period.value=dates[0]+dates[1];
	}
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AssetsInDataReportServlet";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    var period = mainFrm.period.value;
	if(period == null || period == ""){
		alert("会计期间不能为空！");
		mainFrm.period.focus();
		return;
	}
	var dates=mainFrm.period.value.split("-",3);
	if (dates.length==3) {
		mainFrm.period.value=dates[0]+dates[1];
	}
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AssetsInDataReportServlet";
    mainFrm.submit();
}
</script>