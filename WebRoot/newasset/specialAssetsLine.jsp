<%@ page import="com.sino.ams.newasset.dto.AmsAssetsTransLineDTO" %>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
	AmsSpecialAssetsDTO headerDTO = (AmsSpecialAssetsDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
    DTOSet lineSetPri = (DTOSet) request.getAttribute(AssetsWebAttributes.PRIVI_DATA);//EXCEL导入时导入不成功的DTOSET
    List list = (List)request.getAttribute("REMARK_LIST");

%>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">
		<table class="headerTable" border="1" width="140%">
	        <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
	            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
	            <td align=center width="10%">标签号</td>
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
				<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription0" class="finput" readonly value=""></td>
				<td width="10%" align="left" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber0" class="finput" readonly value=""></td>
				<td width="3%" align="right" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="itemQty" id="itemQty0" class="finput3" readonly value=""></td>
				<td width="10%" align="center" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName0" class="finput" readonly value=""></td>
				<td width="6%" align="right" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName0" class="finput" readonly value=""></td>
				<td width="10%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName0" class="finputNoEmpty" readonly value="" onClick="do_SelectDept(this)" title="点击选择或更改调入部门" style="cursor:hand"></td>
				<td width="10%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName0" class="finput" readonly value="" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点" style="cursor:hand"></td>
				<td width="6%" align="left"><input type="text" name="newresponsibilityUserName" id="newresponsibilityUserName0" class="finput" readonly value="" onClick="do_SelectPerson(this)" title="点击选择或更改新责任人" style="cursor:hand"></td>
				<td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate0" class="finput2" style="cursor:hand" readonly value="" onclick="gfPop.fPopCalendar(lineTransDate0)" title="点击选择或更改调拨日期" onBlur="do_SetLineTransDate(this)"></td>
				<td width="10%" align="left"><input type="text" name="remark" id="remark0" class="finput" value=""></td>
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
            <tr id="model" class="dataTR" onClick="executeClick(this)" style="cursor:hand">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value="<%=barcode%>"></td>
				<td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=barcode%>"></td>
				<!-- <td width="7%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="assetNumber" id="assetNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetNumber()%>"></td>-->
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td> 
				<td width="10%" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="modelNumber" id="modelNumber<%=i%>" class="finput" readonly value="<%=lineDTO.getModelNumber()%>"></td>
				<td width="3%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="itemQty" id="itemQty<%=i%>" class="finput3" readonly value="<%=lineDTO.getCurrentUnits()%>"></td>
				<td width="10%" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="oldLocationName" id="oldLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldLocationName()%>"></td>
				<td width="6%" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="oldResponsibilityUserName" id="oldResponsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getOldResponsibilityUserName()%>"></td>
				<td width="10%" align="right"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName<%=i%>" class="finputNoEmpty" readonly value="<%=lineDTO.getResponsibilityDeptName()%>" onClick="do_SelectDept(this)" title="点击选择或更改调入部门" style="cursor:hand"></td>
				<td width="10%" align="right"><input type="text" name="assignedToLocationName" id="assignedToLocationName<%=i%>" class="finput" readonly value="<%=lineDTO.getAssignedToLocationName()%>" onClick="do_SelectLocation(this)" title="点击选择或更改调入地点" style="cursor:hand"></td>
				<td width="6%" align="left"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityUserName()%>" onClick="do_SelectPerson(this)"  title="点击选择或更改新责任人" style="cursor:hand"></td>
				<td width="5%" align="center"><input type="text" name="lineTransDate" id="lineTransDate<%=i%>" class="finput2" style="cursor:hand" readonly value="<%=lineDTO.getLineTransDate()%>" onclick="gfPop.fPopCalendar(lineTransDate0)" title="点击选择或更改调拨日期" onBlur="do_SetLineTransDate(this)"></td>
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
%>
        </table>
    </div>

    <div id="transferDiv" style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;z-index:10;visibility:hidden;width:100%;height:100%">
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
    } 
    }
%>
<tr>
        <td height="10%" width="50%" align="center" bgcolor="#CCCCFF" colspan="2">
            <img src="/images/eam_images/close.jpg" onClick="do_CloseDiv();">
        </td>
</tr>
    </table>
</div>
