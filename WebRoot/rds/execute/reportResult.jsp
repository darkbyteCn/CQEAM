<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.rds.share.constant.RDSDictionaryList" %>

<%@ include file="/rds/public/listPageInclude.jsp"%>


<base target="_self">
<html>
<head>
    <title><bean:write name="REPORT_RESULT" property="reportName"/></title>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/appEntry/LookUp.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/rds/WebLibary/js/report/ReportProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0" onload="do_initPage()" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="/message/MessageProcess"/>
<html:form action="/rds/reportRun" method="post">
    <script type="text/javascript">
        var title = "<bean:write name="REPORT_RESULT" property="reportName"/>";
        printTitleBar(title);
        ArrActions[0][0] = true;
        ArrActions[1][0] = true;
        ArrActions[15][0] = true;
        var fromLookUp = "<bean:write name="REPORT_RESULT" property="fromLookUp"/>";
        if(fromLookUp == "Y"){
            ArrActions[11][0] = true;
        }
        printToolBar();
    </script>
    <bean:write name="REPORT_RESULT" property="parameterHTML" filter="false"/>
    <html:hidden property="act" styleId="act"/>
    <html:hidden property="reportId" styleId="reportId"/>
    <html:hidden property="reportCode" styleId="reportCode"/>
    <html:hidden property="lookUpId" styleId="lookUpId"/>
    <html:hidden property="lookUpCode" styleId="lookUpCode"/>
    <html:hidden property="preview" styleId="preview"/>
    <html:hidden property="clientWidth" styleId="clientWidth"/>
    <input type="hidden" name="returnField" id="returnField" value="">
    <bean:write name="REPORT_RESULT" property="hiddenHTML" filter="false"/>
</html:form>
     <bean:write name="REPORT_RESULT" property="reportDataHTML" filter="false"/>
</body>
</html>
<iframe id="downFrm" name="downFrm" height="0px" width="0px"></iframe>
<iframe width=174 height=189 name="gToday:normal:calendar.js"
			id="gToday:normal:calendar.js" src="<%=contextPath%>/rds/WebLibary/js/calendar/DateHTML.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; left: -500px; top: 0;">
</iframe>

<script type="text/javascript">
reportType = "<bean:write name="REPORT_RESULT" property="reportType"/>";
supportSubSummary = "<bean:write name="REPORT_RESULT" property="supportSubSummary"/>";
reportTypeList = "<%=RDSDictionaryList.REPORT_TYPE_LIST%>";
leftCategorySize = Number("<bean:write name="REPORT_RESULT" property="leftCategorySize"/>");
drillStartColumn = Number("<bean:write name="REPORT_RESULT" property="drillStartColumn"/>");
sumPosition = String("<bean:write name="REPORT_RESULT" property="sumPosition"/>");
sumPositionBottom = "<%=RDSDictionaryList.POSITION_BOTTOM%>";
viewLocationAbove = "<%=RDSDictionaryList.VIEW_LOCATION_ABOVE%>";
viewLocationLeft = "<%=RDSDictionaryList.VIEW_LOCATION_LEFT%>";
</script>