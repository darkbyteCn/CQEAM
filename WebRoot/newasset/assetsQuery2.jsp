<title>用于资产报废和处置</title>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<%
	AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String treeCategory = dto.getTreeCategory();
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = false;
	if(rows != null && !rows.isEmpty()){
		hasData = true;
	}	
	String pageTitle = "资产报废管理>>个人报废资产";
	String dateHeader = "报废日期";
	String userHeader = "报废人";
	if(treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_CLEAR)){
		pageTitle = "资产处置管理>>个人处置资产";
		dateHeader = "处置日期";
		userHeader = "处置人";
	}
%>
<form name="mainFrm" method="post" action="">
	<input type="hidden" name="act" value="">
	<input type="hidden" name="treeCategory" value="<%=treeCategory%>">

<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>

	<table width="100%" border="0" bgcolor="#E9EAE9">
	    <tr>
	    	<td width="8%" align="right">资产名称：</td>
	    	<td width="14%"><input type="text" name="assetsDescription"style="width:100%" value="<%=dto.getAssetsDescription()%>"></td>
	    	<td width="8%" align="right">资产型号：</td>
	    	<td width="14%"><input type="text" name="modelNumber" style="width:100%" value="<%=dto.getModelNumber()%>"></td>
	    	<td width="8%" align="right">资产标签：</td>
	    	<td width="14%"><input type="text" name="barcode" style="width:100%" value="<%=dto.getBarcode()%>"></td>
	    	<td width="8%" align="right"><%=dateHeader%>：</td>
	    	<td width="20%">
				<input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:48%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"><input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:48%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)">
	    	</td>
	    	<td width="6%" align="right">
	    		<img src="/images/eam_images/search.jpg" id="queryImg" style="cursor:'hand'" onclick="do_Search();" alt="查询">
	    	</td>
	    </tr>
	</table>
</form>	
	<div id="headDiv" style="overflow:hidden;position:absolute;top:47px;left:1px;width:850px">
		<table class="headerTable" border="1" width="100%">
	        <tr height="20px">
	            <td align=center width="12%">资产标签</td>
	            <td align=center width="12%">资产编号</td>
	            <td align=center width="12%">资产名称</td>
	            <td align=center width="12%">资产型号</td>
	            <td align=center width="12%">资产原值</td>
	            <td align=center width="12%">启用日期</td>
	            <td align=center width="12%"><%=dateHeader%></td>
	            <td align=center width="12%"><%=userHeader%></td>
	        </tr>
	    </table>
	</div>
<%
	if(hasData){
%>
	<div id="dataDiv" style="overflow:scroll;height:83%;width:867px;position:absolute;top:70px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
	    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%		Row row = null;
		String barcode = "";
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
			barcode = row.getStrValue("BARCODE");
%>		
			<tr class="dataTR" onclick="executeClick(this)">
				<td width="12%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=barcode%>"></td>
				<td width="12%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("ASSET_NUMBER")%>"></td>
				<td width="12%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>
				<td width="12%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
				<td width="12%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput3" readonly value="<%=row.getValue("COST")%>"></td>
				<td width="12%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput2" readonly value="<%=row.getValue("DATE_PLACED_IN_SERVICE")%>"></td>
				<td width="12%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput3" readonly value="<%=row.getValue("TRANS_DATE")%>"></td>
				<td width="12%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" class="finput" readonly value="<%=row.getValue("USERNAME")%>"></td>
			</tr>
<%
		}
%>
		</table>
	</div>
<div style="position:absolute;top:586px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%		
	}
%>	
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>

<script language="javascript">
function do_Search(){
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.AssetsQueryServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}

function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}
</script>
