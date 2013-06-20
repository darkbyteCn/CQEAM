<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.workorder.dto.ZeroTurnHeaderDTO"%>
<%@ page import="com.sino.ams.workorder.dto.ZeroTurnLineDTO"%>
<%
   ZeroTurnHeaderDTO headerDTO = (ZeroTurnHeaderDTO) request.getAttribute(AssetsWebAttributes.ZERO_TURN_DATA);
   DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
   String pageTitle = "零购转资维护单据“" + headerDTO.getTransNo() +"”详细信息";
%>
<html>
	<head>
		<title><%=pageTitle%></title>
		<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
		<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
	</head>
<body leftmargin="0" topmargin="0" rightmargin="1" onload="do_initPage();">
<input type="hidden" name="act" value="">
<input type="hidden" name="transType" value="<%= headerDTO.getTransType()%>">

  <jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.workorder.servlet.ZeroTurnServlet" method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("<%=pageTitle%>");
        printToolBar();
    </script>
<div id="searchDiv" style="overflow:hidden;position:absolute;top:48px;left:0px;width:100%;">
  	<table border="1" class="detailHeader" width="100%" bordercolor="#226E9B" style="border-collapse: collapse; top: 0px;" id="table1">
		<tr>
			<td>
				<table width=100% border="0" align="center">
					<tr align="center">
						<td align=right width="6%" height="18">
							单据编号：
						</td>
						<td width="15%" height="18">
							<input type="text" name="transNo"  readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getTransNo()%> "	size="20">
						</td>
						<td align=right width="6%" height="18">
							单据类型：
						</td>
						<td width="20%" height="18">
							<input type="text" name="transType" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=headerDTO.getTransType()%>" size="20">
						<td align=right width="6%" height="18">
							紧急程度：
						</td>
						<td width="15%" height="18">
							<select name="emergentLevel" class="select_style1" disabled style="width:100%" ><%=headerDTO.getEmergentLevelOption()%></select>
						</td>
					</tr>
					<tr>
						<td align=right width="6%" height="18">
							公司名称：
						</td>
						<td width="15%" height="18">
							<input type="text" name="organizationName" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=headerDTO.getOrganizationName()%>" size="20">
						</td>
						<td align=right width="10%" height="18">
							部门名称：
						</td>
						<td width="20%" height="18">
							<input type="text" name="deptName" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=headerDTO.getDeptName()%>" size="20">
						</td>
						<td align=right width="10%" height="18">
							创建日期：
						</td>
						<td width="15%" height="18">
							<input type="text" name="creationDate" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=headerDTO.getCreationDate()%>"size="20">
						</td>
					</tr>
					<tr>
						<td align=right width="6%" height="18">
							创建人：
						</td>
						<td width="15%" height="18">
							<input type="text" name="createdByName" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=headerDTO.getCreatedByName()%>">
						</td>
						<td align=right width="10%" height="40">
							创建原因：
						</td>
						<td width="40%" height="40" colspan="3">
							<textarea rows="2" name="createdReason" 
								cols="20" style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"><%=headerDTO.getCreatedReason()%></textarea>
						</td>
					</tr>
					
					
					
					
				</table>
			</td>
		</tr>
	</table>
</div>      
<div id="headDiv" style="overflow-y: scroll; overflow-x: hidden; position: absolute; top: 0px; left: 0px; width: 100%;"	>
    <table class=headerTable border=1 style="width: 250%">
        <tr height=23px style="cursor: pointer">
            <td align=center width="5%">标签号</td>
            <td align=center width="5%">采购单号</td>
            <td align=center width="5%">预计到货日期</td>
            <td align=center width="5%">资产目录</td>
            <td align=center width="5%">资产目录名称</td>
            <td align=center width="5%">资产名称</td>
            <td align=center width="5%">规格型号</td>
            <td align=center width="5%">数量</td>
            <td align=center width="5%">数量单位</td>
            <td align=center width="5%">厂商</td>
            <td align=center width="5%">地点编号</td>
            <td align=center width="5%">地点名称</td>
            <td align=center width="5%">责任部门</td>
            <td align=center width="5%">责任人</td>
            <td align=center width="5%">专业部门</td>
            <td align=center width="5%">金额</td>
            <td align=center width="5%">创建人</td>
            <td align=center width="5%">创建日期</td>
            <td align=center width="5%">单据状态</td>
            <td align=center width="5%">备注</td>
              <td style="display:none">隐藏域所在列</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow: scroll; height: 90%; width: 100%; position: absolute; top: 22px; left: 0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="250%" border="1" bordercolor="#666666"	style="TABLE-LAYOUT: fixed; word-break: break-all">
                <%
                    if (lineSet == null || lineSet.isEmpty()) {
                %>
                <!-- 
                <tr class="dataTR" style="display: none" >
                    <td width="7%" align="center" style="cursor: pointer">
                        <input type="text" name="transId"
                            id="transId0" class="finput2" readonly value="">
                    </td>
               
                </tr>
                 -->
                <%
                    } else {
                        ZeroTurnLineDTO  lineDTO = null;
                        for (int i = 0; i < lineSet.getSize(); i++) {
                            lineDTO = (ZeroTurnLineDTO) lineSet.getDTO(i);
                %>
                 <tr class="dataTR" style="cursor:pointer">
				<input type="hidden" name="lineId" id="lineId<%=i%>" value="<%=lineDTO.getLineId() %>">
				<!-- <td width="7%" align="center" style="cursor:pointer" >
				   <input type="text" name="transIds" id="transIds<%=i%>" class="finput" readonly value="<%=lineDTO.getTransId()%>"></td>-->
				 <!--   <input type="text" name="zeroNo" id="zeroNo<%=i%>" class="finput" readonly value="<%=lineDTO.getZeroNo()%>"></td>-->
				<!-- <td width="6%" align="center" style="cursor:pointer" >
				  <input type="text" name="barcode" id="barcode<%=i%>" class="finput" readonly value="<%=lineDTO.getBarcode()%>"></td> -->
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="record" id="record<%=i%>" class="finput" readonly value="<%=lineDTO.getRecord()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="procureCode" id="procureCode<%=i%>" class="finput" readonly value="<%=lineDTO.getProcureCode()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="expectedDate" id="expectedDate<%=i%>" class="finput" readonly value="<%=lineDTO.getExpectedDate()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="contentCode" id="contentCode<%=i%>" class="finput" readonly value="<%=lineDTO.getContentCode()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="contentName" id="contentName<%=i%>" class="finput" readonly value="<%=lineDTO.getContentName()%>"></td>
				<!-- <td width="12%" align="center" style="cursor:pointer" >
				  <input type="text" name="computeDays" style="cursor:pointer" id="computeDays<%=i%>" class="finput" readonly value="<%=lineDTO.getComputeDays()%>"></td> -->
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="assetsDescription" id="assetsDescription<%=i%>" class="finput" readonly value="<%=lineDTO.getAssetsDescription()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="itemSpec" id="itemSpec<%=i%>" class="finput" readonly value="<%=lineDTO.getItemSpec()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="itemQty" id="itemQty<%=i%>" class="finput" readonly value="<%=lineDTO.getItemQty()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="unitOfMeasure" id="unitOfMeasure<%=i%>" class="finput" readonly value="<%=lineDTO.getUnitOfMeasure()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="manufacturerName" id="manufacturerName<%=i%>" class="finput" readonly value="<%=lineDTO.getManufacturerName()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="objectNo" id="objectNo<%=i%>" class="finput" readonly value="<%=lineDTO.getObjectNo()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="workorderObjectName" id="workorderObjectName<%=i%>" class="finput" readonly value="<%=lineDTO.getWorkorderObjectName()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="responsibilityDept" id="responsibilityDept<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityDept()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="responsibilityUser" id="responsibilityUser<%=i%>" class="finput" readonly value="<%=lineDTO.getResponsibilityUser()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="specialityDept" id="specialityDept<%=i%>" class="finput" readonly value="<%=lineDTO.getSpecialityDept()%>"></td>
				<!-- <td width="4%" align="center" style="cursor:pointer" >
				  <input type="text" name="years" id="years<%=i%>" class="finput" readonly value="<%=lineDTO.getYears()%>"></td> -->
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="price" id="price<%=i%>" class="finput" readonly value="<%=lineDTO.getPrice()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="createdBy" id="createdBy<%=i%>" class="finput" readonly value="<%=headerDTO.getCreatedByName()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="createdDate" id="createdDate<%=i%>" class="finput" readonly value="<%=lineDTO.getCreateDate()%>"></td>
				<td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="transStatusVal" id="transStatusVal<%=i%>" class="finput" readonly value="<%=lineDTO.getTransStatusVal()%>"></td>
				   <input type="hidden" name="companyCode" value="<%=lineDTO.getCompanyCode()%>">
				  <input type="hidden" name="unitManager" value="<%=lineDTO.getUnitManager()%>">
				  <input type="hidden" name="costCenterCode" value="<%=lineDTO.getCostCenterCode()%>">
				  <input type="hidden" name="isShare" value="<%=lineDTO.getIsShare()%>">
				  <input type="hidden" name="isBulid" value="<%=lineDTO.getIsBulid()%>">
				  <input type="hidden" name="lneId"  value="<%=lineDTO.getLneId()%>">
				  <input type="hidden" name="opeId"  value="<%=lineDTO.getOpeId()%>">
				  <input type="hidden" name="cexId"  value="<%=lineDTO.getCexId()%>">
				  <input type="hidden" name="nleId" value="<%=lineDTO.getNleId()%>">
				  <input type="hidden" name="transStatus" value="<%=lineDTO.getTransStatus()%>">
				 <td width="5%" align="center" style="cursor:pointer" >
				  <input type="text" name="remark" id="remark<%=i%>" class="finput" readonly value="<%=lineDTO.getRemark()%>"></td>
            </tr>
                <%
                    }
                    }
                %>
            </table>
        </div>
   <input type="hidden" name="transId"
		value="<%=headerDTO.getTransId()%>">
	
	<input type="hidden" name="transStatus"
		value="<%=headerDTO.getTransStatus()%>">
	
	<input type="hidden" name="createdBy"
		value="<%=headerDTO.getCreatedBy()%>">
	<input type="hidden" name="dept" value="<%=headerDTO.getCreatedByName()%>">
	
	<input type="hidden" name="excel" value="">				
			<jsp:include page="/public/hintMessage.jsp" flush="true"/>
		</form>
	</body>
</html>
<script type="text/javascript">
function do_initPage(){
    window.focus();
    do_SetPageWidth();
    do_ControlProcedureBtn();
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}
    
</script>
