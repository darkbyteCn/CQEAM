<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.flow.dto.UserAgencyDTO" %>
<%@ page import="com.sino.framework.security.bean.SessionUtil" %>
<%--
  created by wwb
  Date: 2006-11-21
  Time: 20:04:46
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>代办人信息</title>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>
<base target="_self">
<script type="text/javascript">
    printTitleBar("代办人信息")
</script>
<%
    String userId = "";
    String agentUserId = "";
    String activeStartDate = "";
    String activeEndDate = "";
    String id = "";
    String deptId = "";
    String note = "";
//    String disableDate = "";
//    String userName = "";
//    String deptName = "";
    String agentUserName = "";
    RequestParser parser = new RequestParser();
    parser.transData(request);
    UserAgencyDTO projectDTO = (UserAgencyDTO) request.getAttribute("SPLIT_DATA_VIEW");
    if (projectDTO != null) {
        agentUserId = projectDTO.getAgentUserId();
        activeStartDate = projectDTO.getActiveStartDate();
        activeEndDate = projectDTO.getActiveEndDate();
        note = projectDTO.getNote();
        agentUserName = projectDTO.getAgentUserName();
        userId = projectDTO.getUserId();
        deptId = projectDTO.getDeptId();
        id = projectDTO.getId();
    }
    SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
    String orgId = userAccount.getOrganizationId();
%>
<body topmargin="0" leftmargin="0">
<form method="post" name="mainFrm">
    <input type="hidden" name="forward">
    <input type="hidden" name="id" value="<%=id%>">
    <input type="hidden" name="userId" value="<%=userId%>">
    <input type="hidden" name="deptId" value="<%=deptId%>">
    <input type="hidden" name="isExist">
    <table width="80%" align="center" border="0">
        <tr>
            <td width="15%" align="right">代办人姓名：</td>
            <td width="35%">
                <input type="text" style="width:85%" class="noEmptyInput" readonly name="agentUserName"
                       value="<%=agentUserName%>"><a href="#" class="linka" style="cursor:'hand'"
                                                     onclick="do_agentUserName_SelectItem();">[…]</a></td>
            <input type="hidden" name="agentUserId" value="<%=agentUserId%>">
        </tr>
        <tr>
            <td align="right">起始日期：</td>
            <td>
                <input type="text" style="width:85%" class="noEmptyInput" readonly name="activeStartDate"
                       value="<%=activeStartDate%>"><a class="linka" style="cursor:'hand'"
                                                       onclick="gfPop.fStartPop(activeStartDate,activeEndDate);"><img
                    border='0' src="/images/calendar.gif"/></a>
            </td>
        </tr>
        <tr>
            <td align="right">截至日期：</td>
            <td>
                <input type="text" style="width:85%" class="noEmptyInput" readonly name="activeEndDate"
                       value="<%=activeEndDate%>"><a class="linka" style="cursor:'hand'"
                                                     onclick="gfPop.fEndPop(activeStartDate,activeEndDate);"><img
                    border='0' src="/images/calendar.gif"/></a>
            </td>
        </tr>
        <tr>
            <td width="15%" align="right">备注：</td>
            <td width="35%"><textarea rows="3" cols="10" style="width:85%" name="note"><%=note%></textarea></td>
        </tr>
        <tr>
            <td align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg" onClick="do_save();" alt="保存代办人信息">&nbsp;&nbsp;&nbsp;
                <%if (!agentUserId.equals("")) {%>
                <img src="/images/eam_images/delete.jpg" onClick="do_delete();" id="delete" alt="失效代办人">&nbsp;&nbsp;&nbsp;
                <%}%>
                <img src="/images/eam_images/cancel.jpg" onClick="do_back();" alt="后退">&nbsp;&nbsp;&nbsp;
            </td>
        </tr>
    </table>

</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>
<script type="text/javascript">
function do_agentUserName_SelectItem() {
    mainFrm.agentUserId.value = '';
    mainFrm.agentUserName.value = '';
    var itemValue = getLookUpValues('<%=LookUpConstant.FLOW_AGENT_USER%>', '48', '33', 'organizationId=<%=orgId%>');
    if (itemValue) {
        var obj = itemValue[0];
        mainFrm.agentUserId.value = obj.userId;
        mainFrm.agentUserName.value = obj.username;
        // mainFrm.deptId.value = arr[3];
    }
}
function do_back() {
    document.mainFrm.forward.value = "back";
    document.mainFrm.action = "/servlet/com.sino.flow.servlet.UserAgencyServlet";
    document.mainFrm.submit();
    window.close();
}
function do_delete() {
    document.mainFrm.forward.value = "disable";
    document.mainFrm.action = "/servlet/com.sino.flow.servlet.UserAgencyServlet";
    document.mainFrm.submit();
}
function do_save() {
    var now = new Date();
    var activeStartDate = document.mainFrm.activeStartDate.value.split('-');
    var date = new Date(activeStartDate[0], activeStartDate[1] - 1, activeStartDate[2], 23, 59, 59);
    //    alert(now.getTime());
    //    alert(date.getTime())
    if (now.getTime() >= date.getTime()) {
        alert("起始日期必须大于当前日期！");
        return;
    }
    if (document.mainFrm.agentUserName.value == '') {
        alert("代办人名称不能为空！");
        return;
    }
    if (document.mainFrm.activeStartDate.value == '') {
        alert("起始日期不能为空！");
        return;
    }
    if (document.mainFrm.activeEndDate.value == '') {
        alert("截至日期不能为空！");
        return;
    }
    if (document.mainFrm.userId.value) {
        document.mainFrm.forward.value = "save";
        document.mainFrm.action = "/servlet/com.sino.flow.servlet.UserAgencyServlet";
        document.mainFrm.submit();
    } else {
        document.mainFrm.forward.value = "Insert_New";
        document.mainFrm.action = "/servlet/com.sino.flow.servlet.UserAgencyServlet";
        document.mainFrm.submit();
    }
}
//var xmlHttp;
//function do_verify() {
//    var url = "";
//    var name = document.mainFrm.name.value;
////    alert(document.mainFrm.segment1.value);
//    createXMLHttpRequest();
//    if (document.mainFrm.segment1.value) {
//        url = "/servlet/com.sino.ies.inv.maintenance.servlet.ProjectMaintainServlet?forward=doVerify&segment1=" + document.mainFrm.segment1.value + "&name=" + name;
//    } else {
//        url = "/servlet/com.sino.ies.inv.maintenance.servlet.ProjectMaintainServlet?forward=doVerify&name=" + name;
//    }
////    alert(url)
//    xmlHttp.onreadystatechange = handleReadyStateChange;
//    xmlHttp.open("post", url, true);
//    xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
//    xmlHttp.send(null);
//}
//function createXMLHttpRequest() {
//    try {
//        xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
//    } catch(e) {
//        try {
//            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
//        } catch(e) {
//            try {
//                xmlHttp = new XMLHttpRequest();
//            } catch(e) {
//                alert("创建XMLHttpRequest对象失败！");
//            }
//        }
//    }
//}
//function handleReadyStateChange() {
//    if (xmlHttp.readyState == 4) {
//        if (xmlHttp.status == 200) {
//            if (xmlHttp.responseText == 'Y') {
//                document.mainFrm.isExist.value = 'Y';
//                document.getElementById("flag").style.display = "block"
//                document.mainFrm.name.focus();
//            } else {
//                document.mainFrm.isExist.value = 'N';
//                document.getElementById("flag").style.display = "none"
//            }
//        } else {
//            alert(xmlHttp.status);
//        }
//    }
//}
//function do_init() {
//    var sourceFrom = document.mainFrm.sourceFrom.value;
//    if (sourceFrom == 'MIS') {
//        var nameObj = document.mainFrm.name;
//        var segment1Obj = document.mainFrm.segment1;
//        var descriptionObj = document.mainFrm.description;
//        var startDateObj = document.mainFrm.startDate;
//        var completionDateObj = document.mainFrm.completionDate;
//        var projectType2Obj = document.mainFrm.projectType2;
//        nameObj.readOnly = true;
//        nameObj.className = "readonlyInput";
//        nameObj.onblur = "";
//        descriptionObj.readOnly = true;
//        descriptionObj.className = "readonlyInput";
//        startDateObj.readOnly = true;
//        startDateObj.className = "readonlyInput";
//        completionDateObj.readOnly = true;
//        completionDateObj.className = "readonlyInput";
//        projectType2Obj.disabled = true;
//        projectType2Obj.className = "readonlyInput";
//        var img = document.getElementsByTagName("img");
//        img[1].style.display = "none";
//        img[2].style.display = "none";
//        img[3].style.display = "none";
//        img[4].style.display = "none";
//    }
//}
</script>