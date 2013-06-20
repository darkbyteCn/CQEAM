<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>PDA程序版本维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>

</head>

<body leftmargin="1" topmargin="0">

<%
    String action = StrUtil.nullToString(request.getParameter("act"));
%>
<form name="mainFrm" method="POST" action="">
    <script type="text/javascript">
        printTitleBar("PDA程序版本维护");
    </script>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="groupId" value="">
    <jsp:include page="/message/MessageProcess"/>
    <table border="0" width="100%" id="queryTable" class="queryTable">
        <tr>
            <td width="100%" align="right">
                <img align="middle" src="/images/eam_images/search.jpg" alt="查询" onclick="do_search();">
            </td>
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("模块名称", "模块描述", "版本", "文件大小");
        var widthArr = new Array("25%", "30%", "25%", "20%");
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
        <tr class="dataTR" onclick="show_detail('<%=row.getValue("MODULE")%>'); return false;">
            <td style="word-wrap:break-word" height="20" width="25%"><%=row.getValue("MODULE")%></td>
            <td style="word-wrap:break-word" height="20" width="30%"><%=row.getValue("DESCRIPTION")%></td>
            <td style="word-wrap:break-word" height="20" width="25%"><%=row.getValue("VERSION")%></td>
            <td style="word-wrap:break-word" height="20" width="20%"><%=row.getValue("FILESIZE")%></td>
        </tr>
        <%
            }  }
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
        document.mainFrm.action = "/servlet/com.sino.ams.system.update4pda.servlet.EtsAutoupdateServlet";
        document.mainFrm.submit();
    }

    function show_detail(module) {
        document.mainFrm.action = "/servlet/com.sino.ams.system.update4pda.servlet.EtsAutoupdateServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&module=" + module;
        document.mainFrm.submit();
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_search();
        }
    }
</script>