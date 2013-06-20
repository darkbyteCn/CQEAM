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
%>
<title>资产盘点任务审批页面</title>
<script type="text/javascript" src="/WebLibary/js/util.js"></script>
<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
<script type="text/javascript" src="/WebLibary/js/api.js"></script>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript">
        printToolBar();
    </script>
</head>
<body leftmargin="0" topmargin="0" rightmargin="1" onload="do_initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<%@ include file="/flow/flowNoButton.jsp" %>
<form action="<%=AssetsURLList.CHECK_APPR_SERVLET%>" method="post" name="mainFrm">

<%@ include file="/flow/flowPara.jsp"%>
<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="position:absolute;top:30px;left:0px;width:100%">
<table border="0"  class="queryTable" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<table width=100% border="0">
			    <tr>
			        <td align=right width="10%" height="18">任务编号：</td>
			        <td width="15%" height="18">
			        	<input type="text" name="batchNo"  readonly style="width:100%;" class="input_style2" value="<%=batchDTO.getBatchNo()%>"></td>
			        <td align=right width="10%" height="18">盘点部门：</td>
			        <td width="15%" height="18">
						<input type="text" name="checkDeptName" readonly style="width:100%;" class="input_style2" value="<%=batchDTO.getCheckDeptName()%>"></td>
			        <td align=right width="10%" height="18">建单组别：</td>
			        <td width="15%" height="18">
						<input type="text" name="groupName" readonly style="width:100%;" class="input_style2" value="<%=batchDTO.getGroupName()%>"></td>
			        <td align=right width="10%" height="18">创建人：</td>
			        <td width="15%" height="18">
						<input type="text" name="createdUser" readonly style="width:100%;" class="input_style2" value="<%=batchDTO.getCreatedUser()%>"></td>
			    </tr>
			    <tr>
			        <td align=right width="10%" height="18">盘点类型：</td>
			        <td width="15%" height="18">
			        	<input type="text" name="orderTypeName" readonly style="width:100%;" class="input_style2" value="<%=batchDTO.getOrderTypeName()%>" size="20"></td>
			        <td align=right width="10%" height="40">任务描述：</td>
			        <td width="65%" height="20" colspan="5"><textarea  name="taskDesc" cols="20" class="input_style2" readonly style="width:100%;" ><%=batchDTO.getTaskDesc()%></textarea>
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
	<div id="dataDiv" style="overflow:scroll;height:90%;width:100%;position:absolute;top:34px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
    if (chkOrders != null && !chkOrders.isEmpty()) {
		AmsAssetsCheckHeaderDTO chkOrder = null;
		String transNo = "";
		String headerId = "";
	    for (int i = 0; i < chkOrders.getSize(); i++) {
	        chkOrder = (AmsAssetsCheckHeaderDTO) chkOrders.getDTO(i);
			headerId = chkOrder.getHeaderId();
			transNo = chkOrder.getTransNo();
%>
            <tr class="dataTR" style="cursor:pointer" onClick="do_ShowDetail('<%=headerId%>')" title="点击查看盘点工单“<%=transNo%>”的详细信息">
                <td width="15%"><input type="text" readonly class="finput2" name="transNo" id="transNo<%=i%>" value="<%=chkOrder.getTransNo()%>"></td>
                <td width="10%"><input type="text" readonly class="finput" name="statusName" id="statusName<%=i%>" value="<%=chkOrder.getStatusName()%>"></td>
                <td width="15%"><input type="text" readonly class="finput2" name="objectCode" id="objectCode<%=i%>" value="<%=chkOrder.getObjectCode()%>"></td>
                <td width="20%"><input type="text" readonly class="finput" name="objectName" id="objectName<%=i%>" value="<%=chkOrder.getObjectName()%>"></td>
                <td width="8%"><input type="text" readonly class="finput2" name="checkCategory" id="checkCategory<%=i%>" value="<%=chkOrder.getCheckCategoryName()%>"></td>
                <td width="8%"><input type="text" name="startTime" id="startTime<%=i%>" value="<%=chkOrder.getStartTime()%>" class="finput2" readonly></td>
                <td width="8%"><input type="text" name="implementDays" id="implementDays<%=i%>" value="<%=chkOrder.getImplementDays()%>" class="finput3" readonly></td>
                <td width="8%"><input type="text" name="implementUser" id="implementUser<%=i%>" value="<%=chkOrder.getImplementUser()%>" class="finput" readonly></td>
                <td width="8%"><input type="text" name="archivedUser" id="archivedUser<%=i%>" value="<%=chkOrder.getArchivedUser()%>" class="finput" readonly></td>
            </tr>
<%
        }
    }
%>
        </table>
    </div>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
</form>
</body>

<script type="text/javascript">
function do_initPage(){
    do_SetPageWidth();
    doLoad();
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

function do_Complete_app_yy() {
    document.mainFrm.act.value = "APPROVE_ACTION";
    document.forms[0].submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}
</script>
</html>
