<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.net.reportforms.dto.SitusStatisticsDTO" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-11-13
  Time: 9:40:46
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>工单完成错误率统计</title>
</head>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    SitusStatisticsDTO situsdto = (SitusStatisticsDTO)request.getAttribute(WebAttrConstant.ETS_WORKORDER_DTO);
    String organizationId = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.net.reportforms.servlet.FalsityRateServlet">
    <iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="categoryName"  id="categoryName" value="">
<script type="text/javascript">
    printTitleBar("工单完成错误率统计")
</script>
<table width="100%" border="0">
        <tr>
            <td width="10%" align="right">公司：</td>
            <td width="22%"><select style="width:100%" type="text" class="select_style1" name="organizationId"><%=organizationId%></select></td>
            <td width="18%" align="right">工单下发日期 ：</td>
            <td width="20%"><input style="width:80%" type="text" class="input_style2" name="fromDate" value="<%=situsdto.getFromDate()%>"  onclick="gfPop.fStartPop(fromDate,toDate);"><img src="/images/calendar.gif" alt="点击选择日期"  onclick="gfPop.fStartPop(fromDate,toDate);"></td>
            <td width="6%" align="right">到：</td>
            <td width="24%"><input name="toDate" style="width:75%" class="input_style2" value="<%=situsdto.getToDate()%>" onclick="gfPop.fEndPop(fromDate,toDate);"><img src="/images/calendar.gif" alt="点击选择日期"  onclick="gfPop.fEndPop(fromDate,toDate);"></td>
            <td><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel"></td>
        </tr>
</table>
      <script type="text/javascript">
            var columnArr = new Array("人员","错误工单个数","完成工单个数","错误率");
            var widthArr = new Array("20%","20%","20%","20%");
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
            <td width="20%" align="center" ><%=row.getValue("USERNAME")%></td>
            <td width="20%" align="center"><%=row.getValue("NORMAL_NUM")%></td>
            <td width="20%" align="center" ><%=row.getValue("ERR_NUM")%></td>
            <td width="20%" align="center" ><%=row.getValue("RATE")%></td>
        </tr>
<%
	    }     }
%>
    </table>
</div>
</form>
<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>

<jsp:include page="/message/MessageProcess"/>

</body>

</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.net.reportforms.servlet.FalsityRateServlet";
    mainFrm.submit();
}

function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.net.reportforms.servlet.FalsityRateServlet";
    mainFrm.submit();
}
</script>