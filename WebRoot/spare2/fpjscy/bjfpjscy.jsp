<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-29
  Time: 11:28:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件分配接收差异</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("备件分配接收差异")
</script>
<jsp:include page="/message/MessageProcess"/>
<body onload="do_drop()" leftmargin="1" topmargin="1" onkeydown="autoExeFunction('do_Search()');">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
%>
<form action="/servlet/com.sino.ams.spare2.fpjscy.servlet.AmsBjFpJsCyServlet" name="mainForm" method="post">
    <table border="0" width="100%" align="left" style="position:absolute;top:20px" class="queryHeadBg">
        <tr>
            <td align="right" width="12%">单据号：</td>
            <td align="left"><input type="text" name="transNo"  style="width:80%"
                                    value="<%=StrUtil.nullToString(request.getParameter("transNo"))%>"></td>
            <td align="right" width="10%">单据状态：</td>
            <td height="22"><select name="transStatus" id="transStatus"
                                                style="width:80%"><%=request.getAttribute(WebAttrConstant.TRANS_STATUS)%>
            </select></td>
            <td width=10% align="left">
                <img src="/images/eam_images/search.jpg" alt="查询"
                     onClick="do_Search(); return false;">
            </td>
        </tr>
        <tr>
            <td height="22" align="right" width="12%">创建日期：</td>
            <td height="22" width="25%"><input type="text" name="fromDate" value="<%=reqParser.getParameter("fromDate")%>"
                                   style="width:80%" title="点击选择开始日期" readonly class="readonlyInput"
                                   onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td height="22" align="right" width="10%">到</td>
            <td height="22" width="25%"><input type="text" name="toDate" value="<%=reqParser.getParameter("toDate")%>"
                                   style="width:80%" title="点击选择截止日期" readonly class="readonlyInput"
                                   onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>
            <td width=10% align="left"><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export()"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">

    <div style="height:362;width:100%;position:absolute;top:43px;left:1px;margin-left:0px"
         align="left">
        <table width="838" align="left" border="1" cellpadding="2" cellspacing="0"
               style="position:absolute;left:1px;top:31px" class="headerTable">

            <tr>
                <td height="22" width="10%" align="center">单据号</td>
                <td height="22" width="7%" align="center">创建人</td>
                <td height="22" width="7%" align="center">创建时间</td>
                <td height="22" width="10%" align="center">单据状态</td>
            </tr>
        </table>
    </div>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:362;width:855;position:absolute;top:98px;left:1px;margin-left:0px"
         align="left">
        <table width="100%" border="1" bordercolor="#666666" id="dataTab">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR"
                onclick="do_ShowDetail('<%=row.getValue("TRANS_ID")%>')">
                <td height="22" width="10%" align="center"><%=row.getValue("TRANS_NO")%>
                </td>
                <td height="22" width="7%"><%=row.getValue("CREATED_USER")%>
                </td>
                <td height="22" width="7%"><%=row.getValue("CREATION_DATE")%>
                </td>
                <td height="22" width="10%" align="center"><%=row.getValue("TRANS_STATUS_NAME")%>
                </td>
            </tr>
            <%
                }         }

            %>
        </table>
    </div>
    <%
    %>
    <input type="hidden" name="transId">
</form>
<div style="position:absolute;top:460px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainForm.submit();
    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_ShowDetail(transId) {

        var url = "/servlet/com.sino.ams.spare2.fpjscy.servlet.AmsBjFpJsCyServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + transId;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "instrum", popscript);

    }
    function do_drop() {
        var transStatus = document.getElementById("transStatus");
        dropSpecialOption(transStatus, 'CREATE')
    }
</script>