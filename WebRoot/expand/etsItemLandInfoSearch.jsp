<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.*" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-18
  Time: 15:57:18
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>土地信息查询</title>
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
</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    String fromDate = StrUtil.nullToString(request.getParameter("fromDate"));
    String toDate = StrUtil.nullToString(request.getParameter("toDate"));
    String isRent = (String) reqParser.getAttribute(WebAttrConstant.IS_RENT_OPTION);
    String hasCertficate = (String) request.getAttribute(WebAttrConstant.ISLAND_CERTIFICATE_OPTION);
%>

<body onkeydown="autoExeFunction('do_search()');" onload="initPage();">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.expand.servlet.EtsItemLandInfoServlet" name="mainFrm" method="post">
<script type="text/javascript">
    printTitleBar("土地管理")
</script>

<table border="0" bordercolor="red" width="100%" class="queryHeadBg">
    <tr>
        <td width="10%" align="right">土地条码：</td>
        <td width="15%" align="left">
        <input type="text" name="barcode" style="width:100%" value="<%=reqParser.getParameter("barcode")%>">
        </td>
        <td width="10%" align="right">是否有土地使用证：</td>
        <td width="15%" align="left"><select name="hasCertficate" style="width:100%"><%=hasCertficate%>
        </select></td>
        <td width="10%" align="right">是否租赁：</td>
        <td width="15%" align="left"><select style="width:100%" name="isRent"><%=isRent%>
        </select></td>
        <td width="10%"></td>
    </tr><!--style="display:none;"  -->
    <tr id="calendar" >
        <td align="right">租赁日期：</td>
        <td align="left">
        <input type="text" name="fromDate" value="<%=fromDate%>" readOnly style="width:100%" class="input2" onclick="gfPop.fStartPop(fromDate,toDate);">
        </td>
        <td align="left">
        <img src="/images/calendar.gif" alt="点击选择日期"  onclick="gfPop.fStartPop(fromDate,toDate);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到：
        </td>
        <td align="left">
        <input type="text" name="toDate" value="<%=toDate%>" readOnly style="width:100%" class="input2" onclick="gfPop.fEndPop(fromDate,toDate);">
        </td>
        <td><img src="/images/calendar.gif"  alt="点击选择日期"  onclick="gfPop.fEndPop(fromDate,toDate);">
        </td>
        <td align="left">
            <img src="/images/button/query.gif" alt="查询土地管理" onClick="do_search(); return false;">&nbsp;&nbsp;&nbsp;&nbsp;
            <!--<img src="/images/button/new.gif"  alt="新增房屋" onClick="do_add(); return false;">
             -->
            <img
                src="/images/button/toExcel.gif" id="queryImg" style="cursor:'hand'" onclick="do_Export();" alt="导出到Excel">
        </td>
        <td width="10%"></td>
    </tr>
</table>
<input type="hidden" name="act" value="<%=action%>">


<script type="text/javascript">
    var columnArr = new Array("土地条码", "土地面积", "地积单位", "土地证号", "区县", "是否租赁", "出租人", "租赁日期", "截至日期");
    var widthArr = new Array("10%", "7%", "7%", "15%", "15%", "7%", "10%", "10%", "10%");
    printTableHead(columnArr, widthArr);
</script>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px"
     align="left">
    <table width="100%" border="1" bordercolor="#666666" id="dataTab">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="show_detail('<%=row.getValue("BARCODE")%>','<%=row.getValue("SYSTEMID")%>')">
            <td height="22" width="10%" align="center"><%=row.getValue("BARCODE")%>
            </td>
            <td height="22" width="7%" align="right"><%=row.getValue("LAND_AREA")%>
            </td>
            <td height="22" width="7%" align="center"><%=row.getValue("AREA_UNIT")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("LAND_CERTFICATE_NO")%>
            </td>
            <td height="22" width="15%" align="center"><%=row.getValue("LAND_ADDRESS")%>
            </td>
            <td height="22" width="7%" align="center"><%=row.getValue("IS_RENT")%>
            </td>
            <td height="22" width="10%" align="center"><%=row.getValue("RENT_PERSON")%>
            </td>
            <td height="22" width="10%" align="center"><%=row.getValue("RENT_DATE")%>
            </td>
            <%--<td height="22" width="10%" align="center"><%=row.getValue("PAY_TYPE")%>--%>
            <%--</td>--%>
            <td height="22" width="10%" align="center"><%=row.getValue("END_DATE")%>
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

<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>

<iframe name="downFrm" src="" style="display:none"></iframe>
<script type="text/javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.expand.servlet.EtsItemLandInfoServlet";
        mainFrm.submit();
    }

    function show_detail(barcode, systemId) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.expand.servlet.EtsItemLandInfoServlet?barcode=" + barcode + "&systemId=" + systemId;
        mainFrm.submit();
    }

    function do_add() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.expand.servlet.EtsItemLandInfoServlet";
        mainFrm.submit();
    }

    function do_Export() {                  //导出execl
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.expand.servlet.EtsItemLandInfoServlet";
        mainFrm.submit();
    }

    function do_showCalendar1() {
        var isRent = document.all["isRent"].options;
        for (var i = 0; i < isRent.length; i++) {
            if (isRent[i].selected && isRent[i].value == "Y") {
                //        mainFrm.rentInfo.style.display = "none";
                //        document.all["rentInfo"].style.display = "inline";
                document.getElementById("marqueetipMsg").style.visibility = "visible";
            } else if (isRent[i].selected && isRent[i].value == "N") {
                document.getElementById("marqueetipMsg").style.visibility = "hidden";
                document.mainFrm.fromDate.value = "";
                document.mainFrm.toDate.value = "";

            } else if (isRent[i].selected && isRent[i].value == "") {
                document.getElementById("marqueetipMsg").style.visibility = "hidden";
                document.mainFrm.fromDate.value = "";
                document.mainFrm.toDate.value = "";
            }
        }
        //display:inline
    }

    function do_showCalendar() {
        if (mainFrm.isRent.value == "Y"||mainFrm.isRent.value == "") {
            document.getElementById("calendar").style.display = "block";
        } else {
            document.getElementById("calendar").style.display = "none";
            document.mainFrm.fromDate.value = "";
            document.mainFrm.toDate.value = "";
        }
    }
    function initPage(){
        if (mainFrm.isRent.value == "Y") {
            document.getElementById("calendar").style.display = "block";
        }
    }
</script>

</html>