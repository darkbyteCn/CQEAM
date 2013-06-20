<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.data.*" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.ams.dzyh.constant.DzyhActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>

</head>

<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_Search()');">
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.dzyh.servlet.EtsItemInfoServlet">
    <script type="text/javascript">
        printTitleBar("低值易耗台账>>低值易耗品汇总查询");
    </script>
    <table border="0" width="100%" class="queryHeadBg" id="table1" bgcolor="#E9EAE9">
        <tr>
            <td width="10%" align="right">目录编号：</td>
            <td width="12%" colspan="2">
                <input type="text" name="eiiItemCategory2" style="width:100%" value="<%=parser.getParameter("eiiItemCategory2")%>">
            </td>
            <td width="10%" align="right">品&nbsp;&nbsp;&nbsp;名：</td>
            <td width="12%" colspan="2">
                <input type="text" name="eiiItemName" style="width:100%" value="<%=parser.getParameter("eiiItemName")%>">
            </td>
            <td width="10%" align="right">规格型号：</td>
            <td width="12%" colspan="2">
                <input type="text" name="eiiItemSpec" style="width:100%" value="<%=parser.getParameter("eiiItemSpec")%>">
            </td>
            <td width="10%" align="right">使用部门：</td>
            <td width="12%" colspan="2">
                <input type="text" name="responsibilityDept" style="width:100%" value="<%=parser.getParameter("responsibilityDept")%>">
            </td>
            <td width="20%" align="right">
            <img src="/images/eam_images/search.jpg" alt="查询" onClick="do_Search(); return false;"></td>
            <td>&nbsp;&nbsp;&nbsp;</td>
        </tr>
    </table>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"
               class="headerTable">
            <input type="hidden" name="systemid" value="<%=parser.getParameter("systemid")%>">
            <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

            <tr>
                <td height="22" width="10%" align="center">目录编号</td>
                <td height="22" width="15%" align="center">品名</td>
                <td height="22" width="20%" align="center">规格型号</td>
                <td height="22" width="20%" align="center">使用部门</td>
                <td height="22" width="10%" align="center">数量</td>
            </tr>
        </table>
    </div>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        System.out.println(rows+"-------->>>>>>>>>>>>>>>>>>");
        if (rows != null && !rows.isEmpty()) {
            Row row = null;
    %>
    <div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0"
         align="left">
        <table width="100%" border="1" bordercolor="#666666">
            <%
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
            %>
            <tr class="dataTR">
                <td height="22" width="10%"><%=row.getValue("EII_ITEM_CATEGORY2")%>
                </td>
                <td height="22" width="15%"><%=row.getValue("EII_ITEM_NAME")%>
                </td>
                <td height="22" width="20%"><%=row.getValue("EII_ITEM_SPEC")%>
                </td>
                <td height="22" width="20%"><%=row.getValue("EII_DEPT_NAME")%>
                </td>
                <td height="22" width="10%"><%=row.getValue("ITEM_QTY")%>
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

<%=WebConstant.WAIT_TIP_MSG%>
</body>

<script language="javascript">

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=DzyhActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

</script>