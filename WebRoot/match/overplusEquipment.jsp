<%@ page contentType="text/html;charset=GBK" language="java" %>

<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.match.dto.OverplusEquipmentDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<%--
  Created by IntelliJ IDEA.
  User: Suhp
  Date: 2007-11-24
  Time: 12:58:45
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
</head>
<body onkeydown="autoExeFunction('do_search()')" onload="do_SetPageWidth();">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    OverplusEquipmentDTO dto = (OverplusEquipmentDTO) request.getAttribute(QueryConstant.QUERY_DTO);
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.match.servlet.OverplusEquipmentServlet">
<script type="text/javascript">
    printTitleBar("盘盈资产统计报表")
</script>
<table width="100%" border="0">
	<tr>
		<td width="10%" align="right">设备名称：</td>
		<td width="25%"><input class="input_style1" style="width:100%" type="text" name="itemName" value="<%=dto.getItemName()%>"></td>
		<td width="10%" align="right">地点：</td>
		<td width="35%"><input  class="input_style1" style="width:85%" type="text" name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>"><a href = "#" onclick = "chooseSit()" title = "点击选择地点" class = "linka">[…]</a></td>
		<td width="20%" align="right"></td>
	 </tr>
	 <tr>
		<td width="10%" align="right">设备型号：</td>
		<td width="25%"><input class="input_style1" style="width:100%" type="text" name="itemSpec" value="<%=dto.getItemSpec()%>"></td>
		<td width="10%" align="right">成本中心：</td>
		<td width="35%"><input class="input_style1" style="width:85%" type="text" name="costCenterName" value="<%=dto.getCostCenterName()%>"><a href="#" onClick="do_SelectCostCenter(); " class="linka">[…]</a></td>
		<td width="20%" align="right">
            <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
            <img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">
            </td>
	</tr>
</table>
<input type="hidden" name="act">
<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:830px">
	<table class="eamHeaderTable" border="1" width="140%">
		<tr height="22px">
			<td width="4%" align="center">序号</td>
			<td align="center" width="10%">标签号</td>
			<td align="center" width="10%">设备专业</td>
			<td align="center" width="10%">设备名称</td>
			<td align="center" width="20%">设备型号</td>
			<td align="center" width="12%">地点代码</td>
			<td align="center" width="25%">地点简称</td>
			<td align="center" width="13%">成本中心</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:68%;width:847px;position:absolute;top:92px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	if (hasData) {
		Row row = null;
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
        	<td width="4%"><input type="text" class="finput2" readonly value="<%= i+1 %>"></td>
            <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
            <td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_CATEGORY")%>"></td>
            <td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
            <td width="20%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
            <td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
            <td width="25%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
            <td width="13%"><input type="text" class="finput" readonly value="<%=row.getValue("COST_CENTER_NAME")%>"></td>
		</tr>
<%
	    }   
	}
%>
    </table>
</div>
</form>
<%
	if(hasData){
%>
<div style="position:absolute;top:94%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>

</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
}

function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}

function chooseSit() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
    var dialogWidth = 47.5;
    var dialogHeight = 30;
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (projects) {
        dto2Frm(projects[0], "mainFrm");
    }
}

function do_SelectCostCenter(){
    var lookUpName = "<%=LookUpConstant.COST_CENTER%>";
    var dialogWidth = 47.5;
    var dialogHeight = 30;
    var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (objs) {
		dto2Frm(objs[0], "mainFrm");
    }
}
</script>