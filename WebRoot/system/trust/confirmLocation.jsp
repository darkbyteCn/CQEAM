<%@ page contentType = "text/html; charset=GBK" language = "java" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant" %>
<%@ page import = "com.sino.base.data.Row" %>
<%@ page import = "com.sino.base.data.RowSet" %>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.ams.system.trust.dto.AmsMaintainResponsibilityDTO"%>
<html>
<head>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>可选地点</title>
<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/WebLibary/js/Constant.js"></script>
<script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
<script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
<script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
<script language="javascript" src="/WebLibary/js/DateProcess.js"></script>
<script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
<script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
<script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
<script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
</head>
<%
	AmsMaintainResponsibilityDTO dto = (AmsMaintainResponsibilityDTO)request.getAttribute(QueryConstant.QUERY_DTO);
%>
<body leftmargin="0" topmargin="0" bgcolor="#EFEFEF">
<form method="POST" action="/servlet/com.sino.ams.system.trust.servlet.AmsMaintainResponsibilityServlet" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>

<script type="text/javascript">
    printTitleBar("系统维护>>代维地点确认");
</script>
<table border="0" width="100%" cellspacing="1" style="border-collapse: collapse" id="table1">
	<tr>
		<td width="45%" align="center">可选地点</td>
		<td width="10%" align="center">操作</td>
		<td width="45%" align="center">已选地点</td>
	</tr>
    <hr size="1" color="#3366EE">
    <tr>
		<td width="45%">
		<p align="center">关键字：<input type="text" name="workorderObjectName" size="30" value="<%=dto.getWorkorderObjectName()%>"><img border="0" src="../../images/eam_images/search.jpg" width="63" height="18" onclick="do_Search()"></td>
		<td width="10%" align="center">　</td>
		<td width="45%">　</td>
	</tr>
	<tr>
		<td width="45%" height="400">
		<select size="12" name="objectNo" multiple style="width:100%;height:100%" ondblclick="do_AddLocation()"><%=dto.getToConfirmLocOpt()%></select></td>
		<td width="10%" height="400" align="center">
			<img src="/images/button/add.gif" onClick="do_AddLocation();"><p>
			<img src="/images/button/addAll.gif" onClick="do_AddAllLocation();"><p>
			<img src="/images/eam_images/delete.jpg" onClick="do_RemoveLocation()"><p>
			<img src="/images/button/delAll.gif" onClick="do_RemoveAllLocation()"><p>
			<img src="/images/eam_images/save.jpg" onClick="do_SaveReponsibility()">
		</td>
		<td width="45%" height="400">
		<select size="12" name="workorderObjectNo" multiple style="width:100%;height:100%" ondblclick="do_RemoveLocation()"><%=dto.getConfirmedLocOpt()%></select></td>
	</tr>
</table>
<input type="hidden" name="act" value="">
</form>

</body>

</html>

<script>
function do_AddLocation(){
	moveSelectedOption("objectNo", "workorderObjectNo");
}

function do_AddAllLocation(){
	moveAllOption("objectNo", "workorderObjectNo");
}

function do_RemoveLocation(){
	moveSelectedOption("workorderObjectNo", "objectNo");
}

function do_RemoveAllLocation(){
	moveAllOption("workorderObjectNo", "objectNo");
}

function do_Search(){
	selectAll("workorderObjectNo");
	mainFrm.act.value = "QUERY_ACTION";
	mainFrm.submit();
}


function do_SaveReponsibility(){
	var leftCount = mainFrm.objectNo.options.length;
	var rightCount = mainFrm.workorderObjectNo.options.length;
	if(leftCount + rightCount == 0){
		alert("没有可保存的权限数据");
		return;
	}
	selectAll("workorderObjectNo");
//	mainFrm.action = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainResponsibilityServlet";
//	mainFrm.act.value = "SAVE_ACTION";
	mainFrm.act.value = "CREATE_ACTION";
//	do_SetSavedProp(true);
	mainFrm.submit();
}
</script>