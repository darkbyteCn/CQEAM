<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsDictConstant"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserRightDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    //String status = headerDTO.getTransStatus();
	String transType = headerDTO.getTransType();
	String transId = headerDTO.getTransId();
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
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
    String isGroupPID = request.getAttribute(AssetsWebAttributes.IS_GROUP_PID).toString();//是否市公司综合部流程组别
    String isFinanceGroup = request.getAttribute(AssetsWebAttributes.IS_FINANCE_GROUP).toString();
    
    String isSpecialityDept = StrUtil.nullToString(request.getAttribute("isSpecialityDept"));
    
    //EXCEL黏贴
    boolean isMtlMana = userAccount.isMtlAssetsManager();
    DTOSet lineSetPri2 = (DTOSet) request.getAttribute(AssetsWebAttributes.PRIVI_DATA);//EXCEL导入时导入不成功的DTOSET
    String transStatus = headerDTO.getTransStatus();
    session.setAttribute("lineSetPri",lineSetPri2);
%>
<head>
	<% 	
    	String titleText=headerDTO.getTransTypeValue();
		if (titleText.indexOf("<")!=-1){
			if ("ASS-DONATE".equals(transType)) {
				titleText = "捐赠单";
			} else {
				titleText = "销售单";
			}
		}
    	if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
    		titleText=titleText+"(TD)";
    	}
    %>
	<title><%=titleText%></title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
</head>
<script type="text/javascript">
    printToolBar();
</script>
<%@ include file="/flow/flowNoButton.jsp"%>
<body leftmargin="0" topmargin="0" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()">
<form action="/servlet/com.sino.ams.newasset.rent.servlet.AssetsHeaderServlet" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<%@ include file="/flow/flowPara.jsp" %>
<div id="searchDiv" style="position:absolute;top:25px;left:1px;width:100%">
<jsp:include page="/newasset/rent/assetsHeader.jsp" flush="true"/>
</div>
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="transType" value="<%=transType%>">
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>">
<input type="hidden" name="created" value="<%=headerDTO.getCreated()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=transId%>">
<input type="hidden" name="isGroupPID" value="<%=isGroupPID%>">
<input type="hidden" name="procdureName" value="<%=headerDTO.getProcdureNameIncludeTd(userAccount.getIsTd())%>">

<input type="hidden" name="toDept" value="<%=headerDTO.getToDept()%>">
<input type="hidden" name="toGroup" value="<%=headerDTO.getToGroup()%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="isThred" value="">
<input type="hidden" name="isProvinceUser" id="isProvinceUser" value="<%=userAccount.isProvinceUser()%>">
<input type="hidden" name="isSpecialityDept" id="isSpecialityDept" value="<%=isSpecialityDept %>"> <!-- 是否是实物部门 -->
<input type="hidden" name="excel" value="">
<%
    if (!"IN_PROCESS".equals(transStatus)) {
%>
    <div id="buttonDiv" style="position:absolute;top:195px;left:1px;width:100%">
        <img src="/images/eam_images/choose.jpg" alt="选择资产" onClick="do_SelectAssets(); return false;">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
		<img src="/images/eam_images/imp_from_excel.jpg" alt="Excel导入" onClick="do_excel();">
<%
		if (lineSetPri2 != null && !lineSetPri2.isEmpty()) {
%>
					<img src="/images/eam_images/detail_info.jpg" alt="查看资产标签号未导入成功详细信息" onClick="do_Transfer();">
<%
		}
%>
<% 		
		if (transType.equals("ASS-WASTEFORECAST")) {
%>
		统一设置：
       	<input type="checkbox" name="setRejectType" id="setRejectType"><label for="setRejectType">报废类型</label>
<%
		}
%>
    </div>
<%
    }
%>
        <span id="warn"></span>
<jsp:include page="/newasset/rent/assetsLine.jsp" flush="true"/>
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
	var lookUpName = "LOOK_ASSETS";
	var transTypeValue = mainFrm.transTypeValue.value;
	var transType = mainFrm.transType.value;
	var userPara = "transType=" + transType;
	var responsibilityDept = mainFrm.fromDept.value;
	var specialityDept = mainFrm.specialityDept.value;

    if(transferType != ""){
		userPara += "&transferType=" + transferType;
	}
	
	if (mainFrm.fromDeptName.value == "") {
		alert("请先选择部门，然后才能选择资产");
		return;
	}
	
	//单据类型为借用和送修的时候必须选资产种类
	//if ((transType == "ASS-REPAIR" || transType == "ASS-BORROW" || transType == "ASS-RETURN" || transType == "ASS-WASTEFORECAST" ) && mainFrm.faContentCode.value == "") {
	if( mainFrm.faContentCode && mainFrm.faContentCode.value == "" ){	
		alert("请选择资产种类！");
		return;
	}

	if (mainFrm.specialityDept.value == "") {
		alert("请选择实物管理部门，然后才能选择资产");
		return;
	}

	userPara += "&faContentCode="+mainFrm.faContentCode.value+"&transTypeValue="+transTypeValue+"&responsibilityDept="+responsibilityDept;
	userPara += "&specialityDept="+specialityDept;
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


function do_isProfessionalGroup() {
	var fromGroup = mainFrm.fromGroup.value;
	if(fromGroup != null && fromGroup != ""){
		var url = "<%=AssetsURLList.ASSETS_TRANS_SERVLET%>";
		url += "?act=VALIDATE_ACTION"
		url += "&fromGroup=" + mainFrm.fromGroup.value;
		do_ProcessSimpleAjax(url, null);
	}
}

function do_ProcessResponse(responseContent){
    mainFrm.isGroupPID.value = responseContent;
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
    mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
    mainFrm.submit();
	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function submitH() {
    mainFrm.act.value = "<%=AssetsActionConstant.SUBMIT_ACTION%>";
    mainFrm.submit();
}

function initPage() {
    window.focus();
    do_SetPageWidth();
    doLoad();
    do_ControlProcedureBtn();
    if(document.mainFrm.fromGroup.value=="0"){
	    document.mainFrm.fromGroup.value=document.getElementById("flow_group_id").value;
	    document.mainFrm.fromGroupName.value=document.getElementById("flow_group_name").value;
	    document.mainFrm.fromDept.value=document.getElementById("app_dept_code").value;
	    document.mainFrm.fromDeptName.value=document.getElementById("app_dept_name").value;
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

function validate() {
	return do_AppValidate(); 
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

function do_Save_app() {
	if (!checkRejectTypeNotSelected()) {
		return;
	}
	var transType = mainFrm.transType.value;
	var transTypeValue = mainFrm.transTypeValue.value;
	if (transType=='ASS-SELL' || transType=='ASS-DONATE') {
		mainFrm.transTypeDefine.value = mainFrm.transTypeValue.value;
	}
	if (validate()) {
		mainFrm.act.value = "<%=AssetsActionConstant.SAVE_ACTION%>";
    	mainFrm.submit();
    	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
	}    
}

function do_Complete_app_yy() {
	var transType = mainFrm.transType.value;
	var transTypeValue = mainFrm.transTypeValue.value;
	if (transType=='ASS-SELL' || transType=='ASS-DONATE') {
		mainFrm.transTypeDefine.value = mainFrm.transTypeValue.value;
	}
	if (validate()) {
		mainFrm.act.value = "<%=AssetsActionConstant.SUBMIT_ACTION%>";
    	mainFrm.submit();
    	document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
	}
}


function do_SetCheckCategory(obj){
	if(!mainFrm.setRejectType.checked){
		return;
	}
	var objs = document.getElementsByName("rejectType");
	var dataCount = objs.length;
	var selectedVal = obj.value;
	if(objs && objs.length){		
	
		var chkObj = null;
		var dataTable = document.getElementById("dataTable");
		var rows = dataTable.rows;
		var row = null;
		var checkObj = null;
		var checkedCount = getCheckedBoxCount("subCheck");
		for(var i = 0; i < dataCount; i++){
			if(checkedCount > 0){
				row = rows[i];
				checkObj = row.childNodes[0].childNodes[0];
				if(!checkObj.checked){
					continue;
				}
			}
			chkObj = objs[i];
			selectSpecialOptionByItem(chkObj, selectedVal);
		}
	}
}  

function do_CloseDiv() {
    document.getElementById("transferDiv").style.visibility = "hidden";
     do_SetPageWidth();
}

function do_Transfer() {
    var width = screen.width-10;   
    var height = screen.height-60;   
    window.open("/newasset/assetsDisTransImportErrorInfo.jsp","","left=0,top=0,width="+width+",height="+height+",title=yes,scrollbars=yes,resizable=no,location=no,toolbar=no, menubar=no");
    // document.getElementById("transferDiv").style.visibility = "visible";
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
	
	if(mainFrm.faContentCode.value==""){
		alert("请选择资产种类!");
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

function checkRejectTypeNotSelected() {
	var yn = true;
	var objs = document.getElementsByName("rejectType");
	var dataCount = objs.length;
	for(var i = 0; i < dataCount; i++){
		if ( objs[i].value == "" || objs[i].value == null ) {
			alert("请填写报废原因");
			yn = false;
			objs[i].focus();
			return yn;
		}
	}
	return yn;
}

function SFQuerySave () {
	if (checkRejectTypeNotSelected() == false) {
		Launch_Continue = false;
		//alert("SFQuerySave_false");
	} else {
		Error_Msg = "";
		Launch_Continue = true;
		//alert("SFQuerySave_true");
	}
}

function SFPostSave () {
	if (checkRejectTypeNotSelected() == false) {
		Launch_Continue = false;
		//alert("SFPostSave_false");
	} else {
		Error_Msg = "";
		Launch_Continue = true;
		//alert("SFPostSave_true");
	}
}


function SFQueryComplete () {
	//alert("SFQueryComplete提交");
	if (checkRejectTypeNotSelected() == false) {
		Launch_Continue = false;
	} else {
		Error_Msg = "";
		Launch_Continue = true;
	}
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}

function do_AppValidate() {
    var isValid = true;
    
    if( mainFrm.faContentCode.value == "" ){
		alert("请选择资产种类！");
		isValid = false;
	}
    
    
    var transType = mainFrm.transType.value;
    if( isValid ){
    	//单据类型为借用和送修的时候必须选资产种类
		//if ((transType == "ASS-BORROW" || transType == "ASS-RETURN" || transType == "ASS-WASTEFORECAST" ) && mainFrm.faContentCode.value == "") {
		var createdReason = mainFrm.createdReason.value;
	    if( createdReason == "" ){
	    	alert("请填写说明！");
	    	isValid = false;
	    }
	}
	
	if( isValid ){
	    if (dataTable.rows.length == 0 || (dataTable.rows[0].style.display == 'none' && dataTable.rows.length == 1)) {
	        alert("没有选择行数据，请选择行数据！");
	        isValid = false;
	    }
    }
    return isValid;
}

</script>