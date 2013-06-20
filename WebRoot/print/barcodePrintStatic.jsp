<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import="com.sino.ams.net.reportforms.dto.SitusStatisticsDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.print.dto.AmsBarcodePrintDTO" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-1-17
  Time: 11:20:17
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>打印单据查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>

</head>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    AmsBarcodePrintDTO printdto = (AmsBarcodePrintDTO)request.getAttribute(WebAttrConstant.BARCODE_PRINT_DTO);
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.print.servlet.AmsBarcodePrintServlet">
    <iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="categoryName"  id="categoryName" value="">
<script type="text/javascript">
    printTitleBar("打印单据查询")
</script>
<table width="100%" border="0" bgcolor="#EFEFEF">
        <tr>
            <td width="10%" align="right">申请人：</td>
            <td width="22%"><input style="width:100%"  name="applyName" value="<%=printdto.getApplyName()%>"></td>
            <td width="18%" align="right">申请日期 ：</td>
            <td width="20%"><input style="width:80%" type="text" name="fromDate" value="<%=printdto.getFromDate()%>"><img src="/images/calendar.gif" alt="点击选择日期"  onclick="gfPop.fStartPop(fromDate,toDate);"></td>
            <td width="6%" align="right">到：</td>
            <td width="24%"><input name="toDate" style="width:80%" value="<%=printdto.getToDate()%>"><img src="/images/calendar.gif" alt="点击选择日期"  onclick="gfPop.fEndPop(fromDate,toDate);"></td>
            <td><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
        </tr>
</table>
      <script type="text/javascript">
            var columnArr = new Array("单据号","申请人","申请日期","标签类型","数量","颜色","审批人","审批日期","打印人","打印日期");
            var widthArr = new Array("15%","10%","10%","10%","9%","6%","10%","10%","10%","10%");
            printTableHead(columnArr,widthArr);
        </script>
<input type="hidden" name="act">

<div style="overflow-y:scroll;left:0px;width:100%;height:360px">
    <table width="100%" border="1" bordercolor="#666666">
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && rows.getSize() > 0) {
	    Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="15%" align="center" ><%=row.getValue("BATCH_NO")%></td>
            <td width="10%" align="center"><%=row.getValue("APPLY_NAME")%></td>
            <td width="10%" align="center" ><%=row.getValue("APPLY_DATE")%></td>
            <td width="10%" align="center" ><%=row.getValue("TAG_TYPE")%></td>
            <td width="9%" align="right"><%=row.getValue("TAG_NUMBER")%></td>
            <td width="6%" align="center"><%=row.getValue("TAG_COLOR")%></td>
            <td width="10%" align="center" ><%=row.getValue("APPROVE_NAME")%></td>
            <td width="10%" align="center" ><%=row.getValue("APPROVE_DATE")%></td>
            <td width="10%" align="center" ><%=row.getValue("PRINT_NAME")%></td>
            <td width="10%" align="center" ><%=row.getValue("PRINT_DATE")%></td>
        </tr>
<%
	    }
%>
    </table>
</div>
</form>
<div style="left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%
    }
%>
<jsp:include page="/message/MessageProcess"/>

</body>

</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.print.servlet.AmsBarcodePrintServlet";
    mainFrm.submit();
}

function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.print.servlet.AmsBarcodePrintServlet";
    mainFrm.submit();
}
</script>