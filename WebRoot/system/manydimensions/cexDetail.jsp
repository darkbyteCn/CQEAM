<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant"%>
<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.ams.match.amselementmatch.dto.AmsElementMatchDTO"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<head>
    <title>网络层次属性新增</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<body>
 <jsp:include page="/message/MessageProcess"/>
<%
	AmsElementMatchDTO aemDTO = (AmsElementMatchDTO) request.getAttribute(WebAttrConstant.AMS_ELEMENT_MATCH_DTO);
%>

<form name="mainFrm" method="POST">
 	<script language="javascript">
        printTitleBar("基本信息维护>>投资分类属性维护--新增");
    </script>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:10%">   
	    <table border="0"  width="100%" id="table1" align = "center">
	    	<tr>
	            <td width="30%" align="right" height="22">投资大类：&nbsp;</td>
	             <td width="50%" align="left" height="22">
	            	<input type="text" name = "investCategory1" size = "50" class="input_style1" >&nbsp;<font color="red">*</font>
	            </td>
	        </tr>
	        <tr>
	            <td width="30%" align="right" height="22">投资中类：&nbsp;</td>
	            <td width="50%" align="left" height="22">
	                <input type="text" name="investCategory2" class="input_style1" size="50">&nbsp;<font color="red">*</font>
	            </td>
	        </tr>
	        <tr>
	            <td width="30%" align="right" height="22">投资分类代码：&nbsp;</td>
	             <td width="50%" align="left" height="22">
	            	<input type="text" name = "investCatCode" size = "50" class="input_style1" onblur="do_ValidateCode();">&nbsp;<font color="red">*</font>
	            </td>
	        </tr>
	        <tr>
	            <td width="30%" align="right" height="22">投资分类名称：&nbsp;</td>
	            <td width="50%" align="left" height="22">
	             <input type="text" name="investCatName" class="input_style1" size="50">&nbsp;<font color="red">*</font>
	            </td>
	        </tr>
	     </table>
	    <p align = "center">
	    	<img src="/images/eam_images/save.jpg" alt="保存" onClick="do_Save(); return false;">&nbsp;&nbsp;
	        <img src="/images/eam_images/back.jpg" alt="取消" onClick="history.go(-1);">
	    </p>
		<input type="hidden" name="act" value="">
	</div>
</form>
</body>
</html>
<script>
function do_Save() {
	if(mainFrm.investCategory1.value != ""){
		if(mainFrm.investCategory2.value != ""){
			if(mainFrm.investCatCode.value != ""){
				if(mainFrm.investCatName.value != ""){
					mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
					mainFrm.action = "/servlet/com.sino.ams.system.manydimensions.servlet.CexServlet";
					mainFrm.submit();
				}else{
					alert("投资分类名称不能为空！");
				}			
			}else{
				alert("投资分类代码不能为空！");
			}
		} else {
			alert("投资中类不能为空！");
		}
	} else {
		alert("投资大类不能为空！");
	}
}
var xmlHttp = null;

function do_ValidateCode() {
	if(mainFrm.investCatCode.value != ""){
		var url = "/servlet/com.sino.ams.system.manydimensions.servlet.CexServlet";
		url += "?act=VALIDATE_ACTION"
		url += "&investCatCode=" + mainFrm.investCatCode.value;
		do_ProcessSimpleAjax(url, null);
	}
}

function do_ProcessResponse(responseContent){
	with(document){
		if(responseContent == "Y"){
			alert("您所输入的投资分类代码已存在！请重新输入！");
			mainFrm.investCatCode.value = "";
			mainFrm.investCatCode.focus();
		} 
	}
}
</script>

