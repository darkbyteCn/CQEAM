<%@ page import="com.sino.ams.constant.DictConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.spare.dto.SpareHistoryDTO"%>
<%--
  Created by IntelliJ IDEA.
  User: su
  Date: 2010-2-4
  Time: 10:30:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备品备件月度报表</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SelectProcess.js"></script>
</head>
<body>
<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet"/>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')">
<%
    SpareHistoryDTO dto = (SpareHistoryDTO) request.getAttribute(WebAttrConstant.AMS_SPARE_DTO);
    String organizationId = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    boolean hasData = (rows != null && !rows.isEmpty());
%>
<form action="/servlet/com.sino.ams.spare.servlet.SpareMonthlyReportServlet" name="mainForm" method="post">
    <script type="text/javascript">
        printTitleBar("备品备件月度报表")
    </script>
<table border="0" width="100%" cellspacing="0" cellpadding="0" style="background-color:#efefef">
    <tr>
        <td align="right" width="5%">ID号：</td>
        <td align="left" width="15%"><input type="text" name="barcode" style="width:80%" value="<%=dto.getBarcode()%>"></td>
        <td align="right" width="8%">设备名称：</td>
        <td align="left" width="15%"><input type="text" name="itemName" style="width:80%" value="<%=dto.getItemName()%>"></td>
        <td align="right" width="8%">规格型号：</td>
        <td align="left" width="15%"><input type="text" name="itemSpec" style="width:80%" value="<%=dto.getItemSpec()%>"></td>
        <td align="right" width="8%">设备类型：</td>
        <td align="left" width="15%"><input type="text" name="itemCategory" style="width:80%" value="<%=dto.getItemCategory()%>"></td>
    </tr>
    <tr>
        <td align="right" width="5%">用途：</td>
        <td align="left" width="15%"><input type="text" name="spareUsage" style="width:80%" value="<%=dto.getSpareUsage()%>"></td>
        <td align="right" width="8%">厂商：</td>
        <td width="15%" align="left"><select name="vendorId" style="width:80%"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%></select></td>
        <td align="right" width="8%">月度区间：</td>
        <td>
            <input type="text" name="startDate" value="<%=dto.getStartDate()%>" style="width:80%" title="点击选择开始日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)">
            <img src="/images/calendar.gif" alt="点击选择开始日期" onclick="gfPop.fStartPop(startDate, endDate)">
        </td>
        <td align="right" width="8%">到：</td>
        <td>
            <input type="text" name="endDate" value="<%=dto.getEndDate()%>" style="width:80%" title="点击选择截止日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)">
            <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop(startDate, endDate)">
        </td>
    </tr>
    <tr>
        <td align="right" colspan=8><img src="/images/button/query.gif" alt="查询" onClick="do_search(); return false;"><img src="/images/button/toExcel.gif" alt="导出数据" onclick="do_export()"></td>
    </tr>
</table>
<input type="hidden" name="act" value="">
<div id="headDiv" style="overflow:hidden;position:absolute;top:86px;left:1px;width:844px">
    <table border=1 width="250%" class="headerTable">
        <tr class=headerTable height="20px">
			<td align=center width=2% rowspan="2">ID号</td>
			<td align=center width=5% rowspan="2">设备名称</td>
			<td align=center width=5% rowspan="2">规格型号</td>
			<td align=center width=5% rowspan="2">设备类型</td>
			<td align=center width=5% rowspan="2">用途</td>
			<td align=center width=5% rowspan="2">厂商</td>
            <td align=center width=2% rowspan="2">本期</td>
			<td align=center width=24% colspan="12">本期领用</td>
         </tr>
         <tr class=headerTable height="20px">
			<td align=center width=2%>省公司</td>
			<td align=center width=2%>大同</td>
			<td align=center width=2%>阳泉</td>
			<td align=center width=2%>长治</td>
			<td align=center width=2%>晋城</td>
			<td align=center width=2%>朔州</td>
			<td align=center width=2%>忻州</td>
			<td align=center width=2%>晋中</td>
			<td align=center width=2%>吕梁</td>
			<td align=center width=2%>临汾</td>
			<td align=center width=2%>运城</td>
			<td align=center width=2%>太原</td>
         </tr>
</table>
</div>

<div id="dataDiv" style="overflow:scroll;height:350px;width:860px;position:absolute;top:128px;left:1px" align="left" onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <table id="dataTable" width="250%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
        <%
            if (rows != null && rows.getSize() > 0) {
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="2%" align="center"><%=row.getValue("BARCODE")%></td>
            <td width="5%" align="center"><%=row.getValue("ITEM_NAME")%></td>
            <td width="5%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
            <td width="5%" align="center"><%=row.getValue("ITEM_CATEGORY")%></td>
            <td width="5%" align="center"><%=row.getValue("SPARE_USAGE")%></td>
            <td width="5%" align="center"><%=row.getValue("VENDOR_NAME")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL1_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL2_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL3_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL4_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL5_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL6_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL7_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL8_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL9_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL10_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL11_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL12_QUANTITY")%></td>
            <td width="2%" align="center"><%=row.getValue("TOTAL13_QUANTITY")%></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>

</form>
<%
    if (hasData) {
%>
<div id="navigatorDiv" style="position:absolute;top:478px;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
</body>
<%=WebConstant.WAIT_TIP_MSG%>
</html>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
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