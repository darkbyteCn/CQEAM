<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%--
  Created by IntelliJ IDEA.
  User: jialongchuan
  Date: 2010-7-6
  Time: 11:03:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>应急演练情况维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>

</head>

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String drillName = parser.getParameter("drillName");
    String drillAddress = parser.getParameter("drillAddress");
    String startDate = parser.getParameter("startDate");
    String endDate = parser.getParameter("endDate");
    String orgOption = (String) request.getAttribute(WebAttrConstant.OU_OPTION);
%>
<body onkeydown="autoExeFunction('do_search()')" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.yj.servlet.AmsYjDrillServlet">
    <script type="text/javascript">
        printTitleBar("应急演练情况维护")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <input type="hidden" name="act">
        <tr>
            <td width="6%" align="right">公司名称：</td>
            <td width="11%"><select class="select_style1" style="width:100%" name="organizationId"><%=orgOption%>
            </select></td>
            <td width="6%" align="right">演练名称：</td>
            <td width="10%"><input class="input_style1" style="width:100%" type="text" name="drillName" value="<%=drillName%>"></td>
            <td width="6%" align="right">演练地点：</td>
            <td width="10%"><input class="input_style1" style="width:100%" type="text" name="drillAddress" value="<%=drillAddress%>"></td>
            <td width="15%" align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"><img
                    src="/images/eam_images/new.jpg" alt="新增" onClick="do_add();"><img src="/images/eam_images/export.jpg" style="cursor:'hand'"
                                                                                   onclick="do_Export();" title="导出到Excel">
            </td>
        </tr>
        <tr>
           <td width="6%"  align="right">演练时间：</td>
            <td width="11%">
                <input type="text" name="startDate" value="<%=startDate%>" style="width:100%" title="点击选择开始日期" readonly class="input_style1"
                       onclick="gfPop.fStartPop(startDate, endDate)">
            </td>
            <td width="6%" align="right">到：</td>
            <td width="10%">
                <input type="text" name="endDate" value="<%=endDate%>" style="width:100%" title="点击选择截止日期" readonly class="input_style1"
                       onclick="gfPop.fEndPop(startDate, endDate)">
            </td>
        </tr>
    </table>

    <div id="headDiv" style="overflow:hidden;position:absolute;top:70px;left:1px;width:100%">
        <table border="1" style="color: #FFFFFF" bgcolor="#2983CB" width="280%">
            <tr height="20">
                <td width="4%" align="center">公司名称</td>
                <td width="2%" align="center">序号</td>
                <td width="10%" align="center">演练名称</td>
                <td width="4%" align="center">演练类型</td>
                <td width="4%" align="center">演练性质</td>
                <td width="4%" align="center">演练时间</td>
                <td width="6%" align="center">演练地点</td>
                <td width="4%" align="center">参演人数</td>
                <td width="5%" align="center">演练装备数量</td>
                <td width="7%" align="center">参照预案</td>
                <td width="8%" align="center">演练存在问题</td>
                <td width="5%" align="center">是否需要完善预案</td>
                <td width="4%" align="center">完善预案计划时间</td>
                <td width="8%" align="center">备注</td>
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:70%;width:100%;position:absolute;top:92px;left:1px" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="280%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                if (rows != null && rows.getSize() > 0) {
                    String drillId = "";
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'"
                onclick="show_detail('<%=row.getValue("DRILL_ID")%>')">
                <td width="4%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("ORGANIZATION_NAME")%>">
                </td>
                <td width="2%" align="center" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("DRILL_ID")%>">
                </td>
                <td width="10%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("DRILL_NAME")%>">
                </td>
                <td width="4%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("DRILL_TYPE")%>">
                </td>
                <td width="4%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("DRILL_NATURE")%>">
                </td>
                <td width="4%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("DRILL_DATE")%>">
                </td>
                <td width="6%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("DRILL_ADDRESS")%>">
                </td>
                <td width="4%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("PEOPLE_QUALITY")%>">
                </td>
                <td width="5%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("EQUIPMENT_QUANTITY")%>">
                </td>
                <td width="7%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("PLAN1")%>">
                </td>
                <td width="8%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("QUESTION")%>">
                </td>
                <td width="5%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("IS_PERFECT")%>">
                </td>
                <td width="4%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("PLAN_DATE")%>">
                </td>
                <td width="8%" align="left" onclick="show_detail('<%=drillId%>');"><input type="text" class="finput" readonly
                                                                                         value="<%=row.getValue("REMARK")%>">
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div id="navigatorDiv" style="position:absolute;bottom:0px;left:0;"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script type="text/javascript">

    function initPage() {
        do_SetPageWidth();
    }

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function show_detail(drillId) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.yj.servlet.AmsYjDrillServlet?drillId=" + drillId;
        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

    function do_Export() {                  //导出execl
        document.mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        document.mainFrm.submit();
    }

</script>