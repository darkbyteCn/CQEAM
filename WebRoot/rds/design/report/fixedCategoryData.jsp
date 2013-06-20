<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/dataPageInclude.jsp"%>
<html>
<head>
    <title>固定分组报表分组值定义</title>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/DateProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/FormProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/RadioProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/CheckboxProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/SelectProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/table/TableProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/AjaxProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_initPage();" onkeydown="autoExeFunction('do_Save()');">
<jsp:include page="/message/MessageProcess"/>
<html:form action="/rds/fixedCategoryAction" method="post">
    <script type="text/javascript">
        var title = "固定分组报表分组值定义";
        printTitleBar(title);
        ArrActions[2] = new Array(true, "添加新值", "action_edit.gif", "添加新值", "do_AddNewValue")
        ArrActions[3] = new Array(true, "删除所选", "action_edit.gif", "删除所选", "do_DeleteRow")
        ArrActions[4][0] = true;
        ArrActions[15][0] = true;
        printToolBar();
    </script>

    <table class="auto-style1" style="width: 100%">
        <tr>
            <td>
                <table border="0" width="100%" cellspacing="1">
                    <tr>
                        <td width="20%" height="23">　</td>
                        <td width="30%" height="23" align="center">报表名称</td>
                        <td width="30%" height="23" align="center">分组字段</td>
                        <td width="20%" height="23" align="center">　</td>
                    </tr>
                    <tr height="100">
                        <td width="20%" height="100"></td>
                        <td width="30%" height="100">
                            <html:select property="reportId" styleId="reportId" size="10" style="width:100%" onchange="do_ViewDetail()"><bean:write name="fixedCategoryProcessFrm" property="reportOptions" filter="false"/></html:select>
                        </td>
                        <td width="30%" height="100">
                            <html:select property="categoryId" styleId="categoryId" size="10" style="width:100%" onchange="do_CategoryChange()"><bean:write name="fixedCategoryProcessFrm" property="categoryOptions" filter="false"/></html:select>
                        </td>
                        <td width="20%" height="100"></td>
                    </tr>
                    </table>
            </td>
        </tr>
    </table>    

    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:240px;left:1px;width:100%">
        <table id="headTable" border="1" width="100%">
            <tr class="headerTR" style="text-align:center;cursor:pointer" title="点击全选或取消全选" onclick="executeClick(this)">

                <td width="3%" align="center"><input type="checkbox" name="mainCheck" id="mainCheck" onPropertyChange="checkAll(this.name, 'subCheck')"></td>
                <td width="10%">所处位置</td>
                <td width="20%">字段名称</td>

                <td width="20%">字段描述</td>
                <td width="20%">分组值</td>
                <td width="7%">排序编号</td>

                <td width="20%">父分组值</td>
                <td style="display:none">隐藏域字段</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:55%;width:100%;position:absolute;top:265px;left:1px" align="left"
		     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <logic:notEmpty name="fixedCategoryProcessFrm" property="fixedCategories">
                <logic:iterate id="fixedCategories" name="fixedCategoryProcessFrm" property="fixedCategories" indexId="index">
                    <tr>
                        <td width="3%" align="center"><input type="checkbox" name="subCheck"></td>
                        <td width="10%"><html:text property="viewLocationName" name="fixedCategories" indexed="true" styleClass="tableReadonlyInput1" readonly="true"/></td>
                        <td width="20%"><html:text property="fieldName" name="fixedCategories" indexed="true"  styleClass="tableReadonlyInput1" readonly="true"/></td>

                        <td width="20%"><html:text property="fieldDesc" name="fixedCategories" indexed="true" styleClass="tableReadonlyInput1" readonly="true"/></td>
                        <td width="20%"><html:text property="defineValue" name="fixedCategories" indexed="true" styleClass="tableInput1"/></td>
                        <td width="7%"><html:text property="sortNumber" name="fixedCategories" indexed="true" styleClass="tableInput3"/></td>

                        <td width="20%"><html:select property="parentId" name="fixedCategories" indexed="true" style="width:100%"><bean:write name="fixedCategories" property="parentIdOptions" filter="false"/></html:select></td>
                        <td style="display:none">
                            <html:hidden property="defineId" name="fixedCategories" indexed="true"/>
                        </td>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="fixedCategoryProcessFrm" property="fixedCategories">
                <tr style="display:none">
                    <td width="3%" align="center"><input type="checkbox" name="subCheck"></td>
                    <td width="10%"><input type="text" name="fixedCategories[0].viewLocationName" class="tableReadonlyInput1" readonly="true"></td>
                    <td width="20%"><input type="text" name="fixedCategories[0].fieldName" class="tableReadonlyInput1" readonly="true"></td>

                    <td width="20%"><input type="text" name="fixedCategories[0].fieldDesc" class="tableReadonlyInput1"></td>
                    <td width="20%"><input type="text" name="fixedCategories[0].defineValue" class="tableInput1"></td>
                    <td width="7%"><input type="text" name="fixedCategories[0].sortNumber" class="tableInput3"></td>

                    <td width="20%"><select name="fixedCategories[0].parentId" style="width:100%"></select></td>
                    <td style="display:none">
                        <input type="hidden" name="fixedCategories[0].defineId">
                    </td>
                </tr>
            </logic:empty>
        </table>
       </div>
    <html:hidden property="act" styleId="act"/>
    <select name="referenceParentId" id="referenceParentId" style="display:none"></select>
</html:form>
</body>
</html>
<script type="text/javascript">
var srcObj = null;

function do_initPage(){
    do_SetPageWidth();
}

function configDetailPage() {
    var reportId = event.srcElement.value;
    var pageConfig = new DataPageConfig();
    pageConfig.setActionURL("/rds/fixedCategoryAction.do");
    pageConfig.setAct("DETAIL_ACTION");
    pageConfig.setOpenWindow(false);
    var userParameter = "reportId=" + reportId;
    pageConfig.setParameters(userParameter);
    return pageConfig;
}

function do_CategoryChange(){
    dropAllOption("referenceParentId");
    var userParameter = "reportId=" + document.getElementById("reportId").value;
    userParameter += "&categoryId=" + event.srcElement.value;
    var ajaxProcessor = new RDSAjaxProcessor(do_ShowDefinedValue, true);
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.FixedCategoryService");
    ajaxProcessor.setMethodName("getDefinedCategoryValueXML");
    ajaxProcessor.setStrutsFrm("fixedCategoryProcessFrm");
    ajaxProcessor.setSendData(userParameter);
    ajaxProcessor.performTask();
}

function do_ShowDefinedValue(returnXML){
    var tab = document.getElementById("dataTable");
    deleteRow(tab);
    if(returnXML){
        var tabProcessor = new TableProcessor(tab);
        tabProcessor.setNamePrefix("fixedCategories");
        tabProcessor.setUniqueField("defineId");
        tabProcessor.setClearContent(true);
        var lineNodes = returnXML.getElementsByTagName("line");
        if (lineNodes != null && lineNodes != undefined) {
            for (var i = 0; i < lineNodes.length; i++) {
                var dtoData = xmlNode2DTO(lineNodes[i]);
                tabProcessor.addRowData(dtoData);
            }
        }
    }
    do_SearchParentValue();
}

function do_SearchParentValue(){
    var userParameter = "reportId=" + document.getElementById("reportId").value;
    userParameter += "&categoryId=" + document.getElementById("categoryId").value;
    var ajaxProcessor = new RDSAjaxProcessor(do_ShowDefinedValue, true);
    ajaxProcessor.setServiceClass("com.sino.rds.design.report.service.FixedCategoryService");
    ajaxProcessor.setMethodName("getParentValueOptions");
    ajaxProcessor.setStrutsFrm("fixedCategoryProcessFrm");
    ajaxProcessor.setSendData(userParameter);
    ajaxProcessor.performTask();
}

function do_ShowParentValue(parentOption){
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    if(needValidate(tab)){
        for(var i = 0; i < rows.length; i++){
            var row = rows[i];
            var node = getTrNode(row, "parentId");
            node.outerHTML = "<select name=\"" + node.name + "\" style=\"width:100%\">" + parentOption + "</select>";
        }
    } else {
        document.getElementById("referenceParentId").outerHTML = "<select name=\"referenceParentId\" id=\"referenceParentId\" style=\"display:none\">" + parentOption + "</select>";
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

function do_AddNewValue(){
    var categoryId = document.getElementById("categoryId");
    if(categoryId.value == ""){
        alert("请选择分组字段再进行本操作！");
        return;
    }
    var valueCount = window.prompt("请输入固定值个数", "5");
    if(valueCount == null || valueCount == "null"){
        return;
    }
    if(!isPositiveInteger(valueCount)){
        alert("请输入正整数！");
        return;
    }
    var tab = document.getElementById("dataTable");
    var tabProcessor = new TableProcessor(tab);
    tabProcessor.setNamePrefix("fixedCategories");
    tabProcessor.setUniqueField("defineId");
    for(var i = 0; i < valueCount; i++){
        tabProcessor.addRowData(constructDefaultValue(tab, categoryId.options[categoryId.options.selectedIndex]));
        var row = tab.rows[i];
        var obj = getTrNode(row, "parentId");
        if(obj.options.length == 0){
            copyAllOption("referenceParentId",obj.name);            
        }
    }
}

function constructDefaultValue(tab, opt){
    var defaultData = new Object();
    var sortNumber = tab.rows.length;
    if(tab.rows[0].style.display != "none"){
        sortNumber++;
    }
    defaultData["sortNumber"] = sortNumber;            
    var attributes = opt.attributes;
    for(var i = 0; i < attributes.length; i++){
        var attribute = attributes[i];
        defaultData[attribute.name] = attribute.value;
    }
    return defaultData;
}

function do_DeleteRow(){
    var tab = document.getElementById("dataTable");
    deleteTableRow(tab, "subCheck");    
}
</script>