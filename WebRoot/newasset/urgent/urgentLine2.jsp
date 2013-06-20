<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@page import="com.sino.base.dto.DTOSet"%>
<%@page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentDTO"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentLineDTO"%>
<%   
	UrgentDTO leaseDTO = null;
	leaseDTO = (UrgentDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    DTOSet lines = leaseDTO.getLines(); 
%>

<%
	String[] widthArr = {  "7%" , "10%", "8%", "10%", "5%"  }; 
	int arrIndex = 0;
	%>
	
    <div id="headDiv" style="overflow:hidden;left:1px;width:990px">

	
		<table class="headerTable" border="1" width="100%">
	        <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">标签号</td>  
                <td align=center width="<%= widthArr[ arrIndex++ ] %>">资产名称</td>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">规格型号</td>
	            <%--<td align=center width="<%= widthArr[ arrIndex++ ] %>">资产地点描述</td>--%>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">责任人</td> 	            
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow-y:scroll;overflow-x:hidden;width:1007px;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		arrIndex = 0;
		int alignArrIndex = 0;
		String[] alignArr = {  "center" , "center", "left", "center", "center",   "center","center","left","center","left",   "right","right","right" }; 
		
		if (lines != null && !lines.isEmpty()) { 
		//if (true) { 
			UrgentLineDTO lineDTO = null;
			String barcode = ""; 
			for (int i = 0; i < lines.getSize(); i++) {
			//for (int i = 0; i < 20; i++) {
				arrIndex = 0;
				alignArrIndex = 0;
				lineDTO = (UrgentLineDTO) lines.getDTO(i);
				//lineDTO = new UrgentLineDTO();
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" style="cursor:hand" >
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>"  style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="itemName" id="itemName<%=i%>" class="finput2" readonly value="<%=lineDTO.getItemName()%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>"  style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="itemSpec" id="itemSpec<%=i%>" class="finput2" readonly value="<%=lineDTO.getItemSpec()%>"></td>
				<%--<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>"  style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="workorderObjectLocation" id="workorderObjectLocation<%=i%>" class="finput" readonly value="<%=lineDTO.getWorkorderObjectLocation()%>"></td>--%>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>"  style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput2" readonly value="<%=lineDTO.getResponsibilityUserName()%>"></td>	 				
            </tr>
<%
			}
		} 
             %>           
             </table>
       </div> 