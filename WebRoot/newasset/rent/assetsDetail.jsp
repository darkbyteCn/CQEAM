<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request
			.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
	SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
	String transType = headerDTO.getTransType();
	String titleText = headerDTO.getTransTypeValue();
	if (titleText.indexOf("<") != -1) {
		if ("ASS-DONATE".equals(transType)) {
			titleText = "捐赠单";
		} else {
			titleText = "销售单";
		}
	}
	if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
		titleText = titleText + "(TD)";
	}
%>
<head>
	<title><%=titleText%></title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
<script type="text/javascript">
    printTitleBar("<%=titleText%>“<%=headerDTO.getTransNo()%>”详细信息");
    printToolBar();
</script>
</head>
<body  leftmargin="0" topmargin="0" onload="initPage();">
<form action="/servlet/com.sino.ams.newasset.rent.servlet.AssetsHeaderServlet" method="post" name="mainFrm">
<input type="hidden" name="act" value="">
<input type="hidden" name="transType" value="<%=headerDTO.getTransType()%>">
<input type="hidden" name="transId" value="<%=headerDTO.getTransId()%>">
<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="position:absolute;top:50px;left:1px;width:100%">
<jsp:include page="/newasset/rent/assetsHeader.jsp" flush="true"/>
</div>
<jsp:include page="/newasset/rent/assetsLine.jsp" flush="true"/>
</form>
</body>
</html>
<script type="text/javascript">
function initPage() {
    do_SetPageWidth();
    do_ControlProcedureBtn();
    setFrmReadonly("mainFrm");
    try{
    	disabledSelect();
    }catch(ex){}
} 

function do_ExportOrder(){
    mainFrm.action = "<%=AssetsURLList.ORDER_QUERY_SERVLET%>";
	mainFrm.act.value = "<%=AssetsActionConstant.EPT_DTL_ACTION%>";
	mainFrm.submit();
}


function do_Cancel(){
    self.close();
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}
</script>