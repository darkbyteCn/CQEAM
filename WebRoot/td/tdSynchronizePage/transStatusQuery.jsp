<%--
   Created by   李轶
   Date:        2009-7-22
   Time:        17:22:55
   Function:    事务处理状态查询
--%>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>事务处理状态查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>

</head>


<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    String action = parser.getParameter("act");
    Row row = null;
    String transStatus = parser.getParameter("transStatus");
    String startDate = parser.getParameter("startDate");
    String endDate = parser.getParameter("endDate");
    // String transStatus = parser.getParameter("transStatus");
%>
<body onkeydown="autoExeFunction('do_search()')" leftmargin=0 topmargin=0>
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm"   method="post" action="/servlet/com.sino.td.synAssets.servlet.TdTransDisposeServlet">
    <script type="text/javascript">
        printTitleBar("事务处理状态查询")
    </script>
    <table bgcolor="#E9EAE9" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">

        <tr>
            <td width="10%" align="right">处理状态:</td>
            <td width="20%">
                <select name="transStatus" style="width:80%" align='left'>
                    <option value='4' <%if(transStatus.equals("4")){%>selected <%}%>>全部</option>
                    <option value='0' <%if(transStatus.equals("0")){%>selected <%}%> >尚未同步</option>
                    <option value='1' <%if(transStatus.equals("1")){%>selected <%}%>>正在同步</option>
                    <option value='2' <%if(transStatus.equals("2")){%>selected <%}%>>同步成功</option>
                    <option value='3' <%if(transStatus.equals("3")){%>selected <%}%>>同步出错</option>
                </select>
            </td>


            <td align="left" width="6%">时间：</td>
            <td align="left" style="width:20%">
                <input type="text" name="startDate" value="<%=startDate%>" style="width:80%" title="点击选择日期" readonly
                       class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"><img
                    src="/images/calendar.gif" alt="点击查询" onclick="gfPop.fStartPop(startDate, endDate);">
            </td>
            <td align="right" width="6%">到：</td>
            <td align="left" width="20%">
                <input type="text" name="endDate" value="<%=endDate%>" style="width:80%" title="点击选择日期" readonly
                       class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"><img src="/images/calendar.gif"
                                                                                              alt="点击查询"
                                                                                              onclick="gfPop.fEndPop(startDate, endDate);">
            </td>


            <td align="left" width="18%">
                <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
                <img src="/images/eam_images/export.jpg" alt="点击导出" onclick="do_Export();">
            </td>
        </tr>
</table>
<div style="position:absolute;top:48px;left:0px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" class="headerTable" border="1" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="10%" align="center">事务处理批号</td>
            <td width="35%" align="center">同步结果</td>
            <td width="10%" align="center">处理状态</td>
            <td width="15%" align="center">开始时间</td>
            <td width="15%" align="center">结束时间</td>
            <td width="15%" align="center">出错信息</td>

        </tr>
    </table>
</div>
<div style="overflow-y:scroll;position:absolute;top:71px;left:0px;width:100%;height:72%">
    <table width="100%" border="1" bordercolor="#666666">
        <%
            if (rows != null && rows.getSize() > 0) {
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="do_show('<%=row.getValue("BATCH_ID")%>')">
            <td width="10%" align="center"><%=row.getValue("BATCH_ID")%></td>
            <td width="35%" align="center"><%=row.getValue("REMARK")%></td>
            <td width="10%" align="center"><%=row.getValue("TRANS_STATUS")%></td>
            <td width="15%" align="center"><%=row.getValue("START_DATE")%></td>
            <td width="15%" align="center"><%=row.getValue("END_DATE")%></td>
            <td width="15%" align="center"><%=row.getValue("ERRMSG")%></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
<input type="hidden" name="act" value="<%=action%>">
</form>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm"
        scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script type="text/javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_Export(){
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }

function do_show(id) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    var url = "/servlet/com.sino.td.synAssets.servlet.TdTransDisposeServlet?act=DETAIL_ACTION&id=" + id ;
    var style = "width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
    window.open(url, 'orderWin', style);

}

</script>
