<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>无标题 1</title>
<base target="priviMain" />
<style type="text/css">
.style1 {
	border-color: #000000;
	border-width: 0;
}
</style>
</head>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search()');">
<%
	AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO)request.getAttribute(AssetsWebAttributes.PRIVI_DATA);
%>
<form name="mainFrm" method="post">
<jsp:include page="/message/MessageProcess"/>
	<table style="width: 100%" align="center" class="style1">
		<tr>
			<td style="width: 100%; height: 21px;" colspan="3" align="center"><span lang="zh-cn"><%=dto.getCompanyName()%>&nbsp;&nbsp;&nbsp;&nbsp;<%=dto.getDeptName()%>&nbsp;&nbsp;&nbsp;&nbsp;<%=dto.getRoleName()%></span></td>
		</tr>
		<tr>
			<td style="width: 40%; height: 21px" align="center"><span lang="zh-cn">可选用户</span></td>
			<td style="width: 20%; height: 21px" align="center">操作</td>
			<td style="width: 40%; height: 21px" align="center"><span lang="zh-cn">已选用户</span></td>
		</tr>
	</table>
	<hr color="#5D9FBA" size="1" noshade>
	<table style="width: 100%" align="center" class="style1">
		<tr>
			<td style="width: 40%; height: 21px" align="center">
				<span lang="zh-cn">用户姓名：</span><input type="text" name="userName" value="<%=dto.getUserName()%>"> <img src="/images/eam_images/search.jpg" onClick="do_Search();" title="点击查询用户">
			</td>
			<td style="width: 20%; height: 21px" align="center">　</td>
			<td style="width: 40%; height: 21px" align="center">　</td>
		</tr>
		<tr>
			<td style="width: 40%; height: 480px">
				<select name="allUsers" style="width:100%; height:100%" multiple="multiple" ondblclick="do_AddUser()" title="双击选择该用户">
					<%=request.getAttribute(AssetsWebAttributes.ALL_USER_OPTION)%>
				</select>
			</td>
			<td style="width: 20%; height: 480px" align="center">
				<img src="/images/eam_images/new.jpg" onClick="do_AddUser();"><p>
				<img src="/images/eam_images/add_all.jpg" onClick="do_AddAllUser();"><p>
				<img src="/images/eam_images/delete.jpg" onClick="do_RemoveUser()"><p>
				<img src="/images/eam_images/delete_all.jpg" onClick="do_RemoveAllUser()"><p>
				<img src="/images/eam_images/save.jpg" onClick="do_SavePrivi()">

			</td>
			<td style="width: 40%; height: 400px">
				<select name="userIds" style="width:100%; height:100%" multiple="multiple" ondblclick="do_RemoveUser()" title="双击移出该用户">
					<%=request.getAttribute(AssetsWebAttributes.EXIST_USER_OPTION)%>
				</select>
			</td>
		</tr>
	</table>
	<input type="hidden" name="act" value="">
	<input type="hidden" name="roleId" value="<%=dto.getRoleId()%>">
	<input type="hidden" name="roleName" value="<%=dto.getRoleName()%>">
	<input type="hidden" name="companyCode" value="<%=dto.getCompanyCode()%>">
	<input type="hidden" name="deptCode" value="<%=dto.getDeptCode()%>">
	<input type="hidden" name="companyName" value="<%=dto.getCompanyName()%>">
	<input type="hidden" name="deptName" value="<%=dto.getDeptName()%>">
	<input type="hidden" name="saved" value="<%=dto.isSaved()%>">
</form>
</body>
</html>

<script>
function do_AddUser(){
	moveSelectedOption("allUsers", "userIds");
	do_SetSavedProp(false);
}

function do_AddAllUser(){
	moveAllOption("allUsers", "userIds");
	do_SetSavedProp(false);
}

function do_RemoveUser(){
	moveSelectedOption("userIds", "allUsers");
	do_SetSavedProp(false);
}

function do_RemoveAllUser(){
	moveAllOption("userIds", "allUsers");
	do_SetSavedProp(false);
}

function do_Search(){
	selectAll("userIds");
	mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.PriviRightServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}


function do_SavePrivi(){
	var leftCount = mainFrm.allUsers.options.length;
	var rightCount = mainFrm.userIds.options.length;
	if(leftCount + rightCount == 0){
		alert("没有可保存的权限数据");
		return;
	}
	selectAll("userIds");
	mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsPriviServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
	do_SetSavedProp(true);
	mainFrm.submit();
}

function do_SetSavedProp(savedProp){
	mainFrm.saved.value = savedProp;
}
</script>
