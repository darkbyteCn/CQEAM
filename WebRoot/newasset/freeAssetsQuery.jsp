<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>闲置资产查询</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.servlet.FreeAssetsQueryServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("资产闲置管理-->>闲置资产查询")
</script>

	<table width="100%" border="0" class="queryTable">
		<tr>
			<td width="10%" align="right">资产标签：</td>
			<td width="15%"><input type="text" class="input_style1" name="barcode" style="width:100%" value="<%=dto.getBarcode()%>"></td>
			<td width="10%" align="right">资产名称：</td>
			<td width="15%"><input type="text" class="input_style1" name="assetsDescription" style="width:100%" value="<%=dto.getAssetsDescription()%>"></td>
			<td width="10%" align="right">闲置时间：</td>
			<td width="22%"><input readonly type="text" name="startDate" style="cursor:hand;width:45%" title="点击选择开始日期" class="input_style2" value="<%=dto.getStartDate()%>" onclick="gfPop.fStartPop(startDate,endDate);">到<input type="text" name="endDate" style="cursor:hand;width:45%" title="点击选择截至日期" readonly class="input_style2" value="<%=dto.getEndDate()%>" onclick="gfPop.fEndPop(startDate,endDate);"></td>
			<td width="18%" align="right"><img border="0" src="/images/eam_images/search.jpg" onclick="do_Search();">&nbsp;<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input readonly name="act" type="hidden">
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="385%">
		<tr height="22">
			<td width="3%" align="center">资产标签</td>
			<td width="3%" align="center">资产编号</td>
			<td width="6%" align="center">资产名称</td>
			<td width="5%" align="center">资产型号</td>
			<td width="5%" align="center">地点代码</td>

			<td width="10%" align="center">地点位置</td>
			<td width="8%" align="center">责任部门</td>
			<td width="3%" align="center">责任人</td>
			<td width="3%" align="center">员工号</td>
			<td width="3%" align="center">计量单位</td>
			<td width="3%" align="center">服务年限</td>
			<td width="3%" align="center">启用日期</td>

			<td width="6%" align="center">上次闲置单号</td>
			<td width="3%" align="center">上次闲置日期</td>
			<td width="3%" align="center">上次闲置人</td>
			<td width="3%" align="center">资产原值</td>
			<td width="3%" align="center">资产净值</td>
			<td width="3%" align="center">资产残值</td>

			<td width="11%" align="center">折旧账户代码</td>
			<td width="14%" align="center">折旧账户名称</td>
		</tr>
	</table>
</div>		
<div id="dataDiv" style="overflow:scroll;height:68%;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="385%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>	
		<tr height="22">
			<td width="3%"><input readonly class="finput2" value="<%=row.getValue("BARCODE")%>"></td>
			<td width="3%"><input readonly class="finput2" value="<%=row.getValue("ASSET_NUMBER")%>"></td>
			<td width="6%"><input readonly class="finput" value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
			<td width="5%"><input readonly class="finput" value="<%=row.getValue("MODEL_NUMBER")%>"></td>
			<td width="5%"><input readonly class="finput2" value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>

			<td width="10%"><input readonly class="finput" value="<%=row.getValue("WORKORDER_OBJECT_LOCATION")%>"></td>
			<td width="8%"><input readonly class="finput" value="<%=row.getValue("DEPT_NAME")%>"></td>
			<td width="3%"><input readonly class="finput" value="<%=row.getValue("RESPONSIBILITY_USER_NAME")%>"></td>
			<td width="3%"><input readonly class="finput2" value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
			<td width="3%"><input readonly class="finput" value="<%=row.getValue("UNIT_OF_MEASURE")%>"></td>
			<td width="3%"><input readonly class="finput3" value="<%=row.getValue("LIFE_IN_YEARS")%>"></td>
			<td width="3%"><input readonly class="finput2" value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>

			<td width="6%"><input readonly class="finput2" value="<%=row.getValue("TRANS_NO")%>"></td>
			<td width="3%"><input readonly class="finput2" value="<%=row.getValue("TRANS_DATE")%>"></td>
			<td width="3%"><input readonly class="finput" value="<%=row.getValue("USERNAME")%>"></td>
			<td width="3%"><input readonly class="finput3" value="<%=row.getValue("COST")%>"></td>
			<td width="3%"><input readonly class="finput3" value="<%=row.getValue("DEPRN_COST")%>"></td>
			<td width="3%"><input readonly class="finput3" value="<%=row.getValue("SCRAP_VALUE")%>"></td>

			<td width="11%"><input readonly class="finput2" value="<%=row.getValue("DEPRECIATION_ACCOUNT")%>"></td>
			<td width="14%"><input readonly class="finput2" value="<%=row.getValue("DEPRECIATION_ACCOUNT_NAME")%>"></td>
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
<div style="position:absolute;top:428px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}
</script>