<%@ page import="com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO"%>
<%@ page import="java.math.BigDecimal"%>
<%--
  Created by IntelliJ IDEA.
  User: 李轶
  Date: 2009-3-2
  Time: 22:13:29
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
<title>专业资产构成分布(待报废)</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	RequestParser parser = new RequestParser();
	parser.transData(request);
	SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.ToDiscardedSpecialAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
       printTitleBar("资产基础报表-->>专业资产构成分布(待报废)")
</script>
    <table width="100%" border="0">
		<tr>
            <td width="10%" align="right">公司：</td>
			<td width="15%" align="left">
                <select class="select_style1" name="organizationId" style="width:90%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <td width="10%" align="right">目录：</td>
			<td width="25%" align="left">
                <input type="text" name="contentName" class="input_style2" value="<%=dto.getContentName()%>" style="width:100%" readonly onClick="do_SelectContent();">
                <input type= "hidden" name = "contentCode" value = "">
            </td>
            <td width="10%" align="right">会计期间：</td>
            <td width="15%" colspan="3"><select class="select_style1" style="width:40%" name="year"><%=yearOption%></select>—<select class="select_style1" style="width:35%" name="month"><%=monthOption%></select></td>
            <td width="15%" align="right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();" alert = "点击查询">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
    <input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="specialAssetsType" value="<%=dto.getSpecialAssetsType()%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:150%">
	<table class="eamDbHeaderTable" border="1" width="150%" style = "height:40px">
		<tr height="1">
			<td width="7%" align="center" rowspan="2" height="10">类</td>
			<td width="13%" align="center" rowspan="2" height="10">项</td>
            
            <td width="6%" align="center" rowspan="2" height="10">资产数量</td>
			<td width="6%" align="center" rowspan="2" height="10">原值</td>
			<td width="6%" align="center" rowspan="2" height="10">累计折旧</td>
            <td width="6%" align="center" rowspan="2" height="10">净值</td>
            <td width="6%" align="center" rowspan="2" height="10">累计减值准备</td>
            <td width="6%" align="center" rowspan="2" height="10">净额</td>
            <td width="6%" align="center" rowspan="2" height="10">当期折旧</td>
            
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总额比重</td>
            <td width="7%" align="center" rowspan="2" height="10">较去年同期增长率</td>
            <td width="10%" align="center" colspan="3" height="5">近3年增长率</td>
        </tr>
        <tr class="eamDbHeaderTr">
            <td width="4%" align="center" height="5">2006</td>
            <td width="4%" align="center" height="5">2007</td>
            <td width="4%" align="center" height="5">2008</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:70%;width:150%;position:absolute;top:86px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String ASSETS_SPECIES="";
        
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
            boolean bkColor=StrUtil.isEmpty(row.getValue("ASSETS_NAPE"));
            boolean isNew= ASSETS_SPECIES.equals(row.getStrValue("ASSETS_SPECIES"));
            ASSETS_SPECIES= row.getStrValue("ASSETS_SPECIES");
            if(!ASSETS_SPECIES.equals("类项为空")){
%>
		<tr height="22" <% if (bkColor){%> bgcolor="#EFEFEF" <%}%> >
			<td width="7%"><%=isNew?"":ASSETS_SPECIES%></td>
			<td width="13%" align="right"><%=row.getValue("ASSETS_NAPE")%></td>
            
            <td width="6%" align="right"><%=row.getValue("SUM_COUNT")%></td>
            <td width="6%" align="right"><%=row.getValue("SUM_COST")%></td>
			<td width="6%" align="right"><%=row.getValue("DEPRN_RESERVE")%></td>
			<td width="6%" align="right"><%=row.getValue("NET_BOOK_VALUE")%></td>
			<td width="6%" align="right"><%=row.getValue("IMPAIRMENT_RESERVE")%></td>   <!-- 累计减值准备 -->
            <td width="6%" align="right"><%=row.getValue("LIMIT_VALUE")%></td>
            <td width="6%" align="right"><%=row.getValue("PTD_DEPRN")%></td>
			
            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE").equals("") ? "0" : row.getValue("ASSETS_RATE")%></td>
            <td width="7%" align="right"><%=row.getValue("LAST_YEAR_RATE").equals("") ? "0" : row.getValue("LAST_YEAR_RATE")%></td>

            <td width="4%" align="right"><%=row.getValue("THREE_YEER_THREE_RATE").equals("") ? "0" : row.getValue("THREE_YEER_THREE_RATE")%></td>
            <td width="4%" align="right"><%=row.getValue("THREE_YEER_TWO_RATE").equals("") ? "0" : row.getValue("THREE_YEER_TWO_RATE")%></td>
            <td width="4%" align="right"><%=row.getValue("THREE_YEER_ONE_RATE").equals("") ? "0" : row.getValue("THREE_YEER_ONE_RATE")%></td>
        </tr>
<%
				if(bkColor){
					sumCount += Long.parseLong(row.getValue("SUM_COUNT").toString());
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
		<tr>
			<td width="8%" colspan = "2" height= "22" align = "left">合计：</td>
			
			<td width="6%"  align="right"><%=sumCount %></td>
			<td width="6%" align="right"><%=BigDecimal.valueOf(sumCost).toString().length() - BigDecimal.valueOf(sumCost).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumCost).toString().substring(0, BigDecimal.valueOf(sumCost).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumCost) %></td>	
			<td width="6%" align="right" ><%=BigDecimal.valueOf(sumDeprnReserve).toString().length() - BigDecimal.valueOf(sumDeprnReserve).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumDeprnReserve).toString().substring(0, BigDecimal.valueOf(sumDeprnReserve).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumDeprnReserve) %></td>  
			<td width="6%" align="right" ><%=BigDecimal.valueOf(sumNetBookValue).toString().length() - BigDecimal.valueOf(sumNetBookValue).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumNetBookValue).toString().substring(0, BigDecimal.valueOf(sumNetBookValue).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumNetBookValue) %></td>  
			<td width="6%" align="right" ><%=BigDecimal.valueOf(sumImpairmentReserve).toString().length() - BigDecimal.valueOf(sumImpairmentReserve).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumImpairmentReserve).toString().substring(0, BigDecimal.valueOf(sumImpairmentReserve).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumImpairmentReserve) %></td>  
			<td width="6%" align="right" ><%=BigDecimal.valueOf(sumLimitValue).toString().length() - BigDecimal.valueOf(sumLimitValue).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumLimitValue).toString().substring(0, BigDecimal.valueOf(sumLimitValue).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumLimitValue) %></td>  
			<td width="6%" align="right" ><%=BigDecimal.valueOf(sumPtdDeprn).toString().length() - BigDecimal.valueOf(sumPtdDeprn).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumPtdDeprn).toString().substring(0, BigDecimal.valueOf(sumPtdDeprn).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumPtdDeprn) %></td>
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