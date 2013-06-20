<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.CheckBoxProp" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%@ page import="com.sino.ams.match.dto.AmsMactPropertyDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2007-11-24
  Time: 12:58:45
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>已匹配资产清单(TD)</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
</head>
<body onkeydown="autoExeFunction('do_search()')" onload="do_SetPageWidth();mainFrm.itemName.focus();">

<%
    AmsMactPropertyDTO dto = (AmsMactPropertyDTO) request.getAttribute(QueryConstant.QUERY_DTO);
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.td.match.servlet.AmsMatchedTdListServlet">
<script type="text/javascript">
    printTitleBar("已匹配资产清单(TD)")
</script>
<table width="100%" border="0" bgcolor="#EFEFEF" class="queryHeadBg">
	<tr>
		<td width="10%" align="right">EAM资产名称：</td>
		<td width="35%"><input style="width:85%" type="text" name="itemName" value="<%=dto.getItemName()%>"><a href="" title="点击选择设备名称" onclick="do_SelectItemName(); return false;" class = "linka">[…]</a></td>
		<td width="10%" align="right">标签号：</td>
		<td width="35%"><input style="width:85%" type="text" name="barcode" value="<%=dto.getBarcode()%>"></td>
		<td width="10%" align="right"><img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
	 </tr>
	 <tr>
		<td width="10%" align="right">EAM地点：</td>
		<td width="35%"><input style="width:85%" type="text" name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>"><a href = "#" onclick = "chooseSit()" title = "点击选择地点" class = "linka">[…]</a></td>
		<td width="10%" align="right">成本中心：</td>
		<td width="35%"><input style="width:85%" type="text" name="costCenterName" value="<%=dto.getCostCenterName()%>"><a href="#" onClick="do_SelectCostCenter(); " class="linka">[…]</a></td>
		<td width="10%" align="right"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
	</tr>
</table>
<input type="hidden" name="act">
<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:840px">
    <table class="headerTable" border="1" width="250%">
		<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>"/>
        <tr onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选" height="22">
			<td align="center" width="4%" >EAM标签号</td>
			<td align="center" width="4%">MIS标签号</td>
			<td align="center" width="8%">EAM资产名称</td>
			<td align="center" width="8%">MIS资产名称</td>

			<td align="center" width="8%">EAM资产型号</td>
			<td align="center" width="8%">MIS资产型号</td>
			<td align="center" width="6%">EAM地点代码</td>
			<td align="center" width="6%">MIS地点代码</td>

			<td align="center" width="12%">EAM资产地点</td>
			<td align="center" width="12%">MIS资产地点</td>
			<td align="center" width="3%">EAM责任人</td>
			<td align="center" width="3%">MIS责任人</td>

			<td align="center" width="10%">EAM成本中心</td>
			<td align="center" width="8%">MIS成本中心</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:68%;width:857px;position:absolute;top:94px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
<%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <table width="250%" border="1" bordercolor="#666666">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onClick="executeClick(this)">
            <td width="4%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("BARCODE")%></td>
			<td width="4%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("TAG_NUMBER")%></td>
			<td width="8%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("ITEM_NAME")%></td>
			<td width="8%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("ASSETS_DESCRIPTION")%></td>

			<td width="8%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("ITEM_SPEC")%></td>
			<td width="8%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("MODEL_NUMBER")%></td>
			<td width="6%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("WORKORDER_OBJECT_CODE")%></td>
			<td width="6%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("ASSETS_LOCATION_CODE")%></td>

			<td width="12%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("WORKORDER_OBJECT_LOCATION")%></td>
			<td width="12%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("ASSETS_LOCATION")%></td>
			<td width="3%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("USER_NAME")%></td>
			<td width="3%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("ASSIGNED_TO_NAME")%></td>

			<td width="10%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("COST_NAME_AMS")%></td>
			<td width="8%" align="center" style="word-wrap:break-word" height="22"><%=row.getValue("COST_NAME_MIS")%></td>
		</tr>
<%
	    }
    }
%>
    </table>
</div>
</form>

<div style="position:absolute;bottom:1px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script type="text/javascript">
function do_search() {
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
	mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_Export() {                  //导出execl
	mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}

function chooseSit() {
	var lookUpName = "<%=LookUpConstant.LOOK_UP_ASSETS_OBJECT%>";
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


function do_SelectItemName(){
	var lookUpName = "LOOK_UP_ITEMNAME";
	var dialogWidth = 35;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
    }
}
</script>
