<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by HERRY.
  Date: 2007-10-31
  Time: 14:43:29
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>地市列表</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
</head>
<%
    String transId = StrUtil.nullToString(request.getParameter("transId"));
    String lineId = StrUtil.nullToString(request.getParameter("lineId"));
    String itemCode = StrUtil.nullToString(request.getParameter("itemCode"));
    String attribute1 = StrUtil.nullToString(request.getParameter("attribute1"));
%>
<body leftmargin="0" topmargin="0" onload="do_init()">
<form name="mainForm" action="/servlet/com.sino.nm.spare2.servlet.ItemCountByOuServlet" method="post">
    <table class="headerTable" width="100%" border="1" cellpadding="0" cellspacing="1">
        <tr>
            <td width="35%" align="center">地市</td>
            <td width="35%" align="center">仓库</td>
            <td width="10%" align="center">现有量</td>
            <td width="10%" align="center">保留量</td>
            <td width="10%" align="center">分配数量</td>
        </tr>
    </table>
    <table width="100%" border="1" borderColor="#9FD6FF" cellpadding="0" cellspacing="1">
        <%
            RowSet rows = (RowSet) request.getAttribute("OU_ITEM_COUNT");
            if (rows != null && !rows.isEmpty()) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr id="xhTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'"
                >
            <td width="35%"><%=row.getValue("COMPANY")%>
            </td>
            <td width="35%"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
            </td>
            <td width="10%"><input type="text" name="curOnhandQty" id="curOnhandQty<%=i%>"
                                   value="<%=row.getValue("CUR_ONHAND_QTY")%>"
                                   class="noborderGray" readonly style="width:100%;text-align:center">
            </td>
            <td width="10%"><input type="text" name="reservedQty" id="reservedQty<%=i%>"
                                   value="<%=row.getValue("RESERVED_QTY")%>"
                                   class="noborderGray" readonly style="width:100%;text-align:center">
            </td>
            <td width="10%"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>"
                                   style="width:100%;text-align:center" onblur="checkQTY('<%=i%>',this)">
            </td>
            <td style="display:none">
                <input type="hidden" name="organizationId" id="organizationId<%=i%>"
                       value="<%=row.getValue("ORGANIZATION_ID")%>">
                <input type="hidden" name="detailId" id="detailId<%=i%>" value="<%=row.getValue("DETAIL_ID")%>">
                <input type="hidden" name="objectNo" id="objectNo<%=i%>" value="<%=row.getValue("OBJECT_NO")%>">

            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <input type="hidden" name="itemCode" value="<%=itemCode%>">
    <input type="hidden" name="transId" value="<%=transId%>">
    <input type="hidden" name="lineId" value="<%=lineId%>">
    <input type="hidden" name="attribute1" value="<%=attribute1%>">
    <input type="hidden" name="act">
</form>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
</body>
<script type="text/javascript">
    function checkQTY(id, object) {
        var allocateQty = Number(document.getElementById("quantity" + id).value);
        var onhandQty = Number(document.getElementById("curOnhandQty" + id).value);
        var reservedQty = Number(document.getElementById("reservedQty" + id).value);
        //        alert("reservedQty="+reservedQty)
        //        alert("Number(onhandQty)+Number(reservedQty)="+(Number(onhandQty)+Number(reservedQty)))
        if (allocateQty > (onhandQty - reservedQty)) {
            alert("输入数量不能大于现有量与保留量之差，请重新输入！");
            object.focus();
        }
    }

    function do_init() {
        parent.mainForm.processing.value = "0";
        if (document.getElementById("$$$publicMessage$$$")) {
            parent.do_query();
        }
    }
  </script>
</html>