
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript">
        printToolBar();
    </script>
</head>
<body  leftmargin="0" topmargin="0" onload="initPage();">
<form action="/servlet/com.sino.ams.newasset.servlet.AmsItemAllocationHeaderServlet" method="post" name="mainFrm">
<input type="hidden" name="act" value="">
<input type="hidden" name="transType" value="<%= headerDTO.getTransType()%>">
<input type="hidden" name="transId" value="<%= headerDTO.getTransId()%>">
<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="position:absolute;top:29px;left:1px;width:100%">
    <jsp:include page="/newasset/itemAllocationHeaderDetail.jsp" flush="true"/>
</div>
<jsp:include page="/newasset/itemAllocationLineDetail.jsp" flush="true"/>
</form>
</body>
</html>
<script type="text/javascript">
function initPage() {
    window.focus();
    do_SetPageWidth();
    do_ControlProcedureBtn();
}

function do_ExportOrder(){
    mainFrm.action = "<%=AssetsURLList.ORDER_QUERY_SERVLET%>";
	mainFrm.act.value = "<%=AssetsActionConstant.EPT_DTL_ACTION%>";
	mainFrm.submit();
}

function setAttachmentConfig(){
    var attachmentConfig = new AttachmentConfig();
    attachmentConfig.setOrderPkName("transId");
    return attachmentConfig;
}

</script>