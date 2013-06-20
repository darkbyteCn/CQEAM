<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>

<%@ page import="com.sino.rds.share.form.DataModelFrm" %>

<html>
<head>
    <title>已定义模型</title>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/FormProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<html:form action="/rds/dataModelAction" method="post">
    <table  border="0" width="100%" id="table1" class="queryHeadBg">
        <tr style="height:22px">
            <td width="8%" align="right">模型名称：</td>
            <td width="16%"><html:text property="modelName" styleId="modelName" style="width:100%"/></td>
            <td width="8%" align="right">模型类型：</td>
            <td width="16%"><html:select property="dataSrcType" style="width:100%"><bean:write name="dataModelFrm" property="dataSrcOptions" filter="false"/></html:select></td>
            <td width="8%" align="right">是否有效：</td>
            <td width="16%"><bean:write name="dataModelFrm" property="enabledRadio" filter="false"/></td>
        </tr>
    </table>
    <html:hidden property="act"/>
    <html:hidden property="modelIds" styleId="modelIds"/>
    </html:form>
<div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:23px;left:1px;width:100%">
    <table id="headTable" border="1" width="100%">
        <tr class="headerTR" style="text-align:center">
            <td width="17%">模型名称</td>
            <td width="20%">模型描述</td>
            <td width="10%">模型类型</td>
            <td width="20%">模型</td>
            <td width="10%">数据源</td>
            <td width="10%">是否有效</td>
            <td width="10%">创建日期</td>
        </tr>
    </table>
</div>
<%
    DTOSet dtos = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (dtos != null && !dtos.isEmpty()) {
 %>
    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:46px;left:1px" align="left"
		     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        for (int i = 0; i < dtos.getSize(); i++) {
            DataModelFrm frm = (DataModelFrm)dtos.getDTO(i);
            String modelSQL = frm.getModelSql();
            String tempModelSQL = modelSQL;
            if(tempModelSQL.length() > 40){
                tempModelSQL = tempModelSQL.substring(0, 40) + "...";
            }
%>
            <tr style="height:23px;cursor:pointer" title="点击选中该行，可在其它选项卡察看“<%=frm.getModelName()%>”相关信息" onclick="do_ResponseModelChange(this)" id="<%=frm.getModelId()%>">
                <td width="17%"><%=frm.getModelName()%></td>
                <td width="20%"><%=frm.getModelDesc()%></td>
                <td width="10%"><%=frm.getDataSrcType()%></td>
                <td width="20%" alt="<%=modelSQL%>" onmouseover="showAltValue(this)" onmouseout="hideAltValue()"><%=tempModelSQL%></td>
                <td width="10%"><%=frm.getConnectionName()%></td>
                <td width="10%" align="center"><%=frm.getEnabledName()%></td>
                <td width="10%" align="center"><%=frm.getCreationDate()%></td>
            </tr>
<%
        }
%>
        </table>
       </div>
<%
    }
%>
<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<div id="hiddenDiv" style="position: absolute; overflow:scroll;width: 450px;height:200px;display:none;background-color:#CCFFCC;"></div>
</body>
</html>
<script type="text/javascript">

function showAltValue(td){
    var tr = td.parentNode;
    var dataSrcTd = tr.childNodes[2];
    if(dataSrcTd.innerHTML == "SQL"){
        var pageObj = event.srcElement;
        showObjValue("hiddenDiv", pageObj, '');
    }
}

function hideAltValue(){
    var clientX = event.clientX;
    var clientY = event.clientY;
    var hiddenDiv = document.getElementById("hiddenDiv");
    var offsetLeft = hiddenDiv.offsetLeft;
    var offsetTop = hiddenDiv.offsetTop;
    var offsetWidth = hiddenDiv.offsetWidth;
    var offsetHeight = hiddenDiv.offsetHeight;
    if(clientX < offsetLeft ||  clientX > (offsetLeft + offsetWidth)){
        hiddenDiv.style.display = "none";
    } else if(clientY < offsetTop ||  clientY > (offsetTop + offsetHeight)){
        hiddenDiv.style.display = "none";
    }
}

var checkedColor = "#FFFF99";

function do_SetTrCheckedColor(tr){
    tr.style.backgroundColor = checkedColor;
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    for(var i = 0; i < rows.length; i++){
        var row = rows[i];
        if(row.id == tr.id){
            continue;
        }
        row.style.backgroundColor = "#FFFFFF";
    }
}

function do_ProcessModelDataURL(tr){
    var actionURL = "/rds/dataModelAction.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&modelId=" + tr.id;
    var frm = window.parent.frames["modelDataFrm"];
    frm.location = actionURL;
}

function do_ProcessModelFieldURL(tr){                                                      
    var actionURL = "/rds/modelFieldAction.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&modelId=" + tr.id;
    var frm = window.parent.frames["modelFieldFrm"];
    frm.location = actionURL;
}

function do_SelectReportModel(tr){
    var modelId = tr.id;
    window.parent.document.getElementById("modelId").value = modelId;
    var frm = window.parent.frames["reportDataFrm"];
    var doc = frm.document;
    if(doc.getElementById("reportId") == ""){
        var modelObj = doc.getElementById("modelId");
        selectSpecialOptionByItem(modelObj, modelId);
    }
}

function do_ResponseModelChange(tr){
    do_SetTrCheckedColor(tr);
    do_ProcessModelDataURL(tr);
    do_ProcessModelFieldURL(tr);
    do_SelectReportModel(tr);
    setTimeout(do_NextTab, 2000);
}

function do_NextTab(){
    window.parent.tabBox.doclick(2, "");
}
</script>
