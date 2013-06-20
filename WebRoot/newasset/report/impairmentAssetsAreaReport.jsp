<%@ page import="com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO"%>
<%@ page import="java.math.BigDecimal"%>

<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/WebLibary/js/LookUp.js"></script>
<title>地域资产构成分布(减值)</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.AreaImpairmentAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
     printTitleBar("资产基础报表>>地域资产构成分布(减值)")
</script>
    <table width="100%" border="0" class="queryHeadBg">
		<tr>
            <td width="10%" align="right">公司：</td>
			<td width="30%" align="left">
                <select name="organizationId" style="width:50%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <%--<td width="10%" align="right">会计期间：</td>--%>
            <%--<td width="30%" align="left">--%>
                <%--<input style="width:50%" type="text" name="accountingPeriod" value="<%=StrUtil.nullToString(dto.getAccountingPeriod())%>" title="点击选择会计期" readonly class="noEmptyInput" onclick="gfPop.fPopCalendar(accountingPeriod)">--%>
            <%--</td>--%>
            <%--<td width="7%" align="right">年份：</td>
            <td width="5%"><select style="width:100%" name="year"><%=yearOption%></select></td>
            <td width="7%" align="right">月份：</td>
            <td width="4%"><select style="width:100%" name="month"><%=monthOption%></select></td>--%>
            <td width="5%" align="right">会计期间：</td>
            <td width="14%"><select style="width:40%" name="year"><%=yearOption%></select>—<select style="width:35%" name="month"><%=monthOption%></select></td>
			<td width="20%" align="right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();"> &nbsp;&nbsp; &nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
    <input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="areaAssetsType" value="<%=dto.getAreaAssetsType()%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:42px;left:1px;width:840px">
	<table class="headerTable" border="1" width="250%">
		<tr height="22">
			<td width="5%" align="center" >类</td>
			<td width="6%" align="center" >项</td>
			<td width="5%" align="center" >省市区域固定资产当期减值额</td>
			<td width="3%" align="center" >省市区域资产数量</td>
			<td width="5%" align="center" >县城区域固定资产当期减值额</td>
			<td width="3%" align="center" >县城区域资产数量</td>
			<td width="5%" align="center" >农村区域固定资产当期减值额</td>
			<td width="3%" align="center" >农村区域资产数量</td>
			
            <td width="5%" align="center" >省市区域占当期资产总数比重</td>
            <td width="5%" align="center" >省市区域占当期资产减值额比重</td>
            
			<td width="5%" align="center" >县城区域占当期资产总数比重</td>
			<td width="5%" align="center" >县城区域占当期资产减值额比重</td>
			
			<td width="5%" align="center" >农村区域占当期资产总数比重</td>
			<td width="5%" align="center" >农村区域占当期资产减值额比重</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:65px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="250%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String ASSETS_SPECIES="";
        Double sumCityCost = 0d;
        Double sumCountyCost = 0d;
        Double sumVillageCost = 0d;
        Integer sumCityCount = 0;
        Integer sumCountyCount = 0;
        Integer sumVillageCount = 0;
        
        Float sumCityRate = 0f;
        Float sumCountyRate = 0f;
        Float sumVillageRate = 0f;
        
        Float sumCityImpairRate = 0f;
        Float sumCountyImpairRate = 0f;
        Float sumVillageImpairRate = 0f;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
			<td width="5%" align="right"><%=row.getValue("ASSETS_SPECIES")%></td>
			<td width="6%" align="right"><%=row.getValue("ASSETS_NAPE")%></td>
            <td width="5%" align="right"><%=row.getValue("CITY_IMPAIR_AMOUNT")%></td>
            <td width="3%" align="right"><%=row.getValue("CITY_COUNT")%></td>
            <td width="5%" align="right"><%=row.getValue("COUNTY_IMPAIR_AMOUNT")%></td>
            <td width="3%" align="right"><%=row.getValue("COUNTY_COUNT")%></td>
            <td width="5%" align="right"><%=row.getValue("VILLAGE_IMPAIR_AMOUNT")%></td>
            <td width="3%" align="right"><%=row.getValue("VILLAGE_COUNT")%></td>
            
            <td width="5%" align="right"><%=row.getValue("CITY_RATE") %></td>
            <td width="5%" align="right"><%=row.getValue("COUNTY_RATE") %></td>
            <td width="5%" align="right"><%=row.getValue("VILLAGE_RATE") %></td>
            
            <td width="5%" align="right"><%=row.getValue("CITY_IMPAIR_RATE") %></td>
            <td width="5%" align="right"><%=row.getValue("COUNTY_IMPAIR_RATE") %></td>
            <td width="5%" align="right"><%=row.getValue("VILLAGE_IMPAIR_RATE") %></td>
        </tr>
<%
			if(!row.getValue("CITY_IMPAIR_AMOUNT").equals("")){
				sumCityCost += Double.parseDouble(row.getValue("CITY_COST") + "");
			}
			if(!row.getValue("CITY_COUNT").equals("")){
				sumCityCount += Integer.parseInt(row.getValue("CITY_COUNT") + "");
			}
			if(!row.getValue("COUNTY_IMPAIR_AMOUNT").equals("")){
				sumCountyCost += Double.parseDouble(row.getValue("COUNTY_COST") + "");
			}
			if(!row.getValue("COUNTY_COUNT").equals("")){
				sumCountyCount += Integer.parseInt(row.getValue("COUNTY_COUNT") + "");
			}
			if(!row.getValue("VILLAGE_IMPAIR_AMOUNT").equals("")){
				sumVillageCost += Double.parseDouble(row.getValue("VILLAGE_COST") + "");
			}
			if(!row.getValue("VILLAGE_COUNT").equals("")){
				sumVillageCount += Integer.parseInt(row.getValue("VILLAGE_COUNT") + "");
			}
			
			if(!row.getValue("CITY_RATE").equals("") && !row.getValue("CITY_RATE").equals("%")){
				sumCityRate += Float.parseFloat(row.getValue("CITY_RATE").toString().substring(0, row.getValue("CITY_RATE").toString().indexOf("%")));
			}
			if(!row.getValue("CITY_RATE").equals("") && !row.getValue("COUNTY_RATE").equals("%")){
				sumCountyRate += Float.parseFloat(row.getValue("COUNTY_RATE").toString().substring(0, row.getValue("COUNTY_RATE").toString().indexOf("%")));
			}
			if(!row.getValue("CITY_RATE").equals("") && !row.getValue("VILLAGE_RATE").equals("%")){
				sumVillageRate += Float.parseFloat(row.getValue("VILLAGE_RATE").toString().substring(0, row.getValue("VILLAGE_RATE").toString().indexOf("%")));
			}
			
			if(!row.getValue("CITY_IMPAIR_RATE").equals("") && !row.getValue("CITY_IMPAIR_RATE").equals("%")){
				sumCityImpairRate += Float.parseFloat(row.getValue("CITY_IMPAIR_RATE").toString().substring(0, row.getValue("CITY_IMPAIR_RATE").toString().indexOf("%")));
			}
			if(!row.getValue("COUNTY_IMPAIR_RATE").equals("") && !row.getValue("COUNTY_IMPAIR_RATE").equals("%")){
				sumCountyImpairRate += Float.parseFloat(row.getValue("COUNTY_IMPAIR_RATE").toString().substring(0, row.getValue("COUNTY_IMPAIR_RATE").toString().indexOf("%")));
			}
			if(!row.getValue("VILLAGE_IMPAIR_RATE").equals("") && !row.getValue("VILLAGE_IMPAIR_RATE").equals("%")){
				sumVillageImpairRate += Float.parseFloat(row.getValue("VILLAGE_IMPAIR_RATE").toString().substring(0, row.getValue("VILLAGE_IMPAIR_RATE").toString().indexOf("%")));
			}
		}
        
%>
		<tr height="22">
			<td align="right" colspan = "2">合计：</td>
            <td align="right"><%=sumCityCost != 0d ? BigDecimal.valueOf(sumCityCost).toString().substring(0, BigDecimal.valueOf(sumCityCost).toString().indexOf(".") + 3): "" %></td>
            <td align="right"><%=sumCityCount%></td>
            <td align="right"><%=sumCountyCost != 0d ? BigDecimal.valueOf(sumCountyCost).toString().substring(0, BigDecimal.valueOf(sumCountyCost).toString().indexOf(".") + 3): "" %></td>
            <td align="right"><%=sumCountyCount%></td>
            <td align="right"><%=sumVillageCost != 0d ? BigDecimal.valueOf(sumVillageCost).toString().substring(0, BigDecimal.valueOf(sumVillageCost).toString().indexOf(".") + 3) : ""%></td>
            <td align="right"><%=sumVillageCount%></td>
            <td align="right"><%=sumCityRate.toString().length() - sumCityRate.toString().indexOf(".") > 3 ? sumCityRate.toString().substring(0, sumCityRate.toString().indexOf(".")+4) :sumCityRate %>%</td>
            <td align="right"><%=sumCountyRate.toString().length() - sumCountyRate.toString().indexOf(".") > 3 ? sumCountyRate.toString().substring(0, sumCountyRate.toString().indexOf(".")+4) :sumCountyRate %>%</td>
            <td align="right"><%=sumVillageRate.toString().length() - sumVillageRate.toString().indexOf(".") > 3 ? sumVillageRate.toString().substring(0, sumVillageRate.toString().indexOf(".")+4) :sumVillageRate %>%</td>
            
            <td align="right"><%=sumCityImpairRate.toString().length() - sumCityImpairRate.toString().indexOf(".") > 3 ? sumCityImpairRate.toString().substring(0, sumCityImpairRate.toString().indexOf(".")+4) :sumCityImpairRate %>%</td>
            <td align="right"><%=sumCountyImpairRate.toString().length() - sumCountyImpairRate.toString().indexOf(".") > 3 ? sumCountyImpairRate.toString().substring(0, sumCountyImpairRate.toString().indexOf(".")+4) :sumCountyImpairRate %>%</td>
            <td align="right"><%=sumVillageImpairRate.toString().length() - sumVillageImpairRate.toString().indexOf(".") > 3 ? sumVillageImpairRate.toString().substring(0, sumVillageImpairRate.toString().indexOf(".")+4) :sumVillageImpairRate %></td>
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
//	if(accountingPeriod == null || accountingPeriod == ""){
//		alert("会计期间不能为空！");
//		mainFrm.accountingPeriod.focus();
//		return;
//	}
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AreaImpairmentAssetsReportServlet";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
//	var accountingPeriod = mainFrm.accountingPeriod.value;
//	if(accountingPeriod == null || accountingPeriod == ""){
//		alert("会计期间不能为空！");
//		mainFrm.accountingPeriod.focus();
//		return;
//	}
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AreaImpairmentAssetsReportServlet";
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