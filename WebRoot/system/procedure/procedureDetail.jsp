<%@ page contentType = "text/html; charset=GBK" language = "java" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.ams.system.user.dto.SfRoleDTO" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.ams.system.trust.dto.AmsMaintainCompanyDTO" %>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import = "com.sino.ams.system.trust.dto.AmsMaintainPeopleDTO" %>
<%@ page import = "com.sino.ams.system.procedure.servlet.SfProcedureDefServlet" %>
<%@ page import = "com.sino.ams.system.procedure.dto.SfProcedureDefDTO" %>


<head>
    <meta http-equiv = "Content-Language" content = "zh-cn">
    <meta http-equiv = "Content-Type" content = "text/html; charset=gb2312" />
    <title>流程详细信息</title>
    <link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
    <script type = "text/javascript" src = "/WebLibary/js/Constant.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/CommonUtil.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/FormValidate.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/FormProcess.js"></script>
    <script type = "text/javascript" src = "/WebLibary/js/SelectProcess.js"></script>

</head>

<body onload = "do_Select();">
<jsp:include page = "/message/MessageProcess" flush = "true" />
<%
    SfProcedureDefDTO sfProcedure = (SfProcedureDefDTO) request.getAttribute(WebAttrConstant.PORCDURE_ATTR);
    String disabledOption = request.getAttribute("IsDisabledOption").toString();
    RequestParser parser = new RequestParser();
    parser.transData(request);

%>
<form name = "mainFrm" method = "POST">

    <table border = "0" width = "100%" id = "table1">
        <input type = "hidden" name = "act" value = "<%=parser.getParameter("act")%>">
        <tr>
            <td width = "25%" align = "right" height = "22">流程名称：</td>
            <td width = "50%" align = "left" height = "22" colspan = "3"><input type = "text" name = "procName" size = "40"
                                                                                style = "width:100%" readonly class = "readonlyInput"
                                                                                value = "<%=sfProcedure.getProcName() %>"></td>
            <td width = "25%" align = "left" height = "22"></td>
        </tr>
        <tr>
            <td width = "25%" align = "right" height = "22">流程描述：</td>
            <td width = "50%" align = "left" height = "22" colspan = "3"><input type = "text" name = "description" size = "40"
                                                                                style = "width:100%" readonly class = "readonlyInput"
                                                                                value = "<%=sfProcedure.getDescription()%>">
            </td>
            <td width = "25%" align = "left" height = "22"></td>
        </tr>
        <tr>
            <td width = "25%" align = "right" height = "22">应用表名称：</td>
            <td width = "50%" align = "left" height = "22" colspan = "3"><input type = "text" name = "appTableName"
                                                                                size = "40"
                                                                                style = "width:100%"
                                                                                value = "<%=sfProcedure.getAppTableName()%>"
                    ></td>
            <td width = "25%" align = "left" height = "22"></td>
        </tr>

        <tr>
            <td width = "25%" align = "right" height = "22">应用表主键：</td>
            <td width = "50%" align = "left" height = "22" colspan = "3"><input type = "text" name = "appPkName" size = "40"
                                                                                style = "width:100%"
                                                                                value = "<%=sfProcedure.getAppPkName()%>"></td>
            <td width = "25%" align = "left" height = "22"></td>
        </tr>
        <tr>
            <td width = "25%" align = "right" height = "22">公司编号：</td>
            <td width = "50%" align = "left" height = "22" colspan = "3"><input type = "text" name = "organizationId" size = "40"
                                                                                style = "width:100%"
                                                                                value = "<%=sfProcedure.getOrganizationId()%>">
            </td>
            <td width = "25%" align = "left" height = "22"></td>
        </tr>
        <tr>
            <td width = "25%" align = "right" height = "22">审批应用的路径：</td>
            <td width = "50%" align = "left" height = "22" colspan = "3"><input type = "text" name = "approvePath" size = "40"
                                                                                style = "width:100%"
                                                                                value = "<%=sfProcedure.getApprovePath()%>"></td>
            <td width = "25%" align = "left" height = "22"></td>
        </tr>
        <tr>
            <td width = "25%" align = "right" height = "22">编辑应用的路径：</td>
            <td width = "50%" align = "left" height = "22" colspan = "3"><input type = "text" name = "editPath" size = "40"
                                                                                style = "width:100%"
                                                                                value = "<%=sfProcedure.getEditPath()%>">
            </td>
            <td width = "25%" align = "left" height = "22"></td>
        </tr>
        <tr>
            <td width = "25%" align = "right" height = "22">查询应用路径：</td>
            <td width = "50%" align = "left" height = "22" colspan = "3"><input type = "text" name = "queryPath" size = "40"
                                                                                style = "width:100%"
                                                                                value = "<%=sfProcedure.getQueryPath()%>">
            </td>
            <td width = "25%" align = "left" height = "22"></td>
        </tr>
        <tr>
            <td width = "25%" align = "right" height = "22">取消应用路径：</td>
            <td width = "50%" align = "left" height = "22" colspan = "3"><input type = "text" name = "cancelPath" size = "40"
                                                                                style = "width:100%"
                                                                                value = "<%=sfProcedure.getCancelPath()%>">
            </td>
            <td width = "25%" align = "left" height = "22"></td>
        </tr>
        <tr>
            <td width = "25%" align = "right" height = "22">是否失效：</td>
            <td width = "50%" align = "left" height = "22" colspan = "3">
                <select name = "disabled" style = "width:100%"><%=disabledOption%></select>
            </td>
            <td width = "25%" align = "left" height = "22"></td>
        </tr>
        <tr>
            <td width = "100%" align = "center" height = "22" colspan = "5">
                <img src = "/images/eam_images/save.jpg" alt = "保存" onClick = "do_SaveMaintainCompany(); return false;">&nbsp;
                <%--  <img src="/images/eam_images/delete.jpg" alt="删除" onClick="do_DeleteMaintainCompany(); return false;">&nbsp;--%>
                <img src = "/images/eam_images/back.jpg" alt = "取消" onClick = "do_Back(); return false;"></td>
        </tr>

    </table>

    <input type = "hidden" name = "procId" value = "<%=sfProcedure.getProcId()%>">
</form>
</body>
</html>
<script>
    function do_SaveMaintainCompany() {
        /*     //输入校验 -- 非空
      var fieldNames = "procName;appTableName;appPkName;organizationId;disabled;editPath;queryPath;cancelPath";
      var fieldLabels = "流程名称;应用表名称;应用表主键;OU组织编号;是否失效;编辑应用的路径;查询应用路径;取消应用路径";
      var validateType = EMPTY_VALIDATE;
      var isValid = formValidate(fieldNames, fieldLabels, validateType);
      if (isValid) {*/
        var action = "<%=WebActionConstant.CREATE_ACTION%>";
        if (mainFrm.procId.value != "") {
            action = "<%=WebActionConstant.UPDATE_ACTION%>";
        }
        mainFrm.act.value = action;
        mainFrm.action = "/servlet/com.sino.ams.system.procedure.servlet.SfProcedureDefServlet";
        mainFrm.submit();

    }

    function do_DeleteMaintainCompany() {

        if (confirm("确认删除该流程吗？继续请点“确定”按钮，否则请点“取消”按钮。")) {
            mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            mainFrm.action = "/servlet/com.sino.ams.system.procedure.servlet.SfProcedureDefServlet";
            mainFrm.submit();
        }
    }


    function do_Back() {

        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.procedure.servlet.SfProcedureDefServlet";
        mainFrm.submit();
    }

    function do_Select() {

    }
</script>