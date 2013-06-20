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
    <title>资产管理指标维护</title>
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
    String kpiNameQuery = parser.getParameter("kpiNameQuery");
    String kpiTypeOptionsQuery = (String) request.getAttribute("KPI_TYPE_OPTIONS_QUERY");
%>
<form method="post" name="mainFrm"  action="/servlet/com.sino.ams.system.kpi.servlet.KpiDefineServlet">
<script type="text/javascript">
    printTitleBar("资产管理指标维护")
</script>
<table width="100%" border="0">
        <tr>
            <td width="15%" align="right">指标名称：</td>
            <td width="20%"><input class="input_style1"  style="width:100%" type="text" name="kpiNameQuery" value="<%=kpiNameQuery%>"></td>
            <td width="20%" align="right">指标统计方式：</td>
            <td width="20%"><select name="kpiTypeOptionsQuery" class="select_style1" style="width:80%"><%=kpiTypeOptionsQuery%></select></td>
            <td width="25%" align="center">
            	<img src="/images/eam_images/search.jpg" style="cursor:'hand'" onclick="do_search();" alt="查询">&nbsp;&nbsp;&nbsp;
            	<img src="/images/eam_images/edit.jpg" alt="维护指标数据" onClick="do_maintain();">
            </td>
        </tr>        
</table>
<div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table width="100%" class="headerTable" border="1">
        <tr height="20">
            <td width="12%" align="center">指标代码</td>
            <td width="18%" align="center">指标名称</td>
            <td width="38%" align="center">指标计算描述</td>
            <td width="12%" align="center">指标统计方式</td>
            <td width="6%" align="center">指标权重(%)</td>
            <td width="6%" align="center">是否有效</td>
        </tr>
    </table>
</div>
<input type="hidden" name="act">    

<div style="overflow-y:scroll;left:0;width:100%;height:332px">
    <table width="100%" border="1">
        <%
            if (rows != null && rows.getSize() > 0) {
                for (int i = 0; i < rows.getSize(); i++) {
                    row = rows.getRow(i);
        %>
        <tr height="22" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="12%" ><%=row.getValue("KPI_CODE")%></td>
            <td width="18%"><%=row.getValue("KPI_NAME")%></td>
            <td width="38%" ><%=row.getValue("KPI_DESC")%></td>
            <td width="12%" ><%=row.getValue("KPI_TYPE_NAME")%></td>
            <td width="6%"><%=row.getValue("KPI_VALUE")%></td>
            <td width="6%">
            	<% if ("Y".equalsIgnoreCase((String)row.getValue("IS_ENABLE"))) {%> 有效 
            	<%} else if ("N".equalsIgnoreCase((String)row.getValue("IS_ENABLE"))){%>无效<%} %>
            </td>            
        </tr>
<%
    } }
%>
    </table>
</div>
    </form>
<div style="position:absolute;top:91%;left:0; right:20"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<jsp:include page="/message/MessageProcess"/>
</body>
</html>
<script type="text/javascript">
function do_search() {
    document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
    mainFrm.submit();
}
function show_detail(projectId) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.kpi.servlet.KpiDefineServlet?projectId=" + projectId;
    mainFrm.submit();
}
function do_maintain() {
	document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
    mainFrm.act.value = "<%=WebActionConstant.EDIT_ACTION%>";
    mainFrm.submit();
}
</script>