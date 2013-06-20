<%@ page import="com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO"%>
<%@ page import="java.math.BigDecimal"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-3-5
  Time: 18:02:08
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
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
    SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<title>
<%
    if(dto.getSpecialAssetsType().equals("APPLY_DOMAIN")) {
%>
按应用领域统计
<%
    } else if(dto.getSpecialAssetsType().equals("SHARE_THING")) {
%>
按共享情况统计
<%} else {
%>
专业资产构成报表(新增)
<%}%>

</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.SpecialNewAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
var specialAssetsType = "<%=dto.getSpecialAssetsType()%>";
if (specialAssetsType == "APPLY_DOMAIN") {
    printTitleBar("资产基础报表>>按应用领域统计(新增)")
} else if (specialAssetsType == "SHARE_THING") {
    printTitleBar("资产基础报表>>按共享情况统计(新增)")
} else if (specialAssetsType == "APPLY_DOMAIN_TRANS") {
    printTitleBar("资产基础报表>>按应用领域统计(调拨)")
} else {
    printTitleBar("资产基础报表>>专业资产构成统计(新增)")
}
</script>
	<table width="100%" border="0">
		<tr>
			<td width="15%" align="right">公司：</td>
			<td width="25%" align="left">
                <select class="select_style1" name="organizationId" style="width:65%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <td width="15%" align="right">会计期间：</td>
            <td width="25%"><select class="select_style1" style="width:30%" name="year"><%=yearOption%></select>—<select class="select_style1" style="width:25%" name="month"><%=monthOption%></select></td>
            <td width="20%" align="right">
                <img src="/images/eam_images/search.jpg"  onclick="do_Search();">
                <img src="/images/eam_images/export.jpg"  onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
	<input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="specialAssetsType" value="<%=dto.getSpecialAssetsType()%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:840px">
	<table class="eamDbHeaderTable" border="1" width="200%" height="40">
<%
if(dto.getSpecialAssetsType().equals("APPLY_DOMAIN") || dto.getSpecialAssetsType().equals("APPLY_DOMAIN_TRANS")) {
%>
        <tr height="20">
			<td width="8%" align="center" rowspan="2" height="10">公司</td>
			<td width="12%" align="center" rowspan="2" height="10">应用领域</td>
			
			<td width="6%" align="center" rowspan="2" height="10">资产数量</td>
			<td width="6%" align="center" rowspan="2" height="10">资产原值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计折旧</td>
            <td width="6%" align="center" rowspan="2" height="10">资产净值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计减值准备</td>
            <td width="6%" align="center" rowspan="2" height="10">资产净额</td>
			
            <td width="6%" align="center" rowspan="2" height="10">当期折旧</td>
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总额比重</td>
            <td width="6%" align="center" rowspan="2" height="10">较去年同期增长率</td>
            <td width="12%" align="center" colspan="3" height="5">近3年增长率</td>
        </tr>
        <tr class="eamDbHeaderTr">
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2006" : Integer.parseInt(dto.getYear())-3 %></td>
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2007" : Integer.parseInt(dto.getYear())-2 %></td>
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2008" : Integer.parseInt(dto.getYear())-1 %></td>
        </tr>
<%
} else if (dto.getSpecialAssetsType().equals("SHARE_THING")) {
%>
        <tr height="20">
			<td width="7%" align="center" rowspan="2" height="10">共享类型分类</td>
			<td width="6%" align="center" rowspan="2" height="10">资产数量</td>
			<td width="6%" align="center" rowspan="2" height="10">资产原值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计折旧</td>
            <td width="6%" align="center" rowspan="2" height="10">资产净值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计减值准备</td>
            <td width="6%" align="center" rowspan="2" height="10">资产净额</td>
			
            <td width="6%" align="center" rowspan="2" height="10">当期折旧</td>
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总额比重</td>
            <td width="6%" align="center" rowspan="2" height="10">比上月增长率</td>
            <td width="6%" align="center" rowspan="2" height="10">较去年同期增长率</td>
            <td width="12%" align="center" colspan="3" height="5">近3年增长率</td>
        </tr>
        <tr class="eamDbHeaderTr">
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2006" : Integer.parseInt(dto.getYear())-3 %></td>
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2007" : Integer.parseInt(dto.getYear())-2 %></td>
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2008" : Integer.parseInt(dto.getYear())-1 %></td>
		</tr>
<%} else {
%>
		<tr height="20">
			<td width="8%" align="center" rowspan="2" height="10">类</td>
			<td width="10%" align="center" rowspan="2" height="10">项</td>
			
			<td width="6%" align="center" rowspan="2" height="10">资产数量</td>
			<td width="6%" align="center" rowspan="2" height="10">资产原值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计折旧</td>
            <td width="6%" align="center" rowspan="2" height="10">资产净值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计减值准备</td>
            <td width="6%" align="center" rowspan="2" height="10">资产净额</td>
			
            <td width="6%" align="center" rowspan="2" height="10">当期折旧</td>
            
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总额比重</td>
            <td width="6%" align="center" rowspan="2" height="10">比上月增长率</td>
            <td width="6%" align="center" rowspan="2" height="10">较去年同期增长率</td>
            <td width="12%" align="center" colspan="3" height="5">近3年增长率</td>
        </tr>
        <tr class="eamDbHeaderTr">
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2006" : Integer.parseInt(dto.getYear())-3 %></td>
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2007" : Integer.parseInt(dto.getYear())-2 %></td>
            <td width="4%" align="center" height="5"><%=dto.getYear().equals("") ? "2008" : Integer.parseInt(dto.getYear())-1 %></td>
        </tr>

<%}%>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:86px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String ASSETS_SPECIES="";
        boolean bkColor = false;
        boolean isNew = false;
        
        Double sumCost = 0d;
        Long sumCount = 0L;
        Double sumDeprnReserve = 0d;
        Double sumNetBookValue = 0d;
        Double sumImpairmentReserve = 0d;
        Double sumLimitValue = 0d;
        Double sumPtdDeprn = 0d;
        Float sumRate = 0f;
        
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            if (dto.getSpecialAssetsType().equals("")) {
                bkColor=StrUtil.isEmpty(row.getValue("ASSETS_NAPE"));
                isNew= ASSETS_SPECIES.equals(row.getStrValue("ASSETS_SPECIES"));
                ASSETS_SPECIES= row.getStrValue("ASSETS_SPECIES");
            }
            if(!ASSETS_SPECIES.equals("类项为空")){
%>
<%
            if (dto.getSpecialAssetsType().equals("APPLY_DOMAIN") || dto.getSpecialAssetsType().equals("APPLY_DOMAIN_TRANS")) {
%>
                <tr height="22">
                    <td width="8%" align="right"><%=row.getValue("COMPANY")%></td>
                    <td width="12%" align="right"><%=row.getValue("FA_DESCRIPTION")%></td>
                    <td width="6%" align="right"><%=row.getValue("SUM_COUNT")%></td>
                    <td width="6%"  align="right" ><%=row.getValue("SUM_COST")%></td>		<!-- 原值 -->
					<td width="6%" align="right"><%=row.getValue("DEPRN_RESERVE")%></td>	<!-- 累计折旧 -->
					<td width="6%" align="right"><%=row.getValue("NET_BOOK_VALUE")%></td>   <!-- 资产净值 -->
					<td width="6%" align="right"><%=row.getValue("IMPAIRMENT_RESERVE")%></td>   <!-- 累计减值准备 -->
		            <td width="6%" align="right"><%=row.getValue("LIMIT_VALUE")%></td>		<!-- 资产净额 -->
					
		            <td width="6%" align="right"><%=row.getValue("PTD_DEPRN")%></td>
					
		            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE").equals("%") ? "0%" : row.getValue("ASSETS_RATE")%></td>
		            <td width="6%" align="right"><%=row.getValue("LAST_YEAR_RATE").equals("%") ? "0%" : row.getValue("LAST_YEAR_RATE")%></td>
		
		            <td width="4%" align="right"><%=row.getValue("THREE_YEER_THREE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_THREE_RATE")%></td>
		            <td width="4%" align="right"><%=row.getValue("THREE_YEER_TWO_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_TWO_RATE")%></td>
		            <td width="4%" align="right"><%=row.getValue("THREE_YEER_ONE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_ONE_RATE")%></td>
                </tr>
<%
            } else if (dto.getSpecialAssetsType().equals("SHARE_THING")) {
%>
                <tr height="22">
                    <td width="7%" align="right"><%=row.getValue("SHARE_STATUS")%></td>
                    <td width="6%" align="right"><%=row.getValue("SUM_COUNT")%></td>
                    <td width="6%"  align="right" ><%=row.getValue("SUM_COST")%></td>		<!-- 原值 -->
					<td width="6%" align="right"><%=row.getValue("DEPRN_RESERVE")%></td>	<!-- 累计折旧 -->
					<td width="6%" align="right"><%=row.getValue("NET_BOOK_VALUE")%></td>   <!-- 资产净值 -->
					<td width="6%" align="right"><%=row.getValue("IMPAIRMENT_RESERVE")%></td>   <!-- 累计减值准备 -->
		            <td width="6%" align="right"><%=row.getValue("LIMIT_VALUE")%></td>		<!-- 资产净额 -->
					
		            <td width="6%" align="right"><%=row.getValue("PTD_DEPRN")%></td>
		            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE").equals("%") ? "0%" : row.getValue("ASSETS_RATE")%></td>
		            <td width="6%" align="right"><%=row.getValue("LAST_MONTH_RATE").equals("%") ? "0%" : row.getValue("LAST_MONTH_RATE")%></td>
		            <td width="6%" align="right"><%=row.getValue("LAST_YEAR_RATE").equals("%") ? "0%" : row.getValue("LAST_YEAR_RATE")%></td>
		
		            <td width="4%" align="right"><%=row.getValue("THREE_YEER_THREE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_THREE_RATE")%></td>
		            <td width="4%" align="right"><%=row.getValue("THREE_YEER_TWO_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_TWO_RATE")%></td>
		            <td width="4%" align="right"><%=row.getValue("THREE_YEER_ONE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_ONE_RATE")%></td>
                </tr>
<%
            } else {
%>
                <tr height="22" <% if (bkColor){%> bgcolor="#EFEFEF" <%}%> >
                    <td width="8%"><%=isNew?"":ASSETS_SPECIES%></td>
                    <td width="10%" align="right"><%=row.getValue("ASSETS_NAPE")%></td>
                    <td width="6%" align="right"><%=row.getValue("SUM_COUNT")%></td>
                    <td width="6%"  align="right" ><%=row.getValue("SUM_COST")%></td>		<!-- 原值 -->
					<td width="6%" align="right"><%=row.getValue("DEPRN_RESERVE")%></td>	<!-- 累计折旧 -->
					<td width="6%" align="right"><%=row.getValue("NET_BOOK_VALUE")%></td>   <!-- 资产净值 -->
					<td width="6%" align="right"><%=row.getValue("IMPAIRMENT_RESERVE")%></td>   <!-- 累计减值准备 -->
		            <td width="6%" align="right"><%=row.getValue("LIMIT_VALUE")%></td>		<!-- 资产净额 -->
					
		            <td width="6%" align="right"><%=row.getValue("PTD_DEPRN")%></td>
					
		            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE").equals("%") ? "0%" : row.getValue("ASSETS_RATE")%></td>
		            <td width="6%" align="right"><%=row.getValue("LAST_MONTH_RATE").equals("%") ? "0%" : row.getValue("LAST_MONTH_RATE")%></td>
		            <td width="6%" align="right"><%=row.getValue("LAST_YEAR_RATE").equals("%") ? "0%" : row.getValue("LAST_YEAR_RATE")%></td>
		
		            <td width="4%" align="right"><%=row.getValue("THREE_YEER_THREE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_THREE_RATE")%></td>
		            <td width="4%" align="right"><%=row.getValue("THREE_YEER_TWO_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_TWO_RATE")%></td>
		            <td width="4%" align="right"><%=row.getValue("THREE_YEER_ONE_RATE").equals("%") ? "0%" : row.getValue("THREE_YEER_ONE_RATE")%></td>
                </tr>
<%
            	}
				if(bkColor || dto.getSpecialAssetsType().equals("APPLY_DOMAIN") || dto.getSpecialAssetsType().equals("APPLY_DOMAIN_TRANS") || dto.getSpecialAssetsType().equals("SHARE_THING")){
					if(row.getValue("SUM_COUNT") != null && !row.getValue("SUM_COUNT").equals("")){
						sumCount += Long.parseLong(row.getValue("SUM_COUNT").toString());
					}					
					if(row.getValue("SUM_COST") != null && !"".equals(row.getValue("SUM_COST"))){
						sumCost += Double.parseDouble(row.getValue("SUM_COST").toString());
					}
					if(row.getValue("DEPRN_RESERVE") != null && !"".equals(row.getValue("DEPRN_RESERVE"))){
						sumDeprnReserve += Double.parseDouble(row.getValue("DEPRN_RESERVE").toString());
					}
					if(row.getValue("NET_BOOK_VALUE") != null && !"".equals(row.getValue("NET_BOOK_VALUE"))){
						sumNetBookValue += Double.parseDouble(row.getValue("NET_BOOK_VALUE").toString());
					}
					if(row.getValue("IMPAIRMENT_RESERVE") != null && !"".equals(row.getValue("IMPAIRMENT_RESERVE"))){
						sumImpairmentReserve += Double.parseDouble(row.getValue("IMPAIRMENT_RESERVE").toString());
					}
					if(row.getValue("LIMIT_VALUE") != null && !"".equals(row.getValue("LIMIT_VALUE"))){
						sumLimitValue += Double.parseDouble(row.getValue("LIMIT_VALUE").toString());
					}
					if(row.getValue("PTD_DEPRN") != null && !"".equals(row.getValue("PTD_DEPRN"))){
						sumPtdDeprn += Double.parseDouble(row.getValue("PTD_DEPRN").toString());
					}
					if(!row.getValue("ASSETS_RATE").equals("") && !row.getValue("ASSETS_RATE").equals("%")){
						sumRate += Float.parseFloat(row.getValue("ASSETS_RATE").toString().substring(0, row.getValue("ASSETS_RATE").toString().indexOf("%")));
					}
				}	
      	 }
      }
    	
%> 
		<tr height="22">
			<td width="8%" colspan = "<%=dto.getSpecialAssetsType().equals("SHARE_THING")? "1" : "2" %>" height= "22" align = "left">合计：</td>			
			<td width="6%"  align="right"><%=sumCount %></td>
			<td width="6%" align="right"><%=BigDecimal.valueOf(sumCost).toString().length() - BigDecimal.valueOf(sumCost).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumCost).toString().substring(0, BigDecimal.valueOf(sumCost).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumCost) %></td>	
			<td width="6%" align="right" ><%=BigDecimal.valueOf(sumDeprnReserve).toString().length() - BigDecimal.valueOf(sumDeprnReserve).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumDeprnReserve).toString().substring(0, BigDecimal.valueOf(sumDeprnReserve).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumDeprnReserve) %></td>  
			<td width="6%" align="right" ><%=BigDecimal.valueOf(sumNetBookValue).toString().length() - BigDecimal.valueOf(sumNetBookValue).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumNetBookValue).toString().substring(0, BigDecimal.valueOf(sumNetBookValue).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumNetBookValue) %></td>  
			<td width="6%" align="right" ><%=BigDecimal.valueOf(sumImpairmentReserve).toString().length() - BigDecimal.valueOf(sumImpairmentReserve).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumImpairmentReserve).toString().substring(0, BigDecimal.valueOf(sumImpairmentReserve).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumImpairmentReserve) %></td>  
			<td width="6%" align="right" ><%=BigDecimal.valueOf(sumLimitValue).toString().length() - BigDecimal.valueOf(sumLimitValue).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumLimitValue).toString().substring(0, BigDecimal.valueOf(sumLimitValue).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumLimitValue) %></td>  
			<td width="6%" align="right" ><%=BigDecimal.valueOf(sumPtdDeprn).toString().length() - BigDecimal.valueOf(sumPtdDeprn).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumPtdDeprn).toString().substring(0, BigDecimal.valueOf(sumPtdDeprn).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumPtdDeprn) %></td>
			<td width="6%"  align="right"><%=sumRate.toString().length() - sumRate.toString().indexOf(".") > 3 ? sumRate.toString().substring(0, sumRate.toString().indexOf(".")+3) :sumRate %>%</td>
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
<div style="position:absolute;top:92%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.SpecialNewAssetsReportServlet";
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
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.SpecialNewAssetsReportServlet";
    mainFrm.submit();
}

function do_ShowDetail(organizationId, companyName, scanCount){
	var analyseType = mainFrm.analyseType.value;
	if(scanCount == 0){
		alert("“"+companyName+"”盘点资产数为0，无相关信息。");
		return;
	}
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.SpecialNewAssetsReportServlet";
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