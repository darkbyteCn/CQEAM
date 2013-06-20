<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="srv.ams.employee.dto.SrvEmployeeInfoDTO" %>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>MIS员工基本信息查询</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	SrvEmployeeInfoDTO dto = (SrvEmployeeInfoDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	String pageTitle = request.getParameter("pageTitle");
	if(pageTitle == null){
		pageTitle = "MIS系统接口-->>MIS员工查询";
	}
%>
<form name="mainFrm" method="post" action="/servlet/srv.ams.employee.servlet.MisEmployeeQueryServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>")
</script>
	<table border="0" width="100%" bgcolor="#EFEFEF">
	  <input type="hidden" name="orgName" style="width:100%">
		<tr height="20">
			<td align="right" width="8%">员工名称：</td>
            <td align="left" width="15%"><input type="text" name="fullName" id="fullName" value="<%=dto.getFullName()%>" style="width:100%"></td>
			<td align="right" width="8%">员工编号：</td>
            <td align="left" width="15%"><input type="text" name="employeeNumber" value="<%=dto.getEmployeeNumber()%>" style="width:100%"></td>
		</tr>
		<tr height="20">
			<td align="right" width="8%">所属部门：</td>
            <td align="left" width="15%"><input type="text" name="organization" id="organization" value="<%=dto.getOrganization()%>" style="width:100%"></td>
			<td align="right" width="8%">成本中心代码：</td>
            <td align="left" width="15%"><input type="text" name="deptCode" value="<%=dto.getDeptCode()%>" style="width:100%"></td>
			<td width="4%">
				<img src="/images/eam_images/search.jpg" alt="查询" onClick="do_Search(); return false;"></td>
			<td width="4%">
				<img src="/images/eam_images/export.jpg" alt="导出EXCEL" onclick="do_Export()">
			</td>
		</tr>
		
	</table>
	<input readonly name="act" type="hidden">
</form>
<div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:800px">
	<table class="headerTable" border="1" width="100%">
		<tr height="22">
			<td width="3%" align="center">员工名称</td>
			<td width="3%" align="center">员工编号</td>
			<td width="3%" align="center">所属部门名称</td>
			<td width="3%" align="center">成本中心代码</td>
			<td width="3%" align="center">员工是否有效</td>
			
		</tr>
	</table>
</div>		
<div id="dataDiv" style="overflow:scroll;height:70%;width:800px;position:absolute;top:94px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>	
		<tr height="22">
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("USER_NAME")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
			<td width="3%" align="center"><input class="finput" readonly value="<%=row.getValue("HR_DEPT_NAME")%>"></td>
			<td width="3%" align="center"><input class="finput" readonly value="<%=row.getValue("DEPT_CODE")%>"></td>
			<td width="3%" align="center"><input class="finput2" readonly value="<%=row.getValue("ENABLED")%>"></td>

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