<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>公司已盘点地点</title>
</head>

<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(AssetsWebAttributes.WORKORDER_DTO);
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	Row row = null;
%>
<div id="headDiv" style="overflow:hidden;position:absolute;top:1px;left:1px;width:990px">
	<table class="headerTable" border="1" width="120%">
		<tr>
			<td width="10%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">地点代码</td>
			<td width="40%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">地点简称</td>
			<td width="40%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">所在位置</td>
			<td width="10%" align="center" background="/images/bg_011.gif" style="color: #FFFFFF" height="22">地点分类</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:506px;width:1007px;position:absolute;top:23px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	<table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(rows!= null && !rows.isEmpty()){
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
			<tr style="cursor:hand" title="点击查看地点“<%=row.getValue("WORKORDER_OBJECT_NAME")%>”盘点情况" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" onclick="do_ShowScanInfo('<%=row.getValue("WORKORDER_OBJECT_NO")%>', '<%=row.getValue("WORKORDER_OBJECT_NAME")%>')">
				<td width="10%" height="22"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
				<td width="40%" height="22"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
				<td width="40%" height="22"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
				<td width="10%" height="22"><input type="text" class="finput" readonly value="<%=row.getValue("OBJECT_CATEGORY_NAME")%>"></td>
			</tr>
<%
		}
	}
%>
	</table>
</div>
<div style="position:absolute;top:564px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.ChkLocReportServlet">
	<input name="act" type="hidden">
</form>
</body>
</html>
<script>
function do_ShowScanInfo(objectNo, objectName){
	var screenHeight = window.screen.height;
	var screenWidth = window.screen.width;
	var url = "/servlet/com.sino.ams.newasset.report.servlet.LocItemReportServlet"
	url += "?act=<%=AssetsActionConstant.DETAIL_ACTION%>";
	url += "&organizationId=<%=dto.getOrganizationId()%>";
	url += "&objectCategory=<%=dto.getObjectCategory()%>";
	url += "&startDate=<%=dto.getStartDate()%>";
	url += "&endDate=<%=dto.getStartDate()%>";
	url += "&checkLocation=" + objectNo;
	url += "&objectName=" + objectName;
	var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,help=no,status=no,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,center=yes";
	window.open(url, "itemScanWin", winstyle);
}

</script>
