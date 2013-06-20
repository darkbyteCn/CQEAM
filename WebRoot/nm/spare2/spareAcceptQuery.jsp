<%--
  Created by HERRY.
  Date: 2008-2-18
  Time: 19:52:49
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<html>
<%
    String transType = StrUtil.nullToString(request.getParameter("transType"));
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<head><title>备件接收</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<body>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.nm.spare2.accept.servlet.SpareAcceptServlet">
    <script type="text/javascript">
        printTitleBar("备件接收");
    </script>
    <input type="hidden" name="flexValueId" value="">
    <input type="hidden" name="transType" value="<%=transType%>">
    <input type="hidden" name="transId" value="">
    <input type="hidden" name="act" value="">
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">申领单号：</td>
            <td width="17%"><input type="text" name="transNo" style="width:100%"
                                               value="<%=parser.getParameter("transNo")%>"></td>
            <td width="10%" align="right">发运公司：</td>
            <td width="17%"><select name="fromOrganizationId"
                                                style="width:100%"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%>
            </select></td>
            <td width="10%" align="right"></td>
            <td width="17%"  align="left"><img src="/images/button/query.gif" onclick="do_Search();" alt="查询"></td>
        </tr>
        <tr>
            <td align="right">发运日期：</td>
            <td><input type="text" name="fromDate" value="<%=parser.getParameter("fromDate")%>"
                                   style="width:80%" title="点击选择开始日期" readonly class="readonlyInput"
                                   onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td align="right">至：</td>
            <td><input type="text" name="toDate" value="<%=parser.getParameter("toDate")%>"
                                   style="width:80%" title="点击选择截止日期" readonly class="readonlyInput"
                                   onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>

            <td align="center"><img src="/images/button/accept.gif" alt="已收到" id="img4"
                                                onClick="do_receive();"></td>
            <td align="left"><img src="/images/button/noaccept.gif" alt="未收到" id="img5"
                                              onClick="no_receive();"></td>
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("checkbox", "申领单号", "调拨单号", "发运公司", "名称", "型号", "数量", "发运时间");
        var widthArr = new Array("3%", "15%", "15%", "14%", "16%", "15%", "6%", "10%");
        printTableHead(columnArr, widthArr);

    </script>
    <div style="overflow-y:scroll;left:1px;width:100%;height:310px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="3%" align="center"><input type="checkbox" name="subCheck" id="subCheck<%=i%>"
                                                     value="<%=row.getValue("FREIGHT_ID")%>;<%=row.getValue("ITEM_CODE")%>;<%=row.getValue("DETAIL_ID")%>;<%=row.getValue("FREIGHT_QUANTITY")%>;<%=row.getValue("TRANS_ID")%>"
                                                     style="height:20px;margin:0;padding:0">
                </td>
                <td width="15%" align="center"><%=row.getValue("TRANS_NO")%>
                </td>
                <td width="15%" align="center"><%=row.getValue("ALLOCATE_NO")%>
                </td>
                <td width="14%"><%=row.getValue("ORGNIZATION_NAME")%>
                </td>
                <td width="16%" align="left"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="6%" align="right"><%=row.getValue("FREIGHT_QUANTITY")%>
                </td>
                <td width="10%" align="center"><%=row.getValue("FREIGHT_DATE")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div style="left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script language="javascript">
    var working = false;
    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }

    function do_ShowDetail(primaryKey, transType) {
        //	document.mainFrm.flexValueId.value = primaryKey;
    <%--document.mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";--%>
        var url = "/servlet/com.sino.ams.spare2.accept.servlet.SpareAcceptServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + primaryKey + "&transType=" + transType;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "bjOrder", popscript);
    }

    function do_receive() {
        var checkCount = getCheckedBoxCount("subCheck");
        if (checkCount < 1) {
            alert("请选择一条记录，然后操作！");
        } else {
            if (working) {
                alert("正在处理，请稍候！");
            } else {
                working = true;
                mainFrm.act.value = "<%=WebActionConstant.RECEIVE_ACTION%>";
                mainFrm.submit();
            }
        }
    <%--mainFrm.act.value = "<%=WebActionConstant.RECEIVE_ACTION%>";--%>
    <%--mainFrm.submit();--%>
    }
    function no_receive() {
        var checkCount = getCheckedBoxCount("subCheck");
        if (checkCount < 1) {
            alert("请选择一条记录，然后操作！");
        } else {
            if (working) {
                alert("正在处理，请稍候！");
            } else {
                working = true;
                mainFrm.act.value = "NO_RECEIVE_ACTION";
                mainFrm.submit();
            }
        }
//       mainFrm.act.value = "NO_RECEIVE_ACTION";
//            mainFrm.submit();
    }
</script>