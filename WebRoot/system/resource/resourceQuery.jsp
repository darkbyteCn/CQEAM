<%@ page contentType="text/html; charset=GBK" language="java"   %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.sinoflow.constant.WebAttrConstant" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>URL资源查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script type="text/javascript" src="/WebLibary/js/Constant.js"></script>
    <script type="text/javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/FormProcess.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/AppStandard.js"></script>
</head>
<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_SearchResource()');" onload="do_SetPageWidth();">

 <jsp:include page="/message/MessageProcess"/>
<%
    RequestParser parser = new RequestParser();

    parser.transData(request);
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.sinoflow.framework.resource.servlet.SfResDefineServlet">
    <script type="text/javascript">
        printTitleBar("菜单栏目维护");
    </script>
    <table border="0" width="100%"  id="table1">
        <tr>
            <td width="6%" align="right">名称：</td>
            <td width="20%"><input type="text" name="resName" class="input_style1" style="width:100%"
                                   value="<%=parser.getParameter("resName")%>"></td>
            <td width="6%" align="right">URL：</td>
            <td width="30%"><input type="text" name="resUrl"  class="input_style1" style="width:100%"
                                   value="<%=parser.getParameter("resUrl")%>"></td>
            <td width="8%" align="right">有效性：</td>
            <td width="8%">
                <select name="enabled" id="enabled"  class="select_style1" style="width:100%">
                    <%=request.getAttribute(WebAttrConstant.ENABLED_OPTION)%>
                </select>
            </td>
            <td width="8%" align="center">
             <img align="middle" src="/images/eam_images/search.jpg" alt="查询栏目" onclick="do_SearchResource(); return false;">
            </td>
            <td width="8%" align="center">
             <img align="middle" src="/images/eam_images/new.jpg" alt="点击新增" onclick="do_CreateResource(); return false;">
            </td>
        </tr>
    </table>
 		<input type="hidden" name="act" value="<%=parser.getParameter("act")%>"/>
           <input type="hidden" name="resParId" value="<%=parser.getParameter("resId")%>"/>
    
    <div id="headDiv" style="overflow-y:scroll;width:100%;left:1px;margin-left:0px" align="left">
        <table width="100%" align="left" cellpadding="2" cellspacing="1" class="headerTable">
            <tr>
                <td height="22" width="12%" align="center">编号</td>
                <td height="22" width="15%" align="center">栏目名称</td>
                <td height="22" width="44%" align="center">栏目URL</td>
                <td height="22" width="15%" align="center">父栏目</td>
                <td height="22" width="6%" align="center">有效性</td>
                <td height="22" width="6%" align="center">可见性</td>
            </tr>
        </table>
    </div>

    <div id="dataDiv" style="overflow-y:scroll;height:330px;width:100%;left:1px;margin-left:0px" align="left">
        <table width="100%"  border="1" style="table-layout: fixed;word-wrap: break-word">
            <%
                RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
                if (rows != null && !rows.isEmpty()) {
                    Row row = null;
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
            %>
            <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("SYSTEM_ID")%>'); return false;">
                <td height="22" width="12%"><%=row.getValue("RES_ID")%></td>
                <td height="22" width="16%"><%=row.getValue("RES_NAME")%></td>
                <td height="22" width="44%"><%=row.getValue("RES_URL")%></td>
                <td height="22" width="16%"><%=row.getValue("PAR_NAME")%></td>
                <td height="22" width="6%"><%=row.getValue("ENABLED")%></td>
                <td height="22" width="6%"><%=row.getValue("VISIBLE")%></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
  </form>
<div id="pageNaviDiv"><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%>
</div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script type="text/javascript">

    function do_SearchResource() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.submit();
    }

    function do_CreateResource() {
        mainFrm.resName.value = "";
        mainFrm.resUrl.value = "";
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.submit();
    }

    function do_ShowDetail(systemId) {
        mainFrm.action = "/servlet/com.sino.sinoflow.framework.resource.servlet.SfResDefineServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&systemId=" + systemId;
        mainFrm.submit();
    }

    function refreshTree() {
        var action = mainFrm.act.value;
        if (action == "<%=WebActionConstant.UPDATE_ACTION%>" || action == "<%=WebActionConstant.DELETE_ACTION%>" || action == "<%=WebActionConstant.CREATE_ACTION%>") {
            parent.contents.location.reload();
            parent.parent.banner.location = "/servlet/com.sino.sinoflow.framework.resource.servlet.ResourceTreeServlet";
        }
    }

</script>