<%@ page import="com.sino.ams.newasset.dto.AmsAssetsTransLineDTO" %>
<%@ page import="com.sino.ams.freeflow.FreeFlowDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
	FreeFlowDTO headerDTO = (FreeFlowDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();

	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
 if(transType.equals(AssetsDictConstant.ASS_FREE)){//资产闲置
%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
	            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
	             <td align="center" width="8%">资产账簿</td>
	             <td align=center width="8%">资产标签</td>
	            <td align=center width="6%">资产编号</td>
                <td align=center width="10%">资产说明</td>
	            <td align=center width="10%">资产型号</td>
	            <td align=center width="6%">生产厂商</td>
	            <td align=center width="6%">投入使用日期</td>
	            <td align=center width="4%">数量</td>
				<td align=center width="3%">单位</td>
	            <td align=center width="16%">所在地点</td>
	            <td align=center width="8%">闲置原因</td>
	            <td align=center width="8%">使用年限</td>
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
				<td width="8%" align="center" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
				<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber0" class="finput2" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
				<td width="6%" align="right" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName0" class="finput3" readonly value=""></td>
				<td width="6%" align="center" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService0" class="finput2" readonly value=""></td>
				<td width="4%" align="right" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits0" class="finput3" readonly value=""></td>
				<td width="3%" align="center" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure0" class="finput" readonly value=""></td>
				<td width="16%" align="right" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
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
            <tr class="dataTR" onClick="executeClick(this)" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
				<td width="8%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput2" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput3" readonly value="<%=lineDTO.getVendorName()%>"></td>
				<td width="6%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput2" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="4%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="currentUnits" id="currentUnits<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="3%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
				<td width="16%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
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
	}
%>
 </div>
