<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.system.rent.dto.RentDTO" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  Author:  李轶
  Date:    2007-10-18
  Time:    15:57:18
  Functin: 租赁房屋土地维护。
--%>
<html>
<head><title>租赁资产历史信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("租赁资产历史信息： " + "<%=request.getParameter("barcode")%>");
</script>
<%
	DTOSet historys = (DTOSet) request.getAttribute(AssetsWebAttributes.RENT_HISTORY_DATA);
%>
<body onload="initPage();" topmargin="0" leftmargin="0">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.system.rent.servlet.RentServlet" name="mainFrm" method="post">
<div id="headDiv" style="overflow:hidden;position:absolute;top:19px;left:1px;width:120%">
	<table class="headerTable" border="1" width="120%" style="text-align:center">
		<input type = "hidden" name = "act">
		<input type = "hidden" name ="accessType"  value = "AMS_RENT_INFO_HISTORY">
		<input type = "hidden" name = "barcode" value = "<%=request.getParameter("barcode")%>">
		<tr>
			<td width="8%" height="22">租赁资产标签号</td>
			<td width="8%" height="22">资产名称</td>
			<td width="5%" height="22">规格型号</td>
			<td width="15%" height="22">资产类别描述</td>
			<td width="12%" height="22">签约单位</td>
			<td width="5%" height="22">起始日期</td>
			<td width="5%" height="22">截至日期</td>
			<td width="5%" height="22">租期(年)</td>
			<td width="5%" height="22">总租金</td>
			<td width="5%" height="22">年租金(元)</td>
			<td width="5%" height="22">月租金</td>
			<td width="5%" height="22">是否有效</td>
			<td width="10%" height="22">备注</td>
		</tr>
	</table>
</div>

<div id="dataDiv" style="overflow:scroll;height:89%;width:120px;position:absolute;top:42px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">

<%
	if(historys != null && !historys.isEmpty()){
%> 
    <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
	 <%
		int dataCount = historys.getSize();
		RentDTO history = null;
        RentDTO hdto = (RentDTO)historys.getDTO(0);//用来导出变动历史报表
        for(int i = 0; i < dataCount; i++){
			history = (RentDTO)historys.getDTO(i);
	%>
		<tr>
			<td height="22" width="8%" align="center"><%=history.getBarcode()%>
            </td>
            <td height="22" width="8%" align="center"><%=history.getItemName()%>
            </td>
            <td height="22" width="5%" align="center"><%=history.getItemSpec()%>
            </td>
            <td height="22" width="15%" align="center"><%=history.getContentName()%>
            </td>
            <td height="22" width="12%" align="center"><%=history.getRentPerson()%>
            </td>
            <td height="22" width="5%" align="center"><%=history.getRentDate().getSQLDate() %>
            </td>
            <td height="22" width="5%" align="center"><%=history.getEndDate().getSQLDate() %>
            </td>
            <td height="22" width="5%" align="center"><%=history.getTenancy() %></td>
            <td height="22" width="5%" align="center"><%=history.getRental() %></td>
            <td height="22" width="5%" align="center"><%=history.getYearRental() %>
            </td>
            <td height="22" width="5%" align="center"><%=history.getMonthReantal() %>
            </td>
            <td height="22" width="5%" align="center"><%=history.getDisabled().equals("Y") ? "是" : "否" %>
            </td>
            <td height="22" width="10%" align="center"><%=history.getRemark() %>
            </td>
		</tr>
		<%
			}
		%>
   </table>
<%
			}
%>
   <p align="center"><img border="0" src="/images/eam_images/close.jpg" onclick="self.close()">&nbsp;&nbsp;&nbsp;
   <img src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_Export();"></p>
</div>

</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script>
function initPage(){
	window.focus();
	do_SetPageWidth();
}
var exportNum = 0;
function do_Export() {
	if(exportNum < 1){
		mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
		exportNum ++;
	    mainFrm.submit();
	}else{
		alert("已执行导出命令，请您不要重复点击！");
	}    
}
</script>