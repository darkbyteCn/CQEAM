<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>巡检设备详细信息</title>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
	<script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
</head>
<body topmargin="0" leftmargin="0" onload="initPage()">
<%=WebConstant.WAIT_TIP_MSG%>
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(WebAttrConstant.WORKORDER_DTO);
	RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.GROUP_ITEMS);
	boolean hasData = (rows != null && !rows.isEmpty());
	String objectLocation = dto.getCheckLocationName();
	if(objectLocation == null){
		objectLocation = "";
	}
	String showMsg = objectLocation;
%>
<table border="0" width="100%">
	<tr>
		<td width="90%" align="center"><font color="#004000" size="2"><%=showMsg%></font></td>
		<td><img src="/images/eam_images/export.jpg" title="导出到Excel" onClick="do_Export()"></td>
	</tr>
</table>
<div id="headDiv" style="overflow:hidden;position:absolute;top:21px;left:1px;width:622px">
	<table border="1" width="250%" style="border-collapse: collapse" bordercolor="#999999">
		<tr height="20">
			<td width="10%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">标签号</td>
			<td width="8%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">设备专业</td>
			<td width="12%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">设备名称</td>
			<td width="10%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">设备型号</td>
			<td width="8%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">计量单位</td>
			<td width="22%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">当前地点</td>
			<td width="8%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">责任人</td>
			<td width="22%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">责任部门</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:84%;width:643px;position:absolute;top:42px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="250%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	int rowCount = 0;
	if(hasData){
		Row row = null;
		rowCount = rows.getSize();
		for(int i = 0; i < rowCount; i++){
			row = rows.getRow(i);
%>
		<tr height="22" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')" title="点击查看该设备资产信息" style="cursor:hand" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
			<td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
			<td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("ITEM_CATEGORY")%>"></td>
			<td width="12%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
			<td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_UNIT")%>"></td>
			<td width="22%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
			<td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("USER_NAME")%>"></td>
			<td width="22%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
		</tr>
<%
		}
	}
%>
	</table>
</div>
<form name="mainFrm" action="/servlet/com.sino.ams.newasset.report.servlet.GroupCheckedItemServlet" method="post">
<input type="hidden" name="startDate" value="<%=dto.getStartDate()%>">
<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
<input type="hidden" name="checkLocation" value="<%=dto.getCheckLocation()%>">
<input type="hidden" name="checkLocationName" value="<%=dto.getCheckLocationName()%>">
<input type="hidden" name="checkDept" value="<%=dto.getCheckDept()%>">
<input type="hidden" name="act">
</form>
<div style="position:absolute;top:576px;left:0; right:0" id="totalRec"></div>
</body>

</html>
<script>

function initPage(){
	do_SetPageWidth();
	totalRec.innerHTML = "<font color=green>共<%=rowCount%>条记录</font>" ;
}

function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet";
	url += "?act=<%=AssetsActionConstant.DETAIL_ACTION%>";
	url += "&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}

function do_Export(){
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}
</script>
