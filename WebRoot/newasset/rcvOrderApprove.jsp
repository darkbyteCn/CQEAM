<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>调拨接收审批单</title>
</head>
<body topmargin="0" leftmargin="0"  onload="do_SetPageWidth();">
<%
	AmsAssetsRcvHeaderDTO headerDTO = (AmsAssetsRcvHeaderDTO) request.getAttribute(AssetsWebAttributes.RCV_ORDER_HEAD);
	String orderStatus = headerDTO.getOrderStatus();
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId();
	String userId = userAccount.getUserId();
%>
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="post" action="<%=AssetsURLList.RCV_HEADER_SERVLET%>">
<jsp:include page="/flow/include.jsp" flush="true"/>
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
		    <tr>
		        <td align=right width=8%>接收单号：</td>
		        <td width=17%>
					<input name="receiveNo" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getReceiveNo()%>" ></td>
		        <td align=right width=8%>接收人：</td>
		        <td width=17%>
					<input name="receiveUserName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getReceiveUserName()%>" ></td>
		        <td align=right>接收公司：</td>
		        <td>
					<input name="receiveCompany" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getReceiveCompany()%>" ></td>
				<td align=right width=8%>接收部门：</td>
		        <td width=17%>
					<input name="receiveDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getReceiveDeptName()%>" ></td>
		    </tr>
		    <tr>
				<td align=right width=8%>接收日期：</td>
		        <td width=17%>
					<input name="receiveDate" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getReceiveDate()%>"  ></td>
		        <td align=right width=8%>单据状态：</td>
		        <td width=17%><%=headerDTO.getOrderStatusName()%></td>
		        <td align=right width=8%>建单组别：</td>
		        <td width=17%><input name="groupName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getGroupName()%>"></td>
		        <td align=right width=8%>调拨单号：</td>
		        <td width=17%>
					<input name="transNo" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getTransNo()%>" >
				</td>
		    </tr>
		    <tr>
				<td align=right width=8%>调出公司：</td>
		        <td width=17%>
					<input name="fromCompany" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getFromCompany()%>" ></td>
				<td align=right width=8%>调出部门：</td>
		        <td width=17%>
					<input name="fromDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getFromDeptName()%>" ></td>
				<td align=right width=8%>资产大类：</td>
		        <td width=17%>
					<input name="faContentName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getFaContentName()%>" ></td>
		        <td align=right >调出日期：</td>
		        <td>
				<input name="transOutDate" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getTransOutDate()%>" ></td>
		    </tr>
		    <tr>
				<td align=right width=8% align="right" height="40">调拨说明：　</td>
		        <td colspan="7"  height="40">
				<textarea name="transReason" readonly style="border-style:solid; border-width:0px; width:100%; height:100%; background-color:#F2F9FF" rows="1" cols="20"><%=headerDTO.getTransReason()%></textarea></td>
		    </tr>
		</table>
		</td>
	</tr>
</table>
	<input type="hidden" name="receiveHeaderId" value="<%=headerDTO.getReceiveHeaderId()%>">
	<input type="hidden" name="groupId" value="<%=headerDTO.getGroupId()%>">
	<input type="hidden" name="faContentCode" value="<%=headerDTO.getFaContentCode()%>">
	<input type="hidden" name="procdureName" value="<%=headerDTO.getProcdureName()%>">
	<input type="hidden" name="flowCode" value="">
	<input type="hidden" name="act">
<fieldset style="border:1px solid #397DF3; position:absolute;top:125px;width:100%;height:80%">
    <legend>
        <img src="/images/button/pass.gif" id="img6" alt="通过" onClick="do_ApproveOrder('<%=FlowConstant.FLOW_CODE_NEXT%>'); return false;">
        <img src="/images/eam_images/view_opinion.jpg" alt="查阅审批意见" onClick="viewOpinion(); return false;">
        <img src="/images/eam_images/view_flow.jpg" alt="查阅流程" onClick="viewFlow(); return false;">
        <img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="do_Close(); return false;">
	</legend>
	<jsp:include page="/newasset/rcvLineDetail.jsp" flush="true"/>		
</fieldset>
</form>
</body>
</html>
<script>
function do_ApproveOrder(flowCode) {
	mainFrm.flowCode.value = flowCode;
	var hiddenValue = mainFrm.hiddenRight.value;
	if(flowCode == "<%=FlowConstant.FLOW_CODE_NEXT%>"){
	    var orgId = "<%=orgId%>";
	    var userId = "<%=userId%>";
	    var groupId = mainFrm.groupId.value;
	    var procdureName = mainFrm.procdureName.value;
	    var flowCode2 = "";
	    var paramObj = new Object();
	    paramObj.orgId = orgId;
	    paramObj.useId = userId;
	    paramObj.groupId = groupId;
	    paramObj.procdureName = procdureName;
		if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
			flowCode2 = mainFrm.faContentCode.value;
		}
		paramObj.flowCode = flowCode2;
		paramObj.submitH = 'do_Approve()';
		assign(paramObj);
	} else {
		do_Approve();
	}
}

function do_Approve(){
    mainFrm.act.value = "<%=AssetsActionConstant.APPROVE_ACTION%>";
    mainFrm.submit();
}

function do_Close(){
	self.close();
	if(window.opener && opener.document.forms){
		opener.document.forms[0].submit();
	}
}
</script>