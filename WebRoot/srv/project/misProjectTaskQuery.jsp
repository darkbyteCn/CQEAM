<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="srv.ams.projecttask.dto.SrvTaskinfoDTO" %>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>MIS项目任务查询</title>
</head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	SrvTaskinfoDTO dto = (SrvTaskinfoDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	String pageTitle = request.getParameter("pageTitle");
	if(pageTitle == null){
		pageTitle = "MIS系统接口-->>MIS项目任务查询";
	}
%>
<form name="mainFrm" method="post" action="/servlet/srv.ams.projecttask.servlet.MisProjectTaskQueryServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>")
</script>
	<table border="0" width="100%" bgcolor="#EFEFEF">
	  <input type="hidden" name="orgName"  style="width:100%">
		<tr height="20">
			<td align="right" width="8%">OU名称：</td>
         	<td width="15%">
         		<select name="orgId" id="orgId" style="width:100%" size="1"><%=dto.getOuOption()%></select>
			</td>
			<td align="right" width="10%">项目编号：</td>
            <td align="left" width="12%"><input type="text" name="segment1" id="segment1" value="<%=dto.getSegment1()%>" style="width:100%"></td>
			<td align="right" width="10%">任务编号：</td>
            <td align="left" width="12%"><input type="text" name="taskNumber" value="<%=dto.getTaskNumber()%>" style="width:100%"></td>
		</tr>
		<tr height="20">
			<td align="right" width="10%">任务名称：</td>
            <td align="left" width="12%"><input type="text" name="taskName" value="<%=dto.getTaskName()%>" style="width:100%"></td>
            
			<td width="8%" align="right">更新日期：</td>
			<td width="15%">
				<input type="text" name="startLastUpdatDate" value="<%=dto.getStartLastUpdatDate()%>" style="width:100%" title="点击选择开始日期" readonly class="readonlyInput"  onclick="gfPop.fStartPop(startLastUpdatDate, endLastUpdatDate)" >
			</td>
			<td width="8%" align="right">到：</td>
			<td width="15%" align="left">
				<input type="text" name="endLastUpdatDate" value="<%=dto.getEndLastUpdatDate()%>" style="width:100%" title="点击选择截止日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startLastUpdatDate, endLastUpdatDate)" >
			</td>
			<td width="4%">
				<img src="/images/eam_images/search.jpg" alt="查询" onClick="do_Search(); return false;"></td>
			<td width="4%">
				<img src="/images/eam_images/export.jpg" alt="导出EXCEL" onclick="do_Export()">
			</td>
		</tr>
	</table>
	<input readonly name="act" type="hidden">
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:840px">
	<table class="headerTable" border="1" width="150%">
		<tr height="22">
			<td width="3%" align="center">组织名称</td>
			<td width="3%" align="center">项目编号</td>
			<td width="2%" align="center">任务编号</td>
			<td width="3%" align="center">任务名称</td>
			<td width="3%" align="center">任务经理</td>
			
			<td width="2%" align="center">部门</td>
			<td width="3%" align="center">地点</td>
			<td width="2%" align="center">父级任务编号</td>
			<td width="3%" align="center">描述</td>
			<td width="2%" align="center">任务起始日期</td>
			
			<td width="2%" align="center">任务完成日期</td>
			<td width="2%" align="center">创建日期</td>
			<td width="2%" align="center">更新日期</td>

		</tr>
	</table>
</div>		
<div id="dataDiv" style="overflow:scroll;height:70%;width:857px;position:absolute;top:94px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="150%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>	
		<tr height="22">
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("ORG_NAME")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("SEGMENT1")%>"></td>
			<td width="2%" align="center"><input class="finput" readonly value="<%=row.getValue("TASK_NUMBER")%>"></td>
			<td width="3%" align="center"><input class="finput" readonly value="<%=row.getValue("TASK_NAME")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("TASK_MANAGER")%>"></td>
			
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("ATTRIBUTE1")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("ATTRIBUTE2")%>"></td>
			<td width="2%" align="center"><input class="finput" readonly value="<%=row.getValue("PARENT_TASK_NUM")%>"></td>
			<td width="3%" align="center"><input class="finput" readonly value="<%=row.getValue("DESCRIPTION")%>"></td>
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("START_DATE")%>"></td>
			
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("COMPLETION_DATE")%>"></td>
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("CREATION_DATE")%>"></td>
			<td width="2%" align="center"><input class="finput2" readonly value="<%=row.getValue("LAST_UPDATE_DATE")%>"></td>

		</tr>
<%
		}
	}
%>	
	</table>
</div>
<%
	if(hasData){
%>
<div style="position:absolute;top:475px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script>
function do_Search(){
	var dc=document.getElementById("orgId");
	document.mainFrm.orgName.value=dc.options[dc.selectedIndex].innerText;
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
/*
	if(!document.$$$WebGridSystemFrm$$$){
		alert("请先执行查询，再导出");
		return;
	}
	var totalRecord = Number($$$WebGridSystemFrm$$$.$$$WebGridTotalRecord$$$.value);
	if(totalRecord > 5000){
		alert("当前条件下记录数过多，请输入相应条件精简记录数后再导出");
		return;
	}
*/
	var dc=document.getElementById("orgId");
	document.mainFrm.orgName.value=dc.options[dc.selectedIndex].innerText;
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}

function do_SelectAddress(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_MISLOC%>";
	var dialogWidth = 48;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		mainFrm.assetsLocationCode.value = obj["assetsLocationCode"];
	}
}

function do_SelectPerson(){
	var lookUpName = "LOOK_UP_PERSON";
	var dialogWidth = 47;
	var dialogHeight = 28;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if(objs){
		var obj = objs[0];
		mainFrm.assignedToName.value = obj["userName"];
	}
}

function do_SelectProject() {
	var lookUpName = "LOOKUP_PROJECT2";
	var dialogWidth = 50;
	var dialogHeight = 30;
	var objs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		mainFrm.projectName.value = obj["projectName"];
	}
}

function do_SelectCostCenter(){
	var lookUpName = "LOOK_UP_COST";
    var userPara="organizationId="+document.mainFrm.organizationId.value;
    var dialogWidth = 50;
	var dialogHeight = 28;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight,userPara);
	if (objs) {
		var obj = objs[0];
		document.mainFrm.costCenterCode.value = obj["costCode"];
	}
}
</script>
</html>