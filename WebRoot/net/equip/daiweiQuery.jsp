<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant" %>

<%--
  Created by IntelliJ IDEA.
  User: jialongchuan
  Date: 2008-5-6
  Time: 9:09:30
  To change this template use File | Settings | File Templates.
--%>
<html>

<head>
    <title>设备查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>

</head>

<body onkeydown="autoExeFunction('do_search()')">


<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String cityOption = parser.getAttribute("CITY_OPTION").toString();
    String qryType = parser.getParameter("qryType");
    String barcode = parser.getParameter("barcode");
    String daiweiOption = parser.getAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION).toString();
%>
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("设备查询--按代维")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">公司：</td>
            <td width="20%" colspan=2><select name=organizationId><%=cityOption%>
            </select></td>
            <td width="10%" align="right">代维公司：</td>
            <td width="20%" colspan=2><select style="width:100%" name="daiweiOption"><%=daiweiOption%></select>
            </td>
            <td width="10%" align="right">条码：</td>
            <td width="20%" colspan=2><input type=text name=barcode value="<%=barcode%>"></td>
        </tr>
        <tr>
            <td align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
            <td><img src="/images/button/modifySummary.gif" id="particularImg" style="cursor:'hand'" onclick="do_showDetail()" alt="详细信息"></td>
            <td><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()" alt="导出到Excel"></td>
        </tr>
    </table>

    <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
    <input type="hidden" name="qryType" value="<%=qryType%>">


    <script type="text/javascript">
        var columnArr = new Array("", "条码", "设备名称", "规格型号", "地点编号", "所在地点","代维公司");
        var widthArr = new Array("4%", "10%", "10%", "15%",  "10%", "15%","15%");
        printTableHead(columnArr, widthArr);
    </script>

    <div style="overflow-y:scroll;left:0px;width:100%;height:350px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')">
                <td width="4%" align="center"><input type="radio" id="systemid" name="systemid" value="<%=row.getValue("BARCODE")%>"></td>
                <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%" align="center"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%" align="center"><%=row.getValue("WORKORDER_OBJECT_LOCATION")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%" align="center"><%=row.getValue("NAME")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>

</form>

<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page="<%=URLDefineList.MESSAGE_PROCESS%>" flush="true"/>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "<%=URLDefineList.QRY_ITEM_SERVLET%>";
        mainFrm.submit();
    }

    function do_showDetail() {
        var barcode = getRadioValue("barcode");
        if (barcode != "") {
            var url = "<%=URLDefineList.QRY_ITEM_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>" + "&qryType=<%=qryType%>";
        }
    }


    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "<%=URLDefineList.QRY_ITEM_SERVLET%>";
        mainFrm.submit();
        //        alert(getRadioValue("workorderObjectNo"));
    }

function do_ShowDetail(barcode) {
    var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
    var winName = "assetsWin";
    var style = "width=860,height=495,left=100,top=130";
    window.open(url, winName, style);
    }
</script>