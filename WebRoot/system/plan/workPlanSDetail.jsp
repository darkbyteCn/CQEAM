<%@ page import="com.sino.ams.plan.dto.AmsWorkPlanDTO" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-9-23
  Time: 21:35:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>工作计划详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("工作计划详细信息")
</script>
<body>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    AmsWorkPlanDTO dto = (AmsWorkPlanDTO) request.getAttribute(WebAttrConstant.WORK_PLAN_DTO);
    String action = parser.getParameter("act");

%>
<form action="/servlet/com.sino.ams.plan.servlet.AmsWorkPlanServlet" name="mainForm" method="post">
    <table border="1" width="100%">
        <tr>
            <td align="right">计划名称</td>
            <td><input name="planName" type="text" value="<%=dto.getPlanName()%>" style="width:50%"></td>
        </tr>
        <tr>
            <td align="right">任务描述</td>
            <td><textarea rows="10" cols="63" name="planDesc"><%=dto.getPlanDesc()%></textarea></td>
        </tr>
        <tr>
            <td align="right">执行人</td>
            <td><input type="text" name="" value="<%=dto.getExecuteUserName()%>"><a class="linka" style="cursor:'hand'" ></a>
            </td>
        </tr>
        <tr>
            <td align="right">执行时间</td>
            <td><input type="text" name="executeTime" value="<%=dto.getExecuteTime()%>" style="width:30%"
                       title="点击选择日期" readonly class="input2" onclick="gfPop.fPopCalendar(executeTime)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fPopCalendar(executeTime)"></td>

        </tr>
        <tr>
            <td align="right"></td>
            <td align="left">
                <img src="/images/eam_images/back.jpg" alt="返回"
                     onclick="do_concel();return false;"></td>
        </tr>
    </table>

    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="executeUser" value="<%=dto.getExecuteUser()%>">
    <input type="hidden" name="planId" value="<%=dto.getPlanId()%>">
    <input type="hidden" name="planStatus" value="<%=dto.getPlanStatus()%>">
</form>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function do_concel(){
         history.back();
    }
</script>