<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/match/prematch/headerInclude.jsp"%>
<%@ include file="/match/prematch/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>转资匹配历史</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(request);
	AmsPaMatchDTO dto = (AmsPaMatchDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	String srcPage = dto.getSrcPage();
	int headerTop = 26;
	int dataHeight = 508;
	int naviTop = 575;
	if(srcPage.equals("")){
		headerTop = 26 + 20;
		dataHeight = 360;
		naviTop = 450;
	}
	int dataTop = headerTop + 23;
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" id="mainFrm" method="post" action="/servlet/com.sino.ams.prematch.servlet.AmsPaMatchServlet">
<%
	if(srcPage.equals("")){
%>
<script type="text/javascript">
    printTitleBar("转资预匹配>>转资匹配历史");
</script>
<%
	}	
%>
<jsp:include page="/message/MessageProcess"/>
<%=WebConstant.WAIT_TIP_MSG%>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="8%" align="right">项目编号：</td>
			<td width="12%"><input type="text" name="projectNumber" style="width:70%" value="<%=dto.getProjectNumber()%>"><a href="" title="点击选择项目编号" onclick="do_SelectProject(); return false;">[…]</a></td>
			<td width="8%" align="right">项目名称：　</td>
			<td width="14%"><input type="text" name="projectName" style="width:70%" value="<%=dto.getProjectName()%>"><a href="" title="点击选择项目名称" onclick="do_SelectProject(); return false;">[…]</a></td>
			<td width="8%" align="right">地点代码：</td>
			<td width="12%"><input type="text" name="workorderObjectCode" style="width:70%" value="<%=dto.getWorkorderObjectCode()%>"><a href="" title="点击选择地点代码" onclick="do_SelectAddress(); return false;">[…]</a></td>
			<td width="8%" align="right">地点名称：　</td>
			<td width="14%"><input type="text" name="workorderObjectName" style="width:70%" value="<%=dto.getWorkorderObjectName()%>"><a href="" title="点击选择地点名称" onclick="do_SelectAddress(); return false;">[…]</a></td>
			<td width="16%" align="right"><img border="0" src="/images/eam_images/search.jpg" onclick="do_Search();">&nbsp;<img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	
	<input name="act" type="hidden">
	<input name="srcPage" type="hidden" value="<%=srcPage%>">
</form>

<div id="headDiv" style="overflow:hidden;position:absolute;top:<%=headerTop%>px;left:1px;width:840px">

	<table class="headerTable" border="1" width="320%">
<%
	if(srcPage.equals(AMSActionConstant.DELETE_ACTION)){
%>
		<tr onClick="executeClick(this)" style="cursor:hand" title="点击全选或取消全选">
			<td width="1%" align="center"><input type="checkbox" name="mainCheck" onPropertyChange="checkAll('mainCheck', 'subCheck')"></td>
<%
	} else {
%>
		<tr height="22">
<%
	}
%>
			<td width="4%" align="center">EAM标签</td>
			<td width="4%" align="center">PA标签</td>
			<td width="5%" align="center">EAM名称</td>
			<td width="5%" align="center">PA名称</td>

			<td width="6%" align="center">EAM型号</td>
			<td width="6%" align="center">PA型号</td>
			<td width="3%" align="center">EAM项目编号</td>
			<td width="3%" align="center">PA项目编号</td>

			<td width="6%" align="center">EAM项目名称</td>
			<td width="6%" align="center">PA项目名称</td>
			<td width="5%" align="center">EAM地点代码</td>
			<td width="5%" align="center">PA地点代码</td>

			<td width="9%" align="center">EAM地点名称</td>
			<td width="9%" align="center">PA地点名称</td>
			<td width="3%" align="center">EAM员工号</td>
			<td width="3%" align="center">PA员工号</td>

			<td width="3%" align="center">EAM责任人</td>
			<td width="3%" align="center">PA责任人</td>
			<td width="3%" align="center">匹配人</td>
			<td width="5%" align="center">匹配时间</td>

			<td width="3%" align="center">备注</td>
		</tr>
	</table>

</div>
<div id="dataDiv" style="overflow:scroll;height:<%=dataHeight%>px;width:857px;position:absolute;top:<%=dataTop%>px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="320%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
<%
	if(srcPage.equals(AMSActionConstant.DELETE_ACTION)){
%>
			<td width="1%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
<%
	}				
%>
  			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
  			<td width="4%"><input type="text" class="finput2" readonly value="<%=row.getValue("TAG_NUMBER")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
			<td width="5%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_DESCRIPTION")%>"></td>

			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("MODEL_NUMBER")%>"></td>
			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("PROJECT_NUMBER_AMS")%>"></td>
			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("PROJECT_NUMBER")%>"></td>

			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("PROJECT_NAME_AMS")%>"></td>
			<td width="6%"><input type="text" class="finput" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
			<td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
			<td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSETS_LOCATION_CODE")%>"></td>

			<td width="9%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
			<td width="9%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSETS_LOCATION")%>"></td>
			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>
			<td width="3%"><input type="text" class="finput2" readonly value="<%=row.getValue("ASSIGNED_TO_NUMBER")%>"></td>

			<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("USER_NAME")%>"></td>
			<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("ASSIGNED_TO_NAME")%>"></td>
			<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("USERNAME")%>"></td>
			<td width="5%"><input type="text" class="finput2" readonly value="<%=row.getValue("CREATION_DATE")%>"></td>

			<td width="3%"><input type="text" class="finput" readonly value="<%=row.getValue("REMARK")%>"></td>
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
<div style="position:absolute;top:<%=naviTop%>px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function do_Search(){
	mainFrm.act.value = "<%=AMSActionConstant.QUERY_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
    mainFrm.act.value = "<%=AMSActionConstant.EXPORT_ACTION%>";
	mainFrm.target = "_self";
    mainFrm.submit();
}

function do_SelectProject(){
	var lookUpName = "LOOKUP_PROJECT";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	if (objs) {
		var obj = objs[0];
		dto2Frm(obj, "mainFrm");
    }
}

function do_SelectAddress(){
	var lookUpName = "LOOK_UP_ADDRESS";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var userPara = "organizationId=<%=user.getOrganizationId()%>";
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (objs) {
		var obj = objs[0];
		mainFrm.workorderObjectCode.value = obj["workorderObjectCode"];
		mainFrm.workorderObjectName.value = obj["toObjectName"];
    }
}

function do_ReleaseMatch(){
	mainFrm.act.value = "<%=AMSActionConstant.DELETE_ACTION%>";
	mainFrm.target = "_self";
	mainFrm.submit();
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}
</script>
