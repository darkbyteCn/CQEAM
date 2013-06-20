<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newSite.dto.EamAddressAddHDTO"%>
<%@ page import="com.sino.ams.newSite.dto.EamAddressAddLDTO" %>
<%
    EamAddressAddHDTO batchDTO = (EamAddressAddHDTO) request.getAttribute(AssetsWebAttributes.ADDRESS_HEAD_DATA);
    DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
    String pageTitle = "地点维护单据“" + batchDTO.getTransNo() +"”详细信息";
%>
<html>
	<head>
		<title><%=pageTitle%></title>
		<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
		<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
	</head>
<body leftmargin="0" topmargin="0" rightmargin="1" onload="do_initPage()">
<input type="hidden" name="act" value="">
<input type="hidden" name="transType" value="<%= batchDTO.getTransType()%>">
<input type="hidden" name="transId" value="<%= batchDTO.getTransId()%>">
  <jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.newSite.servlet.EamAddressAddServlet" method="post" name="mainFrm">
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
							<input type="text" name="transNo"  readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=batchDTO.getTransNo()%> "	size="20">
						</td>
						<td align=right width="6%" height="18">
							单据类型：
						</td>
						<td width="20%" height="18">
							<input type="text" name="transType" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=batchDTO.getTransType()%>" size="20">
						<td align=right width="6%" height="18">
							建单组别：
						</td>
						<td width="15%" height="18">
							<input type="text" name="groupName" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=batchDTO.getDeptName()%>"
								onClick="do_SelectAppGroup()" size="20">
						</td>
					</tr>
					<tr>
						<td align=right width="6%" height="18">
							公司名称：
						</td>
						<td width="15%" height="18">
							<input type="text" name="organizationName" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=batchDTO.getOrganizationName()%>" size="20">
						</td>
						<td align=right width="10%" height="18">
							部门名称：
						</td>
						<td width="20%" height="18">
							<input type="text" name="deptName" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=batchDTO.getDeptName()%>" size="20">
						</td>
						<td align=right width="10%" height="18">
							创建日期：
						</td>
						<td width="15%" height="18">
							<input type="text" name="creationDate" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=batchDTO.getCreationDate()%>" size="20">
						</td>
					</tr>
					<tr>
						<td align=right width="6%" height="18">
							创建人：
						</td>
						<td width="15%" height="18">
							<input type="text" name="createdByName" 
								readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"
								value="<%=batchDTO.getCreatedByName()%>">
						</td>
						<td align=right width="10%" height="40">
							创建原因：
						</td>
						<td width="40%" height="40" colspan="3">
							<textarea rows="2" name="createdReason" 
								cols="20" style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"><%=batchDTO.getCreatedReason()%></textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>      
<div id="headDiv" style="overflow-y: scroll; overflow-x: hidden; position: absolute; top: 0px; left: 0px; width: 100%;"	>
    <table class=headerTable border=1 style="width: 100%">
        <tr height=23px style="cursor: pointer">
              <td align=center width="10%">公司名称</td>
              <td align=center width="15%">地点代码</td>
              <td align=center width="19%">地点名称</td>
              <td align=center width="10%">地点专业</td>
              <td align=center width="8%">基站或营业厅编号</td>
              <td align=center width="5%">区域代码</td>
              <td align=center width="7%">地市</td>
              <td align=center width="7%">区县</td>
              <td style="display:none">隐藏域所在列</td>
        </tr>
    </table>
</div>
<div id="dataDiv" style="overflow: scroll; height: 90%; width: 100%; position: absolute; top: 22px; left: 0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666"	style="TABLE-LAYOUT: fixed; word-break: break-all">
                <%
                    if (lineSet == null || lineSet.isEmpty()) {
                %>
                <tr class="dataTR" style="display: none" >
                    <td width="10%" align="center" style="cursor: pointer">
                        <input type="text" name="organizationName"
                            id="organizationName0" class="finput2" readonly value="">
                    </td>
                    <td width="15%" align="center" style="cursor: pointer">
                        <input type="text" name="workorderObjectCode"
                            id="workorderObjectCode0" class="finput2" readonly value=" ">
                    </td>
                    <td width="19%" align="center" style="cursor: pointer">
                        <input type="text" name="workorderObjectName"
                            id="workorderObjectName0" class="finput2" readonly value=" ">
                    </td>
                    <td width="10%" align="left" style="cursor: pointer">
                        <input type="text" name="objectCategory" id="objectCategory0"
                            class="finput" readonly value="">
                    </td>
                    <td width="8%" align="left" style="cursor: pointer">
                        <input type="text" name="btsNo" id="btsNo0"
                            class="finput" readonly value="">
                    </td>
                    <td width="5%" align="right" style="cursor: pointer">
                        <input type="text" name="areaType" id="areaType0"
                            class="finput3" readonly value="">
                    </td>
                    <td width="7%" align="right" style="cursor: pointer">
                        <input type="text" name="city" id="city0" class="finput3"
                            readonly value="">
                    </td>
                    <td width="7%" align="right">
                        <input type="text" name="county" id="county0"
                            class="finputNoEmpty" value="">
                    </td>
                    <td style="display: none">
                        <input type="hidden" name="lineId" id="oldResponsibilityDept0"
                            value="">
                    </td>
                </tr>
                <%
                    } else {
                        EamAddressAddLDTO lineDTO = null;
                        for (int i = 0; i < lineSet.getSize(); i++) {
                            lineDTO = (EamAddressAddLDTO) lineSet.getDTO(i);
                %>
                <tr class="dataTR" 	style="cursor: pointer">
                    <td width="10%" align="center" style="cursor: pointer">
                        <input type="text" name="organizationName" id="barcode<%=i%>"
                            class="finput2" readonly
                            value="<%=batchDTO.getOrganizationName()%>">
                    </td>
                    <td width="15%" align="center" style="cursor: pointer">
                        <input type="text" name="workorderObjectCode"
                            id="workorderObjectCode<%=i%>" class="finput2" readonly
                            value="<%=lineDTO.getWorkorderObjectCode()%>">
                    </td>
                    <td width="19%" align="center" style="cursor: pointer">
                        <input type="text" name="workorderObjectName"
                            id="workorderObjectName<%=i%>" class="finput2" readonly
                            value="<%=lineDTO.getWorkorderObjectName()%>">
                    </td>
                    <td width="10%" align="center" style="cursor: pointer">
                        <input type="text" name="objectCategory"
                            id="objectCategory<%=i%>" class="finput" readonly
                            value="<%=lineDTO.getObjectCategory()%>">
                    </td>
                    <td width="8%" align="center" style="cursor: pointer">
                        <input type="text" name="btsNo"
                            id="btsNo<%=i%>" class="finput" readonly
                            value="<%=lineDTO.getBtsNo()%>">
                    </td>
                    <td width="5%" align="center" style="cursor: pointer">
                        <input type="text" name="areaType" id="areaType<%=i%>"
                            class="finput3" readonly value="<%=lineDTO.getAreaType()%>">
                    </td>
                    <td width="7%" align="center" style="cursor: pointer">
                        <input type="text" name="city" id="city<%=i%>" class="finput3"
                            readonly value="<%=lineDTO.getCity()%>">
                    </td>
                    <td width="7%" align="center">
                        <input type="text" name="county" id="county<%=i%>"
                            readonly class="finput3" value="<%=lineDTO.getCounty()%>">
                    </td>
                    <td style="display: none">
                        <input type="hidden" name="lineId" id="lineId<%=i%>"
                            value="<%=lineDTO.getLineId()%>">
                    </td>
                </tr>
                <%
                    }
                    }
                %>
            </table>
        </div>
	<input type="hidden" name="transId"
		value="<%=batchDTO.getTransId()%>">
	<input type="hidden" name="transStatus"
		value="<%=batchDTO.getTransStatus()%>">
	<input type="hidden" name="organizationId"
		value="<%=batchDTO.getOrganizationId()%>">
	<input type="hidden" name="createdBy"
		value="<%=batchDTO.getCreatedBy()%>">
	<input type="hidden" name="dept" value="<%=batchDTO.getDept()%>">
	<input type="hidden" name="act" value="">
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
    
/**
 * 功能：要进行附件管理的页面需要覆盖本方法。
 */
function setAttachmentConfig(){
    var attchmentConfig = new AttachmentConfig();
    attchmentConfig.setOrderPkName("transId");
    return attchmentConfig;
}
</script>
