<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO" %>
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
  Date: 2007-12-16
  Time: 20:52:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>业务单据查询</title>
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
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<body>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet"/>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')" onload="do_drop()">
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) request.getAttribute("AMSBJTRANSNOHDTO");
//    out.print(dto);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<form action="/servlet/com.sino.ams.spare.query.servlet.AmsBjTransQueryServlet" name="mainForm" method="post">

    <script type="text/javascript">
        printTitleBar("备件业务单据查询")
    </script>
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
        <tr>
            <td align="right" width="15%">单据号：</td>
            <td align="left" width="22%"><input type="text" name="transNo" style="width:80%"
                                                value="<%=dto.getTransNo()%>"></td>
            <td align="right" width="10%">单据状态：</td>
            <td width="22%"><select name="transStatus" id="transStatus"
                                    style="width:100%"><%=request.getAttribute(WebAttrConstant.TRANS_STATUS)%>
            </select></td>
            <td width=10% align="left"> 单据类型：</td>
            <td width="22%"><select name="transType" id="transType"
                                    style="width:100%"><%=request.getAttribute(WebAttrConstant.TRANS_TYPE)%>
            </select></td>

        </tr>
        <tr>
            <td width="15%" align="right">创建日期：</td>
            <td><input type="text" name="fromDate" value="<%=dto.getFromDate()%>"
                       style="width:80%" title="点击选择开始日期" readonly class="readonlyInput"
                       onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td width="7%" align="right">到：</td>
            <td><input type="text" name="toDate" value="<%=dto.getToDate()%>"
                       style="width:80%" title="点击选择截止日期" readonly class="readonlyInput"
                       onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>
            <td width=10% align="left">

            </td>

            <td width=10% align="center"><img src="/images/button/query.gif" alt="查询"
                                              onClick="do_search(); return false;"><img src="/images/button/toExcel.gif"
                                                                                        alt="导出数据"
                                                                                        onclick="do_export()"></td>
        </tr>
    </table>
    <input type="hidden" name="transId" value="<%=dto.getTransId()%>">
    <input type="hidden" name="act" value="">
    <input type="hidden" name="transType" value="">
    <script type="text/javascript">
        var columnArr = new Array("单据号", "创建人", "创建日期", "单据状态", "单据类型");
        var widthArr = new Array("20%", "10%", "10%", "10%", "10%");
        printTableHead(columnArr, widthArr);

    </script>
    <div style="overflow-y:scroll;left:1px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'"
                onclick="do_ShowDetail('<%=row.getValue("TRANS_ID")%>','<%=row.getValue("TRANS_TYPE")%>')">
                <td width="20%" align="center"><%=row.getValue("TRANS_NO")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("CREATED_USER")%>
                </td>
                <td width="10%" align="center"><%=String.valueOf(row.getValue("CREATION_DATE"))%>
                </td>
                <td width="10%" align="center"><%=row.getValue("TRANS_STATUS_NAME")%>
                </td>
                <td width="10%" align="center"><%=row.getValue("TRANS_TYPE_NAME")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>

</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainForm.submit();
    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_drop() {
        var transType = document.getElementById("transType")   ;
        var transStatus = document.getElementById("transStatus");
        dropSpecialOption(transType, 'BJBFS');
//        dropSpecialOption(transStatus, 'APPROVED');
    }
    function do_ShowDetail(transId, transType) {
        mainForm.transId.value = transId;
        mainForm.transType.value = transType;
        var url = "/servlet/com.sino.ams.spare.query.servlet.AmsBjTransQueryServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + transId + "&transType=" + transType;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "instrum", popscript);
    }
</script>