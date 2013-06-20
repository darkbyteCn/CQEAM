<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>

<%@ page import="com.sino.rds.share.form.ReportDefineFrm" %>
<html>
<head>
    <title>报表定义维护</title>
    <script type="text/javascript" src="/rds/WebLibary/js/toolbar/dynlayer.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_initPage();" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<html:form action="/rds/reportDefineAction" method="post">

    <table  border="0" width="100%" id="table1" class="queryHeadBg">
        <tr style="height:22px">
            <td width="7%" align="right">报表类型：</td>
            <td width="13%"><html:select property="reportType" style="width:100%"><bean:write name="reportDefineFrm" property="reportTypeOptions" filter="false"/></html:select></td>
            <td width="7%" align="right">模型：</td>
            <td width="13%"><html:select property="modelId" style="width:100%"><bean:write name="reportDefineFrm" property="modelOptions" filter="false"/></html:select></td>
            <td width="7%" align="right">是否有效：</td>
            <td width="13%"><bean:write name="reportDefineFrm" property="enabledRadio" filter="false"/></td>
            <td width="7%" align="right">报表代码：</td>
            <td width="13%"><html:text property="reportCode" styleId="reportCode" style="width:100%"/></td>
            <td width="7%" align="right">报表名称：</td>
            <td width="13%"><html:text property="reportName" styleId="reportName" style="width:100%"/></td>
        </tr>
    </table>
    <html:hidden property="act"/>
    <html:hidden property="reportIds" styleId="reportIds"/>
    </html:form>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:23px;left:1px;width:100%">
        <table id="headTable" border="1" width="120%">
            <tr class="headerTR" style="text-align:center">
                <td width="2%"><input type="checkbox" name="ctrlBox" onclick="checkAll(this.name, 'subCheck')"></td>
                <td width="9%">报表代码</td>
                <td width="12%">报表名称</td>

                <td width="12%">模型</td>
                <td width="12%">报表类型</td>
                <td width="8%">显示类型</td>

                <td width="8%">每页记录数</td>
                <td width="9%">是否计算总页数</td>
                <td width="6%">钻探标志</td>

                <td width="6%">目标类型</td>
                <td width="6%">是否有效</td>
                <td width="8%">创建日期</td>
            </tr>
        </table>
    </div>
<%
    DTOSet dtos = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (dtos != null && !dtos.isEmpty()) {
 %>

    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:46px;left:1px" align="left"  onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="120%" class="gridTable" border="1" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        for (int i = 0; i < dtos.getSize(); i++) {
            ReportDefineFrm frm = (ReportDefineFrm)dtos.getDTO(i);
%>
            <tr id="<%=frm.getReportId()%>" style="height:23px;cursor:pointer" title="点击选中该行，可在其它选项卡察看“<%=frm.getReportName()%>”相关信息" onclick="do_ResponseReportChange(this)" >
                <td width="2%" align="center"><input type="checkbox" name="subCheck" value="<%=frm.getReportId()%>"></td>
                <td width="9%"><input type="text" readonly class="tableInput1" value="<%=frm.getReportCode()%>"></td>
                <td width="12%"><input type="text" readonly class="tableInput1" value="<%=frm.getReportName()%>"></td>

                <td width="12%"><input type="text" readonly class="tableInput1" value="<%=frm.getModelName()%>"></td>
                <td width="12%"><input type="text" readonly class="tableInput2" value="<%=frm.getReportTypeName()%>"></td>
                <td width="8%"><input type="text" readonly class="tableInput2" value="<%=frm.getDisplayTypeName()%>"></td>

                <td width="8%"><input type="text" readonly class="tableInput3" value="<%=frm.getPageSize()%>"></td>
                <td width="9%"><input type="text" readonly class="tableInput2" value="<%=frm.getCountPagesName()%>"></td>
                <td width="6%"><input type="text" readonly class="tableInput1" value="<%=frm.getSupportDrillDownName()%>"></td>

                <td width="6%"><input type="text" readonly class="tableInput1" value="<%=frm.getDrillDownTypeName()%>"></td>
                <td width="6%"><input type="text" readonly class="tableInput2" value="<%=frm.getEnabledName()%>"></td>
                <td width="8%"><input type="text" readonly class="tableInput2" value="<%=frm.getCreationDate()%>"></td>
            </tr>
<%
        }
%>
        </table>
       </div>
<%
    }
%>
<div id="pageNaviDiv" style="position:absolute;top:596px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
</html>
<script type="text/javascript">

function do_initPage() {
    do_SetPageWidth();
}



var checkedColor = "#FFFF99";

function do_SetTrCheckedColor(tr){
    do_SetCellInputColor(tr, checkedColor);
    var tab = document.getElementById("dataTable");
    var rows = tab.rows;
    for(var i = 0; i < rows.length; i++){
        var row = rows[i];
        if(row.id == tr.id){
            continue;
        }
        do_SetCellInputColor(row, "#FFFFFF");
    }
}

function do_SetCellInputColor(tr, color){
    var cells = tr.cells;
    var cellCount = cells.length;
    for(var i = 0; i < cellCount; i++){
        var cell = cells[i];
        var nodes = cell.childNodes;
        if(nodes[0].tagName == "INPUT"){
            nodes[0].style.backgroundColor = color;
        }
    }
}

function do_ProcessReportDataURL(tr){
    var actionURL = "/rds/reportDefineAction.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&reportId=" + tr.id;
    var frm = window.parent.frames["reportDataFrm"];
    frm.location = actionURL;
}

function do_ProcessReportViewURL(tr){
    var actionURL = "/rds/reportViewProcess.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&reportId=" + tr.id;
    var frm = window.parent.frames["reportViewFrm"];
    frm.location = actionURL;
}

function do_ProcessReportParameterURL(tr){
    var actionURL = "/rds/reportParameterProcess.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&reportId=" + tr.id;
    var frm = window.parent.frames["reportParameterFrm"];
    frm.location = actionURL;
}

function do_ProcessReportCategoryURL(tr){
    var actionURL = "/rds/reportCategoryProcess.do";
    actionURL += "?act=DETAIL_ACTION";
    actionURL += "&reportId=" + tr.id;
    var frm = window.parent.frames["reportCategoryFrm"];
    frm.location = actionURL;
}

function do_ResponseReportChange(tr){
    var obj = event.srcElement;
    while(obj.tagName != "TD"){
        obj = obj.parentNode;
    }
    if(obj.tagName == "TD"){
        if(obj.cellIndex == 0){
            return;
        }
    }
    do_SetTrCheckedColor(tr);
    do_ProcessReportDataURL(tr);
    do_ProcessReportViewURL(tr);
    do_ProcessReportParameterURL(tr);
    do_ProcessReportCategoryURL(tr);
    setTimeout(do_NextTab, 2000);
}

function do_NextTab(){
    window.parent.tabBox.doclick(5, "");
}

function do_CopyReport(){
    var checkedCount = getCheckedBoxCount("subCheck");
    if(checkedCount == 0){
        alert("未选中待复制的报表");
        return;
    }
    var checkedValues = getCheckBoxValue("subCheck", ",");
    var frm = document.forms[0];
    frm.act.value = "COPY_ACTION";
    frm.reportIds.value = checkedValues;
    frm.submit();
}
</script>
