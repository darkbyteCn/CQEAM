<%@ page import="com.sino.ams.newasset.report.dto.AssetsGeneralDTO"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2009-5-13
  Time: 17:50:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>管理指标类报表</title>
 </head>
<body leftmargin="0" topmargin="0" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<%
	AssetsGeneralDTO dto = (AssetsGeneralDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(AssetsWebAttributes.REPORT_DATA);
	boolean hasData = (rows != null && !rows.isEmpty());
    String managerGuideType = dto.getManagerGuideType();
%>
<table border="0" width="100%" background="/images/HeaderBack.png" height="76">
	<tr>
		<td width="100%" align="center">
            <span style="letter-spacing: 3pt; vertical-align: middle">
                <b><font color="#FFFFFF" size="4">
                    <%if(dto.getManagerGuideType().equals("DATA_NICETY")){%>资产模块数据准确率<%} else if (dto.getManagerGuideType().equals("ASSETS_REDOUND")) {%>固定资产回报率<%} else if (dto.getManagerGuideType().equals("ASSETS_TURNOVER")) {%>固定资产周转率<%}%>
                </font></b>
            </span>
        </td>
	</tr>
</table>
<div id="headDiv" style="overflow:hidden;position:absolute;top:75px;left:1px;width:990px">
	<table border="1" bordercolor="#336699" width="100%">
<%
    if (managerGuideType.equals("DATA_NICETY")) {
%>
        <tr height="22">
			<td width="10%" align="center">公司</td>
			<td width="10%" align="center">会计期间</td>
			<td width="10%" align="center">准确率</td>
		</tr>
<%
    } else if (managerGuideType.equals("ASSETS_REDOUND")) {
%>
        <tr height="22">
			<td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">当前净利润</td>
            <td width="10%" align="center">期初总固定资产净额</td>
            <td width="10%" align="center">期末总固定资产净额</td>
            <td width="10%" align="center">固定资产回报率</td>
		</tr>
<%
    } else if (managerGuideType.equals("ASSETS_TURNOVER")) {
%>
        <tr height="22">
			<td width="10%" align="center">公司</td>
            <td width="10%" align="center">会计期间</td>
            <td width="10%" align="center">当期营业收入</td>
            <td width="10%" align="center">期初总固定资产净额</td>
            <td width="10%" align="center">期末总固定资产净额</td>
            <td width="10%" align="center">固定资产周转率</td>
		</tr>
<%
    }
%>
    </table>
</div>
<div id="dataDiv" style="overflow:scroll;height:544px;width:1007px;position:absolute;top:98px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#336699" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
<%
            if (managerGuideType.equals("DATA_NICETY")) {
%>
                <tr height="22">
                    <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("COMPANY")%>"></td>
                    <td width="10%"><input type="text" class="finput3" readonly value="<%=row.getValue("PERIOD")%>"></td>
                    <td width="10%"><input type="text" class="finput3" readonly value="<%=row.getValue("ASSETS_RATE")%>"></td>
                </tr>
<%
            } else if (managerGuideType.equals("ASSETS_REDOUND")) {
%>
                <tr height="22">
                    <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                    <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                    <td width="10%" align="center"><%=row.getValue("CURRENT_AMOUNT")%></td>
                    <td width="10%" align="center"><%=row.getValue("BEGIN_AMOUNT")%></td>
                    <td width="10%" align="center"><%=row.getValue("END_AMOUNT")%></td>
                    <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
                </tr>
<%
            } else if (managerGuideType.equals("ASSETS_TURNOVER")) {
%>
                <tr height="22">
                    <td width="10%" align="center"><%=row.getValue("COMPANY")%></td>
                    <td width="10%" align="center"><%=row.getValue("PERIOD")%></td>
                    <td width="10%" align="center"><%=row.getValue("CURRENT_AMOUNT")%></td>
                    <td width="10%" align="center"><%=row.getValue("BEGIN_AMOUNT")%></td>
                    <td width="10%" align="center"><%=row.getValue("END_AMOUNT")%></td>
                    <td width="10%" align="center"><%=row.getValue("ASSETS_RATE")%></td>
                </tr>
<%
            }
        }
	}
%>
	</table>
</div>
<div style="position:absolute;top:660px;left:450px; right:20px">
    <a href="" onClick="do_Export(); return false"><img src="/images/eam_images/export.jpg" border="0"></a>&nbsp;&nbsp
    <a href="" onClick="self.close(); return false"><img src="/images/eam_images/close.jpg" border="0"></a>
</div>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CostChkExportServlet">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="managerGuideType" value="<%=managerGuideType%>">
    <input type="hidden" name="period" value="<%=dto.getPeriod()%>">
</form>
</body>
</html>
<script>
function initPage(){
	window.focus();
	do_SetPageWidth();
}
function do_Export() {
    mainFrm.act.value = "EXPORT_DETIAL_ACTION";
    mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.report.servlet.AssetsGeneralServlet";
    mainFrm.submit();
}
</script>