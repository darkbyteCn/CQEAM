<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/sampling/headerInclude.jsp"%>
<%@ include file="/sampling/headerInclude.htm"%>
<html>
<head>
<title>资产地点查询</title>
</head>
<body topmargin="0" leftmargin="0" onload="do_SetPageWidth();" onkeydown="autoExeFunction('do_SearchTask()')">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>

<%
	AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    DTOSet tasks = (DTOSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (tasks != null && !tasks.isEmpty());
	String pageTitle = "资产抽查管理>>抽查任务维护";
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
%>
<form name="mainFrm" action="<%=SamplingURLs.TASK_SERVLET%>" method="post">
<script>
    printTitleBar("<%=pageTitle%>")
</script>

<table border="0" class="queryTable" width="100%" style="TABLE-LAYOUT:fixed;word-break:break-all">
	<tr>
		<td width="10%" height="22" align="right">抽查公司：</td>
		<td width="15%" height="22"><select class="select_style1" name="sampledOu" style="width:100%"><%=dto.getSampledOuOpt()%></select></td>
		<td width="10%" height="22" align="right">任务状态：</td>
		<td width="15%" height="22"><select class="select_style1" name="taskStatus" style="width:100%"><%=dto.getTaskStatusOpt()%></select></td>
		<td width="10%" height="22" align="right">任务编号：</td>
		<td width="15%" height="22"><input class="input_style1" type="text" style="width:100%" name="taskNo" value="<%=dto.getTaskNo()%>"></td>
		<td width="10%" height="22" align="right">任务名称：</td>
		<td width="15%" height="22"><input class="input_style1" type="text" style="width:100%" name="taskName" value="<%=dto.getTaskName()%>"></td>
	</tr>
	<tr>
		<td width="10%" height="22" align="right">创建日期：</td>
		<td width="15%" height="22"><input class="input_style1" type="text" style="width:100%;cursor:hand" name="startDate" value="<%=dto.getStartDate()%>" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(startDate, endDate)"></td>
		<td width="10%" height="22" align="right">到：</td>
		<td width="15%" height="22" align="right"><input class="input_style1" type="text" style="width:100%;cursor:hand" name="endDate" value="<%=dto.getEndDate()%>" title="点击选择截止日期" readonly onclick="gfPop.fEndPop(startDate, endDate)"></td>
		<td width="10%" height="22" align="right">截至日期：</td>
		<td width="15%" height="22"><input class="input_style1" type="text" style="width:100%;cursor:hand" name="fromDate" value="<%=dto.getFromDate()%>" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(fromDate, toDate)"></td>
		<td width="10%" height="22" align="right">到：</td>
		<td width="15%" height="22" align="right"><input class="input_style1" type="text" style="width:100%;cursor:hand" name="toDate" value="<%=dto.getToDate()%>" title="点击选择截止日期" readonly onclick="gfPop.fEndPop(fromDate, toDate)"></td>
	</tr>
</table>
	<input type="hidden" name="act">
</form>
<fieldset style="border:0px; position:absolute;top:66px;width:100%;height:370">
    <legend>
        <img src="/images/eam_images/search.jpg" title="点击查询" onclick="do_SearchTask();">
		<img src="/images/eam_images/export.jpg" title="点击导出" onclick="do_ExportTask();">
        <img src="/images/eam_images/new_add.jpg" title="点击新增" onclick="do_CreateTask();">
        <img src="/images/eam_images/openTask.jpg" title="打开任务" onclick="do_OpenTask();">
        <img src="/images/eam_images/closeTask.jpg" title="关闭任务" onclick="do_CloseTask();">
		<img src="/images/eam_images/publishTask.jpg" title="发布任务" onClick="do_PublishTask()">
		<img src="/images/eam_images/cancel.jpg" title="取消任务" onClick="do_CancelTask()">
	</legend>
    <div id="aa" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:20px;left:0px;width:100%" class="crystalScroll">
	<table class="eamHeaderTable" border="1" width="120%" style="text-align:center">
		<tr onClick="executeClick(this)" style="cursor:hand" title="点击选择全部或取消选择">
			<td width="3%" height="22"><input type="checkbox" name="titleCheck" onPropertyChange="checkAll('titleCheck', 'subCheck')"></td>
			<td width="18%" height="22">任务编号</td>
			<td width="20%" height="22">任务名称</td>
			<td width="8%" height="22">起始日期</td>
			<td width="8%" height="22">截至日期</td>
			<td width="8%" height="22">抽查百分比(%)</td>
			<td width="8%" height="22">任务状态</td>
			<td width="8%" height="22">创建日期</td>
		</tr>
	</table>
</div>
<%
	if(hasData){
%>
<div style="overflow:scroll;height:300px;width:100%;position:absolute;top:44px;left:0px" align="left" onscroll="document.getElementById('aa').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		int dataCount = tasks.getSize();
		AmsAssetsSamplingTaskDTO task = null;
		for(int i = 0; i < dataCount; i++){
			task = (AmsAssetsSamplingTaskDTO)tasks.getDTO(i);
%>
		<tr title="点击查看地点信息及其资产信息">
			<td width="3%" align="center"><%=task.getCheckBoxProp()%></td>
			<td width="18%" height="22" onclick="do_ShowDetail('<%=task.getTaskId()%>')"><input class="finput2" readonly value="<%=task.getTaskNo()%>"></td>
			<td width="20%" height="22" onclick="do_ShowDetail('<%=task.getTaskId()%>')"><input class="finput" readonly value="<%=task.getTaskName()%>"></td>
			<td width="8%" height="22" onclick="do_ShowDetail('<%=task.getTaskId()%>')"><input class="finput2" readonly value="<%=task.getStartDate()%>"></td>
			<td width="8%" height="22" onclick="do_ShowDetail('<%=task.getTaskId()%>')"><input class="finput2" readonly value="<%=task.getEndDate()%>"></td>
			<td width="8%" height="22" onclick="do_ShowDetail('<%=task.getTaskId()%>')"><input class="finput3" readonly value="<%=task.getSamplingRatio()%>"></td>
			<td width="8%" height="22" onclick="do_ShowDetail('<%=task.getTaskId()%>')"><input class="finput" readonly value="<%=task.getTaskStatusValue()%>"></td>
			<td width="8%" height="22" onclick="do_ShowDetail('<%=task.getTaskId()%>')"><input class="finput2" readonly value="<%=task.getCreationDate()%>"></td>
		</tr>
<%
		}
%>
	</table>
</div>
<div style="position:absolute;top:350px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
</fieldset>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>

</body>
</html>
<script>
function do_SearchTask(){
	mainFrm.act.value = "<%=SamplingActions.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_ExportTask(){
	mainFrm.act.value = "<%=SamplingActions.EXPORT_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	event.srcElement.disabled = true;
}

function do_ShowDetail(taskId){
	var url = "<%=SamplingURLs.TASK_SERVLET%>?act=<%=SamplingActions.DETAIL_ACTION%>&taskId=" + taskId;
<%
	if(userAccount.isProvinceUser()){
%>
	var style = "width=750,height=500,top=100,left=125,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,task=no,status=no";
<%
	} else {
%>
	var style = "width=750,height=350,top=150,left=125,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,task=no,status=no";
<%
	}
%>
	window.open(url, 'taskWin', style);
}

function do_CreateTask(){
<%
	if(userAccount.isProvinceUser()){
%>
	var style = "width=750,height=500,top=100,left=125,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,task=no,status=no";
<%
	} else {
%>
	var style = "width=750,height=350,top=150,left=125,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,task=no,status=no";
<%
	}
%>
	window.open("/public/waiting2.htm", "taskWin", style);
	mainFrm.act.value = "<%=SamplingActions.NEW_ACTION%>";
    mainFrm.target = "taskWin";
    mainFrm.submit();
}

function do_CloseTask(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("请先执行查询后再执行本操作");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("请先选择相应任务后再执行本操作");
		return;
	}
	if(confirm("系统将关闭开放中的任务，并过滤掉你无权处理的部分。任务关闭后不可再创建抽查工单，确认关闭吗？确认请点击“确定”按钮，否则请点击“取消”按钮")){
		mainFrm.act.value = "<%=SamplingActions.CLOSE_SELECTED_TASK%>";
		mainFrm.submit();
	}
}

function do_OpenTask(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("请先执行查询后再执行本操作");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("请先选择相应任务后再执行本操作");
		return;
	}
	
	if (mainFrm.taskStatus.value == "OPENING") {
		alert("已是开放中任务");
		return;
	}
	var dataTable = document.getElementById("dataTable");
	var rows = dataTable.rows;
	var row = null;
	var cells = null;
	var cell = null;
	var checkObj = null;
	var checkedCount = getCheckedBoxCount("subCheck");
	for(var i = 0; i < rows.length; i++){
		if(checkedCount > 0){
			row = rows[i];
			cells = row.cells;
			checkObj = row.childNodes[0].childNodes[0];
			if(!checkObj.checked){
				continue;
			} else {
				cell = cells[6].childNodes[0].value;
				if (cell == "开放中") {
					alert("选中内容已是开放中任务");
					return;
				}
			}
		}
	}
	
	if(confirm("系统将打开已关闭的任务，并过滤掉你无权处理的部分。任务打开后可在其下继续创建抽查工单，确认打开吗？确认请点击“确定”按钮，否则请点击“取消”按钮")){
		mainFrm.act.value = "<%=SamplingActions.OPEN_SELECTED_TASK%>";
		mainFrm.submit();
	}
}


function do_PublishTask(){
    if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("请先执行查询后再执行本操作");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("请先选择相应任务后再执行本操作");
		return;
	}
    do_verify();

//    if(confirm("系统将发布暂存的任务，并过滤掉你无权处理的部分。任务发布后可在其下创建抽查工单，确认发布吗？确认请点击“确定”按钮，否则请点击“取消”按钮")){
		<%--mainFrm.act.value = "<%=SamplingActions.PUBLISH_SELECTED_TASK%>";--%>
//		mainFrm.submit();
//	}
}

function do_CancelTask(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("请先执行查询后再执行本操作");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("请先选择相应任务后再执行本操作");
		return;
	}
	if(confirm("系统将取消暂存的任务，并过滤掉你无权处理的部分。任务取消后将不可进行任何操作，确认取消吗？确认请点击“确定”按钮，否则请点击“取消”按钮")){
		mainFrm.act.value = "<%=SamplingActions.CANCEL_SELECTED_TASK%>";
		mainFrm.submit();
	}
}

var xmlHttp;
function do_verify() {
    var url = "";
    createXMLHttpRequest();
    var selectTaskId = getCheckedBoxValue("subCheck");
    url = "/servlet/com.sino.ams.sampling.servlet.AmsAssetsSamplingTaskServlet?act=VERIFY&selectTaskId=" + selectTaskId;
    xmlHttp.onreadystatechange = handleReadyStateChange;
    xmlHttp.open("post", url, true);
    xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xmlHttp.send(null);
}

function createXMLHttpRequest() {     //创建XMLHttpRequest对象
    try {
        xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
    } catch(e) {
        try {
            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
        } catch(e) {
            try {
                xmlHttp = new XMLHttpRequest();
            } catch(e) {
                alert("创建XMLHttpRequest对象失败！");
            }
        }
    }
}

function handleReadyStateChange() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            if (xmlHttp.responseText == 'Y') {
                match();
            } else {
                alert("选择的任务中抽查公司不能为空，不能发布任务！");
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}

function match() {
    if(confirm("系统将发布暂存的任务，并过滤掉你无权处理的部分。任务发布后可在其下创建抽查工单，确认发布吗？确认请点击“确定”按钮，否则请点击“取消”按钮")){
		mainFrm.act.value = "<%=SamplingActions.PUBLISH_SELECTED_TASK%>";
		mainFrm.submit();
	}
}
</script>
