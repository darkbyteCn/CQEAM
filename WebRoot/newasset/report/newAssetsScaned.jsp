<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>帐实一致</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.NewAssetsScanedServlet">
<%=WebConstant.WAIT_TIP_MSG%>
    <table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="15%">公司名称：<%=dto.getCompanyName()%></td>
			<td width="15%">截止日期：<%=dto.getEndDate()%></td>
			<td width="10%" align="center">成本中心：</td>
			<td width="25%"><select name="costCenterCode" style="width:100%" <%=dto.getDisabled()%>><%=dto.getCostCenterOpt()%></select></td>
			<td width="10%" align="center">地点名称：</td>
			<td width="15%"><input type="text" name="checkLocationName" value="<%=dto.getCheckLocationName()%>" style="width:80%"><a href=""  title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a></td>
			<td width="10%" align="right"><img border="0" src="/images/eam_images/search.jpg" onclick="do_Search();"></td>
		</tr>
	</table>
    <input type="hidden" name="organizationId" value="<%=dto.getOrganizationId()%>">
	<input type="hidden" name="companyName" value="<%=dto.getCompanyName()%>">
	<input type="hidden" name="startDate" value="<%=dto.getStartDate()%>">
	<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
	<input type="hidden" name="act" value="<%=AssetsActionConstant.EXPORT_ACTION%>">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:21px;left:1px;width:990px">

	<table border="1" style="color: #FFFFFF" bgcolor="#2983CB" width="400%">
		<tr height="21">
			<td width="4%" align="center" rowspan="2">公司名称</td>
			<td width="4%" align="center" rowspan="2">资产标签</td>
			
			<td width="10%" align="center" colspan="2">资产名称</td>
			<td width="10%" align="center" colspan="2">资产型号</td>
			<td width="6%" align="center" colspan="2">启用日期</td>
			
			<td width="10%" align="center" colspan="2">地点代码</td>
			<td width="14%" align="center" colspan="2">地点名称</td>
			<td width="8%" align="center" colspan="2">员工编号</td>
			
			<td width="8%" align="center" colspan="2">责任人</td>
			<td width="10%" align="center" colspan="2">项目名称</td>
			<td width="8%" align="center" colspan="2">项目编号</td>
			
			<td width="10%" align="center" rowspan="2">变更数据</td>
		</tr>
		<tr height="21">
			<td width="5%" align="center">EAM系统</td>
			<td width="5%" align="center">MIS系统</td>

			<td width="5%" align="center">EAM系统</td>
			<td width="5%" align="center">MIS系统</td>
			
			<td width="3%" align="center">EAM系统</td>
			<td width="3%" align="center">MIS系统</td>

			<td width="5%" align="center">EAM系统</td>
			<td width="5%" align="center">MIS系统</td>
			
			<td width="7%" align="center">EAM系统</td>
			<td width="7%" align="center">MIS系统</td>

			<td width="4%" align="center">EAM系统</td>
			<td width="4%" align="center">MIS系统</td>
			<td width="4%" align="center">EAM系统</td>
			<td width="4%" align="center">MIS系统</td>

			<td width="5%" align="center">EAM系统</td>
			<td width="5%" align="center">MIS系统</td>
			<td width="4%" align="center">EAM系统</td>
			<td width="4%" align="center">MIS系统</td>
		</tr>
	</table>


</div>
<div id="dataDiv" style="overflow:scroll;height:520px;width:1007px;position:absolute;top:64px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="400%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
  			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("COMPANY")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("AMS_ITEM_NAME")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("MIS_ITEM_NAME")%>"></td>

			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("AMS_ITEM_SPEC")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("MIS_ITEM_SPEC")%>"></td>
			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("AMS_START_DATE")%>"></td>
			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("MIS_START_DATE")%>"></td>

			<td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("AMS_LOCATION_CODE")%>"></td>
			<td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("MIS_LOCATION_CODE")%>"></td>
			<td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("AMS_LOCATION")%>"></td>
			<td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("MIS_LOCATION")%>"></td>

			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("AMS_EMPLOYEE_NUMBER")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("MIS_EMPLOYEE_NUMBER")%>"></td>
			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("AMS_USER_NAME")%>"></td>
			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("MIS_USER_NAME")%>"></td>

			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("AMS_PROJECT_NAME")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("MIS_PROJECT_NAME")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("AMS_PROJECT_NUMBER")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("MIS_PROJECT_NUMBER")%>"></td>


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
<div style="position:absolute;top:585px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>

</html>
<script>
function do_Search(){
	mainFrm.act.value = "<%=AMSActionConstant.QUERY_ACTION%>";
	mainFrm.costCenterCode.disabled = false;
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_SelectAddress(){
	var lookUpName = "LOOK_UP_ADDRESS";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=dto.getOrganizationId()%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (objs) {
		var obj = objs[0];
		mainFrm.checkLocationName.value = obj["toObjectName"];
    }
}
</script>
