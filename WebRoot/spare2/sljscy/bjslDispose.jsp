<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-28
  Time: 15:29:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>备件申领处理情况</title>
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
  <body onkeydown="autoExeFunction('do_Search()');">
  <%=WebConstant.WAIT_TIP_MSG%>
  <%
      RequestParser reqParser = new RequestParser();
      reqParser.transData(request);
      String action = reqParser.getParameter("act");
  %>
  <form action="/servlet/com.sino.ams.spare2.bjslcy.servlet.AmsBjSlDisposeServlet" name="mainForm" method="post">
        <script type="text/javascript">
    printTitleBar("备件申领处理情况")
</script>
    <table border="0" width="100%" class="queryHeadBg">
        <tr>
            <td align="right" width="12%">单据号：</td>
            <td align="left"><input type="text" name="transNo"  style="width:80%"
                                    value="<%=StrUtil.nullToString(request.getParameter("transNo"))%>"></td>
            <td align="right" width="10%">设备名称：</td>
            <td height="22"><input type="text" name="itemName"  style="width:80%"
                                    value="<%=StrUtil.nullToString(request.getParameter("itemName"))%>"></td>
            <td width=10% align="left">
                <img src="/images/eam_images/search.jpg" alt="查询"
                     onClick="do_Search(); return false;">
            </td>
        </tr>
        <tr>
            <td height="22" align="right" width="12%">型号：</td>
            <td height="22" width="25%"><input type="text" name="itemSpec"  style="width:80%"
                                    value="<%=StrUtil.nullToString(request.getParameter("itemSpec"))%>"></td>
            <td height="22" align="right" width="10%">申领人：</td>
            <td height="22" width="25%"><input type="text" name="toUserName" style="width:80%"
                                                        readonly value="<%=StrUtil.nullToString(request.getParameter("toUserName"))%>"><a  href="#"
                            class="linka" style="cursor:'hand'" onclick="do_selectuser();">[…]</a></td>
            <input type = "hidden" name = "createdBy" value = "">
            <td width=10% align="left"></td>
        </tr>
        <tr>
            <td height="22" align="right" width="12%">申领单据日期：</td>
            <td height="22" width="25%"><input type="text" name="fromDate" value="<%=reqParser.getParameter("fromDate")%>"
                                   style="width:80%" title="点击选择开始日期" readonly class="readonlyInput"
                                   onclick="gfPop.fStartPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
            </td>
            <td height="22" align="right" width="10%">到：</td>
            <td height="22" width="25%"><input type="text" name="toDate" value="<%=reqParser.getParameter("toDate")%>"
                                   style="width:80%" title="点击选择截止日期" readonly class="readonlyInput"
                                   onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
            </td>
            <td width=10% align="left"><img src="/images/eam_images/export.jpg" alt="导出数据" onclick="do_export()"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">

       <script type="text/javascript">
        var columnArr = new Array("单据号", "单据日期", "申领人", "设备名称", "型号", "申请数量", "调拨仓库", "调拨数量", "归还数量", "实际<br>接收日期", "预计<br>归还日期");
        var widthArr = new Array("14%", "10%", "9%", "10%", "9%", "6%", "10%", "6%", "6%", "10%", "10%");
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
            <tr >
                <td height="22" width="14%"><%=row.getValue("TRANS_NO")%></td>
                <td height="22" width="10%"><%=row.getValue("TRANS_DATE")%></td>
                <td height="22" width="9%"><%=row.getValue("USERNAME")%></td>
                <td height="22" width="10%"><%=row.getValue("ITEM_NAME")%></td>
                <td height="22" width="9%"><%=row.getValue("ITEM_SPEC")%></td>
                <td height="22" width="6%"><%=row.getValue("APPLY_QUANTITY")%></td>
                <td height="22" width="10%"><%=row.getValue("FROM_OBJECT_NAME")%></td>
                <td height="22" width="6%"><%=row.getValue("MOVE_QUANTITY")%></td>
                <td height="22" width="6%"><%=row.getValue("RETURN_QTY")%></td>
                <td height="22" width="10%"><%=row.getValue("RCV_DATE")%></td>
                <td height="22" width="10%"><%=row.getValue("RESPECT_RETURN_DATE")%></td>
            </tr>
            <%
                }      }
            %>
        </table>
    </div>

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
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
    function do_ShowDetail(transId) {

        var url = "/servlet/com.sino.ams.spare2.bjslcy.servlet.AmsBjSlDisposeServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&transId="+transId;
        var popscript = "width=1020,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=yes";
        window.open(url, "instrum", popscript);

    }
    function do_drop(){
        var transStatus=document.getElementById("transStatus");
        dropSpecialOption(transStatus,'CREATE')
    }
    function do_selectuser() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                mainForm.createdBy.value = users[0].executeUser;
                mainForm.toUserName.value = users[0].executeUserName;
            }
        }
    }
</script>