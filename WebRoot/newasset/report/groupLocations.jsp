<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>巡检地点详细信息</title>
<link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css">
</head>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_Search();')" >
<div id="$$$waitTipMsg$$$" style="position:absolute; bottom:45%; left:5; z-index:10; visibility:hidden">
	<table width="80%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td bgcolor="#ff9900">
				<table width="100%" height="60" border="0" cellspacing="2" cellpadding="0">
					<tr>
						<td bgcolor="#eeeeee" align="center">正在请求数据，请稍候...<img src="/images/wait.gif" alt=""></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<%
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(WebAttrConstant.WORKORDER_DTO);
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.GroupCheckedLocServlet">
	<table border="0" width="100%">
		<tr>
			<td>关键字：</td>
			<td><input type="text" name="objectCode" value="<%=dto.getObjectCode()%>"></td>
			<td><img src="/images/eam_images/search.jpg" title="点击查询" onClick="do_Search();">　</td>
		</tr>
	</table>
<input type="hidden" id="act" name="act" value="">
<input type="hidden" id="startDate" name="startDate" value="<%=dto.getStartDate()%>">
<input type="hidden" id="endDate" name="endDate" value="<%=dto.getEndDate()%>">
<input type="hidden" id="checkDept" name="checkDept" value="<%=dto.getCheckDept()%>">
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:21px;left:1px;width:340px">
	<table class="headerTable" border="1" width="100%">
		<tr height="20">
			<td width="30%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">地点代码</td>
			<td width="70%" align="center" background="/images/bg_01.gif" style="color: #FFFFFF">地点简称</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:84%;width:357px;position:absolute;top:42px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
		
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22" onclick="do_ShowDetail(this, '<%=row.getValue("CHECK_LOCATION")%>', '<%=row.getValue("CHECK_LOCATION_NAME")%>')" title="点击查看该地点巡检情况" style="cursor:hand"  onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
			<td width="30%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
			<td width="70%"><input type="text" class="finput" readonly value="<%=row.getValue("CHECK_LOCATION_NAME")%>"></td>
		</tr>
<%
		}
	}
%>
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:567px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>

</html>
<script>
function do_ShowDetail(tr, objectNo, objectLocation){
	var url = "/servlet/com.sino.ams.newasset.report.servlet.GroupCheckedItemServlet"
	url += "?act=<%=AssetsActionConstant.DETAIL_ACTION%>"
	url += "&checkLocation=" + objectNo;
	url += "&checkLocationName=" + objectLocation;
	url += "&startDate=" + document.getElementById("startDate").value;
	url += "&endDate=" + document.getElementById("endDate").value;
	url += "&checkDept=" + document.getElementById("checkDept").value;
	var tab = tr.parentNode.parentNode;
	var rows = tab.rows;
	var rowsCount = rows.length;
	var row = null;
	var cells = null;
	var cell = null;
	var  nodes = null;
	for(var i = 0; i < rowsCount; i++){
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
	parent.main.document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.DETAIL_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}
</script>
