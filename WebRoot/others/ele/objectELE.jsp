<%@ page import="com.sino.ams.constant.LookUpConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2007-9-26
  Time: 17:24:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head><title>基站用电维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/calendar.js"></script>
</head>


<body onkeydown="autoExeFunction('do_selectName()');">
<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String action = reqParser.getParameter("act");

%>
<jsp:include page="/message/MessageProcess"/>
<form action="/servlet/com.sino.ams.web.ele.servlet.EtsObjectEleServlet" name="eForm" method="post">
    <script type="text/javascript">
        printTitleBar("基站用电维护")
    </script>
    <table border="0" width="100%" class="queryHeadBg">
        <tr><td width="5%" align="right">公司：</td>
            <td width="10%" align="left">
                <select name="company" style="width:90%">
                    <%=request.getAttribute(WebAttrConstant.OU_OPTION)%>
                </select>
            </td>
            <td align="right">基站名称：</td>
            <td align="left"><input type="text" name="workorderObjectName"
                                    style="width:80%" value="<%=reqParser.getParameter("workorderObjectName")%>"><a
                    class="linka" style="cursor:'hand'" onclick="do_selectName();">[…]</a></td>
            <td align="right" width="5%">会计期间：</td>
            <td align="left"><input type="text" name="period" style="width:80%"
                                    value="<%=StrUtil.nullToString(request.getParameter("period"))%>"></td>
        </tr>
        <tr>
            <td align="right" width="5%">创建时间：</td>
            <td width="12%"><input type="text" name="fromDate" value="<%=reqParser.getParameter("fromDate")%>"
                                   style="width:80%"
                                   title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fStartPop(fromDate,'')">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fStartPop(fromDate,'')"></td>

            <td align="right" width="5%">到：</td>
            <td width="12%"><input type="text" name="toDate" value="<%=reqParser.getParameter("toDate")%>"
                                   style="width:80%"
                                   title="点击选择日期" readonly class="readonlyInput" onclick="gfPop.fEndPop(fromDate, toDate)">
                <img src="/images/calendar.gif" alt="点击选择日期" onclick="gfPop.fEndPop(fromDate,toDate)"></td>
            <td width=5% align="right"><img src="/images/eam_images/search.jpg" alt="查询基站维护信息"
                                            onClick="do_SearchPlan(); return false;"></td>

            <td width="10%" align="center"><img src="/images/eam_images/new.jpg" alt="增加基站维护信息"
                                                onclick="do_add()"></td>
        </tr>
    </table>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="workorderObjectNo">
    <input type="hidden" name="systemid">

 <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"
          class="headerTable">

        <tr>
            <td height="22" width="15%" align="center">基站名称</td>
            <td height="22" width="15%" align="center">会计期间</td>
            <td height="22" width="15%" align="center">用电数量</td>
            <td height="22" width="15%" align="center">单价</td>
            <td height="22" width="15%" align="center">创建人</td>
            <td height="22" width="15%" align="center">创建时间</td>
        </tr>
    </table>
</div>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:362px;width:100%;margin-left:0px"
     align="left">
    <table width="100%" border="1" bordercolor="#666666">
        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="do_show('<%=row.getValue("SYSTEMID")%>')">

            <td height="22" width="15%"><%=row.getValue("WORKORDER_OBJECT_NAME")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("PERIOD")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("QUANTITY")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("UNIT_PRICE")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("USERNAME")%>
            </td>
            <td height="22" width="15%"><%=row.getValue("CREATION_DATE")%>
            </td>
        </tr>
        <%
            } }
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
        eForm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        eForm.submit();
    }
    function do_add() {
        eForm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        eForm.submit()
    }
    function do_show(id) {
        eForm.systemid.value = id;
        eForm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        eForm.action = "/servlet/com.sino.ams.web.ele.servlet.EtsObjectEleServlet?id=" + id;
        eForm.submit();
    }
    function do_selectName() {
        var url = "/servlet/com.sino.ams.bean.AMSLookUpServlet?lookUpName=<%=LookUpConstant.LOOK_UP_BTS%>";
        var popscript = "dialogWidth:50;dialogHeight:30;center:yes;status:no;scrollbars:no";
        var users = window.showModalDialog(url, null, popscript);
        if (users) {
            var user = null;
            for (var i = 0; i < users.length; i++) {
                user = users[i];
                dto2Frm(user, "eForm");
            }
        } else {
            eForm.workorderObjectName.value = '';

        }
    }
</script>