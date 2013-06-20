<%@ page contentType = "text/html;charset=GBK" language = "java" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant" %>
<%@ page import = "com.sino.base.data.Row" %>
<%@ page import = "com.sino.base.data.RowSet" %>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.ams.constant.WebAttrConstant" %>
<%@ page import = "com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  created by YS
  Date: 2007-09-28
  Time: 8:23:36
--%>
<html>
<head>
    <title>工单时间点分布统计报表</title>
</head>
<body onload = "autoSpan('dateTable',1); ">
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
      String yearOption = parser.getAttribute(WebAttrConstant.LAST_FIVE_YEAR_OPTION).toString();
%>
<form method = "post" name = "mainFrm">
    <script type = "text/javascript">
        printTitleBar("工单时间点分布统计报表")
    </script>
    <table width = "100%" border = "0" class="queryTable">
        <tr>
            <td width = "10%" align = "right">年份：</td>
            <td width = "20%"><select style = "width:100%" class="select_style1" name = "year"><%=yearOption%></select></td>
            <td width = 80%></td>
            <td align = "center"><img src = "/images/eam_images/search.jpg" style = "cursor:'hand'" onclick = "do_search();" alt = "查询"></td>
            <td><img src = "/images/eam_images/export.jpg" id = "queryImg" style = "cursor:'hand'" onclick = "do_exportToExcel()" alt = "导出到Excel"></td>
            <td style = "width:70%"></td>
        </tr>
    </table>
    <input type = "hidden" name = "act" value = "<%=parser.getParameter("act")%>">
    <input type = "hidden" name = "qryType" value = "<%=WebAttrConstant.BY_TIME%>">

    <table width = "98.5%" align = "left" border = "1" cellpadding = "2" cellspacing = "0" style = "position:absolute;left:0px;top:41px" class = "headerTable">
        <tr>
            <td width = "18%" align = "center" rowspan = "2"><font color = "#FFFFFF">公司</font></td>
            <td width = "24%" align = "center" colspan = "2"><font color = "#FFFFFF">8点至18点</font></td>
            <td width = "24%" align = "center" colspan = "2"><font color = "#FFFFFF">18点至0点</font></td>
            <td width = "24%" align = "center" colspan = "2"><font color = "#FFFFFF">0点至8点</font></td>
        </tr>
        <tr>
            <td width = "12%" align = "center"><font color = "#FFFFFF">数量</font></td>
            <td width = "12%" align = "center"><font color = "#FFFFFF">百分比</font></td>
            <td width = "12%" align = "center"><font color = "#FFFFFF">数量</font></td>
            <td width = "12%" align = "center"><font color = "#FFFFFF">百分比</font></td>
            <td width = "12%" align = "center"><font color = "#FFFFFF">数量</font></td>
            <td width = "12%" align = "center"><font color = "#FFFFFF">百分比</font></td>
        </tr>
    </table>

    <div style = "overflow-y:scroll; position:absolute;top:80px;left:0px;width:100%;height:68%">
        <table width = "100%" border = "1" bordercolor = "#666666" id = "dateTable">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class = "dataTR" height = "22">
                <td width = "18%" align = "center"><%=row.getValue("ORGANIZATION_NAME")%></td>
                <td width = "12%" align = "center"><%=row.getValue("T1")%></td>
                <td width = "12%" align = "center"><%=row.getValue("P1")%></td>
                <td width = "12%" align = "center"><%=row.getValue("T2")%></td>
                <td width = "12%" align = "center"><%=row.getValue("P2")%></td>
                <td width = "12%" align = "center"><%=row.getValue("T3")%></td>
                <td width = "12%" align = "center"><%=row.getValue("P3")%></td>
            </tr>
            <%
                }    }
            %>
        </table>
    </div>

</form>

<div style = " position:absolute;top:87%;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page = "<%=URLDefineList.MESSAGE_PROCESS%>" flush = "true" />
</body>
</html>
<script type = "text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "<%=URLDefineList.STAT_WO_SERVLET%>";
        //        alert(mainFrm.action);
        mainFrm.submit();
    }
    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "<%=URLDefineList.STAT_WO_SERVLET%>";
        mainFrm.submit();

    }

</script>
