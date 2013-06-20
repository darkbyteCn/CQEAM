<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: SUHP
  Date: 2008-3-13
  Time: 7:15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head><title>库存综合现有量查询</title>
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
  <form action="/servlet/com.sino.ams.spare2.kcxylcy.servlet.AmsStockExistServlet" name="mainForm" method="post">
        <script type="text/javascript">
    printTitleBar("库存综合现有量查询")
</script>
    <table border="0" width="100%" class="queryHeadBg">
        <tr>
            <td align="right" width="10%">公司：</td>
            <td height="22" width="20%"><select name="fromOrganizationId" id="fromOrganizationId" style="width:80%">
            <%=request.getAttribute("OU")%></select></td>
            <td align="right" width="10%">设备名称：</td>
            <td height="22" width="25%"><input type="text" name="itemName"  style="width:80%"
                                    value="<%=StrUtil.nullToString(request.getParameter("itemName"))%>"></td>
            <td align="right" width="10%">型号：</td>
            <td height="22" width="20%"><input type="text" name="itemSpec"  style="width:80%"
                                    value="<%=StrUtil.nullToString(request.getParameter("itemSpec"))%>"></td>
            <td width=10% align="left">
                <img src="/images/eam_images/search.jpg" alt="查询"
                     onClick="do_Search(); return false;">
            </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">

       <script type="text/javascript">
        var columnArr = new Array("仓库", "设备名称", "型号", "现有量", "保留量", "借出量", "待修量", "送修量", "报废量");
        var widthArr = new Array("14%", "14%", "12%", "10%", "10%", "10%", "10%", "10%", "10%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:345px;width:100%;left:1px;margin-left:0px"
         align="left">
        <table width="100%" border="1" bordercolor="#666666" id="dataTab">
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr >
                <td height="22" width="14%"><%=row.getValue("OBJECT_NAME")%></td>
                <td height="22" width="14%"><%=row.getValue("ITEM_NAME")%></td>
                <td height="22" width="12%"><%=row.getValue("ITEM_SPEC")%></td>
                <td height="22" width="10%"><%=row.getValue("QUANTITY")%></td>
                <td height="22" width="10%"><%=row.getValue("RESERVE_QUANTITY")%></td>
                <td height="22" width="10%"><%=row.getValue("LOAN_QUANTITY")%></td>
                <td height="22" width="10%"><%=row.getValue("DISREPAIR_QUANTITY")%></td>
                <td height="22" width="10%"><%=row.getValue("REPAIR_QUANTITY")%></td>
                <td height="22" width="10%"><%=row.getValue("SCRAP_QUANTITY")%></td>
            </tr>
            <%
                }      }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
  </body>
</html>
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
    function do_selectuser() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                mainForm.toUser.value = users[0].executeUser;
                mainForm.toUserName.value = users[0].executeUserName;
            }
        }
    }
</script>