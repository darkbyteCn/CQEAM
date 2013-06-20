<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/sampling/headerInclude.jsp"%>
<%@ include file="/sampling/headerInclude.htm"%>
<html>
<head>
<title>抽查工单创建页面</title>
</head>
<body topmargin="0" leftmargin="0" onload="do_initPage()">
<jsp:include page="/message/MessageProcess"/>

<%
	AmsAssetsSamplingBatchDTO dto = (AmsAssetsSamplingBatchDTO)request.getAttribute(SamplingWebAttributes.BATCH_DTO);
	DTOSet orderHeaders = (DTOSet)request.getAttribute(SamplingWebAttributes.ORDER_HEADERS);
	boolean hasOrder = (orderHeaders != null && !orderHeaders.isEmpty());
	String taskId = dto.getTaskId();
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	String pageTitle = "工单维护页面";
%>
<form name="mainFrm" method="post">
<script>
    printTitleBar("<%=pageTitle%>")
</script>
	<table border="1" bordercolor="#226E9B" class="detailHeader" width="100%" style="border-collapse: collapse" id="table1">
		<tr height="22">
			<td width="10%" align="right">任务编号：</td>
			<td width="23%"><input class="input_style2" type="text" name="taskNo" style="width:100%" class="finput" readonly value="<%=dto.getTaskNo()%>"></td>
			<td width="10%" align="right">任务名称：</td>
			<td width="23%"><input class="input_style2" type="text" name="taskName" style="width:100%" class="finput" readonly value="<%=dto.getTaskName()%>"></td>
			<td width="10%" align="right">任务状态：</td>
			<td width="23%"><input class="input_style2" type="text" name="taskStatusValue" style="width:100%" class="finput" readonly value="<%=dto.getTaskStatusValue()%>"></td>
		</tr>
		<tr height="22">
			<td width="10%" align="right">抽查类别：</td>
			<td width="23%"><input class="input_style2" name="samplingTypeValue" style="width:100%" class="finput" readonly value="<%=dto.getSamplingTypeValue()%>"></td>
			<td width="10%" align="right">抽查百分比：</td>
			<td width="23%"><input class="input_style2" type="text" name="samplingRatio" style="width:100%" class="finput" readonly value="<%=dto.getSamplingRatio()%>"></td>
			<td width="10%" align="right">创建公司：</td>
			<td width="23%"><input class="input_style2" name="createdOuName" style="width:100%" class="finput" readonly value="<%=dto.getCreatedOuName()%>"></td>
		</tr>
		<tr height="22">
			<td width="10%" align="right">开始日期：</td>
			<td width="23%"><input class="input_style2" type="text" style="width:100%" name="startDate" class="finput" readonly value="<%=dto.getStartDate()%>"></td>
			<td width="10%" align="right">截止日期：</td>
			<td width="23%"><input class="input_style2" type="text" style="width:100%" name="endDate" class="finput" readonly value="<%=dto.getEndDate()%>"></td>
			<td width="10%" align="right">抽查公司：</td>
			<td width="23%"><input class="input_style2" name="sampledOuName" style="width:100%" readonly value="<%=dto.getSampledOuName()%>"></td>
		</tr>
		<tr height="22">
			<td width="10%" align="right" height="40">任务描述：</td>
			<td width="23%" height="40"><textarea class="input_style2" style="width:100%;height:100%" name="taskDesc" readonly rows="1" cols="20"><%=dto.getTaskDesc()%></textarea></td>
			<td width="10%" align="right">工单批号：</td>
			<td width="23%"><input class="input_style2" type="text" name="batchNo" style="width:100%;height:100%" readonly value="<%=dto.getBatchNo()%>"></td>
			<td width="10%" height="40" align="right">工单批备注：</td>
			<td width="23%" height="40"><textarea class="input_style2" style="width:100%;height:100%" name="batchRemark" readonly rows="1" cols="20"><%=dto.getBatchRemark()%></textarea></td>
		</tr>
	</table>

<fieldset style="border:1px solid #226E9B; position:absolute;top:138;width:100%;height:560px">
    <legend>
        <img src="/images/eam_images/tmp_save.jpg" alt="暂存工单" onClick="do_SaveOrder(); return false;">
        <img src="/images/eam_images/distriOrder.jpg" alt="下发工单" onClick="do_DistributeOrder(); return false;">
        <img src="/images/eam_images/choose.jpg" alt="选择地点" onClick="do_SelectLocation(); return false;">
        <img src="/images/eam_images/delete_line.jpg" alt="删除工单" onClick="do_DeleteOrders(); return false;">
        <img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="do_Close()">
		统一设置：
		<input type="checkbox" name="allImplementUser" id="allImplementUser"><label for="allImplementUser">执行人</label>
		<input type="checkbox" name="allStartTime" id="allStartTime"><label for="allStartTime">开始日期</label>
		<input type="checkbox" name="allImplementDays" id="allImplementDays"><label for="allImplementDays">执行周期</label>
    </legend>
    <div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:25px;left:0px;width:100%" class="crystalScroll">
    <table class=headerTable border=1 style="width:100%">
        <tr height="20px" onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
            <td align=center width="4%"><input type="checkbox" name="mainCheck" value="" onPropertyChange="checkAll('mainCheck','subCheck')"></td>
            <td align=center width="12%">工单编号</td>
            <td align=center width="10%">工单状态</td>
            <td align=center width="12%">地点代码</td>
            <td align=center width="32%">地点名称</td>
            <td align=center width="10%">开始日期</td>
            <td align=center width="10%">执行周期</td>
            <td align=center width="10%">执行人</td>
            <td style="display:none">隐藏域所在列</td>
        </tr>
    </table>
	</div>
	<div style="overflow:scroll;height:90%;width:100%;position:absolute;top:48px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(!hasOrder){
%>	
            <tr class="dataTR" style="display:none" onClick="executeClick(this)" style="cursor:hand">
                <td width="4%" align="center"><input type="checkbox" name="subCheck" id="subCheck0"></td>
                <td width="12%"><input type="text" readonly class="finput" name="orderNo" id="orderNo0"></td>
                <td width="10%"><input type="text" readonly class="finput" name="orderStatusValue" id="orderStatusValue0"></td>
                <td width="12%"><input type="text" readonly class="finput" name="samplingLocationCode" id="samplingLocationCode0"></td>
                <td width="32%"><input type="text" readonly class="finput" name="samplingLocationName" id="samplingLocationName0"></td>
                <td width="10%"><input type="text" name="startTime" id="startTime0" class="finputNoEmpty" style="cursor:hand;text-align:center" readonly value="" onclick="gfPop.fPopCalendar(startTime0)" title="点击选择或更改该抽查工单“开始日期”" onBlur="do_SetLineStartDate(this)"></td>
                <td width="10%"><input type="text" name="implementDays" id="implementDays0" class="finputNoEmpty" style="cursor:hand" value="" onBlur="do_SetLineDays(this)"></td>
                <td width="10%"><input type="text" name="implementUser" id="implementUser0" class="finputNoEmpty" style="cursor:hand;text-align:left" readonly value="" onclick="do_SelectUser(this);" title="点击选择或更改本抽查工单“执行人”"></td>
                <td style="display:none">
                    <input type="hidden" name="headerId" id="headerId0">
                    <input type="hidden" name="samplingLocation" id="samplingLocation0">
                    <input type="hidden" name="implementBy" id="implementBy0">
                </td>
            </tr>
<%
	} else {
		int orderCount = orderHeaders.getSize();
		AmsAssetsSamplingHeaderDTO orderHeader = null;
		for(int i = 0; i < orderCount; i++){
			orderHeader = (AmsAssetsSamplingHeaderDTO)orderHeaders.getDTO(i);
%>
            <tr class="dataTR" onClick="executeClick(this)" style="cursor:hand">
                <td width="4%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>" value="<%=orderHeader.getHeaderId()%>"></td>
                <td width="12%"><input type="text" readonly class="finput" name="orderNo" id="orderNo<%=i%>" value="<%=orderHeader.getOrderNo()%>"></td>
                <td width="10%"><input type="text" readonly class="finput" name="orderStatusValue" id="orderStatusValue<%=i%>" value="<%=orderHeader.getOrderStatusValue()%>"></td>
                <td width="12%"><input type="text" readonly class="finput" name="samplingLocationCode" id="samplingLocationCode<%=i%>" value="<%=orderHeader.getSamplingLocationCode()%>"></td>
                <td width="32%"><input type="text" readonly class="finput" name="samplingLocationName" id="samplingLocationName<%=i%>" value="<%=orderHeader.getSamplingLocationName()%>"></td>
                <td width="10%"><input type="text" name="startTime" id="startTime<%=i%>" class="finputNoEmpty" style="cursor:hand;text-align:center" readonly onclick="gfPop.fPopCalendar(startTime0)" title="点击选择或更改该抽查工单“开始日期”" onBlur="do_SetLineStartDate(this)" value="<%=orderHeader.getStartTime()%>"></td>
                <td width="10%"><input type="text" name="implementDays" id="implementDays<%=i%>" class="finputNoEmpty" style="cursor:hand" onBlur="do_SetLineDays(this)" value="<%=orderHeader.getImplementDays()%>"></td>
                <td width="10%"><input type="text" name="implementUser" id="implementUser<%=i%>" class="finputNoEmpty" style="cursor:hand;text-align:left" readonly onclick="do_SelectUser(this);" title="点击选择或更改本抽查工单“执行人”" value="<%=orderHeader.getImplementUser()%>"></td>
                <td style="display:none">
                    <input type="hidden" name="headerId" id="headerId<%=i%>" value="<%=orderHeader.getHeaderId()%>">
                    <input type="hidden" name="samplingLocation" id="samplingLocation<%=i%>" value="<%=orderHeader.getSamplingLocation()%>">
                    <input type="hidden" name="implementBy" id="implementBy<%=i%>" value="<%=orderHeader.getImplementBy()%>">
                </td>
            </tr>
<%
		}
	}
%>
        </table>
    </div>
	<input type="hidden" name="act">
	<input type="hidden" name="taskId" value="<%=taskId%>">
	<input type="hidden" name="batchId" value="<%=dto.getBatchId()%>">
	<input type="hidden" name="sampledOu" value="<%=dto.getSampledOu()%>">
	<input type="hidden" name="createdOu" value="<%=dto.getCreatedOu()%>">
</fieldset>	
</form>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<%=SamplingWebAttributes.WEB_WAIT_TIP%>
</body>
</html>
<script type="text/javascript">
function do_initPage(){
	window.focus();
	do_SetPageWidth();
}


function do_SelectLocation(){
	var lookUpName = "<%=SamplingLookUpConstant.LOOK_SAMPLING_LOC%>";
    var dialogWidth = 60;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=dto.getSampledOu()%>";
    var locations = lookUpSamplingValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (locations) {
		var data = null;
		var tab = document.getElementById("dataTable");
		for (var i = 0; i < locations.length; i++) {
			data = locations[i];
			data.orderNo = "<%=SamplingWebAttributes.ORDER_AUTO_PROD%>";
			data.orderStatusValue = "新增";
			appendDTO2Table(tab, data, false, "samplingLocation");
		}
	}
}


/**
  * 功能：选择执行人
 */
function do_SelectUser(lineBox){
	var lookUpName = "LOOK_UP_USER";
	var dialogWidth = 44;
	var dialogHeight = 29;
	var userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	var textId = lineBox.id;
	var textName = lineBox.name;
	var idNumber = textId.substring(textName.length);
	var checkedProp = mainFrm.allImplementUser.checked;
	var userIdName = "implementBy";
	var userNameName = "implementUser";
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


function do_SaveOrder() {
	mainFrm.action = "<%=SamplingURLs.BATCH_SERVLET%>";
	mainFrm.act.value = "<%=SamplingActions.SAVE_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}


function do_DistributeOrder(){
	if(do_Validate()){
		mainFrm.action = "<%=SamplingURLs.BATCH_SERVLET%>";
	    mainFrm.act.value = "<%=SamplingActions.DISTRIBUTE_ORDER%>";
    	mainFrm.submit();
		document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
	}
}

function do_DeleteOrders() {
	var dataTable = document.getElementById("dataTable");
	deleteTableRow(dataTable, "subCheck");
}


function do_Validate() {
	var isValid = true;
	var rows = dataTable.rows;
	var rowCount = rows.length;
	var firstRow = rows[0];
	if(rowCount == 1 && firstRow.style.display == "none"){
		alert("没有创建工单，不能下发。");
		isValid = false;
	} else {
		var fieldNames = "startTime;implementDays;implementUser";
		var fieldLabels = "开始日期;执行周期;执行人";
		var validateType = EMPTY_VALIDATE;
		isValid = formValidate(fieldNames, fieldLabels, validateType);
		if(isValid){
			fieldNames = "implementDays";
			fieldLabels = "执行周期";
			validateType = POSITIVE_INT_VALIDATE;
			isValid = formValidate(fieldNames, fieldLabels, validateType);
		}
	}
	return isValid;
}

</script>
