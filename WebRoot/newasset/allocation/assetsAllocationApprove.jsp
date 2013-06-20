<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-3-31
  Time: 14:09:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    int orgId = userAccount.getOrganizationId();
    int toOrgId = headerDTO.getToOrganizationId();
    String isTd = userAccount.getIsTd();
    int provinceOrgId = SessionUtil.getServletConfigDTO(session).getProvinceOrgId();

%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/DefaultLocationProcess.js"></script>
</head>
<script type="text/javascript">
    printToolBar();
</script>
<%@ include file="/flow/flowNoButton.jsp"%>
<body  leftmargin="0" topmargin="0" onload="initPage1();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<form action="<%=AssetsURLList.ASSETS_ALLOCATION_APPROVE_SERVLET%>" method="post" name="mainFrm">
<%@ include file="/flow/flowPara.jsp" %>
<input type="hidden" name="act" value="">
<input type="hidden" name="transType" value="<%= headerDTO.getTransType()%>">
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>">
<input type="hidden" name="faContentCode" value="<%=headerDTO.getFaContentCode()%>">
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="toOrganizationId" value="<%=headerDTO.getToOrganizationId()%>">
<input type="hidden" name="created" value="<%=headerDTO.getCreated()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=headerDTO.getTransId()%>">
<input type="hidden" name="isDepAM" value="">
<input type="hidden" name="transStatus" value="<%=headerDTO.getTransStatus()%>">
<input type="hidden" name="isMtlAssetsManager" id="isMtlAssetsManager" value="<%=userAccount.isMtlAssetsManager()%>">
<input type="hidden" name="faContentCode" value="">
<input type="hidden" name="isTd" value="<%=isTd%>">
<input type="hidden" name="provinceOrgId" id="provinceOrgId" value="<%=provinceOrgId%>">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/newasset/allocation/assetsAllocationHeaderDetail.jsp" flush="true"/>
<div id="searchDiv" style="position:absolute;top:135px;left:1px;width:100%">
<%
		if(headerDTO.getAttribute3().equals(AssetsDictConstant.NEW_TAG_NODE) && headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP)){
%>
		<img src="/images/eam_images/gen_tag.jpg" id="newTagImg" alt="生成新标签" onClick="do_ProduceNewTag(); return false;">
<%
		}
%>

<%
	if((!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT_RFU))
	     && (
	          (headerDTO.getAttribute2().equals(AssetsDictConstant.EDIT_ACCOUNT))
	         || (headerDTO.getAttribute1().equals("DEPT") && headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_DEPT))
	     )
	){
%>
         统一设置：
<%
	}
%>
<%
		if(headerDTO.getAttribute2().equals(AssetsDictConstant.EDIT_ACCOUNT)){
%>

			<input type="checkbox" name="allLocation" id="allLocation"><label for="allLocation">调入地点</label>
			<input type="checkbox" name="allUser" id="allUser"><label for="allUser">新责任人</label>
			<input type="checkbox" name="allAccount" id="allAccount"><label for="allAccount">新折旧账户</label>
            <input type="checkbox" name="deptToSite" id="deptToSite"><label for="deptToSite">根据调入部门过虑调入地点</label>
<%
		}
%>
<%
        if (headerDTO.getAttribute1().equals("DEPT") && headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_DEPT)) {
%>
            <input type="checkbox" name="defaultLocation" id="defaultLocation" onclick="do_SetDefaultLocation();"><label for="defaultLocation">默认地点</label>
			<input type="checkbox" name="allLocation" id="allLocation"><label for="allLocation">调入地点</label>
			<input type="checkbox" name="allUser" id="allUser"><label for="allUser">新责任人</label>
<%
        }
%>
</div>
<jsp:include page="/newasset/allocation/assetsAllocationLineDetail.jsp" flush="true"/>
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
</body>
</html>
<script type="text/javascript">
var transferType = "";
if(mainFrm.toOrganizationId){
    srcToOrgValue = mainFrm.toOrganizationId.value;
}
if(mainFrm.fromDept){
	srcDeptValue = mainFrm.fromDept.value;
}
if(mainFrm.transferType){
	transferType = mainFrm.transferType.value;
}

function initPage1() {
    window.focus();
    do_SetPageWidth();
    doLoad();
    do_ControlProcedureBtn();
    do_FormatQuantity();
    do_ComputeSummary();
}

function do_FormatQuantity(){
    var tab = document.getElementById("dataTable");
    if(tab){
        var rows = tab.rows;
        if(rows){
            for(var i = 0; i < rows.length; i++){
                var tr = rows[i];
                var node = getTrNode(tr, "currentUnits");
                if(node){
                    var currentUnits = node.value;
                    currentUnits = formatNum(currentUnits, 0);
                    node.value = currentUnits;
                }
            }
        }
    }
}

function do_ComputeSummary(){
    var transferType = mainFrm.transferType.value;
    var transType = mainFrm.transType.value;
    if((transferType != "BTW_COMP") && (transType != "ASS-DIS")){
        return;
    }
    var dataTable = document.getElementById("dataTable");
    var rows = dataTable.rows;
    if(rows != undefined){
        var rowCount = rows.length;
        var idArr = new Array("numValue", "yuanzhiValue", "ljzjValue", "jzzbValue", "jingerValue");
        var summaryCell = new Array("currentUnits", "cost", "depreciation", "impairReserve", "deprnCost");
        var idCount = idArr.length;
        var sumValueArr = new Array();
        for(var i = 0; i < rowCount; i++){
            var tr =  rows[i];
            for(var j = 0; j < idCount; j++){
                var node = getTrNode(tr, summaryCell[j]);
                if(!sumValueArr[j]){
                    sumValueArr[j] = 0;
                }
                sumValueArr[j] += Number(node.value);
            }
        }
        for(j = 0; j < idCount; j++){
            node = document.getElementById(idArr[j]);
            if(j == 0){
                node.value = sumValueArr[j];
            } else {
                node.value = formatNum(sumValueArr[j], 2);
            }
        }
    }
}

function do_ExportOrder(){
    mainFrm.action = "<%=AssetsURLList.ORDER_QUERY_SERVLET%>";
	mainFrm.act.value = "<%=AssetsActionConstant.EPT_DTL_ACTION%>";
	mainFrm.submit();
}

function do_Complete_app_yy() {
    mainFrm.act.value = "<%=AssetsActionConstant.APPROVE_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_ProduceNewTag() {
    var tagObjs = document.getElementsByName("barcode");
	var baocodeCount = tagObjs.length;
	var url = "/servlet/com.sino.ams.newasset.servlet.NewTagProduceServlet";
	url += "?fromOrganizationId=" + mainFrm.fromOrganizationId.value;
	url += "&toOrganizationId=" + mainFrm.toOrganizationId.value;
	url += "&transNo=" +  mainFrm.transNo.value;
	url += "&transId=" +  mainFrm.transId.value;
    url += "&count=" + baocodeCount;
	do_ProcessSimpleAjax(url, null);
}

function do_ProcessResponse(responseContent){
	var newTagObjs = document.getElementsByName("newBarcode");
	var responArr = responseContent.split("&&&");
	var barcodeStr = "";
	var bacodeCount = newTagObjs.length;
	var oldBarcode = "";
	for(var i = 0; i < bacodeCount; i++){
        barcodeStr = responArr[i];
        newTagObjs[i].value = barcodeStr;
	}
	document.getElementById("newTagImg").style.display = "none";
}

//选择组合调入地点
function do_ChoseLocDesc(linkObj) {
    while(linkObj.tagName != "TR"){
        linkObj = linkObj.parentNode;
    }
	var toOrganizationId = mainFrm.toOrganizationId.value;
	var url = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet?act=CHOSE_LOCDESC_ACTION&organizationId=" + toOrganizationId;
    var assignedToLocation = getTrNode(linkObj, "assignedToLocation");
	url += "&workorderObjectNo=" + assignedToLocation.value;
	var deptToSite=false;
	var returnValue = do_ChoseLocData(url);
    var addressChk = mainFrm.allLocation;
	if(!addressChk.checked){
		if (returnValue) {
            getTrNode(linkObj, "assignedToLocationName").value = returnValue.split(";")[0];
            getTrNode(linkObj, "assignedToLocation").value = returnValue.split(";")[1];
            getTrNode(linkObj, "addressId").value = returnValue.split(";")[2];
		} else {
            getTrNode(linkObj, "assignedToLocationName").value = "";
            getTrNode(linkObj, "assignedToLocation").value = "";
            getTrNode(linkObj, "addressId").value = "";
		}
	} else {
		var obj1 = null;
		var emptyData = false;
		if (returnValue) {
			emptyData = false;
		} else {
			emptyData = true;
		}
		var addressNames = document.getElementsByName("assignedToLocationName");
		var addressNos = document.getElementsByName("assignedToLocation");
		var addressIds = document.getElementsByName("addressId");
		var count = addressNames.length;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		var checkedCount = getCheckedBoxCount("subCheck");
		for(var i = 0; i < count; i++){
			if(checkedCount > 0){
				row = rows[i];
				checkObj = row.childNodes[0].childNodes[0];
				if(!checkObj.checked){
					continue;
				}
			}
			if(emptyData){
				addressNames[i].value = "";
				addressNos[i].value = "";
				addressIds[i].value = "";
			} else {
				addressNames[i].value = returnValue.split(";")[0];
				addressNos[i].value = returnValue.split(";")[1];
				addressIds[i].value = returnValue.split(";")[2];
			}
		}
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
function do_SelectLocation(lineBox){
	var toOrganizationId = mainFrm.toOrganizationId.value;
	var transferType = mainFrm.transferType.value;
	var deptCode = "";
	var objName = lineBox.name;
	var objId = lineBox.id;
	var idNumber = objId.substring(objName.length);
    var deptToSite=false;
    if(document.getElementById("deptToSite")){
        if(document.getElementById("deptToSite").checked){
        //部门间调拨根据调入部门过虑调入地点
            deptToSite=true;
        }
    }
	var userPara = "organizationId=" + toOrganizationId + "&transferType=" + transferType + "&deptCode=" + deptCode;
    if(deptToSite){
        userPara = "organizationId=" + toOrganizationId + "&transferType=" + transferType + "&deptCode=" + deptCode+ "&deptToSite=1";;
    }
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	var addressChk = mainFrm.allLocation;
    if(!addressChk){
        return;
	}
	if(!addressChk.checked){
        if (objs) {
			var obj = objs[0];
			document.getElementById("assignedToLocation" + idNumber).value = obj["toObjectNo"];
			document.getElementById("addressId" + idNumber).value = obj["addressId"];
			lineBox.value = obj["toObjectName"];
        } else {
			document.getElementById("assignedToLocation" + idNumber).value = "";
			document.getElementById("addressId" + idNumber).value = "";
			lineBox.value = "";
        }
	} else {
		var obj = null;
		var emptyData = false;
		if (objs) {
			obj = objs[0];
		} else {
			emptyData = true;
		}
		var addressNames = document.getElementsByName("assignedToLocationName");
        var addressNos = document.getElementsByName("assignedToLocation");
		var addressIds = document.getElementsByName("addressId");
		var count = addressNames.length;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		var checkedCount = getCheckedBoxCount("subCheck");
		for(var i = 0; i < count; i++){
			if(checkedCount > 0){
				row = rows[i];
				checkObj = row.childNodes[0].childNodes[0];
				if(!checkObj.checked){
					continue;
				}
			}
			if(emptyData){
				addressNames[i].value = "";
				addressNos[i].value = "";
				addressIds[i].value = "";
			} else {
				addressNames[i].value = obj["toObjectName"];
				addressNos[i].value = obj["toObjectNo"];
				addressIds[i].value = obj["addressId"];
			}
		}
	}
}


//选择新责任人
function do_SelectPerson(lineBox){
	var toOrganizationId = mainFrm.toOrganizationId.value;
	var transferType = mainFrm.transferType.value;

	if(toOrganizationId == ""){
		alert("请先选择调往公司，再选择新责任人。");
		return;
	}
	var objName = lineBox.name;
	var objId = lineBox.id;
	var idNumber = objId.substring(objName.length);
	var deptCode = "";
	if(transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>"){
		deptCode = mainFrm.fromDept.value;
	} else {
		deptCode = document.getElementById("responsibilityDept" + idNumber).value;
		if(deptCode == ""){
			alert("请先选择调往部门，再选择新责任人");
			var deptObj = document.getElementById("responsibilityDeptName" + idNumber);
			do_SelectDept(deptObj);
			return;
		}
	}
	var userPara = "organizationId=" + toOrganizationId + "&transferType=" + transferType + "&deptCode=" + deptCode;
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_RECIIVER%>";
	var dialogWidth = 47;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	var userChk = mainFrm.allUser;
	if(!userChk){
		return;
	}

	if(!userChk.checked){
		if (objs) {
			var obj = objs[0];
			document.getElementById("responsibilityUser" + idNumber).value = obj["employeeId"];
			lineBox.value = obj["userName"];
		} else {
			document.getElementById("responsibilityUser" + idNumber).value = "";
			lineBox.value = "";
		}
	} else {
		var obj = null;
		var emptyData = false;
		if (objs) {
			obj = objs[0];
		} else {
			emptyData = true;
		}
		var userNames = document.getElementsByName("responsibilityUserName");
		var users = document.getElementsByName("responsibilityUser");
		var count = userNames.length;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		var checkedCount = getCheckedBoxCount("subCheck");
		for(var i = 0; i < count; i++){
			if(checkedCount > 0){
				row = rows[i];
				checkObj = row.childNodes[0].childNodes[0];
				if(!checkObj.checked){
					continue;
				}
			}
			if(emptyData){
				userNames[i].value = "";
				users[i].value = "";
			} else {
				userNames[i].value = obj["userName"];
				users[i].value = obj["employeeId"];
			}
		}
	}
}

function do_SelectDepreciationAccount(lineBox){
    var toOrganizationId = mainFrm.toOrganizationId.value;
	var objName = lineBox.name;
	var objId = lineBox.id;
	var idNumber = objId.substring(objName.length);
	var userPara = "organizationId=" + toOrganizationId;
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ACCOUNT%>";
	var dialogWidth = 51;
	var dialogHeight = 29;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	var accountChk = mainFrm.allAccount;
    if(!accountChk.checked){
        if (objs) {
            obj = objs[0];
			lineBox.value = obj["accountCode"];
			lineBox.style.backgroundColor = "#336699";
			lineBox.style.color = "#FFFFFF";
		}
	} else {
        if (objs) {
            var selectedData = objs[0]["accountCode"];
			var accounts = document.getElementsByName("depreciationAccount");
			var count = accounts.length;
			var dataTable = document.getElementById("dataTable");
			for(var i = 0; i < count; i++){
				accounts[i].value = selectedData;
				if(selectedData != ""){
					accounts[i].style.backgroundColor = "#336699";
					accounts[i].style.color = "#FFFFFF";
				}
			}
		}
	}
}

function do_SelectFaCategoryCode(lineBox) {
	var objName = lineBox.name;
	var objId = lineBox.id;
	var idNumber = objId.substring(objName.length);
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_FACAT_CODE%>";
	var dialogWidth = 54;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight);
	var faCatChk = mainFrm.allFaCategory;
	if(!faCatChk){
		return;
	}
	if(!faCatChk.checked){
		if (objs) {
			obj = objs[0];
			document.getElementById("faCategoryCode" + idNumber).value = obj["faCategoryCode"];
		} else {
			lineBox.value = "";
		}
	} else {
		var obj = null;
		if (objs) {
			obj = objs[0];
		} else {
			obj = new Object();
			obj.faCategoryCode = "";
			obj.faCategoryName = "";
		}
		var catCodes = document.getElementsByName("faCategoryCode");
		var count = catCodes.length;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		var checkedCount = getCheckedBoxCount("subCheck");
		for(var i = 0; i < count; i++){
			if(checkedCount > 0){
				row = rows[i];
				checkObj = row.childNodes[0].childNodes[0];
				if(!checkObj.checked){
					continue;
				}
			}
			catCodes[i].value = obj["faCategoryCode"];
		}
	}
}

function validate() {
    if(!do_ValidateSXRequirement()){
		return false;
	} else if(!do_ValidateNewBarcode()){
		return false;
	} else if(!do_ValidateAccount()){
		return false;
	} else {
        return true;
    }
}

/**
 * 功能：
 * 1。接收方资产管理员必须填写“调入地点;新责任人”
 * 2。接收方资产会计必须填写“新折旧账户;新类别”
 */
function do_ValidateSXRequirement(){
	var isValid = true;
	var transferType = mainFrm.transferType.value;
    if(transferType == "<%=AssetsDictConstant.TRANS_BTW_COMP%>"){
        if("<%=toOrgId%>" == "<%=orgId%>"){
            var fieldNames = "";
            var fieldLabels = "";
            var validateType = EMPTY_VALIDATE;
            if("<%=headerDTO.getAttribute1()%>" == "DEPT"){
                fieldNames = "assignedToLocationName;responsibilityUserName";
                fieldLabels = "调入地点;新责任人";
                isValid = formValidate(fieldNames, fieldLabels, validateType);
            } else if("<%=headerDTO.getAttribute1()%>" == "ACCOUNT"){
                fieldNames = "depreciationAccount";
                fieldLabels = "新折旧账户";
                isValid = formValidate(fieldNames, fieldLabels, validateType);
            }
        }
    }
	return isValid;
}

function do_ValidateNewBarcode(){
	var isValid = true;
	if(transferType == "<%=AssetsDictConstant.TRANS_BTW_COMP%>" && "<%=headerDTO.getAttribute3()%>" == "NEW_TAG_NODE"){
		var newTagObjs = document.getElementsByName("newBarcode");
		for(var i = 0; i < newTagObjs.length; i++){
			if(newTagObjs[i].value == ""){
				isValid = false;
				alert("请先生成新标签号！");
                break;
			}
		}
	}
	return isValid;
}

function do_ValidateAccount(){
    var isValid = true;
    var toGroup= document.mainFrm.sf_group.value;
    toGroup = toGroup.substring(toGroup.substring(0, toGroup.length-3).length);
    if(toGroup == '财务部') {
        isValid = isAccountValid();
    }
    return isValid;
}

function isAccountValid(){
    var fieldNames = "depreciationAccount";
    var fieldLabels = "新折旧账户";
    return formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE);
}

function do_ProcessProSpecialGroup(){
    var actionURL = "/servlet/com.sino.ams.newasset.servlet.ProcedureGroupSelectServlet";
    var resHandler = do_SetProSpecialGroup;
    var ajaxProcessor = new AjaxProcessor(actionURL, resHandler, false);
    ajaxProcessor.performTask();
}

function do_SetProSpecialGroup(resText){
    Launch_HandleGroup = resText;
}


function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}
function validateJs(){
    var isValid = true;
    var assignedToLocationName = document.getElementsByName("assignedToLocationName");
    for(var i = 0; i < assignedToLocationName.length; i++){
        if(assignedToLocationName[i].value == ""){
            isValid = false;
            alert("调入地点不能为空！");
            return;
        }
    }
    var responsibilityUserName = document.getElementsByName("responsibilityUserName");
    for(var j = 0; j < responsibilityUserName.length; j++){
        if(responsibilityUserName[j].value == ""){
            isValid = false;
            alert("新责任人不能为空！");
            return;
        }
    }
	return isValid;
    return true;
}
</script>