<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
	AmsAssetsAddressVDTO dtoPara = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String assProp = request.getParameter("assProp");
	if(assProp == null){
		assProp = "";
	}	
%>
<html>
<body topmargin="0" leftmargin="0" onkeydown="autoExeFunction('do_Search()');">
<form method="post" name="mainFrm" action="/servlet/com.sino.ams.newasset.servlet.AssignPersonServlet">
	<table border="0" width="100%" id="table1" style="border-collapse: collapse; "  bgcolor="#EFEFEF">
		<tr>
			<td width="30%" align="right" height="22">员工姓名：</td>
			<td width="50%" align="right" height="22"><input type="text" name="responsibilityUserName" size="20" style="width:100%" value="<%=dtoPara.getResponsibilityUserName()%>"></td>
			<td width="20%" align="center"><img src="/images/eam_images/search.jpg" title="点击查询人员" onClick="do_Search()"></td>
		</tr>
	</table>
	<table border="0" width="100%" id="table1">
		<tr>
			<td width="100%" align="right" height="250">
			<select size="25" name="responsibilityUser" style="width:100%;height:100%" onclick="do_PassPerson(this)"><%=request.getAttribute(AssetsWebAttributes.ASSIGN_PERSONS)%></select>　
			</td>
		</tr>
	</table>
<input type="hidden" name="act">
<input type="hidden" name="deptCode" value="<%=dtoPara.getDeptCode()%>">	
<input type="hidden" name="assProp" value="<%=assProp%>">
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<script>
function do_Search(){
	if(mainFrm.deptCode.value == ""){
		alert("请先选择部门");
		return;
	}
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_PassPerson(obj){
	var assProp = mainFrm.assProp.value;
	if(assProp == "<%=AssetsWebAttributes.ASSIGN_RESPONSIBLE_USER%>"){
		parent.parent.AssignMain.mainFrm.responsibilityUser.value = obj.value;
	} 
}
</script>
