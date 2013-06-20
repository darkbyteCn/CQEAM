<%@ page import="com.sino.ams.newasset.report.dto.AssetsGeneralDTO"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-5-13
  Time: 16:23:42
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
<script type="text/javascript"  src="/WebLibary/js/LookUp.js"></script>
<script type="text/javascript"  src="/WebLibary/js/sinoHelp.js"></script>
<title>管理指标类报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();');document.onkeydown=showKeyDown;" onload="initPage();">
<%
	AssetsGeneralDTO dto = (AssetsGeneralDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    String managerGuideType = dto.getManagerGuideType();
    String pageTile=SessionUtil.getPageTile(request);
    if( managerGuideType.equals( "ASSETS_REDOUND" ) ) {
    	pageTile = "固定资产回报率";
    }
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.AssetsGeneralServlet">
<%=WebConstant.WAIT_TIP_MSG%>
    <script type="text/javascript">
        printTitleBar("<%=pageTile%>");
    </script>
	<table width="100%" border="0">
		<tr>
			<td width="10%" align="right">会计期间：</td>
			<td width="60%" align="left">
                <input  type="text" name="period" style="width:18%" value="<%="".equals(dto.getPeriod()) ? "" : dto.getPeriod().substring(0,4) + "-" + dto.getPeriod().substring(4) + "-01"%>" title="点击选择会计期" readonly class="input_style2" onclick="gfPop.fPopCalendar(period)">
            </td>
            <td width="20%" align="right">
                <img src="/images/eam_images/search.jpg"  onclick="do_Search();">
                <img src="/images/eam_images/export.jpg"  onclick="do_Export();" alt="导出Excel">
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
    if (managerGuideType.equals("DATA_NICETY")) {
%>
            <td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">准确率</td>
<%
    } else if (managerGuideType.equals("ASSETS_REDOUND")) {
%>
            <td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">当前净利润</td>
            <td width="10%" align="center">期初总固定资产净额</td>
            <td width="10%" align="center">期末总固定资产净额</td>
            <td width="10%" align="center">固定资产回报率</td>
<%
    }else if (managerGuideType.equals("ASSETS_TURNOVER")) {
%>
            <td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">当期营业收入</td>
            <td width="10%" align="center">期初总固定资产净额</td>
            <td width="10%" align="center">期末总固定资产净额</td>
            <td width="10%" align="center">固定资产周转率</td>
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
		<tr height="22" title="点击查看各地市明细信息" style="cursor:hand" onClick="do_ShowDetail('<%=row.getValue("PERIOD")%>')">
<%
            if (managerGuideType.equals("DATA_NICETY")) {
%>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
<%
            } else if (managerGuideType.equals("ASSETS_REDOUND")) {
%>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("CURRENT_AMOUNT")%></td>
                <td width="10%" align="center"><%=row.getValue("BEGIN_AMOUNT")%></td>
                <td width="10%" align="center"><%=row.getValue("END_AMOUNT")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
<%
            } else if (managerGuideType.equals("ASSETS_TURNOVER")) {
%>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                <td width="10%" align="center"><%=row.getValue("CURRENT_AMOUNT")%></td>
                <td width="10%" align="center"><%=row.getValue("BEGIN_AMOUNT")%></td>
                <td width="10%" align="center"><%=row.getValue("END_AMOUNT")%></td>
                <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
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
	//alert(mainFrm.period.value);
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AssetsGeneralServlet";
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
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AssetsGeneralServlet";
    mainFrm.submit();
}

function do_ShowDetail(period){
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AssetsGeneralServlet";
    mainFrm.period.value = period;
    mainFrm.act.value = "<%=AssetsActionConstant.DETAIL_ACTION%>";
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    window.open("/public/waiting2.htm", "assWin", style);
    mainFrm.target = "assWin";
    mainFrm.submit();
}
</script>