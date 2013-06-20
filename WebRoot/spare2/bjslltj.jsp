<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by HERRY.
  Date: 2008-4-2
  Time: 15:07:15
--%>
<html>
  <head><title>备件申领量统计</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/TableProcess.js"></script>
  </head>
  <%
      RequestParser parser = new RequestParser();
      parser.transData(request);
      RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
  %>
  <body onkeydown="autoExeFunction('do_search();');">
  <jsp:include page="/message/MessageProcess"/>
  <form name="mainForm" method="POST" action="/servlet/com.sino.ams.spare2.servlet.BjslltjServlet">
      <script type="text/javascript">
          printTitleBar("备件申领量统计");
      </script>
	<input type="hidden" name="act" value="">
<table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" class="queryHeadBg">

	<tr>
        <td align="right">公司：</td>
        <td><select name="organizationId" id="organizationId" style="width:100%">
            <%=request.getAttribute(WebAttrConstant.OU_OPTION)%></select></td>
        <td height="22" align="right">申领日期：</td>
		<td height="22"><input type="text" name="fromDate" value="<%=parser.getParameter("fromDate")%>" style="width:80%" title="点击选择开始日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(fromDate, toDate)">
            <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(fromDate, toDate)">
        </td>
		<td height="22" align="right">到</td>
		<td height="22"><input type="text" name="toDate" value="<%=parser.getParameter("toDate")%>" style="width:80%" title="点击选择截止日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(fromDate, toDate)">
            <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(fromDate, toDate)">
        </td>
        <td><img src="/images/eam_images/export.jpg" alt="导出到EXCEL" onclick="do_export()"></td>
    </tr>
    <tr>
		<td width="10%" height="22" align="right">物料编码：</td>
		<td width="17%" height="22"><input type="text" name="barcode" style="width:100%" value="<%=parser.getParameter("barcode")%>"></td>
		<td width="10%" height="22" align="right">设备名称：</td>
		<td width="20%" height="22"><input type="text" name="itemName" value="<%=parser.getParameter("itemName")%>" style="width:80%"></td>
		<td width="10%" height="22" align="right">规格型号：</td>
		<td width="20%" height="22"><input type="text" name="itemSpec" value="<%=parser.getParameter("itemSpec")%>" style="width:80%"></td>
		<td height="22" colspan="2" align="CENTER"><img src="/images/eam_images/search.jpg" onclick="do_search();" alt="查询"></td>
    </tr>
</table>

<script type="text/javascript">
    var columnArr = new Array("申领公司","物料编码","设备名称","规格型号","申领数量");
    var widthArr = new Array("20%","12%","25%","30%","10%");
    printTableHead(columnArr,widthArr);

</script>
<div style="overflow-y:scroll;left:1px;width:100%;height:360px">
    <table width="100%" border="1" bordercolor="#666666" cellpadding="1">
<%
    if (rows != null && rows.getSize() > 0) {
        Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#FFFFFF'"
                >
            <td width="20%" align="center"><%=row.getValue("FROM_ORGANIZATION_NAME")%></td>
            <td width="12%" align="center"><%=row.getValue("BARCODE")%></td>
            <td width="25%"><%=row.getValue("ITEM_NAME")%></td>
            <td width="30%" ><%=String.valueOf(row.getValue("ITEM_SPEC"))%></td>
            <td width="10%" align="right"><%=row.getValue("QUANTITY")%></td>
        </tr>
<%
	    }
    }
%>
    </table>
</div>
</form>
<div style="left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
</body>
  <iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<%=WebConstant.WAIT_TIP_MSG%>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainForm.submit();
    }
    function do_export() {
        mainForm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainForm.submit();
    }
</script>
</html>