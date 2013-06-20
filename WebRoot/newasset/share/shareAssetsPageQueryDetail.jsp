<%@ page contentType="text/html;charset=GBK" language="java"%>

<%@ page import="java.util.List"%>
<%@ page
	import="com.sino.ams.newasset.assetsharing.constant.AssetSharingConstant"%>
<%@ page import="com.sino.ams.newasset.assetsharing.dto.AssetSharingHeaderDTO"%>
<%@ page import="com.sino.ams.newasset.assetsharing.dto.AssetSharingLineDTO"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>


<html>
	<head>
		<%
			String isPrint = request.getAttribute("isPrint") == null ? ""
										: request.getAttribute("isPrint").toString();
			AssetSharingHeaderDTO headerDTO = (AssetSharingHeaderDTO) request
					.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
					String title="";
					if(isPrint.equals("")){
						title="单据明细";
					}else{
					title="单据明细打印";
					}
		%>
		<title><%=title %></title>
		<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
		<script type="text/javascript" src="/WebLibary/js/OrderProcess.js"></script>
		<script type="text/javascript" src="/WebLibary/js/SinoAttachment.js"></script>
		<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
	</head>
	<body leftmargin="0" topmargin="0" onload="initPage();">

		<table border="0" class="queryTable" width="100%"
			style="border-collapse: collapse" id="table1">
			<tr>
				<td>
					<table width=100% border="0">
			    <tr>
			        <td align=right width=8%>单据号：</td>
			        <td width=13%>
						<input type="text" name="transNo" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getTransNo()%>">
						<input type="text" name="transId" id="transId" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getTransId()%>"></td>
			        <td align=right width=8%>单据类型：</td>
			        <td width=13%>
						<input type="text" name="transTypeValue" class="input_style2" readonly style="width:100%" value="<%=AssetSharingConstant.ASSET_SHARE_CODE_DESC%>">
					</td>
					 <td align=right>公司名称：</td>
			        <td>
						<input name="company" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getCompany()%>"></td>
			         <td align=right width=8%>部门名称</td>
			        <td width=28%>
						<input name="deptName" style="width:100%" class="input_style2" value="<%=headerDTO.getDeptName()%>"/>
			        </td>
			    </tr>
			    <tr> <td align=right width=8%>创建人：</td>
			        <td width=13%>
						<input type="text" name="currUserName" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getCurrUserName()%>" >
					</td>
			        <td align=right>创建日期：</td>
			        <td>
						<input name="creationDate" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getCreationDate()%>" ></td>
			       
					<td align=right width=8%>手机号码：</td>
			         <td>
						<input name="mobilPhone" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getMobilePhone()%>"></td>
					<td align=right width=8%>电子邮件：</td>
			         <td>
						<input name="email" class="input_style2" readonly style="width:100%; " value="<%=headerDTO.getEmail()%>"></td>
			    </tr>
			    <!-- 
			    <tr> <td align=right width=8%>审核人：</td>
			        <td width=13%>
						<input type="text" name="checkUserName" class="input_style2" readonly style="width:100%" value="<%=headerDTO.getCheckUserName()%>" >
					</td>
			    </tr> -->
			    <tr>
			    	<td width="8%" height="60" align="right">备注：</td>
			    	<td width="92%" height="60" colspan="7"><textarea name="remark" readonly style="width:100%;height:100%" class="input_style1"><%=headerDTO.getRemark()%></textarea></td>
			    </tr>
			</table>
				</td>
			</tr>
		</table>
		<%
		if(isPrint.equals("")){
		}else{
		%>
		<jsp:include page="/newasset/approveContent.jsp" flush="true" />
		<%
		}
		%>
		
		<fieldset style="border: 0; position: relative; width: 100%; height: 80%">
			<legend>
				<table width="100%" id="buttonSet">
					<tr>
						<td width="100%">
							<%
								
								if ("".equals(isPrint)) {
							%>
							<img src="/images/eam_images/view_opinion.jpg" alt="查阅审批意见" onClick="do_ViewOpinion(); return false;">
							<%
								} else {
							%>
							<img src="/images/eam_images/page_config.jpg" alt="打印设置"
								onClick="do_SetupPrint(); return false;">
							<img src="/images/eam_images/print_view.jpg" alt="打印设置"
								onClick="do_PrevPrint(); return false;">
							<img src="/images/eam_images/print.jpg" alt="打印"
								onClick="do_PrintOrder(); return false;">
							<%
								}
							%>
							<img src="/images/eam_images/close.jpg" alt="关闭"
								onClick="javascript:window.close();">
						</td>
					</tr>
				</table>
			</legend>

			<div id="headDiv"
				style="overflow: hidden; position: absolute; top: 25px; left: 1px; width: 990px">
				
				<%
		if(isPrint.equals("")){
						%>
						<table class="headerTable" border="1" width="140%">
						<%
					}else{
					%>
					<table class="headerTable" border="1" width="100%">
					<%
					}
		%>
		
				
					<tr height=20px onClick="executeClick(this)" style="cursor: hand"
						title="点击全选或取消全选">
						<td align=center width="6%">
							标签号
						</td>
						<td align=center width="6%">
							资产名称
						</td>
						<td align=center width="6%">
							资产型号
						</td>
						<td align=center width="8%">
							资产地点描述
						</td>
						<td align=center width="5%">
							共享
						</td>
						<td align=center width="5%">
							责任人员工编号
						</td>
						<td align=center width="4%">
							责任人姓名
						</td>
						<td align=center width="4%">
							启用日期
						</td>
						<td align=center width="6%">
							资产目录
						</td>
						<td align=center width="8%">
							资产目录描述
						</td>
						<td style="display: none">
							隐藏域字段
						</td>
					</tr>
				</table>
			</div>
			
					<%
		if(isPrint.equals("")){
						%>
					<div id="dataDiv"
				style="overflow-y: scroll; overflow-x: scroll; width: 1007px;height:410px; position: absolute; top: 48px; left: 1px"
				onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
				
				<table id="dataTable" width="140%" border="1" bordercolor="#666666" "
					style="TABLE-LAYOUT: fixed; word-break: break-all">
						<%
					}else{
					%>
					<div id="dataDiv"
				style="overflow-y: scroll; overflow-x: hidden; width: 1007px; position: absolute; top: 48px; left: 1px"
				onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
					<table id="dataTable" width="100%" border="1" bordercolor="#666666"
					style="TABLE-LAYOUT: fixed; word-break: break-all">
					<%
					}
		%>
					<%
						List<AssetSharingLineDTO> lines = headerDTO.getLines();
						if (lines == null || lines.size() < 1) {
					%>
					<tr class="dataTR" onClick="executeClick(this)"
						style="display: none" title="资产详细信息">
						<td width="6%" align="center" title="资产详细信息" style="cursor: hand"
							onClick="do_ShowDetail(this)">
							<input type="text" name="barcode" id="barcode0" class="finput2"
								readonly value="">
						</td>
						<td width="6%" align="center" title="资产详细信息" style="cursor: hand"
							onClick="do_ShowDetail(this)">
							<input type="text" name="itemName" id="itemName0" class="finput2"
								readonly value="">
						</td>
						<td width="6%" align="center" title="资产详细信息" style="cursor: hand">
							<input name="itemSpec" id="itemSpec0" style="width: 100%"
								class="select_style1" onChange="do_SetDisType(this)" />
						</td>
						<td width="8%" align="left" title="资产详细信息" style="cursor: hand"
							onClick="do_ShowDetail(this)">
							<input type="text" name="workorderObjectLocation"
								id="workorderObjectLocation0" class="finput" readonly value="">
						</td>
						<td width="5%" align="right" title="资产详细信息" style="cursor: hand"
							onClick="do_ShowDetail(this)">
							<select type="text" style="width: 100%" name="shareStatus"
								id="shareStatus0" class="select_style1"
								ONCHANGE="do_SetCheckCategory(this)"><%=headerDTO.getShareStatusOpt()%></select>
						</td>
						<td width="5%" align="right" title="资产详细信息" style="cursor: hand"
							onClick="do_ShowDetail(this)">
							<input type="text" name="employeeNumber" id="employeeNumber0"
								class="finput3" readonly value="">
						</td>
						<td width="4%" align="right">
							<input type="text" name="userName" id="userName0"
								class="finputNoEmpty" value="">
						</td>
						<td width="4%" align="center" title="资产详细信息" style="cursor: hand"
							onClick="do_ShowDetail(this)">
							<input type="text" name="startDate" id="startDate0"
								class="finput2" readonly value="">
						</td>
						<td width="6%" align="center" title="资产详细信息" style="cursor: hand"
							onClick="do_ShowDetail(this)">
							<input type="text" name="contentCode" id="contentCode0"
								class="finput" readonly value="">
						</td>
						<td width="8%" align="right" title="资产详细信息" style="cursor: hand"
							onClick="do_ShowDetail(this)">
							<input type="text" name="contentName" id="contentName0"
								class="finput" readonly value="">
						</td>
						<td style="display: none">
							<input type="hidden" name="systemid" id="systemid0" value="">
							<input type="hidden" name="lineId" id="lineId0" value="">
							<input type="hidden" name="adressId" id="adressId0" value="">
						</td>
					</tr>
					<%
						} else {
							for (int i = 0; i < lines.size(); i++) {
								AssetSharingLineDTO line = lines.get(i);
					%>
					<tr class="dataTR" onClick="executeClick(this)" title="资产详细信息">
						<td width="6%" height="100%" align="center" title="资产详细信息"
							style="cursor: hand" onClick="do_ShowDetail(this)"><%=line.getBarcode()%></td>
						<td width="6%" height="100%" align="center" title="资产详细信息"
							style="cursor: hand" onClick="do_ShowDetail(this)"><%=line.getItemName()%></td>
						<td width="6%" height="100%" align="left" title="资产详细信息"
							style="cursor: hand"><%=line.getItemSpec()%></td>
						<td width="8%" height="100%" align="left" title="资产详细信息"
							style="cursor: hand" onClick="do_ShowDetail(this)"><%=line.getWorkorderObjectLocation()%></td>
						<td width="5%" height="100%" align="center" title="资产详细信息"
							style="cursor: hand" onClick="do_ShowDetail(this)"><%=line.getShareStatusDesc()%></td>
						<td width="5%" height="100%" align="center" title="资产详细信息"
							style="cursor: hand" onClick="do_ShowDetail(this)"><%=line.getEmployeeNumber()%></td>
						<td width="4%" height="100%" align="center"><%=line.getUserName()%></td>
						<td width="4%" height="100%" align="center" title="资产详细信息"
							style="cursor: hand" onClick="do_ShowDetail(this)">
							<%
								Object date = line.getStartDate();
										if (date != null && date.toString().length() > 9) {
											out.print(date.toString().substring(0, 10));
										} else {
											out.print(date);
										}
							%>
						</td>
						<td width="6%" height="100%" align="center" title="资产详细信息"
							style="cursor: hand" onClick="do_ShowDetail(this)"><%=line.getContentCode()%></td>
						<td width="8%" height="100%" align="left" title="资产详细信息"
							style="cursor: hand" onClick="do_ShowDetail(this)"><%=line.getContentName()%></td>
						<td style="display: none">
							<input type="hidden" name="systemId" id="systemId0"
								value="<%=line.getSystemId()%>">
							<input type="hidden" name="lineId" id="lineId0"
								value="<%=line.getLineId()%>">
							<input type="hidden" name="adressId" id="adressId0"
								value="<%=line.getAdressId()%>">
						</td>
					</tr>
					<%
						}
						}
					%>


				</table>
			</div>
		</fieldset>
		<object id="webbrowser" width="0" height="0"
			classid="clsid:8856f961-340a-11d0-a96b-00c04fd705a2"></object>
	</body>
</html>
<script>
function initPage(){
	window.focus();
	do_SetPageWidth();
}

function do_SetupPrint(){
	webbrowser.execwb(8,0);
}

function do_PrevPrint(){
	document.getElementById("buttonSet").style.display = "none";
	webbrowser.execwb(7,0);
	document.getElementById("buttonSet").style.display = "block";
}

function do_PrintOrder(){
	document.getElementById("buttonSet").style.display = "none";
	webbrowser.execwb(6,6);
	document.getElementById("buttonSet").style.display = "block";
}

function do_Close(){
	window.opener=null;
	window.close();
}

</script>