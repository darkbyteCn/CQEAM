<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newasset.message.dto.FreeAssetsMsgSetAndQueryDTO"%>
<%
	FreeAssetsMsgSetAndQueryDTO dto = (FreeAssetsMsgSetAndQueryDTO)request.getAttribute(QueryConstant.QUERY_DTO);
	String msgCategoryId = dto.getMsgCategoryId();
%>
<html>
<head>
</head>
<body onClick="do_ControlResend();">
<form method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<script type="text/javascript">
    printTitleBar("短信提醒设置");
</script>
    <input type="hidden" name="act">
    <input type="hidden" name="msgCategoryId" value="<%=msgCategoryId%>">
    <table width="50%" align="center">
        <tr>
            <td width="15%" align="right" height="20">类别描述：</td>
            <td width="35%" height="20"><input type="text"  style="width:100%" name="msgDesc" class="input_style1" value="<%=dto.getMsgDesc()%>"></td>
        </tr>
        <tr>
            <td align="right" height="20">是否重发：</td>
            <td height="20"><%=dto.getNeedResendRadio()%></td>
        </tr>
        <tr>
            <td align="right" height="20">重发次数：</td>
            <td height="20"><input type="text" style="width:100%" name="resendMaxtimes" class="input_style1" value="<%=dto.getResendMaxtimes()%>"></td>
        </tr>
        <tr>
            <td align="right" height="20">间隔分钟：</td>
            <td height="20"><input type="text" style="width:100%" name="resendDistance" class="input_style1" value="<%=dto.getResendDistance()%>"></td>
        </tr>
        <tr>
            <td align="right" height="20">汇总发送：</td>
            <td height="20"><%=dto.getCollectSendRadio()%></td>
        </tr>
        <tr>
            <td align="right" height="20">公司名称：</td>
            <td height="20"><select class="select_style1" style="width:100%" name="organizationId"><%=dto.getOrganizationOption()%></select></td>
        </tr>
		<tr>
            <td align="right" height="20">是否有效：</td>
            <td height="20"><%=dto.getEnabledRadio()%></td>
        </tr>
        <tr>
            <td align="center" height="22" colspan="2">
            <img src="/images/eam_images/save.jpg" alt="保存" onClick="do_SaveMessage();">
            <img src="/images/eam_images/back.jpg" alt="关闭" onClick="do_TurnBack();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<script>
function do_SaveMessage(){
	with(mainFrm){
	    if(validateFrmData()){
		    act.value = '<%=AMSActionConstant.SAVE_ACTION%>';
		    action="/servlet/com.sino.ams.newasset.message.servlet.FreeAssetsMsgSetAndQueryServlet";
		    submit();
	    }
    }	
}

function do_ControlResend(){
	var radioBox = event.srcElement.name;
	if(radioBox == "needResend"){
		var needResend = getRadioValue("needResend");
		if(needResend == "<%=WebAttrConstant.RESEND_N%>"){
			mainFrm.resendMaxtimes.value = "";
			mainFrm.resendMaxtimes.disabled = true;
			mainFrm.resendMaxtimes.className = "";
			mainFrm.resendDistance.value = "";
			mainFrm.resendDistance.disabled = true;
			mainFrm.resendDistance.className = "";
		} else {
			mainFrm.resendMaxtimes.value = "";
			mainFrm.resendMaxtimes.disabled = false;
			mainFrm.resendMaxtimes.className = "noEmptyInput";
			mainFrm.resendDistance.value = "";
			mainFrm.resendDistance.disabled = false;
			mainFrm.resendDistance.className = "noEmptyInput";
		}
	}
}

function validateFrmData(){
	var isValid = false;
    var fieldNames = "msgDesc";
    var fieldLabels = "短消息类别描述";
    var validateType = EMPTY_VALIDATE;
    isValid = formValidate(fieldNames, fieldLabels, validateType);
    if(isValid){
		var needResend = getRadioValue("needResend");
		if(needResend == "<%=WebAttrConstant.RESEND_Y%>"){
			fieldNames = "resendMaxtimes;resendDistance";
			fieldLabels = "重发次数;重发间隔";
			validateType = POSITIVE_VALIDATE;
			isValid = formValidate(fieldNames, fieldLabels, validateType);
		}
    }
    return isValid;
}

function do_TurnBack(){
	window.location.href = "/servlet/com.sino.ams.newasset.message.servlet.FreeAssetsMsgSetAndQueryServlet";
}
</script>