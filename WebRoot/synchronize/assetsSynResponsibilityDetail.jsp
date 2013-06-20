<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2008-12-4
  Time: 16:12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.synchronize.dto.AmsAssetsSynResponsibilityDTO" %>

<html>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>同步权限定义详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>

</head>
<body >
 <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<%
    AmsAssetsSynResponsibilityDTO dto = (AmsAssetsSynResponsibilityDTO) request.getAttribute("DATADTO");
    String orgHtml = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.synchronize.servlet.AmsAssetsSynResponsibilityServlet">
     <script language="javascript">
        printTitleBar("同步权限定义详细信息");
    </script>
    <input type="hidden" name="show" value="show">
    <input type="hidden" name="responsibilityId" value="<%=dto.getResponsibilityId()%>">
    <input type="hidden" name="respApplId" value="<%=dto.getRespApplId()%>">
    <input type="hidden" name="userId" value="<%=dto.getUserId()%>">
    <input type="hidden" name="applicationId" value="<%=dto.getApplicationId()%>">
    <input type="hidden" name="organizationId1" value="<%=dto.getOrganizationId()%>">
    <input type="hidden" name="transactionType" value="<%=dto.getTransactionType()%>">
    <table border="0" width="100%" id="table1">
            <%
            if(dto.getTransactionType().equals("")){
            %>
            <tr>
        <td width = "25%" align = "right" height = "22">公司：</td>
        <td width = "50%" align = "left" height = "22" >
            <select name = "organizationId"  style = "width:80%"><%=orgHtml%></select>
        </td>
      
    </tr>
        <%}else{%>
             <tr  >
        <td width = "25%" align = "right" height = "22">公司：</td>
        <td width = "50%" align = "left" height = "22" >
            <select name = "organizationId" disabled  style = "width:80%"><%=orgHtml%></select>
        </td>

    </tr>
        <%}%>

        <tr>
            <td width="25%" align="right" height="22">职责描述：</td>
            <td width="25%"><input type="text"  readonly name="responsibilityName" value="<%=dto.getResponsibilityName()%>" style="width:75%"  class="noEmptyInput" ><a href="#" onClick="do_SelectRes();" title="点击选择职责" class="linka">[…]</a></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">用户：</td>
            <td width="25%"><input type="text" readonly name="fullName" value="<%=dto.getFullName()%>" style="width:75%"  class="noEmptyInput" ><a href="#" onClick="do_SelectName();" title="点击选择用户" class="linka">[…]</a></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="100%" align="center" height="22" colspan="5">
                <img src="/images/button/save.gif" alt="保存" onClick="do_SaveRole(); return false;">&nbsp;
                <%--<img src="/images/button/delete.gif" alt="删除" onClick="do_DeleteRole(); return false;">&nbsp;--%>
                <img src="/images/button/back.gif" alt="取消" onClick="do_Back(); return false;"></td>
        </tr>

    </table>
	<input type="hidden" name="act" value="">
</form>
</body>
</html>
<script>
function do_SaveRole() {
	var fieldNames = "organizationId;responsibilityName;fullName";
	var fieldLabels = "公司;职责描述;用户";
	var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);

	if (isValid) {
		var action = "<%=WebActionConstant.CREATE_ACTION%>";
        if (mainFrm.transactionType.value != "") {
			action = "<%=WebActionConstant.UPDATE_ACTION%>";
		}
		mainFrm.act.value = action;
		mainFrm.submit();
	}
}

function do_DeleteRole() {
    var roleId = mainFrm.roleId.value;
    if (confirm("确认删除该角色吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
        mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
        mainFrm.submit();
    }
}


function do_Back() {
    mainFrm.fullName.value="";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
	mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.AmsAssetsSynResponsibilityServlet";
	mainFrm.submit();
}

function do_SelectRes() {
     var projects = getLookUpValues("LOOK_UP_MIS_RES", 48, 30,"organizationId="+document.mainFrm.organizationId.value);
    if (projects) {
        document.mainFrm.responsibilityName.value = projects[0].responsibilityName;
        document.mainFrm.responsibilityId.value = projects[0].responsibilityId;
    }else {
            document.mainFrm.responsibilityName.value="";
            document.mainFrm.responsibilityId.value="";
         document.mainFrm.fullName.value="";
            document.mainFrm.userId.value="";
            document.mainFrm.applicationId.value="";
    }
}
 function do_SelectName() {
     if(document.mainFrm.responsibilityId.value==""){
          alert("请选择职责") ;
     }else {
     var projects = getLookUpValues("LOOK_UP_MIS_RES_USER", 48, 30,"responsibilityId="+document.mainFrm.responsibilityId.value);
    if (projects) {
        document.mainFrm.fullName.value = projects[0].fullName;
        document.mainFrm.userId.value = projects[0].userId;
        document.mainFrm.applicationId.value = projects[0].applicationId;

    }else {
            document.mainFrm.fullName.value="";
            document.mainFrm.userId.value="";
            document.mainFrm.applicationId.value="";
    }  }
}
function do_Check(object) {
	if (object.value != '') {
		if (!isNumber(object.value)) {
			alert("请输入合法数字！");
			object.value = "";
			object.focus();
		} else {
			return true;
		}
	}
}

</script>