<%@ page contentType="text/html;charset=GBK" language="java" %>

<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.match.dto.AmsMactPropertyDTO" %>
<%@ include file="/match/prematch/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2007-11-24
  Time: 12:58:45
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>已匹配资产清单</title>
</head>
<body onkeydown="autoExeFunction('do_search()')" onload="do_SetPageWidth();">

<%=WebConstant.WAIT_TIP_MSG%>
<%
    AmsMactPropertyDTO dto = (AmsMactPropertyDTO) request.getAttribute(QueryConstant.QUERY_DTO);
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.match.servlet.AmsMactPropertyServlet">
<script type="text/javascript">
    printTitleBar("已匹配资产清单")
</script>
<table width="100%" border="0">
	<tr>
		<td width="13%" align="right">EAM资产名称：</td>
		<td width="27%"><input class="input_style1" style="width:85%" type="text" name="itemName" value="<%=dto.getItemName()%>"><a href="" title="点击选择设备名称" onclick="do_SelectItemName(); return false;" class = "linka">[…]</a></td>
		<td width="13%" align="right">标签号：</td>
		<td width="27%" ><input class="input_style1" style="width:100%" type="text" name="barcode" value="<%=dto.getBarcode()%>"></td>
		<td width="20%" align="right">&nbsp;</td>
	 </tr>
	 <tr>
		<td width="13%" align="right">EAM地点：</td>
		<td width="27%"><input class="input_style1" style="width:85%" type="text" name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>"><a href = "#" onclick = "chooseSit()" title = "点击选择地点" class = "linka">[…]</a></td>
		<%--
		<td width="13%" align="right">成本中心：</td>
		<td width="17%"><input style="width:85%" type="text" name="costCenterName" value="<%=dto.getCostCenterName()%>"><a href="#" onClick="do_SelectCostCenter(); " class="linka">[…]</a></td>
		--%>
		<td width="40%" align="right" colspan=2>&nbsp;
		</td>
		<td width="20%" align="right">
            <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
			<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">
		</td>
	</tr>
</table>
<input type="hidden" name="act">
<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:830px">
	<table class="headerTable" border="1" width="300%">
		<tr height="22px">
			<td align="center" width="4%">EAM标签号</td>
			<td align="center" width="4%">MIS标签号</td>
			<td align="center" width="6%">EAM资产名称</td>
			<td align="center" width="6%">MIS资产名称</td> 

			<td align="center" width="6%">EAM资产型号</td>
			<td align="center" width="6%">MIS资产型号</td>
			<td align="center" width="6%">EAM地点代码</td>
			<td align="center" width="6%">MIS地点代码</td>

			<td align="center" width="8%">EAM资产地点</td>
			<td align="center" width="8%">MIS资产地点</td>
			<td align="center" width="3%">EAM责任人</td>
			<td align="center" width="3%">MIS责任人</td>
			<td align="center" width="4%">EAM目录代码</td>
			<td align="center" width="4%">EAM目录描述</td>
			<td align="center" width="4%">MIS目录代码</td>
			<td align="center" width="4%">MIS目录描述</td>
			<%--
			<td align="center" width="10%">EAM成本中心</td>
			<td align="center" width="8%">MIS成本中心</td>
			--%>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:300px;width:847px;position:absolute;top:93px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="300%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    if (hasData) {
	    Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
			<td width="4%" align="center"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
			<td width="4%" align="center"><input type="text" class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
			<td width="6%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
			<td width="6%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>

			<td width="6%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
			<td width="6%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
			<td width="6%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
			<td width="6%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>

			<td width="8%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
			<td width="8%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>
			<td width="3%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("USER_NAME")%>"></td>
			<td width="3%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
			<td width="4%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("CONTENT_CODE")%>"></td>
			<td width="4%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("CONTENT_NAME")%>"></td>
			<td width="4%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CODE")%>"></td>
			<td width="4%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("FA_CATEGORY2")%>"></td>
			<%--
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("COST_NAME_AMS")%>"></td>
			<td width="8%" align="center"><input type="text" class="finput" readonly value="<%=row.getValue("COST_NAME_MIS")%>"></td>
			--%>
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
<div style="position:absolute;top:400px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
