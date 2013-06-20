<%@ page contentType = "text/html;charset=GBK" language = "java" %>
<%@ page import = "com.sino.base.constant.db.QueryConstant" %>
<%@ page import = "com.sino.base.data.Row" %>
<%@ page import = "com.sino.base.data.RowSet" %>
<%@ page import = "com.sino.base.web.request.upload.RequestParser" %>
<%@ page import = "com.sino.base.constant.web.WebActionConstant" %>
<%@ page import = "com.sino.ams.constant.URLDefineList" %>
<%@ page import = "com.sino.ams.constant.LookUpConstant" %>
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
    <link rel = "stylesheet" type = "text/css" href = "/WebLibary/css/main.css">
    <script language = "javascript" src = "/WebLibary/js/Constant.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CommonUtil.js"></script>
    <script language = "javascript" src = "/WebLibary/js/FormProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBar.js"></script>
    <script language = "javascript" src = "/WebLibary/js/SinoToolBarConst.js"></script>
    <script language = "javascript" src = "/WebLibary/js/jslib.js"></script>
    <script language = "javascript" src = "/WebLibary/js/CheckboxProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/RadioProcess.js"></script>
    <script language = "javascript" src = "/WebLibary/js/LookUp.js"></script>
    <script language = "javascript" src = "/WebLibary/js/calendar.js"></script>

</head>

<body onkeydown="autoExeFunction('do_search()')">


<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String cityOption = parser.getAttribute("CITY_OPTION").toString();
    String financePropOption = parser.getAttribute("FINANCE_PROP_OPTION").toString();
    String qryType = parser.getParameter("qryType");
     String itemSpec = parser.getParameter("itemSpec");
   String projectName = parser.getParameter("projectName");

%>
<form method = "post" name = "mainFrm">
    <script type = "text/javascript">
        printTitleBar("设备查询--按分类")
    </script>
    <iframe width = 174 height = 189 name = "gToday:normal:calendar.js" id = "gToday:normal:calendar.js"
        src = "/WebLibary/js/DateHTML.htm" scrolling = "no" frameborder = "0"
        style = "visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
    <table width = "100%" border = "0" class="queryHeadBg">
        <tr>
            <td width = "10%" align = "right">公司：</td>
            <td width = "15%" align = "lift"><select name = organizationId style = "width:100%"><%=cityOption%></select></td>
            <td width = "15%" align = "right">启用日期(≥)：</td>
            <td width = "10%" align = "left"><input type = "text" readonly = "true" class = "readonlyInput" name = "minTime" size = "25" style = "width:70%" onclick = "gfPop.fPopCalendar(minTime)"><img src = "/images/calendar.gif" alt = "点击选择日期" onclick = "gfPop.fPopCalendar(minTime)"></td>
            <td width = "10%" align = "right">所属项目：</td>
            <td width = "15%" align = "left"><input name = projectName  type = text  value="<%=projectName%>"><input type = hidden name = projectId><a href = # title = "点击选择项目" class = "linka" onclick = "do_SelectProj();">[…]</a></td>
            <td width = "10%" align = "center"><img src = "/images/eam_images/search.jpg" style = "cursor:'hand'" onclick = "do_search();" alt = "查询"></td>
            <td><img src = "/images/eam_images/export.jpg" id = "queryImg" style = "cursor:'hand'" onclick = "do_exportToExcel()" alt = "导出Excel"></td>
        </tr>

        <tr><td align = "right">资产类型：</td>
            <td align = "lift"><select name = financeProp style = "width:100%"><%=financePropOption%></select></td>
            <td align = "right">启用日期(≤)：</td>
            <td align = "left"><input type = "text" readonly = "true" class = "readonlyInput" name = "maxTime" size = "25" value = "" style = "width:70%" onclick = "gfPop.fPopCalendar(maxTime)"><img src = "/images/calendar.gif" alt = "点击选择日期" onclick = "gfPop.fPopCalendar(maxTime)"></td>
            <td align = "right">设备型号：</td>
            <td align = "left"><input type = text name = itemSpec value="<%=itemSpec%>"><a href = # title = "点击选择设备型号" class = "linka" onclick = "do_SelectSpec();">[…]</a></td>
            <td width = "10%" align = "center"><img src = "/images/eam_images/change_history.jpg" id = "particularImg" style = "cursor:'hand'" onclick = "do_showDetail()" alt = "显示设备变动历史"></td>
        </tr>
    </table>

    <input type = "hidden" name = "act" value = "<%=parser.getParameter("act")%>">
    <input type = "hidden" name = "qryType" value = "<%=qryType%>">

 <script type="text/javascript">
        var columnArr = new Array("","标签号", "设备名称", "设备型号", "设备分类", "启用日期","所属项目","地点简称","所属区县");
        var widthArr = new Array("4%", "10%", "10%", "15%", "8%","10%","15%","15%","8%");
        printTableHead(columnArr, widthArr);
    </script>

    <div style = "overflow-y:scroll;left:0px;height:72%;width:100%;position:absolute;top:98px">
        <table width = "100%" border = "1" bordercolor = "#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class = "dataTR"  onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')">
                <td width = "4%" align = "center"><input type = "radio" id = "systemid" name = "systemid" value = "<%=row.getValue("BARCODE")%>"></td>
                <td style = "word-wrap:break-word" height = "22" width = "10%" align = "center"><%=row.getValue("BARCODE")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "10%" align = "center"><%=row.getValue("ITEM_NAME")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "15%" align = "center"><%=row.getValue("ITEM_SPEC")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "8%" align = "center"><%=row.getValue("ITEM_CATEGORY")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "10%" align = "center"><%=row.getValue("START_DATE")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "15%" align = "center"><%=row.getValue("PROJECT_NAME")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "15%" align = "center"><%=row.getValue("WORKORDER_OBJECT_NAME")%></td>
                <td style = "word-wrap:break-word" height = "22" width = "8%" align = "center"><%=row.getValue("COUNTY_NAME")%></td>
            </tr>
            <%
                }  }
            %>
        </table>
    </div>

</form>

<div style="position:absolute;top:92%;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
<jsp:include page = "<%=URLDefineList.MESSAGE_PROCESS%>" flush = "true" />
</body>

</html>
<script type = "text/javascript">
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