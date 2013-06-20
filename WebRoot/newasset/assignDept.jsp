<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
	AmsMisDeptDTO dtoPara = (AmsMisDeptDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String assProp = request.getParameter("assProp");
	if(assProp == null){
		assProp = "";
	}
%>
<html>
<body topmargin="0" leftmargin="0" onkeydown="autoExeFunction('do_Search()');">
<form method="post" name="mainFrm" action="/servlet/com.sino.ams.newasset.servlet.AssignDeptServlet">
	<table border="0" width="100%" id="table1" style="border-collapse: collapse; "  bgcolor="#EFEFEF">
		<tr>
			<td width="30%" align="right" height="22">部门名称：</td>
			<td width="50%" align="left" height="22"><input type="text" name="deptName" size="20" style="width:100%" value="<%=dtoPara.getDeptName()%>"></td>
			<td width="20%" align="center" colspan="2"><img src="/images/eam_images/search.jpg" title="点击查询部门" onClick="do_Search()"></td>
		</tr>
	</table>
	<table border="0" width="100%" id="table1" height="92%">
		<tr>
			<td width="100%" align="right" height="100%">
			<select size="25" name="deptCode" style="width:100%;height:100%" onclick="do_PassDeptValue();"><%=request.getAttribute(AssetsWebAttributes.ASSIGN_DEPTS)%></select>　
			</td>
		</tr>
	</table>
<input type="hidden" name="act">	
<input type="hidden" name="assProp" value="<%=assProp%>">
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<script>
function do_PassDeptValue(){
	var assProp = mainFrm.assProp.value;
	if(assProp == "<%=AssetsWebAttributes.ASSIGN_COST_CENTER%>"){
		parent.AssignMain.mainFrm.responsibilityDept.value = mainFrm.deptCode.value;
	} else if(assProp == "<%=AssetsWebAttributes.ASSIGN_RESPONSIBLE_USER%>"){
		parent.parent.AssignMain.mainFrm.responsibilityDept.value = mainFrm.deptCode.value;
		var tab = parent.parent.AssignMain.document.getElementById("dataTable");
		parent.parent.AssignMain.do_Search();
		parent.personContent.mainFrm.deptCode.value = mainFrm.deptCode.value;
		var personCount = parent.personContent.mainFrm.responsibilityUser.length;
		parent.personContent.do_Search();
	} 
}

function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}
</script>
