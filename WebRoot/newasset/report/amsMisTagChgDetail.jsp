<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%--
  Created by HERRY.
  Date: 2008-10-14
  Time: 11:31:29
--%>
<html>
  <head><title>调拨明细</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
  </head>
  <body>
  <jsp:include page="/newasset/report/print.jsp" flush="false" />
  <%
      RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
      String transNo = StrUtil.nullToString(request.getParameter("transNo"));
  %>
  <h2>调拨单:<%=transNo%></h2>

    <table id="labelTable" width="100%" border="1" bordercolor="#666666">
        <thead>
        <tr height="22">
            <td width="50%" align="center">原标签</td>
            <td width="50%" align="center">新标签</td>
        </tr>
        </thead>
        <tbody>
<%
    if (rows != null && rows.getSize() > 0) {
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'"
                >
            <td width="25%" align="center"><%=row.getValue("TAG_NUMBER_FROM")%></td>
            <td width="25%" align="center"><%=row.getValue("TAG_NUMBER_TO")%></td>
        </tr>
<%
	    }
    }

%>
        </tbody>
    </table>

<div style="left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<form id="submitForm" target="submitFrm" name="submitForm" action="/servlet/com.sino.ams.newasset.servlet.AmsMisTagChgServlet">
	<input type="hidden" name="act" />
	<input type="hidden" name="transNo" value="<%= transNo %>" />
</form>  
<iframe name="submitFrm" id="submitFrm" frameborder="0" style="position:absolute;visibility:inherit;   top:0px;   left:0px;  width:0px;   height:0px;    z-index:-999;"></iframe>
  </body>
<script type="text/javascript">
    function beforePrint(){
    }
</script>
</html>