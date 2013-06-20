<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.system.dict.dto.EtsFlexValueSetDTO"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>

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
<body onload="initPage();" onkeydown="autoExeFunction('do_SaveDictionary()');">
 <jsp:include page="/message/MessageProcess"/>
<%
    EtsFlexValueSetDTO flexValueSet = (EtsFlexValueSetDTO)request.getAttribute(WebAttrConstant.DICT_TYPE_DATA);
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.system.dict.servlet.EtsFlexValueSetServlet">
    <script type="text/javascript">
        printTitleBar("字典分类维护");
    </script>
    <table border="0" width="100%"  id="table1">
        <tr>
            <td width="25%" align="right" height="22">分类代码：</td>
            <td width="50%" align="left" height="22">
				<input type="text" name="code" size="40" class="input_style1" style="width:100%" value="<%=flexValueSet.getCode()%>"></td>
            <td width="25%" align="left" height="22">&nbsp;<font style="color: red">*</font> </td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">分类名称：</td>
            <td width="50%" align="left" height="22">
			<input type="text" name="name" size="40" class="input_style1" style="width:100%" value="<%=flexValueSet.getName()%>"></td>
            <td width="25%" align="left" height="22">&nbsp;<font style="color: red">*</font></td>
        </tr>

        <tr>
            <td width="25%" align="right" height="22">分类描述：</td>
            <td width="50%" align="left" height="22">
                <input type="text" name="description" size="40" class="input_style1"  style="width:100%" value="<%=flexValueSet.getDescription()%>">
            <td width="25%" align="left" height="22">&nbsp;<font style="color: red">*</font></td>
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
            <td width="25%" align="right" height="22">可否维护：</td>
            <td width="50%" align="left" height="22"><%=request.getAttribute(WebAttrConstant.MAINTAIN_RADIO)%></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="100%" align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveDictionary(); return false;">&nbsp;
                <img src="/images/eam_images/back.jpg" alt="返回" onClick="do_Back(); return false;"></td>
        </tr>

    </table>
	<input type="hidden" name="act" value="">
    <input type="hidden" name="flexValueSetId" value="<%=flexValueSet.getFlexValueSetId()%>">
</form>
</body>
</html>
<script>

function initPage(){
}

function do_SaveDictionary() {
	var fieldNames = "code;name";
	var fieldLabels = "分类代码;分类名称";
	var isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
	var flexValueSetId = mainFrm.flexValueSetId.value;
	if (isValid) {
		var action = "<%=WebActionConstant.CREATE_ACTION%>";
        if ( flexValueSetId != "" ) { // && flexValueSetId > 0
			action = "<%=WebActionConstant.UPDATE_ACTION%>";
		}
		mainFrm.act.value = action;
		mainFrm.submit();
	}
}

function do_Back(){
	with(mainFrm){
		code.value = "";
		name.value = "";
		act.value = "<%=WebActionConstant.QUERY_ACTION%>";
		submit();
	}
}
</script>