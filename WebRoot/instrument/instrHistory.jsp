<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  User: Zyun
  Date: 2008-2-26
  Time: 13:41:51
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>仪器仪表变动历史</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
</head>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String itemCode = parser.getParameter("itemCode");
%>
<body topmargin="0" leftmargin="0" onLoad="window.focus();">
<form name="mainFrm" method="post" action="">
    <script type="text/javascript">
        printTitleBar("仪器仪表历史")
    </script>
    <%=WebConstant.WAIT_TIP_MSG%>
    <input type="hidden" name="act">
    <input type="hidden" name="itemCode" value="<%=itemCode%>">
    <img src="/images/eam_images/close.jpg" alt="关闭" onClick="window.close();">
    <div style="position:absolute;top:53px;left:0px;width:700px">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <%--<td width="2%" align="center" style="padding:0"><input type="checkbox" name="titleCheck" class="headCheckbox"--%>
                                                                       <!--id="controlBox" onclick="checkAll('titleCheck','Ids')"></td>-->
                <td width="10%" align="center">标签</td>
                <td width="12%" align="center">单据号</td>
                <td width="12%" align="center">设备名称</td>
                <td width="12%" align="center">设备型号</td>
                <td width="8%" align="center">借用日期</td>
                <td width="8%" align="center">送修日期</td>
                <td width="8%" align="center">归还日期</td>
                <td width="8%" align="center">设备状态</td>
            </tr>
        </table>
    </div>
    <div style="overflow-y:scroll;position:absolute;top:75px;left:0px;width:717px;height:70%">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"  onMouseOut="style.backgroundColor='#ffffff'">
                <%--<td width="2%" align="center"><input type="checkbox" name="Ids" value="<%=row.getValue("SYSTEM_ID")%>"></td>--%>
                <td width="10%" align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td width="12%" align="center"><%=row.getValue("TRANS_NO")%>
                </td>
                <td width="12%" align="left"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td width="12%" align="left"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("BORROW_DATE")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("REPAIRE_DATE")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("RETURN_DATE")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("ITEM_STATUS")%>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
</form>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%
    }
%>
</body>
</html>
<script type="text/javascript">

</script>