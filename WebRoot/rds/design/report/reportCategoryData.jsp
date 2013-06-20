<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>

<html>
<head>
    <title>报表分组字段详细信息</title>
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
<html:form action="/rds/reportCategoryProcess" method="post">
    <script type="text/javascript">
        var title = "报表分组字段详细信息";
        ArrActions[4][0] = true;
        ArrActions[15][0] = true;
    </script>
    <logic:empty property="reportId" name="reportCategoryProcessFrm">
        　<div align="center">
            <table border="0" width="100%" cellspacing="0" cellpadding="0" style="text-align: center" height="80%">
                <tr>
                    <td><font size="7" face="微软雅黑">请在“已定义报表”中选择报表</font></td>
                </tr>
            </table>
        </div>
    </logic:empty>
    <logic:notEmpty property="reportId" name="reportCategoryProcessFrm">
        <logic:equal value="1" property="report.reportType" name="reportCategoryProcessFrm">
            　<div align="center">
                <table border="0" width="100%" cellspacing="0" cellpadding="0" style="text-align: center" height="80%">
                    <tr>
                        <td><font size="7" face="微软雅黑">非交叉报表无分组字段信息</font></td>
                    </tr>
                </table>
            </div>
        </logic:equal>
        <logic:notEqual value="1" property="report.reportType" name="reportCategoryProcessFrm">
        　<div align="center">
            <table border="0" width="100%" cellspacing="0" cellpadding="0" style="text-align: center">
                <tr>
                    <td><font size="3" face="微软雅黑">报表“<bean:write name="reportCategoryProcessFrm" property="report.reportName"/>”的分组字段信息</font></td>
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
                        <td width="17%" height="23" align="center">可选字段</td>
                        <td width="14%" height="23" align="center">操作</td>
                        <td width="19%" height="23" align="center" colspan="2">　已选字段</td>
                        <td width="17%" height="23" align="center">分组层级</td>
                        <td width="8%" height="23">&nbsp;&nbsp;</td>
                    </tr>
                    <tr style="height:160px">
                        <td width="8%" rowspan="3"></td>
                        <td width="17%" rowspan="3">
                            <html:select property="availableField" styleId="availableField" multiple="true" style="width:100%;height:100%"><bean:write name="reportCategoryProcessFrm" property="availableFieldsOptions" filter="false"/></html:select>
                        </td>
                        <td width="14%" align="center" height="75">
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="添加所选" id="aboveAdd" onclick="do_AddSelected()"></P>
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="删除所选" id="aboveRemove" onclick="do_ClearSelected()"></P>
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="删除全部" id="aboveRemoveAll" onclick="do_ClearAll()"></P>
                        </td>
                        <td width="2%" align="center" height="75">
                        	<P style="margin-top: 2px; margin-bottom: 2px">报</P>
                        	<P style="margin-top: 2px; margin-bottom: 2px">表</P>
                        	<P style="margin-top: 2px; margin-bottom: 2px">上</P>
                        	<P style="margin-top: 2px; margin-bottom: 2px">方</P>
						</td>
                        <td width="17%" height="75">
                            <html:select property="aboveField" styleId="aboveField" multiple="true" style="width:100%;height:100%" ondblclick="do_ClearSelected()"><bean:write name="reportCategoryProcessFrm" property="aboveFieldsOptions" filter="false"/></html:select></td>
                        <td width="17%" align="center" height="75">
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="置 顶" id="aboveTop" onclick="do_PositionTop()"></P>
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="上 移" id="aboveUp" onclick="do_PositionUp()"></P>
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="下 移" id="aboveDown" onclick="do_PositionDown()"></P>
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="置 底" id="aboveBottom" onclick="do_PositionBottom()"></P>
                        </td>
                        <td width="8%" rowspan="3"> </td>
                    </tr>
                    <tr height="2">
                        <td width="50%" align="center" height="2" colspan="4">
						<hr style="width:100%" color="#666666" size="1"></td>
                    </tr>
                    <tr height="75">
                        <td width="14%" align="center" height="75">
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="添加所选" id="leftAdd" onclick="do_AddSelected()"></P>
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="删除所选" id="leftRemove" onclick="do_ClearSelected()"></P>
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="删除全部" id="leftRemoveAll" onclick="do_ClearAll()"></P>
                        </td>
                        <td width="2%" align="center" height="75">
                        	<P style="margin-top: 2px; margin-bottom: 2px">报</P>
                        	<P style="margin-top: 2px; margin-bottom: 2px">表</P>
                        	<P style="margin-top: 2px; margin-bottom: 2px">左</P>
                        	<P style="margin-top: 2px; margin-bottom: 2px">方</P>
						</td>
                        <td width="17%" height="75"><html:select property="leftField" styleId="leftField" multiple="true" style="width:100%;height:100%" ondblclick="do_ClearSelected()"><bean:write name="reportCategoryProcessFrm" property="leftFieldsOptions" filter="false"/></html:select></td>
                        <td width="17%" align="center" height="75">
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="置 顶" id="leftTop" onclick="do_PositionTop()"></P>
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="上 移" id="leftUp" onclick="do_PositionUp()"></P>
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="下 移" id="leftDown" onclick="do_PositionDown()"></P>
                            <P style="margin-top: 2px; margin-bottom: 2px"><input type="button" value="置 底" id="leftBottom" onclick="do_PositionBottom()"></P>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:285px;left:1px;width:100%">
        <table id="headTable" border="1" width="100%">
            <tr class="headerTR" style="text-align:center">
                <td width="8%">所处位置</td>
                <td width="15%">字段名称</td>
                <td width="15%">字段描述</td>
                <td width="8%">分组层级</td>
                <td width="10%">显示标志</td>
                <td width="8%">是否有效</td>
                <td style="display:none">隐藏域字段</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:358px;left:1px" align="left"
		     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <logic:notEmpty name="reportCategoryProcessFrm" property="categoryFields">
                <logic:iterate id="categoryFields" name="reportCategoryProcessFrm" property="categoryFields" indexId="index">
                    <tr>
                        <td width="8%"><html:text property="viewLocationName" name="categoryFields" indexed="true" styleClass="tableReadonlyInput1" readonly="true"/></td>
                        <td width="15%"><html:text property="fieldName" name="categoryFields" indexed="true"  styleClass="tableReadonlyInput1" readonly="true"/></td>
                        <td width="15%"><html:text property="fieldDesc" name="categoryFields" indexed="true" styleClass="tableInput1"/></td>
                        <td width="8%"><html:text property="categoryLevel" name="categoryFields" indexed="true"  styleClass="tableReadonlyInput3" readonly="true"/></td>
                        <td width="10%"><html:select property="displayFlag" name="categoryFields" indexed="true" style="width:100%"><bean:write name="categoryFields" property="displayFlagOptions" filter="false"/></html:select></td>
                        <td width="8%"><html:select property="enabled" name="categoryFields" indexed="true" style="width:100%"><bean:write name="categoryFields" property="enabledOptions" filter="false"/></html:select></td>
                        <td style="display:none">
                            <html:hidden property="categoryId" name="categoryFields" indexed="true"/>
                            <html:hidden property="fieldId" name="categoryFields" indexed="true"/>
                            <html:hidden property="viewLocation" name="categoryFields" indexed="true"/>
                        </td>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="reportCategoryProcessFrm" property="categoryFields">
                <tr style="display:none">
                    <td width="8%"><input type="text" name="categoryFields[0].viewLocationName" class="tableReadonlyInput1" readonly="true"></td>
                    <td width="15%"><input type="text" name="categoryFields[0].fieldName" class="tableReadonlyInput1" readonly="true"></td>
                    <td width="15%"><input type="text" name="categoryFields[0].fieldDesc" class="tableInput1"></td>
                    <td width="8%"><input type="text" name="categoryFields[0].categoryLevel" class="tableReadonlyInput3" readonly="true"></td>
                    <td width="10%"><select name="categoryFields[0].displayFlag" style="width:100%"></select></td>
                    <td width="8%"><select name="categoryFields[0].enabled" style="width:100%"></select></td>
                    <td style="display:none">
                        <input type="hidden" name="categoryFields[0].categoryId">
                        <input type="hidden" name="categoryFields[0].fieldId">
                        <input type="hidden" name="categoryFields[0].viewLocation">
                    </td>
                </tr>
            </logic:empty>
        </table>
       </div>
        </logic:notEqual>
    </logic:notEmpty>
    <html:select property="referenceEnabled" styleId="referenceEnabled" style="display:none"><bean:write name="reportCategoryProcessFrm" property="enabledOptions" filter="false"/></html:select>
    <html:select property="referenceDisplayFlag" styleId="referenceDisplayFlag" style="display:none"><bean:write name="reportCategoryProcessFrm" property="displayFlagOptions" filter="false"/></html:select>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="reportId" styleId="reportId"/>
    <html:hidden property="dataSaved" styleId="dataSaved"/>
    <div id="pageNaviDiv" align="right"><a href="" onclick="do_Prev();return false;">上一步</a> </div>
</html:form>
</body>
</html>
<script type="text/javascript">
var srcObj = null;

function do_initPage(){
    do_SetPageWidth();
}

function do_AddSelected(){
    var fromObj = document.getElementById("availableField");
    var selectedCount = getSelectedCount(fromObj);
    if(selectedCount == 0){
        alert("未选中待添加的字段，请先选择字段。");
        return;
    }
    var eventSrc = event.srcElement.id;
    srcObj = eventSrc;
    var targetObj = (eventSrc == "aboveAdd") ? "aboveField" : "leftField";
    moveSelectedOption("availableField", targetObj);
    do_RequestServerFields();
}

function do_RequestServerFields(){
    var eventSrc = event.srcElement.id;
    var targetObj = (eventSrc == "aboveAdd") ? "aboveField" : "leftField";
    selectAll(targetObj);
    var fieldIds = getSelectValue(targetObj, ",");
    var lineFieldIds = do_GetLineFieldIds();
    fieldIds = do_MinusFields(fieldIds, lineFieldIds);

    var userParameter = "reportId=" + document.getElementById("reportId").value;
    userParameter += "&fieldIds=" + fieldIds;

    var ajaxProcessor = new RDSAjaxProcessor(do_ProcessFieldsDisplay, true);
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.ReportCategoryProcessService");
    ajaxProcessor.setMethodName("getSelectedFieldsXML");
    ajaxProcessor.setStrutsFrm("reportCategoryProcessFrm");
    ajaxProcessor.setSendData(userParameter);
    ajaxProcessor.performTask();
}

function do_ProcessFieldsDisplay(returnXML){
    if(returnXML){
        var tab = document.getElementById("dataTable");
        var tabProcessor = new TableProcessor(tab);
        tabProcessor.setNamePrefix("categoryFields");
        tabProcessor.setUniqueField("fieldId");
        var lineNodes = returnXML.getElementsByTagName("line");
        if (lineNodes != null && lineNodes != undefined) {
            var viewLocation = (srcObj == "aboveAdd") ? "ABOVE":"LEFT";
            var viewLocationName = (srcObj == "aboveAdd") ? "报表上方":"报表左方";
            var fieldCount = lineNodes.length;
            for (var i = 0; i < fieldCount; i++) {
                var dtoData = xmlNode2DTO(lineNodes[i]);
                dtoData["viewLocation"] = viewLocation;
                dtoData["viewLocationName"] = viewLocationName;
                tabProcessor.addRowData(dtoData);
                do_ProcessLineOptions();
            }
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

function do_ProcessLineOptions(){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    var tr = rows[rows.length - 1];

    var toObj = getTrNode(tr, "enabled");
    var fromObj = document.getElementById("referenceEnabled");
    if(toObj.length == 0){
        copyObjOptions(fromObj, toObj);
    }

    toObj = getTrNode(tr, "displayFlag");
    if(toObj.length == 0){
        fromObj = document.getElementById("referenceDisplayFlag");
        copyObjOptions(fromObj, toObj);
    }
}

function do_AddAll(){
    var eventSrc = event.srcElement.id;
    srcObj = eventSrc;
    var targetObj = (eventSrc == "aboveAdd") ? "aboveField" : "leftField";
    moveAllOption("availableField", targetObj);
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

function do_ClearSelected(){
    var eventSrc = event.srcElement.id;
    var fromObj = (eventSrc.indexOf("above") == 0) ? "aboveField" : "leftField";
    var alreadyField = document.getElementById(fromObj);
    var selectedCount = getSelectedCount(alreadyField);
    if(selectedCount == 0){
        alert("未选中待删除的字段，请先选择字段。");
        return;
    }
    if(confirm("确定删除所选字段吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
        var tab = document.getElementById("dataTable");
        var rows = tab.rows;
        var categoryIds = "";
        for(var j = 0; j < alreadyField.length; j++){
            var option = alreadyField.options[j];
            if(option.selected){
                var clearField = option.value;
                for(var i = 0; i < rows.length; i++){
                    var tr = rows[i];
                    var node = getTrNode(tr, "fieldId");
                    if(node.value == clearField){
                        var categoryId = getTrNode(tr, "categoryId");
                        if(categoryId.value != ""){
                            categoryIds += categoryId.value + ",";
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
        moveSelectedOption(fromObj, "availableField");
        if(categoryIds != ""){
            categoryIds = categoryIds.substring(0, categoryIds.length - 1);
            do_DeleteReportCategory(categoryIds);
        }
    }
}

function do_ClearAll(){
    if(confirm("确定删除所选字段吗？继续请点击“确定”按钮，否则请点击“取消”按钮。")){
        var eventSrc = event.srcElement.id;
        var fromObjName = (eventSrc == "aboveRemoveAll") ? "aboveField" : "leftField";
        var fromObject = document.getElementById(fromObjName);
        var optionCount = fromObject.options.length;
        moveAllOption(fromObjName, "availableField");
        if(optionCount > 0){
            var tab = document.getElementById("dataTable");
            var rows = tab.rows;
            var categoryIds = "";
            for(var i = 0; i < rows.length; i++){
                var tr = rows[i];
                var node = getTrNode(tr, "categoryId");
                var viewLocation = getTrNode(tr, "viewLocation").value;
                if((viewLocation == "ABOVE" && fromObjName == "aboveField")
                        || (viewLocation == "LEFT" && fromObjName == "leftField")){
                    if(rows.length > 1){
                        tab.deleteRow(i);
                        i--;
                    } else {
                        var field = getTrNode(tr, "fieldId");
                        field.value = "";
                        tr.style.display = "none";
                    }
                    if(node.value != ""){
                        categoryIds += node.value + ",";
                    }
                }
            }
            if(categoryIds != ""){
                categoryIds = categoryIds.substring(0, categoryIds.length - 1);
                do_DeleteReportCategory(categoryIds);
            }
        }
    }
}

function do_DeleteReportCategory(categoryIds){
    var userParameter = "reportId=" + document.getElementById("reportId").value;
    userParameter += "&categoryIds=" + categoryIds;
    var ajaxProcessor = new RDSAjaxProcessor(do_ProcessDeleteResponse, true);
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.ReportCategoryProcessService");
    ajaxProcessor.setMethodName("deleteReportCategoryByIds");
    ajaxProcessor.setStrutsFrm("reportCategoryProcessFrm");
    ajaxProcessor.setSendData(userParameter);
    ajaxProcessor.performTask();
}

function do_ProcessDeleteResponse(XMLResponse){
    var content = XMLResponse.getElementsByTagName("content")[0];
    var contentValue = content.text;
    alert(contentValue);
}

function do_PositionTop(){
    var eventSrc = event.srcElement.id;
    var fromObj = (eventSrc == "aboveTop") ? "aboveField" : "leftField";
    var alreadyField = document.getElementById(fromObj);
    moveUp(alreadyField, true);
}

function do_PositionUp(){
    var eventSrc = event.srcElement.id;
    var fromObj = (eventSrc == "aboveUp") ? "aboveField" : "leftField";
    var alreadyField = document.getElementById(fromObj);
    moveUp(alreadyField, false);
}

function do_PositionDown(){
    var eventSrc = event.srcElement.id;
    var fromObj = (eventSrc == "aboveDown") ? "aboveField" : "leftField";
    var alreadyField = document.getElementById(fromObj);
    moveDown(alreadyField, false);
}

function do_PositionBottom(){
    var eventSrc = event.srcElement.id;
    var fromObj = (eventSrc == "aboveBottom") ? "aboveField" : "leftField";
    var alreadyField = document.getElementById(fromObj);
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
            selectAll("aboveField");
            selectAll("leftField");
            var fieldNames = "fieldDesc;displayFlag;enabled";
            var fieldLabels = "字段描述;显示标志;是否有效";
            var namePrefix = "categoryFields";
            isValid = formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE, namePrefix);
            if(isValid){
//                var leftCount = document.getElementById("availableField").length;
//                if(leftCount > 0){
//                    alert("分组字段必须分配完毕。");
//                    isValid = false;
//                } else {
//                    do_ProcessSortNumber("aboveField");
//                    do_ProcessSortNumber("leftField");
//                }
                do_ProcessSortNumber("aboveField");
                do_ProcessSortNumber("leftField");
            }
        } else {
            alert("未选择字段，不能保存。");
        }
    }
    return isValid;
}

function do_ProcessSortNumber(fieldPosition){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    var sortNumberObj = document.getElementById(fieldPosition);
    var options = sortNumberObj.options;
    var categoryLevel = null;
    for(var i = 0; i < rows.length; i++){
        var tr = rows[i];
        var node = getTrNode(tr, "fieldId");
        for(var j = 0; j < options.length; j++){
            var option = options[j];
            var fieldId = option.value;
            if(node.value == fieldId){
                categoryLevel = getTrNode(tr, "categoryLevel");
                categoryLevel.value = (j + 1);
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

function do_Prev(){
    window.parent.tabBox.doclick(7, "");
}
</script>