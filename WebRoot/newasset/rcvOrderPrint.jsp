<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>调拨接收单</title>
</head>
<body topmargin="0" leftmargin="0"  onload="window.focus();">
<%
	AmsAssetsRcvHeaderDTO headerDTO = (AmsAssetsRcvHeaderDTO) request.getAttribute(AssetsWebAttributes.RCV_ORDER_HEAD);
	String orderStatus = headerDTO.getOrderStatus();
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId();
	String userId = userAccount.getUserId();
%>
<form name="mainFrm" method="post">
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
		    <tr>
		        <td align=right width=8%>接收单号：</td>
		        <td width=17%>
					<input name="receiveNo" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getReceiveNo()%>" >
				</td>
		        <td align=right width=8%>接收人：</td>
		        <td width=17%>
					<input name="receiveUserName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getReceiveUserName()%>" >
				</td>
		        <td align=right>接收公司：</td>
		        <td>
					<input name="receiveCompany" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getReceiveCompany()%>" >
				</td>
				<td align=right width=8%>接收部门：</td>
		        <td width=17%>
					<input name="receiveDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getReceiveDeptName()%>" >
				</td>
		    </tr>
		    <tr>
				<td align=right width=8%>接收日期：</td>
		        <td width=17%>
					<input name="receiveDate" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getReceiveDate()%>">
				</td>
		        <td align=right width=8%>单据状态：</td>
		        <td width=17%><%=headerDTO.getOrderStatusName()%></td>
		        <td align=right width=8%>建单组别：</td>
		        <td width=17%>
					<input name="groupName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF" value="<%=headerDTO.getGroupName()%>">
				</td>
		        <td align=right width=8%>调拨单号：</td>
		        <td width=17%>
					<input name="transNo" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getTransNo()%>">
				</td>
		    </tr>
		    <tr>
				<td align=right width=8%>调出公司：</td>
		        <td width=17%>
					<input name="fromCompany" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getFromCompany()%>" >
				</td>
				<td align=right width=8%>调出部门：</td>
		        <td width=17%>
					<input name="fromDeptName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getFromDeptName()%>" >
				</td>
				<td align=right width=8%>资产大类：</td>
		        <td width=17%>
					<input name="faContentName" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getFaContentName()%>" >
				</td>
		        <td align=right >调出日期：</td>
		        <td>
					<input name="transOutDate" readonly style="border-style:solid; border-width:0px; width:100%; background-color:#F2F9FF"  value="<%=headerDTO.getTransOutDate()%>" >
				</td>
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
<jsp:include page="/newasset/approveContent.jsp" flush="true"/>
	<input type="hidden" name="act">
<fieldset style="border:0; position:relative;width:100%">
    <legend>
		<table width="100%" id="buttonSet">
		    <tr>
		    	<td width="100%" align="center">
			        <img src="/images/eam_images/page_config.jpg" alt="打印设置" onClick="do_SetupPrint()">
			        <img src="/images/eam_images/print_view.jpg" alt="打印设置" onClick="do_PrevPrint()">
			        <img src="/images/eam_images/print.jpg" alt="打印" onClick="do_PrintOrder()">
			        <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close()">
		    	</td>
		    </tr>
		</table>
    </legend>
</fieldset>
	<jsp:include page="/newasset/rcvPrintLine.jsp" flush="true"/>		
</form>
  <object id="webbrowser" width="0" height="0" classid="clsid:8856f961-340a-11d0-a96b-00c04fd705a2"></object>
</body>
</html>
<script>

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