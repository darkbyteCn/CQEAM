<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.system.project.dto.EtsPaProjectsAllDTO" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.calen.SimpleDate" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  created by Zyun
  Date: 2007-09-26
  Time: 8:23:30
--%>

<html>
<head>
    <title>项目信息维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/FormValidate.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("项目信息维护")
</script>
<%
    EtsPaProjectsAllDTO proDTO = (EtsPaProjectsAllDTO) request.getAttribute(WebAttrConstant.ETS_PA_PROJECTS_ALL_DTO);
    String projectType = (String) request.getAttribute(WebAttrConstant.PROJECT_TYPE_OPTION);

%>

<body leftmargin="0">
<jsp:include page="/message/MessageProcess"/>
<form action="" method="post" name="mainFrm">
    <input type="hidden" name="act">
    <input type="hidden" name="isExist">
    <input type="hidden" name="projectId" value="<%=proDTO.getProjectId()%>">
    <input type="hidden" name="source" value="<%=proDTO.getSource()%>">
    <table width="50%" align="center">
        <tr>
            <td width="15%" align="right">项目编号：</td>
            <td width="35%"><input class="input_style1" type="text" style="width:90%" name="segment1" value="<%=proDTO.getSegment1()%>"> 
            &nbsp;<font style="color: red">*</font>
             </td>
        </tr>
        <tr>
            <td align="right">项目名称：</td>
            <td>
            <input type="text" class="input_style1" style="width:90%" name="name" value="<%=proDTO.getName()%>" maxlength="35" class="noEmptyInput">
            &nbsp;<font style="color: red">*</font>
            </td>
        </tr>

        <tr>
            <td align="right">项目类型：</td>
            <td width="25%">
             <select class="select_style1" name="projectType" style="width:90%"><%=projectType%></select>
             &nbsp;<font style="color: red">*</font>
            </td>
        </tr>

        <tr>
            <td align="right">开始日期：</td>
            <td>
                <input type="text" onclick="gfPop.fStartPop(startDate,startDate);" readonly class="input_style1" style="width:90%" name="startDate"  value="<%=proDTO.getStartDate1()%>" >
                &nbsp;<font style="color: red">*</font>
            </td>
        </tr>
        <tr>
            <td align="right">截至日期：</td>
            <td>
                <input type="text" onclick="gfPop.fEndPop(startDate,completionDate);" readonly class="input_style1" style="width:90%"  name="completionDate" value="<%=proDTO.getCompletionDate1()%>">
                 &nbsp;<font style="color: red">*</font>
            </td>
        </tr>
        <tr>
            <td align="center" height="22" colspan="3">
                <img src="/images/eam_images/save.jpg" onClick="do_save();">&nbsp;&nbsp;&nbsp;
                <%
                    if (!proDTO.getProjectId().equals("")) {
                %>
                <img src="/images/eam_images/delete.jpg" onClick="do_delete();" id="delete">&nbsp;&nbsp;&nbsp;
                <%
                    }
                %>
                <img src="/images/eam_images/cancel.jpg" onClick="do_back();">&nbsp;&nbsp;&nbsp;
            </td>
        </tr>
    </table>

</form>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_back() {
        mainFrm.name.value = "";
        mainFrm.segment1.value = "";
        mainFrm.action = "/servlet/com.sino.ams.system.project.servlet.EtsPaProjectsAllServlet";
        mainFrm.submit();
    }
    function do_delete() {
        if (confirm("确认删除该项目吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            document.mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            document.mainFrm.action = "/servlet/com.sino.ams.system.project.servlet.EtsPaProjectsAllServlet?projectId" + document.mainFrm.projectId.value;
            document.mainFrm.submit();
        }
    }

    function do_save() {
        var fieldNames = "segment1;name;projectType;startDate;completionDate";
        var fieldLabels = "项目编号;项目名称;项目类型;开始日期;截至日期";
        if (formValidate(fieldNames, fieldLabels, EMPTY_VALIDATE)) {
            with (mainFrm) {
                act.value = "<%=WebActionConstant.CREATE_ACTION%>";
                if (projectId.value != "" && projectId.value > 0 ) {
                    act.value = "<%=WebActionConstant.UPDATE_ACTION%>";
                }
                action = "/servlet/com.sino.ams.system.project.servlet.EtsPaProjectsAllServlet";
                submit();
            }
        }
    }
</script>