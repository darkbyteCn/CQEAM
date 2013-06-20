<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.flow.constant.ReqAttributeList" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%--
  Created by wwb.
  User: demo
  Date: 2006-12-6
  Time: 10:34:50
--%>
<html>
<head>
    <title>发件箱</title>
    <link href="/WebLibary/css/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <%
        RequestParser rp = new RequestParser();
        rp.transData(request);
        String msg = StrUtil.nullToString(request.getAttribute("GET_BACK_MSG"));
    %>
    <script>
        var msg = '<%=msg%>';
        if (msg != '') {
            alert(msg);
        }
    </script>
</head>

<body topmargin="0" leftmargin="0" onkeydown="enterQuery();">
<form action="/servlet/com.sino.flow.servlet.OutboxServlet" method="post" name="mainForm">
    <script type="text/javascript">
        printTitleBar("发件箱");
    </script>
    <input type="hidden" name=process value="">
    <input type="hidden" name=actId value="">

    <table style="width:100%;" class="queryHeadBg">
        <tr>
            <td width=8%>转发日期：</td>
            <td width=17%>
                <input type="text" name="fromDate" style="width:80%" value="<%=rp.getParameter("fromDate")%>">
                <img border='0' src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fEndPop('',fromDate);"/>
            </td>
            <td width=3%>到：</td>
            <td width=17%>
                <input type="text" name="toDate" style="width:80%" value="<%=rp.getParameter("toDate")%>">
                <img border='0' src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate,toDate);"/>
            </td>
            <td align=right width=10%>单据号：</td>
            <td width=15%>
                <input type="text" name="applyNumber" style="width:80%" value="<%=rp.getParameter("applyNumber")%>">
            </td>
            <td width=10%>流程类型：</td>
            <td width=15%>
                <input type="text" name="procName" style="width:80%" value="<%=rp.getParameter("procName")%>">
            </td>
            <td width="5%" align="center">
                <img src="/images/eam_images/search.jpg" alt="查询" onClick="query(); return false;"
                     style="float: right"></td>
        </tr>
    </table>

    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table class="headerTable" border=1 width="100%" style="border-collapse:collapse"
               cellspacing="0">
            <tr height="20px">
                <td align="center" width="20%">流程类型</td>
                <td align="center" width="20%">单据号</td>
                <td align="center" width="15%">转发日期</td>
                <td align="center" width="15%">创建人</td>
                <td align="center" width="15%">当前办理人</td>
                <td align="center" width="10%">操作</td>
            </tr>
        </table>
    </div>

    <div style="overflow-y:scroll;width:100%;height:360px">
        <table style="width:100%;" border=1 borderColor="#9CC4FF" cellpadding="1" cellspacing="0" >
            <%
                RowSet rows = (RowSet) request.getAttribute(ReqAttributeList.OUTBOX_DATA);
                if (rows != null && !rows.isEmpty()) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
//                        if(StrUtil.isNotEmpty(row.getValue("CUR_USER"))){
            %>
            <tr height=20 class="trBright" style="cursor:hand"  onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'"
                onclick="showDetail('<%=row.getValue("APP_ID")%>','<%=row.getValue("QUERY_PATH")%>')">
                <td width=20%><%=row.getValue("PROC_NAME")%></td>
                <td width=20%><%=row.getValue("APPLY_NUMBER")%></td>
                <td width=15% align="center"><%=row.getValue("FROM_DT")%></td>
                <td width=15%><%=row.getValue("CREATED_USER")%></td>
                <td width=15%><%=row.getValue("CUR_USER")%></td>
                <td width=10% align=center><a class="linka" href="#" onclick="getBack('<%=row.getValue("ACTID")%>')">[取回]</a>
                </td>

            </tr>
            <%
//                        }
                    }
                }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<script>
    function query() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainForm.submit();
    }
    function showDetail(appId, path) {
        //        var url = path+appId;
        //        style = 'width=1024,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=auto, resizable=yes,location=no, status=no';
        //        window.open(url, "detailWin", style);
    }
    function enterQuery() {
        if (event.keyCode == 13) {
            query();
        }
    }
    function getBack(actId) {
        document.mainForm.process.value = "GETBACK";
        document.mainForm.actId.value = actId;
        document.mainForm.submit();
    }
</script>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>
