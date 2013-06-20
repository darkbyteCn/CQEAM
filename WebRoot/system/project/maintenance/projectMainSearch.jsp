<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  created by Zyun
  Date: 2007-09-26
  Time: 8:23:30
--%>

<html>
<head>
    <title>项目信息维护</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script language="javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script language="javascript" src="/WebLibary/js/jslib.js"></script>
</head>
<body onkeydown="autoExeFunction('do_search()')">

<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String name = parser.getParameter("name");
    String segment1 = parser.getParameter("segment1");
    String source = parser.getParameter("source");
    String projectType = (String) request.getAttribute(WebAttrConstant.PROJECT_TYPE_OPTION);
    String projectStatusCode = (String) request.getAttribute(WebAttrConstant.PROJECT_STATUS_OPTION);
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.system.project.servlet.EtsPaProjectsAllServlet">
<script type="text/javascript">
    printTitleBar("项目信息维护")
</script>
<table width="100%" border="0" >
        <tr>
            <td width="10%" align="right">项目名称：</td>
            <td width="20%"><input class="input_style1" style="width:100%" type="text" name="name" value="<%=name%>"></td>
            <td width="10%" align="right">项目状态：</td>
            <td width="20%"><select name="projectStatusCode" class="select_style1" style="width:100%"><%=projectStatusCode%></select></td>
            <td width="10%" align="center">
               <img src="/images/eam_images/search.jpg" style="cursor:'hand'"  onclick="do_search();" alt="查询">
            </td>
        </tr>
        <tr>
            <td align="right">项目编号：</td>
            <td><input class="input_style1" style="width:100%" type="text" name="segment1" value="<%=segment1%>"></td>
            <td align="right">项目类型：</td>
            <td><select class="select_style1" style="width:100%" name="projectType"><%=projectType%></select></td>
            <td align="center">
                <img src="/images/eam_images/new.jpg" alt="新增项目" onClick="do_add();"></td>
        </tr>
</table>
<div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" class="headerTable" border="1">
        <tr height="20">
            <td width="10%" align="center">项目编号</td>
            <td width="25%" align="center">项目名称</td>
            <td width="10%" align="center">开始日期</td>
            <td width="10%" align="center">截至日期</td>
            <td width="20%" align="center">项目类型</td>
        </tr>
    </table>
</div>
<input type="hidden" name="act">    

<div style="overflow-y:scroll;left:0;width:100%;height:300px">
    <table width="100%" border="1">
        <%
            if (rows != null && rows.getSize() > 0) {
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr height="21" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'"
            onclick="show_detail('<%=row.getValue("PROJECT_ID")%>')">
            <td width="10%" align="center"><%=row.getValue("SEGMENT1")%></td>
            <td width="25%"><%=row.getValue("NAME")%></td>
            <td width="10%" align="center"><%=row.getValue("START_DATE")%></td>
            <td width="10%" align="center"><%=row.getValue("COMPLETION_DATE")%></td>
            <td width="20%"><%=row.getValue("PROJECT_TYPE")%></td>
        </tr>
<%
    } }
%>
    </table>
</div>
    </form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<jsp:include page="/message/MessageProcess"/>
</body>
</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.project.servlet.EtsPaProjectsAllServlet";
    mainFrm.submit();
}
function show_detail(projectId) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.project.servlet.EtsPaProjectsAllServlet?projectId=" + projectId;
    mainFrm.submit();
}
function do_add() {
    mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.project.servlet.EtsPaProjectsAllServlet";
    mainFrm.submit();
}
</script>