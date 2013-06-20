<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-3-25
  Time: 16:47:34
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>已匹配信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/RadioProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
</head>
<body leftmargin="0" topmargin="0">
<script>
    var ArrAction1 = new Array(true, "取消匹配", "act_refresh.gif", "取消匹配", "dodematch()");
    var ArrAction2 = new Array(true, "关闭", "del.gif", "关闭", "window.close()");
    var ArrActions = new Array(ArrAction1, ArrAction2);
    var ArrSinoViews = new Array();
    printTitleBar("已匹配信息");
    printToolBar();
    var columnArr = new Array("checkbox", "EAM地点", "MIS地点");
    var widthArr = new Array("3%", "45%", "45%");
    printTableHead(columnArr, widthArr);
</script>
<div style="overflow-y:scroll;height:490px;width:100%" align="left"
     onscroll="document.getElementById('headDiv').scrollLeft = this.scrollLeft;">
    <form action="" name="mainForm" method="post">
        <input type="hidden" name="act">
        <table width="100%" border="1" bordercolor="#9FD6FF">
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
                Row row = null;
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
//                        Logger.logInfo("size = " + rows.getSize());
                        row = rows.getRow(i);
            %>
            <tr class="hei" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
                <td height="22" width="3%" align="center"><input type="checkbox" name="subCheck" value="<%=row.getValue("NO_AND_LOCATION")%>">
                </td>
                <td height="22" width="45%"><%=row.getValue("WORKORDER_OBJECT_LOCATION") %>
                </td>
                <td height="22" width="45%"><%=row.getValue("ASSETS_LOCATION") %>
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

    function dodematch() {
            if (getCheckedBoxCount("subCheck") <1) {
                alert("请先选择一条数据！");
                return;
            }
            if (confirm("确定要解除匹配？")) {
                document.forms[0].action = "/servlet/com.sino.ams.match.amsMisLocMatch.servlet.amsMisLocMatchServlet?act=UNMACH";
//                window.parent.action = "/servlet/com.sino.ams.match.amsMisLocMatch.servlet.amsMisLocMatchServlet";
//                window.parent.submit();
                 window.opener.document.forms[0].action = "/match/amsMisLocFrame.jsp";
                 window.opener.document.forms[0].submit();
                 document.forms[0].submit();
            }
        }
</script>
</html>