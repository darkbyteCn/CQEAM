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
<title>若干个月以上未处置的报废资产比率</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	DeptAssetsReportDTO dto = (DeptAssetsReportDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.DiscardAssetsRateServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("若干个月以上未处置的报废资产比率")
</script>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="5%" align="right">月数</td>
			<td width="8%" align="left">
                <input type = "text" name ="month" value = "<%=dto.getMonth()%>">
            </td>
			<td width="7%" align="right"></td>
            <td width="55%" align="left">
            </td>
            <td width="15%">
                
                <img src="/images/eam_images/export.jpg" onclick="do_Export();" alt="导出到Excel" align="right">
                <img src="/images/eam_images/search.jpg"   onclick="do_Search();">
            </td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="20%" align="center">公司</td>
			<td width="10%" align="center">应处置数量</td>
			<td width="8%"  align="center">已处置数量</td>
			<td width="10%" align="center">百分比</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:350px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
        for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
			<td width="20%" align="right"><%=row.getValue("COMPANY")%></td>
			<td width="10%" align="right"><%=row.getValue("DIS_QTY")%></td>
            <td width="8%"  align="right"><%=row.getValue("AREDY_QTY")%></td>
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

</html>
<script>
function initPage(){
	do_SetPageWidth();
}

function do_Search(){
    if (document.mainFrm.month.value == "") {
        alert("请输入月数");
        return false;
    }
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.DiscardAssetsRateServlet";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.DiscardAssetsRateServlet";
    mainFrm.submit();
}
</script>