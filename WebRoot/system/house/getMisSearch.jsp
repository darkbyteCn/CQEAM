<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.*" %>
<%@ page import="com.sino.ams.system.fixing.dto.EtsItemInfoDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2007-10-18
  Time: 15:57:18
  To change this template use File | Settings | File Templates.
--%>
<html>
<head><title>读取新增信息</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/BarVarSX.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");
    String fromDate = StrUtil.nullToString(request.getParameter("fromDate"));
    String toDate = StrUtil.nullToString(request.getParameter("toDate"));
    String isRent = (String) reqParser.getAttribute(WebAttrConstant.IS_RENT_OPTION);
    String isCertificate = (String) request.getAttribute(WebAttrConstant.ISLAND_CERTIFICATE_OPTION);
%>
<body onkeydown="autoExeFunction('do_search()');" onload="do_SetPageWidth();">
<%=WebConstant.WAIT_TIP_MSG%>
<form action="/servlet/com.sino.ams.system.house.servlet.GetMisHousInfoServlet" name="mainFrm" method="post">
<script type="text/javascript">
    printTitleBar("新增房屋土地资产");
    var ArrAction0 = new Array(true, "查看", "action_view.gif", "查看新增房屋土地资产", "do_search");
    var ArrAction1 = new Array(true, "导入", "action_complete.gif", "导入新增房屋土地资产", "do_misIn");
    var ArrAction2 = new Array(true, "导出", "toexcel.gif", "导出", "do_Export");
    var ArrActions = new Array(ArrAction0, ArrAction1, ArrAction2);
    printToolBar();
</script>
<div id="marqueetipMsg" style="position:absolute;top:44px;left:-259px;width:743px; z-index:10; visibility:hidden">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="">
        <td align="right">租赁日期：</td>
        <td align="left"><input type="text" name="fromDate" value="<%=fromDate%>" readOnly style="width:85%"
                                class="input2" onclick="gfPop.fStartPop(fromDate,toDate);"><img src="/images/calendar.gif"
                                                                                                alt="点击选择日期"
                                                                                                onclick="gfPop.fStartPop(fromDate,toDate);">
        </td>
        <td align="right">到：</td>
        <td align="right"><input type="text" name="toDate" value="<%=toDate%>" readOnly style="width:85%"
                                class="input2" onclick="gfPop.fEndPop(fromDate,toDate);"><img src="/images/calendar.gif"
                                                                                              alt="点击选择日期"
                                                                                              onclick="gfPop.fEndPop(fromDate,toDate);">
        </td>
    </table>
</div>

<input type="hidden" name="act" value="<%=action%>">
<script type="text/javascript">
    var columnArr = new Array("条码", "名称", "型号", "责任人","启用日期","地点");
    var widthArr = new Array("10%", "25%", "20%", "10%", "10%","25%");
    printTableHead(columnArr, widthArr);
</script>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div id="dataDiv" style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px" align="left">
    <table width="100%" border="1" bordercolor="#666666" id="dataTab">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" <%--onclick="show_detail('<%=row.getValue("BARCODE")%>','<%=row.getValue("SYSTEMID")%>')"--%>>
            <td height="22" width="10%" align="center"><%=row.getValue("TAG_NUMBER")%></td>
            <td height="22" width="25%" align="left"><%=row.getValue("ASSETS_DESCRIPTION")%></td>
            <td height="22" width="20%" align="left"><%=row.getValue("MODEL_NUMBER")%></td>
            <td height="22" width="10%"><%=row.getValue("ASSIGNED_TO_NAME")%></td>
            <td height="22" width="10%" align="center"><%=row.getValue("DATE_PLACED_IN_SERVICE")%></td>
            <td height="22" width="25%"><%=row.getValue("ASSETS_LOCATION")%></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</form>
<div style="left:0; right:20px" id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

<jsp:include page="/message/MessageProcess"/>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<iframe name="downFrm" src="" style="display:none"></iframe>
<script type="text/javascript">

    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.GetMisHousInfoServlet";
        mainFrm.submit();
    }

    function show_detail(barcode, systemId) {
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.GetMisHousInfoServlet?barcode=" + barcode + "&systemId=" + systemId;
        mainFrm.submit();
    }

    function do_misIn() {   //从mis导入房屋土地的信息                WorkPersonServlet
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.GetMisHousInfoServlet";
        mainFrm.submit();
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    }

    function do_Export() {                  //导出execl
        mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.house.servlet.GetMisHousInfoServlet";
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
        if (mainFrm.isRent.value == "Y") {
            document.getElementById("calendar").style.display = "block";
        } else {
            document.getElementById("calendar").style.display = "none";
            document.mainFrm.fromDate.value = "";
            document.mainFrm.toDate.value = "";
        }
    }
</script>