<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.data.*" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
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
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValueSetServlet">
    <script type="text/javascript">
        printTitleBar("资产分析字典分类维护");
    </script>
    <table border="0" width="100%" class="queryHeadBg" id="table1" bgcolor="#E9EAE9">
        <tr>
            <td width="15%" align="right">分类代码：</td>
            <td width="30%" colspan="2">
                <input type="text" name="code" style="width:100%" value="<%=parser.getParameter("code")%>">
            </td>
            <td width="15%" align="right">分类名称：</td>
            <td width="30%" align="right" colspan="2">
                <input type="text" name="name" style="width:100%" value="<%=parser.getParameter("name")%>"></td>
            <td width="10%"><img src="/images/eam_images/search.jpg" alt="查询分类" onClick="do_Search(); return false;"></td>
        </tr>
        <tr>
            <td width="15%" align="right">是否有效：</td>
            <td width="15%"><%=request.getAttribute(WebAttrConstant.ENABLED_RADIO)%>
            </td>
            <td width="15%" align="right">是否内置：</td>
            <td width="15%"><%=request.getAttribute(WebAttrConstant.IS_INNER_RADIO)%>
            </td>
            <td width="15%" align="right">是否可维护：</td>
            <td width="15%"><%=request.getAttribute(WebAttrConstant.MAINTAIN_RADIO)%>
            </td>
            <td width="10%"><img src="/images/eam_images/new_add.jpg" alt="新增分类" onClick="do_Create(); return false;"></td>
        </tr>
    </table>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table width="100%" align="left" border="1" cellpadding="2" cellspacing="0"
               class="headerTable">
            <input type="hidden" name="flexValueSetId" value="<%=parser.getParameter("flexValueSetId")%>">
            <input type="hidden" name="act" value="<%=parser.getParameter("act")%>">

            <tr>
                <td height="22" width="15%" align="center">分类代码</td>
                <td height="22" width="20%" align="center">分类名称</td>
                <td height="22" width="20%" align="center">分类描述</td>
                <td height="22" width="10%" align="center">是否有效</td>
                <td height="22" width="10%" align="center">是否内置</td>
                <td height="22" width="10%" align="center">可否维护</td>
                <td height="22" width="15%" align="center">创建时间</td>
            </tr>
        </table>
    </div>
    <%
        RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
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
            <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("FLEX_VALUE_SET_ID")%>'); return false;">
                <td height="22" width="15%"><%=row.getValue("CODE")%>
                </td>
                <td height="22" width="20%"><%=row.getValue("NAME")%>
                </td>
                <td height="22" width="20%"><%=row.getValue("DESCRIPTION")%>
                </td>
                <td height="22" width="10%"><%=row.getValue("ENABLED")%>
                </td>
                <td height="22" width="10%"><%=row.getValue("IS_INNER")%>
                </td>
                <td height="22" width="10%"><%=row.getValue("MAINTAIN_FLAG")%>
                </td>
                <td height="22" width="15%" align="center"><%=row.getValue("CREATION_DATE")%>
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
</html>
<script language="javascript">

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_Create() {
        mainFrm.flexValueSetId.value = "";
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

    function do_ShowDetail(primaryKey) {
        mainFrm.flexValueSetId.value = primaryKey;
        mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
        mainFrm.submit();
    }
</script>