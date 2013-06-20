<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-3-20
  Time: 15:19:36
  Function;备件返修率统计.
--%>
<html>
<head>
    <title>备件返修率统计</title>
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
    AmsItemTransLDTO situsdto = (AmsItemTransLDTO) request.getAttribute(WebAttrConstant.AMS_SPARE_DTO);
    String organizationId = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
    String spareUsage = (String)request.getAttribute(WebAttrConstant.SPARE_CATEGORY_OPTION);
    String objectCategory = (String) request.getAttribute(WebAttrConstant.INV_OPTION);
    String vendorName = (String) request.getAttribute(WebAttrConstant.VENDOR_OPTION);
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.spare.servlet.SpareReRatServlet">
    <iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="objectCategory" value="">
<input type="hidden" name="categoryName"  id="categoryName" value="">
<script type="text/javascript">
    printTitleBar("备件返修率统计")
</script>
<!--<table width="100%" border="1" bgcolor="#EFEFEF">-->
 <table width="100%" border="0" bgcolor='#EFEFEF' cellpadding="2" cellspacing="0" >
    <tr>
        <td align="right" width="8%">公司：</td>
        <td width="17%"><select style="width:80%"  name="organizationId"><%=organizationId%></select></td>
        <td align="right" width="8%">设备名称：</td>
        <td width="17%"><input style="width:80%" type="text" name="itemName" value="<%=situsdto.getItemName()%>"></td>
        <td align="right" width="8%">设备型号：</td>
        <td width="17%"><input style="width:80%" type="text" name="itemSpec" value="<%=situsdto.getItemSpec()%>"></td>
        <td align="right" width="8%">设备类型：</td>
        <td width="17%"><input style="width:80%" type="text" name="itemCategory" value="<%=situsdto.getItemCategory()%>"></td>
        <%--<td width="7%"><a style="cursor:'hand'"><img src="/images/button/yearBarChart.gif"  alt="年度返修" onClick="do_yearSearch(); return false;"><img src="/images/button/locBarChart.gif"--%>
                                                                                        <!--alt="地市返修"-->
                                                                                        <%--onclick="do_locSearch()"> </a></td>--%>
    </tr>
    <tr>
        <td align="right" width="8%">用途：</td>
        <td width="17%"><input style="width:80%" type="text" name="spareUsage" value="<%=situsdto.getSpareUsage()%>"></td>
        <td align="right" width="8%">设备厂商：</td>
        <%--<td width="17%"><input style="width:80%" type="text" name="vendorName" value="<%=situsdto.getVendorName()%>"></td>--%>
        <td width="17%"><select name="vendorId" style="width:80%"><%=request.getAttribute(WebAttrConstant.SPARE_VENDOR_OPTION)%></select></td>
        <td align="right" width="8%">年月：</td>
        <td width="7%"><input style="width:40%" type="text" name="searchYear" value="<%=situsdto.getSearchYear()%>">年<input style="width:30%" type="text" name="searchMonth" value="<%=situsdto.getSearchMonth()%>">月</td>
        <td align="right" width="8%"></td>
        <td width="7%"><a style="cursor:'hand'"><img src="/images/button/query.gif"  alt="点击查询" onClick="do_search(); return false;"><img src="/images/button/toExcel.gif"
                                                                                        alt="导出数据"
                                                                                        onclick="do_Export()"></a></td>
    </tr>
</table>
      <script type="text/javascript">
            var columnArr = new Array("公司", "设备名称", "设备型号", "设备类型","用途", "厂商","年份","月份","返修量","现网量","返修率");
            var widthArr = new Array("8%","8%","8%","8%","8%","8%","4%","4%","4%","4%","4%");
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
            <td width="8%" align="center" ><%=row.getValue("COMPANY")%></td>
            <td width="8%" align="center" ><%=row.getValue("ITEM_NAME")%></td>
            <td width="8%" align="center" ><%=row.getValue("ITEM_SPEC")%></td>
            <td width="8%" align="center" ><%=row.getValue("ITEM_CATEGORY")%></td>
            <td width="8%" align="center" ><%=row.getValue("SPARE_USAGE")%></td>
            <td width="8%" align="center"><%=row.getValue("VENDOR_NAME")%></td>
            <td width="4%" align="center"><%=row.getValue("TRANS_YEAR")%></td>
            <td width="4%" align="center"><%=row.getValue("TRANS_MONTH")%></td>
            <td width="4%" align="center" ><%=row.getValue("REP_NUM")%></td>
            <td width="4%" align="center" ><%=row.getValue("TOTAL_NUM")%></td>
            <td width="4%" align="center"><%=row.getValue("RAT_NUM")%></td>
        </tr>
<%
	    }     }
%>
    </table>
</div>
</form>
<div style="left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>

<jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet" flush="true"/>

</body>

</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareReRatServlet";
    mainFrm.submit();
}

function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareReRatServlet";
    mainFrm.submit();
}

function do_yearSearch(){
    mainFrm.act.value = "<%=AMSActionConstant.YEAR_QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareReRatServlet";
    mainFrm.submit();
}

function do_locSearch(){
    mainFrm.act.value = "<%=AMSActionConstant.LOC_QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.spare.servlet.SpareReRatServlet";
    mainFrm.submit();
}
</script>