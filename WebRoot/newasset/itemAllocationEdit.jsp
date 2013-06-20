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
	<script type="text/javascript" src="/WebLibary/js/util.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
	<script type="text/javascript" src="/WebLibary/js/api.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/DefaultLocationProcess.js"></script>
    <script type="text/javascript">
    printToolBar();
	</script>
</head>
<body leftmargin="0" topmargin="0" onLoad="initPage1();helpInit('2.2.B');" onbeforeunload="doBeforeUnload()" onUnload="doUnLoad()">
<%@ include file="/flow/flowNoButton.jsp"%>
<form action="/servlet/com.sino.ams.newasset.servlet.AmsItemAllocationHeaderServlet" method="post" name="mainFrm">
<%@ include file="/flow/flowPara.jsp" %>
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/newasset/itemAllocationHeader.jsp" flush="true"/>
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
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

<input type="hidden" name="excel" value="">
<input type="hidden" name="fromExcel" value="<%= StrUtil.nullToString( request.getParameter("fromExcel") ) %>">
<input type="hidden" name="helpId" value="">
    <div id="searchDiv" style="position:absolute;top:155px;left:1px;width:100%">
 
        <img src="/images/eam_images/choose.jpg" alt="选择资产" onClick="do_SelectAssets(); return false;">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
        <span id="warn"></span>
        <img src="/images/eam_images/imp_from_excel.jpg" alt="Excel导入"  onClick="do_excel();">
<%
    if (lineSetPri2 != null) {
        if (!lineSetPri2.isEmpty()) {
%>
        <img src="/images/eam_images/detail_info.jpg" alt="查看资产标签号未导入成功详细信息"  onClick="do_Transfer();">
<%
        }
    }
%>
		统一设置：
<%
		if(!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT)){
%>
        <input type="checkbox" name="allDept" id="allDept" checked style="display:none"><label for="allDept" style="display:none">新责任部门</label>
<%
    	}
%>
        <input type="checkbox" name="defaultLocation" id="defaultLocation" onClick="do_SetDefaultLocation();"><label for="defaultLocation">默认地点</label>
        <input type="checkbox" name="allLocation" id="allLocation"><label for="allLocation">调入地点</label>
		<input type="checkbox" name="allUser" id="allUser"><label for="allUser">新责任人</label>
        <input type="checkbox" name="deptToSite" id="deptToSite"><label for="deptToSite">根据调入部门过虑调入地点</label>
<%
		if((!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT))&&(headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP))){
%>
		<input type="checkbox" name="allAccount" id="allAccount"><label for="allAccount">新折旧账户</label>
		<input type="checkbox" name="allFaCategory" id="allFaCategory"><label for="allFaCategory">新类别</label>
<%
		}
%>
		<input type="checkbox" name="allTransDate" id="allTransDate"><label for="allTransDate">调拨日期</label>
</div>
<jsp:include page="/newasset/itemAllocationLine.jsp" flush="true"/>
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
    	lun = AssetsLookUpConstant.LOOK_TRANSFER_ITEM;
    /*	if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
    		lun = AssetsLookUpConstant.LOOK_TRANSFER_ASSETS_TD;
    	} else {
    		lun = AssetsLookUpConstant.LOOK_TRANSFER_ASSETS;
    	}*/
    %>
	var lookUpName = "<%=lun%>";
	var userPara = "";
    if(transferType != ""){
		userPara += "&transferType=" + transferType;
	}
    if(transferType == "" || (transferType != "" && transferType != "<%=AssetsDictConstant.TRANS_BTW_COMP%>")){
		var fromDept = mainFrm.fromDept.value;
		if(fromDept == ""){
			alert("请先选择部门，再选择资产！");
			mainFrm.fromDept.focus();
			return;
		}
		userPara += "&deptCode=" + mainFrm.fromDept.value;
	}
    if(mainFrm.faContentCode.value == ""){
		alert("请先选择资产种类，再选择资产！");
		mainFrm.faContentCode.focus();
		return;
	}
	userPara += "&faContentCode=" + mainFrm.faContentCode.value;
	userPara += "&transType=" + mainFrm.transType.value;
	var assets = lookUpAssetsValues(lookUpName, dialogWidth, dialogHeight, userPara);
	if (assets) {
		var data = null;
		for (var i = 0; i < assets.length; i++) {
			data = assets[i];
			appendDTO2Table(dataTable, data, false, "barcode");
		}
	}
}

function deleteLine() {
    var tab = document.getElementById("dataTable");
    deleteTableRow(tab, 'subCheck');
//    if(deleteTableRow(dataTable, 'subCheck')){
//		do_SaveOrder();
//	}
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
//    mainFrm.isGroupPID.value = responseContent;
}

function initPage1() {
    window.focus();
    doLoad();
    do_SetPageWidth();
    do_ControlProcedureBtn();
    mainFrm.fromGroupName.value = sf_group;
    
    document.getElementById("fromGroup").value = document.getElementById("flow_group_id").value;
    
    var appDeptCode = document.getElementById("app_dept_code").value;
    var appDeptName = document.getElementById("app_dept_name").value;
    if (appDeptCode) {
    	document.getElementById("fromDept").value = document.getElementById("app_dept_code").value;
    	
		if( document.getElementById("userDeptName").value == "" ){
    		document.getElementById("userDeptName").value = document.getElementById("app_dept_name").value;
    		document.getElementById("fromDeptData").value = document.getElementById("app_dept_name").value;
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
    if(toOrganizationId == ""){
        alert("请先选择调往公司，再选择地点。");
        return;
    }
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
            fieldLabels += ";调拨日期";
            fieldNames += ";lineTransDate";
            if (transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT%>") {
                fieldLabels += ";调入部门";
                fieldNames += ";responsibilityDeptName";
            } else {
                fieldLabels += ";调入地点;新责任人";
                fieldNames += ";assignedToLocationName;responsibilityUserName";
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
		return
	}
	var id = lineBox.id;
	var lineDate = lineBox.value;
	var dateFields = document.getElementsByName("lineTransDate");
	var dataTable = document.getElementById("dataTable");
	var rows = dataTable.rows;
	var row = null;
	var checkObj = null;
	var checkedCount = getCheckedBoxCount("subCheck");
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

function do_ClearLineData(){
	var rows = dataTable.rows;
	if(rows){
		var tr = rows[0];
		if(!(rows.length == 0 || (rows.length == 1 && tr.style.display == "none"))){
			var fieldNames = "responsibilityDeptName;assignedToLocationName;responsibilityUserName;depreciationAccount;faCategoryCode;assignedToLocation;responsibilityUser;responsibilityDept;addressId";
			if(!isAllEmapty(fieldNames)){
				if(confirm("改变调入公司将清除“调入部门”、“调入地点”、“新责任人”、“新折旧账户”、“新类别”等数据，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮！")){
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
function do_exportToExcel() {
	mainFrm.act.value = "<%=AssetsActionConstant.EXPORT_ACTION%>";
	mainFrm.submit();
}

var contentCode = mainFrm.faContentCode.value;
function do_ChangeContentCode() {
    var rows = dataTable.rows;
    var rowCount = rows.length;
    if (rowCount > 1 || (rowCount == 1 && rows[0].style.display != "none")) {
        if (confirm("改变资产大类将删除已经选择的资产数据，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮")) {
            deleteRow(dataTable);
            contentCode = mainFrm.fromDept.value;
            setCheckBoxState("mainCheck", false);
			do_SaveOrder();
        } else {
            selectSpecialOptionByItem(mainFrm.faContentCode, contentCode);
        }
    }
}

function do_excel() {
    if(transferType == "" || (transferType != "" && transferType != "<%=AssetsDictConstant.TRANS_BTW_COMP%>")){
		var fromDept = mainFrm.fromDept.value;
		if(fromDept == ""){
			alert("请先选择部门，再选择资产！");
			mainFrm.fromDept.focus();
			return;
		}
	}
    if(mainFrm.faContentCode.value == ""){
		alert("请先选择资产种类，再选择资产！");
		mainFrm.faContentCode.focus();
		return;
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
    var ajaxProcessor = new AjaxProcessor(url, do_SetDeptLevel, false);
    ajaxProcessor.performTask();
}

function do_SetDeptLevel(resText) {
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
		} else {
			var depts = document.getElementsByName("responsibilityDept");
			mainFrm.toDept.value = depts[0].value;
		}
	}
    mainFrm.act.value = "<%=AssetsActionConstant.SUBMIT_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}

</script>