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
  Date: 2007-10-28
  Time: 23:21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>仪器仪表返还页面</title>
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
<body  onkeydown="autoExeFunction('do_Search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    AmsInstrumentInfoDTO dto = (AmsInstrumentInfoDTO) request.getAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO);
%>
<form action="/servlet/com.sino.ams.instrument.servlet.AmsInstrumentReturnServlet" name="mainForm" method="post">
    <script type="text/javascript">
    printTitleBar("仪器仪表返还页面")
   </script>
    <table border="0" width="100%"  class="queryHeadBg">
        <tr>
            <td align="right" width="10%">单据号：</td>
            <td align="left" width="20%"><input type="text" name="transNo"    style="width:100%"
                                    value="<%=StrUtil.nullToString(request.getParameter("transNo"))%>"></td>
            <!--<td align="right" width="10%"></td>-->
            <!--<td width="30%">-->
                <%--<select name="transStatus" id="transStatus"--%>
                                                <%--style="width:80%"><%=request.getAttribute(WebAttrConstant.TRANS_STATUS)%>--%>
            <%--</select>--%>
            <!--</td>-->
            <td  align="right" width="10%">创建日期：</td>
            <td><input type="text" name="fromDate" value="<%=reqParser.getParameter("fromDate")%>"
                                   style="width:75%" title="点击选择开始日期" readonly class="readonlyInput"
                                   onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td  align="right" width="5%">到：</td>
            <td><input type="text" name="toDate" value="<%=reqParser.getParameter("toDate")%>"
                                   style="width:75%" title="点击选择截止日期" readonly class="readonlyInput"
                                   onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>
            <td width=10% align="left"><img src="/images/eam_images/search.jpg" alt="查询仪器仪表返还单" onClick="do_Search(); return false;"><img src="/images/eam_images/new_add.jpg" alt="新增仪器仪表还单" onClick="do_Create(''); return false;">
                <%--<img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export()">--%>
                
                </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
      <script type="text/javascript">
        var columnArr = new Array("单据号", "借用人", "确认人","返还日期","确认日期","单据状态");
        var widthArr = new Array("10%","7%","7%","7%","10%","10%");
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

                <td height="22" width="7%"><%=row.getValue("RNAME")%>
                </td>
                <td height="22" width="7%"><%=row.getValue("QNAME")%>
                </td>

                <td height="22" width="7%" align="center"><%=row.getValue("RETURN_DATE")%>
                </td>
                <td height="22" width="10%" align="center"><%=row.getValue("CONFIRM_DATE")%>
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
        var url = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentReturnServlet?act=<%=WebActionConstant.NEW_ACTION%>" ;
//        var popscript = 'width=1020,height=700,top=100,left=100,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=yes';
//        window.open(url, 'planWin', popscript);
         var screenHeight = window.screen.height - 100;
        var screenWidth = window.screen.width - 10;
        var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no";
        window.open(url, "", winstyle);
    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_ShowDetail(transId) {
        var url = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentReturnServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + transId;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "instrum", popscript);

    }
    function do_drop() {
        var transStatus = document.getElementById("transStatus");
        dropSpecialOption(transStatus, 'IN_PROCESS;REJECTED;CONFIRMD;ASSIGNED')
    }
</script>