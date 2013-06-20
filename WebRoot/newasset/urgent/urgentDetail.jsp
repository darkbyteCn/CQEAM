<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@page import="com.sino.ams.newasset.constant.AssetsWebAttributes"%>
<%@page import="com.sino.ams.newasset.urgenttrans.constant.UrgentURLListConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@page import="com.sino.ams.newasset.urgenttrans.dto.UrgentDTO"%>
<%@ page import="com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO" %>
<html>
<%  
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	
	UrgentHeaderDTO headerDTO = null;
	UrgentDTO leaseDTO = null;
	leaseDTO = (UrgentDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
     
	headerDTO = leaseDTO.getHeaderDTO();
    
%>
<head>
	<title>资产紧急调拨申请</title>
	 
    <script type="text/javascript" src="/WebLibary/js/test.js"></script> 
    <script type="text/javascript" src="/WebLibary/js/json.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
	<script type="text/javascript" src="/WebLibary/js/api.js"></script>
	<script type="text/javascript" src="/WebLibary/js/BarVar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>

	<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
   
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth();initPage();" style="overflow-y:scroll;">
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


<input type="hidden" name="act" value=""> 

<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/flow/include.jsp" flush="true"/>
<table border="0" class="queryTable" width="100%" style="border-collapse: collapse" id="table1">
	<tr>
		<td>
			<jsp:include page="/newasset/urgent/urgentHeader.jsp"></jsp:include> 
		</td>
	</tr>
</table> 
 <jsp:include page="/newasset/approveContent.jsp" flush="true"/>
 <table width="100%" id="buttonSet">
		    <tr>
		    	<td width="100%" align="left">
			        <img src="/images/eam_images/page_config.jpg" alt="打印设置" onClick="do_SetupPrint(); return false;">
			        <img src="/images/eam_images/print_view.jpg" alt="打印设置" onClick="do_PrevPrint(); return false;">
			        <img src="/images/eam_images/print.jpg" alt="打印" onClick="do_PrintOrder(); return false;">
			        <%--<img src="/images/eam_images/close.jpg" alt="关闭" onClick="javascript:window.focus();window.close();do_Close();">--%>
		    	</td>
		    </tr>
		</table>
		

<table border="0" class="queryTable" width="100%" style="border-collapse: collapse" id="table2">
	<tr>
		<td>
			<jsp:include page="/newasset/urgent/urgentLine2.jsp" flush="true"/>
		</td>
	</tr>
</table> 

</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
<object id="webbrowser" width="0" height="0" classid="clsid:8856f961-340a-11d0-a96b-00c04fd705a2"></object>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
function initPage() {
    window.focus();
	do_SetPageWidth(); 
	//var att3 = document.getElementById("sf_task_attribute3").value;
	
		setFrmReadonly("mainFrm");
		//document.getElementById( "selectBtn" ).style.display = "none";
		//document.getElementById( "delBtn" ).style.display = "none";
	//var fromGroup = mainFrm.fromGroup.value;
	//if(fromGroup == ""){
	//	do_SelectCreateGroup();
	//}
} 
 
 
function do_Save_app() {
	try{ 
		var actObj = document.getElementById("act");
		actObj.value = "SAVE_ACTION"; 
		document.forms[0].submit();
	}catch(ex){
		alert( ex.message );
	}finally{ 
	} 
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

function doAppSubmit(){
	if( myValid() ){
		do_Div_Complete_Start();
	}else{
		return;
	}
}

function doAppSave(){
	if( myValid() ){
		do_Save();
	}else{
		return;
	} 
}

function validate(){
	return myValid();
}

//验证
function myValid(){ 
	return validateHeader();
}

//验证头信息
function validateHeader(){
	var isValid = true;
	var validateType = EMPTY_VALIDATE;
	var fieldLabels = "调出地点;调出执行人;调入地点;调入执行人;归档人"
    var fieldNames = "fromObjectNo;implementBy;toObjectNo;toImplementBy;archivedBy";
    
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
	}else if(selCategory == 3){
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



function do_SetupPrint(){
	webbrowser.execwb(8,0);
}

function do_PrevPrint(){
	document.getElementById("buttonSet").style.display = "none";
	webbrowser.execwb(7,0);
	document.getElementById("buttonSet").style.display = "block";
}

function do_PrintOrder(){
	document.getElementById("buttonSet").style.display = "none";
	webbrowser.execwb(6,6);
	document.getElementById("buttonSet").style.display = "block";
}

function do_Close(){
	window.opener=null;
	window.close();
}
</script>