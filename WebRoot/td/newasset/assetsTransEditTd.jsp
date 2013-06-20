<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserRightDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/td/newasset/headerIncludeTd.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	TdAssetsTransHeaderDTO headerDTO = (TdAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    String status = headerDTO.getTransStatus();
	String transType = headerDTO.getTransType();
	String transferType = headerDTO.getTransferType();
	String transId = headerDTO.getTransId();
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    boolean isDepAM = userAccount.isDptAssetsManager();
    DTOSet userDTO = userAccount.getUserRight();
    String roleName = "";
    Map  userRightMap = new HashMap();
    for (int i = 0;i<userDTO.getSize();i++) {
        SfUserRightDTO userRight = (SfUserRightDTO)userDTO.getDTO(i);
        roleName = userRight.getRolename();
        userRightMap.put(roleName,roleName);
    }
    boolean isDptMgr = userRightMap.containsValue("部门经理");
    String orgId = userAccount.getOrganizationId();
	String userId = userAccount.getUserId();
	String provinceCode = headerDTO.getServletConfig().getProvinceCode();
    String isGroupPID = request.getAttribute(AssetsWebAttributes.IS_GROUP_PID).toString();//是否市公司综合部流程组别
    //EXCEL黏贴
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.WORKORDER_LOC_ROWSET);
    boolean isMtlMana = userAccount.isMtlAssetsManager();
    DTOSet lineSetPri2 = (DTOSet) request.getAttribute(AssetsWebAttributes.PRIVI_DATA);//EXCEL导入时导入不成功的DTOSET
%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>      
</head>
<body leftmargin="0" topmargin="0" onload="initPage();">
<form action="<%=TdURLDefineList.ASSETS_TRANS_SERVLET_TD%>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/flow/include.jsp" flush="true"/>
<jsp:include page="/td/newasset/transferHeaderTd.jsp" flush="true"/>
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="transType" value="<%=transType%>">
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>">
<input type="hidden" name="created" value="<%=headerDTO.getCreated()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=transId%>">

<input type="hidden" name="procdureName" value="<%=headerDTO.getProcdureName()%>">

<input type="hidden" name="toDept" value="<%=headerDTO.getToDept()%>">
<input type="hidden" name="toGroup" value="<%=headerDTO.getToGroup()%>">
<input type="hidden" name="act" value="">

<input type="hidden" name="excel" value="">
<fieldset style="border:1px solid #397DF3; position:absolute;top:123px;width:100%;height:70%">
    <legend>
        <img src="/images/eam_images/tmp_save.jpg" alt="暂存" onClick="do_SaveOrder(); return false;">
        <img src="/images/eam_images/submit.jpg" alt="完成" onClick="do_SubmitOrder(); return false;">
<%
	if(status.equals(AssetsDictConstant.SAVE_TEMP) || status.equals(AssetsDictConstant.REJECTED))	{
%>
        <img src="/images/eam_images/revoke.jpg" alt="撤消" onClick="do_CancelOrder();">
<%
	}	
%>
        <img src="/images/eam_images/choose.jpg" alt="选择资产" onClick="do_SelectAssets(); return false;">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">

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

<%
	if(transType.equals(AssetsDictConstant.ASS_RED)){
%>
		统一设置：
<%
		if(!headerDTO.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT)){
%>
        <input type="checkbox" name="allDept" id="allDept" checked style="display:none"><label for="allDept" style="display:none">新责任部门</label>
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
<jsp:include page="/td/newasset/transferLineTd.jsp" flush="true"/>
</fieldset>

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
	var lookUpName = "<%=AssetsLookUpConstant.LOOK_TRANSFER_ASSETS_TD%>";
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
    var tab = document.getElementById("dataTable");
    deleteTableRow(tab, 'subCheck');
//    if(deleteTableRow(dataTable, 'subCheck')){
//		do_SaveOrder();
//	}
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
    var url = "/servlet/com.sino.td.newasset.servlet.TdAssetsTransHeaderServlet" ;
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

function do_SaveOrder() {
	var fromGroupName = mainFrm.fromGroupName.value;
	if(fromGroupName == ""){
		alert("输入非法，原因是：“建单组别”必须填写，不能为空！");
		mainFrm.fromGroupName.focus();
		return;
	}
    mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_SubmitOrder() {
    if (!validate()) {
        return;
    }
	var transType = "<%=transType%>";
	var transferType = "<%=transferType%>";
	var provinceCode = "<%=provinceCode%>";
    //判断报废成本
    if (transType == "<%=AssetsDictConstant.ASS_DIS%>") {
        var isVilidate = do_valiQuantity();
        if (!isVilidate) {
            return;
        }
    }
    if(transferType != ""){
		if(transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>"){
			mainFrm.toDept.value = mainFrm.fromDept.value;
		} else {
			var depts = document.getElementsByName("responsibilityDept");
			mainFrm.toDept.value = depts[0].value;
		}
	}
    var orgId = "<%=orgId%>";
	var userId = "<%=userId%>";
    var groupId = mainFrm.fromGroup.value;
    var procdureName = mainFrm.procdureName.value;
//    alert(procdureName);
    var flowCode = "";
	if(transferType == "<%=AssetsDictConstant.TRANS_BTW_COMP%>"){
		flowCode = mainFrm.faContentCode.value;//管理类或网络类
        flowCode = "OTHER";
    }
    if(transType == "<%=AssetsDictConstant.ASS_SUB%>"){
        flowCode = mainFrm.faContentCode.value;        
    }
    var isDepAM = "<%=isDepAM%>";
    var isGroupPID = "<%=isGroupPID%>";
    var isDptMgr ="<%=isDptMgr%>";
//    alert("isDepAM--" + isDepAM + "        isGroupPID--" + isGroupPID + "     isDptMgr--" + isDptMgr + "    faContentCode--" + faContentCode + "   transferType--" + transferType);
    if (transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>" || transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>") {

        if ((isDepAM == "true" && isGroupPID == "false")  || (isDptMgr == "true" && isGroupPID == "false" && transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>")){
            flowCode = "MANAGER";
        }
        if ((isGroupPID == "false" && isDepAM == "false" && isDptMgr == "false") || (isGroupPID == "true" && isDepAM == "false" && isDptMgr == "false" && transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>")){
            flowCode = "OTHER";
        }
        if ((isDptMgr == "true" && isGroupPID == "false") || (isGroupPID == "true" && isDepAM == "false" && isDptMgr == "false") && (transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>")) {
            flowCode = "MULTI";
        }
        if (isDepAM == "true" && isGroupPID == "true" && transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>") {
            flowCode = "MUL-MANA";
        }
        if (isDepAM == "true" && isGroupPID == "true" && transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>") {
            flowCode = "MANAGER";
        }
        if (isDptMgr == "true" && isGroupPID == "true"&& transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>") {
            flowCode = "FINANCE";
        }
        alert(flowCode);
        <%--if (transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>" && isDptMgr == "true") {--%>
//            flowCode = "OTHER";
//        }
    }
    if (transType == "<%=AssetsDictConstant.ASS_DIS%>") {
        var faContentCode = mainFrm.faContentCode.value;
        var isMtlMana = "<%=isMtlMana%>";
        if (orgId == "82" && faContentCode == "MGR-ASSETS" && isMtlMana == "true") {
            flowCode = "P_MTL_MGR";
        } else if (orgId == "82" && faContentCode == "NET-ASSETS" && isMtlMana == "true") {
            flowCode = "P_MTL_NET";
        } else if (orgId == "82" && faContentCode == "MAR-ASSETS" && isMtlMana == "true") {
            flowCode = "P_MTL_MAR";
        } else if (orgId != "82" && faContentCode == "MGR-ASSETS" && isMtlMana == "true") {
            flowCode = "C_MTL_MGR";
        } else if (orgId != "82" && faContentCode == "NET-ASSETS" && isMtlMana == "true") {
            flowCode = "C_MTL_NET";
        } else if (orgId != "82" && faContentCode == "MAR-ASSETS" && isMtlMana == "true") {
            flowCode = "C_MTL_MAR";
        } else {
            flowCode = "OTHER";
        }
    }
    if(transType == "<%=AssetsDictConstant.ASS_SHARE %>" || transType == "<%=AssetsDictConstant.ASS_SELL %>"  || transType == "<%=AssetsDictConstant.ASS_RENT %>" || transType == "<%=AssetsDictConstant.ASS_DONA %>" ){
			if(isDptMgr=="true" ){
	     		 if(orgId !=82  && faContentCode=="NET-ASSETS" && isGroupPID == "false"){
		             flowCode="C-NET";
		         }else if(orgId == 82 && isGroupPID == "true"){
		         	 flowCode="END";
		         } else if(orgId !=82 && faContentCode=="MAR-ASSETS" && isGroupPID == "false"){
		             flowCode="C-MAR" ;
		         } else if (orgId !=82  && faContentCode=="MGR-ASSETS" && isGroupPID == "false"){
		             flowCode="C-MGR";
		         }else if((orgId != 82  && faContentCode=="NET-ASSETS" && isGroupPID == "true") || (orgId == 82 && faContentCode=="NET-ASSETS" && isGroupPID == "false")) {
		             flowCode="P-NET" ;
		         } else if((orgId != 82 && faContentCode=="MAR-ASSETS" && isGroupPID == "true") || (orgId == 82 && faContentCode=="MAR-ASSETS" && isGroupPID == "false")) {
		             flowCode="P-MAR";
		         }else if((orgId != 82 && faContentCode=="MGR-ASSETS" && isGroupPID == "true") || (orgId == 82 && faContentCode=="MGR-ASSETS" && isGroupPID == "false")){
		             flowCode="P-MGR";
		         }       	         
	        }
	 }
    if(provinceCode == "<%=AssetsDictConstant.PROVINCE_CODE_SX%>"){//山西
		if(transType == "<%=AssetsDictConstant.ASS_DIS%>"){//报废
			flowCode = mainFrm.faContentCode.value;
		} else if(transType == "<%=AssetsDictConstant.ASS_CLR%>"){//处置
			flowCode = mainFrm.faContentCode.value;
		} else if(transType == "<%=AssetsDictConstant.ASS_FREE%>"){//闲置
			flowCode = mainFrm.faContentCode.value;
		}
	}
    var paramObj = new Object();
    paramObj.orgId = orgId;
    paramObj.useId = userId;
    paramObj.groupId = groupId;
    paramObj.procdureName = procdureName;
    paramObj.flowCode = flowCode;
    paramObj.submitH = 'submitH()';
    assign(paramObj);
}


function submitH() {
    mainFrm.act.value = "<%=AssetsActionConstant.SUBMIT_ACTION%>";
    mainFrm.submit();
}


function initPage() {
    window.focus();
    do_SetPageWidth();
	var fromGroup = mainFrm.fromGroup.value;
	if(fromGroup == ""){
		do_SelectCreateGroup();
	}
}


function do_SelectCreateGroup(){
	var fromGroup = mainFrm.fromGroup.value;
	var url = "<%=AssetsURLList.GROUP_CHOOSE_SERVLET%>?fromGroup=" + fromGroup;
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
//        var toGroups = document.getElementsByName("toGroup");
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
//                toGroups[i].value = "";
            } else {
                deptNames[i].value = obj["toDeptName"];
				depts[i].value = obj["toDept"];
//                toGroups[i].value = obj["toGroup"];
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
				accounts[i].value = obj["accountCode"];
			}
		}
	}
}


function validate() {
    var isValid = true;
    var transType = mainFrm.transType.value;
    if (dataTable.rows.length == 0 || (dataTable.rows[0].style.display == 'none' && dataTable.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        isValid = false;
    } else {
        var fieldLabels = "建单组别;原因";
        var fieldNames = "fromGroupName;createdReason";
        var validateType = EMPTY_VALIDATE;
        if (transferType) {
            if (transferType != "<%=AssetsDictConstant.TRANS_INN_DEPT%>") {
                fieldLabels += ";调入部门";
                fieldNames += ";responsibilityDeptName";
            } else {
                fieldLabels += ";调入地点;新责任人";
                fieldNames += ";assignedToLocationName;responsibilityUserName";
            }
        } else {
            if (transType == "<%=AssetsDictConstant.ASS_SUB%>") {
                fieldLabels = "损耗名称;损耗日期";
                fieldNames = "lossesName;lossesDate";
            }
        }
        isValid = formValidate(fieldNames, fieldLabels, validateType);
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
	var url = "/servlet/com.sino.td.newasset.servlet.EtsFaTdAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=800,height=360,left=70,top=100";
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
    var url="/workorder/bts/upFile.jsp";
    var dialogStyle = "dialogWidth=18;dialogHeight=6;help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scroll=no";
    var aa=window.showModalDialog(url,"",dialogStyle);
    if (aa) {
        document.mainFrm.excel.value = aa;
        document.mainFrm.act.value = "excel";
        document.mainFrm.submit();
    }
}

function do_CloseDiv() {
    document.getElementById("transferDiv").style.visibility = "hidden";
}
function do_Transfer() {
    document.getElementById("transferDiv").style.visibility = "visible";
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
</script>