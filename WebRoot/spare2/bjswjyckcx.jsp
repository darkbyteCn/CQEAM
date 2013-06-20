<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%--
  Created by HERRY.
  Date: 2008-6-24
  Time: 16:01:55
--%>
<html>
 <head><title>备件实物借用出库查询</title>
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
<body onkeydown="autoExeFunction('do_Search()');">
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.spare2.servlet.BjswyjckcxServlet">
    <script type="text/javascript">
        printTitleBar("备件实物借用出库查询");
    </script>
    <input type="hidden" name="act" value="">
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" class="queryHeadBg">
        <tr>
            <td width="10%" height="22" align="right">调拨单号：</td>
            <td width="17%" height="22"><input type="text" name="transNo" style="width:100%"
                                               value="<%=parser.getParameter("transNo")%>"></td>
            <td width="10%" height="22" align="right">仓库名称：</td>
                    <td width="17%" height="22"><select name="fromObjectNo" style="width:100%"><%=request.getAttribute(WebAttrConstant.INV_OPTION)%></select></td>

            <td width="10%" height="22" align="right">状态：</td>
            <td width="17%" height="22"><select name="transStatus"
                                                style="width:100%"><%=request.getAttribute(WebAttrConstant.TRANS_STATUS)%>
            </select></td>

        </tr>
        <tr>
            <td height="22" align="right">发运日期：</td>
            <td height="22"><input type="text" name="fromDate" value="<%=parser.getParameter("fromDate")%>"
                                   style="width:80%" title="点击选择开始日期" readonly class="readonlyInput"
                                   onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td height="22" align="right">到</td>
            <td height="22"><input type="text" name="toDate" value="<%=parser.getParameter("toDate")%>"
                                   style="width:80%" title="点击选择截止日期" readonly class="readonlyInput"
                                   onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>

            <td width="10%" height="22" align="right"></td>
            <td height="22" align="left"><img src="/images/eam_images/search.jpg" onclick="do_Search();" alt="查询"></td>
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array( "申领单号", "调拨单号", "发运公司", "设备名称", "型号", "数量", "发运时间", "状态", "备注");
        var widthArr = new Array( "12%","12%", "11%", "18%", "15%", "8%", "8%", "7%", "7%");
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
                    >

                <td width="12%" align="center"><%=row.getValue("TRANS_NO")%>
                </td>
                <td width="12%" align="center"><%=row.getValue("ALLOCATE_NO")%>
                </td>
                <td width="11%"><%=row.getValue("ORGNIZATION_NAME")%>
                </td>
                <td width="18%" align="left"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="15%" align="center"><%=String.valueOf(row.getValue("ITEM_SPEC"))%>
                </td>
                <td width="8%" align="center"><%=row.getValue("FREIGHT_QUANTITY")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("FREIGHT_DATE")%>
                </td>
                <td width="7%" align="center"><%=row.getValue("STATUS")%>
                </td>
                <td width="7%" align="center"><%=row.getValue("REMARK")%>
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

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }


    
</script>