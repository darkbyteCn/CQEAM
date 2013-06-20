<%@ page contentType="text/html;charset=GBK" language="java" %>

<%@ include file="/rds/public/dataPageInclude.jsp"%>

<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%
    String lovListURL = contextPath + "/rds/lovProcess.do";
    lovListURL += "?act=QUERY_ACTION";
%>
<html>
<head>
    <title>值列表详细信息</title>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/RadioProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/AjaxProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_initPage();" onkeydown="autoExeFunction('do_Save()');">
<jsp:include page="/message/MessageProcess"/>
<html:form action="/rds/lovProcess" method="post">
    <table border="1" style="width:100%" cellspacing="0" bordercolor="#333333" style="border-collapse: collapse">
        <tr style="height:25px">
            <td width="35%" height="22" align="right">列表名称：</td>
            <td width="65%" height="22"><html:text property="lovName" styleId="lovName" style="width:100%" styleClass="tableInput1"/></td>
        </tr>
        <tr style="height:25px">
            <td width="35%" height="22" align="right">列表类型：</td>
            <td width="65%" height="22"><bean:write name="lovDefineFrm" property="lovTypeRadio" filter="false"/></td>
        </tr>
        <tr id="CONNTr" style="height:25px;display:none">
            <td width="35%" align="right">数据源：</td>
            <td width="65%"><html:select property="connectionId" styleId="connectionId" style="width:100%" onchange="do_ProcessConnection(this)"><bean:write name="lovDefineFrm" property="connectionOptions" filter="false"/></html:select></td>
        <tr>
        <tr id="SQLTr" style="height:300px;display:none">
            <td width="35%" align="right">列表SQL：</td>
            <td width="65%" ><html:textarea property="lovSql" styleId="lovSql" style="width:100%;height:100%" styleClass="tableInput1"/></td>
        </tr>
        <tr id="CONSTr" style="height:300px;display:none">
            <td width="35%" align="right">列表值：</td>
            <td width="65%" ><html:textarea property="lovValue" styleId="lovValue" style="width:100%;height:100%" styleClass="tableInput1"/></td>
        </tr>
        <tr style="height:25px">
            <td width="35%" height="22" align="right">添加空选项：</td>
            <td width="65%" height="22"><bean:write name="lovDefineFrm" property="addBlankRadio" filter="false"/></td>
        </tr>
        <tr style="height:25px">
            <td width="35%" height="22" align="right">是否有效：</td>
            <td width="65%" height="22"><bean:write name="lovDefineFrm" property="enabledRadio" filter="false"/></td>
        </tr>
        <tr style="height:25px">
            <td width="35%" height="22" align="right">创建日期：</td>
            <td width="65%" height="22"><html:text property="creationDate" styleId="creationDate" style="width:100%" styleClass="tableReadonlyInput2" readonly="true"/></td>
        </tr>
        <tr style="height:25px">
            <td width="35%" height="22" align="right">修改日期：</td>
            <td width="65%" height="22"><html:text property="lastUpdateDate" styleId="lastUpdateDate" style="width:100%" styleClass="tableReadonlyInput2" readonly="true"/></td>
        </tr>
    </table>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="lovId" styleId="lovId"/>
    </html:form>
</body>
</html>
<script type="text/javascript">
function do_initPage(){
    var leftFrm = parent.frames["lovListFrm"];
    leftFrm.location.href = "<%=lovListURL%>";
    do_ControlSQLCons();
}

function do_ControlSQLCons(){
    var lovType = getRadioValue("lovType");
    if(lovType == "SQL"){
        document.getElementById("SQLTr").style.display = "block";
        document.getElementById("CONNTr").style.display = "block";
        document.getElementById("CONSTr").style.display = "none";
    } else if(lovType == "CONS"){
        document.getElementById("SQLTr").style.display = "none";
        document.getElementById("CONSTr").style.display = "block";
        document.getElementById("CONNTr").style.display = "none";
    } else {
        document.getElementById("CONSTr").style.display = "none";
        document.getElementById("SQLTr").style.display = "none";
        document.getElementById("CONNTr").style.display = "none";
    }
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
    }
}


function do_Check_Save(){
    var isValid = false;
    var fieldNames = "lovName;lovType;addBlank;enabled";
    var fieldDescs = "列表名称;列表类型;是否添加空选项;是否有效";
    isValid = formValidate(fieldNames, fieldDescs, EMPTY_VALIDATE, "");
    if(isValid){
        var lovType = getCheckedRadio("lovType").id;
        if(lovType != "lovType_ACCOUNT_PERIOD"){
            if(lovType == "lovType_SQL"){
                fieldNames = "lovSql";
                fieldDescs = "列表SQL";
            } else if(lovType == "lovType_CONS>"){
                fieldNames = "lovValue";
                fieldDescs = "列表值";
            }
            isValid = formValidate(fieldNames, fieldDescs, EMPTY_VALIDATE, "");
        }
    }
    return isValid;
}

function configCreatePage() {
    var pageConfig = new DataPageConfig();
    pageConfig.setOpenWindow(false);
    return pageConfig;
}


function do_CloseConfig() {
    var cfg = new CloseConfig();
    cfg.setEditPage(false);
    cfg.setRefreshOpener(false);
    return cfg;
}

function do_LovTypeChange(obj){
    do_ControlSQLCons();
}
</script>