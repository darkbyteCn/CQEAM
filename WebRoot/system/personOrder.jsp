<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>个人工单查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/printToolBar.js"></script>
</head>

<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="autoExeFunction('do_SearchGroup();');">
<jsp:include page="/message/MessageProcess"/>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String groupName = reqParser.getParameter("groupName");
    String groupDesc = reqParser.getParameter("groupDesc");
    String action = reqParser.getParameter("act");
    String actionServlet="/servlet/com.sino.sinoflow.user.servlet.PersonOrderServlet";
%>
<form name="mainFrm" method="POST" action="<%=actionServlet%>">
    <script language="javascript">
        printTitleBar("个人工单查询");
    </script>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="userId" value="">
    <input type="hidden" name="operator" value="">                                                         

    <table border="0" width="100%" id="table1">
        <tr>
            <td width="12%" align="right">单据编号：</td>
            <td width="15%"><input type="text" name="transNo"  class="input_style1" style="width:100%" value=""></td>

            <td width="12%" align="right">单据名称：</td>
            <td width="15%"><input type="text" name="transName"  class="input_style1" style="width:100%" value=""></td>

            <td width="8%" align="center">
             <%--<img align="middle" src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_export(); return false;">--%>
                <img align="middle" src="/images/eam_images/search.jpg" alt="查询" onclick="do_search(); return false;">
            </td>
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("单据号","单据名称", "创建人", "创建时间", "说明");
        var widthArr = new Array("20%", "25%", "15%", "25%", "15%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:75%;width:100%;left:1px;margin-left:0px">
        <table width="100%" border="1">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR">
            
                <td style="word-wrap:break-word" height="22" width="20%"><%=row.getValue("SFACT_APPL_COLUMN_1")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="25%"><%=row.getValue("SFACT_PROC_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%"><%=row.getValue("USERNAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="25%"><%=row.getValue("SFACT_CREATE_DT")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%">
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
    <%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }

    function do_export() {
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_search();
        }
    }
</script>