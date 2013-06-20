<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.web.request.upload.RequestParser"%>
<%@page import="com.sino.base.data.RowSet"%>
<%@page import="com.sino.base.constant.db.QueryConstant"%>
<%@page import="com.sino.base.data.Row"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新增</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
</head>
  <body>
  <%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    String ou = (String)request.getAttribute(WebAttrConstant.CITY_OPTION)  ;
    SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
  %>
    <script type="text/javascript">
        var ArrAction0 = new Array(true, "保存", "action_save.gif", "保存", "do_submit()");
        var ArrAction1 = new Array(true, "关闭", "action_cancel.gif", "关闭", "history.go(-1);");
        var ArrActions = new Array(ArrAction0,ArrAction1);
        var ArrSinoViews = new Array();
        printTitleBar("新增");
        printToolBar();
    </script>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.assetsynchronization.AssetsynchronizationServlet">
	<input type="hidden" name="act">
   	<table width="81%" border="0" align="center" class="">
   		<tr>
   			<td width="12%" align="right">
						公司名称：
			</td>
			<td width="10%" class="input_style2">
				<select name="organizationId" onchange="jilian(this.value);" class="select_style1" style="width: 100%"><%=ou%></select>
			</td>
			<td></td>
		</tr>
		<tr>
            <td width="20%" align="right">用户ID：</td>
            <td width="20%"><input type="text" name="userId" class="input_style1"></td>
            <td><font color="red">*指ERP系统的用户ID</font></td>
        </tr>
        <tr>
            <td width="20%" align="right">员工编号：</td> 
		    <td width="12%"><input type="text" name="archivedUser" class="input_style1"  readonly="true" style="width:75%" ><a href="" onclick="do_SelectUser(mainFrm.archivedUser); return false;" title="点击选择员工编号">[…]</a></td>
            <td><font color="red">*</font></td>
            <input type="hidden" name="employeeNumber"/>
        </tr>
        <tr>
            <td width="20%" align="right">职责ID：</td>
            <td width="20%"><input type="text" name="respId" class="input_style1"></td>
            <td><font color="red">*指ERP系统的职责ID</font></td>
        </tr>
        <tr>
            <td width="20%" align="right">职责应用ID：</td>
            <td width="20%"><input type="text" name="respApplId" class="input_style1"></td>
            <td><font color="red">*指ERP系统职责的应用ID</font></td>
        </tr>
        <tr>
            <td width="20%" align="right">是否默认：</td>
            <td width="20%"><select class="select_style1" name="isDefault" style="width:80%"><option value="Y">是</option><option value="N" >否</option></select></td>
            <td></td>
        </tr> 
    </table>
   </form>
  </body>
</html>
<script type="text/javascript">
    var loginNameWrong = false;
	function do_submit() {
	mainFrm.employeeNumber.value=mainFrm.archivedUser.value;
	if (mainFrm.organizationId.value == "") {
	        alert("请选择公司！")
	        return;
	}
	if (mainFrm.userId.value == "") {
        alert("请输入用户ID！")
        return;
}
	if (mainFrm.employeeNumber.value == "" ) {
	        alert("请输入正确的员工编号！")
	        mainFrm.employeeNumber.focus();
	        return;
	}
	if (mainFrm.respId.value == ""  ) {
	        alert("请输入正确的职责ID！")
	        mainFrm.respId.focus();
	        return;
	}
	if (mainFrm.respApplId.value == "" ) {
	        alert("请输入正确的职责应用ID！")
	        mainFrm.respApplId.focus();
	        return;
	}
	document.mainFrm.act.value = "createDate";
	document.mainFrm.submit();
}
	function do_SelectUser(userBox){
		var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_USER%>";
		var dialogWidth = 44;
		var dialogHeight = 29;
		var userPara = mainFrm.organizationId.value;
		var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
		var boxName = userBox.name;
		if (objs) {
			var obj = objs[0];
			userBox.value = obj["employeeNumber"];
		} else {
			userBox.value = "";
		}
	}
</script>
