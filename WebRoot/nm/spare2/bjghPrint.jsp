<%--
  Created by HERRY.
  Date: 2008-7-29
  Time: 15:22:56
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.nm.spare2.dto.AmsItemTransHDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>

<html>
<head><title>备件归还单据打印</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <style type="text/css">
        input {
            font-size: 11px
        }
    </style>
</head>
<body leftmargin="1" topmargin="1">
<%
    AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) request.getAttribute("AIT_HEADER");
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
//    SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(request);
%>
<form name="mainForm" action="/servlet/com.sino.ams.spare2.servlet.BjghServlet" method="post">
    <h2 align="center">备件归还单</h2>

    <table width="100%" id="table2" cellspacing="1" border="1" bordercolor="#000000">
        <tr height="22">
            <td width="9%" align="center">单据号</td>
            <td width="20%"><%=amsItemTransH.getTransNo()%>
            </td>
            <td width="9%" align="center">借出公司</td>
            <td width="25%"><%=amsItemTransH.getToOrganizationName()%>
            </td>
        </tr>
        <tr height="22">
            <td width="9%" align="center">借出仓库</td>
            <td width="25%"><%=amsItemTransH.getToObjectName()%>
            </td>
            <td align="center">归还部门</td>
            <td>
            </td>
        </tr>
        <tr height="22">
            <td align="center">归还人</td>
            <td><%=amsItemTransH.getCreatedUser()%>
            </td>
            <td align="center">归还日期</td>
            <td><%=amsItemTransH.getCreationDate()%>
            </td>
        </tr>
        <tr height="50">
            <td align="center">备注</td>
            <td colspan="7"><%=amsItemTransH.getRemark()%>
            </td>
        </tr>
    </table>

    <table width="100%" border="1" bordercolor="#000000" id="dataTable" cellpadding="1" cellspacing="1">
        <tr height="22">
            <td align="center">调拨单号</td>
            <td align="center">物料编码</td>
            <td align="center">设备名称</td>
            <td align="center">规格型号</td>
            <td align="center">预计归还日期</td>
            <td align="center">借用数量</td>
            <td align="center">已归还数量</td>
            <td align="center">归还数量(正常)</td>
            <td align="center">归还数量(坏件)</td>
        </tr>
        <%
            if (rows != null && !rows.isEmpty()) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr height="22" id="mainTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'"
            onMouseOut="style.backgroundColor='#FFFFFF'">


            <td width="15%"><%=row.getValue("BATCH_NO")%>
            </td>
            <td width="10%"><%=row.getValue("BARCODE")%>
            </td>
            <td width="15%" name="itemName" id="itemName<%=i%>"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td width="18%" name="itemSpec" id="itemSpec<%=i%>"><%=row.getValue("ITEM_SPEC")%>
            </td>
            <td width="8%" name="respectReturnDate" id="respectReturnDate<%=i%>"
                align="center"><%=row.getValue("RESPECT_RETURN_DATE")%>
            </td>
            <td width="7%" align="right"><%=row.getValue("QUANTITY")%>
            </td>
            <td width="7%" align="right"><%=row.getValue("RETURN_QTY")%>
            </td>
            <td width="9%" align="right"><%=row.getValue("NORMAL_QUANTITY")%>
            </td>
            <td width="9%" align="right"><%=row.getValue("BAD_QUANTITY")%>
            </td>

        </tr>
        <%
                }
            }
        %>
    </table>
    <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</form>
<table width="100%">
    <tr>
        <td width="15%">实物接收人：</td>
        <td width="18%"></td>
        <td width="16%">库存部门负责人：</td>
        <td width="18%"></td>
        <td width="16%">库存保管员：</td>
        <td width="18%"></td>
    </tr>
</table>
<div leftmargin="200"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<script type="text/javascript">
    function beforePrint() {

    }
</script>
</html>