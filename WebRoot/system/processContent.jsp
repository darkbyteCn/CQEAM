<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<%@ page import="com.sino.sinoflow.util" %>
<%--
    查阅流程过程
  Created by hing
  Date: 2009-05-15
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>案件信息</title>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
</head>

<body topmargin=0 leftmargin=0>
<script>
    printTitleBar("查阅案件信息");
</script>
<table width=100%  class="tbBorder" cellspacing="1">
    <tr class=headerTable>
        <td align=center>办理人/收件人</td>
        <td align=center>办理组別</td>
        <td align=center>办理角色</td>
        <td align=center>签收时间</td>
        <td align=center>完成时间</td>
        <td align=center>预定时间</td>
    </tr>
    <%
        RowSet rs = (RowSet) request.getAttribute(WebAttrConstant.SINOFLOW_STATUS);
        if (rs != null && !rs.isEmpty()) {
            for (int i = 0; i < rs.getSize(); i++) {
                Row row = rs.getRow(i);

    %>
    <tr class="nohandTr">
        <%
            if(row.getStrValue("SFACT_SIGN_STATUS").equals("0") || row.getStrValue("SFACT_SIGN_STATUS").equals("")) {
        %>
        <td align=left><%=row.getStrValue("SFACT_TASK_USERS")%></td>
        <%
            } else if(row.getStrValue("SFACT_COMPLETE_STATUS").equals("0") || row.getStrValue("SFACT_COMPLETE_STATUS").equals("")){
        %>
        <td align=left><%=row.getStrValue("SFACT_SIGN_USER")%></td>
        <%
            } else {
        %>
        <td align=left><%=row.getStrValue("SFACT_COMPLETE_USER")%></td>
        <%
            }
            String taskGroup = row.getStrValue("SFACT_TASK_GROUP");
            if(util.isMask(taskGroup)) {
                taskGroup = util.convertMaskGroup(taskGroup, row.getStrValue("SFACT_HANDLER_GROUP"),
                        row.getStrValue("SFACT_PLUS_GROUP"));
            }
        %>
        <td align=left><%=taskGroup%></td>
        <td algin=left><%=row.getStrValue("SFACT_TASK_ROLE")%></td>
        <%
            if(row.getStrValue("SFACT_SIGN_STATUS").equals("0") || row.getStrValue("SFACT_SIGN_STATUS").equals("")) {
        %>
        <td align=center>未签收</td>
        <td align=center></td>
        <td align=center></td>
        <%
            } else {
        %>
        <td align=center><%=row.getStrValue("SFACT_SIGN_DATE")%></td>
        <%
            if(row.getStrValue("SFACT_COMPLETE_STATUS").equals("0") || row.getStrValue("SFACT_COMPLETE_STATUS").equals("")) {
        %>
        <td align=center>未完成</td>
        <%
            } else {
        %>
        <td align=center><%=row.getValue("SFACT_COMPLETE_DATE")%></td>
        <%
            }
        %>
        <td align=center><%=row.getValue("SFACT_SIGN_DUE_DATE")%></td>
        <%
            }
        %>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>