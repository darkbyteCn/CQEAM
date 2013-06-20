<%@ page import="com.sino.ams.newasset.report.dto.DeptAssetsReportDTO"%>
<%-- 
 * User: 李轶
 * Date: 2009-6-15
 * Time: 10:51:55
 * Function	:		资产减值情况报表
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
<title>资产减值情况</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    DeptAssetsReportDTO dto = (DeptAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    boolean isComMa = userAccount.isComAssetsManager();
    String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.ImpairmentAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
 	 printTitleBar("资产基础报表>>资产减值情况")
</script>

    <table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="10%" align="right">公司：</td>
			<td width="30%" align="left">
                <select name="organizationId" style="width:60%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>

		   <%--<td width="10%" align="right">会计期间：</td>--%>
           <%--<td width="30%" align="left">--%>
               <%--<input style="width:60%" type="text" name="accountingPeriod" value="<%=StrUtil.nullToString(dto.getAccountingPeriod())%>" title="点击选择会计期" readonly class="noEmptyInput" onclick="gfPop.fPopCalendar(accountingPeriod)">--%>
           <%--</td>--%>
            <%--<td width="7%" align="right">年份：</td>
            <td width="5%"><select style="width:100%" name="year"><%=yearOption%></select></td>
            <td width="7%" align="right">月份：</td>
            <td width="4%"><select style="width:100%" name="month"><%=monthOption%></select></td>--%>
            <td width="7%" align="right">会计期间：</td>
            <td width="14%"><select style="width:40%" name="year"><%=yearOption%></select>—<select style="width:35%" name="month"><%=monthOption%></select></td>
            <td width="20%"  align = "right">
                <img src="/images/eam_images/search.jpg" onclick="do_Search();">&nbsp;&nbsp;&nbsp;
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
	<input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="deptAssetsType" value="<%=dto.getDeptAssetsType()%>">
</form>

<div id="headDiv" style="overflow:hidden;position:absolute;top:44px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="8%" align="center" >公司</td>
			<td width="6%" align="center">资产数量</td>
			<td width="6%" align="center" >当期减值额</td>
            <td width="6%" align="center" >累计减值额</td>
            <td width="6%" align="center">占当期资产总数比重</td>
        </tr>
    </table>
</div>

<div id="dataDiv" style="overflow:scroll;height:72%;width:857px;position:absolute;top:67px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String COMPANY="";
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            COMPANY= row.getStrValue("COMPANY");
%>
		<tr height="22" >
			<td width="8%" align = "right"><%=COMPANY%></td>
			<td width="6%" align="right"><%=row.getValue("SUM_COUNT")%></td>
            <td width="6%" align="right"><%=row.getValue("IMPAIR_AMOUNT")%></td>
			<td width="6%" align="right"><%=row.getValue("IMPAIR_RESERVE")%></td>
            <td width="6%" align="right"><%=row.getValue("ASSETS_RATE")%></td>
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
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ImpairmentAssetsReportServlet";
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
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.ImpairmentAssetsReportServlet";
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