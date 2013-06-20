<title>系统用户选择</title>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
	AmsAssetsAddressVDTO dtoPara = (AmsAssetsAddressVDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String srcPage = dtoPara.getSrcPage();
	String userLabel = "使用人";
	String fieldName = "maintainUser";
	if(!srcPage.equals("")){
		userLabel = "用户姓名";
		fieldName = "userId";
	}
	String assProp = request.getParameter("assProp");
	if(assProp == null){
		assProp = "";
	}	
	String selectUserId = request.getParameter("selectUserId");
%>
<html>
<body topmargin="0" leftmargin="0" onkeydown="autoExeFunction('do_Search()');">
<form method="post" name="mainFrm" action="<%=AssetsURLList.ASSIGN_USER_SERVLET%>">
	<table border="0" width="100%" id="table1" style="border-collapse: collapse; "  bgcolor="#EFEFEF">
		<tr>
			<td width="30%" align="right" height="22"><%=userLabel%>：</td>
			<td width="50%" align="right" height="22">
			<input type="text" name="maintainUserName" size="20" style="width:100%" value="<%=dtoPara.getMaintainUserName()%>"></td>
			<td width="20%" align="center"><img src="/images/eam_images/search.jpg" title="点击查询人员" onClick="do_Search()"></td>
		</tr>
	</table>
	<table border="0" width="100%" id="table1" height="92%">
		<tr>
			<td width="100%" align="right" height="100%">
			<select size="25" name="<%=fieldName%>" style="width:100%;height:100%" onclick="do_PassValue(this)"><%=request.getAttribute(AssetsWebAttributes.ASSIGN_USERS)%></select>　
			</td>
		</tr>
	</table>
<input type="hidden" name="act">
<input type="hidden" name="srcPage" value="<%=srcPage%>">
<input type="hidden" name="assProp" value="<%=assProp%>">
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
</body>
</html>
<script>
function do_PassValue(obj){
	var srcPage = "<%=srcPage%>";
	if(srcPage != ""){
		parent.priviMain.mainFrm.userId.value = obj.options[obj.selectedIndex].value;
		parent.priviMain.mainFrm.userName.value = obj.options[obj.selectedIndex].text;
		parent.priviMain.do_Search();
	} else {
		parent.AssignMain.mainFrm.maintainUser.value = obj.value;
	}
}

function do_Search(){
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}
</script>
