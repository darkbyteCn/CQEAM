<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<%
	AmsAssetsCheckBatchDTO batchDTO = (AmsAssetsCheckBatchDTO) request.getAttribute(AssetsWebAttributes.CHECK_BATCH_DATA);
	String batchId = batchDTO.getBatchId();
	DTOSet chkOrders = (DTOSet) request.getAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS);
	String today = CalendarUtil.getCurrDate();
    String pageTitle = "资产盘点任务“"+batchDTO.getBatchNo()+"”详细";
%>
<title><%=pageTitle%></title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript">
        printTitleBar("<%=pageTitle%>");
        printToolBar();
    </script>
</head>


<body leftmargin="0" topmargin="0" rightmargin="1" onload="do_initPage()">
<form action="" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="position:absolute;top:50px;left:0px;width:100%">
<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width="10%" height="18">任务编号：</td>
			        <td width="15%" height="18">
					<input type="text" name="batchNo" class="readonlyInput" readonly style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF" value="<%=batchDTO.getBatchNo()%>" size="20"></td>
			        <td align=right width="10%" height="18">盘点部门：</td>
			        <td width="15%" height="18">
					<input type="text" name="checkDeptName" class="readonlyInput" readonly style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF" value="<%=batchDTO.getCheckDeptName()%>" size="20"></td>
			        <td align=right width="10%" height="18">建单组别：</td>
			        <td width="15%" height="18"><input type="text" name="groupName"  class="noEmptyInput" readonly style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF" value="<%=batchDTO.getGroupName()%>" size="20"></td>
			        <td align=right width="10%" height="18">创建人：</td>
			        <td width="15%" height="18"><input type="text" name="createdUser" class="readonlyInput" readonly style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF" value="<%=batchDTO.getCreatedUser()%>"></td>
			    </tr>
			    <tr>
			        <td align=right width="10%" height="18">盘点类型：</td>
			        <td width="15%" height="18">
			        	<input type="text" name="orderTypeName" class="readonlyInput" readonly style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF" value="<%=batchDTO.getOrderTypeName()%>" size="20"></td>
                    <td align=right width="10%" height="18">成本中心：</td>
			        <td width="15%" height="18">
			        	<input type="text" name="costCenterName" class="readonlyInput" readonly style="border-style:solid; border-width:0; width:100%; background-color:#F2F9FF" value="<%=batchDTO.getCostCenterName()%>" size="20"></td>
			        <td align=right width="10%" height="40">任务描述：</td>
			        <td width="40%" height="40" colspan="3">
						<textarea name="taskDesc" readonly rows="2" cols="20" style="border-style:solid; border-width:0; width:100%; height:100%; background-color:#F2F9FF"><%=batchDTO.getTaskDesc()%></textarea>
					</td>
			    </tr>
			</table>
		</td>
	</tr>
</table>
</div>
<input type="hidden" name="batchId" value="<%=batchId%>">
<input type="hidden" name="batchCheckLocation" value="<%=batchDTO.getBatchCheckLocation()%>">
<input type="hidden" name="batchImplementBy" value="<%=batchDTO.getBatchImplementBy()%>">
<input type="hidden" name="today" value="<%=today%>">
<input type="hidden" name="organizationId" value="<%=batchDTO.getOrganizationId()%>">
<input type="hidden" name="groupId" value="<%=batchDTO.getGroupId()%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="flowCode" value="">
<input type="hidden" name="procdureName" value="<%=batchDTO.getProcdureName()%>">
    <div id="headDiv" style="overflow:hidden;position:absolute;top:13px;left:1px;width:100%">
		<table class=headerTable border=1 style="width:120%">
			<tr height="23px">
				<td align=center width="15%">工单编号</td>
				<td align=center width="10%">工单状态</td>
				<td align=center width="15%">地点代码</td>
				<td align=center width="20%">地点名称</td>
				<td align=center width="8%">扫描专业</td>
				<td align=center width="8%">开始日期</td>
				<td align=center width="8%">执行周期(天)</td>
				<td align=center width="8%">执行人</td>
				<td align=center width="8%">归档人</td>
			</tr>
		</table>
	</div>
	<div id="dataDiv" style="overflow:scroll;height:90%;width:100%;position:absolute;top:47px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if (chkOrders != null && !chkOrders.isEmpty()) {
		AmsAssetsCheckHeaderDTO chkOrder = null;
		String transNo = "";
		String headerId = "";
		String fontColor = "#000000";
	    for (int i = 0; i < chkOrders.getSize(); i++) {
	        chkOrder = (AmsAssetsCheckHeaderDTO) chkOrders.getDTO(i);
			headerId = chkOrder.getHeaderId();
			transNo = chkOrder.getTransNo();
			if(chkOrder.hasPreviousOrder()){
				fontColor = "#FF0000";
			} else {
				fontColor = "#000000";
			}
%>
            <tr class="dataTR" style="cursor:pointer" onClick="do_ShowDetail('<%=headerId%>')" title="点击查看盘点工单“<%=transNo%>”的详细信息">
                <td width="15%"><input type="text" readonly class="finput" name="transNo" id="transNo<%=i%>" value="<%=chkOrder.getTransNo()%>" style="color:<%=fontColor%>"></td>
                <td width="10%"><input type="text" readonly class="finput" name="statusName" id="statusName<%=i%>" value="<%=chkOrder.getStatusName()%>"></td>
                <td width="15%"><input type="text" readonly class="finput" name="objectCode" id="objectCode<%=i%>" value="<%=chkOrder.getObjectCode()%>"></td>
                <td width="20%"><input type="text" readonly class="finput" name="objectName" id="objectName<%=i%>" value="<%=chkOrder.getObjectName()%>"></td>
                <td width="8%"><input type="text" readonly class="finput2" name="checkCategory" id="checkCategory<%=i%>" value="<%=chkOrder.getCheckCategoryName()%>"></td>
                <td width="8%"><input type="text" name="startTime" id="startTime<%=i%>" value="<%=chkOrder.getStartTime()%>" class="finput2" readonly></td>
                <td width="8%"><input type="text" name="implementDays" id="implementDays<%=i%>" value="<%=chkOrder.getImplementDays()%>" class="finput3"></td>
                <td width="8%"><input type="text" name="implementUser" id="implementUser<%=i%>" value="<%=chkOrder.getImplementUser()%>" class="finput" readonly></td>
                <td width="8%"><input type="text" name="archivedUser" id="archivedUser<%=i%>" value="<%=chkOrder.getArchivedUser()%>" class="finput" readonly></td>
            </tr>
<%
        }
    }
%>
        </table>
    </div>
</form>
</body>
</html>
<script type="text/javascript">

function do_initPage(){
    do_SetPageWidth();
    needAttachMenu = false;
    do_ControlProcedureBtn();
}

function do_ShowDetail(headerId){
    var url = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckHeaderServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&headerId=" + headerId;

    var factor = 0.95;
    var width = window.screen.availWidth * factor;
    var height = window.screen.availHeight * factor;
    var top = window.screen.height * (1 - factor) / 2;
    var left = window.screen.availWidth * (1 - factor) / 2;

	var winName = "orderWin";
    var style = "width="
            + width
            + ",height="
            + height
            + ",top="
            + top
            + ",left="
            + left
            + ",toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	window.open(url, winName, style);
}

</script>
