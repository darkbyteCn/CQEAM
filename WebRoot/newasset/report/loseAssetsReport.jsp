<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-3-9
  Time: 11:25:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="java.math.BigDecimal"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
<script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
<title>盘亏资产统计报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    EtsFaAssetsDTO dto = (EtsFaAssetsDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.LoseMatchMisReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
   printTitleBar("盘亏资产统计报表")
</script>
    <table width="100%" border="0">
		<tr>
			<td width="10%" align="right">公司：</td>
			<td width="15%" align="left">
                <select class="select_style1" name="organizationId" style="width:100%" onchange = "getDeptOpt();"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <td width="10%" align="right">责任部门：</td>
            <td width="20%" align="left">
               <select class="select_style1" name="responsibilityDept" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select>
            </td>
            <%-- 
            <td width="10%" align="right">会计期间：</td>
            <td width="15%"><select class="select_style1" style="width:40%" name="year"><%=yearOption%></select>—<select class="select_style1" style="width:40%" name="month"><%=monthOption%></select></td>
            --%>
            <td width="20%"  align="right">
                <img src="/images/eam_images/search.jpg"  onclick="do_Search();">&nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
	<input name="act" type="hidden">
	<input name="companyName" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:46px;left:1px;width:840px">
	<table class="eamHeaderTable" border="1" width="100%">
		<tr height="22">
			<td width="3%" align="center">序号</td>
			<td width="12%" align="center">资产名称</td>
			<td width="12%"  align="center">规格型号</td>
			<td width="6%" align="center">资产编号</td>
            <td width="8%" align="center">标签号</td>
            
			<td width="4%" align="center">数量</td>
			<td width="6%" align="center">价值</td>
			<td width="7%" align="center">占当期资产总额比重</td>
			<td width="7%" align="center">较去年同期增长率</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:350px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		Double sumCost = 0d;
        Long sumCount = 0L;
        Float sumRate = 0f;
        Float sumLastYearRate = 0f;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
			<td width="3%" align="center"><%=row.getValue("ROWNUM")%></td>
			<td width="12%" align="center"><%=row.getValue("ASSETS_DESCRIPTION")%></td>
			<td width="12%" align="center"><%=row.getValue("MODEL_NUMBER")%></td>
			<td width="6%" align="center"><%=row.getValue("ASSET_ID")%></td>
            <td width="8%"  align="center"><%=row.getValue("TAG_NUMBER")%></td>			
            <td width="4%" align="right"><%=row.getValue("NO_MATCH_UNITS")%></td>
            <td width="6%" align="right"><%=row.getValue("COST")%></td>
            <td width="7%" align="right"><%=row.getValue("ASSETS_RATE")%></td>
            <td width="7%" align="right"><%=row.getValue("LAST_YEAR_RATE")%></td>
        </tr>
<%
				sumCount += Long.parseLong(row.getValue("NO_MATCH_UNITS").toString());
				if(row.getValue("COST") != null && !"".equals(row.getValue("COST"))){
					sumCost += Double.parseDouble(row.getValue("COST").toString());
				}
				if(!row.getValue("ASSETS_RATE").equals("") && !row.getValue("ASSETS_RATE").equals("%")){
					sumRate += Float.parseFloat(row.getValue("ASSETS_RATE").toString().substring(0, row.getValue("ASSETS_RATE").toString().indexOf("%")));
				}
				if(!row.getValue("LAST_YEAR_RATE").equals("") && !row.getValue("LAST_YEAR_RATE").equals("%")){
					sumLastYearRate += Float.parseFloat(row.getValue("LAST_YEAR_RATE").toString().substring(0, row.getValue("LAST_YEAR_RATE").toString().indexOf("%")));
				}
		}
%>
		<tr height="22">
			<td colspan = "5" height= "22" align = "left">合计：</td>			
			<td align="right"><%=sumCount %></td>
			<td align="right"><%=BigDecimal.valueOf(sumCost).toString().length() - BigDecimal.valueOf(sumCost).toString().indexOf(".") >3 ? BigDecimal.valueOf(sumCost).toString().substring(0, BigDecimal.valueOf(sumCost).toString().indexOf(".") + 3) : BigDecimal.valueOf(sumCost) %></td>	
			<td  align="right"><%=sumRate.toString().length() - sumRate.toString().indexOf(".") > 3 ? sumRate.toString().substring(0, sumRate.toString().indexOf(".")+4) :sumRate %>%</td>
			<td  align="right"><%=sumLastYearRate.toString().length() - sumLastYearRate.toString().indexOf(".") > 3 ? sumLastYearRate.toString().substring(0, sumLastYearRate.toString().indexOf(".")+4) :sumLastYearRate %>%</td>
		</tr>
<%        
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
//	var accountingPeriod = mainFrm.accountingPeriod.value;
//	if(accountingPeriod == null || accountingPeriod == ""){
//		alert("会计期间不能为空！");
//		mainFrm.accountingPeriod.focus();
//		return;
//	}
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.LoseMatchMisReportServlet";
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
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.LoseMatchMisReportServlet";
    mainFrm.submit();
}

function do_ShowDetail(organizationId, companyName, scanCount){
	var analyseType = mainFrm.analyseType.value;
	if(scanCount == 0){
		alert("“"+companyName+"”盘点资产数为0，无相关信息。");
		return;
	}
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.LoseMatchMisReportServlet";
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
var xmlHttp;
function getDeptOpt() {
    var organizationId = document.getElementById("organizationId").value ;
    var url = "/servlet/com.sino.ams.print.servlet.BarcodeReceiveServlet?act=CHOOSE_GROUP&organizationId=" + organizationId;
    xmlHttp = GetXmlHttpObject(setDeptOpt);
    xmlHttp.open('POST', url, true);
    xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;');
    xmlHttp.send("a=1");
}

function setDeptOpt() {
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete") {
        if (xmlHttp.status == 200) {//成功
            var resText = xmlHttp.responseText;
            var resArray = resText.parseJSON();
            if (resArray == "ERROR") {
                alert(resText);
            } else {
                var littleCategoryObj = document.getElementById("responsibilityDept");
                littleCategoryObj.length = 0;
                var emptyOption = new Option("--请选择--", "");
                littleCategoryObj.add(emptyOption)
                if (resArray.length > 0) {
                    var retStr;
                    for (var i = 0; i < resArray.length; i++) {
                        retStr = resArray[i];
                        var arr = retStr.split("$");
                        var option = new Option(arr[1], arr[0]);
                        littleCategoryObj.add(option)
                    }
                }
            }
            xmlHttp = null;
        }
    }
}
</script>