<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.plan.dto.AmsWorkPlanDTO" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-9-20
  Time: 11:14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>新增工作计划</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("新增工作计划")
</script>
<body onload="window.focus()">
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    AmsWorkPlanDTO dto = (AmsWorkPlanDTO) request.getAttribute(WebAttrConstant.WORK_PLAN_DTO);
    String action = reqParser.getParameter("act");
%>
<form action="/servlet/com.sino.ams.plan.servlet.AmsWorkPlanServlet" name="mainForm" method="post">
    <table border="0" width="100%">
        <input type="hidden" name="act" value="<%=action%>">
        <input type="hidden" name="createdBy" value="">
        <input type="hidden" name="executeUserId" value="">
        <tr>
            <td align="right">计划名称</td>
            <td><input name="planName" type="text" value="<%=dto.getPlanName()%>" style="width:57%"></td>
        </tr>
        <tr>
            <td align="right">任务描述</td>
            <td><textarea rows="10" cols="63" name="planDesc"><%=dto.getPlanDesc().trim()%>
            </textarea></td>
        </tr>
        <tr>
            <td align="right">执行人</td>
            <td><input type="text" class="noEmptyInput" name="executeUserName" value="<%=dto.getExecuteUser()%>"><a
                    class="linka" style="cursor:'hand'" onclick="do_selectName();">[…]</a>
            </td>
        </tr>
        <tr>
            <td align="right">执行时间</td>
            <td><input type="text" name="executeTime" value="<%=dto.getExecuteTime()%>" style="width:30%"
                       title="点击选择日期" readonly class="input2" onclick="gfPop.fStartPop(executeDate, '')">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fStartPop(executeDate,'')"></td>
        </tr>
        <tr>
            <td align="right"></td>
            <td align="left"><img src="/images/eam_images/save.jpg" alt="保存计划"
                                  onClick="do_savePlan(); return false;">
                <img src="/images/eam_images/cancel.jpg" alt="取消"
                     onclick="do_concel();return false;"></td>
        </tr>
    </table>
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function do_savePlan() {
        document.mainForm.act.value = "<%=WebActionConstant.SAVE_ACTION%>"
        document.mainForm.action = "/servlet/com.sino.ams.plan.servlet.AmsWorkPlanServlet";
        document.mainForm.submit();
    }
    function do_concel() {
        var flag = false;
        window.returnValue = flag;
        window.close();
    }
    function do_selectName() {
        document.mainForm.executeUserName.value = "";
        document.mainForm.executeUserId.value = "";

    }
</script>