<%--
  Created by IntelliJ IDEA.
  User: V-jiachuanchuan
  Date: 2007-11-9
  Time: 12:00:31
  Function:按任务批查询工单
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>工单任务批查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>

    <%
        RequestParser reqParser = new RequestParser();
        reqParser.transData(request);
        String workorderBatch = reqParser.getParameter("workorderBatch");
        String workorderBatchName = reqParser.getParameter("workorderBatchName");
        String action = reqParser.getParameter("act");
    %>

</head>

<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="do_check()">

<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.QueryIntegrationServlet">
    <script type="text/javascript">
        printTitleBar("工单任务批查询")
    </script>
    <table width="100%" border=0 cellpadding="2" cellspacing="0" class="queryTable" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <input type="hidden" name="act" value="<%=action%>">
        <input type="hidden" name="queryType" value="<%=DictConstant.WOR_STATUS_BATCH%>">
        <input type="hidden" name="groupId" value="">
        <jsp:include page="/message/MessageProcess"/>
        <input type="hidden" name="toexcel">
        <tr>
            <td>
                <table width="99%" align='right' bordercolor="#666666" bordercolor="#666666">
                    <tr>
                        <td align="left" width="22%">工单批号：<input style="width:60%" type="text" name="workorderBatch"  class="input_style1"
                                                                value="<%=workorderBatch%>">
                            <a href="#" title="点击选择任务" class="linka" onclick="showBatch()">[…]</a></td>
                        <td align="left" width="25%">任务名称：<input style="width:60%" type="text" name="workorderBatchName" class="input_style1"
                                                                 value="<%=workorderBatchName%>"></td>
                        <td align="center" width="6%"><a style="cursor:'hand'"><img src="/images/eam_images/search.jpg"
                                                                                    onClick="do_SearchOrder(); return false;"></a>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("工单批号", "任务名称", "任务描述", "创建人", "状态");
        var widthArr = new Array("20%", "20%", "24%", "18%", "18%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        boolean hasData = (rows != null && !rows.isEmpty());
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:72%;width:100%;left:1px;margin-left:0px" align="left">
        <table width="100%" border="1" bordercolor="#666666">

            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);

            %>
            <tr class="dataTR" height="22"
                onclick="do_ShowDetail('<%=row.getValue("WORKORDER_BATCH")%>'); return false;">
                <td width="20%"><%=row.getValue("WORKORDER_BATCH")%>
                </td>
                <td width="20%"><%=row.getValue("WORKORDER_BATCH_NAME")%>
                </td>
                <td width="24%"><%=row.getValue("REMARK")%>
                </td>
                <td width="18%"><%=row.getValue("CREATE_USER")%>
                </td>
                <td width="18%"><%=row.getValue("STATUS")%>
                </td>
                <%



                     }  }



                %>
        </table>
    </div>
          </form>
<%
	if(hasData){
%>
<div style="position:absolute;top:90%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>

</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>

<script language="javascript">

    function do_SearchOrder() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_ShowDetail(WORKORDER_BATCH) {
        mainFrm.action = "/servlet/com.sino.ams.workorder.servlet.QueryIntegrationServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&WORKORDER_BATCH=" + WORKORDER_BATCH;
        mainFrm.submit();
    }

    function doChecked() {

    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchOrder();
        }
    }
    function showBatch() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BATCH%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Batchs = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (Batchs) {
            var Batch = null;
            for (var i = 0; i < Batchs.length; i++) {
                Batch = Batchs[i];
                dto2Frm(Batch, "mainFrm");
            }
        }
    }

</script>