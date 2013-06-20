<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>
<%@ page import="com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp"%>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
    Function:    工单业务统计报表
    Author:      李轶
    Date:        2009-10-28
--%>
<html>
<head>
    <title>工单业务统计报表</title>
    <link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
    <script language = "javascript" src = "/WebLibary/js/Constant.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CommonUtil.js"></script>
    <script language = "javascript" src = "/WebLibary/js/FormProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBar.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBarConst.js"></script>
    <script language = "javascript" src = "/WebLibary/js/jslib.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CheckboxProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/RadioProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/DateProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/TableProcess.js"></script>
</head>
<body onload = "autoSpan('dateTable',1); ">

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    boolean hasData = (rows != null && !rows.isEmpty());
    AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)request.getAttribute(QueryConstant.QUERY_DTO);

%>
<form method = "post" name = "mainFrm">
    <script type = "text/javascript">
        printTitleBar("工单业务统计报表")
    </script>
    <table width = "100%" border = "0" class="queryHeadBg">
        <tr>

            <td width="13%" align="right">工单上传日期：</td>
			<td width="15%"><input type="text" name="startDate" style="cursor:hand;width:100%" title="点击选择开始日期" readonly class="readonlyInput" value="<%=dto.getStartDate()%>" onclick="gfPop.fStartPop(startDate, endDate);" size="20"></td>
			<td width="10%" align="center">到：</td>
			<td width="15%"><input type="text" name="endDate" style="cursor:hand;width:100%" title="点击选择截至日期" readonly class="readonlyInput" value="<%=dto.getEndDate()%>" onclick="gfPop.fEndPop(startDate, endDate);"></td>
            <td width="30%" align = "right">
                <img src = "/images/eam_images/search.jpg" style = "cursor:'hand'" onclick = "do_search();" alt = "查询">&nbsp;&nbsp;&nbsp;
                <img src = "/images/eam_images/export.jpg" id = "queryImg" style = "cursor:'hand'" onclick = "do_exportToExcel()" alt = "导出到Excel">
            </td>
        </tr>
    </table>

    <input type = "hidden" name = "act" value = "<%=parser.getParameter("act")%>">

    <script type = "text/javascript">
        var columnArr = new Array("公司", "地点新增工单", "交接", "巡检", "维修", "搬迁", "调拨", "报废", "合计");
        var widthArr = new Array("15%", "5%", "5%", "5%", "5%", "5%", "5%", "5%", "5%");
        printTableHead(columnArr, widthArr);
    </script>

    <div style = "overflow-y:scroll; left:0px;width:100%;height:400px">
        <table width = "100%" border = "1" bordercolor = "#666666" id = "dateTable">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class = "dataTR" height = "22">
                <td width = "15%" align = "center"><%=row.getValue("ORGANIZATION_NAME")%></td>
                <td width = "5%" align = "center"><%=row.getValue("NEW_OBJECT_COUNT")%></td>
                <td width = "5%" align = "center"><%=row.getValue("CONNECT_COUNT")%></td>
                <td width = "5%" align = "center"><%=row.getValue("PATROL_COUNT")%></td>
                <td width = "5%" align = "center"><%=row.getValue("MAINTENANCE_COUNT")%></td>
                <td width = "5%" align = "center"><%=row.getValue("MOVE_COUNT")%></td>
                <td width = "5%" align = "center"><%=row.getValue("TRANS_COUNT")%></td>
                <td width = "5%" align = "center"><%=row.getValue("DISCARDED_COUNT")%></td>
                <td width = "5%" align = "center"><%=row.getValue("SUM_COUNT")%></td>
            </tr>
            <%
                }        }
            %>
        </table>
    </div>

</form>

<%
	if(hasData){
%>
<div style="position:absolute;top:87%;left:0; right:20"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
	}
%>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page = "<%=URLDefineList.MESSAGE_PROCESS%>" flush = "true" />
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</body>
</html>
<script type = "text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "<%=URLDefineList.WORKORDER_STATISTICS_SERVLET%>";
        //        alert(mainFrm.action);
        mainFrm.submit();
    }
    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "<%=URLDefineList.WORKORDER_STATISTICS_SERVLET%>";
        mainFrm.submit();

    }

</script>