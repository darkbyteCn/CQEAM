<%@ page import="com.sino.rds.share.constant.RDSDictionaryList" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>

<html>
<head>
    <title>报表参数字段详细信息</title>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/FormProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/validate/FormValidate.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/SelectProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/AjaxProcess.js"></script>

</head>
<body leftmargin="0" topmargin="1" onload="do_initPage();" onkeydown="autoExeFunction('do_Save()');">
<jsp:include page="/message/MessageProcess"/>
<html:form action="/rds/reportParameterProcess" method="post">
    <logic:empty property="reportId" name="reportParameterProcessFrm">
        　<div align="center">
            <table border="0" width="100%" cellspacing="0" cellpadding="0" style="text-align: center" height="80%">
                <tr>
                    <td><font size="7" face="微软雅黑">请在“已定义报表”中选择报表</font></td>
                </tr>
            </table>
        </div>
    </logic:empty>
    <logic:notEmpty property="reportId" name="reportParameterProcessFrm">
        　<div align="center">
            <table border="0" width="100%" cellspacing="0" cellpadding="0" style="text-align: center">
                <tr>
                    <td><font size="3" face="微软雅黑">报表“<bean:write name="reportParameterProcessFrm" property="report.reportName"/>”的参数字段信息</font></td>
                </tr>
                <tr>
                    <td height="1px"><hr color="#800000" size="1" style="width:100%"></td>
                </tr>
            </table>
        </div>
    <table style="width: 100%">
        <tr>
            <td>
                <table border="0" width="100%" cellspacing="1">
                    <tr>
                        <td width="8%" height="23">　</td>
                        <td width="25%" height="23" align="center">可选字段</td>
                        <td width="17%" height="23" align="center">操作</td>
                        <td width="25%" height="23" align="center">已选字段</td>
                        <td width="17%" height="23" align="center">显示顺序</td>
                        <td width="8%" height="23">&nbsp;&nbsp;</td>
                    </tr>
                    <tr style="height:180px">
                        <td width="8%"></td>
                        <td width="25%">
                            <html:select property="availableField" styleId="availableField" multiple="true" style="width:100%;height:100%" ondblclick="do_AddSelected()"><bean:write name="reportParameterProcessFrm" property="availableFieldsOptions" filter="false"/></html:select>
                        </td>
                        <td width="17%" align="center">
                            <P><input type="button" value="添加所选" onclick="do_AddSelected()"></P>
                            <P><input type="button" value="添加全部" onclick="do_AddAll();"></P>
                            <P><input type="button" value="删除所选" onclick="do_ClearSelected()"></P>
                            <P><input type="button" value="删除全部" onclick="do_ClearAll()"></P>
                        </td>
                        <td width="25%">
                            <html:select property="alreadyField" styleId="alreadyField" multiple="true" style="width:100%;height:100%" ondblclick="do_ClearSelected()"><bean:write name="reportParameterProcessFrm" property="alreadyFieldsOptions" filter="false"/></html:select>
                        </td>
                        <td width="17%" align="center">
                            <P><input type="button" value="置 顶" onclick="do_PositionTop()"></P>
                            <P><input type="button" value="上 移" onclick="do_PositionUp()"></P>
                            <P><input type="button" value="下 移" onclick="do_PositionDown()"></P>
                            <P><input type="button" value="置 底" onclick="do_PositionBottom()"></P>
                        </td>
                        <td width="8%"> </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:275px;left:1px;width:100%">
        <table id="headTable" border="1" width="100%">
            <tr class="headerTR" style="text-align:center">
                <td width="9%">字段名称</td>
                <td width="10%">字段描述</td>
                <td width="9%">输入类型</td>

                <td width="9%">LOV列表选择</td>
                <td width="9%">LookUp查询</td>
                <td width="9%">日历类型</td>

                <td width="9%">其他URL</td>
                <td width="9%">是否允许空</td>

                <td width="9%">默认值</td>
                <td width="9%">页面布局</td>
                <td width="9%">是否有效</td>
                <td style="display:none">隐藏域字段</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:298px;left:1px" align="left"
		     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <logic:notEmpty name="reportParameterProcessFrm" property="parameterFields">
                <logic:iterate id="parameterFields" name="reportParameterProcessFrm" property="parameterFields" indexId="index">
                    <tr>
                        <td width="9%"><html:text property="fieldName" name="parameterFields" indexed="true" styleClass="tableInput1"/></td>
                        <td width="10%"><html:text property="fieldDesc" name="parameterFields" indexed="true" styleClass="tableInput1"/></td>
                        <td width="9%"><html:select property="inputType" name="parameterFields" indexed="true" style="width:100%" onchange="do_ProcessInputCange(this)"><bean:write name="parameterFields" property="inputTypeOptions" filter="false"/></html:select></td>

                        <td width="9%"><html:select property="lovId" name="parameterFields" indexed="true" style="width:100%" onchange="do_ProcessLOVList(this)"><bean:write name="parameterFields" property="lovOptions" filter="false"/></html:select></td>
                        <td width="9%"><html:select property="lookUpId" name="parameterFields" indexed="true" style="width:100%" onchange="do_ProcessLookUp(this)"><bean:write name="parameterFields" property="lookUpOptions" filter="false"/></html:select></td>
                        <td width="9%"><html:select property="calendarType" name="parameterFields" indexed="true" style="width:100%"><bean:write name="parameterFields" property="calendarTypeOptions" filter="false"/></html:select></td>

                        <td width="9%"><html:text property="parameterUrl" name="parameterFields" indexed="true" styleClass="tableInput1"/></td>
                        <td width="9%"><html:select property="nullAble" name="parameterFields" indexed="true" style="width:100%"><bean:write name="parameterFields" property="nullAbleOptions" filter="false"/></html:select></td>

                        <td width="9%"><html:text property="defaultValue" name="parameterFields" indexed="true" styleClass="tableInput1"/></td>
                        <td width="9%"><html:select property="fieldAlign" name="parameterFields" indexed="true" style="width:100%"><bean:write name="parameterFields" property="fieldAlignOptions" filter="false"/></html:select></td>
                        <td width="9%"><html:select property="enabled" name="parameterFields" indexed="true" style="width:100%"><bean:write name="parameterFields" property="enabledOptions" filter="false"/></html:select></td>
                        <td style="display:none">
                            <html:hidden property="parameterId" name="parameterFields" indexed="true"/>
                            <html:hidden property="fieldId" name="parameterFields" indexed="true"/>
                            <html:hidden property="sortNumber" name="parameterFields" indexed="true"/>
                        </td>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="reportParameterProcessFrm" property="parameterFields">
                <tr style="display:none">
                    <td width="9%"><input type="text" name="parameterFields[0].fieldName" class="tableReadonlyInput1" readonly="true"></td>
                    <td width="10%"><input type="text" name="parameterFields[0].fieldDesc" class="tableInput1"></td>
                    <td width="9%"><select name="parameterFields[0].inputType" style="width:100%" onchange="do_ProcessInputCange(this)"></select></td>

                    <td width="9%"><select name="parameterFields[0].lovId" style="width:100%" onchange="do_ProcessLOVList(this)"></select></td>
                    <td width="9%"><select name="parameterFields[0].lookUpId" style="width:100%" onchange="do_ProcessLookUp(this)"></select></td>
                    <td width="9%"><select name="parameterFields[0].calendarType" style="width:100%"></select></td>

                    <td width="9%"><input type="text" name="parameterFields[0].parameterUrl" class="tableInput1"></td>
                    <td width="9%"><select name="parameterFields[0].nullAble" style="width:100%"></select></td>

                    <td width="9%"><input type="text" name="parameterFields[0].defaultValue" class="tableInput1"></td>
                    <td width="9%"><select name="parameterFields[0].fieldAlign" style="width:100%"></select></td>
                    <td width="9%"><select name="parameterFields[0].enabled" style="width:100%"></select></td>
                    <td style="display:none">
                        <input type="hidden" name="parameterFields[0].parameterId">
                        <input type="hidden" name="parameterFields[0].fieldId">
                        <input type="hidden" name="parameterFields[0].sortNumber">
                    </td>
                </tr>
            </logic:empty>
        </table>
       </div>
    </logic:notEmpty>
    <html:select property="referenceFieldAlign" styleId="referenceFieldAlign" style="display:none"><bean:write name="reportParameterProcessFrm" property="fieldAlignOptions" filter="false"/></html:select>
    <html:select property="referenceEnabled" styleId="referenceEnabled" style="display:none"><bean:write name="reportParameterProcessFrm" property="enabledOptions" filter="false"/></html:select>
    <html:select property="referenceInputType" styleId="referenceInputType" style="display:none"><bean:write name="reportParameterProcessFrm" property="inputTypeOptions" filter="false"/></html:select>
    <html:select property="referenceNullAble" styleId="referenceNullAble" style="display:none"><bean:write name="reportParameterProcessFrm" property="nullAbleOptions" filter="false"/></html:select>
    <html:select property="referenceLov" styleId="referenceLov" style="display:none"><bean:write name="reportParameterProcessFrm" property="lovOptions" filter="false"/></html:select>
    <html:select property="referenceLookUp" styleId="referenceLookUp" style="display:none"><bean:write name="reportParameterProcessFrm" property="lookUpOptions" filter="false"/></html:select>
    <html:select property="referenceCalendarType" styleId="referenceCalendarType" style="display:none"><bean:write name="reportParameterProcessFrm" property="calendarTypeOptions" filter="false"/></html:select>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="reportId" styleId="reportId"/>
    <html:hidden property="dataSaved" styleId="dataSaved"/>
    <div id="pageNaviDiv" align="right"><a href="" onclick="do_Prev();return false;">上一步</a> <a href="" onclick="do_Next();return false;">下一步</a> </div>
</html:form>
</body>
</html>
<script type="text/javascript">
function do_initPage(){
    do_SetPageWidth();
    do_CtrLinesEnableProperty();
}

function do_CtrLinesEnableProperty(){
    var tab = document.getElementById("dataTable");
    if(tab){
        var rows = tab.rows;
        for(var i = 0; i < rows.length; i++){
            var tr = rows[i];
            var obj = getTrNode(tr, "inputType");
            do_ProcessInputCange(obj);
        }
    }
}

function do_AddSelected(){
    var fromObj = document.getElementById("availableField");
    var selectedCount = getSelectedCount(fromObj);
    if(selectedCount == 0){
        alert("未选中待添加的字段，请先选择字段。");
        return;
    }
    moveSelectedOption("availableField", "alreadyField");
    do_RequestServerFields();
}

function do_AddAll(){
    moveAllOption("availableField", "alreadyField");
    do_RequestServerFields();
}

function do_RequestServerFields(){
    selectAll("alreadyField");
    var fieldIds = getSelectValue("alreadyField", ",");
    var lineFieldIds = do_GetLineFieldIds();
    fieldIds = do_MinusFields(fieldIds, lineFieldIds);
    var userParameter = "reportId=" + document.getElementById("reportId").value;
    userParameter += "&fieldIds=" + fieldIds;
    var ajaxProcessor = new RDSAjaxProcessor(do_ProcessFieldsDisplay, true);
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.ReportParameterProcessService");
    ajaxProcessor.setMethodName("getSelectedFieldsXML");
    ajaxProcessor.setStrutsFrm("reportParameterProcessFrm");
    ajaxProcessor.setSendData(userParameter);
    ajaxProcessor.performTask();
}

function do_MinusFields(val1, val2){
    var returnValue = val1;
    if(val2 != ""){
        var arr1 = val1.split(",");
        var arr2 = val2.split(",");
        for(var i = 0; i < arr1.length; i++){
            for(var j = 0; j < arr2.length; j++){
                if(arr1[i] == arr2[j]){
                    arr1.splice(i, 1);
                    i--;
                    break;
                }
            }
        }
        returnValue = "";
        for(i = 0; i < arr1.length; i++){
            returnValue += arr1[i];
            if(i < arr1.length - 1){
                returnValue += ",";
            }
        }
    }
    return returnValue;
}


function do_GetLineFieldIds(){
    var fieldIds = "";
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    for(var i = 0; i < rows.length; i++){
        var tr = rows[i];
        var node = getTrNode(tr, "fieldId");
        fieldIds += node.value;
        if(i < rows.length - 1){
            fieldIds += ",";
        }
    }
    return fieldIds;
}

function do_ProcessFieldsDisplay(returnXML){
    if(returnXML){
        var tab = document.getElementById("dataTable");
        var tabProcessor = new TableProcessor(tab);
        tabProcessor.setNamePrefix("parameterFields");
        tabProcessor.setUniqueField("fieldId");
        var lineNodes = returnXML.getElementsByTagName("line");
        if (lineNodes != null && lineNodes != undefined) {
            for (var i = 0; i < lineNodes.length; i++) {
                var dtoData = xmlNode2DTO(lineNodes[i]);
                tabProcessor.addRowData(dtoData);
                do_ProcessListOptions();
            }
        }
    }
}

function do_ProcessListOptions(){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    var tr = rows[rows.length - 1];

    var toObjs = new Array(
            "enabled", "fieldAlign", "inputType",
            "lovId", "lookUpId", "nullAble", "calendarType");
    var fromObjs = new Array(
            "referenceEnabled", "referenceFieldAlign",
            "referenceInputType", "referenceLov",
            "referenceLookUp", "referenceNullAble", "referenceCalendarType");

    for(var i = 0; i < toObjs.length; i++){
        var toObject = getTrNode(tr, toObjs[i]);
        var fromObject = document.getElementById(fromObjs[i]);
        if(toObject.length == 0){
            copyObjOptions(fromObject, toObject);
        }
    }
}

function xmlNode2DTO(lineNode) {
    var dto = new Object();
    var fields = lineNode.childNodes;
    for (var i = 0; i < fields.length; i++) {
        var field = fields[i];
        var fieldName = field.tagName;
        dto[fieldName] = field.text;
    }
    return dto;
}

function do_ClearSelected(){
    var fromObj = document.getElementById("alreadyField");
    var selectedCount = getSelectedCount(fromObj);
    if(selectedCount == 0){
        alert("未选中待删除的字段，请先选择字段。");
        return;
    }
    if(confirm("确定删除所选字段吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
        var tab = document.getElementById("dataTable");
        var rows = tab.rows;
        var parameterIds = "";
        for(var j = 0; j < fromObj.length; j++){
            var option = fromObj.options[j];
            if(option.selected){
                var clearField = option.value;
                for(var i = 0; i < rows.length; i++){
                    var tr = rows[i];
                    var node = getTrNode(tr, "fieldId");
                    if(node.value == clearField){
                        var parameterId = getTrNode(tr, "parameterId");
                        if(parameterId.value != ""){
                            parameterIds += parameterId.value + ",";
                        }
                        if(rows.length == 1){
                            node.value = "";
                            tr.style.display = "none";
                        } else {
                            tab.deleteRow(i);
                        }
                        break;
                    }
                }
            }
        }
        moveSelectedOption("alreadyField", "availableField");
        if(parameterIds != ""){
            parameterIds = parameterIds.substring(0, parameterIds.length - 1);
            do_DeleteReportParameter(parameterIds);
        }
    }
}

function do_ClearAll(){
    if(confirm("确定删除所选字段吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
        moveAllOption("alreadyField", "availableField");
        var tab = document.getElementById("dataTable");
        var rows = tab.rows;
        var parameterIds = "";
        for(var i = 0; i < rows.length; i++){
            var tr = rows[i];
            var node = getTrNode(tr, "parameterId");
            if(node.value != ""){
                parameterIds += node.value + ",";
            }
        }
        var tabProcessor = new TableProcessor(tab);
        tabProcessor.deleteAll();
        if(parameterIds != ""){
            parameterIds = parameterIds.substring(0, parameterIds.length - 1);
            do_DeleteReportParameter(parameterIds);
        }
    }
}


function do_DeleteReportParameter(parameterIds){
    var userParameter = "reportId=" + document.getElementById("reportId").value;
    userParameter += "&parameterIds=" + parameterIds;
    var ajaxProcessor = new RDSAjaxProcessor(do_ProcessDeleteResponse, true);
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.ReportParameterProcessService");
    ajaxProcessor.setMethodName("deleteReportParameterByIds");
    ajaxProcessor.setStrutsFrm("reportParameterProcessFrm");
    ajaxProcessor.setSendData(userParameter);
    ajaxProcessor.performTask();
}

function do_ProcessDeleteResponse(XMLResponse){
    var content = XMLResponse.getElementsByTagName("content")[0];
    var contentValue = content.text;
    alert(contentValue);
}

function do_PositionTop(){
    var alreadyField = document.getElementById("alreadyField");
    moveUp(alreadyField, true);
}

function do_PositionUp(){
    var alreadyField = document.getElementById("alreadyField");
    moveUp(alreadyField, false);
}

function do_PositionDown(){
    var alreadyField = document.getElementById("alreadyField");
    moveDown(alreadyField, false);

}

function do_PositionBottom(){
    var alreadyField = document.getElementById("alreadyField");
    moveDown(alreadyField, true);
}

function do_Check_Save(){
    var isValid = false;
    var reportId = document.getElementById("reportId").value;
    if(reportId == ""){
        isValid = false;
        alert("活动选项卡页面不含任何报表信息,不能保存,请先在“已定义报表”中选择报表或新增报表!");
    } else {
        var tab = document.getElementById("dataTable");
        if(needValidate(tab)){
            selectAll("alreadyField");
            var fieldNames = "inputType;fieldDesc;enabled";
            var fieldLabels = "输入类型;字段描述;是否有效";
            var namePrefix = "parameterFields";
            isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE, namePrefix);
            if(isValid){
                do_ProcessSortNumber();
            }
        } else {
            alert("未选择字段，不能保存。");
        }
    }
    if(isValid){
        setFrmEnable("reportParameterProcessFrm");
    }
    return isValid;
}

function do_ProcessSortNumber(){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    var sortNumberObj = document.getElementById("alreadyField");
    var options = sortNumberObj.options;
    var sortNumber = null;
    for(var i = 0; i < rows.length; i++){
        var tr = rows[i];
        var node = getTrNode(tr, "fieldId");
        for(var j = 0; j < options.length; j++){
            var option = options[j];
            var fieldId = option.value;
            if(node.value == fieldId){
                sortNumber = getTrNode(tr, "sortNumber");
                sortNumber.value = (j + 1);
                break;
            }
        }
    }
}

/**
 * 获得选中的项目数
 */
function getSelectedCount(select) {
    var retVal = 0;
    var optionObj = select.options;
    var optionCount = optionObj.length;
    for (var i = 0; i < optionCount; i++) {
        if (optionObj[i].selected) {
            retVal++;
        }
    }
    return retVal;
}

function do_CloseConfig() {
    var cfg = new CloseConfig();
    cfg.setEditPage(true);
    cfg.setRefreshOpener(true);
    return cfg;
}

function do_ProcessInputCange(obj){
    var trObj = obj.parentNode.parentNode;
    var inputType = obj.value;
    var disableNames = new Array();
    var enableNames = new Array();
    if(inputType == ""){
        enableNames[enableNames.length] = "lovId";
        enableNames[enableNames.length] = "lookUpId";
        enableNames[enableNames.length] = "calendarType";
        enableNames[enableNames.length] = "parameterUrl";
    } else if(inputType == "<%=RDSDictionaryList.INPUT_TYPE_TEXT%>"
            || inputType == "<%=RDSDictionaryList.INPUT_TYPE_SESSION%>"){
        disableNames[disableNames.length] = "lovId";
        disableNames[disableNames.length] = "lookUpId";
        disableNames[disableNames.length] = "calendarType";
        disableNames[disableNames.length] = "parameterUrl";
    } else if(inputType == "<%=RDSDictionaryList.INPUT_TYPE_CALENDAR%>"){
        enableNames[enableNames.length] = "calendarType";

        disableNames[disableNames.length] = "lovId";
        disableNames[disableNames.length] = "lookUpId";
        disableNames[disableNames.length] = "parameterUrl";
    } else if(inputType == "<%=RDSDictionaryList.INPUT_TYPE_LOV%>"){
        enableNames[enableNames.length] = "lovId";
        disableNames[disableNames.length] = "lookUpId";
        disableNames[disableNames.length] = "calendarType";
        disableNames[disableNames.length] = "parameterUrl";
    } else if(inputType == "<%=RDSDictionaryList.INPUT_TYPE_LOOKUP%>"){
        enableNames[enableNames.length] = "lookUpId";

        disableNames[disableNames.length] = "lovId";
        disableNames[disableNames.length] = "calendarType";
        disableNames[disableNames.length] = "parameterUrl";
    } else if(inputType == "<%=RDSDictionaryList.INPUT_TYPE_URL%>"){
        enableNames[enableNames.length] = "parameterUrl";

        disableNames[disableNames.length] = "lovId";
        disableNames[disableNames.length] = "lookUpId";
        disableNames[disableNames.length] = "calendarType";
    }
    var ele;
    var i;
    for(i = 0; i < enableNames.length; i++){
        ele = getTrNode(trObj, enableNames[i]);
        if(ele){
            ele.disabled = false;
        }
    }
    for(i = 0; i < disableNames.length; i++){
        ele = getTrNode(trObj, disableNames[i]);
        if(ele){
            ele.disabled = true;
        }
    }
}

function do_ProcessLOVList(obj){
    if(obj.value == "ADD_NEW_LOV"){
        var actionURL = "/rds/lovFrm.do";
        actionURL += "?act=";
        var pageConfig = new DataPageConfig();
        pageConfig.setActionURL(actionURL);
        pageConfig.setOpenWindow(true);
        pageConfig.setWindowName("ADD_NEW_LOV");
        pageConfig.setWidthPercent(1);
        pageConfig.setHeightPercent(1);
        do_ProcessDataPage(pageConfig);
    }
}

function do_ProcessLookUp(obj){
    if(obj.value == "ADD_NEW_LOOK_UP"){
        var actionURL = "/rds/lookUpFrm.do";
        actionURL += "?act=";
        var pageConfig = new DataPageConfig();
        pageConfig.setActionURL(actionURL);
        pageConfig.setOpenWindow(true);
        pageConfig.setWindowName("ADD_NEW_LOOK_UP");
        pageConfig.setWidthPercent(1);
        pageConfig.setHeightPercent(1);
        do_ProcessDataPage(pageConfig);
    }
}

function do_Next(){
    var ajaxProcessor = new RDSAjaxProcessor(do_ResponseCheckResult, false);
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.ReportParameterProcessService");
    ajaxProcessor.setMethodName("hasReportParameterField");
    ajaxProcessor.setStrutsFrm("reportParameterProcessFrm");
    ajaxProcessor.setSendData("reportId=" + document.getElementById("reportId").value);
    ajaxProcessor.performTask();
}

function do_ResponseCheckResult(checkResult){
    var goNext = false;
    if(checkResult == "N"){
        if(confirm("确认跳过报表参数定义吗?点击“确定”按钮继续，点击“取消”按钮留在当前选项卡！")){
            goNext = true;
        }
    } else {
        goNext = true;
    }
    if(goNext){
        var actionURL = "/rds/reportCategoryProcess.do";
        actionURL += "?act=DETAIL_ACTION";
        actionURL += "&reportId=" + document.getElementById("reportId").value;
        var frm = window.parent.frames["reportCategoryFrm"];
        frm.location = actionURL;
        setTimeout(do_NextTab, 2000);
    }
}

function do_NextTab(){
    window.parent.tabBox.doclick(8, "");
}

function do_Prev(){
    window.parent.tabBox.doclick(6, "");
}
</script>