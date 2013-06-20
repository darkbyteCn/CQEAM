<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@page import="com.sino.ams.newasset.scrap.constant.ScrapURLListConstant"%>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<html>
<%
	AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) request.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
    String orderNo = headerDTO.getTransNo();
    String pageTitle = headerDTO.getTransTypeValue() + "¡°" + orderNo + "¡±ÏêÏ¸ÐÅÏ¢";
%>
<head>
	<title><%=headerDTO.getTransTypeValue()%></title>
	<script type="text/javascript" src="/WebLibary/js/util.js"></script>
	<script type="text/javascript" src="/WebLibary/js/util2.js"></script>
	<script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
	<script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AjaxProcess.js"></script>
    <script type="text/javascript">
        printTitleBar("<%=pageTitle%>");
        printToolBar();
    </script>
</head>
<body  leftmargin="0" topmargin="0" onload="initPage();">
<form action="<%= ScrapURLListConstant.SCRAP_SERVELT %>" method="post" name="mainFrm">
<input type="hidden" name="act" value="">
<input type="hidden" name="transId" value="<%=headerDTO.getTransId() %>">
<input type="hidden" name="transType" value="<%= headerDTO.getTransType()%>">
<jsp:include page="/message/MessageProcess"/>
<div id="searchDiv" style="overflow:hidden;position:absolute;top:50px;left:1px;width:100%">
    <jsp:include page="/newasset/scrap/headerDetail.jsp" flush="true"/>
</div>
<jsp:include page="/newasset/scrap/transLineDetail.jsp" flush="true"/>
</form>
</body>
</html>
<script type="text/javascript">
function initPage() {
    window.focus();
    do_SetPageWidth();
    do_ControlProcedureBtn();
}
</script>