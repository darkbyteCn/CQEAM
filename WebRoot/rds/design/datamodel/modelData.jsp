<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/dataPageInclude.jsp"%>

<%@ page import="com.sino.rds.share.constant.RDSDictionaryList" %>
<html>
<head>
    <title>模型详细信息</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/rds/WebLibary/css/rds.css">
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/RadioProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/SelectProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/AjaxProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_initPage();" onkeydown="autoExeFunction('do_Save()');">
<jsp:include page="/message/MessageProcess"/>
<html:form action="/rds/dataModelAction" method="post">
    <table  border="0" width="100%" id="table1">
        <tr>
            <td width="25%" align="right">模型名称：</td>
            <td width="50%"><html:text property="modelName" styleId="modelName" style="width:100%"/></td>
            <td width="25%"></td>
        </tr>
        <tr>
            <td width="25%" align="right">模型描述：</td>
            <td width="50%"><html:textarea property="modelDesc" styleId="modelDesc" style="width:100%" rows="10"/></td>
            <td width="25%"></td>
        </tr>
        <tr>
            <td width="25%" align="right">数据源：</td>
            <td width="50%"><html:select property="connectionId" styleId="connectionId" style="width:100%" onchange="do_ProcessConnection(this)"><bean:write name="dataModelFrm" property="connectionOptions" filter="false"/></html:select></td>
            <td width="25%"></td>
        <tr>
        <tr>
            <td width="25%" align="right">模型类型：</td>
            <td width="50%"><html:select property="dataSrcType" styleId="dataSrcType" style="width:100%" onchange="do_ProcessTableTypeChange(this);"><bean:write name="dataModelFrm" property="dataSrcOptions" filter="false"/></html:select></td>
            <td width="25%"></td>
        <tr id="modelSQLTr" style="display:none">
            <td width="25%" align="right" id="modelSQLTd"></td>
            <td width="50%" align="left"><html:textarea property="modelSql" styleId="modelSql" rows="15" style="width:100%"/></td>
            <td width="25%" align="center"></td>
        </tr>
        <tr>
            <td width="25%" align="right">是否有效：</td>
            <td width="50%"><bean:write name="dataModelFrm" property="enabledRadio" filter="false"/></td>
            <td width="25%"></td>
        </tr>
        <tr id="checkMessageTr" style="display:none">
            <td width="25%" align="right"></td>
            <td width="50%">
                <textarea id="checkMessage" rows="15" style="width:100%;"></textarea>
            </td>
            <td width="25%" align="center"></td>
        </tr>
    </table>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="modelId" styleId="modelId"/>
    <html:hidden property="dataSaved" styleId="dataSaved"/>
    <select name="referenceTable" id="referenceTable" style="display:none"><bean:write name="dataModelFrm" property="modelOptions" filter="false"/></select>
    </html:form>
<div id="pageNaviDiv" align="right"><a href="" onclick="do_Next();return false;">下一步</a> </div>
</body>
</html>
<script type="text/javascript">
function do_initPage(){
    do_CtrlParentBtn();
    var obj = document.getElementById("dataSrcType");
    do_ProcessTableTypeChange(obj);
    do_SetPageWidth();
}

function do_CtrlParentBtn(){
    var modelId = document.getElementById("modelId").value;
    if(modelId != ""){
        window.parent.ShowSinoButton(2);
    }
}

function do_ProcessTableTypeChange(obj){
    var dataSrcType = obj.value;
    var modelSQLTr = document.getElementById("modelSQLTr");
    var modelSql = document.getElementById("modelSql");
    var checkMessageTr = document.getElementById("checkMessageTr");
    if(dataSrcType == ""){
        modelSQLTr.style.display = "none";
    } else {
        checkMessageTr.style.display = "none";
        document.getElementById("modelSQLTd").innerText = obj.options[obj.selectedIndex].text+"：";
        if(dataSrcType == "<%=RDSDictionaryList.DATA_SRC_TYPE_SQL%>"){
            modelSql.readOnly = false;
            if(modelSql.tagName != "TEXTAREA"){
                modelSql.outerHTML = "<textarea name=\"modelSql\" rows=\"10\" id=\"modelSql\" style=\"width:100%\"></textarea>";
            }
            modelSQLTr.style.display = "block";
        } else {
            do_RequestServerTables();
        }
    }
}

function do_RequestServerTables(){
    var connectionId = document.getElementById("connectionId").value;
    var tableType = document.getElementById("dataSrcType").value;
    if(connectionId == "" || tableType == ""){
        return;
    }
    var tableName = document.getElementById("modelSql").value;
    var ajaxProcessor = new RDSAjaxProcessor(do_PopulateTables, false);
    ajaxProcessor.setServiceClass("com.sino.rds.design.datamodel.service.SystemTableSearchService");
    ajaxProcessor.setMethodName("getUserTableOptions");
    ajaxProcessor.setStrutsFrm("tableFrm");
    var userParameter = "&tableType=" + tableType;
    userParameter += "&connectionId=" + connectionId;
    userParameter += "&tableName=" + tableName;
    ajaxProcessor.setSendData(userParameter);
    ajaxProcessor.performTask();
}

function do_PopulateTables(selectHtml){
    document.getElementById("modelSql").outerHTML = "<select name=\"modelSql\" id=\"modelSql\" style=\"width:100%\">"+selectHtml+"</select>";
    var modelSQLTr = document.getElementById("modelSQLTr");
    modelSQLTr.style.display = "block";
}

function do_ProcessConnection(obj){
    if(obj.value == "ADD_NEW_CONN"){
        var actionURL = "/rds/dbConnFrm.do";
        actionURL += "?act=";
        var pageConfig = new DataPageConfig();
        pageConfig.setActionURL(actionURL);
        pageConfig.setOpenWindow(true);
        pageConfig.setWindowName("ADD_NEW_CONN");
        pageConfig.setWidthPercent(1);
        pageConfig.setHeightPercent(1);
        do_ProcessDataPage(pageConfig);
    } else {//需要改变获取数据库下的表或视图
        do_RequestServerTables();
    }
}

function do_CheckModelSQL(){
    var modelSql = document.getElementById("modelSql").value;
    if(modelSql != ""){
        var ajaxProcessor = new RDSAjaxProcessor(do_ShowCheckResult, false);
        ajaxProcessor.setServiceClass("com.sino.rds.design.datamodel.service.DataModelProcessService");
        ajaxProcessor.setMethodName("checkModelSQL");
        ajaxProcessor.setStrutsFrm("dataModelFrm");
        var dataModelFrm = document.forms["dataModelFrm"];
        ajaxProcessor.setDataFrm(dataModelFrm);
        ajaxProcessor.performTask();
    }
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
        checkMessage.value = "SQL查询语句非法，原因是:\r\n" + checkResult;
    }
}

function do_Check_Save(){
    var isValid = false;
    var fieldNames = "modelName;connectionId;dataSrcType;enabled";
    var fieldDescs = "模型名称;数据源;模型类型;是否有效";
    isValid = formValidate(fieldNames, fieldDescs, EMPTY_VALIDATE, "");
    if(isValid){
        var obj = document.getElementById("dataSrcType");
        var option = obj.options[obj.selectedIndex];
        fieldNames = "modelSql";
        fieldDescs = option.text;
        isValid = formValidate(fieldNames, fieldDescs, EMPTY_VALIDATE, "");
    }
    return isValid;
}

function do_Save(){
    if(do_Check_Save()){
        do_CheckModelSQL();
    }
}

function do_Next(){
    var modelId = document.getElementById("modelId").value;
    if(modelId == ""){
        alert("模型尚未保存过，不能前进到下一步。");
        return;
    }
    var actionURL = "/rds/modelFieldAction.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&modelId=" + modelId;
    var frm = window.parent.frames["modelFieldFrm"];
    frm.location = actionURL;
    setTimeout(do_NextTab, 2000);
}

function do_NextTab(){
    window.parent.tabBox.doclick(3, "");
}
</script>