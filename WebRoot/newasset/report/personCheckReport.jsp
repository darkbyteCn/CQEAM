<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>盘点结果报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId();
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.PersonChkReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("盘点日常报表-->>个人盘点报表")
</script>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="8%" align="right">责任部门：</td>
			<td width="23%"><select size="1" name="deptCode" style="width:100%"><%=dto.getDeptOpt()%></select></td>
			<td width="6%" align="right">责任人：</td>
			<td width="14%" align="right"><input type="text" name="responsibilityUser" style="width:80%" value="<%=dto.getResponsibilityUser()%>"><a href="" title="点击选择责任人" onclick="do_SelectPerson(); return false;">[…]</a></td>
			<td width="8%" align="right">标签号：</td>
			<td width="14%" align="center"><input type="text" name="tagNumber" style="width:100%" value="<%=dto.getTagNumber()%>" ></td>
			<td width="8%" align="right">设备名称：</td>
			<td width="17%"><input type="text" name="assetsDescription" style="width:80%" value="<%=dto.getAssetsDescription()%>"><a href=""  title="点击选择设备名称" onclick="do_SelectItemName(); return false;">[…]</a></td>
		</tr>
		<tr>
			<td width="8%" align="right">盘点地点：</td>
			<td width="23%"><input type="text" name="checkLocationName" style="width:80%" value="<%=dto.getCheckLocationName()%>"><a href=""  title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a></td>
			<td width="6%" align="right">盘点人：</td>
			<td width="14%" align="right"><input type="text" name="implementUser" style="width:80%" value="<%=dto.getImplementUser()%>"><a href=""  title="点击选择地点" onclick="do_SelectUser(); return false;">[…]</a></td>
			<td width="8%" align="right">盘点时间：</td>
			<td width="22%" align="center" colspan="2"><input type="text" name="startDate" style="cursor:hand;width:45%" title="点击选择开始日期" readonly class="readonlyInput" value="<%=dto.getStartDate()%>" onclick="gfPop.fStartPop(startDate,endDate);" >&nbsp;到<input type="text" name="endDate" style="cursor:hand;width:45%" title="点击选择截至日期" readonly class="readonlyInput" value="<%=dto.getEndDate()%>" onclick="gfPop.fEndPop(startDate,endDate);"></td>
			<td width="17%" align="right"><img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">&nbsp;&nbsp;<img border="0" src="/images/eam_images/search.jpg" onclick="do_Search();"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>


<div id="headDiv" style="overflow:hidden;position:absolute;top:68px;left:1px;width:840px">
	<table class="headerTable" border="1" width="200%">
		<tr height="22">
			<td width="7%" align="center">标签号</td>
			<td width="8%" align="center">设备专业</td>
			<td width="10%" align="center">设备名称</td>

			<td width="10%" align="center">设备型号</td>
			<td width="10%" align="center">地点代码</td>
			<td width="14%" align="center">地点名称</td>

			<td width="10%" align="center">部门名称</td>
			<td width="5%" align="center">责任人</td>
			<td width="5%" align="center">员工号</td>

			<td width="5%" align="center">盘点人</td>
			<td width="10%" align="center">工单编号</td>
			<td width="7%" align="center">盘点日期</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:360px;width:857px;position:absolute;top:91px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">

<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
			<td width="7%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
			<td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_CATEGORY")%>"></td>
			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>

			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
			<td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
			<td width="14%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>

			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("USER_NAME")%>"></td>
			<td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>

			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("CHECK_USER")%>"></td>
			<td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
			<td width="7%"><input type="text" class="finput2" readonly value="<%=row.getValue("ARCHIVED_DATE")%>"></td>
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
<div style="position:absolute;top:468px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}


function do_SelectAddress(){
	var lookUpName = "LOOK_UP_ADDRESS";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=orgId%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (objs) {
		var obj = objs[0];
		mainFrm.checkLocationName.value = obj["toObjectName"];
	}
}


function do_SelectPerson(){
	var lookUpName = "LOOK_UP_PERSON";
	var dialogWidth = 47;
	var dialogHeight = 30;
	var users = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if(users){
		var user = users[0];
		mainFrm.responsibilityUser.value = user["userName"];
	}
}

function do_SelectItemName(){
	var lookUpName = "LOOK_UP_ITEMNAME";
	var dialogWidth = 35;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
//		dto2Frm(obj, "mainFrm");
		mainFrm.assetsDescription.value = obj["itemName"];
    }
}



/**
  * 功能：选择执行人、归档人
 */
function do_SelectUser(){
	var lookUpName = "LOOK_UP_USER";
	var dialogWidth = 44;
	var dialogHeight = 29;
	var userPara = "organizationId=<%=orgId%>"
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (objs) {
		var obj = objs[0];
		mainFrm.implementUser.value = obj["userName"];
	}
}
</script>