<%@ page import="com.sino.ams.freeflow.FreeFlowDTO"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.sino.ams.system.user.dto.*"%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>

<%
	FreeFlowDTO headerDTO = (FreeFlowDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
	String transferType = headerDTO.getTransferType();
	String transId = headerDTO.getTransId();
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String orgId = userAccount.getOrganizationId();
	String userId = userAccount.getUserId();
	String provinceCode = headerDTO.getServletConfig().getProvinceCode();
	String provOrgId = headerDTO.getServletConfig().getProvinceOrgId();
	String sectionRight = StrUtil.nullToString(request.getParameter("sectionRight"));
	String producedNewBarcode = headerDTO.getProducedNewBarcode();
	String attribute4 = headerDTO.getAttribute4();
    DTOSet userDTO = userAccount.getUserRight();
    String roleName = "";
    Map  userRightMap = new HashMap();
    for (int i = 0;i<userDTO.getSize();i++) {
        SfUserRightDTO userRight = (SfUserRightDTO)userDTO.getDTO(i);
        roleName = userRight.getRolename();
        userRightMap.put(roleName,roleName);
    }
    boolean isDptMgr = userRightMap.containsValue("部门经理");
    String isGroupPID = request.getAttribute(AssetsWebAttributes.IS_GROUP_PID).toString();//是否市公司综合部、网络部流程组别
    String groupPid= headerDTO.getGroupPid();
    //EXCEL黏贴
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.WORKORDER_LOC_ROWSET);

%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>
</head>
<body  leftmargin="0" topmargin="0" onload="window.focus();">
<form action="<%=URLDefineList.ORDER_APPROVE_SERVLET%>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/freeflow/headerDetail.jsp" flush="true"/>
<jsp:include page="/flow/include.jsp" flush="true"/>
<input type="hidden" name="faContentCode" value="<%=headerDTO.getFaContentCode()%>">
<input type="hidden" name="fromGroup" value="<%=headerDTO.getFromGroup()%>">
<input type="hidden" name="fromOrganizationId" value="<%=headerDTO.getFromOrganizationId()%>">
<input type="hidden" name="toOrganizationId" value="<%=headerDTO.getToOrganizationId()%>">
<input type="hidden" name="transType" value="<%=transType%>">
<input type="hidden" name="transferType" value="<%=headerDTO.getTransferType()%>">
<input type="hidden" name="created" value="<%=headerDTO.getCreated()%>">
<input type="hidden" name="createdBy" value="<%=headerDTO.getCreatedBy()%>">
<input type="hidden" name="transId" value="<%=transId%>">
<%
	if(transType.equals(AssetsDictConstant.ASS_SHARE)){
%>
	<input type="hidden" name="procdureName" value="资产共享管理流程">
<%		
	} else {
%>
	<input type="hidden" name="procdureName" value="<%=headerDTO.getProcdureName()%>">
<%
	}
%>
<input type="hidden" name="toGroup" id="toGroup" value="<%=headerDTO.getToGroup()%>">
<input type="hidden" name="groupPid" id="groupPid" value="<%=headerDTO.getGroupPid()%>">
<input type="hidden" name="groupProp" id="groupProp" value="<%=headerDTO.getGroupProp()%>">
<input type="hidden" name="provinceCode" value="<%=provinceCode%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="flowCode" value="">
<fieldset style="border:1px solid #397DF3; position:absolute;top:123px;width:100%;height:80%">
    <legend>
<%
	if(!headerDTO.getTransStatus().equals(AssetsDictConstant.APPROVED)){
%>
        <img src="/images/button/pass.gif" id="img6" alt="通过" onClick="do_ApproveOrder('<%=FlowConstant.FLOW_CODE_NEXT%>'); return false;">
<%
		if((transferType.equals(AssetsDictConstant.TRANS_BTW_COMP) && producedNewBarcode.equals("N")) || !transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)){
%>
        <img src="/images/button/noPass.gif" id="img6" alt="不通过" onClick="do_ApproveOrder('<%=FlowConstant.FLOW_CODE_PREV%>'); return false;">
<%
		}
		if(sectionRight.equals(AssetsDictConstant.NEW_TAG_NODE) && producedNewBarcode.equals("N")){
%>
		<img src="/images/eam_images/gen_tag.jpg" id="newTagImg" alt="生成新标签" onClick="do_ProduceNewTag(); return false;">
<%
		}
	}
%>
        <img src="/images/eam_images/view_opinion.jpg" alt="查阅审批意见" onClick="viewOpinion(); return false;">
        <img src="/images/eam_images/view_flow.jpg" alt="查阅流程" onClick="viewFlow(); return false;">
        <img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="window.close(); return false;">
<%
		if(attribute4.equals(AssetsDictConstant.EDIT_ACCOUNT)){
%>
			统一设置：<input type="checkbox" name="allAccount" id="allAccount"><label for="allAccount">新折旧账户</label>
<%
		}
%>
    </legend>
 <div style="position:absolute;bottom:0px;top:0px;left:0px;right:0px;width:100%;height:96%">
		<jsp:include page="/freeflow/transLineDetail.jsp" flush="true"/>
  </div>

    <div style="position:absolute;bottom:0px;top:670px;left:0px;right:0px;width:100%;height:100%">
         <jsp:include page="/freeflow/uploadFile.jsp" flush="true"/>
    </div>
</fieldset>
</form>
</body>
</html>

<script type="text/javascript">

function do_ApproveOrder(flowCode) {
    if(!do_ValidateNewBarcode(flowCode)){
		return false;
	}
	addApproveContent(flowCode);
	var transType = mainFrm.transType.value;

    mainFrm.flowCode.value = flowCode;
	var transferType = mainFrm.transferType.value;
	var sectionRight = mainFrm.sectionRight.value;
	var attribute1 = mainFrm.attribute1.value;
	if(attribute1 != ""){
		attribute1 = document.getElementById(mainFrm.attribute1.value).value;
	}
	var attribute2 = mainFrm.attribute2.value;//单据创建OU组织ID
	var attribute3 = mainFrm.attribute3.value;//组别
	var attribute4 = mainFrm.attribute4.value;//调拨单据的接收OU组织ID
	var attribute5 = mainFrm.attribute5.value;//随意的，需要修改
	var hiddenValue = mainFrm.hiddenRight.value;
	var fromOrganizationId = mainFrm.fromOrganizationId.value;

	var userId = "<%=userId%>";
	var groupId = mainFrm.fromGroup.value;
    var orgId = "<%=orgId%>";
    var groupPid="<%=groupPid%>";
    var isDptMgr="<%=isDptMgr%>";
    var procdureName = mainFrm.procdureName.value;
    var isGroupPID = "<%=isGroupPID%>";
    var faContentCode=mainFrm.faContentCode.value;


    var paramObj = new Object();
	paramObj.orgId = orgId;
	paramObj.useId = userId;
	paramObj.groupId = groupId;
	paramObj.procdureName = procdureName;
    paramObj.flowCode = mainFrm.faContentCode.value;
    if(flowCode == "<%=FlowConstant.FLOW_CODE_NEXT%>"){
        if(attribute2){
			orgId = document.getElementById(mainFrm.attribute2.value).value;
		}
		if(attribute3 != "" && document.getElementById(attribute3).value != ""){
			groupId = document.getElementById(attribute3).value;
		}
		 if(transType == "<%=AssetsDictConstant.ASS_FREE%>"){//闲置
			if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
				if(isDptMgr=="true" ){
		     		 if(orgId != <%=provOrgId%>  && faContentCode=="NET-ASSETS" && isGroupPID == "false"){
			             paramObj.flowCode="C-NET";
			         }else if(orgId == <%=provOrgId%> && isGroupPID == "true"){
			         	 paramObj.flowCode="END";
			         } else if(orgId != <%=provOrgId%> && faContentCode=="MAR-ASSETS" && isGroupPID == "false"){
			             paramObj.flowCode="C-MAR" ;
			         } else if (orgId != <%=provOrgId%>  && faContentCode=="MGR-ASSETS" && isGroupPID == "false"){
			             paramObj.flowCode="C-MGR";
			         }else if((orgId != <%=provOrgId%>  && faContentCode=="NET-ASSETS" && isGroupPID == "true") || (orgId == <%=provOrgId%> && faContentCode=="NET-ASSETS" && isGroupPID == "false")) {
			             paramObj.flowCode="P-NET" ;
			         } else if((orgId != <%=provOrgId%> && faContentCode=="MAR-ASSETS" && isGroupPID == "true") || (orgId == <%=provOrgId%> && faContentCode=="MAR-ASSETS" && isGroupPID == "false")) {
			             paramObj.flowCode="P-MAR";
			         }else if((orgId != <%=provOrgId%> && faContentCode=="MGR-ASSETS" && isGroupPID == "true") || (orgId == <%=provOrgId%> && faContentCode=="MGR-ASSETS" && isGroupPID == "false")){
			             paramObj.flowCode="P-MGR";
			         }    	         
	        	}   
		    } 
		    else {
			  	paramObj.flowCode="";
		    }     	
		} else if(transType == "<%=AssetsDictConstant.ASS_SHARE %>"){		
			if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
<%--				alert("是否部门经理 " + isDptMgr+ "    " + faContentCode + "   " + orgId  + "   是否流程组别" + isGroupPID + "   " + procdureName);--%>
				if(isDptMgr=="true" ){
		     		 if(orgId != <%=provOrgId%>  && faContentCode=="NET-ASSETS" && isGroupPID == "false"){
			             paramObj.flowCode="C-NET";
			         }else if(orgId == <%=provOrgId%> && isGroupPID == "true"){
			         	 paramObj.flowCode="END";
			         } else if(orgId !=<%=provOrgId%> && faContentCode=="MAR-ASSETS" && isGroupPID == "false"){
			             paramObj.flowCode="C-MAR" ;
			         } else if (orgId != <%=provOrgId%>  && faContentCode=="MGR-ASSETS" && isGroupPID == "false"){
			             paramObj.flowCode="C-MGR";
			         }else if((orgId != <%=provOrgId%>  && faContentCode=="NET-ASSETS" && isGroupPID == "true") || (orgId == <%=provOrgId%> && faContentCode=="NET-ASSETS" && isGroupPID == "false")) {
			             paramObj.flowCode="P-NET" ;
			         } else if((orgId != <%=provOrgId%> && faContentCode=="MAR-ASSETS" && isGroupPID == "true") || (orgId == <%=provOrgId%> && faContentCode=="MAR-ASSETS" && isGroupPID == "false")) {
			             paramObj.flowCode="P-MAR";
			         }else if((orgId != <%=provOrgId%> && faContentCode=="MGR-ASSETS" && isGroupPID == "true") || (orgId == <%=provOrgId%> && faContentCode=="MGR-ASSETS" && isGroupPID == "false")){
			             paramObj.flowCode="P-MGR";
			         }    	         
			         
	        	}   
		    } 
		    else {
			  	paramObj.flowCode="";
		    }     	         
	    }
		paramObj.groupId = groupId;
		paramObj.orgId = orgId;
		paramObj.submitH = 'submitH()';
		assign(paramObj);
	} else {
		if(confirm("确认要退回吗？继续请点击“确定”按钮，否则请点击“取消”按钮！")){
			if(mainFrm.approveContent.value == ""){
				alert("退回申请时必须填写审批意见");
				return;
			}
			submitH();
		}
	}
}

function submitH() {
    mainFrm.act.value = "<%=AssetsActionConstant.APPROVE_ACTION%>";
    mainFrm.submit();
}

function do_ShowDetail(barcode){
	var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
	var winName = "assetsWin";
	var style = "width=860,height=495,left=100,top=130";
	window.open(url, winName, style);
}

function do_ValidateNewBarcode(flowCode){
	var isValid = true;
	var sectionRight = "<%=sectionRight%>";
	var producedNewBarcode = "<%=producedNewBarcode%>";
	if(flowCode == "<%=FlowConstant.FLOW_CODE_NEXT%>" && sectionRight == "<%=AssetsDictConstant.NEW_TAG_NODE%>" && producedNewBarcode == "N"){
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

var xmlHttp = null;

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
</script>
