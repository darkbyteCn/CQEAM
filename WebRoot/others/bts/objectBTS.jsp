<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-9-25
  Time: 16:24:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>基站维修管理</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/LookUp.js"></script>
</head>

<body onkeydown="autoExeFunction('do_SearchPlan()');">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");

%>
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.web.bts.servlet.EtsObjectFixfeeServlet" name="obForm" method="post">
    <script type="text/javascript">
        printTitleBar("基站维修管理")
    </script>
    <table width="100%" class="queryHeadBg">
        <tr>
            <td width="5%" align="right">公司：</td>
            <td width="13%" align="left">
                <select name="company" style="width:80%">
                    <%=request.getAttribute(WebAttrConstant.OU_OPTION)%>
                </select></td>

            <td align="right" width="5%">基站名称：</td>
            <td align="left"><input type="text" name="workorderObjectName"
                                    style="width:80%" value="<%=reqParser.getParameter("workorderObjectName")%>"><a href="#"
                    class="linka" style="cursor:'hand'" onclick="do_selectName();">[…]</a></td>
            <td width=8% align="center"><img src="/images/eam_images/search.jpg" alt="查询基站维护信息"
                                             onClick="do_SearchPlan(); return false;"></td>
        </tr>
        <tr>
            <td align="right" width="5%">创建时间：</td>
            <td width="20%"><input type="text" name="fromDate" value="<%=reqParser.getParameter("fromDate")%>"
                                   style="width:80%"  class="readonlyInput"
                                   title="点击选择日期" readonly  onclick="gfPop.fStartPop(fromDate,'')">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fStartPop(fromDate,'')"></td>
            <td align="right" width="5%">到：</td>
            <td width="20%"><input type="text" name="toDate" value="<%=reqParser.getParameter("toDate")%>"
                                   style="width:80%"    class="readonlyInput"
                                   title="点击选择日期" readonly  onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(fromDate,toDate)"></td>

            <td width="10%" align="center"><img src="/images/eam_images/new.jpg" alt="增加基站维护信息"
                                                onclick="do_add()"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="workorderObjectNo">
    <input type="hidden" name="systemId">

    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%"  border="1" cellpadding="2" cellspacing="0"
               class="headerTable">
            <tr>
                <td height="22" width="15%" align="center">基站名称</td>
                <td height="22" width="15%" align="center">基站号</td>
                <td height="22" width="15%" align="center">维修日期</td>
                <td height="22" width="15%" align="center">维修费</td>
                <td height="22" width="15%" align="center">创建日期</td>
                <td height="22" width="15%" align="center">创建人</td>
            </tr>
        </table>
    </div>

    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
                if (rows != null && !rows.isEmpty()) {
            %>
            <%
                Row row = null;
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR" onclick="do_show('<%=row.getValue("SYSTEM_ID")%>')">

                <td height="22" width="15%"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
                </td>
                <td height="22" width="15%"><%=row.getValue("WORKORDER_OBJECT_CODE")%>
                </td>
                <td height="22" width="15%"><%=row.getValue("FIX_DATE")%>
                </td>
                <td height="22" width="15%"><%=row.getValue("AMOUNT")%>
                </td>
                <%--<td height="22" width="15%"><%=row.getValue("REMARK")%>
                </td>--%>
                <td height="22" width="15%"><%=row.getValue("CREATION_DATE")%>
                </td>
                <td height="22" width="15%"><%=row.getValue("USERNAME")%>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
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
        obForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>"
        obForm.submit();
    }
    function do_add() {
        obForm.act.value = "<%=WebActionConstant.NEW_ACTION%>"
        obForm.submit();
    }
    function do_show(id) {
        obForm.systemId.value = id;
        obForm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        obForm.action = "/servlet/com.sino.ams.web.bts.servlet.EtsObjectFixfeeServlet?id=" + id;
        obForm.submit();

    }
    function do_selectName() {
        var lookUpName = "<%=LookUpConstant.LOOK_UP_BTS%>";
        var dialogWidth = 50;
        var dialogHeight = 30;
        var users = getLookUpValues(lookUpName, dialogWidth, dialogHeight);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "obForm");
            }
        } else {
            obForm.workorderObjectName.value = '';
        }
    }
</script>