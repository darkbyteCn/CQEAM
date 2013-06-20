<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-11-12
  Time: 11:08:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String fromDate = StrUtil.nullToString(request.getParameter("fromDate"));
    String toDate = StrUtil.nullToString(request.getParameter("toDate"));
    String workorderObjectName = parser.getParameter("workorderObjectName");
//    String objectCategory = parser.getParameter("objectCategory");
    String objectCategory = (String) request.getAttribute(WebAttrConstant.EQUIPMENT_OPTION);
%>
<%=WebConstant.WAIT_TIP_MSG%>
<body onkeydown="autoExeFunction('do_search()')">
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("工单统计--地点")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <input type="hidden" name="act">
        <tr>
            <td width="10%" align="right">地点简称：</td>
            <td width="20%"><input style="width:85%" type="text" name="workorderObjectName"
                                   value="<%=workorderObjectName%>"><a href="#" onclick="chooseSit()" title="点击选择地点"
                                                                       class="linka">[…]</a></td>
            <td width="10%" align="right">地点专业:</td>
            <td width="20%"><select name="objectCategory" style="width:85%"><%=objectCategory%>
            </select></td>
            <td width="10%" align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'"
                                                onclick="do_search();" alt="查询"></td>
        </tr>
        <tr>
            <td align="right">时间：</td>
            <td align="left"><input style="width:85%" type="text" readOnly name="fromDate" value="<%=fromDate%>"
                                    class="input2" onclick="gfPop.fStartPop(fromDate,toDate);"><img
                    src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fStartPop(fromDate,toDate);"></td>
            <td align="right">到：</td>
            <td align="left"><input type="text" name="toDate" value="<%=toDate%>" readOnly style="width:85%"
                                    class="input2" onclick="gfPop.fEndPop(fromDate,toDate);"><img
                    src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(fromDate,toDate);"></td>
            <td align="center"><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'"
                                    onclick="do_Export();" alt="导出到Excel">
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("地点编号", "地点简称", "新建工单数", "扩容工单数", "巡检工单数","维修工单数", "搬迁工单数",  "减容工单数", "替换工单数", "工单总数");
        var widthArr = new Array("14%", "22%", "8%", "8%", "8%", "8%", "8%", "8%", "8%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:1px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
//                System.out.println("1");
                    int qtyA = 0;
                    int qtyB = 0;
                    int qtyC = 0;
                    int qtyD = 0;
                    int qtyE = 0;
                    int qtyF = 0;
                    int qtyG = 0;
                    int sumQty = 0;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                        qtyA = Integer.parseInt(row.getStrValue("TMPT_A"));
                        qtyB = Integer.parseInt(row.getStrValue("TMPT_B"));
                        qtyC = Integer.parseInt(row.getStrValue("TMPT_C"));
                        qtyD = Integer.parseInt(row.getStrValue("TMPT_D"));
                        qtyE = Integer.parseInt(row.getStrValue("TMPT_E"));
                        qtyF = Integer.parseInt(row.getStrValue("TMPT_F"));
                        qtyG = Integer.parseInt(row.getStrValue("TMPT_G"));
                        sumQty = qtyA + qtyB + qtyC + qtyD + qtyE + qtyF + qtyG;
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="14%" align="center"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td width="22%"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_A")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_B")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_C")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_D")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_E")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_F")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_G")%>
                </td>
                <td width="8%" align="center"><font color="#0033FF"><%=sumQty%>
                </font></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
    <%
        if (rows != null && rows.getSize() > 0) {
    %>
</form>
<div style="left:0; right:20">
    <%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%}%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.net.reportforms.servlet.SitusStatisticsServlet";
        document.mainFrm.submit();
    }

    function do_Export() {                  //导出execl
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.net.reportforms.servlet.SitusStatisticsServlet";
        mainFrm.submit();
    }

    function chooseSit() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
        var dialogWidth = 47.5;
        var dialogHeight = 30;
        var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (projects) {
            dto2Frm(projects[0], "mainFrm");
        }
    }
</script>