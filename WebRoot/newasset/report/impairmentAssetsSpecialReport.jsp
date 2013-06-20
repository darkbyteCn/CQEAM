<%@ page import="com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO"%>
<%@ page import="java.math.BigDecimal"%>
<%--
 * User: 李轶
 * Date: 2009-6-14
 * Time: 13:59:55
 * Function:	按专业资产构成分布(减值)
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/WebLibary/js/LookUp.js"></script>
<title>专业资产构成报表</title>
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
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.SpecialImpairmentAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
      printTitleBar("资产基础报表>>专业资产构成分布(减值)")
</script>

    <table width="100%" border="0" class="queryHeadBg">
		<tr>
            <td width="3%" align="right">公司：</td>
			<td width="12%" align="left">
                <select name="organizationId" style="width:70%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <td width="3%" align="right">目录：</td>
			<td width="25%" align="left">
                <input type="text" name="contentName" value="<%=dto.getContentName()%>" style="width:60%" readonly>
                <a href="#" onClick="do_SelectContent();" title="点击选择目录" class="linka">[…]</a>
                <input type= "hidden" name = "contentCode" value = "">
            </td>
            <%--<td width="4%" align="right">会计期间：</td>--%>
            <%--<td width="10%" align="left">--%>
                <%--<input style="width:80%" type="text" name="accountingPeriod" value="<%=StrUtil.nullToString(dto.getAccountingPeriod())%>" title="点击选择会计期" readonly class="noEmptyInput" onclick="gfPop.fPopCalendar(accountingPeriod)">--%>
            <%--</td>--%>
            <%--<td width="5%" align="right">年份：</td>
            <td width="5%"><select style="width:100%" name="year"><%=yearOption%></select></td>
            <td width="5%" align="right">月份：</td>
            <td width="4%"><select style="width:100%" name="month"><%=monthOption%></select></td>--%>
            <td width="5%" align="right">会计期间：</td>
            <td width="14%"><select style="width:40%" name="year"><%=yearOption%></select>—<select style="width:35%" name="month"><%=monthOption%></select></td>
			<td width="13%" align = "right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
    <input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="specialAssetsType" value="<%=dto.getSpecialAssetsType()%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="8%" align="center" >类</td>
			<td width="11%" align="center">项</td>
			
			<td width="4%" align="center">资产数量</td>
			<td width="4%" align="center">当期减值额</td>
            <td width="4%" align="center">累计减值额</td>
            <td width="6%" align="center">占当期资产总数比重</td>
            <td width="6%" align="center">占当期资产减值额比重</td>
            <td width="6%" align="center">占资产累计减值额比重</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String ASSETS_SPECIES="";
        Double impairAmount = 0d;
        Double impairReserve = 0d;
        Long sumCount = 0L;
        Float sumRate = 0f;
        Float sumAmountRate = 0f;
        Float sumReserveRate = 0f;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            boolean bkColor=StrUtil.isEmpty(row.getValue("ASSETS_NAPE"));
            boolean isNew= ASSETS_SPECIES.equals(row.getStrValue("ASSETS_SPECIES"));
            boolean isMainLine = "".equals(row.getValue("ASSETS_NAPE"));
            ASSETS_SPECIES= row.getStrValue("ASSETS_SPECIES");
            if(!ASSETS_SPECIES.equals("类项为空")){
%>
		<tr height="22" <% if (bkColor){%> bgcolor="#EFEFEF" <%}%> >
			<td width="8%"><%=isNew || !isMainLine ?"":ASSETS_SPECIES%></td>
			<td width="11%" align="right"><%=row.getValue("ASSETS_NAPE")%></td>
			
			<td width="4%" align="right"><%=row.getValue("SUM_COUNT")%></td>			
			<td width="4%"  align="right" ><%=row.getValue("IMPAIR_AMOUNT")%></td>		<!-- 当期减值额 -->
			<td width="4%" align="right"><%=row.getValue("IMPAIR_RESERVE")%></td>	<!-- 累计减值额 -->
			<td width="6%" align="right"><%=row.getValue("ASSETS_RATE").equals("%") ? "0%" : row.getValue("ASSETS_RATE")%></td>   
			<td width="6%" align="right"><%=row.getValue("ASSETS_RATE_IMPAIR_AMOUNT").equals("%") ? "0%" : row.getValue("ASSETS_RATE_IMPAIR_AMOUNT")%></td>
            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE_IMPAIR_RESERVE").equals("%") ? "0%" : row.getValue("ASSETS_RATE_IMPAIR_RESERVE")%></td>		
        </tr>
<%
				if(bkColor){
					sumCount += Long.parseLong(row.getValue("SUM_COUNT").toString());
				
					if(row.getValue("IMPAIR_AMOUNT") != null && !"".equals(row.getValue("IMPAIR_AMOUNT"))){
						impairAmount += Double.parseDouble(row.getValue("IMPAIR_AMOUNT").toString());
					}
					if(row.getValue("IMPAIR_RESERVE") != null && !"".equals(row.getValue("IMPAIR_RESERVE"))){
						impairReserve += Double.parseDouble(row.getValue("IMPAIR_RESERVE").toString());
					}
					if(!row.getValue("ASSETS_RATE").equals("") && !row.getValue("ASSETS_RATE").equals("%")){
						sumRate += Float.parseFloat(row.getValue("ASSETS_RATE").toString().substring(0, row.getValue("ASSETS_RATE").toString().indexOf("%")));
					}
					if(!row.getValue("ASSETS_RATE_IMPAIR_AMOUNT").equals("") && !row.getValue("ASSETS_RATE_IMPAIR_AMOUNT").equals("%")){
						sumAmountRate += Float.parseFloat(row.getValue("ASSETS_RATE_IMPAIR_AMOUNT").toString().substring(0, row.getValue("ASSETS_RATE_IMPAIR_AMOUNT").toString().indexOf("%")));
					}
					if(!row.getValue("ASSETS_RATE_IMPAIR_RESERVE").equals("") && !row.getValue("ASSETS_RATE_IMPAIR_RESERVE").equals("%")){
						sumReserveRate += Float.parseFloat(row.getValue("ASSETS_RATE_IMPAIR_RESERVE").toString().substring(0, row.getValue("ASSETS_RATE_IMPAIR_RESERVE").toString().indexOf("%")));
					}
				}
            }
		}
%>
		<tr>
			<td width="8%" height = "22" colspan = "2" align = "right">合计：</td>
			
			<td width="4%"  align="right"><%=sumCount %></td>
			<td width="4%" align="right"><%=impairAmount != 0d ? BigDecimal.valueOf(impairAmount).toString().substring(0, BigDecimal.valueOf(impairAmount).toString().indexOf(".") + 3) : "0"%></td>	
			<td width="4%" align="right"><%=impairReserve != 0d  ? BigDecimal.valueOf(impairReserve).toString().substring(0, BigDecimal.valueOf(impairReserve).toString().indexOf(".") + 3) : "0" %></td>	
			<td width="6%"  align="right"><%=sumRate.toString().length() - sumRate.toString().indexOf(".") > 3 ? sumRate.toString().substring(0, sumRate.toString().indexOf(".")+4) :sumRate %>%</td>
			<td width="6%"  align="right"><%=sumAmountRate.toString().length() - sumAmountRate.toString().indexOf(".") > 3 ? sumAmountRate.toString().substring(0, sumAmountRate.toString().indexOf(".")+4) :sumAmountRate %>%</td>
			<td width="6%"  align="right"><%=sumReserveRate.toString().length() - sumReserveRate.toString().indexOf(".") > 3 ? sumReserveRate.toString().substring(0, sumReserveRate.toString().indexOf(".")+4) :sumReserveRate %>%</td>
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
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.SpecialImpairmentAssetsReportServlet";
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
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.SpecialImpairmentAssetsReportServlet";
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