<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>资产属性不一致</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" id="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CheckResultServletTwo">
<%=WebConstant.WAIT_TIP_MSG%>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="15%">公司名称：<%=dto.getCompanyName()%></td>
			<td width="15%">截止日期：<%=dto.getEndDate()%></td>
			<td width="10%" align="center">部门名称：</td>
			<td width="25%"><select name="checkDept" style="width:100%"><%=dto.getDeptOpt()%></select></td>
			<td width="10%" align="center">地点名称：</td>
			<td width="15%"><input type="text" name="checkLocationName" value="<%=dto.getCheckLocationName()%>" style="width:80%"><a href=""  title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a></td>
			<td width="10%" align="right"><img border="0" src="/images/eam_images/search.jpg" onclick="do_Search();"></td>
		</tr>
	</table>
	<input type="hidden" name="organizationId" value="<%=dto.getOrganizationId()%>">
	<input type="hidden" name="companyName" value="<%=dto.getCompanyName()%>">
	<input type="hidden" name="endDate" value="<%=dto.getEndDate()%>">
	<input type="hidden" name="analyseType" value="<%=AssetsDictConstant.CHECK_RESULT_2%>"><!--用于控制导出类型-->
	<input name="act" type="hidden">
</form>

<div id="headDiv" style="overflow:hidden;position:absolute;top:26px;left:1px;width:990px">

	<table class="headerTable" border="1" width="300%">
		<tr height="22">
			<td width="4%" align="center">EAM标签</td>
			<td width="4%" align="center">MIS标签</td>
			<td width="9%" align="center">EAM名称</td>
			<td width="9%" align="center">MIS名称</td>

			<td width="9%" align="center">EAM型号</td>
			<td width="9%" align="center">MIS型号</td>
			<td width="5%" align="center">EAM地点代码</td>
			<td width="5%" align="center">MIS地点代码</td>

			<td width="10%" align="center">EAM地点名称</td>
			<td width="10%" align="center">MIS地点名称</td>
			<td width="4%" align="center">EAM员工编号</td>
			<td width="4%" align="center">MIS员工编号</td>

			<td width="4%" align="center">EAM责任人</td>
			<td width="4%" align="center">MIS责任人</td>
			<td width="10%" align="center">不一致数据</td>
		</tr>
	</table>

</div>
<div id="dataDiv" style="overflow:scroll;height:510px;width:1007px;position:absolute;top:49px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="300%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
  			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
  			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
			<td width="9%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
			<td width="9%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>

			<td width="9%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
			<td width="9%"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>

			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSIGNED_TO_NUMBER")%>"></td>

			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("USER_NAME")%>"></td>
			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
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
<div style="position:absolute;top:580px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>

</html>
<script>
function do_Search(){
	mainFrm.act.value = "<%=AMSActionConstant.QUERY_ACTION%>";
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
