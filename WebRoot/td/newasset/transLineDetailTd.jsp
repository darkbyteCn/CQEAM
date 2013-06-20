<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/td/newasset/headerIncludeTd.jsp"%>
<%
	TdAssetsTransHeaderDTO headerDTO = (TdAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
	String transferType = headerDTO.getTransferType();
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
	TdAssetsTransLineDTO lineDTO = null;
	String barcode = "";

    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String orgId = userAccount.getOrganizationId();
	String toOrgId = headerDTO.getToOrganizationId();
    ServletConfigDTO servletConfig = headerDTO.getServletConfig();
    String provinceCode = servletConfig.getProvinceCode();
    if(transType.equals(AssetsDictConstant.ASS_FREE)){//资产闲置
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height=20px>
	            <td align=center width="8%">资产标签</td>
	            <td align=center width="6%">资产编号</td>
	            <td align=center width="10%">资产名称</td>
	            <td align=center width="10%">资产型号</td>
	            <td align=center width="6%">生产厂家</td>
	            <td align=center width="6%">启用日期</td>
	            <td align=center width="4%">数量</td>
				<td align=center width="3%">单位</td>
	            <td align=center width="16%">所在地点</td>
	            <td align=center width="8%">闲置说明</td>
	            <td align=center width="8%">其他说明</td>
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet != null && !lineSet.isEmpty()) {
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="8%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="6%" align="center"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="6%" align="right"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput3" readonly value="<%=lineDTO.getVendorName()%>"></td>
				<td width="6%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="4%" align="right"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="3%" align="center"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
				<td width="16%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason<%=i%>" class="finput" readonly value="<%=lineDTO.getLineReason()%>"></td>
				<td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>
			</tr>

<%
			}
		}
	} else if(transType.equals(AssetsDictConstant.ASS_SHARE)){//资产共享
%>

<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height=20px>
	            <td align=center width="8%">资产标签</td>
	            <td align=center width="6%">资产编号</td>
	            <td align=center width="10%">资产名称</td>
	            <td align=center width="10%">资产型号</td>
	            <td align=center width="6%">生产厂家</td>
	            <td align=center width="6%">启用日期</td>
	            <td align=center width="4%">数量</td>
				<td align=center width="3%">单位</td>
	            <td align=center width="16%">所在地点</td>
	            <td align=center width="8%">共享说明</td>
	            <td align=center width="8%">其他说明</td>
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet != null && !lineSet.isEmpty()) {
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="8%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="6%" align="center"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="6%" align="right"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput3" readonly value="<%=lineDTO.getVendorName()%>"></td>
				<td width="6%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="4%" align="right"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="3%" align="center"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
				<td width="16%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason<%=i%>" class="finput" readonly value="<%=lineDTO.getLineReason()%>"></td>
				<td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>
			</tr>

<%
			}
		}
	} else if(transType.equals(AssetsDictConstant.ASS_SELL)){//资产销售
%>

<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height=20px>
	            <td align=center width="8%">资产标签</td>
	            <td align=center width="6%">资产编号</td>
	            <td align=center width="10%">资产名称</td>
	            <td align=center width="10%">资产型号</td>
	            <td align=center width="6%">生产厂家</td>
	            <td align=center width="6%">启用日期</td>
	            <td align=center width="4%">数量</td>
				<td align=center width="3%">单位</td>
	            <td align=center width="16%">所在地点</td>
	            <td align=center width="8%">销售说明</td>
	            <td align=center width="8%">其他说明</td>
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet != null && !lineSet.isEmpty()) {
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="8%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="6%" align="center"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="6%" align="right"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput3" readonly value="<%=lineDTO.getVendorName()%>"></td>
				<td width="6%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="4%" align="right"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="3%" align="center"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
				<td width="16%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason<%=i%>" class="finput" readonly value="<%=lineDTO.getLineReason()%>"></td>
				<td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>
			</tr>


<%
			}
		}
	} else if(transType.equals(AssetsDictConstant.ASS_RENT)){//资产出租
%>

<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height=20px>
	            <td align=center width="8%">资产标签</td>
	            <td align=center width="6%">资产编号</td>
	            <td align=center width="10%">资产名称</td>
	            <td align=center width="10%">资产型号</td>
	            <td align=center width="6%">生产厂家</td>
	            <td align=center width="6%">启用日期</td>
	            <td align=center width="4%">数量</td>
				<td align=center width="3%">单位</td>
	            <td align=center width="16%">所在地点</td>
	            <td align=center width="8%">出租说明</td>
	            <td align=center width="8%">其他说明</td>
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet != null && !lineSet.isEmpty()) {
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="8%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="6%" align="center"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="6%" align="right"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput3" readonly value="<%=lineDTO.getVendorName()%>"></td>
				<td width="6%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="4%" align="right"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="3%" align="center"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
				<td width="16%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason<%=i%>" class="finput" readonly value="<%=lineDTO.getLineReason()%>"></td>
				<td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>
			</tr>


<%
			}
		}
	} else if(transType.equals(AssetsDictConstant.ASS_DONA)){ //资产捐赠
%>

<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height=20px>
	            <td align=center width="8%">资产标签</td>
	            <td align=center width="6%">资产编号</td>
	            <td align=center width="10%">资产名称</td>
	            <td align=center width="10%">资产型号</td>
	            <td align=center width="6%">生产厂家</td>
	            <td align=center width="6%">启用日期</td>
	            <td align=center width="4%">数量</td>
				<td align=center width="3%">单位</td>
	            <td align=center width="16%">所在地点</td>
	            <td align=center width="8%">捐赠说明</td>
	            <td align=center width="8%">其他说明</td>
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet != null && !lineSet.isEmpty()) {
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="8%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="6%" align="center"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="6%" align="right"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput3" readonly value="<%=lineDTO.getVendorName()%>"></td>
				<td width="6%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="4%" align="right"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="3%" align="center"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
				<td width="16%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason<%=i%>" class="finput" readonly value="<%=lineDTO.getLineReason()%>"></td>
				<td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>
			</tr>


<%
			}
		}
	} else if(!transType.equals(AssetsDictConstant.ASS_RED)){//报废或处置
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
<%
    String currRoleName = headerDTO.getCurrRoleName();
    String attribute4 = headerDTO.getAttribute4();
    if ((currRoleName.equals(servletConfig.getMtlAssetsMgr()) && userAccount.getOrganizationId().equals("82")) || (currRoleName.equals("资产会计") && userAccount.getOrganizationId().equals("82")) || attribute4.equals("FIND_ASSET")) {
%>
        <table class="headerTable" border="1" width="140%">
        <tr height="20px" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
<%if (!attribute4.equals("FIND_ASSET")) {%>
            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
<%}%>
            <td align=center width="10%">资产标签</td>
            <td align=center width="10%">资产编号</td>
            <td align=center width="10%">资产名称</td>
            <td align=center width="10%">资产型号</td>
            <td align=center width="9%">资产原值</td>
            <td align=center width="5%">净值</td>

            <td align=center width="9%">启用日期</td>
			<td align=center width="10%">所在地点</td>
            <td align=center width="5%">责任人</td>
            <td align=center width="10%">责任部门</td>
            <td align=center width="9%">备注</td>
        </tr>
		</table>
<%
    } else {
%>
       <table class="headerTable" border="1" width="140%">
        <tr height="20px">
            <td align=center width="10%">资产标签</td>
            <td align=center width="10%">资产编号</td>
            <td align=center width="10%">资产名称</td>
            <td align=center width="10%">资产型号</td>
            <td align=center width="7%">资产原值</td>
            <td align=center width="7%">净值</td>
            <td align=center width="7%">报废成本</td>
            <td align=center width="9%">启用日期</td>
			<td align=center width="10%">所在地点</td>
            <td align=center width="5%">责任人</td>
            <td align=center width="10%">责任部门</td>
            <td align=center width="9%">备注</td>
        </tr>
		</table>
<%}%>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet != null && !lineSet.isEmpty()) {
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
                if ((currRoleName.equals(servletConfig.getMtlAssetsMgr()) && userAccount.getOrganizationId().equals("82")) || (currRoleName.equals("资产会计") && userAccount.getOrganizationId().equals("82")) || attribute4.equals("FIND_ASSET")) {
%>
            <tr class="dataTR">
<%if (!attribute4.equals("FIND_ASSET")) {%>
                <td width="3%" align="center"><input type="checkbox" name="subCheck" value="<%=barcode%>" <%=lineDTO.getRemark().equals("")?"checked":""%>></td>
<%}%>
                <td width="10%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="10%" align="left"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="10%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="10%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="7%" align="right"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="7%" align="right"><input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
                <td width="7%" align="right"><input type="text" name="retirementCost" id="retirementCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getRetirementCost()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>             
                <td width="9%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput" readonly value="<%=lineDTO.getDatePlacedInService()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="10%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput3" readonly value="<%=lineDTO.getOldLocationName()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="5%" align="right"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="10%" align="left"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
                <td width="9%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
            </tr>

<%
               } else {
%>
            <tr class="dataTR" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="10%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
				<td width="10%" align="left"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="7%" align="right"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>"></td>
				<td width="7%" align="right"><input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>"></td>
                <td width="7%" align="right"><input type="text" name="retirementCost" id="retirementCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getRetirementCost()%>"></td>
                <td width="9%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="10%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput3" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="5%" align="right"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="10%" align="left"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>"></td>
                <td width="9%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
            </tr>
<%
               }
			}
		}
	} else {
		if(transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)){//部门内调拨
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="120%">
			<tr height="20px">
				<td align=center width="10%">资产标签</td>
				<td align=center width="7%">资产编号</td>
				<td align=center width="10%">资产名称</td>
				<td align=center width="10%">资产型号</td>
				<td align=center width="3%">数量</td>
				<td align=center width="13%">调出地点</td>
				<td align=center width="6%">原责任人</td>
				<td align=center width="13%">调入地点</td>
				<td align=center width="6%">新责任人</td>
				<td align=center width="6%">调拨日期</td>
				<td align=center width="13%">摘要</td>
			</tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:92%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
			if (lineSet != null && !lineSet.isEmpty()) {
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
					barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="10%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
				<td width="7%" align="left"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="3%" align="right"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="13%" align="center"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="6%" align="right"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="13%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getAssignedToLocationName()%>"></td>
				<td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityUserName()%>"></td>
				<td width="6%" align="center"><input type="text" name="lineTransDate" id="lineTransDate0" class="finput" readonly value="<%=lineDTO.getLineTransDate()%>"></td>
				<td width="13%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>
            </tr>
<%
				}
			}
		} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)){//部门间调拨
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height="20px">
	            <td align=center width="10%">资产标签</td>
	            <td align=center width="7%">资产编号</td>
	            <td align=center width="10%">资产名称</td>
	            <td align=center width="10%">资产型号</td>
	            <td align=center width="3%">数量</td>
	            <td align=center width="10%">调出地点</td>
	            <td align=center width="6%">原责任人</td>
	            <td align=center width="10%">调入部门</td>
				<td align=center width="10%">调入地点</td>
	            <td align=center width="6%">新责任人</td>
				<td align="center" width="5%">调拨日期</td>
	            <td align=center width="10%">备注</td>
	        </tr>
	    </table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
			if (lineSet != null && !lineSet.isEmpty()) {
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
					barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="10%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
				<td width="7%" align="left"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="3%" align="right"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="10%" align="center"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="6%" align="right"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="10%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityDeptName()%>"></td>
				<td width="10%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getAssignedToLocationName()%>"></td>
				<td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityUserName()%>"></td>
				<td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate0" class="finput" readonly value="<%=lineDTO.getLineTransDate()%>"></td>
				<td width="10%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
            </tr>
<%
				}
			}
		} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){//地市间调拨
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
	    <table width="350%" border="1" class="headerTable">
	        <tr height="20px">
                <td align=center width="69%" colspan="17">调出方</td>
	            <td align=center width="20%" colspan="5">调入方</td>
	            <td align=center width="5%" rowspan="2">调拨日期</td>
	            <td align=center width="5%" rowspan="2">备注</td>
            </tr>
	        <tr height="20px">
	            <td align=center width="5%">资产标签(旧)</td>
	            <td align=center width="5%">资产标签(新)</td>
	            <td align=center width="5%">资产名称</td>
	            <td align=center width="5%">资产型号</td>
	            <td align=center width="3%">数量</td>
	            <td align=center width="3%">原值</td>
	            <td align=center width="5%">累计折旧</td>
                <td align=center width="3%">减值准备</td>
                <td align=center width="3%">残值</td>
                <td align=center width="3%">资产净额</td>
                <td align=center width="5%">厂商</td>
                <td align=center width="3%">启用日期</td>
				<td align=center width="5%">调出部门</td>
	            <td align=center width="5%">调出地点</td>
	            <td align=center width="3%">原责任人</td>
	            <td align=center width="5%">原折旧账户</td>
	            <td align=center width="3%">原类别</td>
	            <td align=center width="5%">调入部门</td>
				<td align=center width="5%">调入地点</td>
				<td align=center width="3%">新责任人</td>
				<td align=center width="5%">新折旧账户</td>
	            <td align=center width="3%">新类别</td>
            </tr>
	    </table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="350%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
			if (lineSet != null && !lineSet.isEmpty()) {
				String attribute4 = headerDTO.getAttribute4();
                String inputClass1 = "finput";
				String inputClass2 = "finput2";
				String inputClass3 = "finput";
				String currRoleName = headerDTO.getCurrRoleName();
				if(provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SHAN) && transferType.equals(AssetsDictConstant.TRANS_BTW_COMP) && toOrgId.equals(orgId)){
					if(currRoleName.equals(servletConfig.getDeptAssetsMgr())){//部门资产管理员
						inputClass1 = "finputNoEmpty";
						inputClass2 = "finput2";
						inputClass3 = "finput";
					} else if(currRoleName.equals("资产会计")){//资产会计
						inputClass1 = "finput";
						inputClass2 = "finputNoEmpty2";
						inputClass3 = "finputNoEmpty";
					}
				}
                for (int i = 0; i < lineSet.getSize(); i++) {
					lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
					barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息">
				<td width="5%" align="center" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="5%" align="left"><input type="text" name="newBarcode" id="newBarcode<%=i%>" class="finput2" readonly value="<%=lineDTO.getNewBarcode()%>"></td>
				<td width="5%" align="left" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="5%" align="left" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="3%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="3%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>"></td>
				<td width="5%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="depreciation" id="depreciation<%=i%>" class="finput3" readonly value="<%=lineDTO.getDepreciation()%>"></td>
                <td width="3%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="impairReserve" id="impairReserve<%=i%>" class="finput3" readonly value="<%=lineDTO.getImpairReserve()%>"></td>
                <td width="3%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="scrapValue" id="scrapValue<%=i%>" class="finput3" readonly value="<%=lineDTO.getScrapValue()%>"></td>
                <td width="3%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>"></td>
                <td width="5%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="manufacturerName" id="manufacturerName<%=i%>" class="finput3" readonly value="<%=lineDTO.getManufacturerName()%>"></td>
                <td width="3%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="5%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>"></td>
				<td width="5%" align="center" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="3%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="5%" align="center" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="oldDepreciationAccount" id="oldDepreciationAccount<%=i%>" class="finput" readonly value="<%=lineDTO.getOldDepreciationAccount()%>"></td>
				<td width="3%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="oldFaCategoryCode" id="oldFaCategoryCode<%=i%>" class="finput2" readonly value="<%=lineDTO.getOldFaCategoryCode()%>"></td>
				<td width="5%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityDeptName()%>"></td>
				<td width="5%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="<%=inputClass1%>" readonly value="<%=lineDTO.getAssignedToLocationName()%>" title="点击选择或更改调入地点" onClick="do_SelectLocation(this)"></td>
				<td width="3%" align="left" ><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="<%=inputClass1%>" readonly value="<%=lineDTO.getResponsibilityUserName()%>" title="点击选择或更改新责任人" onClick="do_SelectPerson(this)"></td>
<%
				if(attribute4.indexOf(AssetsDictConstant.EDIT_ACCOUNT) == -1){
%>
				<%--<td width="5%" align="center" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="depreciationAccount" id="depreciationAccount<%=i%>" class="finput2" readonly value="<%=lineDTO.getDepreciationAccount()%>"></td>--%>
                <td width="5%"  onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="depreciationAccount" id="depreciationAccount<%=i%>" class="<%=inputClass2%>" readonly value="<%=lineDTO.getDepreciationAccount()%>"></td>
<%
				} else {
%>
				<%--<td width="5%" align="center"><input type="text" name="depreciationAccount" id="depreciationAccount<%=i%>" onClick="do_SelectDepreciationAccount(this)" title="点击选择或更改折旧账户" class="finput2" readonly value="<%=lineDTO.getDepreciationAccount()%>"></td>--%>
                <td width="5%" align="center"><input type="text" name="depreciationAccount" id="depreciationAccount<%=i%>" class="<%=inputClass2%>" readonly value="<%=lineDTO.getDepreciationAccount()%>" onClick="do_SelectDepreciationAccount(this)" title="点击选择或更改折旧账户"></td>
<%
				}
%>

				<%--<td width="3%" align="right" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="faCategoryCode" id="faCategoryCode<%=i%>" class="<%=inputClass3%>" readonly value="<%=lineDTO.getFaCategoryCode()%>"></td>--%>
                <td width="3%"><input type="text" name="faCategoryCode" id="faCategoryCode<%=i%>" class="finput" readonly value="<%=lineDTO.getFaCategoryCode()%>"  title="点击选择或更改新类别" onClick="do_SelectFaCategoryCode(this)"></td>
                <td width="5%" align="center" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>" class="finput2" readonly value="<%=lineDTO.getLineTransDate()%>"></td>
				<td width="5%" align="left" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>

                <td style="display:none">
					<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
					<input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
					<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
					<input type="hidden" name="responsibilityDept" id="responsibilityDept<%=i%>" value="<%=lineDTO.getResponsibilityDept()%>">
					<input type="hidden" name="assignedToLocation" id="assignedToLocation<%=i%>" value="<%=lineDTO.getAssignedToLocation()%>">
					<input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>" value="<%=lineDTO.getResponsibilityUser()%>">
					<input type="hidden" name="addressId" id="addressId<%=i%>" value="<%=lineDTO.getAddressId()%>">
				</td>
            </tr>
<%
				}
			}
		}
	}
%>
        </table>
    </div>
