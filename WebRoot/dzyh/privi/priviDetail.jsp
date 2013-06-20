<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.dzyh.dto.EamDhPriviDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page import="com.sino.framework.security.bean.SessionUtil"%>
<%@ page import="com.sino.ams.dzyh.constant.DzyhLookUpConstant"%>
<%@ include file="/dzyh/privi/headerInclude.htm" %>

<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>权限定义详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>

</head>
<body onkeydown="autoExeFunction('do_SaveDZYH()');">
 <jsp:include page="/message/MessageProcess"/>
<%
    EamDhPriviDTO eamPrivi = (EamDhPriviDTO)request.getAttribute(WebAttrConstant.DZYH_PRIVI_DATA);
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.dzyh.servlet.EamDhPriviServlet">
<br>
    <table border="0" width="100%" id="table1">
        <tr>
        
        
            <td width="25%" align="right" height="22">公司：</td>
            <td width="50%" align="left" height="22">
<%
if(userAccount.isProvinceUser()){
%>
			<input type="text" name="companyName" size="40" class="readonlyInput" readonly style="width:100%;cursor:pointer" onclick="do_SelectCompany();" value="<%=eamPrivi.getCompanyName()%>" size="20">
<%
} else {
%>			
			<input type="text" name="companyName" size="40" class="readonlyInput" readonly style="width:100%" value="<%=eamPrivi.getCompanyName()%>" size="20">
<%
}
%>
			</td>
            <td width="25%" align="left" height="22"></td>
        
        </tr>
        <tr>
            <td width="25%" align="right" height="22">部门：</td>
            <td width="50%" align="left" height="22">
			<input type="text" name="deptCode" size="40" style="width:100%" value=""></td>
            <td width="25%" align="left" height="22"></td>
        </tr>

        <tr>
            <td width="25%" align="right" height="22">低值易耗管理员：</td>
            <td width="50%" align="left" height="22">
                <input type="text" name="userName" class="readonlyInput" readonly style="width:100%;cursor:pointer" 
                onclick="do_SelectPriviUser();" value="<%=eamPrivi.getUserName()%>">
        </tr>
        <tr>
            <td width="25%" align="right" height="22">是否生效：</td>
            <td width="50%" align="left" height="22">
			<%--
			<input type="text" name="enabled" size="40" style="width:100%" value="<%=eamPrivi.getEnabled()%>"></td>
            --%>
            <select class='noEmptyInput' name="enabled" style="width:100%">
            <%= request.getAttribute(WebAttrConstant.DZYH_ENABLED_OPTION)%>
            </select>            
            <td width="25%" align="left" height="22">&nbsp;</td>
        </tr>
        
        <tr>
            <td width="100%" align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveDZYH(); return false;">&nbsp;
                <img src="/images/eam_images/back.jpg" alt="返回" onClick="do_Back(); return false;"></td>
        </tr>

    </table>
    
	<input type="hidden" name="act" value="">
    <input type="hidden" name="priviId" value="<%=eamPrivi.getPriviId()%>">
</form>
</body>

<script>

/**
 * 功能:选择用户
 */
function do_SelectPriviUser() {
	var lookUpName = "<%=DzyhLookUpConstant.LOOK_UP_USER%>";
	var dialogWidth = 44;
	var dialogHeight = 30;
	var depts = lookUpPriviValues(lookUpName, dialogWidth, dialogHeight);
    if (depts) {
		dto2Frm(depts[0], "mainFrm");
	} else {
		mainFrm.userName.value = "";
	}
}

function do_SaveDZYH() {
	var fieldNames = "deptCode;userName";
	var fieldLabels = "部门;低值易耗管理员";
	var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
	if (isValid) {
		var action = "<%=WebActionConstant.CREATE_ACTION%>";
        if (mainFrm.priviId.value != "") {
			action = "<%=WebActionConstant.UPDATE_ACTION%>";
		}
		mainFrm.act.value = action;
		mainFrm.submit();
	}
}

function do_Back(){
	with(mainFrm){
		deptCode.value = "";
		userName.value = "";
		act.value = "<%=WebActionConstant.QUERY_ACTION%>";
		submit();
	}
}
</script>