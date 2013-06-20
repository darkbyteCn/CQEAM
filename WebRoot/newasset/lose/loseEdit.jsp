<%@page import="com.sino.ams.newasset.constant.AssetsLookUpConstant"%>
<%@page import="com.sino.ams.newasset.lose.constant.LoseAppConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="com.sino.ams.newasset.lose.constant.LoseURLListConstant" %>
<%@ page import="com.sino.ams.newasset.lose.dto.LoseDTO"%>
<%@page import="com.sino.ams.newasset.lose.dto.LoseHeaderDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<%  
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	
	LoseHeaderDTO headerDTO = null;
	LoseDTO loseDTO = null;
	loseDTO = (LoseDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
     
	headerDTO = loseDTO.getHeaderDTO();
    DTOSet lines = loseDTO.getLines(); 
    String transId = headerDTO.getTransId();
    
%>
<head>
	<title>资产挂失申请</title>
	 
    <script type="text/javascript" src="/WebLibary/js/test.js"></script> 
	<script type="text/javascript" src="/WebLibary/js/util.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
	<script type="text/javascript" src="/WebLibary/js/api.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript">
        printToolBar();
    </script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<%@ include file="/flow/flowNoButton.jsp" %>
<form action="<%= LoseURLListConstant.LOSE_SERVELT %>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
 
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="transType" value="<%=headerDTO.getTransType()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=headerDTO.getTransId()%>">
<input type="hidden" name="fromDept" id="fromDept"  value="<%=headerDTO.getFromDept()%>" >
<input type="hidden" name="act" value="">  
 <%@ include file="/flow/flowPara.jsp"%>

<div id="searchDiv" style="position:absolute;top:28px;left:1px;width:100%">
<jsp:include page="/newasset/lose/loseHeader.jsp" flush="true"/>
</div>
<%
    String att3 = info.getSfactTaskAttribute3();
    if( att3.equals( LoseAppConstant.ATT3_FILL_DATA ) ){
%>
    <div id="buttonDiv" style="position:absolute;top:135px;left:1px;width:100%">
        <img id="selectBtn" src="/images/eam_images/choose.jpg" alt="选择资产" onClick="do_SelectAssets(); return false;">
        <img id="delBtn" src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
    </div>
<%
    }
%>
	<jsp:include page="/newasset/lose/loseLine.jsp" flush="true"/>
</form>
 
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
function initPage() {
    window.focus();
    doLoad();
    do_SetPageWidth();
    var att3 = mainFrm.sf_task_attribute3.value;
	if( att3 != "<%=LoseAppConstant.ATT3_FILL_DATA%>" ){
		setFrmReadonly("mainFrm");
	}else{ 
		if( null == document.forms[0].fromDept.value || document.forms[0].fromDept.value == "" || document.forms[0].fromDept.value=="0" ||  document.forms[0].fromDeptName.value == ""  ){
			//alert( document.getElementById("app_dept_code").value );
			//alert( document.getElementById("app_dept_name").value );
			document.forms[0].fromDept.value=document.getElementById("app_dept_code").value; //流程组别名称
			document.forms[0].fromDeptName.value=document.getElementById("app_dept_name").value; //流程组别名称
			 
		}
	}
    do_ControlProcedureBtn();
}

function deleteLine() {
    var tab = document.getElementById("dataTable");
    deleteTableRow(tab, 'subCheck');
} 

/**
  * 功能：选择资产
 */
function do_SelectAssets() {
	var dataTable = document.getElementById("dataTable");
	var dialogWidth = 52;
	var dialogHeight = 29;
	
	<% 	
    	String lun="";
    	if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
    		lun = AssetsLookUpConstant.LOOK_TRANSFER_ASSETS_TD;
    	} else {
    		lun = AssetsLookUpConstant.LOOK_TRANSFER_ASSETS;
    	}
    %>
	var lookUpName = "<%=lun%>";
	var userPara = "transType=" + mainFrm.transType.value + "&deptCode=" + document.getElementById("fromDept").value;
    userPara += "&specialityDept=" + document.mainFrm.specialityDept.value;
 
	var assets = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (assets) {
		var data = null;
		for (var i = 0; i < assets.length; i++) {
			data = assets[i];
			appendDTO2Table(dataTable, data, false, "barcode");
		}
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



function do_ShowDetail(){
	
}

 function floatOnlyOnkeyDown(obj) {
//    style="ime-mode:disabled"
    var k = window.event.keyCode;
    if ((k == 46) || (k == 8) || (k == 189) || (k == 109) || (k == 190) || (k == 110) || (k >= 48 && k <= 57) || (k >= 96 && k <= 105) || (k >= 37 && k <= 40)) {
    } else if (k == 13 || k == 9) {
//        window.event.keyCode = 9;
    } else {
        window.event.returnValue = false;
    }
}
function floatOnlyOnKeyUp(obj) { 
    if(!isNumber(obj.value)){obj.value = ""}; 
}
       
function changeYearRent( obj ){
	var monthRent = formatNum( Number(obj.value) / 12 , 3 );
	var trObj = obj.parentElement.parentElement;
	trObj.childNodes[13].childNodes[0].value = monthRent;
}

function changeMonthRent( obj ){
	var yearRent = formatNum( Number(obj.value) * 12 , 3 );
	var trObj = obj.parentElement.parentElement;
	trObj.childNodes[12].childNodes[0].value = yearRent;
}
 

function do_Cancel_app(){
	var actObj = document.getElementById("act");
	actObj.value = "CANCEL_ACTION"; 
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
	document.forms[0].submit(); 
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}

function do_AppValidate() {
	var createdReason = mainFrm.createdReason.value;
    if( createdReason == "" ){
    	alert("请填写说明！");
    	return false;
    }
    
	var tab = document.getElementById("dataTable");
    var rowCount = tab.rows.length;
    
	if ( rowCount == 0 || (tab.rows[0].style.display == 'none' && rowCount == 1)) {
        alert("没有选择行数据，请选择行数据！");
        return false;
    }
    return true;
} 
 
</script>