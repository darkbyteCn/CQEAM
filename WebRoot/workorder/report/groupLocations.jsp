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
<title>巡检地点详细信息</title>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">

</head>
<body topmargin="0" leftmargin="0">
<%
	RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.GROUP_LOCATIONS);
	boolean hasData = (rows != null && !rows.isEmpty());
	EtsWorkorderDTO dto = (EtsWorkorderDTO)request.getAttribute(WebAttrConstant.WORKORDER_DTO);
%>
	<table border="1" width="100%" cellspacing="1" style="border-collapse: collapse" bordercolor="#666666">
		<tr height="20">
			<td width="30%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">地点代码</td>
			<td width="70%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">地点名称</td>
		</tr>
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>	
		<tr height="22" onclick="do_ShowDetail(this, '<%=row.getValue("WORKORDER_OBJECT_NO")%>', '<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>')" title="点击查看该地点巡检情况" style="cursor:hand"  onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
			<td width="30%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
			<td width="70%"><input type="text" style="width:100%; border: 1px solid #FFFFFF;text-align: left" readonly value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
		</tr>
<%
		}
	}
%>	
	</table>
<input type="hidden" id="startDate" name="startDate" value="<%=dto.getStartDate()%>">
<input type="hidden" id="endDate" name="endDate" value="<%=dto.getEndDate()%>">
<input type="hidden" id="groupId" name="groupId" value="<%=dto.getGroupId()%>">

</body>

</html>
<script>
function do_ShowDetail(tr, objectNo, objectLocation){
	var url = "/servlet/com.sino.ams.workorder.servlet.GroupItemsServlet"
	url += "?act=<%=WebActionConstant.DETAIL_ACTION%>"
	url += "&workorderObjectNo=" + objectNo;
	url += "&workorderObjectLocation=" + objectLocation;
	url += "&startDate=" + document.getElementById("startDate").value;
	url += "&endDate=" + document.getElementById("endDate").value;
	url += "&groupId=" + document.getElementById("groupId").value;
	var tab = tr.parentNode.parentNode;
	var rows = tab.rows;
	var rowsCount = rows.length;
	var row = null;
	var cells = null;
	var cell = null;
	var  nodes = null;
	for(var i = 1; i < rowsCount; i++){
		row = rows[i];
		cells = row.cells;
		for(var j = 0; j < cells.length; j++){
			cell = cells[j];
			nodes = cell.childNodes;
			nodes = cell.childNodes;
			for(var k = 0; k < nodes.length; k++){
				nodes[k].style.backgroundColor = "#FFFFFF";
				nodes[k].style.color = "#000000";
			}
		}
	}
	cells = tr.cells;
	for(var i = 0; i < cells.length; i++){
		cell = cells[i];
		nodes = cell.childNodes;
		for(var j = 0; j < nodes.length; j++){
			nodes[j].style.backgroundColor = "#336699";
			nodes[j].style.color = "#FFFFFF";
		}
	}
	parent.main.location.href = url;
}
</script>