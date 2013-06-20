<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@page import="com.sino.base.dto.DTOSet"%>
<%@page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@page import="com.sino.ams.newasset.lease.dto.LeaseDTO"%>
<%@page import="com.sino.ams.newasset.lease.dto.LeaseLineDTO"%>
 
<%   
	LeaseDTO leaseDTO = null;
	leaseDTO = (LeaseDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
     
    DTOSet lines = leaseDTO.getLines(); 
    
    String mustStyle = "noEmptyInput";
    String pageType = request.getParameter( "pageType" );
    boolean hasCheckBox = ( !"1".equals( pageType ) ) ; 

	String[] widthArr = {  "7%" , "10%", "8%", "10%", "5%",   "5%","5%","12%","5%","10%",   "5%","5%","5%" }; 
	int arrIndex = 0;
	%>
	
    <div id="headDiv" style="overflow:hidden;width:100%">
			<table class="headerTable" border="1" width="140%">
		        <tr height=20px onClick="executeClick(this)" style="cursor:pointer" title="点击全选或取消全选">
		        	<% 
		        		if( hasCheckBox ){
		        	%>
		            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
		            <%}  %>
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
		            
		            <td align=center width="<%= widthArr[ arrIndex++ ] %>">租期(月)</td>
		            <td align=center width="<%= widthArr[ arrIndex++ ] %>">年租金</td>
		            <td align=center width="<%= widthArr[ arrIndex++ ] %>">月租金</td>
		            
					<td style="display:none">隐藏域字段</td>
		        </tr>
			</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:400px;width:100%;" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		arrIndex = 0;
		int alignArrIndex = 0;
		String[] alignArr = {  "center" , "center", "left", "center", "center",   "center","center","left","center","left",   "right","right","right" }; 
		
		if (lines == null || lines.isEmpty()) {
%>
            <tr class="dataTR" style="display:none" title="点击查看资产详细信息">
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck0" value=""></td>
				
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode0" class="finput2" readonly value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="itemName" id="itemName0" class="finput2" readonly value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="itemSpec" id="itemSpec0" class="finput2" readonly value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="workorderObjectLocation" id="workorderObjectLocation0" class="finput" readonly value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="responsibilityUserName" id="responsibilityUserName0" class="finput2" readonly value=""></td>
				<%-- 
				--%>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer"><input type="text" name="rentDate" id="rentDate0" onclick="gfPop.fStartPop(rentDate0 , rentEndDate0)" style="width: 100%" readonly class="<%= mustStyle %>"  value="" onBlur="do_SetRentDate(this)" ></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer"><input type="text" name="rentEndDate" id="rentEndDate0" onclick="gfPop.fEndPop(rentDate0,rentEndDate0)" style="width: 100%" readonly class="<%= mustStyle %>"  value="" onBlur="do_SetRentEndDate(this)"></td>
                
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer"><input type="text" name="rentPerson" id="rentPerson0" class="<%= mustStyle %>" style="width: 100%" value=""></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer"><input type="text" name="contractNumber" id="contractNumber0" class="<%= mustStyle %>" style="width: 100%" value=""></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer"><input type="text" name="contractName" id="contractName0" class="<%= mustStyle %>" style="width: 100%" value=""></td>
																																													
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer"><input type="text" name="tenancy" onkeydown="floatOnlyOnkeyDown(this)" onkeyup="floatOnlyOnKeyUp(this)"  id="tenancy0" class="<%= mustStyle %>" style="width: 100%" value="" onblur="do_setTenancy(this);"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer"><input type="text" name="yearRental" onchange="changeYearRent(this)" onkeydown="floatOnlyOnkeyDown(this)" onkeyup="floatOnlyOnKeyUp(this)"  id="yearRental0" class="<%= mustStyle %>" style="width: 100%" value=""></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产详细信息" style="cursor:pointer"><input type="text" name="monthRental" onchange="changeMonthRent(this)" onkeydown="floatOnlyOnkeyDown(this)" onkeyup="floatOnlyOnKeyUp(this)"  id="monthRental0" class="<%= mustStyle %>" style="width: 100%" value=""></td>
				
				<td style="display:none">
					<input type="hidden" name="responsibilityUser" id="responsibilityUser0" value="">
				</td>
            </tr>
<%
		} else {
			LeaseLineDTO lineDTO = null;
			String barcode = ""; 
			for (int i = 0; i < lines.getSize(); i++) {
				arrIndex = 0;
				alignArrIndex = 0;
				lineDTO = (LeaseLineDTO) lines.getDTO(i);
				barcode = lineDTO.getBarcode();
%>
            <tr class="dataTR" style="cursor:pointer" title="点击查看资产“<%=barcode%>”详细信息">
				<% 
	        		if( hasCheckBox ){
	        	%>
				<td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value="<%=barcode%>"></td>
				<%}  %>
				
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="barcode" id="barcode<%=i%>" class="finput2" readonly value="<%=barcode%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="itemName" id="itemName<%=i%>" class="finput2" readonly value="<%=lineDTO.getItemName()%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="itemSpec" id="itemSpec<%=i%>" class="finput2" readonly value="<%=lineDTO.getItemSpec()%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="workorderObjectLocation" id="workorderObjectLocation<%=i%>" class="finput" readonly value="<%=lineDTO.getWorkorderObjectLocation()%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer" onClick="do_ShowDetail(this)"><input type="text" name="responsibilityUserName" id="responsibilityUserName<%=i%>" class="finput2" readonly value="<%=lineDTO.getResponsibilityUserName()%>"></td>
				
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer"><input type="text" name="rentDate" id="rentDate<%=i%>" onclick="gfPop.fStartPop(rentDate<%=i%>,rentEndDate<%=i%>)" style="width: 100%" class="<%= mustStyle %>" readonly value="<%=lineDTO.getRentDate()%>"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer"><input type="text" name="rentEndDate" id="rentEndDate<%=i%>" onclick="gfPop.fEndPop(rentDate<%=i%>,rentEndDate<%=i%>)" style="width: 100%" class="<%= mustStyle %>" readonly value="<%=lineDTO.getRentEndDate()%>"></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer"><input type="text" name="rentPerson" id="rentPerson<%=i%>" class="<%= mustStyle %>" style="width: 100%" value="<%=lineDTO.getRentPerson()%>"></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer"><input type="text" name="contractNumber" id="contractNumber<%=i%>" class="<%= mustStyle %>" style="width: 100%" value="<%=lineDTO.getContractNumber()%>"></td>
                <td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer"><input type="text" name="contractName" id="contractName<%=i%>" class="<%= mustStyle %>" style="width: 100%" value="<%=lineDTO.getContractName()%>"></td>
				
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer"><input type="text" name="tenancy" id="tenancy<%=i%>" class="<%= mustStyle %>"  style="width: 100%" value="<%=lineDTO.getTenancy()%>" onkeydown="floatOnlyOnkeyDown(this)" onkeyup="floatOnlyOnKeyUp(this)" onblur="do_setTenancy(this);"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer"><input type="text" name="yearRental" id="yearRental<%=i%>" class="<%= mustStyle %>" style="width: 100%" value="<%=lineDTO.getYearRental()%>" onkeydown="floatOnlyOnkeyDown(this)" onkeyup="floatOnlyOnKeyUp(this)" onchange="changeYearRent(this)"></td>
				<td width="<%= widthArr[ arrIndex++ ] %>" align="<%= alignArr[ alignArrIndex ++ ] %>" title="点击查看资产“<%=barcode%>”详细信息" style="cursor:pointer"><input type="text" name="monthRental" id="monthRental<%=i%>" class="<%= mustStyle %>" style="width: 100%" value="<%=lineDTO.getMonthRental()%>" onkeydown="floatOnlyOnkeyDown(this)" onkeyup="floatOnlyOnKeyUp(this)" onchange="changeMonthRent(this)"></td>
				
				<td style="display:none"> 
					<input type="hidden" name="responsibilityUser" id="responsibilityUser<%=i%>" value="<%=lineDTO.getResponsibilityUser()%>">
				</td>
            </tr>
<%
			}
		}
		
 

%>
             </table>
       </div> 