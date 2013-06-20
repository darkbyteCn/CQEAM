<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.user.dto.SfUserDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-9-27
  Time: 11:44:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>备件维修管理</title>
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

<body onkeydown="autoExeFunction('do_SearchPlan()');">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    SfUserDTO sfUser = (SfUserDTO) session.getAttribute(WebConstant.USER_INFO);
    String action = reqParser.getParameter("act");
%>
<jsp:include page="/message/MessageProcess"/>

<form action="/servlet/com.sino.ams.web.item.servlet.EtsItemFixfeeServlet" name="mForm" method="post">
    <script type="text/javascript">
        printTitleBar("备件维修管理")
    </script>
    <table border="0" width="100%" class="queryHeadBg">
        <tr>
            <td width="8%" align="right">公司：</td>
            <td width="13%" align="left">
                <select name="company" style="width:60%">
                    <%=request.getAttribute(WebAttrConstant.OU_OPTION)%>
                </select></td>
            <td width="7%" align="right">备件名称：</td>
            <td width="12%" align="left"><input type="text" name="itemName" style="width:80%"
                                                value="<%=reqParser.getParameter("itemName")%>"><a
                    class="linka" style="cursor:'hand'" onclick="do_selectName();">[…]</a>
            </td>

            <td width=6% align="center"><img src="/images/eam_images/search.jpg" alt="查询备件维修"
                                             onClick="do_SearchPlan(); return false;"></td>

        </tr>
        <tr>
            <td align="right" width="7%">创建时间：</td>
            <td align="left" width="15%"><input type="text" name="fromDate"
                                                value="<%=reqParser.getParameter("fromDate")%>" style="width:80%"
                                                title="点击选择日期" readonly class="readonlyInput"
                                                onclick="gfPop.fStartPop(fromDate,'')">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fStartPop(fromDate,'')"></td>
            <td align="right" width="5%">到：</td>
            <td align="left" width="15%"><input type="text" name="toDate" value="<%=reqParser.getParameter("toDate")%>"
                                                style="width:80%"
                                                title="点击选择日期" readonly class="readonlyInput"
                                                onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(fromDate,toDate)"></td>

            <td width="6%" align="center"><img src="/images/eam_images/new_add.jpg" alt="新增备件维修"
                                               onClick="do_CreatePlan(); return false;">
            </td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="systemId">
    <input type="hidden" name="barcode">

      <script type="text/javascript">
        var columnArr = new Array("备件名称", "创建人", "维修费","创建日期","维修日期");
        var widthArr = new Array("15%","15%","15%","15%","15%");
        printTableHead(columnArr, widthArr);
    </script>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px"
     align="left">
    <table width="100%" border="1" bordercolor="#666666">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="do_show('<%=row.getValue("SYSTEM_ID")%>')">

            <td height="22" width="15%"><%=row.getValue("ITEM_NAME")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("USERNAME")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("AMOUNT")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("CREATION_DATE")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("FIX_DATE")%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<%
    }
%>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
</body>
<iframe width=174 height=189 name="gToday:normal:calendar.js" id="gToday:normal:calendar.js"
        src="/WebLibary/js/DateHTML.htm" scrolling="no" frameborder="0"
        style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0;">
</iframe>
</html>
<script type="text/javascript">
    function do_SearchPlan() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mForm.submit();
    }
    function do_CreatePlan() {
        mForm.act.value = "<%=WebActionConstant.NEW_ACTION%>"
        mForm.submit();
    }
    function do_selectName() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_SYS_ITEM%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "mForm");
            }
        } else {
            mForm.itemName.value = '';

        }
    }
    function do_show(id) {
        mForm.systemId.value = id;
        mForm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mForm.action = "/servlet/com.sino.ams.web.item.servlet.EtsItemFixfeeServlet?id=" + id;
        mForm.submit();
    }
</script>