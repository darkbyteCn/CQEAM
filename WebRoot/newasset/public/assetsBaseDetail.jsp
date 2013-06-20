<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.sino.ams.newasset.dto.AmsAssetsAddressVDTO"%>
<%@page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@page import="com.sino.ams.newasset.dto.AmsAssetsAddressVDetailDTO"%>
<%
AmsAssetsAddressVDetailDTO item = (AmsAssetsAddressVDetailDTO)request.getAttribute(AssetsWebAttributes.ASSETS_DATA);
%>
<table border="1" width="100%" bordercolor="#666666" id="table3" style="TABLE-LAYOUT:fixed;word-break:break-all">
<tr style="display: none">
	<td width="12%" align="right"></td>
	<td width="46%"></td>
	<td width="12%" align="right"></td>
	<td width="30%"></td>
</tr>
<tr>
	<td colspan="4" class="mainTitleClass" ><B>&nbsp;EAM信息</B></td>
</tr>

<tr >
	<td width="" align="right">资产标签号：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="tagNumber0" readonly value ="<%=item.getBarcode()%>"></td>
	<td width="" align="right">资产种类：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="itemCategoryName" readonly value ="<%=item.getFinancePropName()%>" ></td>
</tr>
<tr>
	<td width="" align="right">资产名称：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="itemName" readonly value ="<%=item.getItemName()%>" ></td>
	<td width="" align="right">规格型号：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="itemSpec" readonly value ="<%=item.getItemSpec()%>" ></td>
</tr>
<tr>
	<td width="" align="right">数量：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="workorderObjectCode" readonly value ="<%=item.getItemQty().trim()%>" ></td>
	<td width="" align="right">生产厂商：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="workorderObjectName" readonly value ="<%=item.getManufacturerName()%>"></td>
</tr>
<tr>
	<td width="" align="right">地点描述：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="workorderObjectName" readonly value ="<%=item.getWorkorderObjectName()%>"></td>
	<td width="" align="right">地点编码：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="workorderObjectCode" readonly value ="<%=item.getWorkorderObjectCode()%>" ></td>
</tr>

<tr>
	<td width="" align="right">地点区划分类描述：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="areaTypeName" readonly value ="<%=item.getAreaTypeName()%>" ></td>
	<td width="" align="right">地点区划分类编码：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="areaType" readonly value ="<%=item.getAreaType()%>"></td>
</tr>
<tr>
	<td width="" align="right">责任人员工姓名：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="responsibilityUserName" readonly value ="<%=item.getResponsibilityUserName()%>"></td>
	<td width="" align="right">责任人员工编号：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="employeeNumber" readonly value ="<%=item.getEmployeeNumber()%>" ></td>
</tr>
<tr>
	<td width="" align="right">责任部门名称：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="responsibilityDeptName" readonly value ="<%=item.getDeptName()%>"></td>
	<td width="" align="right">实物管理部门名称：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="specialityDeptName" readonly value ="<%=item.getSpecialityDeptName()%>"></td>
</tr>
<tr>	
	<td width="" align="right">使用人姓名：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="maintainUserName" readonly value ="<%=item.getMaintainUserName()%>"></td>
	<td width="" align="right">资产状态：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="itemStatusName" readonly value ="<%=item.getItemStatusName()%>"></td>
</tr>

<tr>
	<td colspan="4" class="mainTitleClass" ><B>&nbsp;ERP信息</B></td>
</tr>

<tr>
	<td width="" align="right">资产标签号：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="tagNumber" readonly value ="<%=item.getTagNumber()%>" ></td>
	<td width="" align="right">资产编号：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="assetNumber" readonly value ="<%=item.getAssetNumber()%>"></td>
</tr>
	
<tr>
	<td width="" align="right">资产类别描述：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="faCategory2" readonly value ="<%=item.getContentName()%>"></td>
	<td width="" align="right">资产类别编号：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="faCategory2" readonly value ="<%=item.getContentCode()%>"></td>
</tr>
<tr>
	<td width="" align="right">资产账簿代码：</td>
	<td width=""><input style="width:100%" class="finput"type="text" name="bookTypeCode" readonly value ="<%=item.getBookTypeCode()%>"></td>
	<td width="" align="right">资产账簿名称：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="bookTypeName" readonly value ="<%=item.getBookTypeName()%>" ></td>
</tr>

<tr>
	<td width="" align="right">资产名称：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="assetsDescription" readonly value ="<%=item.getAssetsDescription()%>"></td>
	<td width="" align="right">规格型号：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="modelNumber" readonly value ="<%=item.getModelNumber()%>"></td>
</tr>
<tr>
	<td width="" align="right">资产数量：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="currentUnits" readonly value ="<%=item.getCurrentUnits().trim()%>"></td>
	<td width="" align="right">计量单位：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="unitOfMeasure" readonly value ="<%=item.getUnitOfMeasure()%>"></td>
</tr>
<tr>
	<td width="" align="right">地点描述：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="assetsLocation" readonly value ="<%=item.getAssetsLocation()%>" ></td>
	<td width="" align="right">地点代码：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="assetsLocationCode" readonly value ="<%=item.getAssetsLocationCode()%>" ></td>
</tr>
<tr>
	<td width="" align="right">责任人员工姓名：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="assignedToName" readonly value ="<%=item.getAssignedToName()%>" ></td>
	<td width="" align="right">责任人员工编号：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="assignedToNumber" readonly value ="<%=item.getAssignedToNumber()%>"  ></td>
</tr>
<tr>
	<td width="" align="right">项目名称：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="projectName0" readonly value ="<%=item.getProjectName()%>"></td>
	<td width="" align="right">项目编号：</td>
	<td width=""><input style="width:100%" class="finput"type="text" name="projectName" readonly value ="<%=item.getProjectName()%>"></td>
</tr>
<tr>
	<td width="" align="right">折旧费用账户描述：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="depreciationAccountName" readonly value ="<%=item.getDepreciationAccountName()%>" ></td>
	<td width="" align="right">资产创建日期：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="assetsCreateDate" readonly value ="<%=item.getAssetsCreateDate()%>" ></td>
</tr>
<tr>
	<td width="" align="right">折旧费用账户编码：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="depreciationAccount" readonly value ="<%=item.getDepreciationAccount()%>" ></td>
	<td width="" align="right">资产启用日期：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="datePlacedInService" readonly value ="<%=item.getDatePlacedInService()%>" ></td>
</tr>
<tr>
	<td width="" align="right">资产折旧年限：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="lifeInYears" readonly value ="<%=item.getLifeInYears()%>" ></td>
	<td width="" align="right">折旧剩余月数：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="deprnLeftMonth" readonly value ="<%=item.getDeprnLeftMonth().trim()%>"></td>
</tr>
<%-- 
<tr>
	<td width="" align="right">原值：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="cost" readonly value ="<%=item.getCost()%>" ></td>
	<td width="" align="right">资产净值：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="netAssetValue" readonly value ="<%=item.getNetAssetValue()%>"></td>
</tr>
--%>
<tr>
	<td width="" align="right">报废成本：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="costRetired" readonly value ="<%=item.getCostRetired()%>" ></td>
	<td width="" align="right">报废日期：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="dateRetired" readonly value ="<%=item.getDateRetired()%>"></td>
</tr>
</table>