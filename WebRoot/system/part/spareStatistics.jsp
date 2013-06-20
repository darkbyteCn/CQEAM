<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.net.reportforms.dto.SitusStatisticsDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-1-7
  Time: 20:32:49
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>确认错误统计</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">		
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
    RequestParser parser = new RequestParser();
    parser.transData(request);
//    SitusStatisticsDTO situsdto = (SitusStatisticsDTO)request.getAttribute(WebAttrConstant.ETS_WORKORDER_DTO);
//    String organizationId = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
    String itemName = StrUtil.nullToString(request.getParameter("itemName"));
%>
<form method="post" name="mainFrm">
    <iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="categoryName"  id="categoryName" value="">
<script type="text/javascript">
    printTitleBar("确认错误统计")
</script>
<table width="100%" border="0" >
        <tr>
            <td width="10%" align="right">用户名：</td>
            <td width="22%"><input class="input_style1" style="width:100%" type="text" name="itemName" value ="<%=itemName%>"></td>
            <td width="18%" align="right"></td>
            <td width="20%"></td>
            <td width="6%" align="right"></td>
            <td width="24%"></td>
            <td><img src="/images/eam_images/search.jpg" align="middle" onclick="do_search();" alt="查询">&nbsp;<img src="/images/eam_images/export.jpg" id="queryImg" align="middle" onclick="do_Export();" alt="导出到Excel"></td>
        </tr>
</table>
      <script type="text/javascript">
            var columnArr = new Array("人员名称","地市","错误个数","正确个数","错误率");
            var widthArr = new Array("20%","20%","20%","15%","15%");
            printTableHead(columnArr,widthArr);
      </script>
<input type="hidden" name="act">
<div style="overflow-y:scroll;left:0px;width:100%;height:320px">
    <table width="100%" border="1" >
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && rows.getSize() > 0) {
	    Row row = null;
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="20%" align="center" ><%=row.getValue("USER_NAME")%></td>
            <td width="20%" align="center" ><%=row.getValue("COMPANY_NAME")%></td>
            <td width="20%" align="center" ><%=row.getValue("ERROR_NUM")%></td>
            <td width="15%" align="center"><%=row.getValue("CORRECT_NUM")%></td>
            <td width="15%" align="center" ><%=row.getValue("RATE")%></td>
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
    mainFrm.act.value = "<%=AMSActionConstant.STATISTICS_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.part.servlet.PartConfirmServlet";
    mainFrm.submit();
}

function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.part.servlet.PartConfirmServlet";
    mainFrm.submit();
}
</script>