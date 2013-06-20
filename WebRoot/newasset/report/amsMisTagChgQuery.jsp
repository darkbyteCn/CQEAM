<%--
  Created by HERRY.
  Date: 2008-10-13
  Time: 16:47:24
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
<head><title>打印新旧标签对照表</title>
        <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<body onload="do_SetPageWidth()" onkeydown="autoExeFunction('do_Search()')">
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.newasset.servlet.AmsMisTagChgServlet">
    <script type="text/javascript">
        printTitleBar("打印新旧标签对照表");
    </script>
    <input type="hidden" name="transType" value="ASS-RED">
    <input type="hidden" name="transferType" value="<%=AssetsDictConstant.TRANS_BTW_COMP%>">
    <input type="hidden" name="transId" value="">
    <input type="hidden" name="act" value="">
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0">
        <tr>
            <td width="10%" height="22" align="right">调拨单号：</td>
            <td width="17%" height="22"><input class="input_style1" type="text" name="transNo" style="width:80%"
                                               value="<%=parser.getParameter("transNo")%>"></td>
            <td width="10%" height="22" align="right">调出公司：</td>
            <td width="17%" height="22"><input class="input_style1" type="text" name="fromCompanyName" style="width:80%" value="<%=parser.getParameter("fromCompanyName")%>"></td>
            <td width="10%" height="22" align="right">调入公司：</td>
            <td width="17%" height="22"><input class="input_style1" type="text" name="toCompanyName" style="width:100%" value="<%=parser.getParameter("toCompanyName")%>"></td>
        </tr>
        <tr>
            <td height="22" align="right">创建日期：</td>
            <td height="22"><input class="input_style1" type="text" name="startDate" value="<%=parser.getParameter("startDate")%>"
                                   style="width:80%;cursor:pointer" title="点击选择开始日期" readonly class="readonlyInput"
                                   onclick="gfPop.fStartPop(startDate, SQLEndDate)"></td>
            <td height="22" align="right">到：</td>
            <td height="22"><input class="input_style1" type="text" name="SQLEndDate" value="<%=parser.getParameter("SQLEndDate")%>"
                                   style="width:80%;cursor:pointer" title="点击选择截止日期" readonly class="readonlyInput"
                                   onclick="gfPop.fEndPop(startDate, SQLEndDate)"></td>
            <td height="22" colspan="2" align="right"><img src="/images/eam_images/search.jpg" onclick="do_Search();"
                                                           alt="查询"></td>
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("调拨单号", "调出公司","调入公司", "创建人", "创建日期");
        var widthArr = new Array("15%", "20%", "20%", "10%", "12%");
        printTableHead(columnArr, widthArr);

    </script>
    <div id="dataDiv" style="overflow:scroll;height:81%;width:100%;position:absolute;top:94px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table width="100%" id="dataTable" border="1" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:pointer" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'"
                onclick="do_ShowDetail('<%=row.getValue("TRANS_NO")%>')">
                <td width="15%" align="center"><%=row.getValue("TRANS_NO")%>
                </td>
                <td width="20%"><%=row.getValue("FROM_COMPANY_NAME")%>
                </td>
                <td width="20%"><%=row.getValue("TO_COMPANY_NAME")%></td>
                <td width="10%" align="left"><%=row.getValue("CREATED")%>
                </td>
                <td width="12%" align="center"><%=String.valueOf(row.getValue("CREATION_DATE"))%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div id="pageNaviDiv" style="left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script type="text/javascript">

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }

    function do_ShowDetail(primaryKey) {
        //	document.mainFrm.flexValueId.value = primaryKey;
    <%--document.mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";--%>
        var url = "/servlet/com.sino.ams.newasset.servlet.AmsMisTagChgServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transNo=" + primaryKey;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "amsMisTagChg", popscript);
    }
</script>