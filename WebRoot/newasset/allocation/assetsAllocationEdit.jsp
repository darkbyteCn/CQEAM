<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsDictConstant"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserRightDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-3-28
  Time: 18:05:54
  To change this template use File | Settings | File Templates.
--%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    String transStatus = headerDTO.getTransStatus();
	String transferType = headerDTO.getTransferType();
	String hId = "";
	if (transferType.equals("INN_DEPT")) {
		hId = "'2.2.B'";
	} else if (transferType.equals("BTW_DEPT")) {
		hId = "'2.2.C'";
	} else {
		hId = "'2.2.D'";
	}
	String transId = headerDTO.getTransId();
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    boolean isDepAM = userAccount.isDptAssetsManager();
    DTOSet userDTO = userAccount.getUserRight();
    String roleName = "";
    Map  userRightMap = new HashMap();
    for (int i = 0;i<userDTO.getSize();i++) {
        SfUserRightDTO userRight = (SfUserRightDTO)userDTO.getDTO(i);
        roleName = userRight.getRoleName();
        userRightMap.put(roleName,roleName);
    }
    boolean isDptMgr = userRightMap.containsValue("部门经理");
    int orgId = userAccount.getOrganizationId();
	int userId = userAccount.getUserId();
	String provinceCode = headerDTO.getServletConfig().getProvinceCode();
	int provinceOrgId = headerDTO.getServletConfig().getProvinceOrgId();
	int tdProvinceOrgId = headerDTO.getServletConfig().getTdProvinceOrgId();
    //EXCEL黏贴
    boolean isMtlMana = userAccount.isMtlAssetsManager();
    DTOSet lineSetPri2 = (DTOSet) request.getAttribute(AssetsWebAttributes.PRIVI_DATA);//EXCEL导入时导入不成功的DTOSET
    session.setAttribute("lineSetPri",lineSetPri2);
%>
<head>
	<%
    	String titleText="";
    	String titleBar="";
    	if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
    		titleText=headerDTO.getTransTypeValue()+"(TD)";
    		titleBar=headerDTO.getTransTypeValue()+"(TD)";
    	} else {
    		titleText=headerDTO.getTransTypeValue();
    		titleBar=headerDTO.getTransTypeValue();
    	}
    %>
	<title><%=titleText%></title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/DefaultLocationProcess.js"></script>
</head>
<script type="text/javascript">
    printToolBar(); 
</script>
<%@ include file="/flow/flowNoButton.jsp"%>
<body leftmargin="0" topmargin="0" onLoad="initPage1();helpInit(<%=hId%>)" onbeforeunload="doBeforeUnload()" onUnload="doUnLoad()">
<input type="hidden" name="helpId" value="">
<form action="<%=AssetsURLList.ASSETS_ALLOCATION_SERVLET%>" method="post" name="mainFrm">
<%@ include file="/flow/flowPara.jsp" %>
<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="position:absolute;top:29px;left:1px;width:100%">
<jsp:include page="/newasset/allocation/assetsAllocationHeader.jsp" flush="true"/>
</div>
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
<input type="hidden" name="fromDeptName" value="<%=headerDTO.getFromDeptName()%>">
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>">
<input type="hidden" name="created" value="<%=headerDTO.getCreated()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=transId%>">
<input type="hidden" name="toDept" value="<%=headerDTO.getToDept()%>">
<input type="hidden" name="toGroup" value="<%=headerDTO.getToGroup()%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="isThred" value="">
<input type="hidden" name="transType" value="<%=headerDTO.getTransType()%>">
<input type="hidden" name="isDepAM" value="<%=isDepAM%>">
<input type="hidden" name="transStatus" value="<%=headerDTO.getTransStatus()%>">
<input type="hidden" name="faContentCode" value="">

<input type="hidden" name="excel" value="">
<input type="hidden" name="fromExcel" value="<%= StrUtil.nullToString( request.getParameter("fromExcel") ) %>">


<div id="buttonDiv" style="position:absolute;top:150px;width:100%;height:30px">
        <img src="/images/eam_images/choose.jpg" alt="选择资产" onClick="do_SelectAssets(); return false;">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
        
<%
	if(!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT_RFU)){
%>
        <img src="/images/eam_images/imp_from_excel.jpg" alt="Excel导入"  onClick="do_excel();">

<%
	}
    if (lineSetPri2 != null) {
        if (!lineSetPri2.isEmpty()) {
%>
        <img src="/images/eam_images/detail_info.jpg" alt="查看资产标签号未导入成功详细信息"  onClick="do_Transfer();">
<%
        }
    }
%>

<%
	if(!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT_RFU)){
%>
		统一设置：
<%
	}
		if(!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT)){
%>
        <input type="checkbox" name="allDept" id="allDept" checked style="display:none"><label for="allDept" style="display:none">新责任部门</label>
<%
    	}
%>

<%
	if(!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT_RFU)){
%>
        <input type="checkbox" name="allLocation" id="allLocation"><label for="allLocation">调入地点</label>
		<input type="checkbox" name="allUser" id="allUser"><label for="allUser">新责任人</label>
<%
	}

    if(headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT)
            || headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_DEPT)){
%>
        <input type="checkbox" name="defaultLocation" id="defaultLocation" onClick="do_SetDefaultLocation();"><label for="defaultLocation">默认地点</label>
<%
    }
    if(headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP)){
%>
		<input type="checkbox" name="allAccount" id="allAccount"><label for="allAccount">新折旧账户</label>
		<input type="checkbox" name="allFaCategory" id="allFaCategory"><label for="allFaCategory">新类别</label>
<%
	}
%>

<%
	if(!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT_RFU)){
%>
		<input type="checkbox" name="allTransDate" id="allTransDate"><label for="allTransDate">调拨日期</label>
		<input type="checkbox" name="deptToSite" id="deptToSite"><label for="deptToSite">根据调入部门过虑调入地点</label>
<%
	}
%>
    
</div>
<jsp:include page="/newasset/allocation/assetsAllocationLine.jsp" flush="true"/>
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
var srcToOrgValue = "";
var srcDeptValue = null;
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
var dataTable = document.getElementById("dataTable");

/**
  * 功能：选择资产
 */
function do_SelectAssets() {
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
	var userPara = "";
	
	var specialityDept = "";
    //if(transferType == "" || (transferType != "" && transferType != "<%=AssetsDictConstant.TRANS_BTW_COMP%>")){
    	if (transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT_RFU%>" && transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT%>"){
			specialityDept = mainFrm.specialityDept.value;
		}
	//}

    if(transferType != ""){
		userPara += "&transferType=" + transferType;
	}
    if(transferType == "" || (transferType != "" && transferType != "<%=AssetsDictConstant.TRANS_BTW_COMP%>")){
    	if (transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT_RFU%>"){
			var fromDept = mainFrm.fromDept.value;
			if(fromDept == ""){
				alert("请先选择部门，再选择资产！");
				mainFrm.fromDept.focus();
				return;
			}
			
			//if (mainFrm.specialityDept.value == "") {
			//	alert("请选择实物管理部门，然后才能选择资产");
			//	return;
			//}
			
			userPara += "&deptCode=" + mainFrm.fromDept.value;
			//userPara += "&specialityDept="+specialityDept;
		}
	}
	
	if (transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT_RFU%>" && transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT%>"){
		if (mainFrm.specialityDept.value == "") {
			alert("请选择实物管理部门，然后才能选择资产");
			return;
		}
		userPara += "&specialityDept="+specialityDept;
	}

    userPara += "&transType=" + mainFrm.transType.value;
	if (transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT_RFU%>"){
		var assets = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	} else {
		lookUpName = "<%=AssetsLookUpConstant.LOOK_TRANSFER_ASSETS_RFU%>";
		var assets = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	}
	
	if (assets) {
		if (transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT_RFU%>"){
			var data = null;
			for (var i = 0; i < assets.length; i++) {
				data = assets[i];
				data["faCategoryCode"] = data["oldFaCategoryCode"];
				data["currentUnits"] = formatNum(data["currentUnits"], 0);
				appendDTO2Table(dataTable, data, false, "barcode");
			}
			do_ComputeSummary();
		} else {
			var data = null;
			for (var i = 0; i < assets.length; i++) {
				data = assets[i];
				data["faCategoryCode"] = data["oldFaCategoryCode"];
				data["currentUnits"] = formatNum(data["currentUnits"], 0);
				appendDTO2Table(dataTable, data, false, "barcode" );
			}
			do_ComputeSummary();
		}
	}
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
    var assignedToLocation = getTrNode(linkObj, "assignedToLocation");
	url += "&workorderObjectNo=" + assignedToLocation.value;
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

function deleteLine() {
    var tab = document.getElementById("dataTable");
    deleteTableRow(tab, 'subCheck');
    do_ComputeSummary();
}

function do_isProfessionalGroup() {
	var fromGroup = mainFrm.fromGroup.value;
	if(fromGroup != null && fromGroup != ""){
		var url = "<%=AssetsURLList.ASSETS_ALLOCATION_SERVLET%>";
		url += "?act=VALIDATE_ACTION";
		url += "&fromGroup=" + mainFrm.fromGroup.value;
		do_ProcessSimpleAjax(url, null);
	}
}

function do_ProcessResponse(responseContent){
    mainFrm.isGroupPID.value = responseContent;
}

function initPage1() {
    window.focus();
    do_SetPageWidth();
    doLoad();
    do_ControlProcedureBtn();
    if (mainFrm.fromGroup.value == "0") {
        mainFrm.fromGroup.value = document.getElementById("flow_group_id").value;
        mainFrm.fromGroupName.value = sf_group;
    }
    do_FormatQuantity();
    do_ComputeSummary();
    
    var appDeptCode = document.getElementById("app_dept_code").value;
    var appDeptName = document.getElementById("app_dept_name").value;
    if (appDeptCode) {
		document.getElementById("fromDept").value = document.getElementById("app_dept_code").value;
    }
    if (appDeptName) {
    	document.getElementById("userDeptName").value = document.getElementById("app_dept_name").value;
    }
    
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

function do_SelectCreateGroup(){
	var fromGroup = mainFrm.fromGroup.value;
	var url = "<%=AssetsURLList.GROUP_CHOOSE_SERVLET%>?fromGroup=" + fromGroup;
	var popscript = "dialogWidth:20;dialogHeight:10;center:yes;status:no;scrollbars:no;help:no";
	var group = window.showModalDialog(url, null, popscript);
	if(group){
		dto2Frm(group, "mainFrm");
        do_isProfessionalGroup();
	}
}


/**
 * 功能：点击“调出部门”下拉框时，触发该操作。
 */
function do_ConfirmChange(){
	var rows = dataTable.rows;
	if(rows){
		var rowCount = rows.length;
		var tr = rows[0];
		if(!(rows.length == 0 || (rows.length == 1 && tr.style.display == "none"))){
			if(confirm("改变部门将清除已经选择的数据，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮！")){
				deleteRow(dataTable);
				srcDeptValue = mainFrm.fromDept.value;
				setCheckBoxState("mainCheck", false);
			} else {
				selectSpecialOptionByItem(mainFrm.fromDept, srcDeptValue);
			}
		}
	}
}


/**
 * 功能:选择接收部门
 */
function do_SelectDept(lineBox) {
	var toOrganizationId = mainFrm.toOrganizationId.value;
	if(toOrganizationId == ""){
		alert("请先选择调往公司，再选择新责任部门。");
		return;
	}
	var userPara = "organizationId=" + toOrganizationId + "&transferType=" + transferType;
	var deptCode = "";
    var provinceCode = "<%=provinceCode%>";
    if(transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>"){
		deptCode = mainFrm.fromDept.value;
	}
	userPara += "&deptCode=" + deptCode;
    userPara += "&provinceCode=" + provinceCode;

    var objName = lineBox.name;
	var objId = lineBox.id;
	var idNumber = objId.substring(objName.length);
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
            lineBox.value = "";
			document.getElementById("responsibilityDept" + idNumber).value = "";
			document.getElementById("responsibilityUserName" + idNumber).value = "";
			document.getElementById("responsibilityUser" + idNumber).value = "";
			document.getElementById("assignedToLocationName" + idNumber).value = "";
			document.getElementById("assignedToLocation" + idNumber).value = "";
			document.getElementById("addressId" + idNumber).value = "";
		}
	} else {
		var obj = null;
		var emptyData = false;
		if (objs) {
            obj = objs[0];
			mainFrm.toDept.value = obj["toDept"];//为山西而改变，准备应用于陕西
            mainFrm.toGroup.value = obj["toGroup"];
        } else {
            emptyData = true;
			mainFrm.toDept.value = "";//为山西而改变，准备应用于陕西
		}
		var deptNames = document.getElementsByName("responsibilityDeptName");
		var depts = document.getElementsByName("responsibilityDept");
		var userNames = document.getElementsByName("responsibilityUserName");
		var users = document.getElementsByName("responsibilityUser");
		var addressNames = document.getElementsByName("assignedToLocationName");
		var addressNos = document.getElementsByName("assignedToLocation");
		var addressIds = document.getElementsByName("addressId");
        var count = addressNames.length;
        for(var i = 0; i < count; i++){
			if(emptyData){
                deptNames[i].value = "";
				depts[i].value = "";
				addressNames[i].value = "";
				addressNos[i].value = "";
				addressIds[i].value = "";
				userNames[i].value = "";
				users[i].value = "";
            } else {
                deptNames[i].value = obj["toDeptName"];
				depts[i].value = obj["toDept"];
            }
		}
	}
    if(transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>"){
        var fDept = document.mainFrm.fromDept.value;
        var tDepts = document.getElementsByName("responsibilityDept");
        var tDept = tDepts[0].value;
        do_ThredDept(fDept, tDept);
	}
}

//选择调入地点
function do_SelectLocation(lineBox){
    var toOrganizationId = mainFrm.toOrganizationId.value;
    if(toOrganizationId == ""){
        alert("请先选择调往公司，再选择地点。");
        return;
    }
    var deptToSite=false;
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
    if(document.getElementById("deptToSite")){
        if(document.getElementById("deptToSite").checked){
        //部门间调拨根据调入部门过虑调入地点
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
		var obj1 = null;
		var emptyData = false;
		if (objs) {
			obj1 = objs[0];
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
				addressNames[i].value = obj1["toObjectName"];
				addressNos[i].value = obj1["toObjectNo"];
				addressIds[i].value = obj1["addressId"];
			}
		}
	}
}


//选择新责任人
function do_SelectPerson(lineBox){
	var toOrganizationId = mainFrm.toOrganizationId.value;
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
		var obj1 = null;
		var emptyData = false;
		if (objs) {
			obj1 = objs[0];
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
				userNames[i].value = obj1["userName"];
				users[i].value = obj1["employeeId"];
			}
		}
	}
}

function do_SelectDepreciationAccount(lineBox){
	var toOrganizationId = mainFrm.toOrganizationId.value;
	if(toOrganizationId == ""){
		alert("请先选择调往公司，再选择折旧账户。");
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
			alert("请先选择调往部门，再选择折旧账户。");
			var deptObj = document.getElementById("responsibilityDeptName" + idNumber);
			do_SelectDept(deptObj);
			return;
		}
	}
	var userPara = "organizationId=" + toOrganizationId;
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_ACCOUNT%>";
	var dialogWidth = 51;
	var dialogHeight = 29;
	var objs = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	var accountChk = mainFrm.allAccount;
    if(!accountChk.checked){
		if (objs) {
			var obj = objs[0];
			lineBox.value = obj["accountCode"];
		} else {
			lineBox.value = "";
		}
	} else {
		var obj1 = null;
		var emptyData = false;
		if (objs) {
			obj1 = objs[0];
		} else {
			emptyData = true;
		}
		var accounts = document.getElementsByName("depreciationAccount");
		var count = accounts.length;
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
				accounts[i].value = "";
			} else {
				accounts[i].value = obj1["accountCode"];
			}
		}
	}
}
<%--
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
			var obj = objs[0];
			document.getElementById("faCategoryCode" + idNumber).value = obj["contentCode"];
		} else {
			lineBox.value = "";
		}
	} else {
		var obj1 = null;
		if (objs) {
			obj1 = objs[0];
		} else {
			obj1 = new Object();
			obj1.faCategoryCode = "";
			obj1.faCategoryName = "";
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
			catCodes[i].value = obj1["faCategoryCode"];
		}
	}
}
--%>

function validate() {
    var isValid = true;
    if (dataTable.rows.length == 0 || (dataTable.rows[0].style.display == 'none' && dataTable.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        isValid = false;
    } else {
        var fieldLabels = "建单组别;调拨说明";
        var fieldNames = "fromGroupName;createdReason";
        var validateType = EMPTY_VALIDATE;
        if (transferType) {
            if (transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>") {
                fieldLabels += ";调入部门;调拨日期";
                fieldNames += ";responsibilityDeptName;lineTransDate";
            } else if (transferType == "<%=AssetsDictConstant.TRANS_BTW_COMP%>") {
                fieldLabels += ";调入部门;调拨日期";
                fieldNames += ";responsibilityDeptName;lineTransDate";
            } else {
                fieldLabels += ";调入地点;新责任人;调拨日期";
                fieldNames += ";assignedToLocationName;responsibilityUserName;lineTransDate";
            }
        }
    }
    isValid = formValidate(fieldNames, fieldLabels, validateType);
    return isValid;
} 

function getCalendarPostProcessor(){
    return new CalendarPostProcessor("do_SetLineTransDate");
}

function do_SetLineTransDate(lineBox){
	if(!mainFrm.allTransDate){
		return;
	}
	if(!mainFrm.allTransDate.checked){
		return;
	}
	var id = lineBox.id;
	var lineDate = lineBox.value;
	var dateFields = document.getElementsByName("lineTransDate");
	var dataTable = document.getElementById("dataTable");
	var rows = dataTable.rows;
	var row = null;
	var checkObj = null;
	var checkedCount = getCheckedBoxCount("subCheck");
    if (mainFrm.allTransDate.checked) {
        for(var i = 0; i < dateFields.length; i++){
            if(checkedCount > 0){
                row = rows[i];
                checkObj = row.childNodes[0].childNodes[0];
                if(!checkObj.checked){
                    continue;
                }
            }
            if(dateFields[i].id == id){
                continue;
            }
            dateFields[i].value = lineDate;
        }
    }
}

function do_ClearLineData(){
	var rows = dataTable.rows;
	if(rows){
		var tr = rows[0];
		if(!(rows.length == 0 || (rows.length == 1 && tr.style.display == "none"))){
			//faCategoryCode;
			var fieldNames = "responsibilityDeptName;assignedToLocationName;responsibilityUserName;depreciationAccount;assignedToLocation;responsibilityUser;responsibilityDept;addressId";
			if(!isAllEmapty(fieldNames)){
				if(confirm("改变调入公司将清除“调入部门”、“调入地点”、“新责任人”、“新折旧账户”等数据，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮！")){
					clearFieldValue(fieldNames);
				} else {
					selectSpecialOptionByItem(mainFrm.toOrganizationId, srcToOrgValue);
				}
			}
		}
	}
	srcToOrgValue = mainFrm.toOrganizationId.value;
}

var  al=0;

function do_excel() {
    if(transferType == "" || (transferType != "" && transferType != "<%=AssetsDictConstant.TRANS_BTW_COMP%>")){
		var fromDept = mainFrm.fromDept.value;
		if(fromDept == ""){
			alert("请先选择部门，再选择资产！");
			mainFrm.fromDept.focus();
			return;
		}
	}
    var returnValue = do_ImportExcelData();
    if (returnValue) {
        isSave=true;
        document.mainFrm.excel.value = returnValue;
        document.mainFrm.act.value = "excel";
        document.mainFrm.submit();
    }
}

function do_CloseDiv() {
    document.getElementById("transferDiv").style.visibility = "hidden";
}
function do_Transfer() {
   var width = screen.width-10;
   var height = screen.height-60;   
   window.open("/newasset/assetsDisTransImportErrorInfo.jsp","","left=0,top=0,width="+width+",height="+height+",title=yes,scrollbars=yes,resizable=no,location=no,toolbar=no, menubar=no"); 
}

function do_setQuantity() {
    var length = document.getElementsByName("retirementCost").length;
    for (i = 0; i < length; i++) {
        var retirementCost = document.getElementById("retirementCost" + i).value;
        var cost = document.getElementById("cost" + i).value;
        if (retirementCost < 0) {
            alert("报废成本必须>0");
            document.getElementById("retirementCost" + i).value = "";
            break;
        } else if (retirementCost > cost) {
            alert("报废成本必须<=资产原值");
            document.getElementById("retirementCost" + i).value = "";
            break;
        }
    }
}
function do_valiQuantity(){
    var isVilidate = true;
    var rows = dataTable.rows;
    var rowCount = rows.length;
    for (var i =0; i<rowCount; i++) {
        var cost = document.getElementById("dataTable").rows[i].cells[5].childNodes[0].value;
        var retirementCost = document.getElementById("dataTable").rows[i].cells[7].childNodes[0].value;
        if (retirementCost<0) {
            alert("报废成本必须>0");
            document.getElementById("dataTable").rows[i].cells[7].childNodes[0].value = "";
            isVilidate = false;
        } else if (retirementCost > cost) {
            alert("报废成本必须<=资产原值");
            document.getElementById("dataTable").rows[i].cells[7].childNodes[0].value = "";
            isVilidate = false; 
        }
    }
    return isVilidate;
}

function do_ThredDept(fDept, tDept){
    var url = "/servlet/com.sino.ams.newasset.allocation.servlet.AmsAssetsAllocationHeaderServlet?act=DO_THRED_DEPT&fDept=" + fDept + "&tDept=" + tDept;
    var ajaxProcessor = new AjaxProcessor(url, handleReadyStateChange1, false);
    ajaxProcessor.performTask();
}

function handleReadyStateChange1(resText) {
    document.mainFrm.isThred.value = resText;
}

function do_Save_app () {
    mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_Complete_app_yy() {
    if(transferType != ""){
		if(transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>"){
			mainFrm.toDept.value = mainFrm.fromDept.value;
		} else if(transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT_RFU%>") {

		} else {
			var depts = document.getElementsByName("responsibilityDept");
			mainFrm.toDept.value = depts[0].value;
		}
	}
    mainFrm.act.value = "<%=AssetsActionConstant.SUBMIT_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_ComputeSummary(){
    var transferType = "<%=headerDTO.getTransferType()%>";
    var transType = mainFrm.transType.value;
    if((transferType != "BTW_COMP") && (transType != "ASS-DIS")){
        return;
    }
    var dataTable = document.getElementById("dataTable");
    var rows = dataTable.rows;
    if(rows != undefined){
        var rowCount = rows.length;
        var idArr = new Array("numValue", "yuanzhiValue", "ljzjValue", "jzzbValue", "canzhiValue", "jingerValue");
        var summaryCell = new Array("currentUnits", "cost", "sumDepreciation", "impairReserve", "scrapValue", "deprnCost");
        var idCount = idArr.length;
        var sumValueArr = new Array();
        for(var i = 0; i < rowCount; i++){
            var tr =  rows[i];
            for(var j = 0; j < idCount; j++){
                var node = getTrNode(tr, summaryCell[j]);
                if(!node){
                    continue;
                }
                if(!sumValueArr[j]){
                    sumValueArr[j] = 0;
                }
                sumValueArr[j] += Number(node.value);
            }
        }
        for(j = 0; j < idCount; j++){
            node = document.getElementById(idArr[j]);
            if(!node){
                continue;
            }
            if(j == 0){
                node.value = sumValueArr[j];
            } else {
                node.value = formatNum(sumValueArr[j], 2);
            }
        }
    }
}

function urgentForSumS1() { //紧急调拨补汇总用
    var isValid = true;
    if (dataTable.rows.length == 0 || (dataTable.rows[0].style.display == 'none' && dataTable.rows.length == 1)) {
        alert("请选择紧急调拨汇总需要的数据行");
        isValid = false;
    } else {
        var fieldLabels = "调拨说明";
        var fieldNames = "createdReason";
        var validateType = EMPTY_VALIDATE;
    }
    isValid = formValidate(fieldNames, fieldLabels, validateType);
    return isValid;
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}
</script>