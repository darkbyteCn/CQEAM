<%--
  User: zhoujs
  Date: 2007-11-5
  Time: 16:32:01
  Function:工单监控
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<head>
    <title>工单监控</title>
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

</head>
<html>
<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="do_check()">

<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String workorderBatch = reqParser.getParameter("workorderBatch");
    String workorderBatchName = reqParser.getParameter("workorderBatchName");
    String workorderNo = reqParser.getParameter("workorderNo");
    String action = reqParser.getParameter("act");
%>

<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.OrderProcessServlet">
<script type="text/javascript">
    printTitleBar("工单监控")
</script>
    <jsp:include page="/message/MessageProcess"/>
    <table width="100%" border=0 cellpadding="2" cellspacing="0" class="queryTable">
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="groupId" value="">
    <input type="hidden" name="toexcel">
    <tr>
        <td>
            <table width="99%" align='right' bordercolor="#666666" bordercolor="#666666" border=0>
                <tr>
                    <td align="right" width="6%"> 公司：</td>
                    <td width="15%">
                        <select style="width:100%"         class="select_style1"
                                name="organizationId"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%>
                        </select>
                    </td>
                    <td align="right" width="8%">工单批号：</td>
                    <td width="20%"><input style="width:80%" type="text" class="input_style1"
                                           name="workorderBatch"
                                           value="<%=workorderBatch%>"> <a
                            href="#" title="点击选择任务" class="linka" onclick="showBatch()">[…]</a></td>
                    <td align="right" width="9%">任务名称：</td>
                    <td width="15%"><input style="width:100%" type="text"   class="input_style1"
                                           name="workorderBatchName"
                                           value="<%=workorderBatchName%>">
                    </td>
                    <td align="right" width="8%">工单号：</td>
                    <td width="15%"><input style="width:100%" type="text"  class="input_style1"
                                           name="workorderNo"
                                           value="<%=workorderNo%>"></td>
                    <td align="right" width="6%"><a style="cursor:'hand'"><img src="/images/eam_images/search.jpg"
                                                                               onClick="do_SearchOrder(); return false;"></a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>


<script type="text/javascript">
    var columnArr = new Array("工单批号", "任务名称", "工单号", "工单类型", "地点编号", "地点简称", "任务登记", "任务审批", "工单下发", "已下载", "已扫描上传", "已归档", "已撤销");
    var widthArr = new Array("8%", "7%", "10%", "6%", "8%", "12%", "6%", "6%", "6%", "5%", "8%", "5%", "5%");
    printTableHead(columnArr, widthArr);
</script>

<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:310px;width:100%;left:1px;margin-left:0px" align="left">
    <table width="100%" border="1" bordercolor="#666666">

        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);

        %>
        <tr class="dataTR" height="22" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>'); return false;">
            <td width="8%"><%=row.getValue("BATCH_NO")%>
            </td>
            <td width="7%"><%=row.getValue("WORKORDER_BATCH_NAME")%>
            </td>
            <td width="10%"><%=row.getValue("WORKORDER_NO")%>
            </td>
            <td width="6%"><%=row.getValue("WORKORDER_TYPE_DESC")%>
            </td>
            <td width="8%"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
            </td>
            <td width="12%"><%=row.getValue("WORKORDER_OBJECT_LOCATION")%>
            </td>
            <td width="6%"><img style="display:<%=row.getStrValue("PROCESS1").equals("0")?"none":""%>"
                                src="/images/bar.gif" height="12" width="<%=row.getValue("PROCESS1")%>%"></td>
            <td width="6%"><img style="display:<%=row.getStrValue("PROCESS2").equals("0")?"none":""%>"
                                src="/images/bar.gif" height="12" width="<%=row.getValue("PROCESS2")%>%"></td>
            <td width="6%"><img style="display:<%=row.getStrValue("PROCESS3").equals("0")?"none":""%>"
                                src="/images/bar.gif" height="12" width="<%=row.getValue("PROCESS3")%>%"></td>
            <td width="5%"><img style="display:<%=row.getStrValue("PROCESS4").equals("0")?"none":""%>"
                                src="/images/bar.gif" height="12" width="<%=row.getValue("PROCESS4")%>%"></td>
            <td width="8%"><img style="display:<%=row.getStrValue("PROCESS5").equals("0")?"none":""%>"
                                src="/images/bar.gif" height="12" width="<%=row.getValue("PROCESS5")%>%"></td>
            <td width="5%"><img style="display:<%=row.getStrValue("PROCESS6").equals("0")?"none":""%>"
                                src="/images/bar.gif" height="12" width="<%=row.getValue("PROCESS6")%>%"></td>
            <td width="5%"><img style="display:<%=row.getStrValue("PROCESS7").equals("0")?"none":""%>"
                                src="/images/bar.gif" height="12" width="<%=row.getValue("PROCESS7")%>%"></td>

            <%

                }   }
            %>
    </table>
</div>
 </form>
<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>


<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>

<script language="javascript">

    function do_SearchOrder() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_ShowDetail(WORKORDER_NO)
    {
        var url = "<%=URLDefineList.ORDER_DETAIL_SERVLET%>";
        var screenHeight = window.screen.height - 100;
        var screenWidth = window.screen.width;
        var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no";
        url = "/public/wait.jsp?title=工单详细信息&src="+url+"&act=<%=WebActionConstant.DETAIL_ACTION%>&WORKORDER_NO=" + WORKORDER_NO;
        window.open(url, "", winstyle);
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