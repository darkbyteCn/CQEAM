<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-9-21
  Time: 14:51:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<html>
<head><title>工作计划查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("工作计划查询")
</script>
<body onkeydown="autoExeFunction('do_SearchPlan()');" onload="do_drop()">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
%>
<jsp:include page="/message/MessageProcess"/>
<table border="1" width="100%" align="left" class="queryHeadBg">
    <form action="/servlet/com.sino.ams.plan.servlet.AmsWorkPlanQueryServlet" name="workForm" method="post">
        <input type="hidden" name="act" value="<%=action%>">
        <tr>
            <td width="8%" align="right">计划名称：</td>
            <td width="12%" align="left"><input type="text" name="planName" style="width:100%"
                                                value="<%=StrUtil.nullToString(request.getParameter("planName"))%>">
            </td>
            <td width="8%" align="right">计划状态：</td>
            <td width="13%" align="left">
                <select name="planStatus" style="width:60%">
                    <%=request.getAttribute(WebAttrConstant.PLAN_STATUS_OPTION)%>
                </select>
            </td>
            <td align="right" width="5%">执行人：</td>
            <td width="10%" align="left"><input type="text" class="readonlyInput" readonly name="executeUserName"
                                                style="width:80%"
                                                value="<%=reqParser.getParameter("executeUserName")%>"><a
                    class="linka" style="cursor:'hand'" onclick="do_selectName();">[…]</a></td>

        </tr>
        <tr>
            <td align="right">创建时间：</td>
            <td><input type="text" name="fromDate" value="<%=reqParser.getParameter("fromDate")%>" style="width:80%"
                       title="点击选择日期" readonly class="input2" onclick="gfPop.fStartPop('', fromDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop('',fromDate)"></td>
            <td align="right">到：</td>
            <td><input type="text" name="toDate" value="<%=reqParser.getParameter("toDate")%>" style="width:80%"
                       title="点击选择日期" readonly class="input2" onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(fromDate,toDate)"></td>
            <td width="5%" align="right"></td>
            <td width=8% align="center"><img src="/images/eam_images/search.jpg" alt="查询计划"
                                             onClick="do_SearchPlan(); return false;"></td>


        </tr>
</table>
<input type="hidden" name="executeUser">
<input type="hidden" name="planId" value="">
<!--<input type="hidden" name="loginName">-->
</form>
<div style="height:362;width:100%;position:absolute;top:46px;left:1px;margin-left:0px"
     align="left">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"
           style="position:absolute;left:1px;top:30px" class="headerTable">

        <tr>
            <td height="22" width="15%" align="center">计划名称</td>
            <td height="22" width="15%" align="center">执行人</td>
            <td height="22" width="15%" align="center">执行时间</td>
            <td height="22" width="15%" align="center">执行状态</td>
            <td height="22" width="15%" align="center">创建时间</td>
            <td height="22" width="15%" align="center">创建人</td>
        </tr>
    </table>
</div>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:362;width:102%;position:absolute;top:100px;left:1px;margin-left:0px"
     align="left">
    <table width="100%" border="1" bordercolor="#666666">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="do_show('<%=row.getValue("PLAN_ID")%>')">

            <td height="22" width="15%"><%=row.getValue("PLAN_NAME")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("EXECUTE_USER")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("EXECUTE_TIME")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("PLAN_STATUS")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("CREATION_DATE")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("CREATED_BY")%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<%
    }
%>
<div style="position:absolute;top:460px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function do_SearchPlan() {
        with (workForm) {
             document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            act.value = "<%=WebActionConstant.QUERY_ACTION%>";
            action = "/servlet/com.sino.ams.plan.servlet.AmsWorkPlanQueryServlet";
            submit();
        }
    }
    function do_drop() {
        var transStatus = document.getElementById("planStatus");
        dropSpecialOption(transStatus, '4')
        dropSpecialOption(transStatus, '3')
    }
    function do_show(planId) {
        workForm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        workForm.action = "/servlet/com.sino.ams.plan.servlet.AmsWorkPlanQueryServlet?planId=" + planId;
        workForm.submit();
    }
    function do_selectName() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_USER%>";
        var popscript = "dialogWidth:50;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var users = window.showModalDialog(url, null, popscript);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "workForm");
            }
        } else {
            workForm.executeUser.value = '';
            workForm.executeUserName.value = '';
        }
    }
</script>