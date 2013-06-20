<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.base.dto.Request2DTO" %>
<%@ page import="com.sino.ams.carddepot.dto.YsCardStorageDTO" %>
<%--
  created by YS
  Date: 2008-07-31
  Time: 2:20:36
--%>

<html>
<head>
    <title>当月出库统计</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>

</head>

<body onkeydown="autoExeFunction('do_search()')">

<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String depotDefineListOption = parser.getAttribute("depotDefineListOption").toString();
    String queryType = parser.getParameter("queryType");
    Request2DTO req2DTO = new Request2DTO();
    req2DTO.setDTOClassName(YsCardStorageDTO.class.getName());
    YsCardStorageDTO ysCardStorageDTO = (YsCardStorageDTO) req2DTO.getDTO(request);
%>
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("当月出库统计")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>

            <td width="10%" align="right">仓库名称：</td>
            <td width="20%"><select name="depotId" style="width:100%"><%=depotDefineListOption%></select></td>
            <td width="10%" align="right">区间：</td>
            <td width="15%"><input type="text" name="startDate" value="<%=ysCardStorageDTO.getStartDate()%>" style="width:100%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(startDate, endDate)"></td>

            <td width="15%"><input type="text" name="endDate" value="<%=ysCardStorageDTO.getEndDate()%>" style="width:100%" title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(startDate, endDate)"></td>
            <td align="center">
            <td align="center">
                <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
            <td><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()"
                     alt="导出到Excel"></td>
        </tr>
    </table>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" class="headerTable" border="1">
            <tr height="20">
                <td width="10%" align="center">名称</td>
                <td width="10%" align="center">申领部门</td>
                <td width="10%" align="center">数量</td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="act">
    <input type="hidden" name="queryType" value='<%=queryType%>'>

    <div style="overflow-y:scroll;left:0px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">

                <td width="10%" align="center"><%=row.getValue("CARD_NAME")%></td>
                <td width="10%" align="center"><%=row.getValue("DEPOT_NAME")%></td>
                <td width="10%" align="center"><%=row.getValue("QUANTITY")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js" src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.carddepot.servlet.YsCardStorageServlet";
        mainFrm.submit();
    }   function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.carddepot.servlet.YsCardStorageServlet";
        mainFrm.submit();
    }
</script>