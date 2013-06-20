<%@ page import="com.sino.ams.newasset.report.dto.DeptAssetsReportDTO"%>
<%@ page import="java.math.BigDecimal"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-3-5
  Time: 16:43:38
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
<title>投资分类统计报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
	DeptAssetsReportDTO dto = (DeptAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.InvestAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产基础报表>>投资方向分类情况")
</script>
	<table width="100%" border="0">
		<tr>
			<td width="10%" align="right">公司：</td>
			<td width="20%" align="left">
                <select class="select_style1" name="organizationId" style="width:80%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <td width="10%" align="right">会计期间：</td>
            <td width="20%"><select class="select_style1" style="width:40%" name="year"><%=yearOption%></select>—<select class="select_style1" style="width:40%" name="month"><%=monthOption%></select></td>
            <td width="15%"  align="right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
	<input name="act" type="hidden">
	<input name="companyName" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:840px">
	<table height="40" class="eamDbHeaderTable" border="1" width="200%">
		<tr height="20">
			<td width="8%" align="center" rowspan="2" height="10">公司</td>
			<td width="20%" align="center" rowspan="2" height="10">投资项目分类</td>
			<td width="4%" align="center" rowspan="2" height="10">资产数量</td>
			<td width="6%" align="center" rowspan="2" height="10">投资额</td>
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总额比重</td>
            <td width="6%" align="center" rowspan="2" height="10">较去年同期增长率</td>
            <td width="10%" align="center" colspan="3" height="5">近3年增长率</td>
		</tr>
		<tr height="20" class="eamDbHeaderTr">
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2006" : Integer.parseInt(dto.getYear())-3 %></td>
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2007" : Integer.parseInt(dto.getYear())-2 %></td>
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2008" : Integer.parseInt(dto.getYear())-1 %></td>
        </tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:73%;width:857px;position:absolute;top:86px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		
		Double sumCost = 0d;
        Long sumCount = 0L;
        Float sumRate = 0f;
        
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
            <td width="8%" align="right"><%=row.getValue("COMPANY")%></td>
            <td width="20%" align="right"><%=row.getValue("PROJECT_NAME")%></td>
			<td width="4%" align="right"><%=row.getValue("SUM_COUNT")%></td>
			<td width="6%" align="right" ><%=row.getValue("SUM_COST")%></td>		<!-- 原值 -->
            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE").equals("%") ? "0%" : row.getValue("ASSETS_RATE")%></td>
            <td width="6%" align="right"><%=row.getValue("LAST_YEAR_RATE").equals("%") ? "0%" : row.getValue("LAST_YEAR_RATE")%></td>
            <td width="4%" align="right"><%=row.getValue("THREE_YEER_THREE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_THREE_RATE")%></td>
            <td width="4%" align="right"><%=row.getValue("THREE_YEER_TWO_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_TWO_RATE")%></td>
            <td width="4%" align="right"><%=row.getValue("THREE_YEER_ONE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_ONE_RATE")%></td>
        </tr>
<%
			sumCount += Long.parseLong(row.getValue("SUM_COUNT").toString());
			if(row.getValue("SUM_COST") != null && !"".equals(row.getValue("SUM_COST"))){
				sumCost += Double.parseDouble(row.getValue("SUM_COST").toString());
			}
			if(!row.getValue("ASSETS_RATE").equals("") && !row.getValue("ASSETS_RATE").equals("%")){
				sumRate += Float.parseFloat(row.getValue("ASSETS_RATE").toString().substring(0, row.getValue("ASSETS_RATE").toString().indexOf("%")));
			}
		}
%>
		<tr height="22">
			<td width="8%" colspan = "2" height= "22" align = "left">合计：</td>			
			<td width="6%"  align="right"><%=sumCount %></td>
			<td width="6%" align="right"><%=BigDecimal.valueOf(sumCost).toString().length() - BigDecimal.valueOf(sumCost).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumCost).toString().substring(0, BigDecimal.valueOf(sumCost).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumCost) %></td>	
			<td width="6%"  align="right"><%=sumRate.toString().length() - sumRate.toString().indexOf(".") > 3 ? sumRate.toString().substring(0, sumRate.toString().indexOf(".")+4) :sumRate %>%</td>
			<td width="6%" align="right"></td>
		</tr>
<%
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
	/*var accountingPeriod = mainFrm.accountingPeriod.value;
	if(accountingPeriod == null || accountingPeriod == ""){
		alert("会计期间不能为空！");
		mainFrm.accountingPeriod.focus();
		return;
	}*/
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.InvestAssetsReportServlet";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
	/*var accountingPeriod = mainFrm.accountingPeriod.value;
	if(accountingPeriod == null || accountingPeriod == ""){
		alert("会计期间不能为空！");
		mainFrm.accountingPeriod.focus();
		return;
	}*/
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.InvestAssetsReportServlet";
    mainFrm.submit();
}

function do_ShowDetail(organizationId, companyName, scanCount){
	var analyseType = mainFrm.analyseType.value;
	if(scanCount == 0){
		alert("“"+companyName+"”盘点资产数为0，无相关信息。");
		return;
	}
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.InvestAssetsReportServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.DETAIL_ACTION%>";
	var selObj = mainFrm.organizationId;
	selectSpecialOptionByItem(selObj, organizationId);
	mainFrm.companyName.value = companyName;
    var style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no';
    window.open("/public/waiting2.htm", "assWin", style);
    mainFrm.target = "assWin";
    mainFrm.submit();
}
function do_SelectContent() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_CONTENT%>";
        var dialogWidth = 48;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        } else {
            mainFrm.contentName.value = "";
            mainFrm.contentCode.value = "";
        }
}
</script>