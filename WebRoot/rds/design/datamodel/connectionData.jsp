<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/dataPageInclude.jsp"%>

<html>
<head>
    <title>数据源详细信息</title>
    
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/RadioProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/AjaxProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_initPage();" onkeydown="autoExeFunction('do_Save()');">
<jsp:include page="/message/MessageProcess"/>
<html:form action="/rds/dbConnAction" method="post">
    <table  border="0" width="100%" id="table1">
        <tr>
            <td width="23%" align="right">数据源名：</td>
            <td width="75%"><html:text property="connectionName" styleId="connectionName" style="width:100%"/></td>
            <td width="2%" align="right"></td>
        </tr>
        <tr>
            <td width="23%" align="right">驱动程序：</td>
            <td width="75%"><html:text property="dbDriver" styleId="dbDriver" style="width:100%"/></td>
            <td width="2%" align="right"></td>
        </tr>
        <tr>
            <td width="23%" align="right">连接URL：</td>
            <td width="75%"><html:text property="dbUrl" styleId="dbUrl" style="width:100%"/></td>
            <td width="2%" align="right"></td>
        <tr>
            <td width="23%" align="right">用户名：</td>
            <td width="75%"><html:text property="dbUser" styleId="dbUser" style="width:100%"/></td>
        </tr>
        <tr>
            <td width="23%" align="right">用户密码：</td>
            <td width="75%"><html:text property="dbPwd" styleId="dbPwd" style="width:100%"/></td>
        </tr>
        <tr>
            <td width="23%" align="right">创建日期：</td>
            <td width="75%" align="center"><html:text property="creationDate" styleId="creationDate" style="width:100%" readonly="true"/></td>
            <td width="2%" align="right"></td>
        </tr>
        <tr>
            <td width="23%" align="right">修改日期：</td>
            <td width="75%" align="center"><html:text property="lastUpdateDate" styleId="lastUpdateDate" style="width:100%" readonly="true"/></td>
            <td width="2%" align="right"></td>
        </tr>
        <tr id="checkMessageTr" style="display:none">
            <td width="23%" align="right">错误信息：</td>
            <td width="75%"><textarea id="checkMessage" rows="20" style="width:100%;"></textarea></td>
            <td width="2%" align="right"></td>
        </tr>
    </table>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="connectionId" styleId="connectionId"/>
    </html:form>
</body>
</html>
<script type="text/javascript">
function do_initPage(){
    var doc = window.parent.document;
    var leftFrm = doc.getElementById("connectionListFrm");
    var leftWin = leftFrm.contentWindow;
    if(leftWin){
        leftWin.do_Search();
    }
}

function do_Save(){
    if(do_Check_Save()){
        do_CheckDBConnection();
    }
}

function do_Check_Save(){
    var fieldNames = "connectionName;dbDriver;dbUrl";
    var fieldDesc = "数据源;驱动程序;连接URL";
    return formValidate(fieldNames, fieldDesc, EMPTY_VALIDATE, "");
}

function do_CheckDBConnection(){
    var ajaxProcessor = new RDSAjaxProcessor(do_ShowCheckResult, false);
    ajaxProcessor.setServiceClass("com.sino.rds.design.datamodel.service.DBConnectionService");
    ajaxProcessor.setMethodName("checkDBConnection");
    ajaxProcessor.setStrutsFrm("dbConnFrm");
    var dbConnFrm = document.forms["dbConnFrm"];
    ajaxProcessor.setDataFrm(dbConnFrm);
    ajaxProcessor.performTask();
}

function do_ShowCheckResult(checkResult){
    var checkMessageTr = document.getElementById("checkMessageTr");
    var checkMessage = document.getElementById("checkMessage");
    if(isEmpty(checkResult)){
        document.forms[0].act.value = "SAVE_ACTION";
        document.forms[0].submit();
    } else {
        checkMessageTr.style.display = "block";
        checkMessage.style.color = "red";
        checkMessage.value = "在指定的URL上不能建立数据库连接，原因是:\r\n" + checkResult;
    }
}

function configCreatePage() {
    var pageConfig = new DataPageConfig();
    pageConfig.setOpenWindow(false);
    return pageConfig;
}
</script>