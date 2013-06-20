<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/td/newasset/headerIncludeTd.jsp"%>
<%
	TdAssetsTransHeaderDTO headerDTO = (TdAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
	String transferType = headerDTO.getTransferType();
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
	TdAssetsTransLineDTO lineDTO = null;
	String barcode = "";
	if(transType.equals(AssetsDictConstant.ASS_SUB)){//资产减值
%>
		<table class="headerTable" border="1" width="100%">
	        <tr height=20px>
	            <td align=center width="4%">网元编号</td>
	            <td align=center width="4%">资产编号</td>
                <td align=center width="7%">资产标签号</td>
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
	            <td align=center width="6%">备注</td>
            </tr>
		</table>
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
		for (int i = 0; i < lineSet.getSize(); i++) {
			lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
			barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="4%" align="center"><%=lineDTO.getNetUnit()%></td>
				<td width="4%" align="center"><%=lineDTO.getAssetNumber()%></td>
				<td width="7%" align="center"><%=barcode%></td>
				<td width="8%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="8%" align="left"><%=lineDTO.getVendorName()%></td>
				<td width="4%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="4%" align="right"><%=lineDTO.getDatePlacedInService()%></td>
				<td width="5%" align="center"><%=lineDTO.getSoftInuseVersion()%></td>
				<td width="5%" align="center"><%=lineDTO.getSoftDevalueVersion()%></td>
				<td width="4%" align="right"><%=lineDTO.getCost()%></td>
				<td width="4%" align="right"><%=lineDTO.getDepreciation()%></td>
				<td width="4%" align="right"><%=lineDTO.getDeprnCost()%></td>
				<td width="4%" align="right"><%=lineDTO.getPrepareDevalue()%></td>
				<td width="6%" align="right"><%=lineDTO.getRemark()%></td>
            </tr>
<%
		}
	} else if(transType.equals(AssetsDictConstant.ASS_FREE)){//资产闲置
%>
		<table class="headerTable" border="1" width="100%">
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
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
		for (int i = 0; i < lineSet.getSize(); i++) {
			lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
			barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="8%" align="center"><%=barcode%></td>
				<td width="6%" align="center"><%=lineDTO.getAssetNumber()%></td>
				<td width="10%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="10%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="6%" align="right"><%=lineDTO.getVendorName()%></td>
				<td width="6%" align="center"><%=lineDTO.getDatePlacedInService()%></td>
				<td width="4%" align="right"><%=lineDTO.getCurrentUnits()%></td>
				<td width="3%" align="center"><%=lineDTO.getUnitOfMeasure()%></td>
				<td width="16%" align="right"><%=lineDTO.getOldLocationName()%></td>
				<td width="8%" align="left"><%=lineDTO.getLineReason()%></td>
				<td width="8%" align="left"><%=lineDTO.getRemark()%></td>
            </tr>
<%
		}
	} else if(!transType.equals(AssetsDictConstant.ASS_RED)){//报废或处置
%>
		<table class="headerTable" border="1" width="100%">
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
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
		for (int i = 0; i < lineSet.getSize(); i++) {
			lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
			barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="10%" align="center"><%=barcode%></td>
				<td width="10%" align="left"><%=lineDTO.getAssetNumber()%></td>
				<td width="10%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="10%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="9%" align="right"><%=lineDTO.getCost()%></td>
				<td width="9%" align="center"><%=lineDTO.getDatePlacedInService()%></td>
				<td width="9%" align="right"><%=lineDTO.getDeprnCost()%></td>
				<td width="9%" align="right"><%=lineDTO.getOldLocationName()%></td>
				<td width="10%" align="right"><%=lineDTO.getOldResponsibilityUserName()%></td>
				<td width="10%" align="left"><%=lineDTO.getOldResponsibilityDeptName()%></td>
            </tr>
<%
		}
	} else {//资产调拨
		if(transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)){//部门内调拨
%>
		<table class="headerTable" border="1" width="100%">
			<tr height="20px">
				<td align=center width="9%">资产标签</td>
				<td align=center width="6%">资产编号</td>
				<td align=center width="8%">资产名称</td>
				<td align=center width="8%">资产型号</td>
				<td align=center width="4%">数量</td>
				<td align=center width="20%">调出地点</td>
				<td align=center width="6%">原责任人</td>
				<td align=center width="20%">调入地点</td>
				<td align=center width="6%">新责任人</td>
				<td align=center width="7%">调拨日期</td>
				<td align=center width="7%">摘要</td>
			</tr>
		</table>
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="9%" align="center"><%=barcode%></td>
				<td width="6%" align="center"><%=lineDTO.getAssetNumber()%></td>
				<td width="8%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="8%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="4%" align="right"><%=lineDTO.getCurrentUnits()%></td>
				<td width="20%"><%=lineDTO.getOldLocationName()%></td>
				<td width="6%" align="right"><%=lineDTO.getOldResponsibilityUserName()%></td>
				<td width="20%"><%=lineDTO.getAssignedToLocationName()%></td>
				<td width="6%" align="left"><%=lineDTO.getResponsibilityUserName()%></td>
				<td width="7%" align="center"><%=lineDTO.getLineTransDate()%></td>
				<td width="7%" align="left"><%=lineDTO.getRemark()%></td>
            </tr>
<%
			}
		} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)){//部门间调拨
%>
		<table class="headerTable" border="1" width="100%">
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
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="10%" align="center"><%=barcode%></td>
				<td width="7%" align="left"><%=lineDTO.getAssetNumber()%></td>
				<td width="10%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="10%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="3%" align="right"><%=lineDTO.getCurrentUnits()%></td>
				<td width="10%" align="center"><%=lineDTO.getOldLocationName()%></td>
				<td width="6%" align="right"><%=lineDTO.getOldResponsibilityUserName()%></td>
				<td width="10%" align="right"><%=lineDTO.getResponsibilityDeptName()%></td>
				<td width="10%" align="right"><%=lineDTO.getAssignedToLocationName()%></td>
				<td width="6%" align="left"><%=lineDTO.getResponsibilityUserName()%></td>
				<td width="5%" align="center"><%=lineDTO.getLineTransDate()%></td>
				<td width="10%" align="left"><%=lineDTO.getRemark()%></td>
            </tr>
<%
			}
		} else if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){//地市间调拨
%>
	    <table width="100%" border="1" class="headerTable">
	        <tr height="20px">
	            <td align=center width="64%" colspan="14">调出方</td>
	            <td align=center width="25%" colspan="5">调入方</td>
	            <td align=center width="5%" rowspan="2">调拨日期</td>
	            <td align=center width="7%" rowspan="2">备注</td>
	        </tr>
	        <tr height="20px">
	            <td align=center width="5%">资产标签</td>
	            <td align=center width="5%">资产编号</td>
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
        <table id="dataTable" width="100%" border="1" bordercolor="#666666">
<%
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (TdAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="5%" align="center"><%=barcode%></td>
				<td width="5%" align="left"><%=lineDTO.getAssetNumber()%></td>
				<td width="5%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="5%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="3%" align="right"><%=lineDTO.getCurrentUnits()%></td>
				<td width="3%" align="right"><%=lineDTO.getCost()%></td>
				<td width="5%" align="right"><%=lineDTO.getDepreciation()%></td>
				<td width="3%" align="right"><%=lineDTO.getScrapValue()%></td>
				<td width="5%" align="right"><%=lineDTO.getDatePlacedInService()%></td>
				<td width="5%" align="right"><%=lineDTO.getOldResponsibilityDeptName()%></td>
				<td width="5%" align="center"><%=lineDTO.getOldLocationName()%></td>
				<td width="5%" align="right"><%=lineDTO.getOldResponsibilityUserName()%></td>
				<td width="5%" align="center"><%=lineDTO.getOldDepreciationAccount()%></td>
				<td width="5%" align="right"><%=lineDTO.getOldFaCategoryCode()%></td>
				<td width="5%" align="right"><%=lineDTO.getResponsibilityDeptName()%></td>
				<td width="5%" align="right"><%=lineDTO.getAssignedToLocationName()%></td>
				<td width="5%" align="left"><%=lineDTO.getResponsibilityUserName()%></td>
				<td width="5%" align="center"><%=lineDTO.getDepreciationAccount()%></td>
				<td width="5%" align="right"><%=lineDTO.getFaCategoryCode()%></td>
				<td width="5%" align="center"><%=lineDTO.getLineTransDate()%></td>
				<td width="7%" align="left"><%=lineDTO.getRemark()%></td>
            </tr>
<%
			}
		}
	}
%>
        </table>