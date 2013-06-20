<%--
  Created by HERRY.
  Date: 2008-10-16
  Time: 10:07:19
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsDictConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>

<html>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<head><title>资产调拨流程监控</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
</head>
<body onkeydown="autoExeFunction('do_Search()')">
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.newasset.servlet.AssetsTransMonitorServlet">
    <script type="text/javascript">
        printTitleBar("资产调拨流程监控");
    </script>
    <input type="hidden" name="transType" value="ASS-RED">
    <input type="hidden" name="transferType" value="<%=AssetsDictConstant.TRANS_BTW_COMP%>">
    <input type="hidden" name="transId" value="">
    <input type="hidden" name="act" value="">
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" class="queryHeadBg">
        <tr>
            <td width="10%" height="22" align="right">调拨单号：</td>
            <td width="17%" height="22"><input type="text" name="transNo" style="width:100%"
                                               value="<%=parser.getParameter("transNo")%>"></td>
            <td width="10%" height="22" align="right">调出公司：</td>
            <td width="17%" height="22"><input type="text" name="fromCompanyName" style="width:100%" value="<%=parser.getParameter("fromCompanyName")%>"></td>
            <td width="10%" height="22" align="right">调入公司：</td>
            <td width="17%" height="22"><input type="text" name="toCompanyName" style="width:100%" value="<%=parser.getParameter("toCompanyName")%>"></td>
        </tr>
        <tr>
            <td height="22" align="right">创建日期：</td>
            <td height="22"><input type="text" name="fromDate" value="<%=parser.getParameter("fromDate")%>"
                                   style="width:80%" title="点击选择开始日期" readonly class="readonlyInput"
                                   onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td height="22" align="right">到</td>
            <td height="22"><input type="text" name="toDate" value="<%=parser.getParameter("toDate")%>"
                                   style="width:80%" title="点击选择截止日期" readonly class="readonlyInput"
                                   onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>
            <td height="22" colspan="2" align="right"><img src="/images/eam_images/search.jpg" onclick="do_Search();"
                                                           alt="查询"></td>
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("调拨单号", "调出公司","调入公司", "创建人", "创建日期", "当前任务名", "办理人");
        var widthArr = new Array("20%", "15%", "15%", "10%", "15%", "12%", "10%");
        printTableHead(columnArr, widthArr);

    </script>
    <div style="overflow-y:scroll;left:1px;width:100%;height:70%">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="20%" align="center"><%=row.getValue("TRANS_NO")%>
                </td>
                <td width="15%"><%=row.getValue("FROM_COMPANY_NAME")%>
                </td>
                <td width="15%"><%=row.getValue("TO_COMPANY_NAME")%></td>
                <td width="10%" align="left"><%=row.getValue("CREATED")%>
                </td>
                <td width="15%" align="center"><%=String.valueOf(row.getValue("CREATION_DATE"))%>
                </td>
                <td width="12%"><%=row.getValue("TASK_NAME")%></td>
                <td width="10%"><%=row.getValue("USERNAME")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div style="left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script language="javascript">
    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }
</script>