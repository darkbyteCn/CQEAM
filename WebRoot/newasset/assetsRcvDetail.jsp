<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transferType = headerDTO.getTransferType();
	DTOSet lineSet = (DTOSet) request.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
	String transId = headerDTO.getTransId();
	String transStatus = headerDTO.getTransStatus();
	SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId();
%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>
    <script type="text/javascript">
        var ArrAction0 = new Array(true, "关闭", "action_cancel.gif", "关闭", "do_Close");
        var ArrActions = new Array(ArrAction0);
        var ArrSinoViews = new Array();
        printToolBar();
    </script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();">
<form action="<%=AssetsURLList.ASSETS_TRANS_SERVLET%>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/newasset/allocation/assetsAllocationHeaderDetail.jsp" flush="true"/>
<fieldset style="border:1px solid #397DF3; position:absolute;top:138px;width:100%;height:80%">
    <legend>
<%
	if(headerDTO.canReceive()){
%>
        <img src="/images/eam_images/receive.jpg" id="img6" alt="接收" onClick="do_Receive();">
<%
	}
%>
        <%--<img src="/images/button/close.gif" id="img6" alt="关闭" onClick="do_Close();">--%>
<%
	if(transStatus.equals(AssetsDictConstant.APPROVED)){
%>
		统一设置：
<%
		if(!transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)){
			if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){	
%>
		<input type="checkbox" name="allAccount" id="allAccount"><label for="allAccount">新折旧账户</label>
		<input type="checkbox" name="allFaCategory" id="allFaCategory"><label for="allFaCategory">新类别</label>
<%
			}	
%>
		<input type="checkbox" name="allLocation" id="allLocation"><label for="allLocation">调入地点</label>
		<input type="checkbox" name="allUser" id="allUser"><label for="allUser">新责任人</label>
<%
		}	
	}
%>
    </legend>
<input type="hidden" name="toOrganizationId" value="<%=headerDTO.getToOrganizationId()%>">   
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>"> 
<jsp:include page="/newasset/allocation/assetsAllocationReceiveLine.jsp" flush="true"/>
</fieldset>
<input type="hidden" name="transId" value="<%=transId%>">
<input type="hidden" name="act">
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>

<script>
    var srcDeptValue = null;
    var srcToOrgValue = document.mainFrm.toOrganizationId.value;
    if (document.mainFrm.fromDept) {
        srcDeptValue = document.mainFrm.fromDept.value;
    }
    var dataTable = document.getElementById("dataTable");
    var transferType = "";
    if (document.mainFrm.transferType) {
        transferType = document.mainFrm.transferType.value;
    }


function initPage(){
    window.focus();
    do_SetPageWidth();
}

function do_Receive(){
    var  trans="<%=transferType%>";
    if (trans == "BTW_DEPT")    {
        var fieldNames = "assignedToLocationName;responsibilityUserName";
        var fieldLabels = "调入地点;新责任人";
    } else {
        var fieldNames = "assignedToLocationName;responsibilityUserName;depreciationAccount";
        var fieldLabels = "调入地点;新责任人;新折旧账户";
    }
	var validateType = EMPTY_VALIDATE;
	if(formValidate(fieldNames, fieldLabels, validateType)){
		document.mainFrm.act.value = "<%=AssetsActionConstant.ASSIGN_ACTION%>";
		document.mainFrm.action = "/servlet/com.sino.ams.newasset.servlet.AssetsReceiveServlet";
		document.mainFrm.submit();
		document.document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
	}
}


//选择新责任人
function do_SelectPerson(lineBox){
	var toOrganizationId = mainFrm.toOrganizationId.value;
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
		for(var i = 0; i < count; i++){
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


//选择调入地点
function do_SelectLocation(lineBox){
	var toOrganizationId = mainFrm.toOrganizationId.value;
	var deptCode = "";
	var objName = lineBox.name;
	var objId = lineBox.id;
	var idNumber = objId.substring(objName.length);
	if(transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>"){
		deptCode = mainFrm.fromDept.value;
	} else {
		deptCode = document.getElementById("responsibilityDept" + idNumber).value;
		if(deptCode == ""){
			alert("请先选择调往部门，再选择地点。");
			var deptObj = document.getElementById("responsibilityDeptName" + idNumber);
			do_SelectDept(deptObj);
			return;
		}
	} 
	var userPara = "organizationId=" + toOrganizationId + "&transferType=" + transferType + "&deptCode=" + deptCode;

	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ADDRESS%>";
	var dialogWidth = 55;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	var addressChk = mainFrm.allLocation;
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
		for(var i = 0; i < count; i++){
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

/**
 * 功能:选择接收部门
 */
function do_SelectDept(lineBox) {
	var objName = lineBox.name;
	var objId = lineBox.id;
	var idNumber = objId.substring(objName.length);
	var userPara = "organizationId=<%=orgId%>&transferType=<%=transferType%>";
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_DEPT%>";
	var dialogWidth = 44;
	var dialogHeight = 30;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	var deptChk = mainFrm.allDept;
	if(!deptChk.checked){
		if (objs) {
			obj = objs[0];
			document.getElementById("responsibilityDept" + idNumber).value = obj["toDept"];
			lineBox.value = obj["toDeptName"];
		} else {
			document.getElementById("responsibilityDept" + idNumber).value = "";
			lineBox.value = "";
		}
	} else {
		var obj = null;
		if (objs) {
			obj = objs[0];
		} else {
			obj = new Object();
			obj.toDept = "";
			obj.toDeptName = "";
		}
		var deptNames = document.getElementsByName("responsibilityDeptName");
		var depts = document.getElementsByName("responsibilityDept");
		var count = deptNames.length;
		for(var i = 0; i < count; i++){
			deptNames[i].value = obj["toDeptName"];
			depts[i].value = obj["toDept"];
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
		for(var i = 0; i < count; i++){
			catCodes[i].value = obj["faCategoryCode"];
		}
	}
}


function do_SelectDepreciationAccount(lineBox){
	var objName = lineBox.name;
	var objId = lineBox.id;
	var idNumber = objId.substring(objName.length);
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ACCOUNT%>";
	var dialogWidth = 57;
	var dialogHeight = 29;
    var userPara = "organizationId=<%=orgId%>";
    var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	var accountChk = mainFrm.allAccount;
	if(!accountChk.checked){
		if (objs) {
			obj = objs[0];
			lineBox.value = obj["accountCode"];
		} else {
			lineBox.value = "";
		}
	} else {
		var obj = null;
		if (objs) {
			obj = objs[0];
		} else {
			obj = new Object();
			obj.accountCode = "";
			obj.accountName = "";
		}
		var accounts = document.getElementsByName("depreciationAccount");
		var count = accounts.length;
		for(var i = 0; i < count; i++){
			accounts[i].value = obj["accountCode"];
		}
	}
}
function do_SetLineTransDate(lineBox){
	if(!mainFrm.allTransDate){
		return;
	}
	if(!mainFrm.allTransDate.checked){
		return
	}
	var id = lineBox.id;
	var lineDate = lineBox.value;
	var dateFields = document.getElementsByName("lineTransDate");
	for(var i = 0; i < dateFields.length; i++){
		if(dateFields[i].id == id){
			continue;
		}
		dateFields[i].value = lineDate;
	}
}


function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}
</script>