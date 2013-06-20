<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%--
  Created by wwb.
  User: demo
  Date: 2006-12-6
  Time: 10:34:50
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>个人申请查询</title>
    <link href="/style/css/style.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" src="/style/js/cpsTitleBar.js"></script>
    <%
        RequestParser rp = new RequestParser();
        rp.transData(request);
    %>
</head>

<body topmargin="0" leftmargin="0" onkeydown="autoExeFunction('query();')">
<table width="100%" border="0" cellspacing="0" cellpadding="3" align="center">
    <tr width=100%>
        <td width="4%" align="center" class="buttonTitle">
            <img src="/images/filenew.gif" width="14" height="16"></td><td valign="bottom" align="left"
                                                                           class="buttonTitle">
        <b>个人申请查询</b></td>
    </tr>
</table>
<form action="/servlet/com.sino.flow.servlet.PersonalServlet" method="post" name="mainForm">
    <table style="position:absolute;top:25px;width:100%">
        <tr>
            <td width=10% align=right>单据号：</td>
            <td width=20%><input type="text" style="width:80%;height:20px" name="reportNumber"
                                 value="<%=rp.getParameter("reportNumber")%>"></td>
            <td width=10% align=right>创建日期：</td>
            <td width=20%>
                <input type="text" style="width:80%;height:20px" readonly name="fromDate" onclick="gfPop.fEndPop('',fromDate);"
                       value="<%=rp.getParameter("fromDate")%>">
                <img border='0' src="/images/calendar2.gif" alt="点击选择开始日期" onclick="gfPop.fEndPop('',fromDate);"/>
            </td>
            <td width=10% align=right>到：</td>
            <td width=20%>
                <input type="text" name="toDate" style="width:80%;height:20px" readonly onclick="gfPop.fEndPop(fromDate,toDate);"
                       value="<%=rp.getParameter("toDate")%>">
                <img border='0' src="/images/calendar2.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate,toDate);"/>
            </td>
        </tr>
        <tr>
            <td align=right>单据类型：</td>
            <td><select name=expenseType
                        style="width:80%;text-align:center;height:20px"><%=StrUtil.nullToString(request.getAttribute("EXPENSE_TYPE_OPTION"))%></select>
            </td>
            <td align=right>单据状态：</td>
            <td><select name=headerStatus style="width:80%;height:20px;text-align:center">
                <%=StrUtil.nullToString(request.getAttribute("HEADER_STATUS_OPTION"))%>
            </select>
            </td>
            <td colspan=2  align="center" class="noborder">
                <input type="button" value="查&nbsp;询" onClick="query();" class="formStyleall" style="cursor:pointer">
            </td>
        </tr>
    </table>
</form>
<table border="0" class="headerTable" style="position:absolute;top:80px;width:880px;" cellspacing="0">
    <tr height="20px">
        <td align="center" class="uptitle" width="14%">单据号</td>
        <td align="center" class="uptitle" width="14%">总金额</td>
        <td align="center" class="uptitle" width="14%">单据类型</td>
        <td align="center" class="uptitle" width="14%">单据状态</td>
        <td align="center" class="uptitle" width="14%">创建人</td>
        <td align="center" class="uptitle" width="14%">创建日期</td>
        <td align="center" class="uptitle" width="14%">当前办理人</td>
    </tr>
</table>
<div style="overflow-y:scroll;position:absolute;width:897px;top:102px;height:75%">
    <table style="width:100%;border-collapse:collapse" border="1" cellpadding="0" cellspacing="0">
        <%
            RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
            if (rows != null && !rows.isEmpty()) {
                Row row = null;
                String bgcolor = "";
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
                    if (i % 2 == 0) {
                        bgcolor = "#EBF7FF";
                    } else {
                        bgcolor = "#FFFFFF";
                    }
        %>
        <tr height=20 style="cursor:hand"  class="trBright"
            onclick="showDetail('<%=row.getValue("HEADER_ID")%>','<%=row.getValue("EXPENSE_TYPE")%>')">
            <td width="14%"><%=row.getValue("REPORT_NUMBER")%></td>
            <td width="14%" align=right><%=row.getValue("TOTAL_MONEY")%></td>
            <td width="14%"><%=row.getValue("EXPENSE_TYPE_DESC")%></td>
            <td width="14%"><%=row.getValue("HEADER_STATUS")%></td>
            <td width="14%"><%=row.getValue("USER_NAME")%></td>
            <td width="14%"><%=row.getValue("CREATED_DATE")%></td>
            <td width="14%"><%=row.getValue("CUR_USER")%></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
<%
    if (rows != null && !rows.isEmpty()) {
%>
<table border="0" width="100%" id="table1" style="position:absolute;top:90%">
    <tr>
        <td width="97%" align="right"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%>
        </td>
        <td width="3%"></td>
    </tr>
</table>
<%
    }
%>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<script>
    function query() {
        document.getElementById("tipMsg").style.visibility="visible";
        document.mainForm.submit();
    }
    function showDetail(headerId, type) {
        var url = '/servlet/com.sino.eas.servlet.AccountDetailServlet?headerId=' + headerId + '&expenseType=' + type;
        style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=auto, resizable=yes,location=no, status=no';
        window.open(url, "detailWin", style);
    }
</script>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/style/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>
