<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>专业资产管理员权限维护</title>
<base target="priviMain" />
<style type="text/css">
.style1 {
	border-color: #000000;
	border-width: 0;
}
</style>
</head>
<body onkeydown="autoExeFunction('do_Search()');">
<%
	AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO)request.getAttribute(QueryConstant.QUERY_DTO);
%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.newasset.servlet.ContentPriviServlet">
<jsp:include page="/message/MessageProcess"/>
	<table style="width: 100%" align="center" class="style1">
		<tr>
			<td style="width: 100%; height: 21px;" colspan="3" align="center"><%=dto.getCompanyName()%>&nbsp;&nbsp;&nbsp;&nbsp;<%=dto.getDeptName()%>&nbsp;&nbsp;&nbsp;&nbsp;<%=dto.getRoleName()%></td>
		</tr>
		<tr>
			<td style="width: 40%; height: 21px" align="center">可选权限</td>
			<td style="width: 20%; height: 21px" align="center">操作</td>
			<td style="width: 40%; height: 21px" align="center">已选权限</td>
		</tr>
	</table>
	<hr color="#5D9FBA" size="1" noshade>
	<table style="width: 100%" align="center" class="style1">
		<tr>
			<td style="width: 40%; height: 480px">
				<select name="allfaCategoryCodes" style="width:100%; height:100%" multiple="multiple" ondblclick="do_AddPrivi()" title="双击选择该权限"><%=dto.getNoPriviOption()%></select>
			</td>
			<td style="width: 20%; height: 480px" align="center">
				<img src="/images/eam_images/new.jpg" onClick="do_AddPrivi();"><p>
				<img src="/images/eam_images/add_all.jpg" onClick="do_AddAllPrivi();"><p>
				<img src="/images/eam_images/delete.jpg" onClick="do_RemovePrivi()"><p>
				<img src="/images/eam_images/delete_all.jpg" onClick="do_RemoveAllPrivi()"><p>
				<img src="/images/eam_images/save.jpg" onClick="do_SavePrivi()">

			</td>
			<td style="width: 40%; height: 400px">
				<select name="faCategoryCode" style="width:100%; height:100%" multiple="multiple" ondblclick="do_RemovePrivi()" title="双击移出该权限">
					<%=dto.getExistPriviOption()%>
				</select>
			</td>
		</tr>
	</table>

	<input type="hidden" name="userId" value="<%=dto.getUserId()%>">
	<input type="hidden" name="userName" value="<%=dto.getUserName()%>">
	<input type="hidden" name="act" value="">
	<input type="hidden" name="roleId" value="<%=dto.getRoleId()%>">
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
</body>
</html>
<script>
function do_Search() {
	mainFrm.userId.value = parent.contents.mainFrm.userId.value;
	if(mainFrm.userId.value == ""){
		alert("请先选择用户，在执行查询");
		return;
	}
    mainFrm.act.value = "<%=AssetsActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_AddPrivi(){
	moveSelectedOption("allfaCategoryCodes", "faCategoryCode");
}

function do_AddAllPrivi(){
	moveAllOption("allfaCategoryCodes", "faCategoryCode");
}

function do_RemovePrivi(){
	moveSelectedOption("faCategoryCode", "allfaCategoryCodes");
}

function do_RemoveAllPrivi(){
	moveAllOption("faCategoryCode", "allfaCategoryCodes");
}

function do_SavePrivi(){
	var leftCount = mainFrm.allfaCategoryCodes.options.length;
	var rightCount = mainFrm.faCategoryCode.options.length;
	if(leftCount + rightCount == 0){
		alert("没有可保存的权限数据");
		return;
	}
	selectAll("faCategoryCode");
	mainFrm.action = "";
	mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
	mainFrm.submit();
}
</script>
