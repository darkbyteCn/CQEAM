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
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-9-20
  Time: 10:38:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>工作计划维护</title>
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

<body onkeydown="autoExeFunction('do_SearchPlan()');" onload="do_drop()">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String action = reqParser.getParameter("act");
%>
<%--<%=WebConstant.WAIT_TIP_MSG%>--%>
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.plan.servlet.AmsWorkPlanServlet" name="workForm" method="post">
<script type="text/javascript">
    printTitleBar("工作计划维护")
</script>
    <table border="0" width="100%"  class="queryHeadBg">
        <tr>
            <td width="5%" align="right">计划名称：</td>
            <td width="12%" align="left"><input type="text" name="planName" style="width:80%"
                                                value="<%=StrUtil.nullToString(request.getParameter("planName"))%>">
            </td>
            <td width="5%" align="right">计划状态：</td>
            <td width="13%" align="left">
                <select name="planStatus" id="planStatus" style="width:80%">
                    <%=request.getAttribute(WebAttrConstant.PLAN_STATUS_OPTION)%>
                </select>
            </td>
            <td align="right" width="5%">执行人：</td>
            <td width="10%" align="left"><input type="text" name="executeUserName" style="width:80%" readonly
                                                class="readonlyInput"
                                                value="<%=reqParser.getParameter("executeUserName")%>"><a
                    class="linka" style="cursor:'hand'" onclick="do_selectName();">[…]</a></td>

        </tr>
        <tr>
            <td align="right">创建时间：</td>
            <td><input type="text" name="fromDate" value="<%=reqParser.getParameter("fromDate")%>" style="width:80%"
                       title="点击选择日期" readonly class="readonlyInput"
                       onclick="gfPop.fStartPop(fromDate,'')">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fStartPop(fromDate,'')"></td>
            <td align="right">到：</td>
            <td><input type="text" name="toDate" value="<%=reqParser.getParameter("toDate")%>" style="width:80%"
                       title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(fromDate,toDate)"></td>
            <td align="center" colspan="2"><img src="/images/eam_images/search.jpg" alt="查询计划"
                                            onClick="do_SearchPlan(); return false;">
                <img src="/images/eam_images/new_add.jpg" alt="新增计划"
                                             onClick="do_CreatePlan(); return false;">
                <img src="/images/eam_images/revoke.jpg" alt="撤消计划" onclick="do_back();return false"></td>


        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="planId" value="">
    <input type="hidden" name="executeUser" value="<%=reqParser.getParameter("executeUser")%>">
    <input type="hidden" name="loginName">

    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%"  border="1" cellpadding="2" cellspacing="0"
                class="headerTable">

            <tr>
                <td width="3%" align="center" style="padding:0"><input type="checkbox" name="controlBox"
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
    <div style="overflow-y:scroll;height:362px;width:100%;"
         align="left">
        <table width="100%" border="1" bordercolor="#666666" id="dataTab">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("PLAN_ID")%>','<%=row.getValue("STATUSID")%>')">
                <td width="3%" align="center"><input type="checkbox" name="planId1" id="planId1<%=i%>"
                                                     value="<%=row.getValue("PLAN_ID")%>">
                    <input type=hidden name=planId2 value="<%=row.getValue("PLAN_ID")%>">
                    <input type=hidden name=isCancel id="isCancel<%=i%>" value="">
                    <input type=hidden name=statusId id=statusId<%=i%> value="<%=row.getValue("STATUSID")%>">
                </td>
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
                }   }
            %>
        </table>
    </div>
</form>

<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
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
    function do_CreatePlan() {
        var url = "/servlet/com.sino.ams.plan.servlet.AmsWorkPlanServlet?act=<%=WebActionConstant.NEW_ACTION%>";
        var popscript = 'width=700,height=400,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, 'planWin', popscript);
    }

    function do_ShowDetail(planId) {
        if (event.srcElement.name == 'planId1') {
            return;
        }
        workForm.planId.value = planId;
        workForm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        workForm.submit();
    }

    function do_selectName() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_USER%>";
        var popscript = "dialogWidth:50;dialogHeight:30;center:yes;status:no;scrollbars:no";
        /*   window.open(url);*/
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
    function do_drop() {
     var transStatus = document.getElementById("planStatus");
        dropSpecialOption(transStatus, '4')
        dropSpecialOption(transStatus, '3')
    }
    function do_back(planId, statusId) {
        var checkCount = getCheckedBoxCount("planId1");
        if (checkCount < 1) {
            alert("请选择一条记录，然后撤消！");
        }
        else {
            //  document.getElementById("isCancel" + i).value = 'Y';
            var length = document.getElementById("dataTab").rows.length;
            if (length > 0) {
                var flag = '';
                for (var i = 0; i < length; i++) {
                    if (document.getElementById("planId1" + i).checked) {
                        if (document.getElementById("statusId" + i).value != 1) {
                            //                            alert(document.getElementById("statusId" + i).value);
                            alert("请确认你的计划状态是新建，只有新建才能撤消!");
                            flag = 'N'
                            break;
                        }

                    }
                }
                if (flag != 'N') {
                    /*   var  pland= workForm.planId1.value*/
                    workForm.act.value = "repeal";
                    workForm.action = "/servlet/com.sino.ams.plan.servlet.AmsWorkPlanServlet";
                    workForm.submit();
                }

            }
        }

    }
</script>