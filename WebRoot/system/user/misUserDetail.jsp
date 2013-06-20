<%--
  Created by IntelliJ IDEA.
  User: yuyao
  Date: 2009-1-6
  Time: 15:16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@ page import="com.sino.base.constant.web.WebActionConstant" %>
<%@ page import="com.sino.base.constant.db.QueryConstant" %>
<%@ page import="com.sino.base.data.Row" %>
<%@ page import="com.sino.base.data.RowSet" %>
<%@ page import="com.sino.base.web.request.upload.RequestParser" %>
<%@ page import="com.sino.base.constant.web.WebConstant"%>
<%@ page import="com.sino.ams.constant.WebAttrConstant" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>MIS用户信息查询</title>
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/main.css">
    <link rel="stylesheet" type="text/css" href="/WebLibary/css/eam.css">
    <script language="javascript" src="/WebLibary/js/Constant.js"></script>
    <script language="javascript" src="/WebLibary/js/CommonUtil.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBar.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarConst.js"></script>
    <script type="text/javascript" src="/WebLibary/js/SinoToolBarScroll.js"></script>
    <script language="javascript" src="/WebLibary/js/FormProcess.js"></script>

</head>

<body leftmargin="1" topmargin="0" onkeydown="autoExeFunction('do_SearchRole()')">

<%
    RequestParser reqParser = new RequestParser();
    reqParser.transData(request);
    String deptName = reqParser.getParameter("deptName");
    String userName = reqParser.getParameter("userName");
    String employeeNumber = reqParser.getParameter("employeeNumber");
    String action = reqParser.getParameter("act");
    String ou = (String)request.getAttribute(WebAttrConstant.CITY_OPTION)  ;
%>
<form name="mainFrm" method="POST" action="/servlet/com.sino.ams.system.misuser.servlet.EamMisUserServlet">
    <script language="javascript">
        printTitleBar("HR用户信息查询");
    </script>
    <input type="hidden" name="act" value="<%=action%>">
    <input type="hidden" name="groupId" value="">
    <jsp:include page="/servlet/com.sino.framework.servlet.MessageProcessServlet"/>
    <table border="0" width="100%" style="width:100%;TABLE-LAYOUT:fixed;word-break:break-all">
        <tr>
            <td width="12%" align="right">公司名称：</td>
            <td width="20%"><select name="organizationId" class="select_style1" style="width:100%" ><%=ou%></select></td>
            <td width="12%" align="right">部门名称：</td>
            <td width="20%"><input type="text" name="deptName" class="input_style1"  style="width:100%" value="<%=deptName%>"></td>
            <td width="8%" align="center"><img src="/images/eam_images/search.jpg" alt="查询用户信息"
                                               onClick="do_Search(); return false;"></td>
        </tr>
        <tr>
            <td width="12%" align="right">用户名字：</td>
            <td width="20%"><input type="text" name="userName" class="input_style1" style="width:100%" value="<%=userName%>"></td>
            <td width="12%" align="right">员工编号：</td>
            <td width="20%"><input type="text" name="employeeNumber" class="input_style1"  style="width:100%" value="<%=employeeNumber%>"></td>
        </tr>
    </table>

    <script type="text/javascript">
            var columnArr = new Array("地市名称","员工名称","员工编号","部门名称");
            var widthArr = new Array("25%","20%","15%","40%");
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
        <tr class="dataTR">
            <td style="word-wrap:break-word" height="22" align="center" width="25%"><%=row.getValue("COMPANY")%></td>
            <td style="word-wrap:break-word" height="22"  width="20%"><%=row.getValue("USER_NAME")%></td>
            <td style="word-wrap:break-word" height="22" align="center" width="15%"><%=row.getValue("EMPLOYEE_NUMBER")%></td>
            <td style="word-wrap:break-word" height="22" width="40%"><%=row.getValue("DEPT_NAME")%></td>
        </tr>
        <%
            }  }
        %>
    </table>
</div>
 </form>
<%
    if (rows != null && !rows.isEmpty()) {
%>
<div id="pageNaviDiv" style="position:absolute;top:87%;left:0;"><%=request.getAttribute(QueryConstant.SPLIT_PAGE_HTML)%></div>
<%
    }
%>
<%=WebConstant.WAIT_TIP_MSG%>
</body>
</html>
<script language="javascript">

    function do_Search() {
        document.getElementById("$$$waitTipMsg$$$").style.visibility = "visible";
        mainFrm.act.value = "<%=WebActionConstant.QUERY_ACTION%>";
        mainFrm.action = "/servlet/com.sino.ams.system.misuser.servlet.EamMisUserServlet";
        mainFrm.submit();
    }
</script>