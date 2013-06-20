<%--
  Created by IntelliJ IDEA.
  User: V-jiachuanchuan
  Date: 2007-11-19
  Time: 14:58:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>未使用设备处理</title>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormValidate.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SelectProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/TableProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/datepicker.js"></script>
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <%
        RequestParser reqParser = new RequestParser();
        reqParser.transData(request);
        String action = reqParser.getParameter("act");
    %>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin=0 topmargin=0 onkeydown="do_check()">
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.match.servlet.UnusedAssetsServlet" method="post" name="mainFrm">
    <script>
        printTitleBar("未使用设备处理");
    </script>
    <input type="hidden" name="toDel">
    <table width="100%" class="queryTable">
        <input type="hidden" name="act" value="<%=action%>">
        <input type="hidden" name="groupId" value="">
        <tr>
            <td align="center" width="16%" valign="bottom"><a style="cursor:'hand'"><img src="/images/eam_images/delete.jpg"
                                                                                         onClick="do_DeleteData(); return false;"></a>
            </td>
            <td align="left" valign="top"><a style="cursor:'hand'"><img src="/images/eam_images/search.jpg"
                                                                        onClick="do_SearchOrder(); return false;"></a>
            </td>
        </tr>
    </table>
    <script type="text/javascript">
        //<table width="97%" align="left" border="1" cellpadding="2" cellspacing="0"
        //       style="position:absolute;left:1px;top:45px"
        //       class="headerTable">
        //    <tr height="22">
        //        <td width="16%" align="center">地点编号</td>
        //        <td width="17%" align="center">地点简称</td>
        //        <td width="27%" align="center">所在地点</td>
        //        <td width="20%" align="center">条码</td>
        //        <td width="20%" align="center">原MIS资产条码</td>
        //    </tr>
        //</table>
        var columnArr = new Array( "地点编号", "地点简称", "所在地点", "条码");
        var widthArr = new Array( "16%", "17%", "27%", "20%");
        printTableHead(columnArr, widthArr);
    </script>
    <%
        RowSet toDelRows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (toDelRows != null && !toDelRows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:350px;width:100%;position:absolute;top:69px;left:1px;margin-left:0px" align="left">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                Row toDelRow = null;
                for (int i = 0; toDelRows != null && i < toDelRows.getSize(); i++) {
                    toDelRow = toDelRows.getRow(i);
            %>
            <tr class="dataTR" height="22">
                <%--<td width = "3%" align = "center" ><input name="subCheck" type="checkbox" value= "<%=toDelRow.getValue("BARCODE")%>" ></td>--%>
                <td width="16%">
                    <%=toDelRow.getValue("WORKORDER_OBJECT_CODE") %>
                </td>
                <td width="17%">
                    <%=toDelRow.getValue("WORKORDER_OBJECT_NAME") %>
                </td>
                <td width="27%">
                    <%=toDelRow.getValue("WORKORDER_OBJECT_LOCATION") %>
                </td>
                <td width="20%">
                    <%=toDelRow.getValue("BARCODE") %>
                </td>
                <!--<td>-->
                    <%--<%=toDelRow.getValue("TAG_NUMBER") %>--%>
                <!--</td>-->
            </tr>
            <%
                    }
                }
            %>
        </table>
</div>
</form>
<div style="position:absolute;top:410px;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script language="javascript">
    function do_SearchOrder() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function doChecked() {

    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchOrder();
        }
    }

    function do_DeleteData(BARCODE) {
        if (confirm("确定要删除！")) {
            //        var barcode = mainFrm.barcode.value;
            mainFrm.act.value = "<%=WebActionConstant.DELETE_ACTION%>";
            mainFrm.action = "/servlet/com.sino.ams.match.servlet.UnusedAssetsServlet?BARCODE=" + BARCODE;
            mainFrm.submit();
        }
    }
</script>


