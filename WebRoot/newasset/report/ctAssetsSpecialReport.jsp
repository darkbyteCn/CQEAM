<%@ page import="com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO"%>
<%@ page import="java.math.BigDecimal"%>
<%--
  Created by IntelliJ IDEA.
  Author:		李轶
  Date: 2009-5-22
  Time: 12:22:55
  JSP Name:		ctAssetsSpecialReport.jsp
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
<title>专业构成分布(村通资产)</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CTSpecialAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    var specialAssetsType = "<%=dto.getSpecialAssetsType()%>";
    printTitleBar("资产基础报表>>专业构成分布(村通资产)")
</script>

    <table width="100%" border="0">
		<tr>
			<td width="10%" align="right">公司：</td>
			<td width="15%" align="left">
                <select class="select_style1" name="organizationId" style="width:90%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
			
			<td width="10%" align="right">目录：</td>
			<td width="20%" align="left">
                <input class="input_style2" type="text" name="contentName" value="<%=dto.getContentName()%>" style="width:80%" readonly>
                <a href="#" onClick="do_SelectContent();" title="点击选择目录" class="linka">[…]</a>
                <input type= "hidden" name = "contentCode" value = "">
            </td>
            <td width="10%" align="right">会计期间：</td>
            <td width="15%" colspan="3"><select class="select_style1" style="width:40%" name="year"><%=yearOption%></select>—<select class="select_style1" style="width:35%" name="month"><%=monthOption%></select></td>
			<td width="20%" align = "right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>

    <input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="specialAssetsType" value="<%=dto.getSpecialAssetsType()%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:840px">
	<table class="eamDbHeaderTable" border="1" width="150%" height="40">
		<tr height="40">
			<td width="7%" align="center" rowspan="2" height="10">类</td>
			<td width="10%" align="center" rowspan="2" height="10">项</td>
			
			<td width="5%" align="center" rowspan="2" height="10">数量</td>
            <td width="5%" align="center" rowspan="2" height="10">原值</td>
            
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总数比重</td>
            <td width="6%" align="center" rowspan="2" height="10">占当期资产总额比重</td>
            <td width="5%" align="center" height="10" rowspan="2">比上月增长率</td>
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
			
<div id="dataDiv" style="overflow:scroll;height:72%;width:840px;position:absolute;top:86px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String ASSETS_SPECIES="";
        Double sumCost = 0d;
        Long sumCount = 0L;
        Float sumRate = 0f;
        Float sumCountRate = 0f;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            boolean bkColor=StrUtil.isEmpty(row.getValue("ASSETS_NAPE"));
            boolean isNew= ASSETS_SPECIES.equals(row.getStrValue("ASSETS_SPECIES"));
            ASSETS_SPECIES= row.getStrValue("ASSETS_SPECIES");
            if(!ASSETS_SPECIES.equals("类项为空")){
%>
        <tr height="22" <% if (bkColor){%> bgcolor="#EFEFEF" <%}%> >
			<td width="7%"><%=!bkColor?"":ASSETS_SPECIES%></td>
			<td width="10%" align="right"><%=row.getValue("ASSETS_NAPE")%></td>
			
			<td width="5%" align="right"><%=row.getValue("SUM_COUNT")%></td>
            <td width="5%" align="right"><%=row.getValue("SUM_COST")%></td>

            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE_COUNT").equals("%") ? "0%" : row.getValue("ASSETS_RATE_COUNT")%></td>
            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE").equals("%") ? "0%" : row.getValue("ASSETS_RATE")%></td>
            <td width="5%" align="right"><%=row.getValue("LAST_MONTH_RATE_COUNT").equals("%") ? "0%" : row.getValue("LAST_MONTH_RATE_COUNT")%></td>
            <td width="6%" align="right"><%=row.getValue("LAST_YEAR_RATE_COUNT").equals("%") ? "0%" : row.getValue("LAST_YEAR_RATE_COUNT")%></td>

            <td width="3%" align="right"><%=row.getValue("THREE_YEER_THREE_RATE_COUNT").equals("%") ? "0%" : row.getValue("THREE_YEER_THREE_RATE_COUNT")%></td>
            <td width="3%" align="right"><%=row.getValue("THREE_YEER_TWO_RATE_COUNT").equals("%") ? "0%" : row.getValue("THREE_YEER_TWO_RATE_COUNT")%></td>
            <td width="3%" align="right"><%=row.getValue("THREE_YEER_ONE_RATE_COUNT").equals("%") ? "0%" : row.getValue("THREE_YEER_ONE_RATE_COUNT")%></td>
        </tr>
<%
           
            if(bkColor){
				sumCount += Long.parseLong(row.getValue("SUM_COUNT").toString());
				if(row.getValue("SUM_COST") != null && !"".equals(row.getValue("SUM_COST"))){
					sumCost += Double.parseDouble(row.getValue("SUM_COST").toString());
				}
				if(!row.getValue("ASSETS_RATE").equals("") && !row.getValue("ASSETS_RATE").equals("%")){
					sumRate += Float.parseFloat(row.getValue("ASSETS_RATE").toString().substring(0, row.getValue("ASSETS_RATE").toString().indexOf("%")));
				}
				if(!row.getValue("ASSETS_RATE_COUNT").equals("") && !row.getValue("ASSETS_RATE_COUNT").equals("%")){
					sumCountRate += Float.parseFloat(row.getValue("ASSETS_RATE_COUNT").toString().substring(0, row.getValue("ASSETS_RATE_COUNT").toString().indexOf("%")));
				}
            }
          }
		}

%>
		<tr>
			<td width="8%" colspan = "2" height= "22" align = "right">合计：</td>
			<td width="6%"  align="right"><%=sumCount %></td>
			<td width="6%" align="right"><%=BigDecimal.valueOf(sumCost).toString().length() - BigDecimal.valueOf(sumCost).toString().indexOf(".") > 3 ? BigDecimal.valueOf(sumCost).toString().substring(0, BigDecimal.valueOf(sumCost).toString().indexOf(".") + 3) : sumCost %></td>
            <td width="6%"  align="right"><%=sumCountRate.toString().length() - sumCountRate.toString().indexOf(".") > 3 ? sumCountRate.toString().substring(0, sumCountRate.toString().indexOf(".")+4) :sumCountRate %>%</td>
            <td width="6%"  align="right"><%=sumRate.toString().length() - sumRate.toString().indexOf(".") > 3 ? sumRate.toString().substring(0, sumRate.toString().indexOf(".")+4) :sumRate %>%</td>
		</tr>
<%	} %>
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