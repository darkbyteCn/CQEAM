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
        printTitleBar("基本信息维护>>网络层次属性维护--新增");
    </script>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:10%">   
	    <table border="0"  width="100%" id="table1" align = "center">
	        <tr>
	            <td width="30%" align="right" height="22">网络层次编码：&nbsp;</td>
	             <td width="50%" align="left" height="22">
	            	<input type="text" name = "lneCode" size = "50" class="input_style1" onblur="do_ValidateLneCode();">&nbsp;<font color="red">*</font>
	            </td>
	        </tr>
	        <tr>
	            <td width="30%" align="right" height="22">网络层次名称：&nbsp;</td>
	            <td width="50%" align="left" height="22">
	               <input type="text" name="lneName" class="input_style1" size="50">&nbsp;<font color="red">*</font>
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
<div align="center" id="warnExist" style="color:blue;visibility:hidden">对不起，该网络层次编码已存在!</div>
</body>
</html>
<script>
function do_Save() {
		if(mainFrm.lneCode.value != ""){
			if(mainFrm.lneName.value != ""){
				mainFrm.act.value = "<%=WebActionConstant.CREATE_ACTION%>";
				mainFrm.action = "/servlet/com.sino.ams.system.manydimensions.servlet.NleServlet";
				mainFrm.submit();
			}else{
				alert("网络层次名称不能为空!");
			}			
		}else{
			alert("网络层次编码不能为空!");
		}
}
var xmlHttp = null;

function do_ValidateLneCode() {
	if(mainFrm.lneCode.value != ""){
		var url = "/servlet/com.sino.ams.system.manydimensions.servlet.NleServlet";
		url += "?act=VALIDATE_ACTION"
		url += "&lneCode=" + mainFrm.lneCode.value;
		do_ProcessSimpleAjax(url, null);
	}
}

function do_ProcessResponse(responseContent){
	with(document){
		if(responseContent == "Y"){
			alert("您所输入的网络层次编码已存在！请重新输入！");
			mainFrm.lneCode.value = "";
			mainFrm.lneCode.focus();
		} 
	}
}
</script>

