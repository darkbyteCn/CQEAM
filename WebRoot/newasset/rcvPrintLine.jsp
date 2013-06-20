<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ page import="java.text.NumberFormat" %>
<%
	AmsAssetsRcvHeaderDTO headerDTO = (AmsAssetsRcvHeaderDTO) request.getAttribute(AssetsWebAttributes.RCV_ORDER_HEAD);
	String transferType = headerDTO.getTransferType();
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.RCV_ORDER_LINE);
	AmsAssetsRcvLineDTO lineDTO = null;
	String barcode = "";
	if(transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)){//部门间调拨
%>
		<table bordercolor="#336699" border="1" width="100%">
	        <tr height="20px">
	            <td align="center" width="8%">资产标签</td>
	            <td align="center" width="6%">资产编号</td>
	            <td align="center" width="8%">资产名称</td>
	            <td align="center" width="7%">资产型号</td>
	            <td align="center" width="3%">数量</td>

	            <td align="center" width="5%">资产原值</td>
	            <td align="center" width="5%">累计折旧</td>
	            <td align="center" width="5%">资产净值</td>
	            <td align="center" width="5%">折旧年限</td>
	            <td align="center" width="4%">残值率</td>
				<td align="center" width="7%">启用日期</td>

				<td align="center" width="9%">调出地点</td>
	            <td align="center" width="6%">原责任人</td>
				<td align="center" width="9%">调入地点</td>
	            <td align="center" width="6%">新责任人</td>
				<td align="center" width="7%">调拨日期</td>
	        </tr>
<%
		double cost = 0D;
		double scrapValue = 0D;
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		for (int i = 0; i < lineSet.getSize(); i++) {
			lineDTO = (AmsAssetsRcvLineDTO) lineSet.getDTO(i);
            System.out.println(lineDTO);
            barcode = lineDTO.getBarcode();
			cost = Double.parseDouble(lineDTO.getCost());
			scrapValue = Double.parseDouble(lineDTO.getScrapValue());
%>
            <tr class="dataTR">
				<td width="8%" align="center"><%=barcode%></td>
				<td width="6%" align="center"><%=lineDTO.getAssetNumber()%></td>
				<td width="8%" align="left"><%=lineDTO.getAssetsDescription()%></td>
				<td width="7%" align="left"><%=lineDTO.getModelNumber()%></td>
				<td width="3%" align="right"><%=lineDTO.getCurrentUnits()%></td>

	            <td width="5%" align="right"><%=lineDTO.getCost()%></td>
	            <td width="5%" align="right"><%=lineDTO.getDepreciation()%></td>
	            <td width="5%" align="right"><%=lineDTO.getDeprnCost()%></td>
	            <td width="5%" align="right"><%=lineDTO.getLifeInYears()%></td>
	            <td width="4%" align="right"><%=percentFormat.format(scrapValue/cost)%></td>
				<td width="7%" align="center"><%=lineDTO.getDatePlacedInService()%></td>

				<td width="9%" align="left"><%=lineDTO.getOldLocationName()%></td>
				<td width="6%" align="left"><%=lineDTO.getOldResponsibilityUserName()%></td>
				<td width="9%" align="left"><%=lineDTO.getAssignedToLocationName()%></td>
				<td width="6%" align="left"><%=lineDTO.getResponsibilityUserName()%></td>
				<td width="7%" align="center"><%=lineDTO.getLineTransDate()%></td>
            </tr>
<%
		}	
%>
        </table>
<%
	}
%>
