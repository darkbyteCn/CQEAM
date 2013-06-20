<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/td/newasset/headerIncludeTd.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%
	AmsAssetsAddressVDTO item = (AmsAssetsAddressVDTO)request.getAttribute(AssetsWebAttributes.ASSETS_DATA);
	String title = "";
	if(item.getAssetNumber()!= ""){
		 title = "TD资产“" + item.getAssetNumber()+ "”的详细信息";
	}else{
		 title = "TD资产详细信息";
	}
	String isRetirement = item.getIsRetirements();
	if(isRetirement.equals("1")){
		isRetirement = "是";
	} else{
		isRetirement = "否";
	}
%>
</head>
<body onload="window.focus()" leftmargin="0" topmargin="0">
<script type="text/javascript">
    printTitleBar("<%=title%>")
</script>
<table border="1" width="100%" style="border-collapse: collapse" id="table2" bordercolor="#3366EE">
	<tr>
		<td>
			<table border="1" width="100%" bordercolor="#666666" id="table3" style="TABLE-LAYOUT:fixed;word-break:break-all">
				<tr >
					<td width="3%" align="center" rowspan="6"><B>实<br>物<br>信<br>息</B></td>
					<td width="11%" align="right">标签号：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="tagNumber0" readonly value ="<%=item.getBarcode()%>"></td>
					<td width="11%" align="right">设备专业：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="itemCategoryName" readonly value ="<%=item.getItemCategoryName()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">设备名称：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="itemName" readonly value ="<%=item.getItemName()%>" ></td>
					<td width="11%" align="right">设备型号：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="itemSpec" readonly value ="<%=item.getItemSpec()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">地点代码：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="workorderObjectCode" readonly value ="<%=item.getWorkorderObjectCode()%>" ></td>
					<td width="11%" align="right">地点名称：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="workorderObjectName" readonly value ="<%=item.getWorkorderObjectName()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">员工号：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="employeeNumber" readonly value ="<%=item.getEmployeeNumber()%>" ></td>
					<td width="11%" align="right">责任人姓名：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="responsibilityUserName" readonly value ="<%=item.getResponsibilityUserName()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">责任部门：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="responsibilityDeptName" readonly value ="<%=item.getDeptName()%>"></td>
					<td width="11%" align="right">使用人姓名：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="maintainUserName" readonly value ="<%=item.getMaintainUserName()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">生产厂家：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="vendorName" readonly value ="<%=item.getManufacturerName()%>" ></td>
					<td width="11%" align="right">设备状态：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="itemStatusName" readonly value ="<%=item.getItemStatusName()%>"></td>
				</tr>
				<tr>
					<td width="3%" align="center" rowspan="13"><B>资<br>产<br>信<br>息</B></td>
					<td width="11%" align="right">资产标签：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="tagNumber" readonly value ="<%=item.getTagNumber()%>" ></td>
					<td width="11%" align="right">资产编号：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="assetNumber" readonly value ="<%=item.getAssetNumber()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">应用领域：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="faCategory1" readonly value ="<%=item.getFaCategory1()%>"></td>
					<td width="11%" align="right">资产类别：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="faCategory2" readonly value ="<%=item.getFaCategory2()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">资产名称：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="assetsDescription" readonly value ="<%=item.getAssetsDescription()%>"></td>
					<td width="11%" align="right">资产型号：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="modelNumber" readonly value ="<%=item.getModelNumber()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">资产数量：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="currentUnits" readonly value ="<%=item.getCurrentUnits()%>"></td>
					<td width="11%" align="right">计量单位：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="unitOfMeasure" readonly value ="<%=item.getUnitOfMeasure()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">地点代码：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="assetsLocationCode" readonly value ="<%=item.getAssetsLocationCode()%>" ></td>
					<td width="11%" align="right">地点名称：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="assetsLocation" readonly value ="<%=item.getAssetsLocation()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">员工号：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="assignedToNumber" readonly value ="<%=item.getAssignedToNumber()%>"  ></td>
					<td width="11%" align="right">责任人姓名：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="assignedToName" readonly value ="<%=item.getAssignedToName()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">项目编号：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="projectName" readonly value ="<%=item.getProjectName()%>"></td>
					<td width="11%" align="right">项目名称：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="projectName0" readonly value ="<%=item.getProjectName()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">账簿代码：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="bookTypeCode" readonly value ="<%=item.getBookTypeCode()%>"></td>
					<td width="11%" align="right">账簿名称：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="bookTypeName" readonly value ="<%=item.getBookTypeName()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">折旧账户代码：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="depreciationAccount" readonly value ="<%=item.getDepreciationAccount()%>" ></td>
					<td width="11%" align="right">折旧账户名称：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="depreciationAccountName" readonly value ="<%=item.getDepreciationAccountName()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">创建日期：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="assetsCreateDate" readonly value ="<%=item.getAssetsCreateDate()%>" ></td>
					<td width="11%" align="right">启用日期：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="datePlacedInService" readonly value ="<%=item.getDatePlacedInService()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">原值：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="cost" readonly value ="<%=item.getCost()%>" ></td>
					<td width="11%" align="right">资产净值：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="netAssetValue" readonly value ="<%=item.getNetAssetValue()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">报废成本：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="costRetired" readonly value ="<%=item.getCostRetired()%>" ></td>
					<td width="11%" align="right">报废日期：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="dateRetired" readonly value ="<%=item.getDateRetired()%>"></td>
				</tr>
				</table>
		</td>
	</tr>
</table>
<p align="center"><img border="0" src="/images/eam_images/close.jpg" onclick="self.close()">&nbsp;&nbsp;<img border="0" src="/images/eam_images/change_history.jpg" title="点击查看其变动历史" onclick="do_ShowHistory()"></p>

</body>
</html>
<script>
function do_ShowHistory(){
	var url = "/servlet/com.sino.td.newasset.servlet.TdItemInfoHistoryServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=<%=item.getBarcode()%>";
	var style = "width=1017,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	window.open(url, 'historyWin', style);
}
</script>