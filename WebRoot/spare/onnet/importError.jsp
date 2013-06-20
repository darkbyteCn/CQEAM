<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: T_suhuipeng
  Date: 2011-12-02
  Time: 00:00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>备件现网量导入错误信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
</head>
<%
    RowSet rows = (RowSet) request.getAttribute(WebAttrConstant.ETS_SPARE_DTO);
    Row row = null;
%>
<body leftmargin="0" topmargin="0">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.onnet.servlet.ExcelOnNetSubmit">
    <script type="text/javascript">
        printTitleBar("备件现网量导入错误信息")
    </script>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td width="8%" align="center">部件号</td>
                <td width="7%" align="center">数量</td>
                <td width="8%" align="center">所属组织ID</td>
                <td width="10%" align="center">所属公司</td>
                <td width="10%" align="center">备注</td>
                <td width="17%" align="center">部件号错误信息</td>
                <td width="20%" align="center">OU错误信息</td>
                <td width="20%" align="center">数量错误信息</td>
            </tr>
        </table>
    </div>
    <div style="overflow-y:scroll;left:0px;width:100%;height:562px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="8%" align="center"><%=row.getValue("PART_NO")%>
                </td>
                <td width="7%" align="left"><%=row.getValue("QUANTITY")%>
                </td>
                <td width="8%" align="left"><%=row.getValue("ORGANIZATION_ID")%>
                </td>
                <td width="10%" align="center"><%=row.getValue("COMPANY")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("REMARK")%>
                </td>
                <td width="17%" align="left"><%=row.getValue("PART_NO_ERROR")%>
                </td>
                <td width="20%" align="left"><%=row.getValue("OU_ERROR")%>
                </td>
                <td width="20%" align="left"><%=row.getValue("QUANTITY_ERROR")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<%--<div><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>--%>
</body>
</html>
<script type="text/javascript">
</script>