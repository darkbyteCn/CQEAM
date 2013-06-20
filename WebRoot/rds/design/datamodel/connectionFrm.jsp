<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/dataPageInclude.jsp"%>

<%
    String connectionListURL = contextPath + "/rds/dbConnAction.do";
    connectionListURL += "?act=QUERY_ACTION";

    String connectionDataURL = contextPath + "/rds/dbConnAction.do";
    connectionDataURL += "?act=DETAIL_ACTION";
%>
<html>
<head>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/tab/tab.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/SelectProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/AjaxProcess.js"></script>

</head>
<body leftmargin="0" topmargin="0">
<body>
<html:form action="/rds/lovFrm" method="post">
<script type="text/javascript">
    ArrActions[2][0] = true;
    ArrActions[4][0] = true;
    ArrActions[15][0] = true;
    printTitleBar("数据源接维护");
    printToolBar();
</script>
</html:form>
<table border="0" width="100%" height="100%">
	<tr style="height:22px;text-align:center">
		<td>已定义的数据源</td>
		<td>数据源详细信息</td>
	</tr>    
	<tr>
		<td width="65%"><iframe id="connectionListFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=connectionListURL%>"></iframe></td>
		<td width="35%"><iframe id="connectionDataFrm" style="width:100%;height:100%" border="0" frameborder="0" src="<%=connectionDataURL%>"></iframe></td>
	</tr>
</table>
</body>
</html>
<script type="text/javascript">
    
function do_Save(){
    var frm = document.getElementById("connectionDataFrm");
    var childWin = frm.contentWindow;
    childWin.do_Save();
}

function do_Create(){
    var frm = document.getElementById("connectionDataFrm");
    var childWin = frm.contentWindow;
    childWin.do_Create();
}

function do_Close(){
    var ajaxProcessor = new RDSAjaxProcessor(do_SetConnection2Parent, false);
    ajaxProcessor.setServiceClass("com.sino.rds.share.util.RDSOptionCreateService");
    ajaxProcessor.setMethodName("getConnectionOptions");
    ajaxProcessor.performTask();
}

function do_SetConnection2Parent(resText){
    var doc = window.opener.document;
    var openerConnection = doc.getElementById("connectionId");
    var selectedValue = openerConnection.value;
    openerConnection.outerHTML = "<select name=\"connectionId\" style=\"width:100%\" id=\"connectionId\" onchange=\"do_ProcessConnection(this)\">"+resText+"</select>";
    openerConnection = doc.getElementById("connectionId");
    selectSpecialOptionByItem(openerConnection, selectedValue);
    window.close();
}
</script>