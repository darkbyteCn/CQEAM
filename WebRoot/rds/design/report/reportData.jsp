<%@ page contentType="text/html;charset=GBK" language="java" %>

<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/dataPageInclude.jsp"%>

<%@ page import="com.sino.rds.share.constant.RDSDictionaryList" %>

<html>
<head>
    <title>报表定义详细信息</title>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/RadioProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/AjaxProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_initPage();" onkeydown="autoExeFunction('do_Save()');">
<jsp:include page="/message/MessageProcess"/>
<html:form action="/rds/reportDefineAction" method="post">
<table border="0" width="100%" cellspacing="1">
	<tr>
		<td width="15%">　</td>
		<td width="70%">
            <table border="1" width="100%" cellspacing="0" bordercolor="#333333" style="border-collapse: collapse">
                <tr>
                    <td width="20%" height="22" align="right">报表代码：</td>
                    <td width="30%" height="22"><html:text property="reportCode" styleId="reportCode" readonly="true" style="width:100%"/></td>
                </tr>
                <tr>
                    <td width="20%" height="22" align="right">报表名称：</td>
                    <td width="30%" height="22"><html:text property="reportName" styleId="reportName" style="width:100%"/>
                </tr>
                <tr>
                    <td width="20%" height="88" align="right">报表说明：</td>
                    <td width="30%" height="88"><html:textarea property="reportDesc" styleId="reportDesc" style="width:100%;height:100%"/>
                </tr>
                <tr>
                    <td width="20%" height="22" align="right">报表类型：</td>
                    <td width="30%" height="22"><html:select property="reportType" styleId="reportType" style="width:100%" onchange="do_ReportTypeChange(this)"><bean:write name="reportDefineFrm" property="reportTypeOptions" filter="false"/></html:select></td>
                </tr>
                <tr>
                    <td width="20%" height="22" align="right">模型名称：</td>
                    <td width="30%" height="22"><html:select property="modelId" styleId="modelId" style="width:100%"><bean:write name="reportDefineFrm" property="modelOptions" filter="false"/></html:select> </td>
                </tr>
                <tr id="displayTypeTR">
                    <td width="20%" height="22" align="right">显示类型：</td>
                    <td width="30%" height="22"><bean:write name="reportDefineFrm" property="displayTypeRadio" filter="false"/></td>
                </tr>
                <tr id="pageSizeTR" style="display:none">
                    <td width="20%" height="22" align="right">每页记录数：</td>
                    <td width="30%" height="22"><html:text property="pageSize" styleId="pageSize" style="width:100%"/> </td>
                </tr>
                <tr id="countPagesTR" style="display:none">
                    <td width="20%" height="22" align="right">计算总记录数：</td>
                    <td width="30%" height="22"><bean:write name="reportDefineFrm" property="countPagesRadio" filter="false"/></td>
                </tr>
                <tr>
                    <td width="20%" height="22" align="right">支持钻探：</td>
                    <td width="30%" height="22"><bean:write name="reportDefineFrm" property="supportDrillRadio" filter="false"/></td>
                </tr>
                <tr id="targetTypeTR" style="display:none">
                    <td width="20%" height="22" align="right">目标类型：</td>
                    <td width="30%" height="22"><bean:write name="reportDefineFrm" property="drillTypeRadio" filter="false"/></td>
                </tr>
                <tr id="targetReportTR" style="display:none">
                    <td width="20%" height="22" align="right">目标报表：</td>
                    <td width="30%" height="22"><html:select property="drillDownReport" styleId="drillDownReport" style="width:100%"><bean:write name="reportDefineFrm" property="drillDownReportOptions" filter="false"/></html:select></td>
                </tr>
                <tr id="targetURLTR" style="display:none">
                    <td width="20%" height="22" align="right">目标URL：</td>
                    <td width="30%" height="22"><html:text property="drillDownUrl" styleId="drillDownUrl" style="width:100%"/></td>
                </tr>
                <tr id="targetParaTR" style="display:none">
                    <td width="20%" height="22" align="right">传递参数：</td>
                    <td width="30%" height="22"><html:text property="drillDownParameters" styleId="drillDownParameters" style="width:100%"/> </td>
                </tr>
                <tr style="diaplay:none">
                    <td width="20%" height="22" align="right">显示宽度：</td>
                    <td width="30%" height="22"><html:text property="reportWidth" styleId="reportWidth" style="width:100%"/></td>
                </tr>
                <tr id="sumPositionTR" style="display:none">
                    <td width="20%" height="22" align="right">合计位置：</td>
                    <td width="30%" height="22"><html:select property="sumPosition" styleId="sumPosition" style="width:100%"><bean:write name="reportDefineFrm" property="sumPositionOptions" filter="false"/></html:select></td>
                </tr>
                <tr id="subSummaryTR" style="display:none">
                    <td width="20%" height="22" align="right">支持小计：</td>
                    <td width="30%" height="22"><bean:write name="reportDefineFrm" property="supportSubSummaryRadio" filter="false"/></td>
                </tr>
                <tr style="diaplay:none">
                    <td width="20%" height="22" align="right">是否有效：</td>
                    <td width="30%" height="22"><bean:write name="reportDefineFrm" property="enabledRadio" filter="false"/></td>
                </tr>
            </table>
		</td>
		<td width="15%">　</td>
	</tr>
</table>
    <div id="pageNaviDiv" align="right"><a href="" onclick="do_Next();return false;">下一步</a> </div>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="reportId" styleId="reportId"/>
    <html:hidden property="dataSaved" styleId="dataSaved"/>
    </html:form>
</body>
</html>
<script type="text/javascript">

function do_initPage(){
    do_CtrlParentBtn();
    do_ControlPage();
    do_SetPageWidth();
}

function do_CtrlParentBtn(){
    var reportId = document.getElementById("reportId").value;
    if(reportId != ""){
        window.parent.ShowSinoButton(3);
        window.parent.ShowSinoButton(5);
    }
}

function do_ControlPage(){
    var supportDrill = getCheckedRadio("supportDrillDown");
    do_SupportDrillChange(supportDrill);
    var reportType = document.getElementById("reportType");
    do_ReportTypeChange(reportType);
}

function do_ReportTypeChange(obj){
    var reportType = obj.value;
    if(reportType == ""){
        return;
    }
    do_ControlDisplay(reportType);
    do_ControlSumPosition(reportType);
    do_ControlSubSummary(reportType);
}

function do_ControlDisplay(reportType){
    if(reportType != "<%=RDSDictionaryList.REPORT_TYPE_LIST%>" && reportType != ""){
        makeRadioChecked("displayType", "<%=RDSDictionaryList.DISPLAY_TYPE_ALL%>");
        disableRadio("displayType");
    } else {
        enableRadio("displayType");
    }
}

function do_DisplayTypeChange(obj){
    if(obj == null){
        return;
    }
    if(obj.checked){
        var objValue = obj.value;
        if(objValue == "<%=RDSDictionaryList.DISPLAY_TYPE_PAGE%>"){
            document.getElementById("pageSizeTR").style.display = "block";
            document.getElementById("countPagesTR").style.display = "block";
        } else if(objValue == "<%=RDSDictionaryList.DISPLAY_TYPE_ALL%>"){
            document.getElementById("pageSizeTR").style.display = "none";
            document.getElementById("countPagesTR").style.display = "none";
            document.getElementById("pageSize").value = "";
            clearRadioChecked("countPages");
        }
    }
}

function do_ControlSumPosition(reportType){
    var sumTR = document.getElementById("sumPositionTR");
    if(reportType != "<%=RDSDictionaryList.REPORT_TYPE_LIST%>" && reportType != ""){
        sumTR.style.display = "block";
    } else {
        sumTR.style.display = "none";
    }
}

function do_ControlSubSummary(reportType){
    var sumTR = document.getElementById("subSummaryTR");
    if(reportType != "<%=RDSDictionaryList.REPORT_TYPE_LIST%>" && reportType != ""){
        sumTR.style.display = "block";
    } else {
        sumTR.style.display = "none";
    }
}

function do_SupportDrillChange(obj){
    if(obj == null){
        return;
    }    
    var objValue = obj.value;
    if(objValue == "Y"){
        document.getElementById("targetTypeTR").style.display = "block";
        document.getElementById("targetParaTR").style.display = "block";
        var drillDownType = getCheckedRadio("drillDownType");
        do_DrillTypeChange(drillDownType);
    } else {
        document.getElementById("targetTypeTR").style.display = "none";
        document.getElementById("targetReportTR").style.display = "none";
        document.getElementById("targetURLTR").style.display = "none";
        document.getElementById("targetParaTR").style.display = "none";
    }
}

function do_DrillTypeChange(obj){
    if(obj == null){
        return;
    }
    var objValue = obj.value;
    if(objValue == "<%=RDSDictionaryList.DRILL_TYPE_REPORT%>"){
        document.getElementById("targetReportTR").style.display = "block";
        document.getElementById("targetURLTR").style.display = "none";
    } else if(objValue == "<%=RDSDictionaryList.DRILL_TYPE_URL%>"){
        document.getElementById("targetReportTR").style.display = "none";
        document.getElementById("targetURLTR").style.display = "block";
    }
}

function do_Check_Save(){
    var isValid = false;
    var fieldNames = "reportCode;reportName;reportType;modelId;supportDrillDown;enabled";
    var fieldDescs = "报表代码;报表名称;报表类型;模型;是否支持钻探;是否有效";
    isValid = formValidate(fieldNames, fieldDescs, EMPTY_VALIDATE, "");
    if(isValid){
        var supportDrillDown = getCheckedRadio("supportDrillDown");
        var drillDownType = document.getElementById("drillDownType").value;
        if(supportDrillDown == "Y"){
            if(drillDownType == "<%=RDSDictionaryList.DRILL_TYPE_REPORT%>"){
                fieldNames = "drillDownReport;drillDownParameters";
                fieldDescs = "目标报表;钻探参数";
            } else {
                fieldNames = "drillDownUrl;drillDownParameters";
                fieldDescs = "目标URL;钻探参数";
            }
            isValid = formValidate(fieldNames, fieldDescs, EMPTY_VALIDATE, "");
        }
        if(isValid){
            enableRadio("displayType");
        }
    }
    return isValid;
}


function do_Next(){
    var reportId = document.getElementById("reportId").value;
    if(reportId == ""){
        alert("报表尚未保存过，不能前进到下一步。");
        return;
    }
    var actionURL = "/rds/reportViewProcess.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&reportId=" + reportId;
    var frm = window.parent.frames["reportViewFrm"];
    frm.location = actionURL;
    setTimeout(do_NextTab, 3000);
}

function do_NextTab(){
    window.parent.tabBox.doclick(6, "");    
}
</script>