<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.system.dict.dto.EtsFlexValuesDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.util.StrUtil" %>

<html>
<head>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>组别详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">	
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
</head>
<%
    EtsFlexValuesDTO flexValues = (EtsFlexValuesDTO)request.getAttribute(WebAttrConstant.DICT_DATA);
%>
<body onload="initPage();"  onkeydown="autoExeFunction('do_SaveDictionary()');">
 <jsp:include page="/message/MessageProcess"/>

<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.system.dict.servlet.EtsFlexValuesServlet">
    <script type="text/javascript">
        printTitleBar("系统字典维护");
    </script>
    <table border="0" width="100%" id="table1">
        <tr>
            <td width="25%" align="right" height="22">字典代码：</td>
            <td width="50%" align="left" height="22">
			<input type="text" name="code" size="40" class="input_style1" style="width:100%" value="<%=flexValues.getCode()%>"></td>
            <td width="25%" align="left" height="22">&nbsp;<font style="color: red">*</font>  </td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">字典值：</td>
            <td width="50%" align="left" height="22">
			<input type="text" name="value" size="40" class="input_style1"  style="width:100%" value="<%=flexValues.getValue()%>"></td>
            <td width="25%" align="left" height="22">&nbsp;<font style="color: red">*</font></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">字典描述：</td>
            <td width="50%" align="left" height="22">
                <input type="text" name="description" size="40" class="input_style1"  style="width:100%" value="<%=flexValues.getDescription()%>">
                <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">是否有效：</td>
            <td width="50%" align="left" height="22"><%=request.getAttribute(WebAttrConstant.ENABLED_RADIO)%></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">是否内置：</td>
            <td width="50%" align="left" height="22"><%=request.getAttribute(WebAttrConstant.IS_INNER_RADIO)%></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">所属分类：</td>
            <td width="50%" align="left" height="22">
                <select name="flexValueSetId" class="select_style1" style="width:100%"><%=request.getAttribute(WebAttrConstant.DICT_PARENT_OPT)%></select>
            </td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="100%" align="center" height="22" colspan="3">
<%
	if(!flexValues.getIsInner().equals("Y") || StrUtil.isEmpty( flexValues.getFlexValueId() ) ) {
%>
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveDictionary(); return false;">&nbsp;
<%
	}	
%>
                <img src="/images/eam_images/back.jpg" alt="返回" onClick="do_Back(); return false;"></td>
        </tr>

    </table>
	<input type="hidden" name="act" value="">
    <input type="hidden" name="flexValueId" value="<%=flexValues.getFlexValueId()%>">
    <input type="hidden" name="flexValueSetName" value="">
</form>
</body>
</html>
<script>

function initPage(){
	var selObj = document.mainFrm.flexValueSetId;
	var flexValueSetName = selObj.options[selObj.selectedIndex].text;
	var startIndex = flexValueSetName.indexOf("(") + 1;
	var endIndex = flexValueSetName.length - 1;
	flexValueSetName = flexValueSetName.substring(startIndex, endIndex);
	document.mainFrm.flexValueSetName.value = flexValueSetName;

    <%if(flexValues.getIsInner().equals("Y")&& !StrUtil.isEmpty( flexValues.getFlexValueId() ) ){%>
        document.mainFrm.disabled="Y";
    <%}%>
}

function do_SaveDictionary() {
	var fieldNames = "code;value;flexValueSetId";
	var fieldLabels = "字典代码;字典值;所属分类";
	var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
	var flexValueId = document.mainFrm.flexValueId.value;
	if (isValid) {
		var action = "<%=WebActionConstant.CREATE_ACTION%>";
        if (flexValueId != "" && flexValueId > 0) {
			action = "<%=WebActionConstant.UPDATE_ACTION%>";
		}
		document.mainFrm.act.value = action;
		document.mainFrm.submit();
	}
}

function do_Back(){
	with(mainFrm){
		code.value = "";
		value.value = "";
		act.value = "<%=WebActionConstant.QUERY_ACTION%>";
		submit();
	}
}
</script>