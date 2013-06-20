<%@ page import="com.sino.ams.newasset.report.dto.DeptAssetsReportDTO"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-3-4
  Time: 14:14:09
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
<script language="javascript" src="/WebLibary/js/LookUp.js"></script>
<title>TD资产部门统计报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
    String monthOption = parser.getAttribute(WebAttrConstant.FULL_MONTH_OPTION).toString();
    DeptAssetsReportDTO dto = (DeptAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.TDDeptAssetsReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    var deptAssetsType = "<%=dto.getDeptAssetsType()%>";
    printTitleBar("资产分析报表-->>TD资产部门统计")
</script>
<%
if (dto.getDeptAssetsType().equals("NEW")) {
%>
   <table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="5%" align="right">公司：</td>
			<td width="19%" align="left">
                <select name="organizationId" style="width:50%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
			<td width="5%" align="right">责任部门：</td>
            <td width="23%" align="left">
                <select name="responsibilityDept" style="width:90%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select>
            </td>
            <td width="5%" align="right">年份：</td>
            <td width="6%"><select style="width:100%" name="year"><%=yearOption%></select></td>
            <td width="5%" align="right">月份：</td>
            <td width="6%"><select style="width:100%" name="month"><%=monthOption%></select></td>
            <td width="13%">
                <img src="/images/eam_images/search.jpg"  align="right" onclick="do_Search();">
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
<%
} else {
%>
    <table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="7%" align="right">公司：</td>
			<td width="30%" align="left">
                <select name="organizationId" style="width:75%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
			<td width="7%" align="right">责任部门：</td>
            <td width="30%" align="left">
<%
    if (dto.getDeptAssetsType().equals("DEPT_LOSE")) {
%>
           <select name="countyCode" style="width:95%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select>
<%
    } else {
%>
           <select name="responsibilityDept" style="width:95%"><%=request.getAttribute(AssetsWebAttributes.DEPT_OPTIONS)%></select>
<%                
    }
%>
            </td>
            <td width="15%">
                <img src="/images/eam_images/search.jpg"  align="right" onclick="do_Search();">
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel">
            </td>
		</tr>
	</table>
<%}%>
	<input name="act" type="hidden">
	<input name="companyName" type="hidden">
    <input type="hidden" name="deptAssetsType" value="<%=dto.getDeptAssetsType()%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="10%" align="center">公司</td>
			<td width="20%" align="center">责任部门</td>
			<td width="8%" align="center">数量</td>
			<td width="10%" align="center">价值</td>
			<td width="10%" align="center">百分比</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:350px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        String COMPANY="";
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
            boolean bkColor=StrUtil.isEmpty(row.getValue("DEPT_NAME"));
            boolean isNew= COMPANY.equals(row.getStrValue("COMPANY"));
            COMPANY= row.getStrValue("COMPANY");
%>
		<tr height="22" <% if (bkColor){%> bgcolor="YELLOW" <%}%> >
			<td width="10%"><%=isNew?"":COMPANY%></td>
			<td width="20%" align="right"><%=row.getValue("DEPT_NAME")%></td>
            <td width="8%" align="right"><%=row.getValue("ITEM_QTY")%></td>
			<td width="10%" align="right"><%=row.getValue("SUM_COST")%></td>
			<td width="10%" align="right"><%=row.getValue("ASSETS_RATE")%></td>
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
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
    mainFrm.submit();
}

function do_ShowDetail(organizationId, companyName, scanCount){
	var analyseType = mainFrm.analyseType.value;
	if(scanCount == 0){
		alert("“"+companyName+"”盘点资产数为0，无相关信息。");
		return;
	}
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