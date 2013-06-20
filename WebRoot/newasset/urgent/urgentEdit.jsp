<%@page import="com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant"%>
<%@page import="com.sino.ams.newasset.urgenttrans.constant.UrgentURLListConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newasset.urgenttrans.dto.UrgentDTO"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<%  
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    boolean isDeptAssetsManager = userAccount.isDptAssetsManager();
	
	UrgentHeaderDTO headerDTO = null;
	UrgentDTO leaseDTO = null;
	leaseDTO = (UrgentDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
     
	headerDTO = leaseDTO.getHeaderDTO();
    String transId = headerDTO.getTransId();
    
%>
<head>
	<title>资产紧急调拨申请</title>
	 
	<script type="text/javascript" src="/WebLibary/js/util.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
	
    <script type="text/javascript">
        printToolBar();
    </script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<input type="hidden" name="helpId" value="">
<%@ include file="/flow/flowNoButton.jsp" %>
<form action="<%= UrgentURLListConstant.URGENT_SERVELT %>" method="post" name="mainFrm">
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">

<input type="hidden" name="transType" value="<%=headerDTO.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=headerDTO.getTransId()%>">
<input type="hidden" name="fromDept" value="<%=headerDTO.getFromDept()%>">
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup() %>">
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>">

<input type="hidden" name="fromObjectNo" value="<%=headerDTO.getFromObjectNo()%>">
<input type="hidden" name="toObjectNo" value="<%=headerDTO.getToObjectNo()%>">
<input type="hidden" name="implementBy" value="<%=headerDTO.getImplementBy()%>">
<input type="hidden" name="toImplementBy" value="<%=headerDTO.getToImplementBy()%>">
<input type="hidden" name="archivedBy" value="<%=headerDTO.getArchivedBy()%>">
<input type="hidden" name="isDeptAssetsManager" id="isDeptAssetsManager" value="<%=isDeptAssetsManager%>">


<input type="hidden" name="act" value=""> 

<jsp:include page="/message/MessageProcess"/>
 <%@ include file="/flow/flowPara.jsp"%>
<table border="0" class="queryTable" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<jsp:include page="/newasset/urgent/urgentHeader.jsp"/>
		</td>
	</tr>
</table> 
 
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
var att3 = document.getElementById("sf_task_attribute3").value;

function initPage() {
    window.focus();
	do_SetPageWidth(); 
    doLoad();
	if( att3 == "<%=UrgentAppConstant.ATT3_FILL_DATA%>" ){
		otherInit();
	}else{
		setFrmReadonly("mainFrm");
	}
	do_ControlProcedureBtn();
    helpInit('4.6.1');
}
 


/**
 *功能:设置表单内所有元素不可用
 *参数:表单名
 */
function setFrmReadonly(frm) {
    var frmObj = eval('document.' + frm);
    for (var i = 0; i < frmObj.length; i++) {
    	var obj = frmObj.elements[i];
    	var objType = obj.type;
        var fieldType = obj.type;
        obj.readOnly = true; 
        if( objType == "text" || objType == "password" || objType == "textarea" ){
        	obj.onclick = null;
        } 
        
        if( fieldType == "checkbox" ){
        	obj.disabled = true;
        }
    }
} 

function do_AppValidate() {
    return validateHeader();
}

//验证头信息
function validateHeader(){
	var isValid = true;
	var validateType = EMPTY_VALIDATE;
	var fieldLabels = "调出地点;调出执行人;调入地点;调入执行人;归档人"
    var fieldNames = "fromObjectNo;implementByName;toObjectNo;toImplementByName;archivedByName";
    
    isValid = formValidate(fieldNames, fieldLabels, validateType);
    return isValid;
}
  
function do_ShowDetail(){
	
}



function do_SelectFromLocation(){
    var fromOrganizationId = mainFrm.fromOrganizationId.value; 
	var deptCode = mainFrm.fromDept.value;
	 
	var userPara = "organizationId=" + fromOrganizationId + "&transferType=INN_DEPT&deptCode=" + deptCode; 
	var lookUpName = "LOOK_UP_ADDRESS";
	
	var dialogWidth = 55;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
 
	if (objs) {
		var obj = objs[0];
		document.getElementById("fromObjectNo" ).value = obj["toObjectNo"];
		document.getElementById("fromObjectName" ).value = obj["toObjectName"];
		//document.getElementById("addressId" + idNumber).value = obj["addressId"];
		//lineBox.value = obj["toObjectName"];
	} else {
		document.getElementById("fromObjectNo" ).value = "";
		document.getElementById("fromObjectName" ).value = ""; 
	} 
}

//选择组合调入地点
function do_ChoseLocDesc() {
   	var url = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet?act=CHOSE_LOCDESC_ACTION&organizationId=" + mainFrm.fromOrganizationId.value;
	url += "&workorderObjectNo=" + document.getElementById("toObjectNo").value;	
    //url += "&deptCode=" + mainFrm.fromDept.value;
	var returnValue = do_ChoseLocData(url);
    var addressChk = mainFrm.allLocation;
	if (returnValue) {
		document.getElementById("toObjectName").value = returnValue.split(";")[0];
           document.getElementById("toObjectNo").value = returnValue.split(";")[1];
	} else {
		document.getElementById("toObjectName").value = "";
           document.getElementById("toObjectNo").value = "";
	}
}

function do_ChoseLocData(url) {	
    var factor = 0.5;
    var dialogWidth = window.screen.availWidth * factor;
    var dialogHeight = window.screen.availHeight * factor;
    var dialogStyle = "dialogWidth:"
            + dialogWidth
            + "px;dialogHeight:"
            + dialogHeight
            + "px;center:yes;status:no;scrollbars:no;help:no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scroll=no";
    return window.showModalDialog(url,"",dialogStyle);
}

//选择调入地点
function do_SelectToLocation(){
    var fromOrganizationId = mainFrm.fromOrganizationId.value; 
	var deptCode = mainFrm.fromDept.value;
	 
	var userPara = "organizationId=" + fromOrganizationId + "&transferType=INN_DEPT&deptCode=" + deptCode; 
	var lookUpName = "LOOK_UP_ADDRESS";
	
	var dialogWidth = 55;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
 
	if (objs) {
		var obj = objs[0];
		document.getElementById("toObjectNo" ).value = obj["toObjectNo"];
		document.getElementById("toObjectName" ).value = obj["toObjectName"];
		//document.getElementById("addressId" + idNumber).value = obj["addressId"];
		//lineBox.value = obj["toObjectName"];
	} else {
		document.getElementById("toObjectNo" ).value = "";
		document.getElementById("toObjectName" ).value = ""; 
	} 
}


function do_SelectImplement(){
	do_SelectUser( 1 );
}
 
function do_SelectToImplement(){
	do_SelectUser( 2 );
}



/**
  * 功能：选择执行人、归档人
 */
function do_SelectUser( selCategory ){
	var lookUpName = "LOOK_UP_USER";
	var dialogWidth = 44;
	var dialogHeight = 29;
	var fromOrganizationId = mainFrm.fromOrganizationId.value; 
	var userPara = "organizationId=" + fromOrganizationId + "&groupId=" + mainFrm.fromGroup.value;
	
	var userIdName = "implementBy";
	var userNameName = "implementByName";
	if(selCategory == 1){
		userIdName = "implementBy";
		userNameName = "implementByName";
	} else if(selCategory == 2){
		userIdName = "toImplementBy";
		userNameName = "toImplementByName";
	} else if(selCategory == 3){
		lookUpName = "LOOK_UP_USER_ACHIEVE";
		userIdName = "archivedBy";
		userNameName = "archivedByName";
	}
	
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	 
	if (objs) {
		var obj = objs[0];
		document.getElementById(userIdName ).value = obj["userId"];
		document.getElementById(userNameName ).value = obj["userName"];
	} else {
		document.getElementById(userIdName ).value = "";
		document.getElementById(userNameName ).value = "";
	} 
}


/**
 *  设置值
 */
function otherInit(){
	if( att3 == "<%=UrgentAppConstant.ATT3_FILL_DATA%>" ){
		try{ 
			if( null == document.forms[0].fromDept.value || document.forms[0].fromDept.value == "" ||  document.forms[0].fromGroup.value == ""  ){
		 		document.forms[0].fromGroup.value=document.getElementById("flow_group_id").value; //流程组别名称
				//document.forms[0].fromGroupName.value=document.getElementById("flow_group_name").value; //流程组别名称
				document.forms[0].fromDept.value=document.getElementById("app_dept_code").value; //流程组别名称
				document.forms[0].fromDeptName.value=document.getElementById("flow_group_name").value; //流程组别名称
			}
			/**
			if( document.forms[0].sf_group.value == "*.*" ){
				alert( document.forms[0].sf_group.value );
				document.forms[0].sf_group.value = document.getElementById("flow_group_name").value;
			}
			**/
		}catch(ex){
			alert( ex.message );
		}
	}
} 
 
function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}

function do_Cancel_app(){
	isSave = true;
	var actObj = document.getElementById("act");
	actObj.value = "CANCEL_ACTION"; 
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
	document.forms[0].submit(); 
}
 
</script>