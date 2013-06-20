<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@page import="com.sino.ams.newasset.lose.dto.LoseDTO"%>
<%@page import="com.sino.base.dto.DTOSet"%>
<%@page import="com.sino.ams.newasset.lose.dto.LoseLineDTO"%>
<%   
	LoseDTO loseDTO = null;
	loseDTO = (LoseDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA); 
    DTOSet lines = loseDTO.getLines();  
	String[] widthArr = {  "7%" , "10%", "8%", "10%", "5%",   "5%","5%","12%","5%","10%",   "5%","5%","5%" }; 
	int arrIndex = 0;
	%>
		<table class="headerTable" border="1" width="100%">
	        <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">标签号</td>  
                <td align=center width="<%= widthArr[ arrIndex++ ] %>">资产名称</td>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">规格型号</td>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">资产地点描述</td>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">责任人</td>
	            
                <td align=center width="<%= widthArr[ arrIndex++ ] %>">起租日期</td>
                <td align=center width="<%= widthArr[ arrIndex++ ] %>">止租日期</td>
                <td align=center width="<%= widthArr[ arrIndex++ ] %>">签约单位</td>
				<td align=center width="<%= widthArr[ arrIndex++ ] %>">合同编号</td>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">合同名称</td>
	            
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">租期</td>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">年租金</td>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">月租金</td>
	        </tr>
		</table>
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" >
<%
		arrIndex = 0;
		int alignArrIndex = 0;
		String[] alignArr = {  "center" , "center", "left", "center", "center",   "center","center","left","center","left",   "right","right","right" }; 
		if (lines == null || lines.isEmpty()) {
%>
            <tr class="dataTR" onClick="executeClick(this)" style="display:none" title="点击查看资产详细信息">
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="itemName" id="itemName0" class="finput2" readonly value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="itemSpec" id="itemSpec0" class="finput2" readonly value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="workorderObjectLocation" id="workorderObjectLocation0" class="finput" readonly value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="responsibilityUserName" id="responsibilityUserName0" class="finput2" readonly value=""></td>
				
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="rentDate" id="rentDate0" onclick="gfPop.fStartPop(this.id , '')"  class="finput2"  value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="rentEndDate" id="rentEndDate0" onclick="gfPop.fEndPop( '',this.id )"  class="finput2"  value=""></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="rentPerson" id="rentPerson0" class="finput2" readonly value=""></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="contractNumber" id="contractNumber0" class="finput2"  value=""></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="contractName" id="contractName0" class="finput"  value=""></td>
				
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="tenancy" id="tenancy0" class="finput3"  value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="yearRental" id="yearRental0" class="finput3"  value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="monthRental" id="monthRental0" class="finput3"  value=""></td>
            </tr>
<%
		} else {
			LoseLineDTO lineDTO = null;
			String barcode = ""; 
			for (int i = 0; i < lines.getSize(); i++) {
				arrIndex = 0;
				alignArrIndex = 0;
				lineDTO = (LoseLineDTO) lines.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" onClick="executeClick(this)" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息">
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=barcode%></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getItemName()%></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getItemSpec()%></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getWorkorderObjectLocation()%></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getResponsibilityUserName()%></td>
				
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getRentDate()%></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getRentEndDate()%></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getRentPerson()%></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getContractNumber()%></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getContractName()%></td>
				
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getTenancy()%></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getYearRental()%></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><%=lineDTO.getMonthRental()%></td>
            </tr>
<%
			}
		}
%>  
	</table>