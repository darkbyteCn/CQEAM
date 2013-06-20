<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.nm.ams.instrument.dto.AmsInstrumentEamYbChkTaskDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page contentType="text/html;charset=GB2312" language="java" %>
<%--
  created by YSB
  Date: 2009-01-23
  Time: 14:26:32
--%>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<HTML>
<head>
    <title>仪器仪表检修任务详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script language="javascript" src="/WebLibary/js/ajax.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>

<body TEXT="000000" BGCOLOR="FFFFFF" leftmargin=0 topmargin=0 class="STYLE1"
      onload="window.focus();">
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    AmsInstrumentEamYbChkTaskDTO taskDTO = (AmsInstrumentEamYbChkTaskDTO)request.getAttribute(QueryConstant.QUERY_DTO);//针对Servlet里的dto.setXXX()方法写的
%>

<form name="mainFrm" method="post">
    <jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
    <table border="0" width="100%" style="position:absolute;top:20px">
        <tr>
            <td width="25%" align="right" height="22">任务代码：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="taskId" size="40" style="width:100%"
                       value="<%=taskDTO.getTaskId()%>" readonly>
            </td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">任务名称：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="taskName" size="40" class="noemptyInput" style="width:100%"
                       value="<%=taskDTO.getTaskName()%>" onblur="checkTaskName();">
            </td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="remark" size="40" style="width:100%"
                       value="<%=taskDTO.getRemark()%>" readonly></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="25%" align="right" height="22">公&nbsp;&nbsp;&nbsp;&nbsp;司：</td>
            <td width="50%" align="left" height="22" colspan="3">
                <input type="text" name="company" size="40" style="width:100%"
                       value="<%=taskDTO.getCompany()%>" readonly></td>
            <td width="25%" align="left" height="22"></td>
        </tr>
        <tr>
            <td width="100%" align="center" height="22" colspan="5">
                <img src="/images/button/save.gif" alt="保存" onclick="do_submit()">&nbsp;
                <img src="/images/button/back.gif" alt="取消" onclick="do_close();"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
    <input type="hidden" name="nameExist" id="nameExist" value="N">
</form>
</BODY>

<script type="text/javascript">
function do_submit() {
    var fieldNames = "taskId;taskName;remark;company";
    var fieldLabels = "任务代码;任务名称;备注;公司";

    if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
        if (mainFrm.taskName.value != "") {
            mainFrm.act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
            var nameExist = document.getElementById("nameExist").value;
	        if (nameExist == "N") {
	           mainFrm.action = "/servlet/com.sino.nm.ams.instrument.servlet.AmsInstrumentEamYbChkTaskServlet";
	           mainFrm.submit();
	        } else {
	            alert("该任务名称已存在");
	        }
        } else {
        	alert("任务名称不允许为空!");
        }
    }
}

function do_close() {
    window.close();
    opener.location.href = "/servlet/com.sino.nm.ams.instrument.servlet.AmsInstrumentEamYbChkTaskServlet?act=<%=WebActionConstant.QUERY_ACTION%>";
}

// ------------------------------------------------------------------------
var xmlHttp;

//-- checkTaskName
function checkTaskName() {

    var url = "";
    var objName = document.getElementById("taskName").value;
    xmlHttp = createXMLHttpRequest();
    if (objName != "") {
        url = "/servlet/com.sino.nm.ams.instrument.servlet.AmsInstrumentEamYbChkTaskServlet?act=CHECK_NAME_ACTION&taskName=" + objName;
        xmlHttp.onreadystatechange = handleReadyStateChange;
        xmlHttp.open("post", url, true);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.send(null);
    }
}

//checkName
function handleReadyStateChange() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var resText = xmlHttp.responseText;
            if (resText == '<%=WebAttrConstant.TASK_NAME_EXIST%>') {
                document.getElementById("nameExist").value = "Y";
                document.getElementById("tipMsg").style.visibility = "visible";
            } else if (resText == '<%=WebAttrConstant.TASK_NAME_NOT_EXIST%>') {
                document.getElementById("nameExist").value = "N";
                document.getElementById("tipMsg").style.visibility = "hidden";
            }
        } else {
            alert(xmlHttp.status);
        }
    }
}
</SCRIPT>
</HTML>