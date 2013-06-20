<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/newasset/headerInclude.htm"%>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-11-19
  Time: 11:39:32
  To change this template use File | Settings | File Templates.
--%>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>无形资产</title>
</head>

<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String cityOption = parser.getAttribute("CITY_OPTION").toString();
    String assetNumber = parser.getParameter("assetNumber");
    String barcode = parser.getParameter("barcode");
    String itemName = parser.getParameter("itemName");
    String itemSpec = parser.getParameter("itemSpec");

%>
<body onkeydown="autoExeFunction('do_search()')">
<%=WebConstant.WAIT_TIP_MSG%>
<form name="mainFrm" method="post" action="/servlet/com.sino.ams.system.intangible.servlet.IntangibleServlet">
    <script type="text/javascript">
        printTitleBar("无形资产")
    </script>
    <table width="100%" topmargin="0" border="0" class="queryTable">
        <input type="hidden" name="act">
        <tr>
            <td width="10%" align="right">公司：</td>
            <td width="20%"><select name=organizationId style="width:90%" class="select_style1"><%=cityOption%>
            </select></td>
            <td width="10%" align="right">资产编号：</td>
            <td width="20%"><input style="width:90%" type="text" name="assetNumber" class="input_style1" value="<%=assetNumber%>"></td>
            <td width="10%" align="right">资产条码：</td>
            <td><input type="text" name="barcode" class="input_style1" value="<%=barcode%>" style="width:85%"></td>
            <td width="10%" align="center"></td>
        </tr>
        <tr>
            <td align="right" width="10%">资产名称：</td>
            <td><input type=text name=itemName style="width:90%" class="input_style1" value="<%=itemName%>"><a href=#
                                                                        title="点击选择资产名称"
                                                                        class="linka"
                                                                        onclick="do_SelectSpec();">[…]</a>
            </td>
            <td align="right" width="10%">规格型号：</td>
            <td><input type=text name=itemSpec style="width:90%" class="input_style1" value="<%=itemSpec%>"><a href=#
                                                                        title="点击选择规格型号"
                                                                        class="linka"
                                                                        onclick="do_SelectSpec();">[…]</a>
            </td>
            <!--<td width="10%" align="right">资产类别：</td>-->
            <%--<td width="20%"><input style="width:90%" type="text" name="itemSpec" value="<%=itemSpec%>"></td>--%>
            <td align="center" colspan="2">
            	<img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">
            	<img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()" alt="导出到Excel">
            </td>
        </tr>
    </table>

       <script type="text/javascript">
//        var columnArr = new Array("资产编号", "资产条码", "资产名称","规格型号","资产原值");
        var columnArr = new Array("公司", "资产编号", "资产条码", "资产名称","规格型号","摊销年限","折旧金额","启用日期","供应商");
        var widthArr = new Array("11%","12%","12%","15%","10%", "7%", "8%","8%","17%");
        printTableHead(columnArr, widthArr);
    </script>
    <div style="overflow-y:scroll;left:0px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'"
                onclick="show_detail()">
                <%--<td width="20%" align="center"><%=row.getValue("ASSET_NUMBER")%>--%>
                <%--</td>--%>
                <%--<td width="20%" align="left"><%=row.getValue("BARCODE")%>--%>
                <%--</td>--%>
                <%--<td width="20%" align="left"><%=row.getValue("ITEM_NAME")%>--%>
                <%--</td>--%>
                <%--<td width="20%" align="center"><%=row.getValue("ITEM_SPEC")%>--%>
                <%--</td>--%>
                <%--<td width="20%" align="left"><%=row.getValue("COST")%>--%>
                <%--</td>--%>
                <td width="11%" align="center"><%=row.getValue("COMPANY")%></td>
                <td width="12%" align="center"><%=row.getValue("ASSET_NUMBER")%></td>
                <td width="12%" align="left"><%=row.getValue("BARCODE")%></td>
                <td width="15%" align="left"><%=row.getValue("ITEM_NAME")%></td>
                <td width="10%" align="center"><%=row.getValue("ITEM_SPEC")%></td>
                <td width="7%" align="right"><%=row.getValue("LIFE_IN_YEARS")%></td>
                <td width="8%" align="right"><%=row.getValue("DEPRN_COST")%></td>
                <td width="8%" align="center"><%=row.getValue("DATE_PLACED_IN_SERVICE")%></td>
                <td width="17%" align="left"><%=row.getValue("VENDOR_NAME")%></td>
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
</html>

<script type="text/javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }
    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.submit();
    }
    function show_detail(itemCode) {
        <%--mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";--%>
//        mainFrm.action = "/servlet/com.sino.ams.system.intangible.servlet.IntangibleServlet";
//        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

    function do_SelectSpec() {
        document.mainFrm.itemName.value ="";
        document.mainFrm.itemSpec.value ="";
        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_SYSITEM%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var specs = getLookUpValues(lookUpSpec, dialogWidth, dialogHeight);
        if (specs) {
            var spec = null;
            for (var i = 0; i < specs.length; i++) {
                spec = specs[i];
                dto2Frm(spec, "mainFrm");
            }
        }
    }
</script>