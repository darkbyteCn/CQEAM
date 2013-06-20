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
  Date: 2008-2-21
  Time: 15:31:13
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>组别地点对照</title>
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
//    String company = (String) request.getAttribute(WebAttrConstant.CITY_OPTION);
    String groupId = (String) request.getAttribute(WebAttrConstant.GROUP_OPTION);

//    String company = parser.getParameter("company");
    String objectCategory = request.getAttribute(WebAttrConstant.OBJECT_CATEGORY_OPTION).toString();
     String ret = request.getParameter("ret");
%>
<form method="post" name="mainFrm">
<script type="text/javascript">
    printTitleBar("组别地点对照")
</script>
<table width="100%" border="0" >
        <tr>
            <td width="12%" align="right">组别：</td>
            <td width="26%"><select  class="select_style1" style="width:100%"  name="groupId" ><%=groupId%></select></td>
            <td width="13%" align="right">地点：</td>
            <td width="27%"><select name="objectCategory" class="select_style1" style="width:100%"><%=objectCategory%></select></td>
            <td width="22%" align="center">
             <img src="/images/eam_images/search.jpg" align="middle" onclick="do_search();" alt="查询">
             <img src="/images/eam_images/new.jpg"  align="middle" alt="增加" onClick="do_add(); return false;">
            </td>
        </tr>
</table>
   <div style="left:1px;width:100%;overflow-y:scroll" class="crystalScroll">
    <table  border="1" width="100%" class="headerTable" cellpadding="0" cellspacing="0">
        <tr height="22">
            <!--<td width="8%" align="center">地市</td>-->
            <td width="25%" align="center">组别</td>
            <td width="31%" align="center">查询专业地点</td>
        </tr>
    </table>
</div>
<input type="hidden" name="act">
<div style="overflow-y:scroll;left:0px;width:100%;height:360px">
    <table width="100%" border="1" >
<%
    if (rows != null && rows.getSize() > 0) {
        for (int i = 0; i < rows.getSize(); i++) {
            row = rows.getRow(i);
%>
        <tr height="22" style="cursor:'hand'" onMouseMove="style.backgroundColor='#EFEFEF'" onMouseOut="style.backgroundColor='#ffffff'">
            <%--<td width="8%" align="center" onclick="show_detail('<%=row.getValue("SYSTEMID")%>')"><%=row.getValue("COMPANY")%></td>--%>
            <td width="25%" onclick="show_detail('<%=row.getValue("SYSTEMID")%>')"><%=row.getValue("GROUP_NAME")%></td>
            <td width="31%" align="left" onclick="show_detail('<%=row.getValue("SYSTEMID")%>')"><%=row.getValue("OBJECT_CATEGORY")%></td>
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
    mainFrm.action = "/servlet/com.sino.ams.system.comparison.servlet.EtsObjectCatGroupServlet";
    mainFrm.submit();
}

function show_detail(systemid) {
    mainFrm.act.value = "<%=WebActionConstant.DETAIL_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.comparison.servlet.EtsObjectCatGroupServlet?systemid="+systemid;
    mainFrm.submit();
}

function do_add() {
    mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
    mainFrm.action = "/servlet/com.sino.ams.system.comparison.servlet.EtsObjectCatGroupServlet";
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