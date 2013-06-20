<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsWebAttributes" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by        李轶
  Date:             2009-07-24
  Time:             10:15:55
  Function:         工单统计----地点(陕西)
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title></title>
</head>

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String fromDate = StrUtil.nullToString(request.getParameter("fromDate"));
    String toDate = StrUtil.nullToString(request.getParameter("toDate"));
    String workorderObjectName = parser.getParameter("workorderObjectName");
//    String objectCategory = parser.getParameter("objectCategory");
    String objectCategory = (String) request.getAttribute(WebAttrConstant.EQUIPMENT_OPTION);
%>
<%=WebConstant.WAIT_TIP_MSG%>
<body onkeydown="autoExeFunction('do_search()')">
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("工单统计--按地点")
    </script>
    <table width="100%" border="0" class="queryTable">
        <input type="hidden" name="act">
        <tr>
            <td width="5%" align="right">公司：</td>
			<td width="10%" align="left">
                <select name="organizationId" class="select_style1" style="width:100%"><%=request.getAttribute(AssetsWebAttributes.CITY_OPTION)%></select>
            </td>
            <td width="8%" align="right">地点简称：</td>
            <td width="25%"><input style="width:80%" type="text" name="workorderObjectName" class="input_style2" readonly onclick="chooseSit()"
                                   value="<%=workorderObjectName%>" ><a href="#" onclick="chooseSit()" title="点击选择地点"
                                                                       class="linka">[…]</a></td>
            <td align="right" width="5%">时间：</td>
            <td align="left" width="10%"><input style="width:85%" type="text" readOnly name="fromDate" value="<%=fromDate%>"
                                    class="input_style2" onclick="gfPop.fStartPop(fromDate,toDate);" alt="点击选择日期"></td>
            <td align="right" width="5%">到：</td>
            <td align="left" width="10%"><input type="text" name="toDate" value="<%=toDate%>" readOnly style="width:85%"
                                    class="input_style2" onclick="gfPop.fEndPop(fromDate,toDate);" alt="点击选择日期"></td>
            <td width="15%" align="right">
                <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
                <img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'"  onclick="do_Export();" alt="导出到Excel">
        </tr>
    </table>

    <script type="text/javascript">
        var columnArr = new Array("地点编号", "地点简称", "交接工单数", "巡检工单数","维修工单数", "搬迁工单数", "调拨工单数", "报废工单数" , "工单总数");
        var widthArr = new Array("14%", "24%", "8%", "8%", "8%", "8%", "8%", "8%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:1px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
//                System.out.println("1");
                    int qtyA = 0;
                    int qtyB = 0;
                    int qtyC = 0;
                    int qtyD = 0;
                    int qtyE = 0;
                    int qtyF = 0;
                    int sumQty = 0;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                        qtyA = Integer.parseInt(row.getStrValue("TMPT_A"));
                        qtyB = Integer.parseInt(row.getStrValue("TMPT_B"));
                        qtyC = Integer.parseInt(row.getStrValue("TMPT_C"));
                        qtyD = Integer.parseInt(row.getStrValue("TMPT_D"));
                        qtyE = Integer.parseInt(row.getStrValue("TMPT_E"));
                        qtyF = Integer.parseInt(row.getStrValue("TMPT_F"));
                        sumQty = qtyA + qtyB + qtyC + qtyD + qtyE + qtyF;
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="14%" align="center"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td width="24%"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_A")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_B")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_C")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_D")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_E")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("TMPT_F")%>
                </td>
                <td width="8%" align="center"><font color="#0033FF"><%=sumQty%>
                </font></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
    <%
        if (rows != null && rows.getSize() > 0) {
    %>
</form>
<div style="left:0; right:20">
    <%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%}%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function do_search() {
    	var orgId=document.mainFrm.organizationId.value
    	if(orgId==''){
    		alert('请选择公司');
    		return;
    	}
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        document.mainFrm.action = "/servlet/com.sino.ams.net.reportforms.servlet.OrderStatisticsServlet";
        document.mainFrm.submit();
    }

    function do_Export() {                  //导出execl
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.net.reportforms.servlet.OrderStatisticsServlet";
        mainFrm.submit();
    }

    function chooseSit() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
        var dialogWidth = 47.5;
        var dialogHeight = 30;
        var projects = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (projects) {
            dto2Frm(projects[0], "mainFrm");
        }
    }
</script>