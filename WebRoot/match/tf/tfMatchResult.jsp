<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.jsp" %>
<%@ include file="/newasset/headerInclude.htm" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>通服资产匹配监控报表</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/jslib.js"></script>


</head>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
//    EtsOuCityMapDTO dto = (EtsOuCityMapDTO) request.getAttribute(QueryConstant.QUERY_DTO);
%>
<body  onkeydown="autoExeFunction('do_Search()')" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.match.servlet.TFMatchResultServlet">
    <script type="text/javascript">
        printTitleBar("通服资产匹配监控报表")
    </script>
    <table width="100%">
        <tr>
            <td width="10%" align="right">公司名称：</td>
            <td width="80%">
                <select class="select_style1" size="1" name="organizationId" style="width:28%"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%></select>
            </td>
            <td width="20%">
                <img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_Search();">&nbsp;
                <img src="/images/eam_images/export.jpg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">
            </td>
        </tr>
    </table>
    <input name="act" type="hidden">
    <input name="company" type="hidden">
    <div id="headDiv" style="overflow:hidden;position:absolute;top:45px;left:1px;width:840px">
        <table class="headerTable" border="1" width="100%">
            <tr height="22">
                <td width="16%" align="center">公司名称</td>
                <td width="10%" align="center">MIS资产数量</td>
                <td width="10%" align="center">已匹配资产数量</td>
                <td width="10%" align="center">未匹配资产数量</td>
                <!-- <td width="10%" align="center">已同步资产数量</td> -->
                <!-- <td width="10%" align="center">未同步资产数量</td> -->
                <td width="10%" align="center">已匹配资产百分比</td>
                <!-- <td width="10%" align="center">已同步资产百分比</td> -->
            </tr>
        </table>
    </div>
    <div id="dataDiv" style="overflow:scroll;height:370px;width:857px;position:absolute;top:68px;left:1px" align="left"
         onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
        <table id="dataTable" width="100%" border="1" bordercolor="#666666" style="TABLE-LAYOUT:fixed;word-break:break-all">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td width="16%"><%=row.getValue("COMPANY")%></td>
                <td width="10%" align="right"><%=row.getValue("MIS_COUNT")%></td>
                <td width="10%" align="right"><%=row.getValue("MATCH_COUNT")%></td>
                <td width="10%" align="right"><%=row.getValue("NO_MATCH_COUNT")%></td>


                <td width="10%" align="right"><%=row.getValue("MATCH_RATE")%></td>

            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div style="position:absolute;top:92%;left:0; right:20px"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>

</html>
<script type="text/javascript">

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_Export() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.submit();
    }

    function initPage() {
        do_SetPageWidth();
    }
</script>

