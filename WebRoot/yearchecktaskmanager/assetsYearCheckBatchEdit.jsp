<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<%
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	int orgId = userAccount.getOrganizationId();
	AmsAssetsCheckBatchDTO batchDTO = (AmsAssetsCheckBatchDTO) request.getAttribute(AssetsWebAttributes.CHECK_BATCH_DATA);
	String batchId = batchDTO.getBatchId();
	DTOSet chkHeaders = (DTOSet) request.getAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS);
	String today = CalendarUtil.getCurrDate();
	int validOrderCount = 0;
%>
<title>资产年度盘点单任务批</title>
    <script type="text/javascript" src="/WebLibary/js/util.js"></script>
    <script type="text/javascript" src="/WebLibary/js/util2.js"></script>
    <script type="text/javascript" src="/WebLibary/js/api.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript">
        printToolBar();
    </script>
</head>
<body leftmargin="0" topmargin="0" rightmargin="1" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">

<%@ include file="/flow/flowNoButton.jsp" %>
<form action="<%=AssetsURLList.CHECK_BATC_SERVLET%>" method="post" name="mainFrm">

<%@ include file="/flow/flowPara.jsp"%>
<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="position:absolute;top:29px;left:1px;width:100%">
    <table border="0" width="100%" style="border-collapse: collapse" id="table1">
        <tr style="height:23px">
            <td align=right width="7%" height="18">工单编号：</td>
            <td width="13%" height="18">
                <input type="text" name="batchNo" class="input_style2" readonly style="width:100%" value="<%=batchDTO.getBatchNo()%>" size="20"></td>
            <td align=right width="7%" height="18">盘点部门：</td>
            <td width="26%" height="18">
                <input type="text" name="checkDeptName" id="checkDeptName" readonly value="<%=batchDTO.getCheckDeptName()%>" class="input_style2" style="width:100%"></td>
            <td align=right width="7%" height="18">建单组别：</td>
            <td width="15%" height="18">
                <input type="text" name="groupName" id="checkGroup" class="input_style2" readonly style="width:95%;" value="<%=batchDTO.getGroupName()%>" size="20"></td>
            <td align=right width="7%" height="18">创建人：</td>
            <td width="15%" height="18">
                <input type="text" name="createdUser" class="input_style2" readonly style="width:100%" value="<%=batchDTO.getCreatedUser()%>"></td>
        </tr>
        <tr>
            <td align=right width="7%" height="18">盘点类型：</td>
            <td width="15%" height="18">
                <input type="text" name="orderTypeName" class="input_style2" readonly style="width:100%" value="<%=batchDTO.getOrderTypeName()%>" size="20"></td>
            <td align=right width="7%" height="18">成本中心：</td>
            <td width="15%" height="18">
                <input type="text" name="costCenterName"  readonly style="width:100%;cursor:pointer" value="<%=batchDTO.getCostCenterName()%>" title="点击选择或更改“成本中心”" onClick="chooseCostCenter()" size="20"></td>
            <td align=right width="7%" height="40" valign="top">工单描述：</td>
            <td width="40%" height="43" colspan="5"><textarea name="taskDesc" style="width:100%; height:100%"><%=batchDTO.getTaskDesc()%></textarea>
            </td>
        </tr>
        <!-- jeffery -->
        <tr>
           <td align=right width="7%" height="18">任务名称：</td>
           <td width="15%" height="18">
                <input type="text" name="taskName" class="finputNoEmpty" readonly style="width:80%" value="<%=batchDTO.getTaskName()%>" size="20">
                <a href="" title="点击选择任务" onclick="chooseOrderTask(); return false;">[…]</a></td>
            </td>
            <!-- 2013-07-04 Jeffery-->
	        <td width="7%" align="right">任务编号：</td>
	        <td width="15%">
	          <input class="input_style1" type="text" name="taskNumber" readonly style="width:100%" value="<%=batchDTO.getTaskNumber()%>">
	        </td>
	        <td width="7%" align="right">任务类型：</td>
	        <td width="15%">
	          <input class="input_style1" type="text" name="taskTypeName" readonly  style="width:100%" value="<%=batchDTO.getTaskTypeName()%>">
	        </td>
        </tr>
    </table>
</div>
<input type="hidden" name="creationDate" value="<%=batchDTO.getCreationDate()%>">
<input type="hidden" name="batchId" value="<%=batchId%>">
<input type="hidden" name="costCenterCode" value="<%=batchDTO.getCostCenterCode()%>">
<input type="hidden" name="today" value="<%=today%>">
<input type="hidden" name="organizationId" value="<%=batchDTO.getOrganizationId()%>">
<input type="hidden" name="groupId" value="<%=batchDTO.getGroupId()%>">
<input type="hidden" name="orderType" value="<%=batchDTO.getOrderType()%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="procdureName" value="<%=batchDTO.getProcdureName()%>">
<input type="hidden" name="checkDept" value="<%=batchDTO.getCheckDept()%>">
<!-- <input type="hidden" name="taskNumber" value="<%=batchDTO.getTaskNumber()%>">-->
<input type="hidden" name="taskType" value="<%=batchDTO.getTaskType()%>">
<div id="buttonDiv" style="position:absolute;top:195px;left:1px;width:100%">

        <img src="/images/eam_images/choose.jpg" alt="点击选择盘点地点" onClick="do_SelectCheckLocation(); return false;">
       	<img src="/images/eam_images/imp_from_excel.jpg" alt="点击导入盘点地点" onClick="do_ImportCheckLocation(); return false;">
        <img src="/images/eam_images/delete_line.jpg" alt="删除" onClick="do_DeleteLines(); return false;">

<img src="/images/eam_images/detail_info.jpg" id="img6" alt="资产明细" onClick="do_ViewAssetsData()">

		统一设置：
		<input type="checkbox" name="allImplementUser" id="allImplementUser"><label for="allImplementUser">执行人</label>
		<input type="checkbox" name="allStartTime" id="allStartTime"><label for="allStartTime">开始日期</label>
		<input type="checkbox" name="allImplementDays" id="allImplementDays"><label for="allImplementDays">执行周期</label>
		<input type="checkbox" name="allArchiveUser" id="allArchiveUser"><label for="allArchiveUser">归档人</label>
		<%--<input type="checkbox" name="allCheckCategory" id="allCheckCategory"><label for="allCheckCategory">扫描专业</label>
		--%>

    </div>
    <div id="headDiv" style="overflow:hidden;width:100%">
    <table class=headerTable border=1 id="headTb" style="width:120%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr height=23px style="cursor:pointer" title="点击全选或取消全选">
            <td align=center width="3%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <%--<td align=center width="11%">工单编号</td>
            <td align=center width="8%">工单状态</td>
            <td align=center width="15%">地点代码</td>
            <td align=center width="15%">地点简称</td>
            <td align=center width="8%">扫描专业</td> 
            <td align=center width="10%">开始日期</td>
            <td align=center width="10%">执行周期</td>
            <td align=center width="10%">执行人</td>
            <td align=center width="10%">归档人</td>
            
            --%>
            <td align=center width="14%">工单编号</td>
            <td align=center width="8%">工单状态</td>
            <td align=center width="15%">地点代码</td>
            <td align=center width="20%">地点简称</td>
            <td align=center width="10%">开始日期</td>
            <td align=center width="10%">执行周期</td>
            <td align=center width="10%">执行人</td>
            <td align=center width="10%">归档人</td>
            <td width="0%" style="display:none">-隐藏域所在列<br></td>
        </tr>
    </table>
	</div>

	<div id="dataDiv" style="overflow:scroll;height:70%;width:100%;" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">

        <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
    if (chkHeaders == null || chkHeaders.isEmpty()) {
%>
            <tr class="dataTR" style="display:none">
                <td align="center" width="3%"><input type="checkbox" name="subCheck" id="subCheck0"></td><%--
                <td width="11%"><input type="text" readonly class="finput" name="transNo" id="transNo0"></td>
                <td width="8%"><input type="text" readonly class="finput" name="statusName" id="statusName0"></td>
                <td width="15%"><input type="text" readonly class="finput" name="objectCode" id="objectCode0"></td>
                <td width="15%"><input type="text" readonly class="finput" name="objectName" id="objectNameName0"></td>
                <td width="8%"><select name="checkCategory" id="checkCategory0" style="width:100%" onChange="do_SetCheckCategory(this)"><%=batchDTO.getCheckCategoryOpt()%></select></td> 
                <td width="10%"><input type="text" name="startTime" id="startTime0" class="finputNoEmpty" style="cursor:pointer;text-align:center" readonly value="" onclick="gfPop.fEndPop(referToday0, startTime0)" title="点击选择或更改该盘点工单“开始日期”"></td>
                <td width="10%"><input type="text" name="implementDays" id="implementDays0" class="finputNoEmpty" value="" onkeydown="intOnlyOnkeyDown(this.value);" onBlur="do_SetLineDays(this)"></td>
                <td width="10%"><input type="text" name="implementUser" id="implementUser0" class="finputNoEmpty" style="cursor:pointer;text-align:left" readonly value="" onclick="do_SelectUser(1, this);" title="点击选择或更改本盘点工单“执行人”"></td>
                <td width="10%"><input type="text" name="archivedUser" id="archivedUser0" class="finputNoEmpty" style="cursor:pointer" readonly value="" onclick="do_SelectUser(2, this);" title="点击选择或更改本盘点工单“归档人”"></td>
                --%>
                <td width="14%"><input type="text" readonly class="finput" name="transNo" id="transNo0"></td>
                <td width="8%"><input type="text" readonly class="finput" name="statusName" id="statusName0"></td>
                <td width="15%"><input type="text" readonly class="finput" name="objectCode" id="objectCode0"></td>
                <td width="20%"><input type="text" readonly class="finput" name="objectName" id="objectNameName0"></td>
                <td width="10%"><input type="text" name="startTime" id="startTime0" class="finputNoEmpty" style="cursor:pointer;text-align:center" readonly value="" onclick="gfPop.fEndPop(referToday0, startTime0)" title="点击选择或更改该盘点工单“开始日期”"></td>
                <td width="10%"><input type="text" name="implementDays" id="implementDays0" class="finputNoEmpty" value="" onkeydown="intOnlyOnkeyDown(this.value);" onBlur="do_SetLineDays(this)"></td>
                <td width="10%"><input type="text" name="implementUser" id="implementUser0" class="finputNoEmpty" style="cursor:pointer;text-align:left" readonly value="" onclick="do_SelectUser(1, this);" title="点击选择或更改本盘点工单“执行人”"></td>
                <td width="10%"><input type="text" name="archivedUser" id="archivedUser0" class="finputNoEmpty" style="cursor:pointer" readonly value="" onclick="do_SelectUser(2, this);" title="点击选择或更改本盘点工单“归档人”"></td>

                <td width="0%" style="display:none">
                    <input type="hidden" name="headerId" id="headerId0">
                    <input type="hidden" name="checkLocation" id="checkLocation0">
                    <input type="hidden" name="implementBy" id="implementBy0">
                    <input type="hidden" name="archivedBy" id="archivedBy0">
                    <input type="hidden" name="referToday" id="referToday0" value="<%=today%>">
                </td>
            </tr>
<%
	} else {
		AmsAssetsCheckHeaderDTO chkHeader = null;
		String transNo = "";
		String fontColor = "#000000";
	    for (int i = 0; i < chkHeaders.getSize(); i++) {
	        chkHeader = (AmsAssetsCheckHeaderDTO) chkHeaders.getDTO(i);
			transNo = chkHeader.getTransNo();
			if(chkHeader.hasPreviousOrder()){
				fontColor = "#FF0000";
			} else {
				fontColor = "#000000";
				validOrderCount++;
			}
            int implementDays = chkHeader.getImplementDays();
            String strDays = String.valueOf(implementDays);
            if(strDays.equals("-1")){
                strDays = "";
            }
%>
            <tr class="dataTR">
                <td align="center" width="3%"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value=""></td>
                <%--<td width="11%" onClick="do_ShowDetail(this)" title="点击查看盘点工单“<%=transNo%>”的详细信息"><input type="text" readonly class="finput" name="transNo" id="transNo<%=i%>" value="<%=chkHeader.getTransNo()%>"></td>
                <td width="8%" onClick="do_ShowDetail(this)" title="点击查看盘点工单“<%=transNo%>”的详细信息"><input type="text" readonly class="finput" name="statusName" id="statusName<%=i%>" value="<%=chkHeader.getStatusName()%>"></td>
                <td width="15%" onClick="do_ShowDetail(this)" title="点击查看盘点工单“<%=transNo%>”的详细信息"><input type="text" readonly class="finput" name="objectCode" id="objectCode<%=i%>" value="<%=chkHeader.getObjectCode()%>" style="color:<%=fontColor%>"></td>
                <td width="15%" onClick="do_ShowDetail(this)" title="点击查看盘点工单“<%=transNo%>”的详细信息"><input type="text" readonly class="finput" name="objectName" id="objectName<%=i%>" value="<%=chkHeader.getObjectName()%>"></td>
                <td width="8%"><select name="checkCategory" id="checkCategory<%=i%>" style="width:100%" onChange="do_SetCheckCategory(this)"><%=chkHeader.getCheckCategoryOpt()%></select></td>
                <td width="8%"><input type="text" name="startTime" id="startTime<%=i%>" value="<%=chkHeader.getStartTime()%>" class="finputNoEmpty" style="cursor:pointer;text-align:center" title="点击选择或更改该盘点工单“开始日期”" class="noEmptyInput" readonly onClick="gfPop.fEndPop(referToday<%=i%>, startTime<%=i%>)"></td>
                <td width="8%"><input type="text" name="implementDays" id="implementDays<%=i%>" value="<%=strDays%>" class="finputNoEmpty" style="cursor:pointer" onkeydown="intOnlyOnkeyDown(this.value);" onBlur="do_SetLineDays(this)"></td>
                <td width="8%"><input type="text" name="implementUser" id="implementUser<%=i%>" value="<%=chkHeader.getImplementUser()%>" class="finputNoEmpty" style="cursor:pointer;text-align:left" readonly onclick="do_SelectUser(1, this);" title="点击选择或更改本盘点工单“执行人”"></td>
                <td width="8%"><input type="text" name="archivedUser" id="archivedUser<%=i%>" value="<%=chkHeader.getArchivedUser()%>" class="finputNoEmpty" style="cursor:pointer" readonly onclick="do_SelectUser(2, this);" title="点击选择或更改本盘点工单“归档人”"></td>--%>
                
                <td width="14%" onClick="do_ShowDetail(this)" title="点击查看盘点工单“<%=transNo%>”的详细信息"><input type="text" readonly class="finput" name="transNo" id="transNo<%=i%>" value="<%=chkHeader.getTransNo()%>"></td>
                <td width="8%" onClick="do_ShowDetail(this)" title="点击查看盘点工单“<%=transNo%>”的详细信息"><input type="text" readonly class="finput" name="statusName" id="statusName<%=i%>" value="<%=chkHeader.getStatusName()%>"></td>
                <td width="15%" onClick="do_ShowDetail(this)" title="点击查看盘点工单“<%=transNo%>”的详细信息"><input type="text" readonly class="finput" name="objectCode" id="objectCode<%=i%>" value="<%=chkHeader.getObjectCode()%>" style="color:<%=fontColor%>"></td>
                <td width="20%" onClick="do_ShowDetail(this)" title="点击查看盘点工单“<%=transNo%>”的详细信息"><input type="text" readonly class="finput" name="objectName" id="objectName<%=i%>" value="<%=chkHeader.getObjectName()%>"></td>
                <td width="8%"><input type="text" name="startTime" id="startTime<%=i%>" value="<%=chkHeader.getStartTime()%>" class="finputNoEmpty" style="cursor:pointer;text-align:center" title="点击选择或更改该盘点工单“开始日期”" class="noEmptyInput" readonly onClick="gfPop.fEndPop(referToday<%=i%>, startTime<%=i%>)"></td>
                <td width="8%"><input type="text" name="implementDays" id="implementDays<%=i%>" value="<%=strDays%>" class="finputNoEmpty" style="cursor:pointer" onkeydown="intOnlyOnkeyDown(this.value);" onBlur="do_SetLineDays(this)"></td>
                <td width="8%"><input type="text" name="implementUser" id="implementUser<%=i%>" value="<%=chkHeader.getImplementUser()%>" class="finputNoEmpty" style="cursor:pointer;text-align:left" readonly onclick="do_SelectUser(1, this);" title="点击选择或更改本盘点工单“执行人”"></td>
                <td width="8%"><input type="text" name="archivedUser" id="archivedUser<%=i%>" value="<%=chkHeader.getArchivedUser()%>" class="finputNoEmpty" style="cursor:pointer" readonly onclick="do_SelectUser(2, this);" title="点击选择或更改本盘点工单“归档人”"></td>

                <td width="0%" style="display:none">
                    <input type="hidden" name="headerId" id="headerId<%=i%>" value="<%=chkHeader.getHeaderId()%>">
                    <input type="hidden" name="checkLocation" id="checkLocation<%=i%>" value="<%=chkHeader.getCheckLocation()%>">
                    <input type="hidden" name="implementBy" id="implementBy<%=i%>" value="<%=chkHeader.getImplementBy()%>">
                    <input type="hidden" name="archivedBy" id="archivedBy<%=i%>" value="<%=chkHeader.getArchivedBy()%>">
                    <input type="hidden" name="referToday" id="referToday<%=i%>" value="<%=today%>">
                </td>
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
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script type="text/javascript">
var oldValue = mainFrm.checkDept.value;

function do_DeleteLines() {
    var tab = document.getElementById("dataTable");
    deleteTableRow(tab, 'subCheck');
}

function do_Save_app() {
    document.mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
    document.mainFrm.submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

 function do_AppValidate(postData) {
	var isValid = false;
	var fieldNames = "groupName";
	var fieldLabels = "建单组别";
	var validateType = EMPTY_VALIDATE;
	isValid = formValidate(fieldNames, fieldLabels, validateType);
	if(isValid){
		var rows = dataTable.rows;
		var rowCount = rows.length;
		var firstRow = rows[0];
		if(!postData){
			if((rowCount == 1 && firstRow.style.display == "block") || rowCount > 1){
				fieldNames = "implementDays";
				fieldLabels = "执行周期";
				validateType = POSITIVE_INT_VALIDATE;
				isValid = formValidate(fieldNames, fieldLabels, validateType);
			}
		} else {
			if(rowCount == 1 && firstRow.style.display == "none"){
				if(postData){
					alert("没有选择盘点地点，不能提交！");
					isValid = false;
				}
			} else {
				fieldNames = "startTime;implementDays;implementUser;archivedUser";
				fieldLabels = "开始日期;执行周期;执行人;归档人";
				validateType = EMPTY_VALIDATE;
				isValid = formValidate(fieldNames, fieldLabels, validateType);
				if(isValid){
					fieldNames = "implementDays";
					fieldLabels = "执行周期";
					validateType = INT_VALIDATE;
					isValid = formValidate(fieldNames, fieldLabels, validateType);
				}
			}
		}
	}
	return isValid;
}

function do_ShowDetail(td){
	var tr = td.parentNode;
	var cells = tr.cells;
	var cell = cells[cells.length -1];
	var newasset = cell.childNodes[0];
	var headerId = newasset.value;
	if(headerId == ""){
		alert("本工单尚未生成，不可查看。");
		return;
	}
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

function initPage() {
    window.focus();
	do_SetPageWidth();
    doLoad();
    needAttachMenu = false;
    do_ControlProcedureBtn();
    var groupId = document.mainFrm.groupId.value;
    if (document.getElementById("checkDeptName").value== "") {
        document.mainFrm.groupId.value = document.getElementById("flow_group_id").value;
        document.mainFrm.groupName.value = document.getElementById("flow_group_name").value;
        document.mainFrm.checkDept.value = document.getElementById("app_dept_code").value;
        document.mainFrm.checkDeptName.value = document.getElementById("app_dept_name").value;

    }
}

function do_SelectAppGroup(){
	if(hasUserSelected()){
		if(confirm("改变建单组别会变更工单的执行人和归档人，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮！")){
			clearUsers();
		} else {
			return;
		}
	}
	var groupId = mainFrm.groupId.value;
	var url = "<%=AssetsURLList.GROUP_CHOOSE_SERVLET%>?fromGroup=" + groupId;
	var popscript = "dialogWidth:20;dialogHeight:10;center:yes;status:no;scrollbars:no;help:no";
	var group = window.showModalDialog(url, null, popscript);
	if(group){
		mainFrm.groupId.value = group.fromGroup;
		mainFrm.groupName.value = group.fromGroupName;
	}
}


 function chooseCostCenter() {
    var lookUpName = "<%=LookUpConstant.COST_CENTER%>";
    var dialogWidth = 50.6;
    var dialogHeight = 30;
    var costCenters = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if (costCenters) {
        dto2Frm(costCenters[0], "mainFrm");
    } else {
        document.mainFrm.costCenterCode.value = "";
        document.mainFrm.costCenterName.value = "";
    }
}
function hasUserSelected(){
	var hasSelected = false;
	var users = document.getElementsByName("archivedBy");
	for(var i = 0; i < users.length; i++){
		if(users[i].value != ""){
			hasSelected = true;
			break;
		}
	}
	if(!hasSelected){
		users = document.getElementsByName("implementBy");
		for(var i = 0; i < users.length; i++){
			if(users[i].value != ""){
				hasSelected = true;
				break;
			}
		}
	}
	return hasSelected;
}

function clearUsers(){
	var fieldNames = new Array("implementBy", "implementUser", "archivedBy", "archivedUser");
	for(var i = 0; i < fieldNames.length; i++){
		var users = document.getElementsByName(fieldNames[i]);
		for(var j = 0; j < users.length; j++){
			users[j].value = "";
		}
	}
}

function do_SelectCheckLocation(){
	
	if(mainFrm.taskName.value == ""){
		alert("请先选择盘点任务，再执行本操作。");
		chooseOrderTask();
		return;
	}

	
	if(mainFrm.groupId.value == ""){
		alert("请先选择建单组别，再执行本操作。");
		do_SelectAppGroup();
		return;
	}
	//var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_LOCATION%>";
	var lookUpName = "LOOK_UP_LOCATION_TASK";
	var dialogWidth = 60;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=orgId%>&deptCode=" + mainFrm.checkDept.value;
	userPara += "&costCenterCode=" + mainFrm.costCenterCode.value;
	userPara += "&taskType=" + mainFrm.taskType.value;
	userPara += "&orderNumber=" + mainFrm.taskNumber.value;
	//var locations = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	 var locations = lookUpYearAssetsValues(lookUpName, dialogWidth, dialogHeight,userPara);
	if (locations) {
		var data = null;
		var tab = document.getElementById("dataTable");
		for (var i = 0; i < locations.length; i++) {
			data = locations[i];
			data.transNo = "<%=AssetsWebAttributes.ORDER_AUTO_PROD%>";
			data.statusName = "新增";
			appendDTO2Table(tab, data, false, "checkLocation");
		}
	}
}
/**
 *导入地点信息
 **/
function do_ImportCheckLocation() {
	if(mainFrm.groupId.value == ""){
		alert("请先选择建单组别，再执行本操作。");
		do_SelectAppGroup();
		return;
	}

	if(mainFrm.taskName.value == ""){
		alert("请先选择盘点任务，再执行本操作。");
		chooseOrderTask();
		return;
	}

	if(mainFrm.taskType.value=="ADDRESS-WIRELESS"){
		alert("实地无线不需要导入地点");
		return;
    }

	var popscript = "dialogWidth:60;dialogHeight:30;center:yes;status:no;scrollbars:no;help:no";
	//var url = "/newasset/importCheckLocation.jsp";
	var url = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet?act=ImportLocation?isYear=Y";
	//var locations = window.open(url);
	var locations = window.showModalDialog(url, null, popscript);
	if (locations) {
		var data = null;
		var tab = document.getElementById("dataTable");
		for (var i = 0; i < locations.length; i++) {
			data = locations[i];
			data.transNo = "<%=AssetsWebAttributes.ORDER_AUTO_PROD%>";
			data.statusName = "新增";
			appendDTO2Table(tab, data, false, "checkLocation");
		}
	}
}

/**
  * /////功能：选择执行人、归档人
 */
function do_SelectUser(selCategory, lineBox){
	var lookUpName = "LOOK_UP_USER";
	if(selCategory == 2){
		lookUpName = "LOOK_UP_USER_CHECK_BATCH";
	}
	var dialogWidth = 44;
	var dialogHeight = 29;
	var checkDept=mainFrm.checkDept.value;
	var userPara = "organizationId=<%=orgId%>&groupId=" + mainFrm.groupId.value+"&deptCode="+checkDept;;
	var objs = lookUpYearAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	var textId = lineBox.id;
	var textName = lineBox.name;
	var idNumber = textId.substring(textName.length);
	var checkedProp = false;
	var userIdName = "implementBy";
	var userNameName = "implementUser";
	if(selCategory == 1){
		checkedProp = mainFrm.allImplementUser.checked;
	} else if(selCategory == 2){
		checkedProp = mainFrm.allArchiveUser.checked;
		userIdName = "archivedBy";
		userNameName = "archivedUser";
	}
	if(!checkedProp){
		if (objs) {
			var obj = objs[0];
			document.getElementById(userIdName + idNumber).value = obj["userId"];
			lineBox.value = obj["userName"];
		} else {
			document.getElementById(userIdName + idNumber).value = "";
			lineBox.value = "";
		}
	} else {
		var obj = null;
		var emptyData = false;
		if (objs) {
			obj = objs[0];
		} else {
			emptyData = true;
		}
		var userNames = document.getElementsByName(userNameName);
		var userIds = document.getElementsByName(userIdName);
		var dataCount = userNames.length;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		var checkedCount = getCheckedBoxCount("subCheck");
		for(var i = 0; i < dataCount; i++){
			if(checkedCount > 0){
				row = rows[i];
				checkObj = row.childNodes[0].childNodes[0];
				if(!checkObj.checked){
					continue;
				}
			}
			if(emptyData){
				userNames[i].value = "";
				userIds[i].value = "";
			} else {
				userNames[i].value = obj["userName"];
				userIds[i].value = obj["userId"];
			}
		}
	}
}

function getCalendarPostProcessor(){
    return new CalendarPostProcessor("do_SetLineStartDate");
}

function do_SetLineStartDate(lineBox){
	if(!mainFrm.allStartTime){
		return;
	}
	if(!mainFrm.allStartTime.checked){
		return
	}
	var id = lineBox.id;
	var lineValue = lineBox.value;
	var fields = document.getElementsByName("startTime");
	var dataTable = document.getElementById("dataTable");
	var rows = dataTable.rows;
	var row = null;
	var checkObj = null;
	var checkedCount = getCheckedBoxCount("subCheck");

	for(var i = 0; i < fields.length; i++){
		if(checkedCount > 0){
			row = rows[i];
			checkObj = row.childNodes[0].childNodes[0];
			if(!checkObj.checked){
				continue;
			}
		}
		if(fields[i].id == id){
			continue;
		}
		fields[i].value = lineValue;
	}
}

function do_SetLineDays(lineBox){
	if(!mainFrm.allImplementDays){
		return;
	}
	if(!mainFrm.allImplementDays.checked){
		return
	}
	var id = lineBox.id;
	var lineValue = lineBox.value;
	var fields = document.getElementsByName("implementDays");
	var dataTable = document.getElementById("dataTable");
	var rows = dataTable.rows;
	var row = null;
	var checkObj = null;
	var checkedCount = getCheckedBoxCount("subCheck");
	for(var i = 0; i < fields.length; i++){
		if(checkedCount > 0){
			row = rows[i];
			checkObj = row.childNodes[0].childNodes[0];
			if(!checkObj.checked){
				continue;
			}
		}
		if(fields[i].id == id){
			continue;
		}
		fields[i].value = lineValue;
	}
}

function do_Distribute(){
    mainFrm.act.value = "<%=AssetsActionConstant.DISTRIBUTE_ACTION%>";
    mainFrm.submit();
}

function do_SetCheckCategory(lineBox){
	if(!mainFrm.allCheckCategory.checked){
		return;
	}
	var objs = document.getElementsByName("checkCategory");
	var dataCount = objs.length;
	var selectedVal = lineBox.value;
	if(objs && objs.length){
		var chkObj = null;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		var checkedCount = getCheckedBoxCount("subCheck");
		for(var i = 0; i < dataCount; i++){
			if(checkedCount > 0){
				row = rows[i];
				checkObj = row.childNodes[0].childNodes[0];
				if(!checkObj.checked){
					continue;
				}
			}
			chkObj = objs[i];
			selectSpecialOptionByItem(chkObj, selectedVal);
		}
	}
}
function do_Complete_app_yy() {
    var actObj = document.getElementById("act");
    actObj.value = "SUBMIT_ACTION";
    document.forms[0].submit();
}

function do_ViewAssetsData(){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    var para = "";
    var effectiveTime = 0;
    for(var i = 0; i < rows.length; i++){
        var row = rows[i];
        var checkObj = row.childNodes[0].childNodes[0];
        if(!checkObj.checked){
            continue;
        }
        var checkLocation = getTrNode(row, "checkLocation");
        var checkCategory = getTrNode(row, "checkCategory");
        if(effectiveTime > 0){
            para += "$";
        }
        para += checkLocation.value;
        para += "_" + checkCategory.value;
        effectiveTime++;
    }
    if (para == "") {
        alert("请先选要查看资产明细的地点！");
        return;
    }
    var actionURL = "/servlet/com.sino.ams.newasset.servlet.ObjectAssetsServlet";
    actionURL += "?checkLocation=" + para;
    window.open(actionURL, "objectAssetsWin", "fullscreen=yes");
}

function intOnlyOnkeyDown(obj) {
    var k = window.event.keyCode;
    if ((k >= 48 && k <= 57) || (k >= 96 && k <= 105) || k == 8) {

    } else {
        window.event.returnValue = false;
    }
}

//查询盘点任务列表
function chooseOrderTask() {
    var lookUpName = "LOOK_UP_ORDER_TASK";
    var dialogWidth = 50.6;
    var dialogHeight = 30;
    var userPara = "organizationId=<%=orgId%>&deptCode=" + mainFrm.checkDept.value+"&groupId"+mainFrm.groupId.value;
    var costCenters = lookUpYearAssetsValues(lookUpName, dialogWidth, dialogHeight,userPara);
    if (costCenters) {
        dto2Frm(costCenters[0], "mainFrm");
    } else {
        document.mainFrm.taskNumber.value = "";
        document.mainFrm.taskName.value = "";
        document.mainFrm.taskType.value = "";
    }
}
</script>
