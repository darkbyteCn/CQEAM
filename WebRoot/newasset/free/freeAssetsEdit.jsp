<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsDictConstant"%>
<%@ page import="com.sino.ams.newasset.constant.AssetsLookUpConstant"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserRightDTO"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    //String status = headerDTO.getTransStatus();
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
        roleName = userRight.getRoleName();
        userRightMap.put(roleName,roleName);
    }
    boolean isDptMgr = userRightMap.containsValue("部门经理");
    int orgId = userAccount.getOrganizationId();
	int userId = userAccount.getUserId();
	String provinceCode = headerDTO.getServletConfig().getProvinceCode();
    String isGroupPID = request.getAttribute(AssetsWebAttributes.IS_GROUP_PID).toString();//是否市公司综合部流程组别
    String isFinanceGroup = request.getAttribute(AssetsWebAttributes.IS_FINANCE_GROUP).toString();
    //EXCEL黏贴
    boolean isMtlMana = userAccount.isMtlAssetsManager();
    DTOSet lineSetPri2 = (DTOSet) request.getAttribute(AssetsWebAttributes.PRIVI_DATA);//EXCEL导入时导入不成功的DTOSET
    session.setAttribute("lineSetPri",lineSetPri2);
    String isSpecialityDept = request.getAttribute("isSpecialityDept").toString();
    String isTdProvinceUser = request.getAttribute("isTdProvinceUser").toString();//Td省公司用户
    String transStatus = headerDTO.getTransStatus();
    
    //boolean needBtnDiv = (((!"IN_PROCESS".equals(transStatus))) || (lineSetPri2 != null && !lineSetPri2.isEmpty()));
    int provinceOrgId = SessionUtil.getServletConfigDTO(session).getProvinceOrgId();
    int tdProvinceOrgId = SessionUtil.getServletConfigDTO(request).getTdProvinceOrgId();
    
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
    <script type="text/javascript" src="/WebLibary/js/SinoAttachment.js"></script>
    <script type="text/javascript" src="/WebLibary/js/OrderProcess.js"></script>
</head>
<script type="text/javascript">
    printToolBar();
</script>
<%@ include file="/flow/flowNoButton.jsp"%>
<body leftmargin="0" topmargin="0" onload="initPage();" onbeforeunload="doBeforeUnload()" onunload="doUnLoad()" >
<form action="<%=AssetsURLList.ASSETS_FREE_SERVLET%>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<%@ include file="/flow/flowPara.jsp" %>
<%
boolean isFirst = false;
boolean needBtnDiv = false;
if( actInfo.getSfactTaskAttribute3().equals( "FILL_DATA" ) ){
	isFirst = true ;
	needBtnDiv = true;
}
if( !needBtnDiv ){
	needBtnDiv = (lineSetPri2 != null && !lineSetPri2.isEmpty());
}

%>
<div id="searchDiv" style="position:absolute;top:25px;left:1px;width:100%">
<jsp:include page="/newasset/free/freeAssetsHeader.jsp" flush="true"/>
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
<input type="hidden" name="isTd" value="<%=userAccount.getIsTd() %>">
<input type="hidden" name="isTdProvinceUser" value="<%=isTdProvinceUser%>">
<input type="hidden" name="isProvinceUser" id="isProvinceUser" value="<%=userAccount.isProvinceUser()%>">
<input type="hidden" name="isSpecialityDept" id="isSpecialityDept" value="<%=isSpecialityDept %>"> <!-- 是否是实物部门 -->
<input type="hidden" name="provinceOrgId" id="provinceOrgId" value="<%=provinceOrgId%>">
<input type="hidden" name="excel" value="">
<%
    if(needBtnDiv){
%>
<div id="buttonDiv" style="position:absolute;top:195px;left:1px;width:100%">
<%
    }
    if ( isFirst ){
    		//!"IN_PROCESS".equals(transStatus)) {
%>
        <img src="/images/eam_images/choose.jpg" alt="选择资产" onClick="do_SelectAssets(); return false;">
        <img src="/images/eam_images/delete_line.jpg" alt="删除行" onClick="deleteLine(); return false;">
<%
    }
    if ( !"IN_PROCESS".equals(transStatus)) {
%>
	<img src="/images/eam_images/imp_from_excel.jpg" alt="Excel导入"
						onClick="do_excels();">
	<% } %>
        <span id="warn"></span>
<%
    if (lineSetPri2 != null && !lineSetPri2.isEmpty()) {
%>		
        <img src="/images/eam_images/detail_info.jpg" alt="查看资产标签号未导入成功详细信息"  onClick="do_Transfer();">
        
<%
    }
    if(needBtnDiv){
%>
</div>
<%
    }
%>
<jsp:include page="/newasset/free/freeAssetsLine.jsp" flush="true"/>
     <%
        if (transType.equals(AssetsDictConstant.ASS_RED)||transType.equals(AssetsDictConstant.ASS_DIS)) {
     %>

    <div style="position:absolute;bottom:0px;top:655px;left:0px;right:0px;width:100%;height:100%">
         <jsp:include page="/newasset/uploadInclude.jsp" flush="true"/>
        <%--<% if (transType.equals(AssetsDictConstant.ASS_RED)){ %>--%>
          <%--&nbsp;&nbsp;&nbsp<input type="button" name="sub" value="EXCEL模板下载" onclick="do_exportToExcel();">--%>
        <%--<%} %>--%>
    </div>
      <%
          }
      %>

</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
<div id="ddDiv" style="position: absolute; z-index: 2; top: 130px; left: 350px;background-color:#C0C0C0; border: 1px dotted red; width: 150px; height: 30px; text-align: center; visibility: hidden;">
		<form name="mainFrm2" action="<%=AssetsURLList.ASSETS_FREE_SERVLET%>" method="post" enctype="multipart/form-data">
		   <table border = "0" width="100%">
		   <tr onmousedown="mmd.catchDiv(this)" onmouseup="mmd.releaseDiv(this)" style="cursor:move;background:#A0D4F7;color:white;font:bold;height:20">
	            <td>&nbsp;&nbsp;<span key="PleaseSelectFunction"/></td>
	    	    <td align="right"><div style="padding-right:10px"><font face="webdings" style="cursor:hand" onclick="do_excel();">r</font></div></td>
	        </tr>
		       <tr>
		           <td width="80%" nowrap="nowrap" valign="middle">
		           	<input class="input_style1" type="file" name="flName">
		           <img src="/images/eam_images/imp_from_excel.jpg" alt="提交Excel" onClick="doSub(); return false;" align="center">&nbsp;&nbsp;&nbsp;
					 <a href="/document/template/tag.zip">
		              		<img style="border:none" src="/images/eam_images/download_template.jpg" alt="模板下载" align="center">
		           		</a>

		           </td>
		       </tr>
		   </table>
		</form>
	</div>
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
	var userPara = "transType=" + mainFrm.transType.value;
	
	var specialityDept = mainFrm.specialityDept.value;

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
	
	if (mainFrm.specialityDept.value == "") {
		alert("请选择实物管理部门，然后才能选择资产");
		return;
	}
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
}

function do_isProfessionalGroup() {
	var fromGroup = mainFrm.fromGroup.value;
	if(fromGroup != null && fromGroup != ""){
		var url = "<%=AssetsURLList.ASSETS_TRANS_SERVLET%>";
		url += "?act=VALIDATE_ACTION";
		url += "&fromGroup=" + mainFrm.fromGroup.value;
        var ajaxProcessor = new AjaxProcessor(url, do_ProcessResponse, false);
		ajaxProcessor.performTask();
	}
}

function do_ProcessResponse(responseContent){
    mainFrm.isGroupPID.value = responseContent;
}

function initPage() {
    window.focus();
    do_SetPageWidth();    
    doLoad();   
    do_ControlProcedureBtn();
    if(document.mainFrm.fromGroup.value=="0"||document.mainFrm.fromGroup.value==""){
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

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
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

function validate() {
    var isValid = true;
    var transType = mainFrm.transType.value;
    if (dataTable.rows.length == 0 || (dataTable.rows[0].style.display == 'none' && dataTable.rows.length == 1)) {
        alert("没有选择行数据，请选择行数据！");
        isValid = false;
    } else {
        var fieldLabels = "建单组别;闲置说明";
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
    }
    isValid = formValidate(fieldNames, fieldLabels, validateType);
    return isValid;
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

var contentCode = "";//mainFrm.faContentCode.value;
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
            selectSpecialOptionByItem("", contentCode);
        }
    }
}

function do_excel() {
    var _d = document.getElementById("ddDiv");

    if (_d.style.visibility == "hidden") {
        _d.style.visibility = "visible";
    } else {
        _d.style.visibility = "hidden";
    }
    
}

function do_excels() {
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


function do_ThredDept(fDept, tDept){
    var url = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsTransHeaderServlet?act=DO_THRED_DEPT&fDept=" + fDept + "&tDept=" + tDept;
    var ajaxProcessor = new AjaxProcessor(url, do_ProcessThred, false);
    ajaxProcessor.performTask();
}

function do_ProcessThred(resText) {
    document.mainFrm.isThred.value = resText;
}

function doSub() {
    if (document.mainFrm2.flName.value !== "") {
        mainFrm2.action ="<%=AssetsURLList.ASSETS_FREE_SERVLET%>?act=<%=AMSActionConstant.EXCEL_IMP%>";
        mainFrm2.submit();
    } else {
        alert("请输入文件！");
    }
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


function do_ProcessProSpecialGroup(){
    var actionURL = "/servlet/com.sino.ams.newasset.servlet.ProcedureGroupSelectServlet";
    var ajaxProcessor = new AjaxProcessor(actionURL, do_SetProSpecialGroup, false);
    ajaxProcessor.performTask();
}

function do_SetProSpecialGroup(resText){
    Launch_HandleGroup = resText;
}



function do_AppValidate() {
    var isValid = true;
    var transType = mainFrm.transType.value;
    var fieldLabels = "建单组别;闲置说明";
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
    if( isValid ){
	    if (dataTable.rows.length == 0 || (dataTable.rows[0].style.display == 'none' && dataTable.rows.length == 1)) {
	        alert("没有选择行数据，请选择行数据！");
	        isValid = false;
        }
    }
    
    return isValid;
}
</script>