<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-11-29
  Time: 10:20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件申领接收差异详细</title>
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
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
</head>
<script type="text/javascript">
    printTitleBar("备件申领接收差异详细")
</script>
<body leftmargin="1" topmargin="1" >
<form action="/servlet/com.sino.ams.spare.bjslcy.servlet.AmsBjSlDisposeServlet" name="mainForm"  method="post">
    <fieldset>
        <legend>
            <%--<img src="/images/button/toExcel.gif" alt="导出数据" onClick="do_excel()">--%>
            <img src="/images/button/close.gif" alt="关闭" onClick="window.close();">
        </legend>
        <script type="text/javascript">
            var columnArr = new Array("地市", "备件名称","部件号", "分配数量","接收数量");
            var widthArr = new Array("12%", "15%","15%", "8%","8%");
            printTableHead(columnArr, widthArr);
        </script>
        <div style="overflow-y:scroll;height:450px;width:100%;left:1px;margin-left:0"
             onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
            <table width="100%" border="1" bordercolor="#9FD6FF" id="dataTable" cellpadding="0" cellspacing="0">
                <%
                    RowSet rows = (RowSet) request.getAttribute("DIFFENTED");
                    if (rows != null && !rows.isEmpty()) {

                        Row row = null;
                        for (int i = 0; i < rows.getSize(); i++) {
                            row = rows.getRow(i);
                %>
                <tr height="20">
                    <td width="12%" align="center"><%=row.getValue("COMPANY")%>
                    </td>
                    <td width="15%"  align="center"><%=row.getValue("ITEM_NAME")%>
                    </td>
                    <td width="15%"  align="center"><%=row.getValue("BARCODE")%>
                    </td>
                    <td width="8%"  align="center"> <%=row.getValue("QUANTITY")%>
                    </td>
                    <td width="8%"  align="center"><%=row.getValue("ACCEPT_QTY")%>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </fieldset>
     <input type="hidden" name="act" value="">
</form>

</body>
</html>
<script type="text/javascript">
    function do_excel(){
       mainForm.act.value="<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
</script>