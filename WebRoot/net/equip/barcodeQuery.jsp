<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.newasset.constant.AssetsActionConstant"%>

<%--
  created by YS
  Date: 2007-09-28
  Time: 8:23:36
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

%>
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("设备查询--按条码")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">公司：</td>
            <td width="20%" colspan=2><select name=organizationId><%=cityOption%>
            </select></td>
            <td width="10%" align="right">标签号：</td>
            <td width="20%" colspan=2><input name=barcode type=text style="width:80%" value=<%=barcode%>></td>
  
            <td align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询"></td>
            <td><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()" alt="导出Excel"></td>
            <td><img src="/images/eam_images/change_history.jpg" id="particularImg" style="cursor:'hand'" onclick="do_showDetail()" alt="显示设备变动历史"></td>
        </tr>
    </table>

    <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
    <input type="hidden" name="qryType" value="<%=qryType%>">

  <script type="text/javascript">
        var columnArr = new Array("","标签号", "设备名称", "设备型号", "设备分类", "启用日期","所属项目","地点简称","所属区县");
        var widthArr = new Array("4%", "10%", "10%", "15%", "8%","10%","15%","15%","8%");
        printTableHead(columnArr, widthArr);
    </script>

    <div style="overflow-y:scroll;left:0px;height:72%;width:100%;position:absolute;top:68px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class="dataTR"  onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')">
                <td width="4%" align="center"><input type="radio" id="systemid" name="systemid"
                                                     value="<%=row.getValue("BARCODE")%>"></td>
                <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%" align="center"><%=row.getValue("ITEM_SPEC")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="8%"
                    align="center"><%=row.getValue("ITEM_CATEGORY")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("START_DATE")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%"
                    align="center"><%=row.getValue("PROJECT_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%"
                    align="center"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="8%" align="center"><%=row.getValue("COUNTY_NAME")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>

</form>

<div style="position:absolute;top:92%;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
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
        var barcode = getRadioValue("systemid");
//        if (barcode != "") {
            <%--var url = "<%=URLDefineList.QRY_ITEM_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>" + "&qryType=<%=qryType%>";--%>
//        }
        if (barcode != "") {
            var url = "/servlet/com.sino.ams.newasset.servlet.AmsItemInfoHistoryServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
            var style = "width=1017,height=700,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
            window.open(url, 'historyWin', style);
        }
    }


    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "<%=URLDefineList.QRY_ITEM_SERVLET%>";
        mainFrm.submit();
        //        alert(getRadioValue("workorderObjectNo"));
    }
    function do_SelectProj() {
        var lookUpProj = "<%=LookUpConstant.LOOK_UP_PROJECT2%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var projs = getLookUpValues(lookUpProj, dialogWidth, dialogHeight);
        if (projs) {
            var proj = null;
            for (var i = 0; i < projs.length; i++) {
                proj = projs[i];
                dto2Frm(proj, "mainFrm");
            }
        }
    }
    function do_SelectSpec() {

        var lookUpSpec = "<%=LookUpConstant.LOOK_UP_ITEM_SIMPLE%>";
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
function do_ShowDetail(barcode) {
    var url = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=<%=AssetsActionConstant.DETAIL_ACTION%>&barcode=" + barcode;
    var winName = "assetsWin";
    var style = "width=860,height=495,left=100,top=130";
    window.open(url, winName, style);
}
</script>