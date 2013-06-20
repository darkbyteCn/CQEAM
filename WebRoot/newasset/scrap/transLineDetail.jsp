<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
	AmsAssetsTransLineDTO lineDTO = null;
	String barcode = "";

    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    ServletConfigDTO servletConfig = headerDTO.getServletConfig();
%>
	 
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
<%
    String currRoleName = headerDTO.getCurrRoleName();
    String attribute4 = headerDTO.getAttribute4();
    if ((currRoleName.equals(servletConfig.getMtlAssetsMgr()) && userAccount.getOrganizationId()== 82) || (currRoleName.equals("资产会计") && userAccount.getOrganizationId()== 82) || attribute4.equals("FIND_ASSET")) {
%>
        <table class="headerTable" border="1" width="140%">
        <tr height="23px" onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
<%
        if (!attribute4.equals("FIND_ASSET")) {
%>
            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
<%
        }
%>
            <td align=center width="8%">资产标签</td>
            <td align=center width="8%">报废原因</td>
            <td align=center width="8%">资产编号</td>
            <td align=center width="8%">资产名称</td>
            <td align=center width="8%">资产型号</td>
            <td align=center width="5%">资产原值</td>
            <td align=center width="5%">净值</td>
			<td align=center width="5%">报废成本</td>
            <td align=center width="7%">启用日期</td>
			<td align=center width="8%">所在地点</td>
            <td align=center width="5%">责任人</td>
            <td align=center width="8%">责任部门</td>
            <td align=center width="7%">备注</td>
        </tr>
		</table>
<%
    } else {
%>
       <table class="headerTable" border="1" width="140%">
        <tr height="23px">
            <td align=center width="8%">资产标签</td>
            <td align=center width="8%">报废原因</td>
            <td align=center width="8%">资产编号</td>
            <td align=center width="8%">资产名称</td>
            <td align=center width="8%">资产型号</td>
            <td align=center width="5%">资产原值</td>
            <td align=center width="5%">净值</td>
            <td align=center width="5%">报废成本</td>
            <td align=center width="7%">启用日期</td>
			<td align=center width="8%">所在地点</td>
            <td align=center width="5%">责任人</td>
            <td align=center width="8%">责任部门</td>
            <td align=center width="7%">备注</td>
        </tr>
		</table>
<%
    }
%>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:46px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		if (lineSet != null && !lineSet.isEmpty()) {
			for (int i = 0; i < lineSet.getSize(); i++) {
				lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
				barcode = lineDTO.getBarcode();
                if ((currRoleName.equals(servletConfig.getMtlAssetsMgr()) && userAccount.getOrganizationId()== 82) || (currRoleName.equals("资产会计") && userAccount.getOrganizationId()== 82) || attribute4.equals("FIND_ASSET")) {
 
%>
            <tr class="dataTR">
<%
                if (!attribute4.equals("FIND_ASSET")) {
%>
                <td width="3%" align="center"><input type="checkbox" name="subCheck" value="<%=barcode%>" <%=lineDTO.getRemark().equals("")?"checked":""%>></td>
<%
                }
%>
                <td width="8%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
                <td width="8%" align="center"><input type="text" name="rejectTypeName" id="rejectTypeName<%=i%>" class="finput" readonly value="<%=lineDTO.getRejectTypeName()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="8%" align="left"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="8%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="8%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="5%" align="right"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="5%" align="right"><input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
                <td width="5%" align="right"><input type="text" name="retirementCost" id="retirementCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getRetirementCost()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>             
                <td width="7%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput" readonly value="<%=lineDTO.getDatePlacedInService()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="8%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput3" readonly value="<%=lineDTO.getOldLocationName()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="5%" align="right"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
				<td width="8%" align="left"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
                <td width="7%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
            </tr>

<%
                } else {
%>
            <tr class="dataTR" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')">
				<td width="8%" align="center"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
				<td width="8%" align="center"><input type="text" name="rejectTypeName" id="rejectTypeName<%=i%>" class="finput" readonly value="<%=lineDTO.getRejectTypeName()%>"></td>
				<td width="8%" align="left"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>
				<td width="8%" align="left"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="8%" align="left"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="5%" align="right"><input type="text" name="cost" id="cost<%=i%>" class="finput3" readonly value="<%=lineDTO.getCost()%>"></td>
				<td width="5%" align="right"><input type="text" name="deprnCost" id="deprnCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getDeprnCost()%>"></td>
                <td width="5%" align="right"><input type="text" name="retirementCost" id="retirementCost<%=i%>" class="finput3" readonly value="<%=lineDTO.getRetirementCost()%>"></td>
                <td width="7%" align="center"><input type="text" name="datePlacedInService" id="datePlacedInService<%=i%>" class="finput" readonly value="<%=lineDTO.getDatePlacedInService()%>"></td>
				<td width="8%" align="right"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput3" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="5%" align="right"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="8%" align="left"><input type="text" name="oldResponsibilityDeptName" id="oldResponsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityDeptName()%>"></td>
                <td width="7%" align="left"><input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息" onClick="do_ShowDetail('<%=barcode%>')"></td>
            </tr>
<%
           }
        }
    }
%>
        </table>
    </div>
