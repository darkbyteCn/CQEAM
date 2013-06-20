<%--
  User: zhoujs
  Date: 2007-9-20
  Time: 15:13:26
  Function: 基站流程起始页面
--%>
<%@ page language="java" buffer="none" contentType="text/html; charset=GBK"%>

<%@ page import="com.sino.base.constant.web.WebActionConstant"%>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.util.StrUtil"%>
<%@ page import="com.sino.ams.constant.DictConstant"%>
<%@ page import="com.sino.ams.constant.LookUpConstant"%>
<%@ page import="com.sino.ams.constant.URLDefineList"%>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO"%>
<%@ page import="com.sino.ams.workorder.dto.EtsWorkorderBatchDTO" %>
<%@ page import="com.sino.ams.workorder.util.WorkOrderUtil" %>

<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

	EtsWorkorderBatchDTO etsWorkorderBatch = (EtsWorkorderBatchDTO) request
			.getAttribute(com.sino.ams.constant.WebAttrConstant.WORKORDER_BATCH_ATTR);
	SfUserDTO sfUserDTO = (SfUserDTO) session
			.getAttribute(WebConstant.USER_INFO);
	String workorderType = etsWorkorderBatch.getWorkorderType();
	boolean isNeed = workorderType.equals(DictConstant.ORDER_TYPE_NEW)
			|| workorderType.equals(DictConstant.ORDER_TYPE_EXT)
			|| workorderType.equals(DictConstant.ORDER_TYPE_HDV);
	String prjClass = isNeed ? "noEmptyInput" : "readonlyInput";
	String chooseGroup = request.getParameter("chooseGroup");
    chooseGroup="Y";
	boolean isFirstNode = true;
	String category = StrUtil.nullToString(request
			.getParameter("objectCategory"));
	String procName = WorkOrderUtil.getOrderProcdureName(
			etsWorkorderBatch.getWorkorderType(), category);
%>


<html>
	<link href="/WebLibary/css/view.css" rel="stylesheet" type="text/css">
	<link href="/WebLibary/css/css.css" rel="stylesheet" type="text/css">
	<link href="/WebLibary/css/eam.css" rel="stylesheet" type="text/css">
	<link href="/WebLibary/css/toolBar.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
	<script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
	<script type="text/javascript" src="/WebLibary/js/LookUp.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
	<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
	<script type="text/javascript" src="/flow/flow.js"></script>
	<script type="text/javascript" src="/WebLibary/js/json.js"></script>
	<script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>

	<script type="text/javascript" src="/WebLibary/js/util.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
	<script type="text/javascript" src="/WebLibary/js/api.js"></script>
	<script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
	<script type="text/javascript" src="/WebLibary/js/test.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
	<script type="text/javascript" src="/WebLibary/js/OrderProcess.js"></script>
	<script type="text/javascript" src="/WebLibary/js/SinoAttachment.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
	<style type="text/css">
	.textareaNoEmpty {WIDTH:100%;height:100%;BACKGROUND-COLOR: #FFFF99;}
	</style>
	<head>
		<title>工单任务登记</title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	</head>
    <jsp:include page="/message/MessageProcess"/>
	<body bgcolor="#FFFFFF" text="#000000" topMargin="0" leftMargin="0"	 onload="initPage();" onbeforeunload="doBeforeUnload()"
		onunload="doUnLoad()">
		<%@ include file="/flow/flowNoButton.jsp"%>
		<form name="mainFrm" method="post" action="/servlet/com.sino.ams.workorder.servlet.EtsWorkorderBatchServlet">
		<%@ include file="/flow/flowPara.jsp"%>
	<script type="text/javascript">
        printToolBar();
    </script>
			<table id="batchTable" width="100%" height="100px" align="center" border="0" cellpadding="2" cellspacing="0" >
				<tr>
					<td width="10%" align="right">工单批号：</td>
					<td width="20%">
						<input readonly type="text" align="left" style="width:90%" name="workorderBatch" value="<%=etsWorkorderBatch.getWorkorderBatch()%>" class="input_style2">
					</td>
					<td align="right" width="10%">工单类型：</td>
					<td width="20%">
						<input class="input_style2" align="left" style="width:100%"  readonly="true" name="workorderTypeDesc" type="text" value="<%=etsWorkorderBatch.getWorkorderTypeDesc()%>">
						<input name="workorderType" type="hidden" value="<%=etsWorkorderBatch.getWorkorderType()%>">
					</td>
					<td align="right" width="10%"> 创 建 人：</td>
					<td width="20%">
						<input readonly="true" style="width:100%"  align="left" class="input_style2" name="created_by_desc" type="text" value="<%=etsWorkorderBatch.getCreateUser()%>">
					</td>
                    <td width="10%"></td>
				</tr>
				<tr>
					<td width="10%" align="right">任务名称：</td>
					<td width="20%" >
						<input class="textareaNoEmpty" align="left" type="text" style="width:90%" name="workorderBatchName" value="<%=etsWorkorderBatch.getWorkorderBatchName()%>"><font color="red">*</font>
					</td>
<%--					<td width="10%" align="right">所属工程：</td>
					<td width="20%" >
						<input class="input_style1" align="left" type="text" style="width:85%" name="prjName" value="<%=etsWorkorderBatch.getPrjName()%>" readonly>
					<%
					    if (isFirstNode) {
					%>
						<a href="#" onclick="choosePrj();" title="点击选择工程"><font color="blue"><%=WebConstant.CHOOSE_DICT%></font></a>
					<%
					    }
					%>
					</td>--%>
					<td width="10%" align="right">下单组别：</td>
					<td width="20%">
						<input readonly="true" align="left" style="width:100%"  class="input_style2" name="distributeGroupName" type="text" value="<%=etsWorkorderBatch.getDistributeGroupName()%>">
						<input name="distributeGroupId" type="hidden" value="<%=etsWorkorderBatch.getDistributeGroupId()%>">
					</td>
                    <td width="10%"></td>
				</tr>
				<tr>
				</tr>
				<tr style="height:60px">
					<td width="10%" height="100%" align="right">任务描述：</td>
					<td width="80%" height="100%" colspan="5">
						<textarea name="remark" style="width:100%;height:100%"><%=etsWorkorderBatch.getRemark()%></textarea>
					</td>
                    <td width="10%" height="100%"></td>
				</tr>
			</table>

    <table width="100%" align="center" id="woTable">
        <tr>
            <td>
                <iframe name="wo" id="wo" frameborder="0" style="width:100%;height:100%" src="" scrolling="no"></iframe>
            </td>
        </tr>
    </table>

    <input name="createdBy" type="hidden" value="<%=etsWorkorderBatch.getCreatedBy()%>">
    <input type="hidden" name="prjId" value="<%=etsWorkorderBatch.getPrjId()%>">
    <input type="hidden" name="systemid" value="<%=etsWorkorderBatch.getSystemid()%>">
    <input type="hidden" name="flowSaveType">
    <input type="hidden" name="objectCategory" value="<%=category%>">
    <input type="hidden" name="isFirstNode" value="<%=isFirstNode%>">
    <input type="hidden" name="procName" value="<%=procName%>">
    <input type="hidden" name="fromPage" id="fromPage" value="<%=StrUtil.nullToString(request.getParameter("fromPage"))%>">
<%=WebConstant.WAIT_TIP_MSG%>
</form>
<jsp:include page="/public/hintMessage.jsp" flush="true"/>
</body>
<script type="text/javascript">

function do_Save_app() {//暂存
    document.mainFrm.action = "/servlet/com.sino.ams.workorder.servlet.EtsWorkorderBatchServlet?flowSaveType=<%=DictConstant.FLOW_SAVE%>";
    document.mainFrm.submit();
    document.getElementById("$$$disableMsg$$$").style.visibility = "visible";
}

function do_AppValidate(){
	var isValid = false;
	var fieldLabels = "任务名称";
	var fieldNames = "workorderBatchName";
	var validateType = EMPTY_VALIDATE;
	if (parent.wo.document.all("systemids")) {
		var tab = parent.wo.document.all("dataTable");
    	var rowCount = tab.rows.length;
	}
    var imp = "";
    var arc = "";
	var implement = "";
	var arcUserBy = "";
    if (parent.wo.document.all("systemids")) {
		isValid = formValidate(fieldNames, fieldLabels, validateType);
		if (isValid) {
			for(var i = 0; i < rowCount; i++) { 
				imp = parent.wo.document.all("implement" + i).value;
				implement = imp.substring(imp.indexOf("/") + 1, 100);
				arc = parent.wo.document.all("arcUserBy" + i).value;
				arcUserBy = arc.substring(arc.indexOf("/") + 1, 100);
				if (implement == "") {
					alert("请选择执行人");
					parent.wo.document.all("implement" + i).focus();
					return false;
				}
				if (arcUserBy == "") {
					alert("请选择归档人");
					parent.wo.document.all("arcUserBy" + i).focus();
					return false;
				}
			}
		}
    } else {
        alert("您没有创建工单！");
        isValid = false;
    }
    return isValid;

}

function choosePrj() {
    var lookUpName = "<%=LookUpConstant.LOOK_UP_PROJECT%>";
    var dialogWidth = 50.6;
    var dialogHeight = 30;
    var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
    if(projects){
        dto2Frm(projects[0], "mainFrm");
    }else{
        document.mainFrm.prjName.value="";
        document.mainFrm.prjId.value="";
    }
}

function addOrders() {//添加工单
    var screenHeight = window.screen.height;
    var screenWidth = window.screen.width;

    var dialogStyle = "dialogWidth=" + screenWidth + ";dialogHeight=" + screenHeight + ";help=no;status=no;center=yes;toolbar=no;menubar=no;resizable=no;scroll=no";
    var workorderBatchNo = document.mainFrm.workorderBatch.value;
    var workorderType = document.mainFrm.workorderType.value;
    var category = document.mainFrm.objectCategory.value;
//        var groupId = document.getElementById("flow_group_id").value; //document.mainFrm.distributeGroupId.value;
    var groupId = document.mainFrm.distributeGroupId.value;
//        var groupName = document.getElementById("flow_group_name").value; //document.mainFrm.distributeGroupName.value
    var groupName = document.mainFrm.distributeGroupName.value;
    var workorderTypeDesc = document.mainFrm.workorderTypeDesc.value;
    var url = "<%=URLDefineList.WORKORDER_CHOOSE_SERVLET%>?act=<%=WebActionConstant.NEW_ACTION%>&objectCategory=" + category;
    url += "&workorderType=" + workorderType + "&workorderBatch=" + workorderBatchNo + "&distributeGroupId=" + groupId;
    url += "&workorderTypeDesc=" + workorderTypeDesc + "&groupName=" + groupName;
    var retValue = window.showModalDialog(url, null, dialogStyle);
    if (retValue) {//刷新 Iframe
        var ifra=document.all("wo");
        url=ifra.src;
        ifra.src=url+"&update=y&distributeGroupId="+groupId;
    }

}

function initPage(){
    do_ComputeWOHeight();
    do_LoadTmpWorkorderURL();
    doLoad();
    needAttachMenu = false;
    do_ControlProcedureBtn();
    if (document.getElementById("flow_group_id").value!="") {
        mainFrm.distributeGroupId.value = document.getElementById("flow_group_id").value;
        mainFrm.distributeGroupName.value = document.getElementById("flow_group_name").value;
    }

}

function do_LoadTmpWorkorderURL(){
    var url="<%=URLDefineList.WORKORDER_TMP_SERVLET%>?workorderBatchNo=<%=etsWorkorderBatch.getWorkorderBatch()%>";
    url+="&workorderType=<%=etsWorkorderBatch.getWorkorderType()%>&objectCategory=<%=category%>";
    url += "&groupId=" + document.getElementById("flow_group_id").value;
    url += "&fromPage=" + document.getElementById("fromPage").value;
    if (document.getElementById("sf_task_attribute3").value == "FILL_DATA") {
        url += "&isFirstNode=true";
    }else{
        url += "&isFirstNode=false";
    }
    var ifra = document.all("wo");
    ifra.src = url;
}

function do_ComputeWOHeight(){
    var bodyHeight = document.body.clientHeight;
    var screenHeight = window.screen.height;
    if(bodyHeight + 72 > screenHeight){
        bodyHeight = screenHeight - 72;
    }
    var batchHeight = document.getElementById("batchTable").offsetHeight;
    var woTable = document.getElementById("woTable");
    woTable.style.height = bodyHeight - batchHeight - 60;
}

function do_Complete_app_yy() {
	if(true){
        try{
            var actObj = document.getElementById("flowSaveType");
			actObj.value = "<%=DictConstant.FLOW_COMPLETE%>";
			document.forms[0].submit();
		}catch(ex){
			alert( ex.message );
		}finally{
			enableBtn();
		}
	}
}

function doLoad() {
    if(ArrActions[8][0] == true || document.getElementById("sf_event").value == "5" || document.getElementById("sf_event").value == "4") {
        autoSign = true;
        do_Sign();
    }

    if(document.getElementById("sf_caseID").value.indexOf(":") >= 0  || document.getElementById("sf_copyFlag").value == "1") {
        var nCount=ArrActions.length;
        for (var j=1 ;j< nCount;j++){
            HideSinoButton(j);
        }
//        ShowSinoButton(24);
    }
//    fillData(document.getElementById("sf_fillApiData").value);
    if(document.getElementById("sf_lock").value == "1")
        alert("此任务已被其他用户打开, 数据已被锁定, 只能以只读方式打开数据!");
    //setDivVisibility();  //下面的流程按钮不灰色了]
    init_groups();
    if (document.getElementById("sf_isNew").value=="1") {
        do_SelectGroup();
    }
    fillData(document.getElementById("sf_fillApiData").value);
    //setDivRight();  //下面的流程按钮不灰色了
    if(document.getElementById("sf_comment").value != "") {
        //alert(document.getElementById("sf_comment").value);
    } else {
//        HideSinoButton(9);
    }

    do_appInit();

    SFQueryOpen();

    if(!Launch_Continue) {
        alert(Error_Msg);
        doUnLoad();
        return;
    }
    var tst;
    tst = document.getElementById("sinoflow_load_data").value;
    if(tst != null && tst != "")
        fillData(tst);

    SFPostOpen();

    isLoaded = true;
}


</script>
</html>
