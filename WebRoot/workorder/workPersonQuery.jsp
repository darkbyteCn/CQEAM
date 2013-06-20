<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-1-18
  Time: 10:22:27
  To change this template use File | Settings | File Templates.
--%>
<head>
    <title>个人工单查询</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>

    <%
        RequestParser reqParser = new RequestParser();
        reqParser.transData(request);
        String workorderBatch = reqParser.getParameter("workorderBatch");
        String workorderBatchName = reqParser.getParameter("workorderBatchName");
        String workorderNo = reqParser.getParameter("workorderNo");
        String workorderObjectCode = reqParser.getParameter("workorderObjectCode");
        String workorderObjectName = reqParser.getParameter("workorderObjectName");
        String executeUserName = reqParser.getParameter("executeUserName");
        String startDate = reqParser.getParameter("startDate");
        String action = reqParser.getParameter("act");
        String queryType = reqParser.getParameter("queryType");
    %>

</head>
<html>
<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="do_check()">
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.WorkPersonServlet">
    <script type="text/javascript">
        printTitleBar("个人工单查询");
    </script>
    <table width="100%" border="0" class="queryTable" cellpadding="2" cellspacing="0"
            >
        <input type="hidden" name="act" value="<%=action%>">
        <input type="hidden" name="groupId" value="">
        <input type="hidden" name="queryType" value="<%=queryType%>">
        <tr>
            <td align="right" width="10%">工单号：</td>
            <td width="15%"><input style="width:80%" type="text" class="input_style1" name="workorderNo" value="<%=workorderNo%>"></td>
            <td align="right" width="10%">工单状态：</td>
            <td width="15%"><select style="width:80%"      class="select_style1"
                                    name="workorderFlag"><%=request.getAttribute(WebAttrConstant.PLAN_STATUS_OPTION)%>
            </select>
            </td>
            <td align="right" width="15%">开始时间大于：</td>
            <td width="15%"><input type="text" name="startDate"
                                   value="<%=startDate%>" class="input_style2"
                                   style="width:80%" title="点击选择日期"
                                   readonly onclick="gfPop.fEndPop('',startDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop('',startDate)"></td>
            <td width="10%"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onClick="do_search();" alt="点击查询"></td>
            <td width="10%"><img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_export();" alt="导出到Excel"></td>
        </tr>
    </table>
    <script type="text/javascript">
        var columnArr = new Array("公司名称", "工单号", "工单状态", "所属专业", "工单类型", "地点编号", "地点简称", "开始日期", "实施周期(天)", "执行人", "实际完成日期", "差异", "超时");
        var widthArr = new Array("8%", "9%", "6%", "6%", "6%", "7%", "7%", "7%", "8%", "5%", "8%", "3%", "3%");
        printTableHead(columnArr, widthArr);
    </script>

    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:310px;width:100%;left:1px;margin-left:0px" align="left">
        <table width="100%" border="1" bordercolor="#666666">

            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" height="22" onclick="do_show_detail('<%=row.getValue("WORKORDER_NO")%>'); return false;">
                <td width="8%"><%=row.getValue("ORG_NAME")%>
                </td>
                <td width="9%"><%=row.getValue("WORKORDER_NO")%>
                </td>
                <td width="6%"><%=row.getValue("WORKORDER_FLAG_DESC")%>
                </td>
                <td width="6%"><%=row.getValue("ATTRIBUTE4")%>
                </td>
                <td width="6%"><%=row.getValue("WORKORDER_TYPE_DESC")%>
                </td>
                <td width="7%"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td width="7%"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td width="7%"><%=row.getValue("START_DATE")%>
                </td>
                <td width="8%"><%=row.getValue("IMPLEMENT_DAYS")%>
                </td>
                <td width="5%"><%=row.getValue("IMPLEMENT_USER")%>
                </td>
                <td width="8%"><%=row.getValue("UPLOAD_DATE")%>
                </td>
                <td width="3%"><%=row.getValue("DIFF")%>
                </td>
                <td width="3%"><%=row.getValue("OVERTIME")%>
                </td>

                <%
                    }    }
                %>
        </table>
    </div>
</form>
<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

<%=WebConstant.WAIT_TIP_MSG%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script language="javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_export() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.target = "_self";
        mainFrm.submit();
    }

    function do_show_detail(WORKORDER_NO)
    {
        var url = "<%=URLDefineList.ORDER_DETAIL_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>&WORKORDER_NO=" + WORKORDER_NO;
        var screenHeight = window.screen.height - 100;
        var screenWidth = window.screen.width;
        var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no";
        window.open(url, "", winstyle);
    }

    function doChecked() {

    }

    function do_check() {
        if (event.keyCode == 13) {
            do_search();
        }
    }
    function showLocation() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BTS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Locations = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (Locations) {
            var Location = null;
            for (var i = 0; i < Locations.length; i++) {
                Location = Locations[i];
                dto2Frm(Location, "mainFrm");
            }
        }
    }
    function showUser() {
        var lookUser = "<%=LookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUser, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        }
    }


</script>