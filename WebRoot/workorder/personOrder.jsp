<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  User: zhoujs
  Date: 2008-1-18
  Time: 10:22:27
  Function:个人工单清单签收、签收、重新分配执行人.
--%>

<head>
    <title>个人工单查询</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>
    <%
        RequestParser reqParser = new RequestParser();
        reqParser.transData(request);
        String workorderNo = reqParser.getParameter("workorderNo");
        String startDate = reqParser.getParameter("startDate");
        String action = reqParser.getParameter("act");
        String queryType = reqParser.getParameter("queryType");
    %>

</head>

<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="do_check()">
<jsp:include page="/message/MessageProcess"/>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.workorder.servlet.PersonOrderServlet">
    <script type="text/javascript">
        printTitleBar("工单签收");
    </script>
    <table width="100%" border="0" bgcolor='#efefef' cellpadding="2" cellspacing="0"
            >
        <input type="hidden" name="act" value="<%=action%>">
        <input type="hidden" name="groupId" value="">
        <input type="hidden" name="queryType" value="<%=queryType%>">
        <tr>
            <td align="right" width="10%">工单号：</td>
            <td width="25%"><input style="width:80%" type="text" name="workorderNo" value="<%=workorderNo%>"></td>
            <td align="right" width="15%">开始时间大于：</td>
            <td width="30%"><input type="text" name="startDate"
                                   value="<%=startDate%>" class="readonlyInput"
                                   style="width:80%" title="点击选择日期"
                                   readonly onclick="gfPop.fEndPop('',startDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop('',startDate)"></td>
            <td width="20%"><a style="cursor:'hand'">
                <img src="/images/eam_images/search.jpg" alt="点击查询" onClick="do_SearchOrder(); return false;">
                <img src="/images/eam_images/sign.jpg" alt="选择执行人" onClick="sign(); return false;">
                </a>
            </td>
        </tr>
    </table>
    <!--<script type="text/javascript">-->
        <!--var columnArr = new Array("checkbox", "公司", "工单号", "工单状态", "工单类型", "地点编号", "所在地点", "开始日期", "实施周期(天)", "执行人", "实际完成日期", "差异", "超时");-->
        <!--var widthArr = new Array("3%", "8%", "10%", "6%", "6%", "7%", "12%", "7%", "8%", "5%", "8%", "3%", "3%");-->
        <!--printTableHead(columnArr, widthArr);-->
    <!--</script>-->
      <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">

    <!--<table width="100%" class="headerTable" border="1">-->
    <table  border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="3%" align="center" style="padding:0"><input type="checkbox" name="titleCheck"
                                                                   class="headCheckbox"
                                                                   id="controlBox"
                                                                   onclick="checkAll('titleCheck','workorders')"></td>
            <td width="8%" align="center">公司</td>
            <td width="10%" align="center">工单号</td>
            <td width="6%" align="center">工单状态</td>
            <td width="6%" align="center">工单类型</td>
            <td width="7%" align="center">地点编号</td>
            <td width="12%" align="center">所在地点</td>
            <td width="7%" align="center">开始日期</td>
            <td width="8%" align="center">实施周期(天)</td>
            <td width="5%" align="center">执行人</td>
            <td width="8%" align="center">实际完成日期</td>
            <td width="3%" align="center">差异</td>
            <td width="3%" align="center">超时</td>
        </tr>
    </table>
</div>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        if (rows != null && !rows.isEmpty()) {
    %>
    <div style="overflow-y:scroll;height:330px;width:100%;left:1px;margin-left:0px" align="left">
        <table width="100%" border="1" bordercolor="#666666">

            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" height="22" >
                <td width="3%" align="center"><input type="checkbox" name="workorders" onclick="" value="<%=row.getValue("WORKORDER_NO")%>"></td>
                <td width="8%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("ORG_NAME")%>
                </td>
                <td width="10%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("WORKORDER_NO")%>
                </td>
                <td width="6%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("WORKORDER_FLAG_DESC")%>
                </td>
                <td width="6%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("WORKORDER_TYPE_DESC")%>
                </td>
                <td width="7%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td width="12%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td width="7%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("START_DATE")%>
                </td>
                <td width="8%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("IMPLEMENT_DAYS")%>
                </td>
                <td width="5%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("IMPLEMENT_USER")%>
                </td>
                <td width="8%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("UPLOAD_DATE")%>
                </td>
                <td width="3%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("DIFF")%>
                </td>
                <td width="3%" onclick="do_ShowDetail('<%=row.getValue("WORKORDER_NO")%>');"><%=row.getValue("OVERTIME")%>
                </td>

                <%
                    }    }
                %>
        </table>
    </div>
</form>
<div style="left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>

<%=WebConstant.WAIT_TIP_MSG%>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>

<script language="javascript">

    function do_SearchOrder() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_ShowDetail(WORKORDER_NO)
    {
        var url = "<%=URLDefineList.ORDER_DETAIL_SERVLET%>?act=<%=WebActionConstant.DETAIL_ACTION%>&WORKORDER_NO=" + WORKORDER_NO;
        var screenHeight = window.screen.height - 100;
        var screenWidth = window.screen.width;
        var winstyle = "width=" + screenWidth + ",height=" + screenHeight + ",top=0,left=0,status=yes,resizable=yes,scrollbars=no,toolbar=no,menubar=no,location=no";
        window.open(url, "", winstyle);
    }

    function doChecked() {

    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchOrder();
        }
    }
    function showLocation() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BTS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var Locations = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (Locations) {
            var Location = null;
            for (var i = 0; i < Locations.length; i++) {
                Location = Locations[i];
                dto2Frm(Location, "mainFrm");
            }
        }
    }
    function showUser() {
        var lookUser = "<%=LookUpConstant.LOOK_UP_USER%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUser, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mainFrm");
            }
        }
    }
    function sign(){
        mainFrm.act.value="sign";
        mainFrm.submit();
    }


</script>