<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.newasset.dto.EtsFaAssetsDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  Created by HERRY.
  Date: 2008-7-2
  Time: 15:39:12
--%>
<html>
<head><title>预龄资产查询</title>
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
<body leftmargin="0" topmargin="0">
<jsp:include page="/message/MessageProcess"/>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_search()')">
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    EtsFaAssetsDTO dto = (EtsFaAssetsDTO) request.getAttribute("ASSETS_DTO");
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
%>
<form action="/servlet/com.sino.ams.newasset.servlet.YlAssetsQueryServlet" name="mainForm" method="post">

    <script type="text/javascript">
        printTitleBar("预龄资产查询")
    </script>
    <table border="0" width="100%" id="table1" cellspacing="0" cellpadding="0" style="background-color:#efefef">
        <tr>
            <td width=10% align="right"> 公司名称：</td>
            <td width="16%"><select name="organizationId" id="organizationId"
                                    style="width:100%"><%=request.getAttribute("OU")%>
            </select></td>
            <td width="10%" align="right">资产编号：</td>
            <td width="14%"><input type="text" name="assetNumber" value="<%=dto.getAssetNumber()%>">
            </td>
            <td width="10%" align="right">资产条码：</td>
            <td width="16%"><input type="text" name="tagNumber" value="<%=dto.getTagNumber()%>">
            </td>
            <td width="10%" align="right">资产类别：</td>
            <td width="14%"><input type="text" name="faCategoryCode" value="<%=dto.getFaCategoryCode()%>">
            </td>
        </tr>
        <tr>
            <td align="right">资产名称：</td>
            <td><input type="text" name="assetsDescription" value="<%=dto.getAssetsDescription()%>"></td>
            <td align="right">规格型号：</td>
            <td><input type="text" name="modelNumber" value="<%=dto.getModelNumber()%>"></td>
            <td align="right">截止日期：</td>
            <td><input type="text" name="endDate" value="<%=dto.getEndDate()%>"
                       style="width:100%" title="点击选择截止日期" readonly class="readonlyInput"
                       onclick="gfPop.fEndPop('', endDate)">
            </td>
            <td colspan="2">
                <img src="/images/calendar.gif" alt="点击选择截止日期" onclick="gfPop.fEndPop('', endDate)"> &nbsp;
                <img src="/images/eam_images/search.jpg" alt="查询" onClick="do_search(); return false;"> &nbsp;
                <img src="/images/eam_images/export.jpg" alt="导出EXCEL" onclick="do_export()">
            </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="">
    <script type="text/javascript">
        var columnArr = new Array("公司名称", "资产条码", "资产编号", "资产名称", "资产型号", "资产类别", "责任人", "折旧年限", "启用日期");
        var widthArr = new Array("12%", "10%", "8%", "15%", "15%", "10%", "6%", "7%", "8%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:1px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#FFFFFF'">
                <td width="12%" align="center"><%=row.getValue("ORGANIZATION_NAME")%>
                </td>
                <td width="10%" align="left"><%=row.getValue("TAG_NUMBER")%>
                </td>
                <td width="8%" align="left"><%=row.getValue("ASSET_NUMBER")%>
                </td>
                <td width="15%"><%=row.getValue("ASSETS_DESCRIPTION")%>
                </td>
                <td width="15%"><%=row.getValue("MODEL_NUMBER")%>
                </td>
                <td width="10%" align="center"><%=row.getValue("FA_CATEGORY_CODE")%>
                </td>
                <td width="6%" align="center"><%=row.getValue("ASSIGNED_TO_NAME")%>
                </td>
                <td width="7%" align="center"><%=row.getValue("LIFE_IN_YEARS")%>
                </td>
                <td width="8%" align="center"><%=row.getValue("DATE_PLACED_IN_SERVICE")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>

</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
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
    function do_drop() {
        var transType = document.getElementById("transType")   ;
        var transStatus = document.getElementById("transStatus");
        dropSpecialOption(transType, 'BJBFS');
        //        dropSpecialOption(transStatus, 'APPROVED');
    }
    function do_ShowDetail(barcode) {
        var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=DETAIL_ACTION&barcode=" + barcode;
        var winName = "assetsWin";
        var style = "width=600,height=340,left=100,top=150";
        window.open(url, winName, style);
    }
</script>