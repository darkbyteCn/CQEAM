<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.dto.DTOSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.rds.share.form.ReportDefineFrm" %>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>已定义报表查询</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/rds/WebLibary/css/rds.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/rds/WebLibary/css/rds.css">
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/Constant.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/util/CommonUtil.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/toolbar/SinoToolBar.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/toolbar/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/FormProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/CheckboxProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/form/RadioProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/table/TableProcess.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/appEntry/AppStandard.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/appEntry/ActionButton.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<html:form action="/rds/reportSearch" method="post">
     <script type="text/javascript">
        var title = "已定义报表查询";
        printTitleBar(title);
        ArrActions[0][0] = true;
        ArrActions[1] = new Array(true, "运行", "application_xp.png", "运行", "do_RunReport");
        printToolBar();
    </script>
    <table  border="0" width="99%" id="table1" class="queryHeadBg">
        <tr style="height:22px">
            <td width="10%" align="right">报表类型：</td>
            <td width="15%"><html:select property="reportType" style="width:100%"><bean:write name="reportDefineFrm" property="reportTypeOptions" filter="false"/></html:select></td>
            <td width="10%" align="right">模型：</td>
            <td width="15%"><html:select property="modelId" style="width:100%"><bean:write name="reportDefineFrm" property="modelOptions" filter="false"/></html:select></td>
            <td width="10%" align="right">报表代码：</td>
            <td width="15%"><html:text property="reportCode" styleId="reportCode" style="width:100%"/></td>
            <td width="10%" align="right">报表名称：</td>
            <td width="15%"><html:text property="reportName" styleId="reportName" style="width:100%"/></td>
        </tr>
    </table>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="reportIds" styleId="reportIds"/>
    </html:form>
<%
    DTOSet dtos = (DTOSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (dtos != null && !dtos.isEmpty()) {
 %>
    <div id="headDiv" style="overflow-x:hidden;overflow-y:scroll;position:absolute;top:73px;left:1px;width:100%">
        <table id="headTable" border="1" width="100%">
            <tr class="headerTR" style="text-align:center">
                <td width="3%">&nbsp;</td>
                <td width="15%">报表代码</td>
                <td width="22%">报表名称</td>

                <td width="15%">模型</td>
                <td width="15%">报表类型</td>
                <td width="15%">是否有效</td>
                <td width="15%">创建日期</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:60%;width:100%;position:absolute;top:98px;left:1px" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" border="1" class="gridTable" bordercolor="#666666" id="dataTable" style="TABLE-LAYOUT:fixed;word-break:break-all">
<%
        for (int i = 0; i < dtos.getSize(); i++) {
            ReportDefineFrm frm = (ReportDefineFrm)dtos.getDTO(i);
%>
            <tr onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'" class="dataTR">
                <td width="3%" align="center"><input type="radio" name="reportCodeRadio" value="<%=frm.getReportCode()%>"></td>
                <td width="15%"><input type="text" readonly class="tableInput1" value="<%=frm.getReportCode()%>"></td>
                <td width="22%"><input type="text" readonly class="tableInput1" value="<%=frm.getReportName()%>"></td>

                <td width="15%"><input type="text" readonly class="tableInput1" value="<%=frm.getModelName()%>"></td>
                <td width="15%"><input type="text" readonly class="tableInput2" value="<%=frm.getReportTypeName()%>"></td>
                <td width="15%"><input type="text" readonly class="tableInput2" value="<%=frm.getEnabledName()%>"></td>
                <td width="15%"><input type="text" readonly class="tableInput2" value="<%=frm.getCreationDate()%>"></td>
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
</body>
</html>
<script type="text/javascript">
function do_RunReport(){
    var reportCode = getRadioValue("reportCodeRadio");
    if(reportCode == ""){
        alert("请选择报表。");
        return;
    }
    var pageConfig = new DataPageConfig();
    pageConfig.setActionURL("/rds/reportRun.do");
    pageConfig.setAct("");
    pageConfig.setWindowName(reportCode);
    var userParameter = "reportCode=" + reportCode;
    pageConfig.setParameters(userParameter);
    do_ProcessDataPage(pageConfig);
}
</script>
