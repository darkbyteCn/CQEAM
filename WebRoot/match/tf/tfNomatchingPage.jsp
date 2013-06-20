<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.match.dto.TfAmsNoMactingAssetDTO" %>
<%@ include file="/match/prematch/headerInclude.htm"%>

<html>
<head>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>

<%
	TfAmsNoMactingAssetDTO dto = (TfAmsNoMactingAssetDTO)request.getAttribute(QueryConstant.QUERY_DTO);
%>

<body onkeydown="autoExeFunction('do_search()');" onload="do_SetPageWidth();mainFrm.tagNumber.focus();">

<form action="/servlet/com.sino.ams.match.servlet.TfAmsNoMactingAssetServlet" name="mainFrm" method="post">
    <script type="text/javascript">
    printTitleBar("未匹配通服资产清单")
</script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <table width="100%" border="0">
        <tr>
            <td align="right">标签号：</td>
            <td width="20%"><input class="input_style1" style="width:100%" type="text" name="tagNumber" value="<%=dto.getTagNumber()%>"></td>
            <td align="right">资产地点：</td>
            <td width="20%"><input class="input_style1" style="width:85%" type="text"   name="workorderObjectName" value="<%=dto.getWorkorderObjectName()%>"><a href = "#" onclick = "do_SelectLocation()" title = "点击选择地点" class = "linka">[…]</a></td>
            <td align="right"><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_export();" alt="导出到Excel"></td>
        </tr>
        <tr>
            <td align="right">资产名称：</td>
            <td width="20%"><input class="input_style1" style="width:100%" type="text" name="assetsDescription" value="<%=dto.getAssetsDescription()%>"></td>
            <td width="12%" align="right">责任部门：</td>
            <td width="26%"><input  class="input_style1" style="width:85%"  name="deptName" value = "<%=dto.getDeptName()%>"><a href="#" onclick= "do_SelectCostCenter();" class= "linka">[…]</a></td>
            <td align="right"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
        </tr>
</table>
    <input type="hidden" name="act">
<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:100%">
	<table class="headerTable" border="1" width="160%">
		<tr height="22px">
			<td align="center" width="9%">标签号</td>
			<td align="center" width="5%">资产编号</td>
			<td align="center" width="10%">资产名称</td>

			<td align="center" width="10%">资产型号</td>
			<td align="center" width="10%">地点代码</td>
			<td align="center" width="10%">地点名称</td>

			<td align="center" width="6%">责任部门</td>
			<td align="center" width="6%">启用日期</td>
			<td align="center" width="6%">使用年限</td>

			<td align="center" width="6%">资产原值</td>
			<td align="center" width="10%">资产账簿</td>
			<td align="center" width="6%">目录代码</td>
			<td align="center" width="6%">目录描述</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:68%;width:100%;position:absolute;top:93px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="160%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
    if (hasData) {
		Row row = null;
		for (int i = 0; i < rows.getSize(); i++) {
			row = rows.getRow(i);
%>
		<tr class="dataTR" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
			<td  width="9%"><input type="text" class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
			<td  width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSET_NUMBER")%>"></td>
			<td  width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>

			<td  width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
			<td  width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>
			<td  width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>

			<td  width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
			<td  width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
			<td  width="6%"><input type="text" class="finput3" readonly value="<%=row.getValue("LIFE_IN_YEARS")%>"></td>

			<td  width="6%"><input type="text" class="finput3" readonly value="<%=row.getValue("COST")%>"></td>
			<td  width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("BOOK_TYPE_CODE")%>"></td>
			<td  width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("FA_CODE")%>"></td>
			<td  width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("FA_CATEGORY2")%>"></td>
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
<div id="pageNaviDiv" style="position:absolute;top:470px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
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
	var lookUpName = "<%=LookUpConstant.LOOK_UP_ASSETS_LOCATION%>";
	var dialogWidth = 47.5;
	var dialogHeight = 30;
	var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		mainFrm.workorderObjectName.value = obj["assetsLocation"]
	}
}

function do_SelectCostCenter(){
	//LookUpConstant.COST_CENTER//LOOK_UP_RESPONSIBILITY_DEPT_OU 
	var lookUpName = "<%=LookUpConstant.LOOK_UP_RESPONSIBILITY_DEPT_OU%>";
	var dialogWidth = 47.5;
	var dialogHeight = 30;
	var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		dto2Frm(objs[0], "mainFrm");
	}
}
</script>