<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/dataPageInclude.jsp"%>
<%
    String lookUpListURL = contextPath + "/rds/lookUpProcess.do";
    lookUpListURL += "?act=QUERY_ACTION";
%>
<html>
<head>
    <title>LookUp查询详细信息</title>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/AjaxProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_initPage();" onkeydown="autoExeFunction('do_Save()');">
<jsp:include page="/message/MessageProcess"/>
<html:form action="/rds/lookUpProcess" method="post">
<table border="0" width="100%" cellspacing="1">
	<tr>
		<td width="100%">
            <table border="1" width="100%" cellspacing="0" bordercolor="#333333" style="border-collapse: collapse">
                <tr>
                    <td width="20%" height="22" align="right">查询代码：</td>
                    <td width="30%" height="22"><html:text property="lookUpCode" styleId="lookUpCode" styleClass="tableInput1"/> </td>
                </tr>
                <tr>
                    <td width="20%" height="22" align="right">查询名称：</td>
                    <td width="30%" height="22"><html:text property="lookUpName" styleId="lookUpName" styleClass="tableInput1"/></td>
                </tr>
                <tr>
                    <td width="20%" height="22" align="right">来源报表：</td>
                    <td width="30%" height="22"><html:select property="reportId" styleId="reportId" styleClass="tableInput1" onchange="do_ProcessReportChange(this)"><bean:write name="lookUpFrm" property="reportOptions" filter="false"/></html:select></td>
                </tr>
                <tr>
                    <td width="20%" height="22" align="right">页面标题：</td>
                    <td width="30%" height="22"><html:text property="lookUpTitle" styleId="lookUpTitle" styleClass="tableInput1"/></td>
                </tr>
                <tr>
                    <td width="20%" height="22" align="right">返回字段：</td>
                    <td width="30%" height="22"><html:select property="returnField" styleId="returnField" styleClass="tableInput1"><bean:write name="lookUpFrm" property="returnFieldOptions" filter="false"/></html:select></td>
                </tr>
                <tr>
                    <td width="20%" height="22" align="right">其他返回值：</td>
                    <td width="30%" height="22"><html:text property="otherReturnValue" styleId="otherReturnValue" styleClass="tableInput1"/></td>
                </tr>
                <tr>
                    <td width="20%" height="22" align="right">是否有效：</td>
                    <td width="30%" height="22"><bean:write name="lookUpFrm" property="enabledRadio" filter="false"/></td>
                </tr>
            </table>
		</td>
	</tr>
</table>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="lookUpId" styleId="lookUpId"/>
    </html:form>
</body>
</html>
<script type="text/javascript">
function do_initPage(){
    var leftFrm = parent.frames["lookUpListFrm"];
    leftFrm.location.href = "<%=lookUpListURL%>";
}

function do_Check_Save(){
    var fieldNames = "lookUpCode;lookUpName;reportId;lookUpTitle;returnField;enabled";
    var fieldDescs = "查询代码;查询名称;来源报表;页面标题;返回字段;是否有效";
    return formValidate(fieldNames, fieldDescs, EMPTY_VALIDATE, "");
}

function do_CloseConfig() {
    var cfg = new CloseConfig();
    cfg.setEditPage(false);
    cfg.setRefreshOpener(false);
    return cfg;
}

function configCreatePage() {
    var pageConfig = new DataPageConfig();
    pageConfig.setOpenWindow(false);
    return pageConfig;
}

function do_ProcessReportChange(selObj){
    var userParameter = "reportId=" + selObj.value;
    var ajaxProcessor = new RDSAjaxProcessor(do_ProcessOptions, false);
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.LookUpDefineService");
    ajaxProcessor.setMethodName("getReportFields");
    ajaxProcessor.setStrutsFrm("lookUpFrm");
    ajaxProcessor.setSendData(userParameter);
    ajaxProcessor.performTask();

}

function do_ProcessOptions(responseTxt){
    var returnField = document.getElementById("returnField");
    returnField.outerHTML = "<select name=\"returnField\" id=\"returnField\" style=\"width:100%\">" + responseTxt + "</select>";
}
</script>