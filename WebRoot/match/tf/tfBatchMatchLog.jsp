<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>通服资产匹配历史</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
</head>
<body leftmargin="0" topmargin="0">
<script>
    var ArrAction1 = new Array(true, "取消匹配", "act_refresh.gif", "取消匹配", "unMatch()");
    var ArrAction2 = new Array(true, "按地点匹配", "act_query.gif", "按地点匹配", "matchByLocation()");
    var ArrAction3 = new Array(true, "按县匹配", "act_query.gif", "按县匹配", "matchByCounty()");
    var ArrAction4 = new Array(true, "按市匹配", "act_query.gif", "按市匹配", "matchByCity()");
    var ArrAction5 = new Array(true, "刷新", "act_refresh.gif", "刷新页面", "refreshPage()");
    var ArrAction6 = new Array(true, "关闭", "del.gif", "关闭", "window.close()");
    var ArrActions = new Array(ArrAction1, ArrAction2, ArrAction3, ArrAction4, ArrAction5, ArrAction6);
    var ArrSinoViews = new Array();
    printTitleBar("匹配历史记录");
    printToolBar();
    var columnArr = new Array("", "批号", "EAM设备描述", "EAM规格型号", "MIS设备描述", "MIS规格型号", "匹配类别", "执行人", "执行时间");
    var widthArr = new Array("3%", "6%", "15%", "15%", "15%", "15%", "6%", "6%", "7%");
    printTableHead(columnArr, widthArr);
</script>
<div style="overflow-y:scroll;height:490px;width:100%" align="left"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <form action="/servlet/com.sino.ams.match.servlet.TFBatchMatchLogServlet" name="mainForm" method="post">
        <input type="hidden" name="act">
        <table width="100%" border="1" bordercolor="#9FD6FF">
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
                Row row = null;
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
//                        Logger.logInfo("size = " + rows.getSize());
                        row = rows.getRow(i);
                        String matchType = String.valueOf(row.getValue("MATCH_TYPE"));
                        String matchTypeName = "";
                        if (matchType.equals("0")) {
                            matchTypeName = "按地点";
                        } else if (matchType.equals("1")) {
                            matchTypeName = "按县";
                        } else if (matchType.equals("2")) {
                            matchTypeName = "按市";
                        }

            %>
            <tr class="hei" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td height="22" width="3%" align="center"><input type="radio" name="batchId"
                                                                 value="<%=row.getValue("ID")%>">
                </td>
                <td height="22" width="6%"><%=row.getValue("ID") %>
                </td>
                <td height="22" width="15%"><%=row.getValue("ETS_NAME") %>
                </td>
                <td height="22" width="15%"><%=row.getValue("ETS_SPEC") %>
                </td>
                <td height="22" width="15%"><%=row.getValue("MIS_NAME") %>
                </td>
                <td height="22" width="15%"><%=row.getValue("MIS_SPEC") %>
                </td>
                <td height="22" width="6%"><%=matchTypeName%>
                </td>
                <td height="22" width="6%"><%=row.getValue("CREATED_USER") %>
                </td>
                <td height="22" width="7%"><%=String.valueOf(row.getValue("CREATION_DATE")).substring(0, 10) %>
                </td>

            </tr>

            <%
                    }
                }
            %>
        </table>
    </form>
</div>
<%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
<div id="showMsg"><%=StrUtil.nullToString(request.getAttribute("showMsg"))%>
    <jsp:include page="/message/MessageProcess"/>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
<script type="text/javascript">
    function unMatch() {
        performTask("UN_MATCH");
    }
    function matchByLocation() {
        performTask("matchByLocation");
    }
    function matchByCounty() {
        performTask("matchByCounty");
    }
    function matchByCity() {
        performTask("matchByCity");
    }
    function refreshPage(){
        document.mainForm.act.value = "";
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        document.mainForm.submit();
    }
    function performTask(condition) {
        if (checkData()) {
            document.mainForm.act.value = condition;
            document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
            document.mainForm.submit();
        }
    }
    function checkData() {
        var rs = true;
        var batchId = getRadioValue("batchId");
        if (batchId == "") {
            alert("请先选择一条数据！");
            rs = false;
        }
        return rs;
    }
</script>
</html>