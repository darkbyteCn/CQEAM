<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2008-12-4
  Time: 16:11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.base.util.StrUtil" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>同步权限定义</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>

</head>

<body leftmargin="1" topmargin="0" onload="doChecked();" onkeydown="do_check()">

<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
   String code= (String)request.getAttribute("COMPCODE");
    String roleId = reqParser.getParameter("roleId");
    String rolename = reqParser.getParameter("rolename");
    String description = reqParser.getParameter("description");
    String action = reqParser.getParameter("act");
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.synchronize.servlet.AmsAssetsSynResponsibilityServlet">
    <script language="javascript">
        printTitleBar("同步权限定义");
    </script>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="groupId" value="">
    <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet"/>
    <table border="0" width="100%" id="table1" bgcolor="#eeeeee">
        <tr>
            <td width="6%" align="right">地市名称：</td>
            <td width="11%"><select style="width:100%" name="companyCode" ><%=code%></select></td>
            <td width="12%" align="right">用户：</td>
            <td width="20%"><input type="text" name="fullName"  style="width:100%" value="<%=reqParser.getParameter("fullName")%>"></td>
            <td width="8%" align="center"><img src="/images/button/query.gif" alt="查询权限"
                                               onClick="do_SearchRole(); return false;"></td>
            <td width="8%" align="center"><img src="/images/button/new.gif" alt="点击新增"
                                               onClick="do_CreateRole(); return false;"></td>
        </tr>
    </table>

    <script type="text/javascript">
            var columnArr = new Array("公司","用户","导入职责");
            var widthArr = new Array("30%","20%","50%");
            printTableHead(columnArr,widthArr);
        </script>
<%
    RowSet rows = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    if (rows != null && !rows.isEmpty()) {
%>
<div style="overflow-y:scroll;height:362px;width:100%;left:1px;margin-left:0px">
    <table width="100%" border="1" bordercolor="#666666">

        <%
            Row row = null;
            for (int i = 0; i < rows.getSize(); i++) {
                row = rows.getRow(i);
        %>
        <tr class="dataTR" onclick="do_ShowDetail('<%=row.getValue("RESP_ID")%>;<%=row.getValue("USER_ID")%>;<%=row.getValue("ORGANIZATION_ID")%>'); return false;">
            <td style="word-wrap:break-word" height="22" width="30%"><%=row.getValue("ORG_NAME")%></td>
            <td style="word-wrap:break-word" height="22" width="20%"><%=row.getValue("FULL_NAME")%></td>
            <td style="word-wrap:break-word" height="22" width="50%"><%=row.getValue("RESPONSIBILITY_NAME")%></td>
        </tr>
        <%
            }  }
        %>
    </table>
</div>
 </form>
<div><%=StrUtil.nullToString(request.getAttribute(QueryConstant.SPLIT_PAGE_HTML))%></div>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>

<script language="javascript">

    function do_SearchRole() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.AmsAssetsSynResponsibilityServlet";
        mainFrm.submit();
    }

    function do_CreateRole() {
        mainFrm.act.value = "<%=WebActionConstant.NEW_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.AmsAssetsSynResponsibilityServlet";
        mainFrm.submit();
    }

    function do_ShowDetail(resId) {
         var resId1=resId.split(";");
          var userId= resId1[1]     ;
        var    resId2=resId1[0];
        var organizationId=resId1[2]      ;
        mainFrm.action = "/servlet/com.sino.ams.synchronize.servlet.AmsAssetsSynResponsibilityServlet?act=<%=WebActionConstant.DETAIL_ACTION%>&responsibilityId=" + resId2+"&userId="+userId+"&organizationId="+organizationId;
        mainFrm.submit();
    }

    function doChecked() {

    }

    function do_check() {
        if (event.keyCode == 13) {
            do_SearchRole();
        }
    }
</script>