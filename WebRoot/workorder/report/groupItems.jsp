<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderDTO" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>巡检设备详细信息</title>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
	<script language="javascript" src="/WebLibary/js/AssetsLookUp.js"></script>
</head>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth();">
<%
	EtsWorkorderDTO dto = (EtsWorkorderDTO)request.getAttribute(WebAttrConstant.WORKORDER_DTO);
	RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.GROUP_ITEMS);
	boolean hasData = (rows != null && !rows.isEmpty());
	String objectLocation = request.getParameter("workorderObjectLocation");
	if(objectLocation == null){
		objectLocation = "";
	}
	String showMsg = "地点" + objectLocation + "扫描设备";
%>
<table border="0" width="100%">
	<tr>
		<td width="90%" align="center"><font color="#004000" size="2"><%=showMsg%></font></td>
		<td><img src="/images/eam_images/export.jpg" title="导出到Excel" onClick="do_Export()"></td>
	</tr>
</table>
<div id="headDiv" style="overflow:hidden;position:absolute;top:21px;left:1px;width:640px">
	<table border="1" width="160%" style="border-collapse: collapse" bordercolor="#999999">
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
<div id="dataDiv" style="overflow:scroll;height:90%;width:657px;position:absolute;top:42px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="160%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>	
		<tr height="22" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')" title="点击查看该设备资产信息" style="cursor:hand" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
			<td width="10%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("BARCODE")%>"></td>
			<td width="8%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: center" readonly value="<%=row.getValue("ITEM_CATEGORY")%>"></td>
			<td width="12%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
			<td width="10%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
			<td width="8%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("ITEM_UNIT")%>"></td>
			<td width="22%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
			<td width="8%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("USER_NAME")%>"></td>
			<td width="22%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
		</tr>
<%
		}
	}
%>	
	</table>
</div>
<form name="mainFrm" action="/servlet/com.sino.ams.workorder.servlet.GroupItemsServlet" method="post">
<input type="hidden" name="startDate" value="<%=dto.getStartDate()%>">
<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
<input type="hidden" name="workorderObjectNo" value="<%=dto.getWorkorderObjectNo()%>">
<input type="hidden" name="workorderObjectLocation" value="<%=dto.getWorkorderObjectLocation()%>">
<input type="hidden" name="groupId" value="<%=dto.getGroupId()%>">
<input type="hidden" name="act">
</form>
</body>

</html>
<script>
function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=600,height=340,left=100,top=150";
	window.open(url, winName, style);
}

function do_Export(){
	mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}
</script>