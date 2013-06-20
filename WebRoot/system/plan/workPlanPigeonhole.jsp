<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-9-23
  Time: 15:28:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>工作计划归档</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("工作计划归档")
</script>
<body onkeydown="autoExeFunction('do_SearchPlan()');" onload="do_drop()">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String action = reqParser.getParameter("act");
%>
<jsp:include page="/message/MessageProcess"/>
<table border="1" width="100%" align="left" class="queryHeadBg">
    <form action="/servlet/com.sino.ams.plan.servlet.AmsWorkPlanPigeonholeServlet" name="workForm" method="post">
        <input type="hidden" name="executeUser">
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
            <td width="10%" align="left"><input type="text" class="readonlyInput" readonly name="executeUserName" style="width:80%" value="<%=reqParser.getParameter("executeUserName")%>"><a
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
            <td width=8% align="right"><img src="/images/eam_images/search.jpg" alt="查询计划"
                                            onClick="do_SearchPlan(); return false;"></td>
            <td width="8%" align="left"><img src="/images/eam_images/archive.jpg" alt="归档计划" onclick="do_gui();return false;">
            </td>

        </tr>
</table>
<input type="hidden" name="act" value="<%=action%>">
<input type="hidden" name="planId" value="">

<div style="height:362;width:100%;position:absolute;top:46px;left:0px;margin-left:0px"
     align="left">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"
           style="position:absolute;left:1px;top:30px" class="headerTable">

        <tr>
            <td width="2%" align="center"><input type="checkbox" name="controlBox"
                                                                   class="headCheckbox"
                                                                   id="controlBox"
                                                                   onclick="checkAll('controlBox','planId1')">
            </td>
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
<div style="overflow-y:scroll;height:362;width:102%;position:absolute;top:100px;left:0px;margin-left:0px"
     align="left">
    <table width="100%" border="1" bordercolor="#666666">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR">
            <td width="2%" align="center"><input type="checkbox" name="planId1" id="planId1"
                                                 value="<%=row.getValue("PLAN_ID")%>">
            </td>
            <td height="22" width="15%"
                onclick="do_ShowDetail('<%=row.getValue("PLAN_ID")%>','<%=row.getValue("STATUSID")%>'); return false;"><%=row.getValue("PLAN_NAME")%>
            </td>
            <td height="22" width="15%"
                onclick="do_ShowDetail('<%=row.getValue("PLAN_ID")%>','<%=row.getValue("STATUSID")%>'); return false;"><%=row.getValue("EXECUTE_USER")%>
            </td>
            <td height="22" width="15%"
                onclick="do_ShowDetail('<%=row.getValue("PLAN_ID")%>','<%=row.getValue("STATUSID")%>'); return false;"><%=row.getValue("EXECUTE_TIME")%>
            </td>
            <td height="22" width="15%"
                onclick="do_ShowDetail('<%=row.getValue("PLAN_ID")%>','<%=row.getValue("STATUSID")%>'); return false;"><%=row.getValue("PLAN_STATUS")%>
            </td>
            <td height="22" width="15%"
                onclick="do_ShowDetail('<%=row.getValue("PLAN_ID")%>','<%=row.getValue("STATUSID")%>'); return false;"><%=row.getValue("CREATION_DATE")%>
            </td>
            <td height="22" width="15%"
                onclick="do_ShowDetail('<%=row.getValue("PLAN_ID")%>','<%=row.getValue("STATUSID")%>'); return false;"><%=row.getValue("CREATED_BY")%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</form>
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
            submit();
        }
    }
    function do_ShowDetail(planId) {

        workForm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>"
        workForm.action = "/servlet/com.sino.ams.plan.servlet.AmsWorkPlanPigeonholeServlet?planId=" + planId;
        workForm.submit();
    }
    function do_gui(planId) {
        var checkCount = getCheckedBoxCount("planId1");
        if (checkCount < 1) {
            alert("请选择一条记录，然后归档！");
        }
        else {
            workForm.act.value = "pigeonhole";
            workForm.action = "/servlet/com.sino.ams.plan.servlet.AmsWorkPlanPigeonholeServlet?planId=" + planId;
            workForm.submit();

        }
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
        }else{
           workForm.executeUser.value='';
           workForm.executeUserName.value='';
        }
    }
     function do_drop() {
     var transStatus = document.getElementById("planStatus");
        dropSpecialOption(transStatus, '4')
        dropSpecialOption(transStatus, '3')
        dropSpecialOption(transStatus, '1')
        dropSpecialOption(transStatus, '2')
    }
</script>