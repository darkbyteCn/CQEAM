<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/match/prematch/headerInclude.jsp"%>
<%@ include file="/match/prematch/headerInclude.htm"%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>转资准备清单</title>
 </head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search();')" onload="do_SetPageWidth();">
<%
	SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(request);
	AmsPaMatchDTO dto = (AmsPaMatchDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = (rows != null && !rows.isEmpty());
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.prematch.servlet.PaMatchAMSServlet">
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    printTitleBar("预匹配管理-->>EAM实物清单信息")
</script>
	<table width="100%" border="0" class="queryHeadBg">
		<tr>
			<td width="16%" align="right">项目编号：</td>
			<td width="26%"><input type="text" name="projectNumber" style="width:80%" value="<%=dto.getProjectNumber()%>"><a href="" title="点击选择项目编号" onclick="do_SelectProject(); return false;">[…]</a></td>
			<td width="16%" align="right">地点代码：</td>
			<td width="26%"><input type="text" name="workorderObjectCode" style="width:80%" value="<%=dto.getWorkorderObjectCode()%>"><a href="" title="点击选择地点代码" onclick="do_SelectAddress(); return false;">[…]</a></td>
			<td width="16%" align="right"><img border="0" src="/images/eam_images/search.jpg" onclick="do_Search();"></td>
		</tr>
		<tr>
			<td width="16%" align="right">项目名称：</td>
			<td width="26%"><input type="text" name="projectName" style="width:80%" value="<%=dto.getProjectName()%>"><a href="" title="点击选择项目名称" onclick="do_SelectProject(); return false;">[…]</a></td>
			<td width="16%" align="right">地点名称：</td>
			<td width="26%"><input type="text" name="workorderObjectName" style="width:80%" value="<%=dto.getWorkorderObjectName()%>"><a href="" title="点击选择地点名称" onclick="do_SelectAddress(); return false;">[…]</a></td>
			<td width="16%" align="right"><img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
		</tr>
	</table>
	<input name="act" type="hidden">
</form>

<div id="headDiv" style="overflow:hidden;position:absolute;top:67px;left:1px;width:840px">
	<table class="headerTable" border="1" width="300%">
		<tr height="22">
			<td width="2%"></td>

			<td width="8%" align="center">标签号</td>
			<td width="10%" align="center">设备名称</td>
			<td width="11%" align="center">设备型号</td>
			<td width="8%" align="center">项目编号</td>

			<td width="10%" align="center">项目名称</td>
			<td width="10%" align="center">地点代码</td>
			<td width="20%" align="center">地点名称</td>
			<td width="7%" align="center">员工编号</td>

			<td width="7%" align="center">责任人</td>
			<td width="7%" align="center">启用日期</td>
		</tr>
	</table>
</div>
<div id="dataDiv" style="overflow:scroll;height:77%;width:857px;position:absolute;top:90px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="300%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
	if(hasData){
		Row row = null;
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
%>
		<tr height="22">
			<td width="2%" align="center"><input type="radio" name="systemId" value="<%=row.getValue("SYSTEMID")%>"></td>

  			<td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("BARCODE")%>"></td>
			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_NAME")%>"></td>
			<td width="11%"><input type="text" class="finput" readonly value="<%=row.getValue("ITEM_SPEC")%>"></td>
			<td width="8%"><input type="text" class="finput2" readonly value="<%=row.getValue("PROJECT_NUMBER")%>"></td>

			<td width="10%"><input type="text" class="finput" readonly value="<%=row.getValue("PROJECT_NAME")%>"></td>
			<td width="10%"><input type="text" class="finput2" readonly value="<%=row.getValue("WORKORDER_OBJECT_CODE")%>"></td>
			<td width="20%"><input type="text" class="finput" readonly value="<%=row.getValue("WORKORDER_OBJECT_NAME")%>"></td>
			<td width="7%"><input type="text" class="finput2" readonly value="<%=row.getValue("EMPLOYEE_NUMBER")%>"></td>

			<td width="7%"><input type="text" class="finput" readonly value="<%=row.getValue("USER_NAME")%>"></td>
			<td width="7%"><input type="text" class="finput2" readonly value="<%=row.getValue("START_DATE")%>"></td>
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
<div style="position:absolute;top:570px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

</html>
<script>
function do_Search(){
	with(mainFrm){
		if(projectName.value == "" && projectNumber.value == ""){
			alert("请选择或输入项目之后再查询");
			return;
		}
		if(workorderObjectCode.value == "" && workorderObjectName.value == ""){
			alert("请选择或输入地点之后再查询");
			return;
		}
		act.value = "<%=AMSActionConstant.QUERY_ACTION%>";
		submit();
	}
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
}

function do_Export() {
	with(mainFrm){
		if(projectName.value == "" && projectNumber.value == ""){
			alert("请选择或输入项目之后再导出");
			return;
		}
		if(workorderObjectCode.value == "" && workorderObjectName.value == ""){
			alert("请选择或输入地点之后再导出");
			return;
		}
		act.value = "<%=AMSActionConstant.EXPORT_ACTION%>";
		submit();
	}
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
</script>
