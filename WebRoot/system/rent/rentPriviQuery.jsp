<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<body leftmargin="0" topmargin="0" onkeydown="autoExeFunction('do_Search()');">
<%
	AmsAssetsPriviDTO dtoParameter = (AmsAssetsPriviDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String srcPage = dtoParameter.getSrcPage();
	RowSet rows = (RowSet)request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	boolean hasData = false;
	if(rows != null && !rows.isEmpty()){
		hasData = true;
	}
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	String pageTitle = "租赁资产管理>>租赁资产权限定义";
	if(srcPage.equals(AssetsActionConstant.QUERY_ACTION)){
		pageTitle = "租赁资产管理>>租赁资产权限查询";
	}
%>
<form name="mainFrm" method="post">
<script type="text/javascript">
    printTitleBar("<%=pageTitle%>");
</script>
<jsp:include page="/message/MessageProcess"/>
	<input type="hidden" name="act" value="">
	<input type="hidden" name="srcPage" value="<%=srcPage%>">
	<input type="hidden" name="companyCode" value="<%=dtoParameter.getCompanyCode()%>">
	<input type="hidden" name="deptCode" value="<%=dtoParameter.getDeptCode()%>">
	<input type="hidden" name="roleId" value="<%=dtoParameter.getRoleId()%>">
	<table width="100%" border="0" bgcolor="#E9EAE9">
	    <tr>
	    	<td width="6%" align="right" height="20">公司：</td>
	    	<td width="15%" height="20">
<%
	if(userAccount.isProvinceUser()){
%>
			<input type="text" name="companyName" class="readonlyInput" readonly style="width:100%;cursor:pointer" onclick="do_SelectCompany();" value="<%=dtoParameter.getCompanyName()%>" size="20">
<%
	} else {
%>
			<input type="text" name="companyName" class="readonlyInput" readonly style="width:100%" value="<%=dtoParameter.getCompanyName()%>" size="20">
<%
	}
%>
			</td>
	    	<td width="6%" height="20" align="right">部门：</td>
	    	<td width="15%" height="20"><input type="text" name="deptName" class="readonlyInput" readonly style="width:100%;cursor:pointer" onclick="do_SelectPriviDept();" value="<%=dtoParameter.getDeptName()%>"></td>
	    	<td width="6%" align="right" height="20">角色：</td>
	    	<td width="15%" height="20"><input type="text" name="roleName" class="readonlyInput" readonly style="width:100%;cursor:pointer" onclick="do_SelectPriviRole();" value="<%=dtoParameter.getRoleName()%>"></td>
	    	<td width="6%" height="20" align="right">用户：</td>
	    	<td width="15%" height="20"><input type="text" name="userName" class="readonlyInput" readonly style="width:100%;cursor:pointer" onclick="do_SelectPriviUser();" value="<%=dtoParameter.getUserName()%>"></td>
	    	<td width="16%" align="right" height="20">
	    		<img src="/images/eam_images/search.jpg" id="queryImg" style="cursor:'hand'" onclick="do_Search();" alt="查询">&nbsp;
<%
	if(!srcPage.equals(AssetsActionConstant.QUERY_ACTION)){
%>
	    		<img src="/images/eam_images/new_add.jpg" id="newImg" style="cursor:'hand'" onclick="do_CreatePrivis();" alt="新增">&nbsp;
	    		<img src="/images/eam_images/delete.jpg" id="deleteImg" style="cursor:'hand'" onclick="do_DeletePrivis();" alt="删除">&nbsp;
<%
	}
%>
	    	</td>
	    </tr>
	</table>

<script type="text/javascript">
    var columnArr = new Array("checkbox", "公司代码", "公司名称", "部门代码", "部门名称","角色名称", "用户姓名", "用户登录名");
    var widthArr = new Array("4%", "8%","16%","8%", "22%", "16%", "12%", "10%");
    printTableHead(columnArr,widthArr);
</script>
<%
	if(hasData){
%>
    <div style="overflow-y:scroll;height:350px;width:100%;left:1px;margin-left:0">
<%
	}
%>
</form>
<%
	if(hasData){
%>
	    <table width="100%" border="1" bordercolor="#666666">

<%		Row row = null;
		String priviId = "";
		for(int i = 0; i < rows.getSize(); i++){
			row = rows.getRow(i);
			priviId = row.getStrValue("PRIVI_ID");
%>
			<tr class="dataTR" onclick="executeClick(this)">
				<td width="4%" align="center"><%=row.getValue("$$$CHECK_BOX_PROP$$$")%></td>
				<td width="8%" align="center"><%=row.getValue("COMPANY_CODE")%></td>
				<td width="16%" align="left"><%=row.getValue("COMPANY_NAME")%></td>
				<td width="8%" align="center"><%=row.getValue("DEPT_CODE")%></td>
				<td width="22%" align="left"><%=row.getValue("DEPT_NAME")%></td>
				<td width="16%" align="left"><%=row.getValue("ROLE_NAME")%></td>
				<td width="12%" align="left"><%=row.getValue("USER_NAME")%>&nbsp;</td>
				<td width="10%" align="left"><%=row.getValue("LOGIN_NAME")%>&nbsp;</td>
			</tr>
<%
		}
%>
		</table>
	</div>
<div style="position:absolute;top:476px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
</html>
<script language="javascript">

function do_Search(){
	mainFrm.target = "_self";
//	mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsPriviServlet";
	mainFrm.action = "/servlet/com.sino.ams.system.rent.servlet.AmsRentPriviServlet";
    mainFrm.act.value="<%=AssetsActionConstant.QUERY_ACTION%>";
	mainFrm.submit();
}


function do_SelectCompany(){
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_COMPANY%>";
	var dialogWidth = 38;
	var dialogHeight = 30;
	var depts = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
    if (depts) {
		dto2Frm(depts[0], "mainFrm");
	} else {
		mainFrm.companyName.value = "";
		mainFrm.companyCode.value = "";
	}
}

/**
 * 功能:选择部门
 */
function do_SelectPriviDept() {
	var userProp = "<%=userAccount.isProvinceUser()%>";
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PRI_DEPT%>";
	var dialogWidth = 44;
	var dialogHeight = 30;
	var userPara = "companyCode=" + mainFrm.companyCode.value;
	var depts = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
    if (depts) {
		dto2Frm(depts[0], "mainFrm");
	} else {
		mainFrm.deptName.value = "";
		mainFrm.deptCode.value = "";
		if(userProp == "true"){
			mainFrm.companyName.value = "";
			mainFrm.companyCode.value = "";
		}
	}
}



/**
 * 功能:选择角色
 */
function do_SelectPriviRole() {
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_PRI_ROLE%>";
	var dialogWidth = 44;
	var dialogHeight = 30;
	var depts = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
    if (depts) {
		dto2Frm(depts[0], "mainFrm");
	} else {
		mainFrm.roleName.value = "";
	}
}

/**
 * 功能:选择用户
 */
function do_SelectPriviUser() {
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_USER%>";
	var dialogWidth = 44;
	var dialogHeight = 30;
	var depts = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
    if (depts) {
		dto2Frm(depts[0], "mainFrm");
	} else {
		mainFrm.userName.value = "";
	}
}

/**
 * 功能：删除权限数据
 */
function do_DeletePrivis(){
	if(!mainFrm.$$$CHECK_BOX_HIDDEN$$$){
		alert("没有数据，不能执行该操作！");
		return;
	}
	if(mainFrm.$$$CHECK_BOX_HIDDEN$$$.value == ""){
		alert("没有选中要删除的数据，不能执行该操作！");
		return;
	}
	mainFrm.target = "_self";
	mainFrm.action = "/servlet/com.sino.ams.system.rent.servlet.AmsRentPriviServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.DELETE_ACTION%>";
	mainFrm.submit();
}

function do_CreatePrivis(){
	var target = "detailWin";
	var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
	window.open("about:blank", target, style);
	mainFrm.target = target;
//	mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.AssetsPriviFrmServlet";
	mainFrm.action = "/servlet/com.sino.ams.system.rent.servlet.rentPriviFrmServlet";
	mainFrm.act.value = "<%=AssetsActionConstant.NEW_ACTION%>";
	mainFrm.submit();
}
</script>
