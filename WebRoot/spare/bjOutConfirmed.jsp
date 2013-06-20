<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<%
    String transType = StrUtil.nullToString(request.getParameter("transType"));
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<html>
<head><title>备件调出确认</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
</head>

<body onkeydown="do_check()">
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.spare.servlet.BjOutConfirmedServlet">
    <script type="text/javascript">
        printTitleBar("备件调出确认");
    </script>
    <input type="hidden" name="flexValueId" value="">
    <input type="hidden" name="transType" value="<%=transType%>">
    <input type="hidden" name="transId" value="">
    <input type="hidden" name="act" value="">
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" class="queryHeadBg">
        <tr>
            <td width="10%" height="22" align="right">单据编号：</td>
            <td width="17%" height="22"><input type="text" name="transNo" style="width:80%" value="<%=parser.getParameter("transNo")%>"></td>
            <td width="10%" height="22" align="right">单据状态：</td>
            <td width="17%" height="22"><select name="transStatus" style="width:80%"><option value="" >--请选择--</option><option value = "ALLOTING" <%=parser.getParameter("transStatus").equals("ALLOTING")? "selected":""%> >待调出</option></select>
            </td>
            <td width="10%" height="22" align="right"></td>
            <td width="17%"
                height="22"><%--<select name="fromObjectNo" style="width:100%"><%=request.getAttribute(WebAttrConstant.INV_OPTION)%></select>--%></td>
        </tr>
        <tr>
            <td height="22" align="right">创建日期：</td>
            <td height="22"><input type="text" name="fromDate" value="<%=parser.getParameter("fromDate")%>" style="width:80%" title="点击选择开始日期"
                                   readonly class="readonlyInput" onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td height="22" align="right">到</td>
            <td height="22"><input type="text" name="toDate" value="<%=parser.getParameter("toDate")%>" style="width:80%" title="点击选择截止日期" readonly
                                   class="readonlyInput" onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>
            <td height="22" colspan="2" align="center"><img src="/images/eam_images/search.jpg" onclick="do_Search();" alt="查询"></td>
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("单据编号", "创建人", "创建日期", "单据状态","调入地市");
        var widthArr = new Array("20%", "10%", "10%", "10%","20%");
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
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'"
                onclick="do_ShowDetail('<%=row.getValue("TRANS_ID")%>')">
                <td width="20%" align="center"><%=row.getValue("TRANS_NO")%></td>
                <%--<td width="30%"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>--%>
                <td width="10%" align="left"><%=row.getValue("CREATED_USER")%></td>
                <td width="10%" align="center"><%=String.valueOf(row.getValue("CREATION_DATE"))%></td>
                <td width="10%" align="center"><%=row.getValue("ORDER_STATUS_NAME")%></td>
                <td width="20%" align="center"><%=row.getValue("TO_ORGANIZATION_NAME")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div style="left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no"
        frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<script language="javascript">

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.submit();
    }


    function do_ShowDetail(primaryKey) {
        var transType = document.mainFrm.transType.value;
        var url = "com.sino.ams.spare.servlet.BjOutConfirmedServlet";
        url += "?act=<%=WebActionConstant.DETAIL_ACTION%>&transId=" + primaryKey;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "bjOrder", popscript);
    }

    function do_check() {
        if (event.keyCode == 13) {
            do_Search();
        }
    }
</script>