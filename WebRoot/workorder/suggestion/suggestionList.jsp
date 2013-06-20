<%--
  User: zhoujs
  Date: 2007-12-20 13:47:13
  Function:流程意见查阅
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>意见查阅</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>

</head>

<body leftmargin="1" topmargin="0">

<form name="mainFrm" method="POST" action="">
    <script language="javascript">
        printTitleBar(">>流程意见查阅");
    </script>
    <script type="text/javascript">
        var columnArr = new Array("提交人", "提交日期", "意见");
        var widthArr = new Array("20%", "25%", "55%");
        printTableHead(columnArr, widthArr);
    </script>

    <%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:362;width:100%;left:1px;margin-left:0px" align="left">
    <table width="100%" border="1" bordercolor="#666666">

        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("SYSTEMID")%>'); return false;">
            <td style="word-wrap:break-word" height="22" width="20%"><%=row.getValue("USERNAME")%></td>
            <td style="word-wrap:break-word" height="22" width="25%"><%=row.getValue("RECORD_DATE")%></td>
            <td style="word-wrap:break-word" height="22" width="55%"><%=row.getValue("REMARK")%></td>
        </tr>
        <%
            }
        %>
    </table>
</div>

<div style="position:absolute;top:428px;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>

<%=WebConstant.WAIT_TIP_MSG%>
</form>
</body>
</html>