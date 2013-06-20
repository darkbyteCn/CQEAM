<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	String transType = headerDTO.getTransType();
	String transferType = headerDTO.getTransferType();
	String transId = headerDTO.getTransId();
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	String orgId = userAccount.getOrganizationId();
	String userId = userAccount.getUserId();
	String provinceCode = headerDTO.getServletConfig().getProvinceCode();
	String provOrgId = headerDTO.getServletConfig().getProvinceOrgId();
	String hiddenRight = StrUtil.nullToString(request.getParameter("hiddenRight"));//用于地市间调拨生成新标签的控制
	String producedNewBarcode = headerDTO.getProducedNewBarcode();
%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>
</head>
<body  leftmargin="0" topmargin="0" onload="window.focus();">
<form action="<%=AssetsURLList.ORDER_APPROVE_SERVLET%>" method="post" name="mainFrm">
<jsp:include page="/message/MessageProcess"/>
<jsp:include page="/newasset/headerDetail_42.jsp" flush="true"/>
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
<input type="hidden" name="procdureName" value="<%=headerDTO.getProcdureName()%>">
<input type="hidden" name="toGroup" id="toGroup" value="<%=headerDTO.getToGroup()%>">
<input type="hidden" name="groupPid" id="groupPid" value="<%=headerDTO.getGroupPid()%>">
<input type="hidden" name="groupProp" id="groupProp" value="<%=headerDTO.getGroupProp()%>">
<input type="hidden" name="provinceCode" value="<%=provinceCode%>">
<input type="hidden" name="act" value="">
<input type="hidden" name="flowCode" value="">
<fieldset style="border:1px solid #397DF3; position:absolute;top:123px;width:100%;height:70%">
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
		if(hiddenRight.equals(AssetsDictConstant.NEW_TAG_NODE) && producedNewBarcode.equals("N")){
%>
		<img src="/images/eam_images/gen_tag.jpg" id="newTagImg" alt="生成新标签" onClick="do_ProduceNewTag(); return false;">
<%
		}
	}	
%>
        <img src="/images/eam_images/view_opinion.jpg" alt="查阅审批意见" onClick="viewOpinion(); return false;">
        <img src="/images/eam_images/view_flow.jpg" alt="查阅流程" onClick="viewFlow(); return false;">
        <img src="/images/eam_images/close.jpg" id="img6" alt="关闭" onClick="window.close(); return false;">
    </legend>
<jsp:include page="/newasset/transLineDetail_42.jsp" flush="true"/>

</fieldset>
    <div style="position:absolute;bottom:0px;top:620px;left:0px;right:0px;width:100%;height:100%">
        <jsp:include page="/newasset/uploadInclude.jsp" flush="true"/>

    </div>
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
	var procdureName = mainFrm.procdureName.value;
	var paramObj = new Object();
	paramObj.orgId = orgId;
	paramObj.useId = userId;
	paramObj.groupId = groupId;
	paramObj.procdureName = procdureName;
	paramObj.flowCode = "";
	if(flowCode == "<%=FlowConstant.FLOW_CODE_NEXT%>"){
		if(attribute2){
			orgId = document.getElementById(mainFrm.attribute2.value).value;
		}
		if(attribute3 != "" && document.getElementById(attribute3).value != ""){
			groupId = document.getElementById(attribute3).value;
		}
		if(transType == "<%=AssetsDictConstant.ASS_RED%>"){//调拨
			paramObj.flowCode = "<%=AssetsDictConstant.OTHER_FLOW%>";
			if(transferType == "<%=AssetsDictConstant.TRANS_INN_DEPT%>"){//部门内调拨
				if(sectionRight == "<%=AssetsDictConstant.END_INN_DEPT%>"){
					paramObj.flowCode = "<%=AssetsDictConstant.TRANS_INN_DEPT%>";
				} else if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
					paramObj.flowCode = mainFrm.faContentCode.value;
				}
				if(mainFrm.provinceCode.value == "<%=AssetsDictConstant.PROVINCE_CODE_SX%>"){
					paramObj.flowCode = "";
				}
			} else if(transferType == "<%=AssetsDictConstant.TRANS_BTW_DEPT%>"){//部门间调拨
				if(sectionRight == "<%=AssetsDictConstant.END_BTW_DEPT%>"){
					paramObj.flowCode = "<%=AssetsDictConstant.TRANS_BTW_DEPT%>";
				} else if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
					if(attribute1 != "<%=AssetsDictConstant.GROUP_PROP_SPEC%>"){
						paramObj.flowCode = mainFrm.faContentCode.value;
					}
				}
				if(mainFrm.provinceCode.value == "<%=AssetsDictConstant.PROVINCE_CODE_SX%>"){
					if(attribute3 == "groupPid"){
						paramObj.flowCode = "";
					}
				}
			} else if(transferType == "<%=AssetsDictConstant.TRANS_BTW_COMP%>"){//公司间调拨
				if(sectionRight == "<%=AssetsDictConstant.END_BTW_COMP%>"){
					paramObj.flowCode = "<%=AssetsDictConstant.TRANS_BTW_COMP%>";
				} else if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
					paramObj.flowCode = mainFrm.faContentCode.value;
				}
				if(mainFrm.provinceCode.value == "<%=AssetsDictConstant.PROVINCE_CODE_SX%>"){
					if(attribute3 == "groupPid"){
						paramObj.flowCode = "";
						if(mainFrm.groupPid.value != ""){
							groupId = mainFrm.groupPid.value;
							orgId = mainFrm.fromOrganizationId.value;
						} else {
							groupId = mainFrm.toGroup.value;
							orgId = mainFrm.toOrganizationId.value;
						}
					}
				}
				if(fromOrganizationId == "<%=provOrgId%>" && attribute5 == "Y"){//如果是省公司发出
					attribute4 = document.getElementById(attribute4).value;
					paramObj.flowCode = fromOrganizationId;
					orgId = attribute4;
				}
			}
		} else if(transType == "<%=AssetsDictConstant.ASS_DIS%>"){//报废
			if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
				paramObj.flowCode = mainFrm.faContentCode.value;
				if(attribute2 != ""){
					attribute2 = document.getElementById(attribute2).value;
					if(attribute2 == "<%=provOrgId%>"){
						paramObj.flowCode = attribute2;
					}
				}
			}
		} else if(transType == "<%=AssetsDictConstant.ASS_CLR%>"){//处置
			if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
				paramObj.flowCode = mainFrm.faContentCode.value;
				if(attribute2 != ""){
					attribute2 = document.getElementById(attribute2).value;
					if(attribute2 == "<%=provOrgId%>"){
						paramObj.flowCode = attribute2;
					}
				} 
			}
		} else if(transType == "<%=AssetsDictConstant.ASS_FREE%>"){//闲置
			if(hiddenValue == "<%=AssetsDictConstant.SPLIT_FLOW%>"){
				paramObj.flowCode = mainFrm.faContentCode.value;
				if(attribute2 != ""){
					attribute2 = document.getElementById(attribute2).value;
					if(attribute2 == "<%=provOrgId%>"){
						paramObj.flowCode = attribute2;
					}
				} 
			}
		}
		paramObj.groupId = groupId;
		paramObj.orgId = orgId;
//		alert("paramObj.flowCode = " + paramObj.flowCode);
//		alert("paramObj.groupId = " + paramObj.groupId);
//		alert("paramObj.orgId = " + paramObj.orgId);
		if(mainFrm.approveContent.value == "<%=FlowConstant.APPROVE_CONTENT_REJECT%>"){
			mainFrm.approveContent.value = "";			
		}
		paramObj.submitH = 'submitH()';
		if(mainFrm.approveContent.value == ""){
			mainFrm.approveContent.value = "<%=FlowConstant.APPROVE_CONTENT_AGREE%>";
		}
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
	var hiddenRight = "<%=hiddenRight%>";
	var producedNewBarcode = "<%=producedNewBarcode%>";
	if(flowCode == "<%=FlowConstant.FLOW_CODE_NEXT%>" && hiddenRight == "<%=AssetsDictConstant.NEW_TAG_NODE%>" && producedNewBarcode == "N"){
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
</script>
