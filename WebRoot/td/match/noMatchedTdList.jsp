<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.match.dto.AmsNoMactingAssetDTO" %>
<%@ include file="/match/prematch/headerInclude.htm"%>

<%--
  Created by IntelliJ IDEA.
  User: jiangtao
  Date: 2007-11-22
  Time: 7:54:27
  To change this template use File | Settings | File Templates.
--%>
<html>
<head></head>
<%
	AmsNoMactingAssetDTO dto = (AmsNoMactingAssetDTO)request.getAttribute(QueryConstant.QUERY_DTO);
%>

<body onkeydown="autoExeFunction('do_search()');" onload="do_SetPageWidth();mainFrm.tagNumber.focus();">

<form action="/servlet/com.sino.td.match.servlet.AmsNoMatchedTdListServlet" name="mainFrm" method="post">
    <script type="text/javascript">
    printTitleBar("未匹配资产清单(TD)")
</script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <table width="100%" border="0" bgcolor="#EFEFEF" >
        <tr>
            <td align="right" width="10%">标签号：</td>
            <td width="35%"><input style="width:100%" type="text" name="tagNumber" value="<%=dto.getTagNumber()%>"></td>
            <td align="right" width="10%">MIS资产地点：</td>
            <td width="35%"><input style="width:85%" type="text"   name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>"><a href = "#" onclick = "do_SelectLocation()" title = "点击选择地点" class = "linka">[…]</a></td>
            <td align="right" width="10%"><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_export();" alt="导出到Excel"></td>
        </tr>
        <tr>
            <td align="right" width="10%">资产名称：</td>
            <td width="35%"><input style="width:100%" type="text" name="assetsDescription" value="<%=dto.getAssetsDescription()%>"></td>
            <td width="10%" align="right">成本中心：</td>
            <td width="35%"><input  style="width:85%"  name="costCenterName" value = "<%=dto.getCostCenterName()%>"><a href="#" onclick= "do_SelectCostCenter();" class= "linka">[…]</a></td>
            <td align="right" width="10%"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
        </tr>
</table>
    <input type="hidden" name="act">
<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:830px">
	<table class="headerTable" border="1" width="180%">
		<tr height="22px">
			<td align="center" width="6%">标签号</td>
			<td align="center" width="6%">资产编号</td>
			<td align="center" width="10%">资产名称</td>

			<td align="center" width="17%">资产型号</td>
			<td align="center" width="10%">地点代码</td>
			<td align="center" width="15%">地点名称</td>

			<td align="center" width="12%">成本中心</td>
			<td align="center" width="6%">启用日期</td>
			<td align="center" width="4%">使用年限</td>

			<td align="center" width="6%">资产原值</td>
			<td align="center" width="8%">资产账簿</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:68%;width:847px;position:absolute;top:93px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="180%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    if (hasData) {
		Row row = null;
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
		<tr class="dataTR" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
			<td  width="6%"><%=row.getValue("TAG_NUMBER")%></td>
			<td  width="6%"><%=row.getValue("ASSET_NUMBER")%></td>
			<td  width="10%"><%=row.getValue("ASSETS_DESCRIPTION")%></td>

			<td  width="17%"><%=row.getValue("MODEL_NUMBER")%></td>
			<td  width="10%"><%=row.getValue("ASSETS_LOCATION_CODE")%></td>
			<td  width="15%"><%=row.getValue("ASSETS_LOCATION")%></td>

			<td  width="12%"><%=row.getValue("COST_CENTER_NAME")%></td>
			<td  width="6%"><%=row.getValue("DATE_PLACED_IN_SERVICE")%></td>
			<td  width="4%"><%=row.getValue("LIFE_IN_YEARS")%></td>

			<td  width="6%"><%=row.getValue("COST")%></td>
			<td  width="8%"><%=row.getValue("BOOK_TYPE_CODE")%></td>
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
<div style="position:absolute;left:0;bottom:1px;right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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

function do_export() {
	mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}


function do_SelectLocation() {
	var lookUpName = "<%=LookUpConstant.LOOK_UP_TD_ASSETS_LOCATION%>";
	var dialogWidth = 47.5;
	var dialogHeight = 30;
	var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		mainFrm.workorderObjectName.value = obj["assetsLocation"]
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