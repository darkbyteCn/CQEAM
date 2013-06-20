<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@page import="com.sino.base.dto.DTOSet"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentDTO"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentLineDTO"%>
<%   
	UrgentDTO leaseDTO = null;
	leaseDTO = (UrgentDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    DTOSet lines = leaseDTO.getLines(); 
%>

<%
	String[] widthArr = {  "7%" , "10%", "8%", "5%", "5%"  }; 
	int arrIndex = 0;
	%>
	
    <div id="headDiv" style="overflow:hidden;position:absolute;top:25px;left:1px;width:990px">

	
		<table class="headerTable" border="1" width="100%">
	        <tr height=20px onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">标签号</td>  
                <td align=center width="<%= widthArr[ arrIndex++ ] %>">资产名称</td>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">规格型号</td>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">启用日期</td>
	            <td align=center width="<%= widthArr[ arrIndex++ ] %>">责任人</td> 	            
	        </tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:88%;width:1007px;position:absolute;top:48px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		int alignArrIndex = 0;
		String[] alignArr = {  "center" , "center", "left", "center", "center" }; 
		
		if (lines != null && !lines.isEmpty()) { 
			UrgentLineDTO lineDTO = null;
			String barcode = ""; 
			for (int i = 0; i < lines.getSize(); i++) {
				arrIndex = 0;
				alignArrIndex = 0;
				lineDTO = (UrgentLineDTO) lines.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" style="cursor:hand" title="点击查看资产“<%=barcode%>”详细信息">
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="itemName" id="itemName<%=i%>" class="finput2" readonly value="<%=lineDTO.getItemName()%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="itemSpec" id="itemSpec<%=i%>" class="finput2" readonly value="<%=lineDTO.getItemSpec()%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="startDate" id="startDate<%=i%>" class="finput" readonly value="<%=lineDTO.getStartDate()%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:hand" onClick="do_ShowDetail(this)"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput2" readonly value="<%=lineDTO.getResponsibilityUserName()%>"></td>	 				
            </tr>
<%
			}
		} 
%>
             </table>
       </div> 