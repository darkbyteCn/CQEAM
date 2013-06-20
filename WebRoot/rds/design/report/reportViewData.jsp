<%@ page contentType="text/html;charset=GBK" language="java" %>

<%@ page import="com.sino.rds.share.constant.RDSDictionaryList" %>
<%@ page import="com.sino.rds.share.form.ReportViewProcessFrm" %>
<%@ page import="com.sino.rds.share.form.ReportDefineFrm" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>

<%
    String reportType = "";
%>
<html>
<head>
    <title>报表数据字段详细信息</title>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/toolbar/dynlayer.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/FormProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/validate/FormValidate.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/CheckboxProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/SelectProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/table/TableProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/AjaxProcess.js"></script>

</head>
<body leftmargin="0" topmargin="1" onload="do_initPage();" onkeydown="autoExeFunction('do_Save()');">
<jsp:include page="/message/MessageProcess"/>
<html:form action="/rds/reportViewProcess" method="post">
    <logic:empty property="reportId" name="reportViewProcessFrm">
        　<div align="center">
            <table border="0" width="100%" cellspacing="0" cellpadding="0" style="text-align: center" height="80%">
                <tr>
                    <td><font size="7" face="微软雅黑">请在“已定义报表”中选择报表</font></td>
                </tr>
            </table>
        </div>
    </logic:empty>
    <logic:notEmpty property="reportId" name="reportViewProcessFrm">
<%
    ReportViewProcessFrm rptProcessFrm = (ReportViewProcessFrm)request.getAttribute("reportViewProcessFrm");
    ReportDefineFrm report = rptProcessFrm.getReport();
    reportType = report.getReportType();
    String computeStyle = "";
    if(!report.isIntersectReport()){
        computeStyle = "style=\"display:none\"";
    }
%>
        　<div align="center">
            <table border="0" width="100%" cellspacing="0" cellpadding="0" style="text-align: center">
                <tr>
                    <td><font size="3" face="微软雅黑">报表“<bean:write name="reportViewProcessFrm" property="report.reportName"/>”的数据字段信息</font></td>
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
                            <html:select property="availableField" styleId="availableField" multiple="true" style="width:100%;height:100%" ondblclick="do_AddSelected()"><bean:write name="reportViewProcessFrm" property="availableFieldsOptions" filter="false"/></html:select>
                        </td>
                        <td width="17%" align="center">
                            <P><input type="button" value="添加所选" onclick="do_AddSelected()"></P>
                            <P><input type="button" value="添加全部" onclick="do_AddAll();"></P>
                            <P><input type="button" value="删除所选" onclick="do_ClearSelected()"></P>
                            <P><input type="button" value="删除全部" onclick="do_ClearAll()"></P>
                        </td>
                        <td width="25%">
                            <html:select property="alreadyField" styleId="alreadyField" multiple="true" style="width:100%;height:100%" ondblclick="do_ClearSelected()"><bean:write name="reportViewProcessFrm" property="alreadyFieldsOptions" filter="false"/></html:select>
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
                <td width="15%">字段名称</td>
                <td width="15%">字段描述</td>
                <td width="15%">数据格式</td>
                <td width="10%">小数位数</td>
                <td width="20%" <%=computeStyle%>>计算表达式</td>
                <td width="15%">横向布局</td>
                <td width="10%">是否有效</td>

                <td style="display:none">隐藏域字段</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:298px;left:1px" align="left"
		     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <logic:notEmpty name="reportViewProcessFrm" property="dataFields">
                <logic:iterate id="dataFields" name="reportViewProcessFrm" property="dataFields" indexId="index">
                    <tr>
                        <td width="15%"><html:text property="fieldName" name="dataFields" indexed="true" styleClass="tableReadonlyInput1" readonly="true"/></td>
                        <td width="15%"><html:text property="fieldDesc" name="dataFields" indexed="true" styleClass="tableInput1"/></td>
                        <td width="15%"><html:select property="dataPattern" name="dataFields" indexed="true" style="width:100%"><bean:write name="dataFields" property="dataPatternOptions" filter="false"/></html:select></td>
                        <td width="10%"><html:text property="dotNumber" name="dataFields" indexed="true" styleClass="tableInput3"/></td>
                        <td width="20%" <%=computeStyle%>><html:select property="computeExpression" name="dataFields" indexed="true" style="width:100%"><bean:write name="dataFields" property="computeExpressionOptions" filter="false"/></html:select></td>
                        <td width="15%"><html:select property="fieldAlign" name="dataFields" indexed="true" style="width:100%"><bean:write name="dataFields" property="fieldAlignOptions" filter="false"/></html:select></td>
                        <td width="10%"><html:select property="enabled" name="dataFields" indexed="true" style="width:100%"><bean:write name="dataFields" property="enabledOptions" filter="false"/></html:select></td>
                        <td style="display:none">
                            <html:hidden property="viewId" name="dataFields" indexed="true"/>
                            <html:hidden property="fieldId" name="dataFields" indexed="true"/>
                            <html:hidden property="sortNumber" name="dataFields" indexed="true"/>
                            <html:hidden property="fieldType" name="dataFields" indexed="true"/>
                        </td>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="reportViewProcessFrm" property="dataFields">
                <tr style="display:none">
                    <td width="15%"><input type="text" name="dataFields[0].fieldName" class="tableReadonlyInput1" readonly="true"></td>
                    <td width="15%"><input type="text" name="dataFields[0].fieldDesc" class="tableInput1"></td>
                    <td width="15%"><select name="dataFields[0].dataPattern" style="width:100%"></select></td>
                    <td width="10%"><input type="text" name="dataFields[0].dotNumber" class="tableInput3"></td>
                    <td width="20%" <%=computeStyle%>><select name="dataFields[0].computeExpression" style="width:100%"></select></td>
                    <td width="15%"><select name="dataFields[0].fieldAlign" style="width:100%"></select></td>
                    <td width="10%"><select name="dataFields[0].enabled" style="width:100%"></select></td>

                    <td style="display:none">
                        <input type="hidden" name="dataFields[0].viewId">
                        <input type="hidden" name="dataFields[0].fieldId">
                        <input type="hidden" name="dataFields[0].sortNumber">
                        <input type="hidden" name="dataFields[0].fieldType">
                    </td>
                </tr>
            </logic:empty>
        </table>
       </div>
    </logic:notEmpty>

    <html:select property="referenceFieldAlign" styleId="referenceFieldAlign" style="display:none"><bean:write name="reportViewProcessFrm" property="fieldAlignOptions" filter="false"/></html:select>
    <html:select property="referenceEnabled" styleId="referenceEnabled" style="display:none"><bean:write name="reportViewProcessFrm" property="enabledOptions" filter="false"/></html:select>
    <html:select property="referenceBackGroundTo" styleId="referenceBackGroundTo" style="display:none"><bean:write name="reportViewProcessFrm" property="backGroundToOptions" filter="false"/></html:select>
    <html:select property="referenceDatePattern" styleId="referenceDatePattern" style="display:none"><bean:write name="reportViewProcessFrm" property="datePatternOptions" filter="false"/></html:select>
    <html:select property="referenceNumberPattern" styleId="referenceNumberPattern" style="display:none"><bean:write name="reportViewProcessFrm" property="numberPatternOptions" filter="false"/></html:select>
    <html:select property="referenceComputeExpression" styleId="referenceComputeExpression" style="display:none"><bean:write name="reportViewProcessFrm" property="computeExpressionOptions" filter="false"/></html:select>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="reportId" styleId="reportId"/>
    <html:hidden property="dataSaved" styleId="dataSaved"/>
</html:form>
<div id="pageNaviDiv" align="right"><a href="" onclick="do_Prev();return false;">上一步</a> <a href="" onclick="do_Next();return false;">下一步</a> </div>
</body>
</html>
<script type="text/javascript">
function do_initPage(){
    do_SetPageWidth();
    do_ProcessLineFields();
}

function do_ProcessLineFields(){
    var tab = document.getElementById("dataTable");
    if(needValidate(tab)){
        var rows = tab.rows;
        for(var i = 0; i < rows.length; i++){
            var tr = rows[i];
            var node3 = getTrNode(tr, "fieldType");
            var node4 = getTrNode(tr, "dotNumber");
            var dataType = node3.value;
            if(!(dataType == "NUMBER"
                        || dataType == "SMALLINT"
                        || dataType == "INTEGER"
                        || dataType == "INT"
                        || dataType == "FLOAT"
                        || dataType == "BIGINT"
                        || dataType == "DECIMAL"
                        || dataType == "DOUBLE PRECISION"
                        || dataType == "NUMERIC"
                        || dataType == "COUNTER")){
                node4.value = "勿需设置";
                node4.readOnly = true;
                node4.className = "tableReadonlyInput2";
            }
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
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.ReportViewProcessService");
    ajaxProcessor.setMethodName("getSelectedFieldsXML");
    ajaxProcessor.setStrutsFrm("reportViewProcessFrm");
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
        tabProcessor.setNamePrefix("dataFields");
        tabProcessor.setUniqueField("fieldId");
        var lineNodes = returnXML.getElementsByTagName("line");
        if (lineNodes != null && lineNodes != undefined) {
            for (var i = 0; i < lineNodes.length; i++) {
                var dtoData = xmlNode2DTO(lineNodes[i]);
                tabProcessor.addRowData(dtoData);
                var tr = tab.rows[tab.rows.length - 1];
                var dataType = dtoData["fieldType"];
                var dotNumberObj = getTrNode(tr, "dotNumber");
                if(dataType == "DATE" || dataType == "DATETIME"){
                    do_ProcessDatePattern();
                    dotNumberObj.value = "勿需设置";
                    dotNumberObj.readOnly = true;
                    dotNumberObj.className = "tableReadonlyInput2";
                } else if(dataType == "NUMBER"
                        || dataType == "SMALLINT"
                        || dataType == "INTEGER"
                        || dataType == "INT"
                        || dataType == "FLOAT"
                        || dataType == "BIGINT"
                        || dataType == "DECIMAL"
                        || dataType == "NUMERIC"
                        || dataType == "COUNTER"){
                    do_ProcessNumberPattern();
                    dotNumberObj.readOnly = false;
                    dotNumberObj.className = "tableInput3";
                } else {
                    do_ProcessOtherPattern();
                    dotNumberObj.value = "勿需设置";
                    dotNumberObj.readOnly = true;
                    dotNumberObj.className = "tableReadonlyInput2";
                }
                do_ProcessOtherOptions();
            }
        }
    }
}

function do_ProcessDatePattern(){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    var tr = rows[rows.length - 1];
    var toObj = getTrNode(tr, "dataPattern");
    clearOptions(toObj);
    var fromObj = document.getElementById("referenceDatePattern");
    for (var i = 0; i < fromObj.length; i++) {
        var option = fromObj.options[i];
        toObj.options[toObj.length] = new Option(option.text, option.value);
    }
}

function do_ProcessNumberPattern(){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    var tr = rows[rows.length - 1];
    var toObj = getTrNode(tr, "dataPattern");
    clearOptions(toObj);
    var fromObj = document.getElementById("referenceNumberPattern");
    for (var i = 0; i < fromObj.length; i++) {
        var option = fromObj.options[i];
        toObj.options[toObj.length] = new Option(option.text, option.value);
    }

    toObj = getTrNode(tr, "computeExpression");
    clearOptions(toObj);
    fromObj = document.getElementById("referenceComputeExpression");
    for (i = 0; i < fromObj.length; i++) {
        option = fromObj.options[i];
        toObj.options[toObj.length] = new Option(option.text, option.value);
    }
}

function do_ProcessOtherOptions(){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    var tr = rows[rows.length - 1];

    var toObj = getTrNode(tr, "enabled");
    var fromObj = document.getElementById("referenceEnabled");
    if(toObj.length == 0){
        for (var i = 0; i < fromObj.length; i++) {
            var option = fromObj.options[i];
            toObj.options[toObj.options.length] = new Option(option.text, option.value);
        }
    }

    toObj = getTrNode(tr, "fieldAlign");
    if(toObj.length == 0){
        fromObj = document.getElementById("referenceFieldAlign");
        for (i = 0; i < fromObj.length; i++) {
            option = fromObj.options[i];
            toObj.options[toObj.options.length] = new Option(option.text, option.value);
        }
    }
}

function do_ProcessOtherPattern(){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    var tr = rows[rows.length - 1];
    var toObj = getTrNode(tr, "dataPattern");
    clearOptions(toObj);
    toObj.options[toObj.options.length] = new Option("勿须设置", "");
    toObj = getTrNode(tr, "computeExpression");
    clearOptions(toObj);
    toObj.options[toObj.options.length] = new Option("勿须设置", "");
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
        var viewIds = "";
        for(var j = 0; j < fromObj.length; j++){
            var option = fromObj.options[j];
            if(option.selected){
                var clearField = option.value;
                for(var i = 0; i < rows.length; i++){
                    var tr = rows[i];
                    var node = getTrNode(tr, "fieldId");
                    if(node.value == clearField){
                        var viewId = getTrNode(tr, "viewId");
                        if(viewId.value != ""){
                            viewIds += viewId.value + ",";
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
        if(viewIds != ""){
            viewIds = viewIds.substring(0, viewIds.length - 1);
            do_DeleteReportView(viewIds);
        }
    }
}

function do_ClearAll(){
    if(confirm("确定删除所选字段吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
        moveAllOption("alreadyField", "availableField");
        var tab = document.getElementById("dataTable");
        var rows = tab.rows;
        var viewIds = "";
        for(var i = 0; i < rows.length; i++){
            var tr = rows[i];
            var node = getTrNode(tr, "viewId");
            if(node.value != ""){
                viewIds += node.value + ",";
            }
        }
        var tabProcessor = new TableProcessor(tab);
        tabProcessor.deleteAll();
        if(viewIds != ""){
            viewIds = viewIds.substring(0, viewIds.length - 1);
            do_DeleteReportView(viewIds);
        }
    }
}


function do_DeleteReportView(viewIds){
    var userParameter = "reportId=" + document.getElementById("reportId").value;
    userParameter += "&viewIds=" + viewIds;
    var ajaxProcessor = new RDSAjaxProcessor(do_ProcessDeleteResponse, true);
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.ReportViewProcessService");
    ajaxProcessor.setMethodName("deleteReportViewByIds");
    ajaxProcessor.setStrutsFrm("reportViewProcessFrm");
    ajaxProcessor.setSendData(userParameter);
    ajaxProcessor.performTask();
}

function do_ProcessDeleteResponse(XMLResponse){
    var content = XMLResponse.getElementsByTagName("content")[0];
    alert(content.text);
    do_RefreshReportCategory();
}

function do_RefreshReportCategory(){
    var reportType = "<%=reportType%>";
    if(reportType != "<%=RDSDictionaryList.REPORT_TYPE_LIST%>"){
        var actionURL = "/rds/reportCategoryProcess.do";
        actionURL += "?act=DETAIL_ACTION";
        actionURL += "&reportId=" + document.getElementById("reportId").value;
        var frm = window.parent.frames["reportCategoryFrm"];
        frm.location = actionURL;
    }
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
            var fieldNames = "fieldDesc;enabled";
            var fieldLabels = "字段描述;是否有效";
            var doc = window.parent.frames["reportDataFrm"].document;
            var reportType = doc.getElementById("reportType").value;
            var namePrefix = "dataFields";
            isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE, namePrefix);
            if(isValid){
                var rows = tab.rows;
                for(var i = 0; i < rows.length; i++){
                    var row = rows[i];
                    var dotNumberObj = getTrNode(row, "dotNumber");
                    if(dotNumberObj.value.indexOf("勿需设置") > -1){
                        dotNumberObj.value = ""
                    }
                }
                do_ProcessSortNumber();
            }
        } else {
            alert("未选择字段，不能保存。");
        }
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

function do_Next(){
    var ajaxProcessor = new RDSAjaxProcessor(do_ResponseCheckResult, false);
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.ReportViewProcessService");
    ajaxProcessor.setMethodName("hasReportViewField");
    ajaxProcessor.setStrutsFrm("reportViewProcessFrm");
    ajaxProcessor.setSendData("reportId=" + document.getElementById("reportId").value);
    ajaxProcessor.performTask();
}

function do_ResponseCheckResult(checkResult){
    if(checkResult == "N"){
        alert("尚未定义任何数据字段，不能继续...");
    } else {
        var actionURL = "/rds/reportParameterProcess.do";
        actionURL += "?act=DETAIL_ACTION";
        actionURL += "&reportId=" + document.getElementById("reportId").value;
        var frm = window.parent.frames["reportParameterFrm"];
        frm.location = actionURL;
        setTimeout(do_NextTab, 2000);
    }
}

function do_NextTab(){
    window.parent.tabBox.doclick(7, "");
}

function do_Prev(){
    window.parent.tabBox.doclick(5, "");
}
</script>