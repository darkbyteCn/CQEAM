<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-21
  Time: 17:44:43
  To change this template use File | Settings | File Templates.
--%>

<link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
<script language="javascript" src="/WebLibary/js/Constant.js"></script>
<script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
<script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
<script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
<script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
<script language="javascript" src="/WebLibary/js/jslib.js"></script>
<script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
<script language="javascript" src="/WebLibary/js/calendar.js"></script>

<%
    RowSet rows = (RowSet) request.getAttribute("ERROR_ROWS");
%>
<html>
<head>
    <script type="text/javascript">
        printTitleBar("TD系统转资资产清单错误信息");
    </script>
</head>
<body leftmargin="0" topmargin="0">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.soa.td.srv.pagequiryassetcustdetail.servlet.SBFIFATdPageinquiryAssetCustDetailServlet">
    <div id="headDiv" style="overflow:hidden;position:absolute;top:20px;left:1px;width:98.5%">
        <table class="headerTable" border="1" width="140%">
            <tr height=20px>
                <td align=center width="10%">同步类型</td>
                <td align=center width="10%">同步日期</td>
                <td align=center width="80%">错误信息</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:88%;width:100%;position:absolute;top:40px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="140%" border="1" bordercolor="#666666">
            <%
                if (rows != null && !rows.isEmpty()) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        Row row = rows.getRow(i);
            %>
            <tr class="dataTR">
                <td width="10%" align="left"><%=row.getValue("SYN_TYPE")%></td>
                <td width="10%" align="left"><%=row.getValue("SYN_DATE")%></td>
                <td width="80%" align="left"><%=row.getValue("SYN_MSG")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>

        <table align='center'>
            <tr align='center'>
                <td align='center'><p align="center"><img border="0" src="/images/eam_images/close.jpg" onclick="self.close()"></p></td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="act">
    <iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
            src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
            style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
    </iframe>

</form>
</body>
</html>

