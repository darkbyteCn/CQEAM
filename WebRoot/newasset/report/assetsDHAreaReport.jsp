<%@ page import="com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO"%>
<%@ page import="java.math.BigDecimal"%>
<%--
  Author:		李轶
  Date: 2009-5-25
  Time: 11:53:14
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
<title>地域资产构成分布(重要低耗)</title>
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
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.DHAreaAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    var areaAssetsType = "<%=dto.getAreaAssetsType()%>";
    printTitleBar("资产基础报表>>地域资产构成分布(重要低耗)")
</script>
    
    <table width="100%" border="0">
		<tr>
            <td width="10%" align="right">公司：</td>
			<td width="30%" align="left">
                <select class="select_style1" name="organizationId" style="width:50%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <td width="10%" align="right">会计期间：</td>
            <td width="30%" colspan="3"><select class="select_style1" style="width:40%" name="year"><%=yearOption%></select>—<select class="select_style1" style="width:35%" name="month"><%=monthOption%></select></td>
			<td width="20%"  align="right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
    
    <input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="areaAssetsType" value="<%=dto.getAreaAssetsType()%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:840px">
	<table class="eamDbHeaderTable" border="1" width="330%" height="40">
		<tr height="20">
			<td width="5%" align="center" rowspan="2" height="10">类</td>
			<td width="6%" align="center" rowspan="2" height="10">项</td>
            <td width="3%" align="center" rowspan="2" height="10">省市区域资产数量</td>
            <td width="3%" align="center" rowspan="2" height="10">省市区域资产原值</td>
            <td width="3%" align="center" rowspan="2" height="10">县城区域资产数量</td>
            <td width="3%" align="center" rowspan="2" height="10">县城区域资产原值</td>
            <td width="3%" align="center" rowspan="2" height="10">农村区域资产数量</td>
            <td width="3%" align="center" rowspan="2" height="10">农村区域资产原值</td>

            <td width="5%" align="center" rowspan="2" height="10">省市区域占当期资产总额比重</td>
			<td width="5%" align="center" rowspan="2" height="10">县城区域占当期资产总额比重</td>
			<td width="5%" align="center" rowspan="2" height="10">农村区域占当期资产总额比重</td>
			
			<td width="4%" align="center" rowspan="2" height="10">省市区域比上月增长率</td>
			<td width="4%" align="center" rowspan="2" height="10">县城区域比上月增长率</td>
			<td width="4%" align="center" rowspan="2" height="10">农村区域比上月增长率</td>
			
			<td width="4%" align="center" rowspan="2" height="10">省市区域较去年同期增长率</td>
			<td width="4%" align="center" rowspan="2" height="10">县城区域较去年同期增长率</td>
			<td width="4%" align="center" rowspan="2" height="10">农村区域较去年同期增长率</td>
			<td width="20%" align="center" colspan="9" height="5">近3年增长率</td>
		</tr>
		<tr class="eamDbHeaderTr">
			<td width="3%" align="center" height="4"><%=dto.getYear().equals("") ? "省市区域2006" : "省市区域" + (Integer.parseInt(dto.getYear())-3) %></td>
			<td width="3%" align="center" height="4"><%=dto.getYear().equals("") ? "县城区域2006" : "县城区域" + (Integer.parseInt(dto.getYear())-3) %></td>
			<td width="3%" align="center" height="4"><%=dto.getYear().equals("") ? "农村区域2006" : "农村区域" + (Integer.parseInt(dto.getYear())-3) %></td>
			
            <td width="3%" align="center" height="4"><%=dto.getYear().equals("") ? "省市区域2007" : "省市区域" + (Integer.parseInt(dto.getYear())-2) %></td>
            <td width="3%" align="center" height="4"><%=dto.getYear().equals("") ? "县城区域2007" : "县城区域" + (Integer.parseInt(dto.getYear())-2) %></td>
            <td width="3%" align="center" height="4"><%=dto.getYear().equals("") ? "农村区域2007" : "农村区域" + (Integer.parseInt(dto.getYear())-2) %></td>
            
            <td width="3%" align="center" height="4"><%=dto.getYear().equals("") ? "省市区域2008" : "省市区域" + (Integer.parseInt(dto.getYear())-1) %></td>	
            <td width="3%" align="center" height="4"><%=dto.getYear().equals("") ? "县城区域2008" : "县城区域" + (Integer.parseInt(dto.getYear())-1) %></td>	
            <td width="3%" align="center" height="4"><%=dto.getYear().equals("") ? "农村区域2008" : "农村区域" + (Integer.parseInt(dto.getYear())-1) %></td>	
        </tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:70%;width:857px;position:absolute;top:86px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="330%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
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
        
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
			if(!row.getValue("ASSETS_SPECIES").equals("类为空")){
%>
		<tr height="22">
			<td width="5%" align="right"><%=row.getValue("ASSETS_SPECIES")%></td>
			<td width="6%" align="right"><%=row.getValue("ASSETS_NAPE")%></td>
            <td width="3%" align="right"><%=row.getValue("CITY_COUNT")%></td>
            <td width="3%" align="right"><%=row.getValue("CITY_COST")%></td>
            <td width="3%" align="right"><%=row.getValue("COUNTY_COUNT")%></td>
            <td width="3%" align="right"><%=row.getValue("COUNTY_COST")%></td>
            <td width="3%" align="right"><%=row.getValue("VILLAGE_COUNT")%></td>
            <td width="3%" align="right"><%=row.getValue("VILLAGE_COST")%></td>

            <td width="5%" align="right"><%=row.getValue("CITY_RATE").equals("%") ? "0%" : row.getValue("CITY_RATE") %></td>
            <td width="5%" align="right"><%=row.getValue("COUNTY_RATE").equals("%") ? "0%" : row.getValue("COUNTY_RATE") %></td>
            <td width="5%" align="right"><%=row.getValue("VILLAGE_RATE").equals("%") ? "0%" : row.getValue("VILLAGE_RATE") %></td>
            
            <td width="4%" align="right"><%=row.getValue("CITY_LAST_MONTH_RATE").equals("%") ? "0%" : row.getValue("CITY_LAST_MONTH_RATE") %></td>
            <td width="4%" align="right"><%=row.getValue("COUNTY_LAST_MONTH_RATE").equals("%") ? "0%" : row.getValue("COUNTY_LAST_MONTH_RATE") %></td>
            <td width="4%" align="right"><%=row.getValue("VILLAGE_LAST_MONTH_RATE").equals("%") ? "0%" : row.getValue("VILLAGE_LAST_MONTH_RATE") %></td>
            
            <td width="4%" align="right"><%=row.getValue("CITY_LAST_YEAR_RATE").equals("%") ? "0%" : row.getValue("CITY_LAST_YEAR_RATE") %></td>
            <td width="4%" align="right"><%=row.getValue("COUNTY_LAST_YEAR_RATE").equals("%") ? "0%" : row.getValue("COUNTY_LAST_YEAR_RATE") %></td>
            <td width="4%" align="right"><%=row.getValue("VILLAGE_LAST_YEAR_RATE").equals("%") ? "0%" : row.getValue("VILLAGE_LAST_YEAR_RATE") %></td>
            
            <td width="3%" align="right"><%=row.getValue("CITY_LAST_THREE_YEAR_RATE").equals("%") ? "0%" : row.getValue("CITY_LAST_THREE_YEAR_RATE") %></td>
            <td width="3%" align="right"><%=row.getValue("COUNTY_LAST_THREE_YEAR_RATE").equals("%") ? "0%" : row.getValue("COUNTY_LAST_THREE_YEAR_RATE") %></td>
            <td width="3%" align="right"><%=row.getValue("VILLAGE_LAST_THREE_YEAR_RATE").equals("%") ? "0%" : row.getValue("VILLAGE_LAST_THREE_YEAR_RATE") %></td>
            
            <td width="3%" align="right"><%=row.getValue("CITY_LAST_TWO_YEAR_RATE").equals("%") ? "0%" : row.getValue("CITY_LAST_TWO_YEAR_RATE") %></td>
            <td width="3%" align="right"><%=row.getValue("COUNTY_LAST_TWO_YEAR_RATE").equals("%") ? "0%" : row.getValue("COUNTY_LAST_TWO_YEAR_RATE") %></td>
            <td width="3%" align="right"><%=row.getValue("VILLAGE_LAST_TWO_YEAR_RATE").equals("%") ? "0%" : row.getValue("VILLAGE_LAST_TWO_YEAR_RATE") %></td>
            
            <td width="3%" align="right"><%=row.getValue("CITY_LAST_ONE_YEAR_RATE").equals("%") ? "0%" : row.getValue("CITY_LAST_ONE_YEAR_RATE") %></td>
            <td width="3%" align="right"><%=row.getValue("COUNTY_LAST_ONE_YEAR_RATE").equals("%") ? "0%" : row.getValue("COUNTY_LAST_ONE_YEAR_RATE") %></td>
            <td width="3%" align="right"><%=row.getValue("VILLAGE_LAST_ONE_YEAR_RATE").equals("%") ? "0%" : row.getValue("VILLAGE_LAST_ONE_YEAR_RATE") %></td>
        </tr>
<%
			if(!row.getValue("CITY_COST").equals("")){
				sumCityCost += Double.parseDouble(row.getValue("CITY_COST") + "");
			}
			if(!row.getValue("CITY_COUNT").equals("")){
				sumCityCount += Integer.parseInt(row.getValue("CITY_COUNT") + "");
			}
			if(!row.getValue("COUNTY_COST").equals("")){
				sumCountyCost += Double.parseDouble(row.getValue("COUNTY_COST") + "");
			}
			if(!row.getValue("COUNTY_COUNT").equals("")){
				sumCountyCount += Integer.parseInt(row.getValue("COUNTY_COUNT") + "");
			}
			if(!row.getValue("VILLAGE_COST").equals("")){
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
			}
		}
        
%>
		<tr height="22">
			<td width="6%" align="right" colspan = "2">合计：</td>
            <td width="4%" align="right"><%=sumCityCount%></td>
            <td width="4%" align="right"><%=BigDecimal.valueOf(sumCityCost).toString().length() - BigDecimal.valueOf(sumCityCost).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumCityCost).toString().substring(0, BigDecimal.valueOf(sumCityCost).toString().indexOf(".") + 3) : sumCityCost %></td>
            <td width="4%" align="right"><%=sumCountyCount%></td>
            <td width="4%" align="right"><%=BigDecimal.valueOf(sumCountyCost).toString().length() - BigDecimal.valueOf(sumCountyCost).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumCountyCost).toString().substring(0, BigDecimal.valueOf(sumCountyCost).toString().indexOf(".") + 3) : sumCountyCost %></td>
            <td width="4%" align="right"><%=sumVillageCount%></td>
            <td width="4%" align="right"><%=BigDecimal.valueOf(sumVillageCost).toString().length() - BigDecimal.valueOf(sumVillageCost).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumVillageCost).toString().substring(0, BigDecimal.valueOf(sumVillageCost).toString().indexOf(".") + 3) : sumVillageCost %></td>
            <td width="4%" align="right"><%=sumCityRate %>%</td>
            <td width="4%" align="right"><%=sumCountyRate.toString().length() - sumCountyRate.toString().indexOf(".") > 3 ? sumCountyRate.toString().substring(0, sumCountyRate.toString().indexOf(".")+4) :sumCountyRate %>%</td>
            <td width="4%" align="right"><%=sumVillageRate%>%</td>
            <td width="5%" align="right" colspan = "15"></td>
		</tr>
<%        
	}
%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:89%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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