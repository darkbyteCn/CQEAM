<%--
  Created by IntelliJ IDEA.
  User: V-jiachuanchuan
  Date: 2007-11-15
  Time: 17:51:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>工单任务详细信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
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
        String action = reqParser.getParameter("act");
    %>

</head>

<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="do_check()">

<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.QueryIntegrationServlet">
    <script type="text/javascript">
        printTitleBar("工单任务详细信息")
    </script>
    <table width="100%" border=0 cellpadding="2" cellspacing="0" class="queryHeadBg">
        <input type="hidden" name="act" value="<%=action%>">
        <input type="hidden" name="groupId" value="">
        <input type="hidden" name="workorderBatch" value="">
        <jsp:include page="/message/MessageProcess"/>
        <tr>
            <td align="left" width="16%"><a style="cursor:'hand'"><font color="Blue" size="2"><img
                    src="/images/eam_images/back.jpg" onClick="goBack(); return false;"></font></a>
            </td>
        </tr>
    </table>


    <script type="text/javascript">
        var columnArr = new Array("工单批号", "任务名称", "工单号", "地点编号", "地点简称", "所属专业", "工单类型", "工单状态", "开始时间", "实施周期(天)", "执行人", "备注");
        var widthArr = new Array("8%", "8%", "9%", "8%", "8%", "6%", "6%", "6%", "7%", "8%", "5%", "5%");
        printTableHead(columnArr, widthArr);
    </script>

    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:350px;width:100%;left:1px;margin-left:0px" align="left">
        <table width="100%" border="1" bordercolor="#666666">

            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);

            %>
            <tr class="dataTR" height="22" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;">
                <td width="8%"><%=row.getValue("WORKORDER_BATCH")%>
                </td>
                <td width="8%"><%=row.getValue("WORKORDER_BATCH_NAME")%>
                </td>
                <td width="9%"><%=row.getValue("WORKORDER_NO")%>
                </td>
                <td width="8%"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td width="8%"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td width="6%"><%=row.getValue("ATTRIBUTE4")%>
                </td>
                <td width="6%"><%=row.getValue("WORKORDER_TYPE_DESC")%>
                </td>
                <td width="6%"><%=row.getValue("WORKORDER_FLAG_DESC")%>
                </td>
                <td width="7%"><%=row.getValue("START_DATE")%>
                </td>
                <td width="8%"><%=row.getValue("IMPLEMENT_DAYS")%>
                </td>
                <td width="5%"><%=row.getValue("IMPLEMENT_USER")%>
                </td>
                <td width="5%"><%=row.getValue("REMARK")%>
                </td>

                <%
                    } }
                %>
        </table>
    </div>
</form>
<div style="position:absolute;top:475px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>


</body>

<script language="javascript">

    function do_ShowDetail(WORKORDER_NO)
    {
        var url = "<%=URLDefineList.ORDER_DETAIL_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>&WORKORDER_NO=" + WORKORDER_NO;
        var screenHeight = window.screen.height - 100;
        var screenWidth = window.screen.width;
        var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no";
        window.open(url, "", winstyle);
    }

    function goBack() {
        history.back();
    }
    function doChecked() {

    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchOrder();
        }
    }
</script>

</html>