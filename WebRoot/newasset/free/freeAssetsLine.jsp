<%@ page import="com.sino.ams.newasset.dto.AmsAssetsTransLineDTO" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
	String transferType = headerDTO.getTransferType();
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
    DTOSet lineSetPri = (DTOSet) request.getAttribute(AssetsWebAttributes.PRIVI_DATA);//EXCEL导入时导入不成功的DTOSET
    List list = (List)request.getAttribute("REMARK_LIST");

    if(transType.equals(AssetsDictConstant.ASS_SUB)){//资产减值
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="160%">
	        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
	            <td align=center width="2%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
	            <td align=center width="4%">网元编号</td>
	            <td align=center width="4%">资产编号</td>
                <td align=center width="5%">标签号</td>
                <td align=center width="8%">资产名称</td>
	            <td align=center width="8%">供应商</td>
	            <td align=center width="4%">资产型号</td>
	            <td align=center width="4%">启用日期</td>
	            <td align=center width="5%">软件在用版本</td>
				<td align=center width="5%">软件减值版本</td>
	            <td align=center width="4%">原值</td>
	            <td align=center width="4%">累计折旧</td>
	            <td align=center width="4%">净值</td>
	            <td align=center width="4%">拟提减值</td>
	            <td align=center width="8%">备注</td>
                <td style="display:none">隐藏域字段</td>
            </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="160%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet == null || lineSet.isEmpty()) {
%>
            <tr class="dataTR" onClick="executeClick(this)" style="display:none" title="点击查看资产详细信息">
				<td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
                <td width="4%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick=""><input type="text" name="netUnit" id="netUnit0" class="finput2"  value=""></td>
				<td width="4%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput2" readonly value=""></td>
                <td width="5%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
                <td width="8%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
                <td width="8%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName0" class="finput" readonly value=""></td>
				<td width="4%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
				<td width="4%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput3" readonly value=""></td>
				<td width="5%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick=""><input type="text" name="softInuseVersion" id="softInuseVersion0" class="finput2"  value=""></td>
				<td width="5%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick=""><input type="text" name="softDevalueVersion" id="softDevalueVersion0" class="finput2"  value=""></td>
				<td width="4%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="cost" id="cost0" class="finput3" readonly value=""></td>
				<td width="4%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="depreciation" id="depreciation0" class="finput3" readonly value=""></td>
				<td width="4%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="deprnCost" id="deprnCost0" class="finput3" readonly value=""></td>
				<td width="4%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick=""><input type="text" name="prepareDevalue" id="prepareDevalue0" onkeydown="floatOnly(this.value);" class="finput2"  value=""></td>
				<td width="8%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick=""><input type="text" name="remark" id="remark0" class="finput"  value=""></td>
                <td style="display:none">
                    <input type="hidden" name="assetId" id="assetId0" value="">
                </td>
            </tr>
<%
		} else {
			AmsAssetsTransLineDTO lineDTO = null;
			String barcode = "";
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" onClick="executeClick(this)" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息">
				<td width="2%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
				<td width="4%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick=""><input type="text" name="netUnit" id="netUnit<%=i%>" class="finput2"  value="<%=lineDTO.getNetUnit()%>"></td>
				<td width="4%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="5%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=lineDTO.getBarcode()%>"></td>
				<td width="8%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="8%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput" readonly value="<%=lineDTO.getVendorName()%>"></td>
				<td width="4%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="4%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput3" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="5%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick=""><input type="text" name="softInuseVersion" id="softInuseVersion<%=i%>" class="finput2"  value="<%=lineDTO.getSoftInuseVersion()%>"></td>
				<td width="5%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick=""><input type="text" name="softDevalueVersion" id="softDevalueVersion<%=i%>" class="finput2"  value="<%=lineDTO.getSoftDevalueVersion()%>"></td>
				<td width="4%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="cost" id="cost<%=i%>" class="finput2" readonly value="<%=lineDTO.getCost()%>"></td>
				<td width="4%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="depreciation" id="depreciation<%=i%>" class="finput2" readonly value="<%=lineDTO.getDepreciation()%>"></td>
				<td width="4%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>"></td>
				<td width="4%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick=""><input type="text" name="prepareDevalue" id="prepareDevalue<%=i%>" onkeydown="floatOnly(this.value);" class="finput2"  value="<%=lineDTO.getPrepareDevalue()%>"></td>
				<td width="8%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick=""><input type="text" name="remark" id="remark<%=i%>" class="finput"  value="<%=lineDTO.getRemark()%>"></td>
                <td style="display:none">
                    <input type="hidden" name="assetId" id="assetId0" value="<%=lineDTO.getAssetId()%>">
                </td>
            </tr>
<%	
			}
		}
	}  else if(transType.equals(AssetsDictConstant.ASS_SHARE)){//资产共享
    	%>
    		<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
    			<table class="headerTable" border="1" width="140%">
    		        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
    		            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
    		            <td align=center width="8%">标签号</td>
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
    					<td style="display:none">隐藏域字段</td>
    		        </tr>
    			</table>
    		</div>
    		<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    	        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
    	<%
    			if (lineSet == null || lineSet.isEmpty()) {
    	%>
    	            <tr class="dataTR" onClick="executeClick(this)" style="display:none" title="点击查看资产详细信息">
    					<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
    					<td width="8%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput2" readonly value=""></td>
    					<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
    					<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName0" class="finput3" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput2" readonly value=""></td>
    					<td width="4%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
    					<td width="3%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure0" class="finput" readonly value=""></td>
    					<td width="16%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
    					<td width="8%" align="center"><input type="text" name="lineReason" id="lineReason0" class="finput" value=""></td>
    					<td width="8%" align="center"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
    					<td style="display:none">
    						<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept0" value="">
    						<input type="hidden" name="oldLocation" id="oldLocation0" value="">
    						<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value="">
    					</td>
    	            </tr>
    	<%
    			} else {
    				AmsAssetsTransLineDTO lineDTO = null;
    				String barcode = "";
    				for (int i = 0; i < lineSet.getSize(); i++) {
    					lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
    					barcode = lineDTO.getBarcode();
    	%>
    	            <tr class="dataTR" onClick="executeClick(this)" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息">
    					<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
    					<td width="8%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
    					<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
    					<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
    					<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getModelNumber()%>"></td>
    					<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput2" readonly value="<%=lineDTO.getVendorName()%>"></td>
    					<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
    					<td width="4%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput2" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
    					<td width="3%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput2" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
    					<td width="16%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput2" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
    					<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason<%=i%>" class="finput" value="<%=lineDTO.getLineReason()%>"></td>
    					<td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
    					<td style="display:none">
    						<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
    						<input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
    						<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
    					</td>
    	            </tr>
    	            
 <%	
			}
		}
	}  else if(transType.equals(AssetsDictConstant.ASS_SELL)){//资产销售
    	%>
    		<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
    			<table class="headerTable" border="1" width="140%">
    		        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
    		            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
    		            <td align=center width="8%">标签号</td>
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
    					<td style="display:none">隐藏域字段</td>
    		        </tr>
    			</table>
    		</div>
    		<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    	        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
    	<%
    			if (lineSet == null || lineSet.isEmpty()) {
    	%>
    	            <tr class="dataTR" onClick="executeClick(this)" style="display:none" title="点击查看资产详细信息">
    					<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
    					<td width="8%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput2" readonly value=""></td>
    					<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
    					<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName0" class="finput3" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput2" readonly value=""></td>
    					<td width="4%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
    					<td width="3%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure0" class="finput" readonly value=""></td>
    					<td width="16%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
    					<td width="8%" align="center"><input type="text" name="lineReason" id="lineReason0" class="finput" value=""></td>
    					<td width="8%" align="center"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
    					<td style="display:none">
    						<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept0" value="">
    						<input type="hidden" name="oldLocation" id="oldLocation0" value="">
    						<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value="">
    					</td>
    	            </tr>
    	<%
    			} else {
    				AmsAssetsTransLineDTO lineDTO = null;
    				String barcode = "";
    				for (int i = 0; i < lineSet.getSize(); i++) {
    					lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
    					barcode = lineDTO.getBarcode();
    	%>
    	            <tr class="dataTR" onClick="executeClick(this)" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息">
    					<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
    					<td width="8%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
    					<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
    					<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
    					<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getModelNumber()%>"></td>
    					<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput2" readonly value="<%=lineDTO.getVendorName()%>"></td>
    					<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
    					<td width="4%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput2" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
    					<td width="3%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput2" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
    					<td width="16%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput2" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
    					<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason<%=i%>" class="finput" value="<%=lineDTO.getLineReason()%>"></td>
    					<td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
    					<td style="display:none">
    						<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
    						<input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
    						<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
    					</td>
    	            </tr>
<%	
			}
		}
	}  else if(transType.equals(AssetsDictConstant.ASS_RENT)){//资产出租
    	%>
    		<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
    			<table class="headerTable" border="1" width="140%">
    		        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
    		            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
    		            <td align=center width="8%">标签号</td>
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
    					<td style="display:none">隐藏域字段</td>
    		        </tr>
    			</table>
    		</div>
    		<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    	        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
    	<%
    			if (lineSet == null || lineSet.isEmpty()) {
    	%>
    	            <tr class="dataTR" onClick="executeClick(this)" style="display:none" title="点击查看资产详细信息">
    					<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
    					<td width="8%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput2" readonly value=""></td>
    					<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
    					<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName0" class="finput3" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput2" readonly value=""></td>
    					<td width="4%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
    					<td width="3%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure0" class="finput" readonly value=""></td>
    					<td width="16%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
    					<td width="8%" align="center"><input type="text" name="lineReason" id="lineReason0" class="finput" value=""></td>
    					<td width="8%" align="center"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
    					<td style="display:none">
    						<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept0" value="">
    						<input type="hidden" name="oldLocation" id="oldLocation0" value="">
    						<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value="">
    					</td>
    	            </tr>
    	<%
    			} else {
    				AmsAssetsTransLineDTO lineDTO = null;
    				String barcode = "";
    				for (int i = 0; i < lineSet.getSize(); i++) {
    					lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
    					barcode = lineDTO.getBarcode();
    	%>
    	            <tr class="dataTR" onClick="executeClick(this)" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息">
    					<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
    					<td width="8%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
    					<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
    					<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
    					<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getModelNumber()%>"></td>
    					<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput2" readonly value="<%=lineDTO.getVendorName()%>"></td>
    					<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
    					<td width="4%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput2" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
    					<td width="3%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput2" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
    					<td width="16%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput2" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
    					<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason<%=i%>" class="finput" value="<%=lineDTO.getLineReason()%>"></td>
    					<td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
    					<td style="display:none">
    						<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
    						<input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
    						<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
    					</td>
    	            </tr>
    	            <%	
			}
		}
	}  else if(transType.equals(AssetsDictConstant.ASS_DONA)){//资产捐赠
    	%>
    		<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
    			<table class="headerTable" border="1" width="140%">
    		        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
    		            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
    		            <td align=center width="8%">标签号</td>
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
    					<td style="display:none">隐藏域字段</td>
    		        </tr>
    			</table>
    		</div>
    		<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    	        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
    	<%
    			if (lineSet == null || lineSet.isEmpty()) {
    	%>
    	            <tr class="dataTR" onClick="executeClick(this)" style="display:none" title="点击查看资产详细信息">
    					<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
    					<td width="8%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput2" readonly value=""></td>
    					<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
    					<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName0" class="finput3" readonly value=""></td>
    					<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput2" readonly value=""></td>
    					<td width="4%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
    					<td width="3%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure0" class="finput" readonly value=""></td>
    					<td width="16%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
    					<td width="8%" align="center"><input type="text" name="lineReason" id="lineReason0" class="finput" value=""></td>
    					<td width="8%" align="center"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
    					<td style="display:none">
    						<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept0" value="">
    						<input type="hidden" name="oldLocation" id="oldLocation0" value="">
    						<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value="">
    					</td>
    	            </tr>
    	<%
    			} else {
    				AmsAssetsTransLineDTO lineDTO = null;
    				String barcode = "";
    				for (int i = 0; i < lineSet.getSize(); i++) {
    					lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
    					barcode = lineDTO.getBarcode();
    	%>
    	            <tr class="dataTR" onClick="executeClick(this)" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息">
    					<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
    					<td width="8%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
    					<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
    					<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
    					<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getModelNumber()%>"></td>
    					<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput2" readonly value="<%=lineDTO.getVendorName()%>"></td>
    					<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
    					<td width="4%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput2" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
    					<td width="3%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput2" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
    					<td width="16%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput2" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
    					<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason<%=i%>" class="finput" value="<%=lineDTO.getLineReason()%>"></td>
    					<td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
    					<td style="display:none">
    						<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
    						<input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
    						<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
    					</td>
    	            </tr>
    	<%	
    				}
    			}
    		} else if(transType.equals(AssetsDictConstant.ASS_FREE)){//资产闲置
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
	            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
	            <td align=center width="8%">标签号</td>
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
				<td style="display:none">隐藏域字段</td>
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet == null || lineSet.isEmpty()) {
%>
            <tr class="dataTR" onClick="executeClick(this)" style="display:none" title="点击查看资产详细信息">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
				<td width="8%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
				<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput2" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
				<td width="6%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName0" class="finput3" readonly value=""></td>
				<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput2" readonly value=""></td>
				<td width="4%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
				<td width="3%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure0" class="finput" readonly value=""></td>
				<td width="16%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
				<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason0" class="finput" value=""></td>
				<td width="8%" align="left"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
				<td style="display:none">
					<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept0" value="">
					<input type="hidden" name="oldLocation" id="oldLocation0" value="">
					<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value="">
				</td>
            </tr>
<%
		} else {
			AmsAssetsTransLineDTO lineDTO = null;
			String barcode = "";
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" onClick="executeClick(this)" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
				<td width="8%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput3" readonly value="<%=lineDTO.getVendorName()%>"></td>
				<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="4%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="3%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
				<td width="16%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=barcode%>')"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="8%" align="left"><input type="text" name="lineReason" id="lineReason<%=i%>" class="finput" value="<%=lineDTO.getLineReason()%>"></td>
				<td width="8%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
				<td style="display:none">
					<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
					<input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
					<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
				</td>
            </tr>
<%	
			}
		}
	} else if(!transType.equals(AssetsDictConstant.ASS_RED)){//报废或处置
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
	            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
	            <td align=center width="8%">标签号</td>
	            <td align=center width="6%">资产编号</td>
	            <td align=center width="10%">资产名称</td>
	            <td align=center width="10%">资产型号</td>
	            <td align=center width="6%">资产原值</td>
                <td align=center width="6%">净值</td>
                <td align=center width="6%">报废成本</td>
                <td align=center width="6%">启用日期</td>
				<td align=center width="20%">所在地点</td>
	            <td align=center width="6%">责任人</td>
	            <td align=center width="20%">责任部门</td>
				<td style="display:none">隐藏域字段</td>
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet == null || lineSet.isEmpty()) {
%>
            <tr class="dataTR" onClick="executeClick(this)" style="display:none" title="点击查看资产详细信息">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
				<td width="8%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
				<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput2" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
				<td width="6%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="cost" id="cost0" class="finput3" readonly value=""></td>
				<td width="6%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="deprnCost" id="deprnCost0" class="finput3" readonly value=""></td>
                <td width="6%" align="right"><input type="text" name="retirementCost" id="retirementCost0" class="finputNoEmpty" value=""></td>
                <td width="6%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput2" readonly value=""></td>
                <td width="20%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
				<td width="6%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName0" class="finput" readonly value=""></td>
				<td width="20%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName0" class="finput" readonly value=""></td>
				<td style="display:none">
					<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept0" value="">
					<input type="hidden" name="oldLocation" id="oldLocation0" value="">
					<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value="">
				</td>
            </tr>
<%
		} else {
			AmsAssetsTransLineDTO lineDTO = null;
			String barcode = "";
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" onClick="executeClick(this)" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
				<td width="8%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>"></td>
				<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>"></td>
                <td width="6%" align="right"><input type="text" name="retirementCost" id="retirementCost<%=i%>" class="finputNoEmpty" value="<%=lineDTO.getRetirementCost()%>" onchange="do_setQuantity();"></td>
                <td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
                <td width="20%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="20%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>"></td>
				<td style="display:none">
					<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
					<input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
					<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
				</td>
            </tr>
<%
			}
		}
	} else {
		if(transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)){//部门内调拨
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="120%">
			<tr height="23px" onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
				<td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
				<td align=center width="10%">标签号</td>
				<td align=center width="7%">资产编号</td>
				<td align=center width="10%">资产名称</td>
				<td align=center width="10%">资产型号</td>
				<td align=center width="3%">数量</td>
				<td align=center width="13%">调出地点</td>
				<td align=center width="6%">原责任人</td>
				<td align=center width="13%">调入地点</td>
				<td align=center width="6%">新责任人</td>
				<td align=center width="8%">调拨日期</td>
				<td align=center width="11%">摘要</td>
				<td style="display:none">隐藏域字段</td>
			</tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
			if (lineSet == null || lineSet.isEmpty()) {
%>
            <tr id="model" class="dataTR" onClick="executeClick(this)" style="display:none">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
				<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput" readonly value=""></td>
				<td width="7%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
				<td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
				<td width="13%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
				<td width="6%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName0" class="finput" readonly value=""></td>
				<td width="13%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName0" class="finputNoEmpty" readonly value="" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点" style="cursor:pointer"></td>
				<td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName0" class="finputNoEmpty" readonly value="" onClick="do_SelectPerson(this)" title="点击选择或更改新责任人" style="cursor:pointer"></td>
				<td width="8%" align="center"><input type="text" name="lineTransDate" id="lineTransDate0" class="finput2" style="cursor:pointer" readonly value="" onclick="gfPop.fPopCalendar(lineTransDate0)" title="点击选择或更改调拨日期"></td>
				<td width="11%" align="left"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
                <td style="display:none"><input type="hidden" name="oldLocation" id="oldLocation0" value=""></td>
			    <td style="display:none"><input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value=""></td>
				<td style="display:none"><input type="hidden" name="assignedToLocation" id="assignedToLocation0" value=""></td>
				<td style="display:none"><input type="hidden" name="responsibilityUser" id="responsibilityUser0" value=""></td>
				<td style="display:none"><input type="hidden" name="addressId" id="addressId0" value=""></td>
            </tr>
<%
			} else {
				AmsAssetsTransLineDTO lineDTO = null;
				String barcode = "";
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
					barcode = lineDTO.getBarcode();
%>
            <tr id="model" class="dataTR" onClick="executeClick(this)" style="cursor:pointer">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
				<td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
				<td width="7%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="13%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="13%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finputNoEmpty" readonly value="<%=lineDTO.getAssignedToLocationName()%>" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点" style="cursor:pointer"></td>
				<td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finputNoEmpty" readonly value="<%=lineDTO.getResponsibilityUserName()%>" onClick="do_SelectPerson(this)"  title="点击选择或更改新责任人" style="cursor:pointer"></td>
				<td width="8%" align="center"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>" class="finput2" style="cursor:pointer" readonly value="<%=lineDTO.getLineTransDate()%>" onclick="gfPop.fPopCalendar(lineTransDate<%=i%>)" title="点击选择或更改调拨日期"></td>
				<td width="11%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
                <td style="display:none"><input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>"></td>
				<td style="display:none"><input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>"></td>
				<td style="display:none"><input type="hidden" name="assignedToLocation" id="assignedToLocation<%=i%>" value="<%=lineDTO.getAssignedToLocation()%>"></td>
				<td style="display:none"><input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>" value="<%=lineDTO.getResponsibilityUser()%>"></td>
				<td style="display:none"><input type="hidden" name="addressId" id="addressId<%=i%>" value="<%=lineDTO.getAddressId()%>"></td>
            </tr>
<%
				}
			}
		} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)){//部门间调拨
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
	            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
	            <td align=center width="10%">标签号</td>
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
				<td style="display:none">隐藏域字段</td>
	        </tr>
	    </table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
			if (lineSet == null || lineSet.isEmpty()) {
%>
            <tr id="model" class="dataTR" onClick="executeClick(this)" style="display:none">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
				<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput" readonly value=""></td>
				<td width="7%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
				<td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
				<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
				<td width="6%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName0" class="finput" readonly value=""></td>
				<td width="10%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName0" class="finputNoEmpty" readonly value="" onClick="do_SelectDept(this)" title="点击选择或更改调入部门" style="cursor:pointer"></td>
				<td width="10%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName0" class="finput" readonly value="" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点" style="cursor:pointer"></td>
				<td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName0" class="finput" readonly value="" onClick="do_SelectPerson(this)" title="点击选择或更改新责任人" style="cursor:pointer"></td>
				<td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate0" class="finput2" style="cursor:pointer" readonly value="" onclick="gfPop.fPopCalendar(lineTransDate0)" title="点击选择或更改调拨日期"></td>
				<td width="10%" align="left"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
				<td style="display:none"><input type="hidden" name="oldLocation" id="oldLocation0" value=""></td>
				<td style="display:none"><input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value=""></td>
                <td style="display:none"><input type="hidden" name="responsibilityDept" id="responsibilityDept0" value=""></td>
                <td style="display:none"><input type="hidden" name="assignedToLocation" id="assignedToLocation0" value=""></td>
			    <td style="display:none"><input type="hidden" name="responsibilityUser" id="responsibilityUser0" value=""></td>
				<td style="display:none"><input type="hidden" name="addressId" id="addressId0" value=""></td>
                <%--<td style="display:none"><input type="hidden" name="toGroup" id="toGroup" value=""></td>--%>
            </tr>
<%
			} else {
				AmsAssetsTransLineDTO lineDTO = null;
				String barcode = "";
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
					barcode = lineDTO.getBarcode();
%>
            <tr id="model" class="dataTR" onClick="executeClick(this)" style="cursor:pointer">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
				<td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
				<td width="7%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="10%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName<%=i%>" class="finputNoEmpty" readonly value="<%=lineDTO.getResponsibilityDeptName()%>" onClick="do_SelectDept(this)" title="点击选择或更改调入部门" style="cursor:pointer"></td>
				<td width="10%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getAssignedToLocationName()%>" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点" style="cursor:pointer"></td>
				<td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityUserName()%>" onClick="do_SelectPerson(this)"  title="点击选择或更改新责任人" style="cursor:pointer"></td>
				<td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>" class="finput2" style="cursor:pointer" readonly value="<%=lineDTO.getLineTransDate()%>" onclick="gfPop.fPopCalendar(lineTransDate<%=i%>)" title="点击选择或更改调拨日期"></td>
				<td width="10%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
				<td style="display:none">
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
		} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){//地市间调拨
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
	    <table width="250%" border="1" class="headerTable">
	        <tr height=23px onClick="executeClick(this)" id="headerTr" style="cursor:pointer" title="点击全选或取消全选">
	            <td align=center width="1%" rowspan="2"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
	            <td align=center width="69%" colspan="17">调出方</td>
	            <td align=center width="20%" colspan="5">调入方</td>
	            <td align=center width="5%" rowspan="2">调拨日期</td>
	            <td align=center width="5%" rowspan="2">备注</td>
				<td style="display:none" rowspan="2">隐藏域字段</td>
	        </tr>
	        <tr height=20px onClick="headerTr.click()" style="cursor:pointer" title="点击全选或取消全选">
	            <td align=center width="5%">标签号</td>
	            <td align=center width="3%">资产编号</td>
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
        <table id="dataTable" width="250%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
			if (lineSet == null || lineSet.isEmpty()) {
%>
            <tr id="model" class="dataTR" onClick="executeClick(this)" style="display:none">
				<td width="1%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
				<td width="5%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput" readonly value=""></td>
				<td width="3%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput" readonly value=""></td>
				<td width="5%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
				<td width="5%" align="left" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
				<td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
				<td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="cost" id="cost0" class="finput3" readonly value=""></td>
				<td width="5%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="depreciation" id="depreciation0" class="finput3" readonly value=""></td>
                <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="impairReserve" id="impairReserve0" class="finput3" readonly value=""></td>
                <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="scrapValue" id="scrapValue0" class="finput3" readonly value=""></td>
                <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="deprnCost" id="deprnCost0" class="finput3" readonly value=""></td>
                <td width="5%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="manufacturerName" id="manufacturerName0" class="finput3" readonly value=""></td>
                <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput2" readonly value=""></td>
				<td width="5%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName0" class="finput" readonly value=""></td>
				<td width="5%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
				<td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName0" class="finput" readonly value=""></td>
				<td width="5%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldDepreciationAccount" id="oldDepreciationAccount0" class="finput" readonly value=""></td>
				<td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldFaCategoryCode" id="oldFaCategoryCode0" class="finput" readonly value=""></td>
				<td width="5%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName0" class="finputNoEmpty" style="cursor:pointer" readonly value="" onClick="do_SelectDept(this)" title="点击选择或更改调入部门"></td>
				<td width="5%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName0" class="finput2" style="cursor:pointer" readonly value="" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点"></td>
				<td width="3%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName0" class="finput2" style="cursor:pointer" readonly value="" onClick="do_SelectPerson(this)" title="点击选择或更改新责任人"></td>
				<td width="5%" align="center"><input type="text" name="depreciationAccount" id="depreciationAccount0" class="finput2" style="cursor:pointer" readonly value="" onClick="do_SelectDepreciationAccount(this)" title="点击选择或更改折旧账户"></td>
				<td width="3%" align="right"><input type="text" name="faCategoryCode" id="faCategoryCode0" class="finput2" style="cursor:pointer" readonly onClick="do_SelectFaCategoryCode(this)" title="点击选择或更改新类别"></td>
				<td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate0" class="finput2" readonly value="" onclick="gfPop.fPopCalendar(lineTransDate0)" title="点击选择或更改调拨日期"></td>
				<td width="5%" align="left"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
				<td style="display:none"><input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept0" value=""></td>
				<td style="display:none"><input type="hidden" name="oldLocation" id="oldLocation0" value=""></td>
				<td style="display:none"><input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value=""></td>
                <td style="display:none"><input type="hidden" name="responsibilityDept" id="responsibilityDept0" value=""></td>
                <td style="display:none"><input type="hidden" name="assignedToLocation" id="assignedToLocation0" value=""></td>
				<td style="display:none"><input type="hidden" name="responsibilityUser" id="responsibilityUser0" value=""></td>
				<td style="display:none"><input type="hidden" name="addressId" id="addressId0" value=""></td>
            </tr>
<%
			} else {			
				AmsAssetsTransLineDTO lineDTO = null;
				String barcode = "";
				for (int i = 0; i < lineSet.getSize(); i++) {
					lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
					barcode = lineDTO.getBarcode();
%>
            <tr id="model" class="dataTR" onClick="executeClick(this)" style="cursor:pointer">
				<td width="1%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
				<td width="5%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
				<td width="3%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="5%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="5%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>"></td>
				<td width="5%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="depreciation" id="depreciation<%=i%>" class="finput3" readonly value="<%=lineDTO.getDepreciation()%>"></td>
                <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="impairReserve" id="impairReserve" class="finput3" readonly value="<%=lineDTO.getImpairReserve()%>"></td>
                <td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="scrapValue" id="scrapValue<%=i%>" class="finput3" readonly value="<%=lineDTO.getScrapValue()%>"></td>
                <td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="deprnCost" id="deprnCost" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>"></td>
                <td width="5%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="manufacturerName" id="manufacturerName" class="finput3" readonly value="<%=lineDTO.getManufacturerName()%>"></td>
                <td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="5%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>"></td>
				<td width="5%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="5%" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldDepreciationAccount" id="oldDepreciationAccount<%=i%>" class="finput" readonly value="<%=lineDTO.getOldDepreciationAccount()%>"></td>
				<td width="3%" align="right" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="oldFaCategoryCode" id="oldFaCategoryCode<%=i%>" class="finput" readonly value="<%=lineDTO.getOldFaCategoryCode()%>"></td>
				<td width="5%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName<%=i%>" class="finputNoEmpty" style="cursor:pointer" readonly value="<%=lineDTO.getResponsibilityDeptName()%>" onClick="do_SelectDept(this)" title="点击选择或更改调入部门"></td>
				<td width="5%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finput2" style="cursor:pointer" readonly value="<%=lineDTO.getAssignedToLocationName()%>" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点"></td>
				<td width="3%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput2" style="cursor:pointer" readonly value="<%=lineDTO.getResponsibilityUserName()%>" onClick="do_SelectPerson(this)"  title="点击选择或更改新责任人"></td>
				<td width="5%" align="center"><input type="text" name="depreciationAccount" id="depreciationAccount<%=i%>" class="finput2" style="cursor:pointer" readonly value="<%=lineDTO.getDepreciationAccount()%>" onClick="do_SelectDepreciationAccount(this)" title="点击选择或更改折旧账户"></td>
				<td width="3%" align="right"><input type="text" name="faCategoryCode" id="faCategoryCode<%=i%>" class="finput2" style="cursor:pointer" readonly value="<%=lineDTO.getFaCategoryCode()%>" onClick="do_SelectFaCategoryCode(this)" title="点击选择或更改新类别"></td>
				<td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>" class="finput2" style="cursor:pointer" readonly value="<%=lineDTO.getLineTransDate()%>" onclick="gfPop.fPopCalendar(lineTransDate<%=i%>)" title="点击选择或更改调拨日期" ></td>
				<td width="5%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark()%>"></td>
				<td style="display:none"><input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>"></td>
				<td style="display:none"><input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>"></td>
				<td style="display:none"><input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>"></td>
				<td style="display:none"><input type="hidden" name="responsibilityDept" id="responsibilityDept<%=i%>" value="<%=lineDTO.getResponsibilityDept()%>"></td>
                <td style="display:none"><input type="hidden" name="assignedToLocation" id="assignedToLocation<%=i%>" value="<%=lineDTO.getAssignedToLocation()%>"></td>
				<td style="display:none"><input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>" value="<%=lineDTO.getResponsibilityUser()%>"></td>
				<td style="display:none"><input type="hidden" name="addressId" id="addressId<%=i%>" value="<%=lineDTO.getAddressId()%>"></td>
            </tr>
<%
				}
			}
		}
	}
%>
        </table>
    </div>

    <div id="transferDiv"
     style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
    <table width="50%" class="headerTable">
        <tr class="headerTable">
            <td height="10%" width="10%" align="center">资产标签号</td>
            <td height="10%" width="20%" align="center">可能存在原因</td>
        </tr>
    </table>
    <table width="50%" bgcolor="#CCCCFF">
<%
    if (lineSetPri == null || lineSetPri.isEmpty()) {
%>
        <tr>
            <td height="10%" width="10%"></td>
            <td height="10%" width="20%"></td>
        </tr>
<%
    } else {
        int count=lineSetPri.getSize();
        if(list.size()>count){
            count=list.size();
        }
    for (int i = 0; i < count; i++) {
        AmsAssetsTransLineDTO lineDTO = null;
        lineDTO = (AmsAssetsTransLineDTO) lineSetPri.getDTO(i);
%>
        <tr>
<%
        if (lineSetPri.getSize() > i) {
%>
            <td height="10%" width="10%" align="center"><font size = "2" color="#FF0000"><%=lineDTO.getBarcode()%></font></td>
<%
        } else {
%>
            <td height="10%" width="10%" align="center"></td>
<%
        }
%>

<%
    if (list.size() > i) {
%>
            <td height="10%" width="25%" align="left"><font size = "2" color="#FF0000"><%=list.get(i)%></font></td>
<%
    } else {
%>
           <td height="10%" width="25%" align="center"></td>
<%
    }
%>
        </tr>
<%
    } }
%>
<tr>
        <td height="10%" width="50%" align="center" bgcolor="#CCCCFF" colspan="2">
            <img src="/images/eam_images/close.jpg" onClick="do_CloseDiv();">
        </td>
</tr>
    </table>
</div>
