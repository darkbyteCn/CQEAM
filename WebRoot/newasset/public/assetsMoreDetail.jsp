<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.sino.ams.newasset.dto.AmsAssetsAddressVDTO"%>
<%@page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@page import="com.sino.ams.newasset.dto.AmsAssetsAddressVDetailDTO"%>
<%@page import="com.sino.base.util.StrUtil"%>
<%
AmsAssetsAddressVDetailDTO item = (AmsAssetsAddressVDetailDTO)request.getAttribute(AssetsWebAttributes.ASSETS_DATA);
%>
<table border="1" width="100%" bordercolor="#666666" id="table3" style="TABLE-LAYOUT:fixed;word-break:break-all">
<tr style="display: none">
	<td width="13%" align="right"></td>
	<td width="44%"></td>
	<td width="13%" align="right"></td>
	<td width="30%"></td>
</tr>
<tr>
	<td colspan="4" width="3%" class="mainTitleClass" ><B>&nbsp;资产业务属性</B></td>
</tr>
<tr>
	<td align="right">公司代码：</td>
	<td >
	<input style="width:100%" class="finput" type="text" name="tagNumber0" readonly value ="<%=item.getCompanyCode()%>"></td>
	<td align="right">是否重要固定资产：</td>
	<td >
	<input style="width:100%" class="finput" type="text" name="itemCategoryName" readonly value ="<%=item.getIsImportantAssets()%>" ></td>
</tr>
<tr>
	<td width="" align="right">是否共建设备：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="itemName" readonly value ="<%=item.getConstructStatus()%>" ></td>
	<td width="" align="right">是否共享设备：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="itemSpec" readonly value ="<%=item.getShareStatus()%>" />
</tr>
<%-- 
<tr>
	<td width="" align="right">支撑网设备类型描述：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="workorderObjectCode" readonly value ="<%=item.getSnName()%>" ></td>
	<td width="" align="right">支撑网设备类型编号：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="workorderObjectName" readonly value ="<%=item.getSnId()%>"></td>
</tr>
--%>
<tr> 
	<td width="" align="right">逻辑网络元素名称：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="logNetEle" readonly value ="<%=item.getLogNetEle()%>"></td>
	<td width="" align="right">逻辑网络元素编号：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="workorderObjectCode" readonly value ="<%=item.getNetUnitCode()%>" ></td>
</tr>

<tr>
	<td width="" align="right">投资分类名称：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="areaTypeName" readonly value ="<%=item.getInvestCatName()%>" ></td>
	<td width="" align="right">投资分类编号：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="investCatCode" readonly value ="<%=item.getInvestCatCode()%>"></td>
</tr>
<tr>
	<td width="" align="right">业务平台名称：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="responsibilityUserName" readonly value ="<%=item.getOpeName()%>"></td>
	<td width="" align="right">业务平台编号：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="employeeNumber" readonly value ="<%=item.getOpeCode()%>" ></td>
</tr>
<tr>
	<td width="" align="right">网络层次名称：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="responsibilityDeptName" readonly value ="<%=item.getLneName()%>"></td>
	<td width="" align="right">网络层次编号：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="specialityDeptName" readonly value ="<%=item.getLneCode()%>"></td>
</tr>

<tr>
	<td colspan="4" width="3%" class="mainTitleClass"><B>&nbsp;资产价值信息</B></td>
</tr>

<tr>
	<td width="" align="right">资产原值：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="cost" readonly value ="<%=item.getCost().trim()%>" ></td>
	<td width="" align="right">累计折旧：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="depreciation" readonly value ="<%=StrUtil.nullToString( item.getDepreciation() ).trim()%>"></td>
</tr>
	
<tr>
	<td width="" align="right">资产净值：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="netAssetValue" readonly value ="<%=item.getNetAssetValue().trim()%>"></td>
	<td width="" align="right">资产净额：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="deprnCost" readonly value ="<%=item.getDeprnCost().trim()%>"></td>
</tr>
<tr>
	<td width="" align="right">残值：</td>
	<td width=""><input style="width:100%" class="finput"type="text" name="scrapValue" readonly value ="<%=item.getScrapValue().trim()%>"></td>
	<td width="" align="right">本期折旧额：</td>
	<td width="">
	<input style="width:100%" class="finput" type="text" name="deprnAmount" readonly value ="<%=item.getDeprnAmount().trim()%>" ></td>
</tr>
 
<tr>
	<td width="" align="right">本年折旧额：</td> <%-- YTD_DEPRN --%>
	<td width=""><input style="width:100%" class="finput" type="text" name="ytdDeprn" readonly value ="<%=item.getYtdDeprn().trim()%>"></td>
	<td width="" align="right">本期减值准备：</td> <%-- IMPAIR_AMOUNT  YTD_IMPAIRMENT  IMPAIR_RESERVE impairAmount ytdImpairment --%>
	<td width=""><input style="width:100%" class="finput" type="text" name="impairAmount" readonly value ="<%=item.getImpairAmount().trim()%>"></td>
</tr>
<tr>
	<td width="" align="right">本年减值准备：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="ytdImpairment" readonly value ="<%=item.getYtdImpairment().trim()%>"></td> 
	<td width="" align="right">累计减值准备：</td>
	<td width=""><input style="width:100%" class="finput" type="text" name="impairReserve" readonly value ="<%=item.getImpairReserve().trim()%>"></td>
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
</table>