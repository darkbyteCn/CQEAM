<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
	String transferType = headerDTO.getTransferType();
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
	AmsAssetsTransLineDTO lineDTO = null;
	String barcode = "";
	if(transType.equals(AssetsDictConstant.ASS_SUB)){//资产减值
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="160%">
	        <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
	            <td align=center width="4%">网元编号</td>
	            <td align=center width="4%">资产编号</td>
                <td align=center width="5%">资产标签号</td>
                <td align=center width="8%">资产名称</td>
	            <td align=center width="8%">供应商</td>
	            <td align=center width="4%">规格型号</td>
	            <td align=center width="4%">启用日期</td>
	            <td align=center width="5%">软件在用版本</td>
				<td align=center width="5%">软件减值版本</td>
	            <td align=center width="4%">原值</td>
	            <td align=center width="4%">累计折旧</td>
	            <td align=center width="4%">净值</td>
	            <td align=center width="4%">拟提减值</td>
	            <td align=center width="8%">备注</td>
            </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="160%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		for (int i = 0; i < lineSet.getSize(); i++) {
			lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
			barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="4%"><input type="text" class="finput2" readonly value="<%=lineDTO.getNetUnit()%>"></td>
				<td width="4%"><input type="text" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="5%"><input type="text" class="finput2" readonly value="<%=lineDTO.getBarcode()%>"></td>
				<td width="8%"><input type="text" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="8%"><input type="text" class="finput" readonly value="<%=lineDTO.getVendorName()%>"></td>
				<td width="4%"><input type="text" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="4%"><input type="text" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="5%"><input type="text" class="finput" readonly value="<%=lineDTO.getSoftInuseVersion()%>"></td>
				<td width="5%"><input type="text" class="finput" readonly value="<%=lineDTO.getSoftDevalueVersion()%>"></td>
				<td width="4%"><input type="text" class="finput3" readonly value="<%=lineDTO.getCost()%>"></td>
				<td width="4%"><input type="text" class="finput3" readonly value="<%=lineDTO.getDepreciation()%>"></td>
				<td width="4%"><input type="text" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>"></td>
				<td width="4%"><input type="text" class="finput3" readonly value="<%=lineDTO.getPrepareDevalue()%>"></td>
				<td width="8%"><input type="text" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>
            </tr>
<%
		}
	} else if(transType.equals(AssetsDictConstant.ASS_FREE)){//资产闲置
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
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
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
		<table class="headerTable" border="1" width="140%">
        <tr height="20px">
            <td align=center width="10%">资产标签</td>
            <td align=center width="10%">资产编号</td>
            <td align=center width="10%">资产名称</td>
            <td align=center width="10%">资产型号</td>
            <td align=center width="9%">资产原值</td>
            <td align=center width="9%">启用日期</td>
            <td align=center width="9%">净值</td>
			<td align=center width="9%">所在地点</td>
            <td align=center width="10%">责任人</td>
            <td align=center width="10%">责任部门</td>
        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet != null && !lineSet.isEmpty()) {
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="10%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
				<td width="10%" align="left"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="9%" align="right"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>"></td>
				<td width="9%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="9%" align="right"><input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>"></td>
				<td width="9%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput3" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="10%" align="right"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="10%" align="left"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>"></td>
            </tr>

<%
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
					lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
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
                <td width="6%" align="center"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>" class="finput" readonly value="<%=lineDTO.getLineTransDate()%>"></td>
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
					lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
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
	            <td align=center width="64%" colspan="14">调出方</td>
	            <td align=center width="25%" colspan="5">调入方</td>
	            <td align=center width="5%" rowspan="2">调拨日期</td>
	            <td align=center width="7%" rowspan="2">备注</td>
	        </tr>
	        <tr height="20px">
	            <td align=center width="5%">资产标签(旧)</td>
	            <td align=center width="5%">资产标签(新)</td>
	            <td align=center width="5%">资产名称</td>
	            <td align=center width="5%">资产型号</td>
	            <td align=center width="3%">数量</td>
	            <td align=center width="3%">原值</td>
	            <td align=center width="5%">累计折旧</td>
	            <td align=center width="3%">残值</td>
	            <td align=center width="5%">启用日期</td>
				<td align=center width="5%">调出部门</td>
	            <td align=center width="5%">调出地点</td>
	            <td align=center width="5%">原责任人</td>
	            <td align=center width="5%">原折旧账户</td>
	            <td align=center width="5%">原类别</td>
	            <td align=center width="5%">调入部门</td>
				<td align=center width="5%">调入地点</td>
				<td align=center width="5%">新责任人</td>
				<td align=center width="5%">新折旧账户</td>
	            <td align=center width="5%">新类别</td>
	        </tr>
	    </table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:66px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="350%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
			if (lineSet != null && !lineSet.isEmpty()) {
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
					barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息">
				<td width="5%" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="5%"><input type="text" name="newBarcode" id="newBarcode<%=i%>" class="finput2" readonly value="<%=lineDTO.getNewBarcode()%>"></td>
				<td width="5%"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="5%"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="3%"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="3%"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>"></td>
				<td width="5%"><input type="text" name="depreciation" id="depreciation<%=i%>" class="finput3" readonly value="<%=lineDTO.getDepreciation()%>"></td>
				<td width="3%"><input type="text" name="scrapValue" id="scrapValue<%=i%>" class="finput3" readonly value="<%=lineDTO.getScrapValue()%>"></td>
				<td width="5%"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="5%"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>"></td>
				<td width="5%"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="5%"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="5%"><input type="text" name="oldDepreciationAccount" id="oldDepreciationAccount<%=i%>" class="finput" readonly value="<%=lineDTO.getOldDepreciationAccount()%>"></td>
				<td width="5%"><input type="text" name="oldFaCategoryCode" id="oldFaCategoryCode<%=i%>" class="finput" readonly value="<%=lineDTO.getOldFaCategoryCode()%>"></td>
				<td width="5%"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityDeptName()%>"></td>
				<td width="5%"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getAssignedToLocationName()%>"></td>
				<td width="5%"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityUserName()%>"></td>
				<td width="5%"><input type="text" name="depreciationAccount" id="depreciationAccount<%=i%>" class="finput" readonly value="<%=lineDTO.getDepreciationAccount()%>"></td>
				<td width="5%"><input type="text" name="faCategoryCode" id="faCategoryCode<%=i%>" class="finput" readonly value="<%=lineDTO.getFaCategoryCode()%>"></td>
				<td width="5%"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>" class="finput" readonly value="<%=lineDTO.getLineTransDate()%>"></td>
				<td width="7%"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>
            </tr>
<%
				}
			}
		}
	}
%>
        </table>
    </div>
