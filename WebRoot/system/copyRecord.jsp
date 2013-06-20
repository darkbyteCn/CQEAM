<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<%--
    查阅抄送狀态
  Created by wwb.
  User: demo
  Date: 2006-12-21
  Time: 11:56:30
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>抄送记录</title>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
</head>

<body topmargin=0 leftmargin=0>
<script>
    printTitleBar("抄送记录");
</script>
<table width=100%  class="tbborder">
    <tr class=headerTable>
        <td align=center>抄送人</td>
        <td align=center>抄送部门</td>
        <td align=center>接收人</td>
        <td align=center>接收部门</td>
        <td align=center>抄送时间</td>
        <td align=center>抄送信息</td>
        <td align=center>阅读时间</td>
        <td align=center>阅读狀态</td>
        <td align=center>阅读人意见</td>
    </tr>
    <%
        RowSet rs = (RowSet) request.getAttribute(WebAttrConstant.SINOFLOW_STATUS);
        if (rs != null && !rs.isEmpty()) {
            for (int i = 0; i < rs.getSize(); i++) {
                Row row = rs.getRow(i);
    %>
    <tr class="nohandTr">
        <td align=left><%=row.getStrValue("FROM_USER")%></td>
        <td align=left><%=row.getStrValue("FROM_DEPT")%></td>
        <td align=left><%=row.getStrValue("TASK_USER")%></td>
        <td align=left><%=row.getStrValue("TO_DEPT")%></td>
        <td align=left><%=row.getStrValue("FROM_DATE")%></td>
        <td align=left><%=row.getStrValue("USER_MSG")%></td>
        <td align=center><%=row.getStrValue("COMPLETE_DATE")%></td>
        <td align=left><%=row.getStrValue("STATUS")%></td>
        <td align=left><%=row.getStrValue("READER_NOTE")%></td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>