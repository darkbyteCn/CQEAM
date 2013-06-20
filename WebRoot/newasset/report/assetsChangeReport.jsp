<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>盘点结果报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	AmsAssetsChangedVDTO dto = (AmsAssetsChangedVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.AssetsChangeReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产分析报表-->>资产变更分析");
</script>
	<table width="100%" border="0" class="queryTable">
		<tr>
			<td><%=dto.getCheckBoxHtm()%></td>
			<td width="20%"><img border="0" src="/images/eam_images/search.jpg" width="63" height="18" align="right" onclick="do_Search();">&nbsp;<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>

<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="350%">
		<tr height="22">
			<td width="4%" align="center">公司名称</td>
			<td width="4%" align="center">EAM标签</td>
			<td width="4%" align="center">MIS标签</td>
			<td width="6%" align="center">EAM名称</td>
			<td width="6%" align="center">MIS名称</td>

			<td width="6%" align="center">EAM型号</td>
			<td width="6%" align="center">MIS型号</td>
			<td width="4%" align="center">EAM员工号</td>
			<td width="4%" align="center">MIS员工号</td>
			<td width="4%" align="center">EAM责任人</td>

			<td width="4%" align="center">MIS责任人</td>
			<td width="4%" align="center">EAM地点代码</td>
			<td width="4%" align="center">MIS地点代码</td>
			<td width="6%" align="center">EAM地点</td>
			<td width="6%" align="center">MIS地点</td>

			<td width="4%" align="center">EAM成本中心代码</td>
			<td width="4%" align="center">MIS成本中心代码</td>
			<td width="5%" align="center">EAM成本中心</td>
			<td width="5%" align="center">MIS成本中心</td>
			<td width="10%" align="center">变更内容</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:364px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="350%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("COMPANY")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>

			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSIGNED_TO_NUMBER")%>"></td>
			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("USER_NAME")%>"></td>

			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>
			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>

			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("AMS_COST_CODE")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("MIS_COST_CODE")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("AMS_COST_NAME")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("MIS_COST_NAME")%>"></td>
			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("CHANGED_CONTENT")%>"></td>

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
<div style="position:absolute;top:440px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function initPage(){
	do_SetPageWidth();
}

function do_ControlSelect(chk){
	var chkObjs = document.getElementsByTagName("input");
	var chkObj = null;
	var checkProp = chk.checked;
	for(var i = 0; i < chkObjs.length; i++){
		chkObj = chkObjs[i];
		if(chkObj.type != "checkbox" || chkObj.name == chk.name){
			continue;
		}
		chkObj.checked = checkProp;
	}
}

function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
    mainFrm.submit();
}
</script>