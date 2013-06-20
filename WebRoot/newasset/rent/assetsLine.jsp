<%@ page import="com.sino.ams.newasset.dto.AmsAssetsTransLineDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transStatusH = headerDTO.getTransStatus();
	String transType = headerDTO.getTransType();
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
    DTOSet lineSetPri = (DTOSet) request.getAttribute(AssetsWebAttributes.PRIVI_DATA);//EXCEL导入时导入不成功的DTOSET
    String widthArr[] = {  "6%" , "7%" ,"12%" , "7%" ,"5%"
    					  ,"3%" ,"3%" ,"18%" ,"4%" ,"15%"
    					   ,"12%"};
    int widthIndex = 0;
%>
    		<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:50px;left:1px;width:100%">
    			<table class="headerTable" border="1" width="140%">
    		        <tr height=23px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
    		            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
    		            <td align=center width="<%= widthArr[ widthIndex++ ] %>">资产标签号</td>
<%
    if(transType.equals("ASS-WASTEFORECAST")){
%>
    		            <td align=center width="5%">报废类型</td>
<%
    }
%>
    		            <td align=center width="<%= widthArr[ widthIndex++ ] %>">资产名称</td>
    		            <td align=center width="<%= widthArr[ widthIndex++ ] %>">资产型号</td>
    		            <td align=center width="<%= widthArr[ widthIndex++ ] %>">生产厂家</td>
    		            
    		            <td align=center width="<%= widthArr[ widthIndex++ ] %>">启用日期</td>
    		            <td align=center width="<%= widthArr[ widthIndex++ ] %>">数量</td>
    					<td align=center width="<%= widthArr[ widthIndex++ ] %>">单位</td>
    		            <td align=center width="<%= widthArr[ widthIndex++ ] %>">所在地点</td>
    		            <td align=center width="<%= widthArr[ widthIndex++ ] %>">责任人</td>
    		            
    		            <td align=center width="<%= widthArr[ widthIndex++ ] %>">责任部门</td>
    		            <td align=center width="<%= widthArr[ widthIndex ] %>">备注</td>
    					<td style="display:none">隐藏域字段</td>
    		        </tr>
    			</table>
    		</div>
    		<div id="dataDiv" style="overflow:scroll;height:81%;width:100%;position:absolute;top:74px;left:1px" align="left" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    	        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if (lineSet == null || lineSet.isEmpty()) {
        widthIndex = 0;
%>
    	            <tr class="dataTR" onClick="executeClick(this)" style="display:none" title="点击查看资产详细信息">
    					<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
<%
        if(transType.equals("ASS-WASTEFORECAST")){
%>

    					<td width="5%" title="点击查看资产详细信息" style="cursor:pointer" >
    					    <select name="rejectType" id="rejectType0" style="width: 100%" class="select_style1" onchange="do_SetCheckCategory(this)"><%=headerDTO.getRejectTypeHOpt()%></select>
						</td>
<%
    }
%>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="itemName" id="itemName0" class="finput" readonly value=""></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="itemSpec" id="itemSpec0" class="finput" readonly value=""></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="vendorName" id="vendorName0" class="finput" readonly value=""></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="startDate" id="startDate0" class="finput2" readonly value=""></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="itemQty" id="itemQty0" class="finput3" readonly value=""></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="unitOfMeasure" id="unitOfMeasure0" class="finput" readonly value=""></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="workorderObjectLocation" id="workorderObjectLocation0" class="finput" readonly value=""></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="responsibilityUserName" id="responsibilityUserName0" class="finput" readonly value=""></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName0" class="finput" value=""></td>
    					<td width="<%= widthArr[ widthIndex] %>" align="center" title="点击查看资产详细信息" style="cursor:pointer" ><input type="text" name="remark" id="remark0" class="finput"  value=""></td>
    					<td style="display:none">
    						<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept0" value="">
    						<input type="hidden" name="oldLocation" id="oldLocation0" value="">
    						<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser0" value="">
    						<input type="hidden" name="responsibilityUser" id="responsibilityUser0" value="">
    						<input type="hidden" name="responsibilityDept" id="responsibilityDept0" value="">
    					</td>
    	            </tr>
    	<%
    } else {
        AmsAssetsTransLineDTO lineDTO = null;
        String barcode = "";
        for (int i = 0; i < lineSet.getSize(); i++) {
            lineDTO = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
            barcode = lineDTO.getBarcode();
            widthIndex = 0;
    	%>
    	            <tr class="dataTR" onClick="executeClick(this)" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息">
    					<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value="<%=barcode%>"></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
<%
            if(transType.equals("ASS-WASTEFORECAST")){
                if (!"IN_PROCESS".equals(transStatusH)) {
%>
    					<td width="5%" align="center" title="点击查看资产详细信息" style="cursor:pointer" >
    					    <select name="rejectType" id="rejectType<%=i%>" style="width: 100%" class="select_style1" onchange="do_SetCheckCategory(this)"><%=lineDTO.getRejectTypeOpt()%></select>
						</td>						
<%
                } else {
%>			
			            <td width="5%" align="center" title="点击查看资产详细信息“<%=barcode%>”详细信息" style="cursor: pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')">
			                <input type="text" name="rejectTypeName" id="rejectTypeName<%=i%>" class="finput" readonly value="<%=lineDTO.getRejectTypeName()%>">
			                <input type="hidden" name="rejectType" id="rejectType<%=i%>" class="finput" readonly value="<%=lineDTO.getRejectType()%>">
			            </td>	
<%
                }
            }
%>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="itemName" id="itemName<%=i%>" class="finput" readonly value="<%=lineDTO.getItemName()%>"></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="left" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="itemSpec" id="itemSpec<%=i%>" class="finput" readonly value="<%=lineDTO.getItemSpec()%>"></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="vendorName" id="vendorName<%=i%>" class="finput" readonly value="<%=lineDTO.getVendorName()%>"></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="startDate" id="startDate<%=i%>" class="finput2" readonly value="<%=lineDTO.getStartDate()%>"></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="itemQty" id="itemQty<%=i%>" class="finput3" readonly value="<%=lineDTO.getItemQty() %>"></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="right" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="workorderObjectLocation" id="workorderObjectLocation<%=i%>" class="finput" readonly value="<%=lineDTO.getWorkorderObjectLocation()%>"></td>
						<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityUserName() %>"></td>
    					<td width="<%= widthArr[ widthIndex++ ] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="responsibilityDeptName" id="responsibilityDeptName<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityDeptName() %>"></td>
    					<td width="<%= widthArr[ widthIndex] %>" align="center" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail('<%=lineDTO.getBarcode()%>')"><input type="text" name="remark" id="remark<%=i%>" class="finput" value="<%=lineDTO.getRemark() %>"></td>
    					<td style="display:none">
    						<input type="hidden" name="oldResponsibilityDept" id="oldResponsibilityDept<%=i%>" value="<%=lineDTO.getOldResponsibilityDept()%>">
    						<input type="hidden" name="oldLocation" id="oldLocation<%=i%>" value="<%=lineDTO.getOldLocation()%>">
    						<input type="hidden" name="oldResponsibilityUser" id="oldResponsibilityUser<%=i%>" value="<%=lineDTO.getOldResponsibilityUser()%>">
    						<input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>" value="<%=lineDTO.getResponsibilityUser()%>">
    						<input type="hidden" name="responsibilityDept" id="responsibilityDept<%=i%>" value="<%=lineDTO.getResponsibilityDept()%>">
<%
            if(transType.equals("ASS-WASTEFORECAST") && !"IN_PROCESS".equals(transStatusH)){
%>
<%--    						<input type="hidden" name="rejectType" id="rejectType<%=i%>" value="<%=lineDTO.getRejectType()%>">--%>
<%
            }
%>
    					</td>
    	            </tr>
<%
        }
	}
%>
	</table>
    </div>

    
    <div id="transferDiv" style="position:relative; bottom: 0px; top: 0px; left: 0px; right: 0px; z-index: 10; visibility: hidden; width: 100%; height: 100%">
		<table width="100%" class="headerTable">
			<tr class="headerTable">
				<td height="10%" width="10%" align="center">
					行号
				</td>
				<td height="10%" width="10%" align="center">
					资产标签号
				</td>
				<td height="10%" width="30%" align="center">
					可能存在原因
				</td>
			</tr>
		</table>
		<div   style= "overflow:auto;height:23% "> 
			<table width="100%" bgcolor="#CCCCFF">
<%
	if (lineSetPri == null || lineSetPri.isEmpty()) {
%>
		<tr>
			<td height="10%" width="10%"></td>
			<td height="10%" width="10%"></td>
			<td height="10%" width="30%"></td>
		</tr>
<%
	} else {
		int count = lineSetPri.getSize();
		for (int i = 0; i < count; i++) {
			AmsAssetsTransLineDTO lineDTO = null;
			lineDTO = (AmsAssetsTransLineDTO) lineSetPri.getDTO(i);
%>
		<tr>
<%
			if (lineSetPri.getSize() > i) {
%>
			<td height="10%" width="10%" align="center"><font size="2" color="#FF0000"><%=lineDTO.getExcelLineId()%></font></td>
			<td height="10%" width="10%" align="center"><font size="2" color="#FF0000"><%=lineDTO.getBarcode()%></font></td>
			<td height="10%" width="30%" align="center"><font size="2" color="#FF0000"><%=lineDTO.getErrorMsg()%></font></td>
<%
			} else {
%>
			<td height="10%" width="10%" align="center"></td>
			<td height="10%" width="10%" align="center"></td>
			<td height="10%" width="30%" align="center"></td>
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
</div>
