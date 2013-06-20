<%@ page import = "com.sino.base.data.RowSet" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant" %>
<%@ page import = "com.sino.base.data.Row" %>
<%@ page import = "com.sino.base.util.StrUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: V-yuanshuai
  Date: 2007-12-18
  Time: 16:26:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType = "text/html;charset=GBK" language = "java" %>
<link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
<script language = "javascript" src = "/WebLibary/js/Constant.js"></script>
<script language = "javascript" src = "/WebLibary/js/CommonUtil.js"></script>
<script language = "javascript" src = "/WebLibary/js/FormProcess.js"></script>
<script language = "javascript" src = "/WebLibary/js/SinoToolBar.js"></script>
<script language = "javascript" src = "/WebLibary/js/SinoToolBarConst.js"></script>
<script language = "javascript" src = "/WebLibary/js/jslib.js"></script>
<script language = "javascript" src = "/WebLibary/js/CheckboxProcess.js"></script>
<script language = "javascript" src = "/WebLibary/js/RadioProcess.js"></script>
<html>
<head>
    <title>设备明细</title>
</head>

<body>
<form>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        Row row;
    %>
    <script type = "text/javascript">
        printTitleBar("设备明细");
    </script>

    <script type = "text/javascript">
        var columnArr = new Array("条码", "设备名称", "规格型号", "设备分类", "地点简称");
        var widthArr = new Array("10%", "10%", "15%", "10%", "15%");
        printTableHead(columnArr, widthArr);
    </script>

    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px" align="left">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class = "dataTR">
                <td height = "22" width = "10%" align = "center"><%=row.getValue("BARCODE")%></td>
                <td height = "22" width = "10%" align = "center"><%=row.getValue("ITEM_NAME")%></td>
                <td height = "22" width = "15%" align = "center"><%=row.getValue("ITEM_SPEC")%></td>
                <td height = "22" width = "10%" align = "center"><%=row.getValue("ITEM_CATEGORY")%></td>
                <td height = "22" width = "15%" align = "center"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>

            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
</html>