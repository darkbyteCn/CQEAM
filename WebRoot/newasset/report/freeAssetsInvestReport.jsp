<%@ page import="com.sino.ams.newasset.report.dto.DeptAssetsReportDTO"%>
<%@ page import="java.math.BigDecimal"%>
<%--
  Author:		李轶
  Date: 2009-5-19
  Time: 10:10:10
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
<title>投资方向分类(闲置)</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	RequestParser parser = new RequestParser();
	parser.transData(request);
	DeptAssetsReportDTO dto = (DeptAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.InvestFreeAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产基础报表>>投资方向分类(闲置)")
</script>
	<table width="100%" border="0">
		<tr>
            <td width="15%" align="right">公司：</td>
			<td width="25%" align="left">
                <select class="select_style1" name="organizationId" style="width:90%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <td width="15%" align="right">会计期间：</td>
            <td width="25%"><select class="select_style1" style="width:20%" name="year"><%=yearOption%></select>—<select class="select_style1" style="width:15%" name="month"><%=monthOption%></select></td>
			<td width="20%"  align="right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
	<input name="act" type="hidden">
	<input name="companyName" type="hidden">
</form>

<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:840px">
	<table class="eamDbHeaderTable" border="1" width="130%" height="40">
		<tr height="20">
			<td width="8%" align="center" rowspan="2" height="10">公司</td>
			<td width="20%" align="center" rowspan="2" height="10">投资项目分类</td>
			<td width="6%" align="center" rowspan="2" height="10">资产数量</td>
			<td width="6%" align="center" rowspan="2" height="10">投资额</td>
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总额比重</td>
            <td width="6%" align="center" rowspan="2" height="10">较去年同期增长率</td>
            <td width="7%" align="center" colspan="3" height="5">近3年增长率</td>
		</tr>
		<tr class="eamDbHeaderTr">
            <td width="3%" align="center" height="5"><%=dto.getYear().equals("") ? "2006" : Integer.parseInt(dto.getYear())-3 %></td>
            <td width="3%" align="center" height="5"><%=dto.getYear().equals("") ? "2007" : Integer.parseInt(dto.getYear())-2 %></td>
            <td width="3%" align="center" height="5"><%=dto.getYear().equals("") ? "2008" : Integer.parseInt(dto.getYear())-1 %></td>
        </tr>
	</table>
</div>

<div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:86px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="130%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
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
			<td width="6%" align="right"><%=row.getValue("SUM_COUNT")%></td>
			<td width="6%"  align="right" ><%=row.getValue("SUM_COST")%></td>		<!-- 原值 -->
            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE").equals("%") ? "0%" : row.getValue("ASSETS_RATE")%></td>
            <td width="6%" align="right"><%=row.getValue("LAST_YEAR_RATE").equals("%") ? "0%" : row.getValue("LAST_YEAR_RATE")%></td>
            <td width="3%" align="right"><%=row.getValue("THREE_YEER_THREE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_THREE_RATE")%></td>
            <td width="3%" align="right"><%=row.getValue("THREE_YEER_TWO_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_TWO_RATE")%></td>
            <td width="3%" align="right"><%=row.getValue("THREE_YEER_ONE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_ONE_RATE")%></td>
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
//	var accountingPeriod = mainFrm.accountingPeriod.value;
//	if(accountingPeriod != null && accountingPeriod != ""){
		mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
		mainFrm.target = "_self";
		mainFrm.submit();
		document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
//	} else {
//		alert("会计期间不能为空！");
//		mainFrm.accountingPeriod.focus();
//	}
}

function do_Export() {
//	var accountingPeriod = mainFrm.accountingPeriod.value;
//	if(accountingPeriod != null && accountingPeriod != ""){
	    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
		mainFrm.target = "_self";
	    mainFrm.submit();
//	} else {
//		alert("会计期间不能为空！");
//		mainFrm.accountingPeriod.focus();
//	}
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