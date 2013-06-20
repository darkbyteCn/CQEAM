<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" topmargin="0" leftmargin="0">
<%
	AmsAssetsAddressVDTO locDTO = (AmsAssetsAddressVDTO)request.getAttribute(AssetsWebAttributes.LOCATION_DATA);
	DTOSet historys = (DTOSet) request.getAttribute(AssetsWebAttributes.LOCATION_ASSETS_DATA);
	String pageTitle = "地点" + locDTO.getWorkorderObjectCode() + "详细信息及其资产信息";
%>
<form name="mainFrm" action="/servlet/com.sino.ams.newasset.servlet.LoctionQueryServlet" method="post">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
<table border="1" bordercolor="#226E9B" width="100%" id="table1" class="detailHeader">
	<tr>
		<td width="10%" align="right" height="22">地点代码：</td>
		<td width="15%" height="22"><%=locDTO.getWorkorderObjectCode()%></td>
		<td width="10%" align="right" height="22">地点简称：</td>
		<td width="40%" height="22"><%=locDTO.getWorkorderObjectName()%></td>
		<td width="10%" align="right" height="22">地点类别：</td>
		<td width="15%" height="22"><%=locDTO.getObjectCategoryName()%></td>
	</tr>
	<tr>
		<td width="10%" align="right" height="22">区县名称：</td>
		<td width="15%" height="22"><%=locDTO.getCountyName()%></td>
		<td width="10%" align="right" height="22">所在位置：</td>
		<td width="40%" height="22"><%=locDTO.getWorkorderObjectLocation()%></td>
		<td width="10%" align="right" height="22">公司名称：</td>
		<td width="15%" height="22"><%=locDTO.getCompanyName()%></td>
	</tr>
</table>
<fieldset style="border:1px solid #397DF3; position:absolute;top:73px;width:100%;height:88%">
    <legend>
        <img src="/images/eam_images/export.jpg" title="导出到Excel" onClick="do_Export();">
        <img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="window.close(); return false;">
    </legend>
<div id="headDiv" style="overflow:hidden;position:absolute;left:1px;width:992px">
	<table class="headerTable" border="1" width="120%" style="text-align:center">
		<tr>
			<td width="10%" height="22">资产标签</td>
			<td width="10%" height="22">资产编号</td>
			<td width="10%" height="22">资产名称</td>
			<td width="10%" height="22">资产型号</td>
			<td width="10%" height="22">资产原值</td>
			<td width="10%" height="22">启用日期</td>
			<td width="10%" height="22">资产净值</td>
			<td width="10%" height="22">责任人</td>
			<td width="10%" height="22">责任部门</td>
			<td width="10%" height="22">使用人</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:90%;width:1009px;position:absolute;top:45px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
<%
	if(historys != null && !historys.isEmpty()){
%>    
    <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		int dataCount = historys.getSize();
		AmsAssetsAddressVDTO assetDTO = null;
		String barcode = "";
		for(int i = 0; i < dataCount; i++){
			assetDTO = (AmsAssetsAddressVDTO)historys.getDTO(i);
			barcode = assetDTO.getBarcode();
%>
		<tr title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail('<%=barcode%>')">
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=barcode%>"></td>
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=assetDTO.getAssetNumber()%>"></td>
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=assetDTO.getAssetsDescription()%>"></td>
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=assetDTO.getModelNumber()%>"></td>
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=assetDTO.getCost()%>"></td>
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=assetDTO.getDatePlacedInService()%>"></td>
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=assetDTO.getDeprnCost()%>"></td>
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=assetDTO.getResponsibilityUserName()%>"></td>
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=assetDTO.getDeptName()%>"></td>
			<td width="10%" align="center"><input type="text" class="finput" readonly value="<%=assetDTO.getMaintainUserName()%>"></td>
		</tr>
<%
		}
%>		
	</table>
<%
	}
%>	
</div>	
</fieldset>
<input type="hidden" name="workorderObjectName" value="<%=locDTO.getWorkorderObjectName()%>">
<input type="hidden" name="workorderObjectCode" value="<%=locDTO.getWorkorderObjectCode()%>">
<input type="hidden" name="workorderObjectNo" value="<%=locDTO.getWorkorderObjectNo()%>">
<input type="hidden" name="act">
</form>
</body>
</html>
<script>
function initPage(){
	window.focus();
	do_SetPageWidth();
}
function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}

function do_Export(){
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}
</script>
