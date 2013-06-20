<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.spare.dto.AmsItemTransLDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="org.jfree.chart.ChartFactory" %>
<%@ page import="org.jfree.chart.JFreeChart" %>
<%@ page import="org.jfree.chart.axis.ValueAxis" %>
<%@ page import="org.jfree.chart.labels.StandardCategoryItemLabelGenerator" %>
<%@ page import="org.jfree.chart.plot.CategoryPlot" %>
<%@ page import="org.jfree.chart.plot.PlotOrientation" %>
<%@ page import="org.jfree.chart.renderer.category.CategoryItemRenderer" %>
<%@ page import="org.jfree.chart.servlet.ServletUtilities" %>
<%@ page import="org.jfree.data.category.DefaultCategoryDataset" %>
<%@ page import="java.awt.*" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
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
        <!--<td align="right" width="8%">公司：</td>-->
        <%--<td width="17%"><select style="width:80%"  name="organizationId"><%=organizationId%></select></td>--%>
        <td align="right" width="10%">设备类型：</td>
        <!--<td width="17%"><input style="width:80%" type="text" name="spareUsage" value="<%=situsdto.getSpareUsage()%>"></td>-->
        <td width="17%"><select style="width:80%" type="text" name="spareUsage"><%=spareUsage%>"></select></td>
        <td align="right" width="10%">设备名称：</td>
        <td width="7%"><input style="width:80%" type="text" name="itemName" value="<%=situsdto.getItemName()%>"></td>
        <td align="right" width="8%">年月：</td>
        <td width="7%"><input style="width:40%" type="text" name="searchYear" value="<%=situsdto.getSearchYear()%>">年<input style="width:30%" type="text" name="searchMonth" value="<%=situsdto.getSearchMonth()%>">月</td>
        <td align="right" width="8%"></td>
        <td width="7%"><a style="cursor:'hand'"><img src="/images/button/yearBarChart.gif"  alt="年度返修" onClick="do_yearSearch(); return false;"><img src="/images/button/locBarChart.gif"
                                                                                        alt="地市返修"
                                                                                        onclick="do_locSearch()"></a></td>
    </tr>
    <tr>
        <td align="right" width="10%">设备型号：</td>
        <td width="17%"><input style="width:80%" type="text" name="itemSpec" value="<%=situsdto.getItemSpec()%>"></td>
        <td align="right" width="8%">设备厂商：</td>
        <!--<td width="17%"><input style="width:80%" type="text" name="vendorName" value="<%=situsdto.getVendorName()%>">-->
        <td width="17%"><select style="width:80%" type="text" name="vendorName"><%=vendorName%>"></select>
         </td>
        <td align="right" width="8%"></td>
        <td width="17%"></td>
        <td align="right" width="8%"></td>
        <td width="7%"><a style="cursor:'hand'"><img src="/images/button/query.gif"  alt="点击查询" onClick="do_search(); return false;"><img src="/images/button/toExcel.gif"
                                                                                        alt="导出数据"
                                                                                        onclick="do_Export()"></a></td>
    </tr>
</table>
    <input type="hidden" name="act">
    <div style="overflow-y:scroll;left:0px;width:100%;height:360px">
    <!--IMG SRC="/servlet/com.sino.ams.spare.servlet.ServletDemo1"-->
    <!--jsp:include page="/servlet/com.sino.ams.spare.servlet.ServletDemo1" flush="ture"/-->
    <%
//        pageContext.pushBody(out);
 //       OutputStream os = response.getOutputStream();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && rows.getSize() > 0) {
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
                String value;
                dataset.addValue(Integer.parseInt(row.getStrValue("REP_NUM")), row.getStrValue("VENDOR_NAME")+row.getStrValue("SPARE_USAGE"), row.getStrValue("VENDOR_NAME"));
            }
        }
        JFreeChart chart = ChartFactory.createBarChart(situsdto.getSearchYear()+situsdto.getVendorName()+"返修统计","设备","数量",dataset, PlotOrientation.VERTICAL,true, true, false);
        // get a reference to the plot for further customisation...
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.1);        

        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
        CategoryItemRenderer renderer = plot.getRenderer();
         renderer.setItemLabelGenerator(
                 new StandardCategoryItemLabelGenerator());
  //           renderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);
        renderer.setBaseItemLabelsVisible(true);
//        response.setContentType("image/png");
//        ChartUtilities.writeChartAsPNG(os, chart, 600, 400);
        String filename = ServletUtilities.saveChartAsPNG(chart,700,360,session);
        String graphURL = "/servlet/com.sino.ams.spare.servlet.DisplayChart?filename=" + filename;
//        os.flush();
//        os.close();
//        os=null;
//        response.flushBuffer();
//        out.clear();
//        out = pageContext.pushBody();
    %>
     <img src="<%= graphURL %>">
</div>
</form>

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
    mainFrm.act.value = "<%=AMSActionConstant.YEAR_EXPORT_ACTION%>";
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