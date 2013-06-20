
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.system.basepoint.dto.EtsObjectDTO" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
 <%--
  Created by IntelliJ IDEA.
  User: jialongchuan
  Date: 2008-11-6
  Time: 11:11:26
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>地点最大编号查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
    <script language="javascript" src="/WebLibary/js/CheckboxProcess.js"></script>

</head>

<body onkeydown="autoExeFunction('do_search()')">

<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String deptCode = parser.getParameter("deptCode");
//    String locationCode = parser.getParameter("locationCode");
    String locCategoryDesc = parser.getParameter("locCategoryDesc");
%>
<form method="post" name="mainFrm" action="/servlet/com.sino.ams.system.object.servlet.ObjectMaxServlet">
    <script type="text/javascript">
        printTitleBar("地点最大编号查询")
    </script>
    <table width="100%" border="0" >
        <tr>
            <td align="right" width="13%">公司：</td>
            <td width="26%"><select class="select_style1" style="width:80%" type="text" name="organizationId"><%=request.getAttribute(WebAttrConstant.OU_OPTION)%></select></td>
            <td width="13%" align="right">地点专业：</td>
            <td width="26%">
                <select class="select_style1"  style="width:80%" type="text" name="locationCode"><%=request.getAttribute(WebAttrConstant.CATEGORY_OPTION) %></select>
            </td>
            <td width="22%" align="right">
                <img align="middle" src="/images/eam_images/search.jpg" alt="查询" onClick="do_search(); return false;"> &nbsp;
                <img align="middle" src="/images/eam_images/export.jpg" style="cursor:hand" onclick="do_Export();" title="导出到Excel">&nbsp;
            </td>
    </table>
    <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
        <table border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
            <tr height="22">
                <td width="15%" align="center">地市</td>
                <td width="15%" align="center">地点专业</td>
                <td width="15%" align="center">地点缩写</td>
                <td width="15%" align="center">最大编号</td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="act">

    <div style="overflow-y:scroll;height:322px;width:100%">
        <table width="100%" border="1">
            <%
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr height="22"  style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'"
                onMouseOut="style.backgroundColor='#ffffff'">
                <td width="15%" align="center"><%=row.getValue("ORG_NAME")%></td>
                <td width="15%" align="center"><%=row.getValue("LOC_CATEGORY_DESC")%></td>
                <td width="15%" align="center"><%=row.getValue("LOC_SX")%></td>
                <td width="15%" align="center"><%=row.getValue("MAX_NUMBER")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
</form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>

<jsp:include page="/message/MessageProcess"/>
</body>
</html>
<script type="text/javascript">
    function do_search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
//        mainFrm.action = "/servlet/com.sino.ams.system.object.servlet.ObjectMaxServlet";
        mainFrm.submit();
    }
    function do_Export(){                  //导出execl
    mainFrm.act.value = "<%=WebActionConstant.EXPORT_ACTION%>";
    mainFrm.submit();
}


</script>