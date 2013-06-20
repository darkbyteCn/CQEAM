<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String status = headerDTO.getTransStatus();
	String transType = headerDTO.getTransType();
	String transferType = headerDTO.getTransferType();
	String transId = headerDTO.getTransId();
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId();
	String userId = userAccount.getUserId();      
	String attribute1 = headerDTO.getAttribute1();
%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>      
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="initPage();">
<form action="<%=AssetsURLList.ASSETS_TRANS_SERVLET%>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/flow/include.jsp" flush="true"/>
<jsp:include page="/newasset/transferHeader_42.jsp" flush="true"/>
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
<%
	if(!transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
%>
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<%
	}	
%>
<input type="hidden" name="attribute1" value="<%=attribute1%>">
<input type="hidden" name="transType" value="<%=transType%>">
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>">
<input type="hidden" name="created" value="<%=headerDTO.getCreated()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=transId%>">
<input type="hidden" name="procdureName" value="<%=headerDTO.getProcdureName()%>">
<input type="hidden" name="act" value="">
<fieldset style="border:1px solid #397DF3; position:absolute;top:123px;width:100%;height:70%">
    <legend>
<%
	if(!attribute1.equals(AssetsDictConstant.TRANS_EDIT_YES)){
%>
        <img src="/images/eam_images/tmp_save.jpg" alt="暂存" onClick="do_SaveOrder(); return false;">
<%
	}	
%>
        <img src="/images/eam_images/submit.jpg" alt="完成" onClick="do_SubmitOrder(); return false;">
<%
	if(status.equals(AssetsDictConstant.SAVE_TEMP))	{
%>
        <img src="/images/eam_images/revoke.jpg" alt="撤消" onClick="do_CancelOrder();">
<%
	}	
	if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
		if(attribute1.equals(AssetsDictConstant.TRANS_EDIT_YES)){
%>
        <img src="/images/eam_images/choose.jpg" alt="选择资产" onClick="do_SelectAssets(); return false;">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
<% 
		}
	} else {
%>
        <img src="/images/eam_images/choose.jpg" alt="选择资产" onClick="do_SelectAssets(); return false;">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
<% 

	}
	if(transType.equals(AssetsDictConstant.ASS_RED)){
		if(transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
			if(attribute1.equals(AssetsDictConstant.TRANS_EDIT_YES)){
%>
        <img src="/images/button/pasteData.gif"  alt="粘贴EXCEL" onClick="doPaste(); return false;">
<% 
			}
		} else {
%>
        <img src="/images/button/pasteData.gif"  alt="粘贴EXCEL" onClick="doPaste(); return false;">
<%
		}
	}
%>
        <span id="warn"></span>
<%
	if(!status.equals("") && !status.equals(AssetsDictConstant.SAVE_TEMP)){
%>
        <img src="/images/eam_images/view_opinion.jpg" alt="查阅审批意见" onClick="viewOpinion(); return false;">
        <img src="/images/eam_images/view_flow.jpg" alt="查阅流程" onClick="viewFlow(); return false;">
<%
	}	
%>
        <img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="do_Close(); return false;">
<%
	if(transType.equals(AssetsDictConstant.ASS_RED)){
%>
		统一设置：
<%
		if(!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT)){
%>
		<input type="checkbox" name="allDept" id="allDept"><label for="allDept">新责任部门</label>
<%
    	}
%>
        <input type="checkbox" name="allLocation" id="allLocation"><label for="allLocation">调入地点</label>
		<input type="checkbox" name="allUser" id="allUser"><label for="allUser">新责任人</label>
<%
		if((!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT))&&(headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP))){
%>
		<input type="checkbox" name="allAccount" id="allAccount"><label for="allAccount">新折旧账户</label>
		<input type="checkbox" name="allFaCategory" id="allFaCategory"><label for="allFaCategory">新类别</label>
<%
		}	
%>
		<input type="checkbox" name="allTransDate" id="allTransDate"><label for="allTransDate">调拨日期</label>
<%
	}
%>
	</legend>
<jsp:include page="/newasset/transferLine_42.jsp" flush="true"/>
</fieldset>
     <%
        if (transType.equals(AssetsDictConstant.ASS_RED)||transType.equals(AssetsDictConstant.ASS_DIS)) {
    %>

    <div style="position:absolute;bottom:0px;top:620px;left:0px;right:0px;width:100%;height:100%">
         <jsp:include page="/newasset/uploadInclude.jsp" flush="true"/>
        <% if (transType.equals(AssetsDictConstant.ASS_RED)){ %>
          &nbsp;&nbsp;&nbsp<input type="button" name="sub" value="EXCEL模板下载" onclick="do_exportToExcel();">
        <%} %>
    </div>
      <%
          }
    %>

<%--<fieldset style="border:1px solid #397DF3; position:absolute;top:620px;width:100%;height:10%">--%>
<%--<legend>调拨明细上载</legend>--%>
   <%--<% if (transType.equals(AssetsDictConstant.ASS_RED)){ %>--%>
    <%--&nbsp;&nbsp;&nbsp;&nbsp<input type="button" name="sub" value="EXCEL模板下载" onclick="do_exportToExcel();">--%>
    <%--<%} %>--%>
<%--</fieldset>--%>
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>

</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;"></iframe>
<script type="text/javascript">
var srcToOrgValue = "";
var srcDeptValue = null;
var transferType = "";
var attribute1 = "<%=attribute1%>";

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
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_TRANSFER_ASSETS%>";
	var userPara = "transType=" + mainFrm.transType.value;

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
    if(!deleteTableRow(dataTable, 'subCheck'))return;
	do_delete();
}

//粘贴EXCEL        
var xmlHttp;
var segment10Array = new Array();
var projectNameArray = new Array();
var segment10Index = -1;
var projectNameIndex = -1;
var mark = -1;
function doPaste() {
	try {
		if(transferType != "" && transferType != "<%=AssetsDictConstant.TRANS_BTW_COMP%>"){
		var fromDept = mainFrm.fromDept.value;
		if(fromDept == ""){
			alert("请先选择部门，再粘贴！");
			mainFrm.fromDept.focus();
			return;
		 }
	   } else if(transferType != "" && transferType == "<%=AssetsDictConstant.TRANS_BTW_COMP%>"){
		var toOrganizationId = mainFrm.toOrganizationId.value;
		if(toOrganizationId == ""){
		   alert("请先选择调往公司，再粘贴。");
		   mainFrm.toOrganizationId.focus();
		   return;
		}
	  }
		if (confirm("确定粘贴到当前页面？")) {    
			var text = window.clipboardData.getData("text");
			if (text == null || text == "") {
				alert("请先在EXCEL摸板里复制订单行数据，然后再粘贴！");
				return;
			}
			var rows = text.split('\n');
			for (var i = 0; i < rows.length - 1; i++) {
				mark ++;
				var row = rows[i];
				insertRow(row);
			}
			pageVerifySegment10();
		}
    } catch(e) {
        alert(e.description);
        alert("粘贴失败!");
    }
}

function insertRow(row) {
    var cols;
    if (typeof(row) == 'string') {
        cols = row.split('\t');
    } else {
        cols = row;
    }                                    
    var newRow = document.getElementById("model").cloneNode(true);  
    newRow.style.display = 'block';
    newRow.childNodes[0].childNodes[0].value = mark;
    newRow.childNodes[1].childNodes[0].value = cols[0];
    newRow.childNodes[2].childNodes[0].value = cols[1];
    newRow.childNodes[3].childNodes[0].value = cols[2];
    newRow.childNodes[4].childNodes[0].value = cols[3];
    newRow.childNodes[5].childNodes[0].value = cols[4];
<%
	if(headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT)){
%>
    newRow.childNodes[6].childNodes[0].value = cols[6];
    newRow.childNodes[7].childNodes[0].value = cols[8];
    newRow.childNodes[8].childNodes[0].value = cols[10];
    newRow.childNodes[9].childNodes[0].value = cols[12];
    newRow.childNodes[10].childNodes[0].value = cols[13];
    newRow.childNodes[11].childNodes[0].value = cols[14];
    newRow.childNodes[12].childNodes[0].value = cols[5];
    newRow.childNodes[13].childNodes[0].value = cols[7];
    newRow.childNodes[14].childNodes[0].value = cols[9];
    newRow.childNodes[15].childNodes[0].value = cols[11];
 <%
 	} else if(headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_DEPT)){
%>
    newRow.childNodes[6].childNodes[0].value = cols[6];
    newRow.childNodes[7].childNodes[0].value = cols[8];
    newRow.childNodes[8].childNodes[0].value = cols[10];
    newRow.childNodes[9].childNodes[0].value = cols[12];
    newRow.childNodes[10].childNodes[0].value = cols[14];
    newRow.childNodes[11].childNodes[0].value = cols[15];
    newRow.childNodes[12].childNodes[0].value = cols[16];
    newRow.childNodes[13].childNodes[0].value = cols[5];
    newRow.childNodes[14].childNodes[0].value = cols[7];
    newRow.childNodes[15].childNodes[0].value = cols[9];
    newRow.childNodes[16].childNodes[0].value = cols[11];
    newRow.childNodes[17].childNodes[0].value = cols[13];
<%
	} else if(headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP)){
%>
    newRow.childNodes[6].childNodes[0].value = cols[5];
    newRow.childNodes[7].childNodes[0].value = cols[6];
    newRow.childNodes[8].childNodes[0].value = cols[7];
    newRow.childNodes[9].childNodes[0].value = cols[8];
    newRow.childNodes[10].childNodes[0].value = cols[10];
    newRow.childNodes[11].childNodes[0].value = cols[12];
    newRow.childNodes[12].childNodes[0].value = cols[14];
    newRow.childNodes[13].childNodes[0].value = cols[15];
    newRow.childNodes[14].childNodes[0].value = cols[16];
    newRow.childNodes[15].childNodes[0].value = cols[18];
    newRow.childNodes[16].childNodes[0].value = cols[20];
    newRow.childNodes[17].childNodes[0].value = cols[22];
    newRow.childNodes[18].childNodes[0].value = cols[23];
    newRow.childNodes[19].childNodes[0].value = cols[24];
    newRow.childNodes[20].childNodes[0].value = cols[25];
    newRow.childNodes[21].childNodes[0].value = cols[26];
    newRow.childNodes[22].childNodes[0].value = cols[9];
    newRow.childNodes[23].childNodes[0].value = cols[11];
    newRow.childNodes[24].childNodes[0].value = cols[13];
    newRow.childNodes[25].childNodes[0].value = cols[17];
    newRow.childNodes[26].childNodes[0].value = cols[19];
    newRow.childNodes[27].childNodes[0].value = cols[21];
<%
	}
%>
    document.getElementById("model").parentNode.appendChild(newRow);
}

function pageVerifySegment10() {
    var warn = document.getElementById('warn');
    warn.innerText = '';
    doInitArray();                        
    xmlHttp = createXMLHttpRequest();
    var url = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsTransHeaderServlet" ;
    xmlHttp.open('POST', url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send(segment10Array.toJSONString());
}


function doInitArray() {
    segment10Array = new Array();
    projectNameArray = new Array();
    projectNameIndex = -1;
    var segment10 = document.getElementsByName("segment10");
    for (var i = 2; i < segment10.length; i++) {
        segment10Array[i - 2] = segment10[i].value;
    }
    var projectName = document.getElementsByName("projectName");
    for (var i = 2; i < projectName.length; i++) {
        if (!isEmpty(projectName[i].value)) {
            projectNameIndex++;
            projectNameArray[projectNameIndex] = projectName[i].value;
        }
    }
}
function do_delete(){
   mainFrm.act.value = "<%=AssetsActionConstant.DELETE_ACTION%>";
    mainFrm.submit();
}
function do_SaveOrder() {
    mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
	//document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
    //document.mainFrm.transNo.
    mainFrm.submit();
     //document.getElementById("$$$visibleMsg$$$").style.visibility = "disable";
}

function do_SubmitOrder() {

    if (attribute1 == "<%=AssetsDictConstant.TRANS_EDIT_YES%>") {
        if (dataTable.rows.length == 0 || (dataTable.rows[0].style.display == 'none' && dataTable.rows.length == 1)) {
            alert("没有选择行数据，请选择行数据！");
            return;
        }
    }
    if(transferType == "<%=AssetsDictConstant.TRANS_BTW_COMP%>"&&attribute1=="<%=AssetsDictConstant.TRANS_EDIT_YES%>"){
          var fieldNames = "responsibilityDeptName";
	var fieldLabels = "调入部门";
	var validateType = EMPTY_VALIDATE;
	if(formValidate(fieldNames, fieldLabels, validateType)){
	}else {return;}

    }
    if (!validate()) {
        return;
    }
	var orgId = "<%=orgId%>";
	var userId = "<%=userId%>";
	var groupId = mainFrm.fromGroup.value;
	var procdureName = mainFrm.procdureName.value;
	var flowCode = "";
	var paramObj = new Object();
	if(attribute1 != "<%=AssetsDictConstant.TRANS_EDIT_YES%>"){//创建申请时
		if(orgId == ""){
			orgId = mainFrm.toOrganizationId.value;
		}
//		if(mainFrm.transferType && mainFrm.transferType.value == "<%=AssetsDictConstant.TRANS_BTW_COMP%>"){
			flowCode = mainFrm.faContentCode.value;
//		}
	} else {//审批过程中进入该页面
		var sectionRight = mainFrm.sectionRight.value;
		var hiddenValue = mainFrm.hiddenRight.value;
		if(mainFrm.attribute2.value != ""){
			orgId = document.getElementById(mainFrm.attribute2.value).value;
		}
		flowCode = "<%=AssetsDictConstant.OTHER_FLOW%>";
		if(transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>"){
			if(sectionRight == "<%=AssetsDictConstant.END_INN_DEPT%>"){
				flowCode = "<%=AssetsDictConstant.TRANS_INN_DEPT%>";
			} else if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
				flowCode = mainFrm.faContentCode.value;
			}
		} else if(transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>"){
            if(sectionRight == "<%=AssetsDictConstant.END_BTW_DEPT%>"){
				flowCode = "<%=AssetsDictConstant.TRANS_BTW_DEPT%>";
			} else if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
				flowCode = mainFrm.faContentCode.value;
			}
		} else if(transferType == "<%=AssetsDictConstant.TRANS_BTW_COMP%>"){
			if(sectionRight == "<%=AssetsDictConstant.END_BTW_COMP%>"){
				flowCode = "<%=AssetsDictConstant.TRANS_BTW_COMP%>";
			} else if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
				flowCode = mainFrm.faContentCode.value;
			}
		}
	}
	paramObj.orgId = orgId;
	paramObj.useId = userId;
	paramObj.groupId = groupId;
	paramObj.procdureName = procdureName;
	paramObj.flowCode = flowCode;
	paramObj.submitH = 'submitH()';
//	alert("flowCode = " + flowCode);
	assign(paramObj);
}


function submitH() {
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
    document.getElementById("table1").disabled = false;
    mainFrm.act.value = "<%=AssetsActionConstant.SUBMIT_ACTION%>";
    mainFrm.submit();
    document.getElementById("$$$visibleMsg$$$").style.visibility = "disable";
}

function initPage() {
    window.focus();
	if(attribute1 == "<%=AssetsDictConstant.TRANS_EDIT_YES%>"){//审批过程中进入该页面
		document.getElementById("table1").disabled = true;
//		alert(document.getElementById("table1").disabled);
	} else {
		var fromGroup = mainFrm.fromGroup.value;
		if(fromGroup == ""){
			do_SelectCreateGroup();
		}
	}
}


function do_SelectCreateGroup(){
	var fromGroup = mainFrm.fromGroup.value;
	var url = "<%=AssetsURLList.GROUP_CHOOSE_SERVLET%>";
	url += "?transType=<%=transType%>";
	url += "&transferType=<%=transferType%>";
	var popscript = "dialogWidth:20;dialogHeight:10;center:yes;status:no;scrollbars:no;help:no";
	var group = window.showModalDialog(url, null, popscript);
	if(group){
		dto2Frm(group, "mainFrm");
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
	if(transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>"){
		deptCode = mainFrm.fromDept.value;
	}
	userPara += "&deptCode=" + deptCode;

	var objName = lineBox.name;
	var objId = lineBox.id;
    var lookUpName=""  ;
    var idNumber = objId.substring(objName.length);
           if(transferType=="<%=AssetsDictConstant.TRANS_BTW_COMP%>"){
             lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_DEPT_NM%>";
           }else{
               lookUpName = "<%=AssetsLookUpConstant.LOOK_UP_DEPT%>";
           }

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
		} else {
			emptyData = true;
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
			obj = objs[0];
			lineBox.value = obj["accountCode"];
		} else {
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
		var accounts = document.getElementsByName("depreciationAccount");
		var count = accounts.length;
		for(var i = 0; i < count; i++){
			if(emptyData){
				accounts[i].value = "";
			} else {
				accounts[i].value = obj["accountCode"];
			}
		}
	}
}


function validate() {
	var isValid = true;
	var transType = mainFrm.transType.value;
    var fieldLabels = "";
	var fieldNames = "";
	var validateType = "";
    if(transferType == "<%=AssetsDictConstant.TRANS_BTW_COMP%>"){
		fieldLabels = "调出公司;调入公司;资产大类;调拨说明";
		fieldNames = "fromOrganizationId;toOrganizationId;faContentCode;createdReason";
		validateType = EMPTY_VALIDATE;
		isValid = formValidate(fieldNames, fieldLabels, validateType);
		if(isValid){
			if(mainFrm.fromOrganizationId.value == mainFrm.toOrganizationId.value){
				alert("公司间调拨时，调出公司和调入公司不能为同一公司");
				isValid = false;
			}
		}
    } else {
		if (dataTable.rows.length == 0 || (dataTable.rows[0].style.display == 'none' && dataTable.rows.length == 1)) {
			alert("没有选择行数据，请选择行数据！");
			isValid = false;
		} else {
			fieldLabels = "说明;资产大类";
			fieldNames = "createdReason;faContentCode";
			validateType = EMPTY_VALIDATE;
			if(transferType){
				if(transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT%>"){
					fieldLabels += ";调入部门";
					fieldNames += ";responsibilityDeptName";
				} else {
					fieldLabels += ";调入地点;新责任人";
					fieldNames += ";assignedToLocationName;responsibilityUserName";
				}
			} else if(transType == "<%=AssetsDictConstant.ASS_SUB%>"){
				fieldLabels += ";损耗名称";
				fieldNames += ";lossesName";
			}
            isValid = formValidate(fieldNames, fieldLabels, validateType);
        }
		if(isValid){
			var rows = dataTable.rows;
			if(transType == "<%=AssetsDictConstant.ASS_SUB%>" && rows[0].style.display != 'none'){
				var row = null;
				for(var i = 0; i < rows.length; i++){
					row = rows[i];
					var barcode = getNodeObject(row, "barcode");
					var deprnCost = getNodeObject(row, "deprnCost");
					var prepareDevalue = getNodeObject(row, "prepareDevalue");
					if(Number(prepareDevalue.value) > Number(deprnCost.value)){
						alert("输入非法，原因是：资产“"+barcode.value +"”的“拟提减值"+prepareDevalue.value+"”大于或等于“净值"+deprnCost.value+"”。")
						prepareDevalue.focus();
						prepareDevalue.select();
						isValid = false;
					}
				}
			}
		}
	}
	return isValid;
}

function do_ShowDetail(td){
	var transType = mainFrm.transType.value;
	tr = td.parentNode;
	cells = tr.cells;
	var cell = cells[1];
	if(transType == "<%=AssetsDictConstant.ASS_SUB%>"){
		cell = cells[3];
	}
	var barcode = cell.childNodes[0].value;
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}

function do_CancelOrder() {
	if(confirm("你正准备撤销本单据，确定吗？继续请点击“确定”按钮，否则请点击“取消”按钮!")){
		mainFrm.act.value = "<%=AssetsActionConstant.CANCEL_ACTION%>";
		mainFrm.submit();
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

function do_ClearLineData(){
	var rows = dataTable.rows;
	if(rows){
		var rowCount = rows.length;
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
function do_ChangeContentCode(){
	var rows = dataTable.rows;
	var rowCount = rows.length;
    if (rowCount > 1 || (rowCount == 1 && rows[0].style.display != "none")) {
		if(confirm("改变资产大类将删除已经选择的资产数据，是否继续？继续请点击“确定”按钮，否则请点击“取消”按钮")){
			deleteRow(dataTable);
			contentCode = mainFrm.fromDept.value;
			setCheckBoxState("mainCheck", false);
			do_SaveOrder();
		} else {
			selectSpecialOptionByItem(mainFrm.faContentCode, contentCode);
		}
	}
}

function do_ChangeBookType(sel){
//	alert(sel).options[0].innerHTML;
	if(sel.value == ""){
		mainFrm.bookTypeName.value = "";
	} else {
		mainFrm.bookTypeName.value = getSpecialOptionText("bookTypeCode", sel.value);
	}
	var url = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsTransHeaderServlet";
	url += "?act=GET_TARGET_OU"
	url += "&fromOrganizationId=" + mainFrm.fromOrganizationId.value;
	url += "&toOrganizationId=" + mainFrm.toOrganizationId.value;
	do_ProcessSimpleAjax(url, null);
}

/**
 * 将返回的列表加入区县下拉框，由贾龙川继续，目前有错误。
 */
function do_ProcessResponse(responseContent){
	mainFrm.toOrganizationId.outerHTML = "<select name = \"toOrganizationId\" style=\"width:100%\" class=\"noEmptyInput\" onChange=\"do_ClearLineData()\">" + responseContent + "</select>";
}
</script>