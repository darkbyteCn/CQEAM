<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/sampling/headerInclude.jsp"%>
<%@ include file="/sampling/headerInclude.htm"%>
<html>
<head>
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<!-- onload="do_SetPageWidth()"  id="pageNaviDiv" -->
</head>
<title>抽查任务查询</title>

<body topmargin="0" leftmargin="0" onload="do_SetPageWidth();" onKeyUp="autoExeFunction('do_SearchTask()')">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>

<%
	AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    DTOSet tasks = (DTOSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (tasks != null && !tasks.isEmpty());
	String pageTitle = "资产抽查管理>>抽查任务查询";
%>
<form name="mainFrm" action="<%=SamplingURLs.TASK_SEARCH_SERVLET%>" method="post">
<script>
    printTitleBar("<%=pageTitle%>")
</script>

<table border="0" class="queryHeadBg" width="100%" style="TABLE-LAYOUT:fixed;word-break:break-all">
	<tr>
		<td width="10%" height="22" align="right">抽查公司：</td>
		<td width="13%" height="22"><select class="select_style1" name="sampledOu" style="width:100%"><%=dto.getSampledOuOpt()%></select></td>
		<td width="10%" height="22" align="right">任务状态：</td>
		<td width="13%" height="22"><select class="select_style1" name="taskStatus" style="width:100%"><%=dto.getTaskStatusOpt()%></select></td>
		<td width="10%" height="22" align="right">任务编号：</td>
		<td width="13%" height="22"><input class="input_style1" type="text" style="width:100%" name="taskNo" value="<%=dto.getTaskNo()%>"></td>
		<td width="10%" height="22" align="right">任务名称：</td>
		<td width="13%" height="22"><input class="input_style1" type="text" style="width:100%" name="taskName" value="<%=dto.getTaskName()%>"></td>
		<td width="8%" height="22"><img src="/images/eam_images/search.jpg" title="点击查询" onclick="do_SearchTask();"></td>
	</tr>
	<tr>
		<td width="10%" height="22" align="right">创建日期：</td>
		<td width="13%" height="22"><input class="input_style1" type="text" style="width:100%;cursor:hand" name="startDate" value="<%=dto.getStartDate()%>" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(startDate, endDate)"></td>
		<td width="10%" height="22" align="right">到：</td>
		<td width="13%" height="22" align="right"><input class="input_style1" type="text" style="width:100%;cursor:hand" name="endDate" value="<%=dto.getEndDate()%>" title="点击选择截止日期" readonly onclick="gfPop.fEndPop(startDate, endDate)"></td>
		<td width="10%" height="22" align="right">截至日期：</td>
		<td width="13%" height="22"><input class="input_style1" type="text" style="width:100%;cursor:hand" name="fromDate" value="<%=dto.getFromDate()%>" title="点击选择开始日期" readonly onclick="gfPop.fStartPop(fromDate, toDate)"></td>
		<td width="10%" height="22" align="right">到：</td>
		<td width="13%" height="22" align="right"><input class="input_style1" type="text" style="width:100%;cursor:hand" name="toDate" value="<%=dto.getToDate()%>" title="点击选择截止日期" readonly onclick="gfPop.fEndPop(fromDate, toDate)"></td>
		<td width="8%" height="22"><img src="/images/eam_images/export.jpg" title="点击导出" onclick="do_ExportTask();"></td>
	</tr>
</table>
	<input type="hidden" name="act">
</form>
<div id="headDiv" style="overflow-y:scroll;overflow-x:hidden;position:absolute;top:65px;left:0px;width:100%" class="crystalScroll">
	<table class="eamHeaderTable" border="1" width="120%" style="text-align:center">
		<tr onClick="executeClick(this)" style="cursor:hand" title="点击选择全部或取消选择">
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
<div style="overflow:scroll;height:300px;width:100%;position:absolute;top:89px;left:0px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="120%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
		int dataCount = tasks.getSize();
		AmsAssetsSamplingTaskDTO task = null;
		for(int i = 0; i < dataCount; i++){
			task = (AmsAssetsSamplingTaskDTO)tasks.getDTO(i);
            int samplingRatio = task.getSamplingRatio();
%>
		<tr title="点击查看地点信息及其资产信息" onclick="do_ShowDetail('<%=task.getTaskId()%>')">
			<td width="18%" height="22"><input class="finput2" readonly value="<%=task.getTaskNo()%>"></td>
			<td width="20%" height="22"><input class="finput" readonly value="<%=task.getTaskName()%>"></td>
			<td width="8%" height="22"><input class="finput2" readonly value="<%=task.getStartDate()%>"></td>
			<td width="8%" height="22"><input class="finput2" readonly value="<%=task.getEndDate()%>"></td>
			<td width="8%" height="22"><input class="finput3" readonly value="<%=samplingRatio%>"></td>
			<td width="8%" height="22"><input class="finput" readonly value="<%=task.getTaskStatusValue()%>"></td>
			<td width="8%" height="22"><input class="finput2" readonly value="<%=task.getCreationDate()%>"></td>
		</tr>
<%
		}
%>		
	</table>
</div>	
<div  id="pageNaviDiv" style="position:absolute;top:410px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>

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
	var url = "<%=SamplingURLs.TASK_SERVLET%>?act=<%=SamplingActions.DETAIL_ACTION%>&taskId=" + taskId + "&seachType=Y";
	var style = "width=650,height=500,top=180,left=180,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,task=no,status=no";
	window.open(url, 'taskWin', style);
}
</script>
