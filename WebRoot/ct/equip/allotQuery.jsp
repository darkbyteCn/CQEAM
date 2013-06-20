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
  created by Yu
  Date: 2008-12-03
  Time: 13:33:36
--%>

<html>

<head>
    <title>村通设备查询</title>
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
<style>
.finput {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;}
.finput2 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:center;}
.finput3 {WIDTH:100%;BORDER-RIGHT: 0px ridge;BORDER-TOP: 0px ridge; BORDER-LEFT: 0px ridge ; BORDER-BOTTOM: 0px ridge;font-size: 12px;text-align:right;}
</style>
</head>

<body onkeydown="autoExeFunction('do_search()')">


<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String cityOption = parser.getAttribute("CITY_OPTION").toString();
    String invTypeOption = parser.getAttribute("INV_TYPE_OPTION").toString();
    String qryType = parser.getParameter("qryType");
    String itemSpec = parser.getParameter("itemSpec");
    String projectName = parser.getParameter("projectName");
    String workorderObjectName = parser.getParameter("workorderObjectName");

%>
<form method="post" name="mainFrm">
    <script type="text/javascript">
        printTitleBar("村通设备查询--按仓库")
    </script>
    <table width="100%" border="0" class="queryHeadBg">
        <tr>
            <td width="10%" align="right">仓库类型：</td>
            <td width="20%"><select style="width:100%" name=objectCategory><%=invTypeOption%>
            </select></td>
            <td width="10%" align="right">地点简称：</td>
            <td width="20%"><input name=workorderObjectName type=text value="<%=workorderObjectName%>"
                                   style="width:80%"><a href=#
                                                        title="点击选择地点"
                                                        class="linka"
                                                        onclick="do_SelectInv();">[…]</a>
            </td>
            <td align="right">规格型号：</td>
            <td><input type=text name=itemSpec value="<%=itemSpec%>"><a href=#
                                                                        title="点击选择规格型号"
                                                                        class="linka"
                                                                        onclick="do_SelectSpec();">[…]</a>
            </td>
        </tr>
        <tr>
            <td align="right">公司：</td>
            <td><select name=organizationId style="width:100%"><%=cityOption%>
            </select></td>
            <td align="right">所属工程：</td>
            <td><input name=projectName  type=text style="width:80%" value="<%=projectName%>"><input type=hidden
                                                                                                        name=projectId><a
                    href=# title="点击选择所属工程" class="linka" onclick="do_SelectProj();">[…]</a></td>
            <td align="center"><img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();"
                                    alt="查询"></td>
            <td><img src="/images/button/modifySummary.gif" id="particularImg" style="cursor:'hand'"
                     onclick="do_showDetail()" alt="详细信息"></td>
            <td><img src="/images/eam_images/export.jpg" id="queryImg" style="cursor:'hand'" onclick="do_exportToExcel()"
                     alt="导出到Excel"></td>
        </tr>
    </table>

    <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">
    <input type="hidden" name="qryType" value="<%=qryType%>">

     <script type="text/javascript">
        var columnArr = new Array("","条码", "设备名称", "规格型号", "设备分类", "启用日期","所属工程","地点简称","所属区县");
        var widthArr = new Array("4%", "10%", "10%", "15%", "8%","10%","15%","15%","8%");
        printTableHead(columnArr, widthArr);
    </script>

    <div style="overflow-y:scroll;left:0px;width:100%;height:360px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("BARCODE")%>')">
                <td width="4%" align="center"><input type="radio" id="systemid" name="systemid"
                                                     value="<%=row.getValue("BARCODE")%>"></td>
                <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("BARCODE")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="10%" align="left"><%=row.getValue("ITEM_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%" align="left">
                <input type="text" value="<%=row.getValue("ITEM_SPEC")%>" readonly="readonly" style="width: 100%" class="finput">
                <td style="word-wrap:break-word" height="22" width="8%"
                    align="left"><%=row.getValue("ITEM_CATEGORY")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="10%" align="center"><%=row.getValue("START_DATE")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%"
                    align="left"><%=row.getValue("PROJECT_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="15%"
                    align="left"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td style="word-wrap:break-word" height="22" width="8%" align="left">
                <input type="text" value="<%=row.getValue("COUNTY_NAME")%>" readonly="readonly" size="6" style="width: 100%" class="finput">
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
        mainFrm.action = "<%=URLDefineList.QRY_ETS_ITEM_INFO_SERVLET%>";
        mainFrm.submit();
    }

    function do_showDetail() {
        var barcode = getRadioValue("barcode");
        if (barcode != "") {
            var url = "<%=URLDefineList.QRY_ETS_ITEM_INFO_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>" + "&qryType=<%=qryType%>";
        }
    }


    function do_exportToExcel() {
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "<%=URLDefineList.QRY_ETS_ITEM_INFO_SERVLET%>";
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
    function do_SelectInv() {
        var lookUpInv = "<%=LookUpConstant.LOOK_UP_ADDRESS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var invs = getLookUpValues(lookUpInv, dialogWidth, dialogHeight);
        if (invs) {
            var inv = null;
            for (var i = 0; i < invs.length; i++) {
                inv = invs[i];
                dto2Frm(inv, "mainFrm");
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