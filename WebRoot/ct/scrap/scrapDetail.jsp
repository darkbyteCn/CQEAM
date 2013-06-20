<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.ct.dto.*" %>
<%@ page import="com.sino.ams.newasset.constant.*" %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%
	EtsFaAssetsDTO dto = (EtsFaAssetsDTO)request.getAttribute(AssetsWebAttributes.ASSETS_DATA);
	String title = "村通报废资产“" + dto.getAssetNumber()+ "”的详细信息";
	String isRetirement = dto.getIsRetirements();
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
					<td width="3%" align="center" rowspan="13"><B>村<br>通<br>资<br>产<br>信<br>息</B></td>
					<td width="11%" align="right">资产名称：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="tagNumber0" readonly value ="<%=dto.getAssetsDescription()%>"></td>
					<td width="11%" align="right">资产分类代码：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="itemCategoryName" readonly value ="<%=dto.getFaCategoryCode()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">单位：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="itemName" readonly value ="<%=dto.getUnitOfMeasure()%>" ></td>
					<td width="11%" align="right">年限：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="itemSpec" readonly value ="<%=dto.getLifeInYears()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">型号：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="workorderObjectCode" readonly value ="<%=dto.getModelNumber()%>" ></td>
					<td width="11%" align="right">标签号：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="workorderObjectName" readonly value ="<%=dto.getTagNumber()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">数量：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="employeeNumber" readonly value ="<%=dto.getCurrentUnits()%>" ></td>
					<td width="11%" align="right">资产地点：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="responsibilityUserName" readonly value ="<%=dto.getAssetsLocation()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">项目名称：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="responsibilityDeptName" readonly value ="<%=dto.getProjectName()%>"></td>
					<td width="11%" align="right">启用日期：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="maintainUserName" readonly value ="<%=dto.getDatePlacedInService()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">是否报废：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="vendorName" readonly value ="<%=dto.getIsRetirements()%>" ></td>
					<td width="11%" align="right">资产编号：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="itemStatusName" readonly value ="<%=dto.getAssetNumber()%>"></td>
				</tr>
				<tr>
					
					<td width="11%" align="right">成本：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="tagNumber" readonly value ="<%=dto.getCost()%>" ></td>
					<td width="11%" align="right">净值：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="assetNumber" readonly value ="<%=dto.getDeprnCost()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">资产状态：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="faCategory1" readonly value ="<%=dto.getAssetsStatus()%>"></td>
					<td width="11%" align="right">资产账簿：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="faCategory2" readonly value ="<%=dto.getBookTypeCode()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">责任人姓名：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="assetsDescription" readonly value ="<%=dto.getAssignedToName()%>"></td>
					<td width="11%" align="right">责任人员工号：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="modelNumber" readonly value ="<%=dto.getAssignedToNumber()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">会计科目ID：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="assignedToNumber" readonly value ="<%=dto.getCodeCombinationId()%>"  ></td>
					<td width="11%" align="right">公司代码：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="assignedToName" readonly value ="<%=dto.getCompanyCode()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">资产残值：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="projectName" readonly value ="<%=dto.getScrapValue()%>"></td>
					<td width="11%" align="right">折旧账户：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="projectName0" readonly value ="<%=dto.getDepreciationAccount()%>"></td>
				</tr>
				<tr>
					<td width="11%" align="right">资产地点代码组合：</td>
					<td width="35%"><input style="width:100%" class="finput"type="text" name="bookTypeCode" readonly value ="<%=dto.getAssetsLocationCode()%>"></td>
					<td width="11%" align="right">资产原值：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="bookTypeName" readonly value ="<%=dto.getOriginalCost()%>" ></td>
				</tr>
				<tr>
					<td width="11%" align="right">资产减值准备累计：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="depreciationAccount" readonly value ="<%=dto.getImpairReserve()%>" ></td>
					<td width="11%" align="right">资产累计折旧：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="depreciationAccountName" readonly value ="<%=dto.getDeprnReserve()%>" ></td>
				</tr>
				
				
				<tr>
					<td width="11%" align="right">报废日期：</td>
					<td width="35%">
					<input style="width:100%" class="finput"type="text" name="assetsCreateDate" readonly value ="<%=dto.getRetireDate()%>" ></td>
					
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
	var url = "/servlet/com.sino.ams.newasset.servlet.AmsItemInfoHistoryServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&tagNumber=<%=dto.getTagNumber()%>";
	var style = "width=1017,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	window.open(url, 'historyWin', style);
}
</script>