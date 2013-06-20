<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%
    String contextPath = request.getContextPath();
    String reportName = (String)request.getAttribute("REPORT_NAME");
    reportName = StrUtil.nullToString(reportName);
    if(reportName.length() == 0){
        reportName = "未设定报表名称";
    }
    String title = "“"+reportName+"”查询";
%>
<html>
<head>
    <title><%=title%></title>
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
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/appEntry/LookUp.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/appEntry/ActionButton.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<html:form action="/rds/reportRun" method="post">
    <script type="text/javascript">
        var title = "<%=title%>";
        printTitleBar(title);
        ArrActions[0][0] = true;
        ArrActions[1][0] = true;
        ArrActions[15][0] = true;
        printToolBar();
    </script>
<%
    out.print(request.getAttribute("PAREMETER_HTML"));
%>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="reportId" styleId="reportId"/>
    <html:hidden property="reportCode" styleId="reportCode"/>
    <html:hidden property="lookUpId" styleId="lookUpId"/>
    <html:hidden property="lookUpCode" styleId="lookUpCode"/>
</html:form>
<%
    out.print(request.getAttribute("REPORT_HTML"));
%>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js"
			id="gToday:normal:calendar.js" src="<%=contextPath%>/rds/WebLibary/js/calendar/DateHTML.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; left: -500px; top: 0;"></iframe>
<script type="text/javascript">
function do_Check_Search() {
    var isValid = true;
    var frm = document.forms["searchParameterFrm"];
    var fields = frm.elements;
    if(fields){
        for(var i = 0; i < fields.length; i++){
            var field = fields[i];
            var required = field.getAttribute("required");
            if(required == null){
                continue;
            }
            if(required == "true"){
                var fieldValue = field.value;
                if(fieldValue == ""){
                    var fieldLabel = field.getAttribute("fieldLabel");
                    alert("“"+fieldLabel+"”不允许为空,请输入查询条件。");
                    isValid = false;
                    field.focus();
                    break;
                }
            }
        }
    }
    return isValid;
}

function do_CloseConfig() {
    var cfg = new CloseConfig();
    cfg.setEditPage(false);
    cfg.setRefreshOpener(false);
    return cfg;
}

var paraObj = null;

function configLookup(lookUpId){
    paraObj = event.srcElement;
    var lookupConfig = new LookupConfig();
    lookupConfig.setLookUpId(lookUpId);
    lookupConfig.setProcessor(do_ProcessReturnValue);
}

function do_ProcessReturnValue(returnValue){
    paraObj.value = returnValue;
}
</script>
