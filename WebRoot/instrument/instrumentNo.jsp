<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.instrument.dto.AmsInstrumentInfoDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-10-30
  Time: 17:09:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>仪器仪表单据查询页面</title>
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

<jsp:include page="/message/MessageProcess"/>

<body onload="do_drop()">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO);
%>
<form action="/servlet/com.sino.ams.instrument.servlet.AmsInstrumentNoServlet" name="mainForm" method="post">
    <script type="text/javascript">
    printTitleBar("仪器仪表单据查询页面")
</script>
    <table border="0" width="100%" class="queryHeadBg">
        <tr>
            <td align="right">单据号：</td>
            <td align="left" width="15%"><input type="text" name="transNo" style="width:100%"
                                                value="<%=StrUtil.nullToString(request.getParameter("transNo"))%>"></td>
            <td align="right">单据类型：</td>
            <td width="17%" height="22"><select name="transType"
                                                style="width:100%"><%=request.getAttribute("TRANS_TYPE")%>
            </select></td>
            <td align="right">单据状态：</td>
            <td width="17%" height="22"><select name="transStatus" id="transStatus"
                                                style="width:100%"><%=request.getAttribute(WebAttrConstant.TRANS_STATUS)%>
            </select></td>
            <td width=10% align="left">
                <img src="/images/eam_images/search.jpg" alt="查询仪器仪表借用"
                     onClick="do_Search(); return false;">
            </td>
            <td width=10% align="left"><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export()"></td>

        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">

      <script type="text/javascript">
        var columnArr = new Array("单据号", "借用人", "返还人","盘点人","单据类型","单据状态");
        var widthArr = new Array("10%","7%","7%","7%","7%","10%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px"
         align="left">
        <table width="100%" border="1" bordercolor="#666666" id="dataTab">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR"
                onclick="do_ShowDetail('<%=row.getValue("TRANS_ID")%>')">
                <input type="hidden" name="barcode1">
                <td height="22" width="10%" align="center"><%=row.getValue("TRANS_NO")%>
                </td>
                <td height="22" width="7%"><%=row.getValue("BNAME")%>
                </td>
                <td height="22" width="7%"><%=row.getValue("RNAME")%>
                </td>
                <td height="22" width="7%"><%=row.getValue("CKNAME")%>
                </td>
                <td height="22" width="7%"><%=row.getValue("TRANS_TYPE_NAME")%>
                </td>
                <td height="22" width="10%" align="center"><%=row.getValue("TRANS_STATUS_NAME")%>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <%
        }
    %>
    <input type="hidden" name="transId">
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
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
    function do_Create() {
        var url = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=<%=WebActionConstant.NEW_ACTION%>" ;
        var popscript = 'width=1020,height=700,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
        window.open(url, 'planWin', popscript);
    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_ShowDetail(transId) {

        var url = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentNoServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + transId;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "instrum", popscript);

    }
    function do_drop() {
        var transStatus = document.getElementById("transStatus");
        dropSpecialOption(transStatus, 'IN_PROCESS;REJECTED;CONFIRMD;ASSIGNED')
        //        dropSpecialOption(transStatus,'IN_PROCESS')
        //        dropSpecialOption(transStatus,'REJECTED')
    }
</script>