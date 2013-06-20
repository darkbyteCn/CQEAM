<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
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
      String transId = request.getParameter("transId");
      String lineId = request.getParameter("lineId");
      String itemCode = request.getParameter("itemCode");
  %>
  <body leftmargin="0" topmargin="0">
  <form name="mainForm" action="/servlet/com.sino.ams.spare.servlet.ItemCountByOuServlet" method="post">
  <table class="headerTable" width="100%" border="1">
      <tr>
          <td width="40%" align="center">地市</td>
          <td width="20%" align="center">现有量</td>
          <td width="20%" align="center">保留量</td>
          <td width="20%" align="center">分配数量</td>
      </tr>
  </table>
  <table width="100%" border="1" borderColor="#9FD6FF">
          <%
              RowSet rows = (RowSet) request.getAttribute("OU_ITEM_COUNT");
              if (rows != null && !rows.isEmpty()) {
                  Row row = null;
                  for (int i = 0; i < rows.getSize(); i++) {
                      row = rows.getRow(i);
          %>
                        <tr id="xhTr<%=i%>" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'"
                                >
                        <td width="40%"><%=row.getValue("COMPANY")%>
                        </td>

                        <td width="20%"><input type="text" name="onhandQty" id="onhandQty<%=i%>" value="<%=row.getValue("ONHAND_QTY")%>"
                                               class="noborderGray" readonly style="width:100%;text-align:center">
                        </td>
                        <td width="20%"><input type="text" name="reservedQty" id="reservedQty<%=i%>" value="<%=row.getValue("RESERVED_QTY")%>"
                                               class="noborderGray" readonly style="width:100%;text-align:center">
                        </td>
                        <td width="20%"><input type="text" name="quantity" id="quantity<%=i%>" value="<%=row.getValue("QUANTITY")%>"
                                                style="width:100%;text-align:center" onblur="checkQTY('<%=i%>',this)">
                        </td>
                        <td style="display:none">
                            <input type="hidden" name="organizationId" id="organizationId<%=i%>" value="<%=row.getValue("ORGANIZATION_ID")%>">
                            <input type="hidden" name="detailId" id="detailId<%=i%>" value="<%=row.getValue("DETAIL_ID")%>">

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
      <input type="hidden" name="act" >
      </form>
  <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>
  </body>
<script type="text/javascript">
    function checkQTY(id,object){
         var allocateQty = document.getElementById("quantity"+id).value;
         var onhandQty = Number(document.getElementById("onhandQty"+id).value);
         var reservedQty = Number(document.getElementById("reservedQty"+id).value);
//        alert("reservedQty="+reservedQty)
//        alert("Number(onhandQty)+Number(reservedQty)="+(Number(onhandQty)+Number(reservedQty)))
        if(Number(allocateQty)>(Number(onhandQty)-Number(reservedQty))){
            alert("输入数量不能大于现有量与保留量之差，请重新输入！");
            object.focus();
        }
    }
</script>
</html>