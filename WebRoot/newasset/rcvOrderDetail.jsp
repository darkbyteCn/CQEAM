<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>调拨接收审批单</title>
</head>
<body topmargin="0" leftmargin="0" onload="initPage();">
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
		        <td width=17%>
<%
	if(orderStatus.equals(AssetsDictConstant.SAVE_TEMP) || orderStatus.equals(AssetsDictConstant.REJECTED)){
%>		        
					<input name="groupName" class="noEmptyInput" readonly style="border-style:solid; border-width:1px; width:100%; cursor:hand" value="<%=headerDTO.getGroupName()%>" title="点击选择或更改“建单组别”" onclick="do_SelectCreateGroup();"  >
<%
	} else {
%>	
					<input name="groupName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getGroupName()%>">
<%
	}
%>					
					</td>
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
<%
	if(orderStatus.equals(AssetsDictConstant.SAVE_TEMP) || orderStatus.equals(AssetsDictConstant.REJECTED)){
%>    
        <img src="/images/eam_images/submit.jpg" alt="提交" onClick="do_SubmitOrder(); return false;">
<%
	}
%>    
        <img src="/images/eam_images/view_opinion.jpg" alt="查阅审批意见" onClick="do_ViewOption(); return false;">
        <img src="/images/eam_images/view_flow.jpg" alt="查阅流程" onClick="viewFlow(); return false;">
        <img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="do_Close(); return false;">
	</legend>
	<jsp:include page="/newasset/rcvLineDetail.jsp" flush="true"/>		
</fieldset>
</form>
</body>
</html>
<script>


function initPage() {
    window.focus();
	do_SetPageWidth();
	var fromGroup = mainFrm.groupId.value;
	if(fromGroup == ""){
		do_SelectCreateGroup();
	}
}

function do_SelectCreateGroup(){
	var fromGroup = mainFrm.groupId.value;
	var url = "<%=AssetsURLList.GROUP_CHOOSE_SERVLET%>?fromGroup=" + fromGroup;
	var popscript = "dialogWidth:20;dialogHeight:10;center:yes;status:no;scrollbars:no;help:no";
	var group = window.showModalDialog(url, null, popscript);
	if(group){
		mainFrm.groupId.value = group["fromGroup"];
		mainFrm.groupName.value = group["fromGroupName"];
	}
}


function do_SubmitOrder() {
    if (mainFrm.groupId.value == "") {
    	alert("请选择当前组别，再提交");
    	do_SelectCreateGroup();
        return;
    }
    var orgId = "<%=orgId%>";
    var userId = "<%=userId%>";
    var groupId = mainFrm.groupId.value;
    var procdureName = mainFrm.procdureName.value;
    var flowCode = "";
    var paramObj = new Object();
    paramObj.orgId = orgId;
    paramObj.useId = userId;
    paramObj.groupId = groupId;
    paramObj.procdureName = procdureName;
    paramObj.flowCode = flowCode;
    paramObj.submitH = 'do_Submit()';
    assign(paramObj);
}


function do_Submit() {
    mainFrm.act.value = "<%=AssetsActionConstant.SUBMIT_ACTION%>";
    mainFrm.submit();
}

function do_Close(){
	self.close();
	if(window.opener && opener.document.forms){
		opener.document.forms[0].submit();
	}
}

function do_ViewOption(){
	var appId = "<%=headerDTO.getReceiveHeaderId()%>";
	var appTableName = "AMS_ASSETS_RCV_HEADER";
	viewOpinion(appId, appTableName);
}

</script>