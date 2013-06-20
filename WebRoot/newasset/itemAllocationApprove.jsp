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
     String isFinanceGroup = request.getAttribute(AssetsWebAttributes.IS_FINANCE_GROUP).toString();//是否属于财务部
    String isSpecialGroup = request.getAttribute(AssetsWebAttributes.IS_SPECIAL_GROUP).toString();//是否属于实物部门
    int orgId = userAccount.getOrganizationId();
    int toOrgId = headerDTO.getToOrganizationId();
%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/DefaultLocationProcess.js"></script>
</head>
<script type="text/javascript">
    printToolBar();
</script>
<%@ include file="/flow/flowNoButton.jsp"%>
<body  leftmargin="0" topmargin="0" onload="initPage1();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<form action="/servlet/com.sino.ams.newasset.servlet.AmsItemAllocationApproveServlet" method="post" name="mainFrm">
<%@ include file="/flow/flowPara.jsp" %>
<input type="hidden" name="act" value="">
<input type="hidden" name="transType" value="<%= headerDTO.getTransType()%>">
<input type="hidden" name="transId" value="<%= headerDTO.getTransId()%>">
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>">
    <input type="hidden" name="faContentCode" value="<%=headerDTO.getFaContentCode()%>">
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="toOrganizationId" value="<%=headerDTO.getToOrganizationId()%>">
<input type="hidden" name="created" value="<%=headerDTO.getCreated()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="isDepAM" value="">
<input type="hidden" name="isSpecialGroup" value="<%=isSpecialGroup%>">
<input type="hidden" name="isFinanceGroup" value="<%=isFinanceGroup%>">
<input type="hidden" name="transStatus" value="<%=headerDTO.getTransStatus()%>">
<input type="hidden" name="isMtlAssetsManager" id="isMtlAssetsManager" value="<%=userAccount.isMtlAssetsManager()%>">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/newasset/itemAllocationHeaderDetail.jsp" flush="true"/>
<%
    if(headerDTO.getAttribute2().equals(AssetsDictConstant.EDIT_ACCOUNT) || headerDTO.getAttribute1().equals("DEPT")){
%>
    <div id="searchDiv" style="position:absolute;top:135px;left:1px;width:100%">
<%
    } else {
%>
    <div id="searchDiv" style="position:absolute;top:112px;left:1px;width:100%">
<%
    }
%>

<%
    if(headerDTO.getAttribute2().equals(AssetsDictConstant.EDIT_ACCOUNT)){
%>
        统一设置：
			<input type="checkbox" name="allLocation" id="allLocation"><label for="allLocation">调入地点</label>
			<input type="checkbox" name="allUser" id="allUser"><label for="allUser">新责任人</label>
			<input type="checkbox" name="allAccount" id="allAccount"><label for="allAccount">新折旧账户</label>
			<input type="checkbox" name="allFaCategory" id="allFaCategory"><label for="allFaCategory">新类别</label>
<%
    }
    if (headerDTO.getAttribute1().equals("DEPT")) {
%>
        统一设置：
            <input type="checkbox" name="defaultLocation" id="defaultLocation" onclick="do_SetDefaultLocation();"><label for="defaultLocation">默认地点</label>
			<input type="checkbox" name="allLocation" id="allLocation"><label for="allLocation">调入地点</label>
			<input type="checkbox" name="allUser" id="allUser"><label for="allUser">新责任人</label>
<%
    }
    if(headerDTO.getAttribute2().equals(AssetsDictConstant.EDIT_ACCOUNT) || headerDTO.getAttribute1().equals("DEPT")){
%>
        <input type="checkbox" name="deptToSite" id="deptToSite"><label for="deptToSite">根据调入部门过虑调入地点</label>
<%
    }
%>
</div>
<jsp:include page="/newasset/itemAllocationLineDetail.jsp" flush="true"/>
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
    mainFrm.fromGroupName.value = sf_group;
}

function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
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
	var url = "/servlet/com.sino.ams.newasset.servlet.NewTagProduceServlet";
	url += "?transId=" + mainFrm.transId.value;
	url += "&refNumber=" + mainFrm.transNo.value;
	url += "&fromOrganizationId=" + mainFrm.fromOrganizationId.value;
	url += "&toOrganizationId=" + mainFrm.toOrganizationId.value;
	var sendData = new Array();
	var tagObjs = document.getElementsByName("barcode");
	var baocodeCount = tagObjs.length;
	for(var i = 0; i < baocodeCount; i++){
		sendData[i] = tagObjs[i].value;
	}
//    isSave=true;
	do_ProcessSimpleAjax(url, sendData.toJSONString());
}
function do_ProcessResponse(responseContent){
	var oldTagObjs = document.getElementsByName("barcode");
	var newTagObjs = document.getElementsByName("newBarcode");
	var responArr = responseContent.split("&&&");
	var barcodeStr = "";
	var baocodeCount = oldTagObjs.length;
	var oldBarcode = "";
	var index = -1;
	for(var i = 0; i < baocodeCount; i++){
		oldBarcode = oldTagObjs[i].value;
		for(var j = 0; j < responArr.length; j++){
			barcodeStr = responArr[j];
			index = barcodeStr.indexOf(";");
			if(barcodeStr.substring(0, index) == oldBarcode){
				newTagObjs[i].value = barcodeStr.substring(index + 1);
				responArr = responArr.slice(0, j).concat(responArr.slice(j + 1));
				break;
			}
		}
	}
	document.getElementById("newTagImg").style.display = "none";
}

//选择组合调入地点
function do_ChoseLocDesc(linkObj) {
    while(linkObj.tagName != "TR"){
        linkObj = linkObj.parentNode;
    }
	var toOrganizationId = mainFrm.toOrganizationId.value;
    if(toOrganizationId == ""){
        alert("请先选择调往公司，再选择地点。");
        return;
    }
	var url = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet?act=CHOSE_LOCDESC_ACTION&organizationId=" + toOrganizationId;
	url += "&workorderObjectNo=" + getTrNode(linkObj, "assignedToLocation").value;
	var deptToSite=false;
	var deptCode = "";
	if(transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>"){
		deptCode = mainFrm.fromDept.value;
	} else {
        deptCode = getTrNode(linkObj, "responsibilityDept").value;
		if(deptCode == ""){
			alert("请先选择调往部门，再选择地点。");
			var deptObj = getTrNode(linkObj, "responsibilityDeptName");
			do_SelectDept(deptObj);
			return;
		}
	}
	if(document.getElementById("deptToSite").checked){
    //部门间调拨根据调入部门过虑调入地点
        url += "&deptCode=" + deptCode;
    }
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
        //调拨根据调入部门过虑调入地点
            deptToSite=true;
        }
    }
	var userPara = "organizationId=" + toOrganizationId + "&transferType=" + transferType + "&deptCode=" + deptCode;
    if(deptToSite){
        userPara = "organizationId=" + toOrganizationId + "&transferType=" + transferType + "&deptCode=" + deptCode+ "&deptToSite=1";
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
        //alert(objs);
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
				return;
			}
		}
	}
	return isValid;
}



function validateJs() {
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
}


function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}
</script>