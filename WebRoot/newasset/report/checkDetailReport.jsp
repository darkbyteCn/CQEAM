<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
<title>盘点结果报表</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="initPage();">
<%
	AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.report.servlet.CheckDetailReportServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("盘点常用报表-->>盘点明细报表");
</script>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="8%" align="center">部门名称：</td>
			<td width="24%"><select name="checkDept" class="select_style1" style="width:100%"><%=dto.getCheckDeptOption()%></select></td>
			<td width="8%" align="center">盘点地点：</td>
			<td width="15%"><input type="text" name="checkLocationName" class="input_style1" value="<%=dto.getCheckLocationName()%>" style="width:80%"><a href=""  title="点击选择地点" onclick="do_SelectAddress(); return false;">[…]</a></td>
			<td width="8%" align="center">盘点时间：</td>
			<td width="8%"><input type="text" name="startDate" class="input_style1" style="cursor:hand;width:100%" title="点击选择开始日期" readonly class="readonlyInput" value="<%=dto.getStartDate()%>" onclick="gfPop.fStartPop(startDate,endDate);"></td>
			<td width="3%" align="center">到</td>
			<td width="8%"><input type="text" name="endDate" class="input_style1" style="cursor:hand;width:100%" title="点击选择截至日期" readonly class="readonlyInput" value="<%=dto.getEndDate()%>" onclick="gfPop.fEndPop(startDate,endDate);"></td>
			<td width="18%" align="right"><img border="0" src="/images/eam_images/search.jpg"   onclick="do_Search();">
			<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">
			</td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>

<div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
	<table class="headerTable" border="1" width="200%">
		<tr height="22">
			<td width="10%" align="center">工单编号</td>
			<td width="4%" align="center">工单状态</td>
			<td width="6%" align="center">标签号</td>
			<td width="9%" align="center">设备名称</td>

			<td width="9%" align="center">设备型号</td>
			<td width="5%" align="center">员工号</td>
			<td width="5%" align="center">责任人</td>
			<td width="13%" align="center">部门名称</td>

			<td width="8%" align="center">地点代码</td>
			<td width="13%" align="center">地点名称</td>
			<td width="4%" align="center">盘点人</td>
			<td width="4%" align="center">盘点日期</td>
            <td width="4%" align="center">归档人</td>
			<td width="4%" align="center">归档日期</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:364px;width:857px;position:absolute;top:68px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="200%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
			<td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("TRANS_NO")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("ORDER_STATUS")%>"></td>
			<td width="6%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
			<td width="9%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>

			<td width="9%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
			<td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("RESPONSIBILITY_USER")%>"></td>
			<td width="13%"><input type="text" class="finput" readonly value="<%=row.getValue("DEPT_NAME")%>"></td>

			<td width="8%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
			<td width="13%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
			<td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("CHECK_USER")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("CHECK_DATE")%>"></td>
            <td width="4%"><input type="text" class="finput" readonly value="<%=row.getValue("ARCHIVED_USER")%>"></td>
			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("ARCHIVED_DATE")%>"></td>
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
<div id="pageNaviDiv" style="position:absolute;top:91%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function initPage(){
	do_SetPageWidth();
}

function do_SelectAddress(){
	with(mainFrm){
		var lookUpName = "LOOK_UP_ADDRESS";
		var dialogWidth = 55;
		var dialogHeight = 30;
		userPara = "organizationId=<%=userAccount.getOrganizationId()%>";
		var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
		if (objs) {
			var obj = objs[0];
			checkLocationName.value = obj["toObjectName"];
		} else {
			checkLocationName.value = "";	
		}
	}
}

function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
    mainFrm.submit();
}
</script>