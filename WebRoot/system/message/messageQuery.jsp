<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@page import="com.sino.ams.bean.SyBaseSQLUtil"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%
	SfMsgCategoryDTO dto = (SfMsgCategoryDTO)request.getAttribute(QueryConstant.QUERY_DTO);
    DTOSet dtos = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (dtos != null && !dtos.isEmpty());
%>
<html>
<head>
</head>
<body onkeydown="autoExeFunction('do_Search()');" onload="do_SetPageWidth();">
<form method="post" name="mainFrm" action="/servlet/com.sino.ams.system.message.servlet.SfMsgCategoryServlet">
<jsp:include page="/message/MessageProcess"/>
<script type="text/javascript">
    printTitleBar("短信息设置")
</script>
	<table width="100%" border="0" >
        <tr height="22">
            <td width="10%" align="right">公司名称：</td>
            <td width="26%"><select class="input_style1" style="width:100%; height:21" name="organizationId"><%=dto.getOrganizationOption()%></select></td>
            <td width="10%" align="right">类别描述：</td>
            <td width="26%" align="right"><input type="text" class="input_style1" style="width:100%" name="msgDesc" value="<%=dto.getMsgDesc()%>"></td>
            <td width="28%" align="right" rowspan="2">
            <img src="/images/eam_images/search.jpg" id="queryImg" onclick="do_Search();" alt="查询">
<%
	if(dto.hasInitPrivi()){
%>            
            <img src="/images/eam_images/init.jpg" id="initImg"  alt="初始化" onclick="do_initSMS();">
<%
	}
%>            
			</td>
        </tr>
        <tr height="22">
            <td width="10%" align="right">是否重发：</td>
            <td width="26%"><%=dto.getNeedResendRadio()%></td>
            <td width="10%" align="right">汇总发送：</td>
            <td width="26%"><%=dto.getCollectSendRadio()%></td>
        </tr>
	</table>
	
	
	
	
    <input type="hidden" name="act">
</form>
	<div id="headDiv" style="overflow:hidden;position:absolute;top:67px;left:1px;width:100%">
		<table class="headerTable" border="1" width="100%">
	        <tr height="20">
	            <td width="15%" align="center">公司名称</td>
	            <td width="45%" align="center">类别描述</td>
	            <td width="8%" align="center">是否重发</td>
	            <td width="8%" align="center">重发次数</td>
	            <td width="8%" align="center">间隔分钟</td>
	            <td width="8%" align="center">汇总发送</td>
	            <td width="8%" align="center">有效性</td>
	        </tr>
	    </table>
	</div>
<%
    int dtoCount = 0;
    if (hasData) {
%>
	<div id="dataDiv" style="overflow:scroll;height:70%;width:850px;position:absolute;top:88px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%	    dtoCount = dtos.getSize();
    	SfMsgCategoryDTO message = null;
    	int msgCategoryId = SyBaseSQLUtil.NULL_INT_VALUE;
        for (int i = 0; i < dtoCount; i++) {
            message = (SfMsgCategoryDTO)dtos.getDTO(i);
            msgCategoryId = message.getMsgCategoryId();
%>
	        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'" title="点击查看或修改本条短信设置" onClick="do_ShowDetail('<%=msgCategoryId%>')">
	            <td width="15%"><%=message.getCompanyName()%></td>
	            <td width="45%"><%=message.getMsgDesc()%></td>
	            <td width="8%" align="center"><%=message.getNeedResend()%></td>
	            <td width="8%" align="right"><%=message.getResendMaxtimes()%></td>
	            <td width="8%" align="right"><%=message.getResendDistance()%></td>
	            <td width="8%" align="center"><%=message.getCollectSend()%></td>
	            <td width="8%" align="center"><%=message.getEnabled()%></td>
	        </tr>
<%
        }
%>
    	</table>
	</div>
<div style="position:absolute;top:448px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
</body>
</html>
<script type="text/javascript">
var count = <%=dtoCount%>;

function do_Search() {
    mainFrm.act.value = '<%=AMSActionConstant.QUERY_ACTION%>';
    mainFrm.action="/servlet/com.sino.ams.system.message.servlet.SfMsgCategoryServlet";
    mainFrm.submit();
}

function do_ShowDetail(msgCategoryId) {
    mainFrm.act.value = '<%=AMSActionConstant.DETAIL_ACTION%>';
    mainFrm.action="/servlet/com.sino.ams.system.message.servlet.SfMsgCategoryServlet?msgCategoryId=" + msgCategoryId;
    mainFrm.submit();
}

function do_Save() {
	with(mainFrm){
	    if(validateFrmData()){
		    act.value = '<%=AMSActionConstant.SAVE_ACTION%>';
		    action="/servlet/com.sino.ams.system.message.servlet.SfMsgCategoryServlet";
		    submit();
	    }
    }
}

function do_initSMS(){
    with(mainFrm){
        act.value = '<%=AMSActionConstant.INIT_ACTION%>';
        action="/servlet/com.sino.ams.system.message.servlet.SfMsgCategoryServlet";
        submit();
    }
}

function validateFrmData(){
	var isValid = false;
    var idTemplate = "resendMaxtimes$;resendDistance$";
    var fieldLabels = "重发次数;时间间隔";
    var validateType = POSITIVE_INT_VALIDATE;
    var fieldIds = "";
    var enabledCheckObj = null;
    var needResendCheckObj = null;
    var resendMaxtimesObj = null;
    var resendDistanceObj = null;
    var notCheckCount = 0;
    for (var i = 0; i < count; i++) {
    	enabledCheckObj = document.getElementById("enabledCheck" + i);
    	needResendCheckObj = document.getElementById("needResendCheck" + i);
    	resendMaxtimesObj = document.getElementById("resendMaxtimes" + i);
    	resendDistanceObj = document.getElementById("resendDistance" + i);
    	if(enabledCheckObj.checked && needResendCheckObj.checked){
	        fieldIds = replaceStr(idTemplate, "$", i);
	        isValid = formValidateById(fieldIds, fieldLabels, validateType);
	        if (!isValid) {
	            break;
	        }
        } else {
        	notCheckCount++;
        	resendMaxtimesObj.value = "";
        	resendDistanceObj.value = "";
        }
    }
    if(notCheckCount == count){
    	isValid = true;
    }
    if(isValid){
		with(document){
			for(var i = 0; i < count; i++){
				getElementById("resendMaxtimes" + i).disabled = false;
				getElementById("resendDistance" + i).disabled = false;
			}
		}
    }
    return isValid;
}

var enableChecked = false;
function do_ControlEnable(chkObj){
	var chkId = chkObj.id;
	var chkName = chkObj.name;
	chkId = chkId.substring(chkName.length);
	enableChecked = chkObj.checked;
	var disableProp = true;
	var enableValue = "N";
	if(enableChecked){
		disableProp = false;
		enableValue = "Y";
	}
	with(document){
		getElementById("needResendCheck" + chkId).disabled = disableProp;
		getElementById("noticeAgentCheck" + chkId).disabled = disableProp;
		getElementById("noticeApplyerCheck" + chkId).disabled = disableProp;
		getElementById("noticeVendorCheck" + chkId).disabled = disableProp;
		do_ControlResend(getElementById("needResendCheck" + chkId));
		getElementById("enabled" + chkId).value = enableValue;

	}
}

function do_ControlResend(chkObj){
	var chkId = chkObj.id;
	var chkName = chkObj.name;
	chkId = chkId.substring(chkName.length);
	var chkProp = chkObj.checked;
	
	var disableProp = true;
	var enableValue = "N";
	var classProp = "readonlyInput";
	if(enableChecked && chkProp){
		disableProp = false;
		enableValue = "Y";
		classProp = "";
	}
	var resendTimeObj = null;
	var resendDistObj = null;
	with(document){
		resendTimeObj = getElementById("resendMaxtimes" + chkId);
		resendTimeObj.disabled = disableProp;
		resendTimeObj.className = classProp;

		resendDistObj = getElementById("resendDistance" + chkId);
		resendDistObj.disabled = disableProp;
		resendDistObj.className = classProp;

		getElementById("needResend" + chkId).value = enableValue;
	}
}

function do_ControlValue(chkObj){
	var chkId = chkObj.id;
	var chkName = chkObj.name;
	chkId = chkId.substring(chkName.length);
	chkName = chkName.substring(0, chkName.indexOf("Check"));
	chkId = chkName + chkId;
	var chkValue = "N";
	if(chkObj.checked){
		chkValue = "Y";
	}
	document.getElementById(chkId).value = chkValue;
}
</script>