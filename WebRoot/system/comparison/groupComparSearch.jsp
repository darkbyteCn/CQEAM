
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<%@ page import="com.sino.ams.constant.AMSActionConstant" %>
<%@ page import="com.sino.base.util.StrUtil" %>
<%@ page import="com.sino.ams.constant.URLDefineList" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Zyun
  Date: 2008-1-10
  Time: 11:55:59
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>专业地点对照</title>
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
<body onkeydown="autoExeFunction('do_search()')" onload="initPage();">

<%=WebConstant.WAIT_TIP_MSG%>
<%
    RequestParser parser = new RequestParser();
    parser.transData(request);
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    Row row = null;
    String company = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
//    String company = parser.getParameter("company");
    String objectCategory = request.getAttribute(WebAttrConstant.OBJECT_CATEGORY_OPTION).toString();
     String ret = request.getParameter("ret");
%>
<form method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("专业地点对照")
</script>
<table width="100%" border="0" >
        <tr>
            <td width="12%" align="right">地市：</td>
            <td width="26%"><select  class="select_style1" style="width:100%"  name="company" ><%=company%></select></td>
            <td width="13%" align="right">专业名称：</td>
            <td width="27%"><select name="objectCategory" class="select_style1" style="width:100%"><%=objectCategory%></select></td>
            <td width="22%" align="center">
             <img align="middle"  src="/images/eam_images/new.jpg" alt="点击新增" onClick="do_add(); return false;">
             <img align="middle"  src="/images/eam_images/search.jpg" alt="查询" onclick="do_search();" >&nbsp;&nbsp;&nbsp;
            </td>
        </tr>
</table>
   <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table  border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <td width="8%" align="center">地市</td>
            <td width="25%" align="center">专业名称</td>
            <td width="31%" align="center">查询专业地点</td>
        </tr>
    </table>
</div>
<input type="hidden" name="act">
<div style="overflow-y:scroll;left:0px;width:100%;height:320px">
    <table width="100%" border="1" >
<%
    if (rows != null && rows.getSize() > 0) {                    
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <td width="8%" align="center" onclick="show_detail('<%=row.getValue("SYSTEMID")%>')"><%=row.getValue("COMPANY")%></td>
            <td width="25%" onclick="show_detail('<%=row.getValue("SYSTEMID")%>')"><%=row.getValue("DESCRIPTION")%></td>
            <td width="31%" align="left" onclick="show_detail('<%=row.getValue("SYSTEMID")%>')"><%=row.getValue("SEARCH_CATEGORY")%></td>
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
    mainFrm.action = "/servlet/com.sino.ams.system.comparison.servlet.EtsObjectCategoryServlet";
    mainFrm.submit();
}

function show_detail(systemid) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.comparison.servlet.EtsObjectCategoryServlet?systemid="+systemid;
    mainFrm.submit();
}

function do_add() {
    mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.comparison.servlet.EtsObjectCategoryServlet";
    mainFrm.submit();
}

   function initPage() {
        var ret = "<%=ret%>";
        if (ret == "Y") {
            alert("操作成功！");
        } else if (ret == "N") {
            alert("操作失败！");
        }
    }
</script>